package com.lottery.pay.progress.yeebao.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lottery.pay.IPayConfig;



public class DigestUtil {

	private static String encodingCharset = "UTF-8";
	
	/**
	 * @param aValue
	 * @param aKey
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}
	  public static String mapToUrl(String totalFee,String outTradeNo,IPayConfig  config,String cardType) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("Buy");
	        sb.append(config.getPartner());
	        sb.append(outTradeNo);          // 商户订单号
	        sb.append(totalFee);             // 支付金额
	        sb.append("CNY");
	        sb.append("");       // 商品名称
	        sb.append("");               // 商品种类
	        sb.append(""); // 商品描述
	        sb.append(config.getNoticeUrl()); // 商户接收支付成功数据的地址
	        sb.append("0");                  // 需要填写送货信息 0：不需要  1:需要
	        sb.append("");                    // 商户扩
			sb.append(""); 
			sb.append("1");       
	        return sb.toString();
	    }

	  
	  public static String getFormStr(List<String> keys,Map<String, String> sPara,String requestUrl){
    	  StringBuffer sbHtml = new StringBuffer();
          sbHtml.append("<form target=\"_blank\" name=\"yeepay\" action=\"" + requestUrl +"\" method=\"Post\">");
          for (int i = 0; i < keys.size(); i++) {
              String name = (String) keys.get(i);
              String value = (String) sPara.get(name);
              sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
          }
          //submit按钮控件请不要含有name属性
          sbHtml.append("<input type=\"submit\" value=\"确定 \" style=\"display:none;\"></form>");
          sbHtml.append("<script>document.forms['yeepay'].submit();</script>");
          return sbHtml.toString();
    }
	  
	  /**
		 * get发送消息
		 * 
		 * @param strUrl
		 * @param map
		 * @return
		 * @throws IOException
		 */
		public static List<String> sendGet(String url, String getStr, int timeout,
				String charset) throws Exception {
			List<String>list=new ArrayList<String>();
			BufferedReader br = null;
			try {
				URL u = new URL(url + "?" + getStr);
				HttpURLConnection uc = (HttpURLConnection) u.openConnection();
				uc.setConnectTimeout(30000);
				uc.setDoOutput(true);
				uc.setRequestMethod("GET");
				uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=" + charset);
				String line = null;
				br = new BufferedReader(new InputStreamReader(uc.getInputStream(),
						charset));
				while ((line = br.readLine()) != null) {
					list.add(line);
				}
			} catch (Exception ex) {
				throw new Exception(ex.getMessage());
			} finally {
				try {
					if (br != null) {
						br.close();
						br = null;
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return list;
		}
		
}
