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

/**
 * ChartMechanic data types.
 * These data types are the ones that are pertinent for charting
 * Result sets (Data series) need to have columns of these types
 * Query parameters for binding SQL queries are also of these types
 * In cases where these types do not match more specific SQL types, we
 * do our best to convert them.
 * 
 * may eventually expand this list to more closely match JDBC SQL types
 */
public final class DataType implements Serializable {
    
    public static final int UNKNOWN = 0;
    public static final int INTEGER = 1;
    public static final int DOUBLE = 2;
    public static final int DATE = 3;
    public static final int STRING = 4;
    public static final int BOOLEAN = 5;
    /**
     * IGNORE denotes a column from the FLAT datasource
     * that is not to be persisted.
     */
    public static final int IGNORE = 6;
    public static final int __LAST = 6;

    public static final String LABEL_NUMBER = "NUMBER";
    public static final String LABEL_TEXT = "TEXT";
    
    // order of names must match above enum
    public static String[] names = {
        "UNKNOWN",
        "INTEGER",
        "NUMBER",
        "DATE",
        "TEXT",
        "BOOLEAN",
        "IGNORE"
        };

    private static String[][] ALIASES = {
        null,
        { "INT", "WHOLE" },
        { "DOUBLE", "NUM", "REAL", "NUMERIC" },
        { "TIMESTAMP", "TIME", "DATETIME" },
        { "STRING", "VARCHAR", "CHAR" },
        { "BOOL" }
    };
    public static String toString(int type) {
        if (type >= UNKNOWN && type <= __LAST)
            return names[type];
        else
            return names[UNKNOWN];
    }

    public static int parse(String s) {
        int ret = UNKNOWN;
        for (int i = 0; i < names.length; i++) {
            if (names[i].equalsIgnoreCase(s)) {
                ret = i;
                break;
            }
        }
        if (ret == UNKNOWN) {
            outer:
            for (int i = 0; i < ALIASES.length; i++) {
                for (int j = 0; ALIASES[i] != null && j < ALIASES[i].length; j++) {
                    if (ALIASES[i][j].equalsIgnoreCase(s)) {
                        ret = i;
                        break outer;
                    }
                }
            }
        }
        return ret;
    }
    
    private int type;
    
    public DataType() { type = UNKNOWN;}
    public DataType(int type) {
        if (type > 0 && type < __LAST)
            this.type = type;
        else 
            this.type = UNKNOWN;
    }
    
    public DataType(String typeName) {
        this.type = parse(typeName);
    }
    
    public String toString() {
        return toString(type);
    }

    public static boolean isNumeric (int type) {
        return (type == INTEGER || type == DOUBLE);
    }
    
    public static String[] getValidTypes() {
        return names;
    }
    
    // helpful for switch statements
    public int toInt() {
        return this.type;
    }
    
}
