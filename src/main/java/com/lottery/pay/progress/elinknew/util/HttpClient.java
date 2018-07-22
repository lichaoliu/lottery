package com.lottery.pay.progress.elinknew.util;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.pay.progress.elinkpc.util.BaseHttpSSLSocketFactory;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

public class HttpClient {
	private static Logger logger= LoggerFactory.getLogger(HttpClient.class);
	private URL url;
	  private int connectionTimeout;
	  private int readTimeOut;
	  private String result;

	  public String getResult()
	  {
	    return this.result;
	  }

	  public void setResult(String result)
	  {
	    this.result = result;
	  }
       
	  public static String getResult(Map<String,String>	resultMap,String requestUrl){
		  HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
			String respString=null;
			try {
				int status = hc.send(resultMap, CharsetConstant.CHARSET_UTF8);
				if (200 == status) {
					respString = hc.getResult();
				}
			} catch (Exception e) {
			  logger.error("请求出错",e);
			}
			return respString;
	  }
	  
	  public HttpClient(String url, int connectionTimeout, int readTimeOut)
	  {
	    try
	    {
	      this.url = new URL(url);
	      this.connectionTimeout = connectionTimeout;
	      this.readTimeOut = readTimeOut;
	    } catch (MalformedURLException e) {
			logger.error("请求出错", e);
	    }
	  }

	  public int send(Map<String, String> data, String encoding)
	    throws Exception
	  {
	    try
	    {
	      HttpURLConnection httpURLConnection = createConnection(encoding);
	      if (null == httpURLConnection) {
	        throw new Exception("创建联接失败");
	      }
	      requestServer(httpURLConnection, getRequestParamString(data, encoding), encoding);

	      this.result = response(httpURLConnection, encoding);
	      return httpURLConnection.getResponseCode(); 
	      } catch (Exception e) {
	    	  throw e;
	       }
	  }

	  private void requestServer(URLConnection connection, String message, String encoder)
	    throws Exception
	  {
	    PrintStream out = null;
	    try {
	      connection.connect();
	      out = new PrintStream(connection.getOutputStream(), false, encoder);
	      out.print(message);
	      out.flush();

	      if (null != out)
	        out.close();
	    }
	    catch (Exception e)
	    {
	      throw e;
	    } finally {
	      if (null != out)
	        out.close();
	    }
	  }

	  private String response(HttpURLConnection connection, String encoding)
	    throws URISyntaxException, IOException, Exception
	  {
	    InputStream in = null;
	    StringBuilder sb = new StringBuilder(1024);
	    try {
	      if (200 == connection.getResponseCode()) {
	        in = connection.getInputStream();
	        sb.append(IOUtils.toString(in, encoding));
	      } else {
	        in = connection.getErrorStream();
	        sb.append(IOUtils.toString(in, encoding));
	      }
	      String str = sb.toString();
	      return str;
	    }
	    catch (Exception e)
	    {
	      throw e;
	    }
	    finally {
	      if (null != in) {
	        in.close();
	      }
	      if (null != connection)
	        connection.disconnect(); 
	    }
	  }

	  private HttpURLConnection createConnection(String encoding)
	    throws ProtocolException
	  {
	    HttpURLConnection httpURLConnection = null;
	    try {
	      httpURLConnection = (HttpURLConnection)this.url.openConnection();
	    } catch (IOException e) {
	      e.printStackTrace();
	      return null;
	    }
	    httpURLConnection.setConnectTimeout(this.connectionTimeout);
	    httpURLConnection.setReadTimeout(this.readTimeOut);
	    httpURLConnection.setDoInput(true);
	    httpURLConnection.setDoOutput(true);
	    httpURLConnection.setUseCaches(false);
	    httpURLConnection.setRequestProperty("Content-type", new StringBuilder().append("application/x-www-form-urlencoded;charset=").append(encoding).toString());

	    httpURLConnection.setRequestMethod("POST");
	    if ("https".equalsIgnoreCase(this.url.getProtocol())) {
	      HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
	      husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
	      husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
	      return husn;
	    }
	    return httpURLConnection;
	  }

	  private String getRequestParamString(Map<String, String> requestParam, String coder)
	  {
	    if ((null == coder) || ("".equals(coder))) {
	      coder = "UTF-8";
	    }
	    StringBuffer sf = new StringBuffer("");
	    String reqstr = "";
	    if ((null != requestParam) && (0 != requestParam.size())) {
	      for (Map.Entry en : requestParam.entrySet()) {
	        try {
	          sf.append(new StringBuilder().append((String)en.getKey()).append("=").append((null == en.getValue()) || ("".equals(en.getValue())) ? "" : URLEncoder.encode((String)en.getValue(), coder)).append("&").toString());
	        }
	        catch (UnsupportedEncodingException e)
	        {
				logger.error("请求出错",e);
	          return "";
	        }
	      }
	      reqstr = sf.substring(0, sf.length() - 1);
	    }
	    return reqstr;
	  }
}
