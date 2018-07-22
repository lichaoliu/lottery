package com.lottery.ticket.vender.impl.jydp;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolsAesCrypt {

	private static Logger logger = LoggerFactory.getLogger(ToolsAesCrypt.class);
	
	/**
	 * 解密算法
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc.getBytes());
			try {
				byte[] original = cipher.doFinal(encrypted1);
				return new String(original, "UTF-8");
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] decryptkjava(byte[] bytes) {
		int len = bytes.length;
		for (int i = 0; i < len; i++) {
			bytes[i] = (byte) (bytes[i] ^ 0x6e);
		}
		return bytes;
	}

	/**
	 * 加密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		byte[] raw = sKey.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		return byte2hex(encrypted).toLowerCase();
	}

	/**
	 * 加密
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] ecryptkjava(byte[] bytes) {
		int len = bytes.length;
		for (int i = 0; i < len; i++) {
			bytes[i] = (byte) (bytes[i] ^ 0x6e);
		}
		return bytes;
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

	public static byte[] encrypt2Bytes(byte[] src, String sKey) {
		try {
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(src);
			return encrypted;
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		}
	}

	public static byte[] decrypt2Bytes(byte[] src, String sKey)
			throws Exception {
		try {
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = src;
			try {
				byte[] original = cipher.doFinal(encrypted1);
				return original;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String src = "hello 张";
		String key = "0123456789ASDFGH";
		
		String encode = Encrypt(src, key);
		System.out.println(encode);
		
		System.out.println(Decrypt(encode, key));
	}
	
}
