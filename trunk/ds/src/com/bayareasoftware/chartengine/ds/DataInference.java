/*
 * Copyright 2008-2010 Bay Area Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.bayareasoftware.chartengine.ds;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import au.com.bytecode.opencsv.CSVReader;

import com.bayareasoftware.chartengine.ds.util.HtmlTable;
import com.bayareasoftware.chartengine.ds.util.HtmlTag;
import com.bayareasoftware.chartengine.ds.util.HtmlUtil;
import com.bayareasoftware.chartengine.ds.util.XLS2Data;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.InferenceException;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.model.TableSynopsis;
import com.bayareasoftware.chartengine.util.URLUtil;
import com.google.gdata.util.ServiceException;

/**
 * stateless service to infer data
 */
public class DataInference {

    private static final Log log = LogFactory.getLog(DataInference.class);
    // maximum number of rows to examine while doing inferencing
    protected static final int MAX_ROWS_EXAMINED = 1000;    
    protected DataInference() {}
    private static final DataInference instance = new DataInference();
    protected static final String TBL_PREFIX = "TBL_";
    
    public static DataInference get() { return instance; }
    
    protected void assert200(HttpURLConnection conn, String urlString) throws InferenceException,IOException {
        int code = conn.getResponseCode();
        URL url = conn.getURL();
        String host = null;
        if (url != null) {
            host = url.getHost();
        }
        switch (code) {
        case 200: // :-)
            break;
        case 400:
            throw new InferenceException("Bad request: " + urlString, code);
        case 401:
            // unauthorized
            throw new InferenceException("Unauthorized: " + urlString, code, host);
        case 403:
            // forbidden
            throw new InferenceException("Forbidden: " + urlString,code,host);
        case 404:
            // page not found
            throw new InferenceException("Page not found: " + urlString, code);
        case 407:
            throw new InferenceException("Proxy authentication required: " + urlString,code);
        case -1:
            throw new InferenceException("Response from "+ urlString + " is not valid HTTP");
        default:
            throw new InferenceException("non-200 HTTP response code: " + code, code);
        }
        
    }
    
    /** 
     * if maxRows is < 0, then MAX_ROWS_EXAMINED 
     */
    public InferredData inferFromURL(String url, int maxrows, int maxSynopsisRows) throws InferenceException {
        return inferFromURL(url, null,null, null, maxrows, maxSynopsisRows);
    }

    /** 
     * if maxRows is < 0, then MAX_ROWS_EXAMINED 
     */
    public InferredData inferFromURL(String url, int maxrows) throws InferenceException {
        return inferFromURL(url, null,null, null, maxrows, 10);
    }

    public InferredData inferFromURL(String url, String username, String password) throws InferenceException {
        return inferFromURL(url, username, password, null, MAX_ROWS_EXAMINED, 10);
    }


    /**
     * create an inline CSV datasource from comma separated values
     * uses TypeRecognizer to guess the types
     * 
     * @param inlineData             - comma separated values
     * @param
     * @return
     */
    public InferredData inferFromCSV(String inlineData) throws IOException {
        InferredData idata = new InferredData();
        DataSourceInfo ds = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        ds.setProperty(DataSourceInfo.CSV_DATA, inlineData);
        RawData rd = new RawData();
        rd.data = CSVDataSource.getRawData(ds, -1);
        rd.dsType = DataSourceInfo.CSV_TYPE;
        rd.setProperty(DataSourceInfo.CSV_DATA, inlineData);  // keep this as we need it for rd.toDataSource()
        TypeRecognizer tr = new TypeRecognizer(rd);
        tr.guessColumnTypes(); // TypeRecognizer modifies the rd.metadata
        idata.setRawData(rd);
        return idata;
    }
    
    /**
     * given a datasourceInfo, infer information about it 
     * 
     * the dsi must have an accurate type, and be fully disambiguated with respect to the data contained therein, e.g.
     *   must have HTML tableID if more than one table,  must have a sheet name if more than one sheet in the spreadsheet
     * 
     * the metadata of the dsi is overwritten by what is inferred from the rawdata
     * 
     * @param dsi
     * @param maxrows
     * @return
     * @throws 
     */
    public InferredData inferFromDS(DataSourceInfo dsi, int maxrows) throws Exception{
        if (maxrows < 0) {
            maxrows = MAX_ROWS_EXAMINED;
        }
        String url = dsi.getUrl();
        String dsType = dsi.getTypeName();
        String username = dsi.getUsername();
        String password = dsi.getPassword();
        InputStream is = null;
        try {
            if (!DataSourceInfo.GSS_TYPE.equals(dsType) && URLUtil.isHTTP(url)) {
                HttpURLConnection conn = URLUtil.connectHTTP(url, username, password);
                this.assert200(conn, url);
                is = conn.getInputStream();
            } else {
                is = URLUtil.openURL(url, username, password);
            }
            InferredData idata = fillRawData(dsi,is, maxrows,10);
            return idata;
        } finally {
            close(is);
        }
    }
    
