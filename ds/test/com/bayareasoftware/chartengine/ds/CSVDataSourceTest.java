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

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.util.DataStreamUtil;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CSVDataSourceTest {
    
    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    private static final String SVNLOG = "test/data/svnlog.csv";
    private static final String EMPTY = "test/data/empty.csv";
    private static final String BROKERAGE = "test/data/brokerage.csv";
    private static final String RECESSIONS = "test/data/us-recessions.csv";
    private static final String SPY = "test/data/monthly-spy.csv";
    private static final String QUARTERLY_GDP = "test/data/real-GDP-quarterly.csv";
    private static final String PING_TIMES = "test/data/PingOfBaseStation.txt";
    private static final String PING_DATE_TIMES = "test/data/PingsWithDates.txt";
    
    
    private DataStream eval(DataSourceInfo dsi) throws Exception {
        CSVDataSource csvds = new CSVDataSource(dsi);
        return csvds.getDataStream();
    }
    
    @Test
    public void testUrlData() throws Exception {
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(SVNLOG));
        checkSvnData(eval(info));
    }

    @Test
    public void testEmptyData() throws Exception {
        DataSourceInfo info = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        info.setUrl(new File(EMPTY).toURI().toString());
        //DataStream s = DSFactory.createDataSource(info).getDataStream();
        DataStream s = this.eval(info);
        List<String[]> rawData = DataStreamUtil.getData(s,1);
        assertTrue(rawData.size()==0);
    }
    
    @Test
    public void testInlineData() throws Exception {
        DataSourceInfo info = DataInference.get().inferFromCSV(readData(SVNLOG)).getDataSource();
        //DataSourceInfo info = DSFactory.createInlineDS(readData(SVNLOG));
        checkSvnData(this.eval(info));
    }

    @Test
    public void testEmptyInlineData() throws Exception {
        DataSourceInfo info = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        info.setProperty(DataSourceInfo.CSV_DATA,"");
        //DataStream s = DSFactory.createDataSource(info).getDataStream();
        DataStream s = this.eval(info);
        List<String[]> rawData = DataStreamUtil.getData(s,1);
        assertTrue(rawData.size()==0);
    }

    @Test
    public void testMalformedInlineData() throws Exception {
        DataSourceInfo info = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        String str = "Not commas in this string";
        info.setProperty(DataSourceInfo.CSV_DATA,str);
        //DataStream s = DSFactory.createDataSource(info).getDataStream();
        DataStream s = this.eval(info);
        List<String[]> rawData = DataStreamUtil.getData(s,1);
        assertTrue(rawData.size()==1);
        assertTrue(rawData.get(0).length == 1);
        assertTrue(rawData.get(0)[0].equals(str));
    }

    
    @Test
    public void testFetchMaxRows() throws Exception {
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(SVNLOG));
        // test only getting 5 rows
        List<String[]> data = CSVDataSource.getRawData(info, 5);
        assertTrue(data.size() == 5);
        assertEquals(data.get(4)[0],"154");
        assertEquals(data.get(4)[1],"dave");
    }
    
    @Test
    public void testUSRecessions() throws Exception {
        DataSourceInfo info = DSTestUtil.getDSInfo((new File(RECESSIONS)));
        //DataStream ds = DSFactory.createDataSource(info).getDataStream();
        DataStream ds = this.eval(info);
        p("recession metadata: " + ds.getMetadata());
        DSTestUtil.drainStream(ds, false);
    }
    
    public static final String[] BROKER_NAMES = {
      "TXDate", "Action", "Quantity","Symbol","Price","Amount"  
    };
    /* the "price" column doesn't figure out its a double, b/c the
     * column is empty until the 53rd row
     */
    public static final String[] BROKER_TYPES = {
        DataType.toString(DataType.DATE), DataType.toString(DataType.STRING),
        DataType.toString(DataType.DOUBLE),DataType.toString(DataType.STRING),
        DataType.toString(DataType.DOUBLE),DataType.toString(DataType.DOUBLE)
    };
    
    @Test
    public void testBrokerageData() throws Exception {
        
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(BROKERAGE));
        //DataStream ds = DSFactory.createDataSource(info).getDataStream();
        DataStream ds = this.eval(info);
        Metadata md = ds.getMetadata();
        p("brokerage data start=" + info.getDataStartRow() + " headers=" + info.getHeaderRow() + " metadata:" + md);
        assertEquals("expect 6 columns", 6, md.getColumnCount());
        for (int i = 0; i < BROKER_NAMES.length; i++) {
            ColumnInfo ci = md.getColumnInfo(i+1);
            assertEquals(BROKER_NAMES[i],ci.getName());
            String type = DataType.toString(ci.getType());
            assertEquals("expect column '" + ci.getName() + "' to be type '" + BROKER_TYPES[i] + "'",
                    BROKER_TYPES[i], type);
        }
        DSTestUtil.drainStream(ds, false);
    }

    @Test
    public void testMonthlySPY() throws Exception {
        DataSourceInfo info;
//        info = DataInference.get().inferFromURL(
//                new File(SPY).toURI().toString(), null, null, 15
//                ).toDataSource();
        info = DSTestUtil.getDSInfo(new File(SPY),15);

        DataStream ds = this.eval(info);
        Metadata md = ds.getMetadata();
        assertEquals("expect 7 columns", 7, md.getColumnCount());
        assertEquals("expect column 1 of SPY to be type DATE",
                DataType.toString(DataType.DATE), DataType.toString(md.getColumnType(1))
                );
        int row = 0;
        while (ds.next()) {
            row++;
            Date d = ds.getDate(1);
            Double open = ds.getDouble(2);
            if (row == 19) {
                assertNull("expect null unparseable date at row 19", d);
            } else {
                assertNotNull("expect non-null date at row " + row, d);
            }

            if (row == 20) { // FIXME: is removeNonFloat() too aggressive?
                assertNull("expect null 'open' at row 20, not '" + open + "'", open);
                p("open string at row 20 is '" + ds.getString(2) + "'");
            } else {
                assertNotNull("expect non-null 'open' at row " + row, open);
            }

        }
        assertEquals("expect 186 rows from SPY CSV stream", 186, row);
        
    }
    
    static final String NUMERICS = "V1,V2\n"
        + "1,9.11742E7\n"
        + "2,6.0E7\n"
        + "3,8.2E7\n"
        + "4,2.2E7\n"
        + "5,5.5E7\n";
    @Test
    public void testExponentialNotation() throws Exception {
        // regression test for stripping 'E' from exponential notation double bug
        DataSourceInfo info = DataInference.get().inferFromCSV(NUMERICS).getDataSource();
        Metadata md = info.getInputMetadata();
        assertEquals(2, md.getColumnCount());
        assertEquals(DataType.DOUBLE, md.getColumnType(1));
        assertEquals(DataType.DOUBLE, md.getColumnType(2));
        DataStream ds = DSFactory.createDataSource(info).getDataStream();
        int nrows = 0;
        while (ds.next()) {
            nrows++;
            Double v1 = ds.getDouble(1);
            Double v2 = ds.getDouble(2);
            assertNotNull("row #" + nrows, v1);
            assertNotNull("row #" + nrows, v2);
            assertEquals("row #" + nrows, (double)nrows, v1, .01);
            // every v2 should be more than 10M, less than 100M
            // every v2 should hold true: 10,000,000 < v2 < 100,000,000
            assertTrue("row #" + nrows + ": v2=" + v2, v2 > 10000000);
            assertTrue("row #" + nrows + ": v2=" + v2, v2 < 100000000);
        }
    }
    // data in quarterly format e.g., "1999Q4"
    @Test
    public void testQuarterlyStream() throws Exception {
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(QUARTERLY_GDP));
        //DataStream ds = DSFactory.createDataSource(info).getDataStream();
        DataStream ds = this.eval(info);
        Metadata md = ds.getMetadata();
        assertEquals("expect 2 columns", 2, md.getColumnCount());
        int row = 0;
        //DSTestUtil.drainStream(ds, true);
        Calendar cal = Calendar.getInstance();
        while (ds.next()) {
            row++;
            if (row == 10) {
                cal.setTime(ds.getDate(1));
                assertEquals(1951, cal.get(Calendar.YEAR));
                assertEquals(Calendar.DECEMBER, cal.get(Calendar.MONTH));
            }
        }
        ds.close();
        assertEquals("expect 123 rows from Quarterly GDP", 123, row);
    }
    // TAB separated
    // date format '2009-06-16 20:18:22' 'yyyy-MM-dd HH:mm:ss' 
    @Test
    public void testPingTimeDates() throws Exception {
    	
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(PING_DATE_TIMES));
    	/*DataSourceInfo info = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
    	{
    		info.setUrl(new File(PING_DATE_TIMES).toURL().toString());
    		info.setProperty(DataSourceInfo.CSV_SEPARATOR, "\t");
    		Metadata md = new Metadata(2);
    		md.setColumnType(1, DataType.DATE);
    		md.setColumnName(1, "C1");
    		md.setColumnType(2, DataType.DOUBLE);
    		md.setColumnName(2, "C2");
    		info.setInputMetadata(md);
    	}*/
        DataStream ds = this.eval(info);
        Metadata md = ds.getMetadata();
        // have to override
        md.setColumnType(2, DataType.DOUBLE); 
        String sep = info.getProperty(DataSourceInfo.CSV_SEPARATOR);
        p("ping date-times metadata: " + md + "  --- #cols="+ md.getColumnCount()
        		+ " CSV_SEP=\"" + sep + "\"");
        assertEquals("expect 2 columns", 2, md.getColumnCount());
        assertEquals("expect TAB \\t as csv separator", "\t", sep);
        assertEquals("Expect DATE column for 1", "DATE", DataType.toString(md.getColumnType(1)));
        assertEquals("Expect NUMBER column for 2", "NUMBER", DataType.toString(md.getColumnType(2)));
        int nrows = 0;
        while (ds.next()) {
        	nrows++;
        	Date d = ds.getDate(1);
        	assertNotNull("null date at row " + nrows, d);
        	Double val = ds.getDouble(2);
        	assertNotNull("null value at row " + nrows, val);
        }
        assertEquals("expect 23 rows", 23, nrows);
        //int nrows = DSTestUtil.drainStream(ds, true);
        
    }
    // TAB separated
    // date format with only timestamp HH:mm:ss 
    @Test
    public void testPingTimestamps() throws Exception {
        DataSourceInfo info = DSTestUtil.getDSInfo(new File(PING_TIMES));
        DataStream ds = this.eval(info);
        Metadata md = ds.getMetadata();
        String sep = info.getProperty(DataSourceInfo.CSV_SEPARATOR);
        p("ping times metadata: " + md + "  --- #cols="+ md.getColumnCount()
        		+ " CSV_SEP=\"" + sep + "\"");
        assertEquals("expect 2 columns", 2, md.getColumnCount());
        assertEquals("expect TAB \\t as csv separator", "\t", sep);
        assertEquals("Expect DATE column for 1", "DATE", DataType.toString(md.getColumnType(1)));
        assertEquals("Expect NUMBER column for 2", "NUMBER", DataType.toString(md.getColumnType(2)));
        int nrows = DSTestUtil.drainStream(ds, false);
        assertEquals("expect 87139 rows", 87139, nrows);
        
    }
    
    private void checkSvnData(DataStream ds) {
        Metadata md = ds.getMetadata();
        assertEquals(4, md.getColumnCount());
        assertEquals(DataType.DOUBLE, md.getColumnType(1));
        assertEquals(DataType.STRING, md.getColumnType(2));
        assertEquals(DataType.DATE, md.getColumnType(3));
        assertEquals(DataType.STRING, md.getColumnType(4));
        
        List<String[]> data;
        try {
            data = DataStreamUtil.getData(ds,-1);
            // some spot checks of the data
            assertTrue(data.size() == 156);
            assertEquals("158.0", data.get(0)[0]);
            
            assertEquals("146.0", data.get(12)[0]);
            assertEquals("jolly", data.get(12)[1]);

            // ensure that low string field with embedded newlines work correctly
            String msg =  "an clumsy, initial stab at putting in-place data into the series.  \n\n"+
                    "SeriesInfo can now have a <data> field\n"+
                    "AbstractProducer treats that data as CSV if it exists\n\n"+
                    "If the SeriesTab in the UI had some means to edit that data, then we\n"+
                    "would have the beginnings of charts with completely internalized data\n";
            assertEquals(data.get(12)[3],msg);

            assertEquals("3.0", data.get(154)[0]);
            assertEquals("dave", data.get(154)[1]);
            //assertEquals(data.get(154)[2],"Wed Apr 11 15:15:58 PDT 2007");
            assertEquals("add more junk", data.get(154)[3]);
            assertEquals("2.0", data.get(155)[0]);
        } catch (Exception e) {
            fail("getting data from stream failed due to e");
        }
        
        
    }
    
