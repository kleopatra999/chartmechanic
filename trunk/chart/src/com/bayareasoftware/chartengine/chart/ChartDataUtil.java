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
package com.bayareasoftware.chartengine.chart;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.ds.ScalarDataStream;
import com.bayareasoftware.chartengine.ds.TimeFilterDataStream;
import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.functions.Joiner;
import com.bayareasoftware.chartengine.functions.Mapper;
import com.bayareasoftware.chartengine.functions.Reducer;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.BaseDescriptor;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.ISeries;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

/**
 * a collection of utilities for processing datastreams
 * 
 * placed in this package so it can be be used both by Core 
 * and by the CreateImageTest unit tests
 */
public class ChartDataUtil {
    private static Log log = LogFactory.getLog(ChartDataUtil.class);
    
    /**
     * limit the data stream by date interval
     * parses the parameters and then creates a TimeFilterDataStream that reflects the new stream 
     * @param r
     * @param sd  
     * @param sstr            - start date string
     * @param estr            - end date string
     * @return
     */
    public static DataStream limitStreamByDateInterval(DataStream r, SeriesDescriptor sd, String sstr, String estr) {
        DataStream ret = r;
        
        Date startDate = null;
        Date endDate = null;
        DateRecognizer dr = new DateRecognizer();
        if (estr != null) {
            dr.reset();
            dr.parse(estr);
            if (!dr.failed()) {
                try {
                    endDate = dr.getSimpleDateFormat().parse(estr);
                } catch (ParseException pe) { }
            }
        }

        if (sstr != null) {
            if (isIntervalDate(sstr)) {
                if (endDate == null) {
                    // if no endDate is specified, then attempt to determine the endDate from reading the stream 
                    // if the stream is resettable.
                    // functionally correct, but not very efficient.
                    if (r.isResettable()) {
                        r.reset();
                        int xColumn = sd.getXColumn();
                        try {
                            while (r.next()) {
                                Date d = r.getDate(xColumn);
                                if (d != null) {
                                    if (endDate == null || d.after(endDate)) {
                                        endDate = d;
                                    } 
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        r.reset();
                    }
                }
                
                // hardcode some specify date handling for date ranges that are offsets from the end-date, if any
                // understands formats like -<n>{m|d|y}
                if (endDate != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(endDate);
                    int field = -1;
                    
                    if (sstr.endsWith("d")) {
                        field = Calendar.DAY_OF_YEAR;
                    } else if (sstr.endsWith("m")) {
                        field = Calendar.MONTH;
                    } else if (sstr.endsWith("y")) {
                        field = Calendar.YEAR;
                    }    
                
                    int val = Integer.parseInt(sstr.substring(0,sstr.length()-1));
                    cal.add(field,val);
                    startDate = cal.getTime();

                }
            } else {
                dr.parse(sstr);
                if (!dr.failed()) {
                    try {
                        startDate = dr.getSimpleDateFormat().parse(sstr);
                    } catch (ParseException pe) { }
                }
            }
            
        }
        if (startDate != null || endDate != null) {
            ret = new TimeFilterDataStream(r,startDate,endDate,sd.getXColumn());
        }
        
        return ret;
    }
    
  
    /**
     * compute the values for a derived datastream
     * 
     * @param ci       - the ChartInfo
     * @param bd       - the base descriptor (either series or marker)
     * @param map      - a map of sid -> DataStream.  Should include any streams that bd is dependent on
     * @return         - a new DataStream that represents the values for bd
  * @throws Exception 
     */
    public static DataStream computeDerivedDataStream(ChartInfo ci, BaseDescriptor bd, Map<Integer,DataStream> map)
    {
        DataStream ret = null;
        
        Object o = BuiltInFunctions.makeFunction(bd.getFunc());
        if (o == null) {
            log.error("Function named: " + bd.getFunc() + " not found");
        } else if (o instanceof Joiner) {
            Joiner joiner = (Joiner) o;
            List<Arg> args = bd.getArgs();
            // line up the ISeries objects
            for (Arg a : args) {
                if (a.getArgType() == ArgType.SID) {
                    SeriesDescriptor sd = (SeriesDescriptor) ci.getBaseDescriptorBySID(a.asInt());
                    a.setSeries(sd);
                }
            }
            joiner.init((ISeries)bd, map, bd.getArgs());
            ret = joiner.value();
        } else if (o instanceof Mapper) {
            Mapper mapper = (Mapper)o;
            List<Arg> args = bd.getArgs();
            
            DataStream inputDataStream = null;
            SeriesDescriptor sd = null;
            
            for (Arg a: bd.getArgs()) {
                ArgType argType = a.getArgType();
                if (ArgType.SID.equals(argType)) {
                    String sid = a.getArgValue();
                    BaseDescriptor b = ci.getBaseDescriptorBySID(Integer.decode(sid));
                    if (b instanceof SeriesDescriptor) {
                        sd = (SeriesDescriptor)b;
                        a.setSeries(sd);
                        inputDataStream = map.get(sd.getSid());
                        if (inputDataStream == null) {
                            // should never happen
                            log.error("SHOULD NEVER HAPPEN: could not find DataStream for series: " + sd.getName() + " id: " + sd.getSid());
                            throw new RuntimeException("Could not find DataSource for series: " + sd.getName() + " id: " + sd.getSid());
                        }
                        if (inputDataStream.isResettable())
                            inputDataStream.reset();
                    } else {
                        // TODO: allow markers derived from markers?
                    }
                }
            }
            mapper.init((ISeries)bd, args);
            inputDataStream = mapper.begin(inputDataStream);
            if (sd != null && inputDataStream != null) {
                try {
                    while (inputDataStream.next()) {
                        mapper.iter(inputDataStream);
                    }
                } catch (Exception e) {
                    log.error("Failed while iterating through data stream for series: " + sd.getName() + " id: " + sd.getSid(),e);
                } finally {
                    inputDataStream.close();
                }
                mapper.end();
                ret = mapper.value();
            } 
        } else if (o instanceof Reducer) {
            // non-mapper functions like aggregate functions for Markers are handled elsewhere
            Reducer reducer = (Reducer)o;
            
            List<String> args = new ArrayList<String>();
            
            DataStream inputDataStream = null;
            SeriesDescriptor sd = null;

            int yColumn = -1;
            int xColumn = -1;
            for (Arg a: bd.getArgs()) {
                // we assume that we only have one arg of type ARGTYPE_SID
                // and the other args of of type ARGTYPE_VALUE
                ArgType argType = a.getArgType();
                if (ArgType.SID.equals(argType)) {
                    String sid = a.getArgValue();
                    BaseDescriptor b = ci.getBaseDescriptorBySID(Integer.decode(sid));
                    if (b instanceof SeriesDescriptor) {
                        sd = (SeriesDescriptor)b;
                        xColumn = sd.getXColumn();
                        yColumn = sd.getYColumn();
//                        args.add(String.valueOf(sd.getXColumn()));
//                        args.add(String.valueOf(sd.getYColumn()));
                        inputDataStream = map.get(sd.getSid());
                        if (inputDataStream == null) {
                            // should never happen
                            log.error("SHOULD NEVER HAPPEN: could not find DataStream for series: " + sd.getName() + " id: " + sd.getSid());
                            throw new RuntimeException("Could not find DataSource for series: " + sd.getName() + " id: " + sd.getSid());
                        }
                        if (inputDataStream.isResettable())
                            inputDataStream.reset();
                    } else {
                        // TODO: allow markers derived from markers?
                    }
                } else /*if (Arg.ARGTYPE_VALUE.equals(a.getArgType()))*/ {
                    args.add(a.getArgValue());
                }
            }
                
            reducer.init(args);
            if (sd != null && inputDataStream != null&& yColumn != -1) {
                try {
                    while (inputDataStream.next()) {
                        reducer.iter(inputDataStream.getDouble(yColumn));
                    }
                } catch (Exception e) {
                    log.error("Failed while iterating through data stream for series: " + sd.getName() + " id: " + sd.getSid(),e);
                } finally {
                    inputDataStream.close();
                }
                reducer.end();
                Object endValue = reducer.value();
                ret = new ScalarDataStream(endValue);
                ret.getMetadata().setColumnName(1,"Computed Value");
            } 

        } else {
            log.error("Function named: " + bd.getFunc() + " is not a proper transformation function");
        } 

        
        return ret;
    }
    
    
    private static boolean isIntervalDate(String s) {
        return s.startsWith("-") && (s.endsWith("d") || s.endsWith("m") || s.endsWith("y"));
    }

    
}
