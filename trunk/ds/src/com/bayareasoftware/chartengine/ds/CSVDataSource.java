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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.util.URLUtil;

public class CSVDataSource extends DataSource {
    private static Log log = LogFactory.getLog(CSVDataSource.class);
    
    private StringDataStream dataStream;
    
    public CSVDataSource(DataSourceInfo ci) throws IOException {
        this(ci,-1);
    }

    public CSVDataSource(DataSourceInfo ci, int maxRows) throws IOException {
        List<String[]> data = getRawData(ci,maxRows);
        if (ci.isRowInverted()) {
            data = RawData.invert(data);
        }
        
//        log.warn("============= getRawData returned a list of size: " + data.size() + " for ci.getUrl = " + ci.getUrl());
        
        dataStream = new StringDataStream(data, ci.getInputMetadata(), ci.getDataStartRow(), ci.getDataEndRow());
    }
    /**
     * load a CSVDataSource from an URL and metdata
     * returns null if failed to do so
     * 
     * @param url
     * @param md
     * @return
     */
    public static CSVDataSource loadFromUrl(String url, Metadata md) {
        return loadFromUrl(url,md,-1);
    }

    /**
     * load a CSVDataSource from an URL and metdata
     * returns null if failed to do so
     * 
     * @param url
     * @param md
     * @param maxRows                 - loads at most maxRows if maxRows > 0
     * @return
     */
    public static CSVDataSource loadFromUrl(String url, Metadata md, int maxRows) {
        DataSourceInfo cache_dsi = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        cache_dsi.setUrl(url);
        cache_dsi.setInputMetadata(md);
        try {
            return new CSVDataSource(cache_dsi,maxRows);
        } catch (Exception e) {
            log.warn("failed to load a CSVData from the url: " + url + " because of " + e,e);
            return null;
        }
        
    }
    /**
     * get the raw data as strings from the data source
     * @param ci
     * @param maxRows   - maximum number of rows, if <=0, get as many as possible
     * @return
     * @throws IOException
     */
    public static List<String[]> getRawData(DataSourceInfo ci, int maxRows) throws IOException {

        String url = ci.getUrl();
        char separator = ',';
        String csvSep = ci.getProperty(DataSourceInfo.CSV_SEPARATOR);
        if (csvSep != null) {
            separator = csvSep.charAt(0);
        }
        
//        System.err.println("****** separator = >>>"+separator+"<<<");

        Reader r = null;
        if (url != null) {
            return getRawStrings(URLUtil.openURL(url, ci.getUsername(), ci.getPassword()), maxRows, separator);
        } else {
            String s = ci.getProperty(DataSourceInfo.CSV_DATA);
            if (s == null || s.length() == 0) {
                // there is inline data but it's empty so return an empty List
                return new ArrayList<String[]>();
            }
            String data = unescapeNewline(s);
            r = new StringReader(data.trim());
        }
        return getRawStrings(r,maxRows,separator);
    }
    
    public static List<String[]> getRawStrings(InputStream is, int maxRows, char separator) throws IOException {
        return getRawStrings(new InputStreamReader(is), maxRows, separator);
    }
    
    /**
     * read raw strings and parse it as CSV
     * @param r
     * @param maxRows
     * @param separator    - what character to use a delimiter
     * @return
     * @throws IOException
     */
    private static List<String[]> getRawStrings(Reader r, int maxRows, char separator) throws IOException {
        List<String[]> result = new ArrayList<String[]>();
        char quotechar = CSVReader.DEFAULT_QUOTE_CHARACTER;
        int skipLines = CSVReader.DEFAULT_SKIP_LINES;
        CSVReader cr = new CSVReader(r,separator,quotechar,skipLines);
        String[] row = null;
        int i=0;
        
        if (maxRows < 1)
            maxRows = Integer.MAX_VALUE;
        try {
            while ( (row = cr.readNext()) != null && i < maxRows) {
//                {
//                    StringBuffer line = new StringBuffer(); 
//                    for (int j=0;j<row.length;j++) {
//                        line.append(row[j]).append('\t');
//                    }
//                    log.warn("read: " + line);
//                }
                result.add(row);
                i++;
            }
        } finally {
            cr.close();
            r.close();
        }
        
        return result;
    }

    private static String newline = "\n";
    private static String escaped_newline = "\\"+"\\"+"\\" + "n"; // this is the 4 characters \\\n

    public static String escapeNewline(String s) {
        return s.replace(newline,escaped_newline);
    }
    
    public static String unescapeNewline(String s) {
        return s.replace(escaped_newline,newline);
    }
    public DataStream getDataStream() throws Exception {
        dataStream.reset();
        return evalStreamScript(dataStream);
    }
}
