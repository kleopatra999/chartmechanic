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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayareasoftware.chartengine.model.event.PropChangeSource;


/**
 * A ChartBundle encapsulates everything that is needed to create a chart
 * It consists of 
 *      a ChartInfo  - description of various properties of a chart
 *            including
 *                 a list of SeriesDescriptors: with references via IDs to DataSources
 *                 a list of MarkerDescriptors: with references via IDs to DataSources
 *      a list of DataSourceInfos - entities that describe data sources, referenced by series and markers
 *      
 */
@SuppressWarnings("serial")
public class ChartBundle extends PropChangeSource implements PropertySerializable {

    public static final String PROP_SERIES = "series";
    public static final String PROP_DATASOURCE = "source";

    private ChartInfo chartInfo;
 
    private List<DataSourceInfo> sources = new ArrayList<DataSourceInfo>();

    /**
     * The ChartInfo descriptor for this chart bundle.
     */
    public ChartInfo getChartInfo() {
        return chartInfo;
    }
    public void setChartInfo(ChartInfo chartInfo) {
        this.chartInfo = chartInfo;
    }

    public boolean getUseCache() {
        return chartInfo.getUseCache();
    }
    
    public void setUseCache(boolean b) {
        chartInfo.setUseCache(b);
    }

    public int getPersistState() {
        return chartInfo.getPersistState();
    }
    
    public void setPersistState(int ps) {
        chartInfo.setPersistState(ps);
    }
    
    public String getDescription() {
        return chartInfo.getDescription();
    }
    
    public String getId() {
        return chartInfo.getId();
    }
    
    /**
     * All data sources used in this chart
     * @return All data sources used in this chart
     */
    public DataSourceInfo[] getDataSources() {
        DataSourceInfo[] res = new DataSourceInfo[sources.size()];
        sources.toArray(res);
        return res;
    }
    
    public void setDataSources(List<DataSourceInfo> newSources) {
        sources = newSources;
        fireChange(PROP_DATASOURCE,null,sources);
    }
    
    /*
    public void addSeries(SeriesDescriptor sd, DataSourceInfo dsi) {
//        if (sd.getSource() == null) {
//            throw new RuntimeException("null source id for series");
//        } else if (!sd.getSource().equals(dsi.getId())) {
//        }
        String dsiId = dsi.getId();
        if (dsiId == null) {
            throw new RuntimeException(
                    "null data source ID"
                    );
        }
        // is this a sensible check here?
        // alternatively, we could just: sd.setSource(dsiId);
        if (!dsiId.equals(sd.getSource())) {
            throw new RuntimeException(
                    "series source '" + sd.getSource() + "'!='" + dsiId + "'"
                    );
        }
        addDataSource(dsi);
        chartInfo.addSeriesDescriptor(sd);
    }*/

    // add a data source
    public void addDataSource(DataSourceInfo dsi) {
        if (dsi == null)
            return;
        
        String dsId = dsi.getId();
        if (dsId == null)
            return;
        for (DataSourceInfo d : sources) {
            if (dsi.equals(d))
                return; // already in the list
        }
        sources.add(dsi);
        fireChange(PROP_DATASOURCE,null,sources);
    }
    
    public DataSourceInfo getDataSourceByID(String dsId) {
        DataSourceInfo ret = null;
        if (dsId != null) {
            for (DataSourceInfo d : sources) {
                if (dsId.equals(d.getId())) {
                    ret = d;
                    break;
                }
            }
        }
        return ret;
    }
    
    public MarkerDescriptor addDateMarker(String name, Date d) {
        List<Date> values = new ArrayList<Date>();
        values.add(d);
        return addDateMarker(name,values);
    }
    /**
     * Remove data sources not referenced by series/markers of this chart
     * 
     * This should not be needed, strictly speaking, but existing charts from
     * prior to data source GC being implemented will fail validation without this
     * 
     * @return number of unreferenced data sources gc'd
     */
    public int removeUnreferencedDataSources() {
        Map<String,Boolean> dsRefs = new HashMap<String,Boolean>();
        // grab all data source id's referenced by this chart
        for (MarkerDescriptor md : chartInfo.getMarkers()) {
            String srcid = md.getSource();
            if (srcid != null) {
                dsRefs.put(srcid, Boolean.TRUE);
            }
        }
        for (SeriesDescriptor sd : chartInfo.getSeriesDescriptors()) {
            String srcid = sd.getSource();
            if (srcid != null) {
                dsRefs.put(srcid, Boolean.TRUE);
            }
        }
        int removed = 0;
        if (dsRefs.size() != sources.size()) {
            DataSourceInfo[] sa = getDataSources(); 
            for (DataSourceInfo dsi : sa) {
                if (dsRefs.get(dsi.getId()) == null) {
                    if (!sources.remove(dsi)) {
                        throw new RuntimeException(
                                "Cannot remove '" + dsi.getId() + "' from list "
                                + sources
                                );
                    }
                    removed++;
                }
            }
        } else {
            // all accounted for, no stragglers, just return
        }
        return removed;
    }
    /**
     * add Date value markers to this chart
     * this is a convenience function that creates an associated DataSourceInfo with
     * the string value inlined as CSV data
     *  
     * @return  the markerdescriptor so the CALLER can continue to modify it.  
     *          Note that the caller should not modify the data source of the markerdescriptor returned  
     *         
     */
    public MarkerDescriptor addDateMarker(String name, List<Date> values ) {
        MarkerDescriptor md = new MarkerDescriptor();
        md.setName(name);
        md.setType(MarkerDescriptor.MARKER_TYPE_DATE);
        StringBuilder sb = new StringBuilder();
        for (Date val : values) {
            if (sb.length() > 0) { sb.append(MarkerDescriptor.VALUE_SEP); }
            sb.append(val.getTime());
        }
        md.setValuesAsString(sb.toString());
        chartInfo.addMarkerDescriptor(md);
        return md;
    }
    

