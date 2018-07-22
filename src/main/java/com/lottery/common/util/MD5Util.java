package com.lottery.common.util;

import com.lottery.common.contains.CharsetConstant;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class MD5Util {

	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5(String sourceStr, String charset)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] strTemp;
		if (charset == null) {
			strTemp = sourceStr.getBytes();
		} else {
			strTemp = sourceStr.getBytes(charset);
		}

		MessageDigest mdTemp = MessageDigest.getInstance("MD5");
		mdTemp.update(strTemp);
		byte[] md = mdTemp.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>>
			// 为逻辑右移，将符号位一起右移

			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		return new String(str);
	}
	
	public static String getMD5(byte[] source) {
		String s = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是�?�� 128 位的长整数，
										// 用字节表示就�?16 个字�?
			char str[] = new char[16 * 2]; // 每个字节�?16 进制表示的话，使用两个字符，
											// �?��表示�?16 进制�?�� 32 个字�?
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第�?��字节�?��，对 MD5 的每�?��字节
											// 转换�?16 进制字符的转�?
				byte byte0 = tmp[i]; // 取第 i 个字�?
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中�?4 位的数字转换,
															// >>>
															// 为�?辑右移，将符号位�?��右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中�?4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符�?

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
	/**
	 * 新加密算法
	 * @param s
	 * @return
	 */
	public final static String getMD5ofStr2(String s){ 
		char hexDigits[] = { 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 
				'e', 'f'}; 
		try { 
			byte[] strTemp = s.getBytes(); 
			MessageDigest mdTemp = MessageDigest.getInstance("MD5"); 
			mdTemp.update(strTemp); 
			byte[] md = mdTemp.digest(); 
			int j = md.length; 
			char str[] = new char[j * 2]; 
			int k = 0; 
			for (int i = 0; i < j; i++) { 
				byte byte0 = md[i]; 
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				str[k++] = hexDigits[byte0 & 0xf]; 
			} 
			return new String(str); 
		} 
		catch (Exception e){ 
			return null; 
		} 
	}
	
	public static final String getPassWd(String pw){
		return getMD5ofStr2(getMD5ofStr2(pw).substring(0, 20) + "password").toLowerCase();
	}
	

	/**
	 * utf-8的加密
	 * @param str 加密内容
	 * */
	public static String toMd5(String str)throws Exception {
       return getMD5(str, CharsetConstant.CHARSET_UTF8);
     
	}
	
	public static String MD5(String content) throws UnsupportedEncodingException{
		MD5 md5 = new MD5();
		md5.update(content,  "UTF-8");
		return md5.getHashString();
	}
	public static String encrypt(String x) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(x.getBytes("UTF8"));
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
						.substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *  
	 * @param str 加密内容,charset 加密字符编码
	 * */
	public static String toMd5(String str,String charset)throws Exception{
		return getMD5(str, charset);
	     
		}


	public static String commonsMd5(String content){
		return DigestUtils.md5Hex(content);
	}
}
