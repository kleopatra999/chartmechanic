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

public interface ChartConstants {
    /* plot types */
    // moved to PlotType enum class
//    public static final String PLOT_XY = "xy";
//    public static final String PLOT_TIME = "timeseries";
//    public static final String PLOT_PIE = "pie";
//    public static final String PLOT_PIE3D = "pie3d";
//    public static final String PLOT_CATEGORY = "category";
//    public static final String PLOT_RING = "ring";
//    public static final String PLOT_HISTOGRAM = "histogram";
    
    public static String CM_PROP_PREFIX = "_cm_";
    public static final int MAX_RANGE_AXES = 4;
    
    /* standard param names for charts & series */
    public static final String PARAM_START_DATE = "startDate";
    public static final String PARAM_END_DATE = "endDate";
    
    /* defaults for standard params */
    public static final String DEFAULT_START_DATE = "1000-01-01";
    public static final String DEFAULT_END_DATE = "3000-12-31";
}
