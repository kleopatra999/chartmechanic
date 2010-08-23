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

import java.util.HashMap;

/**
 * ChartDriverManager returns ChartDriver objects that are the main entry point into the charting API
 */
public class ChartDriverManager {
    public static final String JFREECHART = "com.bayareasoftware.chartengine.chart.jfree.JFreeChartDriver";

    // all the Chart drivers we've already instantiated
    private static HashMap<String,Class<? extends ChartDriver>> instantiatedDrivers = new HashMap<String,Class<? extends ChartDriver>>();
    
    public static ChartDriver getChartDriver(String driver) {
        Class<? extends ChartDriver> clazz = instantiatedDrivers.get(driver);
        if (clazz == null) {
            if (JFREECHART.equals(driver)) {
                try {
                    clazz = Class.forName(JFREECHART).asSubclass(ChartDriver.class);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Class named: " + JFREECHART + " was not found");
                } 
            } else  {
                    throw new RuntimeException("ChartDriver named: " + driver + " is not recognized");
                }
                instantiatedDrivers.put(driver, clazz);
        }
        ChartDriver c;
        try {
            c = (ChartDriver) (clazz.newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate instance of class: " + clazz + " because of " + e);
        }
        return c;
    }
}
