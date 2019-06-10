package com.lottery.common.util;

import com.lottery.common.contains.CharsetConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author fengqinyun
 *
 */
public class CoreHttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(CoreHttpUtils.class.getName());
	
	private CoreHttpUtils() {
	}
	
	private static MultiThreadedHttpConnectionManager connectionManager = null;

	private static int connectionTimeOut = 30000;
	private static int socketTimeOut = 30000;
	private static int maxConnectionPerHost = 100;
	private static int maxTotalConnections = 100;
    private static long idleTimeout=30000;//释放空闲连接
	private static HttpClient client;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
		connectionManager.getParams().setSoTimeout(socketTimeOut);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
		connectionManager.closeIdleConnections(idleTimeout);
		client = new HttpClient(connectionManager);
	}
	
	private static String[] httpProxyHeaderName = new String[] {
		"CDN-SRC-IP",
		"HTTP_CDN_SRC_IP",
		"X-FORWARDED-FOR"
	};
	
	public static String getClientIP(HttpServletRequest request) {
		for (String headerName : httpProxyHeaderName) {
			String clientIP = request.getHeader(headerName);
			if (StringUtils.isNotEmpty(clientIP)) {
				return clientIP;
			}
		}
		return request.getRemoteAddr();
	}
	
	public static String[] getClientIPArray(HttpServletRequest request) {
		String clientIP = getClientIP(request);
		if (StringUtils.isEmpty(clientIP)) {
			return null;
		}
		return StringUtils.split(clientIP, ",");
	}
	
	/**
	 * Get方式请求(连接管理器模式)
	 * @param baseurl
	 * @param queryString
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static String urlGetMethod(String baseurl, String queryString, String encoding, int timeout_msec) throws IOException {
		String responseData = null;
		GetMethod getMethod = null;
		StringBuffer urlstr = new StringBuffer(baseurl);
		
		if (urlstr.indexOf("?") == -1) {
			urlstr.append("?");
		} else {
			urlstr.append("&");
		}
		
		urlstr.append((queryString != null) ? queryString : "");
		logger.info("Url to get: {}", urlstr.toString());

		try {
			getMethod = new GetMethod(urlstr.toString());
			getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encoding);
			if (timeout_msec > 0) {
				getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout_msec);
			}
			// 执行getMethod
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				String responseEncoding = getMethod.getResponseCharSet();
				if(responseEncoding==null||responseEncoding.trim().length()==0){
					responseEncoding = encoding;
				}
				InputStream in = getMethod.getResponseBodyAsStream();  
				//这里的编码规则要与页面的相对应  
				BufferedReader br = new BufferedReader(new InputStreamReader(in,responseEncoding));  
				String tempbf = null;;  
				StringBuffer sb = new StringBuffer(100);  
				while (true) {
					tempbf = br.readLine();
					if (tempbf == null) {
						break;
					} else {
						sb.append(tempbf);  
					}
				}
				responseData = sb.toString();
				logger.debug("GET请求响应内容："+responseData);
				br.close();
				responseEncoding = null;
			} else {
				logger.error("GET请求失败,请求地址:"+urlstr.toString()+",响应状态码:" + getMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题,get请求url:"+urlstr.toString(),e);
		} catch (IOException e) {
			logger.error("发生网络异常,get请求url:"+urlstr.toString(),e);
		}
		if (getMethod != null) {
			getMethod.releaseConnection();
			getMethod = null;
		}
	
		return responseData;

	}
	/**
	 * Get方式请求(连接管理器模式)
	 * @param baseurl
	 * @param params
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static String urlGetMethod(String baseurl, Map<String, String> params, String encoding, int timeout_msec) throws IOException {
		return urlGetMethod(baseurl, getQueryString(params, encoding), encoding, timeout_msec);
	}
	/**
	 * Post方式请求(连接管理器模式)
	 * @param baseurl
	 * @param queryString
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static String urlPostMethod(String baseurl, String queryString, String encoding, int timeout_msec) throws IOException {
		Map<String, String> params = parseQueryString(queryString, encoding);
		return urlPostMethod(baseurl, params, encoding, timeout_msec);
	}
	/**
	 * Post方式请求(连接管理器模式)
	 * @param baseurl
	 * @param params
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static String urlPostMethod(String baseurl, Map<String, String> params, String encoding, int timeout_msec) throws IOException {
		String responseData = null;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(baseurl);
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,encoding);
			if (timeout_msec > 0) {
				postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout_msec);
			}
			
			if(params!=null){
				// 将表单的值放入postMethod中
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					String value = params.get(key);
					postMethod.addParameter(key, value);
				}
			}
			//postMethod.setRequestHeader("Connection", "close");//释放空闲连接
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK) {
				String responseEncoding = postMethod.getResponseCharSet();
				if(responseEncoding==null||responseEncoding.trim().length()==0){
					responseEncoding = encoding;
				}
				InputStream is = postMethod.getResponseBodyAsStream();  
				//这里的编码规则要与上面的相对应  
				BufferedReader br = new BufferedReader(new InputStreamReader(is,responseEncoding));  
				String tempbf = null;;  
				StringBuffer sb = new StringBuffer();  
				while (true) {
					tempbf = br.readLine();
					if (tempbf == null) {
						break;
					} else {
						sb.append(tempbf);  
					}
				}
				responseData = sb.toString();
				logger.debug("POST请求响应内容："+responseData);
				br.close();
				responseEncoding = null;
			} else {
				logger.error("POST请求失败,请求地址:"+baseurl+",响应状态码 :" + postMethod.getStatusCode());
			}
		} catch (HttpException e) {
			logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题,post请求url:"+baseurl);
		} catch (IOException e) {
			logger.error("发生网络异常,get请求url:"+baseurl);
		}
		if (postMethod != null) {
			postMethod.releaseConnection();
			//postMethod = null;
		}
		return responseData;
	}
	
	public static String readFromPostMethod(PostMethod postMethod, String encoding) throws Exception {
		String responseEncoding = postMethod.getResponseCharSet();
		if (responseEncoding == null || responseEncoding.trim().length() == 0){
			responseEncoding = encoding;
		}
		InputStream is = postMethod.getResponseBodyAsStream();
		//这里的编码规则要与上面的相对应  
		BufferedReader br = new BufferedReader(new InputStreamReader(is, responseEncoding));  
		String tempbf = null;;  
		StringBuffer sb = new StringBuffer();  
		while (true) {
			tempbf = br.readLine();
			if (tempbf == null) {
				break;
			} else {
				sb.append(tempbf);  
			}
		}
		br.close();
		return sb.toString();
	}
	
	public static PostMethod urlPostMethod(String baseurl, Map<String, String> requestHeadParams, Part[] parts, String encoding, int timeout_msec) throws IOException, HttpException {
		PostMethod postMethod = new PostMethod(baseurl);
		
		postMethod.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);//忽略cookie
		
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
		
		if (timeout_msec > 0) {
			postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout_msec);
		}
		
		if (requestHeadParams != null) {
			Set<String> keySet = requestHeadParams.keySet();
			for (String key : keySet) {
				postMethod.addRequestHeader(key, requestHeadParams.get(key));
			}
		}
		
		if (parts != null) {
			postMethod.setRequestEntity(
					new MultipartRequestEntity(parts, postMethod.getParams())
			);
		}
		
		int statusCode = client.executeMethod(postMethod);
		if (statusCode == HttpStatus.SC_OK) {
			return postMethod;
		}

		if (postMethod != null) {
			postMethod.releaseConnection();
		}
		logger.error("POST请求失败,请求地址: {},响应状态码 : {}", baseurl, postMethod.getStatusCode());
		throw new HttpException("POST请求结果不是200 OK");
	}
	
	/**
	 * 注意：需要在请求前完成对baseurl和queryString的urlencode
	 * @param baseurl
	 * @param queryString
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static List<String> getUrl(String baseurl, String queryString, String encoding, int timeout_msec) throws IOException {
		StringBuffer urlstr = new StringBuffer(baseurl);
		
		List<String> result = new ArrayList<String>();
		
		if (urlstr.indexOf("?") == -1) {
			urlstr.append("?");
		} else {
			urlstr.append("&");
		}
		
		urlstr.append((queryString != null) ? queryString : "");
		logger.info("Url to get: {}", urlstr.toString());
		
		URL url = new URL(urlstr.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (timeout_msec > 0) {
			conn.setConnectTimeout(timeout_msec);
			conn.setReadTimeout(timeout_msec);
		}
		HttpURLConnection.setFollowRedirects(true);
		conn.setUseCaches(false);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				logger.info(line);
				result.add(line);
			}
		}
		in.close();
		return result;
	}
	
	public static List<String> getUrl(String baseurl, Map<String, String> params, String encoding, int timeout_msec) throws IOException {
		return getUrl(baseurl, getQueryString(params, encoding), encoding, timeout_msec);
	}
	
	/**
	 * 注意：需要在请求前完成对baseurl和queryString的urlencode
	 * @param baseurl
	 * @param queryString
	 * @param encoding
	 * @param timeout_msec
	 * @return
	 * @throws IOException
	 */
	public static List<String> postUrl(String baseurl, String queryString, String encoding, int timeout_msec) throws IOException {
		StringBuffer content = new StringBuffer((queryString != null) ? queryString : "");
		
		List<String> result = new ArrayList<String>();
		
		URL url = new URL(baseurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (timeout_msec > 0) {
			conn.setConnectTimeout(timeout_msec);
			conn.setReadTimeout(timeout_msec);
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setAllowUserInteraction(false);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		out.write(content.toString());
		out.flush();
		out.close();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				logger.info(line);
				result.add(line);
			}
		}
		in.close();
		return result;
	}
	
	public static List<String> postUrl(String baseurl, Map<String, String> params, String encoding, int timeout_msec) throws IOException {
		return postUrl(baseurl, getQueryString(params, encoding), encoding, timeout_msec);
	}
	
	/**
	 * @描述:解析key=value&key=value的键值
	 * @param contents
	 * @return
	 */
	public static Map<String, String> parseQueryString(String contents) {
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValues = contents.split("&");
		for (int i = 0; i < keyValues.length; i++) {
			String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
			String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
			
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * @描述:解析key=value&key=value的键值
	 * @param contents
	 * @param encoding
	 * @return
	 */
	public static Map<String, String> parseQueryString(String contents, String encoding) {
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValues = contents.split("&");
		for (int i = 0; i < keyValues.length; i++) {
			String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
			String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
			
			try {
				if (encoding != null && !encoding.isEmpty()) {
					value = URLDecoder.decode(value, encoding);
				} else {
					value = URLDecoder.decode(value, CharsetConstant.CHARSET_UTF8);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @描述: 转换成key=value&key=value的键值
	 * @param params
	 * @return
	 */
	public static String getQueryString(Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return "";
		}
		
		StringBuffer querystr = new StringBuffer();
		
		Set<String> keys = params.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = i.next();
			String val = params.get(key);
			
			if (val == null) {
				val = "";
			}
			
			querystr.append(key);
			querystr.append("=");
			querystr.append(val);
			
			querystr.append("&");
		}
		
		if (querystr.charAt(querystr.length() - 1) == '&') {
			querystr.deleteCharAt(querystr.length() - 1);
		}
		
		return querystr.toString();
	}
	
	public static String getQueryUrl(String url, Map<String, String> params, String encoding) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		return sb.append(getQueryString(params, encoding)).toString();
	}
	
	/**
	 * @描述: 转换成key=value&key=value的键值
	 * @param params
	 * @param keyList 
	 * @return
	 */
	public static String getQueryString(Map<String, String> params, List<String> keyList) {
		if (params == null || params.size() == 0) {
			return "";
		}
		if(keyList==null || keyList.size()==0){
			return getQueryString(params);
		}
		
		StringBuffer querystr = new StringBuffer();
		
		for (int i=0;i<keyList.size();i++) {
			
			String val = params.get(keyList.get(i));
			
			if (val == null) {
				val = "";
			}
			
			querystr.append(keyList.get(i));
			querystr.append("=");
			querystr.append(val);
			
			querystr.append("&");
		}
		
		if (querystr.charAt(querystr.length() - 1) == '&') {
			querystr.deleteCharAt(querystr.length() - 1);
		}
		
		return querystr.toString();
	}
	
	/**
	 * @描述: 转换成key=value&key=value的键值
	 * @param params
	 * @param encoding
	 * @return
	 */
	public static String getQueryString(Map<String, String> params, String encoding) {
		if (params == null || params.size() == 0) {
			return "";
		}
		
		StringBuffer querystr = new StringBuffer();
		
		Set<String> keys = params.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = i.next();
			String val = params.get(key);
			
			if (val == null) {
				val = "";
			}
			
			try {
				querystr.append(URLEncoder.encode(key, encoding));
				querystr.append("=");
				querystr.append(URLEncoder.encode(val, encoding));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				continue;
			}
			
			querystr.append("&");
		}
		
		if (querystr.charAt(querystr.length() - 1) == '&') {
			querystr.deleteCharAt(querystr.length() - 1);
		}
		
		return querystr.toString();
	}
	
	/**
	 * 和javascript的encodeURI效果相同的方法，用于encode完整的url
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String encodeURI(String url, String encoding) {
		if (url == null) {
			return null;
		}
		if (url.indexOf("?") <= 0) {
			return url;
		}
		String[] parts = StringUtils.split(url, "?");
		if (parts.length == 1) {
			return parts[0];
		}
		StringBuffer encodedUrl = new StringBuffer(parts[0]);
		encodedUrl.append("?");
		
		String[] params = StringUtils.split(parts[1], "&");
		for (int i = 0; i < params.length; i ++) {
			String param = params[i];
			if (i > 0) {
				encodedUrl.append("&");
			}
			int index = param.indexOf("=");
			if (index > 0) {
				String key = param.substring(0, index);
				String val = param.substring(index + 1);
				try {
					encodedUrl.append(URLEncoder.encode(key, encoding)).append("=").append(URLEncoder.encode(val, encoding));
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(), e);
					continue;
				}
			} else {
				encodedUrl.append(param);
			}
		}
		
		return encodedUrl.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("test", "1111");
		params.put("nullkey", null);
		params.put("hanzi", "汉字");
		params.put("中文参数", "中文");
		
		System.out.println(getQueryString(params, "UTF-8"));
		
		System.out.println(encodeURI("http://a.dev.lehecai.cn/user/plan_prize_ranking.php?json={\"page\":1,\"pagesize\":10,\"where\":[{\"key\":\"lottery_type\",\"op\":\"=\",\"val\":[30,31,32,33,34]},{\"key\":\"ticket_print_time_start\",\"val\":\"2011-01-01 00:00:00\"},{\"key\":\"ticket_print_time_end\",\"val\":\"2011-09-01 23:59:59\"}]}", "UTF-8"));
	}
}
