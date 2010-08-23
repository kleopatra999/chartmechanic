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

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.bayareasoftware.chartengine.ds.ExcelDataSource;
import com.bayareasoftware.chartengine.ds.ExcelDataStream;
import com.bayareasoftware.chartengine.model.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;

/**
 * Create an HTML table representing POI's view of an excel spreadsheet,
 * the types and contents of each cell....
 * @author David Brown
 *
 */
public class ExcelDump {
    
    public static void main(String[] a) throws Exception {
        if (a.length != 1) {
            System.err.println("usage: ExcelDataSource <xls file>");
            System.exit(1);
        }
        //runNew(a[0]);
        runOld(a[0]);
    }
    private static void runNew(String fileName) throws Exception {
        InputStream inp = new FileInputStream(fileName);
        HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
        ExcelExtractor xt = new ExcelExtractor(wb);

        xt.setFormulasNotResults(false);
        xt.setIncludeBlankCells(true);
        xt.setIncludeSheetNames(false);
        String text = xt.getText();
        String[] lines = StringUtil.splitCompletely(text, '\n');
        for (int i = 0; i < lines.length; i++) {
            System.out.println((i+1) + ") " + lines[i]);
        }
        System.out.println("XLS: \n" + text);
    }
    private static void runOld(String fileName) throws Exception {
        InputStream is = new FileInputStream(fileName);
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        p("first/last row: " + firstRow + "/" + lastRow);
        HSSFRow[] rows = new HSSFRow[lastRow + 1];
        int maxFirstCell = 0, maxLastCell = 0;
        for (int i = firstRow; i <= lastRow; i++) {
            HSSFRow r = sheet.getRow(i);
            if (r != null) {
                rows[i] = r;
                maxFirstCell = Math.max(maxFirstCell, r.getFirstCellNum());
                maxLastCell = Math.max(maxLastCell, r.getLastCellNum());
                
            }
        }
        p("maxFirstCell=" + maxFirstCell + ", maxLastCell=" + maxLastCell);
        
        StringBuilder table = new StringBuilder();
        table.append("<html><head><style>\n");
        table.append("body,td { font-family: monospaced; font-size: 12 }\n");
        table.append("</style></head>");
        table.append("<p>maxFirstCell=" + maxFirstCell + " maxLastCell=" + maxLastCell + "</p>");
        table.append("<table border=\"1\">");
        for (int i = firstRow; i <= lastRow; i++) {
            HSSFRow r = sheet.getRow(i);
            if (r == null) {
                System.err.println("NULL row at " + i);
            }
            table.append(row2string(r, maxLastCell));
        }
        table.append("</table></body></html>");
        File f = new File("sheet.html");
        Writer w = new FileWriter(f);
        w.write(table.toString());
        w.close();
        p("saved to " + f.getAbsolutePath());
    }

    private static String row2string(HSSFRow r, int maxCell) {
        if (r == null) { return ""; }
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<td>#<b>" + r.getRowNum() + "</b> phys="
                + r.getPhysicalNumberOfCells() + "<br/>1st="
                + r.getFirstCellNum() + " last=" + r.getLastCellNum()
                + "</td>");
        for (short i = 0; i < maxCell; i++) {
            HSSFCell c = r.getCell(i);
            sb.append("<td>" + cell2string(c) + "</td>");
        }
        sb.append("</tr>\n");
        return sb.toString();
    }
    private static String cell2string(HSSFCell c) {
        if (c == null) {
            return "<i>NULL CELL</i>";
        }
        int type = c.getCellType();
        String t = null, v = null;
        switch (type) {
        case HSSFCell.CELL_TYPE_BLANK:
            t = "BLANK";
            v = "";
            break;
        case HSSFCell.CELL_TYPE_STRING:
            t = "STRING";
            v = c.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (ExcelInference.isCellDateFormatted(c)) {
                t = "DATE";
                v = c.getDateCellValue() + "";
            } else {
                t = "NUMERIC";
                v = c.getNumericCellValue() + "";
            }
            break;
        case HSSFCell.CELL_TYPE_ERROR:
            t = "ERROR";
            v = "(errByte=" + c.getErrorCellValue() + "/toString=" + c + ")";
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            t = "FORMULA";
            v = c.getCellFormula();
            break;
        default:
            t = "(UNKNOWN TYPE: " + type + ")";
            v = c.toString();
            break;
        }
        short style = c.getCellStyle().getDataFormat();
        return v + "<br/>(" + t + ")<br/>dataformat=0x" + Integer.toHexString(style);
    }
    private static void p(String s) {
        System.out.println(s);
    }
}
