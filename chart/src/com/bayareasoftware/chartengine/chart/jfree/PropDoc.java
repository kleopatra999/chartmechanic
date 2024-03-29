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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bayareasoftware.chartengine.model.types.ChartBeanInfo;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;
import com.bayareasoftware.chartengine.model.types.PropInfo;

public class PropDoc {

    private ChartTypeSystem ts;
    private PrintStream out;
    private boolean isWiki = false;
    // javadoc for bean setters on the jfreechart classes
    // see ../taglib/.../PropsDoclet.java
    private Properties javadocProps;
    public PropDoc(String file, PrintStream out) throws Exception {
        ts = TypeInspector.load(file);
        this.out = out;
        javadocProps = new Properties();
        InputStream is = getClass().getResourceAsStream("/chart-props-doc.properties");
        try {
            javadocProps.load(is);
        } finally {
            if (is != null) is.close();
        }
    }
    
    private void printTOC() {
        
        if (isWiki) {
            out.println("<wiki:toc max_depth=\"2\"/>");
            return;
        }
        List<ChartBeanInfo> l;
        ChartBeanInfo cbi;
        out.println("<ul>"); // master list
        printLi("chart", "chart");
        printLi("title", "title");
        printLi("legend", "legend");
        printLi("marker", "marker");

        printLi("plot", "Plot Types");
        out.println("<ul>");
        l = ts.getPlots();
        for (ChartBeanInfo c : l)
            if (supportedPlots.get(c.getDisplayname()) != null)
                printLi(c.getClassname(), c.getDisplayname());
        out.println("</ul>");

        printLi("axis", "Axis Types");
        out.println("<ul>");
        for (String a : AXIS_TYPES.keySet()) {
            printLi(a, a);
        }
        out.println("</ul>");
        
        printLi("xyrenderers", "XY/timeseries Graph Types");
        out.println("<ul>");
        l = ts.getConcreteXYRenderers();
        for (ChartBeanInfo c : l) {
            printLi(c.getClassname(), c.getDisplayname());
        }
        out.println("</ul>");

        printLi("categoryrenderers", "Category Graph Types");
        out.println("<ul>");
        l = ts.getConcreteCategoryRenderers();
        for (ChartBeanInfo c : l) {
            printLi(c.getClassname(), c.getDisplayname());
        }
        out.println("</ul>");
        out.println("</ul>"); // master TOC list
        
        
    }
    public void run() throws Exception {
        printTOC();
        printChartTypes();
        printXYRenderers();
        printCategoryRenderers();
    }
    
    private void printChartTypes() throws Exception {
        List<ChartBeanInfo> l;
        ChartBeanInfo cbi;
        cbi = ts.getChartBean();
        printMajorAnchor("chart", "chart");
        printBean(cbi);  // chart

        printMajorAnchor("title", "title");
        cbi = ts.getTextTitleBean();
        printBean(cbi);

        cbi = ts.getLegendBean();
        printMajorAnchor("legend", "legend");
        printBean(cbi);

        cbi = ts.getMarkerBean();
        printMajorAnchor("marker", "marker");
        printBean(cbi);

        printMajorAnchor("plot", "plot");
        l = ts.getPlots();
        for (ChartBeanInfo c : l) {
            if (supportedPlots.get(c.getDisplayname()) != null) {
                c = ts.getPlotInfo(c.getClassname()); // this gets superclass props as well
                printMinorAnchor(c.getClassname(), c.getDisplayname());
                printBean(c);
            }
        }

        printMajorAnchor("axis", "Axis Types");
        for (String a : AXIS_TYPES.keySet()) {
            String displayName = AXIS_TYPES.get(a);
            cbi = ts.getAxis(displayName);
            printMinorAnchor(a, displayName);
            printBean(cbi);
        }

    }
    
    private void printXYRenderers() throws Exception {
        List<ChartBeanInfo> l;
        
        printMajorAnchor("xyrenderers", "XY Graph Types");
        l = ts.getConcreteXYRenderers();
        for (ChartBeanInfo c : l) {
            c = ts.getXYRendererInfo(c.getDisplayname());
            printMinorAnchor(c.getClassname(), c.getDisplayname());
            printBean(c);
        }
    }
    
    private void printCategoryRenderers() throws Exception {
        List<ChartBeanInfo> l;
    
        printMajorAnchor("categoryrenderers", "Category Graph Types");
        l = ts.getConcreteCategoryRenderers();
        for (ChartBeanInfo c : l) {
            c = ts.getCategoryRendererInfo(c.getDisplayname());
            printMinorAnchor(c.getClassname(), c.getDisplayname());
            printBean(c);
        }
        
    }
    public static void main(String[] args) throws Exception {
        PrintStream out;
        if (args.length != 4) {
            System.err.println("usage: PropDoc <html-file> <chart-wiki-file> " +
            		"<xy-renderer-wiki-file> <cat-renderer-wiki-file>");
            System.exit(1);
        }
        
        out = new PrintStream(new FileOutputStream(args[0]));
        PropDoc pd = new PropDoc("/jfreechart-types.xml", out);
        pd.isWiki = false;
        pd.run();
        out.close();

        out = new PrintStream(new FileOutputStream(args[1]));
        pd = new PropDoc("/jfreechart-types.xml", out);
        pd.isWiki = true;
        out.println("#summary Reference for chart, plot, axis, title and legend properties");
        pd.printTOC();
        pd.printChartTypes();
        out.close();

        out = new PrintStream(new FileOutputStream(args[2]));
        pd = new PropDoc("/jfreechart-types.xml", out);
        pd.isWiki = true;
        out.println("#summary Reference for  XY graph-type (renderer) properties");
        pd.printTOC();
        pd.printXYRenderers();
        out.close();

        out = new PrintStream(new FileOutputStream(args[3]));
        pd = new PropDoc("/jfreechart-types.xml", out);
        pd.isWiki = true;
        out.println("#summary Reference for Category graph-type (renderer) properties");
        pd.printTOC();
        pd.printCategoryRenderers();
        out.close();
        
    }
    
