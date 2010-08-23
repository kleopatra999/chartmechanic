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
package com.bayareasoftware.chartengine.js;

import java.util.Calendar;
import java.util.Date;

public class FuzzyDateComparator {
    private int interval;
    private int tolerance;
    
    private final static int NONE = 0;
    private final static int WEEK = 1;
    private final static int MONTH = 2;
    private final static int QUARTER = 3;
    private final static int YEAR = 4;
    
    
    private FuzzyDateComparator(int interval, int tolerance){
        this.interval = interval;
        this.tolerance = tolerance;
    }
    
    /**
     * get a fuzzydate comparator, returns null if interval or tolerance is illegal
     * 
     * @param interval       how far apart the dates should be  (one of WEEK, MONTH, QUARTER, YEAR)
     * @param tolerance      tolerance in days
     * @return
     */
    public static FuzzyDateComparator get(String interval, int tolerance) {
        // check the interval strings
        int i = NONE;
        if (interval != null) {
            if (interval.equalsIgnoreCase("WEEK"))
                i = WEEK;
            else if (interval.equalsIgnoreCase("MONTH"))
                i = MONTH;
            else if (interval.equalsIgnoreCase("QUARTER"))
                i = QUARTER;
            else if (interval.equalsIgnoreCase("YEAR"))
                i = YEAR;
        }
        if (i == NONE || tolerance < 0.0)
            return null;
        return new FuzzyDateComparator(i,tolerance);
    }
    
    
    /**
     * compare to see if two dates are within the specified interval and tolerance
     * @param d1      - the earlier date
     * @param d2      - the later date
     * @returns   0 if the dates are dead-on, else a positive value indicating the dates are within the interval
     *              the smaller the value, the closer they were to being dead-on
     *            returns -1, if they are out of the interval
     */
    public int compare(Date d1, Date d2) {
        int ret = -1;
        
        // d2 must be the later date
        if (d2.compareTo(d1) < 0) {
            return -1;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        
        int yearsdiff = year2 - year1;
        
        int doy1 = c1.get(Calendar.DAY_OF_YEAR);
        int doy2 = c2.get(Calendar.DAY_OF_YEAR);
        
        int doydiff = doy2 - doy1;
        
        int dom1 = c1.get(Calendar.DAY_OF_MONTH);
        int dom2 = c2.get(Calendar.DAY_OF_MONTH);
        
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        
        int monthdiff = month2 - month1;
        
        int diff = 0;

        switch (interval) {
        case WEEK:
            if (yearsdiff > 1) {
                return -1;
            }
            if (yearsdiff == 1) {
                doydiff += 365;
            }

            diff = Math.abs(7 - doydiff);
            if (diff <= tolerance) {
                return diff;
            }
            break;
        case MONTH:
            if (yearsdiff > 1) { 
                return -1;
            }
            if (yearsdiff == 1) {
                monthdiff += 12;
                doydiff += 365;
            }
            if (monthdiff == 1 && (dom2 - dom1) == 0) {
                // exactly one month apart and the same day of month
                return 0;
            }
            int daysThisMonth = c1.getActualMaximum(Calendar.DAY_OF_MONTH);
            diff = Math.abs(daysThisMonth - doydiff);
            if (diff <= tolerance) {
                return diff;
            }
            break;
        case QUARTER:
            if (yearsdiff > 1) { 
                return -1;
            }
            if (yearsdiff == 1) {
                monthdiff += 12;
                doydiff += 365;
            }
            if (monthdiff == 3 && (dom2 - dom1) == 0) {
                // exactly three months apart and the same day of month
                return 0;
            }
            diff = Math.abs(90 - doydiff);
            if (diff <= tolerance) {
                return diff;
            }
            break;
        case YEAR:
            if (yearsdiff == 1 && doydiff == 0) {
                // exactly one year apart and the same day of the year
                return 0;
            } 
            if (yearsdiff == 1) {
                doydiff += 365;
            }
            if (year2 - year1 == 2) {
                // it is possible that we have a date like 12/31/1999, and 1/1/2001 that should qualify as a year apart within tolerance
                doydiff += 365 + 365;
            }
            diff = Math.abs(365 - doydiff);
            if (diff <= tolerance) {
                return diff;
            }
            break;
        }
        return ret;
    }
}
