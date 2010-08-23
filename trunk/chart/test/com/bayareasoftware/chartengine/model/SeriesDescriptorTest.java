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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SeriesDescriptorTest {
    @Test
    public void testSerialize() {
        SeriesDescriptor sd = new SeriesDescriptor();
        sd.setName("si1");
        sd.setColor("black");
        sd.setSource("123");
        sd.setHistogramBinSize(0.5);
        sd.setHistogramMin(0.1);
        sd.setHistogramMax(100.0);
        sd.setRenderer("StackedXYAreaRenderer");
        SimpleProps sp = new SimpleProps();
        sp.put("renderAsPercentages","false");
//        sd.setRendererProperties("prop1=value1");
        sd.setRendererProps(sp);
        sd.setTimePeriod(TimeConstants.TIME_WEEK);
        sd.setXColumn(3);
        sd.setYColumn(1);
        sd.setZColumn(2);
        sd.setSeriesNameFromData(4);
        
        SeriesDescriptor sd2 = new SeriesDescriptor();
        SimpleProps p = sd.serializeToProps(null, null);
        sd2.deserializeFromProps(p, null);
        
        assertTrue(sd.equals(sd2));
        
        SeriesDescriptor sd3 = new SeriesDescriptor();
        
        String prefix = "series.0";
        sd3.deserializeFromProps(sd.serializeToProps(null,prefix),prefix);
        
        assertTrue(sd.equals(sd3));

    }
 
 
    
}
