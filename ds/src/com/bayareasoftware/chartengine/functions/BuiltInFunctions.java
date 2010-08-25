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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.StringDataStream;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.js.DataGrid;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.FunctionDescriptor;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.TimeUtil;


/**
 * a collection of builtin functions
 */
public class BuiltInFunctions {
    private static Log log = LogFactory.getLog(BuiltInFunctions.class);
    
    private static List<FunctionDescriptor> reducerFuncs,mapperFuncs,joinerFuncs;
    //public static List<FunctionDescriptor> fns;

    /**
     * these are the function names that are persisted as properties
     * DO NOT change these or else it will be break existing charts
     */
    public static String FN_AVG = "fn.avg";
    public static String FN_MIN = "fn.min";
    public static String FN_MAX = "fn.max";
    public static String FN_COUNT = "fn.count";
    public static String FN_SUM = " fn.sum";
    public static String FN_STDEV = " fn.stdev";
    public static String FN_STDEVP = " fn.stdevp";
     
    public static String FN_MVAVG = "fn.mvavg"; // moving average
    public static String FN_SCALE = "fn.scale"; // scale the values by a multiplier
    public static String FN_INFLATION = "fn.inflation"; // adjust monetary timeseries by inflation
    public static String FN_TIMECHANGE = "fn.timechange"; // year-over-year, month-over-month changes
    public static String FN_TIME_JOIN = "fn.timejoin"; // math on two or more series, after a join
    static {
        reducerFuncs =  new ArrayList<FunctionDescriptor>();
        mapperFuncs =  new ArrayList<FunctionDescriptor>();
        joinerFuncs =  new ArrayList<FunctionDescriptor>();
        
        /** Reducers */
        reducerFuncs.add(new FunctionDescriptor("AVG",FN_AVG,"Average",Average.class,true));
        reducerFuncs.add(new FunctionDescriptor("MIN",FN_MIN,"Minimum",Min.class,true));
        reducerFuncs.add(new FunctionDescriptor("MAX",FN_MAX,"Maximum",Max.class,true));
        reducerFuncs.add(new FunctionDescriptor("COUNT",FN_COUNT,"Count",Count.class,true));
        reducerFuncs.add(new FunctionDescriptor("SUM",FN_SUM,"Sum",Sum.class,true));
        reducerFuncs.add(new FunctionDescriptor("STDEV",FN_STDEV,"Standard Deviation",StDev.class,true)
                .addArg(ArgType.NUMBER, "Multiplier", "Numeric factor to multiply the standard deviation by","1")
        );
        reducerFuncs.add(new FunctionDescriptor("STDEVP",FN_STDEVP,"Population Standard Deviation",StDevP.class,true)
                .addArg(ArgType.NUMBER, "Multiplier", "Numeric factor to multiply the standard deviation by","1")
        );
        /** Mappers **/
        mapperFuncs.add(
                new FunctionDescriptor("MOVING AVG",FN_MVAVG,"Moving Average",MovingAverage.class,false)
                .addArg(ArgType.SID, "Series", "Apply to series")
                .addArg(ArgType.NUMBER, "Window", "Number of samples to take the moving average of","10")
        );
        mapperFuncs.add(
                new FunctionDescriptor("SCALE",FN_SCALE,"Scale",Scale.class,false)
                .addArg(ArgType.SID, "Series", "Apply to series")
                .addArg(ArgType.NUMBER, "Scale Factor", "Numeric value to multiply by original data","1")
                );
        FunctionDescriptor ia = 
            new FunctionDescriptor("INFLATION ADJUST", FN_INFLATION,
                    "Adjust for Inflation", InflationAdjust.class, false);
        ia.addArg(ArgType.SID, "Series", "Apply to series");
        ia.addArg(ArgType.NUMBER, "Base Year",
                "Year for inflation normalization (between 1913 and present)", "2000");
        mapperFuncs.add(ia);
        FunctionDescriptor tc =
            new FunctionDescriptor("TIME CHANGE", FN_TIMECHANGE,
                    "Change over Time",
                    TimeChange.class, false);
        tc.addArg(ArgType.SID, "Series", "Apply to series");
        tc.addArg(ArgType.TIME_INTERVAL, "Time Interval",
                "Earlier time period from which to compute change",
                "" + TimeUtil.INTERVAL_MONTH);
        tc.addArg(ArgType.BOOLEAN, "Percentage", "Express as Percentage", "false");
        mapperFuncs.add(tc);
        /** Joiners **/
        FunctionDescriptor tj;
        tj = new FunctionDescriptor("TIME JOIN", FN_TIME_JOIN, "Join Time Series",
                JoinTime.class, false);
        tj.addArg(ArgType.SID, "Operand 1", "First Series");
        tj.addArg(ArgType.MATH_OPERATOR, "Operator", "Math Function");
        tj.addArg(ArgType.SID, "Operand 2", "Second Series");
        tj.addArg(ArgType.TIME_INTERVAL, "Join Tolerance", "Tolerance interval for time join",
                "" + TimeUtil.INTERVAL_DAY);
        joinerFuncs.add(tj);
        
    }

