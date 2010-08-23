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

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class QuaterDateFormat extends DateFormat {

    public QuaterDateFormat() {
        calendar = new GregorianCalendar();
    }
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        // January is 0
        int month = calendar.get(Calendar.MONTH);
        int q = month / 3 + 1;
        return toAppendTo.append(year).append("-Q").append(q);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date ret = null;
        if (source.length() >= 6) {
            int ind = source.indexOf('Q');
            int ind2 = source.indexOf('q');
            if (ind == -1 && ind2 == -1) {
                ind = source.indexOf(':');
            }
            ind = Math.max(ind, ind2);
            if (ind < 4) {
                return null;
            }
            String in = digits(source.substring(0, ind));
            
            int year;
            try {
                year = Integer.parseInt(in);
            } catch (NumberFormatException nfe) {
                return null;
            }
            in = digits(source.substring(ind + 1));
            if (in.length() != 1) {
                return null;
            }
            //System.out.println("got year=" + year + " parsing quarter: '" + in + "'");
            int q;
            try {
                q = Integer.parseInt(in);
            } catch (NumberFormatException nfe) {
                return null;
            }
            // January is 0...position date at end of quater: m=2|5|8|11
            // date will be one of 03/31 06/30 09/30 12/31
            int month = (q - 1) * 3 + 2;
            calendar.clear();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, date);
            ret = calendar.getTime();
            // System.out.println("returning '" + ret + "'");
            pos.setIndex(6); // must advance this
        }
        return ret;
    }
    private static String digits(String in) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (Character.isDigit(c)) { ret.append(c); }
        }
        return ret.toString();
    }
    
}
