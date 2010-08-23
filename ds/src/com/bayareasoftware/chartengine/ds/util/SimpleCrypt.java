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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * a simple encryption/decryption of sensitive fields so they are not stored in plaintext
 * works simply by using a hardcoded password-based encryption scheme 
 * 
 */
public class SimpleCrypt {
    private static Log log = LogFactory.getLog(SimpleCrypt.class);
    
    // Salt
    private static byte[] salt = {
        (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
        (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
    };

    // Iteration count
    private static int count = 20;

    private static final char[] secret = {'g','u','m','b','y','1','2','3'};
    
    private static SimpleCrypt instance;

    private static Cipher pbeCipher, pbeDecipher;
    
    private SimpleCrypt() {
        PBEKeySpec pbeKeySpec;
        PBEParameterSpec pbeParamSpec;
        SecretKeyFactory keyFac;

        // Create PBE parameter set
        pbeParamSpec = new PBEParameterSpec(salt, count);

        pbeKeySpec = new PBEKeySpec(secret);

        try {
            keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
            SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

            // Create PBE Cipher
            pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
            pbeDecipher = Cipher.getInstance("PBEWithMD5AndDES");

            // Initialize PBE Cipher with key and parameters
            pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
            pbeDecipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
            
        } catch (Exception e) {
            log.error("failed to create cipher key");
        }
    }
    
    public static SimpleCrypt get() {
        if (instance == null) {
            instance = new SimpleCrypt();
        }
        return instance;
    }
    
    /**
     * take a value and encrypt it and Base64 encode it
     * @param s
     * @return
     */
    public String encrypt(String s) {
        String res = null;
        if (s != null) {
            byte[] b = s.getBytes();
            try {
                res = new String(Base64.encodeBase64(pbeCipher.doFinal(b)));
            } catch (Exception e) {
                log.error("Error encrypting: " + s);
            }
        }
        return res;
    }
    
    /**
     * take a value, Base64 decode it and decrypt it
     */
    public String decrypt(String s) {
       String res = null;
       if (s != null) {
           byte[] b = Base64.decodeBase64(s.getBytes());
           try {
               byte[] out = pbeDecipher.doFinal(b);
               res = new String(out);
           } catch (Exception e) {
               log.error("Error decrypting: " + s);
           }
       }
       return res;
    }
}
