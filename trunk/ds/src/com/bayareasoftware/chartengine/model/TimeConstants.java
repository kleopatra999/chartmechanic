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
 * Constants for valid intervals of a timeseries
 */
public interface TimeConstants {
    /* time intervals */
    public static final int TIME_UNKNOWN = -1;
    public static final int TIME_MILLI = 0;
    public static final int TIME_SECOND = 1;
    public static final int TIME_MINUTE = 2;
    public static final int TIME_HOUR = 3;
    public static final int TIME_DAY = 4;
    public static final int TIME_WEEK = 5;
    public static final int TIME_MONTH = 6;
    public static final int TIME_QUARTER = 7;
    public static final int TIME_YEAR = 8;
}
