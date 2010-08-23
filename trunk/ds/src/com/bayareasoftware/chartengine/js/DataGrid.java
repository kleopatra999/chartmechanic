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
package com.bayareasoftware.chartengine.js;

import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_DAY;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_MONTH;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_QUARTER;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_UNKNOWN;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_WEEK;
import static com.bayareasoftware.chartengine.model.TimeConstants.TIME_YEAR;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.script.ScriptException;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.ds.util.FuzzyTimeMap;
import com.bayareasoftware.chartengine.functions.Predicate;
import com.bayareasoftware.chartengine.functions.Reducer;
import com.bayareasoftware.chartengine.js.JSFunctions.RowBinaryFormula;
import com.bayareasoftware.chartengine.js.JSFunctions.UnaryFormula;
import com.bayareasoftware.chartengine.model.ColumnInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.TimeConstants;

/**
 * DataGrid is a java class that maps to a javascript class for use in our transformational scripting language
 */
public class DataGrid {

    private List<Row> rows;
    
    // numeric value to use when a cell is null
    private double defaultNumberValue = 0.0;
    
    // one-based array of DataColumn metadata that describes the columns for this dataset
    private DataColumn[] columns; 
    
    public DataGrid(DataStream ds) throws Exception {
        Metadata metadata = ds.getMetadata();
        if (metadata != null && metadata.getColumnCount() > 0 ) {
            columns = new DataColumn[metadata.getColumnCount()+1];

            for (int i=1;i<=metadata.getColumnCount();i++) {
                columns[i] = new DataColumn(metadata.getColumnInfo(i),i,this);
            }
        }
        int colCount = metadata.getColumnCount();
        
        rows = new ArrayList<Row>();
        while (ds.next()) {
            rows.add(getRow(ds, colCount));
        }
    }
    
    public DataGrid(DataColumn[] columns) {
        if (columns[0] != null) {
            throw new IllegalArgumentException(
                    "first column ID should be null, indexes are 1-based"
                    );
        }
        this.columns = columns;
        this.rows = new ArrayList<Row>();
    }
    
//    // for testing sorting...
//    private void shuffleRows() {
//        Collections.shuffle(rows);
//    }
    
