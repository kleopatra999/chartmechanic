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

import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import static com.bayareasoftware.chartengine.model.DataType.*;

import com.bayareasoftware.chartengine.ds.util.ExcelInference;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.util.DateUtil;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Data stream implementation for reading from an Excel spreadsheet.
 */
public class ExcelDataStream extends DataStream {
    private /*final*/ Iterator rowIterator;
    private Object[] rowData;
    private boolean nextCalled;
    private HSSFFormulaEvaluator evaluator;
    private boolean hardStop = false;
    private DataSourceInfo dsi;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private DateFormat dfmt = DateUtil.createDateFormat("yyyy-MM-dd");
    private DateFormat[] dfmts;
    private DateRecognizer dateRecognizer; // lazy initialization
    public ExcelDataStream(DataSourceInfo dsi, HSSFWorkbook wb, HSSFSheet sheet, Metadata md) {
        super(true);
        setMetadata(md);
        this.dsi = dsi;
        this.wb = wb;
        this.sheet = sheet;
        evaluator = new HSSFFormulaEvaluator(sheet, wb);
        dfmts = new DateFormat[metadata.getColumnCount()];
        // FIXME: are we sure we want to do this?  Might be heavy-handed...
        HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
        /*
        p("before first infer startrow=" + dsi.getDataStartRow() + " metadata=" + this.metadata);
        this.inferMetadata(-1);
        p("before 2nd infer startrow=" + dsi.getDataStartRow() + " metadata=" + this.metadata);
        this.inferMetadata(-1);
        if (true) throw new RuntimeException("BLAH");
        */
        //p("constructing stream with dsi=" + dsi);
        reset();
    }
    @Override
    public void reset() {
        rowIterator = sheet.rowIterator();
        //positionRowIterator(sheet.getFirstRowNum());
        int startRow;
        if (metadata == null) {
            throw new IllegalStateException("no metadata set on data source");
        } else {
            startRow = dsi.getDataStartRow();
            if (startRow == -1) startRow = 0;
        }
        //System.err.println("[XDS] positioning iterator at " + startRow);
        positionRowIterator(startRow);
        nextCalled = false;
        hardStop = false;
    }

