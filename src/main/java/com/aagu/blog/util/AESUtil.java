package com.aagu.blog.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private AESUtil() {}

    private static final String KEY_AES = "AES";

    public static String encrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不满足条件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted);
    }

    public static String decrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不满足条件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encrypted = hex2byte(src);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }

    private static String byte2hex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        String tmpStr;
        for (byte aByte : bytes) {
            tmpStr = Integer.toHexString(aByte & 0xFF);
            if (tmpStr.length() == 1) {
                hexStr.append("0").append(tmpStr);
            } else {
                hexStr.append(tmpStr);
            }
        }
        return hexStr.toString();
    }

    private static byte[] hex2byte(String hexStr) {
        if (hexStr == null) return null;
        int len = hexStr.length();
        if (len % 2 == 1) return null;
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i != len / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
}
