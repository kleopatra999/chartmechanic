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

import static org.junit.Assert.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.StreamStats.ColumnStats;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StreamStatsInfo;
import com.bayareasoftware.chartengine.model.TimeConstants;
import com.bayareasoftware.chartengine.model.TimeUtil;
public class StreamStatsTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }    
    private static final File BROKERAGE = new File("test/data/brokerage.csv"); 
    private static final File SPY = new File("test/data/monthly-spy.csv");
    private static final File CS_XLS = new File("test/data/CSHomePrice_History.xls");
    private static final File RE_SOLD = new File("test/data/RE-sold.csv");
    private static final File BLS_FURNITURE = new File("test/data/Desks-and-extensions.csv");

    private DataStream eval(DataSourceInfo dsi) throws Exception {
        CSVDataSource cds = new CSVDataSource(dsi);
        return cds.getDataStream();
    }
    
    @Test
    public void testSerialize() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(BROKERAGE);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        StreamStats ss = new StreamStats(ds.getMetadata());
        while (ds.next()) {
            ss.update(ds);
        }
        ds.close();
        StreamStatsInfo ssi = ss.toStreamStatsInfo();
        SimpleProps p = ssi.serializeToProps(null, null);
        StreamStatsInfo ssi2 = new StreamStatsInfo();
        //p("loading from props " + p);
        ssi2.deserializeFromProps(p, null);
        assertEquals(ssi, ssi2);
    }

    @Test
    public void testSPYStats() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(SPY);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        StreamStats ss = new StreamStats(ds.getMetadata());
        try {
            while (ds.next()) {
                ss.update(ds);
            }
        } finally {
            ds.close();
        }
        p("SPY stats: " + ss);
        ColumnStats cs = ss.getColumn(1);
        assertEquals(
                "expect column 1 to be type DATE", DataType.toString(DataType.DATE),
                DataType.toString(cs.getType())
                );
        assertEquals(
                DSTestUtil.getDate("2008-06-02"), cs.getMaxDate()
                );
        assertEquals(
                DSTestUtil.getDate("1993-01-29"), cs.getMinDate()
                );
        assertEquals("expect monthly frequency", 
                TimeUtil.decodeTimeInt(TimeConstants.TIME_MONTH),
                cs.getTimePeriodName()
                );
        
    }
    @Test
    public void testCaseShiller() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(CS_XLS);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        StreamStats ss = new StreamStats(ds.getMetadata());
        int nrows = 0;
        try {
            while (ds.next()) {
                nrows++;
                ss.update(ds);
            }
        } finally {
            ds.close();
        }
        p("CS_XLS stats (from " + nrows + " row(s)): " + ss);
        ColumnStats cs = ss.getColumn(1);
        assertEquals(
                "expect column 1 to be type DATE", DataType.toString(DataType.DATE),
                DataType.toString(cs.getType())
                );
        assertEquals("expect monthly frequency", 
                TimeUtil.decodeTimeInt(TimeConstants.TIME_MONTH),
                cs.getTimePeriodName()
                );
        SimpleProps props = new SimpleProps();
        StreamStatsInfo info = ss.toStreamStatsInfo();
        info.serializeToProps(props, null);
        //p("SERIALIZED: " + props);
        StreamStatsInfo i2 = new StreamStatsInfo();
        i2.deserializeFromProps(props, null);
        p("DE-Serialized: " + i2);
        assertEquals(info, i2);
        
    }
    @Test
    public void testBrokerageCSV() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(BROKERAGE);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        StreamStats ss = new StreamStats(ds.getMetadata());
        try {
            while (ds.next()) {
                ss.update(ds);
            }
        } finally {
            ds.close();
        }
        //p("stats: " + ss);
        assertEquals("should've seen 153 rows", 153, ss.getRowCount());
        StreamStats.ColumnStats cs;
        {
            cs = ss.getColumn(1);
            assertEquals("col 1 should be DATE", "DATE", DataType.toString(cs.getType()));
            assertEquals("expect zero nulls for date column", 0, cs.getNullCount());
            DateFormat dfmt = new SimpleDateFormat("yyyy.MM.dd");
            Date min = cs.getMinDate();
            Date max = cs.getMaxDate();
            assertNotNull("expect non-null min date", min);
            assertNotNull("expect non-null max date", max);
            assertNull("expect null minValue", cs.getMinValue());
            assertNull("expect null maxValue", cs.getMaxValue());
            assertEquals(dfmt.parse("2005.06.30"), min);
            assertEquals(dfmt.parse("2007.09.17"), max);
            assertEquals("expect daily frequency", 
                    TimeUtil.decodeTimeInt(TimeConstants.TIME_DAY),
                    cs.getTimePeriodName()
                    );
        }
        {
            cs = ss.getColumn(2);
            assertEquals("col 2 should have 151/153 nulls", 151, cs.getNullCount());
        }
        {
            cs = ss.getColumn(6);
            assertEquals("col 6 named 'Amount'", "Amount", cs.getName());
            assertEquals("col 6 should be NUMBER", "NUMBER", DataType.toString(cs.getType()));
            assertNull("expect null minDate", cs.getMinDate());
            assertNull("expect null maxDate", cs.getMaxDate());
            Double min = cs.getMinValue();
            Double max = cs.getMaxValue();
            Double avg = cs.getAverage();
            assertNotNull("expect non-null minValue", min);
            assertNotNull("expect non-null maxValue", max);
            assertEquals(-400.61, min.doubleValue(), 0.001);
            assertEquals(2377.94, max.doubleValue(), 0.001);
            assertNotNull("expect non-null average", avg);
            assertEquals(403.42, avg.doubleValue(), 0.01);
        }
        //p("metadata: " + dsi.getMetadata());
    }
    static final String RE_SCRIPT = 
        "data.addCol('num sold', 'NUMBER');\n" +
        "data.updateAll('num sold', 0);\n" +
        "data.groupby('DISTRICT', fn.aggregate('num sold', fn.count()));\n" +
        "data.sort('num sold', true);\n";

    @Test
    public void testRESold() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(RE_SOLD);
        dsi.setDataScript(RE_SCRIPT);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataSource src = DSFactory.createDataSource(dsi);
        DataStream ds = //this.eval(dsi);
            DSFactory.eval(src, null);
        Metadata md = ds.getMetadata();
        assertEquals(13, md.getColumnCount());
        assertEquals("num sold", md.getColumnName(13));
        StreamStats ss = new StreamStats(md);
        double obsMin = Double.MAX_VALUE, obsMax = Double.MIN_VALUE;
        int rowCount = 0;
        try {
            while (ds.next()) {
                ss.update(ds);
                Double d = ds.getDouble(13);
                assertNotNull(d);
                if (d < obsMin) {
                    obsMin = d;
                }
                if (d > obsMax) {
                    obsMax = d;
                }
                rowCount++;
            }
        } finally {
            ds.close();
        }
        p("stats: " + ss);
        p("obs min/max/rowCount: " + obsMin + "/" + obsMax + "/" + rowCount);
        ColumnStats cs = ss.getColumn(13);
        assertEquals("num sold", cs.getName());
        p(" column 13 (\'" + cs.getName() + "') " + cs);
        assertEquals(1.0, cs.getMinValue(), .01);
        assertEquals(7.0, cs.getMaxValue(), .01);
    }

    @Test
    public void testBLSMonthly() throws Exception {
        DataSourceInfo dsi = DSTestUtil.getDSInfo(BLS_FURNITURE);
        Metadata md = dsi.getInputMetadata();
        md.setColumnType(1, DataType.DATE);
        md.setColumnFormat(1, "long");
        DataSource src = DSFactory.createDataSource(dsi);
        DataStream ds = DSFactory.eval(src, null);
        StreamStats ss = new StreamStats(ds.getMetadata());
        try {
            while (ds.next()) {
                ss.update(ds);
            }
        } finally {
            ds.close();
        }
        StreamStats.ColumnStats cs;
        cs = ss.getColumn(1);
        p("bls date stats=" + cs);
        assertEquals("expect monthly frequency", 
                TimeUtil.decodeTimeInt(TimeConstants.TIME_MONTH),
                cs.getTimePeriodName()
                );
        /*        
        DSTestUtil.drainStream(ds, true);
        p("bls metadata=" + md);
        */
    }    
    private static void p(String s) {
        System.out.println("[StreamStatsTest] " + s);
    }
}
