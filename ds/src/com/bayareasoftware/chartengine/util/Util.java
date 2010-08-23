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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;

import com.bayareasoftware.chartengine.model.SimpleProps;

/**
 * collection of little static utility methods
 */
public class Util {
    
    // given a JSON string, pretty print it with indentation
    // does the naive thing, may get confused if string literals contain brace or comma
   public static String prettyPrintJSON(String json) {
       StringBuilder sb = new StringBuilder();
       int indent = 0;
       
       for (int i=0; i < json.length(); i++) {
           char c = json.charAt(i);
           sb.append(c);
           if (c == '{') 
               indent += 4;
           if (c == '}')
               indent -= 4;
           if (c == ',' || c == '{') {
               sb.append('\n');
               for (int j=0;j<indent;j++)
                   sb.append(' ');
           }
       }
       return sb.toString();
   }
   
   /**
    * uset this chartset while compressing/uncompressing strings
    */
   private static String DEFAULT_CHARSET = "UTF-8";
   
   /**
    * uncompress a string that was compressed with ZLib compression (Deflater)
    * @param s  - input must be a properly compressed string.  we do not detect otherwise
    * @return
    */
   public static String uncompressString(String s) {
       // Decompress the bytes
       try {
           Inflater decompresser = new Inflater();
           byte[] input;
           input = s.getBytes(DEFAULT_CHARSET);

           input = Base64.decodeBase64(input);

           decompresser.setInput(input);

           // Create an expandable byte array to hold the uncompressed data.
           // the uncompressed data.  Start with 5x the input length
           ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length*5);

           // uncompress the data
           byte[] buf = new byte[1024];
           while (!decompresser.finished()) {
               int count;
               try {
                   count = decompresser.inflate(buf);
               } catch (DataFormatException e) {
                   e.printStackTrace();
                   return null;
               }
               bos.write(buf, 0, count);
           }
           try {
               bos.close();
           } catch (IOException e) {
           }
           decompresser.end();

           // Get the compressed data
           byte[] uncompressedData = bos.toByteArray();


           // Decode the bytes into a String
           return new String(uncompressedData,DEFAULT_CHARSET);
       } catch (UnsupportedEncodingException e1) {
           e1.printStackTrace();
           return null;
       }
   }
   
   /**
    * compress a String using Deflater (ZLIB compression) and Base64encoding
    * @param s
    * @return
    */
   public static String compressString(String s){
       try {
           byte[] input = s.getBytes(DEFAULT_CHARSET);

           // Compress the bytes
           Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
           compresser.setInput(input);
           compresser.finish();


           // Create an expandable byte array to hold the compressed data.
           // You cannot use an array that's the same size as the orginal because
           // there is no guarantee that the compressed data will be smaller than
           // the uncompressed data.
           ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

           // Compress the data
           byte[] buf = new byte[1024];
           while (!compresser.finished()) {
               int count = compresser.deflate(buf);
               bos.write(buf, 0, count);
           }
           try {
               bos.close();
           } catch (IOException e) {
           }

           // Get the compressed data
           byte[] compressedData = bos.toByteArray();

           compressedData = Base64.encodeBase64(compressedData);

           if (compressedData.length < s.length()) {
               // only if we are actually saving space, do we compress
               return new String(compressedData,DEFAULT_CHARSET);
           } else {
               //System.err.println("Not compressing because compressed string is larger than original");
           }
           return s;
       } catch (UnsupportedEncodingException e1) {
           e1.printStackTrace();
           return null;
       }
   }
   
   // convert properties object to a flattened URL representation
   // if sorted is true, sort the keys
   public static String props2URL(SimpleProps p, boolean sorted) {
       StringBuilder sb = new StringBuilder();
       
       String[] keys = p.keySet().toArray(new String[(p.size())]);

       if (sorted) {
           Arrays.sort(keys);
       }
       for (int i=0;i<keys.length;i++) {
           if (i > 0)
               sb.append("&");
           try {
               sb.append(keys[i]).append("=").append(URLEncoder.encode(p.get(keys[i]),DEFAULT_CHARSET));
           } catch (UnsupportedEncodingException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }
       return sb.toString();
   }
}
