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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.bayareasoftware.chartengine.model.event.PropChangeSource;

/**
 * an implementation of java.util.Properties that can
 * handle unicode strings and work in the GWT client.
 * 
 * It is mean to hold bean property names as keys, so
 * doesn't do any of the fancy escaping or newline
 * continuations that the real Properties object does.
 * @author dave
 *
 */
public class SimpleProps extends HashMap<String,String> {

    public static final String PREFIX_SEPARATOR=".";

    private transient PropChangeSource changer;
    
    public SimpleProps() { }
    
    public SimpleProps(String values) {
        load(values);
    }
    
    // set a key/value
    // synonymous with put, but a lot of our calling code used set
//    @Deprecated
//    public void set(String key, String val) {
//        put(key, val);
//    }
    
    /**
     * Set the source object for PropChangeEvent's from this property set.
     * The practical effect is that all the listeners of the specified
     * property change source will be notified of changes to this property set.
     * @param changer
     */
    public void setPropChangeSource(PropChangeSource changer) {
        this.changer = changer;
    }
    
    public PropChangeSource getPropChangeSource() {
        return this.changer;
    }
    
    public void enableEvents() {
        if (changer != null) 
            changer.enableEvents();
    }
    
    public void suppressEvents() {
        if (changer != null) 
            changer.suppressEvents();
    }
    public boolean isEventsEnabled() {
        if (changer != null) {
            return changer.isEventsEnabled();
        } 
        return false;
    }
    
    /**
     * override so that we can fire property change events
     */
    @Override
    public String remove(Object key) {
        String value = super.remove(key);
        if (changer != null && value != null) {
            changer.fireChange(key.toString(), value, null);
        }
        return value;
    }
    /**
     * override so that we can fire property change events
     */
    @Override
    public String put(String key, String value) {
        String old = super.put(key, value);
        //System.err.println("[SimpleProps] firing change '" + key + "', changer " + changer);
        if (changer != null && value != null && !value.equals(old)) {
            changer.fireChange(key, old, value);
        }
        return old;
    }
    public boolean containsKey(String prefix, String s) {
        return containsKey(prefixIt(prefix,s));
    }
    public static String prefixIt(String prefix, String s) {
        if (prefix != null) {
            return prefix+PREFIX_SEPARATOR+s;
        } 
        return s;
    }
    
    /**
     * a utility method that handles optional prefixes for keys
     * only sets if the value is non-null
     * 
     * @param prefix  optional prefix to the key, ignored if null
     * @param key     
     * @param val if val is null, don't do anything
     */
    public void set(String prefix, String key, String val){
        if (val != null) {
            put(prefixIt(prefix,key),val);
        }
    }
    
    /**
     * an utility method that handles optional prefixes for keys
     * @param prefix
     * @param key
     * @return
     */
    public String get(String prefix, String key) {
        return get(prefixIt(prefix,key));
    }
    
