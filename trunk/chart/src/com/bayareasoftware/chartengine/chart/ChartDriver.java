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

import java.util.Map;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.LogoInfo;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;

/**
 * the interface to various kinds of charting frontends such as JFreeChartDriver
 */
public interface ChartDriver {

    public ChartTypeSystem getTypeSystem();

//    /**
//     * set the watermark chart logo.  if null, then clear it and use no logo
//     * @param u
//     */
//    public void setChartLogo(URL u);

    /**
     * set the watermark chart logo.  if null, then clear it and use no logo
     * @param u
     */
    public void setDefaultChartLogo(LogoInfo logo);
    
//    /**
//     * return the existing watermark logo URL.  could be null
//     * @return
//     */
//    public URL getChartLogoURL();
    
    /**
     * main entry point for creating charts
     * create a chart and return it.  Does not cache the chart
     * 
     * 
     * @param ci        ChartInfo that describes the UI properties fo the chart
     * @param logo      LogoInfo (can be null) that describes the watermark/logo for the chart
     * @param sMap      the map of descriptor sid's to DataStream that supply all the data for the chart series and markers
     * @param template  Template of default properties,  can be null
     * @param res - an optional ChartResult can be passed in to guide the creation of the 
     *              object.  If the ChartResult has filePaths set, then those files will be use to materialize
     *              the image, thumbnail, and image maps
     *              can be null;
     * @return
     */
    public ChartResult create(ChartInfo ci, LogoInfo logo, Map<Integer,DataStream> sMap, SimpleProps template, ChartResult dr) throws Exception;

    
    /**
     * given a chart, calculate the datastream that is the 'JOIN' of all the data that is being plotted
     *   
     * @param ci
     * @param sMap
     * @param template
     * @param maxRows                   - if > 0, limit the number of rows returned
     * @return          data stream of results
     * @throws Exception
     */
    public DataStream getData(ChartInfo ci, Map<Integer,DataStream> sMap, SimpleProps template, int maxRows) throws Exception;
    
    /**
     * Maximum chart dimensions that we'll render, for sanity's sake.
     */
    public static final int MAX_WIDTH = 1920;
    public static final int MAX_HEIGHT = 1200;
    
    
}