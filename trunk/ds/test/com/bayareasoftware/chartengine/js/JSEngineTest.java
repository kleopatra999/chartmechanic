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
package com.bayareasoftware.chartengine.js;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.script.ScriptException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.DSFactory;
import com.bayareasoftware.chartengine.ds.DSTestUtil;
import com.bayareasoftware.chartengine.ds.DataInference;
import com.bayareasoftware.chartengine.ds.DataStream;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.util.FileUtil;

public class JSEngineTest {

    @BeforeClass
    public static void setup() {
        DSTestUtil.allowFiles();
    }
    private static final File MLBSALARIES_HTML = new File("test/data/mlb-salaries.html");
    private static final File MEDAL_HTML = new File("test/data/olympic-medals.html");
    private static final File FRUITS_CSV = new File("test/data/favorite-fruits.csv");
    private static final File MASSLAYOFFS_CSV = new File("test/data/mass-layoffs.csv");
    
    private static final File CASE_SCHILLER = new File("test/data/CSHomePrice_History.xls");

    private static final File FIXUP_SCRIPT = new File("test/scripts/fix-medals.js");
    
    private String makeScript(String s) {
        //return JSEngine.SCRIPT_PREFIX+"\n"+s;
        return s;
    }
    
    // make an empty DataSource just to hold the execution of the scripts
    private DataSourceInfo makeEmptyDS(String script) {
        DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        dsi.setDataScript(makeScript(script));
        return dsi;
    }
    
    private DataStream eval(DataSourceInfo dsi) throws Exception{
        return DSFactory.createDataSource(dsi).getDataStream();
//      return DSFactory.eval(dsi);
    }
    
    @Test
    public void testImportPackage() throws Exception {
        // importPackage is never allowed
        DataSourceInfo dsi = makeEmptyDS("importPackage(javax.swing);");
        try {
            //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
            DataStream ds = this.eval(dsi);
            DSTestUtil.drainStream(ds, true);
            fail("expected exception raised, got none");
        } catch (ScriptException sce) {
            p("EXPECTED MESSAGE: " + sce.getMessage());
            assertTrue(sce.getMessage().contains("importing packages is not allowed"));
        }
    }

//  FIXME: this doesn't pass right now with the Rhino javascript engine.  need a way to prevent people from accessing random java packages
//  from their scripts
//    @Test
//    public void testJavaIsUndefined() throws Exception {
//        // make sure that java is undefined so they can't do stuff like java.lang.Class
//        DataSourceInfo dsi = DataInference.get().inferFromURL(MEDAL_HTML.toURL().toString()).toDataSource();
//        // if java is truly undefined, we should only get 3 rows out of the data
//        dsi.setQuery(makeScript("if (typeof(java) == 'undefined') { data.limit(0,3); } else {data.setString(0,0,typeof(java));} "));
//        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
//        int nrows = 0;
//        while (ds.next()) {
//            p("row" +nrows+":"+ ds.getString(1));
//            nrows++;
//        }
//        assertEquals(nrows,3);
//    }

    
    
    @Test
    public void testGlobalNamespace() throws Exception {
    // ensure that the global namespace cannot be polluted by scripts
        DataSourceInfo dsi = makeEmptyDS("globalVar = 3;");
//      dsi = DataInference.get().inferFromURL(MEDAL_HTML.toURL().toString()).toDataSource();
//        dsi.setQuery(makeScript("globalVar = 3;"));
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        
        dsi.setDataScript(makeScript("data.limit(0,globalVar);"));
        try {
            //ds = DSFactory.createDataSource(dsi).getDataStream();
            ds = this.eval(dsi);
            DSTestUtil.drainStream(ds, true);
            fail("expected exception raised, got none");
        } catch (ScriptException sce) {
            p("EXPECTED MESSAGE: " + sce.getMessage());
            assertEquals("expect error at line 1", 1, sce.getLineNumber());
        }
    }
    
    
    @Test
    public void testBadCol() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(FRUITS_CSV.toURI().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(FRUITS_CSV);
        dsi.setDataScript(makeScript("data.setString(0,data.col('BAD COLUMN NAME'),'FOOBAR');"));
        try {
            DataStream ds = this.eval(dsi);
            fail("Should have raised a ScriptException for non-existent column name");
        } catch (ScriptException se) {
            p("EXPECTED MESSAGE: " + se.getMessage());
        }

    }

