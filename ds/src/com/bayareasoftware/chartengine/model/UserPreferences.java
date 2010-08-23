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

import com.bayareasoftware.chartengine.model.event.PropChangeSource;

public class UserPreferences extends PropChangeSource implements PropertySerializable {

    
    public static final String PROP_AUTORENDER="autoRender";
    public static final String PROP_EXPERTMODE="expertMode";
    public static final String PROP_AUTOSAVE="autoSave";
    public static final String PROP_DEFAULTUSER="defaultUser";
    public static final String PROP_DEFAULTPASSWORD="defaultPassword";
    public static final String PROP_MATCHHOST="matchHost";
    
    public static final String PROP_MARKERDEFAULTS="markerDefaults";
    public static final String PROP_NUMRECENTCHARTS="numRecentCharts";
    
    public static final String PROP_BUILDER_CHARTWIDTH="builderChartWidth";
    public static final String PROP_BUILDER_CHARTHEIGHT="builderChartHeight";
    public static final String PROP_BUILDER_LIMITCHARTSIZE="limitBuilderChartSize";
    
    public static final String PROP_LOGO_SETTING="logoSetting";
    public static final String PROP_LOGO="logo";
    
    /**
     * WARN: changing default values may cause backwards incompatibilities
     */
    public static final boolean DEFAULT_AUTORENDER = true;
    public static final boolean DEFAULT_EXPERTMODE = false;
    public static final boolean DEFAULT_AUTOSAVE = false;
    public static final int DEFAULT_NUMRECENTCHARTS = 10;
    public static final int DEFAULT_BUILDER_CHARTWIDTH=600;
    public static final int DEFAULT_BUILDER_CHARTHEIGHT=400;
    public static final boolean DEFAULT_LIMITCHARTSIZE = true;
    // whether to auto render charts on any UI change
    private boolean autoRender = DEFAULT_AUTORENDER;
    
    // whether to show the ChartBuilder UI in expertMode or not
    private boolean expertMode = DEFAULT_EXPERTMODE;

    // whether to automatically save the chart on every edit
    private boolean autoSave = DEFAULT_AUTOSAVE;

    /**
     * the default user name and password are conveniences
     * to avoid having to type in the same username/pw for a common source hostname URL
     */
    private String defaultUserName = null;
    private String defaultPassword = null;
    private String matchHost = null;

    /**
     * how many most recent charts to keep in the chartlist in the Builder UI
     */
    private int numRecentCharts = DEFAULT_NUMRECENTCHARTS;

    /**
     * the preferred size for showing the chart in the Builder
     */
    private int builderChartWidth = DEFAULT_BUILDER_CHARTWIDTH;
    private int builderChartHeight = DEFAULT_BUILDER_CHARTHEIGHT;

    private boolean limitChartSize = DEFAULT_LIMITCHARTSIZE;

    /**
     * enum for the kinds of logo that can be set, used in UserPreferences and in the UI
     */
    public static final int LOGO_DEFAULT = 0;    // use the system default logo
    public static final int LOGO_NONE = 1;       // no logo. 
    public static final int LOGO_TEXT = 2;       // use a text string for the logo
    public static final int LOGO_IMAGE = 3;      // use an image for the logo
    
    private String logo = null; // interpreted as either a text string or a URL depending on logoSetting
    private int logoSetting = LOGO_DEFAULT;
    
    /**
     * default marker properties
     */
    private SimpleProps markerDefaults = new SimpleProps();
    
