package com.lottery.pay.progress.yeebaowap.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;





public class PaymobileUtils {
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
	
	//编码格式UTF-8
	public static final String CHARSET 			= "UTF-8";
	
	
	//使用易宝公钥将AESKey加密生成encryptkey
	public static String buildEncryptkeyValue(String AESKey, String publicKey) {
		String encryptkey	= "";
		try {
			encryptkey = RSA.encrypt(AESKey, publicKey);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return encryptkey;
	}
	
	//使用易宝公钥将AESKey加密生成encryptkey
	public static String buildEncyptkey(String AESKey,String yeepayPublicKey) {
		return buildEncryptkeyValue(AESKey, yeepayPublicKey);
	}
	
	//生成RSA签名：sign
	public static String buildSignValue(TreeMap<String, Object> treeMap,String privateKey) {
		String sign				= "";
		StringBuffer buffer		= new StringBuffer();
		for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
			buffer.append(entry.getValue());
		String signTemp = buffer.toString();
		if (StringUtils.isNotEmpty(privateKey)) {
			sign = RSA.sign(signTemp, privateKey);
		}
	  }
		return sign;
	}
	
	//使用商户私钥生成RSA签名：sign
	public static String buildSign(TreeMap<String, Object> treeMap,String merchantPrivateKey) {
		return buildSignValue(treeMap, merchantPrivateKey);
	}

	//生成密文：data
	public static String buildData(TreeMap<String, Object> treeMap, String AESKey,String merchantaccount,String merchantPrivateKey) {
		String data 		= "";
		//将商户编号放入treeMap
		treeMap.put("merchantaccount", merchantaccount);
		//生成sign，并将其放入treeMap
		String sign			= buildSign(treeMap,merchantPrivateKey);
		treeMap.put("sign", sign);
		String jsonStr		= JSONObject.fromObject(treeMap).toString();
		data				= AES.encryptToBase64(jsonStr, AESKey);
		return data;
	}
	
	
	
	//解密data，获得明文参数
	public static JSONObject decryptValue(String data, String encryptkey, String privateKey) {
		TreeMap<String, String> result	= null;
		//1.使用商户密钥解密encryptKey。
		String AESKey 	= "";
		try {
			AESKey = RSA.decrypt(encryptkey, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		//2.使用AESKey解开data，取得明文参数；解密后格式为json
		String jsonStr	= AES.decryptFromBase64(data, AESKey);
		
		//3.将JSON格式数据转换成TreeMap格式
		JSONObject		jsObject	=JSONObject.fromObject(jsonStr);
		return jsObject;
	}
	
	//解密data，获得明文参数
	public static JSONObject decrypt(String data, String encryptkey,String merchantPrivateKey) {
		return decryptValue(data, encryptkey, merchantPrivateKey);
	}
	//sign验签
	public static boolean checkSignValue(String params, String signYeepay,String yeepayPublicKey) {
		return RSA.checkSignValue(params, signYeepay, yeepayPublicKey);
	}
	
	// sign验签
	public static boolean checkSign(TreeMap<String, String> dataMap,String yeepayPublicKey) {
		//获取明文参数中的sign。
		String signYeepay	= StringUtils.trimToEmpty(dataMap.get("sign"));
		//将明文参数中sign之外的其他参数，拼接成字符串
		StringBuffer buffer	= new StringBuffer();
		for(Entry<String, String> entry : dataMap.entrySet()) {
			String key		= formatStr(entry.getKey());
			String value	= formatStr(entry.getValue());
			if("sign".equals(key)) {
				continue;
			}
			buffer.append(value);
		}
		//result为true时表明验签通过
		boolean result = checkSignValue(buffer.toString(), signYeepay, yeepayPublicKey);
		return result;
	}

	//get请求
	public static Map<String, String> httpGet(String url, String merchantaccount, String data, String encryptkey) {
		
		Map<String, String> result		= null;
		//请求参数为如下三者：merchantaccount、data、enrcyptkey
		Map<String, String> paramMap	= new HashMap<String, String>();
		paramMap.put("data", data);
		paramMap.put("encryptkey", encryptkey);
		paramMap.put("merchantaccount", merchantaccount);
		String responseBody	= HttpClient4Utils.sendHttpRequest(url, paramMap, CHARSET, false);
		JSONObject  jasonObject = JSONObject.fromObject(responseBody);
		result = (Map)jasonObject;
		return result;
	}
	//字符串格式化
	public static String formatStr(String text) {
		return (text == null) ? "" : text.trim();
	}
	private static Random random = new Random();
	//生成AESKey: 16位的随机串
	public static String buildAESKey() {
		int length=16;
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}
}
