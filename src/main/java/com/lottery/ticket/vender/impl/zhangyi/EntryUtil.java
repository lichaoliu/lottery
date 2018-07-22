package com.lottery.ticket.vender.impl.zhangyi;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EntryUtil {
	  
	   private static char[] base64EncodeChars = new char[]{
	       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
	       'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
	       'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
	       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
	       'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
	       'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
	       'w', 'x', 'y', 'z', '0', '1', '2', '3',
	       '4', '5', '6', '7', '8', '9', '+', '/'};
	private static byte[] base64DecodeChars = new byte[]{
	       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	       52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
	       -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	       15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
	       -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	       41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};
	/**
	* 加密
	*
	* @param data 明文的字节数组
	* @return 密文字符串
	*/
	public static String encode(byte[] data) {
	   StringBuffer sb = new StringBuffer();
	   int len = data.length;
	   int i = 0;
	   int b1, b2, b3;
	   while (i < len) {
	       b1 = data[i++] & 0xff;
	       if (i == len) {
	           sb.append(base64EncodeChars[b1 >>> 2]);
	           sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
	           sb.append("==");
	           break;
	       }
	       b2 = data[i++] & 0xff;
	       if (i == len) {
	           sb.append(base64EncodeChars[b1 >>> 2]);
	           sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
	           sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
	           sb.append("=");
	           break;
	       }
	       b3 = data[i++] & 0xff;
	       sb.append(base64EncodeChars[b1 >>> 2]);
	       sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
	       sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
	       sb.append(base64EncodeChars[b3 & 0x3f]);
	   }
	   return sb.toString();
	}
	/**
	* 解密
	*
	* @param str 密文
	* @return 明文的字节数组
	* @throws UnsupportedEncodingException
	*/
	public static byte[] decode(String str,String key) throws UnsupportedEncodingException {
	   StringBuffer sb = new StringBuffer();
	   byte[] data = str.getBytes("US-ASCII");
	   int len = data.length;
	   int i = 0;
	   int b1, b2, b3, b4;
	   while (i < len) {
	       /* b1 */
	       do {
	           b1 = base64DecodeChars[data[i++]];
	       } while (i < len && b1 == -1);
	       if (b1 == -1) break;
	       /* b2 */
	       do {
	           b2 = base64DecodeChars
	                   [data[i++]];
	       } while (i < len && b2 == -1);
	       if (b2 == -1) break;
	       sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
	       /* b3 */
	       do {
	           b3 = data[i++];
	           if (b3 == 61) return sb.toString().getBytes("iso8859-1");
	           b3 = base64DecodeChars[b3];
	       } while (i < len && b3 == -1);
	       if (b3 == -1) break;
	       sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
	       /* b4 */
	       do {
	           b4 = data[i++];
	           if (b4 == 61) return sb.toString().getBytes("iso8859-1");
	           b4 = base64DecodeChars[b4];
	       } while (i < len && b4 == -1);
	       if (b4 == -1) break;
	       sb.append((char) (((b3 & 0x03) << 6) | b4));
	   }
	   return sb.toString().getBytes("iso8859-1");
	} 
	  
	    public static byte[] desEncrypt(byte[] plainText,String dkey) throws Exception {  
	        SecureRandom sr = new SecureRandom();  
	        byte rawKeyData[] = dkey.getBytes();  
	        DESKeySpec dks = new DESKeySpec(rawKeyData);  
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	        SecretKey key = keyFactory.generateSecret(dks);  
	        Cipher cipher = Cipher.getInstance("DES");  
	        cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
	        byte data[] = plainText;  
	        byte encryptedData[] = cipher.doFinal(data);  
	        return encryptedData;  
	    }  
	  
	    public static byte[] desDecrypt(byte[] encryptText,String desKey) throws Exception {  
	        SecureRandom sr = new SecureRandom();  
	        byte rawKeyData[] = desKey.getBytes();  
	        DESKeySpec dks = new DESKeySpec(rawKeyData);  
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	        SecretKey key = keyFactory.generateSecret(dks);  
	        Cipher cipher = Cipher.getInstance("DES");  
	        cipher.init(Cipher.DECRYPT_MODE, key, sr);  
	        byte encryptedData[] = encryptText;  
	        byte decryptedData[] = cipher.doFinal(encryptedData);  
	        return decryptedData;  
	    }  
	  
	    public static String encrypt(String input,String key) throws Exception {  
	        return encode(desEncrypt(input.getBytes(),key));  
	    }  
	  
	    public static String decrypt(String input,String key) throws Exception {  
	        byte[] result = decode(input,key);  
	        return new String(desDecrypt(result,key));  
	    }  
}