    List<String[]> getRawData(int maxRows) {
        List<String[]> ret = new ArrayList<String[]>();
        int last = sheet.getLastRowNum();
        int first = sheet.getFirstRowNum();
        for (int i = first; i < last && i < maxRows; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                ret.add(new String[0]);
                continue;
            }
            Iterator<HSSFCell> iter = (Iterator<HSSFCell>)row.cellIterator();
            int count = row.getLastCellNum() - row.getFirstCellNum();
            String[] s = new String[count];
            int j = 0;
            while (iter.hasNext() && j < count) {
                s[j] = getCellString(iter.next());
                j++;
            }
            ret.add(s);
            /*
            positionRowIterator(i);
            String[] s = new String[rowData.length];
            for (int j = 0; j < s.length; j++) {
                s[j] = this.getString(i + 1);
            }
            re
            t.add(s);
            */
        }
        return ret;
    }

    /*
    private void hack(HSSFSheet sheet, HSSFWorkbook wb) {
        HSSFRow row = sheet.getRow(2);
        HSSFCell cell = row.getCell((short)0);
        double val = cell.getNumericCellValue();
        p("cell at col=0,row=1 has date: " + HSSFDateUtil.getJavaDate(val));
        p("directly from cell: " + cell.getDateCellValue());
        p("isDateFormatted? " + HSSFDateUtil.isCellDateFormatted(cell));
        HSSFCellStyle style = cell.getCellStyle();
        //p("style is: " + style);
        int df = style.getDataFormat();
        p("dataformat 0x" + Integer.toHexString(df) + " isInternalDateFormat? " + HSSFDateUtil.isInternalDateFormat(df));
    }
    */

    // Advances iterator to first data row at position 'rowNum'
    private void positionRowIterator(int rowNum) {
        while (rowIterator.hasNext()) {
            HSSFRow row = (HSSFRow) rowIterator.next();
            if (row.getRowNum() == rowNum) {
                if (metadata != null) {
                    rowData = getRowData(row, metadata);
                }
                return;
            }
        }
        throw new IllegalStateException("Cannot find first data row (requested " + rowNum + ")");
    }

    @Override
    public boolean nextInternal() {
        if (!nextCalled) {
            nextCalled = true;
            return true;
        }
        if (!rowIterator.hasNext()) return false;
        if (hardStop) return false;
        try {
            HSSFRow row = (HSSFRow) rowIterator.next();
            int endRow = dsi.getDataEndRow();
            if (endRow > 0 && row.getRowNum() > endRow) {
                hardStop = true;
                return false;
            }
            rowData = getRowData(row, metadata);
        } catch (Exception e) {
            /* FIXME: another hack: while we make an effort to
             * determine the row where data begins, we don't currently
             * figure out when the valid data *ends*.  This comes up
             * in the timesheet.xls test.
             * 
             *  Swallowing this exception here creates a hard stop, at
             *  the point where we cannot coerce the remaining rows
             *  into the types we expect.
             *  
             *  At some point in the future, we should do a better
             *  job of determining the begin/end of data, with
             *  user tweaks/intervention/feedback
             */
            //p("hard stop called: "  +e);
            //e.printStackTrace();
            hardStop = true;
            return false;
        }
        return true;
    }
    /*
    public boolean isNull(int index) {
        if (metadata.getColumnType(index) == STRING) {
            String s = (String) rowData[index - 1];
            return s == null || (s.trim().length() == 0);
        }
        return rowData[index - 1] == null;
    }
    */
    @Override
    public String getString(int index) {
        //checkType(index, STRING);
        String ret = null;
        if (DATE == metadata.getColumnType(index)) {
            Date d = getDate(index);
            if (d != null) {
                ret = dfmt.format(d);
            }
        } else {
            ret = rowData[index - 1] != null ? rowData[index - 1].toString() :
                null;
        }
        return ret;
    }

    @Override
    public Integer getInt(int index) {
        //checkType(index, INTEGER);
        if (INTEGER == metadata.getColumnType(index)) {
            return rowData[index - 1] != null ?
                    (Integer) rowData[index - 1] : null;
        } else {
            return this.parseInt(getString(index), -1, index);
        }
    }

    @Override
    public Double getDouble(int index) {
        //checkType(index, DOUBLE);
        if (DOUBLE == metadata.getColumnType(index)) {
            return rowData[index - 1] != null ?
                    (Double) rowData[index - 1] : null;
        } else {
            return this.parseDouble(getString(index), -1, index);
        }
    }

    public Boolean getBoolean(int index) {
        //checkType(index, BOOLEAN);
        if (BOOLEAN == metadata.getColumnType(index)) {
            return rowData[index - 1] != null ?
                    (Boolean) rowData[index - 1] : null;
        } else {
            return this.parseBoolean(getString(index), -1, index);
        }
    }
    
    public Date getDate(int index) {
        //checkType(index, DATE);
        Date ret = null;
        if (DATE == metadata.getColumnType(index)) {
            ret = (Date) rowData[index - 1];
        } else {
            String dstr = getString(index);
            if (dfmts[index - 1] != null) {
                ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
            } else if (metadata.getColumnFormat(index) != null) {
                dfmts[index - 1] = DateUtil.createDateFormat(metadata.getColumnFormat(index));
                ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
            } else if (dstr != null) {
                if (dateRecognizer == null) {
                    dateRecognizer = new DateRecognizer();
                }
                dateRecognizer.reset();
                dateRecognizer.parse(dstr);
                if (!dateRecognizer.failed()) {
                    dfmts[index - 1] = dateRecognizer.getSimpleDateFormat();
                    ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
                }
            }
        }
        return ret;
    }

    private void checkType(int index, int type) {
        if (type != metadata.getColumnType(index)) {
            throw new UnsupportedOperationException(
                "Column data is not of type '" + DataType.toString(type) + "'");
        }
        if (rowData == null) {
            throw new IllegalStateException("No current row data");
        }
    }

    private Object[] getRowData(HSSFRow row, Metadata md) {
        evaluator.setCurrentRow(row); // Workaround for forumula evaluator bug
        Object[] data = new Object[md.getColumnCount()];
        int i = 0;
        for (i = 0; i < data.length; i++) {
            int type = md.getColumnType(i + 1);
            HSSFCell c = row.getCell((short)i);
            try {
                data[i] = getCellData(c, row.getRowNum(), type, i+1);
            } catch (NumberFormatException nfe) {
                /* convert invalid numerics to null. This is also
                 * needed with blank cells.
                 */
            }
        }
        return data;
    }

    private String getCellString(HSSFCell cell) {
        return ExcelInference.getCellString(cell, evaluator,dfmt);
    }

    private Object getCellData(HSSFCell cell, int rowNum, int type, int index) {
        evaluator.evaluate(cell);
        if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK || type == UNKNOWN) {
            return null;
        }
        //p("getting from row#" + rowNum + " col#" + cell.getCellNum());
        switch (type) {
        case STRING:
            return getCellString(cell);
        case DATE:
        {
            if (ExcelInference.isCellDateFormatted(cell)) {
                Date d = cell.getDateCellValue();
                if (d != null) {
                    return d;
                }
            }
            // figure out date format
            Date ret = null;
            String dstr = getCellString(cell);
            if (dfmts[index - 1] != null) {
                ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
            } else if (metadata.getColumnFormat(index) != null) {
                dfmts[index - 1] = DateUtil.createDateFormat(metadata.getColumnFormat(index));
                ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
            } else if (dstr != null) {
                if (dateRecognizer == null) {
                    dateRecognizer = new DateRecognizer();
                }
                dateRecognizer.reset();
                dateRecognizer.parse(dstr);
                if (!dateRecognizer.failed()) {
                    dfmts[index - 1] = dateRecognizer.getSimpleDateFormat();
                    ret = this.parseDate(dstr, dfmts[index - 1], -1, index);
                }
            }
            return ret;
        }
        case DOUBLE:
            return cell.getNumericCellValue();
        case INTEGER:
            double d = cell.getNumericCellValue();
            return new Double(d).intValue();
        case BOOLEAN:
            String s = cell.getRichStringCellValue().getString();
            return "true".equalsIgnoreCase(s);
        case IGNORE:
            return null;
        default:
            throw new AssertionError("unexpected type: " + DataType.toString(type));
        }
    }


    private static void p(String s) {
        System.out.println("[XlsStream] " + s);
    }
}
