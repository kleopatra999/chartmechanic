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
/* <!-- in case someone opens this in a browser... --> <pre> */
/*
 * This is a simple multi-threaded Java proxy server
 * for HTTP requests (HTTPS doesn't seem to work, because
 * the CONNECT requests aren't always handled properly).
 * I implemented the class as a thread so you can call it 
 * from other programs and kill it, if necessary (by using 
 * the closeSocket() method).
 *
 * We'll call this the 1.1 version of this class. All I 
 * changed was to separate the HTTP header elements with
 * \r\n instead of just \n, to comply with the official
 * HTTP specification.
 *  
 * This can be used either as a direct proxy to other
 * servers, or as a forwarding proxy to another proxy
 * server. This makes it useful if you want to monitor
 * traffic going to and from a proxy server (for example,
 * you can run this on your local machine and set the
 * fwdServer and fwdPort to a real proxy server, and then
 * tell your browser to use "localhost" as the proxy, and
 * you can watch the browser traffic going in and out).
 *
 * One limitation of this implementation is that it doesn't 
 * close the ProxyThread socket if the client disconnects
 * or the server never responds, so you could end up with
 * a bunch of loose threads running amuck and waiting for
 * connections. As a band-aid, you can set the server socket
 * to timeout after a certain amount of time (use the
 * setTimeout() method in the ProxyThread class), although
 * this can cause false timeouts if a remote server is simply
 * slow to respond.
 *
 * Another thing is that it doesn't limit the number of
 * socket threads it will create, so if you use this on a
 * really busy machine that processed a bunch of requests,
 * you may have problems. You should use thread pools if
 * you're going to try something like this in a "real"
 * application.
 *
 * Note that if you're using the "main" method to run this
 * by itself and you don't need the debug output, it will
 * run a bit faster if you pipe the std output to 'nul'.
 *
 * You may use this code as you wish, just don't pretend 
 * that you wrote it yourself, and don't hold me liable for 
 * anything that it does or doesn't do. If you're feeling 
 * especially honest, please include a link to nsftools.com
 * along with the code. Thanks, and good luck.
 *
 * Julian Robichaux -- http://www.nsftools.com
 */
package com.bayareasoftware.chartengine.ds.util;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;

import com.bayareasoftware.chartengine.model.StringUtil;

public class WebProxy extends Thread {
    public static final int DEFAULT_PORT = 8080;

    private ServerSocket server = null;
    private int thisPort = DEFAULT_PORT;
    private String fwdServer = "";
    private int fwdPort = 0;
    private int ptTimeout = ProxyThread.DEFAULT_TIMEOUT;
    private int debugLevel = 0;
    private PrintStream debugOut = System.out;
    private List<ProxyRecord> records = new ArrayList<ProxyRecord>();

    private static void p(String s) {
        System.out.println("[WebProxy] " + s);
    }
    /*static class MyProxySelector extends ProxySelector {

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
    private static void failureTest() throws Exception {
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "2001");
        ProxySelector psel = ProxySelector.getDefault();
        p("default proxy is " + psel + "/" + psel.getClass().getName());
        ProxySelector.setDefault(new MyProxySelector(psel));
        URL u = new URL("http://chartmechanic.com/");
        InputStream is = u.openStream();
        int r;
        int nread = 0;
        while ((r = is.read()) != -1) {
            //System.out.write(r);
            nread++;
        }
        is.close();
        System.out.flush();
        p("read " + nread + " bytes...");
    }*/
    /* here's a main method, in case you want to run this by itself */
    public static void main(String args[]) throws Exception {
        //failureTest(); if (true) return;
        int port = 0;
        String fwdProxyServer = "";
        int fwdProxyPort = 0;

        if (args.length == 0) {
            System.err
                    .println("USAGE: java WebProxy <port number> [<fwd proxy> <fwd port>]");
            System.err
                    .println("  <port number>   the port this service listens on");
            System.err
                    .println("  <fwd proxy>     optional proxy server to forward requests to");
            System.err
                    .println("  <fwd port>      the port that the optional proxy server is on");
            System.err
                    .println("\nHINT: if you don't want to see all the debug information flying by,");
            System.err
                    .println("you can pipe the output to a file or to 'nul' using \">\". For example:");
            System.err
                    .println("  to send output to the file prox.txt: java WebProxy 8080 > prox.txt");
            System.err
                    .println("  to make the output go away: java WebProxy 8080 > nul");
            return;
        }

        // get the command-line parameters
        port = Integer.parseInt(args[0]);
        if (args.length > 2) {
            fwdProxyServer = args[1];
            fwdProxyPort = Integer.parseInt(args[2]);
        }

        // create and start the WebProxy thread, using a 20 second timeout
        // value to keep the threads from piling up too much
        System.err.println("  **  Starting WebProxy on port " + port
                + ". Press CTRL-C to end.  **\n");
        WebProxy jp = new WebProxy(port, fwdProxyServer, fwdProxyPort, 20);
        jp.setDebug(0, System.out); // or set the debug level to 2 for tons of
                                    // output
        //jp.start();
        jp.run(); // run in main thread

        // run forever; if you were calling this class from another
        // program and you wanted to stop the WebProxy thread at some
        // point, you could write a loop that waits for a certain
        // condition and then calls WebProxy.closeSocket() to kill
        // the running WebProxy thread
        /*
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
        }*/

        // if we ever had a condition that stopped the loop above,
        // we'd want to do this to kill the running thread
        // jp.closeSocket();
        // return;
    }

