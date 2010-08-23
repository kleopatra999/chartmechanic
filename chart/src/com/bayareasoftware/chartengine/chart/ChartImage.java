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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A ChartImage is what the result from a chart cache hit
 * encapsulates the data stream and some meta information about the chart
 *  
 **/
public class ChartImage {
    private InputStream is;
    private long size;
    private String imageType;
    
    public static ChartImage NULL = new ChartImage(null,0);
    
    public ChartImage (InputStream is, long size) {
        this.is = is;
        this.size = size;
        this.imageType = "image/png";
    }

    public static ChartImage makeChartImage(File f) {
        try {
            return new ChartImage(new FileInputStream(f),f.length());
        } catch (FileNotFoundException e) {
            return NULL;
        }
    }
    
    
    public boolean isNull() {
        return (this == NULL);
    }
    
    public String getImageType() {
        return imageType;
    }
    public InputStream getData() {
        return is;
    }
    public long getSize() {
        return size;
    }

}


