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

import java.awt.GraphicsEnvironment;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;

import com.bayareasoftware.chartengine.model.BaseInfo;
import com.bayareasoftware.chartengine.model.types.ChartBeanInfo;
import com.bayareasoftware.chartengine.model.types.ChartTypeSystem;
import com.bayareasoftware.chartengine.model.types.PropInfo;
import com.bayareasoftware.chartengine.model.types.RendererDocInfo;
import com.bayareasoftware.chartengine.model.types.RendererDocSet;
import com.bayareasoftware.chartengine.model.types.RendererDocInfo.RendererColumnDocInfo;
import com.thoughtworks.xstream.XStream;

public class TypeInspector {

    private String file;
    private Map<String, Class> classes = new HashMap<String, Class>();
    private ChartTypeSystem ct;
    private static final Map<Class,Object> examples = new HashMap<Class,Object>() {
        @Override
        public Object put(Class c, Object o) {
            if (o == null) {
                throw new RuntimeException("null example for " + c.getName());
            }
            return super.put(c, o);
        }
    }
    
    ;
    static {
        examples.put(Plot.class, new XYPlot());
        examples.put(XYItemRenderer.class, new DefaultXYItemRenderer());
        examples.put(AbstractXYItemRenderer.class, new DefaultXYItemRenderer());
        examples.put(CategoryItemRenderer.class, new BarRenderer());
        examples.put(AbstractCategoryItemRenderer.class, new BarRenderer());
        examples.put(LogarithmicAxis.class, new LogarithmicAxis(""));
        JFreeChart chartExample = new JFreeChart(new XYPlot());
        examples.put(LegendTitle.class, chartExample.getLegend());
        examples.put(TextTitle.class, new TextTitle(""));
        examples.put(Marker.class, new ValueMarker(0));
    }
    public TypeInspector(String file) throws Exception {
        this.file = file;
    }
    
    public void run() throws Exception {
        ZipFile zf = new ZipFile(file);
        ZipEntry ze;
        try {
            Enumeration en = zf.entries();
            while (en.hasMoreElements()) {
                ze = (ZipEntry) en.nextElement();
                String cname = classname(ze.getName());
                if (cname != null && cname.indexOf(".servlet.") == -1) {
                    //p("got class: " + cname);
                    Class clz = getClass(cname);
                    classes.put(cname, clz);
                }
            }
        } finally {
            zf.close();
        }
        /* get the renderer base class into the xy and category type systems */
        ChartBeanInfo renderBase = null;
        {
            List<Class> tmp = new ArrayList<Class>();
            tmp.add(AbstractRenderer.class);
            renderBase = getBeanInfos(tmp).get(0);
        }
        p("loaded " + classes.size() + " classes");
        List<Class> plotClasses = getSubclasses(Plot.class);
        List<Class> xyRenderClasses = getSubclasses(XYItemRenderer.class);
        List<Class> catRenderClasses = getSubclasses(CategoryItemRenderer.class);
        List<Class> axisClasses = getSubclasses(Axis.class);
        
        List<ChartBeanInfo> plots = getBeanInfos(plotClasses);
        List<ChartBeanInfo> xyrender = getBeanInfos(xyRenderClasses);
        List<ChartBeanInfo> catrender = getBeanInfos(catRenderClasses);
        List<ChartBeanInfo> axes = getBeanInfos(axisClasses);
        
        xyrender.add(renderBase);
        catrender.add(renderBase);
        Map<String, ChartBeanInfo> absMap = getAbstract(plots, null);
        getAbstract(xyrender, absMap);
        getAbstract(catrender, absMap);
        getAbstract(axes, absMap);
        //p("abstractMap contains ####" + absMap + "####");
        p("abstract map has " + absMap.size() + " entries, "
                + this.abstractProps.size() + " abstract props");
        this.fillAbstractDefaults(absMap);
        p(plots.size() + " subclasses of Plot");
        p(xyrender.size() + " subclasses of XYItemRenderer");
        p(catrender.size() + " subclasses of CategoryItemRenderer");
        p(axes.size() + " subclasses of Axis");
        
        ct = new ChartTypeSystem();
        {
            JFreeChart chartExample = new JFreeChart(new XYPlot());
            ct.setChartBean(createMergedBean(JFreeChart.class, chartExample));
            ct.setTextTitleBean(createMergedBean(TextTitle.class, new TextTitle("")));
            ct.setLegendBean(createMergedBean(LegendTitle.class, chartExample.getLegend()));
        }
        //ct.setTextTitleBean(txtTitles.get(0));
        {
            ChartBeanInfo markerBean = getBeanInfo(Marker.class);
            Marker example = new ValueMarker(0);
            List<PropInfo> props = this.getProps(Marker.class, example);
            PropInfo[] pa = new PropInfo[props.size()];
            props.toArray(pa);
            markerBean.setProps(pa);
            ct.setMarkerBean(markerBean);
        }
        ct.setPlots(plots);
        for (ChartBeanInfo cbi : xyrender) {
            ct.addXyRenderer(cbi);
        }
        for (ChartBeanInfo cbi : catrender) {
            ct.addCatRenderer(cbi);
        }
        for (ChartBeanInfo cbi : axes) {
            ct.addAxis(cbi);
        }
        File out = new File("src/jfreechart-types.xml");
        Writer w = new FileWriter(out);
        try {
            XStream xs = getXStream();
            xs.toXML(ct, w);
            w.flush();
        } finally {
            w.close();
        }
        p("wrote type description out to file " + out.getAbsolutePath());
    }

