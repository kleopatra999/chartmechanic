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

import static com.bayareasoftware.chartengine.model.ChartConstants.MAX_RANGE_AXES;

import java.util.ArrayList;
import java.util.List;


/**
 * ChartInfo encapsulates the information about a particular chart
 * A ChartInfo object specifies various chart properties as a string of the form
 *     name=value
 * that is then used by the JFreeChartDriver to set various values in the JFreeChart beans
 *
 * in addition to various string properties, a ChartInfo has the integer properties
 *      width
 *      height
 *      timePeriod
 *  
 * A ChartInfo also contains
 *     - a list of SeriesDescriptors
 *           SeriesDescriptors reference a DataSource object
 *           and contain the charting properties for each series in the Chart
 *     - a list of MarkerDescriptors (which are references by ID ) to MarkerInfo objects
 */
@SuppressWarnings("serial")
public class ChartInfo extends BaseInfo {

    //public static final String PROP_ACL = "acl";
    public static final String PROP_PLOTTYPE = "plottype";
    public static final String PROP_RENDERTYPE ="rendertype";
    public static final String PROP_ALIGN_X = "align_x";
    public static final String PROP_WIDTH = "width";
    public static final String PROP_HEIGHT = "height";
    public static final String PROP_TIMEPERIOD = DataSourceInfo.PROP_TIMEPERIOD;
    public static final String PROP_SERIES_PROPERTIES = "series_properties";
    public static final String PROP_MARKER_PROPERTIES = "marker_properties";
    public static final String PROP_TEMPLATE = "template";
    
    public static final String PROP_USECACHE = "useCache";
    
    private static final boolean DEFAULT_USECACHE = false;
    
    private int width;
    private int height;
    private int timePeriod;  // only meaningful for time series, a TimeConstants value (e.g. TIME_YEAR)
    private boolean alignXValue = true;

    private List<SeriesDescriptor> series = new ArrayList<SeriesDescriptor>();
    private List<MarkerDescriptor> markers = new ArrayList<MarkerDescriptor>();
    
    /**
     * keep track of the max seriesdescriptor id
     * this is used to maintain uniqueness among the seriesdescriptors within a chartinfo
     * 
     * this value is transferred from client->server, but is not persisted as it can be calculated on deserialization
     */
    private int maxSid = 0;
    
    public ChartInfo() {
        super();
    }
    
    /**
     * turn on firing of prop change events to this chart info's simple props.  The
     * events will appear to be originating from 'this' ChartInfo
     */
    public void enablePropChangeEvents() {
        this.props.setPropChangeSource(this);
    }
    public String getTemplate() { 
        return getProperty(PROP_TEMPLATE);
    }
    
    public void setTemplate(String template) {
        setProperty(PROP_TEMPLATE,template);
    }

    public boolean getUseCache() {
        String v = getProperty(PROP_USECACHE);
        if (v == null)
            return DEFAULT_USECACHE;
        else
            return Boolean.valueOf(v);
    }
    
    public void setUseCache(boolean v) {
        setProperty(PROP_USECACHE,String.valueOf(v));
    }

    // check to see if the range axis in question is visible in any visible
    // Seriesdescriptor or Markerdescriptor
    public boolean isRangeAxisVisible(int i) {
        // set up range axes info
        for (SeriesDescriptor sd : series) {
            int axe = sd.getAxisIndex();
            if (axe >= 0 && axe < MAX_RANGE_AXES && sd.isVisible()) {
                if (axe == i)
                    return true;
            } 
        }
        
        for (MarkerDescriptor md : markers) {
            int axe = md.getAxisIndex();
            if (axe >= 0 && axe < MAX_RANGE_AXES && md.isRange() && md.isVisible()) {
                if (axe == i)
                    return true;
            } 
        }
        return false;
    }
    
    public PlotType getPlotType() {
        return PlotType.get(getProperty(PROP_PLOTTYPE));
    }
    public void setPlotType(PlotType ptype) {
        setProperty(PROP_PLOTTYPE,ptype.toString());
    }

    public String getAcl() {
        return getProperty(AclConstants.PROP_ACL);
    }
    public void setAcl(String s) {
        setProperty(AclConstants.PROP_ACL,s);
    }
    
    public int getHeight() {
        return height;
    }
    public void setHeight(int h) {
        int old = this.height;
        this.height = h;
        this.fireChange(PROP_HEIGHT,new Integer(old),new Integer(height));
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        int old = this.width;
        this.width = width;
        this.fireChange(PROP_WIDTH,new Integer(old), new Integer(this.width));
    }
    
