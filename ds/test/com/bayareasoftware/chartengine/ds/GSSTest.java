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

import org.junit.Before;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

public class GSSTest {

    private boolean connected = true;
    
    @Before
    public void setUp() {
        if ("true".equalsIgnoreCase(System.getProperty("disconnected"))) {
            p("not connected, will skip GSS tests");
            connected = false;
        }
    }
    
    public static final String BROKER_URL =
        "http://spreadsheets.google.com/ccc?key=pjoWJxgUx4FgTG7vavJe5Tg&hl=en";
    public static final String[] BROKER_NAMES = {
        "TXDate","Symbol","Quantity","Price","Amount"  
      };
      public static final String[] BROKER_TYPES = {
          DataType.toString(DataType.DATE), DataType.toString(DataType.STRING),
          DataType.toString(DataType.DOUBLE),DataType.toString(DataType.DOUBLE),
          DataType.toString(DataType.DOUBLE)
      };            
    
    // timeout after 60 seconds
    @Test(timeout = 60000)
    public void testGSSBrokerage() throws Exception {
        if (!connected) {
            p("disconnected, will skip GSS test");
            return;
        }
        DataSourceInfo gss;
        /*gss = new DataSourceInfo(DataSourceInfo.GSS_TYPE);
        gss.setUrl(BROKER_URL);
        gss.setTableName("GSS_Broker");
        DSFactory.inferMetadata(gss, 200);
        */
        gss = DataInference.get().inferFromURL(BROKER_URL, "chartmechanic", "gumby1234").getDataSource();
        gss.setUsername("chartmechanic");
        gss.setPassword("gumby1234");
        //DataStream ds = DSFactory.createDataSource(gss).getDataStream();
        GSSDataSource gssds = new GSSDataSource(gss);
        DataStream ds = gssds.getDataStream();
        try {
            Metadata md = ds.getMetadata();
            assertNotNull("null metadata from GSS_Broker stream", md);
            assertEquals("5 columns in GSS brokerage sheet", 5, md.getColumnCount());
            for (int i = 0; i < BROKER_NAMES.length; i++) {
                ColumnInfo ci = md.getColumnInfo(i+1);
                assertEquals(BROKER_NAMES[i],ci.getName());
                String type = DataType.toString(ci.getType());
                assertEquals("expect column '" + ci.getName() + "' to be type '" + BROKER_TYPES[i] + "'",
                        BROKER_TYPES[i], type);
            }
            int nrows = DSTestUtil.drainStream(ds, false);
            // the header row is not included in this row count....
            assertEquals("expect 156 rows", 156, nrows);
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (Exception ignore) { }
            }
        }
        
    }
    private static void p(String s) {
        System.out.println("[GSSTest] " + s);
    }
}
