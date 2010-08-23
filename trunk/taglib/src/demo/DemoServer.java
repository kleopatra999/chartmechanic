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
package demo;

import java.net.URL;

import javax.naming.Context;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.xml.XmlConfiguration;

public class DemoServer {

    private final Server server;

    public DemoServer(int port) throws Exception {
        /*
         * See embedding jetty docs
         * http://docs.codehaus.org/display/JETTY/Embedding+Jetty
         */
        // directory of web application (exploded war file)
        final String WEBAPPDIR = "web/";
        final String ENV_FILE = WEBAPPDIR + "/WEB-INF/jetty-env.xml";
        server = new Server(port);

        final String CONTEXTPATH = "";

        URL warUrl = DemoServer.class.getClassLoader().getResource(WEBAPPDIR);
        if (warUrl == null) {
            throw new RuntimeException("cannot find webapp resource '"
                    + WEBAPPDIR + "'");
        }
        
        final String warUrlString = warUrl.toExternalForm();
        WebAppContext wac = new WebAppContext(warUrlString, CONTEXTPATH);
        server.setHandler(wac);
        // handle jetty-env.xml
        XmlConfiguration config;
        URL webInfUrl = DemoServer.class.getClassLoader().getResource(WEBAPPDIR + "WEB-INF/");
        URL envUrl = DemoServer.class.getClassLoader().getResource(ENV_FILE);
        if (envUrl == null) {
            envUrl = new URL(webInfUrl, "jetty-env.xml");
        }
        config = new XmlConfiguration(envUrl);
        config.configure(server);
  }
    
    public void start() throws Exception {
        server.start();
    }
    
    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws InterruptedException {
        server.join();
    }
    private static void error(String msg) {
        System.err.println(DemoServer.class.getSimpleName() + ": ERROR: " + msg);
        usage();
    }
    
    private static void p(String s) {
        System.err.println("[DemoServer] " + s);
    }
    private static void usage() {
        System.err.println("usage: java " + DemoServer.class.getName() +
                " <listen-port>");
        System.exit(1);
    }
    /*
java.naming.factory.url.pkgs=org.mortbay.naming
java.naming.factory.initial=org.mortbay.naming.InitialContextFactory
     * 
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("chartengine.allowFileUrls", "true");
        System.setProperty(
                Context.INITIAL_CONTEXT_FACTORY,
                "org.mortbay.naming.InitialContextFactory"
                );
        System.setProperty(
                Context.URL_PKG_PREFIXES,
                "org.mortbay.naming"
                );
        if (args.length != 1) {
            usage();
        }
        int port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            error("invalid port: '" + args[0] + "'");
            usage();
        }
        DemoServer jm = new DemoServer(port);
        jm.start();
        jm.join();
    }

}
