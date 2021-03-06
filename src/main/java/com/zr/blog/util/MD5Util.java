package com.zr.blog.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String code(String str) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer stringBuffer = new StringBuffer("");
            for(int offset = 0; offset < byteDigest.length; offset ++) {
                i = byteDigest[offset];
                if(i < 0 ){
                    i += 256;
                }
                if(i < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i));
            }
            // 32bit 加密
            return stringBuffer.toString();
            // 16bit 加密
            // return stringBuffer.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.err.println( code("123456"));
    }
}
