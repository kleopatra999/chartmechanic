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
package com.bayareasoftware.chartengine.ds.util;


import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Various utilities to handling DataStreams.
 */
public final class DataStreamUtil {

    public static void dump(PrintStream ps, DataStream ds) throws Exception {
        Metadata md = ds.getMetadata();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        printHeader(ps, md);
        while (ds.next()) {
            printRow(ps, ds, df);
        }
    }

    private static void printRow(PrintStream ps, DataStream ds, DateFormat df) throws Exception {
        int count = ds.getMetadata().getColumnCount();
        for (int i = 1; i <= count; i++) {
            Object o = ds.getObject(i);
            if (o != null && o instanceof Date && df != null) {
                o = df.format((Date)o);
            }
            ps.printf("|%s", o);
        }
        ps.println("|");
    }
    
    private static void printHeader(PrintStream ps, Metadata md) {
        for (int i = 1; i <= md.getColumnCount(); i++) {
            String name = md.getColumnName(i);
            ps.printf("|%s", name);
        }
        ps.println("|");
    }
    
    public static List<String[]> getData(DataStream ds, int maxRows) throws Exception {
        if (maxRows < 1)
            maxRows = Integer.MAX_VALUE;
        
        List<String[]> ret = new ArrayList<String[]>();
        int i = 0;
        Metadata md = ds.getMetadata();
        int count = md.getColumnCount();
        while (i < maxRows && ds.next()) {
            String[] row = new String[count];
            for (int j = 1; j <= count; j++) {
                Object obj = null;
                switch (md.getColumnType(j)) {
                case DataType.BOOLEAN:
                    obj = ds.getBoolean(j);
                    break;
                case DataType.DATE:
                    obj = ds.getDate(j);
                    break; // FIXME: format
                case DataType.DOUBLE:
                    obj = ds.getDouble(j);
                    break;
                case DataType.INTEGER:
                    obj = ds.getInt(j);
                    break;
                case DataType.STRING:
                case DataType.UNKNOWN:
                default:
                    obj = ds.getString(j);
                }
                row[j - 1] = (obj != null ? obj.toString(): null);
            }
            ret.add(row);
            i++;
        }
        //System.err.println("[DataStreamUtil] got " + ret.size() + " row(s) where max was " + maxRows);
        return ret;
    }

}


