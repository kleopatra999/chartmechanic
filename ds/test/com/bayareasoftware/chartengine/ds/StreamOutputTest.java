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

import static org.junit.Assert.*;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.util.Util;

public class StreamOutputTest {

    @BeforeClass
    public static void init() {
        DSTestUtil.allowFiles();
    }
    @Test
    public void testMLB () throws Exception {
        File f = new File("test/data/mlb-salaries.html");
        Metadata md = new Metadata(3);
        md.setColumnName(1, "rank");
        md.setColumnType(1, DataType.DOUBLE);
        md.setColumnName(2, "team");
        md.setColumnType(2, DataType.STRING);
        md.setColumnName(3, "payroll");
        md.setColumnType(3, DataType.DOUBLE);
        DataSourceInfo dsi = new DataSourceInfo(DataSourceInfo.HTML_TYPE);
        dsi.setInputMetadata(md);
        dsi.setUrl(f.toURI().toString());
        //dsi.setName("MLB Payroll");
        dsi.setProperty(DataSourceInfo.HTML_TABLEID,"" + 16);
        dsi.setDataStartRow(2);
        HtmlDataSource hi = new HtmlDataSource(dsi);
        DataStream ds = hi.getDataStream();

        StringWriter stringWriter = new StringWriter();
        StreamOutput so = new StreamOutput(ds,stringWriter);
        so.runStream();

        assertNotNull(stringWriter);
        assertTrue(stringWriter.toString().startsWith(
                     "rank,team,payroll\n"+
                     "1,New York Yankees,189639045\n"+
                     "2,Boston Red Sox,143026214\n"));
        assertTrue(stringWriter.toString().endsWith(
                     "28,Washington Nationals,37347500\n"+
                     "29,Florida Marlins,30507000\n"+
                     "30,Tampa Bay Devil Rays,24123500\n"));
        
        System.out.println("DataSourceInfo JSON: \n");
        JSON jout = JSONSerializer.toJSON(dsi);
        System.out.println(jout.toString());
        System.out.println("pretty printed: \n" + Util.prettyPrintJSON(jout.toString()));

        // FIXME: need more work to be able to convert the JSON back to our objects 
        Map<String,Class> classMap = new HashMap<String,Class>();
        classMap.put("metaData",Metadata.class);
        DataSourceInfo dsi2 = (DataSourceInfo) JSONObject.toBean(JSONObject.fromObject(jout),
                DataSourceInfo.class,
                classMap);
        System.out.println("toBean() resulted in DataSourceInfo of: ");
        System.out.println(dsi2.toString());
        
        System.out.println("original DataSourceInfo of: ");
        System.out.println(dsi.toString());

        
    }

    @Test
    public void testMonthlySPY() throws Exception {
        File f = new File("test/data/monthly-spy.csv");
            DataSourceInfo info = DataInference.get().inferFromURL(
                    new File("test/data/monthly-spy.csv").toURI().toString(),15).getDataSource();
    //    DataSourceInfo info = DSTestUtil.getDSInfo(f);
        DataStream ds = (new CSVDataSource(info)).getDataStream();
        
        // change the date format to something different from the default
        // note that we set the columnFormat here, and not earlier when we get the data
        // stream as if we do it earlier, the columnFormat is used for the *parsing* of the
        // data.  Here, we just want to use the columnFormat for the output format
        ds.getMetadata().setColumnFormat(1,"MM-dd-yy");
        
        StringWriter stringWriter = new StringWriter();
        StreamOutput so = new StreamOutput(ds,stringWriter);
        so.runStream();

        assertNotNull(stringWriter);
        assertTrue(stringWriter.toString().startsWith(
                "Date,Open,High,Low,Close,Volume,Adj Close\n"+
                "04-06-69,139.83,140.89,136.22,136.29,340787100,136.29\n"+
                "04-05-68,138.38,144.3,137.52,140.35,179236400,140.35\n"+
                "04-04-68,133.61,140.59,132.33,138.26,191956700,138.26\n"+
                "04-03-70,133.14,135.81,126.07,131.97,281440600,131.97\n"));
        assertTrue(stringWriter.toString().endsWith(
                "01-03-67,44.56,45.84,44.22,45.19,136100,35.24\n"+
                "01-02-67,43.97,45.13,42.81,44.41,288600,34.38\n"+
                "01-01-95,43.97,43.97,43.75,43.94,2006400,34.01\n"));
    }
    

