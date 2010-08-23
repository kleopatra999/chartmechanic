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

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.util.FileUtil;
import com.bayareasoftware.chartengine.util.Hash;

/**
 * ChartCache encapsulates the caching of charts.
 * 
 * The basic interface is
 *      public ChartResult getChart(ChartBundle cb, Map<String,String> params);
 *              given a chartbundle, and some params, a ChartResult is returned if the cache has it, otherwise null.      
 *      public void invalidateChartById(String id); 
 *              invalidate all variants of the chart with this id
 *      public void invalidateChartByUser(String userId);
 *              invalidate all charts owned by this user
 *      public ChartResult prepChartResult(ChartBundle cb, Map<String,String> params)
 *              for a given ChartBundle/params, prepare a ChartResult with the filepaths so that the
 *              ChartDriver can materialize the chart directly into those files
 * 
 *      public ChartResult getCurrent(String userId, ChartBundle cb, Map<String,String> params);
 *              each user has a special area called 'CURRENT' that stores the 'currently active' chart that is 
 *              being interactively manipulated.
 *      public void putCurrent(String userId, ChartBundle cb, Map<String,String> params);
 *      public ChartResult prepCurrent(String userId, ChartBundle cb, Map<String,String> params) {
 *              for a given ChartBundle/params, prepare a ChartResult with the filepaths so that the
 *              ChartDriver can materialize the chart directly into those files
 *      public void invalidateCurrent(String userId);
 *               
 *             
 * The Cache is implemented on disk as files.  
 *      each chart is a directory with all variants of that chart as <hashkey>.png and a file called expiry
 *      that stores the soonest expiry (based on datasource TTLs) of the chart
 *      Note that a chart may have other formats cached, but it ALWAYS has at least the png cached
 *      we rely on the presence of the png file to see if the request is a 'HIT' 
 *      e.g.     demo/Foo.chart/<hashKey1>.png
 *               demo/Foo.chart/<hashKey2>.png
 *               demo/Foo.chart/expiry
 * 
 *      in addition, there is a hierarchy called .CURRENT that stores the 'CURRENT' charts for a given user
 *      e.g.     demo/.CURRENT/<hashKey1>.png
 *               demo/.CURRENT/<hashKey2>.png
 * 
 *  
 */
public class ChartDiskCache {
    private static final Log log = LogFactory.getLog(ChartDiskCache.class);
    
    /**
     * the root directory on disk where the the ChartResults should have their paths set
     */
    private File cacheRoot;

    /**
     * create a ChartDiskCache anchored at cacheRoot
     * @param cacheRoot
     */
    public ChartDiskCache(File cacheRoot) {
        this.cacheRoot = cacheRoot;
    }
    
    public File getCacheRoot() { 
        return cacheRoot; 
    }
    
    public void setCacheRoot(File f) {
        cacheRoot = f;
        cacheRoot.mkdirs();
    }

    
    /**
     * given a chartbundle, and some params, a ChartDiskResult is returned if the cache has it, otherwise null.
     * @param cb
     * @param params
     * @return
     */
    public ChartDiskResult getChart(ChartBundle cb, Map<String,String> params) {
       String chartPath = cb.getChartInfo().getId();
       File dir = getChartDir(chartPath);
       if (!dir.exists())
           return null;
       
       long expiry = getExpiry(dir);
       long now = System.currentTimeMillis();
       if (expiry == 0) {
           // data sources for this chart are set to NEVER CACHE, so always a cache miss
           return null;
       }
       if (expiry != -1 && now > expiry) {
           // we've passed the expiry for this chart, do not return the cache result, but instead flush it
           this.invalidateChart(chartPath);
           return null;
       }
       
       String hashKey = hashChart(cb,params);
       return getDiskResultFromDir(dir,hashKey);
    }