    /**
     * main logic for inferring data from a URL
     * returns an InferredData object that has RawData and a DataSourceInfo 
     * the result object may also have a List<TableSynopsis> or a List<String> representing sheet names if the source URL
     * needs disambiguating
     * 
     * @param url
     * @param username
     * @param password
     * @param knownType              - hint on the knowntype of the content.  If null, we try to infer it
     * @param maxrows                - if < 0, use MAX_ROWS_EXAMINED
     * @param maxSynopsisRows        - maximum rows for synopsis 
     * @return                
     * @throws InferenceException
     * @throws IOException
     */
    private InferredData inferFromURL(String url, String username, String password, String dsType, int maxrows, int maxSynopsisRows) 
    throws InferenceException /*,IOException*/ {
        if (maxrows < 0) {
            maxrows = MAX_ROWS_EXAMINED;
        }
        InputStream is = null;
        if (dsType == null) {
            dsType = this.inferTypeFromURLString(url);
        }
        
        try {
            if (!DataSourceInfo.GSS_TYPE.equals(dsType) && URLUtil.isHTTP(url)) {
                // if the url is HTTP but not a google spreadsheet
                HttpURLConnection conn = URLUtil.connectHTTP(url, username, password);
                assert200(conn, url);
                if (dsType == null) {
                    // if inferTypeFromURLString didn't work, try to infer from content type
                    String ctype = this.inferTypeFromContentType(conn);
                    if (ctype != null) { 
                        dsType = ctype; 
                    }
                }
                is = conn.getInputStream();
            } else {
                is = URLUtil.openURL(url, username, password);
            }
            if (dsType == null) {
                throw new InferenceException("Cannot infer a data source type for " + url);
            }
            DataSourceInfo dsi = new DataSourceInfo(dsType);
            dsi.setUrl(url);
            dsi.setUsername(username);
            dsi.setPassword(password);
            return fillRawData(dsi,is,maxrows,maxSynopsisRows);
        } catch (IOException ioe) {
            
            // sometimes files are listed as XLS which are really CSV's.  Try to parse as CSV
            if (DataSourceInfo.EXCEL_TYPE.equals(dsType)) {
                DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
                dsi.setUrl(url);
                dsi.setUsername(username);
                dsi.setPassword(password);
                try {
                    close(is);
                    is = URLUtil.openURL(url,username,password);
                    return fillRawData(dsi,is,maxrows,maxSynopsisRows);
                } catch (IOException e) {
                    throw new InferenceException("IO exception while trying to infer from url: " + url, ioe);
                }
            }
            throw new InferenceException("IO exception while trying to infer from url: " + url, ioe);
        } finally {
            close(is);
        }
    }

