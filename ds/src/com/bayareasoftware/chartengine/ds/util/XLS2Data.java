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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.HSSFUserException;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

//import com.bayareasoftware.chartengine.ds.DSTestUtil;

//import com.bayareasoftware.chartengine.ds.DSTestUtil;


public class XLS2Data implements HSSFListener {
    private int maxRows;
    private POIFSFileSystem fs;

    private int lastRowNumber;
    private int lastColumnNumber;

    /** limit data only to specified sheet name **/
    private String requiredSheetName;
    /* the name of the sheet we're currently parsing */
    private String currentSheetName;
    
    /** Should we output the formula, or the value it has? */
    private boolean outputFormulaValues = true;

    /** For parsing Formulas */
    private SheetRecordCollectingListener workbookBuildingListener;
    private HSSFWorkbook stubWorkbook;

    // Records we pick up as we process
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;

    /** So we known which sheet we're on */
    private int sheetIndex = -1;
    private BoundSheetRecord[] orderedBSRs;
    private ArrayList boundSheetRecords = new ArrayList();

    // For handling formulas with string results
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    private Map<String,List<String[]>> sheet2data = new HashMap<String,List<String[]>>();
    private List<String[]> currentData = new ArrayList<String[]>();
    private List<String> currentRow = new ArrayList<String>();
    
    /**
     * Creates a new XLS -> Data List converter
     * 
     * @param fs
     *      The POIFSFileSystem to process
     * @param minColumns
     *      The minimum number of columns to output, or -1 for no minimum
     */
    private XLS2Data(POIFSFileSystem fs, String sheetName, int maxRows) {
        this.fs = fs;
        this.maxRows = maxRows;
        this.requiredSheetName = sheetName;
    }

    private XLS2Data(InputStream is, String sheetName) throws IOException {
        this(is,sheetName,Integer.MAX_VALUE);
    }
    /**
     * Creates a new XLS -> CSV converter
     * 
     * @param filename
     *      The file to process
     * @param minColumns
     *      The minimum number of columns to output, or -1 for no minimum
     * @throws IOException
     * @throws FileNotFoundException
     */
    public XLS2Data(InputStream is, String sheetName, int maxRows) throws IOException {
        this(new POIFSFileSystem(is), sheetName, maxRows);
    }
    private void startNewSheet() {
        if (currentSheetName != null) {
            sheet2data.put(this.currentSheetName, currentData);
        }
        currentData = new ArrayList<String[]>();
    }
    private void startNewRow() {
        int sz = currentRow.size();
        String[] sa = new String[sz];
        currentRow.toArray(sa);
        // convert empty strings to null
        for (int i = 0; i < sz; i++) {
            if (sa[i].length() == 0) {
                sa[i] = null;
            }
        }
        if (currentData.size() < maxRows) {
            currentData.add(sa);
        }
        currentRow.clear();
        if (currentData.size() >= maxRows) {
            //throw new HSSFUserException("max rows limit reached: " + maxRows);            
            //throw new RuntimeException("max rows limit reached: " + maxRows);
            throw new MaxRowsReachedException(maxRows);
        }
        
    }
    private static class MaxRowsReachedException extends RuntimeException {
        MaxRowsReachedException(int num) {
            super("maximum rows reached: " + num);
        }
    }
    private void appendCell(String value) {
        if (value == null) {
            value = "";
        }
        currentRow.add(value);
    }
    
    public List<String[]> getData() throws Exception {
        List<String[]> ret = null;
        // return specified sheet name, or else first sheet
        if (this.requiredSheetName != null) {
            ret = getData(requiredSheetName);
            if (ret == null) {
                throw new Exception("Required sheet name of: " + requiredSheetName + " is missing in spreadsheet");
            }
        } else {
//        if (ret == null) {
            List<String> names = getSheetNames();
            if (names.size() > 0) {
                ret = getData(names.get(0));
            }
        }
        return ret;
    }
    
