package com.bayareasoftware.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;

public class AbstractMarkerTag extends BodyTagSupport { 

    protected MarkerDescriptor md;
    
    protected AbstractMarkerTag() {
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
    
    protected static Object[][] ATTS = {
        {"name", "name for this marker, as it will appear on the chart.", true },
        //{"datasource", "", false },
        {"yaxis", "The Y axis <code>[0-3]</code> this marker is aligned with.  Defaults to 0", false },
        {"color", "The color or gradient paint of this marker.  Shorthand for the " +
                        "<code>paint</code> property", false },
        {"visible", "boolean that toggles whether or not this marker is displayed.", false },
        {"template", "Sets the name of a template property set that should be applied to this marker.", false },
        //{"", "", false },
    };    
    public List<AttInfo> getBaseAttributes() {
        return DataTag.makeAttsList(ATTS);
    }
    public void setColor(String c) {
        md.setPaint(c);
    }
    public void setVisible(boolean b) { md.setVisible(b); }
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
}
