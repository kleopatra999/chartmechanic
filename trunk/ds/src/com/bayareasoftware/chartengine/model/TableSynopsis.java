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
/**
 * 
 */
package com.bayareasoftware.chartengine.model;

import java.io.Serializable;
import java.util.List;

public class TableSynopsis implements Serializable {
    
    private List<String[]> data;
    private int index = -1;
    private String tableId;
    private int totalRows;
    private int maxColumns;
    public int getTotalRows() { return totalRows; }
    public void setTotalRows(int i) { totalRows = i; }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getTableId() {
        return tableId;
    }
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    public void setDataList(List<String[]> l, int maxColumns) {
        this.maxColumns = maxColumns;
        data = l;
    }
    public int getMaxColumns() {
        return maxColumns;
    }
    public List<String[]> getData() {
        return data;
    }
    public RawData toRawData() {
        RawData ret = new RawData();
        ret.data = data;
        ret.metadata = new Metadata(getMaxColumns());
        //MetadataUtil.setDefaultColumnNames(ret.metadata, "C");
        return ret;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[TableSynopsis: index=").append(index).append(" rows=").append(data.size());
        if (data.size() > 0) {
            sb.append(" cols=").append(maxColumns);
        }
        if (tableId != null) {
            sb.append(" ID=").append(tableId);
        }
        return sb.append("]").toString();
    }
    
}