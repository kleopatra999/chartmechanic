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

import com.bayareasoftware.chartengine.ds.util.GSSUtil;
import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.util.List;

public class GSSDataSource extends /*Queryable*/DataSource {
    private DataSourceInfo gss;
    
    public GSSDataSource(DataSourceInfo info) throws Exception {
        //super(loadData(info), info.getTableName());
        //super(info.getMetadata());
        this.gss = info;
    }

    public DataStream getDataStream() throws Exception {
        return this.evalStreamScript(loadData(gss,Integer.MAX_VALUE));
    }
    
    /*private */static List<String[]> loadRawStrings(DataSourceInfo info, int maxrows)
        throws ServiceException, IOException {
        SpreadsheetService service = GSSUtil.getSpreadsheetService(
                info.getUsername(), info.getPassword()
                );
        List<String[]> data = GSSUtil.getSpreadsheetData(service, info.getUrl(), 
                                                        info.getProperty(DataSourceInfo.SPREADSHEET_NAME),
                                                        maxrows);
        return data;
    }
    public static List<String[]> getRawStrings(String url, String username, String password,
            String sheetName, int maxRows) throws ServiceException, IOException {
        SpreadsheetService service = GSSUtil.getSpreadsheetService(
                username, password
                );
        List<String[]> data = GSSUtil.getSpreadsheetData(service, url,
                                                        sheetName,
                                                        maxRows);
        return data;
        
    }

    private DataStream loadData(DataSourceInfo info, int maxrows)
    throws ServiceException, IOException {
        List<String[]> data = loadRawStrings(info,maxrows);
        if (info.isRowInverted()) {
            data = RawData.invert(data);
        }
        StringDataStream stream = new StringDataStream(data, info.getInputMetadata(),
                info.getDataStartRow(),info.getDataEndRow());
        return stream;
    }
}
