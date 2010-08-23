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

import org.jfree.data.general.Dataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

/**
 * interface for the various kinds of JFreeChart DataSet producers
 */
public interface Producer {
    
    /**
     * Limit number of dynamic series created to this number
     */
    public static final int MAX_DYNAMIC_SERIES = 30; // TODO: make this a config parameter
    
    /**
     * Limit number of rows on a Category Plot 
     */
	public static final int MAX_CATEGORY_ROWS = 100;  // TODO: make this a config parameter
    
    /**
     * construct a dataset of the appropriate type (based on rendertype of the series or the default render type of the chart)
     * the dataset created is initially empty
     * 
     * @param ci
     * @param sd
     * @return
     */
    public Dataset createDataset(ChartInfo ci, SeriesDescriptor sd);
    
    /**
     * populate the dataset for a given row in the DataStream
     *      * 
     * @param d                   - the currentDataset
     * @param sd                  - the SeriesDescriptor  
     * @param rs                  - the stream of raw data
     * @return                    - return true if a row of the raw data was used to populate
     *                              the dataset.  return false if the row was skipped or not used
     *                              (e.g. because it was outside the start/end time range)
     * @throws Exception
     */
    public boolean populateSingle(Dataset d, SeriesDescriptor sd, DataStream rs) throws Exception;
    
    /** 
     * called at the end of processing a series
     * @param d
     * @param sd
     */
    public Dataset endSeries(Dataset d, SeriesDescriptor sd);

    
    /**
     * called at the beginning of processing a series
     * allows concrete producers to intercept/maintain state at the beginning of a series */
    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r);
    
}
