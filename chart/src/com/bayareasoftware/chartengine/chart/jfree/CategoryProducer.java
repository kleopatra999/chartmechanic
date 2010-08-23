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

import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;


public class CategoryProducer implements Producer {
    
	private int rowCount = 0;
    private Map<String,Integer> dynamicSeries = new HashMap();
    public CategoryProducer() {
    }
    
    public Dataset createDataset(ChartInfo ci, SeriesDescriptor sd) {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        return ds;
    }

    public boolean populateSingle(Dataset d, SeriesDescriptor currentSD, DataStream rs) throws Exception {
        boolean res = false;
        DefaultCategoryDataset cd = (DefaultCategoryDataset) d;
        String seriesName = currentSD.getName();
        
        if (currentSD.getSeriesNameFromData() != -1) {
            seriesName = rs.getString(currentSD.getSeriesNameFromData());
            if (seriesName != null) {
                // check that we're not exceeding max dynamic series
                if (dynamicSeries.get(seriesName) != null) {
                    // OK, already have seen this series
                } else if (dynamicSeries.size() >= MAX_DYNAMIC_SERIES) {
                    return res;
                } else {
                    dynamicSeries.put(seriesName, dynamicSeries.size());
                }
            } else {
                // null series name
                return res;
            }
        }
        rowCount++;
        if (rowCount < MAX_CATEGORY_ROWS) {
        	String category = rs.getString(currentSD.getXColumn());
        	Double val = rs.getDouble(currentSD.getYColumn());
        //System.err.println("add category='" + category + "' series='" + seriesName + "' val=" + val
        //        + " rowcount=" + cd.getRowCount() + " colcount=" + cd.getColumnCount());
        	if (seriesName != null && category != null && val != null) {
        		cd.addValue(val, seriesName, category);
        		res = true;
        	}
    	
        } else {
        	// FIXME: warn that max category rows exceeded....
        }
        return res;
    }

    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {
    	rowCount = 0;
    }

    public Dataset endSeries(Dataset d, SeriesDescriptor sd) {
        return d;
    }

}
