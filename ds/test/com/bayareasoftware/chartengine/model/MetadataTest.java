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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import static org.junit.Assert.*;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.TypeRecognizer;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.SQLKeywords;

public class MetadataTest {
    private static final String[][] DATA = {
        { "X", "10",  "10.2", "99",  "1/3/1965"   },
        { "Y", "9",   "2",    "FOO", "10/12/2000" },
        { "Z", "100", "2.2",  "100", "11/5/1999"  }
    };

    private static List<String[]> DATA_LIST;
    static {
        DATA_LIST = new ArrayList<String[]>();
        for (int i = 0; i < DATA.length; i++) {
            DATA_LIST.add(DATA[i]);
        }
    }
    @Test
    public void testBasic() {
        Metadata md = getMetadata();
        assertEquals(3, md.getColumnCount());
        assertEquals("foo", md.getColumnName(1));
        assertEquals(DataType.toString(DataType.INTEGER), DataType.toString(md.getColumnType(1)));
        assertEquals(DataType.UNKNOWN, md.getColumnType(2));
        assertNull(md.getColumnName(3));
    }

    @Test
    public void testEquals() {
        Metadata md1 = getMetadata();
        Metadata md2 = getMetadata();
        md2.setColumnType(2, DataType.INTEGER);
        assertNotSame(md1, md2);
    }

    @Test
    public void testGuessTypes() {
        //CSVInfo csv = new CSVInfo();
        //DataSourceInfo csv = new DataSourceInfo(DataSourceInfo.CSV_TYPE);
        RawData rd = new RawData();
        rd.data = DATA_LIST;
        rd.dsType = DataSourceInfo.CSV_TYPE;
        TypeRecognizer tr = new TypeRecognizer(rd);
        tr.guessColumnTypes();
        Metadata md = rd.metadata;
        assertNotNull(md);
        assertEquals(DataType.STRING,  md.getColumnType(1));
        assertEquals(DataType.DOUBLE, md.getColumnType(2));
        assertEquals(DataType.DOUBLE,  md.getColumnType(3));
        assertEquals(DataType.toString(DataType.DOUBLE),  DataType.toString(md.getColumnType(4)));
        assertEquals(DataType.DATE,    md.getColumnType(5));
    }
    
    @Test
    public void testMungeSameColumnNames() {
        // confirm that munging the column names generates unique column names
        Metadata md = new Metadata(5);
        md.setColumnName(1, "foo");
        md.setColumnName(2, "Foo");
        md.setColumnName(3, "fOo");
        md.setColumnName(4, "foo");
        md.setColumnName(5, "foo");
        
        md.mungeColumnNames(false);
        HashSet<String> names = new HashSet<String>();
        for (int i=1;i<=md.getColumnCount();i++) {
//            System.err.println("colName = " + md.getColumnName(i));
            names.add(md.getColumnName(i));
        }
        assertEquals(5, names.size());
    }
    static final String[] NAMES = {
      " Some  Data ", "Some Data",
      "Totally, Comp|ete|y,\t\r\n    \r\nBogus,\n\n", "Totally Competey Bogus"
    };
    @Test
    public void testSimpleMunge() {
        for (int i = 0; i < NAMES.length; i+=2) {
            //System.err.println("munges to '" + Metadata.mungeColumn(NAMES[i]) + "'");
            assertEquals(NAMES[i+1], Metadata.mungeColumn(NAMES[i]));
        }
        Metadata md = new Metadata(2);
        md.setColumnName(1, "BOGUS | NAME");
        md.setColumnName(2, "BOGUS , NAME");
        md.mungeColumnNames(false);
        assertEquals("BOGUS NAME", md.getColumnName(1));
        assertEquals("BOGUS NAME1", md.getColumnName(2));
    }
    @Test
    public void testMungeColumnNamesWithKeywords() {
        // confirm that munging the column names generates unique column names
        Metadata md = new Metadata(8);
        md.setColumnName(1, "foo");
        md.setColumnName(2, "Foo");
        md.setColumnName(3, "fOo");
        md.setColumnName(4, "SELECT");
        md.setColumnName(5, "S E L E C T");
        md.setColumnName(6, "S.E.L.E.C.T");
        md.setColumnName(7, "fROM");
        md.setColumnName(8, "   f   ; ROM ");
        
        md.mungeColumnNames(true);
        HashSet<String> names = new HashSet<String>();
        for (int i=1;i<=md.getColumnCount();i++) {
//            System.err.println("colName = " + md.getColumnName(i));
            names.add(md.getColumnName(i).toLowerCase());
            assertFalse(SQLKeywords.isKeyword(md.getColumnName(i)));
        }
        assertTrue(names.size() == 8);
    }
    
    @Test
    public void testSerialize() {
        Metadata md = new Metadata(4);
        //md.setName("TestMetaData");
        //md.setDescription("test meta data object");
        md.setColumnName(1, "foo");
        md.setColumnType(1, DataType.INTEGER);
        md.setColumnName(2, "bar");
        // no type specified by column 2, defaults to UNKNOWN
        md.setColumnName(3, "baz");
        md.setColumnType(3, DataType.DOUBLE);
        md.setColumnName(4, "col4");
        md.setColumnType(4, DataType.DATE);
        md.setColumnFormat(4, "yyyy-MM-dd");
        
        Metadata md2 = new Metadata();
        md2.deserializeFromProps(md.serializeToProps(null, null),null);
        assertTrue(md.equals(md2));
        
        Metadata md3 = new Metadata();
        String prefix ="metadata.0.";
        
        md3.deserializeFromProps(md.serializeToProps(null, prefix),prefix);
        assertTrue(md.equals(md3));
        
    }
    
    
    private Metadata getMetadata() {
        Metadata md = new Metadata(3);
        md.setColumnName(1, "foo");
        md.setColumnType(1, DataType.INTEGER);
        md.setColumnName(2, "bar");
        md.setColumnType(3, DataType.DOUBLE);
        return md;
    }
}
