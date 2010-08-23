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
package com.bayareasoftware.chartengine.ds.util;

import org.junit.Test;

import com.bayareasoftware.chartengine.ds.util.MetadataUtil;

import static org.junit.Assert.*;

public class MetadataUtilTest {

    @Test
    public void testGoogleCodeSVNName() {
        
        String url = "http://Google-web-tool_kit.googlecode.com/svn/trunk/";
        String name = MetadataUtil.inferDataSourceName(url);
        assertNotNull(name);
        assertEquals("Infer googlecode.com positive test", "Google_web_tool_kit", name);
        url = "http://chartmechanic.com/foo/bar";
        name = MetadataUtil.inferDataSourceName(url);
        assertEquals("default URL test", "bar", name);
    }
}
