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
import java.util.ArrayList;

public class ChartBeanInfo implements Serializable {
    private String classname, supername, displayname;
    private ArrayList<PropInfo> props = new ArrayList<PropInfo>();
    private boolean isAbstract;
    private RendererDocInfo renderDoc;
    
    public boolean isAbstract() { return isAbstract; }
    public void setAbstract(boolean b) { isAbstract = b; }
    public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getSupername() {
        return supername;
    }
    public void setSupername(String supername) {
        this.supername = supername;
    }
    
    public String getDisplayname() {
        if (displayname != null) {
            return displayname;
        }
        //System.err.println("displayname is null for '" + classname + "'");
        return getClassname();
    }
    public void setDisplayname(String s) { displayname = s; }
    public PropInfo[] getProps() {
        PropInfo[] ret = null;
        ret = new PropInfo[props.size()];
        /*
        for (int i = 0; i < props.size(); i++) {
            ret[i] = (PropInfo) props.get(i);
        }*/
        props.toArray(ret);
        return ret;
    }
    
    public PropInfo findProp(String name) {
        PropInfo[] ps = getProps();
        for (int i = 0; i < ps.length; i++) {
            if (name.equals(ps[i].getName())) {
                return ps[i];
            }
        }
        return null;
    }
    public void addProp(PropInfo pi) {
        props.add(pi);
    }
    
    public void setProps(PropInfo[] pis) {
        props.clear();
        for (int i = 0; pis != null && i < pis.length; i++) {
            props.add(pis[i]);
        }
    }
    public RendererDocInfo getRenderDoc() { return renderDoc; }
    public void setRenderDoc(RendererDocInfo rdi) {
        renderDoc = rdi;
    }
    public String toString() {
        return "[ChartBean: " + getClassname() + " display=" + getDisplayname() + "]";
    }
}