    public static List<FunctionDescriptor> getMapperFunctions() {
        return mapperFuncs;
    }
    public static List<FunctionDescriptor> getReducerFunctions() {
        return reducerFuncs;
    }
    public static List<FunctionDescriptor> getJoinerFunctions() {
        return joinerFuncs;
    }
    // functions that produce a series i.e., mappers+joiners
    public static List<FunctionDescriptor> getSeriesFunctions() {
        List<FunctionDescriptor> ret = new ArrayList();
        ret.addAll(mapperFuncs);
        ret.addAll(joinerFuncs);
        return ret;
    }

    public static List<FunctionDescriptor> getFunctionDescriptors() {
        List<FunctionDescriptor> ret = new ArrayList();
        ret.addAll(reducerFuncs);
        ret.addAll(mapperFuncs);
        ret.addAll(joinerFuncs);
        return ret;
    }

    public static FunctionDescriptor findSeriesFunction(String name) {
        FunctionDescriptor ret;
        ret = findFuncInList(mapperFuncs, name);
        if (ret == null) ret = findFuncInList(joinerFuncs, name);
        return ret;
    }

    public static FunctionDescriptor findMarkerFunction(String name) {
        return findFuncInList(reducerFuncs, name);
    }

    // look based on fname, fn. + fname, or display name, ignore case
    private static FunctionDescriptor findFuncInList(List<FunctionDescriptor> l, String name) {
        FunctionDescriptor ret = null;
        for (FunctionDescriptor fd : l) {
            if (name.equalsIgnoreCase(fd.getName()) ||
                    ("fn." + name).equalsIgnoreCase(fd.getName()) ||
                    name.equalsIgnoreCase(fd.getDisplayName())) {
                ret = fd;
                break;
            }
        }
        return ret;
    }
    public static Object makeFunction(String fname) {
        List<FunctionDescriptor> fns = getFunctionDescriptors();
        for (FunctionDescriptor fd : fns) {
            if (fd.getName().equals(fname)) {
                try {
                    return fd.getImpl().newInstance();
                } catch (Exception e) {
                    log.error("Failed to instantiate class: " + fd.getImpl()); 
                }
            }
        }
        return null;
    }
    
    public static class Average implements Reducer {
        double s = 0.0;
        int count = 0;
        
        public void end() {
        }

        public void iter(Object o) {
            if (o != null && o instanceof Number) {
                s += ((Number)o).doubleValue();
                count++;
            }
        }

        public void init(List<String> args) {
            s = 0.0;
            count = 0;
        }

        public Object value() {
            Object res = null;
            if (count > 0)
                res = new Double(s / count);
            return res;
        }
    }
    
    public static class Max implements Reducer {
        double s = 0.0;
        boolean initValue = false;

        public void end() {
        }

        public void iter(Object o) {
            
            if (o != null && o instanceof Number) {
                double d = ((Number)o).doubleValue();
                if (!initValue) {
                    s = d;
                    initValue = true;
                } else {
                    if (d > s) {
                        s = d;
                    }
                }
            }
        }

