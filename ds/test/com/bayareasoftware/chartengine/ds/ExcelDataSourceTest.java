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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;

public class ExcelDataSourceTest {
    
    @BeforeClass
    public static void init() {
        DSTestUtil.allowFiles();
    }
    static {
        /* see http://poi.apache.org/utils/logging.html */
        /*
        System.getProperties().put(
                "org.apache.poi.util.POILogger",
                "org.apache.poi.util.SystemOutLogger"
                );
        System.getProperties().put("poi.log.level", "" + POILogger.DEBUG);
    
        p("set POI logger to system.out logger...");
        POILogger log = POILogFactory.getLogger("unittest");
        p("logger is of type: " + log.getClass().getName());
        log.log(POILogger.WARN, "this is a WARN log message");
        log.log(POILogger.INFO, "this is a INFO log message");
        log.log(POILogger.DEBUG, "this is a DEBUG log message");
        p("debug enabled? " + log.check(POILogger.DEBUG));
        */
        
    }
    private boolean verbose = false;
    private static final File TIMESHEET_FILE = new File("test/data/timesheet.xls");
    private static final File BROKER_FILE = new File("test/data/brokerage.xls");
    private static final File SIMPLE_FILE = new File("test/data/simple.xls");
    private static final File TWOSHEET_FILE = new File("test/data/two_sheets.xls");
    
    // FIXME/RELEASE NOTE: xlsx file extension, Office 2007 XML format,
    // not supported by POI.  Must be office 2003 file format
    private static final File SPY_FILE = new File("test/data/monthly-spy.xls");
    // case shiller home price index, from:
    // http://www2.standardandpoors.com/portal/site/sp/en/us/page.topic/indices_csmahp/0,0,0,0,0,0,0,0,0,1,5,0,0,0,0,0.html
    private static final File CS_FILE = new File("test/data/CSHomePrice_History.xls");
    private static final File TAXES_FILE = new File("test/data/your_taxes.xls");
    
    private static final String[] SIMPLE_NAMES = {
        "MYDATE", "MYSTRING", "MYVAL"
    };
    private static final String[] SIMPLE_TYPES = {
        DataType.toString(DataType.DATE), DataType.toString(DataType.STRING),
        DataType.toString(DataType.DOUBLE)
    };
    
    
    private DataStream eval(DataSourceInfo dsi) throws Exception {
        ExcelDataSource ds = new ExcelDataSource(dsi);
        return ds.getDataStream();
    }
    
    
    @Test
    public void testSimpleXls() throws Exception {
        RawData rd = DataInference.get().inferFromURL(SIMPLE_FILE.toURI().toString(),-1).getRawData();
        Metadata md = rd.metadata;
        assertEquals("expect 3 columns for simple test", 3, md.getColumnCount());
        for (int i = 0; i < SIMPLE_NAMES.length; i++) {
            ColumnInfo ci = md.getColumnInfo(i+1);
            assertEquals(SIMPLE_NAMES[i],ci.getName());
            String type = DataType.toString(ci.getType());
            assertEquals("expect column '" + ci.getName() + "' to be type '" + SIMPLE_TYPES[i] + "'",
                    SIMPLE_TYPES[i], type);
        }
        //DSTestUtil.printList(rd.data);
        assertEquals("expect 6 rows from simple.xls", 6, rd.data.size());
    }

