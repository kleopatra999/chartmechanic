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
package com.bayareasoftware.chartengine.model.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RendererDocSet implements Serializable {

    private List<RendererDocInfo> xy;
    private List<RendererDocInfo> category;

    public List<RendererDocInfo> getXY() {
        return xy;
    }
    
    public List<RendererDocInfo> getCategory() {
        return category;
    }
    
    public RendererDocInfo findXYByClassname(String cname) {
        RendererDocInfo ret = null;
        for (RendererDocInfo rdc : xy) {
            if (cname.equals(rdc.getClassname())) {
                ret = rdc;
                break;
            }
        }
        return ret;
    }
    public RendererDocInfo findCategoryByClassname(String cname) {
        RendererDocInfo ret = null;
        for (RendererDocInfo rdc : category) {
            if (cname.equals(rdc.getClassname())) {
                ret = rdc;
                break;
            }
        }
        return ret;
    }
    public void addXY(RendererDocInfo r) {
        if (xy == null) xy = new ArrayList();
        xy.add(r);
    }
    public void addCategory(RendererDocInfo r) {
        if (category == null) category = new ArrayList();
        category.add(r);
    }
    
    /* main method,generateDefault used to generate the primordial doc, not needed
     * going forward...and makes GWT compiler barf...
     * private static RendererDocSet generateDefault(ChartTypeSystem ct) {
        RendererDocSet ret = new RendererDocSet();
        List<ChartBeanInfo> renders;
        renders = ct.getConcreteXYRenderers();
        for (ChartBeanInfo cbi : renders) {
            ret.addXY(RendererDocInfo.createDefault(cbi, false));
        }
        renders = ct.getConcreteCategoryRenderers();
        for (ChartBeanInfo cbi : renders) {
            ret.addCategory(RendererDocInfo.createDefault(cbi, true));
        }
        return ret;
    }
    public static void main(String[] a) throws Exception {
        ChartTypeSystem ct = TypeInspector.load("/jfreechart-types.xml");
        RendererDocSet docs = generateDefault(ct);
        Writer w = new FileWriter("jfree-render-doc.xml");
        TypeInspector.getXStream().toXML(docs, w);
        w.flush();
        w.close();
    }*/
    /*package*/ static String renderShorthand(String name) {
        name = name.replace("XY", "");
        name = name.replace("Renderer", "");
        return name;
    }    
}