        public void init(List<String> args) {
            s = 0.0;
            initValue = false;
        }

        public Object value() {
            return new Double(s);
        }
    }
    
    public static class Min implements Reducer {
        double s = 0.0;
        boolean initValue = false;

        public void end() {
        }

        public void iter(Object o) {
            
            if (o != null && o instanceof Number) {
                double d = ((Number)o).doubleValue();
                if (!initValue) {
                    s = d;
                    initValue = true;
                } else {
                    if (d < s) {
                        s = d;
                    }
                }
            }
        }

        public void init(List<String> args) {
            s = 0.0;
            initValue = false;
        }

        public Object value() {
            return new Double(s);
        }
    }
    
    public static class Sum implements Reducer {
        double s = 0.0;
        public void end() {
            
        }

        public void iter(Object o) {
            if (o != null && o instanceof Number) {
                s += ((Number)o).doubleValue();
            }
        }

        public void init(List<String> args) {
            s = 0.0;
        }
        
        public Object value() {
            return new Double(s);
        }
    }
    
    public static class Count implements Reducer {
        int count = 0;

        public void end() {
        }

        public void iter(Object o) {
            if (o != null) {
                count++;
            }
        }

        public void init(List<String> args) {
            count = 0;
        }

        public Object value() {
            return new Double(count);
        }
    }
    
    /**
     * calculate the standard deviation
     * note this differs from the Population Standard Deviation
     * and is equivalent to the STDEV function in Excel
     * 
     * This std deviation is calculated based
     * on an finite sample of n observations.
     * 
     * takes an optional argument which is the multiplier to apply to the std dev
     */
    public static class StDev implements Reducer {

        protected int n = 0;
        protected double mean = 0;
        protected double M2 = 0;
        protected double delta = 0;
        
        protected double multiplier = 1.0;
        public void end() {
            
        }

        public void init(List<String> args) {
            n = 0;
            mean = 0;
            M2 = 0;
            delta = 0;
            
            if (args != null && args.size() > 0) {
                String a = args.get(0);
                if (a != null) {
                    try {
                        multiplier = Double.parseDouble(a);
                    } catch (NumberFormatException nfe) {
                        log.warn("bad argument passed to StDev: "+a);
                    }
                }
            } else {
                multiplier = 1.0;
            }
        }

        public void iter(Object o) {
            if (o != null && o instanceof Number) {
                double x = ((Number)o).doubleValue();
                n = n + 1;
                delta = x - mean;
                mean = mean + delta / n;
                M2 = M2 + delta * (x - mean);
            }
            
        }

        public Object value() {
            double variance = M2 / (n-1);
            double v = Math.sqrt(variance) * multiplier;
            return new Double (v);
        }
    }
    
    /**
     * calculate the population standard deviation
     * note this differs from the Standard Deviation
     * and is equivalent to the STDEVP function in Excel
     * 
     * This is 'sigma' and is calculated base on 
     * the entire population of observations
     * 
     */
    public static class StDevP extends StDev {
        @Override
        public Object value() {
            double variance = M2 / n;
            double v = Math.sqrt(variance) * multiplier;
            return new Double (v);
        }
    }

    public static class MovingAverage implements Mapper {
        private static Log log = LogFactory.getLog(MovingAverage.class);
        private int xCol;
        private int yCol;
        private int windowSize;
        
        private Metadata md;
        private List<String[]> data;
        
        private LinkedList<Double> samples;
        private int samplesSeen;
        private double runningTotal;
        
