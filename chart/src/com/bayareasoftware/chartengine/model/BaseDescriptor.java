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

import com.bayareasoftware.chartengine.model.event.PropChangeSource;

/**
 * the common base class for chart elements (series/markers)
 * 
 * pool common code here so we don't have duplication of code in SeriesDescriptor and MarkerDescriptor
 */
public class BaseDescriptor extends PropChangeSource implements PropertySerializable {

    public static final String PROP_source="id";
    public static final String PROP_sid="sid";
    
    public static final String PROP_func="func";
    public static final String PROP_args="args";
    
    public static final String PROP_axisIndex = "axisIndex";
    public static final String PROP_visible = "visible";

    public static final String PROP_name="name";
    
    public static final String ARGS_PREFIX="arg";
    
    public static String PROP_datascript = "datascript";
    private String datascript;
    
    private static final int DEFAULT_AXISINDEX = 0;
    private static final boolean DEFAULT_VISIBLE = true;

    
    /**
     * name of the function
     */
    private String func;
    private List<Arg> args = new ArrayList<Arg>();

    /**
     * points to the source path (a datasource) that is the source for this descriptor 
     */
    private String id;
    
    /**
     * the sid is a identifier for a series descriptor that 
     * is UNIQUE and PERMANENT within a ChartInfo
     */
    private Integer sid;

    private boolean visible = DEFAULT_VISIBLE;
    
    private int axisIndex = DEFAULT_AXISINDEX;
    
    /**
     * displayed name of this descriptor.  Not necessarily unique within a chart
     */
    private String name;

    public BaseDescriptor() {
    }

    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = false;
        int argCount = SerializeUtil.deserializeListSize(p, prefix, ARGS_PREFIX);
        args.clear();
        for (int i=0;i<argCount;i++) {
            args.add(SerializeUtil.deserializeListElement(new Arg(),p,prefix,ARGS_PREFIX,i));
            ret = true;
        }
        
        String key = SimpleProps.prefixIt(prefix,PROP_func);
        if (p.containsKey(key)) {
            this.func = p.get(key);
            p.remove(key);
            ret = true;
        }
        
        key = SimpleProps.prefixIt(prefix,PROP_datascript);
        if (p.containsKey(key)) {
            this.datascript = p.get(key);
            p.remove(key);
            ret = true;
        }
        
        key = SimpleProps.prefixIt(prefix,PROP_visible);
        if (p.containsKey(key)) {
            setVisible(Boolean.parseBoolean(p.get(key)));
            p.remove(key);
            ret = true;
        }
        
        key = SimpleProps.prefixIt(prefix,PROP_axisIndex);
        if (p.containsKey(key)) {
            setAxisIndex(Integer.parseInt(p.get(key)));
            p.remove(key);
            ret = true;
        }

        key = SimpleProps.prefixIt(prefix,PROP_name);
        if (p.containsKey(key)) {
            setName(p.get(key));
            p.remove(key);
            ret = true;
        }

        
        key = SimpleProps.prefixIt(prefix,PROP_sid);
        if (p.containsKey(key)) {
            setSid(Integer.parseInt(p.get(key)));
            p.remove(key);
            ret = true;
        }

        key = SimpleProps.prefixIt(prefix,PROP_source);
        if (p.containsKey(key)) {
            setSource(p.get(key));
            p.remove(key);
            ret = true;
        }

