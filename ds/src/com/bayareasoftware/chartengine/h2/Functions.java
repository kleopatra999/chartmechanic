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
package com.bayareasoftware.chartengine.h2;

import java.sql.Timestamp;

public class Functions {

    private Functions() { }
    /*
     *  'CREATE ALIAS DATE_TRUNC FOR "com.bayareasoftware.chartengine.h2.Functions.dateTrunc"' 
     */
    public static Timestamp dateTrunc(String part, Timestamp t) {
        if (t == null) {
            return null;
        }
        Timestamp ret = new Timestamp(0, 0, 0, 0, 0, 0, 0);
        if ("year".equalsIgnoreCase(part)) {
            ret.setYear(t.getYear());
            ret.setMonth(0);
            ret.setDate(1);
        } else if ("month".equals(part)) {
            ret.setYear(t.getYear());
            ret.setMonth(t.getMonth());
            ret.setDate(1);
        } else if ("day".equalsIgnoreCase(part)) {
            ret.setYear(t.getYear());
            ret.setMonth(t.getMonth());
            ret.setDate(t.getDate());
        } else {
            ret = t;
        }
        return ret;
    }
}
