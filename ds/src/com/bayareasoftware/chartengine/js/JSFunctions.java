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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.ds.DateRecognizer;
import com.bayareasoftware.chartengine.functions.BuiltInFunctions;
import com.bayareasoftware.chartengine.functions.Predicate;
import com.bayareasoftware.chartengine.functions.Reducer;
import com.bayareasoftware.chartengine.js.DataGrid.Row;

/**
 * this class is the namespace object for all the builtin functions that
 * users can invoke from within our scripting framework for data transformation
 */
public class JSFunctions {
    private static final Log log = LogFactory.getLog(JSFunctions.class);
    
    private static JSFunctions instance;
    
    /**
     * interface for loading a new DataSourceInfo from a path
     */
    private DataLoader dataloader;
    
    public static JSFunctions get() {
        if (instance == null) {
            instance = new JSFunctions();
        }
        return instance;
    }
    
    private JSFunctions() {
        
    }
    
    public interface DataLoader {
        public DataStream load(String path) throws Exception;
    }
    
    public interface UnaryFormula {
        public Object eval(Object o);
    }
    
    public interface RowBinaryFormula {
        public Object eval(Row r1, Row r2);
    }

    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // built-in functions
    ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * load a given datasource by path
     * returns null if none found
     */
    public DataGrid load(String path) throws ScriptException {
        DataGrid ret = null;
        
        if (dataloader != null ) {
            try {
//              DataSourceInfo dsi = dataloader.load(path);
//                if (dsi == null) {
//                    throw new ScriptException("Data Source not found at: " + path);
//                }
                DataStream stream = dataloader.load(path);
                if (stream == null) {
                    throw new ScriptException("Data Source not found at: " + path);
                }
                //ret = new DataGrid(DSFactory.eval(dsi));
                ret = new DataGrid(stream);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ScriptException("Failed to load data source because of: " + e);
            }
        }
        return ret;
    }
    
    /**
     * Fail the current script with the specified error message
     * 
     * @param errMessage the error message
     */
    public void fail(String errMessage) throws ScriptException {
        throw new ScriptException(errMessage);
    }
    public void setLoader(DataLoader loader) {
        this.dataloader = loader;
    }

    /**
     *  update all rows by replace the values in the column 'col' with the result of 
     *  unary('col') 
     * @param dg
     * @param col
     * @param unary
     * @return
     */
    public DataGrid updateAll(DataGrid dg, DataColumn col, UnaryFormula unary) throws ScriptException {
        if (dg != null && dg.isValidColumn(col)) {
            for (int i = 0; i< dg.getRowCount();i++) {
                dg.setValue(i,col,unary.eval(dg.value(i,col)));
            } 
        }
        return dg;
    }
    
    
    /**
     * copy all the values from column1 to column 2
     * @param dg
     * @param col1
     * @param col2
     * @return
     * @throws ScriptException
     */
    public DataGrid copyCol(DataGrid dg, DataColumn col1, DataColumn col2) throws ScriptException {
        if (dg != null && dg.isValidColumn(col1) && dg.isValidColumn(col2)) {
            for (int i = 0; i< dg.getRowCount();i++) {
                dg.setValue(i,col2,dg.value(i,col1));
            } 
        }
        return dg;


    }
    
    /**
     * concatenate the values from col1 and col2 into column 3 with a spacer in between the values
     * 
     * @param dg
     * @param col1
     * @param col2
     * @param spacer   - constant string value, can be null
     * @param col3     - target column
     * @throws ScriptException
     */
    public DataGrid concatCol(DataGrid dg, DataColumn col1, DataColumn col2, String spacer, DataColumn col3) throws ScriptException {
        if (dg != null && dg.isValidColumn(col1) && dg.isValidColumn(col2) && dg.isValidColumn(col3)) {
            for (int i = 0; i< dg.getRowCount();i++) {
                StringBuffer sb = new StringBuffer();
                sb.append(dg.value(i, col1));
                if (spacer != null)
                    sb.append(spacer);
                sb.append(dg.value(i, col2));
                dg.setValue(i,col3,sb.toString());
            } 
        }
        return dg;


    }
    
