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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.util.DataStreamUtil;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StandardProps;

/**
 * Represent chart data from a database using JDBC.
 */
public class JdbcDataSource extends DataSource {
    private final Log log = LogFactory.getLog(JdbcDataSource.class);

    private String driver;
    private String url;
    private String username;
    private String password;
    private String jndiName;
//    private String tableName;
    
    private JdbcDataStream datastream;
    
    // all the JDBC drivers we've already instantiated
    private static Set<String> instantiatedDrivers = new HashSet<String>();
    
    public JdbcDataSource(DataSourceInfo dsi) throws Exception {
        this(dsi.getProps(),dsi.getDataScript());
    }
    
    public JdbcDataSource(SimpleProps props, String query) throws Exception {
        this.driver = props.get(StandardProps.JDBC_DRIVER);
        this.url = props.get(StandardProps.JDBC_URL);
        this.username = props.get(StandardProps.JDBC_USERNAME);
        this.password = props.get(StandardProps.JDBC_PASSWORD);
        this.jndiName = props.get(StandardProps.JDBC_JNDI_NAME);
//        this.tableName = props.get(DataSourceInfo.TABLE_NAME);
        
        this.setQuery(query);
        if (jndiName == null)
            instantiateDriver(driver);
    }
    
//    @Deprecated
//    public JdbcDataSource(String driver,
//                          String url,
//                          String username,
//                          String password)
//        throws Exception
//    {
//        this.driver = driver;
//        this.url = url;
//        this.username = username;
//        this.password = password;
//        instantiateDriver(driver);
//    }
    
    private static void instantiateDriver(String driver) throws Exception {
        if (!instantiatedDrivers.contains(driver)) {
            try {
                Class.forName(driver).newInstance();
                instantiatedDrivers.add(driver);
            } catch (ClassNotFoundException e) {
                int c = driver.charAt(driver.length() - 1);
                throw new Exception("JDBC driver not found: '" + driver + "' "
                        + " (last char is " + c + ")");
            }
        }
    }
    
    /** create a JDBC connection given standard jdbc properties
     * @param props properties must include the StandardProps.{JDBC_DRIVER,JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD} properties
     * @return a JDBC connection
     * @see StandardProps
     */
    public static Connection createConnection(SimpleProps props) throws Exception {
        String jndiName = props.get(StandardProps.JDBC_JNDI_NAME);
        if (jndiName != null) {
            Context ctx = new InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(jndiName);
            return ds.getConnection();
        }
        String driver = props.get(StandardProps.JDBC_DRIVER);
        String url = props.get(StandardProps.JDBC_URL);
        String username = props.get(StandardProps.JDBC_USERNAME);
        String password = props.get(StandardProps.JDBC_PASSWORD);
        instantiateDriver(driver);
        return DriverManager.getConnection(url,username,password);
    }
    
    
    /**
     * this is a bit of an overloaded use of JdbcDataSource
     * this method allows you to run an arbitrary query against this JdbcDataSource
     * where normally, an executeQuery() is against the query that's already DataSource
     * @param sql
     * @return
     * @throws SQLException
     */
    public DataStream executeQuery(String sql) throws Exception {
        if (sql == null || sql.trim().length() == 0) {
            throw new RuntimeException("can't execute empty query body for query named");
        }
        datastream = new JdbcDataStream(this, sql, paramValues);
        return datastream;
    }
    
    @Override
    public DataStream executeQuery() throws Exception {
        String sql = getQuery();
        if (sql == null || sql.trim().length() == 0) {
            throw new RuntimeException("can't execute JdbcDataSource with empty query body");
        }
        datastream = new JdbcDataStream(this, sql, paramValues);
        return datastream;
    }
    
    public Connection createConnection() throws SQLException,NamingException {
        if (jndiName != null) {
            Context ctx = new InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(jndiName);
            return ds.getConnection();
        }
        return DriverManager.getConnection(url,username,password);
    }
    
    @Override
    public DataStream getDataStream() throws Exception {
        if (datastream == null) {
            String q = getQuery();
            if (q == null)
                q = "SELECT * FROM";
            return executeQuery();
        }
        return datastream;
    }

    @Override
    public void close() {
        if (datastream != null)
            datastream.close();
    }
    
    public static RawData getRawData(DataSourceInfo ji, int maxRows) throws Exception {
        RawData ret = new RawData();
        ret.data = new ArrayList<String[]>(maxRows);
        JdbcDataSource jds = new JdbcDataSource(ji);
        DataStream ds = jds.getDataStream();
        try {
            ret.metadata = ds.getMetadata();
            ret.data = DataStreamUtil.getData(ds, maxRows);
        } finally {
            ds.close();
        }
        return ret;
    }
}
