package com.lottery.ticket.vender.process.fcby;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DES3;
import com.lottery.core.IdGeneratorService;
import com.lottery.ticket.IVenderConfig;
@Service
public class GetJczqDynamicSpFromFcby extends GetJczqDataFromFcby {

	final static public String QUERY_DYNAMIC_SP_LIST_CODE = "121";
	@Autowired
	private IdGeneratorService idGeneratorService;
	@Override
	protected void generateRootElement(Map<String, String> reqMap,
			Document returnDoc, IVenderConfig config) throws Exception {

		Element ticketElement = returnDoc.addElement("ticket");
		Element ctrlElement = ticketElement.addElement("ctrl");
		Element agentIdElment = ctrlElement.addElement("agentid");
		Element cmdElement = ctrlElement.addElement("cmd");
		Element messageidElement = ctrlElement.addElement("messageid");
		Element timestampElement = ctrlElement.addElement("timestamp");
		Element mdElement = ctrlElement.addElement("md");
		Element bodyElement = ticketElement.addElement("body");
		StringWriter sw = new StringWriter(); 
		XMLWriter xo = new XMLWriter(sw);
		
		Element bodyContentElement = bodyElement.addElement("body");
		Element queryElement = bodyContentElement.addElement("querystring");
		queryElement.addAttribute("type", "1");
		queryElement.addAttribute("valueType", "0"); //动态sp值查询为0，静态sp值查询为1
		
		xo.write(bodyContentElement);
		xo.close();
		String queryStr = sw.toString();
		agentIdElment.setText(config.getAgentCode());
		cmdElement.setText(QUERY_DYNAMIC_SP_LIST_CODE);
		String messageId = idGeneratorService.getMessageId();
		messageidElement.setText(messageId);
		String timestampStr=CoreDateUtils.getNowDateYYYYMMDDHHMMSS();//得到格式为YYYYMMDDHHMMSS的时间戳
		timestampElement.setText(timestampStr);
		String encbodyContentStr=DES3.des3EncodeCBC(config.getKey(),queryStr);//进行加密
		String message = URLEncoder.encode(encbodyContentStr, CharsetConstant.CHARSET_UTF8); 
		String md = CoreStringUtils.md5(config.getKey() + message, CharsetConstant.CHARSET_UTF8);
		mdElement.setText(md);
		bodyElement.clearContent();
		bodyElement.setText(message);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JSONArray generateJsonData(Element responseElement, Map<String, String> reqMap)
			throws Exception {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		List<Element> list = responseElement.element("response").element("result").element("matchList").elements("item");
		
		for (Element element : list) {
			String id = element.elementText("id");
			String [] ids = StringUtils.split(id, "_");
			
			jsonObject.put("matchDate", "");
			jsonObject.put("matchName", "");
			jsonObject.put("officialDate", ids[0]);
			String issueID =  ids[0];
			String gameNo = ids[2];
			String matchNum = issueID+gameNo;
			jsonObject.put("matchNum", matchNum);
			jsonObject.put("officialNum", gameNo.substring(gameNo.length() - 3, gameNo.length()));
			
			JSONObject jsonSpObject =  new JSONObject();
				List<LotteryType> lotteryList = new ArrayList<LotteryType>();
				lotteryList.add(LotteryType.JCZQ_RQ_SPF);
				lotteryList.add(LotteryType.JCZQ_SPF_WRQ);
				lotteryList.add(LotteryType.JCZQ_JQS);
				lotteryList.add(LotteryType.JCZQ_BQC);
				lotteryList.add(LotteryType.JCZQ_BF);
				for (int i = 0; i < lotteryList.size(); i++) {
					
					if(lotteryList.get(i).getValue() == LotteryType.JCZQ_RQ_SPF.getValue()){
						gererateSpfJsonData(element, jsonSpObject);
					} else if(lotteryList.get(i).getValue() == LotteryType.JCZQ_SPF_WRQ.getValue()){
						gererateSpfWrqJsonData(element, jsonSpObject);
					} else if (LotteryType.JCZQ_BF.getValue() == lotteryList.get(i).getValue()) {
						gererateBfJsonData(element, jsonSpObject);
					} else if (LotteryType.JCZQ_JQS.getValue() == lotteryList.get(i).getValue()) {
						gererateJqsJsonData(element, jsonSpObject);
					} else if (LotteryType.JCZQ_BQC.getValue() == lotteryList.get(i).getValue()) {
						gererateBqcJsonData(element, jsonSpObject);
					}
				}
		
			jsonObject.put("spmap", jsonSpObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	private void gererateBqcJsonData(Element element, JSONObject jsonSpObject) {
		// 半场胜平负
		Element halfElement = element.element("half");
		jsonSpObject.put("bqc_ss", halfElement.elementText("v33"));
		jsonSpObject.put("bqc_sp", halfElement.elementText("v31"));
		jsonSpObject.put("bqc_sf", halfElement.elementText("v30"));
		jsonSpObject.put("bqc_ps", halfElement.elementText("v13"));
		jsonSpObject.put("bqc_pp", halfElement.elementText("v11"));
		jsonSpObject.put("bqc_pf", halfElement.elementText("v10"));
		jsonSpObject.put("bqc_fs", halfElement.elementText("v03"));
		jsonSpObject.put("bqc_fp", halfElement.elementText("v01"));
		jsonSpObject.put("bqc_ff", halfElement.elementText("v00"));
	}

	private void gererateJqsJsonData(Element element, JSONObject jsonSpObject) {
		// 总进球数
		Element goalElement = element.element("goal");
		jsonSpObject.put("jqs_0", goalElement.elementText("v0"));
		jsonSpObject.put("jqs_1", goalElement.elementText("v1"));
		jsonSpObject.put("jqs_2", goalElement.elementText("v2"));
		jsonSpObject.put("jqs_3", goalElement.elementText("v3"));
		jsonSpObject.put("jqs_4", goalElement.elementText("v4"));
		jsonSpObject.put("jqs_5", goalElement.elementText("v5"));
		jsonSpObject.put("jqs_6", goalElement.elementText("v6"));
		jsonSpObject.put("jqs_7", goalElement.elementText("v7"));
	}

	private void gererateBfJsonData(Element element, JSONObject jsonSpObject) {
		// 全场比分
		Element scoreElement = element.element("score");
		jsonSpObject.put("bf_zs_1_0", scoreElement.elementText("v10"));
		jsonSpObject.put("bf_zs_2_0", scoreElement.elementText("v20"));
		jsonSpObject.put("bf_zs_2_1", scoreElement.elementText("v21"));
		jsonSpObject.put("bf_zs_3_0", scoreElement.elementText("v30"));
		jsonSpObject.put("bf_zs_3_1", scoreElement.elementText("v31"));
		jsonSpObject.put("bf_zs_3_2", scoreElement.elementText("v32"));
		jsonSpObject.put("bf_zs_4_0", scoreElement.elementText("v40"));
		jsonSpObject.put("bf_zs_4_1", scoreElement.elementText("v41"));
		jsonSpObject.put("bf_zs_4_2", scoreElement.elementText("v42"));
		jsonSpObject.put("bf_zs_5_0", scoreElement.elementText("v50"));
		jsonSpObject.put("bf_zs_5_1", scoreElement.elementText("v51"));
		jsonSpObject.put("bf_zs_5_2", scoreElement.elementText("v52"));
		jsonSpObject.put("bf_zs_qt", scoreElement.elementText("v90"));

		jsonSpObject.put("bf_zp_0_0", scoreElement.elementText("v00"));
		jsonSpObject.put("bf_zp_1_1", scoreElement.elementText("v11"));
		jsonSpObject.put("bf_zp_2_2", scoreElement.elementText("v22"));
		jsonSpObject.put("bf_zp_3_3", scoreElement.elementText("v33"));
		jsonSpObject.put("bf_zp_qt", scoreElement.elementText("v99"));

		jsonSpObject.put("bf_zf_0_1", scoreElement.elementText("v01"));
		jsonSpObject.put("bf_zf_0_2", scoreElement.elementText("v02"));
		jsonSpObject.put("bf_zf_1_2", scoreElement.elementText("v12"));
		jsonSpObject.put("bf_zf_0_3", scoreElement.elementText("v03"));
		jsonSpObject.put("bf_zf_1_3", scoreElement.elementText("v13"));
		jsonSpObject.put("bf_zf_2_3", scoreElement.elementText("v23"));
		jsonSpObject.put("bf_zf_0_4", scoreElement.elementText("v04"));
		jsonSpObject.put("bf_zf_1_4", scoreElement.elementText("v14"));
		jsonSpObject.put("bf_zf_2_4", scoreElement.elementText("v24"));
		jsonSpObject.put("bf_zf_0_5", scoreElement.elementText("v05"));
		jsonSpObject.put("bf_zf_1_5", scoreElement.elementText("v15"));
		jsonSpObject.put("bf_zf_2_5", scoreElement.elementText("v25"));
		jsonSpObject.put("bf_zf_qt", scoreElement.elementText("v09"));
	}

	private void gererateSpfJsonData(Element element, JSONObject jsonSpObject) {
		Element vsElement = element.element("letVs");
		jsonSpObject.put("spf_s", vsElement.elementText("v3"));
		jsonSpObject.put("spf_p", vsElement.elementText("v1"));
		jsonSpObject.put("spf_f", vsElement.elementText("v0"));
	}
	
	private void gererateSpfWrqJsonData(Element element, JSONObject jsonSpObject) {
		Element vsElement = element.element("vs");
		jsonSpObject.put("spf_wrq_s", vsElement.elementText("v3"));
		jsonSpObject.put("spf_wrq_p", vsElement.elementText("v1"));
		jsonSpObject.put("spf_wrq_f", vsElement.elementText("v0"));
	}


}
