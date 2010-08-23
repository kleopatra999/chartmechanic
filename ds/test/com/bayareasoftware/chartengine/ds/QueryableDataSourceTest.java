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

//import com.bayareasoftware.chartengine.model.CSVInfo;
import static com.bayareasoftware.chartengine.model.DataType.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.util.MetadataUtil;
import com.bayareasoftware.chartengine.model.Metadata;

import java.io.File;
import java.util.Date;

public class QueryableDataSourceTest {
    @Test
    public void testFoo() {}
    /*
    @Test
    public void testLogData() throws Exception {
        QueryableDataSource qds = new QueryableDataSource(
            getSvnLogDataStream("test/data/svnlog.csv"));
        DataStream ds = qds.executeQuery(
            "SELECT REVISION, DATE, DESCRIPTION FROM ChartData WHERE OWNER = 'jolly'"); 
        assertTrue(ds.next());
        assertTrue(ds.getObject(1) instanceof Integer);
        assertTrue(ds.getObject(2) instanceof Date);
        assertTrue(ds.getObject(3) instanceof String);
    }

    private static DataStream getSvnLogDataStream(String file) throws Exception {
        DataStream ds = getCSVDataStream(file);
        Metadata md = ds.getMetadata();
        MetadataUtil.setColumnNames(md,
            new String[] {"REVISION", "OWNER", "DATE", "DESCRIPTION"});
        MetadataUtil.setColumnTypes(md,
            new int[] { INTEGER, STRING, DATE, STRING });
        md.setColumnFormat(3, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return ds;
    }
    
    private static DataStream getCSVDataStream(String file) throws Exception {
        CSVInfo info = new CSVInfo();
        info.setUrl(new File(file).toURL().toString());
        return new CSVDataSource(info).getDataStream();
    }
    */
    
}