    /*
     * the proxy server just listens for connections and creates a new thread
     * for each connection attempt (the ProxyThread class really does all the
     * work)
     */
    public WebProxy(int port) {
        this(port,null,-1);
    }

    public WebProxy(int port, String proxyServer, int proxyPort) {
        /*
        thisPort = port;
        fwdServer = proxyServer;
        fwdPort = proxyPort;
        */
        this(port,proxyServer,proxyPort,ProxyThread.DEFAULT_TIMEOUT);
    }

    public WebProxy(int port, String proxyServer, int proxyPort, int timeout) {
        this.setDaemon(true);
        thisPort = port;
        fwdServer = proxyServer;
        fwdPort = proxyPort;
        ptTimeout = timeout;
    }

    /*
     * allow the user to decide whether or not to send debug output to the
     * console or some other PrintStream
     */
    public void setDebug(int level, PrintStream out) {
        debugLevel = level;
        debugOut = out;
    }

    /*
     * get the port that we're supposed to be listening on
     */
    public int getPort() {
        return thisPort;
    }

    /*
     * return whether or not the socket is currently open
     */
    public boolean isRunning() {
        if (server == null)
            return false;
        else
            return true;
    }

    public List<ProxyRecord> getRecords() { return records; }
    public void clearRecords() {
        records.clear();
    }
    private static final DateFormat fmt = new SimpleDateFormat("HH:mm:ss");
    private synchronized void addRecord(ProxyRecord record) {
        this.records.add(record);
        System.out.println("[Proxy] " + fmt.format(new Date()) + " " + record);
        //printRecords();
    }
    public void printRecords() {
        for (int i = 0; i < records.size(); i++) {
            ProxyRecord r = records.get(i);
            System.out.println("#" + i + ") " + r);
        }
        System.out.println("-----------------------------------");
    }
    /*
     * closeSocket will close the open ServerSocket; use this to halt a running
     * WebProxy thread
     */
    public void closeSocket() {
        try {
            // close the open server socket
            if (server != null) {
                server.close();
            }
            // send it a message to make it stop waiting immediately
            // (not really necessary)
            /*
             * Socket s = new Socket("localhost", thisPort); OutputStream os =
             * s.getOutputStream(); os.write((byte)0); os.close(); s.close();
             */
        } catch (Exception e) {
            if (debugLevel > 0) {
                debugOut.println(e);
                e.printStackTrace(debugOut);
            }
        }

        server = null;
    }

    public void run() {
        try {
            // create a server socket, and loop forever listening for
            // client connections
            server = new ServerSocket(thisPort);
            thisPort = server.getLocalPort();
            if (debugLevel > 0) {
                debugOut.println("Started WebProxy on port " + thisPort);
            }
            debugOut.println("debug level=" + debugLevel);
            while (true) {
                Socket client = server.accept();
                ProxyThread t = new ProxyThread(client, fwdServer, fwdPort);
                t.setDebug(debugLevel, debugOut);
                t.setTimeout(ptTimeout);
                t.start();
            }
        } catch (Exception e) {
            if (debugLevel > 0) {
                String msg = e.getMessage();
                if (msg == null || msg.indexOf("closed") == -1) {
                    debugOut.println("WebProxy Thread error: " + e);
                    e.printStackTrace(debugOut);
                }
            }
            
        }

        closeSocket();
    }

