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
package com.bayareasoftware.chartengine.js;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.CSVDataSource;
import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DSTestUtil;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;

public class JSFunctionsTest {
    private static final File BROKERAGE_CSV = new File("test/data/brokerage.csv");
    private static final File LAYOFF_HTML = new File("test/data/layoff-tracker.html");
    
    @BeforeClass
    public static void init() {
        DSTestUtil.allowFiles();
    }
    private DataGrid getBrokerageData() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(BROKERAGE_CSV.toURI().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(BROKERAGE_CSV);
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        CSVDataSource cds = new CSVDataSource(dsi);
        //DataStream ds = DSFactory.eval(dsi);
        DataStream ds = cds.getDataStream();
        
        DataGrid dg = new DataGrid(ds);
        return dg;
    }
    
    private DataGrid getLayoffData() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(BROKERAGE_CSV.toURI().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(LAYOFF_HTML);
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        return new DataGrid(ds);
    }

    @Test
    public void testGroupBy() throws Exception {
        DataGrid dg = getBrokerageData();
        JSFunctions fn = JSFunctions.get();
        DataColumn dateColumn = dg.col("TXDate");
        DataColumn symbolColumn = dg.col("Symbol");
        DataColumn amtColumn = dg.col("Amount");
        dg.updateAll(dateColumn,fn.date_trunc("month"));
        dg.groupby(dateColumn, symbolColumn, fn.aggregate("Amount",fn.sum()));

        // spot-check a few of the points with known values
        //TXDate: Wed Jun 01 00:00:00 PDT 2005 Symbol: IBM Amount: 274.98
        assertEquals(dg.string(0,symbolColumn),"IBM");
        assertTrue(dg.num(0,amtColumn) == 274.98);
        
        //TXDate: Wed Jun 01 00:00:00 PDT 2005    Symbol: XXX Amount: 216.17     
        assertEquals(dg.string(1,symbolColumn),"XXX");
        assertTrue(dg.num(1,amtColumn) == 216.17);

        //TXDate: Fri Jul 01 00:00:00 PDT 2005    Symbol: ACME    Amount: 303.79
        assertEquals(dg.string(2,symbolColumn),"ACME");
        assertTrue(dg.num(2,amtColumn) == 303.79);

        //TXDate: Fri Jul 01 00:00:00 PDT 2005    Symbol: YYY Amount: 0.0
        assertEquals(dg.string(5,symbolColumn),"YYY");
        assertTrue(dg.num(5,amtColumn) == 0.0);

        //TXDate: Mon Aug 01 00:00:00 PDT 2005    Symbol: ACME    Amount: 326.29999999999995
        assertEquals(dg.string(6,symbolColumn),"ACME");
        Date d = dg.date(6,dateColumn);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        assertEquals(cal.get(Calendar.YEAR),2005);
        assertEquals(cal.get(Calendar.MONTH),7);
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),1);
        
        assertEquals(dg.getRowCount(),133);

