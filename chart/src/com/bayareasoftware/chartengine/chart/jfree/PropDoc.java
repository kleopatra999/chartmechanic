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

import java.io.PrintStream;

import com.bayareasoftware.chartengine.model.types.ChartBeanInfo;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;
import com.bayareasoftware.chartengine.model.types.PropInfo;

public class PropDoc {

    private ChartTypeSystem ts;
    private PrintStream out;
    public PropDoc(String file, PrintStream out) throws Exception {
        ts = TypeInspector.load(file);
        this.out = System.out;
    }
    public void run() throws Exception {
        ChartBeanInfo cbi = ts.getChartBean();
        out.println("<table>\n<tr><th>Name</th><th>Type</th><th>Default</th></tr>");
        printBean(cbi, "chart");
        cbi = ts.getPlotInfo("org.jfree.chart.plot.XYPlot");
        printBean(cbi, "plot");
        cbi = ts.getXYRendererInfo("Line And Shape");
        printBean(cbi, "renderer");
        cbi = ts.getAxis("DateAxis");
        printBean(cbi, "domain-axis");
        cbi = ts.getAxis("NumberAxis");
        printBean(cbi, "range-axis-X");
        cbi = ts.getTextTitleBean();
        printBean(cbi, "title");
        out.println("</table>");
    }
    public static void main(String[] args) throws Exception {
        new PropDoc("/jfreechart-types.xml", System.out).run();
    }

    private void printBean(ChartBeanInfo cbi, String typeName) {
        out.println("<tr><td colspan=\"3\"><b>" + typeName + "</b></td></tr>");
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
    }
    private static String trunc(String s) {
        String ret = s;
        int ind = s.lastIndexOf('.');
        if (ind >= 0) {
            ret = s.substring(ind + 1);
        }
        return ret;
    }
}
