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

import static com.bayareasoftware.chartengine.model.DataType.DOUBLE;
import static com.bayareasoftware.chartengine.model.DataType.INTEGER;
import static com.bayareasoftware.chartengine.model.DataType.UNKNOWN;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bayareasoftware.chartengine.ds.ExcelDataSource;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.StringUtil;

import static com.bayareasoftware.chartengine.model.DataType.*;


/**
 * Special class to do inference of Excel spreadsheets
 * We can, in general, do better than TypeInspector
 * in guessing types an structure, since XLS cells are typed
 * @author dave
 *
 */
public class ExcelInference {

    private static final Log log = LogFactory.getLog(ExcelInference.class);
    private HSSFWorkbook wbk;
    private HSSFSheet sheet;
    private HSSFFormulaEvaluator eval;
    private int maxrows;
    private List<String[]> rawStrings;
    private Metadata metadata;
    private DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
    private int datarow = -1, headerrow = -1;

    public ExcelInference(InputStream is,int maxrows) throws IOException {
        this.maxrows = maxrows;
        wbk = ExcelDataSource.getWorkbook(is);
        is.close();
//        sheet = ExcelDataSource.getSheet(wbk, sheetName);
//        eval = new HSSFFormulaEvaluator(sheet, wbk);
//        // FIXME: decide if we want this...
//        HSSFFormulaEvaluator.evaluateAllFormulaCells(wbk);
//        loadStrings();
//        inferMetadata();
    }
    
    /**
     * return a list of sheet names for this stream
     * @param is
     * @return
     * @throws IOException
     */
    public List<String> getSheetNames() throws IOException {
        int numSheets = wbk.getNumberOfSheets();
        List<String> ret = new ArrayList<String>();
        for (int i=0;i<numSheets;i++) {
            ret.add(wbk.getSheetName(i));
        }
        return ret;
    }
    
