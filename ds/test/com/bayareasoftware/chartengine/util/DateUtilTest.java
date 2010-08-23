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
package com.bayareasoftware.chartengine.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.bayareasoftware.chartengine.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtilTest {
    private static final SimpleDateFormat SDF =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static final String DS1 = "2006-12-15T12:23:23.892-0700";
    private static final String DS2 = "2006-12-15T12:23:23.892234-0700";

    @Test
    public void testRemoveNanos() throws ParseException {
        assertEquals(SDF.parse(DS1), SDF.parse(DateUtil.removeNanos(DS2)));
    }

    @Test
    public void testParseDate() throws ParseException {
        assertEquals(SDF.parse(DS1), DateUtil.parseDate(DS2));
        assertEquals(DateUtil.parseDate(DS2, SDF), DateUtil.parseDate(DS2));
    }
}
