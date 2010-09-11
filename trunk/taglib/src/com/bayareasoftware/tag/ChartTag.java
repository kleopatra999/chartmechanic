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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.bayareasoftware.chartengine.chart.ChartDiskResult;
import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.PlotType;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.model.TimeUtil;
import com.bayareasoftware.chartengine.util.FileUtil;

public class ChartTag extends TagSupport implements ITagDoc, DynamicAttributes {

    private static final Map<String,Boolean> VALID_IMG_ATTS = new HashMap();
    
    private ChartBundle cb;
    private ChartInfo ci;
    private PageContext pc;
    private boolean plotSet = false;
    private String template;
    private long tagStart;
    private int ttl = -1;
    private boolean ttlSet = false;
    private Map<String,String> imgAtts;
    
    public ChartTag() {
        release();
    }
    
    @Override
    public void release() {
        super.release();
        pc = null;
        template = null;
        plotSet = false;
        cb = new ChartBundle();
        ci = new ChartInfo();
        // default dimensions of 400px if undeclared otherwise
        //ci.setWidth(400);
        //ci.setHeight(400);
        // don't let chart ID be random, prevents cache hits
        ci.setId("web-cache");
        cb.setChartInfo(ci);
        ttl = -1;
        ttlSet = false;
        imgAtts = new HashMap();
    }
    
    public void addDataSource(DataSourceInfo dsi) {
        cb.addDataSource(dsi);
    }

    public void addSeriesDescriptor(SeriesDescriptor sd) {
        // make sure we have the referenced data source
        // note that this might be a bit too restrictive:
        // The following will complain (needlessly) when
        // a <data> tag is declared after a <series> tag
        // referencing it.  But I don't know a good alternative...
        String dsid = sd.getSource();
        //p("adding series(\"" + sd.getName() + "\")");
        if (sd.getFunc() == null && cb.getDataSourceByID(dsid) == null) {
            // look for it on the page, external to this chart tag
            DataSourceInfo dsi = PageObjects.get(pc).getDataSource(dsid);
            if (dsi == null) {
                throw new RuntimeException(
                        "cannot find data source id='" + dsid
                        + "' referenced by series '" + sd.getName() + "'");
            }
            cb.addDataSource(dsi);
        }
        ci.addSeriesDescriptor(sd);
    }

    public SeriesDescriptor findSeries(String sname) {
        SeriesDescriptor ret = null;
        List<SeriesDescriptor> l = ci.getSeriesDescriptors();
        for (SeriesDescriptor sd : l)
            if (sname.equals(sd.getName())) {
                ret = sd;
                break;
            }
        return ret;
    }
    public void addMarkerDescriptor(MarkerDescriptor md) {
        // make sure we have the referenced data source, if any
        String dsid = md.getSource();
        if (dsid != null && cb.getDataSourceByID(dsid) == null) {
            // look for it on the page, external to this chart tag
            DataSourceInfo dsi = PageObjects.get(pc).getDataSource(dsid);
            if (dsi == null) {
                throw new RuntimeException(
                        "cannot find data source id='" + dsid
                        + "' referenced by marker '" + md.getName() + "'");
            }
            cb.addDataSource(dsi);
        }
        ci.addMarkerDescriptor(md);
    }

    // be careful not to let a template override something
    // set explicitly on the tag - set here iff not already set!
    private boolean setKnownProp(String name, String val) {
        val = val.trim();
        boolean ret = false;
        if (ChartInfo.PROP_TIMEPERIOD.equalsIgnoreCase(name)) {
            int period = TimeUtil.decodeTimeString(val);
            ci.setTimePeriod(period);
            ret = true;
        } else if (ChartInfo.PROP_HEIGHT.equalsIgnoreCase(name)) {
            ret = true;
            if (ci.getHeight() <= 0)
                ci.setHeight(decodeInt(val, ChartInfo.PROP_HEIGHT));
        } else if (ChartInfo.PROP_WIDTH.equalsIgnoreCase(name)) {
            ret = true;
            if (ci.getWidth() <= 0)
                ci.setWidth(decodeInt(val, ChartInfo.PROP_WIDTH));
        } else if (ChartInfo.PROP_RENDERTYPE.equalsIgnoreCase(name)) {
            ret = true;
            if (ci.getRenderType() == null) ci.setRenderType(val);
        } else if (ChartInfo.PROP_PLOTTYPE.equalsIgnoreCase(name)) {
            ret = true;
            if (!plotSet) this.setPlotType(val);
        }
        
        return ret;
    }

 // ITagDoc
    public List<AttInfo> getAttributes() {
        return DataTag.makeAttsList(ATTS);
    }
    
    private static int decodeInt(String val, String name) {
        try {
            int ret = Integer.parseInt(val);
            return ret;
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(
                    "bad integer value for " + name + ": '" + val + "'"
                    );
        }
    }
    // expected to be fully-qualified prop names,
    public void setProps(SimpleProps sp) {
        for (String key : sp.keySet()) {
            String val = sp.get(key);
            if (!setKnownProp(key, val))
                ci.setProperty(key, val);
        }
    }
    