    private DataSourceInfo getAllTypesInfo() {
        DataSourceInfo info = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        info.setUrl(new File("test/data/alltypes.csv").toURI().toString());
        Metadata md = new Metadata(5);
        md.setColumnName(1, "string column");
        md.setColumnType(1, DataType.STRING);
        md.setColumnName(2, "boolean column");
        md.setColumnType(2, DataType.BOOLEAN);
        md.setColumnName(3, "integer column");
        md.setColumnType(3, DataType.INTEGER);
        // omit the column 4 name on purpose
        md.setColumnType(4, DataType.DOUBLE);
        // omit the column 5 name on purpose
        md.setColumnType(5, DataType.DATE);
        info.setInputMetadata(md);
        return info;
    }
    @Test
    public void testJSONAllTypes() throws Exception {
        DataSourceInfo dsi = getAllTypesInfo();
        dsi.getInputMetadata().setColumnName(4, "double column");
        dsi.getInputMetadata().setColumnName(5, "date column");
        DataStream ds = (new CSVDataSource(dsi)).getDataStream();
        StringWriter stringWriter = new StringWriter();
        StreamOutput so = new JSONStreamOutput(ds,stringWriter,JSONStreamOutput.VARIANT_JSON);
        so.runStream();
        String data = stringWriter.toString();
        //System.out.println("\nINPUT\n" + data);
        JSONObject jsobj = JSONObject.fromObject(data);
        //System.out.println("\n\n" + jsobj.toString());
        JSONObject table = jsobj.getJSONObject("table");
        assertNotNull(table);
        JSONArray cols = table.getJSONArray("cols");
        assertNotNull(cols);
        assertEquals(5,cols.size());
        JSONObject col2 = cols.getJSONObject(1);
        assertNotNull(col2);
        assertEquals("boolean column", col2.get("label"));
        JSONArray rows = table.getJSONArray("rows");
        assertNotNull(rows);
        assertEquals(4,rows.size());
        JSONArray row4 = rows.getJSONArray(3);
        assertNotNull(row4);
        assertNotNull(row4.getJSONObject(1));
        assertEquals("false", row4.getJSONObject(1).get("v"));
        //JSONObject cols = table.getJSONObject("cols");
        //assertTrue(cols.isArray());
        //JSONArray colArray = (JSONArray) cols;
        //System.out.println("\nOUTPUT\n" + jsobj);
    }
    @Test
    public void testAllTypes() throws Exception {
        DataStream ds = (new CSVDataSource(getAllTypesInfo())).getDataStream();
        
     
        // change the double format from to something different from the default
        // note that we set the columnFormat here, and not earlier when we get the data
        // stream as if we do it earlier, the columnFormat is used for the *parsing* of the
        // data.  Here, we just want to use the columnFormat for the output format
        ds.getMetadata().setColumnFormat(4,"0.#####E0");
   
        StringWriter stringWriter = new StringWriter();
        StreamOutput so = new StreamOutput(ds,stringWriter);
        so.runStream();

        assertNotNull(stringWriter);
        assertEquals(stringWriter.toString(),
                "string column,boolean column,integer column,,\n"+
                "abcde,true,123,7.89E-1,2001-01-01\n"+
                "XYZ,false,-10,1.53246E2,2008-06-24\n"+
                "hello world,true,0,0E0,1999-09-08\n"+
                "goodbye,false,-12345,1.00013E2,2003-04-05\n"
                );
        
    }
    
}
