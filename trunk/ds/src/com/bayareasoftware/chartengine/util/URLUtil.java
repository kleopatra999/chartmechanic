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
package com.bayareasoftware.chartengine.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bayareasoftware.chartengine.model.StringUtil;

public class URLUtil {
    
    private static final Log log = LogFactory.getLog(URLUtil.class);
    private static final int DEFAULT_IO_TIMEOUT = 10000; // msec 

    // Firefox 3 on windows
    private static final String DEFAULT_USER_AGENT =
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3 CM-Variant";

    private static boolean allowFileUrls = Boolean.getBoolean("chartengine.allowFileUrls");
    /**
     *  HTTP basic auth password: http://en.wikipedia.org/wiki/Basic_access_authentication
     */
    public static String httpBasicAuth(String username, String password) {
        return new String(
                Base64.encodeBase64((username + ':' + password).getBytes(), false)
        );
    }

    public static void setBasicAuth(URLConnection conn, String username, String password ) {
        conn.addRequestProperty("Authorization", "Basic " + httpBasicAuth(username,password));
    }

    public static void setUserAgent(URLConnection conn) {
        conn.addRequestProperty("User-Agent", DEFAULT_USER_AGENT);
    }
    
    public static boolean isHTTP(String url) {
        url = url.toLowerCase();
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return true;
        }
        return false;
    }
    
    public static boolean isFTP(String url) {
        url = url.toLowerCase();
        if (url.startsWith("ftp://")) {
            return true;
        }
        return false;
        
    }
    private static URLStreamHandler vfsStreamHandler;
    
    public static void setURLStreamHandler(URLStreamHandler h) {
        vfsStreamHandler = h;
    }
    
    public static URL safeURL(String url) throws MalformedURLException {
        if (isVFS(url)) {
            //return new URL(null,url,vfsStreamHandler);
            return newVFSURL(url);
        } 
        if (isHTTP(url) || isFTP(url) || (isFile(url) && allowFileUrls)) {
            return new URL(url);
        } else if (allowFileUrls && url != null && url.startsWith("/")) {
            return URLUtil.class.getResource(url);
        }
        throw new MalformedURLException("invalid URL '" + url + "'");
    }
    private static boolean isVFS(String url) {
        return url.startsWith("vfs:") || url.startsWith("VFS:");
    }
    private static boolean isFile(String url) {
        return url.startsWith("file:");
    }
    // FIXME: HACK to keep SDMX url's working on production site
    private static boolean isPublicWeb(String url) {
        return url.startsWith("file:/var/www/html");
    }

    private static URL newVFSURL(String url) throws MalformedURLException{
        URL u = new URL(null,url,vfsStreamHandler);
        return u;
    }
    public static InputStream openURL(String url, String username, String password)
    throws IOException {
        if (isHTTP(url)) {
            return connectHTTP(url,username,password).getInputStream();
        } else if (isFTP(url)) {
            return connectFTP(url,username,password).getInputStream();
        } else if (isVFS(url)) {
            URL u = newVFSURL(url);
            return u.openStream();
        } else if (isFile(url) && (allowFileUrls || isPublicWeb(url))) {
            URL u = new URL(url);
            return u.openStream();
        } else {
            throw new MalformedURLException("invalid url '" + url + "'");
        }
    }
    public static HttpURLConnection connectHTTP(String url) throws IOException {
        return connectHTTP(url,null,null);
    }
    
    public static HttpURLConnection connectHTTP(String url, String username, String password)
    throws IOException {
        HttpURLConnection ret = null;
        if (!URLUtil.isHTTP(url)) {
            throw new IllegalArgumentException(
                    "not HTTP protocol: '" + url + "'"
            );
        }
        URL u = new URL(url);
        ret = (HttpURLConnection) u.openConnection();
        if (username != null && password != null) {
            URLUtil.setBasicAuth(ret, username, password);
        }
        URLUtil.setUserAgent(ret);
        ret.setConnectTimeout(DEFAULT_IO_TIMEOUT);
        ret.setReadTimeout(DEFAULT_IO_TIMEOUT);
        return ret;
    }
    
    public static URLConnection connectFTP(String url, String username, String password) throws IOException {
        URLConnection ret = null;
        if (!isFTP(url)) {
            throw new IllegalArgumentException(
                    "not FTP protocol: '" + url + "'"
            );
        }
        URL u = new URL(url);
        ret = u.openConnection();
        if (username != null && password != null) {
            URLUtil.setBasicAuth(ret, username, password);
        }
        URLUtil.setUserAgent(ret);
        ret.setConnectTimeout(DEFAULT_IO_TIMEOUT);
        ret.setReadTimeout(DEFAULT_IO_TIMEOUT);
        return ret;
    }
    /**
     * return a Writer to a VFS path given its url
     * @param url
     * @return null if not a VFS url
     * @throws IOException
     */
    public static Writer openURLWriter(String url) throws IOException {
        if (isVFS(url)) {
            //URL u = new URL(url);
            URL u = newVFSURL(url);
            OutputStream os = u.openConnection().getOutputStream();
            return new BufferedWriter(new OutputStreamWriter(os));
        }
        return null;
    }

    public static class ProxyInfo {
        public String host;
        int port;
        public List<String> nonProxyHosts;
    }
    private static final String PROP_HTTP_PROXY_HOST = "http.proxyHost";
    private static final String PROP_HTTP_PROXY_PORT = "http.proxyPort";
    private static final String PROP_HTTPS_PROXY_HOST = "https.proxyHost";
    private static final String PROP_HTTPS_PROXY_PORT = "https.proxyPort";
    private static final String PROP_NON_PROXY = "http.nonProxyHosts";
    
    /**
     * Set the HTTP/HTTPS proxy to use for the runtime, or null hostname for no
     * proxying at all.
     * 
     * @param hostname - proxy host, or null
     * @param port - proxy port
     * @param nonProxyHostList - comma-separated hostnames to be excluded from proxying
     */
    public static void setProxyInformation(String hostname, int port, String nonProxyHostList) {
        List<String> npl = null;
        if ((nonProxyHostList = StringUtil.trim(nonProxyHostList)) != null) {
            String[] sa = StringUtil.splitCompletely(nonProxyHostList, ',', true);
            npl = new ArrayList<String>();
            for (String s : sa) {
                npl.add(s);
            }
        }
        setProxyInformation(hostname,port,npl);
    }
    private static void setProxyInformation(String hostname, int port, List<String> nonProxyHosts) {
        log.info("setting HTTP(S) proxy to " + hostname + ":" + port + ", non-proxy=" + nonProxyHosts);
        Properties sp = System.getProperties();
        if (hostname == null) {
            sp.remove(PROP_HTTP_PROXY_HOST);
            sp.remove(PROP_HTTP_PROXY_PORT);
            sp.remove(PROP_HTTPS_PROXY_HOST);
            sp.remove(PROP_HTTPS_PROXY_PORT);
            sp.remove(PROP_NON_PROXY);
            return;
        }
        String ps = "" + port;
        sp.setProperty(PROP_HTTP_PROXY_HOST, hostname);
        sp.setProperty(PROP_HTTPS_PROXY_HOST, hostname);
        sp.setProperty(PROP_HTTP_PROXY_PORT, ps);
        sp.setProperty(PROP_HTTPS_PROXY_PORT, ps);
        if (nonProxyHosts == null || nonProxyHosts.size() == 0) {
            sp.remove(PROP_NON_PROXY);
        } else {
            StringBuilder sb = new StringBuilder();
            int len = nonProxyHosts.size();
            for (int i = 0; i < len; i++) {
                String nph = StringUtil.trim(nonProxyHosts.get(i));
                if (nph != null) {
                    if (sb.length() > 0) {
                        sb.append('|');
                    }
                    sb.append(nph);
                }
            }
            sp.setProperty(PROP_NON_PROXY, sb.toString());
        }
    }
    /**
     * Install an all-trusting X.509 certificate trust manager, so that
     * successful SSL connections can be made to self-signed SSL sites
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static void openSSL() throws NoSuchAlgorithmException,KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType)
                    throws CertificateException {
            }
        }
        };
            
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());        
    }
    // code to load PEM encoded X.509 certificates, in case we want a trust chain
    // not used currently, so commented out
    /*
    private static void loadCertificates() throws IOException {
        CertificateFactory fac = CertificateFactory.getInstance("X.509");
        // self-signed cert for sf.bayareasoftware.com
        InputStream is = new FileInputStream("test/ssl/server.crt");
        X509Certificate cert = null;
        try {
            cert = (X509Certificate)fac.generateCertificate(is);
        } finally {
            is.close();
        }
        //p("generated cert: " + cert.getClass().getName() + " type=" + cert.getType());
        final X509Certificate baSoftCert = cert;        
    }*/
}