    @Test
    public void testCopyCol() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(FRUITS_CSV);
        dsi.setDataScript(makeScript("data.addCol('name2','TEXT')\n"+
                                     "data.copyCol('name','name2')"));
        DataStream ds = this.eval(dsi);
        while (ds.next()) {
            String name = ds.getString(1);
            String name2 = ds.getString(4);
            assertEquals(name,name2);
        }
    }

    @Test
    public void testConcatCol() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(MASSLAYOFFS_CSV);
        dsi.setDataScript(makeScript("data.addCol('col3','TEXT');\n"+
                   "data.concatCol('Year','Period',' ','col3');\n"+
                   "data.parseAsDate('col3');"));
        DataStream ds = this.eval(dsi);
        while (ds.next()) {
            Date d = ds.getDate(5);
            if (d != null) {
                p("d = " + d);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                assertTrue(cal.get(Calendar.YEAR) == ds.getInt(2));
                String month = ds.getString(3);
                if (month.equals("Jan")) {
                    assertTrue(cal.get(Calendar.MONTH) == Calendar.JANUARY);
                } else if (month.equals("Feb")) {
                    assertTrue(cal.get(Calendar.MONTH) == Calendar.FEBRUARY);
                }
            }
        }
        
    }
    
    @Test
    public void testConcatAsDate() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(MASSLAYOFFS_CSV);
        dsi.setDataScript(makeScript("data.concatAsDate('Year','Period','newColumn');"));
        DataStream ds = this.eval(dsi);
        while (ds.next()) {
            Date d = ds.getDate(5);
            if (d != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                assertTrue(cal.get(Calendar.YEAR) == ds.getInt(2));
                String month = ds.getString(3);
                if (month.equals("Jan")) {
                    assertTrue(cal.get(Calendar.MONTH) == Calendar.JANUARY);
                } else if (month.equals("Feb")) {
                    assertTrue(cal.get(Calendar.MONTH) == Calendar.FEBRUARY);
                }
            }
        }
    }
    
    @Test
    public void testGroupbyDateTrunc() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(MASSLAYOFFS_CSV);
        dsi.setDataScript(makeScript("data.concatAsDate('Year','Period','newDate');\n"+
                                     "data.groupbyDateTrunc('newDate','Month','PeriodDate','Value',fn.sum())"));
        DataStream ds = this.eval(dsi);
        int i = 0;
        while (ds.next()) {
            Date d = ds.getDate(6);
            Integer v = ds.getInt(4);
            p("d = " + d + " v = " + v);
            if (d != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                assertTrue(cal.get(Calendar.DAY_OF_MONTH) == 1); // since we datetrunc'd by Month, all days of month must be 1
                // check against some values we know to be accurate 
                if (i==0)
                    assertTrue(v == 1316);
                else if (i == 1)
                    assertTrue(v == 1234);
                else if (i == 2)
                    assertTrue(v == 1181);
            }
            i++;
        }
    }

    
    
    @Test
    public void testAddCol() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(FRUITS_CSV.toURI().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(FRUITS_CSV);
        dsi.setDataScript(makeScript("data.addCol('party','TEXT')\n" +
                                "data.setString(0,data.col('party'),'BERRY')\n" +
                                "data.setString(1,data.col('party'),'GOURD')"));
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int i = 0;
        while (ds.next()) {
            String party = ds.getString(4);
            if (i == 0)
                assertEquals(party,"BERRY");
            if (i == 1) 
                assertEquals(party,"GOURD");
            
            //System.out.println("row = " + i + " party: " + party);
            i++;
        }
    }

    @Test
    public void testTimeDelta() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(CASE_SCHILLER);
        p("case shiller metadata: " + dsi.getInputMetadata());
        dsi.setDataScript(makeScript("data.addCol('DELTA','NUMBER')\n" +
                                "data.timeDelta('Date','YEAR',1,'DELTA',fn.diff(data.col('CSXR')))"));
        DataStream ds = this.eval(dsi);
        int i = 0;
        int newCol = ds.getMetadata().getColumnCount();
        while (ds.next()) {
            Double delta = ds.getDouble(newCol);
            System.out.println("row = " + i + " delta: " + delta);
            i++;
        }
    }

    
    
    @Test
    public void testGroupBy() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(FRUITS_CSV.toURI().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(FRUITS_CSV);
        dsi.setDataScript(makeScript("data = fn.groupby(data,data.col('name'),fn.aggregate('votes',fn.sum()));"));
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        int appleVotes = 0;
        int bananaVotes = 0;
        int cherryVotes = 0;
        int kiwiVotes = 0;
        while (ds.next()) {
            String name = ds.getString(1);
            int votes = ds.getInt(2);
            if (name.equals("apple"))
                appleVotes = votes;
            else if (name.equals("banana"))
                bananaVotes = votes;
            else if (name.equals("cherry"))
                cherryVotes = votes;
            else if (name.equals("kiwi"))
                kiwiVotes = votes;
        }
        assertTrue( (appleVotes == 18) && (bananaVotes == 20) && (cherryVotes == 26) && (kiwiVotes == 11));
        
        // GROUP by state, sum()
        // using shorter hand notation
        dsi.setDataScript(makeScript("data.groupby('state',fn.aggregate('votes',fn.sum()));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);

        int azVotes = 0;
        int caVotes = 0;
        int maVotes = 0;
        int nyVotes = 0;
        while (ds.next()) {
            String state = ds.getString(3);
            int votes = ds.getInt(2);
            if (state.equals("AZ"))
                azVotes = votes;
            else if (state.equals("CA"))
                caVotes = votes;
            else if (state.equals("MA"))
                maVotes = votes;
            else if (state.equals("NY"))
                nyVotes = votes;
        }
        assertTrue( (azVotes == 14) && (caVotes == 29) && (maVotes == 11) && (nyVotes == 21));
        
        
    }
    
    @Test
    public void testScaleFunction() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(MEDAL_HTML.toURL().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(MEDAL_HTML);
        dsi.setDataScript(makeScript("data.updateAll(data.col(\"Gold\"),fn.scale(10));"));
//        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        while (ds.next()) {
            nrows++;
            int gold = ds.getInt(2);
            if (nrows == 1) {
                // ensure that the first row, gold column is 19 * 10 = 190
                assertEquals(190, gold);
            } else if (nrows == 2) {
                // ensure that the 2nd row, gold column is 35 * 10 = 350
                assertEquals(350, gold);
            }
        }
    }

    
    @Test
    public void testScaleFunction2() throws Exception {
        DataSourceInfo dsi;
        dsi = DSTestUtil.getDSInfo(MEDAL_HTML);
        dsi.setDataScript(makeScript("data.scale(2,10);"));
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        while (ds.next()) {
            nrows++;
            int gold = ds.getInt(2);
            if (nrows == 1) {
                // ensure that the first row, gold column is 19 * 10 = 190
                assertEquals(190, gold);
            } else if (nrows == 2) {
                // ensure that the 2nd row, gold column is 35 * 10 = 350
                assertEquals(350, gold);
            }
        }
    }

    @Test
    public void testPredicates() throws Exception {
        DataSourceInfo dsi;
        //dsi = DSTestUtil.getDSInfo(MLBSALARIES_HTML);
        dsi = getMLBDS();
        
        // filter the teams so that only the chicago cubs is returned
        dsi.setDataScript(makeScript("data = fn.filter(data, data.col('Team'),fn.eq('Chicago Cubs'));"));
//        DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        while (ds.next()) {
            String team = ds.getString(2);
            assertTrue("Chicago Cubs".equals(team));
        }

        // filter the teams so that only the chicago cubs is returned,  same as above case, but using 
        // the shorthand by calling data directly
        dsi.setDataScript(makeScript("data.filter(data.col('Team'),fn.eq('Chicago Cubs'));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);
        while (ds.next()) {
            String team = ds.getString(2);
            assertTrue("Chicago Cubs".equals(team));
        }

        // filter the teams by payroll and ensure that predicates with doubles work ok
        dsi.setDataScript(makeScript("data.filter(data.col('Payroll'),fn.eq(89428213.0));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);
        while (ds.next()) {
            String team = ds.getString(2);
            assertTrue("Philadelphia Phillies".equals(team));
        }

        
        // filter the teams so that every team that's not the cubs is returned
        dsi.setDataScript(makeScript("data.filter(data.col('Team'),fn.not(fn.eq('Chicago Cubs')));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);
        while (ds.next()) {
            String team = ds.getString(2);
            assertFalse("Chicago Cubs".equals(team));
        }

        // filter the teams so that only the cubs or the braves are returned
        dsi.setDataScript(makeScript("data.filter(data.col('Team'),fn.or(fn.eq('Chicago Cubs'),fn.eq('Atlanta Braves')));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);
        while (ds.next()) {
            String team = ds.getString(2);
            assertTrue("Chicago Cubs".equals(team) || "Atlanta Braves".equals(team));
        }

        // filter the team using a query that returns false
        dsi.setDataScript(makeScript("data.filter(data.col('Team'),fn.and(fn.eq('Chicago Cubs'),fn.eq('Atlanta Braves')));"));
        //ds = DSFactory.createDataSource(dsi).getDataStream();
        ds = this.eval(dsi);
        int nrows = 0;
        while (ds.next()) {
            nrows++;
        }
        assertTrue(nrows == 0);

    }
    
    @Test
    public void testAddNewRowAndAverage() throws Exception {
        DataSourceInfo dsi;
        //dsi = DSTestUtil.getDSInfo(MLBSALARIES_HTML);
        dsi = getMLBDS();
        
        // add a new row with the team name 'Average' and a value that equals the average of the payrolls
        dsi.setDataScript(makeScript("avg = fn.avg(data.col('Payroll'));\n"+
                                "r = data.addNewRow();\n" +
                                "data.setString(r,2,'Average');\n" +
                                "data.setNum(r,3,avg);")
                                );
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double sum = 0.0;
        double avg = 0.0;
        while (ds.next()) {
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (team.equals("Average")) {
                avg = salary;
            } else {
                sum += salary;
                nrows++;
            }
        }
        assertTrue( avg == sum / nrows);
    }
    
    @Test
    public void testAddNewAverageColumn2() throws Exception {
        /* like testAddNewAverageColumn but using the updateAll that takes a string column name */ 
        DataSourceInfo dsi;
        //dsi = DSTestUtil.getDSInfo(MLBSALARIES_HTML);
        dsi = getMLBDS();
        
        // add a new column named 'Average' and fill it with a value that equals the average of the payrolls
        dsi.setDataScript(makeScript("avg = fn.avg(data.col('Payroll'));\n"+
                                     "col = data.addCol('Average','NUMBER');\n" +
                                     "data.updateAll('Average',avg);")
                                );
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double sum = 0.0;
        double avgCol = 0.0;
        while (ds.next()) {
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (avgCol != 0.0) {
                assertTrue(avgCol == ds.getDouble(4)); // every row must have the same average value in the column 4
            }
            avgCol = ds.getDouble(4);
            sum += salary;
            nrows++;
        }
        assertTrue( avgCol == sum / nrows);
    }

    
    @Test
    public void testAddNewAverageColumn() throws Exception {
        DataSourceInfo dsi;
        //dsi = DSTestUtil.getDSInfo(MLBSALARIES_HTML);
        dsi = getMLBDS();
        
        // add a new column named 'Average' and fill it with a value that equals the average of the payrolls
        dsi.setDataScript(makeScript("avg = fn.avg(data.col('Payroll'));\n"+
                                     "col = data.addCol('Average','NUM');\n" +
                                     "data.updateAll(col,avg);")
                                );
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double sum = 0.0;
        double avgCol = 0.0;
        while (ds.next()) {
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (avgCol != 0.0) {
                assertTrue(avgCol == ds.getDouble(4)); // every row must have the same average value in the column 4
            }
            avgCol = ds.getDouble(4);
            sum += salary;
            nrows++;
        }
        assertTrue( avgCol == sum / nrows);
    }

    
    
    @Test
    public void testAddNewRowAndSum() throws Exception {
        DataSourceInfo dsi;
        dsi = getMLBDS();
        
        // add a new row with the team name 'Sum' and a total that equals sum of the other payrolls
        dsi.setDataScript(makeScript("s = fn.sum(data.col('Payroll'));" +
        		"                r = data.addNewRow();\n" +
                                "data.setString(r,2,'Sum');\n" +
                                "data.setNum(r,3,s);")
                                );
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double sum = 0.0;
        double sum2 = 0.0;
        while (ds.next()) {
            nrows ++;
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (team.equals("Sum")) {
                sum2 = salary;
            } else {
                sum += salary;
            }
        }
        assertTrue( sum2 == sum);
    }

    @Test
    public void testAddNewRowAndMax() throws Exception {
        DataSourceInfo dsi;
        dsi = getMLBDS();
        
        // add a new row with the team name 'Max' and a payroll that's the max of the payrolls
        dsi.setDataScript(makeScript("m = fn.max(data.col('Payroll'));" +
                "r = data.addNewRow();\n" +
                "data.setString(r,2,'Max');\n" +
                "data.setNum(r,3,m);"
                ));
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double max = 0.0;
        double max2 = 0.0;
        while (ds.next()) {
            nrows ++;
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (nrows == 1) {
                max = salary;
            } 
            if (team.equals("Max")) {
                max2 = salary;
            } else {
                if (salary > max) 
                    max = salary;
            }
        }
        assertTrue( max2 == max);
    }

    /**
     * return the MLB Salaries DataSource, used in several tests
     * @return
     */
    private DataSourceInfo getMLBDS() throws Exception {
        DataSourceInfo dsi;
        dsi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        dsi.setUrl(MLBSALARIES_HTML.toURI().toString());
        dsi.setProperty(DataSourceInfo.HTML_TABLEID,"16");
        
        dsi = DataInference.get().inferFromDS(dsi,-1).getDataSource();
        return dsi;
    }
    @Test
    public void testAddNewRowAndMin() throws Exception {
        DataSourceInfo dsi;
        //dsi = DSTestUtil.getDSInfo(MLBSALARIES_HTML);
        dsi = getMLBDS();
        
        // add a new row the team name 'Min' and a total that equals the min of the payroll
        dsi.setDataScript(makeScript("m = fn.min(data.col('Payroll'));" +
                                "r = data.addNewRow();\n" +
                                "data.setString(r,2,'Min');\n" +
                                "data.setNum(r,3,m);"
                                ));
        //DataStream ds = DSFactory.createDataSource(dsi).getDataStream();
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        double min = 0.0;
        double min2 = 0.0;
        while (ds.next()) {
            nrows ++;
            String team = ds.getString(2);
            double salary = ds.getDouble(3);
            if (nrows == 1) {
                min = salary;
            } 
            if (team.equals("Min")) {
                min2 = salary;
            } else {
                if (salary < min) 
                    min = salary;
            }
        }
        assertTrue( min2== min);
    }

    
    
    
    
    @Test
    public void testFixupMedals() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(MEDAL_HTML.toURL().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(MEDAL_HTML);
        String jsScript = FileUtil.readAsString(FIXUP_SCRIPT);
        dsi.setDataScript(jsScript);
        DataStream ds = this.eval(dsi);
        int nrows = 0;
        String label;
        int gold=0,silver=0,bronze=0,total=0;
        while (ds.next()) {
            nrows++;
            label = ds.getString(1);
            gold = ds.getInt(2);
            silver = ds.getInt(3);
            bronze = ds.getInt(4);
            total = ds.getInt(5);
            if (nrows == 1) {
                // test a random row
                assertEquals("United States", label);
                assertEquals(19, gold);
                assertEquals(21,silver);
                assertEquals(25,bronze);
                assertEquals(65,total);
            }
        }
        assertEquals("expect 16 rows from scripted medal count", 16, nrows);
        assertEquals(39,gold);
        assertEquals(56,silver);
        assertEquals(62,bronze);
        assertEquals(gold+silver+bronze,total);
    }
    
    @Test
    public void testErrorReporting() throws Exception {
        DataSourceInfo dsi;
        //dsi = DataInference.get().inferFromURL(MEDAL_HTML.toURL().toString()).toDataSource();
        dsi = DSTestUtil.getDSInfo(MEDAL_HTML);
        String script = "//JS\ndata.getRowCount();\n\ndata.getColumnCount();\ndata.sort(0,'foo');\n";
        dsi.setDataScript(script);
        DataStream ds = null;
        try {
            //ds = DSFactory.createDataSource(dsi).getDataStream();
            ds = this.eval(dsi);
            DSTestUtil.drainStream(ds, true);
            fail("expected exception raised, got none");
        } catch (ScriptException sce) {
            p("EXPECTED MESSAGE: " + sce.getMessage());
            assertEquals("expect error at line 5", 5, sce.getLineNumber());
            String msg = sce.getMessage();
            assertTrue("expect 'sort' method name in error message", msg.indexOf("sort") != -1);
        } catch (Exception e) {
            fail("expected ScriptException raised, not " + e);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }
    private static void p(String s) { System.err.println("[JSEngineTest] " + s); }
    
//    private static String loadScript(String file) throws IOException {
//        File f = new File(file);
//        return loadScript(f.toURL());
//    }
//    private static String loadScript(URL u) throws IOException {
//        StringBuilder ret = new StringBuilder();
//        InputStream is = u.openStream();
//        try {
//            Reader rdr = new InputStreamReader(is);
//            int r;
//            char[] buf = new char[128];
//            while ((r = rdr.read(buf)) > 0) {
//                ret.append(buf, 0, r);
//            }
//        } finally {
//            is.close();
//        }
//        return ret.toString();
//    }
}
