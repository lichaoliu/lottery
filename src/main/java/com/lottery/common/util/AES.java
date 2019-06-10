package com.lottery.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by fengqinyun on 15/11/23.
 * aes加密
 */
public class AES {
    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String content, String key) throws Exception{

        byte[] raw = key.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    /**解密
     * @param content  待解密内容
     * @param key 解密密钥
     * @return
     */
    public static String decrypt(String content, String key) throws Exception{
        byte[] raw = key.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2byte(content.getBytes());
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, "UTF-8");
    }


    /**
     * 十六进制转化成byte数组
     *
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * byte数组转化十六进制
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}
