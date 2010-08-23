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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ArgType;
import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.MarkerDescriptor;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.PlotType;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.util.FileUtil;

public class ChartCacheTest {
    
    private Random rand = new Random(0);
    
    private static File cacheRoot;
    //private static ChartCache cache;
    private static ChartDiskCache cache;
    
    @BeforeClass
    public static void setup() {
        cacheRoot = new File("test.out/ChartCacheTest");
        cache = new ChartDiskCache(cacheRoot);
        cache.clear();
    }
    
    @Test
    public void testNoCacheIfTTLIsZero() {
        // if a chart has a datasource that has a TTL of zero, then do not cache it
        HashMap<String,String> params = null;

        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId("NoCacheIfTTLIsZero");
        DataSourceInfo dsi = cb.getDataSources()[0];
        dsi.setTTL(0);
        
        ChartDiskResult res = cache.prepChartResult(cb, params);
        cache.putChart(res, cb, params);
        
        // if TTL is null, then the cache should miss
        ChartDiskResult res2 = cache.getChart(cb, params);
        assertNull(res2);
    }
    

    @Test
    public void testTTLExpiry() {
        // if a chart has a datasource that has a TTL, and we ask for the chart after that TTL expires,
        // we should get a cache miss
        HashMap<String,String> params = null;

        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId("TTLExpiry");
        DataSourceInfo dsi = cb.getDataSources()[0];
        dsi.setTTL(1);
        
        ChartDiskResult res = cache.prepChartResult(cb, params);
        cache.putChart(res, cb, params);
        
        // put something at that location to simulate writing out content
        FileUtil.writeString(new File(res.getImagePath()),"simulated png");
        
        ChartDiskResult res2 = cache.getChart(cb, params);
        assertNotNull(res2);
        
        try {
            Thread.sleep(2000); // sleep three seconds;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        res2 = cache.getChart(cb, params);
        assertNull(res2); // since TTL exceeded, should get a cache miss
    }

    @Test
    public void testCurrentPutGet() {
        HashMap<String,String> params = null;

        String userId = "testUser";
        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId("CurrentPutGet");
        ChartDiskResult res = cache.prepCurrent(userId, cb, params);
        cache.putCurrent(res, userId, cb, params);
        
        
        String s = "simulated png";
        // put something at that location to simulate writing out content
        FileUtil.writeString(new File(res.getImagePath()),s);
        
        ChartDiskResult res2 = cache.getCurrent(userId,cb, params);
        assertNotNull(res2);
        
        String s2 = FileUtil.readAsString(new File(res2.getImagePath()));
        assertEquals(s,s2);

        HashMap<String,String> params2 = new HashMap<String,String>();
        params2.put("foo","bar");
        res2 = cache.getCurrent(userId,cb,params2);
        assertNull(res2);
        
        ChartBundle cb2 = makeChartBundle();
        cb2.getChartInfo().setDescription("New Description"); // this is a different chart
        res2 = cache.getCurrent(userId,cb2,params);
        assertNull(res2);
        
        cache.clear();
        res2 = cache.getChart(cb,params);
        assertNull(res2);

        
        ChartBundle cb3 = makeChartBundle();
        DataSourceInfo dsi = cb3.getDataSources()[0];
        dsi.setTTL(0);
        cb3.getChartInfo().setDescription("Data Source has Zero TTL"); // this is a different chart
        ChartDiskResult res3 = cache.prepCurrent(userId, cb3, params);
        cache.putCurrent(res3, userId, cb3, params);
        res3 = cache.getCurrent(userId,cb3,params);
        assertNull(res3); // should not be found because TTL was zero
    }
    
    @Test
    public void testSimplePutGet() {
        HashMap<String,String> params = null;

        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId("SimplePutGet");
        ChartDiskResult res = cache.prepChartResult(cb, params);
        cache.putChart(res, cb, params);
        
        // put something at that location to simulate writing out content
        FileUtil.writeString(new File(res.getImagePath()),"simulated png");
        
        String s = "simulated png";
        // put something at that location to simulate writing out content
        FileUtil.writeString(new File(res.getImagePath()),s);
        
        ChartDiskResult res2 = cache.getChart(cb, params);
        assertNotNull(res2);
        String s2 = FileUtil.readAsString(new File(res2.getImagePath()));
        assertEquals(s,s2);

        HashMap<String,String> params2 = new HashMap<String,String>();
        params2.put("foo","bar");
        res2 = cache.getChart(cb,params2);
        assertNull(res2);
        
        ChartBundle cb2 = makeChartBundle();
        cb2.getChartInfo().setDescription("New Description"); // this is a different chart
        res2 = cache.getChart(cb2,params);
        assertNull(res2);
        
        cache.clear();
        res2 = cache.getChart(cb,params);
        assertNull(res2);
        
        
    }
    
    @Test
    public void testInvalidateImages() throws IOException {
        HashMap<String,String> params = null;

        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId("InvalidateImages");
        ChartDiskResult res = cache.prepChartResult(cb, params);
        cache.putChart(res, cb, params);
        File f = new File(res.getImagePath());
        assertTrue(f.createNewFile());
        
        ChartDiskResult res2 = cache.getChart(cb, params);
        assertNotNull(res2);
        
        cache.invalidateChart(cb.getChartInfo().getId());
        res2 = cache.getChart(cb, params);
        assertNull(res2);
        assertFalse(f.exists());
    }

    @Test
    public void testInvalidateByUser() throws IOException {
        HashMap<String,String> params = null;
        String userId = "testUser2";
        
        ChartBundle cb = makeChartBundle();
        cb.getChartInfo().setId(userId+"/foo/bar/invalid1");
        ChartDiskResult res = cache.prepChartResult(cb, params);
        cache.putChart(res, cb, params);
        File f = new File(res.getImagePath());
        assertTrue(f.createNewFile());
        
        ChartDiskResult res2 = cache.getChart(cb, params);
        assertNotNull(res2);

        
        ChartBundle cb2 = makeChartBundle();
        cb2.getChartInfo().setId(userId+"/foo/bar/invalid2");
        res = cache.prepChartResult(cb2, params);
        cache.putChart(res, cb2, params);
        f = new File(res.getImagePath());
        assertTrue(f.createNewFile());
        
        cache.invalidateChartByUser(userId,false);
        res2 = cache.getChart(cb, params);
        assertNull(res2);
        res2 = cache.getChart(cb2, params);
        assertNull(res2);
        
        // expunge the user directory
        cache.invalidateChartByUser(userId,true);
        File userDir = new File(cache.getCacheRoot(),userId);
        assertFalse(userDir.exists());

    }
    

    @Test
    public void testInvalidateCurrent() throws IOException {
        HashMap<String,String> params = null;
        String userId = "testUser3";
        
        ChartBundle cb = makeChartBundle();
        ChartDiskResult res = cache.prepCurrent(userId,cb, params);
        cache.putCurrent(res, userId, cb, params);
        File f = new File(res.getImagePath());
        assertTrue(f.createNewFile());
        
        ChartDiskResult res2 = cache.getCurrent(userId,cb, params);
        assertNotNull(res2);

        
        ChartBundle cb2 = makeChartBundle();
        res = cache.prepCurrent(userId,cb2,params);
        cache.putCurrent(res, userId,cb2, params);
        f = new File(res.getImagePath());
        assertTrue(f.createNewFile());
        
        cache.invalidateCurrent(userId);
        res2 = cache.getCurrent(userId,cb, params);
        assertNull(res2);
        res2 = cache.getCurrent(userId,cb2, params);
        assertNull(res2);
    }


    

    //  generate a CSV string of 30 random X Y values
    private String getRandomXYValues() {
        StringBuilder sb = new StringBuilder();
        int v1, v2, v3;
        rand.setSeed(System.currentTimeMillis() + rand.nextInt(100000));
        for (int i = 0; i < 30; i++ ) {
            v1 = rand.nextInt(100);
            sb.append(String.valueOf(v1));
            sb.append(',');
            v2 = rand.nextInt(140);
            sb.append(String.valueOf(v2));
            sb.append(',');
            v3 = rand.nextInt(180);
            sb.append(String.valueOf(v3));
            sb.append('\n');
        }
        return sb.toString();
    }
    
    
    // take an index so we can easily make lots of them that differ by a little
    private ChartBundle makeChartBundle() {
        
        ChartBundle cb = new ChartBundle();

        DataSourceInfo ds = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        String s = getRandomXYValues();
        ds.setProperty(DataSourceInfo.CSV_DATA, s);
        ds.setTTL(60); // set a TTL of a minute so that chart cache is effective 
        
        Metadata md = new Metadata(3);
        md.setColumnName(1,"colX");
        md.setColumnType(1,DataType.INTEGER);
        md.setColumnName(2,"colY");
        md.setColumnType(2,DataType.INTEGER);
        md.setColumnName(3, "colZ");
        md.setColumnType(3, DataType.INTEGER);
        ds.setInputMetadata(md);
        
        ChartInfo ci = new ChartInfo();
        ci.setDescription("A Random XY");
        
        SeriesDescriptor sd1 = new SeriesDescriptor();
        sd1.setName("Series 1");
        sd1.setXColumn(1);
        sd1.setYColumn(2);
        sd1.setSource(ds.getId());
        ci.addSeriesDescriptor(sd1);

        SeriesDescriptor sd2 = new SeriesDescriptor();
        sd2.setName("Series 2");
        sd2.setXColumn(1);
        sd2.setYColumn(3);
        sd2.setSource(ds.getId());
        ci.addSeriesDescriptor(sd2);

        ci.setWidth(400);
        ci.setHeight(400);
        ci.setPlotType(PlotType.PLOT_XY);
        ci.setRenderType("Line");
        ci.setProperty("title.text", "XY Random");
        cb.setChartInfo(ci);
        cb.addDataSource(ds);
        {
            MarkerDescriptor avg = new MarkerDescriptor();
            avg.setName("S2 Average");
            avg.setFunc(BuiltInFunctions.FN_AVG);
            avg.addArg(new Arg(ArgType.SID,sd2.getSid()));
            avg.setRange(true);
            avg.setAxisIndex(0);
            ci.addMarkerDescriptor(avg);
            SimpleProps sp = new SimpleProps();
            sp.put("paint", "dark_blue");
            sp.put("stroke", "line=2.0|dash=2");
            //sp.put("paint", "#ffaa00");
            sp.put("labelTextAnchor", "BOTTOM_CENTER");
            sp.put("labelAnchor", "CENTER");
            sp.put("labelFont", "SansSerif-bold-12");
            avg.setMarkerProps(sp);
        }
        {
            MarkerDescriptor avg = new MarkerDescriptor();
            avg.setName("S1 Average");
            avg.setFunc(BuiltInFunctions.FN_AVG);
            avg.addArg(new Arg(ArgType.SID,sd1.getSid()));

            avg.setRange(true);
            avg.setAxisIndex(0);
            ci.addMarkerDescriptor(avg);
            SimpleProps sp = new SimpleProps();
            sp.put("paint", "red");
            sp.put("stroke", "line=2.0|dash=2");
            //sp.put("paint", "#ffaa00");
            sp.put("labelTextAnchor", "TOP_CENTER");
            sp.put("labelAnchor", "CENTER");
            sp.put("labelFont", "SansSerif-bold-12");
            avg.setMarkerProps(sp);
        }
        
        return cb;
    }
    

    
}
