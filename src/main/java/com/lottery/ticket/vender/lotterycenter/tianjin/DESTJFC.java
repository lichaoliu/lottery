package com.lottery.ticket.vender.lotterycenter.tianjin;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 福彩加密类--思乐系统
 * */
public class DESTJFC {

    /**
     * 加密类
     * @param msg 加密消息
     * @param key 秘钥
     * @throws Exception 
     * @throws  encrypt
     * */	
	public static  byte[] encrypt(String msg, String key) throws  Exception{
		SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec objDesKeySpec =  new DESKeySpec(key.getBytes("ASCII"));
		SecretKey secretKey=objKeyFactory.generateSecret(objDesKeySpec);
		Cipher cipher=Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(msg.getBytes());
	}
	
	/**
	 * 解密
	 * 
	 * @param bytemsg 二进制
	 * @param key 秘钥
	 * @return
	 * @throws Exception 

	 */
	public static String decrypt(byte[] bytemsg, String key) throws Exception{
		SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec objDesKeySpec =  new DESKeySpec(key.getBytes("ASCII"));
		SecretKey secretKey=objKeyFactory.generateSecret(objDesKeySpec);
		Cipher cipher=Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		return new String(cipher.doFinal(bytemsg), "utf-8");
		
	}
}
