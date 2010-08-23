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

import java.util.List;

import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

public class HistogramProducer implements Producer {
    
    private double minValue, maxValue, binSize;
    
    public HistogramProducer(ChartInfo ci) {
        // TODO - only use the first series to make a histogram
        List<SeriesDescriptor> series = ci.getSeriesDescriptors();

        if (series.size() > 0) {
            SeriesDescriptor sd = series.get(0);
            minValue = sd.getHistogramMin();
            maxValue = sd.getHistogramMax();
            binSize = sd.getHistogramBinSize();
        }
    }
    public Dataset createDataset(ChartInfo ci,SeriesDescriptor sd) {
        // this dataset always has only one series
        SimpleHistogramDataset ret = new SimpleHistogramDataset(sd.getName());
        ret.setAdjustForBinSize(false);
        addBins(ret);
        return ret;
    }

    public boolean populateSingle(Dataset d, SeriesDescriptor sd, DataStream rs) throws Exception {
        boolean res = false;
        SimpleHistogramDataset shd = (SimpleHistogramDataset) d;
        int valueIndex = sd.getYColumn();
        Double val = rs.getDouble(valueIndex);
        if (val != null) {
        //p("adding observation for series '" + sd.getName() + ": " + val + " valueIndex="
          //      + valueIndex + " '" + rs.getMetadata().getColumnName(valueIndex) + "'");
            if (val >= minValue && val < maxValue && binSize > 0) {
                shd.addObservation(val);
                res = true;
            } else {
                //p("skipping out of range observation: " + val + " min/max=" + minValue + "/" + maxValue);
            }
        }
        return res;
    }

    private void addBins(SimpleHistogramDataset dset) {
        if (minValue < maxValue && binSize > 0) {
            for (double x = minValue; x  < maxValue; x += binSize) {
                SimpleHistogramBin bin = new SimpleHistogramBin(x, x + binSize, true, false);
                dset.addBin(bin);
            }
        }
    }
    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {
    }
    public Dataset endSeries(Dataset d, SeriesDescriptor sd) {
        return d;
    }
    
}
