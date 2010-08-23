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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class SeriesTransformTest {

    
    private void checkEquals(SeriesTransform st, SeriesTransform st2) {
        assertEquals(st.getColumn(), st2.getColumn());
        assertEquals(st.getFilterOp(), st2.getFilterOp());
        assertEquals(st.getFilterValue(), st2.getFilterValue());
        assertEquals(st.getSortOrder(), st2.getSortOrder());
        assertEquals(st.getLimit(), st2.getLimit());
    }
    
    @Test
    public void testNormal() {
        SeriesTransform st = new SeriesTransform();
        st.setColumn(10);
        st.setFilterOp(SeriesTransform.GT);
        st.setFilterValue("100");
        st.setSortOrder(SeriesTransform.ASC);
        st.setLimit(30);
        
        String script = st.genScript();
        SeriesTransform st2 = new SeriesTransform();
        st2.parseScript(script);
        
        checkEquals(st,st2);
    }
    
    @Test
    public void testEmpty() {
        SeriesTransform st = new SeriesTransform();
        String script = st.genScript();
        
        SeriesTransform st2 = new SeriesTransform();
        
        st2.parseScript(script);
        checkEquals(st,st2);
    }
    
    @Test
    public void testSomeFieldsMissing() {
        SeriesTransform st = new SeriesTransform();
        st.setColumn(10);
        st.setSortOrder(SeriesTransform.ASC);
        
        String script = st.genScript();
        
        SeriesTransform st2 = new SeriesTransform();
        
        st2.parseScript(script);
        checkEquals(st,st2);
        
    }
}
