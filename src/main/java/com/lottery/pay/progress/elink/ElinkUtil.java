package com.lottery.pay.progress.elink;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.CharsetConstant;



/**
 * 
 * @author Liuxy
 *
 */
public class ElinkUtil{
	/** = */
	public static final String QSTRING_EQUAL = "=";

	/** & */
	public static final String QSTRING_SPLIT = "&";
	
    /** 
     * 除去请求要素中的空值和签名参数
     * @param para 请求要素
     * @return 去掉空值与签名参数后的请求要素
     */
    public static Map<String, String> paraFilter(Map<String, String> para) {
        Map<String, String> result = new HashMap<String, String>();
        if (para == null || para.size() <= 0) {
            return result;
        }
        for (String key : para.keySet()) {
            String value = para.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("signature")
                || key.equalsIgnoreCase("signMethod")||key.equalsIgnoreCase("type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }


    /**
     * 把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param para 请求要素
     * @param sort 是否需要根据key值作升序排列
     * @param encode 是否需要URL编码
     * @return 拼接成的字符串
     */
    public static String createLinkString(Map<String, String> para, boolean sort, boolean encode) {
        List<String> keys = new ArrayList<String>(para.keySet());
        if (sort)
        	Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = para.get(key);
            if (encode) {
				try {
					value = URLEncoder.encode(value, CharsetConstant.CHARSET_UTF8);
				} catch (UnsupportedEncodingException e) {
				}
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                sb.append(key).append(QSTRING_EQUAL).append(value);
            } else {
                sb.append(key).append(QSTRING_EQUAL).append(value).append(QSTRING_SPLIT);
            }
        }
        return sb.toString();
    }

	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str 需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str) throws UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
			curChar = str.charAt(i);// 取当前字符
			if (curChar == '&') {// 如果读取到&分割符
				putKeyValueToMap(temp, isKey, key, map);
				temp.setLength(0);
				isKey = true;
			} else {
				if (isKey) {// 如果当前生成的是key
					if (curChar == '=') {// 如果读取到=分隔符
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {// 如果当前生成的是value
					temp.append(curChar);
				}
			}
		}
		putKeyValueToMap(temp, isKey, key, map);
		return map;
	}
	
	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map) throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, URLDecoder.decode(temp.toString(), CharsetConstant.CHARSET_UTF8));
		}
	}
    
	/**
	 * 交易接口处理
	 * @param req 请求要素
	 * @param resp 应答要素
	 * @return 封装数据
	 */
	public static String trade(Map<String, String> req,String secrectKey) {
		return buildReq(req,secrectKey);
	}
	

	
    /**
     * 拼接保留域
     * @param req 请求要素
     * @return 保留域
     */
    public static String buildReserved(Map<String, String> req) {
        StringBuilder merReserved = new StringBuilder();
        merReserved.append("{");
        merReserved.append(createLinkString(req, false, true));
        merReserved.append("}");
        return merReserved.toString();
    }
	
	/**
	 * 拼接请求字符串
	 * @param req 请求要素
	 * @return 请求字符串
	 */
	public static String buildReq(Map<String, String> req,String secrectKey) {
	    // 除去数组中的空值和签名参数
        Map<String, String> filteredReq = paraFilter(req);
		// 生成签名结果
		String signature = buildSignature(filteredReq,secrectKey);
		// 签名结果与签名方式加入请求提交参数组中
		filteredReq.put("signature", signature);
		filteredReq.put("signMethod","MD5");
		return createLinkString(filteredReq, false, true);
	}
	
	
	   /**
     * 生成签名
     * @param req 需要签名的要素
     * @return 签名结果字符串
     */
    public static String buildSignature(Map<String, String> req,String key) {
		String prestr = createLinkString(req, true, false);
		prestr = prestr + QSTRING_SPLIT + md5(key);
		return md5(prestr);
    }

	
	
	public static String md5(String str) {
		if (str == null) {
			return null;
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(CharsetConstant.CHARSET_UTF8));
		} catch (NoSuchAlgorithmException e) {
			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
}
	