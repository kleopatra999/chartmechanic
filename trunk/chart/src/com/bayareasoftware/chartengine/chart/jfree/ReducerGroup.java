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
package com.bayareasoftware.chartengine.chart.jfree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.functions.Reducer;

/**
 * a ReducerGroup is a group of Reducers
 * this group gets executed once per row per datastream
 */
@Deprecated
public class ReducerGroup {
    private Log log = LogFactory.getLog(ReducerGroup.class);
    
    private class RInfo {
        String fname;
        int colIndex;
        Reducer reducer;
    }
    
    private List<RInfo> funcs;
    
    public ReducerGroup() {
        funcs = new ArrayList<RInfo>();
    }
    
    public void addFunction(String fname, int colIndex) {
        RInfo ri = new RInfo();
        ri.fname = fname;
        ri.colIndex = colIndex;
        ri.reducer = createFunction(fname);
        funcs.add(ri);
    }
    
    public void beginSeries(DataStream r) {
        for (RInfo ri : funcs) {
            if (ri.reducer != null) {
                ri.reducer.init(null);
            }
        }
    }

    
    public void evalRow(DataStream r) throws Exception {
        for (RInfo ri : funcs) {
            if (ri.reducer != null) {
                ri.reducer.iter(r.getDouble(ri.colIndex));
            }
        }
    }
    public void endSeries() {
        for (RInfo ri : funcs) {
            if (ri.reducer != null) {
                ri.reducer.end();
            }
        }
    }
    
    public Double getEndValue(String fname, int colIndex) {
        for (RInfo ri : funcs) {
            if (ri.fname.equals(fname) && ri.colIndex == colIndex){
                return (Double) ri.reducer.value();
            }
        }
        return null;
    }
    
    private Reducer createFunction(String fname) {
        Reducer fp = null;
        Object o = BuiltInFunctions.makeFunction(fname);
        if (o != null && o instanceof Reducer) {
            fp = (Reducer)o;
        } else {
            log.error("could not created Reducer function named: " + fname);
        }
        return fp;
    }
    
}
