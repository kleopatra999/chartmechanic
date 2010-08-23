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

import static com.bayareasoftware.chartengine.model.DataType.*;

import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents chart data.
 */
public abstract class DataStream {
    protected Metadata metadata;

    protected boolean resetable = false;
    
//    private DataStream(Metadata md) {
//        metadata = md;
//    }
//
    
    protected DataStream(boolean isresetable) {
        this.resetable = isresetable;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    protected void setMetadata(Metadata md) {
        this.metadata = md;
    }
    
    /**
     * can this datastream be reset
     * @return
     */
    public boolean isResettable() {
        return resetable;
    }

    /**
     * subclasses should override this method to reset the stream and return isResetable to be true
     * if they can be reset
     * 
     * for datastreams that are not resettable, than reset is a no-op
     */
    public void reset() {
        if (isResettable()) {
            throw new RuntimeException("resettable, but not overridden for " + getClass().getName());
        }
    }
    
    public final boolean next() throws Exception {
        boolean ret = nextInternal();
        return ret;
    }
    public abstract boolean nextInternal() throws Exception;

    public abstract String getString(int index) throws Exception;

    public abstract Integer getInt(int index) throws Exception;
    
    public abstract Double getDouble(int index) throws Exception;

    public abstract Boolean getBoolean(int index) throws Exception;

    public abstract Date getDate(int index) throws Exception;
    
    //public abstract boolean isNull(int index) throws Exception;

    public Object getObject(int index) throws Exception {
        if (metadata == null) return getString(index);
        //else if (isNull(index)) return null;
        switch (metadata.getColumnType(index)) {
        case INTEGER:
            return getInt(index);
        case DOUBLE:
            return getDouble(index);
        case DATE:
            return getDate(index);
        case BOOLEAN:
            return getBoolean(index);
        default:
            return getString(index);
        }
    }
    
    protected Integer parseInt(String s, int row, int col) {
        Integer i = null;
        if (s != null && (s = s.trim()).length() > 0) {
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException nfe) {
                // FIXME: issue warning about unparseable int
            }
        }
        return i;
    }

    protected Double parseDouble(String s, int row, int col) {
        Double d = null;
        if (s != null && (s = s.trim()).length() > 0) {
            try {
                d = Double.parseDouble(s);
            } catch (NumberFormatException nfe) {
                // FIXME: issue warning about unparseable double
            }
        }
        return d;
    }
    protected Boolean parseBoolean(String s, int row, int col) {
        Boolean b = null;
        if (s != null) {
            if (s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on")) {
                b = Boolean.TRUE;
            }
            if (s.equalsIgnoreCase("N") || s.equalsIgnoreCase("no") || s.equalsIgnoreCase("off")) {
                b = Boolean.FALSE;
            }
        }
        if (b == null && s != null) {
            b = Boolean.parseBoolean(s);
        }
        return b;
    }
    protected Date parseDate(String s, DateFormat fmt, int row, int col) {
        Date ret = null;
        if (s != null && (s = s.trim()).length() > 0) {
            try {
                if (fmt != null) {
                    ret = fmt.parse(DateUtil.removeNanos(s));
                } else {
                    ret = DateUtil.parseDate(s);                    
                }
            } catch (java.text.ParseException pe) {
                // FIXME: issue warning about unparseable date
            }
        }
        return ret;
    }
    public void close() {}
}
