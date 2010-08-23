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
/**
 * 
 */
package com.bayareasoftware.chartengine.model.types;

import java.io.Serializable;

import com.bayareasoftware.chartengine.model.BaseInfo;

public class PropInfo implements Serializable {
    
    private String type, defalt;
    private String name;

    public PropInfo() { }
    public PropInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    } 
    
    public String toString() {
        String ret = "[PropInfo: " + name + " type=" + type;
        if (defalt != null) {
            ret += " default='" + defalt + "'";
        }
        return ret + "]";
    }
    
    public String getDefault() { return defalt; }
    public void setDefault(String s) { defalt = s; }
    public int hashCode() {
        if (defalt == null) {
            return name.hashCode() ^ type.hashCode();
        }
        return name.hashCode() ^ type.hashCode() ^ defalt.hashCode();
    }
    public boolean equals(Object o) {
        if (o == null || !(o instanceof PropInfo)) {
            return false;
        }
        PropInfo other = (PropInfo) o;
        boolean ret = name.equals(other.getName())
        && type.equals(other.getType());
        if (!ret) { return false; }
        if (defalt == null) { return other.defalt == null; }
        return defalt.equals(other.defalt);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}