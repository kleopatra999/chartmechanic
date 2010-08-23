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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * a FunctionDescriptor describes a backend function 
 * that can be used by aggregates, creating derivative series, etc.
 */
public class FunctionDescriptor implements Serializable {
    
    /**
     * the displayName is what is shown in the UI
     */
    private String displayName;
    
    /**
     * the name is the logical name of the function and persisted in property of the chart
     */
    private String name;
    
    /**
     * a longer human-readable description of the function
     */
    private String description;
    
    /**
     * list of required argument names/types/descriptions
     */
    private List<ArgDescriptor> args = new ArrayList<ArgDescriptor>();
    
    // actualy implementation class, not serialized across the wire
    transient Class<?> impl; 
   
    /**
     * Functions are either aggregates (reduce to single value) or mappers (map from one stream to another)
     */
    private boolean isAggregate;
    
    public FunctionDescriptor() {}

    public FunctionDescriptor(String displayName, String name, String description, Class<?> impl, boolean isAggregate) {
        this.displayName = displayName;
        this.name = name;
        this.description = description;
        this.impl = impl;
        this.isAggregate = isAggregate;
        if (isAggregate) {
            // currently, all our aggregate functions have an implicit series id argument
            addArg(ArgType.SID, "Apply to Series", null);
        }
    }

    public boolean isAggregate() {
        return this.isAggregate;
    }
    public void setAggregate(boolean b) {
        this.isAggregate = b;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<ArgDescriptor> getArgs() {
        return args;
    }
    
    public Class<?> getImpl() { 
        return this.impl;
    }
    
    public FunctionDescriptor addArg(ArgType type, String n, String desc, String defaultValue) {
        args.add(new ArgDescriptor(type,n,desc,defaultValue));
        return this;
    }

    public FunctionDescriptor addArg(ArgType type, String n, String desc) {
        return addArg(type,n,desc,null);
    }

    public static class ArgDescriptor implements Serializable {
        private ArgType type;
        private String name, description;
        private String defaultValue;
        
        public ArgDescriptor() {
        }
        
        public ArgDescriptor(ArgType type, String name, String desc) {
            this(type,name,desc,null);
        }

        public ArgDescriptor(ArgType type, String name, String desc, String defaultValue) {
            this.type = type;
            this.name = name;
            this.description = desc;
            this.defaultValue = defaultValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
        
        public void setDefaultValue(String s) {
            defaultValue = s;
        }
        
        public ArgType getType() {
            return type;
        }

        public void setType(ArgType type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


    }
}
