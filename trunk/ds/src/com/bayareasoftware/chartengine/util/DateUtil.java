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

import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.ds.util.LongDateFormat;
import com.bayareasoftware.chartengine.ds.util.QuaterDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Various utility methods for handling dates.
 */
public class DateUtil {
    public static Date parseDate(String s) throws ParseException {
        return parseDate(s, null);
    }
    
    @SuppressWarnings("deprecation")
    public static Date parseDate(String s, DateFormat sdf) throws ParseException {
        s = removeNanos(s);
        try {
            return new Date(s);
        } catch (IllegalArgumentException e) {
            // Fall through
        }
        if (sdf == null) {
            DateRecognizer dr = new DateRecognizer();
            dr.parse(s);
            sdf = dr.getSimpleDateFormat();
        }
        if (sdf != null) {
            try {
                return sdf.parse(s);
            } catch (ParseException e) {
            }
        }
        throw new ParseException("unrecognized date: '" + s + "'", 0);
    }

    public static String getDatePattern(DateFormat df) {
        String ret = null;
        if (df instanceof SimpleDateFormat) {
            ret = ((SimpleDateFormat)df).toPattern();
        } else if (df instanceof QuaterDateFormat) {
            ret = "quarter";
        } else if (df instanceof LongDateFormat) {
            ret = "long";
        }
        return ret;
    }
    
    public static DateFormat createDateFormat(String fmt) {
        SimpleDateFormat ret = null;
        if (fmt.startsWith("quarter")) {
            return new QuaterDateFormat();
        } else if (fmt.equals("long")) {
            return new LongDateFormat();
        } else {
            try {
                ret = new SimpleDateFormat(fmt);
            } catch (Exception badDateFormatString) {
                // FIXME: log user error here for feedback...
            }
        }
        return ret;
    }
    private static final Pattern DATE_PATTERN = Pattern.compile(
        "(\\p{Digit}[.]\\p{Digit}{3})\\p{Digit}+");
    
    // Make specified date string compatible with SimpleDateFormat by changing
    // nanoseconds to milliseconds where specified.
    public static String removeNanos(String s) {
        return DATE_PATTERN.matcher(s).replaceFirst("$1");
    }
}
