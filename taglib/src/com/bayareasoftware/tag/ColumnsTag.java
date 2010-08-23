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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

public class ColumnsTag extends TagSupport implements DynamicAttributes {

    /*
      <c:columns 1="DATE" 1-format="yyyy-MM-dd hh:mm:ss" 2="NUMBER" timeperiod="year"/>
     */
    public void setDynamicAttribute(String uri, String name, Object obj)
            throws JspException {
        
    }

    public int doAfterBody() throws JspException {
        
        DataTag dt = (DataTag) findAncestorWithClass(this, DataTag.class);
        if (dt == null)
            throw new JspException("cannot find enclosing DataTag");
        
        return super.doAfterBody();
    }
}
