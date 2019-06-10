package com.lottery.common.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.*;

import java.util.*;

/**
 * xml解析类
 * */
public class XMLParseTool {
	/**
	 * 获取属性值
	 * 
	 * @param nodeElement
	 * @return
	 */
	public static HashMap<String, String> getElementAttribute(Element nodeElement) {
		if (nodeElement != null) {
			HashMap<String, String> returnMap = new HashMap<String, String>();
			for (Iterator<?> it = nodeElement.attributeIterator(); it.hasNext();) {
				Attribute nodeAttribute = (Attribute) it.next();
				returnMap.put(nodeAttribute.getName(), nodeAttribute.getText());
			}
			return returnMap;
		}
		return null;
	}

	/**
	 * 解析element 得到根节点
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public static Element getRootElement(String strs) throws DocumentException {
		Document doc = DocumentHelper.parseText(strs);
		Element rootElt = doc.getRootElement();
		return rootElt;
	}

	/**
	 * 解析xml下迭代 list属性
	 * */
	public static List<HashMap<String, String>> getElementTextList(String xml, String rootElement, String nodeName) throws DocumentException {
		Element rootElt = getRootElement(xml);
		List<HashMap<String, String>> lists = new ArrayList<HashMap<String, String>>();
		Iterator<?> iterator = null;
		String[] nodePathList = StringUtils.split(nodeName, "/");
		for (int i = 0; i < nodePathList.length; i++) {
			rootElt = rootElt.element(nodePathList[i] + "");
		}
		try {
			iterator = rootElt.elementIterator(rootElement);
		} catch (Exception e) {
			return null;
		}
		while (iterator.hasNext()) {
			HashMap<String, String> returnMap = new HashMap<String, String>();
			Element ele = (Element) iterator.next();
			List<?> nodes = ele.elements();
			for (Iterator<?> it1 = nodes.iterator(); it1.hasNext();) {
				Element node = (Element) it1.next();
				returnMap.put(node.getName(), node.getText());
			}
			lists.add(returnMap);
		}
		return lists;
	}

	/**
	 * 解析xml下迭代 属性 name、sex
	 * 
	 * @param strs
	 *            要解析的xml串
	 * @param nodeName
	 *            取得节点父节点 /response
	 * @param rootElement
	 *            获取循环根节点 alipay_results <?xml version=\"1.0\"
	 *            encoding=\"utf-8\"?><alipay><response>
	 *            <alipay_results><name>zhang
	 *            </name><sex>1</sex></alipay_results> </response></alipay>
	 * @return
	 * @throws DocumentException
	 */
	public static HashMap<String, String> getElementText(String nodeName, String rootElement, String strs) throws DocumentException {
		Element ele = getElement(nodeName, rootElement, strs);
		HashMap<String, String> returnMap = new HashMap<String, String>();
		List<?> nodes = ele.elements();
		for (Iterator<?> it1 = nodes.iterator(); it1.hasNext();) {
			Element node = (Element) it1.next();
			returnMap.put(node.getName(), node.getText());
		}
		return returnMap;
	}

	public static Element getElemText(String nodeName, String strs) throws DocumentException {
		Element rootElt = getRootElement(strs);
		Element element = rootElt.element(nodeName);
		return element;
	}

	/**
	 * 解析xml下迭代 属性 name、sex
	 * 
	 * @param strs
	 *            要解析的xml串
	 * @param nodeName
	 *            取得节点父节点 /response
	 * @param rootElement
	 *            获取循环根节点 alipay_results <?xml version=\"1.0\"
	 *            encoding=\"utf-8\"?><alipay><response>
	 *            <alipay_results><name>zhang
	 *            </name><sex>1</sex></alipay_results> </response></alipay>
	 * @return
	 * @throws DocumentException
	 */
	public static HashMap<String, String> getElementAttribute(String nodeName, String rootElement, String strs) throws DocumentException {
		Element ele = getElement(nodeName, rootElement, strs);
		HashMap<String, String> returnMap = new HashMap<String, String>();
		List<?> nodes = ele.attributes();
		for (Iterator<?> it1 = nodes.iterator(); it1.hasNext();) {
			Attribute node = (Attribute) it1.next();
			returnMap.put(node.getName(), node.getValue());
		}
		return returnMap;
	}

	/**
	 * 读取属性list
	 * 
	 * @param nodeName
	 *            body/response/result/
	 * @param rootElement
	 *            code
	 * @param xmlStr
	 * @return"<ticket><body><response errorcode=\"错误码\"><result>
	 *                                 <code ordered=\"2011122334444555\" state=\"5\" amount =\"500\" preamount =\"500\"/>
	 * <code ordered=\"2011122334444556\" state=\"4\" amount =\"0\" preamount =\"0\"/>
	 * </result></response></body></ticket>";
	 * @throws DocumentException
	 */
	public static List<HashMap<String, String>> getElementAttributeList(String xmlStr, String rootElement, String nodeName) throws DocumentException {
		Element rootElt = getRootElement(xmlStr);
		Iterator<?> iterator = null;
		String[] nodePathList = StringUtils.split(nodeName, "/");
		for (int i = 0; i < nodePathList.length; i++) {
			rootElt = rootElt.element(nodePathList[i] + "");
		}
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Element ele = null;
		iterator = rootElt.elementIterator(rootElement);
		while (iterator.hasNext()) {
			HashMap<String, String> returnMap = new HashMap<String, String>();
			ele = (Element) iterator.next();
			List<?> nodes = ele.attributes();
			for (Iterator<?> it1 = nodes.iterator(); it1.hasNext();) {
				Attribute node = (Attribute) it1.next();
				returnMap.put(node.getName(), node.getValue());
			}
			list.add(returnMap);
		}
		return list;
	}

	public static Element getElement(String xmlStr, String rootElement, String nodeName) throws DocumentException {
		Element rootElt = getRootElement(xmlStr);
		Iterator<?> iterator = null;
		String[] nodePathList = StringUtils.split(nodeName, "/");
		for (int i = 0; i < nodePathList.length; i++) {
			rootElt = rootElt.element(nodePathList[i] + "");
		}
		Element ele = null;
		try {
			iterator = rootElt.elementIterator(rootElement);
		} catch (Exception e) {
			return null;
		}
		if (iterator.hasNext()) {
			ele = (Element) iterator.next();
		}
		return ele;
	}
	
	
	public static Element addBodyElement(Map<String, String> nodeMap,Element element) {
		if ((nodeMap != null) && (!nodeMap.isEmpty())) {
			Iterator<?> it = nodeMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				addBodyElement(key, (String) nodeMap.get(key),element);
			}
		}
		return element;
	}
	
	public static void  addBodyElement(String nodeName, String content,Element element) {
		if ((content != null) && (content.length() > 0))
			element.addElement(nodeName).setText(content);
	}
	
}
