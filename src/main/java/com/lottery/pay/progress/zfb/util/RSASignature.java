/**
 * 
 */
package com.lottery.pay.progress.zfb.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;

import com.lottery.common.contains.CharsetConstant;


public class RSASignature{
	

	// 交易安全检验码，由数字和字母组成的32位字符串
	public static String key = "bp3hw80t25zm4avmb4wn60a6nt0lkwau";
	/**
	* 解密
	* @param content 密文
	* 
	* @param key 商户私钥
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String key,String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(key);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);
        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;
        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }
        return new String(writer.toByteArray(), input_charset);
    }


	
	/**

	* 得到私钥

	* @param key 密钥字符串（经过base64编码）

	* @throws Exception

	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @return 签名值
	*/
	public static String sign(String content, String privateKey)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec(Base64.decode(privateKey)); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update( content.getBytes(CharsetConstant.CHARSET_UTF8) );
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 支付宝公钥
	* @return 布尔值
	*/
	public static boolean doCheck(String content, String sign, String publicKey)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update( content.getBytes(CharsetConstant.CHARSET_UTF8) );
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 将map转为string并排序
	 * @param params
	 * @return
	 */
	 public static String getSignData(Map<String, String> params) {
	        StringBuffer content = new StringBuffer();

	        // 按照key做排序
	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);

	        for (int i = 0; i < keys.size(); i++) {
	            String key = (String) keys.get(i);
	            if ("sign".equals(key)) {
	                continue;
	            }
	            String value = (String) params.get(key);
	            if (value != null) {
	                content.append((i == 0 ? "" : "&") + key + "=" + value);
	            } else {
	                content.append((i == 0 ? "" : "&") + key + "=");
	            }

	        }

	        return content.toString();
	    }

	 /**
	     * 将Map中的数据组装成url
	     * @param params
	     * @return
	     * @throws UnsupportedEncodingException 
	     */
	    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
	        StringBuilder sb = new StringBuilder();
	        boolean isFirst = true;
	        for (String key : params.keySet()) {
	            String value = params.get(key);
	            if (isFirst) {
	                sb.append(key + "=" + '"'+value+'"');
	                isFirst = false;
	            } else {
	                if (value != null) {
	                    sb.append("&" + key + "=" + '"'+value+'"');
	                } else {
	                    sb.append("&" + key + "=");
	                }
	            }
	        }
	        return sb.toString();
	    }
	    
		
	    
	    /**
	     * 生成签名结果
	     * @param sArray 要签名的数组
	     * @return 签名结果字符串
	     */
	    public static String buildMysign(Map<String, String> sArray,String key) {
	        String prestr = createLinkString(sArray); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	        prestr = prestr + key; //把拼接后的字符串再与安全校验码直接连接起来
	        String mysign = md5(prestr);
	        return mysign;
	    }
	    
	    /**
	     * 对字符串进行MD5签名
	     * 
	     * @param text
	     *            明文
	     * 
	     * @return 密文
	     */
	    public static String md5(String text) {
	        return DigestUtils.md5Hex(getContentBytes(text, CharsetConstant.CHARSET_UTF8));

	    }
	    
	    /**
	     * @param content
	     * @param charset
	     * @return
	     * @throws SignatureException
	     * @throws UnsupportedEncodingException 
	     */
	    private static byte[] getContentBytes(String content, String charset) {
	        if (charset == null || "".equals(charset)) {
	            return content.getBytes();
	        }

	        try {
	            return content.getBytes(charset);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
	        }
	    }
	    /** 
	     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param params 需要排序并参与字符拼接的参数组
	     * @return 拼接后字符串
	     */
	    public static String createLinkString(Map<String, String> params) {

	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);
	        String prestr = "";

	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = params.get(key);
	           
	            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
	                prestr = prestr + key + "=" + value;
	            } else {
	                prestr = prestr + key + "=" + value + "&";
	            }
	        }

	        return prestr;
	    }
	    
	    /** 
	     * 把数组所有元素按照固定参数排序，以“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param params 需要参与字符拼接的参数组
	     * @return 拼接后字符串
	     */
	    public static String createLinkStringNoSort(Map<String, String> params) {
	    	
	    	Map<String, String> sParaSort = new HashMap<String, String>();
	    	sParaSort.put("service", params.get("service"));
	    	sParaSort.put("v", params.get("v"));
	    	sParaSort.put("sec_id", params.get("sec_id"));
	    	sParaSort.put("notify_data", params.get("notify_data"));
	    	
	    	String prestr = "";
	    	 for (String key : sParaSort.keySet()) {
	    		 prestr = prestr + key + "=" + sParaSort.get(key) + "&";
	    	 }
	    	 prestr = prestr.substring(0,prestr.length()-1);
	    	 
	    	 return prestr;
	    }

	    
	    
	    /** 
	     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	     * @param params 需要排序并参与字符拼接的参数组
	     * @return 拼接后字符串
	     */
	    public static String createValueLinkString(Map<String, String> params) {

	        List<String> keys = new ArrayList<String>(params.keySet());
	        Collections.sort(keys);
	        String prestr = "";

	        for (int i = 0; i < keys.size(); i++) {
	            String key = keys.get(i);
	            String value = params.get(key);
	            prestr = prestr + value;
	        }

	        return prestr;
	    }

	    /** 
	     * 除去数组中的空值和签名参数
	     * @param sArray 签名参数组
	     * @return 去掉空值与签名参数后的新签名参数组
	     */
	    public static Map<String, String> paraFilter(Map<String, String> sArray) {
	        Map<String, String> result = new HashMap<String, String>();
	        if (sArray == null || sArray.size() <= 0) {
	            return result;
	        }
	        for (String key : sArray.keySet()) {
	            String value = sArray.get(key);
	            if (value == null || "".equals(value)|| key.equalsIgnoreCase("sign")
	                || key.equalsIgnoreCase("sign_type")||key.equalsIgnoreCase("type")) {
	                continue;
	            }
	            result.put(key, value);
	        }
	        return result;
	    }
	    /**
	     * 签名字符串
	     * @param text 需要签名的字符串
	     * @param key 密钥
	     * @param input_charset 编码格式
	     * @return 签名结果
	     */
	    public static String sign(String text, String key, String input_charset) {
	    	text = text + key;
	        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	    }
	    
}