    /**
     * return a new DataGrid that consists only of rows
     * where the predicate evaluates for the value at column col
     * 
     * @param dg  (datagrid is not modified)
     * @param col
     * @param pred
     * @return  a datagrid that share columns and rows that meet the predicate with the original
     */
    public DataGrid filter(DataGrid dg, DataColumn col, Predicate pred) throws ScriptException {
        DataGrid ret = dg;
        if (dg != null && dg.isValidColumn(col)) {
            ret = new DataGrid(dg.getColumns());
            for (int i=0;i<dg.getRowCount();i++) {
                Row row = dg.row(i);
                if (pred.eval(row.value(col.index()))) {
                   ret.addRow(row); 
                }
            }
        }
        return ret;
    }
    
    /**
     * group by for one column - for a given DataGrid, we sort all its rows by 'col' and then process the rows
     * by their 'col' value, evaluating the aggregator 'agg' for each set of rows with a common value 
     * in the 'col' column.  The resultant aggregate value is then stored in the column
     * specified in 'agg'
     * 
     * @param dg
     * @param col
     * @param agg
     * @return
     */
    public DataGrid groupby(DataGrid dg, DataColumn col, Aggregator agg) throws ScriptException {
        DataGrid ret = dg;
        if (dg != null) {
            DataColumn aggregateColumn = dg.col(agg.getColumnName());
            if (dg.isValidColumn(col) && dg.isValidColumn(aggregateColumn)) {
                ret = new DataGrid(dg.getColumns());

                int groupColumnIndex = col.index();
                int aggregateColumnIndex = dg.col(agg.getColumnName()).index();
                // step 1 - sort all the rows by the specified column
                dg.sort(col, false);

                // step 2 - iterate through the rows and whenever 
                // we see a new value in the column, then we need to start a new call to the aggregator
                DataGrid g = new DataGrid(dg.getColumns());
                Object current = null;
                boolean init = true;
                for (int i=0;i<dg.getRowCount();i++) {
                    Row row = dg.row(i);
                    Object value = row.value(col.index());
                    if (value == null) {
                        // skip null values 
                        continue;
                    }
                    if (init) {
                        current = value;
                        init = false;
                    } 
                    if (eq(value, current)) {
                        // we are still in the same group, add to it
                        g.addRow(row);
                    } else {
                        // we see a new value so we are in a different group

                        // first process the previous group
                        Object aggregateValue = agg.eval(g);
                        int r = ret.addNewRow();
                        ret.setValue(r,groupColumnIndex,current);
                        ret.setValue(r,aggregateColumnIndex, aggregateValue);

                        // start a new Group
                        g = new DataGrid(dg.getColumns());
                        g.addRow(row);
                        current = value;
                    }
                }
                // step 3 - process the last group
                if (g.getRowCount() > 0) {
                    Object aggregateValue = agg.eval(g);
                    int r = ret.addNewRow();
                    ret.setValue(r,groupColumnIndex,current);
                    ret.setValue(r,aggregateColumnIndex, aggregateValue);
                }

            }
        }
        return ret;
    }