    public List<String[]> getData(String sheetName) {
        return sheet2data.get(sheetName);
    }
    public List<String> getSheetNames() {
        List<String> ret = new ArrayList<String>();
        if (this.orderedBSRs != null) {
            for (BoundSheetRecord bsr : orderedBSRs) {
                ret.add(bsr.getSheetname());
            }
        }
        return ret;
    }
    
    public void process() throws IOException {
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
                this);
        formatListener = new FormatTrackingHSSFListener(listener);

        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();

        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new SheetRecordCollectingListener(
                    formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        try {
            // we can handle this when we only want to read a particular sheet
            // and use this exception when we've moved beyond that sheet
            factory.abortableProcessWorkbookEvents(request, fs);
        } catch (HSSFUserException hssue) {
            hssue.printStackTrace();
        } catch (MaxRowsReachedException re) {
            System.out.println("aborted? " + re);
        }
        // make sure we got the last sheet
        if (currentSheetName != null && sheet2data.get(currentSheetName) == null) {
            sheet2data.put(currentSheetName, currentData);
        }
        //factory.processWorkbookEvents(request, fs);        
    }
    /**
     * Main HSSFListener method, processes events, and outputs the CSV as the
     * file is processed.
     */
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;

        //p("looking at " + record.getClass().getName() + " sid=0x" + Integer.toHexString(record.getSid()));
        if ((record.getSid() != BoundSheetRecord.sid &&
                record.getSid() != BOFRecord.sid) &&
                currentSheetName != null && this.requiredSheetName != null
                && !this.requiredSheetName.equals(currentSheetName)) {
            return;
        }
        switch (record.getSid()) {
        case BoundSheetRecord.sid:
            boundSheetRecords.add(record);
            break;
        case BOFRecord.sid:
            BOFRecord br = (BOFRecord) record;
            if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                // Create sub workbook if required
                if (workbookBuildingListener != null && stubWorkbook == null) {
                    stubWorkbook = workbookBuildingListener
                            .getStubHSSFWorkbook();
                }

                // Output the worksheet name
                // Works by ordering the BSRs by the location of
                // their BOFRecords, and then knowing that we
                // process BOFRecords in byte offset order
                sheetIndex++;
                if (orderedBSRs == null) {
                    orderedBSRs = BoundSheetRecord
                            .orderByBofPosition(boundSheetRecords);
                }
                //output.println();
                startNewSheet();
                currentSheetName = orderedBSRs[sheetIndex].getSheetname();
                //System.err.println("got sheet name '" + currentSheetName + "' required='"
                  //      + this.requiredSheetName + "'");
                /*if (requiredSheetName == null) {
                    startNewRow();
                //output.println(orderedBSRs[sheetIndex].getSheetname() + " ["
                //+ (sheetIndex + 1) + "]:");
                    appendCell(currentSheetName + " ["
                            + (sheetIndex + 1) + "]:");
                    startNewRow();
                }
                */
            } else if (br.getType() == BOFRecord.TYPE_CHART) {
                //p("got chart");
            }
            break;

        case SSTRecord.sid:
            sstRecord = (SSTRecord) record;
            break;

        case BlankRecord.sid:
            BlankRecord brec = (BlankRecord) record;

            thisRow = brec.getRow();
            thisColumn = brec.getColumn();
            thisStr = "";
            break;
        case BoolErrRecord.sid:
            BoolErrRecord berec = (BoolErrRecord) record;

            thisRow = berec.getRow();
            thisColumn = berec.getColumn();
            thisStr = "";
            break;

        case FormulaRecord.sid:
            FormulaRecord frec = (FormulaRecord) record;

            thisRow = frec.getRow();
            thisColumn = frec.getColumn();

