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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MarkerDescriptor describes a marker in the chart
 * it contains a reference to the DataSource object via the source field
 * the datasource referred to must have date or double data
 * 
 * as well as marker chart-related properties
 * 
 */
@SuppressWarnings("serial")
public class MarkerDescriptor extends BaseDescriptor {
    public static final String PROP_range = "range";
    public static final String PROP_markerType = "markerType";
    public static final String PROP_markerProps = "markerProps";
    public static final String PROP_column1 = "column1";
    public static final String PROP_column2 = "column2";
    public static final String PROP_values = "values";
    public static final String PROP_aggregate = "aggregate";
    public static final String PROP_layer = "layer";
    public static final String PROP_error = "markerError";
    
    
    // some specific properties for markers we can about.  they are not stored as fields
    // but in markerprops
    public static final String PROP_label = "label";
    public static final String PROP_paint = "paint";
    public static final String PROP_alpha = "alpha";
    
    // whether or not to use a label for an interval marker true|false
    public static final String PROP_labelVisible = "labelVisible";
        
    public static final int MARKER_TYPE_UNKNOWN = 0;
    /**
     * A scalar date marker
     */
    public static final int MARKER_TYPE_DATE = 1;
    /**
     * An interval marker describing a ranges of dates
     * For example, periods of recession are commonly represented
     * as interval markers on economic charts. 
     */
    public static final int MARKER_TYPE_DATE_INTERVAL = 2;
    /**
     * A scalar numeric marker
     */
    public static final int MARKER_TYPE_NUMERIC = 3;
    /**
     * An interval of numeric values
     */
    public static final int MARKER_TYPE_NUMERIC_INTERVAL = 4;
    
//    private static final int DEFAULT_AXISINDEX = 0;
//    private static final boolean DEFAULT_VISIBLE = true;
    private static final boolean DEFAULT_ISRANGE = false;
    private static final int DEFAULT_MARKERTYPE = MARKER_TYPE_UNKNOWN;
    
    
    // default columns for pulling out interval data from another data source
    public static final int DEFAULT_COLUMN1 = 1;
    public static final int DEFAULT_COLUMN2 = 2;
    
    
    private SimpleProps markerProps = new SimpleProps();
    
    private int markerType = DEFAULT_MARKERTYPE;
    
    // on range axis or domain axis?
    private boolean range = DEFAULT_ISRANGE;
    
    // from the data source, the marker values can come from two columns
    // either column1 only if it's an numeric/date value
    // or the pair (column1,column2) if it's an interval value
    private int column1 = DEFAULT_COLUMN1;
    private int column2 = DEFAULT_COLUMN2;
    // inline values, separated by VALUE_SEP
    private String values;
    // layer: FOREGROUND or BACKGROUND
    private String layer;
    // label visible true | false
    private boolean labelVisible;
    
    public static final char VALUE_SEP = '|';
    
    private String aggregate;
    
 // some kind of problem or error currently with the marker
    private transient boolean hasError;    
    
    
    public SimpleProps getMarkerProps() {
        return markerProps;
    }
    
    public void setMarkerProps(SimpleProps p) {
        markerProps = p;
        fireChange(PROP_markerProps, null, markerProps);
    }

    public int getType() { return markerType; }
    
    /**
     * the marker type should be oneof MARKER_TYPE_{DATE,NUMERIC,INTERVAL} 
     * @param t
     */
    public void setType(int t) {
        int old = this.markerType;
        markerType = t;
        fireChange(PROP_markerType,new Integer(old),new Integer(markerType));
    }
    
    /**
     * FOREGROUND or BACKGROUND
     * @return
     */
    public String getLayer() {
        return layer;
    }
    public void setLayer(String l) {
        String old = layer;
        layer = l;
        fireChange(PROP_layer, old, layer);
    }
    
    /**
     * interval label visible true|false
     * @return
     */
    public boolean isLabelVisible() {
        return labelVisible;
    }
    public void setLabelVisible(boolean b) {
        boolean old = labelVisible;
        labelVisible = b;
        fireChange(PROP_labelVisible, old, labelVisible);
    }
    @Deprecated
    public String getAggregate() {
        return aggregate;
    }
    
