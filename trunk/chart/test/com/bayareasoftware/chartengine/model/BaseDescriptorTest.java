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

import junit.framework.Assert;

import org.junit.Test;

import com.bayareasoftware.chartengine.functions.BuiltInFunctions;

public class BaseDescriptorTest {

    @Test
    public void testSerialize() {
        BaseDescriptor dd = new BaseDescriptor();
        
        dd.setName("FOO");
        dd.setSource("some/source/path");
        dd.setSid(10000);
        dd.setAxisIndex(1);
        dd.setVisible(false);
        
        dd.setFunc(BuiltInFunctions.FN_AVG);
        Arg arg = new Arg();
        arg.setArgType(ArgType.PATH);
        arg.setArgValue("some/reference/to/a/datasource");
        
        dd.addArg(arg);
        
        arg = new Arg();
        arg.setArgType(ArgType.SID);
        arg.setArgValue("series-ref-1");
        dd.addArg(arg);

        
        arg = new Arg();
        arg.setArgType(ArgType.TEXT);
        arg.setArgValue("1,2,3,4,5");
        dd.addArg(arg);

        SimpleProps props = dd.serializeToProps(null,null);

        BaseDescriptor dd2 = new BaseDescriptor();
        dd2.deserializeFromProps(props,null);
        
//        System.err.println("dd ==> \n" + dd2);
//        System.err.println("dd2 ==> \n" + dd2);
        Assert.assertEquals(dd,dd2);
    }

    
    @Test
    public void testSerializeWithPrefix() {
        BaseDescriptor dd = new BaseDescriptor();
        
        dd.setFunc(BuiltInFunctions.FN_AVG);
        Arg arg = new Arg();
        arg.setArgType(ArgType.PATH);
        arg.setArgValue("some/reference/to/a/datasource");
        
        dd.addArg(arg);
        
        arg = new Arg();
        arg.setArgType(ArgType.SID);
        arg.setArgValue("series-ref-1");
        dd.addArg(arg);

        
        arg = new Arg();
        arg.setArgType(ArgType.TEXT);
        arg.setArgValue("1,2,3,4,5");
        dd.addArg(arg);

        String prefix = "SomePrefix";
        SimpleProps props = dd.serializeToProps(null,prefix);
        
        BaseDescriptor dd2 = new BaseDescriptor();
        dd2.deserializeFromProps(props,prefix);
        
        System.err.println("with prefix = " + prefix + " props ==> \n" + props);
        Assert.assertEquals(dd,dd2);
    }
}
