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

import java.util.Date;

/**
 * ParamMetaData describes the type and default value of a given ChartSet parameter
 * ChartSet Parameters are values that can be bound at chart creation time.  For example,
 * they can be used to change query parameters in the queries of a data source
 */
public class ParamMetaData /*extends BaseInfo*/ {
    
    public static String NAME = "name";
    
    public static final String CLASS_ALIAS = "param-metadata";

    public static final String[] getValidTypes() {
        return DataType.getValidTypes();
    }
    
    /** parameters that start with SYSTEM_PARAM_PREFIX may not be used
     * for user-level params
     */
    public static final String SYSTEM_PARAM_PREFIX="_";
    
    /**
     * types of ParamMetaData are constrained to be a subset of the
     * JDBC types.
     */
    private DataType dataType;
    
    /**
     * the default if no param value is supplied
     */
    private String defaultValue;

    private String name;
    
    public ParamMetaData() {}
    
    public ParamMetaData(String name, int type, String defaultValue) {
        this.name = name; //setName(name);
        this.dataType = new DataType(type);
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        this.name = newName;
    }
//    public String getName() {
//        return getProperty(NAME);
//    }
//
//    /**
//     * every BaseInfo has a name.  names are not unique and may be null  
//     */  
//    public void setName(String newName) {
//        setProperty(NAME,newName);
//    }
//
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public Integer getDefaultInt() {
        Integer ret = null;
        try {
            if (defaultValue != null) {
                ret = Integer.parseInt(defaultValue);
            }
        } catch (NumberFormatException nfe) { } 
        return ret;
    }
    
    public Double getDefaultDouble() {
         Double ret = null;
         try {
             if (defaultValue != null) {
                 ret = Double.parseDouble(defaultValue);
             }
         } catch (NumberFormatException nfe) { } 
         return ret;
     }
    
     public Boolean getDefaultBoolean() {
         Boolean ret = null;
         if (defaultValue != null) {
             ret = Boolean.valueOf(defaultValue);
         }
         return ret;
     }

     public Date getDefaultDate() {
         Date ret = null;
         if (defaultValue != null) {
             ret = parseStandardDate(defaultValue);
         }
         return ret;
     }

     public Object getDefaultObject() {
        Object ret = null;
        switch (dataType.toInt()) {
        case DataType.BOOLEAN:
            ret = getDefaultBoolean();
            break;
        case DataType.STRING:
            ret = getDefaultValue();
            break;
        case DataType.DOUBLE:
            ret = getDefaultDouble();
            break;
        case DataType.INTEGER:
            ret = getDefaultInt();
            break;
        case DataType.DATE:
            ret = getDefaultDate();
            break;
            default:
                throw new IllegalArgumentException(
                        "unknown data type: " + dataType.toInt()
                        );
        }
        return ret;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getType() {
        return dataType.toString();
    }
    
    public int getDataTypeAsInt() {
        return dataType.toInt();
    }
    
    public void setType(String type) {
        this.dataType = new DataType(type);
    }
    
    public void setType(int type) {
        this.dataType = new DataType(type);
    }
    
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    /* parse date format yyyy-MM-dd since GWT has no SimpleDateFormat */
    public static Date parseStandardDate(String s) {
        boolean valid = false;
        int len = s.length();
        if (len >= 10) {
            valid = true;
            for (int i = 0; i < 10; i++) {
                char c = s.charAt(i);
                if (i == 4 || i == 7) {
                    if (c != '-') {
                        valid = false;
                        break;
                    }
                } else if (!Character.isDigit(c)) {
                    valid = false;
                    break;
                }
            }
        }
        if (!valid) {
            return null;
        }
        int y = -1, m = -1, d = -1;
        y = Integer.parseInt(s.substring(0, 4));
        //System.out.println("got y=" + y);
        m = Integer.parseInt(s.substring(5, 7)) - 1;
        d = Integer.parseInt(s.substring(8));
        Date ret = new Date();
        ret.setMonth(m);
        ret.setDate(d);
        if (y >= 1900) {
            y -= 1900;
        }
        ret.setYear(y);
        return ret;
    }
    public String toString() {
        return "[Param: name=\"" + getName() + "\" type=" + dataType.toString()
        + " default=\"" + getDefaultValue() + "\" defaultObj=\""
        + getDefaultObject() + "\"]";
    }
    
 
}
