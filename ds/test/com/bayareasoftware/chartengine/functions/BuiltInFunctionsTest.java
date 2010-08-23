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
package com.bayareasoftware.chartengine.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuiltInFunctionsTest {

    /**
     * evaluate the reducer function on the given array of vals
     * @param r
     * @param vals
     */
    private void eval(Reducer r, double[] vals) {
        r.init(null);
        for (double v: vals) {
            r.iter(new Double(v));
        }
        r.end();
    }

    private void eval(Reducer r, double[] vals, double arg) {
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(arg));
        r.init(args);
        for (double v: vals) {
            r.iter(new Double(v));
        }
        r.end();
    }
            

    
    @Test
    public void testAverage() {
        Reducer r = (Reducer)BuiltInFunctions.makeFunction(BuiltInFunctions.FN_AVG);
        eval(r,new double[] {
                1.0,
                2.0,
                3.0,
                4.0,
                5.0 });
        assertEquals(r.value(),3.0);

        eval(r,new double[] { 100.0 });
        assertEquals(r.value(),100.0);
    }
    
    
    @Test
    public void testMin() {
        Reducer r = (Reducer)BuiltInFunctions.makeFunction(BuiltInFunctions.FN_MIN);
        eval(r,new double[] {
                1.0,
                2.0,
                3.0,
                4.0,
                5.0 });
        assertEquals(r.value(),1.0);

        eval(r,new double[] { 100.0 });
        assertEquals(r.value(),100.0);
    }

    @Test
    public void testMax() {
        Reducer r = (Reducer)BuiltInFunctions.makeFunction(BuiltInFunctions.FN_MAX);
        eval(r,new double[] {
                1.0,
                2.0,
                3.0,
                4.0,
                5.0 });
        assertEquals(r.value(),5.0);

        eval(r,new double[] { 100.0 });
        assertEquals(r.value(),100.0);
    }

    
    @Test
    public void testSum() {
        Reducer r = (Reducer)BuiltInFunctions.makeFunction(BuiltInFunctions.FN_SUM);
        eval(r,new double[] {
                1.0,
                2.0,
                3.0,
                4.0,
                5.0 });
        assertEquals(r.value(),15.0);

        eval(r,new double[] { 100.0 });
        assertEquals(r.value(),100.0);
    }

    
    @Test
    public void testCount() {
        Reducer r = (Reducer)BuiltInFunctions.makeFunction(BuiltInFunctions.FN_COUNT);
        eval(r,new double[] {
                1.0,
                2.0,
                3.0,
                4.0,
                5.0 });
        assertEquals(r.value(),5.0);

        eval(r,new double[] { 100.0 });
        assertEquals(r.value(),1.0);
    }


    
    @Test
    public void testStDev() {
        Reducer r = (Reducer) BuiltInFunctions.makeFunction(BuiltInFunctions.FN_STDEV);
        eval(r,new double[] {
                3.0,
                7.0,
                7.0,
                19.0 });
        assertEquals((Double)(r.value()),6.92820323,0.000001);

        eval(r,new double[] {
                3.0,
                7.0,
                7.0,
                19.0 }, -1.0);
        assertEquals((Double)(r.value()),-6.92820323,0.000001);

        eval(r,new double[] {
                3.0,
                7.0,
                7.0,
                19.0 }, 2.0);
        assertEquals((Double)(r.value()),13.85640646,0.000001);

        
        eval(r,new double[] {
                13.0,
                23.0,
                12.0,
                44.0,
                55.0});
        assertEquals((Double)(r.value()),19.2431806,0.000001);
        
        eval(r,new double[] {
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0});
        assertEquals((Double)(r.value()),0.0,0.000001);

    }


    @Test
    public void testStDevP() {
        Reducer r = (Reducer) BuiltInFunctions.makeFunction(BuiltInFunctions.FN_STDEVP);
        eval(r,new double[] {
                3.0,
                7.0,
                7.0,
                19.0 });
        assertEquals((Double)(r.value()),6.0,0.00001);
        
        eval(r,new double[] {
                13.0,
                23.0,
                12.0,
                44.0,
                55.0});
        assertEquals((Double)(r.value()),17.21162,0.00001);
        
        eval(r,new double[] {
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0});
        assertEquals((Double)(r.value()),0.0,0.000001);
    }

    
    
}
