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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PropChangeTest {

    public class ChangeListener implements PropChangeListener {
        public int numEventsReceived = 0;
        public Object lastOldValue;
        public Object lastNewValue;
        public Object lastSource;
        public Object lastPropName;
        
        ChangeListener() {
        }
        
        public void propertyChanged(PropChangeEvent pce) {
            numEventsReceived++;
            lastOldValue = pce.getOldValue();
            lastNewValue = pce.getNewValue();
            lastSource = pce.getSource();
            lastPropName = pce.getPropertyName();
            
            // hit the toString method to make code coverage happy
            System.out.println("prop change event received: " + pce);
        }
    }
    /**
     * test the firing of properties
     */
    @Test public void testFireProperty() {
        PropChangeSource source = new PropChangeSource();
        
        ChangeListener l1 = new ChangeListener();
        source.addPropChangeListener(l1);

        String propName = "prop1";
        String oldValue = "oldValue";
        String newValue = "newValue";
        source.fireChange(propName, oldValue, newValue);
        
        assertThat((String)l1.lastPropName,is(propName));
        assertThat((String)l1.lastOldValue,is(oldValue));
        assertThat((String)l1.lastNewValue,is(newValue));
        assertTrue(l1.lastSource == source);

        assertTrue(l1.numEventsReceived == 1);
        
        source.fireChange(propName, oldValue, oldValue);
        // if the old and new values are the same, the listener is not triggered.

        
        ChangeListener l2 = new ChangeListener();
        source.addPropChangeListener(l2);
        
        String prop2Name = "prop2";
        source.fireChange(prop2Name, oldValue, newValue);
        
        assertTrue(l1.numEventsReceived == 2);
        assertTrue(l2.numEventsReceived == 1);

        source.removePropChangeListener(l1);
        source.fireChange(propName, oldValue, newValue);
        
        assertTrue(l1.numEventsReceived == 2);
        assertTrue(l2.numEventsReceived == 2);
        
        assertThat((String)l2.lastPropName,is(propName));
     
        source.removePropChangeListener(l2);
        // now we should have no listeners
        
        source.fireChange(propName, oldValue, newValue);
        
        assertTrue(l1.numEventsReceived == 2);
        assertTrue(l2.numEventsReceived == 2);
        
        // extra remove should be ok
        source.removePropChangeListener(l2);
        
    }
}
