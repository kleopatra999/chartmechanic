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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.StringDataStream;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.Metadata;

public class TimeChange implements Mapper {
    private int xCol, yCol;
    private List<String[]> data;
    private Metadata inputMeta;
    private boolean asPercent = false;
    private long window; // msec to delta between samples
    private FuzzyTimeMap<Double> dateMap;
    
    //@Override
    public DataStream begin(DataStream d) {
        data = new ArrayList<String[]>();
        inputMeta = d.getMetadata();
        if (inputMeta == null) {
            throw new NullPointerException("input metadata is null for data stream");
        } else if (inputMeta.getColumnCount() < 2) {
            throw new IllegalArgumentException(
                    "not enough columns for inflation adjustment: "
                    + inputMeta.getColumnCount()
                    );
        }
        int colCount = inputMeta.getColumnCount();
        if (xCol < 1 || xCol > colCount) {
            throw new IllegalArgumentException(
                    "x column out of range: " + xCol + "/" + colCount
                    );
        }
        if (yCol < 1 || yCol > colCount) {
            throw new IllegalArgumentException(
                    "y column out of range: " + yCol + "/" + colCount
                    );
        }
        int xtype = inputMeta.getColumnType(xCol);
        int ytype = inputMeta.getColumnType(yCol);
        if (xtype != DataType.DATE) {
            throw new IllegalArgumentException(
                    "expect DATE for x column at " + xCol + " not " +
                    DataType.toString(xtype)
                    );
        }
        if (ytype != DataType.DOUBLE && ytype != DataType.INTEGER) {
            throw new IllegalArgumentException(
                    "expect NUMBER for y column at " + yCol + " not " +
                    DataType.toString(ytype)
                    );
        }
        return d;
    }

    public void end() {
        // TODO Auto-generated method stub

    }

    public void init(ISeries out, List<Arg> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException(
                    "Wrong number of arguments: expect 3 not " + args.size()
                    );
        }
        
        ISeries series = args.get(0).asSeries();
        if (series == null) {
            throw new IllegalArgumentException(
                    "null series for '" + out.getName() + "': " + args.get(0).getArgValue()
                    );
        }
        xCol = series.getXColumn();
        yCol = series.getYColumn();
        if (args.get(1).asLong() == null) {
            throw new IllegalArgumentException(
                    "null window for '" + out.getName() + "': " + args.get(1).getArgValue()
                    );
        }
        window = args.get(1).asLong();
        asPercent = args.get(2).asBoolean();
        // FIXME: more sensible tolerance, fine-tuned tolerance
        // depending on window YEAR/month etc
        dateMap = new FuzzyTimeMap<Double>(window / 7);
    }

    public void iter(DataStream d) {
        try {
            Date date = d.getDate(xCol);
            Double val = d.getDouble(yCol);
            if (date != null && val != null) {
                String[] vals = new String[2];
                long time = date.getTime();
                vals[0] = String.valueOf(time);
                vals[1] = String.valueOf(val);
                data.add(vals);
                dateMap.put(date, val);
            }
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void computeData() {
        // replace all the values with time-over-time changes
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            long now = Long.parseLong(row[0]);
            //Date now = new Date(
            // get previous value at -window, if any
            Date ago = new Date(now - window);
            {
                //p("from " + new Date(now) + " looking for " + ago);
            }
            Double agoVal = dateMap.get(ago);
            if (agoVal != null) {
                Double nowVal = Double.parseDouble(row[1]);
                double delta = nowVal - agoVal;
                // compute percent if needed, avoid div-by-zero though
                if (asPercent) {
                    if (agoVal != 0) {
                        delta = delta / agoVal * 100;
                    /*} else if (delta > 0) {
                        delta = Double.POSITIVE_INFINITY;
                    } else if (delta < 0) {
                        delta = Double.NEGATIVE_INFINITY;
                        */
                    } else {
                        // delta is zero
                        delta = 0;
                    }
                }
                row[1] = Double.toString(delta);
            } else {
                row[1] = null; // no value at this date
            }
        }
    }

    public DataStream value() {
        Metadata out = new Metadata(2);
        out.setColumnType(1, DataType.DATE);
        out.setColumnFormat(1, Metadata.INTERNAL_DATE_FORMAT);
        out.setColumnName(1, inputMeta.getColumnName(xCol));
        out.setColumnType(2, DataType.DOUBLE);
        out.setColumnName(2, inputMeta.getColumnName(yCol) + " change over time");
        computeData();
        StringDataStream ret = new StringDataStream(data, out, 0, data.size());
        return ret;
    }
    /*
    public static void main(String[] a) throws Exception {
        System.setProperty("chartengine.allowFileUrls", "true");
        File f = new File("test/data/monthly-cpi.csv");
        DataSourceInfo dsi;
        dsi = DataInference.get().inferFromURL(f.toURL().toString(), 20).getDataSource();
        DataStream ds =  DSFactory.createDataSource(dsi).getDataStream();

        TimeChange tc = new TimeChange();
        {
            List<String> args = new ArrayList();
            args.add("1");
            args.add("2");
            args.add("" + FuzzyTimeMap.MONTH/* * 12);
            args.add("false");
            tc.init(args);
            
        }
        {
            String url =
                "http://www.chartmechanic.com/rest/fs/dave/Case%20Shiller%20for%20selected%20cities?format=csv";
            DataSourceInfo info = DataInference.get().inferFromURL(url, 20).getDataSource();
            ds = DSFactory.createDataSource(info).getDataStream();
            tc.begin(ds);
            while (ds.next()) {
                tc.iter(ds);
            }
            ds.close();
            ds = tc.value();
            while (ds.next()) {
                Date d = ds.getDate(1);
                Double val = ds.getDouble(2);
                p(d + "|" + val);
            }
            //DSTestUtil.drainStream(ds, true);
        }
    }
    */
    private static void p(String s) {
        System.err.println("[TimeChange] " + s);
    }

}
