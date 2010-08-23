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
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;


public class ChartInfoTest {
    @Test
    public void testEmptySeriesDescriptors() {
    	ChartInfo ci = new ChartInfo();
    	Assert.assertTrue(ci.getSeriesCount() == 0);
    }

    @Test
    public void testSeriesDescriptors() {
    	ChartInfo ci = new ChartInfo();

    	ci.addSeriesDescriptor(newSD("1000",0,true));
        ci.addSeriesDescriptor(newSD("1001",0,true));
        ci.addSeriesDescriptor(newSD("1002",1,false));
    	
        ci.removeSeriesDescriptor(0); // remove the first SeriesDescriptor ('1000')
        ci.removeSeriesDescriptor(0); // remove the second SeriesDescriptor ('1001')
        
    	Assert.assertTrue(ci.getSeriesCount() == 1);  // after two removals we should have one left
    	SeriesDescriptor sd = ci.getSeriesDescriptor(0);
    	Assert.assertTrue(sd.getAxisIndex() == 1);
    	Assert.assertFalse(sd.isVisible());
    	Assert.assertTrue(sd.getSource().equals("1002"));

    }

  
    
    
    
    @Test
    public void testDerivedSeriesDescriptors() {
        ChartInfo ci = new ChartInfo();

        SeriesDescriptor sd1 = new SeriesDescriptor();
        sd1.setSid(100);
        ci.addSeriesDescriptor(sd1);
        
        SeriesDescriptor sd2 = new SeriesDescriptor();
        sd2.setSid(101);
        ci.addSeriesDescriptor(sd2);

        // sd3 depends on sd1
        SeriesDescriptor sd3 = new SeriesDescriptor();
        sd3.setSid(102);
        sd3.setFunc("some-func");
        sd3.addArg(new Arg(ArgType.SID,sd1.getSid()));
        
        ci.addSeriesDescriptor(sd3);
        
        Assert.assertTrue(ci.getSeriesCount() == 3);
        
        List<BaseDescriptor> dependents = ci.getDependentDescriptors(sd1);
        Assert.assertNotNull(dependents);
        Assert.assertTrue(dependents.size() == 1);
        Assert.assertTrue(dependents.get(0).getSid().equals(sd3.getSid()));
        
        // md1 depends on sd2
        MarkerDescriptor md1 = new MarkerDescriptor();
        md1.setSid(103);
        md1.setFunc("some-func");
        md1.addArg(new Arg(ArgType.SID,sd2.getSid()));
        ci.addMarkerDescriptor(md1);

        //md2 depends on md1 
        MarkerDescriptor md2 = new MarkerDescriptor();
        md2.setSid(104);
        md2.setFunc("some-func");
        md2.addArg(new Arg(ArgType.SID,md1.getSid()));
        ci.addMarkerDescriptor(md2);

        // sd4 depends on sd3
        SeriesDescriptor sd4 = new SeriesDescriptor();
        sd4.setSid(105);
        sd4.setFunc("some-func");
        sd4.addArg(new Arg(ArgType.SID,sd3.getSid()));
        
        List<BaseDescriptor> ordered = ci.getSortedDescriptors();
        // getSortDescriptors() doesn't require that the sid's come back in order from 100->104
        // but that would be a correct result from our implementation so let's check for that
        Integer last = Integer.MIN_VALUE;
        for (BaseDescriptor b : ordered) {
            Assert.assertTrue(b.getSid() > last);
            last = b.getSid();
//            System.err.println("b.sid = " + b.getSid());
        }
    }

    public SeriesDescriptor newSD(String sourceId, int axisIndex, boolean visible) {
        SeriesDescriptor sd = new SeriesDescriptor();
        sd.setSource(sourceId);
        sd.setAxisIndex(axisIndex);
        sd.setVisible(visible);
        return sd;
    }
    