    public void setTemplate(String tname) {
        this.template = tname;
    }
    
    public void setTitle(String tname) {
        ci.setProperty("title.text", tname);
    }    

    public void setWidth(int w) {
        ci.setWidth(w);
    }
    
    public void setHeight(int h) {
        ci.setHeight(h);
    }
    
    public void setRenderType(String rt) {
        // FIXME: validate
        ci.setRenderType(rt);
    }

    public void setGraphType(String gt) {
        setRenderType(gt);
    }
    
    public void setTtl(int ttl) {
        this.ttl = ttl;
        ttlSet = true;
    }
    @Override
    public int doStartTag() throws JspException {
        tagStart = System.currentTimeMillis();
        // apply template here, early, so it can be overridden later
        SimpleProps sp;
        if (template != null) {
                        sp = PageObjects.get(pc).getTemplate(template);
            if (sp == null) {
                throw new IllegalArgumentException(
                        "template '" + template + "' not found"
                        );
            }
            setProps(sp);
        } else if ((sp = PageObjects.get(pc).getTemplate("default")) != null) {
            setProps(sp);
        } else {
            //p("doStartTag(), no template...");
        }
        
        return Tag.EVAL_BODY_INCLUDE;
    }
    @Override
    public void setPageContext(PageContext pc) {
        this.pc = pc;
        super.setPageContext(pc);
    }
    
    @Override
    public int doAfterBody() throws JspException {
        try {
            if (!plotSet)
                inferPlotType();
            fixupMarkers();
            makeTag();
        } catch (RuntimeException re) {
            throw re;
        } catch (JspException je) {
            throw je;
        } catch (Exception e) { throw new JspException(e); }
        long duration = System.currentTimeMillis() - tagStart;
        //p("duration=" + duration + " ms");
        return super.doAfterBody();
    }
    
