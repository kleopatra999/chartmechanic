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

import java.util.ArrayList;
import java.util.List;


import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ParamMetaData;
import com.bayareasoftware.chartengine.model.StringUtil;

/**
 * Parses complete queries included handling of special embedded query parameters
 * 
 * Parses statements like:
 * <pre>
 * SELECT D,OBS
 * FROM my_table
 * WHERE
 * D > ?${DATE startDate=1900-01-01}
 * AND OBS > ?${DOUBLE minObs=0}
 * </pre>
 * @author dave
 *
 */
public class QueryParser {
    private List<ParamMetaData> params = new ArrayList<ParamMetaData>();
    private String orig, result;
    public QueryParser(String sql) {
        orig = sql;
    }
    public String getResult() {
        if (result == null) {
            result = parse();
        }
        return result;
    }
    public List<ParamMetaData> getParams() {
        getResult();
        return params;
    }
    /**
     * from a given query, infer a set of param meta data and optional default values
     * 
     *  we look for ?${<type> <name>=<default-value>} in the query string
     *  and replace those with plain ?
     *  and then we look for paramName in our paramMetaData
     *  and create the appropriately indexed entry in the JDBCParams array
     *  with the correct type and value.
     *  The value comes either from the param values or from the default value of the param Meta data
     *  
     * @param query - query string.  
     *        params - an empy List<JDBCParam> that is modified by this method  
     * @return the new query string (only '?' without param names, ready to send to JDBC )
     *         the List<JDBCParams> is a list of JDBCParams (<int,String> pairs) that are 
     *         ready to be used by PreparedStatement to bind the JDBC query parameters
     */    
    private String parse() {
        int i = 0;
        int len = orig.length();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        StringBuilder sb = new StringBuilder();
        while (i < len ) {
            int c = orig.codePointAt(i);
            if (c == '?' && !(inSingleQuote || inDoubleQuote)) {
                int s = skipWS(i+1);
                // next chars should look like '${DATE startDate=1991-09-09}'
                c = orig.codePointAt(s);
                if (c != '$') {
                    badChar(s, '$');
                }
                c = orig.codePointAt(++s);
                if (c != '{') {
                    badChar(s, '{');
                }
                int ind = orig.indexOf('}', s);
                if (ind == -1) {
                    throw new RuntimeException(
                            "Unterminated expression (open brace '{'"
                            + " begins at index " + s + ")"
                            );
                }
                String expr = orig.substring(s+1, ind);
                //p("got expression '" + expr + "'");
                ParamMetaData pmd = parseParam(expr, s);
                //p("parsed param: " + pmd);
                params.add(pmd);
                sb.append('?');
                i = ind + 1;
            } else {
                // if we were in quotes, we reached the end of it
                // otherwise, we are entering a quoted string
                if (c == '\'') {
                    inSingleQuote = !inSingleQuote;
                }
                if (c == '"') {
                    inDoubleQuote = !inDoubleQuote; 
                }
                sb.appendCodePoint(c);
                i++;
            }
        }
        return sb.toString();
    }
    
    private void badChar(int index, char expected) {
        StringBuilder sb = new StringBuilder();
        int bad = orig.codePointAt(index);
        sb.append("bad character at index ").append(index)
        .append(": expected '").append(expected).append('\'')
        .append(" not '").appendCodePoint(bad).append('\'');
        throw new RuntimeException(sb.toString());
    }
    private void badText(int index, String msg) {
        throw new RuntimeException(
                "unparseable text starting at location " + index
                + ": " + msg
                );
    }
    private int skipWS(int start) {
        int c;
        int ret = start;
        int len = orig.length();
        while (ret < len && Character.isWhitespace(c = orig.codePointAt(ret))) {
            ret++;
        }
        return ret;
    }
    
    /*
     * of the form: TYPE name=defaultValue
     * e.g. 'DATE startDate=1991-02-01'
     *   'DOUBLE minObs=10.0'
     */
    private ParamMetaData parseParam(String in, int location) {
        ParamMetaData ret = null;
        int type = DataType.UNKNOWN;
        String name = null, defaultVal = null;
        String[] split = StringUtil.split(in, ' ');
        if (split.length < 2) {
            badText(location, "expected valid data type");
        }
        type = DataType.parse(split[0]);
        if (type == DataType.UNKNOWN || type == DataType.IGNORE || type == DataType.__LAST) {
            badText(location, "invalid data type: '" + split[0] + "'");
        }
        split = StringUtil.split(split[1], '=', false);
        name = split[0].trim();
        if (split.length > 1) {
            defaultVal = split[1];
        }
        ret = new ParamMetaData(name, type, defaultVal);
        return ret;
    }
    public static void main(String[] a) throws Exception {
        String q = "SELECT D,OBS FROM my_table\n"
        + " WHERE D > ?${DATE startDate=1931-11-25}\n"
        + " AND OBS > ?${number minObs=-10.0}\n"
        + " ORDER BY D ASC";
        QueryParser qp = new QueryParser(q);
        String res = qp.getResult();
        p("resulting query: " + res);
        p("params: " + qp.getParams());
    }
    
    private static void p(String s) {
        System.out.println(s);
    }
}
