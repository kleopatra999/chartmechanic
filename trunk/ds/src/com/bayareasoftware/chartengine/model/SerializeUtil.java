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

import java.util.List;

/**
 * utilites that assist in property serialization
 */
public class SerializeUtil {
    public static final String LIST_SIZE_KEY="n";
    
    /**
     * serializes an List of PropertySerializable's to a property
     *    uses the lstPrefix so that you end up with
     *       <lstPrefix>.n = count of elements in List
     *       <lstPrefix>.0.<key>=<value>
     *       <lstPrefix>.1.<key>=<value>, etc.    for each element of the list
     * @param p             incoming property to be modified.  if null, allocates a new SimpleProps
     * @param prefix        prefix for the resultant properties
     * @param lstPrefix     additional prefix for list contents
     * @return
     */
    public static SimpleProps serializeList(SimpleProps p, String prefix, List<? extends PropertySerializable> lst, String lstPrefix) {
        // list of SeriesInfo
        if (p == null)
            p = new SimpleProps();
        
        if (lst != null && lst.size() > 0) {
            p.set(prefix,
                    SimpleProps.prefixIt(lstPrefix,LIST_SIZE_KEY),
                  String.valueOf(lst.size()));
            for (int i=0;i<lst.size();i++) {
                PropertySerializable b = lst.get(i);
                // serialize each member of the list, now with the prefix
                // <prefix>.<lstPrefix>.<i>
                p = b.serializeToProps(p, SimpleProps.prefixIt(prefix,SimpleProps.prefixIt(lstPrefix,String.valueOf(i))));
            }
        }
        return p;
    }

    /**
     * like serializeList, but instead of the contents of each list element, only serialize out their IDs
     * @param p
     * @param prefix
     * @param lst
     * @param lstPrefix
     * @return
     */
    public static SimpleProps serializeListIDs(SimpleProps p, String prefix, List<? extends BaseInfo> lst, String lstPrefix) {
        if (p == null)
            p = new SimpleProps();
        
        if (lst != null && lst.size() > 0) {
            p.set(prefix,
                    SimpleProps.prefixIt(lstPrefix,LIST_SIZE_KEY),
                  String.valueOf(lst.size()));
            for (int i=0;i<lst.size();i++) {
                // serialize just the ID of the member of each list
                // <prefix>.<lstPrefix>.<i>.id = <value>;
                BaseInfo b = lst.get(i);
                p.set(SimpleProps.prefixIt(prefix,SimpleProps.prefixIt(lstPrefix,String.valueOf(i))),
                      BaseInfo.PROP_ID,
                      b.getId());
            }
        }
        return p;
    }
    
    /**
     * if p is the serialized property made by serializeList() of a List of PropertySerializables with list prefix lstPrefix
     * then return the size of the list.
     * 
     * in other words, this returns the value of the key <lstPrefix>.n
     *  
     * @param p
     * @param prefix
     * @param lstPrefix
     * @return
     */
    public static int deserializeListSize(SimpleProps p, String prefix, String lstPrefix) {
        int result = 0;
        if (p != null) {
            String v = p.get(prefix,SimpleProps.prefixIt(lstPrefix,LIST_SIZE_KEY));
            if (v != null) {
                try {
                    result = Integer.parseInt(v);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    /**
     * if p is the serialized property made by serializeList() of a List of PropertySerializables with list prefix lstPrefix
     * then return the i-th element 
     *  
     * in other words, this deserializes all the properties that look like
     *    <lstPrefix>.<index>.<key>   into the object o
     * 
     * if no relevant key/values are found, the object passed in is unaffected
     * @param p     the property object
     * @param o      object to deserialize into
     * @param prefix  the property prefix
     * @param lstPrefix  the list prefix 
     * @param i       the element of the list you are looking for
     * @return       the deserialized object
     */
    public static <T extends PropertySerializable> T deserializeListElement(T o, SimpleProps p, String prefix, String lstPrefix, int i) {
       o.deserializeFromProps(p, SimpleProps.prefixIt(prefix,SimpleProps.prefixIt(lstPrefix,String.valueOf(i))));
       return o;
    }

}