    public UserPreferences() {
        
    }

    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = false;
        try {
            String key;
            String s;
            
            key = SimpleProps.prefixIt(prefix,PROP_AUTORENDER);
            s = p.get(key);
            if (s != null) {
                this.autoRender = Boolean.parseBoolean(s);
                ret = true;
            }
            
            key = SimpleProps.prefixIt(prefix,PROP_EXPERTMODE);
            s = p.get(key);
            if (s != null) {
                this.expertMode = Boolean.parseBoolean(s);
                ret = true;
            }
            
            key = SimpleProps.prefixIt(prefix,PROP_AUTOSAVE);
            s = p.get(key);
            if (s != null) {
                this.autoSave = Boolean.parseBoolean(s);
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_DEFAULTUSER);
            s = p.get(key);
            if (s != null) {
                this.defaultUserName = s;
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_DEFAULTPASSWORD);
            s = p.get(key);
            if (s != null) {
                this.defaultPassword = s;
                ret = true;
            }
            

            key = SimpleProps.prefixIt(prefix,PROP_MATCHHOST);
            s = p.get(key);
            if (s != null) {
                this.matchHost = s;
                ret = true;
            }
            
            key = SimpleProps.prefixIt(prefix,PROP_NUMRECENTCHARTS);
            s = p.get(key);
            if (s != null) {
                this.numRecentCharts = Integer.parseInt(s);
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_BUILDER_LIMITCHARTSIZE);
            s = p.get(key);
            if (s != null) {
                this.limitChartSize = Boolean.parseBoolean(s);
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_BUILDER_CHARTHEIGHT);
            s = p.get(key);
            if (s != null) {
                this.builderChartHeight = Integer.parseInt(s);
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_BUILDER_CHARTWIDTH);
            s = p.get(key);
            if (s != null) {
                this.builderChartWidth = Integer.parseInt(s);
                ret = true;
            }
            
            key = SimpleProps.prefixIt(prefix,PROP_LOGO);
            s = p.get(key);
            if (s != null) {
                this.logo = s;
                ret = true;
            }

            key = SimpleProps.prefixIt(prefix,PROP_LOGO_SETTING);
            s = p.get(key);
            if (s != null) {
                this.logoSetting = Integer.parseInt(s);
                ret = true;
            }
            
            SimpleProps mProps = p.subset(SimpleProps.prefixIt(prefix,PROP_MARKERDEFAULTS));
            if (mProps != null && mProps.size() > 0) {
                this.markerDefaults = mProps;
                ret = true;
            } else {
                this.markerDefaults = new SimpleProps();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        if (p == null)
            p = new SimpleProps();
        
        p.set(prefix,PROP_AUTORENDER,String.valueOf(autoRender));
        p.set(prefix,PROP_EXPERTMODE,String.valueOf(expertMode));
        p.set(prefix,PROP_AUTOSAVE,String.valueOf(autoSave));
        if (defaultUserName != null)
            p.set(prefix,PROP_DEFAULTUSER,defaultUserName);
        if (defaultPassword != null)
            p.set(prefix,PROP_DEFAULTPASSWORD,defaultPassword);
        if (matchHost != null)
            p.set(prefix,PROP_MATCHHOST,matchHost);
        p.set(prefix,PROP_NUMRECENTCHARTS,String.valueOf(numRecentCharts));
        
        p.set(prefix,PROP_BUILDER_LIMITCHARTSIZE,String.valueOf(limitChartSize));
        p.set(prefix,PROP_BUILDER_CHARTWIDTH,String.valueOf(builderChartWidth));
        p.set(prefix,PROP_BUILDER_CHARTHEIGHT,String.valueOf(builderChartHeight));

        if (logo != null)
            p.set(prefix,PROP_LOGO,logo);
        p.set(prefix,PROP_LOGO_SETTING,String.valueOf(logoSetting));
        
        if (markerDefaults != null && markerDefaults.size() > 0) {
            String markerPrefix = SimpleProps.prefixIt(prefix, PROP_MARKERDEFAULTS);
            p.mergeWithPrefix(markerPrefix, markerDefaults);
        }
        return p;
    }

    /**
     * how many most recent charts to keep in history?
     * @param n
     */
    public void setNumRecentCharts(int n) {
        int old = this.numRecentCharts;
        this.numRecentCharts = n;
        fireChange(PROP_NUMRECENTCHARTS,new Integer(old),new Integer(this.numRecentCharts));
    }
    
    /**
     * 
     * @return
     */
    public int getNumRecentCharts() {
       return this.numRecentCharts; 
    }
    
    public void setMatchHost(String s) {
        String old = this.matchHost;
        this.matchHost = s;
        fireChange(PROP_MATCHHOST,old,s);
    }
    
    public String getMatchHost() {
        return this.matchHost;
    }
    
    public void setDefaultUserName(String s) {
        String old = this.defaultUserName;
        this.defaultUserName = s;
        fireChange(PROP_DEFAULTUSER,old,s);
    }
    
    public String getDefaultUserName() {
        return this.defaultUserName;
    }

    public void setDefaultPassword(String s) {
        String old = this.defaultPassword;
        this.defaultPassword = s;
        fireChange(PROP_DEFAULTPASSWORD,old,s);
    }
    
    public String getDefaultPassword() {
        return this.defaultPassword;
    }
    
    public boolean isAutoRender() {
        return autoRender;
    }

    public void setAutoRender(boolean b) {
        Boolean old = new Boolean(this.autoRender);
        this.autoRender = b;
        fireChange(PROP_AUTORENDER,old,new Boolean(this.autoRender));
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean b) {
        Boolean old = new Boolean(this.expertMode);
        this.expertMode = b;
        fireChange(PROP_EXPERTMODE,old,new Boolean(this.expertMode));   
    }
    
    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean b) {
        Boolean old = new Boolean(this.autoSave);
        this.autoSave = b;
        fireChange(PROP_AUTOSAVE,old,new Boolean(this.autoSave));
        
    }

