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

import java.util.Date;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.js.DataColumn;
import com.bayareasoftware.chartengine.js.DataGrid.Row;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

public class DataGridStream extends DataStream {

    DataGrid grid;
    int rowCount = -1;
    
    DataGridStream(DataGrid grid) {
        super(true);
        this.grid = grid;
        //this.metadata = grid.getMetadata();
       // DataColumn[] columns = grid.getColumns();
        int colCount = grid.getColumnCount();
        this.metadata = new Metadata(colCount);
        for (int i=1;i<= colCount;i++) {
            DataColumn col = grid.col(i);
            metadata.setColumnDescription(i,col.getDescription());
            metadata.setColumnType(i,col.getType());
            if (col.getType() == DataType.DATE) {
                // for any Date types, we always use the 'long' string representation for DataGridStream
                //metadata.setColumnFormat(i, col.getFormat());
                metadata.setColumnFormat(i, Metadata.INTERNAL_DATE_FORMAT);
            }
            metadata.setColumnName(i, col.getName());
        }
//        this.metadata = new Metadata(grid.getColumns());
    }
    
    @Override
    public void reset() {
        rowCount = -1;
    }
    
    @Override
    public Boolean getBoolean(int index) throws Exception {
        Boolean ret = null;
        String s = getString(index);
        if (s != null) {
            ret = this.parseBoolean(s, rowCount, index);
        }
        return ret;
    }

    @Override
    public Date getDate(int index) throws Exception {
        Date d = null;
        if (!eos()) {
            Row r= grid.row(rowCount);
            if (r != null) {
                d = r.date(index);
            }
        }
        return d;
    }

    @Override
    public Double getDouble(int index) throws Exception {
        Double d = null;
        if (!eos() && !grid.isNull(rowCount, index)) {
            Row r= grid.row(rowCount);
            if (r != null && !r.isNull(index)) {
                d = r.num(index);
            }
        }
        return d;
    }

    @Override
    public Integer getInt(int index) throws Exception {
        Double d = getDouble(index);
        return d != null ? d.intValue(): null;
    }

    @Override
    public String getString(int index) throws Exception {
        String s = null;
        if (!eos()) {
            Row r = grid.row(rowCount);
            if (r != null) {
                s = r.string(index);
            }
        }
        return s;
    }

    @Override
    public boolean nextInternal() throws Exception {
        rowCount++;
        if (eos()) {
            return false;
        }
        return true;
    }
    
    private boolean eos() { return rowCount >= grid.getRowCount(); }
}
