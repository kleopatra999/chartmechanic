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
 * describes the various types of supported plots
 *
 * the string value of the plottypes cannot change.  they are persistent properties
 * however, the display name of the plotType can be altered as needed 
 */
public enum PlotType {
    PLOT_XY("xy","XY", "org.jfree.chart.plot.XYPlot"),
    PLOT_TIME("timeseries","Time Series", "org.jfree.chart.plot.XYPlot"),
    PLOT_PIE("pie", "Pie", "org.jfree.chart.plot.PiePlot"),
    PLOT_PIE3D("pie3d", "Pie (3D)", "org.jfree.chart.plot.PiePlot3D"),
    PLOT_CATEGORY("category", "Category","org.jfree.chart.plot.CategoryPlot"),
    PLOT_RING("ring", "Ring Plot","org.jfree.chart.plot.RingPlot"),
    PLOT_HISTOGRAM("histogram", "Histogram","org.jfree.chart.plot.XYPlot"),
    PLOT_GANTT("gantt","Gantt","org.jfree.chart.plot.CategoryPlot");
    
    final String val;
    final String displayName;
    final String plotClass;
    
    PlotType(String val, String displayName, String plotClass) {
        this.val = val;
        this.displayName = displayName;
        this.plotClass = plotClass;
    }
    
    @Override
    public String toString() {
        return val;
    }
    
    public String displayName() {
        return displayName;
    }
    
    public String plotClass() {
        return plotClass;
    }
    
    /** get the PlotType that corresponds to the string,
     * if the string value is invalid return null;
     * @param s
     * @return null if string value is invalid
     */
    public static PlotType get(String s) {
        PlotType ret = null;
        if (s != null) {
            for (PlotType f: values()) {
                if (f.toString().equalsIgnoreCase(s)) {
                    ret = f;
                    break;
                }
            }
        }
        if (ret == null && "time".equalsIgnoreCase(s))
            ret = PLOT_TIME;
        return ret;
    }
    
    /** 
     * does this PlotType have render options
     * @return
     */
    public boolean hasRenderOption() {
        return (this == PLOT_XY || this == PLOT_TIME || this == PLOT_CATEGORY || this == PLOT_HISTOGRAM); 
    }

}
