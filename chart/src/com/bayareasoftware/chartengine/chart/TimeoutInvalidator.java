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
package com.bayareasoftware.chartengine.chart;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.ChartInfo;


public class TimeoutInvalidator implements CacheInvalidator {

    private static final Log log = LogFactory.getLog(TimeoutInvalidator.class);
    private long timeoutSecs;
    
    public TimeoutInvalidator() { }
    public TimeoutInvalidator(long t) {
        timeoutSecs = t;
    }
    public boolean isValid(ChartInfo ci, File f) {
        boolean ret = true;
        long now = System.currentTimeMillis();
        if (!f.exists() || f.lastModified() + timeoutSecs*1000 < now) { 
            ret = false;
            if (log.isDebugEnabled()) {
                if (!f.exists()) {
                    log.debug("chart '" + ci.getId() + "' doesn't exist");
                } else {
                    log.debug("chart '" + ci.getId() + "' timed out");
                }
            }
        }
        return ret;
    }
    public long getTimeoutSecs() {
        return timeoutSecs;
    }
    public void setTimeoutSecs(long timeoutSecs) {
        this.timeoutSecs = timeoutSecs;
    }
}
