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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bayareasoftware.chartengine.chart.jfree.ChartUtil;

public class ChartUtilTest {

    @Test
    public void testDecodeColor() {
        Color c;
        c = ChartUtil.decodeColor("white");
        assertNotNull("null value for color 'white'", c);
        assertEquals("'white' doesn't translate to Color.", Color.WHITE, c); 
        c = ChartUtil.decodeColor("#05ff00");
        assertNotNull("null value for #00ff00", c);
        assertEquals(5, c.getRed());
        assertEquals(255, c.getGreen());
        assertEquals(0, c.getBlue());
    }
    
    @Test
    public void testDecodePaint() {
        Paint p = null;
        String s = "gradient:red,#303030,0,0,100,100,true";
        p = ChartUtil.decodePaint(s);
        assertNotNull("null value for decoding paint '" + s + "'", p);
        assertTrue("paint not a gradient paint: " + p.getClass().getName(),
                p instanceof GradientPaint);
        GradientPaint gp = (GradientPaint) p;
        Color c1 = gp.getColor1();
        Color c2 = gp.getColor2();
        Point2D p1 = gp.getPoint1();
        Point2D p2 = gp.getPoint2();
        assertTrue(0.0 == p1.getX());
        assertTrue(0.0 == p1.getY());
        assertTrue(100.0 == p2.getX());
        assertTrue(100.0 == p2.getY());
        assertEquals(0xff, c1.getRed());
        assertEquals(0, c1.getGreen());
        assertEquals(0, c1.getBlue());
        assertEquals(0x30, c2.getRed());
        assertEquals(0x30, c2.getGreen());
        assertEquals(0x30, c2.getBlue());
    }
}
