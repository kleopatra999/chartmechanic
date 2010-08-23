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
package com.bayareasoftware.chartengine.ds.util;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.Cell;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for accessing the Google Spreadsheet Service.
 */
public final class GSSUtil {
    private static final FeedURLFactory FACTORY = FeedURLFactory.getDefault();
    private static final String APPLICATION_NAME = "chartmechanic.com-ChartMechanic-0.1";

    private static final String PRIVATE = "private";
    private static final String FULL = "full";

    private static final String USERNAME = "chartmechanic@gmail.com";
    private static final String PASSWORD = "gumby1234";
    
    /**
     * Returns a SpreadsheetService for the specified Google username and password.
     *
     * @param username the username (i.e. chartmechanic@gmail.com)
     * @param password the user's password
     * @return a SpreadsheetService for accessing the user's documents
     * @throws AuthenticationException if the username or password was incorrect
     */
    public static SpreadsheetService getSpreadsheetService(String username,
                                                           String password)
            throws AuthenticationException {
        SpreadsheetService service = new SpreadsheetService(APPLICATION_NAME);
        if (username == null && password == null) {
            username = USERNAME;
            password = PASSWORD;
        }
        if (username != null && password != null) {
            service.setUserCredentials(username, password);
        }
        return service;
    }

    /**
     * Returns a SpreadsheetService for the 'chartmechanic' Google user.
     * 
     * @return the SpreadsheetService accessing the user's documents
     * @throws AuthenticationException if the username or password was incorrect
     */
    public static SpreadsheetService getSpreadsheetService()
            throws AuthenticationException {
        return getSpreadsheetService(USERNAME, PASSWORD);
    }
    
    /**
     * Given a SpreadsheetService and spreadsheet URL, returns the data for
     * the spreadsheet as a matrix of string values.
     *
     * @param service the SpreadsheetService
     * @param url the Google Spreadsheets URL of the spreadsheet
     * @param name the optional name of the worksheet (defaults to first one)
     * @return the spreadsheet data
     * @exception IOException if an I/O error occurred
     * @exception ServiceException if there was an error processing the request
     */
    public static List<String[]> getSpreadsheetData(SpreadsheetService service,
                                                String url, String name, int maxrows)
            throws IOException, ServiceException {
        String key = FeedURLFactory.getSpreadsheetKeyFromUrl(url);
        URL feedUrl = FACTORY.getWorksheetFeedUrl(key, PRIVATE, FULL);
        WorksheetFeed feed = service.getFeed(feedUrl, WorksheetFeed.class);
        WorksheetEntry worksheet = getWorksheet(feed, name);
        maxrows = Math.min(maxrows, worksheet.getRowCount());
        String[][] data = new String[maxrows][worksheet.getColCount()];
        CellFeed cells = service.getFeed(worksheet.getCellFeedUrl(), CellFeed.class);
        int rownum = 0;
        for (CellEntry ce : cells.getEntries()) {
            Cell cell = ce.getCell();
            int row = cell.getRow();
            int col = cell.getCol();
            if (row > 0 && col > 0 && row <= maxrows) {
                data[row - 1][col - 1] = cell.getValue();
            }
        }
        return trim(data);
    }

    /**
     * Given a spreadsheet service and spreadsheet URL, returns the data for
     * the spreadsheet as a matrix of string values. The data for the first
     * worksheet is returned
     *
     * @param service the SpreadsheetService
     * @param url the Google Spreadsheets URL of the spreadsheet
     * @return the spreadsheet data
     * @exception IOException if an I/O error occurred
     * @exception ServiceException if there was an error processing the request
     */
    public static List<String[]> getSpreadsheetData(SpreadsheetService service,
                                                	String url, int maxrows)
            throws IOException, ServiceException {
        return getSpreadsheetData(service, url, null, maxrows);
    }
    
    private static WorksheetEntry getWorksheet(WorksheetFeed feed, String name) {
        List<WorksheetEntry> worksheets = feed.getEntries();
        if (name == null) return worksheets.get(0);
        for (WorksheetEntry worksheet : worksheets) {
            if (name.equals(worksheet.getTitle().getPlainText())) {
                return worksheet;
            }
        }
        throw new IllegalArgumentException("Worksheet named '" + name + "' not found");
    }
    
    // remove trailing empty rows and columns from data
    private static List<String[]> trim(String[][] data) {
        return removeEmptyColumns(removeEmptyRows(data));
    }
    
    private static String[][] removeEmptyRows(String[][] data) {
        // Remove trailing empty rows
        for (int i = data.length; --i >= 0 && isEmptyRow(data[i]); ) {
            String[][] tmp = new String[i][];
            System.arraycopy(data, 0, tmp, 0, tmp.length);
            data = tmp;
        }
        return data;
    }

    private static boolean isEmptyRow(String[] row) {
        for (String cell : row) {
            if (cell != null) return false;
        }
        return true;
    }

    private static List<String[]> removeEmptyColumns(String[][] data) {
        // Find maximum index of non-empty column
        int maxCol = 0;
        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && i > maxCol) maxCol = i;
            }
        }
        // Remove trailing empty columns
        List<String[]> result = new ArrayList<String[]>();
        
        for (int i = 0; i < data.length; i++) {
            String[] row = new String[maxCol + 1];
            System.arraycopy(data[i], 0, row, 0, row.length);
//            data[i] = row;
            result.add(row);
        }
        return result;
    }

    // timesheet sample
    private static final String URL_1 = "http://spreadsheets.google.com/ccc?key=pjoWJxgUx4FgUzT_LzczmVw&hl=en";
    // svnlog sample
    private static final String URL_2 = "http://spreadsheets.google.com/ccc?key=pjoWJxgUx4FgJmoguOT7NWg&hl=en";
    // brokerage sample
    private static final String URL_3 = "http://spreadsheets.google.com/ccc?key=pjoWJxgUx4FjAUOcIbhnhOQ&hl=en";
    // shiller P/E example
    private static final String URL_4 = "http://spreadsheets.google.com/ccc?key=pLJJLZL5xN362xBFVaibEYA&hl=en";
    public static void main(String[] args) throws Exception {
        SpreadsheetService service = new SpreadsheetService("ChartMechanic");
        service.setUserCredentials("chartmechanic@gmail.com", "gumby1234");
        
        printSheet(service, URL_4);
    }
    
    private static void printSheet(SpreadsheetService service, String url)
    throws Exception {
        List<String[]> data = getSpreadsheetData(service, url, Integer.MAX_VALUE);
        for (String[] row : data) {
            printRow(row);
        }
    }

    private static void printRow(String[] row) {
        StringBuilder sb = new StringBuilder();
        for (String s : row) {
            sb.append('|').append(s);
        }
        System.out.println(sb.append('|'));
    }
}
