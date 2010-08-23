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
package com.bayareasoftware.chartengine.util;

import org.junit.Test;

import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StringUtil;

import static org.junit.Assert.*;

public class UtilTest {

    @Test
    public void testTrim() {
        assertNull(StringUtil.trim(null));
        assertNull(StringUtil.trim(""));
        assertNull(StringUtil.trim("\t"));
        assertNull(StringUtil.trim("     "));
        assertNull(StringUtil.trim("  \n   "));
        
        assertEquals(StringUtil.trim("  hello\n\t"), "hello");
    }
    
    @Test
    public void testSplit() {
        String[] s = StringUtil.split("hello:world",':');
        assertTrue(s.length==2);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world");

        s = StringUtil.split("hello:world:there",':');
        assertTrue(s.length==2);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world:there");

        s = StringUtil.split(" hello : world:there",':');
        assertTrue(s.length==2);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world:there");

    }
    
    @Test
    public void testSplitCompletely() {
        String[] s = StringUtil.splitCompletely("hello:world",':');
        assertTrue(s.length==2);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world");

        s = StringUtil.splitCompletely("hello:world:there",':');
        assertTrue(s.length==3);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world");
        assertEquals(s[2],"there");

        
        s = StringUtil.splitCompletely("hello:world::there",':');
        assertTrue(s.length==4);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world");
        assertEquals(s[2],"");
        assertEquals(s[3],"there");

        
        s = StringUtil.splitCompletely(" hello : world:\t:there",':',true);
        assertTrue(s.length==4);
        assertEquals(s[0],"hello");
        assertEquals(s[1],"world");
        assertEquals(s[2],"");
        assertEquals(s[3],"there");
    }
    
    @Test
    public void testFixupString() {
        assertEquals(StringUtil.collapseWS("hello world"),"hello world");
        assertEquals(StringUtil.collapseWS("hello          world"),"hello world");
        assertEquals(StringUtil.collapseWS("hello \t\n world"),"hello world");
    }

    @Test
    public void testCompressUncompress() {
        String s = "s.2.id=10918\n"+
                   "s.2.name=YHOO price\n"+
                   "s.2.sourceId=10919\n"+
                   "s.2.sqlQuery=SELECT D as 'DATE',adjclose as 'YHOO'\n"+
                   "FROM ticker_YHOO\n"+ 
                   "WHERE D >= ?${DATE startDate=2008-01-15}\n"+
                   "order by D\n"+
                   "s.3.color=#55392d\n"+
                   "s.3.id=10927\n"+
                   "s.3.name=MSFT price\n"+
                   "s.3.sourceId=10918\n"+
                   "s.3.sqlQuery=SELECT D,adjclose as 'MSFT' FROM ticker_msft\n"+
                   "order by D\n"+
                   "s.n=4\n"+
                   "sd.0.axisIndex=0\n"+
                   "sd.0.id=10920\n"+
                   "sd.0.visible=true\n"+
                   "sd.1.axisIndex=0\n"+
                   "sd.1.id=10919\n"+
                   "sd.1.visible=true\n"+
                   "sd.2.axisIndex=0\n"+
                   "sd.2.id=10918\n"+
                   "sd.2.visible=true\n"+
                   "sd.3.axisIndex=0\n"+
                   "sd.3.id=10927\n"+
                   "sd.3.visible=false\n"+
                   "sd.n=4\n"+
                   "timeperiod=6\n"+
                   "title.font=Arial Black-16\n"+
                   "title.text=Microsoft Yahoo spread\n"+
                   "width=750\n";
 
        
        String s2 = Util.compressString(s);
        System.out.println("original length = " + s.length() + " compressed length = " + s2.length());
        assertEquals(s,Util.uncompressString(Util.compressString(s)));
    }
    
    @Test
    public void testProps2URL() {
        SimpleProps p = new SimpleProps();
        p.put("key3","value with &<>=/\\");
        String s = Util.props2URL(p, false);
        assertEquals(s,"key3=value+with+%26%3C%3E%3D%2F%5C");

        p.clear();
        p.put("aaaaa","1");
        p.put("bbb","1");
        p.put("dddd","1");
        p.put("e","1");
        p.put("ccccccccccccccccc","1");
        s = Util.props2URL(p,true);
        assertEquals(s,"aaaaa=1&bbb=1&ccccccccccccccccc=1&dddd=1&e=1"); // sorted
        
    }
}
