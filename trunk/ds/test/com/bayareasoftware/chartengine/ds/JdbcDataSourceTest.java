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
package com.bayareasoftware.chartengine.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import com.bayareasoftware.chartengine.ds.JdbcDataSource;
import com.bayareasoftware.chartengine.ds.QueryParser;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.ParamMetaData;
import com.bayareasoftware.chartengine.model.SimpleProps;
import com.bayareasoftware.chartengine.model.StandardProps;

public class JdbcDataSourceTest {

    // sample values
//    private String driver = "org.h2.Driver";
//    private String pw = "";
//    private String user = "";
//    private String url = "jdbc:h2:mem:JdbcDataSourceTest;DB_CLOSE_DELAY=-1";
//    
//  private JdbcDataSource newJdbcDataSource() throws Exception {
//  JdbcDataSource jds = new JdbcDataSource(driver,url,user,pw);
//  return jds;
//}
    
    static SimpleProps dbprops = new SimpleProps();
    
    @BeforeClass
    public static void setupProps() {
        dbprops.put(StandardProps.JDBC_DRIVER,"org.h2.Driver");
        dbprops.put(StandardProps.JDBC_URL,"jdbc:h2:mem:charttest;DB_CLOSE_DELAY=-1");
        dbprops.put(StandardProps.JDBC_USERNAME,"sa");
        dbprops.put(StandardProps.JDBC_PASSWORD,"");
    }

    
    private JdbcDataSource newJdbcDataSource(String tableName, String query) throws Exception {
        dbprops.put(DataSourceInfo.TABLE_NAME, tableName);
        JdbcDataSource jds = new JdbcDataSource(dbprops,query);
        return jds;
    }

    @Test 
    public void testBadJdbcDriver() {
        try {
            SimpleProps props = new SimpleProps();
            props.putAll(dbprops);
            props.put(StandardProps.JDBC_DRIVER,"BAD.DRIVER");
            JdbcDataSource jds = new JdbcDataSource(props,"select 1");
            Assert.fail("jdbcinfo with bad driver should fail to be created");
        } catch (Exception e) {
            // exception expected from creation of jdbcdatasource with bad jdbcinfo
        }
    }
    
    @Test 
    public void testSimpleQuery() {
        try {
            JdbcDataSource jds = newJdbcDataSource("","select 1");
            jds.executeQuery();
        } catch (Exception e) {
            Assert.fail("unexpected failure " + e);
        }
    }
    