    private Map<String, ChartBeanInfo> getAbstract(List<ChartBeanInfo> l,
            Map<String, ChartBeanInfo> map) {
        if (map == null) {
            map = new HashMap<String, ChartBeanInfo>();
        }
        for (ChartBeanInfo cbi : l) {
            if (cbi.isAbstract()) {
                map.put(cbi.getClassname(), cbi);
            }
        }
        return map;
    }
    
    private void fillAbstractDefaults(Map<String, ChartBeanInfo> map) {
        for (AbstractProp ap : this.abstractProps.values()) {
            ChartBeanInfo cbi = map.get(ap.declaringClass.getName());
            if (cbi != null) {
                PropInfo pi = cbi.findProp(ap.name);
                if (pi != null) {
                    if (pi.getDefault() == null) {
                        Object o = BeanUtil.getProp(ap.example, pi.getName());
                        if (o != null) {
                            String defalt = BeanUtil.type2string(ap.declaringClass, o);
                            if (defalt != null) {
                                //p("setting default of " + defalt + " for abs prop " + pi.getName());
                                pi.setDefault(defalt);
                            }
                        //} else if (pi.getType().equals("java.awt.Stroke")) {
                          //  p("no default stroke for " + cbi.getClassname() + "." + pi.getName());
                        }
                    } else {
                        //p("abstract CBI " + cbi.getClassname() +
                          //      " already has default for prop " + pi.getName());
                    }
                } else {
                    p("cannot find prop '" + ap.name + "' for CBI "
                            + cbi.getClassname());
                }
            } else {
                p("cannot find CBI for abstract class: " + ap.declaringClass.getName());
            }
        }
    }
    public static ChartTypeSystem load(String f) throws Exception {
        ChartTypeSystem cts = null;
        //InputStream is = new FileInputStream(f);
        XStream xs = getXStream();
        InputStream is = TypeInspector.class.getResourceAsStream(f);
        if (is == null) {
            throw new RuntimeException("FATAL: TypeInspector.load cannot find resource named: " + f);
        }
        try {
            cts = (ChartTypeSystem) xs.fromXML(is);
        } finally {
            is.close();
        }
        RendererDocSet renderDoc = null;
        is = TypeInspector.class.getResourceAsStream("/jfree-render-doc.xml");
        if (is == null) {
            throw new RuntimeException("FATAL: TypeInspector.load cannot find "
                     + "resource named: '/jfree-render-doc.xml'");
        }
        try {
            renderDoc = (RendererDocSet) xs.fromXML(is);
        } finally {
            is.close();
        }
        cts.mergeDoc(renderDoc);
        return cts;
    }
    
