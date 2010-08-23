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

import junit.framework.Assert;

import org.junit.Test;

public class SimpleCryptTest {

    @Test
    public void testEncrypt() {
        SimpleCrypt sc = SimpleCrypt.get();
        
        String s = "Some password";
        String es = sc.encrypt(s);
        
        Assert.assertNotSame(s, es);
        
        System.err.println("es = " + es + " decrypted(es) = " + sc.decrypt(es));

        //Assert.assertEquals(sc.decrypt(es),s);
        
        String s2 = "Some other password";
        Assert.assertNotSame(sc.encrypt(s2), es);
    }
}