    public static class ProxyRecord {
        public String firstLine;
        public int responseLength = -1;
        public long time, duration;
        ProxyRecord(String fl) {
            firstLine = fl;
            time = System.currentTimeMillis();
        }
        public String toString() {
            return "[ProxyRecord response length="
            + responseLength + " duration=" + duration + " ms."
            + "\"" + firstLine + "\"]";
        }
        public void finish(int rspLen) {
            responseLength = rspLen;
            duration = System.currentTimeMillis() - time;
            
        }
    }
    private static int DEFAULT_HTTP_PORT = 80;
    private static int DEFAULT_HTTPS_PORT = 443;
    private class Headers {
        public String firstLine;
        public String method, host, protocol, file;
        public int port;
        public Map<String,String> map = new HashMap<String,String>();
        
        Headers(String msg) {
            parse(msg);
        }
        Headers(byte[] buf, int len) {
            String msg = new String(buf,0,0,len);
            parse(msg);
        }

        void filterOut(String headerPrefix) {
            Map<String,String> newMap = new HashMap<String,String>();
            for (String key : map.keySet()) {
                if (!key.startsWith(headerPrefix)) {
                    newMap.put(key, map.get(key));
                }
            }
            map = newMap;
        }
        private void parse(String msg) {
            String[] lines = StringUtil.splitCompletely(msg, '\n', true);
            firstLine = lines[0];
            for (int i = 1; i < lines.length; i++) {
                String line = StringUtil.trim(lines[i]);
                if (line == null) {
                    continue;
                }
                int ind = line.indexOf(':');
                if (ind > 0) {
                    String key = StringUtil.trim(line.substring(0,ind));
                    String val = StringUtil.trim(line.substring(ind+1));
                    map.put(key, val);
                }
            }
            String[] fields = StringUtil.splitCompletely(firstLine, ' ', true);
            if (fields.length != 3) {
                String errMsg = "ERROR: bad first line doesn't have 3 fields ##" + firstLine + "###";
                debugOut.println(errMsg);
                throw new IllegalArgumentException(errMsg);
            }
            method = fields[0];
            host = fields[1];
            protocol = fields[2];
            if (host.startsWith("http://")) {
                host = host.substring(7);
            }
            port = -1;
            try {
                int ind = host.indexOf(':');
                int slashInd = host.indexOf('/');
                if (debugLevel > 0) {
                    debugOut.println("[WebProxy-Headers] parsing host string '" + host + "' index=" + ind);
                }
                if (ind > 0 && (slashInd == -1 || ind < slashInd)) {
                    if (slashInd == -1) {
                        port = Integer.parseInt(host.substring(ind + 1));
                    } else {
                        port = Integer.parseInt(host.substring(ind + 1, slashInd));
                        file = host.substring(slashInd);
                    }
                    host = host.substring(0,ind);
                } else if ("CONNECT".equalsIgnoreCase(method)) {
                    port = DEFAULT_HTTPS_PORT;
                } else {
                    port = DEFAULT_HTTP_PORT;
                    if (slashInd != -1) {
                        file = host.substring(slashInd);
                        host = host.substring(0, slashInd);
                    }
                }
            } catch (RuntimeException re) {
                re.printStackTrace();
                throw re;
            }
        }
        // FIXME: this doesn't handle post data, should not matter for
        // our testing purposes though
        public String toRequest() {
            StringBuilder sb = new StringBuilder();
            sb.append(method).append(' ').append(file).append(" HTTP/1.0\r\n");
            for (String key : map.keySet()) {
                String val = map.get(key);
                sb.append(key).append(": ").append(val).append("\r\n");
            }
            sb.append("\r\n");
            return sb.toString();
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[HEADERS \"").append(firstLine).append("\"\n");
            sb.append("METHOD=").append(method).append('\n')
            .append("HOST=").append(host).append('\n')
            .append("PORT=").append(port).append('\n')
            .append("FILE=").append(file).append('\n')
            .append("PROTOCOL=").append(protocol).append('\n');
            for (String key : map.keySet()) {
                String val = map.get(key);
                sb.append(" ").append(key).append('=').append(val).append('\n');
            }
            sb.append(']');
            return sb.toString();
        }
    }
    /*
     * The ProxyThread will take an HTTP request from the client socket and send it
     * to either the server that the client is trying to contact, or another proxy
     * server
     */
    class ProxyThread extends Thread {
        private Socket pSocket;
        private String fwdServer = "";
        private int fwdPort = 0;
        private int debugLevel = 0;
        private PrintStream debugOut = System.out;

