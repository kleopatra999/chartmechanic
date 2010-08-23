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
package com.bayareasoftware.chartengine.h2;

import java.sql.Timestamp;

import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionsTest {
    @Test public void testFunctions() {
        
      assertNull(Functions.dateTrunc("month",null));
      // use the deprecated Timestamp API to conveniently set my timestamp to 2001/01/02 03:04:05 
      Timestamp t = new Timestamp(2001,1,2,3,4,5,6); 

      Timestamp t2;
      t2 = Functions.dateTrunc("month",t);
      // truncated to month should result in day should result in no date, hours, minutes, and seconds
      assertTrue(t2.getYear() == 2001);
      assertTrue(t2.getMonth() == 1);
      assertTrue(t2.getDate() == 1);
      assertTrue(t2.getHours() == 0);
      assertTrue(t2.getMinutes() == 0);
      assertTrue(t2.getSeconds() == 0);
      assertTrue(t2.getNanos() == 0);

      // truncated to year should result in day should result in no months, date, hours, minutes, and seconds
      t2 = Functions.dateTrunc("year",t);
      assertTrue(t2.getYear() == 2001);
      assertTrue(t2.getMonth() == 0);
      assertTrue(t2.getDate() == 1);
      assertTrue(t2.getHours() == 0);
      assertTrue(t2.getMinutes() == 0);
      assertTrue(t2.getSeconds() == 0);
      assertTrue(t2.getNanos() == 0);

      // truncated to day should result in zero hours, minutes, and seconds
      t2 = Functions.dateTrunc("day",t);
      assertTrue(t2.getYear() == 2001);
      assertTrue(t2.getMonth() == 1);
      assertTrue(t2.getDate() == 2);
      assertTrue(t2.getHours() == 0);
      assertTrue(t2.getMinutes() == 0);
      assertTrue(t2.getSeconds() == 0);
      assertTrue(t2.getNanos() == 0);

      // given an invalid date part, just return the original timestamp
      assertEquals(Functions.dateTrunc("invalid-part-name",t),t);
    }
}
