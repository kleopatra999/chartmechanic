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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.UnitType;

import com.bayareasoftware.chartengine.chart.ShapeUtil;
import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.util.DateUtil;
import com.bayareasoftware.chartengine.util.URLUtil;


public class BeanUtil {

    private static final Log log = LogFactory.getLog(BeanUtil.class);
    private BeanUtil() { }

    /**
     * call setters on the bean given a map of properties
     * optionally, supply a prefix so that only keys that start with prefix are used
     * 
     * note that keys that start with ChartConstants.CM_PROP_PREFIX are always ignored
     *
     * props is not modified in the course of this method
     * 
     * @param bean
     * @param props     if null, does nothing.
     * @param prefix    if non-null, only use properties with keys that start with prefix and use the suffix part of that key
     *                  if null, use all keys
     */
    public static void setProps(Object bean, Map<String,String> props, String prefix) {
        if (props == null)
            return;
        
        PropertyDescriptor[] pds = getPDs(bean.getClass());
        
        Iterator<String> iter = props.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();

            if (prefix == null || key.startsWith(prefix)) {
                String v = props.get(key);
                
                if (v == null)
                    continue;

                //p("looking at '" + k + "'='" + v + "' ("
                //  + hex(k) + "=" + hex(v));
                String k = key;
                if (prefix != null) {
                    k = k.substring(prefix.length());
                }
                if (k.startsWith(ChartConstants.CM_PROP_PREFIX)) {
                    // not a real property of this bean, but an internally used property
                    continue;
                }

                PropertyDescriptor pd = findPD(pds, k);
                if (pd == null) {
                    if (log.isDebugEnabled()) {
                        log.debug("cannot find property '" + k + "' for " + bean.getClass().getName());
//                        (new Exception()).printStackTrace();
                    }
                } else {
                    try {
                        Object converted = convertType(v, pd.getPropertyType());
                        setProp(bean, pd, converted);
                    } catch (Exception cannotSet) {
                        // FIXME: preserve and report error
                        p("cannot convert '" + v + "' to " + pd.getPropertyType().getName()
                                + " for property '" + pd.getName() + " of bean "
                                + bean.getClass().getName() + ": " + cannotSet);
                    }
                }
            }
        }
    }

    
    public static String hex(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            String digit = Integer.toHexString(c);
            if (digit.length() == 1) {
                digit = "0" + digit;
            }
            sb.append(digit);
        }
        return sb.toString();
    }
    public static Object getProp(Object bean, String pname) {
        Object o = getProp(bean, getPD(bean, pname));
        return o;
    }
    
    public static void setProp(Object bean, String pname, Object value) {
        setProp(bean, getPD(bean, pname), value);
    }
    private static Object getProp(Object bean, PropertyDescriptor pd) {
        Method reader = pd.getReadMethod();
        return reader != null ? invoke(bean, reader) : null;
    }
    private static void setProp(Object bean, PropertyDescriptor pd, Object value) {
        Method writer = pd.getWriteMethod();
        if (writer == null) {
            log.warn("No setter for " + bean.getClass().getName() + "." + pd.getName());
            return;
        }
        //p("setting " + pd.getName() +"(type=" + pd.getPropertyType().getName() + ")");
        invoke(bean, writer, value);
    }
    
    private static final Object[] NO_ARGS = new Object[0];
    
    private static Object invoke(Object bean, Method m) {
        return invoke(bean, m, NO_ARGS);
    }
    
    private static Object invoke(Object bean, Method m, Object arg) {
        Object[] args;
        if (arg != null && arg instanceof Object[]) {
            args = (Object[]) arg;
        } else {
            args = new Object[1];
            args[0] = arg;
        }
        try {
            return m.invoke(bean, args);
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(
                    "cannot invoke " + m.getName() + ":",
                    iae
                    );
        } catch (InvocationTargetException ite) {
            handleITE(m, ite);
        }
        // should not reach
        return null;
    }
    
