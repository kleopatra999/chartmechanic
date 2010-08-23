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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DemoUtil {
    public static void escapeHtmlStream(Reader in, Writer out) throws IOException {
        int i;
        // true if last char was blank
        boolean lastWasBlankChar = false;
        while ((i = in.read()) != -1) {
            char c = (char) i;
            if (c == ' ') {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss
                // word breaking
                if (lastWasBlankChar) {
                    lastWasBlankChar = false;
                    out.write("&nbsp;");
                } else {
                    lastWasBlankChar = true;
                    out.append(' ');
                }
            } else {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"') {
                    out.append("&quot;");
                } else if (c == '&') {
                    out.append("&amp;");
                } else if (c == '<') {
                    out.append("&lt;");
                } else if (c == '>') {
                    out.append("&gt;");
                } else if (c == '\n') {
                    // Handle Newline
                    out.append("<br>");
                } else {
                    int ci = 0xffff & c;
                    if (ci < 160) {
                        // nothing special only 7 Bit
                        out.append(c);
                    } else {
                        // Not 7 Bit use the unicode system
                        out.append("&#");
                        out.append(new Integer(ci).toString());
                        out.append(';');
                    }
                }
            }
        }
    }
    
    /**
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     * 
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException 
     * @throws IOException
     * 
     * Credit where it's due: http://www.uofr.net/~greg/java/get-resource-listing.html
     */
    public static String[] getResourceListing(Class clazz, String path)
            throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
            /*
             * In case of a jar file, we can't actually find a directory. Have
             * to assume the same jar as clazz.
             */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
            /* A JAR path */
            String jarPath = dirURL.getPath().substring(5,
                    dirURL.getPath().indexOf("!")); // strip out only the JAR
                                                    // file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            try {
                Enumeration<JarEntry> entries = jar.entries(); // gives ALL
                                                               // entries in jar
                Set<String> result = new HashSet<String>(); // avoid duplicates
                                                            // in case it is a
                                                            // subdirectory
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (name.startsWith(path)) { // filter according to the path
                        String entry = name.substring(path.length());
                        int checkSubdir = entry.indexOf("/");
                        if (checkSubdir >= 0) {
                            // if it is a subdirectory, we just return the
                            // directory name
                            entry = entry.substring(0, checkSubdir);
                        }
                        result.add(entry);
                    }
                }
                return result.toArray(new String[result.size()]);
            } finally {
                jar.close();
            }
        }
        throw new UnsupportedOperationException("Cannot list files for URL "
                + dirURL);
    }
    
    public static void getResources(String uri) throws Exception {
        Class c = DemoUtil.class;
        
    }
}