    private void fillXLSRawData(DataSourceInfo dsi, RawData rd, InputStream is, int maxRows, int maxSynopsisRows, InferredData res)
    throws IOException,InferenceException {
        String sheetName = dsi.getProperty(DataSourceInfo.SPREADSHEET_NAME);
        XLS2Data xd = new XLS2Data(is,sheetName,maxRows);
        xd.process();
        List<String> sheetNames = xd.getSheetNames();
        if (sheetName == null) {
            if (sheetNames.size() == 1) {
                sheetName = sheetNames.get(0);
            }
        }
        if (sheetName != null) {
            rd.data = xd.getData(sheetName);
            //rd.metadata = ei.getMetadata();
            //rd.dataStartRow = ei.getDataRow();
            //rd.headerRow = ei.getHeaderRow();
            rd.setProperty(DataSourceInfo.SPREADSHEET_NAME,sheetName);
            // get a title
            String fileTitle = StringUtil.url2name(rd.url);
            String title = StringUtil.collapseWS(StringUtil.legalPath(fileTitle + "-" + sheetName));
            rd.setProposedTitle(title);
        } else {
            // no sheetname supplied and there's more than one sheet
            // create synopses of the worksheets
            List<TableSynopsis> synopses = new ArrayList<TableSynopsis>();
            
            for (int i=0;i<sheetNames.size();i++) {
                String sheet = sheetNames.get(i);
                List<String[]> data = xd.getData(sheet);
                if (data.size() > 0) {
                    int maxCols = 0;
                    for (int j = 0; j < data.size(); j++) {
                        maxCols = Math.max(maxCols, data.get(j).length);
                    }
                    // store this into rd in case we end up with only one viable worksheet, we don't
                    // have to call ExcelInference.getRawStrings() again and repopulate it
                    rd.data = data; 
                    TableSynopsis s = new TableSynopsis();
                    s.setTotalRows(data.size());
                    if (maxSynopsisRows > 0 && data.size() > maxSynopsisRows) {
                        // limit synopsis to a certain number of rows
                        // make new array list, don't pass back sublist as-is, since it
                        // gets serialization errors for me (dave) on some vm's:
                        // http://extjs.com/forum/showthread.php?t=41414
                        data = new ArrayList(data.subList(0,maxSynopsisRows));
                    }
                    s.setTableId(sheet);
                    s.setIndex(i);
                    s.setDataList(data, maxCols);
                    synopses.add(s);
                }
            }
            if (synopses.size() == 1) {
                // no sheetname supplied and there were more than one sheet but only one was a reasonable one
                // so just use that
                // we already have rd.data set so we are good to go
                // if there is only one reasonable table, use it
                rd.setProperty(DataSourceInfo.SPREADSHEET_NAME,synopses.get(0).getTableId());
            } else {
                rd.data = null; // null out our optimistic setting of rd.data
                //res.setSheetNames(sheetNames);
                res.setMissingDSProperty(DataSourceInfo.SPREADSHEET_NAME);
                res.setSynopses(synopses);
            }
        }
    }