//    private static void handleIPE(Class c, IntrospectionException ite) {
//        Throwable t = ite.getCause();
//        if (t instanceof Error) {
//            throw (Error) t;
//        }
//        if (t instanceof RuntimeException) {
//            throw (RuntimeException) t;
//        } else {
//            throw new RuntimeException(
//                    "Error introspecting " + c.getName() + ":",
//                    t
//                    );
//        }
//    }

    private static void handleITE(Method m, InvocationTargetException ite) {
        Throwable t = ite.getCause();
        if (t instanceof Error) {
            throw (Error) t;
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new RuntimeException(
                    "Error invoking method " + m.getName() + ":",
                    t
                    );
        }
    }
    
    private static PropertyDescriptor getPD(Object bean, String pname) {
        PropertyDescriptor ret = null;
        Class c = bean.getClass();
        ret = findPD(getPDs(c), pname);
        return ret;
    }
    
    private static PropertyDescriptor[] getPDs(Class c) {
        try {
            BeanInfo bi = Introspector.getBeanInfo(c);
            return bi.getPropertyDescriptors();
        } catch (IntrospectionException inte) {
            log.error("cannot introspect class: " + c.getClass(), inte);
            throw new RuntimeException(
                    "cannot introspect class: " + c.getClass() + inte,
                    inte
                    );
        }
        
    }
    private static PropertyDescriptor findPD(PropertyDescriptor[] pds, String name) {
        PropertyDescriptor ret = null;
        for (PropertyDescriptor pd : pds) {
            String pname = pd.getName();
            if (name.equalsIgnoreCase(pname)) {
                ret = pd;
                break;
            }
        }
        return ret;
    }
    

    public static String type2string(Class declaringClass, Object o) {
        if (o == null) {
            return null;
        }
        String ret = null;
        Class c = o.getClass();
        if (c == String.class) {
            ret = (String) o;
            if (ret != null && ret.length() == 0) {
                ret = null;
            }
        } else if (c == Boolean.class || c == Boolean.TYPE) {
            ret = o.toString();
        } else if (c == Integer.class || c == Integer.TYPE) {
            ret = o.toString();
        } else if (c == java.awt.Color.class/* || c == java.awt.Paint.class*/) {
            Color col = (Color) o;
//            ret = '#' + pad(Integer.toHexString(col.getRed()))
//            + pad(Integer.toHexString(col.getGreen()))
//            + pad(Integer.toHexString(col.getBlue()));
            ret = ChartUtil.encodeColor(col);
        } else if (c == Float.class || c == Float.TYPE) {
            ret = o.toString();
        } else if (c == Double.class || c == Double.TYPE) {
            ret = o.toString();
        } else if (c == BasicStroke.class) {
            BasicStroke stroke = (BasicStroke) o;
            ret = ChartUtil.encodeStroke(stroke);
        } else if (c == Font.class) {
            Font f = (Font) o;
            ret = f.getFamily() + "-" + f.getSize();
        } else if (c == GradientPaint.class) {
            GradientPaint gp = (GradientPaint) o;
            ret = "gradient:" + type2string(c, gp.getColor1()) +
            "," + type2string(c, gp.getColor2()) + "," + gp.getPoint1().getX()
            + "," + gp.getPoint1().getY() + "," + gp.getPoint2().getX()
            + "," + gp.getPoint2().getY() + ",true";
        } else if (c == DateFormat.class || c == SimpleDateFormat.class) {
            if (o instanceof SimpleDateFormat) {
                ret = ((SimpleDateFormat)o).toPattern();
            }
        } else if (c == PlotOrientation.class) {
            if (o == PlotOrientation.HORIZONTAL) {
                ret = "HORIZONTAL";
            } else if (o == PlotOrientation.VERTICAL) {
                ret = "VERTICAL";
            }
        } else if (c == RectangleInsets.class) {
            RectangleInsets rect = (RectangleInsets)o;
            // top,left,bottom,right
            ret = "" + rect.getTop() + ',' + rect.getLeft() + ','
            + rect.getBottom() + ',' + rect.getRight();
            if (rect.getUnitType() == UnitType.RELATIVE) {
                ret += ",RELATIVE";
            }
            
        } else if (c == NumberFormat.class) {
            if (o instanceof DecimalFormat) {
                ret = ((DecimalFormat)o).toPattern();
            }
        } else if (c == HorizontalAlignment.class) {
            if (o == HorizontalAlignment.CENTER) {
                ret = "CENTER";
            } else if (o == HorizontalAlignment.LEFT) {
                ret= "LEFT";
            } else if (o == HorizontalAlignment.RIGHT) {
                ret = "RIGHT";
            }
        } else if (c == NumberTickUnit.class) {
            NumberTickUnit ntu = (NumberTickUnit) o;
            ret = "" + ntu.getSize();
        } else if (c == Date.class) {
            Date d = (Date)o;
            synchronized (STANDARD_DATE) {
                ret = STANDARD_DATE.format(d);
            }
        } else if (c == StandardBarPainter.class || c == GradientBarPainter.class) {
            ret = ChartUtil.encodeCategoryBarPainter((BarPainter)o);
        } else if (c == StandardXYBarPainter.class || c == GradientXYBarPainter.class) {
            ret = ChartUtil.encodeXYBarPainter((XYBarPainter)o);
        } else if (c == DateTickMarkPosition.class) {
            ret = ChartUtil.encodeDateTickMarkPosition((DateTickMarkPosition)o);
        } else if (c == RectangleEdge.class) {
            ret = ChartUtil.encodeRectangleEdge((RectangleEdge)o);
        } else if (c == VerticalAlignment.class) {
            ret = ChartUtil.encodeVerticalAlignment((VerticalAlignment)o);
        } else if (c == CategoryLabelPositions.class) {
            ret = ChartUtil.encodeCategoryLabelPositions((CategoryLabelPositions)o);
        }
        return ret;
    }
    // standard cm date format 
    private static final DateFormat STANDARD_DATE = DateUtil.createDateFormat("yyyy-MM-dd"); 
