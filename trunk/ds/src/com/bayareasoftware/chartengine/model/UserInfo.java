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

import java.util.Date;

/**
 * represents a user in the system
 *
 */
public class UserInfo extends BaseInfo {
    public static final String PROP_FIRSTNAME = "firstname";
    public static final String PROP_LASTNAME = "lastname";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_CREATED = "created";
    public static final String PROP_LOGIN = "login";
    public static final String PROP_PREFERENCES = "preferences";

    private Date created;

    private String[] roles;
    // should never be null. might be empty, but should never be null
    private UserPreferences prefs = new UserPreferences();
    
    // login name is the name field from BaseInfo
    public String getLogin() {
        return getProperty(PROP_LOGIN);
    }
    
    public void setLogin(String login) {
        setProperty(PROP_LOGIN,login);
    }
    
    public String getFirstName() {
        return getProperty(PROP_FIRSTNAME);
    }
    public void setFirstName(String firstName) {
        setProperty(PROP_FIRSTNAME,firstName);
    }
    
    public String getLastName() {
        return getProperty(PROP_LASTNAME);
    }
    public void setLastName(String lastName) {
        setProperty(PROP_LASTNAME,lastName);
    }
    
    public String getEmail() {
        return getProperty(PROP_EMAIL);
    }
    public void setEmail(String email) {
        setProperty(PROP_EMAIL,email);
    }

    public UserPreferences getPreferences() {
        return prefs;
    }
    
    public void setPreferences(UserPreferences p) {
        UserPreferences old = this.prefs;
        this.prefs = p;
        fireChange(PROP_PREFERENCES,old,this.prefs);
    }
    
    public String toString() {
        StringBuffer ret = new StringBuffer("[User: login='" + getLogin() + "' " + id + "=" + getId() + " ");
        ret.append(" roles=(");
        String[] roles = getRoles();
        for (int i = 0; roles != null && i < roles.length; i++) {
            if (i > 0) { ret.append(","); }
            ret.append(roles[i]);
        }
        ret.append(")]");
        return ret.toString();
    }

    public String getPassword_sha1_base64() {
        return getProperty(PROP_PASSWORD);
    }

    public void setPassword_sha1_base64(String password_sha1_base64) {
        setProperty(PROP_PASSWORD,password_sha1_base64);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        Date old = this.created;
        this.created = created;
        this.fireChange(PROP_CREATED, old, this.created);
    }
    
    
    /**
     * deserialize this object from a SimpleProps
     * 
     * @param p
     * @param prefix  if specified, only use the keys with this prefix 
     * @return true if successful, false otherwise
     */
    @Override
    public boolean deserializeFromProps(SimpleProps p, String prefix) {
        boolean ret = super.deserializeFromProps(p, prefix);
        try {
            String k = PROP_CREATED;
            String v = props.get(k);
            if (v != null) {
                long dateTime = Long.parseLong(v);
                this.created = new Date(dateTime);
                ret = true;
            }
            
            k = PROP_PREFERENCES;
            if (prefs == null) 
                prefs = new UserPreferences();
            
            ret = prefs.deserializeFromProps(this.props, PROP_PREFERENCES);
            
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }
    
    public String[] getRoles() { return roles; }
    public void setRoles(String[] r) { roles = r; }
    public boolean hasRole(String r) {
        boolean ret = false;
        for (String s : roles) {
            if (r.equals(s)) {
                ret = true;
                break;
            }
        }
        return ret;
    }
    
    @Override
    public SimpleProps serializeToProps(SimpleProps p, String prefix) {
        SimpleProps sp = super.serializeToProps(p, prefix);
        if (created != null) {
            // store the created time as the Long representation of the date
            sp.set(prefix,PROP_CREATED,created.getTime()+""); 
        }
        if (prefs != null) {
            prefs.serializeToProps(sp, PROP_PREFERENCES);
        }
        return sp;
    }
}
