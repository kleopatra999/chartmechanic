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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MarkerInfoTest {
    
    @Test
    public void testSerialize() {
        MarkerDescriptor mi = new MarkerDescriptor();
        String mname = "mark me";
        mi.setName(mname);
        mi.setType(MarkerDescriptor.MARKER_TYPE_NUMERIC);
        //mi.getValue().setNumericValue(123.456);
        //mi.setValuesAsString(values)
        double[] vals = new double[] { 123.456 } ;
        mi.setValues(vals);

        SimpleProps p = mi.serializeToProps(null, null);
        System.out.println("p = \n" + p);
        
        MarkerDescriptor mi2 = new MarkerDescriptor();
        
        mi2.deserializeFromProps(p, null);
        
        //System.out.println("mi2 = " + mi2 + " mi2.props = " + mi2.toString());
        assertTrue(mi.getType() == mi2.getType());
        assertEquals(mname, mi2.getName());
        assertEquals(vals.length, mi2.getValues().length);
        assertEquals(vals[0], mi2.getValues()[0], .01);
        /*
        //assertTrue(mi.getValue().getNumericValue() == mi2.getValue().getNumericValue());
        
        double[] values = new double[3];
        values[0]=1.0;
        values[1]=-6.123;
        values[2]=2.0;
        mi.getValue().setNumericValues(values);
        
        String prefix = "markerinfo.0.";
        p = mi.serializeToProps(null, prefix);
        System.out.println("p = \n" + p);

        MarkerInfo mi3 = new MarkerInfo();
        mi3.deserializeFromProps(p,prefix);
        
        System.out.println("mi3 = " + mi2 + " mi3.props = " + mi3.getProps());
        assertTrue(mi.getType() == mi3.getType());
        double[] mi3_values = mi3.getValue().getNumericValues();
        assertTrue(mi3_values.length == 3);
        assertTrue(mi3_values[0] == values[0]);
        assertTrue(mi3_values[1] == values[1]);
        assertTrue(mi3_values[2] == values[2]);
        */
    }

}
