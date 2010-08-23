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
import java.util.List;

public class InferredData implements Serializable {

    private RawData rd;
    private List<TableSynopsis> synopses;
    
    // additional property that needs to be set in the DataSource
    // if null, then no additional property needed
    // if non-null, this is the property that needs a String value set
    private String missingDSProperty;
    
    public InferredData() {
    }

    public RawData getRawData() {
        return rd;
    }

    public void setRawData(RawData rd) {
        this.rd = rd;
    }

    public DataSourceInfo getDataSource() {
        return rd.toDataSource();
    }

    public List<TableSynopsis> getSynopses() {
        return synopses;
    }

    public void setSynopses(List<TableSynopsis> synopses) {
        this.synopses = synopses;
    }

    public String getMissingDSProperty() {
        return missingDSProperty;
    }
    
    public void setMissingDSProperty(String s) {
        missingDSProperty = s;
    }
    
    /**
     * returns true if the inferredData need more information
     * @return
     */
    public boolean needMoreInfo() {
        return (missingDSProperty != null);
    }
}
