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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for any objects that need to fire Property change events. 
 * Useful for maintaining referential data integrity, especially within the UI
 */
public class PropChangeSource {
    
    private transient List<PropChangeListener>listeners = null;
    
    // allow events to be enabled to disabled
    private transient boolean eventsEnabled = true;
    private transient PropChangeEvent suppressedEvent = null;
    
    /**
     * add a listener if it isn't already listening to property changes from this object
     * @param l the listener object
     */
    public synchronized void addPropChangeListener(PropChangeListener l) {
        if (listeners == null) 
            listeners = new ArrayList<PropChangeListener>();
        
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }
    
    /**
     * remove a listener so that it will no only receive PropChangeEvent's
     * @param l the listener object
     */
    public synchronized void removePropChangeListener(PropChangeListener l) {
        if (listeners != null)
            listeners.remove(l);
    }
    
    /**
     * fire off a PropChangeEvent.  If there are no listeners, do nothing.  If the old and new values are equal(), 
     * then nothing has really changed so do not fire events.
     * 
     * @param prop   the name of the property
     * @param old    the old value of this property, can be null
     * @param newval the new value of this property, can be null
     */
    public void fireChange(String prop, Object old, Object newv) {
        fireChange(this,prop,old,newv,false);
    }
    
    
    /**
     * fire off a PropChangeEvent.  If there are no listeners, do nothing.  If the old and new values are equal(), 
     * then nothing has really changed so do not fire events.
     * 
     * @param prop   the name of the property
     * @param old    the old value of this property, can be null
     * @param newval the new value of this property, can be null
     * @param force  if true, fire can event even if the old/new are the same 
     */
    public void fireChange(String prop, Object old, Object newv, boolean force) {
        fireChange(this,prop,old,newv,force);
    }

    
    /**
     * fire off a PropChangeEvent, with the specified object as the source of the event.
     * If there are no listeners, do nothing.  If the old and new values are equal(), 
     * then nothing has really changed so do not fire events.
     *
     * @param src    the source of the property change event
     * @param prop   the name of the property
     * @param old    the old value of this property, can be null
     * @param newval the new value of this property, can be null
     */
    public void fireChange(Object src, String prop, Object old, Object newv, boolean force) {
        

        if (listeners == null || listeners.size() == 0)
            // nothing to do, don't bother
            return;
    
        if (!force) {
            if (old != null && old.equals(newv)) {
                // nothing actually changed, don't fire events for efficiency...
                return;
            }
        }
        PropChangeEvent evt = new PropChangeEvent(src,prop,old,newv);
        if (eventsEnabled) { 
            for (int i = 0; i < listeners.size(); i++) {
                PropChangeListener pcl = listeners.get(i);
                pcl.propertyChanged(evt);
            }
        } else {
            suppressedEvent = evt;
        }
        
   }
    
    /**
     * suppress the firing of events
     * but we keep track of the last event that WOULD HAVE been fired
     * so when we re-enable events, we immediately fire that 'pent-up' event.
     */
    public void suppressEvents() {
        this.eventsEnabled = false;
        this.suppressedEvent = null;
    }
    
    /**
     * enable events
     * if there was a suppressed event, fire that 
     */
    public void enableEvents() {
        this.eventsEnabled = true;
        if (this.suppressedEvent != null) {
            // fire the suppressedEvent
            for (int i = 0; i < listeners.size(); i++) {
                PropChangeListener pcl = listeners.get(i);
                pcl.propertyChanged(this.suppressedEvent);
            }
            suppressedEvent = null;
        }
    }
    
    public boolean isEventsEnabled () {
        return this.eventsEnabled;
    }
    
}

