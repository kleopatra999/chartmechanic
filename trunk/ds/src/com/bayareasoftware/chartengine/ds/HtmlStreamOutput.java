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

import com.bayareasoftware.chartengine.model.DataType;

/**
 * output a stream of data into a HTML format
 */
public class HtmlStreamOutput extends StreamOutput {
    
    // these attrs are optional HTML attributes for <table>, <th>, <tr> and <td>
    protected String tableAttrs = null;
    protected String headerAttrs = null;
    protected String rowAttrs = null;
    protected String dataAttrs = null; 
    
    public HtmlStreamOutput(DataStream stream, Writer out) {
        super(stream, out);
    }
    
    @Override
    protected void outputRow() throws Exception {
        out.write("<tr ");
        if (rowAttrs != null)
            out.write(rowAttrs);
        out.write(">");
        for (int j = 1; j <= columnCount; j++) {
            out.write("<td ");
            if (dataAttrs != null) 
                out.write(dataAttrs);
            out.write(">");
            outputAtom(j);
            out.write("</td>");
        }
        out.write("</tr>\n");
    }
    
    @Override
    protected void outputHeader() throws IOException {
        out.write("<table ");
        if (tableAttrs != null)
            out.write(tableAttrs);
        out.write(">\n");
        int colCount = metadata.getColumnCount();
        for (int k = 1;k<=colCount;k++) {
            out.write("<th ");
            if (headerAttrs != null)
                out.write(headerAttrs);
            out.write(">");
            out.write(metadata.getColumnName(k));
            out.write("(");
            out.write(DataType.toString(metadata.getColumnType(k)));
            out.write(")</th>");
        }
    }
    
    @Override
    protected void outputFooter() throws IOException {
        out.write("</table>\n");
    }

    public String getTableAttrs() {
        return tableAttrs;
    }

    public void setTableAttrs(String tableAttrs) {
        this.tableAttrs = tableAttrs;
    }

    public String getHeaderAttrs() {
        return headerAttrs;
    }

    public void setHeaderAttrs(String headerAttrs) {
        this.headerAttrs = headerAttrs;
    }

    public String getRowAttrs() {
        return rowAttrs;
    }

    public void setRowAttrs(String rowAttrs) {
        this.rowAttrs = rowAttrs;
    }

    public String getDataAttrs() {
        return dataAttrs;
    }

    public void setDataAttrs(String dataAttrs) {
        this.dataAttrs = dataAttrs;
    }
}
