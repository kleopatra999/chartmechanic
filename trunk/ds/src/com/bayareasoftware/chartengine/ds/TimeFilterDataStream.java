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

public class TimeFilterDataStream extends DataStream{

    private DataStream orig;
    private Date startDate, endDate;
    private int dateColumn;
    
    public TimeFilterDataStream(DataStream orig, Date startDate, Date endDate, int dateColumn) {
        super(orig.isResettable());
        this.orig = orig;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateColumn = dateColumn;
        setMetadata(orig.getMetadata());
    }
    
    @Override
    public Boolean getBoolean(int index) throws Exception {
        return this.orig.getBoolean(index);
    }

    @Override
    public Date getDate(int index) throws Exception {
        return this.orig.getDate(index);
    }

    @Override
    public Double getDouble(int index) throws Exception {
        return this.orig.getDouble(index);
    }

    @Override
    public Integer getInt(int index) throws Exception {
        return this.orig.getInt(index);
    }

    @Override
    public String getString(int index) throws Exception {
        return this.orig.getString(index);
    }

    @Override
    public boolean nextInternal() throws Exception {
        while (this.orig.next()) {
            Date d = getDate(dateColumn);
            if (dateInRange(d,startDate,endDate)) {
                return true;
            }
            // if not in range, iterate again until we get a date that's in range
        }
        return false;
    }

    public void reset() {
        if (/*orig*/this.isResettable()) {
            orig.reset();
        } else {
            throw new RuntimeException("stream not resettable");
        }
    }
    /**
     * is the supplied date within the interval (startDate, endDate)?
     * null dates are considered in range 
     * @param d 
     * @param startDate
     * @param endDate
     * @return
     */
    private boolean dateInRange(Date d, Date startDate, Date endDate) {
        boolean ret = true;
        if (d != null) {
            if (startDate != null && d.before(startDate)) {
                ret = false;
            } else if (endDate != null && d.after(endDate)) {
                ret = false;
            }
        }
        return ret;
    }
}
