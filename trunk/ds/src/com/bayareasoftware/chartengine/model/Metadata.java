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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bayareasoftware.chartengine.model.event.PropChangeSource;
import com.bayareasoftware.chartengine.util.Util;

/**
 * Tabular data metadata.
 */
public class Metadata
    extends PropChangeSource implements PropertySerializable
{
    public static final String CLASS_ALIAS = "metadata";
   
   // on changes to any columninfo value, we fire the PROP_COLUMNS event
    public static final String PROP_COLUMNS = "cols";
    
    private ColumnInfo[] columns;

    public Metadata() { columns = new ColumnInfo[0]; }

    public Metadata(int columnCount) {
        columns = new ColumnInfo[columnCount];
        for (int i = 0; i < columns.length; i++) {
            ColumnInfo ci = new ColumnInfo();
            columns[i] = ci;
        }
    }

    /**
     * create a new Metadata from another Metadata, copying columninfo values
     */
    public Metadata(Metadata m) {
        ColumnInfo[] cols = m.columns;
        this.columns = new ColumnInfo[cols.length];
        for (int i=0;i<columns.length;i++) {
            ColumnInfo ci = new ColumnInfo();
            ci.setName(cols[i].getName());
            ci.setFormat(cols[i].getFormat());
            ci.setType(cols[i].getType());
            columns[i] = ci;
        }
    }
    
    public ColumnInfo getColumnInfo(int index) {
        return get(index);
    }
    
    public void addColumnInfo(ColumnInfo colInfo) {
        ColumnInfo[] newColumns = new ColumnInfo[columns.length+1];
        for (int i=0;i<columns.length;i++) {
            newColumns[i]=columns[i];
        }
        ColumnInfo c = new ColumnInfo();
        c.setName(colInfo.getName());
        c.setType(colInfo.getType());
        c.setDescription(colInfo.getDescription());
        c.setFormat(colInfo.getFormat());
        
        newColumns[columns.length] = c;
        columns = newColumns;
        fireChange(PROP_COLUMNS,null,columns);
    }
    
    public void setColumnInfo(int index, ColumnInfo colInfo) {
        ColumnInfo c= get(index);
        c.setName(colInfo.getName());
        c.setType(colInfo.getType());
        c.setDescription(colInfo.getDescription());
        c.setFormat(colInfo.getFormat());
        fireChange(PROP_COLUMNS,null,columns);
    }
    
    public void setColumnName(int index, String name) {
        get(index).setName(name);
        fireChange(PROP_COLUMNS,null,columns);
    }

    public void setColumnType(int index, int type) {
        get(index).setType(type);
        fireChange(PROP_COLUMNS,null,columns);
    }
    
    // internally, the preferred string representation of a Date is as its millisecond long values
    public final static String INTERNAL_DATE_FORMAT="long";

    public void setColumnFormat(int index, String format) {
        get(index).setFormat(format);
        fireChange(PROP_COLUMNS,null,columns);
    }
    
    public void setColumnDescription(int index, String desc) {
        get(index).setDescription(desc);
        fireChange(PROP_COLUMNS,null,columns);
    }
    public int getColumnCount() { return columns.length; }
    
    public String getColumnName(int index) { return get(index).getName(); }

    /**
     * return all column names 
     * @return
     */
    public List<String> getAllColumnNames() {
        List<String> res = new ArrayList<String>();
        for (ColumnInfo col : columns) {
            res.add(col.getName());
        }
        return res;
    }

    /**
     * return names of only numeric columns
     * @return
     */
    public List<String> getAllNumericColumnNames() {
        List<String> res = new ArrayList<String>();
        for (ColumnInfo col : columns) {
            if ( DataType.isNumeric(col.getType())) 
                res.add(col.getName());
        }
        return res;
    }
    
    /**
     * return the indices (1-based) of numeric columns
     * @return
     */
    public List<Integer> getAllNumericColumnIndices() {
        List<Integer> res = new ArrayList<Integer>();
        for (int i=0;i<columns.length;i++) {
            ColumnInfo col = columns[i];
            if ( DataType.isNumeric(col.getType())) 
                res.add(new Integer(i+1));
        }
        return res;
    }

    /**
     * return names of only date columns
     * @return
     */
    public List<String> getAllDateColumnNames() {
        List<String> res = new ArrayList<String>();
        for (ColumnInfo col : columns) {
            if ( col.getType() == DataType.DATE )
                res.add(col.getName());
        }
        return res;
    }

    /**
     * return the indices (1-based) of date columns
     * @return
     */
    public List<Integer> getAllDateColumnIndices() {
        List<Integer> res = new ArrayList<Integer>();
        for (int i=0;i<columns.length;i++) {
            ColumnInfo col = columns[i];
            if (col.getType() == DataType.DATE ) 
                res.add(new Integer(i+1));
        }
        return res;
    }

    /**
     * return the indices (1-based) of text columns
     * @return
     */
    public List<Integer> getAllTextColumnIndices() {
        List<Integer> res = new ArrayList<Integer>();
        for (int i=0;i<columns.length;i++) {
            ColumnInfo col = columns[i];
            if (col.getType() == DataType.STRING)
                res.add(new Integer(i+1));
        }
        return res;
    }

    
    public int getColumnType(int index) { return get(index).getType(); }

    public String getColumnFormat(int index) { return get(index).getFormat(); }

    public String getColumnDescription(int index) {
        return get(index).getDescription();
    }

    private ColumnInfo get(int index) {
        if (index < 1 || index > columns.length) {
            throw new IndexOutOfBoundsException(
                "Column index: " + index + " is outside of the range [1,"+columns.length+"]");
        }
        return columns[index - 1];
    }
    
    // munge the column names so that every name is unique and a legal DB column name
    public void mungeColumnNames(boolean excludeSQL) {
        // hashmap of (String)name -> (Integer)count of names
        // in this way, if we see the same name we saw before, we incr the count and use the count
        // to distinguish the names
        HashMap<String,Integer> name_counts = new HashMap<String,Integer>();
        for (int i=0;i<columns.length;i++) {
          ColumnInfo col = columns[i];
          String baseName = col.getName();
          if (baseName == null) {
              baseName = "C" + i;
          }
          String colName;
          if (excludeSQL) {
              colName = mungeToSQL(baseName.toLowerCase()); // treat names with case insensitivity
          } else {
              colName = mungeColumn(baseName);
          }
          Integer count = (name_counts.get(colName));
          if ( count == null) {
              col.setName(colName);
              name_counts.put(colName,new Integer(0));
          } else {
              count = new Integer(count.intValue() + 1);
              col.setName(colName+count.toString());
              name_counts.put(colName, count);
          }
        }
    }

    private static boolean isWhitespace(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t'; 
    }
    // remove ',' '|', and collapse whitespace
    public static String mungeColumn(String s) {
        StringBuffer sb = new StringBuffer();
        int len = s.length();
        boolean inWS = false;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (isWhitespace(c)) {
                if (sb.length() == 0) {
                    // leading WS
                    continue;
                } else if (!inWS) {
                    inWS = true;
                }
            } else if (c != ',' && c != '|') {
                if (inWS) {
                    sb.append(' ');
                    inWS = false;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }
    // given a string, munge it into a legal column name
    private static String mungeToSQL(String s) {
        StringBuilder res = new StringBuilder();
        // attempt to make a name out of only the letters and digits, plus underbar '_'
        for (int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c) || c == '_')
                res.append(c);
            else if (c == '.')
                res.append('_');
        }
        if (SQLKeywords.isKeyword(res.toString())) {
            res.append("_");
        }
        if (res.length() > 0) {
            if (!Character.isLetter(res.charAt(0))) {
                // want to ensure that the munged name starts alphabetic
                res.insert(0,"Col_");
            }
        } else {
            res.append("Column_");
        }
        return res.toString();
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Metadata)) return false;
        Metadata md = (Metadata) obj;
