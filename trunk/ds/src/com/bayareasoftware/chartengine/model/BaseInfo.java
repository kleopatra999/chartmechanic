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
import java.util.Iterator;
import java.util.List;

import com.bayareasoftware.chartengine.model.event.PropChangeSource;

/**
 * the base class for our model objects
 * Every model object contains a set of properties
 * the Base properties that all models have are
 *      String name
 *      String description
 *      
 * BaseInfo also handles property changes by firing events when properties change
 *
 */
public class BaseInfo extends PropChangeSource implements Serializable, PropertySerializable, StandardProps {
    
    protected SimpleProps props = new SimpleProps();
    /**
     * unique database ID
     */
    protected String id;
    public static final String PROP_ID = "id";
    
    // when multiple properties are changed at once (e.g. via a setProperties())
    // then the name of event fired is PROP_PROPERTIES
    public static final String PROP_PROPERTIES = "properties";
    
    public static final String PROP_PERSIST_STATE = "persistState";
    /**
    // a BaseInfo can have a persistState of UNMODIFIED, MODIFIED, or NEWLY_CREATED 
     * 
     * UNMODIFIED means this is a persistent object that has been unmodified since it was loaded
     * MODIFIED means the object has been modified with some client changes
     * NEWLY_CREATED means this is a new object that has not yet been persisted
     */
    public static final int UNMODIFIED = 0;
    public static final int MODIFIED = 1;
    public static final int NEWLY_CREATED = 2;
    
    // the persistState is only used in the client, not in the server, and is not serialized across the wire
    transient private int persistState = UNMODIFIED;
    
    public BaseInfo() {
        Integer id = IdGeneratorFactory.nextId();
        if (id != null) {
            //setId(id.toString());
            // don't use setId() here as we don't want to fire a change event for the object constructor 
            this.id = id.toString();
        }
    }

    
    /**
     * @return null if object has no Id 
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        String old = this.id;
        this.id = id;
        this.fireChange(PROP_ID,old, this.id);
    }
    
    public String getDescription() {
        return getProperty(DESCRIPTION);
    }
    
    public void setDescription(String description) {
        setProperty(DESCRIPTION,description);
    }
    
    /**
     * set the property. fires a change event
     * 
     * @param key must be non-null
     * @param value if value is null, remove the property
     */
    public void setProperty(String key, String value) {
        if (props == null)
            props = new SimpleProps();
        
        if (key != null) {
            Object oldValue = props.get(key);
            if (value != null && value.length() > 0) {
                props.put(key,value);
                if (props.getPropChangeSource() != this) {
                    // don't fire the same event twice if the props change is the same as this
                    this.fireChange(key,oldValue,value);
                }
            } else {
                props.remove(key);
                if (props.getPropChangeSource() != this) {
                    // don't fire the same event twice if the props change is the same as this
                    this.fireChange(key,oldValue,null);
                }
            }
        }
    }

