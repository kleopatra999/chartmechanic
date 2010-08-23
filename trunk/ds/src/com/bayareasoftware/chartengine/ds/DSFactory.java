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

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.DataSourceInfo;

/**
 * DSFactory is the factory entry point for dealing with DataSourceInfo's of various types
 * DSFactory will handle the various kinds of DataSourceInfo's appropriately by checking
 * their types and calling the concrete classes
 */
public class DSFactory {
    private static Log log = LogFactory.getLog(DSFactory.class);
    
    public static final String INPUTCACHE = ".inputcache.";
    public static final String OUTPUTCACHE = ".outputcache.";
    // obsolete stats files for input and output - delete if they are found
    public static final String OLD_INPUTSTATS = ".inputstats.";
    public static final String OLD_OUTPUTSTATS = ".outputstats.";
    public static final String INPUTSTATS = ".istats.";
    public static final String OUTPUTSTATS = ".ostats.";
    
    /**
     * create a new DataSource from a DataSourceInfo
     * this is the preferred entry point for creating DataSources (vs
     * invoking the *DataSource constructors directly)
     * 
     * 
     * @param dsi   - must be non-null, else a NPE is thrown
     * @return a DataSource corresponding to the DataSourceInfo, null if there is any problem instantiating the datasource
     *
     */
    public static DataSource createDataSource(DataSourceInfo dsi) throws Exception {
        DataSource ret = null;
        if (dsi == null) {
            throw new NullPointerException("null DataSourceInfo");
        }
            String typename = dsi.getTypeName();
        if (DataSourceInfo.CSV_TYPE.equals(typename)) {
            ret = new CSVDataSource(dsi);
        } else if (DataSourceInfo.EXCEL_TYPE.equals(typename)) {
            ret = new ExcelDataSource(dsi);
            /*
        } else if (DataSourceInfo.SVN_TYPE.equals(typename)) {
            ret = new SVNDataSource(dsi);
            */
        } else if (DataSourceInfo.GSS_TYPE.equals(typename)) {
            ret = new GSSDataSource(dsi);
        } else if (DataSourceInfo.HTML_TYPE.equals(typename)) {
            ret = new HtmlDataSource(dsi);
/*            
        } else if (DataSourceInfo.RSS_TYPE.equals(typename)) {
            ret = new RssDataSource(dsi);
*/            
        } else if (DataSourceInfo.JDBC_TYPE.equals(typename)) {
            ret = new JdbcDataSource(dsi);
        } else {
            log.warn("Cannot create datassource from unknown DataSourceInfo type of :"+ typename);
            new Exception().printStackTrace();
            return null;
        }

        ret.setQuery(dsi.getDataScript());
        return ret;
    }
    
    /**
     * Evaluate the specified DataSource with the given parameters and return
     * the resultant DataStream
     * @param ds               the DataSourcenthat describes the logical data source
     * @param paramValues      name/value pairs of parameters to substitute into the query
     * @return                 a data stream of results
     */

    public static DataStream eval(DataSource ds, Map<String,String> paramValues) 
    throws Exception {
        ds.setParamValues(paramValues);
        DataStream r = ds.executeQuery();
        return r;
    }

    /**
     * interactive cmdline tool to browse data sources
     * @param a
     * @throws Exception
     */
    public static void main(String[] a) throws Exception {
        if (a.length == 0) {
            usage();
        }
        String typename = DataSourceInfo.CSV_TYPE;
        String outputFormat = "csv";
        String url = null;

        // clumsy arg parsing without regards to error checking
        for (int i=0;i<a.length;i++) {
            String arg = a[i];
            if (arg.startsWith("type=")) {
                typename=arg.substring(5);
                if (typename.equals("xls"))
                    typename=DataSourceInfo.EXCEL_TYPE; // xls is an alias for excel
            } else
                if (arg.startsWith("out=")) {
                    outputFormat = arg.substring(4);
                } else {
                    url = arg;
                }
        }
        
            if (url == null || url.trim().equals("")) {
                System.err.println("an URL must be supplied");
                usage();
            }

            boolean isHttp = false;
            if (!url.startsWith("http:") && !url.startsWith("file:") && !url.startsWith("https:")) {
                File f = new File(url);
                url = f.toURI().toString();
            } else {
                isHttp = url.startsWith("http");
            }

            System.out.println("loading url:" + url + " of type:"+typename);
            DataSourceInfo dsi = null;
            if (isHttp) {
                //dsi = DataInference.get().inferFromURL(url).toDataSource();
                dsi = DataInference.get().inferFromURL(url, DataInference.MAX_ROWS_EXAMINED).getDataSource();
            } else {
                dsi = new DataSourceInfo(typename);
                dsi.setUrl(url);
            }
            //dsi.setName("DSFactory.cmdline");
                    
            DataSource ds = DSFactory.createDataSource(dsi);
            if (ds == null) {
                System.err.println("Failed to create data source");
            }
            StreamOutput so = null;
            if (outputFormat.equals("csv")) {
                so = new StreamOutput(ds.getDataStream(),new PrintWriter(System.out));
            } else if (outputFormat.equals("html")) {
                so = new HtmlStreamOutput(ds.getDataStream(),new PrintWriter(System.out));
            } else if (outputFormat.equals("json")) {
                so = new JSONStreamOutput(ds.getDataStream(),new PrintWriter(System.out),JSONStreamOutput.VARIANT_JSON);
            } else if (outputFormat.equals("gvis")) {
                so = new JSONStreamOutput(ds.getDataStream(),new PrintWriter(System.out),JSONStreamOutput.VARIANT_GVIS);
            }
            so.runStream();
            so.close();
    }
    
    private static void usage() {
        System.err.println("usage: DSFactory [type=<csv|excel|gss|html|jdbc|xls>  Default:csv]  [out=<csv|html|json|gvis> default:csv] <file-name | URL>");
        System.err.println("\t example: DSFactory type=excel test/data/brokerage.xls");
        System.err.println("\t          DSFactory type=csv http://ichart.finance.yahoo.com/table.csv?s=IBM&a=00&b=2&c=1962&d=05&e=20&f=2008&g=m&ignore=.csv");
        System.exit(1);
    }
}
