package com.sun.demo.util;

import java.security.MessageDigest;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/17
 */
public class MD5Util {
    /**
     * MD5加密
     * @param message 需要加密的信息，例：123456
     * @return 返回MD5加密后的32位大写字符串，例：E10ADC3949BA59ABBE56E057F20F883E
     */
    public static String encode(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
        byte[] messageByte = message.getBytes("UTF-8");
        byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
        return bytesToHex(md5Byte);                            // 转换为16进制字符串
    }

    /***
     * 将字节数组转换成16进制字符串
     * @param bytes 需要转换的字节数组，例：[-31,10,-36,57,73,-70,89,-85,-66,86,-32,87,-14,15,-120,62]
     * @return 返回转换后的大写字符串，例：E10ADC3949BA59ABBE56E057F20F883E
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        int num;
        for (byte b : bytes) {
            num = b;
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(encode("000000"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