    @Test
    public void testTwoSheets() throws Exception {
        DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
        dsi.setUrl(TWOSHEET_FILE.toURI().toString());
        dsi.setDataStartRow(2);
        Metadata md = new Metadata(2);
        md.setColumnName(1,"Col1");
        md.setColumnType(1,DataType.STRING);
        md.setColumnName(2,"Col2");
        md.setColumnType(2,DataType.DOUBLE);
        dsi.setInputMetadata(md);
        
        dsi.setProperty(DataSourceInfo.SPREADSHEET_NAME,"Sheet2");
        ExcelDataSource eds = new ExcelDataSource(dsi);
        DataStream ds = eds.getDataStream();

        assertTrue(ds.next());
        assertEquals(ds.getString(1),"AAAA");
        assertTrue(ds.getDouble(2) == 10.0);

        assertTrue(ds.next());
        assertEquals(ds.getString(1),"BBBB");
        assertTrue(ds.getDouble(2) == 20.0);

        assertTrue(ds.next());
        assertEquals(ds.getString(1),"CCCC");
        assertTrue(ds.getDouble(2) == 30.0);

        assertFalse(ds.next());
        
        // now change the sheet name to something else
        dsi.setProperty(DataSourceInfo.SPREADSHEET_NAME,"Not There");
        eds = new ExcelDataSource(dsi);
        try {
            ds = eds.getDataStream();
            Assert.fail("Should have failed to read a spreadsheet when the sheet name is incorrect");
        } catch (Exception e) {
            System.err.println("expected exception of : " + e + " received from an incorrect sheet name");
        }

        assertFalse(ds.next()); // the stream should be empty

    }

    
    public static final String[] BROKER_NAMES = {
        "TXDate", "Action", "Quantity","Symbol","Price","Amount"  
      };
      public static final String[] BROKER_TYPES = {
          DataType.toString(DataType.DATE), DataType.toString(DataType.STRING),
          DataType.toString(DataType.DOUBLE),DataType.toString(DataType.STRING),
          DataType.toString(DataType.STRING),DataType.toString(DataType.DOUBLE)
      };
      
    @Test
    public void testBrokerage() throws Exception {
//        RawData rd = DataInference.get().inferFromURL(BROKER_FILE.toURI().toString(),
//                null, null, 40);
        RawData rd = DSTestUtil.getRawData(BROKER_FILE, DataSourceInfo.EXCEL_TYPE, 40);
        DataSourceInfo ei = rd.toDataSource();
        ExcelDataSource eds = new ExcelDataSource(ei);
        DataStream ds = eds.getDataStream();
        Metadata md = rd.metadata;
        p("broker data start: " + rd.dataStartRow);
        p("broker metadata=" + md);
        assertEquals("expect 7 columns in brokerage.xls", 7, md.getColumnCount());
        for (int i = 0; i < BROKER_NAMES.length; i++) {
            ColumnInfo ci = md.getColumnInfo(i+1);
            assertEquals(BROKER_NAMES[i],ci.getName());
            String type = DataType.toString(ci.getType());
            assertEquals("expect column '" + ci.getName() + "' to be type '" + BROKER_TYPES[i] + "'",
                    BROKER_TYPES[i], type);
        }
        int nrows = DSTestUtil.drainStream(ds, verbose);
        assertEquals("expect 156 rows from brokerage.xls stream", 156, nrows);
        assertEquals("expect 40 rows from brokerage.xls raw", 40, rd.data.size());

    }

    public static final String[] TIMESHEET_NAMES = {
        "DATE", "DESCRIPTION", "HOURS", "RATE", "AMOUNT"
      };
      public static final String[] TIMESHEET_TYPES = {
          DataType.toString(DataType.DATE), DataType.toString(DataType.STRING),
          DataType.toString(DataType.DOUBLE),DataType.toString(DataType.DOUBLE),
          DataType.toString(DataType.DOUBLE)
      };
    @Test
    public void testTimesheet() throws Exception {
//        DataSourceInfo ei = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
//        ei.setUrl(TIMESHEET_FILE.toURI().toString());
//        RawData rd = DSTestUtil.getRawData(ei,100);
//        ei.setInputMetadata(rd.metadata);
//        ei.setDataStartRow(rd.dataStartRow);
        InferredData idata = DataInference.get().inferFromURL(TIMESHEET_FILE.toURI().toString(),100);
        DataSourceInfo ei = idata.getDataSource();
        RawData rd = idata.getRawData();
        ExcelDataSource eds = new ExcelDataSource(ei);
        DataStream ds = eds.getDataStream();
        Metadata md = ds.getMetadata();
        p("timesheet metadata=" + md);
        p("timesheet data start=" + rd.dataStartRow + " headers=" + rd.headerRow);
        assertEquals("expect 5 columns in timesheet", 5, md.getColumnCount());
        for (int i = 1; i <= md.getColumnCount(); i++) {
            String colName = md.getColumnName(i);
            assertEquals(TIMESHEET_NAMES[i-1],colName);
            assertEquals("column named '" + colName + "'",
                    TIMESHEET_TYPES[i-1],DataType.toString(md.getColumnType(i)));
        }
        int nrows = DSTestUtil.drainStream(ds, verbose);
        assertEquals("expect 23 rows from timesheet.xls stream", 23, nrows);
        //Metadata md = rd.metadata;
        //p("timesheet metadata=" + md);
        //DSTestUtil.printList(rd.data);
        assertEquals("expect 38 rows from timesheet.xls raw", 38, rd.data.size());
    }
    
