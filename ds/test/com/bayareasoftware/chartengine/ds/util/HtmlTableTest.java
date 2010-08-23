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

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import com.bayareasoftware.chartengine.ds.DSTestUtil;
import com.bayareasoftware.chartengine.ds.util.HtmlTable;
import com.bayareasoftware.chartengine.ds.util.HtmlTag;
import com.bayareasoftware.chartengine.ds.util.HtmlUtil;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.util.URLUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HtmlTableTest {
    
    @BeforeClass
    public static void initClass() {
        DSTestUtil.allowFiles();
    }
    private static final String CHARSET = "ISO-8859-1";
    
    public static final String TABLE1 = wrap(
        "<TABLE summary=\"This table charts the number of cups\n" +
        "                   of coffee consumed by each senator, the type \n" +
        "                   of coffee (decaf or regular), and whether \n" +
        "                   taken with sugar.\">\n" +
        "<CAPTION>Cups of coffee consumed by each senator</CAPTION>\n" +
        "<TR>\n" +
        "   <TH>Name</TH>\n" +
        "   <TH>Cups</TH>\n" +
        "   <TH>Type of Coffee</TH>\n" +
        "   <TH>Sugar?</TH>\n" +
        "<TR>\n" +
        "   <TD>T. Sexton</TD>\n" +
        "   <TD><a href=\"http://www.somewhere.com/\">10</a></TD>\n" +
        "   <TD>Espresso</TD>\n" +
        "   <TD>No</TD>\n" +
        "<TR>\n" +
        "   <TD>J. Dinnen</TD>\n" +
        "   <TD>5</TD>\n" +
        "   <TD>Decaf</TD>\n" +
        "   <TD>Yes</TD>\n" +
        "</TABLE>");

    public static final String TABLE2 = wrap(
        "<TABLE border=\"1\">\n" +
        "<COLGROUP>\n" +
        "<COL><COL align=\"char\" char=\".\">\n" +
        "<THEAD>\n" +
        "<TR><TH>Vegetable <TH>Cost per kilo\n" +
        "<TBODY>\n" +
        "<TR><TD>Lettuce        <TD>$1\n" +
        "<TR><TD>Silver carrots <TD>$10.50\n" +
        "<TR><TD>Golden turnips <TD>$100.30\n" +
        "</TABLE>");

    private static String wrap(String table) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
               "<HTML><HEAD><TITLE>Sample Table</TITLE></HEAD>\n" +
               "<BODY>\n" + table + "\n</BODY></HTML>\n";
    }

    static Node parseTable(String table) throws Exception {
        InputStream is = new ByteArrayInputStream(table.getBytes(CHARSET));
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        Document doc = tidy.parseDOM(is, null);
        Element e = doc.getDocumentElement();
        return e.getChildNodes().item(1).getChildNodes().item(0);
    }

    static String getContent(File f) throws Exception {
        FileReader rdr = new FileReader(f);
        StringBuilder sb = new StringBuilder();
        try {
            char[] buf = new char[1024];
            int r;
            while ((r = rdr.read(buf)) > 0) {
                sb.append(buf, 0, r);
            }
        } finally {
            rdr.close();
        }
        return sb.toString();
    }
    public static byte[] getBytes(String html) {
        try {
            return html.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    @Test
    public void testTable1() throws Exception {
        HtmlTable table = new HtmlTable(parseTable(TABLE1));
        List<String[]> data = table.getData();
        String[] header = data.get(0);
        assertEquals(4, header.length);     // 4 header columns
        assertEquals(3, data.size());       // 4 data rows, incl. header
        assertEquals(4, data.get(0).length);    // 4 data columns
        assertEquals("Name", header[0]);
        assertEquals("Sugar?", header[3]);
        assertEquals("10", data.get(1)[1]);
        assertEquals("5", data.get(2)[1]);
    }

    @Test
    public void testTable2() throws Exception {
        HtmlTable table = new HtmlTable(parseTable(TABLE2));
        List<String[]> data = table.getData();
        String[] header = data.get(0);
        assertEquals(2, header.length);
        assertEquals(4, data.size());
        assertEquals(2, data.get(0).length);
        assertEquals("$1", data.get(1)[1]);
        assertEquals("$100.30", data.get(3)[1]);
    }
    static final String[][] HTML_TITLES = {
        { "mlb-salaries.html", "MLB Baseball 2006 Salaries - CBSSports.com" },
        { "olympic-medals.html", "Medal Count - Yahoo! Sports coverage of the Summer Olympics in Beijing" },
        { "h41hist1.htm",
          "FRB: H.4.1 Historical - Table 1 - Overview and Memorandum Items (Weekly Averages) - November 13, 2008"
        },
        { "polb.com_teus_archive_since_1995.asp.html", "Port of Long Beach - TEUs Archive Since 1995" },
        { "continuum-builds.html", "Continuum - Continuum Web Builds" },
        { "vc-funding-stats.html", "Silicon Valley VC Funding CY00-to-date" }
    };
    @Test
    public void testDocumentTitle() throws Exception {
        for (String[] sa : HTML_TITLES) {
            File f = new File("test/data/" + sa[0]);
            //String mlbhtml = getContent(f);
            InputStream is = new FileInputStream(f);
            Node docNode = null;
            try {
                Document doc = HtmlUtil.getHtmlDocument(is);
                //docNode = doc.getDocumentElement();
                docNode = doc;
            } finally {
                is.close();
            }
            String title = HtmlUtil.getDocumentTitle(docNode);
            p("title for '" + sa[0] + "' is ###" + title + "###");
            assertEquals("expecting title for '" + sa[0] + "'", sa[1], title);
        }
    }
    @Test
    public void testSimpleMLB() throws Exception {
        File f = new File("test/data/mlb-salaries-simple.html");
        HtmlTable table = new HtmlTable(parseTable(getContent(f)));
        List<String[]> data = table.getData();
        assertEquals("2007 Team Payrolls", data.get(0)[0]);
        assertEquals("No.", data.get(1)[0]);
        assertEquals("1.", data.get(2)[0]);
        assertEquals( "San Francisco Giants", data.get(13)[1]);
        assertEquals(32, data.size());
        
        /*
        if (header == null) {
            p("header is NULL");
        } else {
            p("header has " + header.length + " columns");
        }
        int count = 0;
        for (String[] sa : data) {
            StringBuilder row = new StringBuilder();
            for (String s : sa) {
                row.append(s).append('|');
            }
            count++;
            System.out.println("#" + (count) + ") " + row);
        }
        */
    }
    @Test
    public void testFRB_H6() throws Exception {
        File f = new File("test/data/frb_h6.html");
        HtmlTable table = getTable(f.toURL().toString(), 0);//new HtmlTable(tableNodes.get(0));
        List<String[]> data = table.getData();
        DSTestUtil.printList(data);
        assertEquals(14, data.size());
        p("got " + data.size() + " rows");
        String firstDataCell = data.get(4)[0];
        int c = firstDataCell.charAt(0);
        p("first data cell: \"" + firstDataCell + "\" 1st char=" + c);
        //assertEquals(30, data.size());
        //assertEquals(data.get(11)[1], "San Francisco Giants");
        int count = 0;
        for (String[] sa : data) {
            StringBuilder row = new StringBuilder();
            for (String s : sa) {
                s = '"' + s + '"';
                row.append(s).append('|');
            }
            count++;
            System.out.println("#" + (count) + ") " + row);
        }
        DSTestUtil.printList(data);
    }
    @Test
    public void testFailedBanksRaw() throws Exception {
        
        File f = new File("test/data/failedbanklist.html");
        
        HtmlTable table = getTable(f.toURL().toString(), 7);
        //String[] header = table.getHeader();
        List<String[]> data = table.getData();
        assertEquals("Bank Name", data.get(0)[0]);
        assertEquals("Closing Date", data.get(0)[2]);
        assertEquals("Updated Date", data.get(0)[3]);
        
        //DSTestUtil.printList(data);
        assertEquals("New Frontier Bank, Greeley, CO", data.get(1)[0]);
        
        //DSTestUtil.printList(data);
    }
    
    private static HtmlTable getTable(String url, int index) throws Exception {
        Document doc = HtmlUtil.getHtmlDocument(
                URLUtil.openURL(url,null,null)
                );
        Node html = doc.getDocumentElement();
        if (HtmlTag.getTag(html) != HtmlTag.HTML) {
            throw new IllegalArgumentException("Not an HTML document");
        }
        Node body = HtmlUtil.getChild(html, HtmlTag.BODY);
        if (body == null) {
            throw new IllegalArgumentException("No HTML <BODY> tag found");
        }
        List<Node> tableNodes = HtmlUtil.getNodesByTag(body, HtmlTag.TABLE, null);
        if (tableNodes.size() == 0) {
            throw new RuntimeException("cannot find table in " + url);
        }
        HtmlTable table = new HtmlTable(tableNodes.get(index));
        return table;
    }
    private static void p(String s) {
        System.err.println("[HtmlTableTest] " + s);
    }
}
