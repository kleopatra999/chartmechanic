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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.ChartBundle;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.util.FileUtil;
import com.bayareasoftware.chartengine.util.Hash;

/**
 * ChartCache encapsulates the caching of charts.
 * ChartCache is filled by the JFreeChartDriver when charts are created
 * and is accessed by the REST APIs to serve up the images
 *
 * the ChartCache is an LRU style cache, where periodically we check for older (not accessed) entries
 * and remove them.  When we flush a cache entry from the ChartResult, we also remove the files on disk
 * 
 *  the implementation of ChartCache is a two-level cache
 *      the first key is the ChartInfo ID
 *      the second key is the hash formed by chart properties and parameters
 *      
 *  the strategy is to keep the per-chart entries at a reasonable size by maintaining a LRU discipline
 *  in this way, we hope to prune the 'referenced-only-once' garbage entries formed by the user tweaking
 *  properties in the ChartBuilder.  At the same time, by keeping a reasonable number of entries present
 *  we support a few legitimate 'variants' of the same chart (e.g. different time parameters) 
 * 
 * Note that the cache itself is purely an in-memory structure.  The elements in our cache are ChartResult objects
 * which have file paths. 
 * 
 * 
 * 
 */
@Deprecated
public class ChartCache {
    private static final Log log = LogFactory.getLog(ChartCache.class);
    
    /**
     * the root directory on disk where the the ChartResults should have their paths set
     *  
     */
    private File cacheRoot;
    
    /**
     * the directory where the anonymous user charts are stored
     */
    private File anonDirRoot;
    
    // all the anonymous chart session charts are stored in ANONYMOUS_CACHEDIR
    private final static String ANONYMOUS_CACHEDIR = "ANONYMOUS";
    
    
    private static int INITIAL_CAPACITY = 10000;
    
    // aim is to keep the per-chart entries to a limited number so 
    // we don't have too many 'unreferenced' garbage entries in the per-chart hashmap
    // unreferenced chartresults come as a result of 
    private static int lru_size = 10;
    
    public static class CacheStat {
        public String id; // id of the chart;
        public long accesses; // how many accesses to this chart id
        public Date last; // last accessed time
        public int numVariants; // number of variants of this chart that differ in their properties/params
        public CacheStat (String id, ChartEntries entry) {
            this.id = id;
            this.accesses = entry.accesses;
            this.last = new Date(entry.lastAccessed);
            this.numVariants = entry.map.size();
            
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public long getAccesses() {
            return accesses;
        }
        public void setAccesses(long accesses) {
            this.accesses = accesses;
        }
        public Date getLast() {
            return last;
        }
        public void setLast(Date last) {
            this.last = last;
        }
        public int getNumVariants() {
            return numVariants;
        }
        public void setNumVariants(int numVariants) {
            this.numVariants = numVariants;
        }
    }
    
    private static class ChartEntries {
        public long accesses;
        public long lastAccessed;
        public LinkedHashMap<String,ChartResult> map;
        
        // soonest expiry of datasources for this chart.  if -1, never expires.  if 0, always expires
        public long dsExpiry; 
        
        public ChartEntries(long expiry) {
            this.dsExpiry = expiry;
            this.accesses = 0;
            map = new LinkedHashMap<String,ChartResult>(10,0.75f,true) {
                // since this is a access-order LinkedHashMap
                // eldest means least recently accessed map
                // so if we grow to PER_CHART_LRU_SIZE entries, we remove the LRU entry
                protected boolean removeEldestEntry(Map.Entry<String,ChartResult> eldest) {
                    if (size() > lru_size){
//                        log.warn("removing eldest entry due to size: " + size() + " exceeding LRU size of " + PER_CHART_LRU_SIZE + " chartresult:" + eldest.getValue());
                        flushEntry(eldest.getValue());
                        return true;
                    }
                    else
                        return false;
                }
            };
        }
        
        public long getDataExpiry() {
            return this.dsExpiry;
        }
        
        public ChartResult get(String key) {
            accesses++;
            lastAccessed = System.currentTimeMillis();
            ChartResult res = map.get(key);
            return res;
        }
        public void put(String key, ChartResult cr) {
            accesses++;
            lastAccessed = System.currentTimeMillis();
            map.put(key, cr);
        }
        
        // flush all the ChartResults from disk 
        public void flushFromDisk() {
           for (ChartResult cr : map.values()) {
               flushEntry(cr);
           }
        }
        
        private void flushEntry(ChartResult cr) {
//            removeFile(cr.getImagePath());
//            removeFile(cr.getThumbPath());
//            removeFile(cr.getImageMapPath());
        }
    
        private void removeFile(String s) {
            if (s != null)
                new File(s).delete();
        }

    }