    @Deprecated
    public void setAggregate(String agg) {
        String old = aggregate;
        this.aggregate = agg;
        fireChange(PROP_aggregate, old, agg);
    }
    
    public String getValuesAsString() {
        return values;
    }
    public void setValuesAsString(String values) {
        String old = this.values;
        this.values = values;
        fireChange(PROP_values,old,values);
    }
    public Date[] getDateValues() {
        double[] nums = getValues();
        Date[] ret = new Date[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ret[i] = new Date((long)nums[i]);
        }
        return ret;
    }
    public double[] getValues() {
        String valString = getValuesAsString();
        if (valString == null) {
            return null;
        }
        List<Double> vals = new ArrayList<Double>();
        String[] s = StringUtil.splitCompletely(valString, VALUE_SEP, true);
        for (int i = 0; i < s.length; i++) {
            String val = s[i];
            if (val == null || val.length() == 0) {
                continue;
            }
            try {
                Double d = Double.valueOf(val);
                vals.add(d);
            } catch (NumberFormatException nfe) { 
            }
        }
        double[] ret = new double[vals.size()];
        for (int i = 0; i < ret.length; i++) {
            Double d = vals.get(i);
            ret[i] = d.doubleValue();
        }
        return ret;
    }
    
    public void setValues(double[] vals) {
        if (vals == null) {
            values = null;
            return;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < vals.length; i++) {
            sb.append(vals[i]);
            if (i < vals.length - 1) {
                sb.append(VALUE_SEP);
            }
        }
        setValuesAsString(sb.toString());
    }
    
    public String toString() {
        return serializeToProps(null,null).toString();
    }

    public boolean equals(Object o) {
        
        if (o == null)
            return false;

        if (! super.equals(o))
            return false;
        
        if (! (o instanceof MarkerDescriptor))
            return false;

        MarkerDescriptor other = (MarkerDescriptor) o;
        
        if (!objEquals(getName(), other.getName()))
            return false;
        
        if (range != other.range ||
            markerType != other.markerType ||
            column1 != other.column1 ||
            column2 != other.column2)
            return false;
            
        if (markerProps != null) {
            if (other.markerProps != null) {
                return markerProps.toString().equals(other.markerProps.toString());
            } else
                return false;
        } else
            return (other.markerProps == null);
    }
    
    public int hashCode() {
        int ret = 0;
        String name = getName();
        if (name != null)
            ret ^= name.hashCode();
        ret ^= column1;
        ret ^= column2;
        ret += ( range ? 1 : 0);
        ret ^= markerType;
        return ret;
    }


    public MarkerDescriptor() {
        
    }
    
    public MarkerDescriptor(String sourceId, boolean isRange, int axisIndex, boolean visible) {
        this.setSource(sourceId);
        this.setAxisIndex(axisIndex);
        this.setVisible(visible);
        this.range = isRange;
    }
    
    public String getLabel() {
        String ret= null;
        if (markerProps != null) {
            ret = markerProps.get(PROP_label);
        }
        return ret;
    }
    
    public void setLabel(String s) {
        String old = getLabel();
        markerProps.put(PROP_label,s);
        this.fireChange(PROP_label,old,s);
    }
    
    public String getPaint() {
        String ret = null;
        if (markerProps != null) {
            ret = markerProps.get(PROP_paint);
        }
        return ret;
    }
    
    public void setPaint(String s) {
        String old = getPaint();
        markerProps.put(PROP_paint,s);
        this.fireChange(PROP_paint,old,s);
    }

    public String getAlpha() {
        String ret = null;
        if (markerProps != null) {
            ret = markerProps.get(PROP_alpha);
        }
        return ret;
    }

    public void setAlpha(String s) {
        String old = getAlpha();
        markerProps.put(PROP_alpha,s);
        this.fireChange(PROP_alpha,old,s);
    }

    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        p = super.serializeToProps(p, prefix);
        
