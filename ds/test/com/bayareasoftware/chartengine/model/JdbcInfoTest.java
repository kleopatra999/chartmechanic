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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
//import static org.junit.Assert.fail;
import junit.framework.JUnit4TestAdapter;
//import org.junit.Before;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.UserInfo;

public class JdbcInfoTest {
    private static final String login = "TestUser";
    private static final String firstname = "Test";
    private static final String lastname = "User";
    private static final String email = "testuser@devnull.com";
    
    private UserInfo getTestUser() {
        UserInfo ui = new UserInfo();
        ui.setLogin(login);
        ui.setFirstName(firstname);
        ui.setLastName(lastname);
        ui.setEmail(email);
        return ui;
    }
    
    // sample values
    private String driver = "org.h2.Driver";
    private String pw = "tiger";
    private String user = "scott";
    private String url = "jdbc:h2:mem:chartsample";
    
    @Test public void testDriver() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        assertEquals(ji.getProperty(DataSourceInfo.JDBC_DRIVER), null);
        ji.setProperty(DataSourceInfo.JDBC_DRIVER,driver);
        assertEquals(ji.getProperty(DataSourceInfo.JDBC_DRIVER),driver);
        ji.setProperty(DataSourceInfo.JDBC_DRIVER,null);
        assertEquals(ji.getProperty(DataSourceInfo.JDBC_DRIVER),null);
    }
    
    @Test public void testPassword() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        assertEquals(ji.getPassword(), null);
        ji.setPassword(pw);
        assertEquals(ji.getPassword(),pw);
        ji.setPassword(null);
        assertEquals(ji.getPassword(),null);
    }

    @Test public void testUser() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        assertEquals(ji.getUsername(), null);
        ji.setUsername(user);
        assertEquals(ji.getUsername(),user);
        ji.setUsername(null);
        assertEquals(ji.getUsername(),null);
    }

    @Test public void testUrl() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        assertEquals(ji.getUrl(), null);
        ji.setUrl(url);
        assertEquals(ji.getUrl(),url);
        ji.setUrl(null);
        assertEquals(ji.getUrl(),null);
    }

    /*
    @Test public void testValidate() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        assertFalse("validate should fail for a null JdbcInfo", ji.isValid());
        ji.setUrl(url);
        assertFalse("validate should fail even if url is set", ji.isValid());
        ji.setUsername(user);
        assertFalse("validate should fail even if user and url are set", ji.isValid());
        ji.setPassword(pw);
        assertFalse("validate should fail even if user,url,password are set", ji.isValid());
        ji.setDriver(driver);
        assertTrue("validate should pass now that user,url,password, and driver are set", ji.isValid());

        //QueryInfo q1 = new QueryInfo();
        //q1.setName("q1");
        //ji.addQuery(q1);
        assertTrue(ji.isValid());
        QueryInfo q2 = new QueryInfo();
        q2.setName("q2");
        ji.addQuery(q2);
        assertTrue(ji.isValid());
        QueryInfo q3 = new QueryInfo();
        q3.setName("q2");
        ji.addQuery(q3);
        assertFalse("jdbcinfo with duplicate query names should not be valid",ji.isValid());
        
    }*/
/*
    @Test public void testDataSourceAndQueryRelationship() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        ji.setUrl(url);
        ji.setUsername(user);
        ji.setPassword(pw);
        ji.setDriver(driver);
        ji.setOwner(getTestUser());
        ji.setDescription("test JDBC Info");
        QueryInfo q1 = new QueryInfo();
        q1.setName("q1");
        q1.setQuery("select foo from bar");
        
        ji.addQuery(q1);
        assertTrue(ji.getQueries().size() == 1);
        assertTrue(ji.getQueries().get(0) == q1);
        assertTrue(q1.getDatasource() == ji);
        
    }
    
    @Test public void testAddAndRemoveQuery() {
        DataSourceInfo ji = new DataSourceInfo(DataSourceInfo.JDBC_TYPE);
        String qname = "q1";
        QueryInfo q1 = new QueryInfo();
        q1.setName(qname);
        ji.addQuery(q1);
        
        QueryInfo q2;
        
        q2 = ji.getQuery("q1");
        assertTrue("newly added query could not be found", sameQueryInfo(q1,q2));
        
        q2.setName("q2");
        q2.setQuery("select * from EMP");
        ji.addQuery(q2); // should add a new QueryInfo to the list
        
        String[] qNames = ji.getQueriesNames();
        assertTrue("added two queries but didn't get two out", qNames != null && qNames.length == 2);
        
        ji.removeQuery(q1);
        assertTrue("removed query should not be found", ji.getQuery("q1") == null);
        
        List qList = ji.getQueries();
        assertTrue("after removal, only one query left", qList.size() == 1);
        assertTrue(sameQueryInfo( (QueryInfo)(qList.get(0)),q2));

//        System.out.println ("ji.toString() = " + ji);
        
    }*/
//    
//    private boolean sameQueryInfo(QueryInfo q1, QueryInfo q2) {
//        return q1 != null & q2 != null && 
//               q1.getName().equals(q2.getName()) &&
//               (q1.getQuery() != null ? q1.getQuery().equals(q2.getQuery()) : 
//                                      q2.getQuery() == null);
//    }
//    
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JdbcInfoTest.class);
    }
}