    /**
     * only meaningful for time series, a TimeConstants value (e.g. TIME_YEAR)
     * @return
     */
    public int getTimePeriod() {
        return timePeriod;
    }
    
    public void setTimePeriod(int timePeriod) {
        int old = this.timePeriod;
        this.timePeriod = timePeriod;
        this.fireChange(PROP_TIMEPERIOD, new Integer(old), new Integer(this.timePeriod));
    }
    public boolean isAlignXValue() {
        return this.alignXValue;
    }
    public void setAlignXValue(boolean x) {
        boolean old = alignXValue;
        alignXValue = x;
        fireChange(PROP_ALIGN_X, old, x);
    }
    
    
    public String toString() {
        return serializeToProps(null,null).toString();
    }
    
    public String getRenderType() {
        return getProperty(PROP_RENDERTYPE);
    }
    
    public void setRenderType(String renderType) {
        setProperty(PROP_RENDERTYPE,renderType);
    }
    
    public static String getDomainAxisPropertyPrefix() {
        return "domain-axis";
    }
    
    public static String getRangeAxisPropertyPrefix(int index) {
        return "range-axis-"+index;
    }
    
    /****************************************************
     ************ SERIES DESCRIPTOR CODE ****************
     ****************************************************/
    public int getSeriesCount() {
        return series.size();
    }
    
    public List<SeriesDescriptor> getSeriesDescriptors() {
        return series;
    }
    
    public SeriesDescriptor getSeriesDescriptor(int index) {
        if (index >= 0 && index < series.size()) {
            return series.get(index);
        }
        return null;
    }
    public BaseDescriptor getBaseDescriptorBySID(Integer sid) {
        if (sid != null) {
            for (SeriesDescriptor s: series) {
                if (sid.equals(s.getSid())) {
                    return s;
                }
            }
            for (MarkerDescriptor m : markers) {
                if (sid.equals(m.getSid())) {
                    return m;
                }
            }
        } 
        return null;
    }
    
    
    public void addSeriesDescriptor(SeriesDescriptor cs) {
        if (!series.contains(cs)) {
            if (cs.getSid() == null) {
                cs.setSid(nextSid());
            }
            series.add(cs);
            this.fireChange(PROP_SERIES_PROPERTIES, null, series);
        }
    }
    
    // package-private.  used by ChartBundle, upstream users should always go through ChartBundle
    /*public*/ void removeSeriesDescriptor(int index) {
        SeriesDescriptor master = series.remove(index);
        List<BaseDescriptor> deps = this.getDependentDescriptors(master);
        int markerCount = 0;
        for (BaseDescriptor bd : deps) {
            if (bd instanceof MarkerDescriptor) {
                markers.remove(bd);
                markerCount++;
            } else if (bd instanceof SeriesDescriptor) {
                series.remove(bd);
            }
        }
        this.fireChange(PROP_SERIES_PROPERTIES, null, series);
        if (markerCount > 0) {
            this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
        }
    }
    
    public void clearAllSeriesDescriptors() {
    	series.clear();
    	this.fireChange(PROP_SERIES_PROPERTIES, null, series);
    }
    
    public boolean swapSeries(int index1, int index2) {
        int sz = series.size();
        if (index1 >= 0 && index2 >= 0
            && index1 < sz && index2 < sz
            && index1 != index2) {
            SeriesDescriptor a = series.get(index1);
            SeriesDescriptor b = series.get(index2);
            series.set(index1, b);
            series.set(index2, a);
            this.fireChange(PROP_SERIES_PROPERTIES,null,series);
            return true;
        } else {
            return false;
        }
    }

    /****************************************************
     ************ MARKER DESCRIPTOR CODE ****************
     ****************************************************/    
    public int getMarkerCount() { return markers.size(); }
    public List<MarkerDescriptor> getMarkers() { return markers; }
    public void setMarkers(List<MarkerDescriptor> m) {
        markers = m;
        this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
    }
    /*
    public boolean removeMarkerById(String id) {
        boolean ret = false;
        for (int i = 0; i < markers.size(); i++) {
            MarkerDescriptor m = markers.get(i);
            if (m.getSource().equals(id)) {
                markers.remove(m);
                ret = true;
                this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
                break;
            }
        }
        return ret;
    }*/
    
    // package-private.  used by ChartBundle, upstream users should always go through ChartBundle
    /*public*/ void removeMarkerDescriptor(int index) {
        markers.remove(index);
        this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
    }
    

    public void addMarkerDescriptor(MarkerDescriptor md) {
        if (!markers.contains(md)) {
            if (md.getSid() == null) {
                md.setSid(nextSid());
            }
            markers.add(md);
            this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
        }
    }
    
