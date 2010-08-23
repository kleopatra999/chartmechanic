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
package com.bayareasoftware.chartengine.functions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.util.DataStreamUtil;
import com.bayareasoftware.chartengine.js.DataGrid;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.TimeUtil;

public class JoinTime implements Joiner {

    private static final int OP_ADD = 0;
    private static final int OP_SUB = 1;
    private static final int OP_MULT = 2;
    private static final int OP_DIV = 3;
    // operands
    ISeries left, right;
    int op;
    // fuzzy time map tolerance
    long tolerance;
    DataGrid grid;
    public void init(ISeries series, Map<Integer,DataStream> streamMap, List<Arg> args) {
        if (args.size() != 4) {
            throw new IllegalArgumentException(
                    "Wrong number of arguments: expect 4 not " + args.size()
                    );
        }
        left = args.get(0).asSeries();
        right = args.get(2).asSeries();
        String opstr = args.get(1).getArgValue();
        {
            String s = opstr;
            // FIXME: enum constants for these somewhere
            if ("SUBTRACT".equalsIgnoreCase(s)) {
                op = OP_SUB;
            } else if ("MULTIPLY".equals(s)) {
                op = OP_MULT;
            } else if ("DIVIDE".equals(s)) {
                op = OP_DIV;
            } else {
                op = OP_ADD;
            }
        }
        tolerance = args.get(3).asLong();
        // make sure columns are right on output
        series.setXColumn(1);
        series.setYColumn(4);
        try {
            calc(streamMap, opstr);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("error evaluationg time series arithmetic", e);
        }
    }

    private void calc(Map<Integer,DataStream> map, String opstr) throws Exception {
        List<DataStream> streamList = new ArrayList<DataStream>();
        streamList.add(map.get(left.getSid()));
        streamList.add(map.get(right.getSid()));
        List<ISeries> sl = new ArrayList<ISeries>();
        sl.add(left);
        sl.add(right);
        grid = JoinUtil.joinByDate(streamList, sl, tolerance);
        grid.addCol("calculated", "NUMBER");        
        for (int i = 0; i < grid.getRowCount(); i++) {
            Double v1 = grid.getDouble(i, 2);
            Double v2 = grid.getDouble(i, 3);
            if (v2 != null && v1 != null && v1 != 0.0D) {
                Double val = null;
                switch (op) {
                case OP_SUB:
                    val = v1 - v2; break;
                case OP_MULT:
                    val = v1 * v2; break;
                case OP_DIV:
                    if (v2 != 0d) {
                        val = v1 / v2; 
                    }
                    break;
                case OP_ADD:
                default:
                    val = v1 + v2;
                    break;
                }
                //p("at " + grid.date(i, 1) + " " + v1 + " " + opstr + " " + v2 + "=" + val);
                grid.setNum(i, 4, val);
            } else {
                grid.setValue(i, 4, null);
            }
        }        
    }
    
    public DataStream value() {
        return grid.toStream();
    }


    public static void p(String s) {
        System.out.println("[TimeRatio] " + s);
    }
}