    public static XStream getXStream() {
        XStream xs = new XStream();
        xs.alias("prop", PropInfo.class);
        xs.alias("bean", ChartBeanInfo.class);
        xs.alias("chart-types", ChartTypeSystem.class);
        xs.aliasField("chart", ChartTypeSystem.class, "chartBean");
        //xs.aliasField("default", PropInfo.class, "defalt");
//        xs.useAttributeFor(BaseInfo.class, "name");
        xs.useAttributeFor(PropInfo.class, "name");
        xs.useAttributeFor(PropInfo.class, "type");
        xs.useAttributeFor(PropInfo.class, "defalt");
        //xs.useAttributeFor(PropInfo.class, "default");
        xs.useAttributeFor(ChartBeanInfo.class, "supername");
        xs.useAttributeFor(ChartBeanInfo.class, "classname");
        xs.useAttributeFor(ChartBeanInfo.class, "displayname");
        xs.useAttributeFor(ChartBeanInfo.class, "isAbstract");
        // renderer documentation
        xs.alias("renderers", RendererDocSet.class);
        xs.alias("render-doc", RendererDocInfo.class);
        xs.useAttributeFor(RendererDocInfo.class, "displayName");
        xs.useAttributeFor(RendererDocInfo.class, "excluded");
        xs.useAttributeFor(RendererDocInfo.class, "datasetType");
        // renderer columns
        xs.alias("column", RendererColumnDocInfo.class);
        xs.useAttributeFor(RendererColumnDocInfo.class, "type");
        return xs;
    }
    private ChartBeanInfo getBeanInfo(Class c) throws Exception {
        return getBeanInfo(c, createExample(c));
    }
    
    private ChartBeanInfo getBeanInfo(Class c, Object example) throws Exception {
        ChartBeanInfo cbi = new ChartBeanInfo();
        cbi.setClassname(c.getName());
        cbi.setDisplayname(this.getRendererShortName(c.getName()));
        cbi.setAbstract((c.getModifiers() & Modifier.ABSTRACT) != 0);
        Class sup = c.getSuperclass();
        if (sup != null && sup != Object.class) {
            cbi.setSupername(sup.getName());
        }
        //p("ChartBean " + cbi);
        List<PropInfo> props = getProps(c,example);
        for (PropInfo pi : props) {
            //p("   prop: " + pi);
            cbi.addProp(pi);
        }
        return cbi;
    }
    /*
     *  merge all the properties up through the class's hierarchy, all the way
     *  to java.lang.Object.  Useful when you only care about once instance
     *  e.g., LegendTitle
     */
    //
    private ChartBeanInfo createMergedBean(final Class c, Object example) throws Exception {

        //List<Class> classes = new ArrayList<Class>();
        ChartBeanInfo ret = getBeanInfo(c);
        Class c2 = c;
        List<PropInfo> allProps = new ArrayList<PropInfo>();
        while (c2 != Object.class) {
            p("adding " + c2.getName());
            List<PropInfo> pl = getProps(c2, example);
            for (PropInfo pi : pl) {
                if (!allProps.contains(pi)) {
                    allProps.add(pi);
                }
            }
            c2 = c2.getSuperclass();
            //classes.add(c2);
        }
        /*
        List<ChartBeanInfo> bis = getBeanInfos(classes);
        for (ChartBeanInfo cbi : bis) {
            PropInfo[] pis = cbi.getProps();
            for (PropInfo pi : pis) {
                if (!allProps.contains(pi)) {
                    allProps.add(pi);
                }
            }
        }*/
        PropInfo[] pis = new PropInfo[allProps.size()];
        allProps.toArray(pis);
        p("merged " + pis.length + " props for " + c.getName());
        ret.setProps(pis);
        return ret;
    }
    private List<ChartBeanInfo> getBeanInfos(List<Class> classes) throws Exception {
        List<ChartBeanInfo> ret = new ArrayList<ChartBeanInfo>();
        for (Class c : classes) {
            ChartBeanInfo cbi = getBeanInfo(c);
            if (cbi != null) {
                ret.add(cbi);
            }
        }
        return ret;
    }

    private Map<String, AbstractProp> abstractProps = new HashMap<String, AbstractProp>();

    private void recordAbstractProp(Object example, PropertyDescriptor pd) {
        Class decl = pd.getWriteMethod().getDeclaringClass();
        String key = decl.getName() + "." + pd.getName();
        if (abstractProps.get(key) == null) {
            abstractProps.put(key, new AbstractProp(decl, pd.getName(), example));
        }
    }
    private class AbstractProp {
        Class declaringClass;
        String name, defalt;
        Object example;
        public AbstractProp(Class decl, String name, Object example) {
            this.declaringClass = decl;
            this.example = example;
            this.name = name;
        }
    }
    
