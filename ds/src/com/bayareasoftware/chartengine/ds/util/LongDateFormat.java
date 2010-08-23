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
package com.bayareasoftware.chartengine.ds.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

/**
 * a DateFormat class that converts between a long and a Date representation
 */
public class LongDateFormat extends DateFormat{

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return toAppendTo.append(date.getTime());
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        int index = pos.getIndex();
        Date ret = new Date(Long.parseLong(source.substring(index)));
        pos.setIndex(index+source.length()); // must advance this or else DateFormat thinks this is a parse error
        return ret;
    }

}
