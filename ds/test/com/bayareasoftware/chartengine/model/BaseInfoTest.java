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

public class BaseInfoTest {

    @Test
    public void testNormal() {
        BaseInfo bi = new BaseInfo();
        String id = "1";
        bi.setId(id);
        Assert.assertEquals(id, bi.getId());
//        String name = "bi1";
//        bi.setName(name);
//        Assert.assertEquals(name, bi.getName());
        String d = "sample description";
        bi.setDescription(d);
        Assert.assertEquals(d, bi.getDescription());
        
        
        Assert.assertNull(bi.getProperty("Not there"));
        bi.setProperty("key","value");
        Assert.assertEquals(bi.getProperty("key"),"value");
        
        bi.setProperty("key2","value2");
        
        Assert.assertEquals(bi.getProperty("key"),"value");
        Assert.assertEquals(bi.getProperty("key2"),"value2");
        
        bi.setProperty("key3","value3");
        bi.setProperty("key4","value4");
        bi.setProperty("key5","100");
        Assert.assertEquals(bi.getProperty("key3"),"value3");
        Assert.assertEquals(bi.getProperty("key4"),"value4");

        Assert.assertTrue(bi.getPropertyAsInt("key5",0) == 100);
        Assert.assertTrue(bi.getPropertyAsInt("not there",-1) == -1);
    }

    
    @Test
    public void testLoad() {
        BaseInfo bi = new BaseInfo();
        Assert.assertNull(bi.getProperty("key"));
        
        SimpleProps p = new SimpleProps();
        p.put("key1","value1");
        bi.loadProperties(p);
        Assert.assertEquals(bi.getProperty("key1"),"value1");
        
        p = null;
        bi.loadProperties(p);
        
    }
    
    @Test
    public void testSerialize() {
        BaseInfo bi = new BaseInfo();
        bi.setId("100");
//        bi.setName("Name");
        bi.setDescription("Description");
        bi.setProperty("key1","value1");
        
        Assert.assertTrue(bi.equals(bi));

        SimpleProps p = bi.serializeToProps(null, null);
//        System.out.println("bi = " + bi + " p = " + p);

        BaseInfo bi2 = new BaseInfo();
        
        bi2.deserializeFromProps(p, null);

//        System.out.println("bi2 = " + bi2 + " p = " + p);
        
        Assert.assertTrue(bi.equals(bi2));
        
        BaseInfo bi3 = new BaseInfo();
        p = bi.serializeToProps(null,"BI1");
        System.out.println("serialization of baseinfo with prefix BI1 = \n" + p);
        bi3.deserializeFromProps(p, "BI1");
        System.out.println("post deserialization bi3 = " + bi3 + " bi3.props = " + bi3.getProps());
        Assert.assertTrue(bi.equals(bi3));
        
        
    }
    
    
}
