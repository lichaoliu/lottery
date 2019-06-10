package com.lottery.common.util;

import com.lottery.common.contains.CharsetConstant;
import org.apache.commons.codec.binary.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by fengqinyun on 16/3/1.
 */
public class DesUtil {
    private final static String DES = "DES";
    /**
     * 加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception{
        byte[] bt=encrypt(data.getBytes(CharsetConstant.CHARSET_UTF8), key.getBytes());
        return new String(new org.apache.commons.codec.binary.Base64().encode(bt));
    }

    /**
     * 解密
     * @param data
     * @param key
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception{
        byte[] buf=new Base64().decodeBase64(data);
        byte[] bt=decrypt(buf,key.getBytes());
        return new String(bt);
    }


    /**
     * 根据键值进行加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
    /**
     * 根据键值进行解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
}
