package com.lottery.ticket.vender.process.fcby;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DES3;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.fcby.FcbyService;
@Service
public class GetJczqDrawResultFromFcby extends GetJczqDataFromFcby {

    @Autowired
    private IdGeneratorService idGeneratorService;
	private final static  String QUERY_PHASE_DRAW_RESULT_CODE = "120";
	@Autowired
	private JczqRaceService jczqRaceService;
	public static Map<String, String> WEEKSTR = new HashMap<String, String>();
	
	static {
		WEEKSTR.put("周日", "7");
		WEEKSTR.put("周一", "1");
		WEEKSTR.put("周二", "2");
		WEEKSTR.put("周三", "3");
		WEEKSTR.put("周四", "4");
		WEEKSTR.put("周五", "5");
		WEEKSTR.put("周六", "6");
	}
	@Override
	public JSONArray perform(Map<String, String> reqMap, IVenderConfig config) {

		JSONArray jarray = new JSONArray();
		Map<String, String> requestMap = new HashMap<String, String>();
        String phase = reqMap.get("phase");

        List<JczqRace> closedRaceList = null;
        try {
        	List<Integer> statusList=new ArrayList<Integer>();
        	
        	closedRaceList=jczqRaceService.getByPhaseAndStatus(phase, statusList);
		}catch (Exception e) {
			logger.error("API获取赛程列表失败异常!", e);
			return jarray;
		}
		
		if (closedRaceList == null || closedRaceList.isEmpty()){
			return jarray;
		}
		for (JczqRace jczqRace : closedRaceList) {
			Document returnDoc = DocumentHelper.createDocument();
			try {
				String matchNum = jczqRace.getMatchNum();
				String raceNum = matchNum.substring(matchNum.length()-3, matchNum.length());
				String weekId = WEEKSTR.get(jczqRace.getOfficialWeekDay());
				String requestId = phase + "_" + weekId + "_" + raceNum;
				generateRequestElement(requestId, reqMap, returnDoc, config);
				StringWriter sw = new StringWriter(); 
				XMLWriter xo = new XMLWriter(sw);
				xo.write(returnDoc);
				xo.close();
				requestMap.put("msg", sw.toString());
				String response = FcbyService.request(requestMap, config);
				
				if (response == null) {
					logger.error("{}瑞彩2013数据处理失败,请求内容={},响应内容为空", new Object[] { logger.getName(), xo.toString() });
					continue;
				}
				//对body中的信息进行解码
				String responseElementDec=DES3.des3DecodeCBC(config.getKey(), response);
				Document responseElement = DocumentHelper.parseText(responseElementDec);
				JSONObject jo = generateSingleFcbyJsonData(requestId, responseElement.getRootElement(), reqMap);
				if(jo != null){
					jarray.add(jo);
				}
				
			} catch (Exception e) {
				logger.error("GetJczqDataFromRuiCai2013:获取数据 发生异常", e);
			}
			
		}
		return jarray;
	}

	protected void generateRequestElement(String requestId, Map<String, String> reqMap,
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
		queryElement.addAttribute("match", requestId); //动态sp值查询为0，静态sp值查询为1
		
		xo.write(bodyContentElement);
		xo.close();
		String queryStr = sw.toString();
		agentIdElment.setText(config.getAgentCode());
		cmdElement.setText(QUERY_PHASE_DRAW_RESULT_CODE);
		String messageId = idGeneratorService.getMessageId();// 最长32位
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

	@Override
	protected void generateRootElement(Map<String, String> reqMap,
			Document returnDoc, IVenderConfig config) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected JSONArray generateJsonData(Element responseElement,
			Map<String, String> reqMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected JSONObject generateSingleFcbyJsonData(String requestId, Element responseElement, Map<String, String> reqMap) throws Exception {
		
		JSONObject jsonObject = new JSONObject();
		Element response = responseElement.element("response");
		if(!"0".equals(response.attributeValue("errorcode"))){
			logger.error("无该赛程的奖金数据，返回码：{},requestId={}", response.attributeValue("errorcode"), requestId);
			return null;
		}
		Element resultElement = response.element("result");
		String officialDate = StringUtils.split(requestId, "_")[0];
		String gameNo = StringUtils.split(requestId, "_")[2];
		jsonObject.put("matchName", "");
		jsonObject.put("matchDate", "");
		jsonObject.put("officialDate", officialDate);
		String matchNum = officialDate+gameNo;
		jsonObject.put("matchNum", matchNum);
		jsonObject.put("officialNum", gameNo.substring(gameNo.length() - 3, gameNo.length()));
		jsonObject.put("homeTeam", "");
		jsonObject.put("awayTeam", "");

		Element singleBonusElement = resultElement.element("singleBonus");
		// 胜平负奖金,浮动奖金投注的开奖奖金
		jsonObject.put("prizeSpf", "1".equals(singleBonusElement.elementText("b1"))?"":singleBonusElement.elementText("b1"));
		// 胜平负奖金,浮动奖金投注的开奖奖金
		jsonObject.put("prizeSpfWrq", "1".equals(singleBonusElement.elementText("b0"))?"":singleBonusElement.elementText("b0"));
		// 全场比分奖金,浮动奖金投注的开奖奖金
		jsonObject.put("prizeBf", "1".equals(singleBonusElement.elementText("b4"))?"":singleBonusElement.elementText("b4"));
		// 进球总数奖金,浮动奖金投注的开奖奖金
		jsonObject.put("prizeJqs", "1".equals(singleBonusElement.elementText("b5"))?"":singleBonusElement.elementText("b5"));
		// 半全场胜平负奖金,浮动奖金投注的开奖奖金
		jsonObject.put("prizeBqc","1".equals(singleBonusElement.elementText("b6"))?"":singleBonusElement.elementText("b6"));

		String halfScore = resultElement.elementText("firstHalfResult");
		String fullScore = resultElement.elementText("result");
		// 上半场比分
		jsonObject.put("firstHalf", halfScore);
		// 最终比分
		jsonObject.put("finalScore", fullScore);
		return jsonObject;
	}
	

	
}
