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
package com.bayareasoftware.chartengine.functions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DSTestUtil;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.TimeUtil;

public class JoinTimeTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }

    static final File CS_HOME = new File("test/data/CSHomePrice_History.xls");
    static final File GLD_MONTH = new File("test/data/monthly-gold.csv");
    static final File GLD_YEAR = new File("test/data/yearly-gold.csv");
    static List<Arg> makeJoinArgs(ISeries s1, ISeries s2, String op, long interval) {
        List<Arg> args = new ArrayList<Arg>();
        {
            Arg a;
            a = new Arg(ArgType.SID, s1.getSid());
            a.setSeries(s1);
            args.add(a);
            a = new Arg(ArgType.MATH_OPERATOR, op);
            args.add(a);
            a = new Arg(ArgType.SID, s2.getSid());
            a.setSeries(s2);
            args.add(a);
            args.add(new Arg(ArgType.TIME_INTERVAL, interval + ""));
        }
        return args;
    }
    @Test
    public void testMonthlyJoin() throws Exception {
        
        DataStream ds1, ds2;
        {
            DataSourceInfo dsi;
            dsi = DSTestUtil.getDSInfo(CS_HOME);
            //dsi = DataInference.get().inferFromURL(url1, 100).getDataSource();
            ds1 = DSFactory.createDataSource(dsi).getDataStream();
            //dsi = DataInference.get().inferFromURL(url2, 100).getDataSource();
            dsi = DSTestUtil.getDSInfo(GLD_MONTH);
            ds2 = DSFactory.createDataSource(dsi).getDataStream();
        }

        int sid = 1;
        ISeries s1 = new MySeries("Case Shiller SF", sid++, 1, 5);
        ISeries s2 = new MySeries("Gold", sid++, 1, 3);
        ISeries out = new MySeries("CS/Gold", sid++, 1, 1);
        
        Map<Integer,DataStream> map = new HashMap<Integer,DataStream>();
        map.put(s1.getSid(), ds1);
        map.put(s2.getSid(), ds2);
        List<Arg> args = makeJoinArgs(s1, s2, "DIVIDE", TimeUtil.INTERVAL_MONTH);
        JoinTime tr = new JoinTime();
        tr.init(out, map, args);
        DataStream ds = tr.value();

        p("monthly join metadata: " + ds.getMetadata());
        int r = 0;
        while (ds.next()) {
            if (r == 0) {
                assertEquals(46.61, ds.getDouble(2), .01);
                assertEquals(400.5, ds.getDouble(3), .01);
                assertEquals(.116, ds.getDouble(4), .05);
            } else if (r == 62) {
                assertEquals(69.17, ds.getDouble(2), .01);
                assertEquals(341.7, ds.getDouble(3), .01);
                assertEquals(.2024, ds.getDouble(4), .01);
                
            }
            r++;
        }
        assertEquals(r,255);
        /*
        ds.reset();
        p("r=" + r);
        DataStreamUtil.dump(System.out, ds);
        */
    }
    @Test
    public void testYearlyJoin() throws Exception {
        
        DataStream ds1, ds2;
        {
            DataSourceInfo dsi;
            dsi = DSTestUtil.getDSInfo(CS_HOME);
            //dsi = DataInference.get().inferFromURL(url1, 100).getDataSource();
            ds1 = DSFactory.createDataSource(dsi).getDataStream();
            //dsi = DataInference.get().inferFromURL(url2, 100).getDataSource();
            dsi = DSTestUtil.getDSInfo(GLD_YEAR);
            ds2 = DSFactory.createDataSource(dsi).getDataStream();
        }

        int sid = 1;
        ISeries s1 = new MySeries("Case Shiller SF", sid++, 1, 5);
        ISeries s2 = new MySeries("Gold", sid++, 1, 3);
        ISeries out = new MySeries("CS/Gold", sid++, 1, 1);
        
        Map<Integer,DataStream> map = new HashMap<Integer,DataStream>();
        map.put(s1.getSid(), ds1);
        map.put(s2.getSid(), ds2);
        List<Arg> args = makeJoinArgs(s1, s2, "DIVIDE", TimeUtil.INTERVAL_MONTH);
        JoinTime tr = new JoinTime();
        tr.init(out, map, args);
        DataStream ds = tr.value();

        p("yearly join metadata: " + ds.getMetadata());
        int r = 0;
        while (ds.next()) {
            assertNotNull(ds.getDate(1));
            if (r % 12 != 0) {
                assertNotNull("null date at row=" + r, ds.getDate(1));
                assertNotNull(ds.getDouble(2));
                assertNull(ds.getDouble(3));
            } else if (r == 0) {
                assertEquals(46.61, ds.getDouble(2), .01);
                assertEquals(484.1, ds.getDouble(3), .01);
                assertEquals(.096, ds.getDouble(4), .01);
            } else if (r == 60) {
                assertEquals(69.67, ds.getDouble(2), .01);
                assertEquals(332.3, ds.getDouble(3), .01);
                assertEquals(.209, ds.getDouble(4), .01);
                
            }
            r++;
        }
        assertEquals(r,255);
        /*
        ds.reset();
        DataStreamUtil.dump(System.out, ds);
        */
    }    
    private static class MySeries implements ISeries {

        private String name;
        private int id, x , y;
        MySeries(String n, int i, int x, int y) {
            this.name = n;
            this.id = i;
            this.x = x;
            this.y = y;
        }
        public String getName() { return name; }
        public Integer getSid() { return id; }
        public int getXColumn() { return x;  }
        public void setXColumn(int x) { this.x = x; }
        public int getYColumn() { return y; }
        public void setYColumn(int y) { this.y = y; }
        public int getZColumn() { return 0; }

        public boolean deserializeFromProps(SimpleProps p, String prefix) {
            return false;
        }

        public SimpleProps serializeToProps(SimpleProps p, String prefix) {
            return null;
        }
    }
    private static void p(String s) {
        System.out.println("[JoinTimeTest] " + s);
    }

}
