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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.StringUtil;

public class MarkerTag extends AbstractMarkerTag implements ITagDoc {

    
    public MarkerTag() {
        release();
    }

    public void setDatasource(String datasource) {
        md.setSource(datasource);
    }

    public void setBandValues(String vals) {
        md.setValuesAsString(vals);
        double[] d = md.getValues();
        // FIXME: time axis?
        if (d != null && d.length > 1) {
            md.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC_INTERVAL);
        }
    }

    public void setValue(String vals) {
        setBandValues(vals);
    }
    
    public void setDateValues(String valStr) {
        if (valStr != null) {
            String[] vals = StringUtil.splitCompletely(valStr, '|');
            if (vals.length > 1) {
                md.setType(MarkerDescriptor.MARKER_TYPE_DATE_INTERVAL);
            } else {
                md.setType(MarkerDescriptor.MARKER_TYPE_DATE);                
            }
            StringBuilder sb = new StringBuilder();
            DateRecognizer dr = new DateRecognizer();
            dr.parse(vals[0]);
            if (dr.failed()) {
                throw new IllegalArgumentException(
                        "Cannot parse date string '" + vals[0] + "'"
                        );
            }
            DateFormat dft = dr.getSimpleDateFormat();
            for (String ds : vals) {
                try {
                    Date d = dft.parse(ds);
                    if (sb.length() > 0) sb.append('|');
                    sb.append(d.getTime());
                } catch (ParseException pe) {
                    throw new IllegalArgumentException(
                            "Cannot parse date string '" + ds + "'"
                            );
                }
            }
            md.setValuesAsString(sb.toString());
        }
    }
    
    public void setDate(String dateStr) {
        setDateValues(dateStr);
    }

    public void setXaxis(boolean b) {
        md.setRange(!b);
    }
    @Override
    public int doEndTag() throws JspException {
        ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
        if (ct == null)
            throw new JspException("cannot find enclosing ChartTag for marker "
                    + md.getName());
        ct.addMarkerDescriptor(md);
        //ct.addSeriesDescriptor(sd);
        release();
        return super.doEndTag();
    }
    
    private static Object[][] ATTS = {
        {"datasource", "Datasource for interval/band marker values", false },
        {"value", "Specifies the numeric value(s) for this marker, along a numeric axis." +
                        " A line marker" +
                        " will be drawn for a single value, multiple values will" +
                        " produce an interval (band) marker.  Multiple values" +
                        "must be separated by a vertical bar '|'", false, "bandValues" },
        {"dateValues", "Specifies the date value(s) for this marker, along a date/time" +
                        " axis.  Multiple values will produce an interval (band) marker." +
                        " Multiple values must be separated by a vertical bar '|'.  The" +
                        " format of the dates must comply with one of " +
                        "<a href=\"data.jsp#date-formats\">the valid date formats</a>" +
                        "for the <code>data</code> tag.", false, "date" },
        {"xaxis", "boolean that specifies if this marker should be on the X (domain) axis", false },
        {"template", "Sets the name of a template property set that should be applied to this marker.", false },
        //{"", "", false },
    };    
    public List<AttInfo> getAttributes() {
        List ret = super.getBaseAttributes();
        return DataTag.makeAttsList(ATTS, ret);
    }    
    
    private static void p(String s) {
        System.out.println("[MarkerTag] " + s);
    }

}
