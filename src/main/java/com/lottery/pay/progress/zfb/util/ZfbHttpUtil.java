package com.lottery.pay.progress.zfb.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.FilePartSource;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.lottery.common.contains.CharsetConstant;
public class ZfbHttpUtil {
		/**
		 * HTTP连接管理器，该连接管理器必须是线程安全的.
		 */
		private static HttpConnectionManager connectionManager;
		private static int defaultMaxConnPerHost = 30;
		private static int defaultMaxTotalConn = 80;
		/** 回应超时时间, 由bean factory设置，缺省为30秒钟 */
		private static int defaultSoTimeout = 30000;
		/** 连接超时时间，由bean factory设置，缺省为8秒钟 */
		private static int defaultConnectionTimeout = 8000;
		private static String DEFAULT_CHARSET = CharsetConstant.CHARSET_UTF8;
		/** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒 */
		private static final long defaultHttpConnectionManagerTimeout = 3 * 1000;
		private static ZfbHttpUtil httpProtocolHandler = new ZfbHttpUtil();

		public static ZfbHttpUtil getInstance() {
			return httpProtocolHandler;
		}

		/**
		 * 私有的构造方法
		 */
		private ZfbHttpUtil() {
			// 创建一个线程安全的HTTP连接池
			if (connectionManager == null) {
				connectionManager = new MultiThreadedHttpConnectionManager();
				connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
				connectionManager.getParams().setMaxTotalConnections(
						defaultMaxTotalConn);
			}
		}

		public static NameValuePair[] generatNameValuePair(
				Map<String, String> properties) {
			if (properties == null || properties.size() == 0) {
				return null;
			}
			NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : properties.entrySet()) {
				nameValuePair[i++] = new NameValuePair(entry.getKey(),
						entry.getValue());
			}
			return nameValuePair;
		}

		

		/**
		 * get方法请求
		 * 
		 * @param sParaTemp
		 * @param requestUrl
		 * @param strParaFileName
		 *            文件类型的参数名
		 * @param strFilePath
		 *            文件路径
		 * @return
		 * @throws Exception
		 */
		public static String buildRequest(Map<String, String> sParaTemp,
				String requestUrl, String strParaFileName, String strFilePath)
				throws Exception {
			ZfbHttpUtil httpProtocolHandler = getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			// 设置编码集
			request.setCharset(CharsetConstant.CHARSET_UTF8);
			request.setParameters(generatNameValuePair(sParaTemp));
			request.setUrl(requestUrl + "_input_charset="
					+ CharsetConstant.CHARSET_UTF8);
			HttpResponse response = httpProtocolHandler.execute(request,
					strParaFileName, strFilePath);
			if (response == null) {
				return null;
			}
			String strResult = response.getStringResult();
			return strResult;
		}
	/**
	 * 执行Http请求
	 * 
	 * @param request
	 *            请求数据
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @return
	 * @throws HttpException
	 *             , IOException
	 */
	public HttpResponse execute(HttpRequest request, String strParaFileName,
			String strFilePath) throws HttpException, IOException {
		HttpClient httpclient = new HttpClient(connectionManager);
		// 设置连接超时
		int connectionTimeout = defaultConnectionTimeout;
		if (request.getConnectionTimeout() > 0) {
			connectionTimeout = request.getConnectionTimeout();
		}
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置回应超时
		int soTimeout = defaultSoTimeout;
		if (request.getTimeout() > 0) {
			soTimeout = request.getTimeout();
		}
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);
		// 设置等待ConnectionManager释放connection的时间
		httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);
		String charset = request.getCharset();
		charset = charset == null ? DEFAULT_CHARSET : charset;
		HttpMethod method = null;
		// get模式且不带上传文件
		if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
			method = new GetMethod(request.getUrl());
			method.getParams().setCredentialCharset(charset);
			// parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
			method.setQueryString(request.getQueryString());
		} else if (strParaFileName.equals("") && strFilePath.equals("")) {
			// post模式且不带上传文件
			method = new PostMethod(request.getUrl());
			((PostMethod) method).addParameters(request.getParameters());
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded; text/html; charset="
							+ charset);
		} else {
			// post模式且带上传文件
			method = new PostMethod(request.getUrl());
			List<Part> parts = new ArrayList<Part>();
			for (int i = 0; i < request.getParameters().length; i++) {
				parts.add(new StringPart(request.getParameters()[i].getName(),
						request.getParameters()[i].getValue(), charset));
			}
			// 增加文件参数，strParaFileName是参数名，使用本地文件
			parts.add(new FilePart(strParaFileName, new FilePartSource(
					new File(strFilePath))));
			// 设置请求体
			((PostMethod) method).setRequestEntity(new MultipartRequestEntity(
					parts.toArray(new Part[0]), new HttpMethodParams()));
		}

		// 设置Http Header中的User-Agent属性
		method.addRequestHeader("User-Agent", "Mozilla/4.0");
		HttpResponse response = new HttpResponse();
		try {
			httpclient.executeMethod(method);
			if (request.getResultType().equals(HttpResultType.STRING)) {
				response.setStringResult(method.getResponseBodyAsString());
			} else if (request.getResultType().equals(HttpResultType.BYTES)) {
				response.setByteResult(method.getResponseBody());
			}
			response.setResponseHeaders(method.getResponseHeaders());
		} catch (IOException ex) {
			return null;
		} catch (Exception ex) {
			return null;
		} finally {
			method.releaseConnection();
		}
		return response;
	}

}
