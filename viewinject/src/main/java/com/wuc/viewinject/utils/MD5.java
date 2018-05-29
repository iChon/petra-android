package com.wuc.viewinject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',  '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    private static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MessageDigest初始化失败");
            e.printStackTrace();
        }
    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        byte[] digest = messagedigest.digest();
        return bufferToHex(digest, 0, digest.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuilder sb = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
            char c1 = hexDigits[bytes[l] & 0xf];
            sb.append(c0);
            sb.append(c1);
        }
        return sb.toString();
    }

}
