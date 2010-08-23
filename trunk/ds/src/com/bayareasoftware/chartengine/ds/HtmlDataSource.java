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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import com.bayareasoftware.chartengine.ds.util.HtmlTable;
import com.bayareasoftware.chartengine.ds.util.HtmlTag;
import com.bayareasoftware.chartengine.ds.util.HtmlUtil;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.util.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HtmlDataSource extends DataSource {
    private final StringDataStream dataStream;
    private static Log log = LogFactory.getLog(HtmlDataSource.class);

    public HtmlDataSource(DataSourceInfo hi) throws IOException {
        HtmlTable htmlTable = getTable(hi,-1);
        List<String[]> data = htmlTable.getData();
        if (hi.isRowInverted()) {
            data = RawData.invert(data);
        }
        dataStream = new StringDataStream(data, hi.getInputMetadata(),
                hi.getDataStartRow(), hi.getDataEndRow());
    }
    
    
    private static HtmlTable getTable(DataSourceInfo hi, int maxRows) throws IOException {
        if (maxRows < 1)
            maxRows = Integer.MAX_VALUE;
        try {    
            Document doc = HtmlUtil.getHtmlDocument(
                    URLUtil.openURL(hi.getUrl(), hi.getUsername(), hi.getPassword())
                    );
            String tid = hi.getProperty(DataSourceInfo.HTML_TABLEID);
            //return getTable(doc, tid, maxRows, hi.getUrl());
            HtmlTable result = HtmlUtil.getTable(doc,tid,maxRows);
            if (result == null) {
                throw new IllegalArgumentException("No matching table found for id='" + tid + "' at url " + hi.getUrl()
                );
            }
            return result;
        }  catch (IOException ioe) {
            log.warn("failed to read html document at:" + hi.getUrl() + " for datasource: " + hi.getId());
            throw ioe;
        }
    }
    
//    private static HtmlTable getTable(Document doc, String tid, int maxRows, String url) {
//        Node html = doc.getDocumentElement();
//        if (HtmlTag.getTag(html) != HtmlTag.HTML) {
//            throw new IllegalArgumentException("Not an HTML document");
//        }
//        Node body = HtmlUtil.getChild(html, HtmlTag.BODY);
//        if (body == null) {
//            throw new IllegalArgumentException("No HTML <BODY> tag found");
//        }
//        List<Node> tableNodes = HtmlUtil.getNodesByTag(body, HtmlTag.TABLE, null);
//        Node table = getTable(tableNodes, tid);
//        if (table == null) {
//            int tableNum = -1;
//            // try to take the tableID as an integer, and if it's a
//            // number 'N', take the Nth table on the page, via depth
//            // first search...
//            try {
//                tableNum = Integer.parseInt(tid);
//            } catch (NullPointerException ignore) {
//            } catch (NumberFormatException ignore) {
//            }
//            if (tableNum > -1 && tableNum < tableNodes.size()) {
//                table = tableNodes.get(tableNum); 
//            }
//        }
//        if (table == null) {
//            throw new IllegalArgumentException(
//                    "No matching table found for id='" + tid + "'"
//                    + " at url " + url
//                    );
//        }
//        HtmlTable htmlTable = new HtmlTable(table,maxRows);
//        return htmlTable;
//    }

    /** get data as Strings, with no type coercion */
    public static List<String[]> getRawData(DataSourceInfo hi, int maxRows) throws IOException {
        HtmlTable table = getTable(hi,maxRows);
        return table.getData();
    }
    
    public static List<String[]> getRawStrings(Document doc, RawData rd, int maxRows) {
        //HtmlTable table = getTable(doc, rd.getProperty(DataSourceInfo.HTML_TABLEID),maxRows, rd.url);
        HtmlTable table = HtmlUtil.getTable(doc, rd.getProperty(DataSourceInfo.HTML_TABLEID),maxRows);
        if (table != null) {
            return table.getData();
        }
        return null;
    }
//    private static Node getTable(List<Node> nodes, String id) {
//        for (Node node : nodes) {
//            if (isTable(node, id)) return node;
//        }
//        return null;
//    }

//    private static boolean isTable(Node node, String id) {
//        return HtmlTag.getTag(node) == HtmlTag.TABLE &&
//            (id == null || id.equals(HtmlUtil.getAttribute(node, "id")));
//    }
//    
    public DataStream getDataStream() throws Exception {
        dataStream.reset();
        return this.evalStreamScript(dataStream);
    }

}
