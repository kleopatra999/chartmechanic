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
import org.jfree.data.general.DefaultPieDataset;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;

public class PieProducer extends Producer {

    public PieProducer() {
        super();
    }
    
    public Dataset createDataset(ChartInfo ci, SeriesDescriptor sd) {
        DefaultPieDataset ds = new DefaultPieDataset();
        return ds;
    }

    public String populateSingle(Dataset d, SeriesDescriptor currentSD, DataStream rs) throws Exception {
        DefaultPieDataset pd = (DefaultPieDataset) d;
        String catName = rs.getString(currentSD.getXColumn());
        String ret = null;
        if (catName != null) {
            Double v = rs.getDouble(currentSD.getYColumn());
            if (v != null) {
                pd.setValue(catName, v.doubleValue());
                ret = currentSD.getName();
                if (currentSD.getLinkExpression() != null) {
                    String url = ChartContext.translateLinkExpression(
                            rs, rs.getMetadata(), ret, currentSD.getLinkExpression()
                            );
                    this.recordImgMapUrl(d, ret, catName, v, url);
                }
            }
        } 
        return ret;
    }

    public void beginSeries(Dataset d, SeriesDescriptor sd, DataStream r) {
    }

    public Dataset endSeries(Dataset d, SeriesDescriptor sd) {
        return d;
    }
    
}