    public List<String[]> getRawStrings(String sheetName) {
//        if (rawStrings == null) { 
        rawStrings = new ArrayList<String[]>();
//        }
        
        sheet = ExcelDataSource.getSheet(wbk, sheetName);
        eval = new HSSFFormulaEvaluator(sheet, wbk);
        // FIXME: decide if we want this...
        HSSFFormulaEvaluator.evaluateAllFormulaCells(wbk);
        loadStrings();
        inferMetadata();

        return rawStrings; 
    }
    public Metadata getMetadata() { return metadata; }
    public int getDataRow() { return datarow; }
    public int getHeaderRow() { return headerrow; }
    private void inferMetadata() {
        datarow = getDataRowNum();
        if (datarow == -1) {
            // can't figure out anything - go braindead
            datarow = 1;
            headerrow = 0;
            metadata = getBraindeadMetadata();
        } else {
        /*p("inferred data row=" + datarow + " cell="
                + getCellString(sheet.getRow(datarow).getCell(2))
                + " cell(0,0)=" + getCellString(sheet.getRow(0).getCell(0)));
                */
            metadata = getMetadata(sheet.getRow(datarow));
            HSSFRow titleRow = inferHeaderRow(datarow);
            if (titleRow != null) {
                headerrow = titleRow.getRowNum();
                fillColumnNames(titleRow);
            }
        }
        // make a more laborious guess at column types that are unknown...
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            if (metadata.getColumnType(i) == UNKNOWN) {
                metadata.setColumnType(i, this.inferColumnType(sheet, datarow, i-1));
            }
            if (metadata.getColumnName(i) == null) {
                metadata.setColumnName(i, "col" + i);
            }
        }
        
    }
    
    private void fillColumnNames(HSSFRow titleRow) {
        Iterator tcs = titleRow.cellIterator();
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            HSSFCell tc = null;
            if (tcs.hasNext()) {
                tc = (HSSFCell) tcs.next();
            }
            if (metadata.getColumnName(i) == null) {
                String name = getCellString(tc);
                if (name == null) {
                    name = "col" + i;
                }
                name = StringUtil.trim(name);
                if (name == null) {
                    name = "Column_"+i;
                }
                metadata.setColumnName(i, name);
            }
        }
    }
    
    private HSSFRow inferHeaderRow(int dataRowNum) {

        if (dataRowNum < 1) return null;
        HSSFRow titleRow = sheet.getRow(dataRowNum - 1);
        
        if (titleRow == null)
            return null;
        
        HSSFRow dataRow = sheet.getRow(dataRowNum);
        
        short titleCells = titleRow.getLastCellNum();
        short dataCells = dataRow.getLastCellNum();
        if (titleCells != dataCells) {
            /* expect columns in title row same # columns in first row of data */
            //p("titleLastCell=" + titleCells + "/dataLastCell=" + dataCells);
            return null;
        }
        if (true) { return titleRow; }
        //p("getTitleRow() iterating over " + titleCells + " cells");
        Iterator tcs = titleRow.cellIterator();
        Iterator dcs = dataRow.cellIterator();
        while (tcs.hasNext()) {
            HSSFCell tc = (HSSFCell) tcs.next();
            if (tc.getCellType() != HSSFCell.CELL_TYPE_STRING) return null;
            HSSFCell dc = (HSSFCell) dcs.next();
            if (tc.getCellNum() != dc.getCellNum()) return null;
        }
        return titleRow;
    }
    
    private int getDataRowNum() {
        Iterator<HSSFRow> rows = sheet.rowIterator();
        HSSFRow firstRow = null;
        int matched = 0;
        int i = 0;
        while (rows.hasNext()) {
            HSSFRow row = rows.next();
            eval.setCurrentRow(row); // Workaround for forumula evaluator bug
            //p("looking at row " + row.getRowNum());
            if (firstRow == null) {
                if (hasNumericCell(row)/* && (md == null || hasMetadata(row, md))*/) {
                    //p("proposing row #" + i + " as 1st data row...");
                    firstRow = row;
                    matched = 0;
                }
            } else if (!match(row, firstRow)) {
                firstRow = null;
            } else if (++matched >= 2) {
                return firstRow.getRowNum();
            }
            i++;
        }
        return -1;
    }

    // Returns true iff specified rows match in terms of cell
    // positions and cell types.
    private static boolean match(HSSFRow row1, HSSFRow row2) {
        if (row1.getLastCellNum() != row2.getLastCellNum()
                || row1.getFirstCellNum() != row2.getFirstCellNum()) {
            return false;
        }
        //p("rows " + row1.getRowNum() + "/" + row2.getRowNum() + " MIGHT be a match");
        Iterator cs1 = row1.cellIterator();
        Iterator cs2 = row2.cellIterator();
        while (cs1.hasNext()) {
            HSSFCell c1 = (HSSFCell) cs1.next();
            if (!cs2.hasNext()) {
                return false;
            }
            HSSFCell c2 = (HSSFCell) cs2.next();
            if (c1.getCellNum() != c2.getCellNum()) return false;
            if (c1.getCellType() != c2.getCellType()) return false;
        }
        return true;
    }
    
    private boolean hasNumericCell(HSSFRow row) {
        eval.setCurrentRow(row); // Workaround for forumula evaluator bug
        Iterator<HSSFCell> ci = row.cellIterator();
        while (ci.hasNext()) {
            switch (getType(ci.next())) {
            case INTEGER: case DOUBLE:
                return true;
            }
        }
        return false;
    }

    /* for a column that is empty/null in the "dataRow", scan
     * all the cells in the column, looking for a non-null cell
     * from which to infer a type for the column.
     * 
     * note that we look at logical (not physical) column index
     */
    private int inferColumnType(HSSFSheet sheet, int startRow, int logicalCol) {
        //p("laborious inferColumnType for column=" + logicalCol + " starting from row=" + startRow);
        int ret = UNKNOWN;
        short col = (short) logicalCol;
        for (int i = startRow; i <= maxrows && i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            if (row != null) {
                HSSFCell cell = row.getCell(col);
                if (cell != null) {
                    ret = this.getType(cell);
                    if (ret != UNKNOWN) {
                        //p("inferred type=" + DataType.toString(ret) + " from cell='" + getCellString(cell) + "'");
                        //p("inferred type " + DataType.toString(ret) + " from row/col " + i + "/" + col);
                        break;
                    }
                }
            }
        }
        return ret;
    }
    private int getMaxColumn() {
        int ret = 0;
        for (int i = 0; i < maxrows && i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            if (row != null) {
                int cols = row.getLastCellNum();
                ret = Math.max(ret, cols);
            }
        }
        return ret;
    }
    private Metadata getBraindeadMetadata() {
        int ncols = getMaxColumn();
        Metadata md = new Metadata(ncols);
        /*
        for (int i = 1; i <= ncols; i++) {
            md.setColumnName(i, "col" + i);
            md.setColumnType(i, DataType.UNKNOWN);
        }
        */
        return md;
    }
    private Metadata getMetadata(HSSFRow row) {
        int ncols = row.getLastCellNum() - row.getFirstCellNum();
        Metadata md = new Metadata(ncols);
        eval.setCurrentRow(row); // Workaround for formula evaluator bug
        int i = 1;
        for (short s = row.getFirstCellNum(); s < row.getLastCellNum(); s++) {
            HSSFCell cell = row.getCell(s);
            int type = getType(cell);
            if (s == 1) {
                //p("getMeta(): for cell " + row.getRowNum() + "/" + cell.getCellNum() +
                  //      " got type=" + DataType.toString(type)+ " from '" + getCellString(cell)+ "'");
            }
            //if (type == UNKNOWN) return null;
            md.setColumnType(i++, type);
        }
        return md;
    }

    private void loadStrings() {
        int last = sheet.getLastRowNum();
        int first = sheet.getFirstRowNum();
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0/*first*/; i < last && i < maxrows; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                rawStrings.add(new String[0]);
                continue;
            }
            int count;// = row.getLastCellNum() - row.getFirstCellNum();
            count = row.getLastCellNum();
            eval.setCurrentRow(row);
            if (count < 0) {
                rawStrings.add(new String[0]);
                continue;
            }
            Iterator<HSSFCell> iter = row.cellIterator();
            String[] s = new String[count];
            while (iter.hasNext()) {
                HSSFCell cell = iter.next();
                int col = cell.getCellNum();
                if (col >= 0 && col < count) {
                    s[col] = getCellString(cell, eval, fmt);
               } else {
                   String msg = "cell at row=" + rawStrings.size()
                   + " column=" + col + " is out of bounds.";
                   throw new RuntimeException(msg);
               }
            }
            rawStrings.add(s);
        }
    }
    private int getType(HSSFCell cell) {
        if (cell == null) {
            return UNKNOWN;
        }
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
            return ExcelInference.isCellDateFormatted(cell) ?
                DATE : DOUBLE;
            case HSSFCell.CELL_TYPE_FORMULA:
            {
                int type = eval.evaluateFormulaCell(cell);
                switch (type) {
                case HSSFCell.CELL_TYPE_STRING:
                    return STRING;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    return BOOLEAN;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    return ExcelInference.isCellDateFormatted(cell) ?
                        DATE : DOUBLE;
                default:
                    return UNKNOWN;
                
                }
            }
            case HSSFCell.CELL_TYPE_STRING:
                String empty = StringUtil.trim(getCellString(cell));
                return empty == null ? UNKNOWN: STRING;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return BOOLEAN;
            default:
                return UNKNOWN;
        }
    }
    
    private String getCellString(HSSFCell cell) {
        return ExcelInference.getCellString(cell, eval,dfmt);
    }
    
    public static String getCellString(HSSFCell cell, HSSFFormulaEvaluator eval, DateFormat dfmt) {
        if (cell == null) {
            return null;
        }
        String ret = null;
        eval.evaluate(cell);
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
        case HSSFCell.CELL_TYPE_FORMULA: // ?
            if (isCellDateFormatted(cell)) {
                if (dfmt == null) { dfmt = new SimpleDateFormat("yyyy-MM-dd"); }
                Date d = cell.getDateCellValue();
                if (d != null) {
                    ret = dfmt.format(d);
                } else {
                    ret = "";
                }
            } else {
                try {
                    ret = "" + cell.getNumericCellValue();
                } catch (IllegalStateException ise) {
                    int errVal = cell.getErrorCellValue();
                    String formula = cell.getCellFormula();
                    int cacheType = cell.getCachedFormulaResultType();
                    throw new RuntimeException(ise.getMessage() +
                            ": errVal=" + errVal + " formula='" + formula
                            + "' cacheType=" + cacheType);
                }
            }
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            ret = null;
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            ret = "" + cell.getBooleanCellValue();
            break;
        case HSSFCell.CELL_TYPE_STRING:
        default:
            ret = cell.getRichStringCellValue().getString();
        }
        return ret;
    }
    /* FIXME/HACK: cells in our test data spreadsheet are dates formatted as MM/dd/yy
     * but POI doesn't recognize them as date formatted.  They have
     * format code 0xa5
     */
    public static boolean isCellDateFormatted(HSSFCell cell) {
        boolean ret = false;
        try {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                ret = HSSFDateUtil.isCellDateFormatted(cell);
            }
            /* this is hokey, was trying to understand weird formatting
             * codes from OpenOffice
            if (!ret) {
                double val = cell.getNumericCellValue();
                short fmt = -1;
                if (HSSFDateUtil.isValidExcelDate(val)) {
                    if (cell.getCellStyle() != null) {
                        fmt = cell.getCellStyle().getDataFormat();
                        if (fmt == 0xa4 || fmt == 0xa6 || fmt == 0xaf) {
                           ret = true;
                        }
                    }
                }
            }
            */
        } catch (NumberFormatException ignore) {
        }
        return ret;
    }
        
    public static void main(String[] a) throws Exception {
        if (a.length == 0) {
            System.err.println("usage: ExcelInference <file>");
            System.exit(1);
        }
        InputStream is = new FileInputStream(a[0]);
        ExcelInference ei = new ExcelInference(is,Integer.MAX_VALUE);
        p("header row=" + ei.getHeaderRow() + " data row=" + ei.getDataRow());
        p("metadata=" + ei.getMetadata());
    }
    private static void p(String s) { System.out.println("[XLInfer] " + s); }
}
