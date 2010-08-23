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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.StringUtil;

/**
 * JSEngine is our internal script engine that executes transformations on DataStream using
 * javascript
 * 
 */
public final class JSEngine {

    private static final Log log = LogFactory.getLog(JSEngine.class);
    private JSEngine() {}
    
//    public static final String SCRIPT_PREFIX = "//JS";
    
    public static boolean isScript(String q) {
        return StringUtil.trim(q) != null;
//        return q != null && q.startsWith(SCRIPT_PREFIX);
    }
    
    public static DataStream evalStream(DataStream ds, String script) throws Exception {
        if (isScript(script)) {
            DataGrid grid = evalGrid(ds,script);
            return grid.toStream();
        }
        return ds;
    }

    private static void verifyScript(String script) throws ScriptException {
        // verify that
        if (script.indexOf("importPackage") != -1) {
            throw new ScriptException("importing packages is not allowed in script");
        }
 
    }
    
//    private static void showBindings(Bindings b) {
//        if (b != null) {
//            for (String k : b.keySet()) {
//                log.warn("bindings: k = " + k + " v= " + b.get(k));
//            }
//        }
//
//    }

    private static DataGrid evalGrid(DataStream inputData, String script) throws Exception {
        
        verifyScript(script);
        
        // TODO: in the future, allow multiple args to be passed in
        DataGrid[] args = new DataGrid[1];
//        DataGrid arg0 = null;
        try {
            args[0] = new DataGrid(inputData);
        } finally {
            // done with reading original stream, close it
            // don't leak streams!
            inputData.close();
            inputData = null;
        }
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("ECMAScript");
        
        // add this for JDK 1.5 + JSR 223 RI backwards compatibility
        if (jsEngine == null) {
            jsEngine = mgr.getEngineByName("js");
            if (jsEngine == null) {
                System.err.println("Couldn't find 'ECMAScript' or 'js' engine");
                return null;
            }
        }
        //jsEngine.put("data", ret);

//        SimpleScriptContext ssc = new SimpleScriptContext();
//        jsEngine.setContext(ssc);
        DataGrid ret = null;
        try {
            jsEngine.put("functions",JSFunctions.get());                    // builtin functions
            jsEngine.put("args",args);                                      // the arguments to the script
            jsEngine.eval("var data = args[0]; var fn = functions");        // some useful aliases
            
//            showBindings(jsEngine.getBindings(ScriptContext.ENGINE_SCOPE));
//            showBindings(jsEngine.getBindings(ScriptContext.GLOBAL_SCOPE));
            jsEngine.eval(script);
            
            
            Object result = jsEngine.get("data");
            if (result != null) {
                if (result instanceof DataGrid) {
                    ret = (DataGrid)result;
                } else {
                    log.error("Script must return a DataGrid object");
                    throw new ScriptException("Script must return a DataGrid object");
                }
            }
            
        } catch (ScriptException se) {
            if (log.isDebugEnabled()) {
                log.debug("caught script exception, re-throwing", se);
            }
            rethrowScriptException(se);
        }
        
        return ret;
    }
    
//    private static void printFactories() {
//        ScriptEngineManager mgr = new ScriptEngineManager();
//        for (ScriptEngineFactory sef : mgr.getEngineFactories()) {
//            System.err.println("name=" + sef.getEngineName() + 
//                              "\tlang=" + sef.getLanguageName() +
//                              "\tshort names: ");
//            for (String n : sef.getNames()) {
//                System.err.print(n + " ");
//            }
//            System.out.println();
//        }
//    }

    
    private static void rethrowScriptException(ScriptException sce) throws ScriptException {
        String msg = null, filename = null;
        int line = sce.getLineNumber();
        int column = sce.getColumnNumber();
        filename = sce.getFileName();
        msg = sce.getMessage();
        if (msg == null) { msg = ""; }
        // If this doesn't cover all the cleanup we want, use Regex...
        if (filename == null || "<Unknown source>".equals(filename)) {
            filename = "SCRIPT";
        }
        msg = msg.replace("Wrapped javax.script.ScriptException: ","");
        msg = msg.replace("org.mozilla.javascript.WrappedException: ", "");
        msg = msg.replace("org.mozilla.javascript.EvaluatorException: ", "");
        msg = msg.replace("sun.org.mozilla.javascript.internal.EvaluatorException: ", "");
        msg = msg.replace("sun.org.mozilla.javascript.internal.EcmaError: ", "");
        msg = msg.replace("com.bayareasoftware.chartengine.js.", "");
        int ind = msg.indexOf("(<Unknown source>");
        if (ind != -1) {
            msg = msg.substring(0, ind);
        }
        throw new ScriptException(msg, filename, line, column);
    }
//    private static DataStream loadUnderlyingStream(DataSourceInfo dsi) throws Exception {
//        String origScript = dsi.getDataScript();
//        try {
//            dsi.setDataScript(null);
//            //return DSFactory.createDataSource(dsi).getDataStream();
//            return DSFactory.eval(dsi);
//        } finally {
//            dsi.setDataScript(origScript);
//        }
//    }
//
//    private static void p(String s) {
//        System.err.println("[JSEngine] " + s);
//    }
}
