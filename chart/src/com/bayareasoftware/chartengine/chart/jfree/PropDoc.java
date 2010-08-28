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
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayareasoftware.chartengine.model.types.ChartBeanInfo;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;
import com.bayareasoftware.chartengine.model.types.PropInfo;

public class PropDoc {

    private ChartTypeSystem ts;
    private PrintStream out;
    private boolean isWiki = false;
    
    public PropDoc(String file, PrintStream out) throws Exception {
        ts = TypeInspector.load(file);
        this.out = out;
    }
    public void run() throws Exception {
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

        
        out.println("</ul>"); // master list
        
        cbi = ts.getChartBean();
        printMajorAnchor("chart", "chart");
        printBean(cbi);  // chart
        /*
        cbi = ts.getPlotInfo("org.jfree.chart.plot.XYPlot");
        printBean(cbi);
        cbi = ts.getXYRendererInfo("Line And Shape");
        printBean(cbi);
        cbi = ts.getAxis("DateAxis");
        printBean(cbi);
        cbi = ts.getAxis("NumberAxis");
        printBean(cbi);
        */
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

        printMajorAnchor("xyrenderers", "XY Graph Types");
        l = ts.getConcreteXYRenderers();
        for (ChartBeanInfo c : l) {
            c = ts.getXYRendererInfo(c.getDisplayname());
            printMinorAnchor(c.getClassname(), c.getDisplayname());
            printBean(c);
        }

        printMajorAnchor("categoryrenderers", "Category Graph Types");
        l = ts.getConcreteCategoryRenderers();
        for (ChartBeanInfo c : l) {
            c = ts.getCategoryRendererInfo(c.getDisplayname());
            printMinorAnchor(c.getClassname(), c.getDisplayname());
            printBean(c);
        }
        
    }
    public static void main(String[] args) throws Exception {
        PrintStream out = System.out;
        if (args.length > 0)
            out = new PrintStream(new FileOutputStream(args[0]));
        new PropDoc("/jfreechart-types.xml", out).run();
    }

    private void printLi(String anchor, String text) {
        out.println("<li><a href=\"#" + anchor + "\">" + text + "</a></li>");
    }
    private void printMajorAnchor(String anchor, String text) {
        if (isWiki) out.println("= " + text + " =");
        else out.println("<a name=\"" + anchor + "\"></a><h1>" + text + "</h1>");
    }
    
    private void printMinorAnchor(String anchor, String text) {
        if (isWiki) out.println("== " + text + " ==");
        else out.println("<a name=\"" + anchor + "\"></a><h2>" + text + "</h2>"
                + "\n &nbsp;&nbsp;&nbsp;<a href=\"\">top</a>"
                );
    }

    private String javadocLink(ChartBeanInfo cbi) {
        String cname = cbi.getClassname();
        String path = cname.replace('.', '/') + ".html";
        return "<a href=\"http://www.jfree.org/jfreechart/api/javadoc/" + path
        + "\">" + cname + "</a>";
    }
    private void printBean(ChartBeanInfo cbi) {
        out.println("<table>\n<tr><th>Name</th><th>Type</th><th>Default</th></tr>");
        out.println("<tr><td colspan=\"3\"><b>" + javadocLink(cbi) + "</b></td></tr>");
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
            out.println("<tr><td>" + pi.getName() + "</td><td>" + trunc(type)
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
