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

import static com.bayareasoftware.chartengine.ds.util.HtmlTableTest.TABLE1;
import static com.bayareasoftware.chartengine.ds.util.HtmlTableTest.getBytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.StringUtil;

public class HtmlDataSourceTest {
    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    @Test
    public void testTable1() throws Exception {
        DataStream ds = getDataStream(TABLE1);
        Metadata md = ds.getMetadata();
        assertEquals(4, md.getColumnCount());
        assertEquals("Name", md.getColumnName(1));
        assertEquals("Cups", md.getColumnName(2));
        assertEquals("Sugar?", md.getColumnName(4));
        assertTrue(ds.next());
        assertEquals("T. Sexton", ds.getString(1));
        int i = ds.getInt(2);
        assertEquals(10, i);
        assertEquals("No", ds.getString(4));
        assertTrue(ds.next());
        i = ds.getInt(2);
        assertEquals(5, i);
        assertEquals("Yes", ds.getString(4));
        assertFalse(ds.next());
        assertFalse(ds.next());
    }
    @Test
    public void testMLB() throws Exception {
        File f = new File("test/data/mlb-salaries.html");
        Metadata md = new Metadata(3);
        md.setColumnName(1, "rank");
        md.setColumnType(1, DataType.DOUBLE);
        md.setColumnName(2, "team");
        md.setColumnType(1, DataType.STRING);
        md.setColumnName(3, "payroll");
        md.setColumnType(3, DataType.DOUBLE);
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setInputMetadata(md);
//        hi.setName("MLB Payroll");
        hi.setProperty(DataSourceInfo.HTML_TABLEID,"" + 16);
        hi.setUrl(f.toURL().toString());
        hi.setDataStartRow(2);
        DataStream ds = new HtmlDataSource(hi).getDataStream();
        int nrows = DSTestUtil.drainStream(ds, true);
        assertEquals("expect 30 teams in MLB", 30, nrows);
    }

    @Test
    public void testMLBWithEndRow() throws Exception {
        File f = new File("test/data/mlb-salaries.html");
        Metadata md = new Metadata(3);
        md.setColumnName(1, "rank");
        md.setColumnType(1, DataType.DOUBLE);
        md.setColumnName(2, "team");
        md.setColumnType(1, DataType.STRING);
        md.setColumnName(3, "payroll");
        md.setColumnType(3, DataType.DOUBLE);
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setInputMetadata(md);
//        hi.setName("MLB Payroll");
        hi.setProperty(DataSourceInfo.HTML_TABLEID,"" + 16);
        hi.setUrl(f.toURI().toString());
        hi.setDataStartRow(12); // normal data starts at row 2 so we're looking at the 11th time
        hi.setDataEndRow(14);
        DataStream ds = new HtmlDataSource(hi).getDataStream();
        assertTrue(ds.next());
        assertEquals("St. Louis Cardinals",ds.getString(2));
        assertTrue(ds.next());
        assertEquals("San Francisco Giants",ds.getString(2));
        assertTrue(ds.next());
        assertEquals("Philadelphia Phillies",ds.getString(2));
        assertFalse(ds.next());

    }

    @Test
    public void testPOLBRaw() throws Exception {
        File f = new File("test/data/polb.com_yearly_teus.asp.html");
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
//        hi.setName("POLB");
        Metadata md = new Metadata(6);
        md.setColumnName(1, "year");
        md.setColumnType(1, DataType.DATE);
        md.setColumnName(2, "loaded_inbound");
        md.setColumnType(2, DataType.DOUBLE);
        md.setColumnName(3, "loaded_outbound");
        md.setColumnType(3, DataType.DOUBLE);
        md.setColumnName(4, "loaded_total");
        md.setColumnType(4, DataType.DOUBLE);
        md.setColumnName(5, "empties");
        md.setColumnType(5, DataType.DOUBLE);
        md.setColumnName(6, "total_containers");
        md.setColumnType(6, DataType.DOUBLE);
        hi.setInputMetadata(md);
        hi.setProperty(DataSourceInfo.HTML_TABLEID,"" + 10);
        hi.setUrl(f.toURI().toString());
        hi.setDataStartRow(4);
        DataStream ds = new HtmlDataSource(hi).getDataStream();
        int nrows = DSTestUtil.drainStream(ds, true);
        assertEquals("expect 12 rows in POLB data", 12, nrows);
    }
    @Test
    public void testMLBRaw() throws Exception {
        File f = new File("test/data/mlb-salaries.html");
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        //hi.setName("MLB Payroll");
        hi.setProperty(DataSourceInfo.HTML_TABLEID,"" + 16);
        hi.setUrl(f.toURI().toString());
        RawData raw = DataInference.get().inferFromDS(hi, 50).getRawData();
        //DSTestUtil.printList(raw.data);
    }
    @Test
    public void testInvertVCRaw() throws Exception {
        File f = new File("test/data/vc-funding-stats.html");
        DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        dsi.setUrl(f.toURI().toString());
        dsi.setProperty(DataSourceInfo.HTML_TABLEID,"7");
        RawData rd = DataInference.get().inferFromDS(dsi,40).getRawData();
        //RawData rd = DSTestUtil.getRawData(f,DataSourceInfo.HTML_TYPE,40);
        rd.data = RawData.invert(rd.data);
        assertEquals(10, rd.data.size());
        assertEquals("2002", rd.data.get(4)[0]);
        assertEquals("$601", rd.data.get(9)[6]);
    }
    
