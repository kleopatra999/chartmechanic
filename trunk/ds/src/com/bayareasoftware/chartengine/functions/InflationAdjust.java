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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.StringDataStream;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.Metadata;

public class InflationAdjust implements Mapper {

    private int xCol, yCol, year;
    private double baseCPI; // cpi for our year/month
    private List<String[]> data;
    private static final Calendar cal = Calendar.getInstance();
    private Metadata inputMeta;
    static {
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        
    }
    private static int getMaxYear() {
        cal.setTime(new Date());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return cal.get(Calendar.YEAR);
    }
    public static final int MIN_YEAR = 1913;
    public static final int MAX_YEAR = getMaxYear();
    
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
        return d;
    }

    //@Override
    public void end() {
        // TODO Auto-generated method stub

    }

    private static synchronized Date getDateFromYear(int year) {
        cal.set(Calendar.YEAR, year);
        return cal.getTime();
    }
    /**
     * take three arguments:
     *      - the x column index 
     *      - the y column index
     *      - and a year (the basis for the CPI adjustment)
     */
    //@Override
    public void init(ISeries out, List<Arg> args) {
        if (usCPI.size() == 0) {
            throw new IllegalStateException(
                    "inflation function not available: CPI data not initialized"
                    );
        } else if (args.size() != 2) {
            throw new IllegalArgumentException(
                    "Wrong number of arguments: expect 2 not " + args.size()
                    );
        } else {
            
            ISeries series = args.get(0).asSeries();
            if (series == null) {
                throw new IllegalArgumentException(
                        "null series for '" + out.getName() + "': " + args.get(0).getArgValue()
                        );
            }
            xCol = series.getXColumn();
            yCol = series.getYColumn();
            if (args.get(1).asInt() == null) {
                throw new IllegalArgumentException(
                        "null year for '" + out.getName() + "': " + args.get(1).getArgValue()
                        );
            }
            year = args.get(1).asInt();
            
            if (year < MIN_YEAR || year > MAX_YEAR) {
                throw new IllegalArgumentException(
                        "year " + year + " out of allowed range: must be >=" + MIN_YEAR
                        + " and <=" + MAX_YEAR
                        );
            }
            Date baseDate = getDateFromYear(year);
            Double baseVal = usCPI.get(baseDate);
            if (baseVal == null) {
                throw new RuntimeException("no base CPI for " + baseDate);
            }
            baseCPI = baseVal;
            //p("baseVal for " + baseDate + " is " + baseCPI);
        }
    }

    //@Override
    public void iter(DataStream d) {
        Double outVal = null;
        try {
            Date date = d.getDate(xCol);
            Double origVal = d.getDouble(yCol);
            if (date != null && origVal != null) {
                Double spotCPI = usCPI.get(date);
                if (spotCPI != null) {
                    outVal = origVal * baseCPI / spotCPI;
                    //p("origVal=" + origVal + " spotCPI=" + spotCPI + " outVal=" + outVal + " basecpi=" + baseCPI);
                }
                
            }
            String[] vals = new String[2];
            if (date != null) {
                vals[0] = String.valueOf(date.getTime());
            }
            if (outVal != null) {
                vals[1] = String.valueOf(outVal);
            }
            data.add(vals);
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        

    }

    //@Override
    public DataStream value() {
        Metadata out = new Metadata(2);
        out.setColumnType(1, DataType.DATE);
        out.setColumnFormat(1, Metadata.INTERNAL_DATE_FORMAT);
        out.setColumnName(1, inputMeta.getColumnName(xCol));
        out.setColumnType(2, DataType.DOUBLE);
        out.setColumnName(2, inputMeta.getColumnName(yCol) + " (inflation adjusted)");
        StringDataStream ret = new StringDataStream(data, out, 0, data.size());
        return ret;
    }

    private static final FuzzyTimeMap<Double> usCPI = new FuzzyTimeMap<Double>(FuzzyTimeMap.MONTH + 10 * FuzzyTimeMap.DAY);
    
    public static void populateCPI(DataSourceInfo dsi) throws Exception {
        if (usCPI.size() > 0) {
            throw new RuntimeException(
                    "CPI map already populated"
                    );
        }
        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        try {
            populateCPI(ds);
        } finally {
            ds.close();
        }
    }
    private static void populateCPI(DataStream ds) throws Exception {
        Metadata md = ds.getMetadata();
        if (md.getColumnCount() < 2) {
            throw new IllegalArgumentException("not enough columns for CPI: " + md);
        } else if (md.getColumnType(1) != DataType.DATE) {
            throw new IllegalArgumentException("expecting date not " + DataType.toString(md.getColumnType(1)));
        } else if (md.getColumnType(2) != DataType.DOUBLE) {
            throw new IllegalArgumentException("expecting DOUBLE not " + DataType.toString(md.getColumnType(2)));
        }
        int row = 0;
        while (ds.next()) {
            Date d = ds.getDate(1);
            Double val = ds.getDouble(2);
            if (d == null) {
                throw new RuntimeException("null date at row #" + row);
            } else if (val == null) {
                throw new RuntimeException("null value at row #" + row);
            }
            usCPI.put(d, val);
            row++;
        }
    }
    /*
    public static void main(String[] a) throws Exception {
        System.setProperty("chartengine.allowFileUrls", "true");
        p("max year is " + MAX_YEAR + " date from 1982 is " + getDateFromYear(1982));
        File f = new File("test/data/monthly-cpi.csv");
        DataSourceInfo dsi;
        dsi = DataInference.get().inferFromURL(f.toURL().toString(), 20).getDataSource();
        DataStream ds =  DSFactory.createDataSource(dsi).getDataStream();
        try {
            populateCPI(ds);
        } finally {
            ds.close();
        }
        InflationAdjust ia = new InflationAdjust();
        {
            List<String> args = new ArrayList();
            args.add("1");
            args.add("2");
            args.add("1975");
            ia.init(args);
            
        }
        {
            String url =
                "http://www.chartmechanic.com/rest/fs/dave/Case%20Shiller%20for%20selected%20cities?format=csv";
            DataSourceInfo info = DataInference.get().inferFromURL(url, 20).getDataSource();
            ds = DSFactory.createDataSource(info).getDataStream();
            ia.begin(ds);
            while (ds.next()) {
                ia.iter(ds);
            }
            ds.close();
            ds = ia.value();
            while (ds.next()) {
                Date d = ds.getDate(1);
                Double val = ds.getDouble(2);
                p(d + "|" + val);
            }
            //DSTestUtil.drainStream(ds, true);
        }
    }
    private static void p(String s) {
        System.err.println("[InflationAdjust] " + s);
    }
    */
}
