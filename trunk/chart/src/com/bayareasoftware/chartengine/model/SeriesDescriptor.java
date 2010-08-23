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

/**
 * SeriesDescriptor describes a series in the chart
 *  it contains a reference to the DataSource object via the source field
 *  it also contains a index to the axis that the Series uses
 */
public class SeriesDescriptor extends BaseDescriptor implements ISeries {

    public static final String PROP_color="color";
    public static final String PROP_renderer="renderer";
    public static final String PROP_renderer_properties="rendererProperties";
    public static final String PROP_series_name_from_data="seriesNameFromData";
    public static final String PROP_x_column="xColumn";
    public static final String PROP_y_column="yColumn";
    public static final String PROP_z_column="zColumn";
    public static final String PROP_histogram_bin = "histogramBinSize";
    public static final String PROP_histogram_min = "histogramMin";
    public static final String PROP_histogram_max = "histogramMax";
    public static final String PROP_timePeriod = "timePeriod";
    public static final String PROP_error = "seriesError";
    
    private static final int DEFAULT_XCOLUMN = 1;
    private static final int DEFAULT_YCOLUMN = 2;
    private static final int DEFAULT_ZCOLUMN = -1;

    /**
     * the x/y/z columns mean x/y/z in most cases, but they can also be overloaded for some plot types:
     *     for PLOT_CATEGORY:    xColumn means the category string
     *                           yColumn means the numeric value
     *     for PLOT_GANTT:       xColumn means the task name
     *                           yColumn means the start date
     *                           zColumn means the end date
     *                           seriesNameFromData means the column that contains the series name
     */
    private int xColumn = DEFAULT_XCOLUMN;
    private int yColumn = DEFAULT_YCOLUMN;
    private int zColumn = DEFAULT_ZCOLUMN;
    
    private static final int DEFAULT_SERIES_NAME_FROM_DATA = -1; 
    // column in the result set to get the seriesName
    private int seriesNameFromData = DEFAULT_SERIES_NAME_FROM_DATA;
    
    
    private static final int DEFAULT_TIMEPERIOD = TimeConstants.TIME_UNKNOWN;
    // preferred time period size for this series, only
    // applicable to timeSeries of course
    private int timePeriod = DEFAULT_TIMEPERIOD;
    
    private static final double DEFAULT_HISTOGRAM_BIN_SIZE = 0.0;
    private static final double DEFAULT_HISTOGRAM_MIN = 0.0;
    private static final double DEFAULT_HISTOGRAM_MAX = 0.0;
    
    // the min,max,and bin size values for Histogram series
    private double histogramBinSize = DEFAULT_HISTOGRAM_BIN_SIZE;
    private double histogramMin = DEFAULT_HISTOGRAM_MIN;
    private double histogramMax = DEFAULT_HISTOGRAM_MAX;
    
    private String color;
    private String renderer;
    
    // some kind of problem or error currently with the series
    private transient boolean hasError;
    //private String rendererProperties;
    
    // a series can have its own renderer with its own render properties 
    // these props also masquerade as the seriesDescriptor itself when it comes to property change events
    private SimpleProps rendererProps = new SimpleProps();
    
//    private String name;

    public SeriesDescriptor() {
        rendererProps.setPropChangeSource(this);
    }
    
    public SeriesDescriptor(String name, String sourceId) {
        setName(name);
        setSource(sourceId);
        rendererProps.setPropChangeSource(this);
    }
    
//    public SeriesDescriptor(String sourceId, int axisIndex, boolean visible) {
//        setSource(sourceId);
//        setAxisIndex(axisIndex);
//        setVisible(visible);
//        rendererProps.setPropChangeSource(this);
//    }
    
