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

import javax.script.ScriptException;

import com.bayareasoftware.chartengine.functions.Reducer;

/**
 * an aggregator takes a set of rows, a column description
 * and a reducer.
 * 
 * It runs the reducer on the rows with values from that column,
 * producing a single value
 */
public class Aggregator {

    private String columnName;
    private Reducer f;
    
    public Aggregator(String colName, Reducer f) {
        this.columnName = colName;
        this.f = f;
    }
    
    public String getColumnName() {
        return columnName;
    }

    public Object eval(DataGrid dg) throws ScriptException {
        Object ret = null;
        if (columnName != null && f != null) {
            if (dg != null) {
                int colIndex = dg.getColumn(columnName);
                if (colIndex > 0) {
                    f.init(null);
                    for (int i=0;i<dg.getRowCount();i++) {
                        f.iter(dg.value(i,colIndex));
                    }
                    f.end();
                    ret = f.value();
                }
            }
        }
        return ret;
    }
}
