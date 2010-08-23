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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * a class for inferring useful information about a database table 
 *
 */
public class DBTableInspector {
    private Log log = LogFactory.getLog(getClass());

    // a useful holder for the column meta data we get back from JDBC
    // note that we always UPPERCASE the column names
    public static class TableDesc {
        public List<String> colNames = new ArrayList<String>();
        public List<Integer> colTypes = new ArrayList<Integer>();
        
        public TableDesc(String tableName, Connection conn) throws SQLException {
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs;
            // look for upper case on H2, lower on postgres
            if (conn.getClass().getName().indexOf("postgres") == -1) {
                tableName = tableName.toUpperCase();
            } else
                tableName = tableName.toLowerCase();
                
            rs = dmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                colNames.add(rs.getString("COLUMN_NAME"));
                colTypes.add(rs.getInt("DATA_TYPE"));
            }
            rs.close();
        }
        
        public int getNumColumns() {
            return colNames.size();
        }
        
        public String getColName(int ind) {
            return colNames.get(ind);
        }

        public int getIndex(String columnName) {
            for (int i=0;i<colNames.size();i++) {
                if (colNames.get(i).equalsIgnoreCase(columnName))
                        return i;
            }
            return -1;
        }

        public int getColType(int ind) {
            if (colTypes.get(ind) != null)
                return colTypes.get(ind).intValue();
            else
                return Types.OTHER;
        }
    }
    
    private String table;
    private Connection conn;
    private TableDesc columnMetaData;
    
    public DBTableInspector(String table, Connection conn) {
        this.table = table;
        this.conn = conn;
        try{
            columnMetaData = new TableDesc(table,conn);
        } catch (SQLException sqe) {
            log.error("could not read column meta data from table: " + 
                    table + "and connection:" + conn + "because of :" + sqe);
        }
    }
    
    public TableDesc getColumnMetaData() {
        return columnMetaData;
    }
    
    public int getRowCount() {
        int ret = -1;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table);
                if (rs.next()) {
                    ret = rs.getInt(1);
                }
            } finally {
                rs.close();
                stmt.close();
            }
        } catch (SQLException sqe) {
            log.error("could not get row count from" +
                    "table: " + table + "and connection:" + conn + 
                    "because of :" + sqe);
        }
        return ret;   
    }
    
    /** 
     * @return the first index of a time/date column, if any in this table
     * index is 0-based.  if no time/date column, then return -1.
     */
    public int getTimeColumn() {
        int ret = -1;
        for (int i=0;i<getNumColumns();i++) {
            if (isTimeColumn(i))
                return i;
        }
        return ret;
    }
    
    /**
     * get the cardinalities of each column
     */
    public int[] getCardinalities() {
        int numColumns = columnMetaData.getNumColumns();
        int[] res = new int[numColumns];
        for (int i=0;i<numColumns;i++) {
            res[i] = getCardinality(i);
        }
        return res;
    }
    
    public int getCardinality(String columnName) {
        return getCardinality(getIndex(columnName));
    }
    
    /**
     * get the cardinality of values in the specified column
     * @param columnIndex - 0-based
     * @return -1 if bad columnIndex
     */
    public int getCardinality(int columnIndex) {
        int ret = -1;
        if (columnIndex >= 0 && columnMetaData != null) {
            String colName = columnMetaData.getColName(columnIndex);
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = null;
                try {
                    rs = stmt.executeQuery("SELECT count (distinct " + colName + " ) from " + table);
                    if (rs.next())
                        ret = rs.getInt(1);
                } finally {
                    rs.close();
                    stmt.close();
                } 
            } catch (SQLException sqe) {
                log.error("could not get cardinality from column: " + colName + " in " +
                        "table: " + table + "and connection:" + conn + 
                        "because of :" + sqe);
            }
        }
        return ret;
    }
    
    /**
     * for the given column name, return its index
     * returns -1 if no such name
     * @param columnName
     * @return 0-basedindex
     */
    public int getIndex(String columnName) {
        return columnMetaData.getIndex(columnName);
    }
    
    public boolean isUniqueColumn(String columnName) {
        return isUniqueColumn(getIndex(columnName));
    }
    
    public int getNumColumns() {
        return columnMetaData.getNumColumns();
    }
    
    public boolean isTimeColumn(String columnName) {
        return isTimeColumn(getIndex(columnName));
    }
    
    public boolean isTimeColumn(int index) {
        if (index < 0 || index >= getNumColumns())
            return false;
        int t = columnMetaData.getColType(index);
        return (t == Types.DATE || t ==  Types.TIME || t == Types.TIMESTAMP);
    }
    
    public boolean isUniqueColumn(int index) {
        if (index < 0 || index >= getNumColumns())
            return false;
      int numRows = getRowCount();
      return (getCardinality(index) == numRows);
    }
}
