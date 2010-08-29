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
/**
 * 
 */
package com.bayareasoftware.chartengine.model.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.PlotType;

public class ChartTypeSystem implements Serializable, ChartConstants {
    
    private String[] fontNames;
    private ChartBeanInfo chartBean;
    private ChartBeanInfo textTitleBean;
    private ChartBeanInfo markerBean;
    private ChartBeanInfo legendBean;
    private List<ChartBeanInfo> plots = new ArrayList<ChartBeanInfo>();
    private HashMap<String,ChartBeanInfo> xyrenders = new HashMap<String,ChartBeanInfo>();
    private HashMap<String,ChartBeanInfo> catrenders = new HashMap<String,ChartBeanInfo>();
    /*
     * display names to renderer types
     */
    private transient HashMap<String,String> xyRenderNames = new HashMap<String,String>();
    private transient HashMap<String,String> catRenderNames = new HashMap<String,String>();
    /**
     * Axis,ValueAxis,NumberAxis,DateAxis,CategoryAxis,LogarithmicAxis
     */
    private HashMap<String,ChartBeanInfo> axes = new HashMap<String,ChartBeanInfo>();
    
    public ChartBeanInfo findBean(String c) {
        ChartBeanInfo ret = null;
        if (c.equals(chartBean.getClassname())) ret = chartBean;
        else if (c.equals(textTitleBean.getClassname())) ret = textTitleBean;
        else if (c.equals(markerBean.getClassname())) ret = markerBean;
        else if (c.equals(legendBean.getClassname())) ret = legendBean;
        else {
            ret = xyrenders.get(c);
            if (ret == null) {
                ret = catrenders.get(c);
            }
            if (ret == null) {
                ret = axes.get(c);
            }
            if (ret == null) {
                for (int i = 0; i < plots.size(); i++) {
                    if (c.equals(plots.get(i).getClassname())) {
                        ret = plots.get(i);
                        break;
                    }
                }
            }
        }
        return ret;
    }
    public ChartBeanInfo getChartBean() {
        return chartBean;
    }
    public void setChartBean(ChartBeanInfo chartBean) {
        this.chartBean = chartBean;
    }
    
    public void setTextTitleBean(ChartBeanInfo c) {
        textTitleBean = c;
    }
    
    public ChartBeanInfo getTextTitleBean() { return textTitleBean; }
    
    public ChartBeanInfo getMarkerBean() { return markerBean; }
    public void setMarkerBean(ChartBeanInfo cbi) { markerBean = cbi; }
    public ChartBeanInfo getLegendBean() { return legendBean; }
    public void setLegendBean(ChartBeanInfo cbi) { legendBean = cbi; }
    
    public List<ChartBeanInfo> getPlots() {
        return plots;
    }
    public void setPlots(List<ChartBeanInfo> plots) {
        this.plots = plots;
    }
    
    public void addPlot(ChartBeanInfo plotInfo) {
        plots.add(plotInfo);
    }
    
    public void addXyRenderer(ChartBeanInfo xyInfo) {
        xyrenders.put(xyInfo.getDisplayname(), xyInfo);
    }
    
    public void addCatRenderer(ChartBeanInfo catInfo) {
        catrenders.put(catInfo.getDisplayname(), catInfo);
    }
    
    public void addAxis(ChartBeanInfo axisInfo) {
        axes.put(axisInfo.getDisplayname(), axisInfo);
    }

    public String[] getFontNames() {
        return fontNames;
    }
    
    public void setFontNames(String[] s) {
        fontNames = s;
    }
    public ChartBeanInfo findPlot(String classname) {
        for (int i = 0; i < plots.size(); i++) {
            ChartBeanInfo cbi = plots.get(i);
            if (classname.equals(cbi.getClassname())) {
                return cbi;
            }
        }
        throw new RuntimeException(
                "no plot type: " + classname
                );
    }
    
    /**
     * get property info for the specified class, and
     * all its super class(es)
     */
    public ChartBeanInfo getPlotInfo(String classname) {
        ChartBeanInfo cbi = findPlot(classname);
        if (cbi != null) {
            return getInheritedProps(cbi, "plot");
        }
        return null;
    }
    