        // the socketTimeout is used to time out the connection to
        // the remote server after a certain period of inactivity;
        // the value is in milliseconds -- use zero if you don't want
        // a timeout
        public static final int DEFAULT_TIMEOUT = 20 * 1000;
        private int socketTimeout = DEFAULT_TIMEOUT;

        public ProxyThread(Socket s) {
            //pSocket = s;
            this(s,null,-1);
        }

        public ProxyThread(Socket s, String proxy, int port) {
            this.setDaemon(true);
            pSocket = s;
            fwdServer = proxy;
            fwdPort = port;
        }

        public void setTimeout(int timeout) {
            // assume that the user will pass the timeout value
            // in seconds (because that's just more intuitive)
            socketTimeout = timeout * 1000;
        }

        public void setDebug(int level, PrintStream out) {
            debugLevel = level;
            debugOut = out;
        }

        public void run() {
            try {
                long startTime = System.currentTimeMillis();

                // client streams (make sure you're using streams that use
                // byte arrays, so things like GIF and JPEG files and file
                // downloads will transfer properly)
                BufferedInputStream clientIn = new BufferedInputStream(pSocket
                        .getInputStream());
                BufferedOutputStream clientOut = new BufferedOutputStream(pSocket
                        .getOutputStream());

                // the socket to the remote server
                Socket server = null;

                // headers of the request
                Headers rqHeaders;
                {
                    // other variables
                    byte[] request = null;
                    byte[] response = null;
                    int requestLength = 0;
                    int responseLength = 0;
                    int pos = -1;

                    // get the header info (the web browser won't disconnect after
                    // it's sent a request, so make sure the waitForDisconnect
                    // parameter is false)
                    request = getHTTPData(clientIn, false);
                    requestLength = Array.getLength(request);

                    rqHeaders = new Headers(request,requestLength);
                    if (debugLevel > 0) {
                        debugOut.println("[WebProxy] got request header ####" + rqHeaders + "####");
                    }
                }
                String hostName = rqHeaders.host;
                int hostPort = rqHeaders.port;
                // separate the host name from the host port, if necessary
                // (like if it's "servername:8000")


                // either forward this request to another proxy server or
                // send it straight to the Host
                try {
                    if (fwdServer != null && (fwdServer.length() > 0) && (fwdPort > 0)) {
                        server = new Socket(fwdServer, fwdPort);
                    } else {
                        server = new Socket(hostName, hostPort);
                    }
                } catch (Exception e) {
                    e.printStackTrace(debugOut);
                    // tell the client there was an error
                    String errMsg = "HTTP/1.0 500\nContent Type: text/plain\n\n"
                            + "Error connecting to the server:\n" + e + "\n";
                    clientOut.write(errMsg.getBytes(), 0, errMsg.length());
                    clientOut.flush();
                    close(this.pSocket);
                    return;
                }

                server.setSoTimeout(socketTimeout);
                BufferedInputStream serverIn = new BufferedInputStream(server
                        .getInputStream());
                BufferedOutputStream serverOut = new BufferedOutputStream(
                        server.getOutputStream());
                ProxyRecord record = new ProxyRecord(rqHeaders.firstLine);
                addRecord(record);
                
                if ("connect".equalsIgnoreCase(rqHeaders.method)) {
                    // ssl proxy
                    DumbPipe dp1 = new DumbPipe(clientIn, serverOut, server, null);
                    Thread t = new Thread(dp1, rqHeaders.host + "-runner-1");
                    t.setDaemon(true);
                    t.start();
                    DumbPipe dp2 = new DumbPipe(serverIn, clientOut, null, record);
                    t = new Thread(dp2, rqHeaders.host + "-runner-2");
                    t.setDaemon(true);
                    t.start();
                    String proxyResponse = "HTTP/1.0 200 Connection Established\r\n"
                            + "Proxy-Agent: Quick-Hack 0.1\r\n\r\n";
                    clientOut.write(proxyResponse.getBytes());
                    clientOut.flush();
                    return;
                }
                rqHeaders.filterOut("Proxy-");
                rqHeaders.map.put("Connection", "Close");
                String rqText = rqHeaders.toRequest();
                if (debugLevel > 0) {
                    debugOut.println("***[WebProxy] sending forward request ###" + rqText
                            + "###");
                }
                // send the request out
                serverOut.write(rqText.getBytes());
                serverOut.flush();

                // and get the response; if we're not at a debug level that
                // requires us to return the data in the response, just stream
                // it back to the client to save ourselves from having to
                // create and destroy an unnecessary byte array. Also, we
                // should set the waitForDisconnect parameter to' true',
                // because some servers (like Google) don't always set the
                // Content-Length header field, so we have to listen until
                // they decide to disconnect (or the connection times out).
                /*Headers rspHeaders;
                {
                    byte[] response = getHTTPData(serverIn, true);
                    int responseLength = Array.getLength(response);
                    rspHeaders = new Headers(response, responseLength);
                }
                */
                byte[] response = null;
                int responseLength;
                if (debugLevel > 1) {
                    response = getHTTPData(serverIn, true);
                    responseLength = Array.getLength(response);
                } else {
                    responseLength = streamHTTPData(serverIn, clientOut, true);
                }
                
                serverIn.close();
                serverOut.close();

                // send the response back to the client, if we haven't already
                if (debugLevel > 1)
                    clientOut.write(response, 0, responseLength);

                // if the user wants debug info, send them debug info; however,
                // keep in mind that because we're using threads, the output won't
                // necessarily be synchronous
                if (debugLevel > 0) {
                    long endTime = System.currentTimeMillis();
                    debugOut.println("Request from "
                            + pSocket.getInetAddress().getHostAddress()
                            + " on Port " + pSocket.getLocalPort() + " to host "
                            + hostName + ":" + hostPort + "\n"
                            + " " + responseLength
                            + " bytes returned, "
                            + Long.toString(endTime - startTime) + " ms elapsed)");
                    debugOut.flush();
                }
                if (debugLevel > 1) {
                    debugOut.println("REQUEST:\n" + rqHeaders.toString());
                    debugOut.println("RESPONSE:\n" + (new String(response)));
                    debugOut.flush();
                }
                record.finish(responseLength);
                // close all the client streams so we can listen again
                clientOut.close();
                clientIn.close();
                pSocket.close();
            } catch (Exception e) {
                if (debugLevel > 0) {
                    debugOut.println("Error in ProxyThread: " + e);
                    e.printStackTrace(debugOut);
                }
                // e.printStackTrace();
            }

        }

