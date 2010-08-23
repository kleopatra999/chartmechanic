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


import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

import static org.junit.Assert.*;

public class ScalarDataStreamTest {

    @Test
    public void testDouble() throws Exception {
        Double d = new Double(3.1415);
        ScalarDataStream sds = new ScalarDataStream(d);
        int count = 0;
        Double v = null;
        while (sds.next()) {
            v = sds.getDouble(0);
            count++;
        }
        
        assertTrue(count == 1);
        assertEquals(d,v);
        
        Metadata md = sds.getMetadata();
        assertTrue(md.getColumnCount() == 1);
        assertTrue(md.getColumnInfo(1).getType() == DataType.DOUBLE);
    }
    
    @Test
    public void testString() throws Exception {
        String s = new String("hello world");
        ScalarDataStream sds = new ScalarDataStream(s);
        int count = 0;
        String v = null;
        while (sds.next()) {
            v = sds.getString(0);
            count++;
        }
        
        assertTrue(count == 1);
        assertEquals(s,v);
        
        Metadata md = sds.getMetadata();
        assertTrue(md.getColumnCount() == 1);
        assertTrue(md.getColumnInfo(1).getType() == DataType.STRING);
    }
}