    public void putChart(ChartDiskResult res, ChartBundle cb, Map<String,String> params) {
        String hashKey = hashChart(cb,params);
        String chartPath = cb.getChartInfo().getId();
        File dir = getChartDir(chartPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long expiry = mostRecentDSExpiry(cb);
        storeExpiry(dir,expiry);
    }
    
    /**
     * invalidate all variants of the chart with this id
     * @param id
     */
    public void invalidateChart(String chartId) {
        File dir = getChartDir(chartId);
//        log.warn("ChartDiskCache.invalidateChart   deep deleting dir = " + dir.getAbsolutePath());
        FileUtil.deepDeleteChildren(dir);
    }
    
    /**
     * invalidate all charts owned by this user
     * @param userId
     * @param expungeUser    - expunge the user entirely from the cache?
     */
    public void invalidateChartByUser(String userId, boolean expungeUser) {
        File dir = new File(getCacheRoot(),userId);
//        log.warn("ChartDiskCache.invalidateChartByUserId   deep deleting dir = " + dir.getAbsolutePath());
        if (expungeUser) {
            FileUtil.deepDelete(dir);
        } else {
            FileUtil.deepDeleteChildren(dir);
            // make sure we re-create the .CURRENT dir
            File currentDir = getCurrentDir(userId);
            currentDir.mkdirs();
        }
    }

    /**
     * each user has a special area called 'CURRENT' that stores the 'currently active' chart that is 
     * being interactively manipulated.
     * 
     * @param userId
     * @param cb
     * @param params
     * @return
     */
    public ChartDiskResult getCurrent(String userId, ChartBundle cb, Map<String,String> params) {
        String chartPath = cb.getChartInfo().getId();
        File dir = getCurrentDir(userId);
        if (!dir.exists())
            return null;
        
        long expiry = getExpiry(dir);
        long now = System.currentTimeMillis();
        if (expiry == 0) {
            // data sources for this chart are set to NEVER CACHE, so always a cache miss
            return null;
        }
        
        String hashKey = hashChart(cb,params);
        return getDiskResultFromDir(dir,hashKey);
    }
    
    public void putCurrent(ChartDiskResult res, String userId, ChartBundle cb, Map<String,String> params) {
//        String hashKey = hashChart(cb,params);
        File dir = getCurrentDir(userId);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long expiry = mostRecentDSExpiry(cb);
        storeExpiry(dir,expiry);
    }
    
    /**
     * flush the CURRENT chart and all its variants for this user
     * @param userId
     */
    public void invalidateCurrent(String userId) {
        File dir = getCurrentDir(userId);
        FileUtil.deepDelete(dir);
    }

    /**
     * for a given ChartBundle/params, prepare a ChartResult with the filepaths so that the
     * ChartDriver can materialize the chart directly into those files
     * 
     * @param cb
     * @param params
     * @return
     */
    public ChartDiskResult prepChartResult(ChartBundle cb, Map<String,String> params) {
        String hashKey = hashChart(cb,params);
        String chartPath = cb.getChartInfo().getId();
        File dir = getChartDir(chartPath);
        return prepDiskResult(dir,hashKey);
    }
    
    /**
     * for a given ChartBundle/params, prepare a ChartResult with the filepaths so that the
     * ChartDriver can materialize the chart directly into those files
     * 
     * @param cb
     * @param params
     * @return
     */
    public ChartDiskResult prepCurrent(String userId, ChartBundle cb, Map<String,String> params) {
        String hashKey = hashChart(cb,params);
        File dir = getCurrentDir(userId);
        return prepDiskResult(dir,hashKey);
    }
    
    /**
     * clear out the cache 
     * returns number of files deleted
     */
    public int clear() {
        int deleted = 0;
        if (cacheRoot.isDirectory()) {
            File[] cruft = cacheRoot.listFiles();
            for (File f : cruft) {
                deleted += FileUtil.deepDelete(f);
            }
            log.warn("cache root of '" + cacheRoot + "' cleared");
        }
        return deleted;
    }

    // JFreeChartDriver always writes out the PNG 
    // so we use the presence of the PNG file to detect if we have a cache 'hit' on disk
    private static final String PNG_SUFFIX = ".png";
    
    private ChartDiskResult prepDiskResult(File dir, String hashKey) {
        ChartDiskResult res = new ChartDiskResult();

        dir.mkdirs();
        File f = new File(dir, hashKey + PNG_SUFFIX);
        res.setImagePath(f.getAbsolutePath());
        File tnf = new File(dir, hashKey + "_t.png");
        res.setThumbPath(tnf.getAbsolutePath());
        File imf = new File(dir, hashKey + ".imap");
        res.setImageMapPath(imf.getAbsolutePath());
        File pdf = new File(dir, hashKey + ".pdf");
        res.setPdfPath(pdf.getAbsolutePath());
        File ps = new File(dir, hashKey + ".ps");
        res.setPSPath(ps.getAbsolutePath());
        File emf = new File(dir, hashKey + ".emf");
        res.setEMFPath(emf.getAbsolutePath());
        
        return res;
    }
    
    private ChartDiskResult getDiskResultFromDir(File dir, String hashKey) {
        if (!dir.exists())
            return null;
        
        ChartDiskResult res = new ChartDiskResult();
        
        File f = new File(dir, hashKey + PNG_SUFFIX);
        if (f.exists()) {
            res.setImagePath(f.getAbsolutePath());
        }
        File tnf = new File(dir, hashKey + "_t.png");
        if (tnf.exists()) {
            res.setThumbPath(tnf.getAbsolutePath());
        }
        
        File imf = new File(dir, hashKey + ".imap");
        if (imf.exists()) {
            res.setImageMapPath(imf.getAbsolutePath());
        }
        
        File pdf = new File(dir, hashKey + ".pdf");
        if (pdf.exists()) {
            res.setPdfPath(pdf.getAbsolutePath());
        }
        
        File ps = new File(dir, hashKey + ".ps");
        if (ps.exists()) {
            res.setPSPath(ps.getAbsolutePath());
        }
        
        File emf = new File(dir, hashKey + ".emf");
        if (emf.exists()) {
            res.setEMFPath(emf.getAbsolutePath());
        }
        
        if (f.exists() || tnf.exists() || imf.exists() || pdf.exists() || ps.exists() || emf.exists())
            return res;
       
        // if none of the files we're looking for exists, then return an empty ChartDiskResult
        return null;
        
    }
    /**
     * get the CURRENT area for this user
     * @param userId
     * @return
     */
    private File getCurrentDir(String userId) {
        String path = userId + File.separator + ".CURRENT"; 
        File dir = new File(getCacheRoot(),path);
        return dir;
        
    }
    
    private File getChartDir(String chartPath) {
        File dir = new File(getCacheRoot(),chartPath);
        return dir;
    }
    
    
    /**
     * given a ChartBundle, return the date that is the most recent DataSource expiry (TTL expires)
     * returns -1 if no expiry 
     * returns 0 if expiry is NOW   (i.e. always expires)
     * otherwise returns the milliseconds at which the expiry occurs
     * @param cb
     * @return
     */
    private long mostRecentDSExpiry(ChartBundle cb) {
        long res = 0;
        int minTTL = Integer.MAX_VALUE;
        for (DataSourceInfo ds : cb.getDataSources()) {
            int ttl = ds.getTTL();
            if (ttl < minTTL) {
                minTTL = ttl;
            }
        }
        if (minTTL == -1) {
            res = -1;
        } else if (minTTL == 0) {
            res = 0;
        } else {
            // TTL is in seconds.
            // as long as there is a TTL, calculate it with respect to now.
            res = System.currentTimeMillis() + 1000 * minTTL;  
        }
        return res;
    }
    /**
     * generate a hash key based on the chartbundle properties and the params
     * @param cb
     * @param params    - can be null
     * @return
     */
    private String hashChart(ChartBundle cb,  Map<String,String> params) {
        Hash h = new Hash();
        SimpleProps p = cb.getCacheableProps();
        h.update(p.toString());
        if (params != null) {
            for (Map.Entry<String,String> e : params.entrySet()) {
        
                h.update(e.getKey());
                String val = e.getValue();
                if (val != null) {
                    h.update(val);
                }
            }
        }
        return h.toHexString();
    }
    
    /**
     * get the expiry value for the chart at this dir.  if none exists, return -1
     * @param dir
     * @return
     */
    private long getExpiry(File dir) {
        //FIXME
        File f= new File(dir,EXPIRY);
        if (f.exists()) {
            String s = FileUtil.readAsString(f);
            try {
                Long l = Long.parseLong(s);
                return l.longValue();
            } catch (NumberFormatException nfe ) {
                log.warn("expiry file at " + f.getAbsolutePath() + " had unexpected value of : " + s);
                return -1;
            }
        } 
        
        log.warn("expiry file at " + f.getAbsolutePath() + " is missing!");
        return -1;
    }
    
    // a special file called expiry in the directory holds the time in msecs of the expiry
    private static final String EXPIRY = "expiry";
    private void storeExpiry(File dir, long expiry) {
        File f = new File(dir,EXPIRY);
        FileUtil.writeString(f, String.valueOf(expiry));
    }
}
