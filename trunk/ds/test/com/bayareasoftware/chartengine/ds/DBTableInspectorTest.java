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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.DBTableInspector;
import com.bayareasoftware.chartengine.ds.JdbcDataSource;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StandardProps;

public class DBTableInspectorTest {
    
    private static Connection conn;
    
    @BeforeClass
    public static void init() {
        try {
            SimpleProps props = new SimpleProps();
            props.put(StandardProps.JDBC_DRIVER,"org.postgresql.Driver");
            props.put(StandardProps.JDBC_URL,"jdbc:postgresql://localhost/cm_data_test");
            props.put(StandardProps.JDBC_USERNAME,"bt");
            props.put(StandardProps.JDBC_PASSWORD,"");
            conn = JdbcDataSource.createConnection(props);
            createSampleTable(conn);
        } catch (Exception e) {
            Assert.fail("failed to initialize test tables due to " + e);
        }
        
    }
    private static String T1 = "T1";
    private static int T1_ROWS = 10;
    
    private static String T2 = "T2";
    private static int T2_ROWS = 20;
    
    private static String T3 = "T3";
    private static int T3_ROWS = 0;
        
    private static final String[] SCHEMA =  {
            "CREATE TABLE " + T1 +  " (c_int INTEGER, c_timestamp TIMESTAMP, c_varchar VARCHAR(100), c_double DOUBLE PRECISION);",
            "CREATE TABLE " + T2 + " (c_x DOUBLE PRECISION, c_y DOUBLE PRECISION)",
            "CREATE TABLE " + T3 + " (f1 INTEGER)"
    };

    private static void createSampleTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            stmt.execute("DROP TABLE T1");
            stmt.execute("DROP TABLE T2");
            stmt.execute("DROP TABLE T3");
        } catch (SQLException sqe) {
            // if drop table failed, that's ok
        }
        
        for (String s : SCHEMA) {
            stmt.execute(s);
        }
        stmt.close();
        
        PreparedStatement pstmt = conn.prepareStatement(
             "INSERT INTO " + T1 + " (c_int, c_timestamp, c_varchar, c_double) VALUES (?,?,?,?)");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i<T1_ROWS;i++) {
            pstmt.setInt(1,i);
            pstmt.setTimestamp(2, new java.sql.Timestamp(startTime + 1000 * i));
            pstmt.setString(3, (i % 2 == 0) ? "even" : "odd");
            pstmt.setDouble(4, 1000.0 * i);
            pstmt.execute();
        }
        pstmt.close();
        
        pstmt = conn.prepareStatement(
            "INSERT INTO " + T2 + " (c_x, c_y) VALUES (?,?)");
        for (int i = 0; i<T2_ROWS;i++) {
            pstmt.setDouble(1,1000.0 * i);
            pstmt.setDouble(2,1000.0 - i);
            pstmt.execute();
        }
        pstmt.close();
    }
    
    @Test
    public void testNumRows() {
        DBTableInspector di = new DBTableInspector(T1,conn);
        Assert.assertEquals(T1_ROWS, di.getRowCount());
        di = new DBTableInspector(T2,conn);
        Assert.assertEquals(T2_ROWS, di.getRowCount());
        di = new DBTableInspector(T3,conn);
        Assert.assertEquals(T3_ROWS, di.getRowCount());
    }
    
    @Test
    public void testGetTimeColumn() {
        DBTableInspector di = new DBTableInspector(T1,conn);
        Assert.assertEquals(1, di.getTimeColumn());
        di = new DBTableInspector(T2,conn);
        Assert.assertEquals(-1, di.getTimeColumn());
        di = new DBTableInspector(T3,conn);
        Assert.assertEquals(-1, di.getTimeColumn());
    }

    @Test
    public void testGetCardinality() {
        DBTableInspector di = new DBTableInspector(T1,conn);
        Assert.assertEquals(T1_ROWS, di.getCardinality(0));
        Assert.assertEquals(T1_ROWS, di.getCardinality(1));
        Assert.assertEquals(2, di.getCardinality(2));
        Assert.assertEquals(T1_ROWS, di.getCardinality(3));
        
        di = new DBTableInspector(T2,conn);
        Assert.assertEquals(T2_ROWS, di.getCardinality("c_x"));
        Assert.assertEquals(T2_ROWS, di.getCardinality("c_y"));
        Assert.assertEquals(-1, di.getCardinality("NOT_THERE"));

        int[] c = di.getCardinalities();
        Assert.assertTrue(c.length == 2);
        Assert.assertTrue(c[0] == T2_ROWS);
        Assert.assertTrue(c[1] == T2_ROWS);
    }
    
    @Test
    public void testIsTimeColumn() {
        DBTableInspector di = new DBTableInspector(T1,conn);
        Assert.assertFalse(di.isTimeColumn(0));
        Assert.assertTrue(di.isTimeColumn(1));
        Assert.assertFalse(di.isTimeColumn(2));
        Assert.assertFalse(di.isTimeColumn("c_double"));
        Assert.assertFalse(di.isTimeColumn("NOT_THERE"));
    }
    
    @Test
    public void testIsUnique() {
        DBTableInspector di = new DBTableInspector(T1,conn);
        Assert.assertTrue(di.isUniqueColumn(0));
        Assert.assertTrue(di.isUniqueColumn(1));
        Assert.assertFalse(di.isUniqueColumn(2));
        Assert.assertTrue(di.isUniqueColumn("c_double"));
        Assert.assertFalse(di.isUniqueColumn("NOT_THERE"));
    }

    
}
