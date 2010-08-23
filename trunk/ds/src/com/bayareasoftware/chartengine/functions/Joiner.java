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
import java.util.Map;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.Arg;
import com.bayareasoftware.chartengine.model.ISeries;

/**
 * Joiners join multiple streams by some column
 * @author dave
 *
 */
public interface Joiner {
    /**
     * called once to initialize with arguments
     */
    
    public void init(ISeries out, Map<Integer,DataStream> streamMap, List<Arg> args);
    
    /**
     * return the resultant stream
     * @return
     */
    public DataStream value();

}