    public ChartBeanInfo getXYRendererInfo(String displayName) {
        ChartBeanInfo cbi = xyrenders.get(displayName);
        if (cbi != null) {
            return getInheritedProps(cbi, "xyrender");
        }
        return null;
    }
    public ChartBeanInfo getCategoryRendererInfo(String displayName) {
        ChartBeanInfo cbi = catrenders.get(displayName);
        if (cbi != null) {
            return getInheritedProps(cbi, "catrender");
        }
        return null;
    }
    public ChartBeanInfo getAxis(String displayName) {
        ChartBeanInfo cbi = axes.get(displayName);
        if (cbi != null) {
            return getInheritedProps(cbi, "axis");
        }
        return null;
    }
    
    private ChartBeanInfo findBean(String classname, String type) {
        ChartBeanInfo ret = null;
        if ("plot".equals(type)) {
            ret = findPlot(classname);
        } else if ("xyrender".equals(type)) {
            ret = xyrenders.get(displayName(classname));
        } else if ("catrender".equals(type)) {
            ret = catrenders.get(displayName(classname));
        } else if ("axis".equals(type)) {
            ret = axes.get(displayName(classname));
        } else {
            throw new RuntimeException("bad discriminator '" + type + "'");
        }
        if (ret == null) {
            p("cannot find " + classname + "/" + displayName(classname));
        }
        return ret;
    }
    private String displayName(String classname) {
        int ind = classname.lastIndexOf('.');
        if (ind > 0) {
            return classname.substring(ind + 1);
        }
        return classname;
    }
    /**
     * 
     * @param cbi
     * @return
     */
    private ChartBeanInfo getInheritedProps(ChartBeanInfo cbi, String type) {
        ChartBeanInfo ret = cbi;
        
        ChartBeanInfo current = cbi;
        
        // walk up the superclass hierarchy
        while (current != null && current.getSupername() != null) {
            // p("looking for superclass '" + cbi.getSupername() + "' of '" + cbi.getClassname() + "' for type: " + type);
            current = findBean(current.getSupername(), type);
            if (current != null) {
                // only set the property if it doesn't alredy exist in the original class
                for (PropInfo pi : current.getProps()) {
                    if (ret.findProp(pi.getName()) == null) {
                        ret.addProp(pi);
                    }
                }
            }
        }
        
        return ret;

//        ChartBeanInfo ret = new ChartBeanInfo();
//        List<PropInfo> l = new ArrayList<PropInfo>();
//        ret.setClassname(cbi.getClassname());
//        ret.setSupername(cbi.getSupername());
//      PropInfo[] props = cbi.getProps();
//        for (int i = 0; i < props.length; i++) {
//            l.add(props[i]);
//        }
//        while (cbi.getSupername() != null) {
//            //p("looking for superclass '"
//                    //+ cbi.getSupername() + "' of '" + cbi.getClassname() + "'");
//            cbi = findBean(cbi.getSupername(), type);
//            props = cbi.getProps();
//            for (int i = 0; i < props.length; i++) {
//                l.add(props[i]);
//            }   
//        }
//        Collections.sort(l, PROPCOMP);
//        props = new PropInfo[l.size()];
//        for (int i = 0; i < l.size(); i++) {
//            props[i] = (PropInfo) l.get(i);
//        }
//        
//        ret.setProps(props);
//        return ret;        
    }
    public String getCatRenderer(String displayName) {
        return getRenderer(displayName, catrenders);
    }
    public String getXYRenderer(String displayName) {
        return getRenderer(displayName, xyrenders);
    }
    private String getRenderer(String displayName, HashMap<String,ChartBeanInfo> map) {
        ChartBeanInfo cbi = map.get(displayName);
        if (cbi != null) {
            return cbi.getClassname();
        } else if (displayName.indexOf('.') != -1) {
            // assume it's a classname
            return displayName;
        }
        return null;
    }

    public List<ChartBeanInfo> getConcreteXYRenderers() {
        return getMapValues(xyrenders, false);
    }
    
    public List<ChartBeanInfo> getConcreteCategoryRenderers() {
        return getMapValues(catrenders, false);
    }
    
    public List<ChartBeanInfo> getConcreteGanttRenderers() {
        List<ChartBeanInfo> ret = new ArrayList<ChartBeanInfo>();
        ret.add(this.getCategoryRendererInfo("Gantt"));
        return ret;
    }
    
    private static final Comparator<ChartBeanInfo> BEANCOMP = new Comparator<ChartBeanInfo>() {
        public int compare(ChartBeanInfo x, ChartBeanInfo y) {
            return x.getDisplayname().compareTo(y.getDisplayname());
        }
    };
    
