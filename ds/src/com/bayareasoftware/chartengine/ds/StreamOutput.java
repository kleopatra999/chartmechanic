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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.StringUtil;
import com.bayareasoftware.chartengine.util.DateUtil;

/**
 * StreamOutput outputs DataStreams into Writers in a textual format.
 * It outputs the header of the DataStream first, then each row, with each column
 * value separated by a COLUMN_DELIMITER (default is comma), and each row by a ROW_DELIMITER
 * (default is newline).  With the default settings, the output looks a CSV format
 * 
 * the output uses the format strings in the metadata when columns have format strings 
 * 
 * Subclasses should override outputHeader(), outputRow(), outputFooter() accordingly
 * 
 */
public class StreamOutput {

    protected DataStream stream;
    protected Metadata metadata;
    protected int columnCount;
    protected Writer out;
    protected DateFormat defaultDateFmt;
    protected DecimalFormat defaultDecimalFmt;
    
    protected String columnDelimiter = ",";
    protected String rowDelimiter = "\n";
    protected String defaultDateFormat = "yyyy-MM-dd";
    
    protected String defaultNumberFormat = "###.##";
    
    public StreamOutput(DataStream stream, Writer out) {
        this.stream = stream;
        this.out = out;
        this.metadata = stream.getMetadata();
        columnCount = metadata.getColumnCount();
        defaultDateFmt = new SimpleDateFormat(defaultDateFormat);
        defaultDecimalFmt = new DecimalFormat(defaultNumberFormat);
    }

    /**
     * run the stream up until the end
     * @return number of rows produced
     * @throws Exception
     */
    public int runStream() throws Exception {
        return runStream(-1);
    }
    
    /** 
     * run the stream up to maxRows number of rows
     * @param maxRows  - if negative, runs to end of stream
     * @throws Exception
     * @return number of rows produced
     */
    public int runStream(int maxRows) throws Exception {
        outputHeader();
        if (maxRows < 0)
            maxRows = Integer.MAX_VALUE;
        int r = 0;
        while (stream.next() && r < maxRows) {
            outputRow();
            r++;
        }
        outputFooter();
        out.flush();
        return r;
    }
    
    
    protected void outputRow() throws Exception {
        for (int i = 1; i <= columnCount; i++) {
            if (metadata.getColumnType(i) != DataType.IGNORE) {
                outputAtom(i);
                if (i < columnCount) { out.write(columnDelimiter); }
            }
        }
        out.write(rowDelimiter);
    }
    protected void outputAtom(int index) throws Exception {
        int dataType = metadata.getColumnType(index);
        switch (dataType) {
        case DataType.BOOLEAN:
            Boolean b = stream.getBoolean(index);
            if (b != null) {
                if (b) {
                    out.write("true");
                } else {
                    out.write("false");
                }
            }
            break;
        case DataType.INTEGER:
            Integer i = stream.getInt(index);
            if (i != null) {
                out.write(String.valueOf(i));
            }
            break;
        case DataType.DOUBLE:
            Double d = stream.getDouble(index);
            if (d != null) {
                String fmt = StringUtil.trim(metadata.getColumnFormat(index));
                DecimalFormat decFmt;
                if (fmt != null)
                    decFmt = new DecimalFormat(fmt);
                else
                    decFmt = defaultDecimalFmt;
                out.write(decFmt.format(d));
            }
            break;
        case DataType.STRING:
            String s = stream.getString(index);
            if (s != null) {
                out.write(s);
            }
            break;
        case DataType.DATE:
            Date date = stream.getDate(index);
            if (date != null) {
                DateFormat dateFmt = defaultDateFmt;
                String fmt = StringUtil.trim(metadata.getColumnFormat(index));
                if (fmt != null &&
                        !fmt.equals(Metadata.INTERNAL_DATE_FORMAT)) {
                    dateFmt = DateUtil.createDateFormat(fmt);
//                   dateFmt = new SimpleDateFormat(fmt);
                }
                String val = dateFmt.format(date);
                out.write(val);
            }
            break;
        }
    }
    
    protected void outputHeader() throws IOException {
        for (int i = 1; i <= columnCount; i++) {
            String name = metadata.getColumnName(i);
            if (name != null)
                out.write(name);
            else
                out.write("");
            if (i < columnCount) {
                out.write(columnDelimiter);
            }
        }
        out.write(rowDelimiter);
    }
 
    // subclass can override this to do something at the end of the stream
    protected void outputFooter() throws IOException {
        
    }
    
    // close the stream and the writer 
    public void close() throws IOException {
        if (stream != null)
            stream.close();
        if (out !=null) {
            out.close();
        }
    }

}
