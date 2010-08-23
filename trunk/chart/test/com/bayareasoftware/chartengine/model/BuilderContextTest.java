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

public class BuilderContextTest {
    
    @Test
    public void testSerialize() {
        BuilderContext bc = new BuilderContext();
        bc.add("foo/bar/baz");
        bc.add("123/456");
        bc.add("some-random-path");
        
        SimpleProps p = bc.serializeToProps(null,null);
        
        BuilderContext bc2 = new BuilderContext();
        bc2.deserializeFromProps(p,null);
        
        Assert.assertEquals(bc,bc2);
    }
}