        /*if (this.name != null) {
            p.set(prefix,PROP_name,this.name);
        }*/
        
        if (this.values != null) {
            p.set(prefix, PROP_values, values);
        }
        
        if (this.column1 != DEFAULT_COLUMN1)
            p.set(prefix,PROP_column1,String.valueOf(this.column1));

        if (this.column2 != DEFAULT_COLUMN2)
            p.set(prefix,PROP_column2,String.valueOf(this.column2));
        
        if (this.markerType != DEFAULT_MARKERTYPE)
            p.set(prefix,PROP_markerType,String.valueOf(this.markerType));
        
        if (this.range != DEFAULT_ISRANGE)
            p.set(prefix,PROP_range,String.valueOf(this.range));
        
        if (this.aggregate != null) {
            p.set(prefix, PROP_aggregate, aggregate);
        }
        if (this.layer != null) {
            p.set(prefix, PROP_layer, layer);
        }
        p.set(prefix, PROP_labelVisible, String.valueOf(isLabelVisible()));
        for (String k : markerProps.keySet()) {
            p.set(prefix,k,markerProps.get(k));
        }
        
        return p;
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

        try {
            SimpleProps props = p.subset(prefix);
            if (props.size() == 0) {
                return false;
            }
            
            String key;

            key = PROP_column1;
            if (props.containsKey(key)) {
                setColumn1(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_column2;
            if (props.containsKey(key)) {
                setColumn2(Integer.parseInt(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_markerType;
            if (props.containsKey(key)) {
                setType(Integer.valueOf(props.get(key)));
                props.remove(key);
                ret = true;
            }

            key = PROP_range;
            if (props.containsKey(key)) {
                setRange(Boolean.parseBoolean(props.get(key)));
                props.remove(key);
                ret = true;
            }
            
            key = PROP_values;
            if (props.containsKey(key)) {
                this.setValuesAsString(props.get(key));
                props.remove(key);
                ret = true;
            }
            
            key = PROP_aggregate;
            if (props.containsKey(key)) {
                this.setAggregate(props.get(key));
                props.remove(key);
                ret = true;
            }

            key = PROP_layer;
            if (props.containsKey(key)) {
                this.setLayer(props.get(key));
                props.remove(key);
                ret = true;
            }

            key = PROP_labelVisible;
            if (props.containsKey(key)) {
                this.setLabelVisible("true".equalsIgnoreCase(props.get(key)));
                props.remove(key);
                ret = true;
            } else {
                // backward compat: make label visible true by default
                this.labelVisible = true;
            }
            
            // all the properties that remain are the marker properties
            markerProps = props;
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
            // FIXME: how to handle parsing exceptions while deserializing?
        }
        return ret;
    }
    
    @Deprecated
    public int getColumn1() {
        return column1;
    }
    
    @Deprecated
    public void setColumn1(int c) {
        int old = this.column1;
        this.column1 = c;
        this.fireChange(PROP_column1,old,this.column1);
    }

    @Deprecated
    public int getColumn2() {
        return column2;
    }
    @Deprecated
    public void setColumn2(int c) {
        int old = this.column2;
        this.column2 = c;
        this.fireChange(PROP_column1,old,this.column2);
    }

    
    public boolean isRange() {
        return range;
    }
    
    public boolean isInterval() {
        return markerType == MARKER_TYPE_NUMERIC_INTERVAL || markerType == MARKER_TYPE_DATE_INTERVAL; 
    }
    
    public boolean isDate() {
        return markerType == MARKER_TYPE_DATE || markerType == MARKER_TYPE_DATE_INTERVAL; 
    }
    /**
     *  @param range  - if true, indicates that this Marker goes on the range axis
     */
    public void setRange(boolean range) {
        boolean old = this.range;
        this.range = range;
        this.fireChange(PROP_range, old, this.range);
    }
    
    public boolean isError() { return hasError; }
    public void setError(boolean b) {
        boolean old = hasError;
        hasError = b;
        fireChange(PROP_error, old, hasError, true);
    }    
}