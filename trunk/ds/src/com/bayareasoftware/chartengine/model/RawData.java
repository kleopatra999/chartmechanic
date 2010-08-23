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
import java.util.List;

/**
 * A structure to hold Raw Data, which is data as strings
 * holds the data as an List of Strings[]'s
 * 
 * also includes the InferenceEngine's best guess at the metadata
 * which describes the string data, and which row seems to contain
 * reasonable headers (column names), and the row that where data
 * seems to start
 */
public class RawData implements Serializable {
    /**
     * First N rows of data
     */
    public List<String[]> data;
    /**
     * inference engine guesses about column names and types
     */
    public Metadata metadata;
    /**
     * where data seems to start
     */
    public int dataStartRow = -1, headerRow = -1;
    /**
     * row number where data ends
     */
    private int dataEndRow = -1;
    /**
     * The inferred type of the data source.  See DataSourceInfo.CSV_TYPE,
     * DataSourceInfo.EXCEL_TYPE, DataSourceInfo.HTML_TYPE, etc...
     */
    public String dsType;
    /**
     * track the url string we're talking to...
     */
    public String url;

    private SimpleProps props;
    public RawData() {}
    public RawData(List<String[]> data, Metadata metadata) {
        this.data = data;
        this.metadata = metadata;
    }
    
    /**
     * Proposed title of this data source, based on some external or contextual
     * data.  For example, the <title> text of an html page that contained
     * the table of these data.
     */
    private String title;
    public String getProposedTitle() {
        return title;
    }
    public void setProposedTitle(String s) {
        title = s;
    }
    /**
     * properties are used to hold other values that are specific
     * to particular datasource types.  For example, the table ID
     * of an HTML data source.  See DataSourceInfo.HTML_TABLEID
     * 
     */
    public String getProperty(String name) {
        String ret = null;
        if (props != null) {
            ret = props.get(name);
        }
        return ret;
    }
    public int getDataEndRow() { return dataEndRow; }
    public void setDataEndRow(int r) {
        dataEndRow = r;
    }
    public void setProperty(String name, String value) {
        if (props == null) { props = new SimpleProps(); }
        props.put(name, value);
    }
    
    public DataSourceInfo toDataSource() {
        DataSourceInfo ret = new DataSourceInfo(dsType);
        
        ret.setUrl(url);
        ret.setInputMetadata(metadata);
        ret.setDataStartRow(dataStartRow);
        ret.setDataEndRow(dataEndRow);
        ret.setHeaderRow(headerRow);
        if (props != null) {
            for (String name : props.keySet()) {
                ret.setProperty(name, props.get(name));
            }
        }
        // FIXME: username/password? YES,done
        // - see DataInference.fillRawData() - should propagate as properties above
        return ret;
    }
    public RawData invert() {
        RawData ret = new RawData();
        ret.data = invert(this.data);
        ret.dataStartRow = 2;
        ret.dsType = this.dsType;
        ret.headerRow = 1;
        ret.url = this.url;
        ret.props = new SimpleProps();
        if (this.props != null)
            ret.props.putAll(this.props);
        ret.props.put(DataSourceInfo.INVERT_ROW_COLUMN, "true");
        int ncols = maxColumns(ret.data);
        ret.metadata = new Metadata(ncols);
        for (int i = 1; i <= ncols; i++) {
            ret.metadata.setColumnName(i, "Col" + i);
            ret.metadata.setColumnType(i, DataType.UNKNOWN);
        }
        return ret;
    }
    private static int maxColumns(List<String[]> in) {
        int incols = 0;
        int inrows = in.size();
        for (int i = 0; i < inrows; i++) {
            incols = Math.max(incols, in.get(i).length);
        }
        return incols;
    }
    public static List<String[]> invert(List<String[]> in) {
        List<String[]> out = new ArrayList<String[]>();
        int inrows = in.size();
        int incols = maxColumns(in);
        for (int i = 0; i < incols; i++) {
            String[] outrow = new String[inrows];
            out.add(outrow);
            for (int j = 0; j < inrows; j++) {
                String[] inrow = in.get(j);
                if (i < inrow.length) {
                    outrow[j] = inrow[i];
                }
            }
        }
        return out;
    }
    /**
     * Returns a default attempt at a human-readable description of the data.
     * This is basically all the text from before the column and data area
     * of the data set, if any.  
     */
    public String getInferredDescription() {
       StringBuffer sb = new StringBuffer();
       if (title != null) {
           sb.append(title).append('\n');
       }
       int limit = Math.min(headerRow, dataStartRow);
       //System.err.println("inferring description from headerRow=" + headerRow + " dataStartRow="
       //        + dataStartRow + " limit=" + limit);
       for (int row = 0; row < limit; row++) {
           String[] cells = data.get(row);
           boolean foundData = false;
           for (int j = 0; j < cells.length; j++) {
               if (cells[j] != null && cells[j].length() > 0) {
                   if (foundData) {
                       // put ' ' between previous block of text on this line and this one
                       sb.append(' ');
                   }
                   sb.append(StringUtil.collapseWS(cells[j]));
                   foundData = true;
               }
           }
           if (foundData) {
               // if non-empty row, append a newline
               sb.append('\n');
           }
       }
       return sb.length() > 0 ? sb.toString() : null;
    }
    public String toString() {
        return "[RawData: startRow=" + dataStartRow + " headerRow=" + headerRow
        + " metadata=" + metadata + "]";
    }
}
