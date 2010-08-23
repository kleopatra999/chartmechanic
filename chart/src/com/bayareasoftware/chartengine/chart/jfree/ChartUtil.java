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
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartColor;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.UnitType;

import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.model.TimeConstants;
import com.bayareasoftware.chartengine.model.TimeUtil;

public class ChartUtil implements ChartConstants {
    
    private static final Log log = LogFactory.getLog(ChartUtil.class);
    public static HashMap<String,Color> colorMap = new HashMap<String,Color>();
    
    private ChartUtil() { }
    
//    public static String[] getPlotTypes() {
//        String[] ret = new String[6];
//        int i = 0;
//        ret[i++] = PLOT_XY;
//        ret[i++] = PLOT_TIME;
//        ret[i++] = PLOT_CATEGORY;
//        ret[i++] = PLOT_PIE;
//        ret[i++] = PLOT_PIE3D;
//        ret[i++] = PLOT_RING;
//        return ret;
//    }
//
  
    private static Color decodeHexColor(String hex, Float alpha) {
        if (hex.charAt(0) == '#') {
            hex = hex.substring(1);
        }
        long lv;
        try {
            lv = Long.parseLong(hex, 16);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return null;
        }
        int val = (int) lv;
        int r, g, b, a = 0xff;
        if (hex.length() == 8) {
            // alpha last 8 bits, grab it and shift
            a = val & 0xff;
            val >>= 8;
        }
        if (alpha != null) {
            float af = alpha;
            if (af >= 0f && af <= 1f) {
                a = (int)(a * af);
            }
        }
        b = val & 0xff;
        g = (val >> 8) & 0xff;
        r = (val >> 16) & 0xff;
        //p("turned '" + hex + "' into '" + Integer.toHexString(r) + Integer.toHexString(g)
          //      + Integer.toHexString(b) + "' a=" + a);
        Color ret = new Color(r,g,b,a);
        return ret;
    }
    
    private static String pad(String s) {
        if (s.length() == 1) {
            s = '0' + s;
        }
        return s;
    }

    
    /**
     * encode a color into a string
     * @param c
     * @return
     */
    public static String encodeColor(Color col) {
        String ret = '#' + pad(Integer.toHexString(col.getRed()))
                         + pad(Integer.toHexString(col.getGreen()))
                         + pad(Integer.toHexString(col.getBlue()));
        return ret;
        
    }
    
    public static Color decodeColor(String colorName) {
        return decodeColor(colorName, null);
    }
    public static Color decodeColor(String col, Float alpha) {
        final String colorName = StringUtil.trim(col);
        if (colorName == null) {
            return null;
        }

        Color result = null;
        if (alpha == null) {
            result = colorMap.get(colorName);
            if (result != null) {
                return result;
            }
        }
        
        if (result == null && colorName.charAt(0) == '#') {
            result = decodeHexColor(colorName, alpha);
            if (result != null) {
                if (alpha == null) {
                    colorMap.put(colorName, result);
                }
                return result;
            }
        }
        /*
        if (colorName.length() >= 7 && colorName.charAt(0) == '#') {
            result = Color.decode(colorName);
            colorMap.put(colorName, result);
            if (alpha != null && alpha >= 0 && alpha <= 1) {
                int a = (int)(alpha * 255);
                result = new Color(result.getRed(), result.getGreen(), result.getBlue(), a);
            }
            return result;
        }
        
        if (colorName.length() >= 6 && isHexColor(colorName)) {
            result = Color.decode('#' + colorName);
            if (alpha != null && alpha >= 0 && alpha <= 1) {
                int a = (int)(alpha * 255);
                result = new Color(result.getRed(), result.getGreen(), result.getBlue(), a);
            }
            colorMap.put(colorName, result);
            return result;
        }
        */
        if (result == null) {
            try {
                Field f = Color.class.getField(colorName);
                result = (Color) f.get(null);
                colorMap.put(colorName, result);
                return result;
            } catch (Exception ignore) { }
            try {
                Field f = ChartColor.class.getField(colorName.toUpperCase());
                result = (Color) f.get(null);
                colorMap.put(colorName, result);
                return result;
            } catch (Exception ignore) { }
            // FIXME: need to record error/warning here....
            //throw new RuntimeException("cannot parse color '" + colorName + "'");
            return null;
        } else {
            return result;
        }
    }

