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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.Before;
import org.junit.Test;

import com.bayareasoftware.chartengine.model.DataSourceInfo;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.InferenceException;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.Metadata;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.model.TableSynopsis;

public class DataInferenceTest {

    private boolean connected = true;
    
    @Before
    public void setUp() {
        DSTestUtil.allowFiles();
        if ("true".equalsIgnoreCase(System.getProperty("disconnected"))) {
            p("not connected, will skip GSS tests");
            connected = false;
        }
    }
    
    @Test
    public void testInferTabSeparated() throws Exception {
    	String data = "C1\tC2\n";
    }
    
    @Test
    public void testInferH4Html() throws Exception {
        File f = new File("test/data/h41hist1.htm");
        RawData rd = DataInference.get().inferFromURL(f.toURI().toString(),-1).getRawData();
        assertEquals("expect HTML", DataSourceInfo.HTML_TYPE, rd.dsType);
        Metadata md = rd.metadata;
        assertEquals(10, md.getColumnCount());
        assertEquals("DATE", DataType.toString(md.getColumnType(1)));
        //System.out.println("got raw data: " + rd);
        //System.out.println("got metadata: " + md);
    }
    @Test
    public void testInferFromCSV() throws Exception {
        String values = "2008-01-01,1.0,abc\n"+
                        "2008-02-02,2.0,def";
        //DataSourceInfo ds = DSFactory.createInlineDS(values);
        DataSourceInfo ds = DataInference.get().inferFromCSV(values).getDataSource();
        
        assertEquals(ds.getTypeName(), DataSourceInfo.CSV_TYPE);
        assertEquals(values, ds.getProperty(DataSourceInfo.CSV_DATA));
        Metadata md = ds.getInputMetadata();
        assertTrue(md.getColumnCount() == 3);
        assertEquals(DataType.DATE, md.getColumnType(1));
        assertEquals(DataType.DOUBLE, md.getColumnType(2));
        assertEquals(DataType.STRING, md.getColumnType(3));
    }
    
    @Test
    public void testInferAmbiguousHTMLpage() throws Exception {
        // try to infer from a HTML page that has multiple tables
        DataInference di = DataInference.get();
        File f;
        f = new File("test/data/mlb-salaries.html");
        InferredData idata = di.inferFromURL(f.toURI().toString(),-1);
        List<TableSynopsis> synopses = idata.getSynopses();

        assertTrue(synopses == null); // only one reasonable table in that html
        
        
    }
    
    @Test
    public void testInferFromURLString() throws Exception {
        DataInference di = DataInference.get();
        File f;
        f = new File("test/data/mlb-salaries.html");
        assertEquals(DataSourceInfo.HTML_TYPE, di.inferTypeFromURLString(f.toURI().toString()));
        f = new File("test/data/mlb-salaries.HTM");
        assertEquals(DataSourceInfo.HTML_TYPE, di.inferTypeFromURLString(f.toURI().toString()));
        f = new File("test/data/brokerage.csv");
        assertEquals(DataSourceInfo.CSV_TYPE, di.inferTypeFromURLString(f.toURI().toString()));
        f = new File("test/data/timesheet.xls");
        assertEquals(DataSourceInfo.EXCEL_TYPE, di.inferTypeFromURLString(f.toURI().toString()));
    }
    
    private static String SF_URL = "http://sf.bayareasoftware.com/"; 
    @Test
    public void testInferHTTP() throws Exception {
        if (!connected) {
            p("tests running in disconnected mode, skipping testInferHTTP()");
            return;
        }
        DataInference di = DataInference.get();
        // should get 401
        RawData rd;
        try {
            //rd = di.inferFromURL(SF_URL + "private/data.csv", null, null);
            rd = di.inferFromURL(SF_URL + "private/data.csv",-1).getRawData();
            fail("expected InferenceException to be raised for password protected file");
        } catch (InferenceException ife) {
                assertTrue("expect requireAuth()=true", ife.requiresAuth());
        } catch (Throwable t) {
            t.printStackTrace();
            fail("unexpected exception raised " + t);
        }
        // should go through
        DataSourceInfo dsi;
        
        dsi = di.inferFromURL(SF_URL + "private/data.csv", "guest", "gumby1234").getDataSource();
        assertEquals(DataSourceInfo.CSV_TYPE, dsi.getTypeName());
        
        dsi = di.inferFromURL(SF_URL + "private/data.html", "guest", "gumby1234").getDataSource();
        assertEquals(DataSourceInfo.HTML_TYPE, dsi.getTypeName());
        
        dsi = di.inferFromURL(SF_URL + "private/CS-home-prices.xls", "guest", "gumby1234").getDataSource();
        assertEquals(DataSourceInfo.EXCEL_TYPE, dsi.getTypeName());
    }

//    private static String SF_HTTPS_URL = "https://sf.bayareasoftware.com/"; 
//    @Test
//    public void testInferHTTPS() throws Exception {
//        if (!connected) {
//            p("tests running in disconnected mode, skipping testInferHTTP()");
//            return;
//        }
//        DataInference di = DataInference.get();
//        // should get 401
//        InferredData id;
//        RawData rd;
//        DataSourceInfo dsi;
//        setCertValidation();
//        // should go through
//        id = di.inferFromURL(SF_HTTPS_URL + "private/CS-home-prices.xls", "guest", "gumby1234");
//        assertFalse("should not need more info about XLS", id.needMoreInfo());
//        rd = id.getRawData();
//        assertNotNull("expect rawdata present", rd);
//        assertEquals("expect XLS type", DataSourceInfo.EXCEL_TYPE, rd.dsType);
//    }
    private void setCertValidation() throws Exception {
        CertificateFactory fac = CertificateFactory.getInstance("X.509");
        // self-signed cert for sf.bayareasoftware.com
        InputStream is = new FileInputStream("test/ssl/server.crt");
        X509Certificate cert = null;
        try {
            cert = (X509Certificate)fac.generateCertificate(is);
        } finally {
            is.close();
        }
        p("generated cert: " + cert.getClass().getName() + " type=" + cert.getType());
        final X509Certificate baSoftCert = cert;
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[] { baSoftCert };
                }
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType)
                throws CertificateException {
                    p("checkServerTrusted(type='" + authType + "' # certs=" + certs.length + ")");
                    for (X509Certificate cert : certs) {
                        //p("cert X500 principal: " + cert.getSubjectX500Principal().getName());
                        if (cert.equals(baSoftCert)) {
                            p("cert checks out with basoft!");
                            return;
                        }
                    }
                    throw new CertificateException("cannot trust " + certs[0].toString());
                }
            }
        };
        
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        //if (true) return;
    }
    private static void p(String s) {
        System.err.println("[DataInferenceTest] " + s);
    }
    
}
