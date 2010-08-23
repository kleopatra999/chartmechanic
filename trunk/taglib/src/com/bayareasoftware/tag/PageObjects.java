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
package com.bayareasoftware.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.SimpleProps;

/**
 * Encapsulates chart tag data (data sources, templates) held on a page
 * (i.e., in a JSP PageContext)
 * 
 * @author dave
 *
 */
public class PageObjects {
    
    private static final String ATT_NAME = "chartmechanic.tag.PageData";
    private static Map<String,SimpleProps> templates = new HashMap();
    private static Map<String,DataSourceInfo> sources = new HashMap();
    
    public static PageObjects get(PageContext pc) {
        PageObjects ret = (PageObjects) pc.getAttribute(ATT_NAME);
        if (ret == null) {
            ret = new PageObjects();
            pc.setAttribute(ATT_NAME, ret);
        }
        return ret;
    }
    
    public SimpleProps getTemplate(String name) {
        if (name == null)
            name = "default";
        return templates.get(name);
    }
    
    public void storeTemplate(String name, SimpleProps template) {
        SimpleProps sp = getTemplate(name);
        if (sp != null) {
            sp.putAll(template);
            template = sp;
        }
        templates.put(name, template);
    }
    
    public DataSourceInfo getDataSource(String id) {
        DataSourceInfo ret = sources.get(id);
        if (ret == null)
            throw new RuntimeException("no data source found with id='" + id + "'");
        return ret;
    }
    
    public void addDataSource(String id, DataSourceInfo dsi) {
        sources.put(id, dsi);
    }
    
}