    public SimpleProps getMarkerDefaults() {
        return markerDefaults;
    }
    
    public void setLogoSetting(int setting) {
        int old = this.logoSetting;
        this.logoSetting = setting;
        fireChange(PROP_LOGO_SETTING,old,this.logoSetting);
    }
    
    public int getLogoSetting() {
        return this.logoSetting;
    }

    public void setLogo(String s) {
        String old = this.logo;
        this.logo = s;
        fireChange(PROP_LOGO,old,this.logo);
    }
    
    /**
     * this is either a text setting or a URL, depending on the setting of logoSetting
     * @return
     */
    public String getLogo() {
        return this.logo;
    }
    public void setMarkerDefaults(SimpleProps markerDefaults) {
        SimpleProps old = this.markerDefaults;
        this.markerDefaults = markerDefaults;
        fireChange(PROP_MARKERDEFAULTS,old,this.markerDefaults);
    }

    public int getBuilderChartHeight() {
        return builderChartHeight;
    }

    public void setBuilderChartHeight(int builderChartHeight) {
        int old = this.builderChartHeight;
        this.builderChartHeight = builderChartHeight;
        fireChange(PROP_BUILDER_CHARTHEIGHT,new Integer(old), new Integer(this.builderChartHeight));
    }

    public int getBuilderChartWidth() {
        return builderChartWidth;
    }

    public void setBuilderChartWidth(int builderChartWidth) {
        int old = this.builderChartWidth;
        this.builderChartWidth = builderChartWidth;
        fireChange(PROP_BUILDER_CHARTWIDTH,new Integer(old), new Integer(this.builderChartWidth));
    }

    /**
     * true if either the chart height or width is limited
     * if neither are limited, then no false
     * @return
     */
    public boolean isLimitChartSize() {
        return this.limitChartSize;
    }
    
    public void setLimitChartSize(boolean b) {
        boolean old = this.limitChartSize;
        this.limitChartSize = b;
        fireChange(PROP_BUILDER_LIMITCHARTSIZE,new Boolean(old), new Boolean(limitChartSize));
    }
    
    public int hashCode() {
        int ret = -1;
        ret += ( autoRender ? 1 : 0);
        ret += ( autoSave ? 1 : 0);
        ret += ( expertMode ? 1 : 0);
        if (this.defaultPassword != null)
            ret ^= defaultPassword.hashCode();
        if (this.defaultUserName != null)
            ret ^= defaultUserName.hashCode();
        if (this.matchHost != null)
            ret ^= matchHost.hashCode();
        if (this.markerDefaults != null)
            ret ^= markerDefaults.hashCode();
        ret ^= numRecentCharts;
        ret ^= builderChartWidth;
        ret ^= builderChartHeight;
        ret ^= ( limitChartSize ? 1 : 0);

        return ret;
    }
    
    public boolean equals(Object o) {
        if (o == null) { 
            return false;
        }
        if (!(o instanceof UserPreferences)) {
            return false;
        }
        UserPreferences other = (UserPreferences) o;
        
        if (objEquals(defaultPassword,other.defaultPassword) &&
            objEquals(defaultUserName, other.defaultUserName) &&
            objEquals(matchHost,other.matchHost) &&
            autoRender == other.autoRender &&
            autoSave == other.autoSave &&
            expertMode == other.expertMode &&
            numRecentCharts == other.numRecentCharts &&
            builderChartHeight == other.builderChartHeight &&
            builderChartWidth == other.builderChartWidth &&
            limitChartSize == other.limitChartSize &&
            objEquals(markerDefaults,other.markerDefaults)) {
            return true;
        }
        
        return false;
    }
    
    // check if both objs are equal, taking into account nulls
    private boolean objEquals(Object o1, Object o2) {
        if (o1 == null) {
            return (o2 == null);
        } 
        return o1.equals(o2);
    }

}
