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

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;

import com.bayareasoftware.chartengine.chart.ChartDataUtil;
import com.bayareasoftware.chartengine.chart.ChartDiskCache;
import com.bayareasoftware.chartengine.chart.ChartDiskResult;
import com.bayareasoftware.chartengine.chart.ChartDriver;
import com.bayareasoftware.chartengine.chart.ChartDriverManager;
import com.bayareasoftware.chartengine.chart.ChartResult;
import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DataSource;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.functions.InflationAdjust;
import com.bayareasoftware.chartengine.model.BaseDescriptor;
import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.ChartConstants;
import com.bayareasoftware.chartengine.model.ChartInfo;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.LogoInfo;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.SeriesDescriptor;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.util.FileUtil;

/* configuration:
 *  cacheLocation, cacheTTL
 *  servletPrefix (relative to contextPath)
 *  default JDBC stuff
 *  default template
 */
public class ChartController {

    /**
     * Constants for configuration property names
     */
    public static final String CONFIG_CACHE_DIR = "cm.chartCacheDir";
    public static final String CONFIG_CACHE_TTL = "cm.chartCacheTTL";
    public static final String CONFIG_SERVLET_PREFIX = "cm.chartServletPrefix";
    public static final String CONFIG_JDBC_DRIVER = "cm.jdbcDriver";
    public static final String CONFIG_JDBC_URL = "cm.jdbcUrl";
    public static final String CONFIG_JDBC_USERNAME = "cm.jdbcUsername";
    public static final String CONFIG_JDBC_PASSWORD = "cm.jdbcPassword";
    public static final String CONFIG_JDBC_JNDI_NAME = "cm.jdbcJndiName";
    public static final int DEFAULT_TTL = 120;
    
    private SimpleProps templateChartProps = null; //new SimpleProps();
    private ChartDriver fac;
    private File cacheRoot;
    //static ChartCache cache;
    private ChartDiskCache cache;
    private String servletPrefix;
    private String defaultJdbcDriver, defaultJdbcUrl,
    defaultJdbcUsername, defaultJdbcPassword, defaultJndiName;
    private Map<String,SimpleProps> builtinTemplates = new HashMap();
    private int defaultTtl = DEFAULT_TTL;
    
    private ChartController() {
        fac = ChartDriverManager.getChartDriver(ChartDriverManager.JFREECHART);
    }
    
    public void configure(ServletConfig sc) {
        String cachePath = StringUtil.joinPaths(
                System.getProperty("java.io.tmpdir"), "chart-cache");
        cachePath = getConfig(sc, CONFIG_CACHE_DIR, cachePath);
        // cache dir
        cacheRoot = new File(cachePath);
        cacheRoot.mkdirs();
        cache = new ChartDiskCache(cacheRoot);
        cache.clear();
        // cache TTL
        defaultTtl = Integer.parseInt(getConfig(sc, CONFIG_CACHE_TTL, "" + DEFAULT_TTL));
        // URI prefix
        servletPrefix = getConfig(sc, CONFIG_SERVLET_PREFIX, "/chart-images");
        // ChartTag takes account of contextPath, don't do it here
        /*servletPrefix = StringUtil.joinPaths(
                sc.getServletContext().getContextPath(), servletPrefix);
                */
        defaultJdbcDriver = getConfig(sc, CONFIG_JDBC_DRIVER, null);
        defaultJdbcUrl = getConfig(sc, CONFIG_JDBC_URL, null);
        defaultJdbcUsername = getConfig(sc, CONFIG_JDBC_USERNAME, null);
        defaultJdbcPassword = getConfig(sc, CONFIG_JDBC_PASSWORD, null);
        defaultJndiName = getConfig(sc, CONFIG_JDBC_JNDI_NAME, null);
        initTheme();
        initBuiltinTemplates();
        try {
            initData();
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            p("cannot initialize data: " + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    private static ChartController instance;

    public static ChartController get() {
        // http://en.wikipedia.org/wiki/Singleton_pattern#Traditional_simple_way_using_synchronization
        if (instance == null) {
            synchronized (ChartController.class) {
                if (instance == null) {
                    instance = new ChartController();
                }
            }
        }
        return instance;
    }
    
    public String getChartURI(ChartDiskResult cdr) {
        String rootPath = cacheRoot.getAbsolutePath();
        String path = cdr.getImagePath();
        int len = rootPath.length();
        return StringUtil.joinPaths(servletPrefix,  path.substring(len + 1));
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

    private String getResourceText(String res) {
        try {
            URL u = getClass().getResource(res);
            if (u == null)
                throw new RuntimeException("resource '" + res + "' not found");
            return FileUtil.readStreamAsString(u.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public SimpleProps getBuiltinTemplate(String name) {
        return builtinTemplates.get(name.toLowerCase());
    }
    private void initBuiltinTemplates() {
        final String resPrefix = "/com/bayareasoftware/tag/templates/";
        String templates = getResourceText(resPrefix + "templates.list");
        p("loaded template list");
        String[] list = StringUtil.splitCompletely(templates, '\n');
        for (String t : list) {
            t = t.trim();
            if (t.length() == 0) continue;
            String data = getResourceText(resPrefix + t + ".template");
            builtinTemplates.put(t, new SimpleProps(data));
            p("loaded template " + t);
        }
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
    
    /* routines for initializing certain built-in datasets */

    public int getDefaultTtl() {
        return defaultTtl;
    }

    public void setDefaultTtl(int defaultTtl) {
        this.defaultTtl = defaultTtl;
    }

    public String getDefaultJndiName() {
        return defaultJndiName;
    }

    public void setDefaultJndiName(String defaultJndiName) {
        this.defaultJndiName = defaultJndiName;
    }

    private String readURLAsString(String url) throws IOException {
        URL u = getClass().getResource(url);
        if (u == null) {
            throw new RuntimeException(
                    "Cannot find URL resource '" + url + "'"
                    );
        }
        Reader rdr = new InputStreamReader(u.openStream());
        StringBuilder sb = new StringBuilder();
        try {
            char[] buf = new char[512];
            int r;
            while ((r = rdr.read(buf)) > 0){
                sb.append(buf, 0, r);
            }
        } finally {
            rdr.close();
        }
        return sb.toString();
    }
    
    private void initData() throws Exception {
        DataSourceInfo cpi = createMonthlyCPI();
        InflationAdjust.populateCPI(cpi);
    }
    private DataSourceInfo createMonthlyCPI() throws Exception {
        String data = readURLAsString("/data/monthly-cpi.csv");
        DataSourceInfo ret = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        ret.setProperty(DataSourceInfo.CSV_DATA, data);
        ret.setDataStartRow(1);
        Metadata md = new Metadata(2);
        md.setColumnType(1, DataType.DATE);
        md.setColumnName(1, "Year & Month");
        md.setColumnFormat(1, "MM/yyyy");
        md.setColumnType(2, DataType.DOUBLE);
        md.setColumnName(2, "CPI");
        ret.setInputMetadata(md);
        ret.setDescription("Monthly US Consumer Price Index (CPI)\n"
                + "source: http://www.bls.gov/");
        return ret;
    }
    
    private static String getConfig(ServletConfig sc, String name, String defalt) {
        String ret;
        if ((ret = System.getProperty(name)) == null) {
            if ((ret = sc.getInitParameter(name)) == null) {
                ret = defalt;
            }
        }
        return ret;
    }
    private static void p(String s) {
        System.out.println("[ChartControl] " + s);
    }
}