    private static boolean eq(Object a, Object b) {
        if (a != null) {
            return a.equals(b);
        }
        return b == null;
    }
    /**
     * group by for two columns- for a given DataGrid, we sort all its rows by 'col' and 
     * secondarily by their 'col2' column and then process the rows
     * evaluating the aggregator 'agg' for each set of rows with a common set of 'col' and 'col2' values
     * The resultant aggregate value is then stored in the column specified in 'agg'
     * 
     * @param dg
     * @param col
     * @param col2
     * @param agg
     * @return
     */
    public DataGrid groupby(DataGrid dg, DataColumn col, DataColumn col2, Aggregator agg) throws ScriptException {
        DataGrid ret = dg;
        if (dg != null) {
            DataColumn aggregateColumn = dg.col(agg.getColumnName());
            if (dg.isValidColumn(col) && dg.isValidColumn(aggregateColumn)) {
                ret = new DataGrid(dg.getColumns());

                int groupCol1 = col.index();
                int groupCol2 = col2.index();
                int aggregateColumnIndex = dg.col(agg.getColumnName()).index();
                // step 1 - sort all the rows by the specified column
                dg.sort(col, false, col2, false);

                // step 2 - iterate through the rows and whenever 
                // we see a new value in the col or col2, then we need to start a new call to the aggregator
                DataGrid g = new DataGrid(dg.getColumns());
                Object current1 = null;
                Object current2 = null;
                boolean init = true;
                for (int i=0;i<dg.getRowCount();i++) {
                    Row row = dg.row(i);
                    Object value1 = row.value(col.index());
                    Object value2 = row.value(col2.index());
                    if (init) {
                        current1 = value1;
                        current2 = value2;
                        init =false;
                    } 
                    if (eq(value1, current1) && eq(value2, current2)) {
                        // we are still in the same group
                        g.addRow(row);
                    } else {
                        // we see a new value so we are in a different group

                        // first process the previous group
                        Object aggregateValue = agg.eval(g);
                        int r = ret.addNewRow();
                        ret.setValue(r,groupCol1, current1);
                        ret.setValue(r,groupCol2, current2);
                        ret.setValue(r,aggregateColumnIndex, aggregateValue);

                        // start a new Group
                        g = new DataGrid(dg.getColumns());
                        g.addRow(row);
                        current1 = value1;
                        current2 = value2;
                    }
                }
                // step 3 - process the last group
                if (g.getRowCount() > 0) {
                    Object aggregateValue = agg.eval(g);
                    int r = ret.addNewRow();
                    ret.setValue(r,groupCol1,current1);
                    ret.setValue(r,groupCol2,current2);
                    ret.setValue(r,aggregateColumnIndex, aggregateValue);
                }

            }
        }
        return ret;
    }

    /**
     * return a formula that will take a diff between the double column 'col' from two rows (r1 - r2)
     * returns null, if either column value is not a number 
     *  
     * @param col
     * @return
     */
    public RowBinaryFormula diff(DataColumn col) throws ScriptException {
        if (col == null) {
            throw new ScriptException("Cannot pass null column to diff");
        }
        final int colIndex = col.index();
        return new RowBinaryFormula() {
            public Object eval(Row r1, Row r2) {
                Object ret = null;
                Double d1 = null;
                Double d2 = null;
                if (r1 != null && !r1.isNull(colIndex)) {
                    d1 = r1.num(colIndex);
                }
                if (r2 != null && !r2.isNull(colIndex)) {
                    d2 = r2.num(colIndex);
                }
                if (d1 != null && d2 != null) {
                    ret = new Double (d1.doubleValue() - d2.doubleValue());
                }
                return ret;
            }
        };
    }
    
    public RowBinaryFormula diffPercent(DataColumn col) throws ScriptException {
        if (col == null) {
            throw new ScriptException("Cannot pass null column to diff");
        }
        final int colIndex = col.index();
        return new RowBinaryFormula() {
            // for time delta, 2nd number is the earlier number
            public Object eval(Row r1, Row r2) {
                Object ret = null;
                Double d1 = null;
                Double d2 = null;
                if (r1 != null && !r1.isNull(colIndex)) {
                    d1 = r1.num(colIndex);
                }
                if (r2 != null && !r2.isNull(colIndex)) {
                    d2 = r2.num(colIndex);
                }
                if (d1 != null && d2 != null) {
                    double n2 = d2.doubleValue();
                    if (n2 != 0) {
                        ret = new Double ((d1.doubleValue() - d2.doubleValue())/n2 * 100);
                    }
                }
                return ret;
            }
        };
        
    }
    /**
     * multiply the input by a scalar
     *  
     * @param scalar
     * @returns the unary formula
     */
    public UnaryFormula scale(double scalar) {
        final double val = scalar;
        return new UnaryFormula() {
            public Object eval(Object o) {
                Object ret = null;
                if (o != null) {
                    ret = new Double(((Number)o).doubleValue() * val);
                } 
                return ret;
            }
        };
    }

    /**
     * always returns a constant
     * @param o
     * @return
     */
    public UnaryFormula constant(Object o) {
        final Object c = o;
        return new UnaryFormula() {
            public Object eval(Object o) {
                return c;
            }
        };
    }
    
    /**
     * Create a date from the given string
     */
    public Date date(String inputString) throws ParseException {
        Date ret = null;
        DateRecognizer dr = new DateRecognizer();
        dr.reset();
        dr.parse(inputString);
        if (!dr.failed()) {
            ret = dr.getSimpleDateFormat().parse(inputString);
        }
        return ret;
    }
    
