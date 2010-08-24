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
package com.bayareasoftware.tag;

import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.Axis;

import com.bayareasoftware.chartengine.chart.ChartDataUtil;
import com.bayareasoftware.chartengine.chart.ChartDiskCache;
import com.bayareasoftware.chartengine.chart.ChartDiskResult;
import com.bayareasoftware.chartengine.chart.ChartDriver;
import com.bayareasoftware.chartengine.chart.ChartDriverManager;
import com.bayareasoftware.chartengine.chart.ChartResult;
import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataSource;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.BaseDescriptor;
import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.LogoInfo;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;

public class ChartController {

    private SimpleProps templateChartProps = new SimpleProps();
    private ChartDriver fac;
    private File cacheRoot;
    //static ChartCache cache;
    private ChartDiskCache cache;
    private String servletPrefix;
    private String defaultJdbcDriver, defaultJdbcUrl,
    defaultJdbcUsername, defaultJdbcPassword;
    private boolean relativeURIs = false;
    private ChartController() {
        fac = ChartDriverManager.getChartDriver(ChartDriverManager.JFREECHART);
        // FIXME: config
        cacheRoot = new File("/tmp/chart-cache");
        cacheRoot.mkdirs();
        cache = new ChartDiskCache(cacheRoot);
        cache.clear();
        servletPrefix = "/chart-images";
        initTheme();
    }
    
    private static ChartController instance = new ChartController();

    public static ChartController get() { return instance; }
    
    public String getChartURI(ChartDiskResult cdr) {
        String rootPath = cacheRoot.getAbsolutePath();
        String path = cdr.getImagePath();
        int len = rootPath.length();
        
        return servletPrefix + "/" + path.substring(len + 1);
    }
    public File getCacheRoot() { return cacheRoot; }

    public ChartDiskResult getChart(ChartBundle cb, Map<String,String> params) {
        return cache.getChart(cb, params);
    }
    
    public void putChart(ChartDiskResult cdr, ChartBundle cb, Map<String,String> params) {
        cache.putChart(cdr, cb, params);
    }
    public ChartDiskResult prepChartResult(ChartBundle cb) {
        return cache.prepChartResult(cb, null);
    }
    public ChartDiskResult createChart(ChartBundle cb, LogoInfo logo, ChartDiskResult dr)
    throws Exception {
        Map<Integer,DataStream> smap = new HashMap<Integer,DataStream>();
        
        Map<String,String> paramValues = new HashMap<String,String>(); // no param values
        
        ChartInfo ci = cb.getChartInfo();
        for (BaseDescriptor bd : ci.getSortedDescriptors()) {
            String src = bd.getSource();
            if (src != null) {
                // this is a descriptor that gets data straight from a data source
                DataSourceInfo dsi = cb.getDataSourceByID(src);
                DataSource dataSource = DSFactory.createDataSource(dsi);
                DataStream r = DSFactory.eval(dataSource,paramValues);

                if (bd instanceof SeriesDescriptor) {
                    if (paramValues != null) {
                        String sstr = paramValues.get(ChartConstants.PARAM_START_DATE);
                        String estr = paramValues.get(ChartConstants.PARAM_END_DATE);
                        if (sstr != null || estr != null) {
                            r = ChartDataUtil.limitStreamByDateInterval(r,(SeriesDescriptor)bd,sstr,estr);
                        }
                    }
                }
                smap.put(bd.getSid(),r);
            } else {
                if (bd.getFunc() != null) {
                    DataStream d = ChartDataUtil.computeDerivedDataStream(ci,bd,smap);
                    smap.put(bd.getSid(),d);
                }
            }
            
        }
        ChartResult cr = new ChartResult(dr);
        cr = fac.create(cb.getChartInfo(),logo,smap,templateChartProps,cr);
        return cr.getDiskResult();
        
    }    

    private void initTheme() {
        /*
        StandardChartTheme theme;
        //theme = new StandardChartTheme("mine");
        theme = (StandardChartTheme) StandardChartTheme.createLegacyTheme();
        theme.setSmallFont(Font.decode("Arial-plain-10"));
        theme.setRegularFont(Font.decode("Arial-plain-12"));
        theme.setLargeFont(Font.decode("Arial-plain-16"));
        theme.setLargeFont(Font.decode("Arial-bold-20"));
        ChartFactory.setChartTheme(theme);
        //Axis.DEFAULT_TICK_LABEL_FONT = Font.decode("Arial-plain-10");
        p("initialized theme");
        */
    }
    
    public String getDefaultJdbcDriver() {
        return defaultJdbcDriver;
    }

    public void setDefaultJdbcDriver(String defaultJdbcDriver) {
        this.defaultJdbcDriver = defaultJdbcDriver;
    }

    public String getDefaultJdbcUrl() {
        return defaultJdbcUrl;
    }

    public void setDefaultJdbcUrl(String defaultJdbcUrl) {
        this.defaultJdbcUrl = defaultJdbcUrl;
    }

    public boolean getRelativeURIs() { return relativeURIs; }
    public void setRelativeURIs(boolean b) { relativeURIs = b; }
    
    public String getDefaultJdbcUsername() {
        return defaultJdbcUsername;
    }

    public void setDefaultJdbcUsername(String defaultJdbcUsername) {
        this.defaultJdbcUsername = defaultJdbcUsername;
    }

    public String getDefaultJdbcPassword() {
        return defaultJdbcPassword;
    }

    public void setDefaultJdbcPassword(String defaultJdbcPassword) {
        this.defaultJdbcPassword = defaultJdbcPassword;
    }
    
    private static void p(String s) {
        System.out.println("[ChartControl] " + s);
    }
}
