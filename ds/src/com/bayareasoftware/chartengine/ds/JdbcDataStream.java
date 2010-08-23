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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.util.MetadataUtil;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.ParamMetaData;
import com.bayareasoftware.chartengine.util.DateUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

public class JdbcDataStream extends DataStream {
    private static final Log log = LogFactory.getLog(JdbcDataStream.class);

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;
    private Map<String,String> params;
    private List<ParamMetaData> meta;
    private DateRecognizer drec;
    private String sql;
    
    private JdbcDataSource jds;
    
    JdbcDataStream(JdbcDataSource ds, String rawsql, Map<String,String> params) throws Exception {
        super(true);
        this.jds = ds;
        QueryParser qp = new QueryParser(rawsql);
        this.params = params;
        sql = qp.getResult();
        meta = qp.getParams();
        
        execute();
        /*
        try {
            rs = execute(sql);
            if (rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();
                this.metadata = new Metadata(count);
                for (int i = 1; i <= count; i++) {
                    metadata.setColumnName(i, rsmd.getColumnName(i));
                    int jdbcType = rsmd.getColumnType(i);
                    int mdtype = MetadataUtil.getDataType(jdbcType);
                    if (mdtype == DataType.UNKNOWN) {
                        log.warn("cannot convert jdbc type " + jdbcType);
                    }
                    metadata.setColumnType(i, mdtype);
                }
            }
        } catch (SQLException sqe) {
            close();
            throw sqe;
        }*/
    }

    public String getSql() {
        return sql;
    }
    