    @Test
    public void testSimpleRaw() throws Exception {
//        DataSourceInfo ei = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
//        ei.setUrl(SIMPLE_FILE.toURI().toString());
        RawData raw = DSTestUtil.getRawData(SIMPLE_FILE,DataSourceInfo.EXCEL_TYPE, 10);
        p("simple raw metadata: " + raw.metadata);
        if (verbose) DSTestUtil.printList(raw.data);
    }
    @Test
    public void testBrokerageRaw() throws Exception {
//        DataSourceInfo ei = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
//        ei.setUrl(BROKER_FILE.toURI().toString());
        RawData raw = DSTestUtil.getRawData(BROKER_FILE,DataSourceInfo.EXCEL_TYPE, 10);
        p("broker raw metadata: " + raw.metadata);
        if (verbose) DSTestUtil.printList(raw.data);
    }
    @Test
    public void testTimesheetRaw() throws Exception {
//        DataSourceInfo ei = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
//        ei.setUrl(TIMESHEET_FILE.toURI().toString());
//        RawData raw = DSTestUtil.getRawData(ei, 20);
        RawData raw = DSTestUtil.getRawData(TIMESHEET_FILE,DataSourceInfo.EXCEL_TYPE, 20);
        p("timesheet raw metadata: " + raw.metadata);
        if (verbose) DSTestUtil.printList(raw.data);
    }
    @Test
    public void testCaseShiller() throws Exception {
        DataSourceInfo ei;
        //ei = DataInference.get().inferFromURL(CS_FILE.toURI().toString()).toDataSource();
        ei = DSTestUtil.getDSInfo(CS_FILE);
        Metadata md = ei.getInputMetadata();
        assertEquals("expect inferred header row to be 1", 1, ei.getHeaderRow());
        assertEquals("expect inferred data row to be 2", 2, ei.getDataStartRow());
        md.setColumnName(1, "DATE");
        assertEquals("column 2 should be named 'PHXR'", "PHXR", md.getColumnName(2));
        assertEquals("column 5 should be named 'SFXR'", "SFXR", md.getColumnName(5));
        assertEquals("column 17 should be named 'NYXR'", "NYXR", md.getColumnName(17));
        // all other columns are numeric
        for (int i = 2; i <= md.getColumnCount(); i++) {
            md.setColumnType(i, DataType.DOUBLE);
        }
        p("case shiller metadata: " + md);
        ExcelDataSource eds = new ExcelDataSource(ei);
        DataStream ds = eds.getDataStream();
        int nrows = DSTestUtil.drainStream(ds, verbose);
        assertEquals("expect 255 rows from Case Shiller home data excel file", 255, nrows);

    }
 
