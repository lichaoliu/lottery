package com.lottery.pay.progress.elinkpc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.lottery.common.contains.CharsetConstant;

public class HttpClient {

	  public static String send(Map<String, String> data,String url) throws Exception
	  {
	    try
	    {
	      HttpURLConnection httpURLConnection = createConnection(CharsetConstant.CHARSET_UTF8,url);
	      if (httpURLConnection == null) {
	        throw new Exception("创建联接失败");
	      }
	      requestServer(httpURLConnection, getRequestParamString(data, CharsetConstant.CHARSET_UTF8), CharsetConstant.CHARSET_UTF8);
	      String result = response(httpURLConnection, CharsetConstant.CHARSET_UTF8);
	      return result;
	    } catch (Exception e1) {
	       throw e1;
	    }
	  }

	  private static void requestServer(URLConnection connection, String message, String encoder)
	    throws Exception
	  {
	    PrintStream out = null;
	    try {
	      connection.connect();
	      out = new PrintStream(connection.getOutputStream(), false, encoder);
	      out.print(message);
	      out.flush();
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      if (out != null)
	        out.close();
	    }
	  }

	  private static String response(HttpURLConnection connection, String encoding)
	    throws URISyntaxException, IOException, Exception
	  {
	    InputStream in = null;
	    StringBuilder sb = new StringBuilder(1024);
	    BufferedReader br = null;
	    String temp = null;
	    String str1 =null;
	    try {
	      if (200 == connection.getResponseCode()) {
	        in = connection.getInputStream();
	        br = new BufferedReader(new InputStreamReader(in, encoding));
	        while ((temp = br.readLine()) != null)
	          sb.append(temp);
	      }
	      else {
	        in = connection.getErrorStream();
	        br = new BufferedReader(new InputStreamReader(in, encoding));
	        while ((temp = br.readLine()) != null) {
	          sb.append(temp);
	        }
	      }
	      str1 = sb.toString();
	      return str1;
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      if (br != null) {
	        br.close();
	      }
	      if (in != null) {
	        in.close();
	      }
	      if (connection != null)
	        connection.disconnect();
	    }
	  }

	  private static HttpURLConnection createConnection(String encoding,String url)
	    throws ProtocolException
	  {
		 URL urlStr=null;
		 HttpURLConnection httpURLConnection = null;
	    try {  
	      urlStr= new URL(url);
	      httpURLConnection = (HttpURLConnection)urlStr.openConnection();
	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    }
	    httpURLConnection.setConnectTimeout(30000);
	    httpURLConnection.setReadTimeout(30000);
	    httpURLConnection.setDoInput(true);
	    httpURLConnection.setDoOutput(true);
	    httpURLConnection.setUseCaches(false);
	    httpURLConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=" + encoding);
	    httpURLConnection.setRequestMethod("POST");
	    if ("https".equalsIgnoreCase(urlStr.getProtocol())) {
	      HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
	      husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
	      husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
	      return husn;
	    }
	    return httpURLConnection;
	  }

	  private static String getRequestParamString(Map<String, String> requestParam, String coder)
	  {
	    if ((coder == null) || ("".equals(coder))) {
	      coder = "UTF-8";
	    }
	    StringBuffer sf = new StringBuffer("");
	    String reqstr = "";
	    if ((requestParam != null) && (requestParam.size() != 0)) {
	      for (Map.Entry en : requestParam.entrySet()) {
	        try {
	          sf.append((String)en.getKey() + 
	            "=" + (
	            (en.getValue() == null) || ("".equals(en.getValue())) ? "" : 
	            URLEncoder.encode((String)en.getValue(), coder)) + "&");
	        } catch (UnsupportedEncodingException e) {
	          e.printStackTrace();
	          return "";
	        }
	      }
	      reqstr = sf.substring(0, sf.length() - 1);
	    }
	    return reqstr;
	  }
}
