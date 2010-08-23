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

/***
 * BuilderContext encapsulates the current working set and state of the ChartBuilder UI
 * used so that the user can go back to their previous working state easily
 * 
 * keep track of a list of String ids
 */
@SuppressWarnings("serial")
public class BuilderContext implements PropertySerializable {

    public List<String> ids;
    
    public BuilderContext() {
        this.ids = new ArrayList<String>();
    }
    
    public List<String> getIds() {
        return ids;
    }
    
    public void add(String id) {
        ids.add(id);
    }
    
    // if oldId exists in the list, replace it with the new Id
    public void setIds(List<String> newIds) {
        ids = newIds;
    }
    
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (! (o instanceof BuilderContext) )
            return false;
        
        BuilderContext other = (BuilderContext)o;
        if (ids.size() != other.ids.size()) {
            return false;
        }
        
        for (int i = 0; i < ids.size(); i++) {
            if (!(ids.get(i).equals(other.ids.get(i))))
                        return false;
        }
        return true;
    }
    
    public int hashCode() {
        return ids.hashCode();
    }
    
    private static String PREFIX = "builder";
    
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        int n = SerializeUtil.deserializeListSize(p, prefix, PREFIX);
        
        for (int i=0;i<n;i++) {
            // <prefix>.<lstPrefix>.<i>.id = <value>;
            String id = p.get(SimpleProps.prefixIt(prefix,SimpleProps.prefixIt(PREFIX,String.valueOf(i))),
                              BaseInfo.PROP_ID);
            ids.add(id);
        }
        return (n > 0);
    }

    /**
     * remove any ids that are temporary and transient
     */
    public void removeTransientIDs() {
        List<String> newIds = new ArrayList<String>();
        for (String id : ids) {
            if (!BaseInfo.isTempID(id)) {
                newIds.add(id);
            }
        }
        this.ids = newIds;
    }
    
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        if (p == null)
            p = new SimpleProps();
        
        if (ids != null && ids.size() > 0) {
            p.set(prefix,
                    SimpleProps.prefixIt(PREFIX,SerializeUtil.LIST_SIZE_KEY),
                    String.valueOf(ids.size()));
            for (int i=0;i<ids.size();i++) {
                // serialize just the ID of the member of each list
                // <prefix>.<lstPrefix>.<i>.id = <value>;
                String id = ids.get(i);
                p.set(SimpleProps.prefixIt(prefix,SimpleProps.prefixIt(PREFIX,String.valueOf(i))),
                      BaseInfo.PROP_ID,
                      id);
            }
        }
        return p;
    }

    public String toString() {
        return serializeToProps(null,null).toString();
    }
}
