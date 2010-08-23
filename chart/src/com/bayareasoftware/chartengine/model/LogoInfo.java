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

/**
 * LogoInfo encapsulates information about the chart logo
 * the chart logo can either be
 *   - not visible
 *   - URL to an image
 *   - text string
 *   
 * There is one LogoInfo per User in the system.  If the user does not have one set, then
 * the system-wide default logo is used
 */
@SuppressWarnings("serial")
public class LogoInfo implements Serializable {

 
    private boolean visible;
    
   /**
    * if URL starts with /com/bayareasoftware/chartengine, then it is a class resource, else it is a vfs URL
    * if no 'vfs:" present, then interpret it as a reference to a file in vfs:admin/images
    */
    private String url;
    
    private String txt;
    
    public LogoInfo() {
        
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
    
    public boolean equals(Object o) {
        if (o == null) { 
            return false;
        }
        if (!(o instanceof LogoInfo)) {
            return false;
        }
        LogoInfo other = (LogoInfo) o;
        
        if (txt == null) {
            if (other.txt != null)
                return false;
        } else {
            if (!txt.equals(other.txt))
                return false;
        }
        
        if (url == null) {
            if (other.url != null)
                return false;
        }  else {
            if (!url.equals(other.url))
                return false;
        }

        return (visible == other.visible);
    }
    
    public int hashCode() {
        int ret = -1;

        if (this.txt != null) {
            ret ^= txt.hashCode();
        }
        if (this.url != null) {
            ret ^= url.hashCode();
        }
        
        if (visible)
            ret += 1;

        return ret;
    }

    public String toString() {
        return "url: " + url + " txt = " + txt + " visible = " + visible;
    }
}
