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

public class SimplePropsTest {

    
    @Test 
    public void testSubset() {
        SimpleProps p1 = new SimpleProps();
        p1.put("k1","v1");
        p1.put("k2","v2");
        p1.put("pf.k3","v3");
        p1.put("pf.k4","v4");
        
        SimpleProps p2 = p1.subset("pf");
        Assert.assertTrue(p2.size() == 2);
        Assert.assertTrue(p2.get("k3").equals("v3"));
        Assert.assertTrue(p2.get("k4").equals("v4"));
        
        Assert.assertTrue(p1.subset(null).size() == 4);
    }
    
    @Test
    public void testRemoveWithPrefix() {
        SimpleProps p1 = new SimpleProps();
        p1.put("k1","v1");
        p1.put("k2","v2");
        p1.put("pf.k3","v3");

        p1.removeWithPrefix("pf.");
        Assert.assertNull(p1.get("pf.k3"));
        
        p1.removeWithPrefix("not there");
        Assert.assertTrue(p1.size() == 2);
        
        p1.removeWithPrefix("null");
        Assert.assertTrue(p1.size() == 2);
        
    }

    @Test
    public void testRemoveWithSuffix() {
        SimpleProps p1 = new SimpleProps();
        p1.put("k1.suffix1","v1");
        p1.put("k2.suffix2","v2");
        p1.put("pf.not.a.suffix1.k3","v3");

        p1.removeWithSuffix("not there");
        Assert.assertTrue(p1.size() == 3);
        
        p1.removeWithSuffix("suffix1");
        Assert.assertNull(p1.get("k1.suffix1"));
        Assert.assertNotNull(p1.get("pf.not.a.suffix1.k3"));

        p1.removeWithPrefix("null");
        Assert.assertTrue(p1.size() == 2);
        
    }

    
    
    @Test
    public void testPutAll() {
        SimpleProps p1 = new SimpleProps();
        p1.put("k1","v1");
        p1.put("k2","v2");
        p1.put("sharedKey","v3");
        SimpleProps p2 = new SimpleProps();
        p2.put("p2.key","aaa");
        p2.put("key","bbb");
        p2.put("sharedKey","bbb");
        
        p1.putAll("p2", p2);
        Assert.assertTrue(p1.get("p2.key").equals("aaa"));
        Assert.assertTrue(p1.get("key") == null);
        Assert.assertTrue(p1.get("sharedKey").equals("v3"));
        
        p1.putAll(null,p2);
        Assert.assertTrue(p1.get("p2.key").equals("aaa"));
        Assert.assertTrue(p1.get("key").equals("bbb"));
        Assert.assertTrue(p1.get("sharedKey").equals("bbb"));
    }
    
    @Test
    public void testMergePrefix() {
        SimpleProps p1 = new SimpleProps();
        p1.put("k1","v1");
        p1.put("k2","v2");
        SimpleProps p2 = new SimpleProps();
        p2.put("p2.key","aaa");
        p2.put("key","bbb");

        p1.mergeWithPrefix("PREFIX", p2);
        Assert.assertTrue(p1.get("p2.key") == null);
        Assert.assertTrue(p1.get("key") == null);
        Assert.assertTrue(p1.get("PREFIX.p2.key").equals("aaa"));
        Assert.assertTrue(p1.get("PREFIX.key").equals("bbb"));

        // test that prefixed get works
        Assert.assertTrue(p1.get("PREFIX","key").equals("bbb"));
        // test that prefixed get works
        Assert.assertNull(p1.get("bad PREFIX","key"));
     }
    
    @Test
    public void testLoad() {
        SimpleProps p = new SimpleProps("key1=value1\nkey2=value2");
        Assert.assertTrue(p.get("key1").equals("value1"));
        Assert.assertTrue(p.get("not there") == null);

        p.load("key1=newValue\nkey3=value3");
        Assert.assertTrue(p.get("key1").equals("newValue"));
        Assert.assertTrue(p.get("key2").equals("value2"));
        Assert.assertTrue(p.get("key3").equals("value3"));

        Assert.assertTrue(p.keySet().size() == 3);
        p.load("");
        Assert.assertTrue(p.keySet().size() == 3);
        p.load(null);
        Assert.assertTrue(p.keySet().size() == 3);

        p.load("badly formed string");
        Assert.assertTrue(p.keySet().size() == 3);
        p.load("whitespace_value= "); // key with no values are not allowed in load
        Assert.assertEquals(p.get("whitespace_value")," ");
    }
    
    @Test
    public void testValuesWithNewlines() {
        SimpleProps p = new SimpleProps();
        p.put("key1","value1");
        p.put("key2","value2");
        p.put("key3","this is a multi\nline property\n");
        p.put("key4","this is a value with embedded equals\nfoo=bar");
        
        String s = p.toString();
        
        SimpleProps p2 = new SimpleProps();
        p2.load(s);
        
        Assert.assertTrue(p2.size() == 4);
        Assert.assertTrue(p2.get("key3").equals("this is a multi\nline property\n"));
        Assert.assertTrue(p2.get("key4").equals("this is a value with embedded equals\nfoo=bar"));
        Assert.assertNull(p2.get("foo")); // make sure foo=bar didn't inadvertently get treated as key/value

    }
    @Test
    public void testFileEndsWithNewlineWS() {
        SimpleProps p = new SimpleProps("xxx=yyy\n  ");
        p = p.trimWhitespace();
        Assert.assertEquals("yyy", p.get("xxx"));
    }
}