    /**
     * @param s                      - input stream
     * @param columnIndex            - column to sort on
     * @param descending             - whether to sort descending or not
     * @return
     */
    public static DataStream sortStream(DataStream s, int columnIndex, boolean descending) {
        
        if (s.isResettable()) {
            s.reset();
        }
        try {
            DataGrid dg = new DataGrid(s);
            dg.sort(columnIndex, descending);
            s = dg.toStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (s.isResettable()) {
            s.reset();
        }
        
        return s;
    }
    
    
    /**
     * return number of rows
     */
    public int getRowCount() { 
        return rows.size(); 
    }
    
    /**
     * returns the number of columns
     * @return
     */
    public int getColumnCount() { 
        if (columns == null)
            return 0;
        else
            return columns.length - 1;
    }
    
    public DataStream toStream() {
        return new DataGridStream(this);
    }
    
    
    /**
     *  return the index of the supplied column
     * @param s  (between 1 <= s <= this.getColumnCount())
     * @return  0 if not found
     * @throws ScriptException if not found
     */
    public int getColumn(String s) throws ScriptException {
        int result = 0;
        for (int i=1;i<columns.length;i++) {
            if (s.equals(columns[i].getName())) {
                result = i;
                break;
            }
        }
        if (result == 0)
            throw new ScriptException("Column named: " + s + " does not exist");
        return result;
    }
    
    /**
     * return the Column for this colName
     * 
     * @param colName
     * @return
     */
    public DataColumn col(String colName) throws ScriptException{
        int colIndex = getColumn(colName);
        return col(colIndex);
    }
    
    /**
     * return the Column for this 1-based index
     * @param colIndex
     * @return
     */
    public DataColumn col(int colIndex) {
        if (colIndex > 0 && colIndex < columns.length)
            return columns[colIndex];
        else
            return null;
    }

    /**
     * this is the (private) workhorse that actually adds a column to the DataGrid 
     */
    private DataColumn addCol(ColumnInfo colInfo) throws ScriptException {
        DataColumn ret = null;
        String colName = colInfo.getName();
        for (int i=1;i<columns.length;i++) {
            if (colName.equals(columns[i].getName())) {
                //throw new ScriptException("Column named: " + colName + " already exists.  New column name must be unique");
                // already exists - just verify that the types are compatible
                if (colInfo.getType() != columns[i].getType()) {
                    throw new ScriptException(
                            "Cannot change type of existing column '" + colInfo.getName()
                            + "' from " + DataType.toString(columns[i].getType())
                            + " to " + DataType.toString(colInfo.getType())
                            );
                }
                return columns[i];
            }
        }
        DataColumn[] newColumns = new DataColumn[columns.length+1];
        newColumns[0] = null;
        for (int i=1;i<columns.length;i++) {
            newColumns[i] = columns[i];
        }
        DataColumn newColumn = new DataColumn(colInfo,columns.length,this);
        newColumns[columns.length] = newColumn;
        this.columns = newColumns;
        
        for (Row r : rows ) {
            r.addColumn();
        }
        ret = newColumn;
        return ret;
        
    }
    
    /**
     * copy all the values from column1 to column2
     * @param c1  - the originating column
     * @param c2  - the target column
     * @return
     */
    public DataGrid copyCol(String c1, String c2) throws ScriptException {
        return JSFunctions.get().copyCol(this,col(c1), col(c2));
    }
    
    /**
     * concatenate values from two columns into a thrid
     *  so  c3 = c1 + spacer + c2
     *   
     * @param c1   - column 1
     * @param c2   - column 2
     * @param spacer - a constant 'spacer' value to aid in concatenation (can be null)
     * @param c3   - target column
     * @return
     * @throws ScriptException
     */
    public DataGrid concatCol(String c1, String c2, String spacer, String c3) throws ScriptException {
        return JSFunctions.get().concatCol(this,col(c1),col(c2),spacer,col(c3));
    }

    /**
     * concatenate values form column c1 and c2 into a Date column c3
     * 
     * @param c1  
     * @param c2
     * @param c3
     * @return
     * @throws ScriptException
     */
    public DataGrid concatAsDate(String c1, String c2, String c3) throws ScriptException{
       DataColumn col3 = this.addCol(c3,"DATE");

       DataColumn col1 = col(c1);
       DataColumn col2 = col(c2);
       
       DateRecognizer dr = new DateRecognizer();
       dr.reset();
       DateFormat sdf  = null;
       for (int i=0;i<getRowCount();i++) {
           Object o1 = value(i,col1);
           Object o2 = value(i,col2);
           if (o1 == null || o2 == null) {
               continue;
           }
           String s = o1 + " " +  o2;
           if (sdf == null) {
               dr.parse(s);
               if (!dr.failed()) {
                   sdf = dr.getSimpleDateFormat();
               }
           }
           if (sdf != null) {
               try {
                   setValue(i,col3,sdf.parse(s));
               } catch (ParseException pe) {
                   setValue(i,col3,null);
               }
           } else {
               setValue(i,col3,null);
           }
       }
       return this;
    }

    /**
     * modify the column named colName to the type colType
     * @param colName
     * @param colType
     * @return
     * @throws ScriptException
     */
    public DataColumn setColType(String colName, String colType) throws ScriptException {
        DataColumn column = col(colName);
        int dtype = DataType.parse(colType);
        if (dtype == DataType.UNKNOWN) {
            throw new ScriptException("Column type must be one of: INTEGER, DOUBLE, DATE, STRING, BOOLEAN");
        }
        column.setType(dtype);
        if (dtype == DataType.DATE) {
            column.setFormat(Metadata.INTERNAL_DATE_FORMAT);
            // any dynamically created Date columns must have our default string representation, e.g. Date in milliseconds
        }
//        if (dtype == DataType.DATE) {
//            // fire up the DateRecognizer again to re-interpret
//            DateRecognizer dr = new DateRecognizer();
//            dr.reset();
//            for (int i=0;i<getRowCount();i++) {
//                Object o = value(i,column.index());
//                dr.parse(o.toString());
//                if (!dr.failed()) {
//                    column.setFormat(DateUtil.getDatePattern(dr.getSimpleDateFormat()));
//                    break;
//                }
//            }
//        }
        return column;
    }
    
    /**
     * parse all the values in the given column as Dates, using DateRecognizer to help us do so
     * also alters the type of the column to DATE regardless of what it was before
     *  
     *  nulls out any values that are unparseable
     * @param colName
     * @return
     */
    public DataGrid parseAsDate(String colName) throws ScriptException {
        DataColumn column = col(colName);
        // fire up the DateRecognizer again to re-interpret
        DateRecognizer dr = new DateRecognizer();
        dr.reset();
        DateFormat sdf  = null;
        for (int i=0;i<getRowCount();i++) {
            Object o = value(i,column.index());
            if (sdf == null) {
                dr.parse(o.toString());
                if (!dr.failed()) {
                    sdf = dr.getSimpleDateFormat();
                }
            }
            if (sdf != null) {
                try {
                    setValue(i,column.index(),sdf.parse(o.toString()));
                } catch (ParseException pe) {
                    setValue(i,column.index(),null);
                }
            } else {
                setValue(i,column.index(),null);
            }
        }
        column.setType(DataType.DATE);

        return this;
    }
    
    /**
     * add a column to this DataGrid
     * 
     * @param colName   - name of the column, must be unique compared to other columns
     * @param colType   - data type: one of "INTEGER","DOUBLE","DATE","STRING","BOOLEAN"
     * @param description - column description
     * @return
     */
    public DataColumn addCol(String colName, String colType, String description) throws ScriptException {
        DataColumn ret = null;

        // ensure that the data type is correct;
        int dtype = DataType.parse(colType);
        if (dtype == DataType.UNKNOWN) {
            throw new ScriptException("Column type must be one of: INTEGER, DOUBLE, DATE, STRING, BOOLEAN");
        }

        // ensure that the colName is unique
        if (colName != null ) {
            for (int i=1;i<columns.length;i++) {
                if (colName.equals(columns[i].getName())) {
                    //throw new ScriptException("Column named: " + colName + " already exists.  New column name must be unique");
                    // already exists - just verify that the types are compatible
                    if (dtype != columns[i].getType()) {
                        throw new ScriptException(
                                "Cannot change type of existing column '" + colName
                                + "' from " + DataType.toString(columns[i].getType())
                                + " to " + DataType.toString(dtype)
                                );
                    }
                    
                }
            }
            ColumnInfo colInfo = new ColumnInfo(colName, dtype);
            if (dtype == DataType.DATE) {
                colInfo.setFormat(Metadata.INTERNAL_DATE_FORMAT);
                // any dynamically created Date columns must have our default string representation, e.g. Date in milliseconds
            }
            if (description != null) 
                colInfo.setDescription(description);
            ret = addCol(colInfo);
        } else {
            throw new ScriptException("Column names cannot be null");
        }
        return ret;
    }

    /**
     * add a column to this DataGrid
     * 
     * @param colName   - name of the column, must be unique compared to other columns
     * @param colType   - data type: one of "INTEGER","DOUBLE","DATE","STRING","BOOLEAN"
     * @return
     */
    public DataColumn addCol(String colName, String colType) throws ScriptException {
        return addCol(colName, colType,null);
    }
    
//    /**
//     * add a column to this DataGrid
//     * 
//     * @param colName   - name of the column, must be unique compared to other columns
//     * @param colType   - data type: one of "INTEGER","DOUBLE","DATE","STRING","BOOLEAN"
//     * @param format    - data type format
//     * @return
//     */
//    public DataColumn addCol(String colName, String colType, String format) throws ScriptException {
//        return addCol(colName, colType, format, null);
//    }
    
   /*package */ DataColumn[] getColumns() {
        return columns;
    }

    /**
     * is the column valid for this DataGrid 
     * @param col
     * @return
     */
    /* pacakge */ boolean isValidColumn(DataColumn col) {
        return (col != null && col.data() == this);
    }
    
    private void checkColumnBounds(int col) throws ScriptException{
        if (col > 0 && col < columns.length) 
            return;
        
        throw new ScriptException("Column index must be between 1 and " + columns.length);
    }
    
    public Date date(int row, int col) throws ScriptException {
        Date ret = null;
        checkRowBounds(row);
        checkColumnBounds(col);
        ret = row(row).date(col);
        return ret;
    }
    
    public Date date(int row, DataColumn col) throws ScriptException {
        Date ret = null;
        if (isValidColumn(col)) {
            ret = date(row,col.index());
        }
        return ret;
    }
    
    public Double getDouble(int row, int col) throws ScriptException {
        Double ret = null;
        if (!isNull(row,col)) {
            ret = num(row,col);
        }
        return ret;
    }
    public double num(int row, int col) throws ScriptException {
        Double ret = null;
        checkRowBounds(row);
        checkColumnBounds(col);
        ret = row(row).num(col);
        return ret != null ? ret: defaultNumberValue;
    }
    
    public double num(int row, DataColumn column) throws ScriptException{
        Double ret = null;
        if (isValidColumn(column)) 
            ret = num(row,column.index());
        return ret;
    }
    
    public String string(int row, int col) throws ScriptException {
        String ret = null;
        checkRowBounds(row);
        checkColumnBounds(col);
        ret = row(row).string(col);
        return ret;
    }
    
    public String string(int row, DataColumn column) throws ScriptException {
        String ret = null;
        if (isValidColumn(column)) 
            ret = string(row,column.index());
        return ret;
    }

    public Object value(int row, int col) throws ScriptException {
        Object ret = null;
        checkRowBounds(row);
        checkColumnBounds(col);
        ret = row(row).value(col);
        return ret;
    }
    
    public Object value(int row, DataColumn column) throws ScriptException {
        Object ret = null;
        if (isValidColumn(column)) 
            ret = value(row,column.index());
        return ret;
    }
    
    public void setString(int row, int col, Object val) throws ScriptException {
        setValue(row,col, val != null ? val.toString() : null);
    }
    
    public void setString(int row, DataColumn col, Object val) throws ScriptException {
        if (isValidColumn(col)) {
            setString(row,col.index(),val);
        }
    }
    
    public void setValue(int row, int col, Object val) throws ScriptException {
        checkRowBounds(row);
        checkColumnBounds(col);
        Row r = row(row);
        if (r != null) {
            r.setValue(col,val);
        }
    }
    
    public void setValue(int row, DataColumn col, Object val) throws ScriptException{
        if (isValidColumn(col)) {
            setValue(row,col.index(),val);
        }
    }
    
    public void setNum(int row, int col, Number val) throws ScriptException {
        checkRowBounds(row);
        checkColumnBounds(col);
        int type = columns[col].getType();
        if (type != DataType.INTEGER && type != DataType.DOUBLE) {
            throw new ScriptException(
                    DataType.toString(type) + " not compatible with numeric column"
            );
        }
        Row r = row(row);
        if (r != null) {
            if (val != null) {
                r.setValue(col,val);
            } else {
                r.setValue(col,null);
            }
        }
    }
    
    public void setNum(int row, DataColumn col, Number val) throws ScriptException {
        if (isValidColumn(col)) {
            setNum(row,col.index(),val);
        }
    }
    
    public boolean isNull(int row, int col) throws ScriptException {
        boolean ret = true;
        checkRowBounds(row);
        checkColumnBounds(col);
        ret = row(row).isNull(col);
        return ret;
    }
    
    public boolean isNull(int row, DataColumn col) throws ScriptException {
        boolean ret = true;
        if (isValidColumn(col)) {
            return isNull(row,col.index());
        }
        return ret;
    }
    
    public void addRow(Row r) {
        rows.add(r);
    }
    
    /**
     * add a new blank row and return the index to that row
     */
    public int addNewRow() {
        Object[] vals = new Object[this.getColumnCount()];
        rows.add(new Row(vals));
        return rows.size()-1;
    }
    
    public Row row(int r) throws ScriptException {
        Row ret = null;
        checkRowBounds(r);
        ret = rows.get(r);
        return ret;
    }
    
    /* excise extraneous rows: startRow <= data <= endRow */
    public void limit(int startRow, int endRow) {
        startRow = Math.max(startRow, 0);
        endRow = Math.min(endRow, getRowCount());
        // list.sublist()
        // Returns a view of the portion of this list between the
        // specified fromIndex, inclusive, and toIndex, exclusive.
        rows = rows.subList(startRow, endRow);
    }
    
    public void checkRowBounds(int row) throws ScriptException {
        if (row >= 0 && row < rows.size())
            return;
        
        throw new ScriptException("Row index must be between 0 and " + rows.size());
    }
    
    public void sort(int col, boolean desc) {
        if (col < 0 || col >= columns.length) {
            return;
        }
        Collections.sort(rows, new RowComp(columns, col, desc));
    }
    
    /**
     * sort a datagrid by the specified column
     * @param col
     * @param desc    - if true, sort in a descending order
     */
    public void sort(DataColumn col, boolean desc) {
        if (isValidColumn(col)) {
            sort(col.index(),desc);
        }
    }
    
    public void sort(String colName, boolean desc) throws ScriptException {
        sort(getColumn(colName),desc);
    }
    
    /**
     * sort a datagrid by two columns
     * @param primary   - the primary sort column
     * @param desc1     - whether the primary is descending or not
     * @param secondary - the secondary sort column
     * @param desc2     - whether the secondary is descending or not
     */
    public void sort(int col, boolean desc1, int col2, boolean desc2) throws ScriptException {
        checkColumnBounds(col);
        checkColumnBounds(col2);
        Collections.sort(rows, new RowComp(columns, col, desc1, col2, desc2));

    }
    
    /**
     * sort a datagrid by two columns
     * @param primary   - the primary sort column
     * @param desc1     - whether the primary is descending or not
     * @param secondary - the secondary sort column
     * @param desc2     - whether the secondary is descending or not
     */
    public void sort(DataColumn primary, boolean desc1, DataColumn secondary, boolean desc2) throws ScriptException {

        if (isValidColumn(primary) && isValidColumn(secondary)) {
            sort(primary.index(), desc1, secondary.index(), desc2);
        }
    }
    
    public void sort(String colName, boolean desc, String colName2, boolean desc2) throws ScriptException {
        sort(getColumn(colName),desc,getColumn(colName2),desc2);
    }

    
    // convenience syntactic sugar for some functions in JSFunctions
    public void filter(DataColumn col, Predicate pred) throws ScriptException {
        DataGrid ret = JSFunctions.get().filter(this,col,pred);
        this.columns = ret.columns;
        this.rows = ret.rows;
    }
    
    public void filter(String colName, Predicate pred) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            filter(c,pred);
        }
    }
    
    public void filter(int columnIndex, Predicate pred) throws ScriptException {
        checkColumnBounds(columnIndex);
        DataColumn c = col(columnIndex);
        if (c != null) {
            filter(c,pred);
        }
    }
    
    public void scale(int columnIndex, double scalar) throws ScriptException {
        checkColumnBounds(columnIndex);
        DataColumn c = col(columnIndex);
        if (c != null) {
            updateAll(c,JSFunctions.get().scale(scalar));
        }
    }
    /**
     * perform a date-trunc on the dateCol according to the trunc_arg
     * 
     * @param dateCol            - the date column
     * @param trunc_arg          - possible values are year|month|day
     * @param newColName         - resulting truncated date values are stored in this column
     * @param valueCol           - the column where the values to be aggregated are
     * @param reducer            - reducer function (e.g. fn.sum())
     */
    public void groupbyDateTrunc(String dateCol, String trunc_arg, String newColName, String valueCol, Reducer r) throws ScriptException {
        JSFunctions.get().verifyDateTruncParam(trunc_arg);
        this.addCol(newColName, "DATE");
        this.copyCol(dateCol,newColName);
        this.updateAll(newColName,JSFunctions.get().date_trunc(trunc_arg));
        this.groupby(newColName,new Aggregator(valueCol,r));
    }
            
    public void groupby(DataColumn col, Aggregator agg) throws ScriptException {
        DataGrid ret = JSFunctions.get().groupby(this,col,agg);
        this.columns = ret.columns;
        this.rows = ret.rows;
    }

    public void groupby(String colName, Aggregator agg) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            groupby(c,agg);
        }
    }
    
    public void groupby(DataColumn col1, DataColumn col2, Aggregator agg) throws ScriptException {
        DataGrid ret = JSFunctions.get().groupby(this,col1,col2,agg);
        this.columns = ret.columns;
        this.rows = ret.rows;
    }

    public void groupby(String colName, String colName2, Aggregator agg) throws ScriptException {
        DataColumn c1 = col(colName);
        DataColumn c2 = col(colName2);
        if (c1 != null && c2 != null) {
            groupby(c1,c2,agg);
        }
    }
    
    public void updateAll(DataColumn col,UnaryFormula formula) throws ScriptException {
        JSFunctions.get().updateAll(this,col,formula);
    }
    
    public void updateAll(DataColumn col,String value) throws ScriptException {
        if (col != null) {
            updateAll(col,JSFunctions.get().constant(value));
        }
    }
    

    public void updateAll(DataColumn col, Date value) throws ScriptException {
        if (col != null) {
            updateAll(col,JSFunctions.get().constant(value));
        }
    }
    
    public void updateAll(DataColumn col, Double value) throws ScriptException {
        if (col != null) {
            updateAll(col,JSFunctions.get().constant(value));
        }
    }


    /**
     * update every row by apply the unary formula to the column named 'colName'
     * 
     * MODIFIES the datagrid  
     * 
     * @param colName 
     * @param formula
     */
    public void updateAll(String colName,UnaryFormula formula) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            updateAll(c,formula);
        }
    }

    
    /**
     * update every row by setting it to a String constant
     * 
     * MODIFIES the datagrid  
     * 
     * @param colName 
     * @param value
     */
    public void updateAll(String colName,String value) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            updateAll(c,JSFunctions.get().constant(value));
        }
    }

    /**
     * update every row by setting it to a Number constant
     * 
     * MODIFIES the datagrid  
     * 
     * @param colName 
     * @param value
     */
    public void updateAll(String colName,Double value) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            updateAll(c,JSFunctions.get().constant(value));
        }
    }

    /**
     * update every row by setting it to a Date constant
     * 
     * MODIFIES the datagrid  
     * 
     * @param colName 
     * @param value
     */
    public void updateAll(String colName,Date value) throws ScriptException {
        DataColumn c = col(colName);
        if (c != null) {
            updateAll(c,JSFunctions.get().constant(value));
        }
    }


    /**
     * update the column named 'targetColumn' of every row
     * by evaluating the formula with the row, and it's delta-row.  The delta-row is that whose colName column
     * is interval away from the current row within a certain tolerance
     * 
     * MODIFIES the datagrid  
     * 
     * @param col           - one of the date column
     * @param interval      - one of WEEK, MONTH, QUARTER, YEAR
     * @param tolerance     - in days
     * @param targetColumn  - target column to store the formula results
     * @param formula
     */
    public void timeDelta(DataColumn col, String interval, double tolerance, DataColumn targetCol, RowBinaryFormula formula) 
        throws ScriptException {
        if (col == null) {
            throw new ScriptException ("null column not allowed for timeDelta");
        }
        if (col.getType() != DataType.DATE) {
            throw new ScriptException ("column named: " + col.getName() + " must be of DATE type");
        }

        if (targetCol == null) {
            throw new ScriptException ("null target column not allowed or timeDelta");
        }

        int numRows = this.getRowCount();
        if (numRows <= 1)
            return;

        int timeDeltaType = TIME_UNKNOWN;
        if (interval != null) {
            if (interval.equalsIgnoreCase("WEEK"))
                timeDeltaType = TIME_WEEK;
            else if (interval.equalsIgnoreCase("MONTH"))
                timeDeltaType = TIME_MONTH;
            else if (interval.equalsIgnoreCase("QUARTER"))
                timeDeltaType = TIME_QUARTER;
            else if (interval.equalsIgnoreCase("YEAR"))
                timeDeltaType = TIME_YEAR;
        }
        
        if (timeDeltaType == TimeConstants.TIME_UNKNOWN) {
            throw new ScriptException("TimeDelta interval must be one of: WEEK, MONTH, QUARTER, YEAR,"
            		+ " not '" + interval + "'");
        }
        
        // tolerance parameter to FuzzyTimeMap is in milliseconds;
        FuzzyTimeMap<Row> ftm = new FuzzyTimeMap<Row>( (long) (tolerance * 1000 * 60 * 60 * 24)); 
        
        // first, insert all the Rows into the FuzzyTreeMap by the dates in 'col'
        for (int i=0;i<numRows;i++) {
            ftm.put(date(i,col),row(i));
        }
        
        // now, for each row, find the row that delta-matches it, and calculate the target column
        for (int i=0;i<numRows;i++) {
            Date d1 = date(i,col);
            Date d2 = computePriorDate(d1,timeDeltaType);
            Row r1 = row(i);
            Row r2 = ftm.get(d2);
            if (r2 != null) {
                setValue(i,targetCol,formula.eval(r1, r2));
            }
        }
    }

    /**
     * update the column named 'targetColumn' of every row
     * by evaluating the formula with the row, and it's delta-row.  The delta-row is that whose colName column
     * is interval away from the current row within a certain tolerance
     * 
     * MODIFIES the datagrid  
     * 
     * @param colName       - name of the date column
     * @param interval      - one of WEEK, MONTH, QUARTER, YEAR
     * @param tolerance     - in days
     * @param targetColumn  - target column to store the formula results
     * @param formula
     */
    public void timeDelta(String colName, String interval, double tolerance, String targetColumn, RowBinaryFormula formula) 
    throws ScriptException {
        timeDelta(col(colName),interval, tolerance, col(targetColumn), formula);
    }

    public void timeDelta(int colIndex, String interval, double tolerance, int targetColIndex, RowBinaryFormula formula) 
    throws ScriptException {
        checkColumnBounds(colIndex);
        checkColumnBounds(targetColIndex);
        timeDelta(columns[colIndex],interval, tolerance, columns[targetColIndex], formula);
    }

    
    
    private Date computePriorDate(Date d, int timeType) {
        Date ret = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        switch (timeType) {
        case TIME_DAY:
            cal.add(Calendar.DAY_OF_YEAR, -1);
            ret = cal.getTime();
            break;
        case TIME_WEEK:
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            ret = cal.getTime();
            break;
        case TIME_MONTH:
            cal.add(Calendar.MONTH, -1);
            ret = cal.getTime();
            break;
        case TIME_QUARTER:
            cal.add(Calendar.MONTH, -3);
            ret = cal.getTime();
            break;
        case TIME_YEAR:
        default:
            cal.add(Calendar.YEAR, -1);
            ret = cal.getTime();
            break;
        }
        return ret;
    }

    
    private Row getRow(DataStream ds, int cols) throws Exception {
        Object[] vals = new Object[cols];
        for (int i = 0; i < cols; i++) {
            vals[i] = ds.getObject(i + 1);
        }
        return new Row(vals);
    }
    
    /**
     * compare two Rows for sorting purposes
     */
    public static class RowComp implements Comparator<Row> {
        int col;
        int colType;
        boolean desc = false;

        int col2;
        int colType2;
        boolean desc2 = false;
        boolean hasSecondary = false; // has a secondary sort order or not
        
        RowComp(DataColumn columns[], int col, boolean desc) {
            this.col = col;
            this.desc = desc;
            this.colType = columns[col].getType();
        }

        
        RowComp(DataColumn columns[], int col, boolean desc, int col2, boolean desc2 ) {
            this.col = col;
            this.desc = desc;
            this.colType = columns[col].getType();
            this.col2 = col2;
            this.desc2 = desc2;
            this.colType2 = columns[col2].getType();
            this.hasSecondary = true;
        }

        public int compare(Row r1, Row r2) {
            int result = 0;
            switch (colType) {
                case DataType.DATE:
                    result = compareDate(r1.date(col), r2.date(col));
                    break;
                case DataType.INTEGER:
                case DataType.DOUBLE:
                    result = compareNum(r1.num(col), r2.num(col));
                    break;
                case DataType.STRING:
                default:
                    result = compareStr(r1.string(col), r2.string(col));
            }
            if (hasSecondary && result == 0) {
                // go to secondary if primary sort is equal
                switch (colType2) {
                case DataType.DATE:
                    result = compareDate(r1.date(col2), r2.date(col2));
                    break;
                case DataType.INTEGER:
                case DataType.DOUBLE:
                    result = compareNum(r1.num(col2), r2.num(col2));
                    break;
                case DataType.STRING:
                default:
                    result = compareStr(r1.string(col2), r2.string(col2));
                }     
                if (desc2) {
                    result = -result;
                }
            } else {
                if (desc) {
                    result = -result;
                }
            }
                
            return result;
        }
        
        /**
         * Returns a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the second
         * 
         *  We assume here that nulls sort less than (come before) non-null dates
         *  
         *  these sorts are always in ASCENDING order
         */
        private int compareDate(Date d1, Date d2 ) {
            int ret = 0;
            if (d1 == null) {
                if (d2 == null) {
                    ret = 0;
                } else {
                    ret = -1;
                }
            } else if (d2 == null) {
                ret = 1;
            } else {
                ret = d1.compareTo(d2);
            }
            return ret;
        }
        
        private int compareNum(Double d1, Double d2) {
            int ret = 0;
            if (d1 == null) {
                if (d2 == null) {
                    ret = 0;
                } else {
                    ret = -1;
                }
            } else if (d2 == null) {
                ret = 1;
            } else {
                ret = d1.compareTo(d2);
            }
            return ret;
        }
        
        private int compareStr(String s1, String s2) {
            int ret = 0;
            if (s1 == null) {
                if (s2 == null) {
                    ret = 0;
                } else {
                    ret = -1;
                }
            } else if (s2 == null) {
                ret = 1;
            } else {
                ret = s1.compareTo(s2);
            }
            return ret;
        }
    }
    
    // NOTE: column-based accessors to the row values are 1-based, 
    // just like Metadata
    public class Row {
        public Object[] vals;
        public Row(Object[] vals) {
            this.vals = vals;
        }
        public Object[] getData() { return vals; }
        
        public Double num(int i) {
            Double ret = null;
            int index = i - 1;
            if (inBounds(index) && vals[index] != null && vals[index] instanceof Number) {
                ret = ((Number)vals[index]).doubleValue();
            }
            return ret;
        }
        public String string(int i) {
            String s = null;
            int index = i-1;
            if (inBounds(index) && vals[index] != null) {
                Object o = vals[index];
                
                if (o instanceof Date) {
                    // internally, our string representation of a Date is always as its long value
                    s = String.valueOf(((Date)o).getTime());
                } else {
                    s = vals[index].toString();
                }
            }
            return s;
        }
        public Date date(int i) {
            Date d = null;
            int index = i-1;
            if (inBounds(index) && vals[index] instanceof Date) {
                d = (Date)vals[index];
            }
            return d;
        }
//        public void setString(int i, Object o) {
//            int index = i - 1;
//            if (inBounds(index)) {
//                vals[index] = o != null ? o.toString() : null;
//            }
//        }
//        public void setNum(int i, Double val) {
//            int index = i- 1;
//            if (inBounds(index)) {
//                vals[index] = val;
//            }
//        }
//
        public void setValue(int i, Object o) {
            int index = i-1;
            if (inBounds(index)) {
                vals[index] = o;
            }
        }

        public Object value(int i) {
            Object o = null;
            int index = i-1;
            if (inBounds(index)) {
                o = vals[index];
            }
            return o;
        }
        
        public boolean isNull(int i) {
            boolean ret = true;
            int index = i-1;
            if (inBounds(index) && vals[index] != null) {
                ret = false;
            }
            return ret;
        }
        
        // expand the row by one column
        public void addColumn() {
            Object[] newVals = new Object[vals.length+1];
            for (int i=0;i<vals.length;i++) {
                newVals[i] = vals[i];
            }
            vals = newVals;
        }
        
        private boolean inBounds(int i) {
            return i >= 0 && i < vals.length;
        }

    }

    private static void p(String s) { System.out.println("[DataGrid] " + s); }
}