    public static ValueAxis decodeValueAxis(String s) {
        ValueAxis ret = null;
        if ("number".equalsIgnoreCase(s)) {
            ret = new NumberAxis();
        } else if ("number3d".equalsIgnoreCase(s)) {
            ret = new NumberAxis3D();
        } else if ("date".equalsIgnoreCase(s)) {
            ret = new DateAxis();
        } else if ("log".equalsIgnoreCase(s)) {
            ret = new LogarithmicAxis(""); // FIXME: LogarithmicAxis instead?
            ((LogarithmicAxis)ret).setAllowNegativesFlag(true);
        } else if ("period".equalsIgnoreCase(s)) {
            ret = new PeriodAxis("");
        }
        return ret;
    }
    private static boolean isHexColor(String s) {
        boolean ret = false;
        if (s.length() == 6) {
            ret = true;
            for (int i = 0; i < 6; i++) {
                char c = s.charAt(i);
                c = Character.toLowerCase(c);
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')) {
                    // still valid
                } else {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }
    
    public static int decodeTimeUnit(String s) {
        int ret = DateTickUnit.MILLISECOND;
        
        return ret;
    }
    public static Paint decodePaint(String str) {
        if (str != null && str.startsWith("gradient:")) {
            Paint ret = null;
            str = str.substring("gradient:".length());
            String[] s = StringUtil.splitCompletely(str, ',', true);
            // need at least the colors 
            if (s.length < 2) {
                // FIXME: parse warning here
                return null;
            }
            Color c1 = decodeColor(s[0]);
            Color c2 = decodeColor(s[1]);
            if (c1 == null || c2 == null) {
                // FIXME: parse warning here
                return null;
            }
            float x1 = 0, y1 = 0, x2 = 100, y2 = 100;
            boolean cyclical = true;
            try {
                x1 = Float.parseFloat(s[2]);
                y1 = Float.parseFloat(s[3]);
                x2 = Float.parseFloat(s[4]);
                y2 = Float.parseFloat(s[5]);
                cyclical = Boolean.parseBoolean(s[6]);
            } catch (Exception ignore) {
                // FIXME: possible warning here
            }
            ret = new GradientPaint(x1,y1,c1,x2,y2,c2,cyclical);
            return ret;
        } else {
            return (Paint)decodeColor(str);//, alpha);
        }
    }
    public static final String BAR_PAINTER_SOLID = "SOLID";
    public static final String BAR_PAINTER_GRADIENT = "GRADIENT";
    public static String encodeXYBarPainter(XYBarPainter p) {
        String ret = null;
        if (p != null) {
            if (p instanceof StandardXYBarPainter) ret = BAR_PAINTER_SOLID;
            else if (p instanceof GradientXYBarPainter) ret = BAR_PAINTER_GRADIENT;
        }
        return ret;
    }
    public static XYBarPainter decodeXYBarPainter(String s) {
        XYBarPainter ret;
        if (BAR_PAINTER_GRADIENT.equals(s)) {
            ret = new GradientXYBarPainter();
        } else {
            ret = new StandardXYBarPainter();
        }
        return ret;
    }
    public static String encodeCategoryBarPainter(BarPainter p) {
        String ret = null;
        if (p != null) {
            if (p instanceof StandardBarPainter) ret = BAR_PAINTER_SOLID;
            else if (p instanceof GradientBarPainter) ret = BAR_PAINTER_GRADIENT;
        }
        return ret;
    }
    public static BarPainter decodeCategoryBarPainter(String s) {
        BarPainter ret;
        if (BAR_PAINTER_GRADIENT.equals(s)) {
            ret = new GradientBarPainter();
        } else {
            ret = new StandardBarPainter();
        }
        return ret;
    }
    private static final float[][] DEFAULT_DASHES = {
        null,
        { 2.0f, 2.0f }, // this is defaul for dashed lines in jfreechart
        { 0.5f, 1.0f },
        { 1.0f, 1.0f },
        { 3.0f, 3.0f },
        { 4.0f, 4.0f },
        { 5.0f, 5.0f },
        { 4.0f, 6.0f },
        { 8.0f, 8.0f },
        { 2.0f, 6.0f, 6.0f, 2.0f }
    };
    /**
     * 
     */
    public static RectangleInsets decodeRect(String str) {
        RectangleInsets ret = null;
        String[] s = StringUtil.splitCompletely(str, ',', true);
        double top = 0, left = 0, bottom = 0, right = 0;
        UnitType ut = UnitType.ABSOLUTE; 
        if (s.length >= 4) {
            top = decodeDouble(s[0],0);
            left = decodeDouble(s[1],0);
            bottom = decodeDouble(s[2],0);
            right = decodeDouble(s[3],0);
        }
        if (s.length > 4 && s[4].equalsIgnoreCase("relative")) {
            ut = UnitType.RELATIVE;
        }
        ret = new RectangleInsets(ut,top,left,bottom,right);
        return ret;
    }
    public static double decodeDouble(String s, double defalt) {
        double d = defalt;
        s = StringUtil.trim(s);
        if (s != null) {
            try {
                d = Double.parseDouble(s);
            } catch (NumberFormatException ignore) { }
        }
        return d;
    }
    /*
     * line=<float>,dash=<dash-type-int>
     */
    public static Stroke decodeStroke(String val) {
        if (!val.startsWith("line") && val.length() > 0) {
            // backward compat with old version that just had a float
            return new BasicStroke(Float.parseFloat(val));
        }
        Stroke ret = null;
        float line = 0.1f;
        int cap, join; 
        cap = BasicStroke.CAP_SQUARE;
        join = BasicStroke.JOIN_MITER;
        float miterlimit = 10f;
        float[] dashes = null;
        Map<String,String> params = nvPairs(val, '|');
        {
            String s;
            s = params.get("line");
            try {
                line = Float.parseFloat(s);
            } catch (NumberFormatException ignore) {}
            int dashIndex = 0;
            try {
                dashIndex = Integer.parseInt(params.get("dash"));
            } catch (NumberFormatException ignore) {
            } catch (NullPointerException ignore) {
            }
            //p("decoding stroke with line=" + line + " dash index=" + dashIndex);
            if (dashIndex > 0 && dashIndex < DEFAULT_DASHES.length) {
                dashes = DEFAULT_DASHES[dashIndex];
                miterlimit = 1.0f;
                cap = BasicStroke.CAP_BUTT;
                join = BasicStroke.JOIN_ROUND;
            }
        }
        float[] d2 = null;
        if (dashes != null) {
            d2 = new float[dashes.length];
            for (int i = 0; i < d2.length; i++) {
                d2[i] = line * dashes[i];
            }
        }
        ret = new BasicStroke(line, cap, join, miterlimit, d2, 0f);
        return ret;
    }
    public static String encodeStroke(BasicStroke bs) {
        StringBuilder sb = new StringBuilder();
        sb.append("line=").append(bs.getLineWidth()).append('|');
        /*
        sb.append("cap=");
        switch (bs.getEndCap()) {
        case BasicStroke.CAP_BUTT:
            sb.append("butt"); break;
        case BasicStroke.CAP_ROUND:
            sb.append("round"); break;
        case BasicStroke.CAP_SQUARE:
            sb.append("square"); break;
        default:
            sb.append(bs.getEndCap());
        }
        sb.append("|").append("join=");
        switch (bs.getLineJoin()) {
        case BasicStroke.JOIN_BEVEL:
            sb.append("bevel"); break;
        case BasicStroke.JOIN_MITER:
            sb.append("miter"); break;
        case BasicStroke.JOIN_ROUND:
            sb.append("round"); break;
        default:
            sb.append(bs.getLineJoin());
        }
        sb.append("|dashphase=").append(bs.getDashPhase()).append("|");
        sb.append("miterlimit=").append(bs.getMiterLimit()).append("|");
        */
        float[] dashes = bs.getDashArray();
        int dashType = 0;
        if (dashes != null) {
            dashType = 1;
        }
        sb.append("dash=").append(dashType);
        /*
        if (dashes != null) {
            for (int i = 0; i < dashes.length; i++) {
                if (i != 0) sb.append(',');
                sb.append(dashes[i]);
            }
        } else {
            sb.append("NONE");
        }
        */
        return sb.toString();
    }
    public static TextAnchor decodeTextAnchor(String s) {
        TextAnchor ret = null;
        if (s != null) {
            s = s.toUpperCase();
            ret = str2textAnchor.get(s);
        }
        if (ret == null) {
            ret = TextAnchor.TOP_CENTER;
        }
        return ret;
    }

    public static RectangleAnchor decodeRectAnchor(String s) {
        RectangleAnchor ret = null;
        if (s != null) {
            s = s.toUpperCase();
            ret = str2rectAnchor.get(s);
        }
        if (ret == null) {
            ret = RectangleAnchor.TOP;
        }
        return ret;
    }
    public static Layer decodeLayer(String val, Layer defalt) {
        Layer ret = defalt;
        if ("BACKGROUND".equalsIgnoreCase(val) || "back".equalsIgnoreCase(val)) {
            ret = Layer.BACKGROUND;
        } else if ("FOREGROUND".equalsIgnoreCase(val) || "fore".equalsIgnoreCase(val)) {
            ret = Layer.FOREGROUND;
        }
        return ret;
    }
    /*
     * format: <count>-<PERIOD-NAME>
     * "1-YEAR"
     * "5-DAY"
     * count is optional, and is implicitly 1 if not specified, so
     * "MONTH" is valid too, as "1-MONTH"
     */
    public static DateTickUnit decodeDateTickUnit(String val) {
        if (val == null) {
            return null;
        }
        DateTickUnit ret = null;
        int period = -1, count = 1;
        // period corresponds to constants in DateTickUnit
        // -1 is none of those...
        String[] sa = StringUtil.split(val, '-', true);
        int cmPeriod = TimeConstants.TIME_UNKNOWN;
        
        if (sa.length > 1) {
            try {
                count = Math.max(1, Integer.parseInt(sa[0]));
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
            try {
                cmPeriod = TimeUtil.decodeTimeString(sa[1]);
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
            
        } else if (sa.length > 0) {
            try {
                cmPeriod = TimeUtil.decodeTimeString(sa[0]);
            } catch (Exception ignore) {
                //ignore.printStackTrace();
            }
        }
        switch (cmPeriod) {
        case TimeConstants.TIME_MILLI:
            period = DateTickUnit.MILLISECOND;
            break;
        case TimeConstants.TIME_SECOND:
            period = DateTickUnit.SECOND;
            break;
        case TimeConstants.TIME_MINUTE:
            period = DateTickUnit.MINUTE;
            break;
        case TimeConstants.TIME_HOUR:
            period = DateTickUnit.HOUR;
            break;
        case TimeConstants.TIME_DAY:
            period = DateTickUnit.DAY;
            break;
        case TimeConstants.TIME_WEEK:
            period = DateTickUnit.DAY;
            count *= 7;
            break;
        case TimeConstants.TIME_MONTH:
            period = DateTickUnit.MONTH;
            break;
        case TimeConstants.TIME_QUARTER:
            period = DateTickUnit.MONTH;
            count *= 3;
            break;
        case TimeConstants.TIME_YEAR:
            period = DateTickUnit.YEAR;
            break;
        case TimeConstants.TIME_UNKNOWN:
        default:
            break;
        }
        //p("period=" + period + " decoded val='" + val + "'");
        if (period != -1) {
            ret = new DateTickUnit(period,count);
        }
        if (val != null && ret == null) {
            log.warn("could not decode DateTickUnit from '" + val + "'" );
        }
        return ret;
    }
    public static String encodeDateTickMarkPosition(DateTickMarkPosition pos) {
        String ret = null;
        if (pos == DateTickMarkPosition.START) {
            ret = "START";
        } else if (pos == DateTickMarkPosition.MIDDLE) {
            ret = "MIDDLE";
        } else if (pos == DateTickMarkPosition.END) {
            ret = "END";
        }
        return ret;
    }
    public static DateTickMarkPosition decodeDateTickMarkPosition(String val) {
        DateTickMarkPosition ret = DateTickMarkPosition.START;
        if ("MIDDLE".equalsIgnoreCase(val)) {
            ret = DateTickMarkPosition.MIDDLE;
        } else if ("END".equalsIgnoreCase(val)) {
            ret = DateTickMarkPosition.END;
        }
        return ret;
    }
    public static String encodeRectangleEdge(RectangleEdge re) {
        String ret = null;
        if (re == RectangleEdge.BOTTOM) { ret = "BOTTOM"; }
        else if (re == RectangleEdge.LEFT) { ret = "LEFT"; }
        else if (re == RectangleEdge.RIGHT) { ret = "RIGHT"; }
        else if (re == RectangleEdge.TOP) { ret = "TOP"; }
        return ret;
    }
    public static RectangleEdge decodeRectangleEdge(String s) {
        RectangleEdge ret = RectangleEdge.BOTTOM;
        if ("BOTTOM".equalsIgnoreCase(s)) {
            ret = RectangleEdge.BOTTOM;
        } else if ("LEFT".equalsIgnoreCase(s)) {
            ret = RectangleEdge.LEFT;
        } else if ("RIGHT".equalsIgnoreCase(s)) {
            ret = RectangleEdge.RIGHT;
        } else if ("TOP".equalsIgnoreCase(s)) {
            ret = RectangleEdge.TOP;
        }
        return ret;
    }
    public static String encodeVerticalAlignment(VerticalAlignment v) {
        String ret = null;
        if (v == VerticalAlignment.BOTTOM) {
            ret = "BOTTOM";
        } else if (v == VerticalAlignment.CENTER) {
            ret = "CENTER";
        } else if (v == VerticalAlignment.TOP) {
            ret = "TOP";
        }
        return ret;
    }
    public static VerticalAlignment decodeVerticalAlignment(String s) {
        VerticalAlignment v = null;
        if ("BOTTOM".equalsIgnoreCase(s)) {
            v = VerticalAlignment.BOTTOM;
        } else if ("CENTER".equalsIgnoreCase(s)) {
            v = VerticalAlignment.CENTER;
        } else if ("TOP".equalsIgnoreCase(s)) {
            v = VerticalAlignment.TOP;
        }
        return v;
    }
    public static String encodeCategoryLabelPositions(CategoryLabelPositions pos) {
        String ret = null;
        if (pos == CategoryLabelPositions.STANDARD) {
            ret = "STANDARD";
        } else if (pos == CategoryLabelPositions.UP_45) {
            ret = "UP_45";
        } else if (pos == CategoryLabelPositions.UP_90) {
            ret = "UP_90";
        } else if (pos == CategoryLabelPositions.DOWN_45) {
            ret = "DOWN_45";
        } else if (pos == CategoryLabelPositions.DOWN_90) {
            ret = "DOWN_90";
        }
        return ret;
    }
    public static CategoryLabelPositions decodeCategoryLabelPositions(String spec) {
        CategoryLabelPositions ret = null;
        if ("STANDARD".equals(spec)) {
            ret = CategoryLabelPositions.STANDARD;
        } else if ("UP_45".equals(spec)) {
            ret = CategoryLabelPositions.UP_45;
        } else if ("UP_90".equals(spec)) {
            ret = CategoryLabelPositions.UP_90;
        } else if ("DOWN_45".equals(spec)) {
            ret = CategoryLabelPositions.DOWN_45;
        } else if ("DOWN_90".equals(spec)) {
            ret = CategoryLabelPositions.DOWN_90;
        }
        return ret;
    }
    private static Map<String,String> nvPairs(String s, char delim) {
        Map<String,String> ret = new HashMap<String,String>();
        String[] nv1 = StringUtil.splitCompletely(s, delim, true);
        for (String eq : nv1) {
            String[] pair = StringUtil.split(eq, '=', true);
            if (pair.length == 2 && pair[1] != null && pair[1].length() != 0) {
                ret.put(pair[0], pair[1]);
            }
        }
        return ret;
    }
    private static final Map<String,RectangleAnchor> str2rectAnchor = new HashMap<String,RectangleAnchor>();
    static {
        str2rectAnchor.put("CENTER", RectangleAnchor.CENTER);
        str2rectAnchor.put("TOP", RectangleAnchor.TOP);
        str2rectAnchor.put("TOP_LEFT", RectangleAnchor.TOP_LEFT);
        str2rectAnchor.put("TOP_RIGHT", RectangleAnchor.TOP_RIGHT);
        str2rectAnchor.put("BOTTOM", RectangleAnchor.BOTTOM);
        str2rectAnchor.put("BOTTOM_LEFT", RectangleAnchor.BOTTOM_LEFT);
        str2rectAnchor.put("BOTTOM_RIGHT", RectangleAnchor.BOTTOM_RIGHT);
        str2rectAnchor.put("LEFT", RectangleAnchor.LEFT);
        str2rectAnchor.put("RIGHT", RectangleAnchor.RIGHT);        
    }
    private static final Map<String,TextAnchor> str2textAnchor = new HashMap<String,TextAnchor>();
    static {
        str2textAnchor.put("TOP_LEFT", TextAnchor.TOP_LEFT);
        str2textAnchor.put("TOP_CENTER", TextAnchor.TOP_CENTER);
        str2textAnchor.put("TOP_RIGHT", TextAnchor.TOP_RIGHT);
        str2textAnchor.put("HALF_ASCENT_LEFT", TextAnchor.HALF_ASCENT_LEFT);
        str2textAnchor.put("HALF_ASCENT_CENTER", TextAnchor.HALF_ASCENT_CENTER);
        str2textAnchor.put("HALF_ASCENT_RIGHT", TextAnchor.HALF_ASCENT_RIGHT);
        str2textAnchor.put("CENTER_LEFT", TextAnchor.CENTER_LEFT);
        str2textAnchor.put("CENTER", TextAnchor.CENTER);
        str2textAnchor.put("CENTER_RIGHT", TextAnchor.CENTER_RIGHT);
        str2textAnchor.put("BASELINE_LEFT", TextAnchor.BASELINE_LEFT);
        str2textAnchor.put("BASELINE_CENTER", TextAnchor.BASELINE_CENTER);
        str2textAnchor.put("BASELINE_RIGHT", TextAnchor.BASELINE_RIGHT);
        str2textAnchor.put("BOTTOM_LEFT", TextAnchor.BOTTOM_LEFT);
        str2textAnchor.put("BOTTOM_CENTER", TextAnchor.BOTTOM_CENTER);
        str2textAnchor.put("BOTTOM_RIGHT", TextAnchor.BOTTOM_RIGHT);        
    }
    private static void p(String s) {
        System.err.println("[ChartUtil] " + s);
    }
    public static void main(String[] a) throws Exception {
        //printRectangleAnchors();
        //String s = "#ff0000";
        //Color c = decodeColor(s);
        //System.err.println(s + "->" + c.getRed() + " (" + c.getRGB() + ")");
        //printTextAnchors();
        String s = "#FFff807f";
        Color c = decodeHexColor(s, null);
        p("'" + s + "'->'" + c + "'");
    }
    private static void printTextAnchors() {
        for (String s : str2textAnchor.keySet()) {
            System.out.println("ret.addItem(\"" + s + "\");");
        }
    }
//    private static void printRectangleAnchors() throws Exception {
//        Class c = RectangleAnchor.class;
//        Field[] fields = c.getFields();
//        for (Field f : fields) {
//            int mods = f.getModifiers();
//            if (f.getType() == RectangleAnchor.class && 
//                    (mods & Modifier.STATIC) != 0 &&
//                    (mods & Modifier.PUBLIC) != 0) {
//                String n  = f.getName();
//                System.out.println("str2rectAnchor.put(\"" + n + "\", RectangleAnchor." + n + ");");
//            }
//        }
//    }
//    private static void printTextAnchorsCode() throws Exception {
//        Class c = TextAnchor.class;
//        Field[] fields = c.getFields();
//        for (Field f : fields) {
//            int mods = f.getModifiers();
//            if (f.getType() == TextAnchor.class && 
//                    (mods & Modifier.STATIC) != 0 &&
//                    (mods & Modifier.PUBLIC) != 0) {
//                String n  = f.getName();
//                System.out.println("str2textAnchor.put(\"" + n + "\", TextAnchor." + n + ");");
//            }
//        }
//    }
}