            if (outputFormulaValues) {
                if (Double.isNaN(frec.getValue())) {
                    // Formula result is a string
                    // This is stored in the next record
                    outputNextStringRecord = true;
                    nextRow = frec.getRow();
                    nextColumn = frec.getColumn();
                } else {
                    thisStr = "" + frec.getValue();
                    //thisStr = formatListener.formatNumberDateCell(frec);
                }
            } else {
                thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook,
                        frec.getParsedExpression()) + '"';
            }
            break;
        case StringRecord.sid:
            if (outputNextStringRecord) {
                // String for formula
                StringRecord srec = (StringRecord) record;
                thisStr = srec.getString();
                thisRow = nextRow;
                thisColumn = nextColumn;
                outputNextStringRecord = false;
            }
            break;

        case LabelRecord.sid:
            LabelRecord lrec = (LabelRecord) record;

            thisRow = lrec.getRow();
            thisColumn = lrec.getColumn();
            //thisStr = '"' + lrec.getValue() + '"';
            thisStr = lrec.getValue();
            break;
        case LabelSSTRecord.sid:
            LabelSSTRecord lsrec = (LabelSSTRecord) record;

            thisRow = lsrec.getRow();
            thisColumn = lsrec.getColumn();
            if (sstRecord == null) {
                thisStr = '"' + "(No SST Record, can't identify string)" + '"';
            } else {
                //thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex())
                //      .toString() + '"';
                thisStr = sstRecord.getString(lsrec.getSSTIndex())
                      .toString();
            }
            break;
        case NoteRecord.sid:
            NoteRecord nrec = (NoteRecord) record;

            thisRow = nrec.getRow();
            thisColumn = nrec.getColumn();
            // TODO: Find object to match nrec.getShapeId()
            thisStr = '"' + "(TODO - NoteRecord (" + thisRow + "," + thisColumn + "))" + '"';
            break;
        case NumberRecord.sid:
            NumberRecord numrec = (NumberRecord) record;

            thisRow = numrec.getRow();
            thisColumn = numrec.getColumn();

            // Format
            thisStr = formatListener.formatNumberDateCell(numrec);
            break;
        case RKRecord.sid:
            RKRecord rkrec = (RKRecord) record;

            thisRow = rkrec.getRow();
            thisColumn = rkrec.getColumn();
            thisStr = '"' + "(TODO - RKRecord (" + thisRow + "," + thisColumn + "))" + '"';
            break;
        default:
            break;
        }

        // Handle new row
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }

        // Handle missing column
        if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
            thisRow = mc.getRow();
            thisColumn = mc.getColumn();
            thisStr = "";
        }

        // If we got something to print out, do so
        if (thisStr != null) {
            appendCell(thisStr);
        }

        // Update column and row count
        if (thisRow > -1)
            lastRowNumber = thisRow;
        if (thisColumn > -1)
            lastColumnNumber = thisColumn;

        // Handle end of row
        if (record instanceof LastCellOfRowDummyRecord) {
            // We're onto a new row
            lastColumnNumber = -1;

            // End the row
            //output.println();
            startNewRow();
        }
    }

    static boolean closed = false;
    public static void main(String[] a) throws Exception {
        String file;
        String dir = "/home/dave/clients/poi-trunk/src/testcases/org/apache/poi/hssf/data/";
        file = dir + "WithChart.xls";
        file = "/home/dave/chartmechanic/trunk/sample-data/sample/spreadsheet-with-chart.xls";
        if (false && a.length > 0) {
            file = a[0];
        }
        InputStream is = new FileInputStream(file) {        
            public void close() throws IOException {
                super.close();
                closed = true;
            }
        };
        XLS2Data xd;
        //xd = new XLS2Data(is, "QFS Projects (cmplt'd)", -1);
        xd = new XLS2Data(is, null, 100);
        xd.process();
        System.err.println("input stream closed? " + closed);
        List<String[]> data = xd.getData();
        System.out.println("sheet names: " + xd.getSheetNames()
                + " map size: " + xd.sheet2data.size()
                + " rows: " + data.size());
        //DSTestUtil.printList(data);
    }
    private static void p(String s) {
        System.err.println("[XLS2Data] " + s);
    }
}
