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
package com.bayareasoftware.chartengine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuarterDateFormatTest {

    private static final String[] data = {
        "1947-02-03", "1947-Q1",
        "1977-08-30", "1977-Q3",
        "1988-12-12", "1988-Q4",
        "2001-04-09", "2001-Q2"
    };
    @Test
    public void testFormat() throws ParseException {
        DateFormat fmt = new com.bayareasoftware.chartengine.ds.util.QuaterDateFormat();
        SimpleDateFormat ctrl = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < data.length; i+=2) {
            Date d = ctrl.parse(data[i]);
            assertEquals(data[i+1], fmt.format(d));
        }
    }
    
    private static final String[] data2 = {
        "2007Q1", "2007-03-31",
        "1998-q2", "1998-06-30",
        "1977-Q4", "1977-12-31",
        "1954q3", "1954-09-30",
        "1991:2", "1991-06-30"
    };
    @Test
    public void testParse() throws ParseException {
        DateFormat dfmt = new com.bayareasoftware.chartengine.ds.util.QuaterDateFormat();
        DateFormat ctrl = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < data2.length; i+=2) {
            Date d = dfmt.parse(data2[i]);
            Date d2 = ctrl.parse(data2[i+1]);
            assertEquals(d2,d);
        }
    }
    private static final String[] bad = {
      "FOO", "1998", "Q1997", "1998Q", "2002Qblah"  
    };
    @Test
    public void testMalformed() {
        for (int i = 0; i < bad.length; i++) {
            try {
                DateFormat dfmt;
                dfmt = new com.bayareasoftware.chartengine.ds.util.QuaterDateFormat();
                dfmt.parse(bad[i]);
                fail("expected parseException raised on '" + bad[i] + "'");
            } catch (ParseException expected) { 
                /*OK*/
            } catch (Exception uhoh) {
                uhoh.printStackTrace();
                fail("expected exception from '" + bad[i] + "':" + uhoh);
            }

        }
    }
    private static void p(String s) {
        System.out.println("[QuarterDateTest] " + s);
    }
}
