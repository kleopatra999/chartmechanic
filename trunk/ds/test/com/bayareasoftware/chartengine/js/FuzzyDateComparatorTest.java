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
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
import org.junit.Test;

public class FuzzyDateComparatorTest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    private Date d(String s) throws ParseException {
        return sdf.parse(s);
    }
    
    @Test
    public void testMonth() throws Exception {
        FuzzyDateComparator comp = FuzzyDateComparator.get("MONTH", 0);
        assertEquals(comp.compare(d("1999-01-01"),d("1999-02-01")),0);
        assertEquals(comp.compare(d("1999-10-15"),d("1999-11-15")),0);
        assertEquals(comp.compare(d("1999-12-30"),d("2000-01-30")),0);
        assertEquals(comp.compare(d("1999-12-30"),d("2001-05-29")),-1);

        // give it three days tolerance 
        comp = FuzzyDateComparator.get("MONTH", 3);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-28")) < 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-29")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-30")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-31")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-02-01")) == 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-02-02")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-02-03")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-02-04")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-02-05")) < 0);
        
        // dates that span the year boundary
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-26")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-27")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-28")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-29")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-30")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-31")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-02-01")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-02-02")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-02-03")) < 0);
        
        //dates that are clearly out of interval
        assertTrue(comp.compare(d("1999-2-30"),d("2005-02-04")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2005-02-04")) < 0);

    }
    
    @Test
    public void testWeek() throws Exception {
        FuzzyDateComparator comp = FuzzyDateComparator.get("WEEK", 0);
        assertEquals(comp.compare(d("1999-01-01"),d("1999-01-08")),0);
        assertEquals(comp.compare(d("1999-10-15"),d("1999-10-22")),0);
        assertEquals(comp.compare(d("1999-05-30"),d("1999-06-06")),0);
        assertEquals(comp.compare(d("1999-12-30"),d("2000-01-06")),0);
        
        assertEquals(comp.compare(d("1999-12-30"),d("2000-01-07")),-1);
        assertEquals(comp.compare(d("1999-01-01"),d("1999-01-09")),-1);

        // give it two days tolerance 
        comp = FuzzyDateComparator.get("WEEK", 2);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-05")) < 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-06")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-07")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-08")) == 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-09")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-10")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-01-11")) < 0);
        
        // dates that span the year boundary
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-03")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-04")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-05")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-06")) == 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-07")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-08")) >= 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2000-01-09")) < 0);

        //dates that are clearly out of interval
        assertTrue(comp.compare(d("1999-2-30"),d("2005-02-04")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2005-02-04")) < 0);

    }
    
    
    @Test
    public void testQuarter() throws Exception {
        FuzzyDateComparator comp;
        
        comp = FuzzyDateComparator.get("QUARTER", 0);
        assertEquals(comp.compare(d("1999-01-01"),d("1999-04-01")),0);
        assertEquals(comp.compare(d("1999-10-15"),d("2000-01-15")),0);
        assertEquals(comp.compare(d("1999-05-30"),d("1999-08-30")),0);
        
        assertEquals(comp.compare(d("1999-12-30"),d("2000-01-07")),-1);
        assertEquals(comp.compare(d("1999-01-01"),d("1999-01-09")),-1);

        // give it four days tolerance 
        comp = FuzzyDateComparator.get("QUARTER", 4);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-03-27")) < 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-03-28")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-03-29")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-03-30")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-03-31")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-01")) == 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-02")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-03")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-04")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-05")) >= 0);
        assertTrue(comp.compare(d("1999-01-01"),d("1999-04-06")) < 0);
        
        // dates that span the year boundary
        assertTrue(comp.compare(d("2000-12-31"),d("2001-03-27")) < 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-03-28")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-03-29")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-03-30")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-03-31")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-01")) == 0); // exactly 90 days apart 
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-02")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-03")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-04")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-05")) >= 0);
        assertTrue(comp.compare(d("2000-12-31"),d("2001-04-06")) < 0);
        
        //dates that are clearly out of interval
        assertTrue(comp.compare(d("1999-2-30"),d("2005-02-04")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2005-02-04")) < 0);
    }
    
    @Test
    public void testYear() throws Exception {
        FuzzyDateComparator comp;
        
        comp = FuzzyDateComparator.get("YEAR", 0);
        assertEquals(comp.compare(d("2002-07-04"),d("2003-07-04")),0);
        assertEquals(comp.compare(d("2002-10-15"),d("2003-10-15")),0);
        assertEquals(comp.compare(d("2002-12-30"),d("2003-12-30")),0);
        
     // this is not an exact match because 2000 is a leap year and we are using 365 days
        assertEquals(comp.compare(d("1999-07-04"),d("2000-07-04")),-1); 
        
        // give it four days tolerance 
        comp = FuzzyDateComparator.get("YEAR", 4);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-04-26")) < 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-04-27")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-04-28")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-04-29")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-04-30")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-01")) == 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-02")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-03")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-04")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-05")) >= 0);
        assertTrue(comp.compare(d("2002-05-01"),d("2003-05-06")) < 0);

        
        // dates that span two years
        assertTrue(comp.compare(d("2001-12-31"),d("2003-01-01")) >= 0);
        assertTrue(comp.compare(d("2001-12-31"),d("2003-01-02")) >= 0);
        assertTrue(comp.compare(d("2001-12-31"),d("2003-01-03")) >= 0);
        assertTrue(comp.compare(d("2001-12-31"),d("2003-01-04")) >= 0);
        assertTrue(comp.compare(d("2001-12-31"),d("2003-01-05")) < 0);
        
        //dates that are clearly out of interval
        assertTrue(comp.compare(d("1999-2-30"),d("2005-02-04")) < 0);
        assertTrue(comp.compare(d("1999-12-30"),d("2005-02-04")) < 0);
        

    }

}
