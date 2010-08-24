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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.TableSynopsis;
public class ExcelInferenceTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    private static final File MONTHLY_XLS = new File("test/data/Monthly.xls");
    private static final File WITH_CHART_XLS = new File("test/data/spreadsheet-with-chart.xls");
    private static final File TAXES_XLS = new File("test/data/your_taxes.xls");
    private static final File TWO_SHEETS_XLS = new File("test/data/two_sheets.xls");
    private static final File CS_XLS = new File("test/data/CSHomePrice_History.xls");
    private static final File MLB_BRACKET_XLS = new File("test/data/2008-mlb-bracket.xls");
    
    @Test
    public void testMonthly() throws Exception {
        RawData rd;
        rd = DSTestUtil.getRawData(MONTHLY_XLS,DataSourceInfo.EXCEL_TYPE,-1);
        // need to hint that column #1 is date
        rd.metadata.setColumnType(1, DataType.DATE);
        assertEquals("expect header row 0", 0, rd.headerRow);
        assertEquals("expect data row 1", 1, rd.dataStartRow);
        //DSTestUtil.printList(rd.data);
        for (int i = 2; i <= rd.metadata.getColumnCount(); i++) {
            String colName = rd.metadata.getColumnName(i);
            assertEquals("expect double column at #" + i + " '" + colName + "'",
                    DataType.toString(DataType.DOUBLE),
                    DataType.toString(rd.metadata.getColumnType(i))
                    );
        }
        DataSourceInfo dsi = rd.toDataSource();
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        //DSTestUtil.drainStream(ds, true);
        int row = 0;
        while (ds.next()) {
            Date d = ds.getDate(1);
            row++;
            if (row <= 30) {
                assertNotNull("expect non-null dates for rows 1-30", d);
            }
            //p("#" + row + "='" + d + "'");
        }
    }
    
    @Test
    public void testTwoSheets() throws Exception {
        String url = TWO_SHEETS_XLS.toURI().toString();
        InferredData idata = DataInference.get().inferFromURL(url, -1);
        
        assertTrue(idata.needMoreInfo());
        //List<String> sheetNames = idata.getSheetNames();
        
        List<TableSynopsis> synopses = idata.getSynopses();
        
        assertTrue(synopses != null);
        assertTrue(synopses.size() == 2);
        assertTrue(synopses.get(0).getTableId().equals("Sheet1"));
        assertTrue(synopses.get(1).getTableId().equals("Sheet2"));
        
        
    }
    /*
    @Test
    public void testTaxesInference() throws Exception {
        p("begin taxes inference");
        String url = TAXES_XLS.toURL().toString();
        InferredData idata = DataInference.get().inferFromURL(url, 50);
        RawData rd = idata.getRawData();
        assertNotNull(rd);
        p("taxes inferred metadata=" + rd.metadata + " data start=" + rd.dataStartRow
                + " header row=" + rd.headerRow);
        DSTestUtil.printList(rd.data);
    }*/
    @Test
    public void testTaxes() throws Exception {
        RawData rd;
        rd = DSTestUtil.getRawData(TAXES_XLS,DataSourceInfo.EXCEL_TYPE,50);
        //p("assumed taxes metadata to be " + rd.metadata);
        assertEquals(34, rd.data.size());
        {
            // test that null cells get picked up as null strings
            // i.e., don't skip over empty cells
            String[] ssRow = rd.data.get(9);
            p("9/0='" + ssRow[0] + "' 9/1='" + ssRow[1] + "' 9/2='" + ssRow[2] + "'");
            assertEquals(6, ssRow.length);
            assertNull("expect null at row/col 9/0 not '" + ssRow[0] + "'", ssRow[0]);
            assertEquals("Social Security", ssRow[2]);
        }
        //DSTestUtil.printList(rd.data);
        //p("taxes inferred start row=" + rd.dataStartRow);
        Metadata md = rd.metadata;
        md.setColumnType(1, DataType.IGNORE);
        md.setColumnType(2, DataType.IGNORE);
        md.setColumnType(3, DataType.STRING);
        md.setColumnName(3, "Category");
        md.setColumnType(4, DataType.DOUBLE);
        md.setColumnName(4, "Budget Dollars");
        md.setColumnType(5, DataType.DOUBLE);
        md.setColumnName(5, "Percent of Taxes");
        md.setColumnType(6, DataType.DOUBLE);
        md.setColumnName(6, "Share per citizen");
        //rd.dataStartRow = 11;
        //p("taxes start row=" + rd.dataStartRow);
        //p("taxes metadata=" + rd.metadata);
        //Metadata md = rd.metadata;
        DataSourceInfo dsi = rd.toDataSource();
        dsi.setDataStartRow(9);
        dsi.setDataEndRow(27);
        //DataStream stream = DSFactory.createDataSource(dsi).getDataStream();
        ExcelDataSource ds = new ExcelDataSource(dsi); 
        DataStream stream = ds.getDataStream();
        int rowCount = 0;
        double dollarSum = 0, percentSum = 0, shareSum = 0;
        while (stream.next()) {
            String cat = stream.getString(3);
            Double dollar = stream.getDouble(4);
            Double percent = stream.getDouble(5);
            Double share = stream.getDouble(6);
            assertNotNull("category", cat);
            assertNotNull("dollar", dollar);
            assertNotNull("percent", percent);
            assertNotNull("share", share);

            assertTrue("dollar>0", dollar > 0);
            assertTrue("percent>0", percent > 0);
            assertTrue("share>0", share > 0);
            
            dollarSum += dollar;
            percentSum += percent;
            shareSum += share;
            rowCount++;
        }
        assertEquals("expect 19 rows", 19, rowCount);
        //p("dollar sum " + dollarSum);
        // compare the sum of the taxes column to the sum from the spreadsheet
        assertTrue("expect at least 31 rows from raw data", rd.data.size() >= 31);
        String sumString = rd.data.get(29)[3];
        assertNotNull("expect non-null formula sum string at row/col 29/3", sumString);
        double expectSum = 0;
        try {
            expectSum = Double.parseDouble(sumString);
        } catch (NumberFormatException nfe) {
            fail("cannot parse number string '" + sumString + "'");
        }
        //p("delta exp - observed =" + (expectSum - dollarSum));
        // get them correct within, say 100B dollars - a rounding error
        // to the federal gov't!
        // the observed delta at the writing is $43B
        assertEquals("checking data sum with sum from spreadsheet", expectSum, dollarSum, 1.0E11);
        //DSTestUtil.drainStream(stream, true);
    }
    @Test
    public void testCS() throws Exception {
        RawData rd;
//        rd = DataInference.get().inferFromURL(
//                CS_XLS.toURL().toExternalForm(),null,null,12
//                );
        //rd = DSTestUtil.getRawData(DSTestUtil.getDSInfo(CS_XLS),12);
        rd = DSTestUtil.getRawData(CS_XLS,DataSourceInfo.EXCEL_TYPE,30);

        //p("case shiller metadata=" + rd.metadata);
        //p("case shiller header,data rows " + rd.headerRow + "," + rd.dataStartRow);
        assertEquals("expect column 1 to be DATE", "DATE", DataType.toString(rd.metadata.getColumnType(1)));
        // AZ-Phoenix
        assertEquals("expect column 2 to be NUMBER", "NUMBER", DataType.toString(rd.metadata.getColumnType(2)));
    }
    // no good structured data, but should get raw data anyway
    @Test
    public void testUnstructured() throws Exception {
        RawData rd;
//        rd = DataInference.get().inferFromURL(
//                MLB_BRACKET_XLS.toURL().toExternalForm(),null,null,40
//                );
        //rd = DSTestUtil.getRawData(DSTestUtil.getDSInfo(MLB_BRACKET_XLS),40);
        rd = DSTestUtil.getRawData(MLB_BRACKET_XLS,DataSourceInfo.EXCEL_TYPE,40);
        //DSTestUtil.printList(rd.data);
        assertEquals("expect 34 rows in unstructed file", 34, rd.data.size());
        assertEquals("expect 12 columns in unstructured metadata", 12, rd.metadata.getColumnCount());
        p("unstructed metadata: " + rd.metadata);
    }
    //@Test
    public void testWithCharts() throws Exception {
        RawData rd;
        rd = DSTestUtil.getRawData(WITH_CHART_XLS, DataSourceInfo.EXCEL_TYPE, 40);
        assertEquals("2004.0", rd.data.get(0)[1]);
        assertEquals("550000", rd.data.get(1)[2]);
        p("data for " + WITH_CHART_XLS.getAbsolutePath() + ":");
        DSTestUtil.printList(rd.data);
    }
    private static void p(String s) {
        System.out.println("[XLSInferTest] " + s);
    }
}