    @Test
    public void testCaseShillerRaw() throws Exception {
//        DataSourceInfo ei = new DataSourceInfo(DataSourceInfo.EXCEL_TYPE);
//        ei.setUrl(CS_FILE.toURI().toString());
//        RawData raw = DSTestUtil.getRawData(ei, 1000);
        InferredData idata = DataInference.get().inferFromURL(CS_FILE.toURI().toString(),1000);
        RawData raw = idata.getRawData();
        DataSourceInfo ei = idata.getDataSource();
        //RawData raw = DSTestUtil.getRawData(CS_FILE,DataSourceInfo.EXCEL_TYPE, 1000);
        p("case shiller RAW metadata: " + raw.metadata);
        p("got " + raw.data.size() + " row(s) from Case Shiller Raw startRow="
                + ei.getDataStartRow() + " headerRow=" + ei.getHeaderRow());
        assertEquals("expect 257 rows from Case Shiller home data excel file", 257, raw.data.size());
        if (verbose) DSTestUtil.printList(raw.data);
    }
    /**
     * Asserts that some mal-formated date and numeric cells are returned
     * as null, rather than raising an exception
     */
    @Test 
    public void testSPY() throws Exception {
        DataSourceInfo ei = DSTestUtil.getDSInfo(SPY_FILE);
        Metadata md = ei.getInputMetadata();
        //md.setColumnName(1, "DATE");
        p("SPY metadata: " + md);
//        DataSource eds = DSFactory.createDataSource(ei);
//        DataStream ds = eds.getDataStream();
        DataStream ds = this.eval(ei);
        int row = 0;
        while (ds.next()) {
            row++;
            Date d = ds.getDate(1);
            Double open = ds.getDouble(2);
            //p("SPY at row " + row + ": date='" + d + "' open='" + open + "'");
            if (row == 19) {
                assertNull("expect null unparseable date at row 19", d);
                p("unparseable date at row 19: '" + ds.getString(1) + "'");
            } else {
                assertNotNull("expect non-null date at row " + row, d);
            }

            if (row == 20) {
                assertNull("expect null 'open' at row 20, not '" + open + "'", open);
                p("open string at row 20 is '" + ds.getString(2) + "'");
            } else {
                assertNotNull("expect non-null 'open' at row " + row, open);
            }

        }
        p("spy test read " + row + " row(s)");
    }
    /**
     * Make sure that null cells still get represented as nulls, among other things
     */
    @Test
    public void testTaxesNullCells() throws Exception {
        RawData rd;
//        rd = DataInference.get().inferFromURL(
//                TAXES_FILE.toURL().toExternalForm(),null,null,50
//                );
        //rd = DSTestUtil.getRawData(DSTestUtil.getDSInfo(TAXES_FILE), 50);
        rd = DSTestUtil.getRawData(TAXES_FILE,DataSourceInfo.EXCEL_TYPE,50);
        //DSTestUtil.printList(rd.data);
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
        DataSourceInfo dsi = rd.toDataSource();
        dsi.setDataStartRow(9);
        dsi.setDataEndRow(27);
        //DataStream stream = DSFactory.createDataSource(dsi).getDataStream();
        DataStream stream = this.eval(dsi);
        int nrows = 0;
        double budgetSum = 0, percentSum = 0, shareSum = 0;
        while (stream.next()) {
            String cat = stream.getString(3);
            if (cat == null || !cat.startsWith("Afghanistan")) {
                Double bval = stream.getDouble(4);
                assertNotNull("null value at row/col " + nrows + "/4 str='" + stream.getString(4) + "'", bval);
                budgetSum += bval;
            }
            percentSum += stream.getDouble(5);
            shareSum += stream.getDouble(6);
            nrows++;
        }
        p("nrows=" + nrows + " budgetSum=" + budgetSum + ", percentSum="
                + percentSum + ", shareSum=" + shareSum);
        assertEquals(19,nrows);
        assertEquals(9875, shareSum, 1);
        assertEquals(1.23, percentSum, .01);
        assertEquals(2.919E12, budgetSum, 1E10);
    }
    private static void p(String s) {
        System.out.println("[XlsTest] " + s);
    }

}
