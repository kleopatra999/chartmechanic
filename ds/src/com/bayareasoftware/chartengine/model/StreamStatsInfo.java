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
package com.bayareasoftware.chartengine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * represents serializable stream statistics that is passed to the front-end 
 */
public class StreamStatsInfo implements Serializable, PropertySerializable {

    public static String PROP_METADATA = "md";   
    public static String PROP_LASTMODIFIED = "lastModified"; // last modified in msec
    public static String PROP_ROWCOUNT = "rowcount";
    
    private int rowCount;
    private long lastModified;
    private List<ColStatsInfo> columns;
    private Metadata metadata;
    
    public StreamStatsInfo() {
    }
    @Override
    public int hashCode() {
        return rowCount;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof StreamStatsInfo)) {
            return false;
        }
        StreamStatsInfo o = (StreamStatsInfo) obj;
        boolean ret = rowCount == o.rowCount;
        if (ret) {
            ret = eq(columns,o.columns);
            if (ret) {
                ret = eq(metadata, o.metadata);
            }
        }
        return ret;
    }
    public Metadata getMetadata() {
        return metadata;
    }
    public void setMetadata(Metadata md) {
        metadata = md;
    }
    public void setColumns(List<ColStatsInfo> cols) {
        this.columns = cols;
    }
    public int getRowCount() {
        return rowCount;
    }
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    public long getLastModified() {
        return lastModified;
    }
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
    /** one-based index */
    public ColStatsInfo getColumn(int index) {
        if (index > 0 && columns != null && index <= columns.size()) {
            return columns.get(index - 1);
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[Stats: rows=").append(rowCount).append(" lastModified = ")
        .append(lastModified).append('\n');
        for (int i = 0; columns != null && i < columns.size(); i++) {
            sb.append("  ");
            columns.get(i).toString(sb);
            sb.append(",\n");
        }
        
        return sb.append(']').toString();
    }
    
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        // assume prefix == null as we do not serialize streamstats as a component with anything enclosing construct
        boolean ret = false;
        try {
            Metadata md;
            if (this.metadata == null) { 
                md = new Metadata();
            } else
                md = this.metadata;
            
            if (p.containsKey(PROP_ROWCOUNT)) {
                this.rowCount = Integer.valueOf(p.get(PROP_ROWCOUNT));
                ret = true;
            }
            if (p.containsKey(PROP_LASTMODIFIED)) {
                this.lastModified = Long.valueOf(p.get(PROP_LASTMODIFIED));
            }
            String mdprefix = PROP_METADATA;
            ret = md.deserializeFromProps(p, mdprefix);
            if (ret == true) {
                this.metadata = md;
            } 
            int ncols = SerializeUtil.deserializeListSize(p, prefix, "column");
            columns = new ArrayList<ColStatsInfo>();
            for (int i = 0; i < ncols; i++) {
                ColStatsInfo cs = new ColStatsInfo();
                SerializeUtil.deserializeListElement(cs, p, prefix, "column", i);
                columns.add(cs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        if (p == null)
            p = new SimpleProps();
        if (metadata != null) {
            // serialize the metadata element and append it to our existing simpleprops
            // but ensure that it has the addition prefix PROP_METADATA
            metadata.serializeToProps(p, SimpleProps.prefixIt(prefix,PROP_METADATA));
        }
        
        p.set(prefix,PROP_LASTMODIFIED,String.valueOf(this.lastModified));
        p.set(prefix, PROP_ROWCOUNT, String.valueOf(rowCount));
        SerializeUtil.serializeList(p, prefix, columns, "column");
        return p;
        
    }
    private static boolean eq(Object a, Object b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }
    
    public static final String PROP_DATATYPE = "datatype";
    public static final String PROP_MIN = "min";
    public static final String PROP_MAX = "max";
    public static final String PROP_AVG = "avg";
    public static final String PROP_NULLS = "nulls";
    public static final String PROP_TIME_PERIOD = "time_period";
    public static final String PROP_MIN_DATE = "min_date";
    public static final String PROP_MAX_DATE = "max_date";
    // serializable streams stats of a particular column
    public static class ColStatsInfo implements Serializable,PropertySerializable {
        // one of the constants in DataType
        private int dataType;
        private Double min, max, avg;
        private int nulls;
        // one of the TIME_* values in TimeConstants
        private int timePeriod = TimeConstants.TIME_UNKNOWN;
        private Date minDate, maxDate;
        
        public ColStatsInfo() { }

        @Override
        public int hashCode() {
            return dataType ^ nulls;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof ColStatsInfo)) {
                return false;
            }
            ColStatsInfo o = (ColStatsInfo)obj;
            boolean ret = 
                (dataType == o.dataType && nulls == o.nulls && timePeriod == o.timePeriod);
            if (ret) {
                ret =
                    eq(min,o.min) && eq(max,o.max) && eq(avg,o.avg)
                    && eq(minDate,o.minDate) && eq(maxDate,o.maxDate);
            }
            return ret;
        }
        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            toString(sb);
            return sb.toString();
        }
        void toString(StringBuffer sb) {
            sb.append("[Col Type: ")
            .append(DataType.toString(dataType)).append(' ');
            switch (dataType) {
            case DataType.DATE:
                sb.append("MinDate: ");
                if (minDate == null) {
                    sb.append("NULL ");
                } else {
                    sb.append(minDate).append(' ');
                }
                sb.append("MaxDate: ");
                if (maxDate == null) {
                    sb.append("NULL");
                } else {
                    sb.append(maxDate);
                }
                sb.append(" Frequency: ").append(this.getTimePeriodName());
                break;
            case DataType.DOUBLE:
            case DataType.INTEGER:
                sb.append("MinValue: ").append(getMin()).append(" MaxValue: ").append(getMax());
            }
            sb.append(" Nulls: ").append(nulls);
            sb.append(']');
        }
        //void p(String s) {
          //  System.out.println("[ColStatsInfo] " + s);
        //}
        public boolean deserializeFromProps(SimpleProps p, String prefix) {
            /*p("deserializing with prefix=" + prefix + ": '" + prefix + "."
                    + PROP_DATATYPE + "'='" + p.get(prefix + '.' + PROP_DATATYPE) + "'");
                    */
            boolean ret = false;
            try {
                
                if (p.containsKey(prefix,PROP_DATATYPE)) {
                    dataType = Integer.valueOf(p.get(prefix, PROP_DATATYPE));
                    ret = true;
                }
                if (p.containsKey(prefix, PROP_MIN)) {
                    min = Double.valueOf(p.get(prefix, PROP_MIN));
                }
                if (p.containsKey(prefix, PROP_MAX)) {
                    max = Double.valueOf(p.get(prefix, PROP_MAX));
                }
                if (p.containsKey(prefix, PROP_AVG)) {
                    avg = Double.valueOf(p.get(prefix, PROP_AVG));
                }
                if (p.containsKey(prefix, PROP_NULLS)) {
                    nulls = Integer.valueOf(p.get(prefix, PROP_NULLS));
                }
                if (p.containsKey(prefix, PROP_TIME_PERIOD)) {
                    timePeriod = Integer.valueOf(p.get(prefix, PROP_TIME_PERIOD));
                }
                if (p.containsKey(prefix, PROP_MIN_DATE)) {
                    minDate = new Date(Long.valueOf(p.get(prefix, PROP_MIN_DATE)));
                }
                if (p.containsKey(prefix, PROP_MAX_DATE)) {
                    maxDate = new Date(Long.valueOf(p.get(prefix, PROP_MAX_DATE)));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                ret = false;
            }
            return ret;
        }

        public SimpleProps serializeToProps(SimpleProps p, String prefix) {
            if (p == null) {
                p = new SimpleProps();
            }
            p.set(prefix, PROP_DATATYPE, String.valueOf(dataType));
            p.set(prefix, PROP_NULLS, String.valueOf(nulls));
            switch (dataType) {
            case DataType.DOUBLE:
            case DataType.INTEGER:
                if (min != null) p.set(prefix, PROP_MIN, String.valueOf(min));
                if (max != null) p.set(prefix, PROP_MAX, String.valueOf(max));
                if (avg != null) p.set(prefix, PROP_AVG, String.valueOf(avg));
                break;
            case DataType.DATE:
                if (minDate != null) {
                    p.set(prefix, PROP_MIN_DATE, String.valueOf(minDate.getTime()));
                }
                if (maxDate != null) {
                    p.set(prefix, PROP_MAX_DATE, String.valueOf(maxDate.getTime()));
                }
                p.set(prefix, PROP_TIME_PERIOD, String.valueOf(timePeriod)); 
            }
            return p;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public Double getMin() {
            return min;
        }

        public void setMin(Double min) {
            this.min = min;
        }

        public Double getMax() {
            return max;
        }

        public void setMax(Double max) {
            this.max = max;
        }

        public Double getAvg() {
            return avg;
        }

        public void setAvg(Double avg) {
            this.avg = avg;
        }

        public int getNulls() {
            return nulls;
        }

        public void setNulls(int nulls) {
            this.nulls = nulls;
        }

        public int getTimePeriod() {
            /*
            if (this.getDataType() != DataType.DATE) {
                throw new RuntimeException("data type is " + DataType.toString(getDataType()) + " not DATE");
            }*/
            return timePeriod;
        }

        public void setTimePeriod(int timePeriod) {
            this.timePeriod = timePeriod;
        }
        public String getTimePeriodName() {
            return TimeUtil.decodeTimeInt(getTimePeriod());
        }

        public Date getMinDate() {
            return minDate;
        }

        public void setMinDate(Date minDate) {
            this.minDate = minDate;
        }

        public Date getMaxDate() {
            return maxDate;
        }

        public void setMaxDate(Date maxDate) {
            this.maxDate = maxDate;
        }
        
    }
}