    public String toString() {
        SimpleProps p = serializeToProps(null,null);
        return p.toString();
    }

    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    public SimpleProps serializeToProps(SimpleProps sp, String prefix) {

        sp = super.serializeToProps(sp, prefix);
        
        if (getColor() != null)
            sp.set(prefix,PROP_color,getColor());
        if (getRenderer() != null)
            sp.set(prefix,PROP_renderer,getRenderer());
        
//        if (getRendererProperties() != null)
//            sp.set(prefix,PROP_renderer_properties,getRendererProperties());

        if (rendererProps.size() > 0) {
            sp.mergeWithPrefix(SimpleProps.prefixIt(prefix, PROP_renderer_properties),rendererProps);
        }
        if (getSeriesNameFromData() != DEFAULT_SERIES_NAME_FROM_DATA) 
            sp.set(prefix,PROP_series_name_from_data,String.valueOf(getSeriesNameFromData()));
        
        if (getXColumn() != DEFAULT_XCOLUMN) 
            sp.set(prefix,PROP_x_column,String.valueOf(getXColumn()));
        
        if (getYColumn() != DEFAULT_YCOLUMN)
            sp.set(prefix,PROP_y_column,String.valueOf(getYColumn()));
        
        if (getZColumn() != DEFAULT_ZCOLUMN)
            sp.set(prefix,PROP_z_column,String.valueOf(getZColumn()));
        
        if (getTimePeriod() != DEFAULT_TIMEPERIOD)
            sp.set(prefix,PROP_timePeriod,String.valueOf(getTimePeriod()));
        
        if (getHistogramBinSize() != DEFAULT_HISTOGRAM_BIN_SIZE)
            sp.set(prefix,PROP_histogram_bin,String.valueOf(getHistogramBinSize()));
        
        if (getHistogramMin() != DEFAULT_HISTOGRAM_MIN)
            sp.set(prefix,PROP_histogram_min,String.valueOf(getHistogramMin()));
        
        if (getHistogramMax() != DEFAULT_HISTOGRAM_MAX)
            sp.set(prefix,PROP_histogram_max,String.valueOf(getHistogramMax()));

        return sp;
    }

    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = super.deserializeFromProps(p, prefix);
        SimpleProps props = p.subset(prefix);
        
