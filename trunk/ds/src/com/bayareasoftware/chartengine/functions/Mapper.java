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
package com.bayareasoftware.chartengine.functions;

import java.util.List;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ISeries;

/**
 * mappers take a stream and map it to another stream
 * examples include
 *    calculating the moving average
 */
public interface Mapper {
    /**
     * called once to initialize with arguments
     */
    
    public void init(ISeries out, List<Arg> args);
    
    /**
     * called once at the beginning of the mapping with the input streams to this mapper
     * the return value is used to iterate through the values so the mapper can at the begin() call
     * transform the initial datastream as desired (e.g. sort it)
     */
    public DataStream begin(DataStream d);
    
    /**
     * iter() is called once per row in the datastream
     * @param o
     */
    public void iter(DataStream d);

    /**
     * end the mapping
     */
    public void end();
    
    /**
     * return the resultant stream
     * @return
     */
    public DataStream value();
}