    @Test
    public void testInvertVCStream() throws Exception {
        File f = new File("test/data/vc-funding-stats.html");
        //RawData rd = DSTestUtil.getRawData(f,DataSourceInfo.HTML_TYPE,40);
        DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        dsi.setUrl(f.toURI().toString());
        dsi.setProperty(DataSourceInfo.HTML_TABLEID,"7");
        //RawData rd = DataInference.get().getRawData(dsi,40);

//        DataSourceInfo dsi = rd.toDataSource();
        dsi.setRowInverted(true);
        dsi.setDataStartRow(2);
        Metadata md = new Metadata(22);
        md.setColumnName(1, "Year");
        md.setColumnType(1, DataType.DATE);
        md.setColumnType(2, DataType.UNKNOWN);
        for (int i = 3; i < 20; i++) {
            md.setColumnType(i, DataType.DOUBLE);
            md.setColumnName(i, "V" + i);
        }
        md.setColumnType(21, DataType.IGNORE);
        md.setColumnType(22, DataType.IGNORE);
        dsi.setInputMetadata(md);
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        int nrows = 0;
        while (ds.next()) {
            Date d = ds.getDate(1);
            assertNotNull("expect non-null date at row=" + nrows, d);
            String dstr = d.toString();
            assertTrue(dstr.indexOf("200" + nrows) != -1);
            if (nrows == 5) {
                Double val = ds.getDouble(3);
                assertNotNull(val);
                assertEquals(49.0d, val.doubleValue(), .01);

                val = ds.getDouble(8);
                assertNotNull(val);
                assertEquals(1134.0, val.doubleValue(), .01);

                val = ds.getDouble(20);
                assertNotNull(val);
                assertEquals(8482.0, val.doubleValue(), .01);
            }
            nrows++;
        }
        assertEquals(8, nrows);
    }

    private static final String[] WFC_NAMES = {
      "Date", "Open", "High", "Low", "Close", "Avg Vol", "Adj Close*"  
    };
    @Test
    public void testWFCVolume() throws Exception {
        File f = new File("test/data/wfc-monthly.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setUrl(url);
        hi.setProperty(DataSourceInfo.HTML_TABLEID, "19");
        InferredData id = DataInference.get().inferFromDS(hi, 100);
        {
            DataSourceInfo dsi = id.getDataSource();
            Metadata md = dsi.getOutputMetadata();
            assertEquals(7, md.getColumnCount());
            for (int i = 1; i <= md.getColumnCount(); i++) {
                assertEquals(WFC_NAMES[i-1], md.getColumnName(i));
                if (i > 1) {
                    assertEquals("NUMBER", DataType.toString(md.getColumnType(i)));
                }
            }
            assertEquals("DATE", DataType.toString(md.getColumnType(1)));
        }
        DataStream ds = new HtmlDataSource(hi).getDataStream();
        while (ds.next()) {
            Date d = ds.getDate(1);
            Double vol = ds.getDouble(6);
            if (vol != null) {
                assertTrue("expect " + vol + " > 5,000,000", vol > 5000000);
            }
            //System.err.println("'" + d + "'='" + vol + "'");
        }
        //DSTestUtil.drainStream(ds, true);
    }
    private static void printHTML(List<String[]> data, PrintStream out) {
        out.println("<table border=\"1\">");
        int colcount = 0;
        for (String[] row : data) {
            colcount = Math.max(colcount, row.length);
        }
        for (String[] row : data) {
            out.println("<tr>");
            for (int i = 0; i < colcount; i++) {
                String s = null;
                if (i < row.length) s = row[i];
                s = StringUtil.trim(s);
                if (s == null) s = "&nbsp;";
                out.println("<td>" + s + "</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");
    }
    private DataStream getDataStream(String html) throws Exception {
        File tmp = File.createTempFile("cmt", ".html");
        FileOutputStream os = new FileOutputStream(tmp);
        try {
            os.write(getBytes(html));
        } finally {
            os.close();
        }
        try {
//            DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
//            hi.setUrl(tmp.toURI().toString());
//            RawData rd = DSFactory.getRawData(hi,10);
//            hi.setInputMetadata(rd.metadata);
//            hi.setDataStartRow(rd.dataStartRow);
////          DSFactory.getRawData(hi,10);
////          return new HtmlDataSource(hi).getDataStream();
//            DSFactory.getRawData(hi,10);
//            return new HtmlDataSource(DSFactory.getRawData(hi,10).toDataSource()).getDataStream();
            //DataSourceInfo hi = DataInference.get().inferDS(DataSourceInfo.HTML_TYPE,tmp.toURI().toString(),10);
            DataSourceInfo hi = DataInference.get().inferFromURL(tmp.toURI().toString(),10).getDataSource();
            HtmlDataSource htds = new HtmlDataSource(hi);
            //System.err.println("[HtmlDataSourceTest] dataStart=" + hi.getDataStartRow());
            return htds.getDataStream();
            //return DSFactory.eval(hi);
            
        } finally {
            tmp.delete();
        }
    }
}
