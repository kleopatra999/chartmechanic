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

import java.io.Serializable;

public class ColumnInfo implements Serializable {
    public static final String CLASS_ALIAS = "column";
    
    private String name, description;
    private int type;
    private String format;

    public ColumnInfo() {}
    
    public ColumnInfo(String name, int type) {
        this.name = name;
        this.type = type;
    }
    
    public ColumnInfo(ColumnInfo ci) {
        this.name = ci.name;
        this.description = ci.description;
        this.type = ci.type;
        this.format = ci.format;
    }
    
    public String getName() {return name; }
    public int getType() { return type; }
    public String getFormat() { return format; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setType(int type) { this.type = type; }
    public void setFormat(String s) { this.format = s; }
    public void setDescription(String s) { description = s; }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ColumnInfo)) return false;
        ColumnInfo ci = (ColumnInfo) obj;
        if (name == null) return ci.name == null;
        if (format == null) return ci.format == null;
        return name.equals(ci.name) && type == ci.type &&
               format.equals(ci.format);
    }
    
    public int hashCode() {
        int hc = type;
        if (name != null) hc ^= name.hashCode();
        if (format != null) hc ^= format.hashCode();
        if (description != null) hc ^= description.hashCode();
        return hc;
    }

    
    // note that toString()/fromString() and encodeColumns() and decodeColumns() work together
    // and should be kept consistent

    // separator for fields when converting to/from string
    private static char FIELD_DELIMITER = '|';
    // separator for individual array elements when converting to/from string
    private static char ELEMENT_DELIMITER = ',';
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(FIELD_DELIMITER).append(DataType.toString(type));
        if (format != null) {
            String s = format;
            if (s.indexOf(FIELD_DELIMITER) != -1 || s.indexOf(ELEMENT_DELIMITER) != -1) {
                // if the format field has the delimiters in it, then we need to quote it.
                s = SimpleProps.quoteString(s);
            }
            sb.append(FIELD_DELIMITER).append(s);
        }
        return sb.toString();
    }
    
    public static ColumnInfo fromString(String in) {
        if (in == null)
            return null;
        String[] s = SimpleProps.splitFull(in, FIELD_DELIMITER);
        if (s.length < 2) {
            throw new IllegalArgumentException(
                    "String '" + in + "' only has len=" + s.length + " when splitting on '"
                    + FIELD_DELIMITER + "'"
                    );
        }
        ColumnInfo col = new ColumnInfo();
        col.setName(s[0]);
        col.setType(DataType.parse(s[1]));
        if (s.length > 2) {
            col.setFormat(SimpleProps.unquoteString(s[2]));
        }
        return col;
    }
    
    /**
     * encode columns as a string where comma-separated string of columns where each
     * column is its string representation <name>|<type>|<format>
     * e.g.   date_|DATE|yyyy-MM-dd,open_|DOUBLE,high|DOUBLE,low|DOUBLE,close_|DOUBLE,volume|INTEGER,adjclose|DOUBLE
     */
    public static String encodeColumns(ColumnInfo[] cols) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (ColumnInfo col : cols) {
            if (!first) {
                sb.append(ELEMENT_DELIMITER);
            } else 
                first = false;
            sb.append(col.toString());
        }
        return sb.toString();
    }
    
    public static ColumnInfo[] decodeColumns(String in) {
        ColumnInfo[] cols = null;
        String[] sa = SimpleProps.splitFull(in, ELEMENT_DELIMITER);
        if (sa != null) {
            cols = new ColumnInfo[sa.length];
            for (int i = 0; i < sa.length; i++) {
                cols[i] = fromString(sa[i]);
            }
        }
        return cols;
    }
}