    private void printLi(String anchor, String text) {
        out.println("<li><a href=\"#" + anchor + "\">" + text + "</a></li>");
    }
    private void printMajorAnchor(String anchor, String text) {
        if (isWiki) out.println("= " + text + " =");
        else out.println("<a name=\"" + anchor + "\"></a><h1>" + text + "</h1>");
    }
    
    private boolean isWikiWord(String s) {
        if (!Character.isUpperCase(s.charAt(0))) {
            return false;
        }
        if (Character.isUpperCase(s.charAt(1))) {
            return false;
        }
        if (Character.isUpperCase(s.charAt(s.length() - 1))) {
            return false;
        }
        boolean ret = false;
        // true if we see at least one more upper
        // false if we see a ' '
        int len = s.length();
        for (int i = 2; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) ret = true;
            else if (Character.isWhitespace(c)) {
                ret = false;
                break;
            }
        }
        return ret;
    }
    private void printMinorAnchor(String anchor, String text) {
        String prefix = "";
        if (isWikiWord(text)) {
            prefix = "!";
        }
        if (isWiki) out.println("== " + prefix + text + " ==");
        else out.println("<a name=\"" + anchor + "\"></a><h2>" + text + "</h2>"
                + "\n &nbsp;&nbsp;&nbsp;<a href=\"\">top</a>"
                );
    }

    private void printClassComment(String cname) {
        String comment = javadocProps.getProperty(cname + ".class");
        if (comment == null) comment = "";
        if (isWiki) {
            comment = comment.replace("../../../../../images/",
            "http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/");
            comment = comment.replace("../../../../images/",
            "http://chartmechanic.googlecode.com/svn/trunk/taglib/web/images/");
            comment = comment.replace("<P>", "<br/><br/>");
            comment = comment.replace("<p>", "<br/><br/>");
            comment = comment.replace("<br>", "<br/>");
            comment = comment.replace("<BR>", "<br/>");
            comment = comment.replace("</p>", "");
            comment = comment.replace("</P>", "");
        }
        out.println("<p>" + comment + "</p>");
    }
    
    private String javadocLink(ChartBeanInfo cbi) {
        String cname = cbi.getClassname();
        String path = cname.replace('.', '/') + ".html";
        return "<a href=\"http://www.jfree.org/jfreechart/api/javadoc/" + path
        + "\">" + cname + "</a>";
    }
    
    private String javadocDoc(String cname, String prop) {
        String ret = javadocProps.getProperty(cname + "." + prop);
        if (ret == null) {
            try {
                Class c = Class.forName(cname);
                Class sup = c.getSuperclass();
                if (!"java.lang.Object".equals(sup.getName())) {
                    ret = javadocDoc(sup.getName(), prop);
                }
            } catch (ClassNotFoundException cnfe) {
                throw new RuntimeException(cnfe);
            }
        }
        return ret;
        
    }
    private void printBean(ChartBeanInfo cbi) {
        printClassComment(cbi.getClassname());
        out.println("<table class=\"doc-table\">\n<tr><th>Name</th><th>Type</th><th>Description</th><th>Default</th></tr>");
        out.println("<tr><td colspan=\"4\" align=\"center\"><b>" + javadocLink(cbi) + "</b></td></tr>");
        for (PropInfo pi : cbi.getProps()) {
            String def = pi.getDefault();
            String type = pi.getType();
            if (!BeanUtil.supportsType(type)) {
                continue;
            }
            if (def == null) {
                def = "&nbsp;";
            } else {
                def = def.replaceAll("&", "&amp;");
                def = def.replaceAll("<", "&lt;");
                def = def.replaceAll(">", "&gt;");
            }
            String javadoc = javadocDoc(cbi.getClassname(), pi.getName());
            if (javadoc == null) javadoc = "&nbsp;";
            javadoc = javadoc.replace("<P>", "<br/><br/>");
            javadoc = javadoc.replace("<p>", "<br/><br/>");
            out.println("<tr><td>" + pi.getName() + "</td><td>" + trunc(type)
                    + "</td><td>" + javadoc
                    + "</td><td><code>" + def + "</code></td></tr>");
        }
        out.println("</table>");
    }
    private static String trunc(String s) {
        String ret = s;
        int ind = s.lastIndexOf('.');
        if (ind >= 0) {
            ret = s.substring(ind + 1);
        }
        return ret;
    }
    
    private static final Map<String,String> AXIS_TYPES = new HashMap();
    static {
        AXIS_TYPES.put("number", "NumberAxis");
        AXIS_TYPES.put("log", "LogarithmicAxis");
        AXIS_TYPES.put("date", "DateAxis");
        AXIS_TYPES.put("period", "PeriodAxis");
    };
    private static Map supportedPlots = new HashMap();
    static {
        supportedPlots.put("PiePlot", true);
        supportedPlots.put("PiePlot3D", true);
        supportedPlots.put("XYPlot", true);
        supportedPlots.put("CategoryPlot", true);
    }
}
