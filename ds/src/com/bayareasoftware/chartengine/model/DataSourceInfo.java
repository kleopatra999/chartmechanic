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
package com.bayareasoftware.chartengine.model;

import java.util.Date;

/**
 * A DataSourceInfo encapsulates information about a particular data source (e.g. JDBC, HTML, CSV, FRB, etc.)
 *  
 * Different kinds of data sources are represented in a 'meta' fashion as DataSourceInfo using the
 * field 'typename' to distinguish the types and different properties in the 'props' field.
 * 
 * Note that since we are not using strongly-typed subtypes, the handling of various data sources are more 
 * enforced by convention than by compile-time checking
 *
 * each DataSourceInfo has a SOURCE - described by an URL. 
 * 
 * a DataSourceInfo has up to three kinds of data in it:
 *     raw data:   the unadulterated raw data from the source.   raw data consists of rows of columns of Strings
 *     
 *     input data:   the raw data as constrained by the inputMetadata field.   Data is constrained to our set of types
 *                   the input data is possible cached.  If cached, we use a canonical date format.
 *                   
 *     output data:  the result of 'eval'-ing the input data through its data script.  Output data may have different
 *                   number of columns from the input data.
 *                   
 *                   the outputMetadata describes the output data.   In the absences of a datascript, the
 *                   output data will be the same as the input data.
 *     
 *
 */
public class DataSourceInfo extends BaseInfo {

    // these USERNAME/PW/URL are for the original source (e.g. HTML table)
    public static String PROP_USERNAME = "username";
    public static String PROP_PASSWORD = "password";
    
    public static String PROP_SOURCE_UID="source_uid";
    
    public static String PROP_OUTPUTMETADATA = "omd";
    private Metadata outputMetadata;
    
    public static String PROP_INPUTMETADATA = "md";
    private Metadata inputMetadata;
    
    public static String PROP_DATASCRIPT = "datascript";
    private String datascript;
    
    public static String PROP_TYPENAME = "typename";

    public static String PROP_TIMEPERIOD = "timeperiod";
    
    // time-to-live for this DataSource, in seconds
    // this controls the caching for the DataSource
    public static String PROP_TTL = "validFor";
    
    // type names of different kinds of DataSourceInfo's
    public static String JDBC_TYPE = "jdbc";
    public static String CSV_TYPE = "csv";
    public static String EXCEL_TYPE = "excel";
    public static String GSS_TYPE = "gss"; // google spreadsheet
    public static String HTML_TYPE = "html";
    /*public static String RSS_TYPE = "rss"; <-- moved to sandbox/incubator */
    /*public static String SVN_TYPE = "svn"; <-- moved to sandbox/incubator */
    public static String FRB_TYPE = "frb"; // federal reserve data 
    public static String NYFED_TYPE = "nyfed"; // ny fed
    
    // property names 
    public static String CSV_DATA = "csv_data";
    public static String SPREADSHEET_NAME = "spreadsheetname"; // used for excel and google
    public static String HTML_TABLEID="html_tableid";
    
    public static String DATA_START_ROW = "data_start";
    public static String DATA_END_ROW = "data_end";
    public static String HEADER_ROW = "header_row";
    
    public static String TABLE_NAME = "table_name";
    public static String INVERT_ROW_COLUMN = "inverted_row_column";
    

    // optional property to indicate what delimiter to use for CSV values
    // must always be a single character string
    public static String CSV_SEPARATOR="csv_separator";
    
    /**
     * a datasourceinfo has optional StreamStatsInfo that is set upon loading from VFS. 
     * The StreamStatsInfo may be null and the caller needs to handle that accordingly
     * the streamstats for a DataSourceInfo is not serialized/deserialized with the DataSourceInfo but is
     * persisted separately
     */
    private StreamStatsInfo streamStatsInfo;

    /**
     * this is a timestamp of when the last time the cache of this data was filled
     * might be null TTL = 0 or if no cache exists
     * this is not stored in the DataSourceInfo file on VFS but is set upon loading
     */
    private Date lastUpdated;
    
    public DataSourceInfo() {}     

    public DataSourceInfo(String typename) {
        setTypeName(typename);
    }
    
    public String getUsername() {
        return getProperty(PROP_USERNAME);
    }

    public void setUsername(String username) {
        setProperty(PROP_USERNAME,username);
    }

    public String getPassword() {
        return getProperty(PROP_PASSWORD);
    }

    public void setPassword(String password) {
        setProperty(PROP_PASSWORD,password);
    }
    
    public StreamStatsInfo getStreamStatsInfo() {
        return streamStatsInfo;
    }