    public MarkerDescriptor addNumericMarker(String name, Double d) {
        List<Double> values = new ArrayList<Double>();
        values.add(d);
        return addNumericMarker(name,values);
    }

    /**
     * add Numeric value markers to this chart
     * this is a convenience function that creates an associated DataSourceInfo with
     * the numeric value inlined
     *  
     * @return  the markerdescriptor so the CALLER can continue to modify it.  
     *          Note that the caller should not modify the data source of the markerdescriptor returned  
     *         
     */
    public MarkerDescriptor addNumericMarker(String name, List<Double> values ) {
        MarkerDescriptor md = new MarkerDescriptor();
        md.setName(name);
        if (values.size() == 1) 
            md.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC);
        else 
            md.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC_INTERVAL);
        StringBuilder sb = new StringBuilder();
        for (Double val : values) {
            if (sb.length() > 0) { sb.append(MarkerDescriptor.VALUE_SEP); }
            sb.append(val);
        }
        md.setValuesAsString(sb.toString());
        chartInfo.addMarkerDescriptor(md);
        return md;
    }
    
    
    /**
     * add a data interval value marker to this chart
     * this is a convenience function that creates an associated DataSourceInfo with
     * the numeric value inlined
     *  
     * @return  the markerdescriptor so the CALLER can continue to modify it.  
     *          Note that the caller should not modify the data source of the markerdescriptor returned  
     *         
     */
    public MarkerDescriptor addDateIntervalMarker(String name, List<Date[]> val) {
        MarkerDescriptor md = new MarkerDescriptor();
        md.setName(name);
        md.setType(MarkerDescriptor.MARKER_TYPE_DATE_INTERVAL);
        StringBuilder sb = new StringBuilder();
        for (Date[] pair : val) {
            if (sb.length() > 0) { sb.append(MarkerDescriptor.VALUE_SEP); }
            sb.append(pair[0].getTime()).append(MarkerDescriptor.VALUE_SEP).append(pair[1].getTime());
        }
        md.setValuesAsString(sb.toString());
        chartInfo.addMarkerDescriptor(md);
        return md;
    }


    /**
     * remove the marker at this index.
     * also removes the datasource pointed to by this marker descriptor if it has no more referrers in this chart bundle
     * @param index
     */
    public void removeMarkerDescriptor(int index) {
        MarkerDescriptor md = chartInfo.getMarker(index);
        if (md != null) {
            DataSourceInfo dsi = getDataSourceByID(md.getSource());
            if (dsi != null) {
                // check to see if any other SeriesDescriptors or MarkerDescriptors have reference to this datasource
                if (numReferrers(dsi.getId()) == 1) {
                    // if only referrer is this series, then remove the datasource
                    sources.remove(dsi);
                }
            }
            chartInfo.removeMarkerDescriptor(index);
        }
    }

    /**
     * remove the series at this index.
     * also removes the datasource pointed to by this seriesdescriptor if it has no more referrers in this chart bundle
     * @param index
     */
    public void removeSeriesDescriptor(int index) {
        SeriesDescriptor sd = chartInfo.getSeriesDescriptor(index);
        if (sd != null) {
            DataSourceInfo dsi = getDataSourceByID(sd.getSource());
            if (dsi != null) {
                // check to see if any other SeriesDescriptors or MarkerDescriptors have reference to this datasource
                if (numReferrers(dsi.getId()) == 1) {
                    // if only referrer is this series, then remove the datasource
                    sources.remove(dsi);
                }
            }
            
            chartInfo.removeSeriesDescriptor(index);
        }
    }

    public String getMarkerError(MarkerDescriptor md) {
        String ret = null;
        double[] vals = md.getValues();
        if (vals == null) {
            String sourceID = md.getSource();
            if (sourceID != null) {
                DataSourceInfo dsi = getDataSourceByID(sourceID);
                if (dsi == null) {
                    ret = "Data Source '" + sourceID + "' unavailable or not found";
                }
            }
            String sfunc = md.getFunc();
            if (sfunc != null) {
                // check to see that the arguments refer to series that exist in this series
                List<Arg> args = md.getArgs();
                for (Arg a : args) {
                    ArgType argType = a.getArgType();
                    if (ArgType.SID.equals(argType)) {
                        String s = a.getArgValue();
                        if (s != null) {
                            Integer sid = Integer.parseInt(s);
                            if (chartInfo.getBaseDescriptorBySID(sid) == null)
                                ret = "Dependent Series '" + sid + "' unavailable or not found";
                        }
                    }
                }
            }
        }
        md.setError(ret != null);
        return ret;
    }
    
    public String getSeriesError(SeriesDescriptor sd) {
        String ret = null;
        String sfunc = sd.getFunc();
        if (sfunc == null) {
            String sourceID = sd.getSource();
            if (sourceID == null) {
                ret = "no data source associated to this series";
            } else {
                DataSourceInfo dsi = getDataSourceByID(sourceID);
                if (dsi == null) {
                    ret = "Data Source '" + sourceID + "' unavailable or not found";
                }
            }
        } else {
            // check to see that the arguments refer to series that exist in this series
            List<Arg> args = sd.getArgs();
            for (Arg a : args) {
                ArgType argType = a.getArgType();
                if (ArgType.SID.equals(argType)) {
                    String s = a.getArgValue();
                    if (s != null) {
                        Integer sid = Integer.parseInt(s);
                        if (chartInfo.getBaseDescriptorBySID(sid) == null)
                            ret = "Dependent Series '" + sid + "' unavailable or not found";
                    }
                }
            }
        }
        sd.setError(ret != null);
        return ret;
    }
    /**
     * return the number of descriptors (series/marker) that refer to the specified datasource ID
     * @param dsi
     * @return
     */
    private int numReferrers(String dsID) {
        int res = 0;
        if (dsID != null) { 
            for (SeriesDescriptor sd : chartInfo.getSeriesDescriptors()) {
                if (dsID.equals(sd.getSource())) {
                    res++;
                }
            }
            for (MarkerDescriptor md : chartInfo.getMarkers()) {
                if (dsID.equals(md.getSource())) {
                    res++;
                }
            }
        }
        return res;
    }
    
    /**
     * Change the rendering order of two series in this chart.
     * 
     * @param index1 index of the first series
     * @param index2 index of the second series
     * @return true if the series were swapped, false otherwise
     */
    public boolean swapSeries(int index1, int index2) {
        return chartInfo.swapSeries(index1,index2);
    }

    /**
     * Check referential integrity of id's and components of this chart bundle,
     * verifying that this bundle "makes sense".
     * 
     * raises RuntimeException if it doesn't  (FIXME: should return friendlier output when invalid)
     * 
     */
    public List<String> validate() {
        if (chartInfo == null) {
            throw new RuntimeException("no chart info");
        }
        
        List<String> ret = new ArrayList<String>();
        Map<String,Boolean> dsRefs = new HashMap<String,Boolean>(); 
        // ensure that every marker referenced by the ChartInfo is actually in this bundle
        for (MarkerDescriptor md : chartInfo.getMarkers()) {
            double[] vals = md.getValues();
            if (vals == null || vals.length == 0) {
                String sourceID = md.getSource();
                if (sourceID == null) {
                    ret.add("marker '" + md.getName() + "' has no inline"
                            + " values, and no data source ID");
                    continue;
                }
                DataSourceInfo dsi = this.getDataSourceByID(sourceID);
                if (dsi == null) {
                    ret.add(
                            "no data source with id='" + sourceID
                            + "' (referred to by marker '" + md.getName() + "')"
                            );
                } else {
                    dsRefs.put(sourceID, Boolean.TRUE);
                }
            } else {
                // inline values, no datasource reference checking needed...
            }
        
        }

        // ensure that the data source referred to by each series descriptor
        // is in this bundle
        for (SeriesDescriptor sd : chartInfo.getSeriesDescriptors()) {
            String sourceID = sd.getSource();
            if (sourceID == null) {
                if (sd.getFunc() == null) {
                    ret.add(
                            "series '" + sd.getName() + "' has no datasource ID"
                            );
                    sd.setError(true);
                } else {
                    // function series, OK, doesn't need DS ref
                }
            } else {
                DataSourceInfo dsi = this.getDataSourceByID(sourceID);
                if (dsi == null) {
                    ret.add(
                            "no data source with id='" + sourceID
                            + "' (referred to by series '" + sd.getName() + "')"
                            );
                    sd.setError(true);
                } else {
                    dsRefs.put(sourceID, Boolean.TRUE);
                }
            }
        }
        // ensure that every data source in this bundle is referred to by a marker or
        // series...i.e., if a data source has no references to it, it should *NOT*
        // be in the bundle.
        DataSourceInfo[] dsi = this.getDataSources();
        for (int i = 0; i < dsi.length; i++) {
            String sourceID = dsi[i].getId();
            if (sourceID == null) {
                ret.add(
                        "data source #" + i + " of chart '" + getId() + "' has null ID: "
                        + dsi[i]
                        );
            } else if (dsRefs.get(sourceID) == null) {
                ret.add(
                        "data source #" + i + "/'" + sourceID + "' of chart '" + getId() +
                        "' is not referred to by any markers or series"
                        );
                
            }
        }
        return ret;
    }
 
    @Override
    public String toString() {
        return serializeToProps(null,null).toString();
    }
    
    
    // prefix for properties while serializing
    private final static String MARKER_PREFIX="m";
    private final static String DATASOURCE_PREFIX="ds";
    
    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix
     * 
     *    chartInfo properties are listed without prefix
     *    seriesInfo properties are listed as 
     *        s.n=<count>
     *        s.0.<key>=<value>
     *        s.1.<key>=<value>, etc.
     *    markerInfo properties are listed as
     *        m.n=<count>
     *        m.0.<key>=<value>
     *        m.1.<key>=<value>, etc.
     *    datasourceInfo propesties are listed as
     *        ds.n=<count>
     *        ds.0.<key>=<value>
     *        ds.1.<key>=<value>, etc.
     *        
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    public SimpleProps serializeToProps(SimpleProps sp, String prefix) {
        SimpleProps ret = null;
        if (chartInfo != null) {
            ret = chartInfo.serializeToProps(null,null);

            ret = SerializeUtil.serializeList(ret, prefix, sources, DATASOURCE_PREFIX);
            
        }
        return ret;
    }

    // not all properties are strictly needed for rendering a chart
    // some are only informative this method returns a properties object of just those properties that are essential for
    // charting this is used by both the builder and the server-side when rendering charts and determining
    // how to put them into the chartcache
    public SimpleProps getCacheableProps() {
        SimpleProps p = serializeToProps(null,null);
//        p.removeWithSuffix("description"); // remove all the keys named description, don't need those for rendering the chart
        return p;
    }
    
    /** 
     * series the objet to a simpleprops, but for the contained objects (series/markers/sources)
     * only serialize out their IDs (or references) instead of their full contents
     * @param sp
     * @param prefix
     * @return
     */
    public SimpleProps serializeToPropsWithRefs(SimpleProps sp, String prefix) {
        SimpleProps ret = null;
        if (chartInfo != null) {
            ret = chartInfo.serializeToProps(null,null);

            ret = SerializeUtil.serializeListIDs(ret, prefix, sources, DATASOURCE_PREFIX);
        }
        return ret;
    }
    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        try {
            if (chartInfo == null)
                chartInfo = new ChartInfo();
            

            int dsCount = SerializeUtil.deserializeListSize(p, prefix, DATASOURCE_PREFIX);
            sources.clear();
            for (int i=0;i<dsCount;i++) {
                sources.add(SerializeUtil.deserializeListElement(new DataSourceInfo(),p,prefix,DATASOURCE_PREFIX,i));
            }

            // remove all the datasource related props so they don't end up in the chartInfo as general properties
            p.removeWithPrefix(DATASOURCE_PREFIX+SimpleProps.PREFIX_SEPARATOR);
            
            chartInfo.deserializeFromProps(p, prefix);
            
            
            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
            // FIXME: how to handle parsing exceptions while deserializing?
        }
    }

        @Override
        public int hashCode() {
            int ret = -1;
            if (chartInfo != null) {
                ret = chartInfo.hashCode();
            }
            for (DataSourceInfo dsi : sources) {
                ret ^= dsi.hashCode();
            }
            return ret;
        }
        
        @Override
        public boolean equals (Object o) {
            if (o == null)
                return false;
            
            if (! (o instanceof ChartBundle))
                return false;

            ChartBundle other = (ChartBundle)o;
            
            if (sources.size() == other.sources.size()) {
                for (int i=0;i<sources.size();i++) {
                    if (!sources.get(i).equals(other.sources.get(i)))
                        return false;
                }
            } else
                return false;

            if (chartInfo != null) {
                return chartInfo.equals(other.chartInfo);
            } else  {
                return (other.chartInfo == null);
            }

    }
}