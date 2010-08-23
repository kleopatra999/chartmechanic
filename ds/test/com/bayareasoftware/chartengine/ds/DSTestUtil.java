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
package com.bayareasoftware.chartengine.ds;

import static com.bayareasoftware.chartengine.model.DataType.BOOLEAN;
import static com.bayareasoftware.chartengine.model.DataType.DATE;
import static com.bayareasoftware.chartengine.model.DataType.DOUBLE;
import static com.bayareasoftware.chartengine.model.DataType.INTEGER;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.InferenceException;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;

public class DSTestUtil {

    // infer from the File object a DataSourceInfo  
    // this is used for TESTING only and not part of the main core logic for inferring DataSources
    public static DataSourceInfo getDSInfo(File f) throws InferenceException, IOException {
        return getDSInfo(f,DataInference.MAX_ROWS_EXAMINED);

    }

    // get RawData from a File object
    // this is used for TESTING only and not part of the main core logic for inferring DataSources
    public static RawData getRawData(File f, String dsType, int maxRows) throws InferenceException, Exception {
        String url = f.toURI().toString();
        if (dsType != null) {
            DataSourceInfo dsi = new DataSourceInfo(dsType);
            dsi.setUrl(url);
            return DataInference.get().inferFromDS(dsi,maxRows).getRawData();
        } 
        return DataInference.get().inferFromURL(url, maxRows).getRawData();
    }

    
    public static DataSourceInfo getDSInfo(File f, int maxRows) throws InferenceException, IOException {
        //RawData rd = DataInference.get().inferFromURL(url, null, null, type, DataInference.MAX_ROWS_EXAMINED);
        return DataInference.get().inferFromURL(f.toURI().toString(), maxRows).getDataSource();
    }

    public static DataSourceInfo getDSInfo(String url) throws InferenceException, IOException {
        //RawData rd = DataInference.get().inferFromURL(url, null, null, type, DataInference.MAX_ROWS_EXAMINED);
        return DataInference.get().inferFromURL(url, 100).getDataSource();
    }
    
    public static int drainStream(DataStream stream, boolean verbose) throws Exception {
        Metadata md = stream.getMetadata();
        int ret = 0;
        int cols = md.getColumnCount();
        while (stream.next()) {
            StringBuilder ps = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
                ps.append('|');
                Object obj = null;
                if (md.getColumnType(i) == DATE) {
                    Date d = stream.getDate(i);
                    if (d != null) {
                        obj = new Timestamp(d.getTime());
                    }
                } else if (md.getColumnType(i) == INTEGER) {
                    obj = stream.getInt(i);
                } else if (md.getColumnType(i) == DOUBLE) {
                    obj = stream.getDouble(i);
                } else if (md.getColumnType(i) == BOOLEAN) {
                    obj = stream.getBoolean(i);
                } else {
                    obj = stream.getString(i);
                }
                if (obj != null) {
                    ps.append(obj);
                } else {
                    ps.append("<NULL>");
                }
            }
            ps.append('|');
            if (verbose) {
                p("#" + ret + ") " + ps);
            }
            ret++;
        }
        return ret;
    }
    /** yyyy-MM-dd */
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    public static Date getDate(String s) throws ParseException {
        return fmt.parse(s);
    }
    public static int printList(List<String[]> l) {
        int i;
        StringBuilder sb = new StringBuilder();
        for (i = 0; i < l.size(); i++) {
            sb.setLength(0);
            String[] row = l.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == null) {
                    sb.append("");
                } else {
                    sb.append(row[j]);
                }
                sb.append("|");
            }
            p("#" + i + ") " + sb);
        }
        return i;
    }
    public static void allowFiles() {
        System.setProperty("chartengine.allowFileUrls", "true");
    }
    private static void p(String s) {
        System.out.println("[DSTestUtil] " + s);
    }
}
