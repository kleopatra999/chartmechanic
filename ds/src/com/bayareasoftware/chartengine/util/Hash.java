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

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * MD5 Hashing utility that builds up a hash from a series of objects and base types fed into it.  
 * 
 * To use this class the Hash object and then call update() with various objects.  Finally use, getBytes() of toHexString() to get out the hash value  
 */
public final class Hash {
    private final MessageDigest md;
    private byte[] bytes;

    public Hash() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not found", e);
        }
    }

    public Hash(byte[] b)   { this(); update(b); }
    public Hash(byte[] b, int off, int len) { this(); update(b, off, len); }
    public Hash(ByteBuffer bb) { this(); update(bb); }
    public Hash(String s)   { this(); update(s); }
    public Hash(char c)     { this(); update(c); }
    public Hash(short s)    { this(); update(s); }
    public Hash(int i)      { this(); update(i); }
    public Hash(long l)     { this(); update(l); }
    public Hash(float f)    { this(); update(f); }
    public Hash(double d)   { this(); update(d); }

    public Hash update(byte b) {
        checkNotFinished();
        md.update(b);
        return this;
    }

    public Hash update(byte[] b) {
        return update(b, 0, b.length);
    }

    public Hash update(byte[] b, int off, int len) {
        checkNotFinished();
        md.update(b, off, len);
        return this;
    }

    public Hash update(ByteBuffer bb) {
        checkNotFinished();
        md.update(bb);
        return this;
    }

    public Hash update(String s) {
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                update(s.charAt(i));
            }
        }
        return this;
    }

    public Hash update(short s) {
        return update((byte) (s >> 8)).update((byte) s);
    }

    public Hash update(char c) {
        return update((short) c);
    }

    public Hash update(int i) {
        return update((short) (i >> 16)).update((short) i);
    }

    public Hash update(long l) {
        return update((int) (l >> 32)).update((int) l);
    }

    public Hash update(float f) {
        return update((Float.floatToRawIntBits(f)));
    }

    public Hash update(double d) {
        return update((Double.doubleToRawLongBits(d)));
    }
    
    public Hash update(Timestamp t) {
        // FIXME: is this unique enough?
        return update(t.getTime());
    }

    public Hash finish() {
        bytes = md.digest();
        return this;
    }

    public void reset() {
        bytes = null;
    }

    public byte[] getBytes() {
        ensureFinished();
        return bytes;
    }
    
    public String toHexString() {
        ensureFinished();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 8) & 0xf, 16));
            sb.append(Character.forDigit(b & 0xf, 16));
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Hash)) return false;
        Hash hash = (Hash) obj;
        return Arrays.equals(getBytes(), hash.getBytes());
    }

    public int hashCode() {
        byte[] b = getBytes();
        return (b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3];
    }

    public String toString() {
        return toHexString();
    }

    private void checkNotFinished() {
        if (bytes != null) {
            throw new IllegalStateException("Hash must be reset before resuse");
        }
    }
    
    private void ensureFinished() {
        if (bytes == null) finish();
    }
}