//        if (!super.equals(obj))
//            return false;
        if (columns.length != md.columns.length) return false;
        for (int i = 0; i < columns.length; i++) {
            if (!columns[i].equals(md.columns[i])) return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = super.hashCode();
        for (int i = 0; i < columns.length; i++) {
            hash = hash ^ columns[i].hashCode();
        }
        return hash;
    }

    public String toString() {
        SimpleProps p = serializeToProps(null,null);
        return p.toString();
    }
    


    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    //@Override
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        // metadata do not have id's anymore as they are not external references to entities.
        // don't serialize out the transient id field
        //this.id = null;
        //SimpleProps sp = super.serializeToProps(p, prefix);
        // we serialize the entire columninfo[] into one string
        if (p == null)
            p = new SimpleProps();
        
        p.set(prefix,PROP_COLUMNS,ColumnInfo.encodeColumns(this.columns));
        return p;
    }

    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    //@Override
    public boolean deserializeFromProps(SimpleProps props, String prefix) {
        //boolean ret = super.deserializeFromProps(p, prefix);
        boolean ret = false;
        try {
            String key = SimpleProps.prefixIt(prefix,PROP_COLUMNS);
            String s = props.get(key);
            if (s != null) {
                this.columns = ColumnInfo.decodeColumns(s);
                props.remove(key);
                ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
}