    private List<PropInfo> getProps(Class c) throws Exception {
        Object example = createExample(c);
        return getProps(c, example);
    }
    
    private List<PropInfo> getProps(Class c, Object example) throws Exception {
        List<PropInfo> ret = new ArrayList<PropInfo>();
        BeanInfo bi = Introspector.getBeanInfo(c);
        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        if (example == null) {
            p("got NO example object for " + c.getName());
        }
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null &&
                    pd.getPropertyType() != null) {
                // exclude properties inherited from superclass
                if (pd.getWriteMethod().getDeclaringClass() == c) {
                    PropInfo pi = new PropInfo(pd.getName(), pd.getPropertyType().getName());
                    if (example != null) {
                        Object defalt = BeanUtil.getProp(example, pd.getName());
                        if (defalt != null) {
                            String str = BeanUtil.type2string(c, defalt);
                            pi.setDefault(str);
                        }
                    }
                    ret.add(pi);
                } else if ((pd.getWriteMethod().getDeclaringClass().getModifiers() & Modifier.ABSTRACT) != 0 && example != null) {
                    //p("abstract prop '" + pd.getName() + " from " + pd.getWriteMethod().getDeclaringClass().getName()
                    //        + " (concrete: " + c.getName() + ")");
                    recordAbstractProp(example, pd);
                }
            }
        }
        return ret;
    }
    
    private Object createExample(Class c) throws Exception {
        Object ret = null;
        if ((c.getModifiers() & Modifier.ABSTRACT) == 0) {
            Constructor cons = null;
            try {
                cons = c.getConstructor();
            } catch (NoSuchMethodException ignore) {
                // Class.getConstructor(...) throws Exception
                // rather than returning null
            }
            if (cons != null && (cons.getModifiers() & Modifier.PUBLIC) != 0) {
                ret = cons.newInstance((Object[]) null);
            }
        }
        if (ret == null && c == JFreeChart.class) {
            // special case this one....
            ret = new JFreeChart(new XYPlot());
        } else if (ret == null && (c == Axis.class || c == ValueAxis.class)) {
            // this one too
            ret = new DateAxis();
        }
        if (ret == null) {
            ret = examples.get(c);
        }
        return ret;
    }
    private List<Class> getSubclasses(Class c) throws Exception {
        List<Class> ret = new ArrayList<Class>();
        for (String s : classes.keySet()) {
            Class clz = classes.get(s);
            if (c.isAssignableFrom(clz)) {
                ret.add(clz);
            }
        }
        return ret;
    }
    
    private Class getClass(String cname) throws Exception {
        //p("loading " + cname);
        return Class.forName(cname);
    }
    private static void listFonts() {
        String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String n : names) {
            System.out.println(n);
        }
    }
    public static void main(String[] a) throws Exception {
        if (a.length > 0 && "-listFonts".equals(a[0])) {
            listFonts();
            System.exit(0);
        }
        JFreeChartDriver.initializeCMDefaults();
        TypeInspector ji = new TypeInspector(a[0]);
        //Class c = Class.forName("org.jfree.chart.renderer.category.BarRenderer");
        //Object bean = c.newInstance();
        //ji.getProps(c);
        ji.run();
    }

    private String getRendererShortName(String classname) {
        String ret = null;
        int ind = classname.lastIndexOf('.');
        if (ind > 0) {
            classname = classname.substring(ind + 1);
        }
        int len = classname.length();
        ind = classname.indexOf("Renderer");
        ret = classname;
        if (ind > 0) {
            ret = classname;
            // int rlen = "Renderer".length();
            // String prefix = classname.substring(0, ind);
            // String suffix = "";
        }
        return ret;
    }
    
    private String classname(String s) {
        String ret = null;
        if (s.endsWith(".class")) {
            int len = s.length();
            ret = s.substring(0, len - 6);
            ret = ret.replace('/', '.');
        }
        return ret;
    }

    private static void p(String s) {
        System.err.println("[TypeInspector] " + s); 
    }
}
