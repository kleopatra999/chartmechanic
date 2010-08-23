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
 * exception raised in the course of doing Inference
 */
public class InferenceException extends Exception {
    
    private int code;
    private String host;
    
    public InferenceException() {}
    public InferenceException (String msg) {
        super(msg);
    }
    public InferenceException(String msg, Throwable nested) {
        super(msg,nested);
    }

    public InferenceException (String msg, int code) {
        super(msg);
        this.code = code;
    }

    public InferenceException (String msg, int code, String host) {
        super(msg);
        this.code = code;
        this.host = host;
    }
    
    public String getMsg() {
        return getMessage();
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getHost() {
        return this.host;
    }
    
    // is this exception related to needing authentication
    public boolean requiresAuth() {
        return this.code == 401 || this.code == 403;
    }
}
