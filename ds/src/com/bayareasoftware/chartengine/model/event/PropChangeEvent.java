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
package com.bayareasoftware.chartengine.model.event;

/**
 * modeled after java.beans.PropertyChangeEvent}
 * @author David Brown
 *
 */

public class PropChangeEvent {
    private Object source, oldValue, newValue;
    private String propertyName;
    
    public PropChangeEvent(Object src, String pname,
            Object old, Object newval) {
        this.source = src;
        propertyName = pname;
        oldValue = old;
        newValue = newval;
    }
    public boolean isProp(String testName) {
        return propertyName.equals(testName);
    }
    public Object getSource() {
        return source;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public String toString() {
        return "[PropChange: " + propertyName + " changed on " + source + "]";
    }
}