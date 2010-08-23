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
/**
 * 
 */
package com.bayareasoftware.chartengine.model;

public enum ArgType {
    PATH("path"), // refers to a VFS path
    SID("sid"),  // refers to a series id
    TEXT("text"),  // any text
    NUMBER("number"), // any number
    DATE("date"), // a date
    BOOLEAN("boolean"),
    TIME_INTERVAL("timeinterval"), // a time interval, e.g., "20-days" etc
    MATH_OPERATOR("math_operator") // add/subtract/mult/divide
    ; 
    private ArgType(String s) {
        name = s;
    }
    private final String name;
    public String toString() {
        return name;
    }
    public String getName() {return name; }
    public static ArgType decode(String s) {
        ArgType[] args = ArgType.class.getEnumConstants();
        for (ArgType a : args) {
            if (s.equals(a.name)) {
                return a;
            }
        }
        return null;
    }
}