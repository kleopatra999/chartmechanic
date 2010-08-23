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

import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_DAY;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_MONTH;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_QUARTER;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_WEEK;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_YEAR;

import java.util.Calendar;
import java.util.Date;

import com.bayareasoftware.chartengine.ds.util.DataStreamUtil;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

/**
 * Compute month-over-month, year-over-year changes
 * 
 * @author dave
 *
 */
public class TimeDeltaStream extends DataStream {

    protected DataStream stream;
    protected int dateCol, valueCol, timeType, totValueCol;
    
    protected Calendar cal;
    protected FuzzyTimeMap<Double> memory;
    protected Date currDate;
    protected Double currVal;
    
    public TimeDeltaStream(DataStream stream, int dateCol, int valueCol, int timeType, long tolerance) {
        super(false);
        this.stream = stream;
        this.dateCol = dateCol;
        this.valueCol = valueCol;
        this.timeType = timeType;
        Metadata otherMd = stream.getMetadata();
        int colCount = otherMd.getColumnCount() + 1;
        totValueCol = colCount;
        this.metadata = new Metadata(colCount);
        for (int i = 1; i < colCount; i++) {
            ColumnInfo ci = otherMd.getColumnInfo(i);
            metadata.setColumnDescription(i, ci.getDescription());
            metadata.setColumnFormat(i, ci.getFormat());
            metadata.setColumnName(i, ci.getName());
            metadata.setColumnType(i, ci.getType());
        }
        metadata.setColumnType(colCount, DataType.DOUBLE);
        metadata.setColumnName(colCount, "time_over_time_delta");
        memory = new FuzzyTimeMap<Double>(tolerance);        
        cal = Calendar.getInstance();
    }
    
    @Override
    public Boolean getBoolean(int index) throws Exception {
        if (index != totValueCol) {
            return stream.getBoolean(index);
        }
        throw new RuntimeException("NYI");
    }

    @Override
    public Date getDate(int index) throws Exception {
        if (index != totValueCol) {
            Date ret = stream.getDate(index);
            if (index == dateCol) {
                currDate = ret;
            }
            return ret;
        }
        //throw new RuntimeException("invalid");
        return null;
    }

    @Override
    public Double getDouble(int index) throws Exception {
        if (index != totValueCol) {
            double ret = stream.getDouble(index);
            if (index == valueCol) {
                currVal = ret;
            }
            return ret;
        }
        Double ret = null;
        if (currDate == null) {
            currDate = stream.getDate(dateCol);
        }
        if (currVal == null) {
            currVal = stream.getDouble(valueCol);
        }
        if (currDate != null && currVal != null) {
            Date oldDate = computePriorDate(currDate);
            Double oldVal = memory.get(oldDate);
            if (oldVal != null) {
                if (oldVal != 0) { // avoid division by zero
                    ret = ((currVal - oldVal) / oldVal) * 100;
                }
            }
        }

        return ret;
    }

    @Override
    public Integer getInt(int index) throws Exception {
        if (index != totValueCol) {
            return stream.getInt(index);
        }
        //throw new RuntimeException("NYI");
        return null;
    }

    @Override
    public String getString(int index) throws Exception {
        if (index != totValueCol) {
            return stream.getString(index);
        }
        //throw new RuntimeException("NYI");
        return null;
    }

    /*@Override
    public boolean isNull(int index) throws Exception {
        if (index != totValueCol) {
            return stream.isNull(index);
        }
        if (currDate == null) {
            currDate = stream.getDate(dateCol);
        }
        if (currVal == null) {
            if (!stream.isNull(valueCol)) {
                currVal = stream.getDouble(valueCol);
            }
        }
        if (currDate == null || currVal == null) {
            return true;
        }
        Date prior = computePriorDate(currDate);
        //p("look for prior date " + prior + " from " + currDate);
        return memory.get(prior) == null;
    }*/

    @Override
    public boolean nextInternal() throws Exception {
        if (currDate != null && currVal != null) {
            memory.put(currDate, currVal);
        }
        currDate = null;
        currVal = null;
        return stream.next();
    }

    private Date computePriorDate(Date d) {
        Date ret = null;
        cal.setTime(d);
        switch (timeType) {
        case TIME_DAY:
            cal.add(Calendar.DAY_OF_YEAR, -1);
            ret = cal.getTime();
            break;
        case TIME_WEEK:
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            ret = cal.getTime();
            break;
        case TIME_MONTH:
            cal.add(Calendar.MONTH, -1);
            ret = cal.getTime();
            break;
        case TIME_QUARTER:
            cal.add(Calendar.MONTH, -3);
            ret = cal.getTime();
            break;
        case TIME_YEAR:
            cal.add(Calendar.YEAR, -1);
            ret = cal.getTime();
            break;
        default:
            throw new RuntimeException(
                    "unhandled time type: " + DataType.toString(timeType)
                    );
        }
        //pe("prior date of " + d + " is " + ret);
        return ret;
    }
    public static void main(String[] a) throws Exception {
        
        DataStream ds = null;
        //String table;
        //table = "frbfor_for_dtfhhm37ypdhh_q"; // Mortgage obligations of homeowners, quarterly
        //table = "frbg20_owned_dtros_n_m"; //  Single-family Real Estate Loans Owned by Finance Companies, monthly
        /*
        SimpleProps props = new SimpleProps();
        props.put(StandardProps.JDBC_DRIVER,"org.postgresql.Driver");
        props.put(StandardProps.JDBC_URL,"jdbc:postgresql://localhost/cm_data_test");
        props.put(StandardProps.JDBC_USERNAME,"bt");
        props.put(StandardProps.JDBC_PASSWORD,"");
        props.put(DataSourceInfo.TABLE_NAME,table);
        String query = "SELECT D, OBS FROM " + table + " ORDER BY D";
        JdbcDataSource jds = new JdbcDataSource(props,query);
              
        ds = jds.executeQuery();
        */
        try {
            DataSourceInfo dsi;
            dsi = DataInference.get().inferFromURL("http://www.chartmechanic.com/csv?c=10959",-1).getDataSource();
            ds = DSFactory.createDataSource(dsi).getDataStream();
            TimeDeltaStream tds = null;
            //tds = new TimeDeltaStream(rsds, 1, 2, TIME_YEAR, DAY * 3); // YoY changes
            //tds = new TimeDeltaStream(rsds, 1, 2, TIME_MONTH, DAY * 4); // MoM changes
            tds = new TimeDeltaStream(ds, 1, 2, TIME_YEAR, FuzzyTimeMap.DAY * 3);
            DataStreamUtil.dump(System.out, tds);
            //System.out.println("memory map: " + tds.memory);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
//    private static void p(String s) {
//        System.out.println(s);
//    }
//    private static void pe(String s) {
//        System.err.println(s);
//    }
}