    private void execute() throws SQLException,NamingException {
        close(); // close everything before re-executing
        connection = jds.createConnection();
        statement = connection.prepareStatement(sql);
        setParameters(statement);
        if (statement.execute() || true) {
            rs = statement.getResultSet();
        } else {
            close();
            throw new RuntimeException("no result set for query");
        }
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            this.metadata = new Metadata(count);
            for (int i = 1; i <= count; i++) {
                metadata.setColumnName(i, rsmd.getColumnName(i));
                int jdbcType = rsmd.getColumnType(i);
                int mdtype = MetadataUtil.getDataType(jdbcType);
                if (mdtype == DataType.UNKNOWN) {
                    log.warn("cannot convert jdbc type " + jdbcType);
                }
                metadata.setColumnType(i, mdtype);
            }
        }
    }
    
    /**
     * we 'reset' the JDBC datastream by re-running the query
     * assuming that the query is stable and returns the same result during the window of time for
     * datastream processing 
     */
    @Override
    public void reset() {
        try {
            execute();
        } catch (Exception sqe) {
            log.error("Failed to re-run query",sqe);
        }
    }

    
    private ResultSet execute(String sql) throws SQLException {
        statement = connection.prepareStatement(sql);
        setParameters(statement);
        if (statement.execute() || true) {
            return statement.getResultSet();
        } else {
            throw new RuntimeException("no result set for query");
        }
    }

    private Date getDate(ParamMetaData pmd) {
        Date ret = null;
        if (drec == null) {
            drec = new DateRecognizer();
        }
        drec.reset();
        String val = getValue(pmd);
        if (val != null) {
            drec.parse(val);
            if (!drec.failed()) {
                try {
                    ret = drec.getSimpleDateFormat().parse(val);
                } catch (java.text.ParseException pe) {
                }
            }
        }
        if (ret == null) {
            val = pmd.getDefaultValue();
            if (val != null) {
                drec.reset();
                drec.parse(val);
                if (!drec.failed()) {
                    try {
                        ret = drec.getSimpleDateFormat().parse(val);
                    } catch (java.text.ParseException pe) {
                    }
                }
            }
        }
        return ret;
    }
    private String getValue(ParamMetaData pmd) {
        // FIXME: lookup case-insensitive, to be more lenient?
        String val = null;
        if (params != null) {
            val = params.get(pmd.getName());
            if (val != null && (val = val.trim()).length() == 0) {
                val = null;
            }
        }
        return val;
    }
    private Integer getInt(ParamMetaData pmd) {
        Integer ret = null;
        String val = getValue(pmd);
        if (val != null) {
            try {
                ret = Integer.parseInt(val);
            } catch (NumberFormatException nfe) { }
        }
        if (ret == null) {
            ret = pmd.getDefaultInt();
        }
        return ret;
    }
    private Double getDouble(ParamMetaData pmd) {
        Double ret = null;
        String val = getValue(pmd);
        if (val != null) {
            try {
                ret = Double.parseDouble(val);
            } catch (NumberFormatException nfe) { }
        }
        if (ret == null) {
            ret = pmd.getDefaultDouble();
        }
        return ret;
    }
    private Boolean getBoolean(ParamMetaData pmd) {
        Boolean ret = null;
        String val = getValue(pmd);
        if (val != null) {
            try {
                ret = Boolean.valueOf(val);
            } catch (NumberFormatException nfe) { }
        }
        if (ret == null) {
            ret = pmd.getDefaultBoolean();
        }
        return ret;
    }
    private String getString(ParamMetaData pmd) {
        String val = getValue(pmd);
        if (val == null) {
            val = pmd.getDefaultValue();
        }
        return val;
    }
    private void setParameters(PreparedStatement ps) throws SQLException {
        int len = meta.size();
        for (int i = 0; i < len; i++) {
            int index = i + 1;
            ParamMetaData pmd = meta.get(i);
            switch(pmd.getDataTypeAsInt()) {
            case DataType.BOOLEAN:
                Boolean b = getBoolean(pmd);
                if (b != null) {
                    ps.setBoolean(index, b);
                } else {
                    ps.setNull(index, Types.BOOLEAN);
                }
                break;
            case DataType.DOUBLE:
                Double d = getDouble(pmd);
                if (d != null) {
                    ps.setDouble(index, d);
                } else {
                    ps.setNull(index, Types.DOUBLE);
                }
                break;
            case DataType.INTEGER:
                Integer I = getInt(pmd);
                if (I != null) {
                    ps.setInt(index, I);
                } else {
                    ps.setNull(index, Types.INTEGER);
                }
                break;
            case DataType.DATE:
                Date D = getDate(pmd);
                if (D != null) {
                    ps.setTimestamp(index, new Timestamp(D.getTime()));
                } else {
                    ps.setNull(index, Types.TIMESTAMP);
                }
                break;
            case DataType.STRING:
                String s = getValue(pmd);
                if (s != null) {
                    ps.setString(index, s);
                } else {
                    ps.setNull(index, Types.VARCHAR);
                }
                break;
                default:
                    throw new RuntimeException(
                            "Unknown data type: " + pmd.getDataTypeAsInt()
                            );
            }
        }
    }

    public ResultSet getResultSet() {
        return rs;
    }
    
    public void close() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.warn("Unable to close JDBC result set", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.warn("Unable to close JDBC statement", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.warn("Unable to close JDBC connection", e);
            }
        }
    }
    
    @Override
    public Boolean getBoolean(int index) throws Exception {
        Boolean b = rs.getBoolean(index);
        return rs.wasNull() ? null: b;
    }

    @Override
    public Date getDate(int index) throws Exception {
        Date d = rs.getDate(index);
        return rs.wasNull() ? null: d;
    }

    @Override
    public Double getDouble(int index) throws Exception {
        Double d = rs.getDouble(index);
        return rs.wasNull() ? null: d;
    }

    @Override
    public Integer getInt(int index) throws Exception {
        Integer i = rs.getInt(index);
        return rs.wasNull() ? null: i;
    }

    @Override
    public String getString(int index) throws Exception {
        return rs.getString(index);
    }

    /*@Override
    public boolean isNull(int index) throws Exception {
        Object o = rs.getObject(index);
        return rs.wasNull();
    }*/

    @Override
    public boolean nextInternal() throws Exception {
        return rs.next();
    }

}
