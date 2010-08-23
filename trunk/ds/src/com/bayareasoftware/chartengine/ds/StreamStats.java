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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.TimeConstants;
import com.bayareasoftware.chartengine.model.TimeUtil;
import com.bayareasoftware.chartengine.model.StreamStatsInfo;
import com.bayareasoftware.chartengine.model.StreamStatsInfo.ColStatsInfo;

/**
 * Class to collect qualitiative stats about a data set:
 * #rows, min/max values, min/max dates
 * 
 *
 */
public class StreamStats {
    
    private static Log log = LogFactory.getLog(StreamStats.class);
    
    public static String PROP_METADATA = "md";   
    public static String PROP_LASTMODIFIED = "lastModified"; // last modified in m-seconds
    //public static String PROP_SOURCE = "source";
//    public static String AGGREGATE_AVG = "fn.avg";
//    public static String AGGREGATE_MIN = "fn.min";
//    public static String AGGREGATE_MAX = "fn.max";
    private int rowCount;
    private ColumnStats[] cols;
    private Metadata metadata;
    
    // time in milliseconds
    private long lastModified;

    // set if the stream came from an source URL
    private String source;
    
    public StreamStats() {
        this.lastModified = System.currentTimeMillis();
    }
    
    public StreamStats(Metadata md) {
        this.metadata = md;
        initcols(md);
        this.lastModified = System.currentTimeMillis();
    }
    
    private void initcols(Metadata md) {
        if (md != null) {
            cols = new ColumnStats[md.getColumnCount()];
            for (int i = 0; i < cols.length; i++) {
                ColumnInfo ci = md.getColumnInfo(i+1);
                cols[i] = new ColumnStats(ci, i+1);
            }
        } else {
            throw new NullPointerException("null metadata");
        }
    }
    public Metadata getMetadata() {
        return this.metadata;
    }
    
    public void setLastModified(long time) {
        this.lastModified=time;
    }
    
    public long getLastModified() {
        return lastModified;
    }
    
    
    /*
    public void setSource(String s) {
        this.source = s;
    }
    
    public String getSource() {
        return this.source;
    }*/
    
    /** update one row */
    public void update(DataStream ds) throws Exception {
        rowCount++;
        for (int i = 0; i < cols.length; i++) {
            cols[i].update(ds);
        }
    }
    public int getRowCount() { return rowCount; }
    /** one-based index */
    public ColumnStats getColumn(int index) {
        if (index > 0 && index <= cols.length) {
            return cols[index-1];
        }
        return null;
    }
//    public Double getAggregate(String agg, int col) {
//        Double ret = null;
//        ColumnStats cs = getColumn(col);
//        if (cs != null) {
//            if (agg.equals(AGGREGATE_AVG)) {
//                ret = cs.getAverage();
//            } else if (agg.equals(AGGREGATE_MIN)) {
//                ret = cs.getMinValue();
//            } else if (agg.equals(AGGREGATE_MAX)) {
//                ret = cs.getMaxValue();
//            }
//        }
//        return ret;
//    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Stats: rows=").append(rowCount).append(" lastModified = ").append(lastModified);
        for (int i = 0; i < cols.length; i++) {
            sb.append("  ");
            cols[i].toString(sb);
            sb.append(",\n");
        }
        
