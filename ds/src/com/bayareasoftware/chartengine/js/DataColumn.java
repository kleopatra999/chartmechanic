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

import com.bayareasoftware.chartengine.model.ColumnInfo;

// a DataColumn is a meta descriptor of a column
// it has back-references to its metadata and its DataGrid so we can always get back to the DataGrid if we need to 
public class DataColumn extends ColumnInfo {
    private int colIndex;
    private DataGrid data;
    public DataColumn(ColumnInfo colInfo, int colIndex) {
        this(colInfo, colIndex, null);
    }
    public DataColumn(ColumnInfo colInfo, int colIndex, DataGrid dg) {
        super(colInfo);
        this.colIndex = colIndex;
        this.data = dg;
    }
    
    public DataGrid data() {
        return this.data;
    }
    
    public int index() {
        return this.colIndex;
    }
}
