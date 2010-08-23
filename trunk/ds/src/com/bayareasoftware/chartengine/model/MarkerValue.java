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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MarkerValue encapsulates the actual data for the marker: either
 * numeric,date-based,or interval values.
 */
@SuppressWarnings("serial")
public class MarkerValue implements Serializable {
    /**
     * values is either a list of double[2] or a list of double[1] depending
     * on whether the marker value is interval or not
     */
    List<double[]> values= new ArrayList<double[]>();
    
    public MarkerValue() {
        
    }
    
    public void addIntervalValues(double v1, double v2) {
        values.add(new double[]{v1,v2});
    }
    public void addSingleValue(double v1) {
        values.add(new double[]{v1});
    }
    
    public List<double[]> getValues() {
        return values;
    }
    
    /**
     * return the singular value for this marker
     */ 
    public double getValue() {
        return values.get(0)[0];
    }
    
}