    public void setStreamStatsInfo(StreamStatsInfo streamStatsInfo) {
        this.streamStatsInfo = streamStatsInfo;
    }

    @Override
    public void setId(String id) {
        /*
        try {
            if (getId() == null) throw new RuntimeException("setting id to '" + id + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        super.setId(id);
    }
    public String getUrl() {
        return getProperty(URL);
    }

    public void setUrl(String url) {
        setProperty(URL,url);
    }
    
    public String getAcl() {
        return getProperty(AclConstants.PROP_ACL);
    }
    public void setAcl(String s) {
        setProperty(AclConstants.PROP_ACL,s);
    }
    
    public int getDataStartRow() {
        return getPropertyAsInt(DATA_START_ROW,-1);
    }

    public void setDataStartRow(int dataStartRow) {
        setProperty(DATA_START_ROW,String.valueOf(dataStartRow));
    }

    
    public int getTTL() {
        // if there's no TTL set, assume 1 hour, a reasonable setting
        // that helps with legacy data sources that have no TTL
        return getPropertyAsInt(PROP_TTL,3600);
    }
    
    //
    // Time-to-live  in seconds
    // this controls the caching of the datasource
    // if the DataSource cache is older than 'ttl' seconds, then we flush the cache and go back to the original url
    // otherwise, we are free to use the cache
    public void setTTL(int ttl) {
        setProperty(PROP_TTL,String.valueOf(ttl));
    }
    
    /**
     * The periodicity of the first DATE column in the dataset
     */
    public int getTimePeriod() {
        return getPropertyAsInt(PROP_TIMEPERIOD, TimeUtil.TIME_UNKNOWN);
    }
    
    public void setTimePeriod(int tp) {
        setProperty(PROP_TIMEPERIOD, String.valueOf(tp));
    }
    /**
     * return the index of the column that matches the specified name
     * @param s    name of the index
     * @return     -1 if not found
     */
    public int getColumnIndexFromName(String s) {
        Metadata md = this.getOutputMetadata();
        if (md != null && s != null) {
            for (int i=1;i<=md.getColumnCount();i++) {
                if (s.equals(md.getColumnName(i))) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    
    public int getDataEndRow() {
        return getPropertyAsInt(DATA_END_ROW,-1);
    }
    
    public void setDataEndRow(int dataEndRow) {
        setProperty(DATA_END_ROW,String.valueOf(dataEndRow));
    }

    public int getHeaderRow() {
        return getPropertyAsInt(HEADER_ROW,-1);
    }

    public void setHeaderRow(int headerRow) {
        setProperty(HEADER_ROW,String.valueOf(headerRow));
    }

    public String getDataScript() {
        return this.datascript;
    }

    public void setDataScript(String datascript) {
        String old = this.datascript;
        this.datascript = datascript;
        fireChange(PROP_DATASCRIPT,old,this.datascript);
    }
    
    public Metadata getOutputMetadata() {
        if (outputMetadata == null)
            return inputMetadata;
        return outputMetadata;
    }

    public void setOutputMetadata(Metadata md) {
        Metadata old = this.outputMetadata;
        this.outputMetadata = md;
        fireChange(PROP_OUTPUTMETADATA,old,md);
    }
    
    public Metadata getInputMetadata() {
        return inputMetadata;
    }

    public void setInputMetadata(Metadata md) {
        Metadata old = this.inputMetadata;
        this.inputMetadata = md;
        fireChange(PROP_INPUTMETADATA,old,md);
    }

    /**
     * optional name of the DB table backing this DataSourceInfo
     */
    public String getTableName() {
        return getProperty(TABLE_NAME);
    }

    public void setTableName(String name) {
        setProperty(TABLE_NAME,name);
    }

    public int hashCode() {
        int ret = super.hashCode();
        if (inputMetadata != null) {
            ret ^= inputMetadata.hashCode();
        }
        if (outputMetadata != null) {
            ret ^= outputMetadata.hashCode();
        }

        return ret;
    }
    
    public boolean equals(Object o) {
        if (o == null) { 
            return false;
        }
        if (!(o instanceof DataSourceInfo)) {
            return false;
        }
        DataSourceInfo other = (DataSourceInfo) o;

        boolean ret = super.equals(o) &&
        (inputMetadata != null ? inputMetadata.equals(other.inputMetadata) : other.inputMetadata == null) &&
        (outputMetadata != null ? outputMetadata.equals(other.outputMetadata) : other.outputMetadata == null);

        return ret;
        
    }
    
    /**
    the source UID is a unique identifier in the originating data that corresponds to this DataSource
    it is used by the Updater to find the DataSource that matches the originating data (e.g. the series name in the FRB data)
    **/
    public String getSourceUID() {
        return getProperty(PROP_SOURCE_UID);
    }

    public void setSourceUID(String sourceUID) {
        setProperty(PROP_SOURCE_UID,sourceUID);
    }

    public String getTypeName() {
        return getProperty(PROP_TYPENAME);
    }

    public void setTypeName(String typeName) {
        setProperty(PROP_TYPENAME,typeName);
    }
    
    // this should eventually be moved somewhere else.  keep it here for now for ease of access
    // by the GWT code (which doesn't link with many of the other Util classes
    public static String nameFromUrl(String s) {
        int lastSlash = s.lastIndexOf('/');
        if (lastSlash == s.length() - 1) {
            // if the last slash is at the very end, ignore that and pick a better suffix
            s = s.substring(0,s.length()-1);
            lastSlash = s.lastIndexOf('/');
        }
        if (lastSlash == -1) {
            // should never have URLs with no slashes in them
            return s;
        }
        StringBuilder res = new StringBuilder();
        for (int i=lastSlash+1;i<s.length();i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c))
                res.append(c);
        }
        return res.toString();
    }
    
    public String toString() {
        return serializeToProps(null,null).toString();
    }
    
    /**
     * When true, rows and columns are swapped from the original data source
     */
    public boolean isRowInverted() {
        return "true".equalsIgnoreCase(this.getProperty(INVERT_ROW_COLUMN));
    }
    public void setRowInverted(boolean b) {
        if (b) {
            this.setProperty(INVERT_ROW_COLUMN, "true");
        } else {
            this.props.remove(INVERT_ROW_COLUMN);
        }
    }
    
    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    @Override
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        SimpleProps sp = super.serializeToProps(p, prefix);
        if (inputMetadata != null) {
            // serialize the metadata element and append it to our existing simpleprops
            // but ensure that it has the addition prefix PROP_INPUTMETADATA
            inputMetadata.serializeToProps(sp, SimpleProps.prefixIt(prefix,PROP_INPUTMETADATA));
        }
        if (outputMetadata != null) {
            // serialize the metadata element and append it to our existing simpleprops
            // but ensure that it has the addition prefix PROP_OUTPUTMETADATA
            outputMetadata.serializeToProps(sp, SimpleProps.prefixIt(prefix,PROP_OUTPUTMETADATA));
        }

        if (datascript != null) {
            sp.set(prefix,PROP_DATASCRIPT,datascript);
        }
        return sp;
    }

    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
//        System.err.println("deserializing ds from p= " + p + " with prefix = " + prefix);
        boolean ret = super.deserializeFromProps(p, prefix);
        try {
            Metadata md;
            if (this.inputMetadata == null) { 
                md = new Metadata();
            } else
                md = this.inputMetadata;

            String mdprefix = PROP_INPUTMETADATA;
            if (this.props.containsKey(SimpleProps.prefixIt(mdprefix,Metadata.PROP_COLUMNS))) {
                ret = md.deserializeFromProps(this.props, mdprefix);
                if (ret == true) {
                    // if the deserialization returns false, then do not alter this.metadata
                    this.inputMetadata = md;
                } 
                this.props.removeWithPrefix(mdprefix);
            }
            
            if (this.outputMetadata == null) { 
                md = new Metadata();
            } else
                md = this.outputMetadata;

            mdprefix = PROP_OUTPUTMETADATA;
            // datasource's may not have serialized their outputmetadata so skip it if it doesn't exist
            if (this.props.containsKey(SimpleProps.prefixIt(mdprefix,Metadata.PROP_COLUMNS))) {
                ret = md.deserializeFromProps(this.props, mdprefix);
                if (ret == true) {
                    this.outputMetadata = md;
                } else {
                    // if no output Metadeta is specified, then it is null
                    // and the accessor funciton getOutputMetadata() will return the inputMetdata()
                    this.outputMetadata = null;
                }

                this.props.removeWithPrefix(mdprefix);
            }
            String k = PROP_DATASCRIPT;
            if (this.props.containsKey(k)) {
                this.datascript = this.props.get(k);
                this.props.remove(k);
            }
            
            { // the old name for datascript was query.  keep this here for backwards compatibility
                k = "query";
                if (this.props.containsKey(k)) {
                    this.datascript = this.props.get(k);
                    this.props.remove(k);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
            // FIXME: how to handle parsing exceptions while deserializing?
        }
        return ret;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    /**
     * can this datasource by materialized to a cache from its URL or in-place data?
     * @return
     */
    public boolean isMaterializable() {
        String typeName = this.getTypeName();
    
        return !(FRB_TYPE.equals(typeName) || NYFED_TYPE.equals(typeName)); 
    }
}