    // MarkerTag/MarkerDescriptor currently infers a lot w.r.t. the
    // type (interval vs single, time vs numeric)  we're trying to
    // infer the right type here - basically, if it has a data source,
    // assume interval...may not always be right though...
    private void fixupMarkers() {
        PlotType pt = ci.getPlotType();
        for (MarkerDescriptor md : ci.getMarkers()) {
            if (md.getSource() == null) {
                // assume these are ok
                continue;
            }
            if (pt == PlotType.PLOT_TIME && !md.isRange()) {
                // date interval
                md.setType(MarkerDescriptor.MARKER_TYPE_DATE_INTERVAL);
            } else {
                md.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC_INTERVAL);
            }
        }
    }
    public void setPlotType(String str) {
        PlotType pt = PlotType.get(str);
        if (pt == null)
            throw new IllegalArgumentException("invalid plot type '" + str + "'");
        ci.setPlotType(pt);
        plotSet = true;
    }
    
    private void inferPlotType() {
        // look at first series X column type
        if (ci.getSeriesCount() > 0) {
            SeriesDescriptor sd = ci.getSeriesDescriptor(0);
            DataSourceInfo ds = cb.getDataSourceByID(sd.getSource());
            if (ds != null) {
                int xcol = sd.getXColumn();
                Metadata md = ds.getInputMetadata();
                PlotType pt = PlotType.PLOT_CATEGORY;
                if (xcol > 0 && xcol <= md.getColumnCount()) {
                    int xType = md.getColumnType(xcol);
                    if (xType == DataType.DATE)
                        pt = PlotType.PLOT_TIME;
                    else if (DataType.isNumeric(xType))
                        pt = PlotType.PLOT_XY;
                }
                ci.setPlotType(pt);
            }
        } else {
            throw new RuntimeException("no series defined for chart");
        }
    }
    
    private void validateColumns() {
        // for time plot, X column must all be DATE
        // for xy plot, X column must all be NUMBER
        // for any plot, y column must be NUMBER
    }

    private boolean isTtlExpired(ChartDiskResult res) {
        boolean ret = false;
        // note: shadowing member var
        ChartController cc = ChartController.get();
        int ttl = ttlSet ? this.ttl : cc.getDefaultTtl();
        if (ttl == 0) {
            // never use cache
            ret = true;
        } else if (ttl < 0) {
            // always use cache if populated
            ret = false;
        } else {
            File f = new File(res.getImagePath());
            if (f.isFile()) {
                long expiry = 1000 * ttl + f.lastModified();
                ret = System.currentTimeMillis() > expiry;
            } else {
                //p("WARN: " + f.getAbsolutePath() + " not found");
            }
        }
        return ret;
    }
    
    private void makeTag() throws Exception {
        JspWriter out = pageContext.getOut();
        List<String> errs = cb.validate();
        // FIXME: validate that all series have correct
        // data types for X,Y columns
        if (errs != null && errs.size() > 0) {
            // FIXME: remove 'orphaned' data sources that have
            // no series attached to them
            out.println("<font color='#ff0000'>");
            out.println("<b>Chart Errors</b><ol>");
            for (String err : errs) out.println("<li>" + err + "</li>");
            out.println("</ol></font>");
            return;
        }
        ChartController cc = ChartController.get();
        ChartDiskResult cdr;
        cdr = cc.getChart(cb, null);
        //String title = ci.getProperty("title.text");

        boolean haveLinks = ci.haveImageMapLinks();
        if (cdr == null || isTtlExpired(cdr)) {
            cdr = cc.prepChartResult(cb);
            cdr.setGenerateThumbnail(false);
            cdr.setGenerateImageMap(haveLinks);
            cdr = cc.createChart(cb, null, cdr);
            cc.putChart(cdr, cb, null);
        } else {
            //p("cache hit '" + title + "'");
        }
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String uri = StringUtil.joinPaths(
                req.getContextPath(), cc.getChartURI(cdr)
                );
        out.print("<img src=\"" + uri + "\" width=\""+ ci.getWidth() + "\""
                + " height=\"" + ci.getHeight() + "\"");
        // pass through html img attributes
        for (String att : imgAtts.keySet())
            out.print(" " + att + "=\"" + imgAtts.get(att) + "\"");
        if (haveLinks)
            out.print(" usemap=\"#" + cdr.getImageMapId() + "\"");
        out.println("/>");
        if (haveLinks) {
            File f = new File(cdr.getImageMapPath());
            if (f.exists())
                FileUtil.writeFile(f, out);
            else
                throw new RuntimeException("expected imagemap file "
                        + f.getAbsolutePath() + " not found");
        }
        /* NOTE NOTE NOTE:
         * Container is too aggressive about re-using tags, we must
         * release/reset ourselves after end tag
         */
        this.release();
    }
    
    static String validPlots() {
        //AttInfo ai = null;
        //System.out.println((ai.required ? "yes" : "no") + " other");
        StringBuilder sb = new StringBuilder();
        for (PlotType pt : PlotType.values())
            sb.append(' ').append(pt.name());
        return sb.toString();
    }
    static final String[] IMG_ATTS = {
        "alt", "align", "border", "hspace", "longdesc", "vspace", "class", "dir", "id",
        "lang", "style", "onabort", "onclick", "ondblclick", "onmousedown",
        "onmousemove", "onmouseout", "onmouseover", "onmouseup", "onkeydown", "onkeypress",
        "onkeyup"
      };
    
    private static Object[][] ATTS = {
        { "width", "The width of the chart, in pixels", false },
        { "height", "The height of the chart, in pixels", false },
        { "plotType", "Specifies which kind of plot to use for" +
        		" the chart.  Note that only certain data types" +
        		" for the X and Y axes are valid for a given type" +
        		" of plot; see <a href=\"charts.jsp#plot-types\">the " +
        		" charts document</a> for compatibility.<br/>" +
        		"Valid values are<code>"
        		+ validPlots() + "</code>.<br/>If <code>plotType</code>" +
                        " is not set, the chart tag will try to infer your" +
                        " desired plot type, based on the <a href=\"data.jsp#data-types\">" +
                        "data type</a> of the X columns of your series.", false },
        { "graphType", "Sets the default graph type (renderer) to be used" +
        		" for series of this chart (e.g., lines, bars, dots, etc)." +
        		" Individual <code>&lt;series&gt;</code> tags may override" +
        		" the default graphType.  See <a href=\"charts.jsp#graph-types\">" +
        		"graph types</a> section for a list of valid graph types.",
        		false, "renderType" },
        { "ttl", "Specifies a time-to-live, in seconds, for the cache entry of " +
        		"this chart.  Charts with cache entries younger than the TTL " +
        		"will not be re-drawn.  A ttl of <code>0</code> will never " +
        		"use the cache; a ttl of <code>1</code> will always use the " +
        		"cache.  The default is <code>" + ChartController.DEFAULT_TTL + 
        		"</code>.", false },
        { "title", "Sets the title text of the chart.  Shorthand for setting" +
        		" chart property <code>title.text</code>", false },
        { "template", "Provides a bag of pre-set chart properties (a " +
        		"<a href=\"charts.jsp#templates\">template</a>" +
        		").  If no template is set for a chart, then a template" +
        		" called <code>default</code> will be used, if one" +
        		"is defined.", false },
        { "&lt;standard HTML img tag attributes&gt;", "All of the standard attributes" +
        		" of the HTML <code>img</code> tag are passed through as-is" +
        		" from the <code>chart</code> tag to the generated <code>img</code>" +
        		" tag.  The supported attributes are: <code>" + getImgAtts()
        		+ "</code>", false},
    };
    private static void p(String s) {
        System.out.println("[ChartTag] " + s);
    }

    private static String getImgAtts() {
        StringBuilder sb = new StringBuilder();
        for (String a : IMG_ATTS) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(a);
        }
        return sb.toString();
    }
    
    /* DynamicAttributes */
    public void setDynamicAttribute(String uri, String name, Object obj)
    throws JspException {
        String val = obj == null ? "" : obj.toString();
        if (VALID_IMG_ATTS.get(name.toLowerCase()) == null) {
            throw new JspException(
                    "'" + name + "' is not a valid attribute of the HTML <img> tag"
                    );
        }
        imgAtts.put(name, val);
    }
    static {
        for (String att : IMG_ATTS)
            VALID_IMG_ATTS.put(att, Boolean.TRUE);
    }
}
