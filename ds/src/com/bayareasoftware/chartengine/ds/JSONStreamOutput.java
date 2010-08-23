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
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

import com.bayareasoftware.chartengine.model.DataType;

public class JSONStreamOutput extends StreamOutput {

    public static final int VARIANT_JSON = 2;
    public static final int VARIANT_GVIS = 3;

    private Calendar cal;
    private int variant;
    
    public JSONStreamOutput(DataStream stream, Writer out, int variant) {
        super(stream, out /*,variant*/);
        cal = Calendar.getInstance();
        this.variant = variant;
    }
    @Override
    public int runStream(int maxRows) throws Exception {
        out.write("{\"table\": {\n");
        outputHeader();
        out.write(",\n\"rows\":[\n"); // comma after cols field        
        int r = 0;
        if (maxRows < 0 )
            maxRows = Integer.MAX_VALUE;
        while (stream.next() && r < maxRows) {
            if (r > 0) {
              out.write(",\n"); 
            }
            outputRow();
            r++;
        }
        out.write("]\n"); // close 'rows' outer array
        out.write("}\n"); // close 'table' tuple braces
        out.write("}\n"); // close {table
        
        out.flush();
        return r;
    }    
    @Override
    protected void outputRow() throws Exception {
        out.write(" [");
        for (int i = 1; i <= columnCount; i++) {
            outputAtom(i);
            if (i < columnCount) {
                out.write(',');
            }
        }
        out.write("]");
    }
    @Override
    protected void outputAtom(int index) throws Exception {
        int type = metadata.getColumnType(index);
        if (type == DataType.IGNORE) {
            return;
        }
        out.write("{\"v\":");
            String val = null;
            switch (type) {
            case DataType.BOOLEAN:
                Boolean b = stream.getBoolean(index);
                val = b != null ? "\"" + String.valueOf(b) + "\"" : "null"; 
                break;
            case DataType.DATE:
                val = getJSONDate(cal, stream.getDate(index));
                break;
            case DataType.DOUBLE:
                Double d = stream.getDouble(index);
                //val = d != null ? "\"" + String.valueOf(d) + "\"" : "null";
                val = d != null ?  String.valueOf(d) : "null";
                break;
            case DataType.INTEGER:
                Integer i = stream.getInt(index);
                //val = i != null ? "\"" + Integer.valueOf(i) + "\"" : "null";
                val = i != null ? String.valueOf(i) : "null";
                break;
            default:
                String s = stream.getString(index);
                val = s != null ? "\"" + s + "\"" : "null";
                
            }
            out.write(val);
            out.write('}');
    }
    @Override
    protected void outputHeader() throws IOException {
        //outputHeader(md, out);
        out.write("\"cols\": [\n");
        for (int i = 1; i <= columnCount; i++) {
            String name = metadata.getColumnName(i);
            int type = metadata.getColumnType(i);
            String desc = metadata.getColumnDescription(i);
            if (desc == null || desc.length() == 0) {
                desc = name;
            }
            out.write(" {\"id\":\"" + name + "\",\"label\":\"" + desc + "\",\"type\":\"" + getVisType(type)+ "\"}");
            if (i != columnCount) {
                out.write(",");
            }
            out.write('\n');
        }
        out.write(']');
    }

    /**
     * http://code.google.com/apis/visualization/documentation/reference.html#QueryResponse
     */
    protected static String getVisType(int dataType) {
        String ret = "t"; // 'text'
        switch (dataType) {
        case DataType.DOUBLE:
        case DataType.INTEGER:
            ret = "n";
            break;
        case DataType.BOOLEAN:
            ret = "b";
            break; // FIXME: not sure if right
        case DataType.DATE:
            ret = "d";
            break;
        // FIXME: also handle datetime, timeofday
        }
        return ret;
    }
    protected String getJSONDate(Calendar cal, Date d) {
        if (d == null) { return "null"; }
        StringBuilder sb = new StringBuilder();
        if (variant == VARIANT_GVIS) {
            cal.setTime(d);
            sb.append("new Date(").append(cal.get(Calendar.YEAR))
            .append(',').append(cal.get(Calendar.MONTH)).append(',')
            .append(cal.get(Calendar.DAY_OF_MONTH)).append(')');
        } else {
            sb.append("\"" + d.getTime()+ "\"");
        }
        return sb.toString();
    }    
}
