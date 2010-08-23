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


import static com.bayareasoftware.chartengine.model.DataType.*;

import com.bayareasoftware.chartengine.ds.util.MetadataUtil;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.util.DateUtil;
import com.bayareasoftware.chartengine.model.StringUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeRecognizer {
    private Metadata metadata;
    private final DateRecognizer dateRecognizer;
    //private final DataSourceInfo dsInfo;
    private RawData rd;
    public TypeRecognizer(RawData rd, DateRecognizer dr) {
        this.rd = rd;
        metadata = rd.metadata;
        dateRecognizer = dr;
    }

    public TypeRecognizer(RawData rd) {
        this(rd, new DateRecognizer());
    }

    private static int guessColumnCount(List<String[]> data, int rowCount) {
        int ret = 0;
        rowCount = Math.min(rowCount, data.size());
        for (int i = 0; i < rowCount; i++) {
            String[] sa = data.get(i);
            if (sa != null) {
                ret = Math.max(ret, sa.length);
            }
        }
        return ret;
    }
    /**
     * Try to figure out which row has all Strings, and is
     * a sensible guess for column names
     * @param data
     * @param rowCount
     */
    private void guessHeaderRow() {
        int rowCount = rd.data.size();
        
        if (rowCount == 1) {
            // in the special case where row count == 1
            // commonly the case for simple, in-place data
            rd.headerRow = -1;
            rd.dataStartRow = 0;
            return;
        }
        
        int mdCount = metadata.getColumnCount();
        
        // start guess from row 20 (or rowCount, whichever is less) and go backwards
        // this helps us from being faked out by multi-row header rows
        int startGuessing = Math.min(20,rowCount - 1);
        for (int i=startGuessing;i>=0;i--) {
        //for (int i=0; i < startGuessing; i++) {
            String[] sa = rd.data.get(i);
            if (sa.length == mdCount && isHeader(sa)) {
                rd.headerRow = i;
                for (int j = 0; j < sa.length; j++) {
                    metadata.setColumnName(j+1, StringUtil.collapseWS(sa[j]));
                }
                // at this point, guess that data starts
                // at header row + 1
                //dsInfo.setDataStartRow(i+1);
                rd.dataStartRow = i+1;
                break;
            } 
                
            if (sa.length != mdCount) {
                // if we see a row that's not the right number of columns, 
                // assume that it is the header row
                rd.headerRow = i;
                for (int j=0;j<mdCount;j++) {
                    if (j < sa.length) {
                        String s = sa[j];
                        if (StringUtil.trim(s) != null){ 
                            metadata.setColumnName(j+1,StringUtil.collapseWS(s));
                            continue;
                        }
                    }
                    metadata.setColumnName(j+1,"Column_"+(j+1));
                }
                //dsInfo.setDataStartRow(i+1);
                rd.dataStartRow = i+1;
                break;
            }
        }
    }

    public void guessColumnTypes() {
        int rowCount = rd.data.size();
        if (metadata == null) {
            int guessColCount = guessColumnCount(rd.data, rowCount);
            metadata = new Metadata(guessColCount);
            //dsInfo.setMetadata(metadata);
            rd.metadata = metadata;
            MetadataUtil.setDefaultColumnNames(metadata, "C");
        }
        int columnCount = metadata.getColumnCount();
        ColumnScore[] scores = new ColumnScore[columnCount];
        // figure out types
        for (int i = 0; i < columnCount; i++) {
            scores[i] = new ColumnScore();
            for (int j = 0; j < rowCount; j++) {
                String value = getCell(rd.data,j,i);
                scores[i].observe(j,value);
            }
            int type = scores[i].getProbableType();
            metadata.setColumnType(i+1, type);
            if (type == DataType.DATE) {
                metadata.setColumnFormat(i+1, scores[i].dateFormat);
            }
        }
        // figure out data start row and headers
        int proposedDataStart = -1;
        {
            // hold an election for the row where data starts
            // each column gets one vote
            Map<Integer,Integer> votes = new HashMap<Integer,Integer>();
            for (int i = 0; i < columnCount; i++) {
                int type = scores[i].getProbableType();
                if (type  == DataType.DATE || type == DataType.DOUBLE) {
                    int startRow = scores[i].firstDataRow;
                    Integer vote = votes.get(startRow);
                    if (vote == null) {
                        vote = 1;
                    } else {
                        vote = vote + 1;
                    }
                    votes.put(startRow, vote);
                }
            }
            int maxVotes = -1;
            for (Integer row : votes.keySet()) {
                Integer count = votes.get(row);
                if (count > maxVotes) {
                    maxVotes = count;
                    proposedDataStart = row;
                } else if (count == maxVotes) {
                    // prefer the lower row number when things are equal
                    proposedDataStart = Math.min(proposedDataStart, row);
                }
            }
            //p("vote winner: " + proposedDataStart + ", votes map: " + votes);
        }
        /*
            int proposedDataStart = rowCount;
            for (int i = 0; i < columnCount; i++) {
                if (scores[i].getProbableType() == DataType.DATE
                        || scores[i].getProbableType() == DataType.DOUBLE) {
                    proposedDataStart = Math.min(proposedDataStart, scores[i].firstDataRow);
                }
            }
         */
        if (proposedDataStart != -1) {
            rd.dataStartRow = proposedDataStart;
        } else {
            rd.dataStartRow = 1;
        }
        //rd.headerRow = Math.max(rd.dataStartRow - 1, 0);
        rd.headerRow = rd.dataStartRow - 1;
        // fill in column names
        {
            for (int i = 0; i < columnCount; i++) {
                //p(" colscore[" + i + "]=" + scores[i]);
                for (int j = rd.headerRow; j >= 0; j--) {
                    String value = this.getCell(rd.data, j, i);
                    if (value != null) {
                        
                        metadata.setColumnName(i+1, StringUtil.collapseWS(value));
                        break;
                    }
                }
            }
        }
        //p("rowCount=" + rowCount + " dataStart=" + rd.dataStartRow + " headers=" +rd.headerRow
         //+ " metadata=" + rd.metadata);
    }

    /*
     * Returns true if the specified row looks like a CSV header (every
     * column is a string.
     */
    private static boolean isHeader(String[] row) {
        for (int i = 0; i < row.length; i++) {
            int guess = guessType(row[i]);
            if (guess != STRING) {
                return false;
            }
        }
        return true;
    }
    
    
    private static int guessType(String s) {
        if (s == null)
            return STRING;
        /*
        try {
            Integer.parseInt(s);
            return INTEGER;
        } catch (NumberFormatException e) {
            // Fall through
        }
        */
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            // Fall through
        }
        if (looksLikeCurrency(s)) {
            return DOUBLE;
        }
        if (looksLikePercent(s)) {
            return DOUBLE;
        }
        return STRING;
    }
    private static final double SENSIBLE_YEAR_MIN = 1800d;
    private static final double SENSIBLE_YEAR_MAX = 2100d;
    // return matching date format
    private String looksLikeDate(String s) {
        String ret = null;
        if (s != null) {
            dateRecognizer.reset();
            dateRecognizer.parse(s);
            if (!dateRecognizer.failed()) {
                boolean sensibleDate = true;
                /* make sure we're not looking at number that matches
                 * something like 'yyyy'
                 */
                try {
                    double val = Double.parseDouble(s);
                    if (val < SENSIBLE_YEAR_MIN || val > SENSIBLE_YEAR_MAX) {
                        //p("NOT A SENSIBLE YEAR: " + val);
                        sensibleDate = false;
                    }
                } catch (NumberFormatException ignore) {
                    // is a date
                }
                if (sensibleDate) {
                    ret = DateUtil.getDatePattern(dateRecognizer.getSimpleDateFormat());
                }
            }
        }
        return ret;
    }
    private boolean looksLikeNumber(String s) {
        boolean ret = false;
        if (s != null) {
            s = stripOuterParens(s);
            try {
                Double.parseDouble(s);
                ret = true;
            } catch (NumberFormatException ignore) { }
            if (!ret) {
                if (looksLikeCurrency(s) || looksLikePercent(s)) {
                    ret = true;
                }
            }
        }
        return ret;
    }
    /*
     * common formatting for negative numbers '($1,234)'
     */
    private static String stripOuterParens(String s) {
        String ret = s;
        if (s != null) {
            int len = s.length();
            if (len > 2 && s.charAt(0) == '(' && s.charAt(len-1) == ')') {
                ret = s.substring(1, len - 1);
                //System.err.println("*** stripOuterParens('" + s + "')->'" + ret + "'");
            }
        }
        return ret;
    }
    private static boolean looksLikePercent(String s) {
        boolean ret = false;
        s = s.trim();
        int len = s.length();
        if (len > 0 && s.charAt(len-1)=='%') {
            try {
                Double.parseDouble(s.substring(0,len-1));
                return true;
            } catch (NumberFormatException e) {
                // fall through
            }
        }
        return false;
    }
    
    private static final char[] CURRENCIES = {
        '$', 8364 /*euro*/, 165 /*yen*/, 163 /*pound*/, 8360 /*rupee*/,
        8369 // peso <-- not represented in any of our fonts! but still parse it
    };    
    public static boolean isCurrencyChar(char c) {
        for (int i = 0; i < CURRENCIES.length; i++) {
            if (c == CURRENCIES[i]) {
                return true;
            }
        }
        return false;
    }
    public static boolean looksLikeCurrency(String s) {
        boolean ret = true;
        s = s.trim();
        int len = s.length();
        boolean sawDigit = false;
        int hyphenCount = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                sawDigit = true;
                continue;
            } else if (isCurrencyChar(c)) {
                // FIXME: handle multi-char currencies
                // e.g., "USD" "GBP"
                
                // handle "$8" and "-$8" and "$-8"
                if (i > 1) {
                    ret = false;
                    break;
                }
            } else if (c == '-') {
                if (sawDigit || ++hyphenCount > 1) {
                    ret = false;
                    break;
                }
            } else if (c == ',' || c == ' ' || c == '.') {
                continue;
            } else {
                ret = false;
                break;
            }
        }
        return ret && sawDigit;
    }
    private class ColumnScore implements Serializable {
        int observations = 0;
        int nulls = 0, dates = 0, numbers = 0, strings = 0;
        // first not-null row number
        private int firstNonNullRow = -1;
        // first row that can be either date/number (i.e., data)
        private int firstDataRow = -1;
        private String dateFormat = null;
        public void observe(int row, String value) {
            observations++;
            if (value == null) {
                nulls++;
                return;
            }
            if (firstNonNullRow == -1) {
                firstNonNullRow = row;
            }
            String dfmt = looksLikeDate(value);
            if (dateFormat == null) {
                // record matching date format
                dateFormat = dfmt;
            }
            boolean isDate = dfmt != null;
            boolean isNumber = looksLikeNumber(value);
            if ((isDate || isNumber) && firstDataRow == -1) {
                firstDataRow = row;
            }
            if (isDate) {
                dates++;
            }
            if (isNumber) {
                numbers++;
            }
            if (!isDate && !isNumber) {
                strings++;
            }
        }
        public String toString() {
            String ret = "[ColScore: type=" + DataType.toString(getProbableType())
            + " count=" + observations + " nulls=" + nulls + " nums=" + numbers
            + " dates=" + dates + " strings=" + strings + " firstData=" + this.firstDataRow
            + " firstAnything=" + this.firstNonNullRow;
            if (true || getProbableType() == DataType.DATE) {
                ret += (" dfmt='" + this.dateFormat + "'");
            }
            return ret + "]";
        }
        public int getProbableType() {
            int ret = DataType.UNKNOWN;
            if (dates > 0 && dates >= numbers && dates >= strings) {
                ret = DataType.DATE;
            } else if (numbers > 0 && numbers >= dates && numbers >= strings) {
                ret = DataType.DOUBLE;
            } else if (strings > 0) {
                ret = DataType.STRING;
            }
            
            return ret;
        }
    }
    /*
     * get trimmed string, or null if out of bounds 
     */
    private static String getCell(List<String[]> data, int row, int col) {
        String ret = null;
        if (row < data.size()) {
            String[] sa = data.get(row);
            if (col < sa.length) {
                ret = sa[col];
            }
        }
        return trim(ret);
    }
    private static String trim(String s) {
        if (s != null && (s = s.trim()).length() == 0) {
            s = null;
        }
        return s;
    }
    private static void p(String s) {
        System.err.println("[TypeRecognizer] " + s);
    }
}