//        for (int i =0;i< dg.getRowCount();i++) {
//            System.out.println("i = " + i + " TXDate: " + dg.date(i, dateColumn) +
//                               "\tSymbol: " + dg.string(i,symbolColumn) +
//                               "\tAmount: " + dg.num(i, amtColumn));
//        }

    }
    // date-trunc + groupby(month,industry)
    @Test
    public void testCompoundGroupBy() throws Exception {
        DataGrid dg = getLayoffData();
        JSFunctions fn = JSFunctions.get();
        DataColumn dateCol = dg.col("Date");
        DataColumn indCol = dg.col("Industry");
        DataColumn amtCol = dg.col("Total Laid Off");
        assertNotNull(amtCol);
        assertNotNull(dateCol);
        assertNotNull(indCol);
        assertEquals(DataType.DATE, dateCol.getType());
        assertEquals(DataType.DOUBLE, amtCol.getType());
        dg.updateAll(dateCol,fn.date_trunc("month"));
        dg.groupby(dateCol, indCol, fn.aggregate("Total Laid Off",fn.sum()));
        DataStream ds = dg.toStream();
        //DSTestUtil.drainStream(ds, true);
        //if (true) return;
        int row = 0;
        while (ds.next()) {
            if (row > 0) {
                Date d = ds.getDate(1);
                Double num = ds.getDouble(3);
                String industry = ds.getString(4);
                assertNotNull("null date at row " + row, d);
                assertNotNull("null number at row " + row, num);
                assertNotNull("null industry at row " + row, industry);
                if (row == 39) {
                    assertEquals("Conglomerates", industry);
                    assertEquals(6065, num, .01);
                }
            }
            row++;
        }
        assertEquals(70, row);
    }    
    
    @Test
    public void testSort() throws Exception {
        
        DataGrid dg = getBrokerageData();
        DataColumn symbolColumn = dg.col("Symbol");
        
        assertTrue(symbolColumn != null);
        
        // sort the 'Symbol' row in ascending order
        dg.sort(symbolColumn, false); 
        String s = dg.string(0,symbolColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            String s2 = dg.string(i,symbolColumn);
            if (s != null) {
                assertTrue(s.compareTo(s2) <= 0);
            }
            s = s2;
        }
        
        // sort the 'Symbol' column in descending order
        dg = getBrokerageData();
        symbolColumn = dg.col("Symbol");
        assertTrue(symbolColumn.getType() == DataType.STRING);
        dg.sort(symbolColumn, true); 
        s = dg.string(0,symbolColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            String s2 = dg.string(i,symbolColumn);
            if (s != null) { 
                assertTrue(s.compareTo(s2) >= 0);
            }
            s = s2;
        }
        
        // sort the 'Amount' column in ascending order
        dg = getBrokerageData();
        DataColumn amtColumn = dg.col("Amount");
        assertTrue(amtColumn.getType() == DataType.DOUBLE);
        dg.sort(amtColumn, false); 
        double d = dg.num(0, amtColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            double d2 = dg.num(i,amtColumn);
            assertTrue(d <= d2);
            d = d2;
        }

        
        // sort the 'TXDate' column in descending order
        dg = getBrokerageData();
        DataColumn txColumn = dg.col("TXDate");
        assertTrue(txColumn.getType() == DataType.DATE);
        dg.sort(txColumn, true); 
        Date txd = dg.date(0,txColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            Date txd2 = dg.date(i,txColumn);
            if (txd != null) {
                assertTrue(txd.compareTo(txd2)>=0);
            }
            txd = txd2;
        }

        
        // sort the 'Symbol' column in ascending order and the 'TXDate' column in descending order
        dg = getBrokerageData();
        symbolColumn = dg.col("Symbol");
        txColumn = dg.col("TXDate");
        dg.sort(symbolColumn, false, txColumn, true);
        s = dg.string(0,symbolColumn);
        txd = dg.date(0,txColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            String s2 = dg.string(i,symbolColumn);
            Date txd2 = dg.date(i,txColumn);
            //System.err.println("s2 = " + s2 + " txd2 = " + txd2);
            if (s != null) {
                if (s.equals(s2)){
                    assertTrue(txd.compareTo(txd2) >= 0);
                } else {
                    assertTrue(s.compareTo(s2) <= 0);
                }
            }
            s = s2;
            txd = txd2;
        }

        // sort the 'Symbol' column in descending order and the 'TXDate' column in ascending order
        dg = getBrokerageData();
        symbolColumn = dg.col("Symbol");
        txColumn = dg.col("TXDate");
        dg.sort(symbolColumn, true, txColumn, false);
        s = dg.string(0,symbolColumn);
        txd = dg.date(0,txColumn);
        for (int i = 0; i < dg.getRowCount(); i++) {
            String s2 = dg.string(i,symbolColumn);
            Date txd2 = dg.date(i,txColumn);
            //System.err.println("s2 = " + s2 + " txd2 = " + txd2);
            if (s != null) {
                if (s.equals(s2)){
                    assertTrue(txd.compareTo(txd2) <= 0);
                } else {
                    assertTrue(s.compareTo(s2) >= 0);
                }
            }
            s = s2;
            txd = txd2;
        }

        
        
    }
    
    @Test
    public void testDateTrunc() throws Exception {
        
        JSFunctions jsf = JSFunctions.get();
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        
        Calendar cal2 = Calendar.getInstance();
        
        Object result;
        Date d2;
        
        result = jsf.date_trunc("year").eval(d);
        
        assertTrue(result != null && result instanceof Date);
        d2 = (Date)result;

        cal2.setTime(d2);
        assertEquals(cal2.get(Calendar.YEAR), cal.get(Calendar.YEAR)); // year must be same
        assertEquals(cal2.get(Calendar.MONTH),0); 
        assertEquals(cal2.get(Calendar.DAY_OF_MONTH),1); 


        
        result = jsf.date_trunc("month").eval(d);
        assertTrue(result != null && result instanceof Date);
        d2 = (Date)result;

        cal2.setTime(d2);
        assertEquals(cal2.get(Calendar.YEAR), cal.get(Calendar.YEAR)); // year must be same
        assertEquals(cal2.get(Calendar.MONTH), cal.get(Calendar.MONTH)); // month must be same 
        assertEquals(cal2.get(Calendar.DAY_OF_MONTH),1); 

        result = jsf.date_trunc("day").eval(d);

        assertTrue(result != null && result instanceof Date);
        d2 = (Date)result;

        cal2.setTime(d2);
        assertEquals(cal2.get(Calendar.YEAR), cal.get(Calendar.YEAR)); // year must be same
        assertEquals(cal2.get(Calendar.MONTH), cal.get(Calendar.MONTH)); // month must be same 
        assertEquals(cal2.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.DAY_OF_MONTH)); // day must be same
        assertEquals(cal2.get(Calendar.HOUR),0);
        assertEquals(cal2.get(Calendar.MINUTE),0);
        assertEquals(cal2.get(Calendar.SECOND),0);
        assertEquals(cal2.get(Calendar.MILLISECOND),0);
        

        for (int month=0;month<11;month++) {
            cal.set(2001,month,5);
            result = jsf.date_trunc("quarter").eval(cal.getTime());
            assertTrue(result != null && result instanceof Date);
            d2 = (Date)result;
            cal2.setTime(d2);
            assertEquals(cal2.get(Calendar.YEAR), cal.get(Calendar.YEAR)); // year must be same
            int m = cal2.get(Calendar.MONTH);
            if (month < 3)
                assertTrue (m == Calendar.JANUARY);
            else if (month < 6) 
                assertTrue (m == Calendar.APRIL);
            else if (month < 9)
                assertTrue (m == Calendar.JULY);
            else
                assertTrue (m == Calendar.OCTOBER);
            assertEquals(cal2.get(Calendar.DAY_OF_MONTH),1); 
        }

        try {
            result = jsf.date_trunc("illegal part").eval(d);
            fail("should have raised an exception");
        } catch (Exception e) {
            // exception raised due to improper part passed to date_trunc
        }
    }
}
