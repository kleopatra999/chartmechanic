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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.general.Dataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.TimeConstants;


public abstract class AbstractProducer implements TimeConstants {
    
    protected Log log = LogFactory.getLog(AbstractProducer.class);

    protected SeriesDescriptor currentSeriesDescriptor = null;

    public AbstractProducer() {
    }
    
    protected SeriesDescriptor getCurrentSeriesDescriptor(){
        return currentSeriesDescriptor;
    }

    /**
     * construct a dataset of the appropriate type (based on rendertype of the series or the default render type of the chart)
     * the dataset created is initially empty
     * 
     * @param ci
     * @param sd
     * @return
     */
    protected abstract Dataset createDataset(ChartInfo ci, SeriesDescriptor sd);
    
    /**
     * this is invoked once per row in the DataStream
     * @param d
     * @param rs
     * @throws Exception
     */
    protected abstract void populateSingle(Dataset d, DataStream rs) throws Exception;
    
    /** hook for subclasses to intercept the end of a series */
    protected Dataset endSeries(Dataset d, SeriesDescriptor sd) { 
        return d;
    }
    
    /** hook for subclasses to intercept/maintain state at the beginning of a series */
    protected void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {}
    
  
}