//    public static void main(String[] a) throws Exception {
//        if (a.length == 0) {
//            System.err.println("usage: CSVDataSourceTest <file or URL>");
//            System.exit(1);
//        }
//        String url;
//        if (a[0].startsWith("file:/") || a[0].startsWith("http://") || a[0].startsWith("https://")) {
//            url = a[0];
//        } else {
//            url = (new File(a[0])).toURI().toString();
//        }
//        DataSourceInfo info = DSTestUtil.getDSInfo(url);
//        //DataSource ds = DSFactory.createDataSource(info);
//        //DataStream s = ds.getDataStream();
//        DataStream s = new CSVDataSource(info).getDataStream();
//        Metadata md = s.getMetadata();
//        md.mungeColumnNames(false);
//        p("metatdata: " + md);
//        int nrows = DSTestUtil.drainStream(s, true);
//        p("read " + nrows + " rows...");
//    }    
    
    private String readData(String file) throws Exception {
        FileReader r = new FileReader(file);
        StringBuilder sb = new StringBuilder();
        char[] cbuf = new char[1024];
        int len;
        while ((len = r.read(cbuf)) > 0) {
            sb.append(cbuf, 0, len);
        }
        return CSVDataSource.escapeNewline(sb.toString());
    }
    
    private static void p(String s) {
        System.err.println("[CSVTest] " + s);
    }
}
