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
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataSource;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.util.DateUtil;

/**
 * Write the contents of a data source as an HTML table.
 * Intended primarily for debugging, but could be prettified
 * for production rendering too.
 * 
 * @author dave
 *
 */
public class DataTableTag extends TagSupport implements ITagDoc {

    private DataSourceInfo dsi;
    private PageContext page;
    private int offset = 0, length = 25;
    private String cSSClass, style;
    
    @Override
    public void setPageContext(PageContext p) {
        super.setPageContext(p);
        page = p;
    }

    @Override
    public void release() {
        super.release();
        page = null;
        dsi = null;
        offset = 0;
        length = 25;
    }
    
    public void setStyle(String st) {
        style = st;
    }
    public void setCss(String clazz) {
        setCssClass(clazz);
    }
    public void setCssClass(String clazz) {
        this.cSSClass = clazz;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDatasource(String dsid) {
        dsi = PageObjects.get(page).getDataSource(dsid);
        if (dsi == null) {
            throw new RuntimeException(
                    "cannot find data source id='" + dsid
                    + "' in PageContext");
            
        }
    }

    private String getTH(ColumnInfo ci) {
        // FIXME: escape html in name
        String ret = "<th>" + ci.getName()+ "<br/>\n";
        int type = ci.getType();
        if (DataType.isNumeric(type)) ret += "(NUMBER)";
        else if (DataType.DATE == type) {
            ret += "(DATE)<br/>\n";
            String fmt = ci.getFormat();
            if (fmt != null) {
                ret += "(" + fmt + ")";
            }
        } else if (type == DataType.STRING || type == DataType.BOOLEAN) {
            ret += "(TEXT)";
        } else {
            ret += DataType.toString(type);
        }
        return ret + "</th>";
    }
    private void writeTable() throws Exception {
        DataStream stream = null;
        try {
            DataSource src = DSFactory.createDataSource(dsi);
            stream = DSFactory.eval(src, null);
            Metadata md = stream.getMetadata();
            JspWriter out = page.getOut();
            out.print("<table");
            if (cSSClass != null) out.print(" class=\"" + cSSClass + "\"");
            if (style != null) out.print(" style=\"" + style + "\"");
            out.println("><tr>");
            // header row
            for (int i = 1; i <= md.getColumnCount(); i++) {
                ColumnInfo ci = md.getColumnInfo(i);
                out.print(getTH(ci));
            }
            out.println("</tr>");
            int rows = 0;
            while (rows < offset && stream.next()) rows++;
            DateFormat df = DateUtil.createDateFormat(
                    "yyyy-MM-dd hh:mm:ss"
                    );
            while (rows < offset + length && stream.next()) {
                out.println("<tr class=\""
                        + ((rows % 2 == 0) ? "even" : "odd")
                        + "\">"
                        );
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    Object o = stream.getObject(i);
                    int ctype = md.getColumnType(i);
                    //p("(" + rows + "," + i + ")='" + o + "'");
                    if (o == null) o = "&nbsp;";
                    else if (ctype == DataType.DATE) {
                        // give a nice precise date format, to make
                        // it clear what the actual date is...
                        o = df.format((Date)o);
                    } else if (ctype == DataType.STRING) {
                        String text = stream.getString(i);
                        // FIXME: truncate iff too long
                    }
                    // FIXME: escape HTML here!
                    out.println("<td>" + o + "</td>");
                }
                out.println("</tr>");
                rows++;
            }
            out.println("</table>");
        } finally {
            if (stream != null) stream.close();
        }
    }
    
    public int doEndTag() throws JspException {
        try {
            writeTable();
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new JspException("exception writing table", e);
        } finally {
            release();
        }
        return super.doEndTag();
    }
    
    private static Object[][] ATTS = {
        {"datasource", "datasource ID to display as an HTML table", true },
        {"css", "Sets the <code>class=...</code> CSS class of the generated table", false, "cssClass" },
        {"style", "Sets the <code>style=...</code> CSS style attribute of the generated table", false },
        {"offset", "Specifies the number of rows to skip in the beginning of" +
        		" the data.  Defaults to 0", false },
        {"length", "Specifies the maximum number of rows to display.  Defaults to 25", false },
        //{"", "", false },
    };    
    public List<AttInfo> getAttributes() {
        return DataTag.makeAttsList(ATTS);
    }    
    private static void p(String s) {
        System.out.println("[DataTableTag] " + s);
    }
}