        return sb.append(']').toString();
    }
    private static final int MAX_TIME_SAMPLES = 20;
    /** We stop keeping track of distinct values beyond this number */
    //public static final int MAX_DISTINCT_VALUES = 50;
    public class ColumnStats {
        private ColumnInfo colInfo;
        private int index;
        private double min = Double.MAX_VALUE, max = Double.MIN_VALUE, avg = Double.NaN;
        private int nulls;
        // one of the TIME_* values in TimeConstants
        private int timePeriod;
        private Date minDate, maxDate;
        // transient values for the process of record keeping, not for the
        // stats itself
        private transient List<Long> timeSamples;
        private transient boolean sawAverage = false;
        // keep track of how many distinct Values we've seen
        //private transient Map distinctTracker;
        // public default CTOR for serialization
        //public ColumnStats() { }
        
        public ColumnStats(ColumnInfo ci, int index) {
            colInfo = ci;
            this.index = index;
            timeSamples = new ArrayList<Long>();
            timePeriod = -2; // undetermined,uninitialized
        }
    
        public ColStatsInfo toInfo() {
            ColStatsInfo ret = new ColStatsInfo();
            ret.setDataType(colInfo.getType());
            ret.setNulls(nulls);
            if (colInfo.getType() == DataType.DOUBLE || colInfo.getType() == DataType.INTEGER) {
                ret.setAvg(avg);
                ret.setMin(min);
                ret.setMax(max);
            }
            if (colInfo.getType() == DataType.DATE) {
                ret.setMinDate(minDate);
                ret.setMaxDate(maxDate);
                ret.setTimePeriod(getTimePeriod());
            }
            return ret;
        }
        private void determineFrequency() {
            // determine <strike>minimum</strike> median delta between adjacent time periods
            Collections.sort(timeSamples);
            long delta = -1;
            List<Long> deltas = new ArrayList<Long>();
            for (int i = 0; i < timeSamples.size() - 2; i++) {
                Long a = timeSamples.get(i);
                Long b = timeSamples.get(i+1);
                long diff = Math.abs(a - b);
                deltas.add(diff);
                if (diff > 0) {
                    if (delta == -1) {
                        delta = diff;
                    } else if (diff < delta) {
                        delta = diff;
                    }
                }
            }
            if (deltas.size() > 2) {
                Collections.sort(deltas);
                // take median delta
                long proposal = deltas.get(deltas.size() / 2);
                if (proposal > 0) {
                    delta = proposal;
                }
            }
            timePeriod = TimeUtil.periodForInterval(delta);
            // default to something reasonable...
            if (timePeriod == TimeConstants.TIME_UNKNOWN) {
                timePeriod = TimeConstants.TIME_DAY;
            }
        }
        public void update(DataStream ds) throws Exception {
            int type = colInfo.getType();
            switch (type) {
            case DataType.DATE:
                Date d = ds.getDate(index);
                if (d == null) {
                    nulls++;
                } else {
                    if (minDate == null || d.before(minDate)) {
                        minDate = d;
                    }
                    if (maxDate == null || d.after(maxDate)) {
                        maxDate = d;
                    }
                    if (timePeriod == -2) {
                        if (timeSamples.size() < MAX_TIME_SAMPLES) {
                            timeSamples.add(d.getTime());
                        }
                    }
                }
                break;
            case DataType.INTEGER:
            case DataType.DOUBLE:
                Double num = null;
                if (type == DataType.INTEGER) {
                    Integer ig = ds.getInt(index);
                    if (ig != null) {
                        num = ig.doubleValue();
                    }
                } else {
                    num = ds.getDouble(index);
                }
                if (num == null) {
                    nulls++;
                } else {
                    if (num < min) {
                    min = num;
                    }
                    if (num > max) {
                        max = num;
                    }
                    if (!sawAverage) {
                        sawAverage = true;
                        avg = num.doubleValue();
                    } else {
                        double rowCount = getRowCount();
                        avg = ((rowCount - 1) * avg + num)/rowCount;
                    }
                }
                break;
            case DataType.STRING:
                String s = ds.getString(index);
                if (s == null) {
                    nulls++;
                }
                break;
            case DataType.BOOLEAN:
                Boolean b = ds.getBoolean(index);
                if (b == null) {
                    nulls++;
                }
                break;
            case DataType.IGNORE:
            case DataType.UNKNOWN:
                default:
                    break;
            }
        }

        public String getName() { return colInfo.getName(); }
        public int getType() { return colInfo.getType(); }
        public Double getMinValue() {
            return min == Double.MAX_VALUE ? null: min;
        }
        public String toString() {
            StringBuffer sb = new StringBuffer();
            toString(sb);
            return sb.toString();
        }
        void toString(StringBuffer sb) {
            sb.append("[Col: \"").append(getName()).append('\"').append(" Type: ")
            .append(DataType.toString(getType())).append(' ');
            switch (getType()) {
            case DataType.DATE:
                sb.append("MinDate: ");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                if (minDate == null) {
                    sb.append("NULL ");
                } else {
                    sb.append(df.format(minDate)).append(' ');
                }
                sb.append("MaxDate: ");
                if (maxDate == null) {
                    sb.append("NULL");
                } else {
                    sb.append(df.format(maxDate));
                }
                sb.append(" Frequency: ").append(this.getTimePeriodName());
                break;
            case DataType.DOUBLE:
            case DataType.INTEGER:
                sb.append("MinValue: ").append(getMinValue()).append(" MaxValue: ").append(getMaxValue());
            }
            sb.append(" Nulls: ").append(nulls);
            sb.append(']');
        }
        public Double getAverage() {
            return avg == Double.NaN ? null: avg;
        }
        public Double getMaxValue() {
            return max == Double.MIN_VALUE ? null: max;
        }

        public int getNullCount() { return nulls; }
        
        /**
         * One of the TIME_* values in TimeConstants
         * @return
         */
        public int getTimePeriod() {
            if (timePeriod == -2) {
                if (timeSamples != null && timeSamples.size() > 1) {
                    determineFrequency();
                } else {
                    timePeriod = TimeConstants.TIME_UNKNOWN;
                }
            }
            return timePeriod;
        }
        public String getTimePeriodName() {
            return TimeUtil.decodeTimeInt(getTimePeriod());
        }

        public Date getMinDate() { return minDate; }

        public Date getMaxDate() { return maxDate; }
    }

    /**
     * convert a StreamStats to a StreamStatsInfo, a serializable object that
     * be passed to the front-end
     * @return
     */
    public StreamStatsInfo toStreamStatsInfo() {
        StreamStatsInfo ret = new StreamStatsInfo();
        ret.setMetadata(metadata);
        ret.setRowCount(getRowCount());
        ret.setLastModified(getLastModified());
        List<ColStatsInfo> l = new ArrayList<ColStatsInfo>();
        for (int i = 1; cols != null && i <= cols.length; i++) {
            l.add(this.getColumn(i).toInfo());
        }
        ret.setColumns(l);
        return ret;
    }
}
