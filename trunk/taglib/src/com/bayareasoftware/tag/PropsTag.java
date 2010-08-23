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
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.TimeUtil;

public class PropsTag extends BodyTagSupport
implements DynamicAttributes,ITagDoc {
    private PageContext pc;
    private SimpleProps props;
    private String template, prefix;
    public PropsTag() {
        release();
    }
    
    @Override
    public void release() {
        super.release();
        props = new SimpleProps();
        pc = null;
        template = prefix = null;
    }
    
    private static final String[] VALID_PREFIXES = {
      "chart", "plot", "renderer", "title", "legend",
      "domain-axis", "range-axis-0", "range-axis-1",
      "range-axis-2", "range-axis-3"
    };
    private static String validPrefixDoc() {
        StringBuilder sb = new StringBuilder("<code>");
        for (String p : VALID_PREFIXES)
            sb.append(p).append(" ");
        return sb.append("</code>").toString();
    }
    private static Object[][] ATTS =  {
        { "template", "Names this bag of properties as a <b>template</b>" +
        		", which lets these properties be shared across" +
        		" multiple charts.", false },
        { "prefix", "Provides a shorthand for targeting this bag" +
        		" of properties at one particular element of a" +
        		" chart. See the <a href=\"charts.jsp#properties\">" +
        		"chart properties</a> section for details on how the" +
        		" shorhand works.  Valid values for prefix are: " +
        		validPrefixDoc(), false }
    };
    
    // ITagDoc
    public List<AttInfo> getAttributes() {
        return DataTag.makeAttsList(ATTS);
    }
    
    //@Override
    public void setDynamicAttribute(String uri, String localName, Object val)
            throws JspException {
        //p("setDynamicAttribute('" + uri + "','" + localName + "',val='"
          //      + val + "' type=" + val.getClass().getName() + ")");
        props.set(null, localName, val != null ? val.toString() : null);
    }
    
    public void setTemplate(String t) {
        this.template = t;
    }

    public void setPrefix(String p) {
        this.prefix = p;
    }
    @Override
    public void setPageContext(PageContext pc) {
        super.setPageContext(pc);
        this.pc = pc;
        /*p("servlet version: " + pc.getServletContext().getMajorVersion()
                + "." + pc.getServletContext().getMinorVersion());*/
    }
    @Override
    public int doAfterBody() throws JspException {
        
        try {
            if (bodyContent == null) return super.doAfterBody();
            String body = DataTag.readBody(bodyContent);
            if (body != null)
                props.load(body);
            props = props.trimWhitespace();
            if (prefix != null) {
                SimpleProps tmp = new SimpleProps();
                for (String k : props.keySet())
                    tmp.set(prefix, k, props.get(k));
                props = tmp;
            }
            if (template != null) {
                PageObjects.get(pc).storeTemplate(template, props);
            }
            ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
            if (ct == null) {
                if (template == null)
                    throw new JspException(
                            "cannot find enclosing ChartTag, and not a template"
                            );
            } else {
                ct.setProps(props);
            }
        } catch (IOException e) { throw new JspException(e); }
        release();
        return super.doAfterBody();
    }
    private static void p(String s) {
        System.out.println("[PropsTag] " + s);
    }

}
