package com.wang.myandroid.utils;

/**
 * Created by xminnov on 15/12/24.
 */
public final class ByteUtils {

    public static boolean validateHexStr(String str) {
        str = str.replace(" ", "");
        if (str.length() % 2 == 1) return false;
        for (char c : str.toCharArray()) {
            if ((c < '0' || c > '9') && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) return false;
        }
        return true;
    }

    public static byte[] str2hex(String str) {
        str = str.replace(" ", "");
        byte[] bytes = new byte[str.length()/2];
        for (int i=0; i<bytes.length; i++) {
            bytes[i] = (byte)Integer.parseInt(str.substring(i*2, i*2+2), 16);
        }
        return bytes;
    }

    public static String hex2str(byte[] bytes, String padding) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b)).append(padding);
        }
        return builder.toString();
    }

    public static String hex2str(byte[] bytes) {
        return hex2str(bytes, " ");
    }

    public static String epcBytes2Hex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i ++) {
            sb.append(String.format("%02X", bytes[i]));
            if((i + 1) % 4 == 0){
                sb.append("  ");
            }
        }
        return sb.toString().trim();
    }
}