        private byte[] getHTTPData(InputStream in, boolean waitForDisconnect) {
            // get the HTTP data from an InputStream, and return it as
            // a byte array, and also return the Host entry in the header,
            // if it's specified -- note that we have to use a StringBuffer
            // for the' host' variable, because a String won't return any
            // information when it's used as a parameter like that
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            streamHTTPData(in, bs, waitForDisconnect);
            return bs.toByteArray();
        }

        private int streamHTTPData(InputStream in, OutputStream out,
                boolean waitForDisconnect) {
            // get the HTTP data from an InputStream, and send it to
            // the designated OutputStream
            StringBuffer header = new StringBuffer("");
            String data = "";
            int responseCode = 200;
            int contentLength = 0;
            int pos = -1;
            int byteCount = 0;

            try {
                // get the first line of the header, so we know the response code
                data = readLine(in);
                if (data != null) {
                    header.append(data + "\r\n");
                    pos = data.indexOf(" ");
                    if ((data.toLowerCase().startsWith("http")) && (pos >= 0)
                            && (data.indexOf(" ", pos + 1) >= 0)) {
                        String rcString = data.substring(pos + 1, data.indexOf(" ",
                                pos + 1));
                        try {
                            responseCode = Integer.parseInt(rcString);
                        } catch (Exception e) {
                            if (debugLevel > 0)
                                debugOut.println("Error parsing response code "
                                        + rcString);
                        }
                    }
                }

                // get the rest of the header info
                while ((data = readLine(in)) != null) {
                    // the header ends at the first blank line
                    if (data.length() == 0)
                        break;
                    header.append(data + "\r\n");

                    // check for the Content-Length header
                    pos = data.toLowerCase().indexOf("content-length:");
                    if (pos >= 0)
                        contentLength = Integer.parseInt(data.substring(pos + 15)
                                .trim());
                }

                // add a blank line to terminate the header info
                header.append("\r\n");

                // convert the header to a byte array, and write it to our stream
                out.write(header.toString().getBytes(), 0, header.length());

                // if the header indicated that this was not a 200 response,
                // just return what we've got if there is no Content-Length,
                // because we may not be getting anything else
                if ((responseCode != 200) && (contentLength == 0)) {
                    out.flush();
                    return header.length();
                }

                // get the body, if any; we try to use the Content-Length header to
                // determine how much data we're supposed to be getting, because
                // sometimes the client/server won't disconnect after sending us
                // information...
                if (contentLength > 0)
                    waitForDisconnect = false;

                if ((contentLength > 0) || (waitForDisconnect)) {
                    try {
                        byte[] buf = new byte[4096];
                        int bytesIn = 0;
                        while (((byteCount < contentLength) || (waitForDisconnect))
                                && ((bytesIn = in.read(buf)) >= 0)) {
                            out.write(buf, 0, bytesIn);
                            byteCount += bytesIn;
                        }
                    } catch (Exception e) {
                        String errMsg = "Error getting HTTP body: " + e;
                        if (debugLevel > 0) {
                            debugOut.println(errMsg);
                            e.printStackTrace(debugOut);
                        }
                        // bs.write(errMsg.getBytes(), 0, errMsg.length());
                    }
                }
            } catch (Exception e) {
                if (debugLevel > 0) {
                    debugOut.println("Error getting HTTP data: " + e);
                    e.printStackTrace(debugOut);
                }
            }

            // flush the OutputStream and return
            try {
                out.flush();
            } catch (Exception e) {
            }
            return (header.length() + byteCount);
        }