//    private static String pad(String s) {
//        if (s.length() == 1) {
//            s = '0' + s;
//        }
//        return s;
//    }
    
    public static Object convertType(String s, Class c) {
        // FIXME: need more types
        // FIXME: need error checking (NumberFormatException, etc) here
        if (s == null) {
            return null;
        }
        Object ret = null;
        if (c == String.class) {
            ret = s;
        } else if (c == Boolean.class || c == Boolean.TYPE) {
            ret = "true".equalsIgnoreCase(s) ? Boolean.TRUE: Boolean.FALSE; 
        } else if (c == Integer.class || c == Integer.TYPE) {
            ret = Integer.parseInt(s);
        } else if (c == java.awt.Color.class || c == java.awt.Paint.class) {
            ret = ChartUtil.decodePaint(s);
        } else if (c == Float.class || c == Float.TYPE) {
            ret = Float.parseFloat(s);
        } else if (c == Double.class || c == Double.TYPE) {
            ret = Double.parseDouble(s);
        } else if (c == Stroke.class) {
            return ChartUtil.decodeStroke(s);
        } else if (c == Font.class) {
            Font f = Font.decode(s);
            ret = f;
        } else if (c == java.awt.Image.class) {
            ret = loadImage(s);
//            log.warn("from url=" + s + " loaded image=" + ret);
        } else if (c == DateFormat.class || c == SimpleDateFormat.class) {
            //SimpleDateFormat sdf = new SimpleDateFormat(s);
            //ret = sdf;
            ret = DateUtil.createDateFormat(s);
        } else if (c == java.awt.Shape.class) {
            ret = ShapeUtil.decode(s);
        } else if (c == PlotOrientation.class) {
            if ("HORIZONTAL".equalsIgnoreCase(s)) {
                ret = PlotOrientation.HORIZONTAL;
            } else if ("VERTICAL".equalsIgnoreCase(s)) {
                ret = PlotOrientation.VERTICAL;
            }
        } else if (c == AxisLocation.class) {
            if ("BOTTOM_OR_LEFT".equals(s)) {
                ret = AxisLocation.BOTTOM_OR_LEFT;
            } else if ("BOTTOM_OR_RIGHT".equals(s)) {
                ret = AxisLocation.BOTTOM_OR_RIGHT;
            } else if ("TOP_OR_LEFT".equals(s)) {
                ret = AxisLocation.TOP_OR_LEFT;
            } else if ("TOP_OR_RIGHT".equals(s)) {
                ret = AxisLocation.TOP_OR_RIGHT;
            }
        } else if (c == TextAnchor.class) {
            ret = ChartUtil.decodeTextAnchor(s);
        } else if (c == RectangleAnchor.class) {
            ret = ChartUtil.decodeRectAnchor(s);
        } else if (c == RectangleInsets.class) {
            ret = ChartUtil.decodeRect(s);
        } else if (c == NumberFormat.class || c == DecimalFormat.class) {
            DecimalFormat df = new DecimalFormat(s);
            ret = df;
        } else if (c == HorizontalAlignment.class) {
            if ("CENTER".equalsIgnoreCase(s)) {
                ret = HorizontalAlignment.CENTER;
            } else if ("LEFT".equalsIgnoreCase(s)) {
                ret = HorizontalAlignment.LEFT;
            } else if ("RIGHT".equalsIgnoreCase(s)) {
                ret = HorizontalAlignment.RIGHT;
            }
        } else if (c == NumberTickUnit.class) {
            double size = Double.parseDouble(s);
            NumberTickUnit ntu = new NumberTickUnit(size);
            ret = ntu;
        } else if (c == Date.class) {
            try {
                ret = DateUtil.parseDate(s);
                //p("parsed '" + s + "' to date #" + ret + "#");
            } catch (java.text.ParseException ignore) {ignore.printStackTrace(); }
        } else if (c == XYBarPainter.class) {
            ret = ChartUtil.decodeXYBarPainter(s);
        } else if (c == BarPainter.class) {
            ret = ChartUtil.decodeCategoryBarPainter(s);
        } else if (c == DateTickUnit.class) {
            ret = ChartUtil.decodeDateTickUnit(s);
        } else if (c == DateTickMarkPosition.class) {
            ret = ChartUtil.decodeDateTickMarkPosition(s);
        } else if (c == RectangleEdge.class) {
            ret = ChartUtil.decodeRectangleEdge(s);
        } else if (c == VerticalAlignment.class) {
            ret = ChartUtil.decodeVerticalAlignment(s);
        } else if (c == CategoryLabelPositions.class) {
            ret = ChartUtil.decodeCategoryLabelPositions(s);
        } else {
            log.warn("cannot convert to type " + c.getName());
            throw new RuntimeException("cannot convert type: " + c.getName());
        }
        return ret;
    }

    private static final Class[] SUPPORTED_CLASSES = {
        String.class, Boolean.class, Boolean.TYPE, Integer.class, Integer.TYPE,
        Float.class, Float.TYPE, Double.class, Double.TYPE, Color.class, Paint.class,
        GradientPaint.class, Stroke.class, BasicStroke.class, Font.class,
        DateFormat.class, SimpleDateFormat.class, PlotOrientation.class,
        RectangleInsets.class, NumberFormat.class, DecimalFormat.class,
        HorizontalAlignment.class, NumberTickUnit.class, Image.class, Shape.class,
        AxisLocation.class, TextAnchor.class, RectangleAnchor.class, Date.class,
        XYBarPainter.class, /*Category*/BarPainter.class, DateTickUnit.class,
        DateTickMarkPosition.class, RectangleEdge.class, VerticalAlignment.class,
        CategoryLabelPositions.class
    };
    // map of supported types
    static final Map<String,Boolean> TYPE_MAP = new HashMap<String,Boolean>();
    static {
        for (Class c : SUPPORTED_CLASSES) {
            TYPE_MAP.put(c.getName(), Boolean.TRUE);
        }
    }
    public static boolean supportsType(String classname) {
        return TYPE_MAP.get(classname) == Boolean.TRUE;
    }
    private static Image loadImage(String url) {
        Image ret = null;
        try {
            //ret = Toolkit.getDefaultToolkit().createImage(url);
            URL u = URLUtil.safeURL(url);
            if (u == null) {
                return null;
            }
            ImageIcon ii = new ImageIcon(u);
            int count = 0;
            while (ii.getImageLoadStatus() == MediaTracker.LOADING
                    && count < 50) {
                count++;
                Thread.sleep(100);
            }
            ret = ii.getImage();
            //p("loaded image, tracker status: " + mediaStatus(ii.getImageLoadStatus()));
        } catch (Exception e) {
            log.error("could not load image from url=" + url, e);
        }
        return ret;
    }
    private static void printImageAlignmentConstants() throws Exception {
        Class c = Class.forName("org.jfree.ui.Align");
        Field[] fields = c.getFields();
        for (Field f : fields) {
            int mods = f.getModifiers();
            if ((mods & Modifier.PUBLIC) != 0 && (mods & Modifier.STATIC) != 0
                    && f.getType() == Integer.TYPE) {
                //System.out.println("IMG_ALIGN_MAP.put(\"" + f.getName() + "\", " + f.getInt(c) + ");");
                System.out.println("{\"" + f.getName() + "\", \"" + f.getInt(c) + "\"},");
            }
        }
    }
    public static void main(String[] a) throws Exception {
        printImageAlignmentConstants();
        /*
        SimpleProps props = new SimpleProps();
        props.put("backgroundPaint","white");
        props.put("foo","bar");
        JFreeChart jfc = new JFreeChart(new PiePlot());
        setProps(jfc, props,null);
        */
        /*
        Class c = Class.forName("org.jfree.chart.renderer.category.BarRenderer");
        Object bean = c.newInstance();
        PropertyDescriptor[] pds = getPDs(c);
        for (PropertyDescriptor p : pds) {
            p("prop descriptor: '" + p.getName() + "' reader='" + p.getReadMethod() + "'");
            System.err.println("  default=" + getProp(bean, p));
        }
        */
    }
    
    private static void p(String s) {
        System.err.println("[BeanUtil] " + s);
    }
    

    
}
