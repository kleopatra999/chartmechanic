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
package com.bayareasoftware.tag;

import java.util.List;

import javax.servlet.jsp.JspException;

import com.bayareasoftware.chartengine.chart.jfree.Producer;

public class SeriesTag extends AbstractSeriesTag
implements ITagDoc {

    public SeriesTag() {
        super();
    }
    @Override
    public void release() {
        super.release();
    }

    public void setDatasource(String datasource) {
        sd.setSource(datasource);
    }
    
    public void setX(int x) { sd.setXColumn(x); }
    public void setY(int x) { sd.setYColumn(x); }
    public void setZ(int x) { sd.setZColumn(x); }

    public void setDynamicNameColumn(int col) {
        sd.setSeriesNameFromData(col);
    }
    
    private static final Object[][] ATTS = {
        {"datasource", "The datasource ID that this series will pull data from.", true },
        {"x", "The (1-based) column index on the series' datasource " +
            "for the X axis values of this series.  Defaults to <code>1</code>", false },
        {"y", "The (1-based) column index on the series' datasource " +
            "for the Y axis values of this series.  Defaults to <code>2</code>", false },
        {"z", "The (1-based) column index on the series' datasource " +
            "for the Z values of this series, if needed.  Note that only a few" +
            " graph-type renderers (e.g., <code>bubble</code>) even use" +
            " the Z value for anything.  Defaults to <code>3</code>", false },
        {"dynamicNameColumn", "Specifies a (1-based) column index whose value(s)" +
          " will determine the names of more than one series on this chart.  You" +
          " will get one series created for each distinct value in the specified" +
          " column.  As a sanity check, no more than " + Producer.MAX_DYNAMIC_SERIES +
          " series will be created.", false },
    };
    
    public List<AttInfo> getAttributes() {
        List<AttInfo> l = DataTag.makeAttsList(BASE_ATTS);
        return DataTag.makeAttsList(ATTS, l);
    }    

    
    @Override
    public int doEndTag() throws JspException {
        ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
        if (ct == null)
            throw new JspException("cannot find enclosing ChartTag");
        ct.addSeriesDescriptor(sd);
        release();
        return super.doEndTag();
    }
    
    private static void p(String s) {
        System.out.println("[SeriesTag] " + s);
    }
}
