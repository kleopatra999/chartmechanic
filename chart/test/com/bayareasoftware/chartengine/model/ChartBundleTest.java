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
package com.bayareasoftware.chartengine.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.functions.BuiltInFunctions;

public class ChartBundleTest {
    
    static SimpleProps h2dbprops = new SimpleProps();
    
    @BeforeClass
    public static void setupClass() throws Exception {
        h2dbprops.put(StandardProps.JDBC_DRIVER,"org.h2.Driver");
        h2dbprops.put(StandardProps.JDBC_URL,"jdbc:h2:mem:charttest;DB_CLOSE_DELAY=-1");
        h2dbprops.put(StandardProps.JDBC_USERNAME,"sa");
        h2dbprops.put(StandardProps.JDBC_PASSWORD,"");
    }
    
    private static DataSourceInfo h2Info(String tableName) {
        DataSourceInfo d = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        d.setTableName(tableName);
        d.loadProperties(h2dbprops);
        return d;
    }
    
    private DataSourceInfo ds(String id) {
        DataSourceInfo ret = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        ret.setId(id);
        return ret;
    }
    
    private List<Date[]> getTuesdayRange() {
//      StringBuilder sb = new StringBuilder();
    List<Date[]> ret = new ArrayList<Date[]>();
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, 2008);
    cal.set(Calendar.DAY_OF_YEAR, 1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    while (cal.get(Calendar.MONTH) < Calendar.APRIL) {
        Date[] da = new Date[2];
        da[0] = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        da[1] = cal.getTime();
        //p("tues range: " + da[0] + " - " + da[1]);
//        sb.append(da[0]).append(',').append(da[1]).append('\n');
        ret.add(da);
        cal.add(Calendar.DAY_OF_YEAR, 6);
    }
//    return sb.toString();
    return ret;
  }
    
//    @Test
//    public void testAddRemoveMarkers() {
//        ChartBundle cb = new ChartBundle();
//        ChartInfo ci = new ChartInfo();
//        cb.setChartInfo(ci);
//
//        MarkerInfo mi1 = new MarkerInfo();
//        mi1.setName("MARKER 1");
//        cb.addMarker(mi1);
//        
//        MarkerInfo mi2 = new MarkerInfo();
//        mi1.setName("MARKER 2");
//        cb.addMarker(mi2);
//        
//        List<MarkerInfo> markers = cb.getMarkers();
//        Assert.assertTrue(markers != null && markers.size() == 2);
//        Assert.assertTrue(markers.get(0).getId().equals(mi1.getId()));
//        Assert.assertTrue(markers.get(1).getId().equals(mi2.getId()));
//        
//        cb.removeMarker(mi1);
//        markers = cb.getMarkers();
//        Assert.assertTrue(markers != null && markers.size() == 1);
//        Assert.assertTrue(markers.get(0).getId().equals(mi2.getId()));
//    }
    
    
    @Test
    public void testNormalBundle() {
        ChartBundle cb = new ChartBundle();
        ChartInfo ci = new ChartInfo();
        
        //String name = "90-day";
        String description = "90-day timeseries from 2008-01-01";
        String id = "new/chart";
        ci.setId(id);
        ci.setPlotType(PlotType.PLOT_TIME);
        //ci.setName(name);
        ci.setDescription(description);
        ci.setTimePeriod(TimeConstants.TIME_DAY);
        ci.setWidth(700);
        ci.setHeight(400);
        ci.setRenderType("Line");
        
        cb.setChartInfo(ci);
        
        //Assert.assertEquals(cb.getName(),name);
        Assert.assertEquals(cb.getDescription(),description);
        Assert.assertEquals(cb.getId(), id);

        DataSourceInfo ds;
        SeriesDescriptor s;
        
        ds = h2Info("TIMESERIES");
        ds.setDataScript("SELECT d,x FROM TIMESERIES");
        cb.addDataSource(ds);
        s = new SeriesDescriptor("X",ds.getId());
        ci.addSeriesDescriptor(s);
        
        ds = h2Info("TIMESERIES");
        ds.setDataScript("SELECT d,y FROM TIMESERIES");
        s = new SeriesDescriptor("Y",ds.getId());
        ci.addSeriesDescriptor(s);
        cb.addDataSource(ds);

        MarkerDescriptor mi;
        SimpleProps sp = new SimpleProps();
        sp.put("stroke", "line=2.0|dash=2");
        sp.put("paint", "#ffaa00");
        sp.put("labelTextAnchor", "BOTTOM_CENTER");
        sp.put("labelAnchor", "CENTER");
        sp.put("labelFont", "SansSerif-bold-12");
        sp.put("labelPaint", "#ffaa00");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.DAY_OF_YEAR, 31 + 28 + 2);
        mi = cb.addDateMarker("MARCH 2nd", cal.getTime());
        mi.setMarkerProps(sp);
        
//        sp = new SimpleProps();
//        sp.put("paint", "#aaff00"); // add another marker property called 'paint' to ensure that the two marker descriptors are serialized properly 
        mi = cb.addDateIntervalMarker("TUESDAY", getTuesdayRange());
//        mi.setMarkerProps(sp);
        
