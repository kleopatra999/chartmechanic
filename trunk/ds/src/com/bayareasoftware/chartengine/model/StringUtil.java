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
 * Generic string manipulation utilities.  They're in the model package
 * so that it is available in GWT clients.
 *
 */
public class StringUtil {

    /**
     * trim a string of whitespace.   If a string is null or only consists of whitespace return null
     * 
     * @param in
     * @return
     */
    public static String trim(String in) {
        if (in == null || (in = in.trim()).length() == 0) {
            in = null;
        } else if (in.indexOf(NBSP) != -1) {
            in = in.replace(NBSP, ' ');
            if ((in = in.trim()).length() == 0) {
                in = null;
            }
        }
        return in;
    }

    /**
     * split a String into an array of two strings based on the delim character.  Also trims any whitespaces
     * e.g. split("hello:world",':')  ->   ["hello","world"]
     *      split("hello:world:there",':')  ->   ["hello","world:there"]
     * 
     * @param input   - must be non-null
     * @param delim
     * @return
     */
    public static String[] split(String input, char delim) {
        return split(input, delim, true);
    }

    /**
     * split a String into an array of two strings based on the delim character.  Also trims any whitespaces
     * 
     * @param input   - must be non-null
     * @param delim
     * @param trim    - if true, trims any whitespace
     * @return
     */
    public static String[] split(String input, char delim, boolean trim) {
        String[] ret;
        int ind = input.indexOf(delim);
        if (ind < 0) {
            ret = new String[1];
            ret[0] = input;
        } else {
            ret = new String[2];
            ret[0] = input.substring(0, ind);
            ret[1] = input.substring(ind + 1);
            if (trim) {
                ret[0] = ret[0].trim();
                ret[1] = ret[1].trim();
            }
        }
        return ret;
    }

    /**
     * split a string completely into an array of Strings based on a character delimiter
     * e.g.
     *  e.g. split("abc,def,ghi",',')  ->   ["abc","def","ghi"]
     * @param input
     * @param delim
     * @return
     */
    public static String[] splitCompletely(String input, char delim) {
        return splitCompletely(input, delim, false);
    }

