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
 * IdGeneratorFactory is a means to generate unique IDs
 * allow different generators so we can have different client/server
 * behavior.
 * 
 * On the client, we generate client-scoped unique IDs (negative values)
 * and on the server side, we don't generate IDs, but instead assign
 * their persistent IDs from the database
 *
 */
public class IdGeneratorFactory {
    private static IdGenerator generator;
    
    public static void setGenerator(IdGenerator g) {
        if (generator != null) {
            throw new RuntimeException("Cannot set the IdGeneratoryFactory more than once");
        } else 
            generator = g;
    }
    public static IdGenerator getGenerator() {
        return generator;
    }
    
    public static Integer nextId() {
        if (generator == null){
            setGenerator(new NegativeIdGenerator());
        }
        if (generator != null) {
            return generator.nextID();
        } else 
            return null;
    }

}
