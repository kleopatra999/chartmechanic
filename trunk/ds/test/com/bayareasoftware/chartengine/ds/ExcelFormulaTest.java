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

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.TableSynopsis;

public class ExcelFormulaTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    private static final File QFS_XLS = new File("test/data/QFS Data for Bay Area SW.xls");
    
    private static boolean runAllTests = true;
    @Test
    public void testQFSSum() throws Exception {
        String url = QFS_XLS.toURI().toString();
        InferredData idata = DataInference.get().inferFromURL(url, -1);
        
        assertTrue(idata.needMoreInfo());
        assertEquals(DataSourceInfo.SPREADSHEET_NAME, idata.getMissingDSProperty());
        //List<String> sheetNames = idata.getSheetNames();
        
        List<TableSynopsis> synopses = idata.getSynopses();
        
        assertNotNull(synopses);
        assertEquals(2, synopses.size());
        assertEquals("Dashboard", synopses.get(0).getTableId());
        assertEquals("QFS Projects (cmplt'd)", synopses.get(1).getTableId());
        
        DataSourceInfo dsi = idata.getDataSource();
        dsi.setProperty(DataSourceInfo.SPREADSHEET_NAME, "QFS Projects (cmplt'd)");
        idata = DataInference.get().inferFromDS(dsi, 100);
        assertFalse(idata.needMoreInfo());
        RawData rd = idata.getRawData();
        //DSTestUtil.printList(rd.data);
        assertEquals("expect 80 rows", 80, rd.data.size());
        double val;
        String s;
        String[] sa;
        // line 22 "Parts E-Fees", column K, formula: =SUM(K20:K21) 
        {
            sa = rd.data.get(21);
            assertNotNull(sa);
            assertTrue(sa.length > 10);
            s = sa[10];
            val = Double.parseDouble(s);
            assertEquals(280494d, val, .51);
        }
        // line 6, column Q, formula: =SUMIF($A$12:$A$68,$A6,Q$12:Q$68)
        {
            sa = rd.data.get(5);
            assertNotNull(sa);
            assertTrue(sa.length > 16);
            s = sa[16];
            p("row 5 col 16 ='" + s + "'");
            val = Double.parseDouble(s);
            if (runAllTests) {
                assertEquals(562825, val, .5);
            } else {
                p("FIXME: skipping over SUMIF() test....");
            }
        }
        //DSTestUtil.printList(rd.data);
        
    }
    
    private static void p(String s) {
        System.out.println("[XLSFormulaTest] " + s);
    }
}