    @Test
    public void testGetSeriesDescriptors() {
        ChartInfo ci = new ChartInfo();
        ci.addSeriesDescriptor(newSD("1000",0,true));
        ci.addSeriesDescriptor(newSD("1001",0,true));
        ci.addSeriesDescriptor(newSD("1002",1,false));
    	
    	Assert.assertTrue(ci.getSeriesCount() == 3);

    	SeriesDescriptor sd = ci.getSeriesDescriptor(0);
    	Assert.assertTrue(sd.getAxisIndex() == 0);
    	Assert.assertTrue(sd.getSource().equals("1000"));
    	Assert.assertTrue(sd.isVisible());

        sd = ci.getSeriesDescriptor(1);
        Assert.assertTrue(sd.getAxisIndex() == 0);
        Assert.assertTrue(sd.getSource().equals("1001"));
        Assert.assertTrue(sd.isVisible());

        sd = ci.getSeriesDescriptor(2);
        Assert.assertTrue(sd.getAxisIndex() == 1);
        Assert.assertTrue(sd.getSource().equals("1002"));
        Assert.assertFalse(sd.isVisible());
    	
    	List<SeriesDescriptor> descriptors = ci.getSeriesDescriptors();
    	
        Assert.assertTrue(descriptors.size() == 3);
        Assert.assertTrue(descriptors.get(0).getSource().equals("1000"));
        Assert.assertTrue(descriptors.get(1).getSource().equals("1001"));
        Assert.assertTrue(descriptors.get(2).getSource().equals("1002"));

        ci.clearAllSeriesDescriptors();
        Assert.assertTrue(ci.getSeriesCount() == 0);
        
    }
    
    
    @Test
    public void testSetSeriesDescriptors() {
        int SIZE = 5;
        Integer[] ids = new Integer[SIZE];
        //SeriesDescriptor[] descriptors = new SeriesDescriptor[SIZE];
        List<SeriesDescriptor> descriptors = new ArrayList<SeriesDescriptor>(SIZE);
        
        ChartInfo ci = new ChartInfo();
        for (int i=0;i<SIZE;i++) {
            ids[i] = new Integer(1000+i);
            ci.addSeriesDescriptor(newSD(ids[i].toString(),0,true));
            //descriptors.add(newSD(ids[i].toString(),0,true));
        }
        
        //ci.setSeriesDescriptors(descriptors);
        
        Assert.assertTrue(ci.getSeriesCount() == SIZE);  
        
        SeriesDescriptor sd = new SeriesDescriptor();
        sd.setSource("9999");
        ci.addSeriesDescriptor(sd);
        
        Assert.assertTrue(ci.getSeriesCount() == SIZE+1);
        Assert.assertTrue(ci.getSeriesDescriptor(SIZE).equals(sd));
    }
    
    @Test
    public void testSetGetProperties() {
        ChartInfo ci = new ChartInfo();
        ci.setProperty("plot.backgroundAlpha","0");
        Assert.assertTrue(ci.getProperty("plot.backgroundAlpha").equals("0"));
    }
    
    @Test
    public void testSetGetTimePeriod() {
        ChartInfo ci = new ChartInfo();
        ci.setTimePeriod(TimeConstants.TIME_DAY);
        Assert.assertTrue(ci.getTimePeriod() == TimeConstants.TIME_DAY);
    }
    
    @Test
    public void testToString() {
        // to game code coverage
        ChartInfo ci = new ChartInfo();
        ci.setPlotType(PlotType.PLOT_TIME);
        ci.setTimePeriod(TimeConstants.TIME_DAY);
        //ci.addSeriesId("1000");
        ci.addSeriesDescriptor(newSD("1000",0,true));
        Assert.assertTrue(ci.toString() != null);
        List<SeriesDescriptor> sds = ci.getSeriesDescriptors();
        Assert.assertTrue(sds.size() == 1);
        Assert.assertTrue(sds.get(0).getSource().equals("1000"));
    }
    

    @Test
    public void testSerialize() {
        ChartInfo ci = new ChartInfo();

        ci.addSeriesDescriptor(newSD("1000",0,true));
        ci.addSeriesDescriptor(newSD("1001",0,true));
        ci.addSeriesDescriptor(newSD("1002",1,false));
        MarkerDescriptor md = new MarkerDescriptor();
        md.setName("200");
        ci.addMarkerDescriptor(md);
        
        SimpleProps p = ci.serializeToProps(null,null);
//      System.out.println("p = " + p);
        
        ChartInfo ci2 = new ChartInfo();
        ci2.deserializeFromProps(p, null);
        
        Assert.assertTrue(ci.equals(ci2));

        String prefix = "SOME PREFIX";
        p = ci.serializeToProps(null,prefix);
//        System.out.println("serialized with prefix:\n" + p);
        
        ChartInfo ci3 = new ChartInfo();
        ci3.deserializeFromProps(p, prefix);

//        System.out.println("======> ci = " + ci);
//        System.out.println("======> ci3 = " + ci3);
        
        Assert.assertTrue(ci.equals(ci3));
        
        
    }
    
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ChartInfoTest.class);
    }
    
    
}
