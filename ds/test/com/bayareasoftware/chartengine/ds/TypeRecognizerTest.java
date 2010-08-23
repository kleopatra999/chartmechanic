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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;

public class TypeRecognizerTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    // int,timestamp,currency,string
    private static final String[][] DATA1 = {
        { "1", "1994-05-28", "1.00", "dog", "" },
        { "2", "1996-03-04", "2.08", "gorilla", "" },
        { "", "2003-06-28", "4.13", "bird", "" },
        { "4", "2005-05-03", "11.11", "cat", "" },
        { "", "", "", "", "" }
    };
    
    private static final String[][] DATA2 = {
        { "DATE", "NUMBER" },
        { "1991:1", "1.00" },
        { "1991:2", "2.00" },
        { "1991:3", "3.00" },  
        { "1991:4", "4.00" },  
        { "1992:1", "5.00" },  
        { "1992:2", "6.00" },  
        { "1992:3", "7.00" }  
    };
    private static List<String[]> makeDataList(String[][] in) {
        List<String[]> ret = new ArrayList<String[]>();
        for (int i = 0; i < in.length; i++) {
            ret.add(in[i]);
        }
        return ret;
    }    
    /* null or empty string values in raw data
     * should have no effect on type deduction
     */
    @Test
    public void testData1() {
        //Metadata md = new Metadata(DATA1[0].length);
        //CSVInfo csv = new CSVInfo();
        //DataSourceInfo csv = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        RawData rd = new RawData();
        rd.data = makeDataList(DATA1);
        rd.dsType = DataSourceInfo.CSV_TYPE;
        TypeRecognizer tr = new TypeRecognizer(rd);
        //TypeRecognizer.guessColumnTypes(md, DATA1);
        tr.guessColumnTypes();
        Metadata md = rd.metadata;
        assertNotNull("need non-null metadata after type guessing", md);
        assertEquals("expecting 5 columns", 5, md.getColumnCount());
        String type;
        type = DataType.toString(md.getColumnType(1));
        assertEquals("expecting NUMBER for column 1", "NUMBER", type);
        type = DataType.toString(md.getColumnType(2));
        assertEquals("expecting DATE for column 2", "DATE", type);
        type = DataType.toString(md.getColumnType(3));
        assertEquals("expecting DOUBLE for column 3", "NUMBER", type);
        type = DataType.toString(md.getColumnType(4));
        assertEquals("expecting TEXT for column 4", "TEXT", type);
        type = DataType.toString(md.getColumnType(5));
        assertEquals("expecting UNKNOWN for column 5", "UNKNOWN", type);
    }

    @Test
    public void testData2() {
        //Metadata md = new Metadata(DATA1[0].length);
        //CSVInfo csv = new CSVInfo();
        //DataSourceInfo csv = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        RawData rd = new RawData();
        rd.data = makeDataList(DATA2);
        rd.dsType = DataSourceInfo.CSV_TYPE;
        TypeRecognizer tr = new TypeRecognizer(rd);
        //TypeRecognizer.guessColumnTypes(md, DATA1);
        tr.guessColumnTypes();
        Metadata md = rd.metadata;
        assertNotNull("need non-null metadata after type guessing", md);
        assertEquals("expecting 2 columns", 2, md.getColumnCount());
        String type;
        type = DataType.toString(md.getColumnType(1));
        assertEquals("expecting DATE for column 1", "DATE", type);
        type = DataType.toString(md.getColumnType(2));
        assertEquals("expecting NUMBER for column 2", "NUMBER", type);
    }
    
    @Test
    public void testMLB() throws Exception {
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        File f = new File("test/data/mlb-salaries.html");
        //hi.setName("MLB Payroll");
        hi.setProperty(DataSourceInfo.HTML_TABLEID,""+16);
        hi.setUrl(f.toURI().toString());
        RawData rd = DataInference.get().inferFromDS(hi, 10).getRawData();

        assertEquals("expect guessed header row to be 1", 1, rd.headerRow);
        assertEquals("expect guessed data start row to be 2",
                2, rd.dataStartRow);
        Metadata md = rd.metadata;
        assertEquals("expect 3 columns discovered", 3, md.getColumnCount());
        ColumnInfo ci;
        ci = md.getColumnInfo(1);
        /* FIXME: strip out illegal DB identifier chars like '.' */
        assertEquals("No.", ci.getName());
        assertEquals("expect NUMBER for 'No.'",
                DataType.toString(DataType.DOUBLE),
                DataType.toString(ci.getType()));
        
        ci = md.getColumnInfo(2);
        assertEquals("Team", ci.getName());
        assertEquals("expect TEXT for 'Team'",
                DataType.toString(DataType.STRING),
                DataType.toString(ci.getType()));

        ci = md.getColumnInfo(3);
        assertEquals("Payroll", ci.getName());
        /** FIXME: HANDLE CURRENCY
        assertEquals("expect DOUBLE for 'Payroll'",
                DataType.toString(DataType.DOUBLE),
                DataType.toString(ci.getType()));
                */
    }
    
    @Test
    public void testBrokerageXLS() throws Exception {
        File f = new File("test/data/brokerage.xls");
        //hi.setName("MLB Payroll");
        RawData rd = DataInference.get().inferFromURL(f.toURL().toString(), 40).getRawData();
        //System.out.println("brokerage metadata:" + rd.metadata);
        //DSTestUtil.printList(rd.data);
        assertEquals("expect guessed data start row to be 1",
                1, rd.dataStartRow);
        assertEquals("expect guessed header row to be 0", 0, rd.headerRow);
        
    }
    @Test
    public void testDateNumberDistinguishing() throws Exception {
        File f = new File("test/data/home-ownership-rates.csv");
        //hi.setName("MLB Payroll");
        RawData rd = DataInference.get().inferFromURL(f.toURL().toString(), 40).getRawData();
        Metadata md = rd.metadata;
        p("home-ownership metadata: " + md);
        assertEquals("expect column 1 of all years to be date",
                DataType.toString(DataType.DATE),
                DataType.toString(md.getColumnType(1))
                );
        //DSTestUtil.printList(rd.data);
    }
    private static void p(String s){
        System.out.println("[TypeRecTest] " + s);
    }
}
