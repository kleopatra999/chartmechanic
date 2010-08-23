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

/**
 * contains the hardcoded pie item labels that are supported by the JFreeChartDriver
 */
public enum PieItemLabelFormats {

    NAME_ONLY("name"),
    NAME_PERCENTAGE("name ##%"),
    NAME_PERCENTAGE_IN_PAREN("name (##%)"),
    NAME_PERCENTAGE2("name ##.##%"),
    NAME_PERCENTAGE2_IN_PAREN("name (##.##%)"),
    NAME_EQ_VALUE_PERCENTAGE("name = ### (##%)"),
    NAME_EQ_VALUE2_PERCENTAGE2("name = ###.## (##.##%)"),
    PERCENTAGE_ONLY("##%"),
    PERCENTAGE2_ONLY("##.##%"),
    VALUE_ONLY("###"),
    VALUE2_ONLY("###.##");
    
    
    public static final PieItemLabelFormats DEFAULT = NAME_PERCENTAGE2_IN_PAREN; 
    
    final String displayValue;
    
    PieItemLabelFormats(String displayValue) {
        this.displayValue = displayValue;
    }
    
    @Override
    public String toString() {
        return displayValue;
    }

    /** get the PieItemLabelFormats that corresponds to the string,
     * if the string value is invalid return null;
     * @param s
     * @return null if string value is invalid
     */
    public static PieItemLabelFormats get(String s) {
        PieItemLabelFormats ret = null;
        if (s != null) {
            for (PieItemLabelFormats f: values()) {
                if (f.toString().equals(s)) {
                    ret = f;
                    break;
                }
            }
        }
        return ret;
    }
    
    // our own artificial property name for setting pie item label formats 
    public static final String PIE_ITEM_FORMAT_PROPERTY="pieItemLabelFormat";

    public static final String PIE_ITEM_FORMAT_TYPE="org.jfree.chart.labels.StandardPieSectionLabelGenerator";


}
