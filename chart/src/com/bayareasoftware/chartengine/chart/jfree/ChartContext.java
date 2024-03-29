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
package com.bayareasoftware.chartengine.chart.jfree;

import static com.bayareasoftware.chartengine.model.ChartConstants.CM_PROP_PREFIX;
import static com.bayareasoftware.chartengine.model.ChartConstants.MAX_RANGE_AXES;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.MarkerValue;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.PlotType;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.util.DateUtil;

/**
 * ChartContext contains state related to chart creation.  It holds all the actual data, mapping of series to data, etc. that
 * is produced in the course of creating a chart.  It differs from ChartInfo in that ChartInfo is the *logical* chart specification
 * It is also not the final result (the JFreeChart object), but it provides the context for creating the JFreeChart object 
 */
public class ChartContext {
    protected Log log = LogFactory.getLog(ChartContext.class);

    private ChartInfo ci;
    private DSMap dsmap;
    private List<MarkerValue> markerValues;
    
    private class AxisInfo {
        boolean visible;
        String  axisType;
        AxisLocation location;
    } 
    
    protected static class DSValueKey {
        final int dsHash;
        final String seriesName;
        final double value;
        final Comparable xValue;
        public DSValueKey(Dataset ds, String sname, Comparable xValue, double value) {
            if (xValue == null)
                throw new IllegalArgumentException(
                        "null xValue"
                        );
            dsHash = System.identityHashCode(ds);
            seriesName = sname;
            this.xValue = xValue;
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            int ret = dsHash;
            if (seriesName != null) ret ^= seriesName.hashCode();
            ret ^= (int) value;
            ret ^= xValue.hashCode();
            return ret;
        }
        
        @Override
        public boolean equals(Object o) {
            DSValueKey other = (DSValueKey) o;
            if (other.dsHash != dsHash) return false;
            if (other.value != value) return false;
            if (!xValue.equals(other.xValue)) return false;
            if (other.seriesName == null) return seriesName == null;
            return seriesName.equals(other.seriesName);
        }
    }
        
    protected static DSValueKey vkey(Dataset ds, String series, Comparable xValue, double value) {
        return new DSValueKey(ds, series, xValue, value);
    }

    public static final String NULL_URL = "";

    /** get the url for a given imagemap item */
    public String getImageMapUrl(Dataset ds, String seriesName, Comparable xValue,
            double value) {
        DSValueKey vkey = vkey(ds, seriesName, xValue, value);
        String ret = mapMap.get(vkey);
        if (NULL_URL.equals(ret))
            ret = null;
        return ret;
    }
    
    protected Map<DSValueKey,String> mapMap;
    
    private AxisInfo rangeaxes[];
    
    // map of datasource id to groups of functions that need to operate on that datasource
    // for purposes of calculating aggregates
//    private Map<String,ReducerGroup> fnMap = new HashMap<String, ReducerGroup>();

    
    /**
     * 
     * @param ci
     * @param sMap   - the map of descriptor sid's to DataStreams that supply all the data for the chart series and markers
     * @throws Exception
     */
    public ChartContext(ChartInfo ci, Map<Integer,DataStream> sMap) throws Exception {
        this.ci = ci;
        mapMap = new HashMap();
        this.dsmap = createDatasets(sMap);
        this.markerValues = createMarkerValues(sMap);
        
        rangeaxes = new AxisInfo[MAX_RANGE_AXES];
        for (int i=0;i<MAX_RANGE_AXES;i++) {
            rangeaxes[i] = new AxisInfo();
            rangeaxes[i].visible = ci.isRangeAxisVisible(i);
        }
        
        SimpleProps chartProps = ci.getProps();
        for (int i = 0; i < MAX_RANGE_AXES; i++) {
            String rangeAxisPrefix = ChartInfo.getRangeAxisPropertyPrefix(i);
            if (!rangeaxes[i].visible) {
                continue;
            }
            String locationProp = rangeAxisPrefix + "." + CM_PROP_PREFIX + "axisLocation";
            String typeProp = rangeAxisPrefix + "." + CM_PROP_PREFIX + "axisType";
            rangeaxes[i].axisType = chartProps.get(typeProp);
            if (rangeaxes[i].axisType == null)
                rangeaxes[i].axisType = chartProps.get(rangeAxisPrefix + ".axisType");
            String locationVal = chartProps.get(locationProp);
            if (locationVal == null)
                locationVal = chartProps.get(rangeAxisPrefix + ".axisLocation");
            if (locationVal != null) {
                rangeaxes[i].location = (AxisLocation) BeanUtil.convertType(locationVal, AxisLocation.class); 
            }
        }
    }

