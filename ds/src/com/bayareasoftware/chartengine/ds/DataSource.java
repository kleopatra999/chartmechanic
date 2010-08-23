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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bayareasoftware.chartengine.js.JSEngine;
import com.bayareasoftware.chartengine.model.ParamMetaData;

/**
 * a DataSource can represents data from any variety of sources, e.g. JDBC, Html, Excel, etc.
 * 
 * DataSource differs from chartengine.model.DataSourceInfo in that DataSourceInfo
 * is the logical specification for a data source, and DataSource is the actual
 * backend implementation of a data source.  DataSource never goes to the GWT client 
 */
public abstract class DataSource {
    /** 
     * List of ParamMetaData of query parameters
     * may be null
     * may also be ignored by specific datasources
     */
    List<ParamMetaData> paramMeta;
    
    /**
     * map of name->values of query values
     * may be null
     * may also be ignored by specific datasources
     */
    Map<String,String> paramValues;
    
    private String query;
    
    public boolean supportsQuery() { return false; }

    /**
     * return the data stream for this DataSource
     * the stream is newly created or reset so that it can be read
     * at the beginning, i.e. it should not be an already-read stream where the cursor
     * is somewhere mid-stream or at the end.
     * 
     * @return
     * @throws Exception
     */
    public abstract DataStream getDataStream() throws Exception;

    /**
     * Evaluate a data stream through the data script engine iff there is
     * a valid script/query.  Else simply return the original data stream.
     * @param baseStream - the original (unevaluated, or "raw") stream
     * @return the evaluated stream
     */
    protected DataStream evalStreamScript(DataStream baseStream) throws Exception {
        DataStream ret = baseStream;
        if (JSEngine.isScript(getQuery())) {
            ret = JSEngine.evalStream(baseStream, getQuery());
        }
        return ret;
    }
    public void setParamMeta(List<ParamMetaData> paramMeta) {
        this.paramMeta = paramMeta;
    }
    
    public void setParamValues(Map<String,String> paramValues) {
        this.paramValues = paramValues;
    }
    
    public DataStream executeQuery() throws Exception {
        return getDataStream();
    }

    /**
     * Provide each stream an opportunity to figure out a starting
     * point for incremental updates for the sake of efficiency.
     * It can look into data that has already been persisted, and
     * return only newer data, if possible
     * @param conn
     * @param tableName
     * @throws SQLException
     */
    public void determineDataStart(Connection conn, String tableName) throws SQLException {
        
    }
    public void close() {}

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    };
}
