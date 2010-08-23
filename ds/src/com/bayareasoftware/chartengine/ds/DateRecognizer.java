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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bayareasoftware.chartengine.util.DateUtil;

/**
 * Utility class for recognizing date formats in chart data.
 * TODO Add support for other locales (month/day reversed, etc)
 */
public class DateRecognizer {
    private final List<Format> formats;
    private DateFormat sdf;
    private boolean failed;

    public static final int F_NONE = 0;    // No parse flags
    public static final int F_TIME = 1;    // Includes optional timestamp

    private static final Object[] FORMATS = {
        "MMM d, yyyy", F_TIME,              // Dec 15, 2006
        "MM/dd/yy", F_TIME,                 // 12/15/06 12:23:23
        "MM-dd-yy", F_TIME,                 // 12-15-06 12:23:23
        "yyyy-MM-dd", F_TIME,               // 2006-12-15 12:23:23
        "yyyyMMdd", F_TIME,                 // 20061215 12:23:23
//        "yy", F_NONE,                       // 06
        "yyyy", F_NONE,                     // 2006
        "yyyy.'0'", F_NONE,                 // 2006.0
        "'FY' yy", F_NONE,                  // FY 2006
        "dd-MMM-yy", F_TIME,                // 15-Dec-06 12:23:23
        "dd MMM yy", F_TIME,                // 15 Dec 06 12:23:23
        "dd-MMM", F_NONE,                   // 15-Dec
        "EE MMM dd yy", F_TIME,             // Fri Dec 15 2006 12:23:23
        "EE MMM dd HH:mm:ss yy", F_NONE,    // Fri Dec 15 12:23:23 2006
        "MMM yy", F_NONE,                   // Dec 06, December 2006
        "MMM-yy", F_NONE,                   // Dec-06, December 2006
        "MMM yyyy", F_NONE,                 // Dec 2006, December 2006
        "MMM-yyyy", F_NONE,                 // Dec-2006, December-2006
        "MM/yy", F_NONE,                    // 12/06
        "MM/yyyy", F_NONE,                  // 12/2006
        "EE MMM dd HH:mm:ss z yyyy", F_TIME, // Fri Dec 15 12:23:23 PDT 2006
        "dd MMM", F_TIME,                   // 15 Dec 12:23 pm
        "yyyy/MM", F_NONE,                  // 2006/12
        "yyyy-MM", F_NONE,                  // 2006-12
        "yyyy-MMM", F_NONE,                 // 1959-May
        "yyyy MMM", F_NONE,                 // 2006 Dec
        "yyyy-MMM'.'", F_NONE,              // 2006-Dec. 15
        "yyyy-MMM'.' d", F_NONE,            // 2006-Dec  3
        "yyyy.MM", F_NONE,                  // 2006.12
        "yyyy-MMM d", F_NONE,              // 1972-Jan.
        "dd MMM yyyy", F_NONE,              // 03 Jan 2000
        "yyyy-MM-dd'T'HH:mm:ss.SSS", F_NONE,
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", F_NONE,
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", F_NONE,
        "quarter", F_NONE,
        "MMM. dd, yyyy", F_NONE,              // Feb. 27, 2008  (used in dry cargo shipping tables)
        "yyyyMMM", F_NONE,                     //yyyyMMM
        "yyyy'M'MM", F_NONE,
        "dd/MM/yy", F_NONE,                   // European style 
        "HH:mm:ss", F_NONE   // just timestamp
    };
    /*
    public static void main(String[] a) throws Exception {
        DateRecognizer dr = new DateRecognizer();
        java.util.Date d = new java.util.Date();
        List<Format> fmts = dr.getFormats();
        int i = 0;
        for (Format f : fmts) {
            DateFormat df = f.getDateFormat();
            System.out.println("#" + i++ + ") \"" + f.getPattern() + "\" | " + df.format(d));
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2006);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 5);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        d = cal.getTime();
        System.out.println("d=" + d);
        System.out.println("millis=" + d.getTime());
    }*/

    public DateRecognizer() {
        int len = FORMATS.length / 2;
        formats = new ArrayList<Format>(len);
        for (int i = 0; i < len; i++) {
            String datePattern = (String) FORMATS[i * 2];
            int supportsTime = (Integer) FORMATS[i * 2 + 1];
            formats.add(new Format(datePattern, supportsTime));
        }
    }

    public List<Format>  getFormats() {
        return formats;
    }
    public void addFormat(String datePattern, int flags) {
        formats.add(new Format(datePattern, flags));
    }

    public void addFormat(String format) {
        addFormat(format, F_NONE);
    }

    public void parse(String ds) {
        if (failed) return;
        ds = DateUtil.removeNanos(ds.trim());
        if (sdf == null) {
            sdf = findFormat(ds);
        } else {
            ParsePosition pp = new ParsePosition(0);
            if (sdf.parse(ds, pp) == null || pp.getIndex() < ds.length()) {
                sdf = null;
            }
        }
        if (sdf == null) failed = true;
    }

    public DateFormat getSimpleDateFormat() {
        return sdf;
    }

    public boolean failed() {
        return failed;
    }
    
    private DateFormat findFormat(String ds) {
        for (Format f : formats) {
            DateFormat sdf = f.match(ds);
            if (sdf != null) return sdf;
        }
        return null;
    }

    private static final Pattern TIME_PATTERN = Pattern.compile(
        "\\s*\\d+:\\d+(:\\d+)?(\\s*)(\\p{Alpha}*)\\s*");

    static String getTimePattern(String ts) {
        Matcher m = TIME_PATTERN.matcher(ts);
        if (!m.matches()) return null;
        StringBuilder sb = new StringBuilder();
        boolean is12hr = m.group(3).length() > 0;
        sb.append(is12hr ? "hh:mm" : "HH:mm");
        if (m.group(1) != null) sb.append(":ss");
        if (is12hr) {
            if (m.group(2).length() > 0) sb.append(' ');
            sb.append('a');
        }
        return sb.toString();
    }
    
    public void reset() {
        failed = false;
        sdf = null;
    }

    public static class Format {
        final String datePattern;
        final int flags;
        DateFormat sdf;

        private Format(String datePattern, int flags) {
            this.datePattern = datePattern;
            this.flags = flags;
        }

        DateFormat match(String ds) {
            DateFormat sdf = getDateFormat();
            ParsePosition pp = new ParsePosition(0);
            if (sdf.parse(ds, pp) == null) return null;
            String ts = ds.substring(pp.getIndex()).trim();
            if (ts.length() == 0) return sdf;
            if (!hasTime()) return null;
            String tp = getTimePattern(ts);
            return tp != null ?
                new SimpleDateFormat(datePattern + ' ' + tp) : null;
        }

        public DateFormat getDateFormat() {
            if (sdf == null) {
                sdf = DateUtil.createDateFormat(datePattern);
                sdf.setLenient(false);
            }
            return sdf;
        }

        public boolean hasTime() {
            return (flags & F_TIME) != 0;
        }
        
        public String getPattern() {
            return datePattern;
        }

        public String toString() {
            return datePattern;
        }
    }
}
