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

import java.util.ArrayList;
import java.util.List;

public class AttInfo {

    public String name, description;
    public boolean required;
    public List<String> aliases = new ArrayList();
    
    public AttInfo(String name, String desc, boolean req) {
        this.name = name;
        description = desc;
        required = req;
    }
    
    public AttInfo setDescription(String d) {
        description = d;
        return this;
    }
    
    public AttInfo addAlias(String alias) {
        aliases.add(alias);
        return this;
    }
}