    public String getProperty(String key) {
        if (props == null)
            return null;
        
        return props.get(key);
    }
    
  
    /**
     * returns the properties as an int
     * if the property can't be parsed as an int, returns the defaultValue
     * @return
     */
    public int getPropertyAsInt(String key, int defaultValue) {
        if (props == null)
            return defaultValue;
        
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
    
    /**
     * returns the properties as a string
     * if props is null, returns ""
     * 
     * use with caution.  this string representation of the properties has as
     * its representation newline-delimited <key>=<value> paris
     * @return
     */
    public String propertiesAsString() {
        if (props == null)
            return "";
        
        return props.toString();
    }

    /**
     * load additional properties, preserves existing properties
     * @param properties  must be in the format expected by SimpleProps, namely newlined delimited key=value pairs
     */
    @Deprecated
    public void loadPropertiesFromString(String properties) {
        if (props == null)
            props = new SimpleProps();
        
        props.load(properties);
        this.fireChange(PROP_PROPERTIES,null,props);
    }

    /**
     * load additional properties, preserves existing properties
     * @param properties
     */
    public void loadProperties(SimpleProps newProps) {
        if (props == null)
            props = new SimpleProps();
        
        if (newProps != null) {
            props.putAll(null,newProps);
            this.fireChange(PROP_PROPERTIES,null,props);
        }
    }
    
    public SimpleProps getProps() {
        return props;
    }
    
    public int hashCode() {
        int ret = -1;

        if (this.props != null) {
            ret ^= props.hashCode();
        }
        if (this.id != null) {
            ret ^= id.hashCode();
        }
        return ret;
    }

    /**
     * two BaseInfo objects are equal if their id's are equal and if their properties are equal
     */
    public boolean equals(Object o) {
        if (o == null) { 
            return false;
        }
        if (!(o instanceof BaseInfo)) {
            return false;
        }
        BaseInfo other = (BaseInfo) o;
        
        if (id == null) {
            if (other.id != null)
                return false;
        } else {
            if (!id.equals(other.id))
                return false;
        }
        
        if (props != null) {
            if (other.props != null)
                return props.toString().equals(other.props.toString());
            else
                return false;
        } else {
            return (other.props == null);
        }

    }

    /**
     * serialize this object to a SimpleProps and prefix every key with an optional prefix 
     * @param props    if null, creates a new props, otherwise, inserts into existing one
     * @param prefix   may be null
     * @return the props
     */
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        if (p == null)
            p = new SimpleProps();
        p.mergeWithPrefix(prefix,this.props);
        if (id != null) {
            p.set(prefix,PROP_ID,id.toString());
        }
        return p;
    }

    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = false;
        if (this.props == null)
            this.props = new SimpleProps();
        
        Iterator<String> iter = p.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (prefix == null) {
                this.props.put(key,p.get(key));
                ret = true;
            } else {
                String pf = prefix + SimpleProps.PREFIX_SEPARATOR;
                if (key.startsWith(pf)) {
                    String newKey = key.substring(pf.length());
                    this.props.put(newKey,p.get(key));
                    ret = true;
                }
            }
        }
        
        String key = "id";
        if (this.props.containsKey(key)) {
            this.id = this.props.get(key);
            this.props.remove(key); // remove the extraneous id key from the properties
        }
        
        return ret;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        SimpleProps p = serializeToProps(null,null);
        sb.append(p.toString());
        return sb.toString();
    }
    
    /**
     * is the ID passed in a transient ID indicating a newly created, non-persistent
     * object?
     * 
     * IDs which start with a - and do not have any slashes in them are temp IDs
     * they are negative integer values
     * 
     * @param s
     * @return
     */
    public static boolean isTempID(String id) {
        if (id != null) {
            return (id.startsWith("-") && id.indexOf('/') == -1);
        } else {
            return true;
        }
        
    }
    
    
    /**
     * returns the last part of a ID, e.g.
     *      foo/bar/baz  -->  baz
     * unless it's a temporary id, in which case return the defalt value
     * @param s  - the ID
     * @param defalt
     * @return
     */
    public static String lastIDPart(String s, String defalt) {
        String result = defalt;
        if (s != null) {
            if (!BaseInfo.isTempID(s)) {
                int ind = s.lastIndexOf('/');
                if (ind >= 0) {
                    result = s.substring(ind + 1);
                }
            }
        }
        return result;
    }


    public int getPersistState() {
        return persistState;
    }


    public void setPersistState(int persistState) {
        Integer old = this.persistState; 
        this.persistState = persistState;
        fireChange(PROP_PERSIST_STATE,old,this.persistState);
    }
    
    /**
     * fire off a PropChangeEvent.  If there are no listeners, do nothing.  If the old and new values are equal(), 
     * then nothing has really changed so do not fire events.
     * 
     * @param prop   the name of the property
     * @param old    the old value of this property, can be null
     * @param newval the new value of this property, can be null
     */
    @Override
    public void fireChange(String prop, Object old, Object newv) {
        if (!PROP_PERSIST_STATE.equals(prop)) {
            if (persistState == UNMODIFIED) {
                persistState = MODIFIED;
            }
        }
        super.fireChange(this,prop,old,newv,false);
    }

}