    private char inferCSVSeparator(BufferedInputStream is) throws IOException {
    	final int MAX_READ = 2000;
    	is.mark(MAX_READ);
    	char ret = 0;
    	String SEPS = "\t|;,:";
    	try {
    		StringBuilder sb = new StringBuilder();
    		int r, nread = 0;
    		while (nread < MAX_READ && (r = is.read()) > 0) {
    			sb.append((char)r);
    			nread++;
    		}
    		String s = sb.toString();
    		for (int i = 0; i < SEPS.length(); i++) {
    			char c = SEPS.charAt(i);
    			if (s.indexOf(c) != -1) {
    				ret = c;
    				break;
    			}
    		}
    	} finally {
    		is.reset();
    	}
    	return ret;
    }
    private InferredData fillRawData(DataSourceInfo dsi, InputStream is,int maxRows, int maxSynopsisRows)
    throws IOException,InferenceException {
        
//        log.warn("**********  fillRawData dsi = " + dsi);
        InferredData res = new InferredData();
        RawData rd = new RawData();
        rd.url = dsi.getUrl();
        rd.dsType = dsi.getTypeName();
        
        String typename = rd.dsType;
        String username = dsi.getUsername();
        String password = dsi.getPassword();
        String proposedTitle = null;
        // keep username and password, to be preserved for later RawData.toDataSource()
        if (username != null) {
            rd.setProperty(DataSourceInfo.PROP_USERNAME, username);
        }
        if (password != null) {
            rd.setProperty(DataSourceInfo.PROP_PASSWORD, password);
        }
        
        if (DataSourceInfo.CSV_TYPE.equals(typename)) {
            char separator;
            String csvSeparator = dsi.getProperty(DataSourceInfo.CSV_SEPARATOR);
            if (csvSeparator != null) {
                rd.setProperty(DataSourceInfo.CSV_SEPARATOR,csvSeparator);
                separator = csvSeparator.charAt(0);
            } else {
            	BufferedInputStream bis = new BufferedInputStream(is);
            	is = bis;
            	separator = inferCSVSeparator(bis);
            	if (separator == 0) {
            		 separator = CSVReader.DEFAULT_SEPARATOR;            		
            	}
            	rd.setProperty(DataSourceInfo.CSV_SEPARATOR, "" + separator);
            	dsi.setProperty(DataSourceInfo.CSV_SEPARATOR, "" + separator);
            }
            rd.data = CSVDataSource.getRawStrings(is, maxRows,separator);
        } else if (DataSourceInfo.EXCEL_TYPE.equals(typename)) {
            this.fillXLSRawData(dsi, rd, is, maxRows, maxSynopsisRows, res);
        } else if (DataSourceInfo.HTML_TYPE.equals(typename)) {
            Document document = HtmlUtil.getHtmlDocument(is);
            if (false/*isSVN(document)*/) {
                // was identified earlier as HTML, but is actually
                // an SVN directory listing formatted as HTML...
                // change type here
                /* SVN moved to sandbox/incubator
                rd.dsType = DataSourceInfo.SVN_TYPE;
                rd.data = SVNDataSource.getRawStrings(rd.url, username, password, maxRows);
                rd.metadata = SVNDataSource.createSVNMetaData();
                */
            } else {
                //this.inferHtmlTableRaw(document, rd, maxRows);
                List<HtmlTable> tables = HtmlUtil.getTableSynopses(document);
                int numTables = 0; // numTables is the number of tables with more than one row
                proposedTitle = HtmlUtil.getDocumentTitle(document); // grab title text of HTML page 
                int maxRowCount = -1; // maximum number of rows read
                int longestTable = -1; // index of the table with the maxrowcount
                int count = 0;
                for (HtmlTable t : tables) {
                    int rows = t.getTotalRows();
                    if (rows > 1)
                        numTables++;
                    if (rows > 1 && rows > maxRowCount) {
                        maxRowCount = rows;
                        longestTable = count;
                    }
                    count++;
                }
                
                // make TableSynopsis out of all the 'reasonable' potential tables
                // reasonable tables have more than one row, more the one column
                // and do not have all null values
                List<TableSynopsis> synopses = HtmlUtil.tables2synopses(tables);
                
                String tableID = dsi.getProperty(DataSourceInfo.HTML_TABLEID);
                if (tableID != null) {
                    // if the tableID has been set, coming in, use it.
                    rd.setProperty(DataSourceInfo.HTML_TABLEID,tableID);
                    rd.data = HtmlDataSource.getRawStrings(document, rd, maxRows);
                    if (rd.data == null) {
                        throw new InferenceException("Could not get table values from HTML document");
                    }
                } else {
                    if (synopses.size() == 1) {
                        // if there is only one reasonable table, use it
                        rd.setProperty(DataSourceInfo.HTML_TABLEID,synopses.get(0).getTableId());
                        rd.data = HtmlDataSource.getRawStrings(document, rd, maxRows);
                        if (rd.data == null) {
                            throw new InferenceException("Could not get table values from HTML document");
                        }
                    } else if (synopses.size() == 0) {
                        throw new InferenceException("No reasonable tables found in HTML document");
                    } else {
                        // if there are more than one reasonable table, then
                        // we fill the table synopses and let the user decide which one he wants
                        res.setSynopses(synopses);
                        res.setMissingDSProperty(DataSourceInfo.HTML_TABLEID);
                    }
                }
            }
        } else if (DataSourceInfo.GSS_TYPE.equals(typename)) {  
            try {
                rd.data = GSSDataSource.getRawStrings(rd.url, username, password,
                        rd.getProperty(DataSourceInfo.SPREADSHEET_NAME), maxRows);
            } catch (ServiceException svce) {
                throw new IOException(
                        "error from spreadsheet service: '"
                        + svce.getResponseBody() + "' (http code: "
                        + svce.getHttpErrorCodeOverride() + ")"
                        );
            }
            /* SVN moved to sandbox/incubator
        } else if (DataSourceInfo.SVN_TYPE.equals(typename)) {  
            rd.data = SVNDataSource.getRawStrings(rd.url, username, password, maxRows);
            rd.metadata = SVNDataSource.createSVNMetaData();
            */
        } else if (DataSourceInfo.JDBC_TYPE.equals(typename)) {
            throw new RuntimeException("FIXME: JDBC currently NYI");
            //rd = JdbcDataSource.getRawData(dsi,maxRows);
        } else {
            throw new IllegalArgumentException(
                    "unrecognized data source type: '" + typename + "'"
            );
        }
        
        res.setRawData(rd);
        if (rd.data != null) {
            if (rd.metadata == null) {
                TypeRecognizer tr = new TypeRecognizer(rd);
                tr.guessColumnTypes();
            }
//            res.setRawData(rd);
//            dsi.setInputMetadata(rd.metadata);
//            dsi.setDataStartRow(rd.dataStartRow);
//            dsi.setHeaderRow(rd.headerRow);
        } 
//        else {
//            // DataInferenceResult has a null RawData field on return
//        }
//        res.setDataSource(dsi);
        // figure out reasonable name for this thing, if not set already
        if (StringUtil.trim(rd.getProposedTitle()) == null) {
            if (proposedTitle == null) {
                proposedTitle = StringUtil.url2name(rd.url);
            } else {
                proposedTitle = StringUtil.legalPath(proposedTitle);
            }
            proposedTitle = StringUtil.collapseWS(proposedTitle);
            rd.setProposedTitle(proposedTitle);
        }
        return res;
        
    }
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException ignore) {}
        }
    }

    /**
     * attempt to infer the DataSourceInfo type from a URL string by looking at suffixes in the URL
     * 
     * assumes  csv/txt/text -> CSV_TYPE
     * 
     * if it can't figure it out from the url, returns nul
     * 
     * @param urlOrig
     * @return
     */
    /*package - because used by unit tests*/
    String inferTypeFromURLString(String urlOrig) {
        String ret = null;
        String lower = urlOrig.toLowerCase();
        if ((lower.startsWith("http://") || lower.startsWith("https://"))
                && lower.indexOf("spreadsheets.google.com") != -1) {
            ret = DataSourceInfo.GSS_TYPE;
        } else {
            int len = urlOrig.length();
            int ind = urlOrig.lastIndexOf('.');
            if (ind > 0 && ind < len - 1) {
                String ext = urlOrig.substring(ind + 1).toLowerCase();
                if (ext.equals("csv") || ext.equals("txt") || ext.equals("text")) {
                    ret = DataSourceInfo.CSV_TYPE;
                } else if (ext.equals("html") || ext.equals("htm")) {
                    ret = DataSourceInfo.HTML_TYPE;
                } else if (ext.equals("xls")) {
                    ret = DataSourceInfo.EXCEL_TYPE;
                }
            }
        }
        // strip query params off
        return ret;
    }
    
    /**
     * try to infer the DataSourceInfo type from the content type of the Http connection
     * returns null if we can't figure it out
     * 
     * @param conn
     * @return
     */
    private String inferTypeFromContentType(HttpURLConnection conn) {
        String ret = null;
        if (isCSV(conn)) {
            ret = DataSourceInfo.CSV_TYPE;
        } else if (isHTML(conn)) {
            // FIXME: handle SVN here
            ret = DataSourceInfo.HTML_TYPE;
        } else if (isXLS(conn)) {
            ret = DataSourceInfo.EXCEL_TYPE;
        }
        return ret;
    }
    
    private boolean isCSV(HttpURLConnection htc) {
        boolean ret = false;
        String ctype = htc.getContentType();
        if (ctype != null) {
            ctype = ctype.toLowerCase();
            if (ctype.startsWith("text/csv") || ctype.startsWith("text/plain")) {
                ret = true;
            }
        }
        return ret;
    }

    private boolean isXLS(HttpURLConnection htc) {
        boolean ret = false;
        String ctype = htc.getContentType();
        if (ctype != null) {
            ctype = ctype.toLowerCase();
            if (ctype.indexOf("excel") != -1) {
                ret = true;
            }
        }
        return ret;
    }
    
    private boolean isHTML(HttpURLConnection htc) {
        boolean ret = false;
        String ctype = htc.getContentType();
        if (ctype != null && ctype.toLowerCase().startsWith("text/html")) {
            ret = true;
        }
        return ret;
    }    
    

    public boolean isSVN(Document doc) {
        boolean ret = false;
        Node root = doc.getDocumentElement();
        if (root != null) {
            Node body = HtmlUtil.getChild(root, HtmlTag.BODY);
            Node head = HtmlUtil.getChild(root, HtmlTag.HEAD);
            if (body != null && head != null) {
                Node title = HtmlUtil.getChild(head, HtmlTag.TITLE);
                Node h2 = HtmlUtil.getChild(body, HtmlTag.H2);
                Node ul = HtmlUtil.getChild(body, HtmlTag.UL);
                if (title != null && h2 != null && ul != null) {
                    String titleStr = HtmlUtil.getText(title);
                    String h2Str = HtmlUtil.getText(h2);
                    if (titleStr == null) titleStr = "";
                    if (h2Str == null) h2Str = "";
                    titleStr = titleStr.toLowerCase();
                    h2Str = h2Str.toLowerCase();
                    if (titleStr.indexOf("revision") != -1 && h2Str.indexOf("revision") != -1
                        && HtmlUtil.countChildNodes(body, HtmlTag.TABLE) == 0) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

}