    /**
     * put the values of 'other' where the key starts with start with 'prefix' into this
     * if prefix == null, load all other properties 
     */ 
    public void putAll(String prefix, SimpleProps other) {
        Iterator<String> iter = other.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (prefix == null || key.startsWith(prefix)) {
                String val = other.get(key);
                this.put(key, val);
            }
        }
    }

    /** 
     * remove all values whose key starts with prefix
     * MODIFIES the object 
     * @param prefix
     */
    public SimpleProps removeWithPrefix(String prefix) {
        if (prefix != null) {
            String[] keys = this.keySet().toArray(new String[0]);
            for (String key : keys) {
                if (key.startsWith(prefix))
                    this.remove(key);
            }
        }
        return this;
    }

    /** 
     * remove all values whose key ends with suffix
     * 
     * @param suffix
     * @param other
     */
    public SimpleProps removeWithSuffix(String suffix) {
        if (suffix != null) {
            String[] keys = this.keySet().toArray(new String[0]);
            for (String key : keys) {
                if (key.endsWith(suffix))
                    this.remove(key);
            }
        }
        return this;
    }
    
    /**
     * Remove all leading and trailing whitespace from names and values.
     * Returns a new properties object; the original (<i>this</i>) is unmodified
     */
    public SimpleProps trimWhitespace() {
        SimpleProps ret = new SimpleProps();
        for (String key : keySet()) {
            String val = get(key);
            ret.put(key.trim(), val.trim());
        }
        return ret;
    }
    

    
    /**
     * return a SimpleProps that is a subset of the original
     * with keys that start with prefix. In the simpleprops that is returned
     * the keys have the prefix removed
     * the original props is unchanged
     * 
     * @param props   - the new props.  could be empty if no keys start with prefix
     * @param prefix  - prefix w/o the PREFIX_SEPARATOR.  if prefix == null, then return a copy of the original props with all keys 
     * @return
     */
    
    public SimpleProps subset(String prefix) {
        SimpleProps ret = new SimpleProps();
        Iterator<String> iter = keySet().iterator();
        String pf = null;
        int pfLen = 0;
        if (prefix != null) {
            pf = prefix + PREFIX_SEPARATOR;
            pfLen = pf.length();
        }
        while (iter.hasNext()) {
            String key = iter.next();
            if (prefix == null) {
                ret.put(key,this.get(key));
            } else {
                if (key.startsWith(pf)) {
                    String newKey = key.substring(pfLen);
                    ret.put(newKey,this.get(key));
                }
            }
        }
        return ret;
    }

    /** 
     * merge in values from the 'other' simpleprops, prefixing its keys with an optional prefix
     * 
     * @param prefix
     * @param other
     */
    public void mergeWithPrefix(String prefix, SimpleProps other) {
        Iterator<String> iter = other.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            this.put(prefixIt(prefix,key), other.get(key));
        }
    }

    
    // delimiters for the string representation of SimpleProps
    // note that load/toString() must be kept consistent in
    // their use of delimiters

    static final private char KEY_VALUE_DELIMITER = '=';
    static final private char PROPERTY_DELIMITER='\n';
    
    /**
     * load additional properties specified by propStr
     * properties are name=value separated by newlines
     * preserves existing properties
     */
    public void load(String propStr) {
        if (propStr == null) {
            return;
        }
        String[] nvp = splitFull(propStr, PROPERTY_DELIMITER);
        String lastKey = null;
        for (int i = 0; i < nvp.length; i++) {
            String line = nvp[i];
            if (line.startsWith(" ")) {
                // if the line starts with space, it's a continuation of the last key
                if (lastKey != null) {
                    put(lastKey,get(lastKey)
                                +"\n"  // original value had an embedded \n
                                +line.substring(1)); // skip the artificial space we added at the beginning  
                }
                continue;
            } 
            if (trim(line) == null) {
                continue;
            }
            String[] nv = split(nvp[i], KEY_VALUE_DELIMITER);
            if (nv.length == 2) {
                //String value = trim(nv[1]);
                // don't trim the value, else we would lose all values that are supposed to be legitimate whitespaces
                String value = nv[1];
                if (value != null) {
                    String key = nv[0];
                    put(key,value);
                    lastKey = key;
                }
            }
        }
    }
    
    /**
     * return the string representation,  
     * each key/value pair is delimited by KEY_VALUE_DELIMITER
     * each property is separated by PROPERTY_DELIMITER
     * 
     * @param sortedKeys  - if true, sort the keys alphabetically
     * @return
     */
    public String toString(boolean sortedKeys) {
        StringBuilder sb = new StringBuilder();
        
        String[] keys = keySet().toArray(new String[size()]);

        if (sortedKeys) {
            Arrays.sort(keys);
        }
        
        for (int i=0;i<keys.length;i++) {
            String value = get(keys[i]);
            if (value == null || value.length() == 0) {
//                System.out.println("******************* skipping  key = " + keys[i] + " because value = " + value);
                continue; // skip any keys whose values are empty strings.  don't want that messing up our serialization/deserialization from string
            }
            
            if (i > 0)
                sb.append(PROPERTY_DELIMITER);
            String newValue = handleNewlines(value);
            sb.append(keys[i]).append(KEY_VALUE_DELIMITER).append(newValue);
        }
        return sb.toString();
    }
    
    // given a string, replace all \n with \n<space> 
    private String handleNewlines(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i); // FIXME: unicode?
            sb.append(c);
            if (s.charAt(i) == '\n')
                sb.append(' ');
        
        }
        return sb.toString();
    }
    
    public String toString() {
        return toString(true);
    }

    
    private static String[] split(String str, char delim) {
        String[] ret;
        int ind = str.indexOf(delim);
        if (ind < 0) {
            ret = new String[1];
            ret[0] = str;
        } else {
            ret = new String[2];
            ret[0] = str.substring(0, ind);
            ret[1] = str.substring(ind + 1);
        }
        return ret;
    }
    
    // split the string fully into an array of strings base on the delim character
    // delimiter inside the quote character do not count
    
    private static final char QUOTE ='"';
       
    public static String quoteString(String s) {
        return QUOTE + s + QUOTE;
    }
    
    /**
     * remove the quote chars from a string
     * save to use even if the string was not quoted
     * @param s
     * @return
     */
    public static String unquoteString(String s) {
        String res = s;
        if (s != null) {
            if (s.charAt(0) == QUOTE && s.charAt(s.length()-1) == QUOTE)
                res = s.substring(1,s.length()-1);
        }
        return res;
    }
    public static String[] splitFull(String str, char delim) {
        if (str == null)
            return null;
        int len = str.length();
        int count = 0;
        boolean inQuote = false;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == QUOTE) {
                inQuote = !inQuote;
            }
            if (c == delim && !inQuote) {
                count++;
            }
        }
        String[] ret = new String[count + 1];
        int begin = 0, off = 0;
        for (int end = 0; end < len; end++) {
            char c = str.charAt(end);
            if (c == QUOTE) {
                inQuote = !inQuote;
            }
            if (c == delim && !inQuote) {
                String s = str.substring(begin, end);
                ret[off++] = s;
                begin = end + 1;
            }
        }
        ret[off] = str.substring(begin);
        return ret;
    }
    
    private static String trim(String s) {
        if (s != null && (s = s.trim()).length() == 0) {
            s = null;
        }
        return s;
    }
    
}
