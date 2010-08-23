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

/**
 * reducers take a list of values and reduce them to a single value
 * examples include
 *  sum/max/min/count/avg
 */
public interface Reducer {
    /**
     * called once at the beginning of the aggregation
     */
    public void init(List<String> args);
    
    /**
     * iter() is called for every value to be aggregated over
     * @param o
     */
    public void iter(Object o);

    /**
     * end the aggregation
     */
    public void end();
    
    /**
     * return the aggregate value
     * @return
     */
    public Object value();
}
