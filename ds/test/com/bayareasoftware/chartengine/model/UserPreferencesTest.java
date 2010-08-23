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

import org.junit.Assert;
import org.junit.Test;

public class UserPreferencesTest {
    @Test
    public void testSerialize() {
        UserPreferences u = new UserPreferences();
        
        u.setAutoRender(false);
        u.setAutoSave(true);
        u.setDefaultPassword("default-password");
        u.setDefaultUserName("default-username");
        u.setExpertMode(false);
        u.setMatchHost("match-host");
        
        SimpleProps sp = new SimpleProps();
        sp.put("stroke","line=4.0|dash=2");
        sp.put("alpha", "0.8");
        sp.put("paint","#ffaa00");
        sp.put("labelTextAnchor","BOTTOM_CENTER");
        sp.put("labelFont","Arial-12");
        sp.put("labelAnchor", "CENTER");
        
        u.setMarkerDefaults(sp);

        SimpleProps p = u.serializeToProps(null, null);

        UserPreferences u2 = new UserPreferences();
        
        u2.deserializeFromProps(p, null);

        Assert.assertEquals(u,u2);
        
        p = u.serializeToProps(null,"some-prefix");
        
        UserPreferences u3 = new UserPreferences();
        u3.deserializeFromProps(p, "some-prefix");
        Assert.assertEquals(u,u3);
        
        
    }
    
}