    public MarkerValue getMarkerValue(int i) {
        return markerValues.get(i);
    }
    
//    public Dataset getDefaultDataset() {
//        return dsmap.getDefaultDataset();
//    }

    public boolean isRangeAxisVisible(int index) {
        boolean res = false;
        if (index >=0 && index < MAX_RANGE_AXES)
            res = rangeaxes[index].visible;
        return res;
    }
    
    public AxisLocation getRangeAxisLocation(int index) {
        AxisLocation res = null;
        if (index >=0 && index < MAX_RANGE_AXES)
            res = rangeaxes[index].location;
        return res;
    }
    
    public String getRangeAxisType(int index) {
        String res = null;
        if (index >=0 && index < MAX_RANGE_AXES)
            res = rangeaxes[index].axisType;
        return res;

    }
    
    public int getDatasetCount() {
        return dsmap.getDatasetCount();
    }
    
    public Dataset getDataset(int index) {
        return dsmap.getDataset(index);
    }
    
    public int getRangeAxisForDataset(int index) {
        return dsmap.getRangeAxisForDataset(index);
    }
    
    public int getIndexOfDataset(Dataset ds) {
        return dsmap.getIndexOfDataset(ds);
    }
    
    public String getRendererForSeries(int index) {
        String res = null;
        SeriesDescriptor sd = ci.getSeriesDescriptor(index);
        if (sd != null)
            res = sd.getRenderer();
        return res;
    }
    
    public String getRendererForDataset(int index) {
        return dsmap.getRendererForDataset(index);
    }
    
    public SimpleProps getRendererPropsForDataset(int index) {
        return dsmap.getRendererPropsForDataset(index);
    }
    
    public String getColor(int index) {
        String res = null;
        SeriesDescriptor sd = ci.getSeriesDescriptor(index);
        if (sd != null)
            res = sd.getColor();
        return res;
    }

    public SeriesDescriptor getSeries(int index) {
        return ci.getSeriesDescriptor(index);
    }
    
    public int getSeriesCount() {
      return ci.getSeriesCount();
    }
    
    public boolean isSeriesVisible(int index) {
        boolean res = false;
        SeriesDescriptor sd = ci.getSeriesDescriptor(index);
        if (sd != null)
            res = (sd.isVisible() && !sd.isError());
        return res;
    }
    
    public Dataset getDatasetForSeries(SeriesDescriptor sd) {
        return dsmap.getDatasetForSeries(sd);
    }
    
    /**
     * create a map of series to datasets
     * 
     * @param ci                 - ChartInfo
     * @param sMap               - map of series name to DataStream
     * @param paramValues        - parameter values
     * @return                   a map of series -> DataSets
     * @throws Exception
     */
    private DSMap createDatasets(Map<Integer,DataStream> sMap)
            throws Exception {
        
        Producer prod = null;
        PlotType ptype = ci.getPlotType();
        
        if (ptype == null) {
            throw new RuntimeException("Unexpected null plot type");
        }
        switch (ptype) {
        case PLOT_XY:
            prod = new XYProducer();
            break;
        case PLOT_TIME:
            prod = new TimeProducer(ci.getTimePeriod());
            break;
        case PLOT_CATEGORY:
            prod = new CategoryProducer();
            break;
        case PLOT_GANTT:
            prod = new GanttProducer();
            break;
        case PLOT_PIE:
        case PLOT_PIE3D:
        case PLOT_RING:
            prod = new PieProducer();
            break;
        case PLOT_HISTOGRAM:
            prod = new HistogramProducer(ci);
            break;
        }
        
        prod.setUrlMap(mapMap);
        
        DSMap dsm = new DSMap(prod, ci);
        
        for (SeriesDescriptor sd : ci.getSeriesDescriptors()) {
            DataStream r = null;
            if (!sd.isVisible()) {
                continue;
            }
            r = sMap.get(sd.getSid());
            Metadata md = r.getMetadata();
            if (r != null) {
                try {
                    Dataset d = dsm.getDatasetForSeries(sd);
                    if (d != null) {
                        // ///////////////////
                        // processing done at the beginning of the datastream
                        // ///////////////////
                        prod.beginSeries(d, sd, r);

                        // ///////////////////
                        // main loop, once per row in the datastream
                        // ///////////////////
                        if (r.isResettable())
                            r.reset();
                        
                        while (r.next()) {
                            String seriesName = prod.populateSingle(d, sd, r);
                        }

                        // ///////////////////
                        // processing done at the end of the datastream
                        // ///////////////////
                        d = prod.endSeries(d, sd);
                        dsm.setDatasetForSeries(sd, d);
                    } else {
                        throw new RuntimeException("no dataset for series "
                                + sd.getName());
                    }
                } finally {
                    r.close();
                }
            }
        }
        return dsm;
    }
    