    /**
     * returns true if the datetruncc param is legal, false otherwise 
     * @param s
     * @return
     */
    public void verifyDateTruncParam(String s) throws ScriptException {
        boolean ok = ("year".equalsIgnoreCase(s) || 
        		"month".equalsIgnoreCase(s) ||
        		"quarter".equalsIgnoreCase(s) ||
        		"day".equalsIgnoreCase(s));
        if (!ok) {
            throw new ScriptException("Date trunc parameter must be one of [year|quarter|month|day]");
        }
    }
    /**
     * truncate the date by part 
     * takes a Date object and returns a Date object
     * 
     * @param part  - possible values are  year|month|day
     * @return
     */
    public UnaryFormula date_trunc(String part) throws ScriptException {
       final String datePart = part;
       verifyDateTruncParam(part);
       return new UnaryFormula() {
           public Object eval(Object o) {
               Object ret = null;
               if (o != null && o instanceof Date) {
                   Date d = (Date)o;
                   Calendar cal = Calendar.getInstance();
                   cal.setTime(d);
                   if ("year".equalsIgnoreCase(datePart)) {
                       cal.set(cal.get(Calendar.YEAR),0,1,0,0,0);
                       cal.set(Calendar.MILLISECOND, 0);
                   } else if ("month".equalsIgnoreCase(datePart)) {
                       cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);
                       cal.set(Calendar.MILLISECOND, 0);
                   } else if ("day".equalsIgnoreCase(datePart)) {
                       cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),0,0,0);
                       cal.set(Calendar.MILLISECOND, 0);
                   } else if ("quarter".equalsIgnoreCase(datePart)) {
                       int m = cal.get(Calendar.MONTH);
                       
                       int qm;
                       if (m < 3) { // Jan/Feb/Mar
                           qm = Calendar.JANUARY; 
                       } else if (m < 6) { // May/June/July
                           qm = Calendar.APRIL;
                       } else if (m < 9) { // Aug/Sept/Oct 
                           qm = Calendar.JULY;
                       } else 
                           qm = Calendar.OCTOBER;

                       cal.set(cal.get(Calendar.YEAR),qm,1,0,0,0);
                       cal.set(Calendar.MILLISECOND, 0);
                   } else {
                       log.warn("unknown part string: " + datePart + " passed to date_trunc");
                   }
                   ret = cal.getTime();
               }
               return ret;
           }
       };
    }
    
    public Reducer sum() {
        return new BuiltInFunctions.Sum();
    }

    public Reducer avg() {
        return new BuiltInFunctions.Average();
    }

    public Reducer max() {
        return new BuiltInFunctions.Max();
    }

    public Reducer min() {
        return new BuiltInFunctions.Min();
    }

    public Reducer count() {
        return new BuiltInFunctions.Count();
    }
    
    /**
     * returns an aggregator to be used in a groupby function
     *  
     * @param colName
     * @param r
     * @return
     */
    public Aggregator aggregate(String colName, Reducer r) {
        return new Aggregator(colName,r);
    }

    
    private Double evalAggregator(DataColumn col, Reducer r) throws ScriptException {
        Double ret = null;
        if (col != null) {
            Aggregator agg = new Aggregator(col.getName(),r);
            ret = (Double) agg.eval(col.data());
        }
        return ret;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////
    // built-in aggregates
    ///////////////////////////////////////////////////////////////////////////////////////////
    /** 
     * average up all the numeric values for a particular column
     * 
     * @param col - must describe a numeric type
     * @return
     */
    public Double avg(DataColumn col) throws ScriptException {
        return evalAggregator(col,avg());
    }

    /** 
     * maximum of all numeric values for a particular column
     * 
     * @param col - must describe a numeric type
     * @return
     */
    public Double max(DataColumn col) throws ScriptException {
        return evalAggregator(col,max());
    }
    
    /** 
     * minimum of all numeric values for a particular column
     * 
     * @param col - must describe a numeric type
     * @return
     */
    public Double min(DataColumn col) throws ScriptException {
        return evalAggregator(col,min());
    }

    /** 
     * sum up all the numeric values for a particular column
     * 
     * @param col - must describe a numeric type
     * @return
     */
    public Double sum(DataColumn col) throws ScriptException {
        return evalAggregator(col,sum());
    }


    /** 
     * sum up all the numeric values for a particular column
     * 
     * @param col - must describe a numeric type
     * @return
     */
    public Double count(DataColumn col) throws ScriptException  {
        return evalAggregator(col,count());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////
    // built-in predicates
    ////////////////////////////////////////////////////////////////////////////////////////////
    // check for equality based on .equals()
    public Predicate eq(Object v) {
        final Object val = v;
        return new Predicate() {
            public boolean eval(Object o) {
                boolean ret;
                if (o != null) {
                    ret = o.equals(val);
                } else
                    ret = (val == null);
//                log.warn("Predicate eq: comparing o = " + o + " with val = " + val + " returns ret = " + ret);
                return ret;
            }
        };
    }

    public Predicate neq(Object v) {
        final Object val = v;
        return new Predicate() {
            public boolean eval(Object o) {
                boolean ret;
                if (o != null) {
                    ret = !o.equals(val);
                } else
                    ret = (val != null);
                return ret;
            }
        };
        
    }

    public Predicate gt(Object v) {
        final Object val = v;
        return new Predicate() {
            public boolean eval(Object o) {
                boolean ret = greaterThan(o,val);
//                log.warn("Predicate gt: comparing o = " + o + " with val = " + val + " returns ret = " + ret);
                return ret;
            }
        };
    }

    
    public Predicate lt(Object v) {
        final Object val = v;
        return new Predicate() {
            public boolean eval(Object o) {
                boolean ret = lessThan(o,val);
//                log.warn("Predicate lt: comparing o = " + o + " with val = " + val + " returns ret = " + ret);
                return ret;
            }
        };
    }

    
    // returns boolean opposite of 
    public Predicate not(Predicate p) {
        final Predicate pred = p;
        return new Predicate() {
            public boolean eval(Object o) {
                return !(pred.eval(o));
            }
        };
    }

    // returns boolean opposite of 
    public Predicate and(Predicate p1, Predicate p2) {
        final Predicate pred1 = p1;
        final Predicate pred2 = p2;
        return new Predicate() {
            public boolean eval(Object o) {
                return (pred1.eval(o) && pred2.eval(o));
            }
        };
    }

    // returns boolean opposite of 
    public Predicate or(Predicate p1, Predicate p2) {
        final Predicate pred1 = p1;
        final Predicate pred2 = p2;
        return new Predicate() {
            public boolean eval(Object o) {
                return (pred1.eval(o) || pred2.eval(o));
            }
        };
    }
    
    
    /**
     * compare two objects that can be Number/Date/String
     * does not try to coerce them, only if both objects are of the same type will this do a true comparison
     * otherwise, returns false
     * 
     * @param o1
     * @param o2
     * @return
     */
    private boolean greaterThan(Object o1, Object o2) {
        boolean ret = false;
        if (o1 != null && o2 != null) {
            if (o1 instanceof Number && o2 instanceof Number) {
                ret =  ((Number)o1).doubleValue() > ((Number)o2).doubleValue();
            } else 
                if (o1 instanceof Date && o2 instanceof Date) {
                    ret = ((Date)o1).getTime() > ((Date)o2).getTime();
                } else
                    if (o1 instanceof String && o2 instanceof String) {
                        ret = ((String)o1).compareTo((String)o2) > 0;
                    }
        }
        return ret;
    }
    

    /**
     * compare two objects that can be Number/Date/String
     * does not try to coerce them, only if both objects are of the same type will this do a true comparison
     * otherwise, returns false
     * 
     * @param o1
     * @param o2
     * @return
     */
    private boolean lessThan(Object o1, Object o2) {
        boolean ret = false;
        if (o1 != null && o2 != null) {
            if (o1 instanceof Number && o2 instanceof Number) {
                ret =  ((Number)o1).doubleValue() < ((Number)o2).doubleValue();
            } else 
                if (o1 instanceof Date && o2 instanceof Date) {
                    ret = ((Date)o1).getTime() < ((Date)o2).getTime();
                } else
                    if (o1 instanceof String && o2 instanceof String) {
                        ret = ((String)o1).compareTo((String)o2) < 0;
                    }
        }
        return ret;
    }
}
