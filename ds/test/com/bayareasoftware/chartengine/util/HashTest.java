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

import static junit.framework.Assert.*;
import org.junit.Test;

import com.bayareasoftware.chartengine.util.Hash;

import java.nio.ByteBuffer;

public class HashTest {
    private static final byte[] BYTES = new byte[512];
    private static final String STRING = "Hello, world.";
    private static final short SHORT = 0x1234;
    private static final int INT = 0x12345678;
    private static final long LONG = 0x1234567890123456L;
    private static final float FLOAT = 1.2345F;
    private static final double DOUBLE = 1.2344556677;

    static {
        for (int i = 0; i < BYTES.length; i++) {
            BYTES[i] = (byte) i;
        }
    }

    @Test
    public void testUpdateBytes() {
        Hash h1 = new Hash(BYTES);
        Hash h2 = new Hash();
        for (byte b : BYTES) {
            h2.update(b);
        }
        assertEquals(h1, h2);
        Hash h3 = new Hash(ByteBuffer.wrap(BYTES));
        assertEquals(h1, h3);
        Hash h4 = new Hash(BYTES).update(SHORT);
        assertNotSame(h1, h4);
    }

    @Test
    public void testUpdateString() {
        Hash h = new Hash();
        for (int i = 0; i < STRING.length(); i++) {
            h.update(STRING.charAt(i));
        }
        assertEquals(new Hash(STRING), h);
    }
    
    @Test
    public void testUpdateShort() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort(SHORT);
        bb.flip();
        assertEquals(new Hash(bb), new Hash(SHORT));
    }

    @Test
    public void testUpdateInt() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(INT);
        bb.flip();
        assertEquals(new Hash(bb), new Hash(INT));
    }

    @Test
    public void testUpdateLong() {
        ByteBuffer bb = ByteBuffer.allocate(8).putLong(LONG);
        bb.flip();
        assertEquals(new Hash(bb), new Hash(LONG));
    }

    @Test
    public void testUpdateFloat() {
        ByteBuffer bb = ByteBuffer.allocate(4).putFloat(FLOAT);
        bb.flip();
        assertEquals(new Hash(bb), new Hash(FLOAT));
    }

    @Test
    public void testUpdateDouble() {
        ByteBuffer bb = ByteBuffer.allocate(8).putDouble(DOUBLE);
        bb.flip();
        assertEquals(new Hash(bb), new Hash(DOUBLE));
    }

    @Test
    public void testToHexString() {
        // Make sure they are all hex digits
        String s1 = new Hash(BYTES).toHexString();
        for (char c : s1.toCharArray()) {
            assertNotSame(-1, Character.digit(c, 16));
        }
        // Make sure hash generates same length string
        String s2 = new Hash(SHORT).toHexString();
        assertEquals(s1.length(), s2.length());
    }


    @Test
    public void testReset() {
        Hash h = new Hash(STRING);
        h.update(SHORT);
        h.finish();
        try {
            h.update(SHORT);
            fail("Update should fail after hash finalized");
        } catch (IllegalStateException e) {
            // Fall through
        }
        h.reset();
        h.update(SHORT);
    }
}