    protected static String translateLinkExpression(DataStream stream, Metadata md,
            String sname, String link) throws Exception {
        if (link == null) return "";
        if (link.contains("{series}")) {
            if (sname == null) sname = "";
            sname = URLEncoder.encode(sname);
            link = link.replace("{series}", sname);
        }
        DateFormat df = null;
        for (int i = 1; i <= md.getColumnCount(); i++) {
            String rep = "{" + i + "}";
            if (link.contains(rep)) {
                int type = md.getColumnType(i);
                String str;
                // FIXME: standardize, document date & number formatting...
                if (type == DataType.DATE) {
                    // FIXME: could use original dateformat for this column here too...
                    if (df == null) df = DateUtil.createDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = stream.getDate(i);
                    str = d != null ? df.format(d) : "";
                } else {
                    Object o = stream.getObject(i);
                    str = o == null ? "": o.toString();
                }
                str = URLEncoder.encode(str);
                link = link.replace(rep, str);
            }
        }
        return link;
    }
    
    private List<MarkerValue> createMarkerValues(Map<Integer,DataStream> sMap)
    throws Exception {
        List<MarkerValue> ret = new ArrayList<MarkerValue>();
        for (MarkerDescriptor md : ci.getMarkers()) {
            boolean isInterval = md.isInterval();
            
            DataStream r = sMap.get(md.getSid());
            MarkerValue mv = new MarkerValue();
            if (r != null) {
                try {
                    r.reset();
                    if (md.getFunc() != null) {
                        Double numericVal = r.getDouble(1);
                        if (numericVal != null) {
                            mv.addSingleValue(numericVal);
                        }
                    } else if (md.getSource() != null) {
                        // marker data comes from another data source
                        Metadata mdata = r.getMetadata();
                        int col1_index = MarkerDescriptor.DEFAULT_COLUMN1; // always assume that we are taking data from column 1 and 2
                        int column1Type = mdata.getColumnType(col1_index);

                        if (isInterval) {
                            int col2_index = MarkerDescriptor.DEFAULT_COLUMN2;
                            int column2Type = mdata.getColumnType(col2_index);
                            while (r.next()) {
                                Double v1 = getMarkerValueFromColumn(r, col1_index,
                                        column1Type);
                                Double v2 = getMarkerValueFromColumn(r, col2_index,
                                        column2Type);
                                if (v1 != null && v2 != null)
                                    mv.addIntervalValues(v1, v2);
                            }
                        } else {
                            // single values only
                            while (r.next()) {
                                Double v1 = getMarkerValueFromColumn(r, col1_index,
                                        column1Type);
                                if (v1 != null) 
                                    mv.addSingleValue(v1);
                            }
                        }
                    } 
                } finally {
                    r.close();
                }
            } else {
                double[] vals = md.getValues();
                if (vals != null && vals.length > 0) {
                    // hardcoded values for markers
                    if (isInterval) {
                        for (int i = 0; i < vals.length - 1; i+=2) {
                            mv.addIntervalValues(vals[i], vals[i+1]);
                        }
                    } else {
                        // single value
                        mv.addSingleValue(vals[0]);
                    }
                } else {
                    log.warn("Missing data stream or hardcoded values for marker descriptor: " + md);
                }
            }
            
            ret.add(mv);
        }
        return ret;
    }

    private Double getMarkerValueFromColumn (DataStream r, int colIndex, int colType) throws Exception {
        Double v1 = null;
        if (colType == DataType.DATE) {
            Date d = r.getDate(colIndex);
            if (d != null) {
                v1 = (double)d.getTime();
            }
        } else if (colType == DataType.DOUBLE) {
            v1 = r.getDouble(colIndex);
        } else if (colType == DataType.INTEGER) {
            v1 = (double) (r.getInt(colIndex));
        } else {
            throw new Exception("can't coerce marker data of type: " + colType + " in col: " + colIndex + " to a double"); 
        }
        return v1;
    }
    

}
