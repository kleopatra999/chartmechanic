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
package com.bayareasoftware.chartengine.model;

public class TimeUtil implements TimeConstants {
    public static String[] getTimePeriods() {
        String[] ret = new String[9];
        int i = 0;
        ret[i++] = decodeTimeInt(TimeConstants.TIME_MILLI);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_SECOND);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_MINUTE);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_HOUR);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_DAY);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_WEEK);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_MONTH);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_QUARTER);
        ret[i++] = decodeTimeInt(TimeConstants.TIME_YEAR);
        return ret;
    }
    
    public static int decodeTimeString(String s) {
        int ret = -1;
        if ("millisecond".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_MILLI;
        } else if ("second".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_SECOND;
        } else if ("minute".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_MINUTE;
        } else if ("hour".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_HOUR;
        } else if ("day".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_DAY;
        } else if ("week".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_WEEK;
        } else if ("month".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_MONTH;
        } else if ("quarter".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_QUARTER;
        } else if ("year".equalsIgnoreCase(s)) {
            ret = TimeConstants.TIME_YEAR;
        } else if (s == null || "unknown".equalsIgnoreCase(s) || "".equals(s)) {
            ret = TimeConstants.TIME_UNKNOWN;
        } else {
            throw new IllegalArgumentException(
                    "unknown time period name '" + s + "'"
                    );
        }
        return ret;
    }
    
    public static final long INTERVAL_SEC = 1000;
    public static final long INTERVAL_MINUTE = 1000 * 60;
    public static final long INTERVAL_HOUR = 1000 * 60 * 60;
    public static final long INTERVAL_DAY = INTERVAL_HOUR * 24;
    public static final long INTERVAL_WEEK = INTERVAL_DAY * 7;
    public static final long INTERVAL_MONTH = INTERVAL_DAY * 27;
    public static final long INTERVAL_QUARTER = INTERVAL_MONTH * 3;
    public static final long INTERVAL_YEAR = INTERVAL_DAY * 360;
    /** 
     * Find appropriate time constant given the specified interval
     */
    public static int periodForInterval(long msec) {
        int ret = TimeConstants.TIME_UNKNOWN;
        if (msec <= 0) {
            ret = TIME_UNKNOWN;
        } else if (msec < INTERVAL_SEC) {
            ret = TIME_MILLI;
        } else if (msec < INTERVAL_MINUTE) {
            ret = TIME_SECOND;
        } else if (msec < INTERVAL_HOUR) {
            ret = TIME_MINUTE;
        } else if (msec < INTERVAL_DAY) {
            ret = TIME_HOUR;
        } else if (msec < INTERVAL_WEEK) {
            ret = TIME_DAY;
        } else if (msec < INTERVAL_MONTH) {
            ret = TIME_WEEK;
        } else if (msec < INTERVAL_QUARTER) {
            ret = TIME_MONTH;
        } else if (msec < INTERVAL_YEAR) {
            ret = TIME_QUARTER;
        } else {
            ret = TIME_YEAR;
        }
        return ret;
    }
    
    /*
     * format: <count>-<PERIOD-NAME>
     * "1-YEAR"
     * "5-DAY"
     * "2-week"
     * count is optional, and is implicitly 1 if not specified, so
     * "MONTH" is valid too, as "1-MONTH"
     */
    public static long decodeTimeInterval(String val) {
        long ret = 0;
        if (val == null) {
            return ret;
        }
        
        // string literal of a long, as #msec, is acceptable...
        // if it parses as a long, return that
        try {
            ret = Long.parseLong(val);
            return ret;
        } catch (NumberFormatException notANumber) {
            // fall through to normal parsing
        }
        long period = 0;
        int count = 1;
        String[] sa = StringUtil.split(val, '-', true);
        int cmPeriod = TimeConstants.TIME_UNKNOWN;
        
        if (sa.length > 1) {
            try {
                count = Math.max(1, Integer.parseInt(sa[0]));
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
            try {
                cmPeriod = TimeUtil.decodeTimeString(sa[1]);
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
            
        } else if (sa.length > 0) {
            try {
                cmPeriod = TimeUtil.decodeTimeString(sa[0]);
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
        }
        switch (cmPeriod) {
        case TimeConstants.TIME_MILLI:
            period = 1;
            break;
        case TimeConstants.TIME_SECOND:
            period = INTERVAL_SEC;
            break;
        case TimeConstants.TIME_MINUTE:
            period = INTERVAL_MINUTE;
            break;
        case TimeConstants.TIME_HOUR:
            period = INTERVAL_HOUR;
            break;
        case TimeConstants.TIME_DAY:
            period = INTERVAL_DAY;
            break;
        case TimeConstants.TIME_WEEK:
            period = INTERVAL_WEEK;
            break;
        case TimeConstants.TIME_MONTH:
            period = INTERVAL_MONTH;
            break;
        case TimeConstants.TIME_QUARTER:
            period = INTERVAL_QUARTER;
            break;
        case TimeConstants.TIME_YEAR:
            period = INTERVAL_YEAR;
            break;
        case TimeConstants.TIME_UNKNOWN:
        default:
            break;
        }
        //p("period=" + period + " decoded val='" + val + "'");
        if (period != 0) {
            ret = period * count;
        } else {
            throw new IllegalArgumentException(
                    "cannot parse '" + val + "' as a time period"
                    );
        }
        /*if (val != null && ret == null) {
            log.warn("could not decode DateTickUnit from '" + val + "'" );
        }*/
        return ret;
    }    
    public static String decodeTimeInt(int t) {
        String ret = null;
        switch (t) {
        case TimeConstants.TIME_UNKNOWN:
            ret = "unknown"; break;
        case TimeConstants.TIME_MILLI:
            ret = "millisecond"; break;
        case TimeConstants.TIME_SECOND:
            ret = "second"; break;
        case TimeConstants.TIME_MINUTE:
            ret = "minute"; break;
        case TimeConstants.TIME_HOUR:
            ret = "hour"; break;
        case TimeConstants.TIME_DAY:
            ret = "day"; break;
        case TimeConstants.TIME_WEEK:
            ret = "week"; break;
        case TimeConstants.TIME_MONTH:
            ret = "month"; break;
        case TimeConstants.TIME_QUARTER:
            ret = "quarter"; break;
        case TimeConstants.TIME_YEAR:
            ret = "year"; break;
        default:
            throw new IllegalArgumentException("invalid time interval: " + t);
        }
        return ret;
    }
    
}