        private DataStream value;
        /**
         * take three arguments:
         *      - the x column index 
         *      - the y column index
         *      - and a number (which is the size of the window to take the moving average)
         */
        public void init(ISeries out, List<Arg> args) {
            if (args.size() != 2) {
                throw new IllegalArgumentException("Wrong number of arguments");
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
                            "null window for '" + out.getName() + "': " + args.get(1).getArgValue()
                            );
                }
                windowSize = args.get(1).asInt();
                samples = new LinkedList<Double>();
                runningTotal = 0.0;
//                System.err.println("****MOVING AVERAGE initialized with xCol = " + xCol +
//                        " yCol = " + yCol + " windowSize = " + windowSize);
            }
        }

        public DataStream begin(DataStream d) {
            //d = DataGrid.sortStream(d, xCol, false);

            if (d.getMetadata().getColumnInfo(xCol).getType() == DataType.DATE) {
                // if the x column is a date, be sure we sort that in ascending order first
                d = DataGrid.sortStream(d, xCol, false);
            } else {
                if (d.isResettable()) {
                    d.reset();
                }
            }
            
            Metadata m = d.getMetadata();
            ColumnInfo xColInfo = m.getColumnInfo(xCol);
            ColumnInfo yColInfo = m.getColumnInfo(yCol);
            md = new Metadata(2);
            md.setColumnInfo(1,xColInfo);
            md.setColumnInfo(2,yColInfo);
            data = new ArrayList<String[]>();
            

            return d;
        }

        public void end() {
            value = new StringDataStream(data,md,0,data.size());
        }

        public void iter(DataStream d) {
            String[] row = new String[2];
            try {
                row[0] = d.getString(xCol);
                
                Double v = d.getDouble(yCol);
                if (v != null) {
                    samplesSeen++;
                    runningTotal += v;
                    samples.add(v);

                    Double movingAvg;
                    if (samplesSeen < windowSize) {
                        row[1] = "";
                    } else {
                        if (samplesSeen > windowSize) {
                            runningTotal -= samples.remove(); // remove the first element from the sample list
                        }
                        movingAvg = runningTotal / windowSize;
                        row[1] = movingAvg.toString();
                    } 
                    data.add(row);
                } // else null values are ignored in the moving averages
 
            } catch (Exception e) {
                log.warn("error reading from data stream",e); 
            }
        }

        public DataStream value() {
            return value;
        }
        
    }
    
    
    
    public static class Scale implements Mapper {
        private static Log log = LogFactory.getLog(Scale.class);
        private int xCol;
        private int yCol;
        
        private double factor; // scale by how much
        
        private Metadata md;
        private List<String[]> data;
        
        private DataStream value;
        /**
         * take three arguments:
         *      - the x column index 
         *      - the y column index
         *      - and a number (the multiple to scale by)
         */
        public void init(ISeries out, List<Arg> args) {
            if (args.size() != 2) {
                log.error("Wrong number of arguments");
            } else {
                ISeries series = args.get(0).asSeries();
                if (series == null) {
                    throw new IllegalArgumentException(
                            "null series for '" + out.getName() + "': " + args.get(0).getArgValue()
                            );
                }
                xCol = series.getXColumn();
                yCol = series.getYColumn();
                if (args.get(1).asDouble() == null) {
                    throw new IllegalArgumentException(
                            "null scale for '" + out.getName() + "': " + args.get(1).getArgValue()
                            );
                }
                factor = args.get(1).asDouble();
            }
        }

        public DataStream begin(DataStream d) {
            Metadata m = d.getMetadata();
            ColumnInfo xColInfo = m.getColumnInfo(xCol);
            ColumnInfo yColInfo = m.getColumnInfo(yCol);
            md = new Metadata(2);
            md.setColumnInfo(1,xColInfo);
            md.setColumnInfo(2,yColInfo);
            data = new ArrayList<String[]>();
            
            return d;
        }

        public void end() {
            value = new StringDataStream(data,md,0,data.size());
        }

        public void iter(DataStream d) {
            String[] row = new String[2];
            try {
                row[0] = d.getString(xCol);

                Double v = d.getDouble(yCol);
                if (v == null) {
                    log.warn("Skipping null valued y column");
                }

                v = v * factor;
                
                row[1] = v.toString();
                data.add(row); 
            } catch (Exception e) {
                log.warn("error reading from data stream",e); 
            }
        }

        public DataStream value() {
            return value;
        }
        
    }
}
