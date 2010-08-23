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
package com.bayareasoftware.chartengine.model;

/**
 * a data structure that contains ways that series data can be transformed
 * 
 *  converts to/from datascript 
 */
public class SeriesTransform {

    /**
     * the column in the data source that our transformation will apply to
     */
    private int column;
    
    /**
     * null, ascending, or descending
     */
    private String sortOrder;
    
    /**
     * comparator such as >,<,=,!=
     */
    private String filterOp;
    
    /**
     * string representation of numeric value to compare against  
     */
    private String filterValue;

    /**
     * the value to scale the Y column 
     */
    private String scaleValue;
    
    /**
     * the column for the Z values
     */
    private int zColumn = -1;
    
    /**
     * the value to scale the Z column
     */
    private String scaleZValue;
    
    /**
     * limit count of number of values
     * could be null.
     */
    private Integer limit;
    
    private static final char SEP = ',';
    private static final String GENSCRIPT_COMMENT = "//GENERATED";
    
    public static final String GT = ">";
    public static final String LT = "<";
    public static final String EQ = "=";
    public static final String NEQ = "!=";

    public static final String ASC = "Ascending";
    public static final String DESC = "Descending";

    // number of comma-separated parts that we use as our string representation
    private static final int NUM_PARTS = 8;
    
    public SeriesTransform() {
    }
    
    /**
     * get string representations of the sort orders
     * the first element is null to represent no sort
     * @return
     */
    public static String[] getSortOrders() {
        return new String[] { "", ASC, DESC };
    }
    
    /**
     * get string representations of the sort orders
     * the first element is null to represent no filter
     * @return
     */
    public static String[] getFilterOps() {
        return new String[] { "", GT, LT, EQ, NEQ }; 
    }
    
    /**
     * parse the generated script comment to get the constituent parts 
     * very crude, caveman-like approach.  Logic needs to match genScript()
     * 
     * returns true if script was parsed successfully
     */
    public boolean parseScript(String s) {
        boolean ret = false;
        String[] scriptLines = StringUtil.splitCompletely(s,'\n',true);
        if (scriptLines.length > 0) {
            String comment = scriptLines[scriptLines.length-1];
            if (comment.startsWith(GENSCRIPT_COMMENT)) {
                comment = comment.substring(GENSCRIPT_COMMENT.length()+1); // skip the comment and the first SEP
                String[] parts = StringUtil.splitCompletely(comment,SEP,true);
//                for (int i = 0; i < parts.length; i++) {
//                    System.err.println("parts["+i+"]= " + parts[i]); 
//                }
                if (parts.length > 0) {
                    if (parts[0] != null && parts[0].length() > 0) {
                        column = Integer.parseInt(parts[0]);
                    }
                }
                
                sortOrder = null;
                if (parts.length > 1) {
                    if (parts[1] != null && parts[1].length() > 0) {
                        sortOrder = parts[1];
                    } 
                }

                filterOp = null;
                if (parts.length > 2) {
                    if (parts[2] != null && parts[2].length() > 0) {
                        filterOp = parts[2];
                    }
                }

                filterValue = null;
                if (parts.length > 3) {
                    if (parts[3] != null && parts[3].length() > 0) {
                        filterValue = parts[3];
                    }
                }

                if (parts.length > 4) {
                    if (parts[4] != null && parts[4].length() > 0) {
                        limit = new Integer(Integer.parseInt(parts[4]));
                    }
                }
                
                if (parts.length > 5) {
                    if (parts[5] != null && parts[5].length() > 0) {
                        scaleValue = parts[5];
                    }
                }
                
                if (parts.length > 6) {
                    if (parts[6] != null && parts[6].length() > 0) {
                        zColumn = Integer.parseInt(parts[6]);
                    }                    
                }
                
                if (parts.length > 7) {
                    if (parts[7] != null && parts[7].length() > 0) {
                        scaleZValue = parts[7];
                    }                    
                }
                
                
                ret = true;
            }
        }
        return ret;
    }

    /**
     * this is a crude way to generate code
     * 
     * the encoded comment line looks like
     *  // GENERATED,<column>,<sortOrder>,<filterOp>,<filterValue>,<limit>,<scaleValue>,<zColumn>,<zScaleValue>
     * 
     * @return generated, legal datascript with a final comment line that encodes the SeriesTransform
     */
    public String genScript() {
        StringBuffer script = new StringBuffer();
        
    //    StringBuffer comment = new StringBuffer();
        String[] parts = new String[NUM_PARTS];
        
        //comment.append(GENSCRIPT_COMMENT);
        //comment.append(column).append(SEP);
        parts[0] = String.valueOf(column);
        
        if (StringUtil.trim(sortOrder) != null) {
            //comment.append(sortOrder);
            parts[1] = sortOrder; 
            script.append("data.sort("+column+",");
            script.append(sortOrder.equals(DESC)+")");
            script.append("\n");
        } else
            parts[1] = null;
        

        boolean hasFilter = StringUtil.trim(filterOp) != null && StringUtil.trim(filterValue) != null;
        
        if (hasFilter) {
            parts[2] = filterOp;
        } else {
            parts[2] = null;
        }
        
        if (hasFilter) {
            parts[3] = filterValue;
            script.append("data.filter("+column+",");
            String funcName = "fn.eq";
            if (filterOp.equals(GT)) {
                funcName = "fn.gt";
            } else if (filterOp.equals(LT)) {
                funcName = "fn.lt";
            } else if (filterOp.equals(NEQ)) {
                funcName = "fn.neq";
            }
            script.append(funcName + "(" + filterValue + "))");
            script.append("\n");
        } else {
            parts[3] = null;
        }
        
        if (limit != null) {
            script.append("data.limit(0,"+limit+")\n");
            parts[4] = String.valueOf(limit);
        } else {
            parts[4] = null;
        }
        
        if (scaleValue != null) {
            script.append("data.scale("+column+","+scaleValue+")\n");
            parts[5] = String.valueOf(scaleValue);
        } else 
            parts[5] = null;


        parts[6] = String.valueOf(zColumn);
        
        if (scaleZValue != null && zColumn != -1) {
            script.append("data.scale("+zColumn+","+scaleZValue+")\n");
            parts[7] = String.valueOf(scaleZValue);
        } else 
            parts[7] = null;

        StringBuffer comment = new StringBuffer();
        comment.append(GENSCRIPT_COMMENT);
        for (String p : parts) {
            comment.append(SEP);
            if (p != null)
                comment.append(p);
        }
        script.append(comment);
        
        return script.toString();
    }

    public String getScaleValue() {
        return scaleValue;
    }
    
    public void setScaleValue(String scaleValue) {
        this.scaleValue = scaleValue;
    }
    
    public String getScaleZValue() {
        return scaleZValue;
    }
    
    public void setScaleZValue(String s) {
        this.scaleZValue = s;
    }
    
    
    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getFilterOp() {
        return filterOp;
    }

    public void setFilterOp(String filterOp) {
        this.filterOp = filterOp;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public int getColumn() {
        return column;
    }
    
    public int getZColumn() {
        return zColumn;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setZColumn(int column) {
        this.zColumn = column;
    }
    
    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