        String key;
        try {
            key = PROP_name;
            if (props.containsKey(key)) {
                setName(props.get(key));
                props.remove(key);
                ret = true;
            }

            key = PROP_color;
            if (props.containsKey(key)) {
                setColor(props.get(key));
                props.remove(key);
                ret = true;
            }

            key = PROP_renderer;
            if (props.containsKey(key)) {
                setRenderer(props.get(key));
                props.remove(key);
                ret = true;
            }

//            key = PROP_renderer_properties;
//            if (props.containsKey(key)) {
//                setRendererProperties(props.get(key));
//                props.remove(key);
//                ret = true;
//            }
            
            SimpleProps rp = props.subset(PROP_renderer_properties); 
            if (rp.size() > 0) {
                this.rendererProps = rp;
                rendererProps.setPropChangeSource(this);
            }
            
            key = PROP_series_name_from_data;
            if (props.containsKey(key)) {
                setSeriesNameFromData(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_x_column;
            if (props.containsKey(key)) {
                setXColumn(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_y_column;
            if (props.containsKey(key)) {
                setYColumn(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_z_column;
            if (props.containsKey(key)) {
                setZColumn(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_timePeriod;
            if (props.containsKey(key)) {
                setTimePeriod(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_histogram_bin;
            if (props.containsKey(key)) {
                setHistogramBinSize(Double.parseDouble(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_histogram_max;
            if (props.containsKey(key)) {
                setHistogramMax(Double.parseDouble(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_histogram_min;
            if (props.containsKey(key)) {
                setHistogramMin(Double.parseDouble(props.get(key)));
                props.remove(key);
                ret = true;
            }
        } catch (Exception e) {
                e.printStackTrace();
                ret = false;
                // FIXME: how to handle parsing exceptions?
        }
        return ret;
    }
    
     /**
     * The color or paint to use for this series.  If null, a color will
     * be automatically assigned to the series during rendering.
     */
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        String old = this.color;
        this.color = color;
        this.fireChange(PROP_color, old, this.color);
    }

    /**
     * The type of renderer to use for this series.  If not set, a series
     * will be rendered with the default renderer of the enclosing chart.
     */
    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        String old = this.renderer;
        this.renderer = renderer;
        this.fireChange(PROP_renderer, old, this.renderer);
    }

    public int getSeriesNameFromData() { 
        return seriesNameFromData; 
    }
    
    public void setSeriesNameFromData(int i) { 
         int old = this.seriesNameFromData;
         this.seriesNameFromData = i;
         this.fireChange(PROP_series_name_from_data, new Integer(old), new Integer(this.seriesNameFromData));
    }

    /**
     * @return renderProperties for this seres.  never NULL, but may be size() == 0
     */
    public SimpleProps getRendererProps() {
        return rendererProps;
    }

    /**
     * set the renderer properties.   CANNOT be NULL.
     * @param sp
     */
    public void setRendererProps(SimpleProps sp) {
        SimpleProps old = this.rendererProps;
        this.rendererProps = sp;
        if (rendererProps != null) {
            rendererProps.setPropChangeSource(this);
        }
        this.fireChange(PROP_renderer_properties,old,this.rendererProps);
    }
    
//    public String getRendererProperties() {
//        return rendererProperties;
//    }
//
//    public void setRendererProperties(String rendererProperties) {
//        String old = this.rendererProperties;
//        this.rendererProperties = rendererProperties;
//        this.fireChange(PROP_renderer_properties,old,this.rendererProperties);
//    }

    
    /**
     * Column in the result set of the X value (domain), or 'category'
     * in category plots, or time domain    
     */
    public int getXColumn() {
        
        return xColumn;
    }

    public void setXColumn(int column) {
        int old = this.xColumn;
        this.xColumn = column;
        this.fireChange(PROP_x_column,new Integer(old),new Integer(this.xColumn));
    }

    /**
     *  Column in the result set of the Y value (Range) or 'value' in category plots
     */
    public int getYColumn() {
        return yColumn;
    }

    public void setYColumn(int column) {
        int old = this.yColumn;
        yColumn = column;
        this.fireChange(PROP_y_column,new Integer(old),new Integer(this.yColumn));
    }

    /**
     *  Column in the result set of the Z value, where applicable.  For example,
     *  when a series uses a bubble renderer, the Z column describes the diameter
     *  of the bubble.
     */
    public int getZColumn() {
        return zColumn;
    }

    public void setZColumn(int column) {
        int old = this.zColumn;
        zColumn = column;
        this.fireChange(PROP_z_column, new Integer(old), new Integer(this.zColumn));
    }

    /**
     * The size of each bin (bucket) for a series used in a histogram plot
     */
    public double getHistogramBinSize() {
        return histogramBinSize;
    }

    public void setHistogramBinSize(double h) {
        double old = this.histogramBinSize;
        this.histogramBinSize = h;
        if (old != histogramBinSize) {
            fireChange(PROP_histogram_bin, new Double(old), new Double(h));
        }
    }

    /**
     * The smallest bin (bucket) for a series used in a histogram plot
     */
    public double getHistogramMin() {
        return histogramMin;
    }

    public void setHistogramMin(double hm) {
        double old = this.histogramMin;
        this.histogramMin = hm;
        fireChange(PROP_histogram_min, new Double(old), new Double(hm));
    }

    /**
     * The largest bin (bucket) for a series used in a histogram plot
     */
    public double getHistogramMax() {
        return histogramMax;
    }

    public void setHistogramMax(double hm) {
        double old = this.histogramMax;
        this.histogramMax = hm;
        fireChange(PROP_histogram_max, new Double(old), new Double(hm));
    }

    /**
     * Sets the preferred time period of a timeseries (e.g., HOUR,DAY,MONTH, etc).
     * @see com.bayareasoftware.chartengine.model.TimeConstants
     */
    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int tp) {
        int old = this.timePeriod;
        this.timePeriod = tp;
        fireChange(PROP_timePeriod, new Integer(old), new Integer(this.timePeriod));
    }
    
    public boolean equals(Object o) {
        if (o == null)
            return false;
        
        if (! (o instanceof SeriesDescriptor))
            return false;

        if (! super.equals(o))
            return false;
        
        SeriesDescriptor other = (SeriesDescriptor) o;
        
        if (!objEquals(color, other.color) ||
            !objEquals(renderer, other.renderer) ||
            //!objEquals(rendererProperties, other.rendererProperties))
            !objEquals(rendererProps, other.rendererProps))
            return false;
        
        if (xColumn != other.xColumn || 
            yColumn != other.yColumn ||
            zColumn != other.zColumn ||
            timePeriod != other.timePeriod ||
            seriesNameFromData != other.seriesNameFromData)
            return false;
            
        return true;
        
            
    }
    
    public int hashCode() {
        int ret = super.hashCode();
        ret ^= xColumn;
        ret ^= yColumn;
        ret ^= timePeriod;
        ret ^= Math.round(histogramBinSize);
        ret ^= Math.round(histogramMax);
        ret ^= Math.round(histogramMin);
        ret ^= seriesNameFromData;
        if (this.color != null)
            ret ^= color.hashCode();
        if (this.renderer != null)
            ret ^= renderer.hashCode();
    //        if (this.rendererProperties != null)
    //            ret ^= rendererProperties.hashCode();
        if (this.rendererProps != null) {
            ret ^= rendererProps.hashCode();
        }
        
        return ret;
    }
    
    public boolean isError() { return hasError; }
    public void setError(boolean b) {
        boolean old = hasError;
        hasError = b;
        fireChange(PROP_error, old, hasError);
    }
    
}

