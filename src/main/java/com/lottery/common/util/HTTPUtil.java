package com.lottery.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import java.net.Socket;
import java.net.URL;
import java.nio.charset.CodingErrorAction;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lottery.common.contains.CharsetConstant;

import javax.net.ssl.*;

public class HTTPUtil {


    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    /**
     * 回应超时时间, 由bean factory设置，缺省为30秒钟
     */
    private static int defaultSoTimeout = 30000;

    private static int defaultSeconds=30;//默认30s
    /**
     * 连接超时时间，由bean factory设置，缺省为8秒钟
     */

    private static String DEFAULT_CHARSET = CharsetConstant.CHARSET_UTF8;

    private static final int HTTP_MAX_CONNECTIONS = 1000;
    private static final int MAX_RETRY_TIMES = 3;


    private static String[] httpProxyHeaderName = new String[]{"CDN-SRC-IP", "HTTP_CDN_SRC_IP", "X-FORWARDED-FOR"};


    /**
     * @param contents
     * @returnvn
     * @描述:解析key=value&key=value的键值
     */
    protected static Map<String, String> parseQueryString(String contents) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(contents)){
            return map;
        }
        String[] keyValues = contents.split("&");
        for (int i = 0; i < keyValues.length; i++) {
            String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
            String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
            map.put(key, value);
        }
        return map;
    }

    /**
     * httpclient post请求
     *
     * @param url        请求地址
     * @param parameters 请求参数
     * @param timeOut 超时时间(毫秒)
     * @param charSet 请求字符编码
     * @param returnCharSet 请求返回字符编码
     * @return 返回结果集
     * 代理
     * HttpHost proxy = new HttpHost("192.168.13.19", 7777);
     * httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
     * @throws Exception
     */
    protected static String post(String url, List<NameValuePair> parameters, String charSet,String returnCharSet, int timeOut) throws Exception {
        if (StringUtils.isBlank(url)){
            throw  new RuntimeException("url is null");
        }
        CloseableHttpClient httpclient = getHttpClient(timeOut);
                //HttpClients.createDefault();


        try {
            HttpPost httppost = new HttpPost(url);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, charSet);
            httppost.setEntity(uefEntity);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).build();
            httppost.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    httppost.abort();
                    throw new RuntimeException("HttpClient post error,url="+url+",status code=" + statusCode);
                }
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    return EntityUtils.toString(entity, returnCharSet);
                }
            } finally {
                if(response!=null){
                    response.close();
                }

            }
            return null;
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();

        }
    }


    /**
     * @param timeout  单位:秒
     * */

    private static CloseableHttpClient getHttpClient(int timeout) throws Exception{
        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager trustManager = new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws CertificateException {
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                // http注册
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                // https注册
                .register("https", new SSLConnectionSocketFactory(sc)).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout*1000)
                // 设置请求的最大响应时间
                .setConnectionRequestTimeout(timeout*1000)
                // 设置TCP建立连接的超时时间
                .setSocketTimeout(timeout*1000).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

        connectionManager.setDefaultSocketConfig(socketConfig);
        // Create message constraints
        MessageConstraints constraints = MessageConstraints.custom().setMaxHeaderCount(200)
                .setMaxLineLength(100000).build();

        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(constraints).build();

        connectionManager.setDefaultConnectionConfig(connectionConfig);
        // 连接池里的最大连接数
        connectionManager.setMaxTotal(HTTP_MAX_CONNECTIONS);
        // 每个路由的默认最大连接数,这服务器的数量以及连接池的最大连接数有关
        connectionManager.setDefaultMaxPerRoute(HTTP_MAX_CONNECTIONS / 2);

        List<BasicHeader> defaultHeaders = new ArrayList<BasicHeader>() {
            private static final long serialVersionUID = 1263811764541797122L;
            {
                // 不使用缓存
                add(new BasicHeader("Expires", "0"));
                add(new BasicHeader("Cache-Control", "no-store"));
            }
        };

        return HttpClientBuilder.create()
                .setRetryHandler(new DefaultHttpRequestRetryHandler(MAX_RETRY_TIMES, true))
                .setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
                .setDefaultHeaders(defaultHeaders).build();
    }

    public static String post(String url, String param)throws  Exception{
        return post(url, param,CharsetConstant.CHARSET_DEFAULT);
    }



    /**
     * 发送http请求
     *
     * @param url 请求地址，param请求参数
     * @throws Exception
     */
    public static String post(String url, String param,String charset) throws Exception {

        OutputStreamWriter reqOut = null;

        try {

            HttpURLConnection connection = createConnection(url,"POST",charset,defaultSeconds);
            if (param != null) {
                reqOut = new OutputStreamWriter(connection.getOutputStream(),charset);
                reqOut.write(param);
                reqOut.flush();
            }
            int charCount = -1;
            int status = connection.getResponseCode();
            if (status == HttpStatus.SC_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
                StringBuffer responseMessage = new StringBuffer();
                while ((charCount = br.read()) != -1) {
                    responseMessage.append((char) charCount);
                }
                br.close();
                return responseMessage.toString();
            }
           throw  new RuntimeException("请求返回的状态不是200,url="+url+",return status="+status);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (reqOut != null)
                    reqOut.close();
            } catch (IOException e) {

            }
        }

    }

    private static HttpURLConnection createConnection(String url,String method,String encoding,int timeout)
            throws Exception
    {


        URL  urlStr= new URL(url);
        HttpURLConnection   httpURLConnection = (HttpURLConnection)urlStr.openConnection();

        httpURLConnection.setConnectTimeout(timeout*1000);
        httpURLConnection.setReadTimeout(timeout*1000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=" + encoding);
        httpURLConnection.setRequestMethod(method);
        if ("https".equalsIgnoreCase(urlStr.getProtocol())) {
            HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
            husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
            return husn;
        }
        return httpURLConnection;
    }
    

    /**
     * http发送json请求
     *
     * @param url  请求地址，
     * @param json 原始json串
     * @throws Exception
     */

    public static String postJson(String url, String json) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {

            HttpPost httpPost = new HttpPost(url);
           // httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

            StringEntity se = new StringEntity(json,DEFAULT_CHARSET);//解决乱码
            se.setContentEncoding(DEFAULT_CHARSET);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try{
                int code = response.getStatusLine().getStatusCode();

                if (code == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    return  EntityUtils.toString(entity,DEFAULT_CHARSET);
                }else {
                    httpPost.abort();
                    throw new RuntimeException("HttpClient post error,url="+url+" status code :" + code);
                }
            }finally {
                response.close();
            }

        } catch (Exception e) {
            throw  e;
        }finally {
            httpclient.close();
        }

    }




    public static String get(String url, String param) throws Exception {
        return get(url, param, defaultSeconds, CharsetConstant.CHARSET_UTF8);
    }

    /**
     * get发送消息
     *
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String get(String url, String param, int timeout, String charset) throws Exception {
        BufferedReader br = null;
        InputStream in = null;
        try {

            HttpURLConnection connection = createConnection(url,"GET",charset,timeout);

            OutputStreamWriter reqOut = null;
            if (StringUtil.isNotEmpt(param)) {
                reqOut = new OutputStreamWriter(connection.getOutputStream(), charset);
                reqOut.write(param);
                reqOut.flush();
            }
            int charCount = -1;
            in = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in, CharsetConstant.CHARSET_UTF8));
            StringBuffer responseMessage = new StringBuffer();
            while ((charCount = br.read()) != -1) {
                responseMessage.append((char) charCount);
            }
            return responseMessage.toString();
        } catch (Exception e) {

            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
    
    
    public static String sendPostMsg(String url, Map<String, String> paramMap,Map<String,List<String>> paramList) throws Exception {

        return post(url, getNamevaluepairList(paramMap,paramList), DEFAULT_CHARSET,DEFAULT_CHARSET,defaultSoTimeout);

    }
    

    public static String sendPostMsg(String url, Map<String, String> paramMap) throws Exception {

        return post(url, getNamevaluepairList(paramMap), DEFAULT_CHARSET,DEFAULT_CHARSET,defaultSoTimeout);

    }

    public static String sendPostMsg(String url, Map<String, String> paramMap,int timeOut) throws Exception {

        return post(url, getNamevaluepairList(paramMap), DEFAULT_CHARSET,DEFAULT_CHARSET,timeOut);

    }


    public static String doGet(String url,String param)throws Exception{
        return doGet(url, parseQueryString(param), DEFAULT_CHARSET, defaultSoTimeout);
    }


    public static String doGet(String url, Map<String, String> params, String charset,int timeout) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = getNamevaluepairList(params);

                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout*1000).setSocketTimeout(timeout*1000).build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity =null;
            try{
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    httpGet.abort();
                    throw new RuntimeException("HttpClient get error,url="+url+" status code :" + statusCode);
                }
                entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, DEFAULT_CHARSET);

                }

                return result;
            }finally {
                EntityUtils.consume(entity);
                response.close();
            }

        } catch (Exception e) {
            throw e;
        }finally {
            httpClient.close();
        }

    }

    public static String sendPostMsg(String url, String param) throws Exception {
        return sendPostMsg(url, parseQueryString(param));
    }
    public static String sendPostMsg(String url, String param,int timeOut) throws Exception {
        return sendPostMsg(url, parseQueryString(param),timeOut);
    }

    public static String sendPostMsg(String url, String param, String charSet,String returnCharSet, int timeOut) throws Exception {
        return sendPostMsg(url, parseQueryString(param), charSet,returnCharSet, timeOut);

    }

    public static String sendPostMsg(String url, Map<String, String> paramMap, String charSet,String returnCharSet, int timeOut) throws Exception {
        return post(url, getNamevaluepairList(paramMap), charSet,returnCharSet, timeOut);
    }

    protected static List<NameValuePair> getNamevaluepairList(Map<String, String> properties) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (properties == null) {
            return list;
        }

        for (Map.Entry<String, String> entry : properties.entrySet()) {

            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }



    protected static List<NameValuePair> getNamevaluepairList(Map<String, String> properties,Map<String,List<String>> paramsList) {
    	List<NameValuePair> pairList = getNamevaluepairList(properties);
    	if(paramsList!=null) {
    		for(Entry<String, List<String>> entry : paramsList.entrySet()) {
    			if(entry.getValue()!=null&&entry.getValue().size()>0) {
    				for(String value:entry.getValue()) {
    					pairList.add(new BasicNameValuePair(entry.getKey(), value));
    				}
    			}
    		}
    	}
    	return pairList;
    }


    public static String sendSocket(String url, int port, String message, String charSet, int timeOut) throws Exception {
        Socket soc = null;
        String msg = null;
        try {
            if (StringUtils.isBlank(charSet))
                charSet = CharsetConstant.CHARSET_UTF8;
            if (timeOut == 0) {
                timeOut = defaultSoTimeout;
            }
            soc = new Socket(url, port);
            soc.setSoTimeout(timeOut);



            PrintWriter out = new PrintWriter(soc.getOutputStream());

            // 向服务器发送请求
            out.println(message);
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream(), charSet));
            msg = in.readLine();

            in.close();
            out.close();
            soc.close();

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (soc != null && !soc.isClosed())
                    soc.close();
            } catch (IOException e) {

            }
        }

        return msg;

    }

}
