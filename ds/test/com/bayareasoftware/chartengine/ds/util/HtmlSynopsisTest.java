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
package com.bayareasoftware.chartengine.ds.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.DSTestUtil;
import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.TableSynopsis;

public class HtmlSynopsisTest {
    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    @Test
    public void testContinuumBuilds() throws Exception {
        File f = new File("test/data/continuum-builds.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setUrl(url);
        List<HtmlTable> tables = HtmlUtil.getTableSynopses(hi);
        assertEquals("expect 4 tables total", 4, tables.size());
        HtmlTable builds = tables.get(2);
        assertEquals("expect 185 rows from target in synopsis",   185, builds.getTotalRows());
        assertEquals("expect '171' at row 2, col=0 (zero-based)", "171", builds.getData().get(2)[0]);

    }
    
    @Test
    public void testMLBSynopses() throws Exception {
        File f = new File("test/data/mlb-salaries.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setUrl(url);
        List<HtmlTable> tables = HtmlUtil.getTableSynopses(hi);
        assertEquals("expect 23 tables total", 23, tables.size());
        
        HtmlTable mlbPay = tables.get(16);
        assertEquals("expect 32 rows from target in synopsis",
                32, mlbPay.getTotalRows());
        assertEquals("expect table id 'dont_sign_arod'", "dont_sign_arod", mlbPay.getTagId());
        printTable(mlbPay);
        assertEquals(HtmlUtil.SYNOPSIS_ROWS, mlbPay.getRowCount());
        assertEquals("expect 'Payroll' at row=1 col=2 (zero-based)", "Payroll", mlbPay.getData().get(1)[2]);
        assertEquals("expect 'New York Yankees' at row=2 col=2", "New York Yankees", mlbPay.getData().get(2)[1]);
        /*
        p("got " + tables.size() + " tables from " + url);
        int count = 0;
        for (HtmlTable t : tables) {
            if (t.getRowCount() > 2) {
                p("table #" + count + ":");
                printTable(t);
            }
            count++;
        }
        */
    }
    @Test
    public void testPOLBYearlySynopses() throws Exception {
        File f = new File("test/data/polb.com_yearly_teus.asp.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        //hi.setName("POLB TEUS");
        hi.setUrl(url);
        List<HtmlTable> tables = HtmlUtil.getTableSynopses(hi);
        assertEquals("expect 12 tables total", 12, tables.size());

        HtmlTable teu = tables.get(10);
        assertEquals("expect 16 total rows", 16, teu.getTotalRows());
        printTable(teu);
        assertEquals(HtmlUtil.SYNOPSIS_ROWS, teu.getRowCount());
        assertEquals("expect 'Yearly TEU Totals' at 1/0", "Yearly TEU Totals", teu.getData().get(1)[0]);
        assertEquals("expect '5,010,523' at 4/3", "5,010,523", teu.getData().get(4)[3]);
        /*
        p("got " + tables.size() + " tables from " + url);
        int count = 0;
        for (HtmlTable t : tables) {
            if (t.getRowCount() > 2) { 
                p("table: " + count + ":");
                printTable(t);
            }
            count++;
        }
        */
        //assertEquals("expect 23 tables total", 23, tables.size());
    }
    @Test
    public void testPOLBArchiveSynopses() throws Exception {
        File f = new File("test/data/polb.com_teus_archive_since_1995.asp.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        //hi.setName("POLB TEUS");
        hi.setUrl(url);
        List<HtmlTable> tables = HtmlUtil.getTableSynopses(hi);
        assertEquals("expect 12 tables total", 12, tables.size());

        /*
        p("got " + tables.size() + " tables from " + url);
        int count = 0;
        for (HtmlTable t : tables) {
            if (t.getRowCount() > 2) {
                p("table #" + count + ":");
                printTable(t);
            }
            count++;
        }*/
        
        HtmlTable teu = tables.get(10);
        p("got " + teu.getTotalRows() + " row(s) in main table");
        printTable(teu);
        assertEquals("expect 151 total rows", 151, teu.getTotalRows());
        assertEquals(HtmlUtil.SYNOPSIS_ROWS, teu.getRowCount());
        assertEquals("expect 'Since 1995' at 1/0", "Since 1995", teu.getData().get(1)[0]);
    }
    
    @Test
    public void testYahooFinanceDividends() throws Exception {
        File f = new File("test/data/wfc-dividends.html");
        String url = f.toURI().toString();
        DataSourceInfo hi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        hi.setUrl(url);
        List<HtmlTable> tables = HtmlUtil.getTableSynopses(hi);
        List<TableSynopsis> syns = HtmlUtil.tables2synopses(tables);
        assertEquals(31, tables.size());
        p("got " +tables.size() + " tables, " + syns.size() + " synopses");
        TableSynopsis syn = syns.get(syns.size() - 1);
        TableSynopsis max = syns.get(0);
        // get the longest table by row count
        for (TableSynopsis ts : syns) {
            if (ts.getTotalRows() > max.getTotalRows()) {
                max = ts;
            }
        }
        //p("max is " + max + " id=" + max.getIndex() + "/" + max.getTableId() + " #rows=" + max.getTotalRows());
        assertEquals("19", max.getTableId());
        //p("last table is: " + syn.getIndex());
        //printTable(table);
        //assertEquals("expect 31 tables total", 31, tables.size());
        hi.setProperty(DataSourceInfo.HTML_TABLEID, max.getTableId());
        InferredData id = DataInference.get().inferFromDS(hi, 1000);
        assertNull(id.getMissingDSProperty());
        RawData rd = id.getRawData();
        assertEquals(71, rd.data.size());
        assertEquals(
                "WFC Historical Prices for WELLS FARGO & CO NEW - Yahoo! Finance",
                rd.getProposedTitle()
                );
        //DSTestUtil.printList(rd.data);
        // validate content
        assertEquals("1-Nov-06", rd.data.get(9)[0]);
        assertEquals("$ 0.24 Dividend", rd.data.get(16)[1]);

    }
    
    private void printTable(HtmlTable t) {
        List<String[]> data = t.getData();
        int row = 0;
        StringBuilder sb = new StringBuilder();
        for (String[] sa : data) {
            sb.setLength(0);
            sb.append("row#").append(row).append("|");
            row++;
            for (String s : sa) {
                sb.append(s).append('|');
            }
            System.out.println(sb);
        }
    }
    private static void p(String s) {
        System.out.println("[SynopsisTest] " + s);
    }
}
