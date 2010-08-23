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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * an InfoHeap maps a set of UI IDs (possible transient) to
 * persistent IDs (positive IDs from the DB or serverpaths for the FS store)
 *
 */
@SuppressWarnings("serial")
public class InfoHeap implements Serializable {

    private HashMap<String,String> heap = new HashMap<String,String>();
    
    public String put(String id, String entityId) {
        return heap.put(id,entityId);
    }
    public String get(String id) {
        return heap.get(id);
    }
    
    public int getNumIDs() {
        return heap.size();
    }
    
    /**
     * refresh the IDs
     * returns the number of IDs refreshed
     * @param cb
     * @return
     */
    public int refreshIDs(Object obj) {
        int res = 0;
       if (obj instanceof ChartBundle) {
           ChartBundle cb = (ChartBundle)obj;
           String newId;
           ChartInfo chartInfo = cb.getChartInfo();
           newId = get(chartInfo.getId());
           if (newId != null) {
               chartInfo.setId(newId);
               res++;
           }
           
           List<SeriesDescriptor> series = chartInfo.getSeriesDescriptors();
           for (SeriesDescriptor sd : series) {
               newId = heap.get(sd.getSource());
               if (newId != null) {
                   sd.setSource(newId);
                   res++;
               }
           }
           List<MarkerDescriptor> markers = chartInfo.getMarkers();
           for (MarkerDescriptor md : markers) {
               newId = heap.get(md.getSource());
               if (newId != null) {
                   md.setSource(newId);
                   res++;
               }
           }

           DataSourceInfo[] dsa = cb.getDataSources();
           for (int i = 0; i < dsa.length; i++) {
               newId = heap.get(dsa[i].getId());
               if (newId != null) {
                   dsa[i].setId(newId);
                   res++;
               }
           }
       } else if (obj instanceof DataSourceInfo) {
           DataSourceInfo ds = (DataSourceInfo)obj;
           String newId = heap.get(ds.getId());
           if (newId != null)
               ds.setId(newId);
           res++;
       } else if (obj instanceof BuilderContext) {
           BuilderContext bc = (BuilderContext)obj;
           List<String> newIds = new ArrayList<String>();
           for (String s : bc.getIds()) {
               String newId = heap.get(s);
               if (newId != null) {
                   newIds.add(newId);
               } else {
                   newIds.add(s);
               }
           }
           bc.setIds(newIds);
       }
       
       return res;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : heap.keySet()) {
            sb.append(key).append("=>").append(heap.get(key)).append(";");
        }
        return sb.toString();
    }
    
}