    /**
     * the cache is a linked hash map of ChartInfo ID -> ChartEntries
     * the ChartEntries is itself a map of chart hashes -> ChartResults
     */
    private LinkedHashMap<String,ChartEntries> cache;
    
    /*public*/ private ChartCache(File cacheRoot) {
        this.cacheRoot = cacheRoot;
        makeAnonDir();
        
        // start with an initial size of 10,000 in the top-level hashmap, but let it 
        // grow unbounded
        this.cache = new LinkedHashMap<String,ChartEntries>(INITIAL_CAPACITY,0.75f,true);
    }
    
    private void makeAnonDir() {
        anonDirRoot = new File(cacheRoot,ANONYMOUS_CACHEDIR);        
        anonDirRoot.mkdirs();
    }
    
    public File getCacheRoot() { return cacheRoot; }
    public void setCacheRoot(File f) {
        cacheRoot = f;
        cacheRoot.mkdirs();
        makeAnonDir();
    }

    public File getAnonDirRoot() {
        return anonDirRoot;
    }
    
    public void setLRUsize(int s) {
        lru_size = s;
    }
    public int getLRUsize() {
        return lru_size;
    }
    
    /** 
     * return a list of information about last N charts accessed,
     * 
     * will take linear time so as cache grows larger, this will be more expensive
     * 
     * @param n - if n is larger than existing cache size, just return all IDs
     * @return a list of CacheStats
     */
    public synchronized List<CacheStat> getLastN(int n) {
        List<CacheStat> result = new ArrayList<CacheStat>();
        
        // since the the linkedhashmap is access-ordered from least-recent to most-recent
        
        int start = cache.size() - n;  
        if (start < 0)
            start = 0;

        Set<Map.Entry<String,ChartEntries>> entries = cache.entrySet();
        Iterator<Map.Entry<String,ChartEntries>> iter = entries.iterator();
        
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry<String, ChartEntries> entry = iter.next();
            if (i >= start) {
                result.add(new CacheStat(entry.getKey(), entry.getValue()));
            }
            i++;
        }

        // reverse the results so we have the most recent one first
        Collections.reverse(result);
        
        return result;
    }
    
     /**
     * look in the cache to see if the chart exists
     * if it does, return the ChartResult, which contains the filename of the .PNG
     * else return null 
     */ 
    public synchronized ChartResult getImage(ChartBundle cb, 
                                             HashMap<String,String> params) {
        
//        log.warn("********************* getImage called with chart id = " + cb.getChartInfo().getId() );
        
        String id = cb.getChartInfo().getId();
        ChartEntries entries = cache.get(id);
        if (entries != null) {
            
            long expiry = entries.getDataExpiry();
            
            long now = System.currentTimeMillis();
            if (now > expiry && expiry != -1) {
                // we've passed the expiry. don't use the cache, but instead flush it.
//                log.warn("################ now is greater than expiry of : " + expiry + " don't use cache but flush it instead");
                this.invalidateImagesByID(id);
                return null;
            }

            String hashKey = hashChart(cb,params);
            ChartResult result = entries.get(hashKey);
            
//           if (result != null)
//               log.warn("ChartResult.getImage ############# HIT, getImage called with chart id = " + cb.getChartInfo().getId() + " params = " + params +
//                        " hashKey = " + hashKey + " returning result with image path = " + result.getImagePath());
//           else
//              log.warn("ChartResult.getImage ############# MISS, getImage called with chart id = " + cb.getChartInfo().getId() + " params = " + params +
//                        " hashKey = " + hashKey + " MISSED");

            return result;

        }
//        log.warn("#############  MISS getImage called with chart id = " + cb.getChartInfo().getId() + ".  No entries found for that id");
        return null;
    }
    

    /**
     * flush the entries with the specified chart id
     * @param id
     */
    public synchronized void invalidateImagesByID(String id) {
        ChartEntries entries = cache.get(id);
        if (entries != null) {
            entries.flushFromDisk();
            cache.remove(id);
//            log.warn("removed images related to chart with id: " + id + " from cache");
        }
    }
    
  
    
    /**
     * flush all the entries whose ids start with prefix
     * this is a temporary kludge to flush charts by a given author
     * probably need to restructure the cache to hash by author
     * @param prefix
     */
    public synchronized void invalidateImagesByIdPrefix(String prefix) {
        List<String> deadList = new ArrayList<String>();
        for (String key : cache.keySet()) {
            if (key.startsWith(prefix)) {
                deadList.add(key);
            }
        }
        
        log.warn("Invalidating " + deadList.size() + " charts that start with " + prefix); 
        for (String id : deadList) {
            invalidateImagesByID(id);
        }
    }
    
