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

import static org.junit.Assert.*;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.UserInfo;

public class UserInfoTest {
    private static final String login = "TestUser";
    private static final String firstname = "Test";
    private static final String lastname = "User";
    private static final String email = "testuser@devnull.com";
    
    public static UserInfo getTestUser() {
        UserInfo ui = new UserInfo();
        ui.setLogin(login);
        ui.setFirstName(firstname);
        ui.setLastName(lastname);
        ui.setEmail(email);
        return ui;
    }
    
    @Test
    public void testTestUser() {
        UserInfo u = getTestUser();
        assertTrue(login.equals(u.getLogin()));
        assertTrue(firstname.equals(u.getFirstName()));
        assertTrue(lastname.equals(u.getLastName()));
        assertTrue(email.equals(u.getEmail()));
    }
}
