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

import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

public class XYProducer implements Producer {
    public XYProducer() {
        super();
    }
    
    public Dataset createDataset(ChartInfo ci, SeriesDescriptor sd) {
        DefaultTableXYDataset xyd = new DefaultTableXYDataset(); 
        return xyd;
    }
    
    /**
     * keep a mapping of SeriesDescriptor to XYSeries
     * so we are not relying solely on series names to find the XYSeries
     * this allows the case of multiple series with the same name.  Chart looks goofy but it's better than
     * losing the same named series altogether
     */
    private Map<SeriesDescriptor,XYSeries> sXYMap;
    
    /**
     * get the TimeSeries that matches the current SeriesDescriptor;
     * @return
     */
    private XYSeries getXYSeries(SeriesDescriptor sd) {
        XYSeries ret = null;
        if (sXYMap != null) {
            ret = sXYMap.get(sd);
        }
        return ret;
    }
    
    private void addXYSeries(XYSeries t,SeriesDescriptor sd) {
        if (sXYMap == null) {
            sXYMap = new HashMap<SeriesDescriptor,XYSeries>();
        }
        sXYMap.put(sd,t);
    }


    public boolean populateSingle(Dataset d, SeriesDescriptor sd, DataStream rs)
            throws Exception {
        DefaultTableXYDataset xyd = (DefaultTableXYDataset) d;
        XYSeries series = null;
        String seriesName = sd.getName();
        if (sd.getSeriesNameFromData() != -1) {
            seriesName = rs.getString(sd.getSeriesNameFromData());
        }
        series = getXYSeries(sd);
        if (series == null) {
            series = new XYSeries(seriesName, true, false);
            xyd.addSeries(series);
            addXYSeries(series,sd);
        }
        
        Double x = rs.getDouble(sd.getXColumn());
        Double y = rs.getDouble(sd.getYColumn());
        if (x == null || y == null) {
            return false;
        }
        series.addOrUpdate(x, y);
        return true;
    }

    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {
    }

    public Dataset endSeries(Dataset d, SeriesDescriptor sd) {
        return d;
    }

}