        private String readLine(InputStream in) {
            // reads a line of text from an InputStream
            StringBuffer data = new StringBuffer("");
            int c;

            try {
                // if we have nothing to read, just return null
                in.mark(1);
                if (in.read() == -1)
                    return null;
                else
                    in.reset();

                while ((c = in.read()) >= 0) {
                    // check for an end-of-line character
                    if ((c == 0) || (c == 10) || (c == 13))
                        break;
                    else
                        data.append((char) c);
                }

                // deal with the case where the end-of-line terminator is \r\n
                if (c == 13) {
                    in.mark(1);
                    if (in.read() != 10)
                        in.reset();
                }
            } catch (Exception e) {
                if (debugLevel > 0) {
                    debugOut.println("Error getting header: " + e);
                    e.printStackTrace(debugOut);
                }
            }

            // and return what we have
            return data.toString();
        }

    }
    private static void close(Object o) {
        if (o == null) { return; }
        try {
            if (o instanceof InputStream) {
                ((InputStream)o).close();
            }
            if (o instanceof OutputStream) {
                ((OutputStream)o).close();
            }
            if (o instanceof Socket) {
                ((Socket)o).close();
            }
        } catch (Exception ignore) {
            
        }
    }
    private class DumbPipe implements Runnable {
        private InputStream is;
        private OutputStream os;
        private Socket sock;
        private ProxyRecord record;
        DumbPipe(InputStream is, OutputStream os, Socket s, ProxyRecord rec) {
            this.is = is;
            this.os = os;
            this.sock = s;
            this.record = rec;
        }
        public void run() {
            try {
                run0();
            } catch (IOException ignore) {
                String msg = ignore.getMessage();
                if (msg == null) msg = "";
                msg = msg.toLowerCase();
                if (msg.indexOf("closed") == -1 && msg.indexOf("timed out") == -1) {
                    ignore.printStackTrace(debugOut);
                }
            } catch (Exception e) {
                e.printStackTrace(debugOut);
            } finally {
                close(sock);
                close(is);
                close(os);
            }
        }
        void run0() throws IOException {
            if (record != null) {
                addRecord(record);
            }
            byte[] buf = new byte[2048];
            int r, nread = 0;
            try {
            while ((r = is.read(buf)) > 0) {
                os.write(buf,0,r);
                nread += r;
                os.flush();
            }
            } finally {
                if (record != null) {
                    record.finish(nread);
                }
            }
        }
    }
}


