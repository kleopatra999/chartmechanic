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
package com.bayareasoftware.chartengine.chart;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ShapeUtil {

    public static final double DEFAULT_SIZE = 6.0;
    
    public static final Shape SQUARE = square(DEFAULT_SIZE);
    public static final Shape CIRCLE = circle(DEFAULT_SIZE);
    public static final Shape TRIANGLE_UP = upTriangle(DEFAULT_SIZE);
    public static final Shape TRIANGLE_DOWN = downTriangle(DEFAULT_SIZE);
    public static final Shape DIAMOND = diamond(DEFAULT_SIZE);

    public static final Shape decode(String spec) {
        Shape ret = null;
        double size = DEFAULT_SIZE;
        if ("square".equalsIgnoreCase(spec)) {
            ret = SQUARE;
        } else if ("circle".equalsIgnoreCase(spec)) {
            ret = CIRCLE;
        } else if ("triangle-up".equalsIgnoreCase(spec)) {
            ret = TRIANGLE_UP;
        } else if ("triangle-down".equalsIgnoreCase(spec)) {
            ret = TRIANGLE_DOWN;
        } else if ("diamond".equalsIgnoreCase(spec)) {
            ret = DIAMOND;
        } else {
            throw new IllegalArgumentException(
                    "unknown shape: '" + spec + "'"
                    );
        }
        return ret;
    }
    public static Shape rect(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x,y,w,h);
    }
    public static Shape square(double size) {
        double del = - size / 2;
        return rect(del,del,size,size);
    }
    public static Shape ellipse(double x, double y, double w, double h) {
        return new Ellipse2D.Double(x,y,w,h);
    }
    public static Shape circle(double size) {
        double del = - size / 2;
        return ellipse(del,del,size,size);
    }
    public static Shape diamond(double size) {
        int del = (int)(size / 2);
        int[] xp = new int[] {0,del,0,-del};
        int[] yp = new int[] {-del,0,del,0};
        return new Polygon(xp,yp,4);
    }
    public static Shape upTriangle(double size) {
        double del = size / 2;
        int[] xp = new int[] { 0, (int)del, (int)-del };
        int[] yp = new int[] {(int)-del, (int)del, (int)del };
        return new Polygon(xp,yp,3);
    }
    public static Shape downTriangle(double size) {
        double del = size / 2;
        int[] xp = new int[] { (int)-del, (int)del, 0 };
        int[] yp = new int[] {(int)-del, (int)-del, (int)del };
        return new Polygon(xp,yp,3);
    }
}
