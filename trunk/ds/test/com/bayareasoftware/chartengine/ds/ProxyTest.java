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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bayareasoftware.chartengine.ds.util.WebProxy;
import com.bayareasoftware.chartengine.ds.util.WebProxy.ProxyRecord;
import com.bayareasoftware.chartengine.model.DataType;
import com.bayareasoftware.chartengine.model.InferenceException;
import com.bayareasoftware.chartengine.model.InferredData;
import com.bayareasoftware.chartengine.model.RawData;
import com.bayareasoftware.chartengine.util.URLUtil;

public class ProxyTest {

    private static WebProxy proxy;
    private static boolean connected = true;
    private static PrintStream proxyLog;
    @BeforeClass
    public static void startProxy() throws Exception {
        if ("true".equalsIgnoreCase(System.getProperty("disconnected"))) {
            p("not connected, will skip proxy tests");
            connected = false;
        }
        
        proxy = new WebProxy(0);
        proxyLog = new PrintStream(new FileOutputStream("test.out/http-proxy.log"));
        proxy.setDebug(1, proxyLog);
        proxy.start();
        // allow proxy to bind to ephemeral server socket port
        Thread.sleep(2000);
        p("started proxy on localhost:" + proxy.getPort());
        // install our own proxy selector, to fail when the proxy fails
        // i want to catch those errors, rather than letting the sun.net.*
        // implementation connect directly....
        ProxySelector.setDefault(new MyProxySelector(ProxySelector.getDefault()));
        /*
        List<String> nonProxy = new ArrayList<String>();
        nonProxy.add("localhost");
        nonProxy.add("sf.bayareasoftware.com");
        URLUtil.setProxyInformation("localhost", proxy.getPort(), nonProxy);
        URLUtil.setProxyInformation("localhost", proxy.getPort(), (String)null);
        */
    }
    @AfterClass
    public static void stopProxy() {
        if (proxy != null) {
            proxy.closeSocket();
            if (proxyLog != null) {
                proxyLog.flush();
                proxyLog.close();
            }
        }
    }

    @Test
    public void testHTTP() throws Exception {
        if (!connected) {
            p("not connected, will skip proxy tests");
            return;
        }
        
        String url1 = "http://www.chartmechanic.com/rest/fs/demo/FRB/H3/H3/RESNB14A_BA_N.WW?format=csv";
        String url2 = "http://sf.bayareasoftware.com/private/data.csv";
        RawData rd;
        
        // (1) turn off proxy, everything should connect directly
        setProxy(null, -1, (String)null);
        proxy.clearRecords();
        rd = getData(url1, null, null);
        assertTrue("expect www.chartmechanic.com not to be proxied", proxy.getRecords().size() == 0);
        rd = getData(url2, "guest", "gumby1234");
        assertTrue("expect sf.bayareasoftware.com not to be proxied", proxy.getRecords().size() == 0);
        
        // (2) turn on proxy, but exclude sf.bayareasoftware.com
        setProxy("localhost", proxy.getPort(), "localhost,sf.bayareasoftware.com");
        proxy.clearRecords();
        rd = getData(url1,null,null);
        assertProxyHostPresent("chartmechanic.com");
        assertEquals("expect 2 columns in cm metadata", 2, rd.metadata.getColumnCount());
        assertEquals("DATE", DataType.toString(rd.metadata.getColumnType(1)));
        assertEquals("D", rd.metadata.getColumnName(1));
        assertEquals("OBS", rd.metadata.getColumnName(2));
        
        proxy.clearRecords();
        assertEquals(0,proxy.getRecords().size());
        rd = getData(url2, "guest","gumby1234");
        p("sf metadata=" + rd.metadata);
        assertTrue("expect sf.bayareasoftware.com not to be proxied ("
                + proxy.getRecords() + ")", proxy.getRecords().size() == 0);

        // (3) turn on proxy, include sf.bayareasoftware.com
        setProxy("localhost", proxy.getPort(), "localhost");
        proxy.clearRecords();
        rd = getData(url2, "guest","gumby1234");
        assertProxyHostPresent("sf.bayareasoftware.com");
    }

//    @Test
//    public void testHTTPS() throws Exception {
//        if (!connected) {
//            p("not connected, will skip proxy tests");
//            return;
//        }
//        URLUtil.openSSL();
//        String url = "https://sf.bayareasoftware.com/private/CS-home-prices.xls";
//        RawData rd;
//        // (1) turn off proxy, everything should connect directly
//        setProxy(null, -1, (String)null);
//        proxy.clearRecords();
//        try {
//            rd = getData(url, "guest", "gumby1234");
//        } catch (Exception e) {
//            Throwable t = e.getCause();
//            t.printStackTrace();
//        }
//        assertTrue("expect sf.bayareasoftware.com not to be proxied", proxy.getRecords().size() == 0);
//        // (2) proxy for everything
//        setProxy("localhost", proxy.getPort(), "localhost");
//        proxy.clearRecords();
//        rd = getData(url, "guest", "gumby1234");
//        assertProxyHostPresent("sf.bayareasoftware.com");
//        p("https proxy metadata: " + rd.metadata);
//        assertEquals(25, rd.metadata.getColumnCount());
//        assertEquals("expect col 1 to be type DATE",
//                "DATE", DataType.toString(rd.metadata.getColumnType(1)));
//        assertEquals("expect col 2 to be type NUMBER",
//                "NUMBER", DataType.toString(rd.metadata.getColumnType(2)));
//    }
    
    private static RawData getData(String url, String user, String password) throws Exception {
        InferredData id = null;
        //url = "http://chartmechanic.com/rest/fs/dave/Case%20Shiller%20for%20selected%20cities?format=csv";
        //url = "http://www.chartmechanic.com/rest/fs/demo/FRB/H3/H3/RESNB14A_BA_N.WW?format=csv";
        try {
            id = DataInference.get().inferFromURL(
                    url,user,password
                    );
        } catch (InferenceException infe) {
            p("infer failed: " + infe.getMessage() + "/" + infe);
            throw infe;
        }
        assertNotNull(id);
        RawData rd = id.getRawData();
        assertNotNull(rd);
        return rd;
    }
    private static void assertProxyHostPresent(String host) throws InterruptedException {
        Thread.sleep(1000);
        List<ProxyRecord> records = proxy.getRecords();
        for (ProxyRecord rec : records) {
            if (rec.firstLine.indexOf(host) != -1) {
                return;
            }
        }
        String msg = "ERROR: host '" + host + "' not found in proxy " + records.size() + " record(s):";
        p(msg);
        proxy.printRecords();
        fail(msg);
    }
    private static void setProxy(String host, int port, String exclude) throws Exception {
        URLUtil.setProxyInformation(host, port, exclude);
    }
    static class MyProxySelector extends ProxySelector {

        ProxySelector delegate;
        MyProxySelector(ProxySelector delegate) {
            this.delegate = delegate;
        }
        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            System.err.println("connect failed to " + uri + "/" + sa + ": " + ioe);
            throw new RuntimeException("proxy at " + sa + " failed", ioe);
        }

        @Override
        public List<Proxy> select(URI uri) {
            List<Proxy> ret = delegate.select(uri);
            p("select(" + uri + ")-> size=" + ret.size() + " / " + ret);
            return ret;
        }
        
    }
    private static void p(String s) {
        System.out.println("[ProxyTest] " + s);
    }
}
