package com.lottery.pay.progress.elinkpc.util;

import com.lottery.common.contains.CharsetConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SDKUtil {
	protected static Logger logger = LoggerFactory.getLogger(SDKUtil.class);
	protected static char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 
	    'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 
	    'v', 'w', 'x', 'y', 'z' };


	 


	  public static boolean sign(Map<String, String> data, String encoding,String signCertPath,String signCertPwd)
	  {
	    data.put("certId", CertUtil.getSignCertId(signCertPath,signCertPwd));
	    String stringData = coverMap2String(data);
	    byte[] byteSign = (byte[])null;
	    String stringSign = null;
	    try
	    {
	      byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
	      byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKey(signCertPwd), signDigest));
	      stringSign = new String(byteSign);
	      data.put("signature", stringSign.trim());
	      return true;
	    } catch (Exception e) {
	      logger.error("签名异常", e);
	    }
	    return false;
	  }


	  public static boolean validate(Map<String, String> resData,String validateCertDir)
	  {
	    String stringSign = (String)resData.get("signature");
	    String certId = (String)resData.get("certId");
	    String stringData = coverMap2String(resData);
	    try
	    {
	      return SecureUtil.validateSignBySoft(
	        CertUtil.getValidateKey(certId,validateCertDir), SecureUtil.base64Decode(stringSign
	        .getBytes(CharsetConstant.CHARSET_UTF8)), SecureUtil.sha1X16(stringData, CharsetConstant.CHARSET_UTF8));
	    } catch (UnsupportedEncodingException e) {
	    	logger.error(e.getMessage(), e);
	    } catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	    }
	    return false;
	  }

	  @SuppressWarnings("unchecked")
	public static String coverMap2String(Map<String, String> data)
	  {
	    TreeMap<String,String> tree = new TreeMap<String,String>();
	    Iterator<?> it = data.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry<String,String> en = (Map.Entry<String,String>)it.next();
	      if ("signature".equals(((String)en.getKey()).trim())||"type".equals(((String)en.getKey()).trim())) {
	        continue;
	      }
	      tree.put((String)en.getKey(), (String)en.getValue());
	    }
	    it = tree.entrySet().iterator();
	    StringBuffer sf = new StringBuffer();
	    while (it.hasNext()) {
	      Map.Entry<String,String> en = (Map.Entry<String,String>)it.next();
	      sf.append((String)en.getKey() + "=" + (String)en.getValue() +  "&");
	    }
	    return sf.substring(0, sf.length() - 1);
	  }

	  private static Map<String, String> convertResultString2Map(String res)
	  {
	    Map<String,String> map = null;
	    if ((res != null) && (!"".equals(res.trim()))) {
	      String[] resArray = res.split("&");
	      if (resArray.length != 0) {
	        map = new HashMap<String, String>(resArray.length);
	        for (String arrayStr : resArray) {
	          if ((arrayStr == null) || ("".equals(arrayStr.trim()))) {
	            continue;
	          }
	          int index = arrayStr.indexOf("=");
	          if (-1 == index) {
	            continue;
	          }
	          map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
	        }
	      }
	    }
	    return map;
	  }

	  private static void convertResultStringJoinMap(String res, Map<String, String> map)
	  {
	    if ((res != null) && (!"".equals(res.trim()))) {
	      String[] resArray = res.split("&");
	      if (resArray.length != 0)
	        for (String arrayStr : resArray) {
	          if ((arrayStr == null) || ("".equals(arrayStr.trim()))) {
	            continue;
	          }
	          int index = arrayStr.indexOf("=");
	          if (-1 == index) {
	            continue;
	          }
	          map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
	        }
	    }
	  }

	  public static Map<String, String> coverResultString2Map(String result)
	  {
	    return convertResultStringToMap(result);
	  }

	  public static Map<String, String> convertResultStringToMap(String result)
	  {
	    if (result.contains("{"))
	    {
	      String separator = "\\{";
	      String[] res = result.split(separator);
	      Map<String,String> map = new HashMap<String,String>();
	      convertResultStringJoinMap(res[0], map);
	      for (int i = 1; i < res.length; i++)
	      {
	        int index = res[i].indexOf("}");
	        String specialValue = "{" + res[i].substring(0, index) + "}";
	        int indexKey = res[(i - 1)].lastIndexOf("&");
	        String specialKey = res[(i - 1)].substring(indexKey + 1, 
	          res[(i - 1)].length() - 1);
	        map.put(specialKey, specialValue);
	        String normalResult = res[i].substring(index + 2, res[i].length());

	        convertResultStringJoinMap(normalResult, map);
	      }

	      return map;
	    }

	    return convertResultString2Map(result);
	  }

	

}
