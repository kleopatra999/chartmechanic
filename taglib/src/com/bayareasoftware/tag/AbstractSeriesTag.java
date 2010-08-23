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
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import com.bayareasoftware.chartengine.chart.jfree.Producer;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.TimeUtil;

/**
 * Base class of generic series and function-based series
 * @author dave
 *
 */
public abstract class AbstractSeriesTag extends BodyTagSupport {

    protected SeriesDescriptor sd;
    public AbstractSeriesTag() {
        sd = new SeriesDescriptor();
    }
    @Override
    public void release() {
        super.release();
        sd = new SeriesDescriptor();
    }
    
    public void setName(String name) {
        sd.setName(name);
    }
    
    protected boolean setDynamicAttribute(String name, String val) throws JspException {
        String nl = name.toLowerCase();
        if ("renderer".equals(nl) || "render".equals(nl)
                || "graph-type".equals(nl) || "graph".equals(nl)
                || "type".equals(nl) || "graphType".equals(nl)) {
            sd.setRenderer(val);
        } else if ("color".equals(nl) || "paint".equals(nl)) {
            sd.setColor(val);
        } else if ("time".equals(nl) || "timeperiod".equals(nl)
                || "period".equals(nl)) {
            int period = TimeUtil.decodeTimeString(val);
            sd.setTimePeriod(period);
        } else {
            return false;
        }
        return true;
    }
    
    protected static final Object[][] BASE_ATTS = {
        {"name", "The name of this series as it will appear on the chart.", true },
        {"graphType", "Specifies the <a href=\"charts.jsp#graph-types\">graph type</a>" +
            " for this series.  This will override the default graph type" +
            " set for the entire chart, if any.", false,
            "renderer", "render", "type", "graph-type"},
        {"color", "Sets the color (or gradient paint) for this series.", false, "paint" },
        {"timeperiod", "Sets the time frequency of data points for this series, " +
            "which in turn controls the \"width\" of each point for the series. " +
            "Most relevant when using certain graphTypes (e.g., <code>bar</code>).",
            false, "time", "period" },
        {"yaxis", "Specifies the Y (or <code>range</code>) axis that this series" +
            " will bind to.  Valid values are <code>0 1 2 3</code>.  Defaults to <code>0</code>.",
            false },
        {"visible", "<code>boolean</code> to toggle if this series is shown on the" +
                        " chart or not.", false },
    };    

}