    public void clearAllMarkers() {
        this.markers.clear();
        this.fireChange(PROP_MARKER_PROPERTIES, null, markers);
    }
    
    public MarkerDescriptor getMarker(int index) {
        MarkerDescriptor ret = null;
        if (index >= 0 && index < getMarkerCount()) {
            ret = markers.get(index);
        }
        return ret;
    }
    
    public final static String SERIES_DESCRIPTOR_PREFIX="sd";
    public final static String MARKER_DESCRIPTOR_PREFIX="md";
    
    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    @Override
    public SimpleProps serializeToProps(SimpleProps sp, String prefix) {
        SimpleProps p = super.serializeToProps(sp, prefix);
        p.set(prefix,PROP_WIDTH,String.valueOf(this.width));
        p.set(prefix,PROP_HEIGHT,String.valueOf(this.height));
        p.set(prefix,PROP_TIMEPERIOD,String.valueOf(this.timePeriod));
        p.set(prefix, PROP_ALIGN_X, String.valueOf(alignXValue));
        
        p = SerializeUtil.serializeList(p, prefix, series, SERIES_DESCRIPTOR_PREFIX);
        p = SerializeUtil.serializeList(p, prefix, markers, MARKER_DESCRIPTOR_PREFIX);
        
        return p;
    }

    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = super.deserializeFromProps(p, prefix);
        
        // after the base class deserializes, we have all the properties we need in this.props
        // with the prefix stripped, thus we don't need to check the prefix anymore

