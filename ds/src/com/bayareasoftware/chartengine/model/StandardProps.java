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

// collection of standard property names we used in various places
public interface StandardProps {
    
    // BaseInfo properties
    static String DESCRIPTION = "description";
    
    static String URL = "url";
    
    // these are for DB connections
    static String JDBC_DRIVER="jdbc_driver";
    static String JDBC_URL="jdbc_url";
    static String JDBC_USERNAME="jdbc_username";
    static String JDBC_PASSWORD="jdbc_password";
    static String JDBC_JNDI_NAME = "jndi_name";
    
}   

