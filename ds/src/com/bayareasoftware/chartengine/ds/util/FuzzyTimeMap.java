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
package com.bayareasoftware.chartengine.ds.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FuzzyTimeMap<V>
 implements java.io.Serializable {
    
    /* FUZZY TIME TOLERANCES
     */ 
    public static final long SECOND = 1000;
    public static final long MINUTE = 61 * SECOND;
    public static final long HOUR = 60 * MINUTE + SECOND;
    public static final long DAY = 24 * HOUR + SECOND;
    public static final long WEEK = 7 * DAY + SECOND;
    public static final long MONTH = 31 * DAY + SECOND;
    
    private TreeMap<Long,V> map = new TreeMap<Long,V>();
    private long tolerance;
    public FuzzyTimeMap(long tolerance) {
        this.tolerance = tolerance;
    }

    public Set<Long> keySet() {
        return map.keySet();
    }
    public int size() { return map.size(); }
    // implement ourselves for now to stick to JDK 1.5.  
    // Can replace with TreeMap.floorEntry when we fully migrate to JDK 1.6
    // floorEntry returns the key-value mapping associated with the greatest
    // key less than or equal to the given key or null
    private Map.Entry<Long,V> floorEntry(TreeMap<Long,V> map, Long key) {
        Map.Entry<Long, V> ret = null;
        for (Map.Entry<Long,V> entry : map.entrySet()) {
            if (entry.getKey() <= key) {
                ret = entry;
            } else {
                // since entrySet returns keys in ascending order,
                // once we exceed the key we are looking for, we are done
                break;
            }
        }
        return ret;
    }

    // implement ourselves for now to stick to JDK 1.5.  
    // Can replace with TreeMap.ceilingEntry when we fully migrate to JDK 1.6
    // ceilingEntry returns the key-value mapping associated with the least
    // key less equal to the given key or null
    private Map.Entry<Long,V> ceilingEntry(TreeMap<Long,V> map, Long key) {
        Map.Entry<Long, V> ret = null;
        for (Map.Entry<Long,V> entry : map.entrySet()) {
            if (entry.getKey() >= key) {
                // since entrySet returns keys in ascending order,
                // the first key that exceeds or equals the key is all we need
                ret = entry;
                break;
            }
        }
        return ret;
    }

    
    public V get(Date d) {
        long time = d.getTime();
        V ret = null;
        //return map.get(d.getTime());
        long floorVal = Long.MIN_VALUE;
        long ceilVal = Long.MAX_VALUE;
        //       Map.Entry<Long, V> floor = map.floorEntry(time);
        Map.Entry<Long, V> floor = floorEntry(map,time);
        //Map.Entry<Long, V> ceiling = map.ceilingEntry(time);
        Map.Entry<Long, V> ceiling = ceilingEntry(map,time);
        if (floor != null) {
            floorVal = floor.getKey();
        }
        if (ceiling != null) {
            ceilVal = ceiling.getKey();
        }
        long floorDelta = time - floorVal;
        long ceilDelta = ceilVal - time;
        if (floorDelta < ceilDelta) {
            if (floorDelta <= tolerance) {
                if (floor != null) {
                    ret = floor.getValue();
                }
            }
        } else {
            if (ceilDelta <= tolerance) {
                if (ceiling != null) {
                    ret = ceiling.getValue();
                }
            }
        }
        return ret;
    }
     
    public void put(Date d, V value) {
        map.put(d.getTime(), value);
    }
     
    public V remove(Date d) {
        return map.remove(d.getTime());
    }
     public static void main(String[] a) throws Exception {
         
//        TreeMap<Long, Double> map = new TreeMap<Long, Double>();
//        map.put(8L, 8D);
//        map.put(12L, 12D);
//        Map.Entry<Long, Double> floor = map.floorEntry(10l);
//        Map.Entry<Long, Double> ceiling = map.ceilingEntry(10l);
//        p("floor=" + floor.getKey() + "/" + floor.getValue());
//        p("ceiling=" + ceiling.getKey() + "/" + ceiling.getValue());

    }

    private static void p(String s) {
        System.out.println(s);
    }
}
