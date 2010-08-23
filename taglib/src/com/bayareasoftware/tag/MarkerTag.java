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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StringUtil;

public class MarkerTag extends BodyTagSupport implements ITagDoc {

    private MarkerDescriptor md;
    
    public MarkerTag() {
        release();
    }
    @Override
    public void release() {
        super.release();
        md = new MarkerDescriptor();
        md.setRange(true); // assume 1st y axis
        md.setAxisIndex(1);
        md.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC); // assume single value
    }
    public void setName(String name) {
        md.setName(name);
    }

    public void setDatasource(String datasource) {
        md.setSource(datasource);
    }

    public void setColor(String c) {
        md.setPaint(c);
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

    public void setVisible(boolean b) { md.setVisible(b); }
    public void setXaxis(boolean b) {
        md.setRange(!b);
    }
    public void setYaxis(int axis) {
        if (axis >= 0 && axis <= 3) {
            md.setAxisIndex(axis);
        } else {
            throw new IllegalArgumentException(
                    "invalid axis index: '" + axis + "' must be 1 <= axis <= 4"
                    );
        }
    }
    
    public void setTemplate(String template) {
        SimpleProps sp = PageObjects.get(pageContext).getTemplate(template);
        if (sp != null) {
            String s = sp.get("layer");
            if (s != null) {
                md.setLayer(s);
            }
            md.setMarkerProps(sp);
        }
    }
    @Override
    public int doAfterBody() throws JspException {
        try {
            if (bodyContent == null) return super.doAfterBody();
            String body = DataTag.readBody(bodyContent);
            if (body != null) {
                body = body.trim();
                SimpleProps sp = new SimpleProps(body);
                sp = sp.trimWhitespace();
                // don't stomp on existing props if present
                SimpleProps template = md.getMarkerProps();
                if (template != null) {
                    for (String key : template.keySet())
                        if (sp.get(key) == null)
                            sp.put(key, template.get(key));
                }
                String s = sp.get("layer");
                if (s != null) {
                    md.setLayer(s);
                }
                md.setMarkerProps(sp);
                //.setRendererProps(sp);
            }
        } catch (IOException ioe) {
            throw new JspException("error ready tag body", ioe);
        }
        return super.doAfterBody();
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
        {"name", "name for this marker, as it will appear on the chart.", true },
        //{"datasource", "", false },
        {"yaxis", "The Y axis <code>[0-3]</code> this marker is aligned with.  Defaults to 0", false },
        {"color", "The color or gradient paint of this marker.  Shorthand for the " +
        		"<code>paint</code> property", false },
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
        {"visible", "boolean that toggles whether or not this marker is displayed.", false },
        {"xaxis", "boolean that specifies if this marker should be on the X (domain) axis", false },
        {"template", "Sets the name of a template property set that should be applied to this marker.", false },
        //{"", "", false },
    };    
    public List<AttInfo> getAttributes() {
        return DataTag.makeAttsList(ATTS);
    }    
    
    private static void p(String s) {
        System.out.println("[MarkerTag] " + s);
    }

}
