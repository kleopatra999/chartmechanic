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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.util.DateUtil;

/**
 * Base class for data stream types based on string data.
 */
public class StringDataStream extends DataStream {
    protected DateFormat[] dateFormats;
    protected final List<String[]> data;
    protected int count = -1;
    protected int endDataRow = -1;
    protected int firstDataRow;
    
    public StringDataStream(List<String[]> sdata, Metadata md, int firstDataRow, int endDataRow) {
        super(true);
        
        setMetadata(md != null ? md : getMetadata(sdata));
        this.firstDataRow = firstDataRow;
        this.endDataRow = endDataRow;
        resetCount();
        if (sdata != null) {
            data = sdata;
        } else {
            data = new ArrayList<String[]>();
        }
    }
    
    private static Metadata getMetadata(List<String[]> data) {
        int count = 0;
        for (String[] row : data) {
            if (row.length > count) count = row.length;
        }
        return new Metadata(count);
    }

    @Override
    public boolean nextInternal() {
        if (count >= data.size() - 1) return false;
        if ( endDataRow != -1 && count == endDataRow)
            return false;
        count++;
        return true;
    }

    @Override
    public String getString(int index) {
        if (count < 0 || count >= data.size()) {
            throw new IllegalStateException("No current data row");
        }
        String[] row = data.get(count);
        int i = index - 1;
        if (i < 0 || i >= row.length) {
            //throw new IndexOutOfBoundsException("Column does not exist");
            return null;
        }
        return (row[i] != null && row[i].length() > 0) ? row[i] : null;
    }
    
    /**
     * reset this stream to the head
     */
    @Override
    public void reset() {
        resetCount();
    }
    
    private void resetCount() {
        count = firstDataRow - 1;
        if (count < -1) { count = -1; }
    }
    
    @Override
    public Integer getInt(int index) throws Exception {
        String s = removeNonDigit(getCheckedString(index));
        return s != null ?  this.parseInt(s, -1, index) : null;
    }

    @Override
    public Double getDouble(int index) throws Exception {
    	String s = getCheckedString(index);
        //System.err.println("parsing '" + s + "' as double");
        s = removeNonFloat(getCheckedString(index));
        return s != null ? this.parseDouble(s, -1, index) : null;
    }

    private static String removeNonDigit(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                ret.append(c);
            } else if (ret.length() == 0 && c == '-') {
                // keep minus sign iff beginning...
                ret.append(c);
            } else if (Character.isLetter(c)) {
                // call this one unparse-able as a number
                return null;
            }
        }
        return ret.length() > 0 ? ret.toString(): null;
    }

    private static String removeNonFloat(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        boolean sawExponent = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                ret.append(c);
            } else if (ret.length() == 0 && c == '-') {
                // keep minus sign iff beginning...
                ret.append(c);
            } else if (ret.length() > 0 && (c == 'E' || c == 'e') && !sawExponent) {
                ret.append(c);
                sawExponent = true;
            } else if (Character.isLetter(c)) {
                // call this one unparse-able as a number
                // we'll string non-letter chars, but the
                // presence of letters indicates that this is
                // likely not a reliable number
                //return null;
            	break;
            }
        }
        return ret.length() > 0 ? ret.toString(): null;
    }    
    @Override
    public Boolean getBoolean(int index) throws Exception {
        String s = getCheckedString(index);
        return s != null ? this.parseBoolean(s, -1, index) : null;
    }
    
    @Override
    public Date getDate(int index) throws Exception {
        String s = getCheckedString(index);
        DateFormat sdf = getDateFormat(index);
        if (s != null) {
            return this.parseDate(s, sdf, -1, index);
        } else {
            return null;
        }
    }
    
    private String getCheckedString(int index) {
        return trim(getString(index));
    }
    
    private static String trim(String s) {
        if (s != null) {
            s = s.trim();
            if (s.length() == 0) {
                s = null;
            }
        }
        return s;
    }

    private DateFormat getDateFormat(int index) {
        if (metadata == null) return null;
        ColumnInfo ci = metadata.getColumnInfo(index);
        String fmt = ci.getFormat();
        if (ci.getType() != DataType.DATE || fmt == null) return null;
        if (dateFormats == null) {
            dateFormats = new DateFormat[metadata.getColumnCount()];
        }
        DateFormat sdf = dateFormats[index - 1];
        if (sdf == null) {
            //   dateFormats[index - 1] = sdf = new SimpleDateFormat(fmt);
            dateFormats[index - 1] = sdf = DateUtil.createDateFormat(fmt);
        }
        return sdf;
    }
}
