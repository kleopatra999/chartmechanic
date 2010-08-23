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

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import com.bayareasoftware.chartengine.ds.util.DataStreamUtil;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StandardProps;

public class JoinTimeStream extends DataStream {

    private StreamHolder[] streams;
    private final HashMap<Integer,ColEntry> cols;
    private Date minDate;
    public JoinTimeStream(DataStream[] s, int[] indexes) {
        super(false);
        cols = new HashMap<Integer,ColEntry>();
        streams = new StreamHolder[s.length];
        int totalCols = 1;
        for (int i = 0; i < s.length; i++) {
            streams[i] = new StreamHolder(s[i], indexes[i]);
            //pe("stream[" + i + "] has " + streams[i].md.getColumnCount() + " cols");
            totalCols += (streams[i].md.getColumnCount() - 1); 
        }
        //pe("total columns " + totalCols);
        this.metadata = new Metadata(totalCols);
        metadata.setColumnType(1, DataType.DATE);
        metadata.setColumnName(1, "DATE");
        metadata.setColumnFormat(1,Metadata.INTERNAL_DATE_FORMAT);
        int col = 1;
        for (int i = 0; i < s.length; i++) {
            StreamHolder sh = streams[i];
            //pe("stream[" + i + "] has dateCol=" + sh.dateCol);
            for (int j = 1; j <= sh.md.getColumnCount(); j++) {
                if (j == sh.dateCol) {
                    continue;
                }
                col++;
                //pe("on col " + col + " (" + streams[i].md.getColumnName(j) + ")");
                cols.put(col, new ColEntry(streams[i], j));
                metadata.setColumnType(col, sh.md.getColumnType(j));
                metadata.setColumnName(col, sh.md.getColumnName(j));
                metadata.setColumnDescription(col, sh.md.getColumnDescription(j));
            }
        }
    }
    
    private static class ColEntry {
        StreamHolder sh;
        int index;
        ColEntry(StreamHolder sh, int index) {
            this.sh = sh;
            this.index = index;
        }
        public Boolean getBoolean() throws Exception {
            if (sh.isActive()) {
                return sh.stream.getBoolean(index);
            }
            return false;
        }

        public Date getDate() throws Exception {
            if (sh.isActive()) {
                return sh.stream.getDate(index);
            }
            return null;
        }

        public Double getDouble() throws Exception {
            if (sh.isActive()) {
                return sh.stream.getDouble(index);
            }
            return null;
        }

        public Integer getInt() throws Exception {
            if (sh.isActive()) {
                return sh.stream.getInt(index);
            }
            return 0;
        }

        public String getString() throws Exception {
            if (sh.isActive()) {
                return sh.stream.getString(index);
            }
            return null;
        }

        /*
        public boolean isNull() throws Exception {
            if (sh.isActive()) {
                return sh.stream.isNull(index);
            }
            return true;
        }*/

    }
    
    private ColEntry getCol(int index) {
        ColEntry ret = cols.get(index);
        if (ret == null) {
            throw new IllegalArgumentException(
                    "column index out of range: " + index
                    );
        }
        return ret;
    }
    @Override
    public Boolean getBoolean(int index) throws Exception {
        return getCol(index).getBoolean();
    }

    @Override
    public Date getDate(int index) throws Exception {
        if (index == 1) {
            return minDate;
        }
        return getCol(index).getDate();
    }

    @Override
    public Double getDouble(int index) throws Exception {
        return getCol(index).getDouble();
    }

    @Override
    public Integer getInt(int index) throws Exception {
        return getCol(index).getInt();
    }

    @Override
    public String getString(int index) throws Exception {
        if (index == 1) {
            return minDate != null ? minDate.toString() : null;
        }
        return getCol(index).getString();
    }

    /*@Override
    public boolean isNull(int index) throws Exception {
        if (index == 1) {
            return minDate == null;
        }
        ColEntry col = getCol(index);
        if (col.sh.isEmpty()) {
            return true;
        }
        return col.isNull();
    }*/

    @Override
    public boolean nextInternal() throws Exception {
        for (int i = 0; i < streams.length; i++) {
            if (streams[i].isActive()) {
                streams[i].next();
            }
        }
        minDate = getMinDate();
        if (minDate == null) {
            return false;
        }
        for (int i = 0; i < streams.length; i++) {
            if (streams[i].isEmpty()) {
                streams[i].active = false;
                continue;
            }
            Date d = streams[i].getCurrentDate();
            if (d == null) {
                // FIXME: null dates have to be active
                // otherwise we'll never advance the stream
                // this messes up the notion of "join-by-date" though
                streams[i].active = true;
            } else if (datesEqual(minDate, d)) {
                streams[i].active = true;
            } else {
                streams[i].active = false;
            }
        }
        return true;
    }
    
