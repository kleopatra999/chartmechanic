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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.StandardProps;
import com.bayareasoftware.chartengine.util.URLUtil;

public class DataTag extends BodyTagSupport
implements DynamicAttributes, ITagDoc {

    private String id;
    private PageContext pc;
    private Metadata md;
    private String type;
    // JDBC only
    private String jndiName, driver;
    // used by jdbc, and others when we get them
    private String url, username, password;
    private boolean parsedBody = false;
    public DataTag() {
        release();
    }
    
    private static final Object[][] ATTS = {
        {"id", "Identifier for this data source, so that" +
           " other tags can refer to it.  The ID should be " +
           "unique within a JSP page.", true },
        {"type", "Type of the data source.  Currently only" +
          " CSV or SQL are supported.  Default assumes CSV", false},
        {"jndiName", "JNDI name of a SQL data source." +
              " Use either this or 'driver'/'url' are required", false, "jndi" },
        { "driver", "JDBC driver class for when type='SQL'", false },
        { "url", "JDBC driver URL for when type='SQL'", false },
        { "username", "resource username for data URL", false, "user" },
        { "password", "resource password for data URL", false, "passwd" },
    };
    @Override
    public void release() {
        md = null;
        id = null;
        pc = null;
        type = "csv";
        jndiName = null;
        driver = url = username = password = null;
        parsedBody = false;
        super.release();
    }
    
    // ITagDoc
    public List<AttInfo> getAttributes() {
        return makeAttsList(ATTS);
    }
    public static List<AttInfo> makeAttsList(Object[][] objs) {
        return makeAttsList(objs, new ArrayList());
    }    
    public static List<AttInfo> makeAttsList(Object[][] objs,
            List<AttInfo> l) {
        AttInfo at;
        for (Object[] oa : objs) {
            at = new AttInfo((String)oa[0], (String)oa[1], (Boolean)oa[2]);
            for (int i = 3; i < oa.length; i++)
                at.addAlias((String)oa[i]);
            l.add(at);
        }
        return l;
    }
    //@Override
    public void setDynamicAttribute(String uri, String localName, Object obj)
    throws JspException {
        String name = localName.toLowerCase();
        String val = obj != null ? obj.toString() : null;
        if ("type".equals(name)) {
            if ("csv".equalsIgnoreCase(val) || "sql".equalsIgnoreCase(val)) {
                type = val.toLowerCase();
            } else {
                throw new IllegalArgumentException(
                        "invalid data source type '" + val + "', expecting either "
                        + " 'csv' or 'sql'"
                        );
            }
        } else if ("jndiname".equals(name) || "jndi".equals(name)) {
            jndiName = val;
        } else if ("driver".equals(name)) {
            driver = val;
        } else if ("url".equals(name)) {
            url = val;
        } else if ("user".equals(name) || "username".equals(name)) {
            username = val;
        } else if ("password".equals(name) || "passwd".equals(name)) {
            password = val;
        } else {
            throw new IllegalArgumentException(
                    "do not understand attribute '" + localName + "'");
        }
        //p("setDynamicAttribute('" + uri + "','" + localName + "',val='"
          //      + val + "' type=" + val.getClass().getName() + ")");
        //throw new IllegalArgumentException("do not understand attribute: "
          //      + localName + "=" + val);
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setPageContext(PageContext pc) {
        super.setPageContext(pc);
        this.pc = pc;
    }
    
    @Override
    public int doEndTag() throws JspException {
        if (parsedBody) {
            release();
            return super.doEndTag();
        }
        // no body - required to be type="csv" with a URL
        if (!"csv".equalsIgnoreCase(type) || url == null) {
            throw new JspException(
                    "data tag (ID=" + id + ") without a body must" +
                    " have type=\"csv\" and a url pointing to data"
                    );
        }
        try {
            URL u = URLUtil.safeURL(url);
            if (u == null) {
                throw new JspException("cannot load URL '" + url + "'");
            }
            Reader rdr = new InputStreamReader(u.openStream());
            String data = null;
            try {
                data = readAsString(rdr);
            } finally {
                rdr.close();
            }
            DataSourceInfo dsi = parseCSVData(data);
            storeDataSource(dsi);
        } catch (IOException e) {
            throw new JspException("cannot load URL '" + url + "'", e);
        }
        release();
        return super.doEndTag();
    }
    
    private DataSourceInfo parseCSVData(String data) throws IOException {
        DataSourceInfo ds = null;
        ds = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        ds.setProperty(DataSourceInfo.CSV_DATA, data);
        if (md == null) {
            InferredData id = DataInference.get().inferFromCSV(data);
            RawData rd = id.getRawData();
            md = rd.metadata;
            ds.setInputMetadata(md);
            ds.setDataStartRow(rd.dataStartRow);
            ds.setDataEndRow(rd.getDataEndRow());
            //p("inferred for CSV " + md);
        }
        return ds;
    }
    
    private void storeDataSource(DataSourceInfo dsi) {
        dsi.setId(id);
        ChartTag ct = (ChartTag) findAncestorWithClass(this, ChartTag.class);
        /*if (ct == null)
            throw new JspException("cannot find enclosing ChartTag");
            */
        if (ct != null) {
            // if enclosed in a chart tag, just make it private to that chart
            ct.addDataSource(dsi);
        } else {
            // if not in a chart tag, export to the rest of the page
            PageObjects.get(pc).addDataSource(id, dsi);
        }
        
    }
    @Override
    public int doAfterBody() throws JspException {
        try {
            String body = readBody(bodyContent);
            //p("got body ###" + bodyCSV + "###");
            DataSourceInfo ds;
            if ("sql".equals(type)) {
                ds = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
                if (jndiName != null) {
                    ds.setProperty(StandardProps.JDBC_JNDI_NAME, jndiName);
                } else {
                    checkJdbcSettings();
                    ds.setProperty(StandardProps.JDBC_DRIVER, driver);
                    ds.setProperty(StandardProps.JDBC_URL, url);
                    if (username != null) 
                        ds.setProperty(StandardProps.JDBC_USERNAME, username);
                    if (password != null)
                        ds.setProperty(StandardProps.JDBC_PASSWORD, password);
                }
                //ds.setProperty(DataSourceInfo., value)
                ds.setDataScript(body);
            } else {
                ds = parseCSVData(body);
            }
            storeDataSource(ds);
        } catch (IOException e) { throw new JspException(e); }
        parsedBody = true;
        return super.doAfterBody();
    }
    
    // get from config default iff not set on this tag
    // complain if driver & url not set
    private void checkJdbcSettings() throws JspException {
        ChartController cc = ChartController.get();
        if (driver == null) driver = cc.getDefaultJdbcDriver();
        if (url == null) url = cc.getDefaultJdbcUrl();
        if (username == null) username = cc.getDefaultJdbcUsername();
        if (password == null) password = cc.getDefaultJdbcPassword();
        if (driver == null) iia("no JDBC driver specified, on this tag or globally");
        if (url == null) iia("no JDBC URL specified, on this tag or globally");
    }
    private void iia(String msg) throws JspException {
        throw new //IllegalArgumentException(
            JspException(
                "for datasource id='" + id + "': " + msg
                );
    }
    private static void p(String s) {
        System.out.println("[DataTag] " + s);
    }
    
    public static String readBody(BodyContent bc) throws IOException {
        Reader rdr = bc.getReader();
        return readAsString(rdr);
    }
    
    static String readAsString(Reader rdr) throws IOException {
        char[] c = new char[1024];
        int r;
        StringBuilder sb = new StringBuilder();
        while ((r = rdr.read(c)) != -1) {
            sb.append(c, 0, r);
        }
        return sb.toString();
    }
}