    /**
     * split a string completely into an array of Strings
     * @param input
     * @param delim
     * @param trim    - if true, trims the strings too
     * @return
     */
    public static String[] splitCompletely(String str, char delim, boolean trim) {
        if (str == null)
            return null;
        int len = str.length();
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == delim) {
                count++;
            }
        }
        String[] ret = new String[count + 1];
        int begin = 0, off = 0;
        for (int end = 0; end < len; end++) {
            if (str.charAt(end) == delim) {
                String s = str.substring(begin, end);
                if (trim) {
                    s = s.trim();
                }
                ret[off++] = s;
                begin = end + 1;
            }
        }
        ret[off] = str.substring(begin);
        if (trim) {
            ret[off] = ret[off].trim();
        }
        return ret;
    }

    /**
     * Join path elements ensuring that all the results are separated
     * by exactly one '/'
     */
    public static String joinPaths(String... paths) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            // empty string, null -> not there
            if (path == null) continue;
            int len = path.length();
            if (len == 0) continue;
            if (i > 0 && path.charAt(0) == '/') {
                path = path.substring(1);
                if (--len == 0) continue;
            }
            if (ret.length() > 0 && ret.charAt(ret.length() - 1) != '/') {
                // previous element may have ended with '/'
                ret.append('/');
            }
            ret.append(path);
        }
        return ret.toString();
    }
    
    /**
     * remove adjacent whitespace
     * collapses/converts any series of whitespace into a single character
     * 
     * @param    in   - input string
     * @param    single  - single character to collapse into
     * @param    preserveNewline  - if true, preserve new line, else treat them as whitespace
     */
    public static String collapseWS(String in, char single, boolean preserveNewline) {
        if (in == null)
            return null;
        in = in.trim();
        StringBuilder ret = new StringBuilder();
        int len = in.length();
        boolean inWS = false;
        for (int i = 0; i < len; i++) {
            char c = in.charAt(i);
            if (isWhitespace(c)) {
                if (c == '\n' && preserveNewline) {
                    // if character is a newline and we are preserving newlines
                    // then don't treat it as a whitespace
                    ret.append(c);
                    inWS = false;
                } else {
                    if (!inWS) {
                        ret.append(single);
                    }
                    inWS = true;
                } 
            } else {
                ret.append(c);
                inWS = false;
            }
        }
        return trim(ret.toString());
        
    }
    /**
     * remove adjacent whitespace
     * collapses/converts any series of whitespace into a single space
     */
    public static String collapseWS(String in) {
        return collapseWS(in,' ',false);
    }

    /**
     * remainder of string after the last slash
     */
    public static String afterSlash(String s) {
        if (s != null) {
            int ind = s.lastIndexOf('/');
            if (ind != -1 && ind < s.length() - 1) {
                s = s.substring(ind + 1);
            }
        }
        return s;
    }
    
    
    /**
     * From 'dave/Case Shiller.chart' -> 'Case Shiller'
     */
    public static String getSimpleName(String name) {
        String ret = name;
        if (ret != null) {
            ret = beforeDot(afterSlash(ret));
        }
        return ret;
    }
    
    /**
     * Return an educated guess about a reasonable "Name" of something, giving a URL
     */
    public static String url2name(String url) {
        //String ret = beforeDot(afterSlash(url));
        String ret = getSimpleName(url);
        
        if (ret != null) {
            ret = legalPath(ret);
        }
        if (ret != null) {
            ret = collapseWS(ret);
        }

        // sometimes the URL is encoded with spaces, replace those as we can tolerate spaces in the names
        if (ret != null) {
            ret = ret.replaceAll("%20"," ");
        }
        return ret;
    }
    /**
     * given a string, munge it into a legal path so we
     * only have letters, digits and underbar
     * @param in
     * @return
     * 
     * also see "Illegal Characters on Various Operating Systems"
     * http://www.grouplogic.com/knowledge/index.cfm/fuseaction/view_Info/docID/111
     */
    public static String legalPath(String in) {
        StringBuilder res = new StringBuilder();
        // attempt to make a name out of only the letters and digits, plus underbar '_'
        for (int i=0;i<in.length();i++) {
            char c = in.charAt(i);
            if (Character.isLetterOrDigit(c)
                    || c == '_' || c== '.' || c == ' ' || c == '-'
                    || c == '(' || c == ')' || c == '&' || c == '@'
                    || c == '%' || c == '!') {
                res.append(c);
            } else {
                //res.append("_");
                res.append(" ");
            }
        }
        String ret = res.toString();
        return ret;
    }
    
    public static String beforeDot(String input) {
        String ret = input;
        if (input != null) {
            int lastDot = input.lastIndexOf(".");
            if (lastDot > 0) {
                ret = input.substring(0, lastDot);
            }
        }
        return ret;
    }
    
    private static final char NBSP = (char)160;
    private static boolean isWhitespace(char c) {
        ///return Character.isWhitespace(c);
        // from "man iso_8859-1" decimal 160 is non-break space
        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == NBSP;
    }
    
    

    public static final String COLUMN_DELIMITERS[][] = {
        {"COMMA",","},
        {"TAB","\t"},
        {"COLON",":"},
        {"SEMI-COLON",";"},
        {"VERTICAL BAR","|"}
//        {"WHITESPACE","ws"}
    };

    public static String handleCSVColumnDelimiter(String ret, String delim) {
        if (ret != null) {
            if (delim == null || delim.length() == 0) {
                delim = "\t";
            }
//            if (delim.equals("ws")) {
//                // collapse all whitespaces into tabs, preserving newlines
//                ret = collapseWS(ret,'\t',true);
//                delim = "\t";
//            }
            int ind = ret.indexOf(delim);
            if (ind != -1) {
                // if there are tabs, assume the whole thing is tab-separated,
                // rather than comma separated, and that any commas are
                // for number/date formatting
                // FIXME: need better control over this - maybe a checkbox
                // for tab/comma separated? or at least we can do more
                // intelligent inferencing...
                if (!delim.equals(",")) {
                    ret = ret.replace(",", "");
                    //ret = ret.replace('\t', ',');
                    ret = ret.replace(delim,",");
                }
            }
        }
        return ret;
    }
}
