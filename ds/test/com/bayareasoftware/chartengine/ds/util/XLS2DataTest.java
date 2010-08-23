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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.bayareasoftware.chartengine.ds.DSTestUtil;

public class XLS2DataTest {

    @Test
    public void testTaxes() throws Exception {
        InputStream is = null;
        try {
            is = new FileInputStream("test/data/your_taxes.xls");
            XLS2Data xd = new XLS2Data(is,null,50);
            xd.process();
            DSTestUtil.printList(xd.getData());
        } finally {
            close(is);
        }
    }
    
    // assert that stream gets closed by XLS reader, apart from us
    private boolean streamClosed = false;
    @Test
    public void testRowLimit() throws Exception {
        streamClosed = false;
        InputStream is = new FileInputStream("test/data/CSHomePrice_History.xls") {
            public void close() throws IOException {
                super.close();
                streamClosed = true;
            }
        };
        final int MAX_ROWS = 20;
        XLS2Data xd = new XLS2Data(is,null,MAX_ROWS);
        xd.process();
        List<String[]> data = xd.getData();
        assertNotNull(data);
        assertEquals("expect only " + MAX_ROWS + " data limit", MAX_ROWS, data.size());
        assertTrue("expect stream to be closed by callee, not caller", streamClosed);
    }
    private static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException ignore) { }
    }
}
