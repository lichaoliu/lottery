package com.lottery.ticket.vender.impl.fcby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DES3;
import com.lottery.ticket.IVenderConfig;

public class FcbyService {

	private static final Logger logger = LoggerFactory.getLogger(FcbyService.class);

	public final static String XML_MESSAGE_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ticket></ticket>";
	public final static String PHASE_DRAW_DETAIL = "102";
	public final static String TICKET_CHECK = "103";
	public final static String TICKET_SEND_CODE = "109";
	public final static String ACCOUNT_SYNCHRONOUS = "105";
	public final static String TICKET_BATCH_CHECK = "118";//检票

	/** 执行非送票请求动作 */
	public static String request(Map<String, String> requestMap, IVenderConfig config) throws Exception {

		StringBuffer log = new StringBuffer();
		log.append("请求地址 : " + config.getRequestUrl() + " ; ");
		String requestString = CoreHttpUtils.getQueryString(requestMap);
		log.append("请求消息 : " + requestString + " ; ");
		String response = doRequest(requestString, config.getRequestUrl(), log);
		log.append("响应消息 : " + response + " ; ");

		return verify(response, config, log);
	}

	/** 执行基础校验 */
	private static String verify(String responseString, IVenderConfig ruiCai2013Config, StringBuffer log) {
		try {
			Document responseDoc=convertStringToDocument(responseString);
			Element res = responseDoc.getRootElement();
			String bodyStr=res.getChildText("body");
			Element ctrl = res.getChild("ctrl");
			String verifyCode = ctrl.getChildText("md");

			String result = ctrl.getChildText("result");
			
			log.append("返回码 : " + result + " ; ");

			boolean match = verifyCode.equals(CoreStringUtils.md5(ruiCai2013Config.getKey() + bodyStr, CharsetConstant.CHARSET_UTF8));

			if (match) {
				log.append("数据验证通过!");
			} else {
				log.append("数据验证未通过!");
				logger.error(log.toString());
				return null;
			}

			if ("0".equals(result)) {
				return res.getChildText("body");
			} else {
				log.append(" --> 全局返回码不为0,说明动作执行失败,不需要进一步处理!");
				logger.error(log.toString());
				return null;
			}

		} catch (Exception e) {
			log.append(" --> 初步解析数据出现异常!");
			logger.error(log.toString());
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/** 生成请求Map */
	public static Map<String, String> generatorRequestMap(IVenderConfig ruiCai2013Config, String cmdcode, String messageId,Element bodyElement) {
		XMLOutputter xo = new XMLOutputter();
		Map<String, String> requestMap = null;
		try {
			
			SAXBuilder sb = new SAXBuilder();
			Reader reader = new StringReader(XML_MESSAGE_DECLARATION);
			Document doc = sb.build(reader);
			Element root = doc.getRootElement();

			Element ctrl =new Element("ctrl");
			Element agentid =new Element("agentid");
			agentid.setText(ruiCai2013Config.getAgentCode());
			Element cmd =new Element("cmd");
			cmd.setText(cmdcode);
			Element messageid =new Element("messageid");
			messageid.setText(messageId);
			String timestampStr=CoreDateUtils.getNowDateYYYYMMDDHHMMSS();//得到格式为YYYYMMDDHHMMSS的时间戳
			Element timestamp =new Element("timestamp");
			timestamp.setText(timestampStr);
			Element md =new Element("md");
			String bodyContentStr=xo.outputString(bodyElement);//请求/响应消息体即<body>和</body>之间的内容，但还包括<body>和</body>。
			String encbodyContentStr=DES3.des3EncodeCBC(ruiCai2013Config.getKey(), bodyContentStr);//进行加密
			String message = URLEncoder.encode(encbodyContentStr, CharsetConstant.CHARSET_UTF8);  
			md.setText(CoreStringUtils.md5(ruiCai2013Config.getKey() + message, CharsetConstant.CHARSET_UTF8));
			
			ctrl.addContent(agentid);
			ctrl.addContent(cmd);
			ctrl.addContent(messageid);
			ctrl.addContent(timestamp);
			ctrl.addContent(md);
			
			Element body =new Element("body");
			body.setText(message);
		
			root.addContent(ctrl);
			root.addContent(body);
			doc.setRootElement(root);
			
			String requestString =convertDocumentToString(doc);
			requestMap = new HashMap<String, String>();
			requestMap.put("msg", requestString);

		} catch (Exception e) {
			logger.error("生成请求消息体失败!");
			logger.error(e.getMessage(), e);
			return null;
		}
		return requestMap;
	}

	/** 将字符串转成Document格式 */
	public static Document convertStringToDocument(String msg) throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		Reader in = new StringReader(msg);
		doc = builder.build(in);
		return doc;
	}

	/** 将Document格式转成字符串 */
	public static String convertDocumentToString(Document doc) {
		XMLOutputter xo = new XMLOutputter();
		Format format = xo.getFormat();
		format.setEncoding("UTF-8");
		format.setLineSeparator("");
		xo.setFormat(format);
		return xo.outputString(doc);
	}
	
	/** 执行请求 */
	private static String doRequest(String data, String url, StringBuffer log) throws Exception {

		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		String response = null;
		try {
			URL urlObj = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = urlObj.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			conn.setConnectTimeout(10000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);// 发送POST请求必须设置如下两行
			conn.setDoInput(true);

			wr = new OutputStreamWriter(conn.getOutputStream());// 获取URLConnection对象对应的输出流s
			wr.write(data);// 发送请求参数
			wr.flush();// flush输出流的缓冲

			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));// 定义BufferedReader输入流来读取URL的响应

			int n;
			StringBuffer buffer = new StringBuffer();
			while ((n = rd.read()) != -1) {
				buffer.append((char) n);
			}

			response = buffer.toString();
			return response;
		} catch (SocketTimeoutException e) {
			log.append(" --> 网络连接超时");
			logger.error(log.toString());
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.append(" --> 请求异常");
			logger.error(log.toString());
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			// 使用finally块来关闭输出流,输入流
			try {
				if (wr != null) {
					wr.close();
				}
				if (rd != null) {
					rd.close();
				}
			} catch (IOException ex) {
				log.append(" --> 关闭输入输出流失败!");
				logger.error(log.toString());
			}
		}
	}
}