    @Override
    public void close() {
        for (int i = 0; i < streams.length; i++) {
            try {
                streams[i].stream.close();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }
    // FIXME: allow for "close-enough" tolerance threshold
    private boolean datesEqual(Date d1, Date d2) {
        return d1.equals(d2);
    }
    private Date getMinDate() throws Exception {
        Date ret = null;
        for (int i = 0; i < streams.length; i++) {
            if (ret == null) {
                ret = streams[i].getCurrentDate();
            } else {
                Date d = streams[i].getCurrentDate();
                if (d != null && d.before(ret)) {
                    ret = d;
                }
            }
        }
        return ret;
    }
    private static class StreamHolder {
        DataStream stream;
        int dateCol;
        Metadata md;
        boolean empty = false;
        Date date;
        boolean active = true;
        StreamHolder(DataStream stream, int dc) {
            this.stream = stream;
            this.dateCol = dc;
            md = stream.getMetadata();
            if (dateCol <= 0) {
                // assume we should use the first date column
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    if (DataType.DATE == md.getColumnType(i)) {
                        dateCol = i;
                        break;
                    }
                }
                if (dateCol <= 0) {
                    throw new IllegalArgumentException(
                            "no date column in stream: " + md
                            );
                }
            }
            {
                // test type
                int type = md.getColumnType(dateCol);
                if (type != DataType.DATE) {
                    throw new IllegalArgumentException(
                            "expected type DATE for column index " + dateCol
                            + " of " + md + " not " + DataType.toString(type)
                            );
                }
            }
        }
        Date getCurrentDate() throws Exception {
            if (date != null) {
                return date;
            }
            if (isEmpty()) {
                return null;
            }
            /*if (stream.isNull(dateCol)) {
                return null;
            }*/
            date = stream.getDate(dateCol);
            return date;
        }
        boolean isEmpty() { return empty; }
        boolean isActive() { return active; }
        boolean next() throws Exception {
            date = null;
            active = false;
            boolean ret = false;
            if (!empty) {
                ret = stream.next();
                if (!ret) {
                    empty = true;
                }
            }
            return ret;
        }
    }
    
    public static void main(String[] a) throws Exception {
        DataSourceInfo info = DataInference.get().inferFromURL(new File("test/data/brokerage.csv").toURL().toString(), -1).getDataSource();
        DataStream stream1 = (new CSVDataSource(info)).getDataStream();
        
        p("Metadata for stream1: " + stream1.getMetadata());
        //DataStreamUtil.dump(System.out, stream1);
        //JdbcDataSource jds = new JdbcDataSource("org.postgresql.Driver","jdbc:postgresql://localhost/cm_data_test","bt","");
        String table = "frbg20_owned_dtros_n_m"; //  Single-family Real Estate Loans Owned by Finance Companies, monthly
        
        SimpleProps props = new SimpleProps();
        props.put(StandardProps.JDBC_DRIVER,"org.postgresql.Driver");
        props.put(StandardProps.JDBC_URL,"jdbc:postgresql://localhost/cm_data_test");
        props.put(StandardProps.JDBC_USERNAME,"bt");
        props.put(StandardProps.JDBC_PASSWORD,"");
        props.put(DataSourceInfo.TABLE_NAME,table);
        String query = "SELECT D, OBS FROM " + table + " ORDER BY D";
        JdbcDataSource jds = new JdbcDataSource(props,query);
        try {
            //DataStream stream2 = jds.executeQuery("SELECT D, OBS FROM " + table + " ORDER BY D");
            DataStream stream2 = jds.executeQuery();
            DataStream[] dss = new DataStream[2];
            dss[0] = stream1;
            dss[1] = stream2;
            int[] indices = new int[2];
            JoinTimeStream jts = new JoinTimeStream(dss, indices);
            DataStreamUtil.dump(System.out, jts);
        } finally {
            jds.close();
        }
    }
    private static void pe(String s) {
        System.err.println("[JoinTimeStream] " + s);
    }
    private static void p(String s) {
        System.out.println(s);
    }
}
