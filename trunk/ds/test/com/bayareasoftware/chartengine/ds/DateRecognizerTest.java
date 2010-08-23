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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.bayareasoftware.chartengine.util.DateUtil;

public class DateRecognizerTest {
    private static final String M = "m";
    private static final String D = "d";
    private static final String Y = "y";
    private static final String H = "h";
    private static final String I = "i"; // Minutes
    private static final String S = "s";
    private static final String T = H+I+S;
    private static final String MDY = "mdy";
    private static final String MDYT = MDY+T;
    private static final String MY = "my";
    private static final String MD = "md";
    
    private static final String[] DATES = {
        "December 15, 2006",        MDY,
        "december 15, 2006",        MDY,
        "12/15/06 12:23:23",        MDYT,
        "12/15/06",                 MDY,
        "12/15/2006 12:23:23",      MDYT,
        "12/15/2006",               MDY,
        "15 Dec 2006",              MDY,
        "2006/12",                  MY,
        "2006-12",                  MY,
        "2006.12",                  MY,
        "12-15-06 12:23:23",        MDYT,
        "12-15-06",                 MDY,
        "12-15-2006 12:23:23",      MDYT,
        "12-15-2006",               MDY,
        "2006-12-15",               MDY,
        "2006-12-15 12:23:23",      MDYT,
        "20061215 12:23:23",        MDYT,
        "20061215",                 MDY,
        "2006",                     Y,
        "2006.0",                   Y,
        "FY 2006",                  Y,
//        "06",                       Y,
        "15-Dec-06 12:23:23",       MDYT,
        "15-Dec-06",                MDY,
        "15-Dec-2006 12:23:23",     MDYT,
        "15-Dec-2006",              MDY,
        "15-Dec",                   MD,
        "Fri Dec 15 2006",          MDY,
        "Fri Dec 15 12:23:23 2006", MDYT,
        "December 2006",            MY,
        "Dec 15, 2006",             MDY,
        "Dec 15, 2006 12:23:23",    MDYT,
        "Dec 15, 2006 12:23:23 pm", MDYT,
        "Dec 2006",                 MY,
        "Dec-2006",                 MY,
        "Dec 06",                   MY,
        "Dec-06",                   MY,
        "2006 Dec",                 MY,
        "12/06",                    MY,
        "12/2006",                  MY,
        "2006-Dec.",                MY,
        "2006-Dec. 15",             MDY,
        "15 Dec 06 12:23 pm",       MDY+H+I,
        "Fri Dec 15 12:23:23 PST 2006", MDYT,
        "2006-12-15T12:23:23.892-0800", MDYT,
        "2006-12-15T12:23:23.892Z", MDYT,
        "Dec. 15, 2006",            MDY,
        "2006Dec",                  MY,
        "2006M12",                  MY,
        "15/12/2006",                MDY
    };

    private static final String[] TIMES = {
        "12:12 pm", "hh:mm a",
        "12:12pm",  "hh:mma",
        "23:12",    "HH:mm",
    };

    @Test
    public void testTimes() {
        for (int i = 0; i < TIMES.length / 2; ) {
            String pattern = DateRecognizer.getTimePattern(TIMES[i++]);
            assertEquals(TIMES[i++], pattern);
        }
    }

    @Test
    public void testAdHoc() throws Exception {
        SimpleDateFormat sdf;
        Date d;
        { 
            sdf = new SimpleDateFormat("yyyy-MMM dd");
            d = new Date();
            p(d + " --> " + sdf.format(d));
            p("" + sdf.parse("2006-Dec 15"));
        }
        {
            sdf = new SimpleDateFormat("yyyy.MM");
            d =sdf.parse("1871.1");
            p("'1871.1'->'" + d + "'");
        }
        {
            sdf = new SimpleDateFormat("yyyy.'0'");
            d = sdf.parse("1987.0");
            p("'1987.0'->'" + d + "'");
            
        }
    }
    @Test
    public void testQuarters() throws ParseException {
        DateRecognizer dr = new DateRecognizer();
        DateFormat sdf;
        Date d;
        
        dr.reset();
        dr.parse("1947Q2");
        assertFalse("need to recognize yyyy'Q'q date format", dr.failed());
        sdf = dr.getSimpleDateFormat();
        assertNotNull("expect not null for quater SimpleDateFormat", sdf);
        d = sdf.parse("1947Q2");
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        assertEquals(1947, cal.get(Calendar.YEAR));
        assertEquals(Calendar.JUNE, cal.get(Calendar.MONTH));
        assertEquals(30, cal.get(Calendar.DATE));
        dr.reset();
        {
            String q = "1991:2";
            dr.parse(q);
            assertFalse("need to recognize yyyy':'q date format", dr.failed());
            sdf = dr.getSimpleDateFormat();
            d = sdf.parse(q);
            p("parsed '" + q + "' to '" + d + "'" );
        }
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testDates() throws Exception {
        DateRecognizer dr = new DateRecognizer();
        for (int i = 0; i < DATES.length / 2; i++) {
            String ds = DATES[i * 2];
            String fmt = DATES[i * 2 + 1];
            dr.reset();
            dr.parse(ds);
            DateFormat sdf = dr.getSimpleDateFormat();
            assertNotNull("cannot recognize date string '" + ds + "'", sdf);
            Date date;
            try {
                date = sdf.parse(ds);
            } catch (ParseException e) {
                throw new AssertionError(e);
            }
            String msg = ds + DateUtil.getDatePattern(sdf);//" (" + sdf.toPattern() + ")";
            if (fmt.contains(M)) assertEquals(msg, 11, date.getMonth());
            if (fmt.contains(D)) assertEquals(msg, 15, date.getDate());
            if (fmt.contains(Y)) assertEquals(msg, 2006 - 1900, date.getYear());
            if (fmt.contains(H)) assertEquals(msg, 12, date.getHours());
            if (fmt.contains(I)) assertEquals(msg, 23, date.getMinutes());
            if (fmt.contains(S)) assertEquals(msg, 23, date.getSeconds());
        }
        {
            // test other format without abbreviation
            String ds = "1972-May";
            dr.reset();
            dr.parse(ds);
            DateFormat sdf = dr.getSimpleDateFormat();
            assertNotNull(ds, sdf);
            Date d = sdf.parse(ds);
            assertEquals(72, d.getYear());
            assertEquals(4, d.getMonth());
        }
        
    }

    @Test
    public void testNanos() throws ParseException {
        DateRecognizer dr = new DateRecognizer();
        dr.parse("2006-12-15T12:23:23.892444Z");
        assertFalse(dr.failed());
        dr.parse("2006-12-15T12:23:23.555555Z");
        assertFalse(dr.failed());
        DateFormat sdf = dr.getSimpleDateFormat();
        assertNotNull(sdf);
        Date d = DateUtil.parseDate("2006-12-15T12:23:23.892444Z", sdf);
        assertEquals(892, d.getTime() % 1000);
    }
    
    private static void p(String s) {
        System.out.println("[DateRecTest] " + s);
    }
}
