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
package demo;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.tag.ChartController;

public class DemoContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent evt) {

    }

    /*
java.naming.factory.url.pkgs=org.mortbay.naming
java.naming.factory.initial=org.mortbay.naming.InitialContextFactory 
     */
    @Override
    public void contextInitialized(ServletContextEvent evt) {
        p("initializing demo DBMS tables:");
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DemoDS");
            conn = ds.getConnection();
            createTables(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }
        p("initialized demo data");
        ChartController.get().setRelativeURIs(true);
        p("initialized chart controller");
    }

    private static void createTables(Connection conn) throws Exception {
        //p("creating tables");
        Random rand = new Random(System.currentTimeMillis());
        try {
            Statement st = conn.createStatement();
            st.execute("CREATE TABLE LINEAR (x INTEGER, y INTEGER)");
            st.execute("CREATE TABLE QUADRATIC (x INTEGER, y INTEGER)");
            st.execute("CREATE TABLE RANDOM (x INTEGER, y INTEGER)");
            st.execute("CREATE TABLE GAUSSIAN (x FLOAT8)");
            st.execute("CREATE TABLE TIMESERIES (d TIMESTAMP, x FLOAT8, y FLOAT8, w FLOAT8, z FLOAT8)");
            st.execute("CREATE TABLE RANDOM_WALK (d TIMESTAMP, x FLOAT8, y FLOAT8, w FLOAT8, z FLOAT8)");
            st.execute("CREATE TABLE CATEGORY (name VARCHAR(20), val1 INTEGER, val2 INTEGER)");
            st.close();
            CallableStatement c1 = conn.prepareCall(
                    "INSERT INTO LINEAR (x,y) VALUES (?, ?)"
                    );
            CallableStatement c2 = conn.prepareCall(
                    "INSERT INTO QUADRATIC (x,y) VALUES (?, ?)"
                    );
            CallableStatement c3 = conn.prepareCall(
                    "INSERT INTO RANDOM (x,y) VALUES (?, ?)"
                    );
            CallableStatement c4 = conn.prepareCall(
                    "INSERT INTO CATEGORY (name,val1,val2) VALUES (?, ?, ?)"
                    );
            
            for (int x = 0; x <= 10; x++) {
                c1.setInt(1, x);
                c1.setInt(2, x);
                c2.setInt(1, x);
                c2.setInt(2, x*x);
                int r = rand.nextInt();
                r = Math.abs(r);
                r %= 10;
                r += 10;
                c3.setInt(1, x);
                c3.setInt(2, r);
                c1.execute();
                c2.execute();
                c3.execute();
                c4.setString(1, "Cat-" + x);
                c4.setInt(2, (rand.nextInt() % 10) + 1);
                c4.setInt(3, Math.abs(rand.nextInt() % 100));
                c4.execute();
            }
            c1.close();
            c2.close();
            c3.close();
            c4.close();
            CallableStatement gs = conn.prepareCall("INSERT INTO GAUSSIAN(x) VALUES (?)");
            for (int i = 0; i < 1000; i++) {
                // put it in a range from -10 to 10, centered on zero
                double val = rand.nextGaussian();
                //System.err.println("raw gaussian=" + val);
                //val *= 5;
                //val -= 10;
                //val = Math.min(val, 9.99999999999);
                //val = Math.max(val, -10);
                gs.setDouble(1, val);
                gs.execute();
            }
            setupTimeseries(conn);
            setupRandomWalk(conn);
            //setupBrokerage(conn);
        } finally {
            if (conn != null) {
                //p("NOT closing connection");
                conn.close();
            }
        }
    }
    
    private static void setupBrokerage(Connection conn) throws Exception {
        Statement st = conn.createStatement();
        st.execute("CREATE TABLE BROKERAGE (d TIMESTAMP, symbol VARCHAR(20), amount FLOAT8)");
        st.execute("CREATE ALIAS DATE_TRUNC FOR \"com.bayareasoftware.chartengine.h2.Functions.dateTrunc\"");
        CallableStatement cs = conn.prepareCall("INSERT INTO BROKERAGE (d,symbol,amount) VALUES (?,?,?)");
        File brokerFile = new File("../ds/test/data/brokerage.csv");
        String brokerUrl = brokerFile.toURI().toString();
        DataSourceInfo dsi = DataInference.get().inferFromURL(brokerUrl,-1).getDataSource();
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        // TXDate=1,Symbol=4,Amount=6
        while (ds.next()) {
            Date d = ds.getDate(1);
            String symbol = ds.getString(4);
            Double amt = ds.getDouble(6);
            if (amt != null) {
                //p(d + ",\"" + symbol + "\"," + amt);
                cs.setTimestamp(1, new Timestamp(d.getTime()));
                cs.setString(2, symbol);
                cs.setDouble(3, amt.doubleValue());
                cs.execute();
            }
        }
        if (false) {
            ResultSet rs = st.executeQuery(
                    "SELECT date_trunc('month',d) as DT,symbol,SUM(amount) FROM BROKERAGE"
                    + " GROUP BY DT,symbol order by DT"
                    );
            while (rs.next()) {
                Date d = rs.getDate(1);
                String symbol = rs.getString(2);
                double amt = rs.getDouble(3);
                p(d + "," + symbol + "," + amt);
            }
        }
        ds.close();
        st.close();
        cs.close();
    }

    private static void setupRandomWalk(Connection conn) throws SQLException {
        CallableStatement call = conn.prepareCall(
                "INSERT INTO RANDOM_WALK (d,x,y,w,z) VALUES (?, ?, ?, ?, ?)"
                );
        Random rand = new Random(0L);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        double w,x,y,z;
        w = x = y = z = 50.0;
        for (int i = 0; i < 265; i++) {
            call.setDate(1, new java.sql.Date(cal.getTime().getTime()));
            call.setDouble(2, w);
            call.setDouble(3, x);
            call.setDouble(4, y);
            call.setDouble(5, z);
            if (call.executeUpdate() == CallableStatement.EXECUTE_FAILED) {
                throw new RuntimeException("insert to RANDOM_WALK failed...");
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
            w = walk(rand, w);
            x = walk(rand, x);
            y = walk(rand, y);
            z = walk(rand, z);
        }
        call.close();
    }
    
    private static double walk(Random rand, double value) {
        double step = rand.nextDouble() * 2.0d;
        boolean b = rand.nextBoolean();
        if (b)
            step = -step;
        return value + step;
    }
    private static void setupTimeseries(Connection conn) throws SQLException {
        CallableStatement call = conn.prepareCall(
                "INSERT INTO TIMESERIES (d,x,y,w,z) VALUES (?, ?, ?, ?, ?)"
                );
        Random rand = new Random(0L);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2010);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        for (int i = 0; i < 265; i++) {
            call.setDate(1, new java.sql.Date(cal.getTime().getTime()));
            call.setDouble(2, rand.nextDouble() * 10.0d + 10);
            call.setDouble(3, rand.nextDouble() * 10.0d);
            call.setDouble(4, Math.abs(rand.nextDouble()) * 10.0d);
            call.setDouble(5, Math.abs(rand.nextDouble()) * 10.0d);
            if (call.executeUpdate() == CallableStatement.EXECUTE_FAILED) {
                throw new RuntimeException("insert to TIMESERIES failed...");
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        call.close();
    }
    private static void close(Object o) {
        if (o == null) return;
        try {
            if (o instanceof Connection) ((Connection)o).close();
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void p(String s) {
        System.out.println("[DemoListener] " + s);
    }
}
