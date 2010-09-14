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

import java.util.Date;
import java.util.HashMap;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.Dataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

public class GanttProducer extends Producer {
    private HashMap<String,TaskSeries> taskSeriesMap;
    
    public GanttProducer() {
        
    }

    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {
    }

    public Dataset createDataset(ChartInfo ci, SeriesDescriptor sd) {
        TaskSeriesCollection collection = new TaskSeriesCollection();
        taskSeriesMap = new HashMap<String,TaskSeries>();
        return collection;
    }

    public Dataset endSeries(Dataset d, SeriesDescriptor sd) {
        TaskSeriesCollection collection = (TaskSeriesCollection)d;
        
        if (taskSeriesMap != null) {
            for (TaskSeries ts : taskSeriesMap.values()) {
                collection.add(ts);
            }
        }
        return d;
    }

    public String populateSingle(Dataset d, SeriesDescriptor currentSD, DataStream rs)
            throws Exception {
        String seriesName = currentSD.getName();
        if (currentSD.getSeriesNameFromData() != -1) {
            seriesName = rs.getString(currentSD.getSeriesNameFromData());
        }
        if (seriesName != null) {
            TaskSeries ts = taskSeriesMap.get(seriesName);
            if (ts == null) {
                ts = new TaskSeries(seriesName);
                taskSeriesMap.put(seriesName,ts);
            }
            String taskName = rs.getString(currentSD.getXColumn());
            Date startDate = rs.getDate(currentSD.getYColumn());
            Date endDate = rs.getDate(currentSD.getZColumn());
            if (taskName != null && startDate != null && endDate != null) {
                ts.add(new Task(taskName,startDate,endDate));
            }
        }
        return seriesName;
    }

}