        try {
            String k;
            k = PROP_WIDTH;
            if (this.props.containsKey(PROP_WIDTH)) {
                this.width = Integer.valueOf(this.props.get(k));
                ret = true;
                this.props.remove(k);
            }
            k = PROP_HEIGHT;
            if (this.props.containsKey(k)) {
                this.height = Integer.valueOf(this.props.get(k));
                ret = true;
                this.props.remove(k);
            }
            k = PROP_TIMEPERIOD;
            if (this.props.containsKey(k)) {
                this.timePeriod = Integer.valueOf(this.props.get(k));
                ret = true;
                this.props.remove(k);
            }
            k = PROP_ALIGN_X;
            if (this.props.containsKey(k)) {
                this.alignXValue = Boolean.valueOf(this.props.get(k));
                ret = true;
                this.props.remove(k);
            }
            
            
            int seriesCount = SerializeUtil.deserializeListSize(p, prefix, SERIES_DESCRIPTOR_PREFIX);
            series.clear();
            for (int i=0;i<seriesCount;i++) {
                SeriesDescriptor sd = SerializeUtil.deserializeListElement(new SeriesDescriptor(),p,prefix,SERIES_DESCRIPTOR_PREFIX,i);
                Integer sid = sd.getSid();
                if (sid == null) {
                    // legacy series descriptor without an id, assign it one
                    sd.setSid(nextSid());
                } else {
                    // if there is a sid, then make sure that our maxSid take that into account so we don't
                    // mistakenly assign that id value again
                    if (sid >= maxSid) { 
                        maxSid = sid+1;
                    }
                }
                series.add(sd);
                ret = true;
            }
            this.props.removeWithPrefix(SERIES_DESCRIPTOR_PREFIX+SimpleProps.PREFIX_SEPARATOR); // remove all keys that start with series.

            int markerCount = SerializeUtil.deserializeListSize(p, prefix, MARKER_DESCRIPTOR_PREFIX);
            markers.clear();
            for (int i=0;i<markerCount;i++) {
                MarkerDescriptor md = SerializeUtil.deserializeListElement(new MarkerDescriptor(),p,prefix,MARKER_DESCRIPTOR_PREFIX,i);
                Integer sid = md.getSid();
                if (sid == null) {
                    // legacy marker descriptor without an id, assign it one
                    md.setSid(nextSid());
                } else {
                    // if there is a sid, then make sure that our maxSid take that into account so we don't
                    // mistakenly assign that id value again
                    if (sid >= maxSid) { 
                        maxSid = sid+1;
                    }
                }
                markers.add(md);
                ret = true;
            }
            
            // FIX-UP old markers here
            // if a markerDescriptor has an aggregate field, then we convert it to the new style markers here
            for (MarkerDescriptor md : markers) {
                String agg = md.getAggregate();
                if (agg != null) {
                    // find the sid that matches the series in question
                    Integer sid = null;
                    for (SeriesDescriptor sd : series) {
                        String src = sd.getSource();
                        if (src != null && src.equals(md.getSource()) &&
                                sd.getYColumn() == md.getColumn1()) {
                            sid = sd.getSid();
                            break;
                        }
                    }
                    if (sid != null) {
                        md.setFunc(agg);
                        md.addArg(new Arg(ArgType.SID,sid));
                        md.setAggregate(null);
                        md.setSource(null);
                    } else {
                        // System.err.println("Could not convert old-style marker to new-style marker because no matching series was found for marker:" + md);
                    }
                }
            }
            
            this.props.removeWithPrefix(MARKER_DESCRIPTOR_PREFIX+SimpleProps.PREFIX_SEPARATOR); // remove all keys that start with series.
            
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
            // FIXME: how to handle parsing exceptions while deserializing?
        }
        return ret;
    }
    
    /**
     * given a descriptor, return a list of all other descriptors that are dependent on this one
     * a descriptor is 'dependent' on another if it is a argument to the function for a derived descriptor
     * only check on-level dependence, not recursive dependence
     * 
     * @param bd      - must be non-null, must be a BaseDescriptor IN this chart
     * @return        - size of list is 0 if there are no dependent descriptors
     */
    public List<BaseDescriptor> getDependentDescriptors(BaseDescriptor src) {
        List<BaseDescriptor> ret = new ArrayList<BaseDescriptor>();
        Integer sid = src.getSid();
        if (sid != null) {
            for (SeriesDescriptor sd : series) {
                if (sd.dependsOn(src)) {
                    ret.add(sd);
                }       
            }
            for (MarkerDescriptor md : markers) {
                if (md.dependsOn(src)) {
                    ret.add(md);
                }       
            }
        }
        return ret;
    }
    
    /**
     * the next sid to use.  Sids should be unique within a chartinfo
     * @return
     */
    private Integer nextSid() {
        return maxSid++;
    }
    
    @Override
    public boolean equals (Object o) {
        if (o == null)
            return false;
        
        if (! (o instanceof ChartInfo))
            return false;

        if (! super.equals(o))
            return false;
        
        ChartInfo other = (ChartInfo)o;
        if (series.size() == other.series.size()) {
            for (int i=0;i<series.size();i++) {
                if (!series.get(i).equals(other.series.get(i)))
                    return false;
            }
        } else
            return false;
        
        if (markers.size() == other.markers.size()) {
            for (int i=0;i<markers.size();i++) {
                if (!markers.get(i).equals(other.markers.get(i)))
                    return false;
            }
        } else
            return false;
        
        return (this.height == other.height &&
                this.width == other.width &&
                this.timePeriod == other.timePeriod &&
                this.maxSid == other.maxSid);
    }
 
    @Override
    public int hashCode() {
        int ret = super.hashCode();
        ret ^= height;
        ret ^= width;
        ret ^= timePeriod;
        ret ^= maxSid;
        for (SeriesDescriptor sd : series) {
            ret ^= sd.hashCode();
        }
        for (MarkerDescriptor md : markers) {
            ret ^= md.hashCode();
        }
        return ret;
    }
    
    /**
     * return a list of descriptors sorted by dependence
     * the any descriptor in the list is followed, never preceded by another descriptor that
     * depends on it
     * @return
     */
    public List<BaseDescriptor> getSortedDescriptors() {
        List<BaseDescriptor> ret = new ArrayList<BaseDescriptor>();
        // list of basedescriptors that depends on some other base descriptor
        List<BaseDescriptor> dependsOn = new ArrayList<BaseDescriptor>();
        for (SeriesDescriptor sd : series) {
            if (!sd.hasDependency()) {
                ret.add(sd);
            } else {
                dependsOn.add(sd);
            }
        }
        for (MarkerDescriptor md : markers) {
            if (!md.hasDependency()) {
                ret.add(md);
            } else {
                dependsOn.add(md);
            }
        }

        // loops assumes there are no referential cycles within the descriptors
         while (dependsOn.size() > 0) {
            // remove the first element
            BaseDescriptor bd = dependsOn.remove(0);
            
            boolean ok = true;
            // check to see if I depend on someone in the dependsOn list
            for (BaseDescriptor b : dependsOn) {
                if (bd.dependsOn(b)) {
                    ok = false;
                    break;
                }
            }
            
            if (ok) {
                // I don't depend on anyone on the dependsOn list, that means
                // all the descriptors I'm dependent on are already in the ret list, so I'm good to go
                ret.add(bd);
            } else {
                // I depend on someone who's not been added to the ret list yet, so put me back in the end of the dependsOn list
                dependsOn.add(bd);
            }
        }
        return ret;
    }

}
