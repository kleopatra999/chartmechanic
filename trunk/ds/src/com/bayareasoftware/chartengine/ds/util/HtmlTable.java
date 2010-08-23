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
package com.bayareasoftware.chartengine.ds.util;

import static com.bayareasoftware.chartengine.ds.util.HtmlTag.TABLE;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.TBODY;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.TD;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.TH;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.THEAD;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.TR;
import static com.bayareasoftware.chartengine.ds.util.HtmlTag.getTag;
import static com.bayareasoftware.chartengine.ds.util.HtmlUtil.countChildNodes;
import static com.bayareasoftware.chartengine.ds.util.HtmlUtil.getChild;
import static com.bayareasoftware.chartengine.ds.util.HtmlUtil.getChildren;
import static com.bayareasoftware.chartengine.ds.util.HtmlUtil.getText;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;


/**
 * Utility class for parsing HTML table element.
 *
 * TODO Handle table directionality
 */
public class HtmlTable {
    private String[] header;
    private List<String[]> data;
    /* the 'id' attribute of the HTML table, or null if not present */
    private String tagId;
    // number of rows in actual table, if our maxRows
    // param capped how many rows to fetch
    private int totalRows = -1;
    
    public int getTotalRows() {
        return totalRows == -1 ? this.getRowCount() : totalRows;
    }
    public String getTagId() { return tagId; }
    
    public HtmlTable(Node table) {
        this(table,Integer.MAX_VALUE);
    }

    public HtmlTable(Node table, int maxRows) {
        parse(table,maxRows);
    }
    /*
    public String[] getHeader() {
        return header;
    }
*/
    /**
     * returns true if this table has some non-null data
     * @return
     */
//    public boolean hasData() {
//        if (totalRows > 0 && data != null) {
//            for (String row[] : data) {
//                for (String s : row) {
//                    if (s != null && !s.trim().equals("")) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
    /**
     * @return the number of rows of data we actually have,  may be less than 
     * the totalRows since we may not have fetched everything
     */
    public int getRowCount() {
        int ret = 0;
        if (data != null) {
            ret = data.size();
        }
        return ret;
    }
    public List<String[]> getData() {
        return data;
    }

    private void parse(Node table, int maxRows) {
        if (getTag(table) != TABLE) {
            throw new IllegalArgumentException(
                "Not a TABLE element: " + table.getNodeName());
        }
        this.tagId = HtmlUtil.getAttribute(table, "id");
        List<Node> nodes = getChildren(table);
        header = parseHeader(nodes);
        data = parseData(nodes, maxRows,header);
        Node tbody = getChild(table, TBODY);
        if (tbody != null) {
            // if the first child is a tbody, look inside it
            table = tbody;
        }
        if (maxRows < Integer.MAX_VALUE) {
            this.totalRows = countChildNodes(table, TR);
        }
    }

    private String[] parseHeader(List<Node> nodes) {
        Node thead = findNode(nodes, THEAD);
        if (thead != null) {
            nodes = getChildren(thead);
            Node tr = findNode(nodes, TR);
            return tr != null ? parseTH(getChildren(tr)) : null;
        }
        return null;
    }

    private List<String[]> parseData(List<Node> nodes, int maxRows, String[] headers) {
        Node tbody = findNode(nodes, TBODY);
        if (tbody != null) {
            nodes = getChildren(tbody);
        }
        int size = Math.min(maxRows, nodes.size());
        List<String[]> data = new ArrayList<String[]>(size);
        if (headers != null) {
            data.add(headers);
        }
        for (Node node : nodes) {
            if (getTag(node) == TR) {
                String[] row = parseRow(getChildren(node));
                if (row != null) {
                    data.add(row);
                    if (data.size() >= maxRows) {
                        break;
                    }
                }
            }
        }
        return data;
        //return data.toArray(new String[data.size()][]);
    }

    private static String[] parseTH(List<Node> nodes) {
        List<String> header = new ArrayList<String>(nodes.size());
        for (Node node : nodes) {
            HtmlTag tag = getTag(node);
            if (tag != TH) return null;
            header.add(getText(node));
        }
        return header.toArray(new String[header.size()]);
    }

    private static String[] parseRow(List<Node> nodes) {
        List<String> data = new ArrayList<String>(nodes.size());
        for (Node node : nodes) {
            HtmlTag tag = getTag(node);
            if (tag == TD || tag == TH) data.add(getText(node));
        }
        int size = data.size();
        return size > 0 ? data.toArray(new String[size]) : null;
    }
    
    private static Node findNode(List<Node> nodes, HtmlTag tag) {
        for (Node node : nodes) {
            if (getTag(node) == tag) return node;
        }
        return null;
    }
    
    
}