        return ret;
    }

    public SimpleProps serializeToProps(SimpleProps p, String prefix) {

        if (p == null)
            p = new SimpleProps();

        
        // for all properties, only serialize if they differ from default values
        // SimpleProps.set() already handles the case where values are null strings
        // check explicitly in the case of the integer/double values
        
        p.set(prefix,PROP_func,getFunc());
        p.set(prefix,PROP_datascript,getDataScript());
        p.set(prefix,PROP_source,getSource());
        p.set(prefix,PROP_name,getName());
      

        if (getSid() != null)
            p.set(prefix,PROP_sid,getSid().toString());
        
        if (args.size() > 0) {
            p = SerializeUtil.serializeList(p, prefix, args, ARGS_PREFIX);
        }
        
        if (getAxisIndex() != DEFAULT_AXISINDEX)
            p.set(prefix,PROP_axisIndex,String.valueOf(getAxisIndex()));

        if (isVisible() != DEFAULT_VISIBLE)
            p.set(prefix,PROP_visible,String.valueOf(isVisible()));
        
        return p;
    }

    public int hashCode() {
        int ret = -1;

        ret ^= axisIndex;

        if (this.func != null)
            ret ^= func.hashCode();
        if (this.datascript != null) 
            ret ^= datascript.hashCode();
        if (this.args != null)
            ret ^= args.hashCode();
        if (this.id != null)
            ret ^= id.hashCode();
        if (this.sid != null) 
            ret ^= sid.hashCode();
        if (this.name != null)
            ret ^= name.hashCode();

        ret += ( visible ? 1 : 0);
        return ret;
    }


    @Override
    public boolean equals (Object o) {
        if (o == null)
            return false;

        if (! (o instanceof BaseDescriptor))
            return false;

        BaseDescriptor other = (BaseDescriptor)o;

        if (this.args == null) {
            if (other.args != null)
                return false;
        } else {
            if (other.args == null)
                return false;

            if (this.args.size() == other.args.size()) {
                for (int i=0;i<this.args.size();i++) {
                    if (! args.get(i).equals(other.args.get(i))) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

        return objEquals(this.func, other.func) &&
               objEquals(this.datascript,other.datascript) &&
               objEquals(this.id, other.id) &&
               objEquals(this.sid, other.sid) &&
               objEquals(this.name,other.name) &&
               this.axisIndex == other.axisIndex &&
               this.visible == other.visible;
    }

    // check if both objs are equal, taking into account nulls
    public static boolean objEquals(Object o1, Object o2) {
        if (o1 == null) {
            return (o2 == null);
        } 
        return o1.equals(o2);
    }
    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        String old = this.func;
        this.func = func;
        fireChange(PROP_func,old,this.func);
    }

    
    public String getDataScript() {
        return this.datascript;
    }

    public void setDataScript(String datascript) {
        String old = this.datascript;
        this.datascript = datascript;
        fireChange(PROP_datascript,old,this.datascript);
    }
    
    public List<Arg> getArgs() {
        return args;
    }

    public void setArgs(List<Arg> args) {
        List<Arg> old = this.args;
        this.args = args;
        fireChange(PROP_args,old,this.args);

    }

    public void addArg(Arg a) {
        args.add(a);
        fireChange(PROP_args,null,this.args);

    }

    public String getSource() {
        return id;
    }

    public void setSource(String source) {
        String old = this.id;
        this.id = source;
        this.fireChange(PROP_source,old,this.id);
    }
    
    public Integer getSid() {
        return this.sid;
    }
    
    public void setSid(Integer i) {
        Integer old = this.sid;
        this.sid = i;
        fireChange(PROP_sid, old, this.sid);
    }
    
    public boolean isVisible() { 
        return visible; 
    }
    
    public void setVisible(boolean b) {
        boolean old = visible;
        visible = b;
        fireChange(PROP_visible, new Boolean(old), new Boolean(visible));
    }
    
    public int getAxisIndex() {
        return axisIndex;
    }
    public void setAxisIndex(int axisIndex) {
        int old = this.axisIndex;
        this.axisIndex = axisIndex;
        this.fireChange(PROP_axisIndex,old,this.axisIndex);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        this.fireChange(PROP_name, old, this.name);
    }
    
    public String toString() {
        SimpleProps p = serializeToProps(null,null);
        return p.toString();
    }

    /**
     * does this object depend on src
     * 
     * note we are NOT dependent on ourselves
     * @param src
     * @return
     */
    public boolean dependsOn(BaseDescriptor src) {
        boolean ret = false;
        
        if (src != null) {
            Integer srcSid = src.getSid();
            if (srcSid != null && ! srcSid.equals(this.sid)) {
                for (Arg arg : this.getArgs()) {
                    if (ArgType.SID.equals(arg.getArgType())) {
                        if (srcSid.toString().equals(arg.getArgValue())) {
                            ret = true;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }
    
    /**
     * returns true if this object has any dependencies on any other descriptors
     * @return
     */
    public boolean hasDependency() {
        boolean ret = false;   
        if (getFunc() != null) {
            for (Arg arg : this.getArgs()) {
                if (ArgType.SID.equals(arg.getArgType())) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    
}