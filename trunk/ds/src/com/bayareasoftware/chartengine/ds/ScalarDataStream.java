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

package com.bayareasoftware.chartengine.ds;

import java.util.Date;

import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;

/**
 * a special case datastream of only one scalar value
 * useful for holding the results of aggregate functions
 */
public class ScalarDataStream extends DataStream{

    private boolean alreadyRead = false;
    private Object val;
    private int scalarType;
    
    public ScalarDataStream(Object value) {
        super(true);
        
        this.val = value;
        this.metadata = new Metadata();
        ColumnInfo colInfo = new ColumnInfo();
        colInfo.setName("scalar");
        scalarType = DataType.UNKNOWN;
        
        if (val != null) {
            if (val instanceof String) {
                scalarType = DataType.STRING;
            } else if (val instanceof Double) {
                scalarType = DataType.DOUBLE;
            } else if (val instanceof Integer) {
                scalarType = DataType.INTEGER;
            } else if (val instanceof Boolean) {
                scalarType = DataType.BOOLEAN;
            } else if (val instanceof Date) {
                scalarType = DataType.DATE;
            }
        }
        colInfo.setType(scalarType);
        metadata.addColumnInfo(colInfo);
        alreadyRead = false;
    }
    @Override
    public Boolean getBoolean(int index) throws Exception {
        if (scalarType == DataType.BOOLEAN)
            return (Boolean)val;
        return null;
    }

    @Override
    public Date getDate(int index) throws Exception {
        if (scalarType == DataType.DATE)
            return (Date)val;
        return null;
    }

    @Override
    public Double getDouble(int index) throws Exception {
        if (scalarType == DataType.DOUBLE)
            return (Double)val;
        return null;
    }

    @Override
    public Integer getInt(int index) throws Exception {
        if (scalarType == DataType.INTEGER)
            return (Integer)val;
        return null;
    }

    @Override
    public String getString(int index) throws Exception {
        if (val != null) {
            return val.toString();
        } 
        return null;
    }

    @Override
    public boolean nextInternal() throws Exception {
        if (alreadyRead) {
            return false;
        } 
            
        alreadyRead = true;
        return true;
    }
    
    /**
     * reset this stream to the head
     */
    @Override
    public void reset() {
        alreadyRead = false;
    }

}
