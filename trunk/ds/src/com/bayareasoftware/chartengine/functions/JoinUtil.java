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

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.JoinTimeStream;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.js.DataColumn;
import com.bayareasoftware.chartengine.js.DataGrid;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ISeries;

public class JoinUtil {
    public static void loadTimeMap(FuzzyTimeMap<Double> map, DataStream ds, int xcol, int ycol)
    throws Exception {
        while (ds.next()) {
            Date d = ds.getDate(xcol);
            Double val = ds.getDouble(ycol);
            if (d != null && val != null) {
                map.put(d, val);
            }
        }
    }

    public static DataGrid joinByDate(List<DataStream> streams, List<ISeries> series, long tolerance) throws Exception {
        if (streams.size() != series.size()) {
            throw new IllegalArgumentException(
                    "mismatched sizes: " + streams.size() + "," +
                    + series.size()
                    );
        }
        DataGrid ret = null;
        // first DataColumn is null
        // allocate nstreams + 1 (extra column is the date we're joining on...)
        final int N = streams.size();
        DataColumn[] cols = new DataColumn[N + 2];
        cols[1] = new DataColumn(new ColumnInfo("DATE", DataType.DATE), 1);
        FuzzyTimeMap<Double>[] ftmaps = new FuzzyTimeMap[N];
        for (int i = 0; i < streams.size(); i++) {
            DataStream ds = streams.get(i);
            ISeries se = series.get(i);
            int xcol = se.getXColumn();
            int ycol = se.getYColumn();
            ColumnInfo ci = ds.getMetadata().getColumnInfo(xcol);
            if (ci.getType() != DataType.DATE) {
                throw new IllegalArgumentException(
                        "for x column of stream " + se.getName() + " expected type DATE at index "
                        + ycol + " not " + DataType.toString(ci.getType())
                        );
            }
            ci = ds.getMetadata().getColumnInfo(ycol);
            cols[i+2] = new DataColumn(ci, i+2);
            ftmaps[i] = new FuzzyTimeMap<Double>(tolerance);
            loadTimeMap(ftmaps[i], ds, xcol, ycol);
        }
        ret = new DataGrid(cols);
        Set<Long> sl = ftmaps[0].keySet();
        {
            for (Long l : sl) {
                Date d = new Date(l);
                int r = ret.addNewRow();
                ret.setValue(r, 1, d);
                Double val1 = ftmaps[0].get(d);
                ret.setValue(r, 2, val1);
                for (int j = 1; j < ftmaps.length; j++) {
                    Double valn = ftmaps[j].get(d);
                    if (valn != null) {
                        ret.setValue(r, j+2, valn);
                    }
                }
            }
        }
        ret.sort(1, false);
        /*{ -- JoinTimeStream based implementation
            DataStream[] sa = new DataStream[streams.size()];
            streams.toArray(sa);
            int[] ia = new int[xcols.size()];
            for (int i = 0; i < xcols.size(); i++) {
                ia[i] = xcols.get(i);
            }
            JoinTimeStream jts = new JoinTimeStream(sa, ia);
            ret = new DataGrid(jts);
        }*/
        return ret;
    }
}
