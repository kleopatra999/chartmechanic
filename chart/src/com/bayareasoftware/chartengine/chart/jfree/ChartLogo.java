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

import java.awt.Image;

/**
 * data structure hold the various image(s) or text to be used as a chart logo
 */
public class ChartLogo {

    public Image img = null;
    public int width = 0;
    public int height = 0;
    
    // a scaled version of the logo, to be used if the chart is too small
    public Image smallImg = null;

    // if text is present, use that instead of img
    public String txt;
    
    public boolean visible = false;
    
    public ChartLogo() {
        
    }
}
