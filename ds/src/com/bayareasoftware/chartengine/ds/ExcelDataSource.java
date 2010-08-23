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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bayareasoftware.chartengine.ds.util.XLS2Data;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.util.URLUtil;

/**
 * Support for Microsoft Excel spreadsheet data source.
 */
public class ExcelDataSource extends DataSource {
    private final DataSourceInfo ei;
    public ExcelDataSource(DataSourceInfo ei) {
        this.ei = ei;
    }
                                                       
    public static List<String[]> getRawStrings(InputStream is, int maxrows, String sheetId) throws Exception {
        XLS2Data xd = new XLS2Data(is,sheetId,maxrows);
        xd.process();
        return xd.getData();
    }

    public static HSSFWorkbook getWorkbook(InputStream is) throws IOException {
        try {
            HSSFWorkbook wbk = new HSSFWorkbook(is);
            rewriteFormulas(wbk);
            return wbk;
        } finally {
            is.close();
        }
    }
    public static HSSFSheet getSheet(HSSFWorkbook wb, String name) {
        HSSFSheet sheet = name != null ? wb.getSheet(name) : wb.getSheetAt(0);
        if (sheet == null) {
            String s = name != null ? "name '" + name + "'" : "";
            throw new IllegalArgumentException(
                "No worksheet " + s + "found in spreadsheet");
        }
        return sheet;
    }

    public DataStream getDataStream() throws Exception {
        DataStream ds = null;
        String sheetName = ei.getProperty(DataSourceInfo.SPREADSHEET_NAME);
        InputStream is = URLUtil.openURL(ei.getUrl(), ei.getUsername(), ei.getPassword());
        List<String[]> data = getRawStrings(is,Integer.MAX_VALUE,sheetName);
        if (ei.isRowInverted()) {
            data = RawData.invert(data);
        }
        Metadata md = ei.getInputMetadata();
        int dataStart = ei.getDataStartRow();
        int dataEnd = ei.getDataEndRow();
        ds = new StringDataStream(data,md,dataStart,dataEnd);
        ds = this.evalStreamScript(ds);
        return ds;
    }    
    /*
    public DataStream getDataStream() throws Exception {
        HSSFSheet sheet = getSheet(wb, ei.getProperty(DataSourceInfo.SPREADSHEET_NAME));
        return this.evalStreamScript(new ExcelDataStream(ei, wb, sheet, ei.getInputMetadata()));
    }*/
    
    /**
     * work around bug with named cells
     * 
     */
    private static void rewriteFormulas(final HSSFWorkbook workbook) {
        //if (true) return;
        // build up a cache of names
        // this is just an easy way of fetching the HSSFName based on the string
        // representation of the name
        final Map<String, HSSFName> nameCache = new HashMap<String, HSSFName>(
                workbook.getNumberOfNames());
        for (int i = 0; i < workbook.getNumberOfNames(); i++) {
            final HSSFName name = workbook.getNameAt(i);
            nameCache.put(name.getNameName(), name);
        }
        // remove all the sheet names from the name references, having the sheet
        // names around messes up the formulas
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            nameCache.remove(workbook.getSheetName(i));
        }
        //p("Names: " + nameCache.keySet());

        // loop over all the cells and rewrite the formula ones
        for (int sheetCount = 0; sheetCount < workbook.getNumberOfSheets();
    sheetCount++) {
            final HSSFSheet sheet = workbook.getSheetAt(sheetCount);
            for (final Iterator rowIterator = sheet.rowIterator(); rowIterator
                    .hasNext();) {
                final HSSFRow row = (HSSFRow) rowIterator.next();
                for (final Iterator cellIterator = row.cellIterator(); cellIterator
                        .hasNext();) {
                    final HSSFCell cell = (HSSFCell) cellIterator.next();
                    if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                        String formula = cell.getCellFormula();
                        for (final String name : nameCache.keySet()) {
                            final Pattern pattern = Pattern.compile("(\\W|^)"
                                    + name + "(\\W|$)",
                                    Pattern.CASE_INSENSITIVE);
                            final HSSFName hssfName = nameCache.get(name);
                            formula = pattern.matcher(formula).replaceAll(
                                    "$1"
                                            + hssfName.getReference().replace(
                                                    "$", "\\$") + "$2");
                        }
                        //p("Resetting Cell (" + cell.toString()
                          //      + ") Formula:" + formula);
                        cell.setCellFormula(formula);
                    } // end if
                } // end for
            } // end for
        } // end for
    }    
}
