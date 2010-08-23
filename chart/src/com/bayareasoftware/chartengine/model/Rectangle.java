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

import java.io.Serializable;

public class Rectangle implements Serializable {
    public int x,y,width,height;
    public Rectangle() { }
    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public Rectangle(double x, double y, double w, double h) {
        this((int)x,(int)y,(int)w,(int)h);
    }
    public String toString() {
        return "[Rectangle: x=" + x + ",y=" + y + ",w=" + width + ",h=" + height + "]";
    }
}