    /**
     * fill the cache by adding a chart
     * if the chart has a most recent data expiry of 0, that means one or more of its data source is set to never cache
     * in that case, we don't add the chart to the cache since it doesn't make any sense to do so
     * 
     * @param cr
     * @param cb
     * @param params - the chart params (can be null)
     * @return the key that will find the chartresult again
     */
    public synchronized void putChart(ChartResult res, ChartBundle cb, Map<String,String> params) {
        String hashKey = hashChart(cb,params);
        
        String chartId = cb.getChartInfo().getId();
        ChartEntries entries = cache.get(chartId);
        if (entries == null) {
            long expiry = mostRecentDSExpiry(cb);
            if (expiry == 0) {
//                log.warn("############ putChart is not adding chart with chartId = " + chartId + " because it contains data source that is set to NEVER CACHE");
                return;
            }
            entries = new ChartEntries(expiry);
//            log.warn("############## putChart called with chartId = " + chartId + " most recent expiry = " + expiry);
        }
        
//        log.warn("ChartCache.putChart ################# putChart called with cb = " + cb + "\nchartId = " + chartId + " params = " + params +
//                 " hashKey = " + hashKey + " adding res with image path:" + res.getImagePath());
        entries.put(hashKey,res);
        cache.put(chartId,entries);
        //return hashKey;
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
     * for a given ChartBundle, prepare a ChartResult with the filepaths so 
     * that the ChartDriver can materialize the chart directly into those files instead of
     * materializing them in-memory
     * 
     * workspace is an optional directory to further organize place the chartresult files
     * 
     */
/*
    public ChartResult prepChartResult(ChartBundle cb, Map<String,String> params, String workspace) {
        ChartResult res = new ChartResult();
        String hashKey = hashChart(cb,params);
        
        File dir;
        if (workspace != null && workspace.trim() != null) {
            dir = new File(getCacheRoot(),workspace);
        } else
            dir = getCacheRoot();

        dir.mkdirs();
        
        // note that the image path is named .png but may actually be the image in a different
        // format, such as PDF or PS
        File f = new File(dir, hashKey + ".png");
        res.setImagePath(f.getAbsolutePath());
        File tnf = new File(dir, hashKey + "_t.png");
        res.setThumbPath(tnf.getAbsolutePath());
        File imf = new File(dir, hashKey + ".imap");
        res.setImageMapPath(imf.getAbsolutePath());
        
        return res;
    }
*/

    
   
    
    /**
     * for a given ChartBundle, prepare a ChartResult with the filepaths so 
     * that the ChartDriver can materialize the chart directly into those files instead of
     * materializing them in-memory
     * 
     * this is for an anonymous user, so we store the the files under the anonId directory in the cache root
     * and then the CMSessionListener will remove those files once the session is invalidated
     *
     * also, since we are assuming there is only one chart being worked on, we do not hash the chart results, but simply
     * use the anonID
     */
    /*
    public ChartResult prepAnonymousChartResult(String anonId) {
        ChartResult res = new ChartResult();
        File dir = getAnonDirRoot();
        
        // note that the image path is named .png but may actually be the image in a different
        // format, such as PDF or PS
        File f = new File(dir, anonId + ".png");
        res.setImagePath(f.getAbsolutePath());
        File tnf = new File(dir, anonId + "_t.png");
        res.setThumbPath(tnf.getAbsolutePath());
        File imf = new File(dir, anonId + ".imap");
        res.setImageMapPath(imf.getAbsolutePath());
        
        return res;
    }*/

    /**
     * invalidate the chart in the cache referred to by the anonID 
     * @param anonID
     */
    public void invalidateByAnonymousID(String anonID) {
        File dir = getAnonDirRoot();
        File f = new File(dir, anonID + ".png");
        f.delete();
        File tnf = new File(dir, anonID + "_t.png");
        tnf.delete();
        File imf = new File(dir, anonID + ".imap");
        imf.delete();
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
        } else {
            if (!cacheRoot.mkdirs()) {
                log.error("cache root of " + cacheRoot + " could not be created!");
            }
        }
        cache.clear();
        return deleted;
    }
    
    /**
     * generate a hash key based on the chartbundle properties and the params
     * @param cb
     * @param params    - can be null
     * @return
     */
    private String hashChart(ChartBundle cb,  Map<String,String> params) {
        Hash h = new Hash();
        h = hashChartBundle(h,cb);
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

    private Hash hashChartBundle(Hash h, ChartBundle cb) {
        SimpleProps p = cb.getCacheableProps();
        h.update(p.toString());
        return h;
    }

//    private static Hash updateIfNotNull(Hash h, String s) {
//        if (s != null) h.update(s);
//        return h;
//    }
}