    @Test
    public void testTransformQuery() {
        try {
            String query = "CREATE TABLE TestTable (" +
                        "intF INT," + 
                        "booleanF BOOLEAN," +
                        "tinyintF TINYINT," +
                        "smallintF SMALLINT," +
                        "bigintF BIGINT," +
                        "doubleF DOUBLE," +   
                        "realF REAL," +
                        "timeF TIME," +
                        "dateF DATE," +
                        "timestampF TIMESTAMP," +
                        "varcharF VARCHAR)";
            JdbcDataSource jds = newJdbcDataSource("TestTable",query);
            jds.executeQuery();
            HashMap<String,String> paramValues = new HashMap<String,String>();
            
            String p1Name = "intP";
            String p1Value = "0";
            
            paramValues.put(p1Name, p1Value);
            paramValues.put("doubleP", "1972");
            jds.setParamValues(paramValues);
            
            query = "SELECT * from TestTable where " +
                            "intF = ?${INT intP=0} AND " +
                            "smallintF = ?${INT smallintP=123} AND " +
                            "bigintF = ?${INT bigintP=0} AND " +
                            "doubleF = ?${NUM doubleP=0} AND " + 
                            "realF = ?${NUM realP=0} AND " +
                            "timestampF = ?${DATE timestampP} AND " +
                            "dateF = ?${DATE dateP} AND " +
 //                           "timeF = ?timeP AND " +
                            "varcharF = ?${STRING varcharP=hello world}";
            
            String q2;
            List<ParamMetaData> jdbcParams;
            {
                QueryParser qp = new QueryParser(query);
                q2 = qp.getResult();
                jdbcParams = qp.getParams();
            }
/*            
            System.out.println("after transform query: q2 = " + q2);
            for (JDBCParam jp : jdbcParams) {
                System.out.println("jp.getType() = " + jp.getType() + " jp.getValue() = " + jp.getValue());
            }
            */
            // named params must all be transformed out of the query
            Assert.assertTrue(q2.indexOf("?intP") == -1); 
            Assert.assertTrue(q2.indexOf("?smallintP") == -1); 
            Assert.assertTrue(q2.indexOf("?bigintP") == -1); 
            Assert.assertTrue(q2.indexOf("?doubleP") == -1); 
            Assert.assertTrue(q2.indexOf("?realP") == -1); 
            Assert.assertTrue(q2.indexOf("?timestampP") == -1);
            Assert.assertTrue(q2.indexOf("?dateP") == -1); 
//            Assert.assertTrue(q2.indexOf("?timeP") == -1);
            Assert.assertTrue(q2.indexOf("?varcharP") == -1);
            
            Assert.assertEquals(8, jdbcParams.size());

            ParamMetaData jp = jdbcParams.get(0);
            
            // check that the param value of we set for intP is returned properly from transformQuery 
            Assert.assertEquals(DataType.INTEGER, jp.getDataTypeAsInt());
            Assert.assertEquals(p1Value, jp.getDefaultValue());

            // the next param value should be the default (set below in makeParamMeta)
            jp = jdbcParams.get(1);
            Assert.assertEquals(DataType.INTEGER, jp.getDataTypeAsInt());
            Assert.assertThat(jp.getDefaultInt(), is(123));
            
            jds.setQuery(query);
            jds.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("unexpected failure " + e);
        }
    }
    
   
    @Test
    public void testTransformQueryWithQuotedQuestionMarks() {
        try {
            String query = "CREATE TABLE TestTable2 (" +
                           "intF INT," + 
                           "varcharF VARCHAR)";

            JdbcDataSource jds = newJdbcDataSource("TestTable2",query);
            jds.executeQuery();
            HashMap<String,String> paramValues = new HashMap<String,String>();
            
            String p1Name = "intP";
            String p1Value = "100";
            
            paramValues.put(p1Name, p1Value);
            
            jds.setParamValues(paramValues);
            
            query = "SELECT * from TestTable where " +
                            "intF = ?${INT intP=0} AND varcharF != '?${STRING varcharP}' OR varcharF != \"?foobar\"";
            String q2;
            List<ParamMetaData> jdbcParams;
            {
                QueryParser qp = new QueryParser(query);
                q2 = qp.getResult();
                jdbcParams = qp.getParams();
            }
            System.out.println("q2 = " + q2);
            // named params must all be transformed out of the query
            Assert.assertTrue(q2.indexOf("?intP") == -1); 
            Assert.assertTrue(q2.indexOf("?${STRING varcharP}") != -1); 
            Assert.assertTrue(q2.indexOf("?foobar") != -1); 
            
            Assert.assertTrue(jdbcParams.size() == 1);

            ParamMetaData jp = jdbcParams.get(0);
            
            Assert.assertEquals(DataType.INTEGER, jp.getDataTypeAsInt());
        
        } catch (Exception e) {
            Assert.fail("unexpected failure " + e);
        }
    }
    
    @Test
    public void testTransformQueryWithWeirdWhitespace() {
        try {
            String query = "CREATE TABLE TestTable3 (" +
                    "intF INT," + 
                    "varcharF VARCHAR)";
            JdbcDataSource jds = newJdbcDataSource("TestTable3",query);
            jds.executeQuery();
            
            HashMap<String,String> paramValues = new HashMap<String,String>();
            
            String p1Name = "intP";
            String p1Value = "100";
            
            paramValues.put(p1Name, p1Value);
            
            jds.setParamValues(paramValues);
            
            query = "SELECT * from TestTable where " +
                            "intF = ?${int intP}\nAND varcharF != '?varcharP' OR varcharF != \"?foobar\"";
            String q2;
            List<ParamMetaData> jdbcParams;
            {
                QueryParser qp = new QueryParser(query);
                q2 = qp.getResult();
                System.out.println("q2 = " + q2);
                jdbcParams = qp.getParams();
            }
            // named params must all be transformed out of the query
            Assert.assertTrue(q2.indexOf("?intP") == -1); 
            Assert.assertTrue(q2.indexOf("?varcharP") != -1); 
            Assert.assertTrue(q2.indexOf("?foobar") != -1); 
            
            Assert.assertTrue(jdbcParams.size() == 1);

            ParamMetaData jp = jdbcParams.get(0);
            
            // check that the param value of we set for intP is returned properly from transformQuery 
            Assert.assertEquals(DataType.INTEGER, jp.getDataTypeAsInt());
            Assert.assertNull(jp.getDefaultInt());
        
        } catch (Exception e) {
            Assert.fail("unexpected failure " + e);
        }
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JdbcDataSourceTest.class);
    }
}