    private List<ChartBeanInfo> getMapValues(HashMap<String,ChartBeanInfo> mp, boolean includeAbstract) {
        List<ChartBeanInfo> ret = new ArrayList<ChartBeanInfo>();
        for (ChartBeanInfo cbi: mp.values()) {
            if ((!cbi.isAbstract() || includeAbstract) && !ret.contains(cbi)) {
                ret.add(cbi);
            }
        }
        Collections.sort(ret, BEANCOMP);
        return ret;
    }
    public void mergeDoc(RendererDocSet rds) {
        HashMap<String,ChartBeanInfo> newXY = new HashMap<String,ChartBeanInfo>();
        for (ChartBeanInfo xyr : this.xyrenders.values()) {
            if (xyr.isAbstract()) { // must keep abstract
                newXY.put(xyr.getDisplayname(), xyr);
                continue;
            }
            RendererDocInfo rdi = rds.findXYByClassname(xyr.getClassname());
            if (rdi != null && !rdi.isExcluded()) {
                // put under old display name/classname as well, so we can find superclasses...
                newXY.put(xyr.getDisplayname(), xyr);
                String disp = rdi.getDisplayName();
                xyr.setDisplayname(disp);
                xyr.setRenderDoc(rdi);
                newXY.put(disp, xyr);
                xyRenderNames.put(disp, xyr.getClassname());
            }
        }
        xyrenders = newXY;
        HashMap<String,ChartBeanInfo> newCat = new HashMap<String,ChartBeanInfo>();
        for (ChartBeanInfo catr : this.catrenders.values()) {
            if (catr.isAbstract()) { // must keep abstract
                newCat.put(catr.getDisplayname(), catr);
                continue;
            }
            RendererDocInfo rdi = rds.findCategoryByClassname(catr.getClassname());
            if (rdi != null && !rdi.isExcluded()) {
                // put under old display name/classname as well, so we can find superclasses...
                newCat.put(catr.getDisplayname(), catr);
                String disp = rdi.getDisplayName();
                catr.setDisplayname(disp);
                catr.setRenderDoc(rdi);
                newCat.put(disp, catr);
                catRenderNames.put(disp, catr.getDisplayname());
            }
        }
        catrenders = newCat;
    }
    
    /**
     * for a given plot type and a render type, return its RenderInfo
     * @param ptype
     * @param rtype
     * @return   null if no renderinfo is applicable
     */
    public ChartBeanInfo getRenderInfo(PlotType ptype, String rtype) {
        ChartBeanInfo renderInfo = null;
        switch (ptype) {
        case PLOT_XY:
        case PLOT_TIME:
        case PLOT_HISTOGRAM:
            renderInfo = this.getXYRendererInfo(rtype);
            break;
        case PLOT_CATEGORY:
        case PLOT_GANTT:
            renderInfo = this.getCategoryRendererInfo(rtype);
            break;
        case PLOT_PIE:
        case PLOT_PIE3D:
        case PLOT_RING:
            // leave renderInfo as null
            break;
        }
//        if (PlotType.PLOT_XY == ptype || 
//            PlotType.PLOT_TIME == ptype ||
//            PlotType.PLOT_HISTOGRAM == ptype) {
//            renderInfo = this.getXYRendererInfo(rtype);
//        } else if (PlotType.PLOT_CATEGORY == ptype) {
//            renderInfo = this.getCategoryRendererInfo(rtype);
//        } // if it's PLOT_PIE, PLOT_PIE3D or PLOT_RING, returns null 
        return renderInfo;
    }
    
    /**
     * short display names to full classnames for XY renderers
     */
    public Map<String,String> getXYRenderNameMap() {
        return this.xyRenderNames;
    }
    /**
     * short display names to full classnames for Category renderers
     */
    public Map<String,String> getCategoryRenderNameMap() {
        return this.catRenderNames;
    }
    private Object readResolve() {
        //p("readResolve() maps are...");
        //printMaps();
        if (xyRenderNames == null) {
            xyRenderNames = new HashMap<String,String>();            
        }
        if (catRenderNames == null) {
            catRenderNames = new HashMap<String,String>();            
        }
        return this;
    }    
    public void printMaps() {
        p("xyRenderers=" + this.xyrenders);
        p("catRenderers=" + this.catrenders);
    }
    
    private static void p(String s) {
        System.err.println("[ChartTypeSystem] " + s);
    }
}