        try {
            cb.validate();
        } catch (Exception e) {
            Assert.fail("ChartBundle should have been valid but wasn't because of " + e);
        }

        cb.setUseCache(true);
        System.out.println("normal bundle cb = " + cb);
        
        ChartBundle cb2 = new ChartBundle();
        SimpleProps p = cb.serializeToProps(null,null);
        
        System.out.println("serialized to property of cb = \n" + p);

        cb2.deserializeFromProps(p, null);
        
        System.out.println("cb2 = " + cb);
        
        try {
            cb2.validate();
        } catch (Exception e) {
            Assert.fail("deserialized cb2 should have been valid but wasn't because of " + e);
        }
        
        Assert.assertEquals(cb,cb2);
        
//      XStream xstream = new XStream(new JettisonMappedXmlDriver());
//        XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
//        // using straight XML, we are actually about to deserialize and compare accurately
//        //XStream xstream = new XStream();  
//        xstream.alias("chartbundle", ChartBundle.class);
//
//        String json = xstream.toXML(cb);
//        System.out.println("ChartBundle JSON from JettisonMapper in XStream: ===========>\n");
//        System.out.println(json);
        
    }
    @Test
    public void testErrorChecking() {
        ChartBundle cb = new ChartBundle();
        ChartInfo ci;
        cb.setChartInfo(ci = new ChartInfo());
        ci.setId("integrity test");
        SeriesDescriptor s;
        DataSourceInfo d;
        MarkerDescriptor m;
        
        {
            d = ds("FOO");
            d.setId("test/d1");
            s = new SeriesDescriptor();
            s.setSource(d.getId());
            ci.addSeriesDescriptor(s);
            assertEquals(1, ci.getSeriesCount());
            // chart has series, but not the data source.
            // assert reference error
            assertNotNull(cb.getSeriesError(s));
            assertTrue(s.isError());
            // add the data source, no more error
            cb.addDataSource(d);
            assertNull(cb.getSeriesError(s));
            assertFalse(s.isError());
            cb.removeSeriesDescriptor(0);
            assertEquals(0,cb.getDataSources().length);
        }
        {
            m = cb.addNumericMarker("M1", 20d);
            assertEquals(1, ci.getMarkerCount());
            assertNull(cb.getMarkerError(m));
            
            m = new MarkerDescriptor();
            m.setName("M2");
            m.setSource(d.getId());
            ci.addMarkerDescriptor(m);
            assertEquals(2, ci.getMarkerCount());
            // chart has marker, but not the data source.
            // assert reference error on 2nd, not 1st
            assertNull(cb.getMarkerError(ci.getMarker(0)));
            assertFalse(ci.getMarker(0).isError());
            assertNotNull(cb.getMarkerError(ci.getMarker(1)));
            assertTrue(m.isError());
            // add data source, no more error
            cb.addDataSource(d);
            String err = cb.getMarkerError(ci.getMarker(1));
            assertFalse(m.isError());
            assertNull("expect null error, not '" + err + "'", err);
            
        }
    }    
    @Test
    public void testIntegrity() {
        ChartBundle cb = new ChartBundle();
        ChartInfo ci;
        cb.setChartInfo(ci = new ChartInfo());
        ci.setId("integrity test");
        SeriesDescriptor s;
        DataSourceInfo d;
        MarkerDescriptor m;
        
        d = ds("d1");
        s = new SeriesDescriptor();
        s.setName("s1");
        s.setSource(d.getId());
        cb.addDataSource(d);
        assertEquals(
                "should have failed validation with unrefereced data source '" + d.getId() + "'",
                1, cb.validate().size()
                );
        // should remove 1
        assertEquals(1, cb.removeUnreferencedDataSources());
        // add it back
        cb.addDataSource(d);
        // add a series referencing it
        ci.addSeriesDescriptor(s);
        // should now validate
        assertEquals(0, cb.validate().size());
        cb.removeSeriesDescriptor(0);
        // valid after removal
        assertEquals(0, cb.validate().size());
        assertEquals(0, cb.getDataSources().length);
        
        m = new MarkerDescriptor();
        m.setSource(d.getId());
        cb.addDataSource(d);
        ci.addMarkerDescriptor(m);
        // validate
        assertEquals(0, cb.validate().size());
        
        cb.removeMarkerDescriptor(0);
        // validate
        assertEquals(0, cb.validate().size());
        assertEquals(0, cb.getDataSources().length);

        cb.addDataSource(d);
        ci.addMarkerDescriptor(m);
        ci.addSeriesDescriptor(s);
        assertEquals(0, cb.validate().size());
        // both marker and series refer to same data source
        assertEquals(1, cb.getDataSources().length);
        
        cb.removeMarkerDescriptor(0);
        // should still be valid, and have one data source
        assertEquals(0, cb.validate().size());
        // remove the series
        cb.removeSeriesDescriptor(0);
        assertEquals(0, cb.validate().size());
        // no more data sources in the  bundle
        assertEquals(0, cb.getDataSources().length);
    }
    
    // removing a series, formula-based markers/series based on it
    // should also be removed
    @Test
    public void testRemoveDependendDescriptors() {
        ChartBundle cb = new ChartBundle();
        ChartInfo ci;
        cb.setChartInfo(ci = new ChartInfo());
        ci.setId("integrity test 2");
        SeriesDescriptor s, s2, ma;
        DataSourceInfo d;
        MarkerDescriptor avg;

        d = ds("d1");
        s = new SeriesDescriptor();
        s.setName("s1");
        s.setSource(d.getId());
        cb.addDataSource(d);
        ci.addSeriesDescriptor(s);

        s2 = new SeriesDescriptor();
        s2.setName("s2");
        s2.setSource(d.getId());
        ci.addSeriesDescriptor(s2);

        assertEquals(2, ci.getSeriesCount());
        
        // add a marker averge
        {
            avg = new MarkerDescriptor();
            avg.setFunc(BuiltInFunctions.FN_AVG);
            avg.addArg(new Arg(ArgType.SID, s.getSid()));
            ci.addMarkerDescriptor(avg);
        }
        // add a moving average of s
        {
            ma = new SeriesDescriptor();
            ma.setName("s1 MA");
            ma.setFunc(BuiltInFunctions.FN_MVAVG);
            ma.addArg(new Arg(ArgType.SID, s.getSid()));
            ma.addArg(new Arg(ArgType.NUMBER, 5));
            ci.addSeriesDescriptor(ma);
        }
        assertEquals(3, ci.getSeriesCount());
        assertEquals(1, ci.getMarkerCount());
        assertEquals("expect s to be at index 0", s, ci.getSeriesDescriptor(0));
        assertEquals("expect s2 at index 1", s2, ci.getSeriesDescriptor(1));
        assertEquals("expect ma at index 2", ma, ci.getSeriesDescriptor(2));
        //p("sids for series are '" + s.getSid() + "' '" + s2.getSid() + "' '" + ma.getSid() + "'");
        cb.removeSeriesDescriptor(0);
        assertEquals("now expect s2 at index 0", s2, ci.getSeriesDescriptor(0));
        assertEquals("no expect zero markers", 0, ci.getMarkerCount());
        assertEquals("expect only 1 series after removal", 1, ci.getSeriesCount());
    }
    
    private static void p(String s) {
        System.out.println("[ChartBundleTest] " + s);
    }
}
