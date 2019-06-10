package com.lottery.ticket.vender.process.fcby;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.RaceSaleStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DES3;
import com.lottery.core.IdGeneratorService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.fcby.FcbyConverter;
@Service
public class GetJczqScheduleFromFcby extends GetJczqDataFromFcby {

	final static public String QUERY_SALE_PHASE_LIST_CODE = "119";
	@Autowired
	private IdGeneratorService idGeneratorService;
	@Autowired
	private FcbyConverter fcbyConverter;
	protected void generateRootElement(Map<String, String> reqMap, Document returnDoc, IVenderConfig config) throws Exception {
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
		
		xo.write(bodyContentElement);
		xo.close();
		String queryStr = sw.toString();
		agentIdElment.setText(config.getAgentCode());
		cmdElement.setText(QUERY_SALE_PHASE_LIST_CODE);
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
	protected JSONArray generateJsonData(Element responseElement,
			Map<String, String> reqMap) throws Exception {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		List<Element> list = responseElement.element("response").element("result").element("vsList").elements("vs");
		
		for (Element element : list) {
			String time = CoreDateUtils.formatDate(element.elementText("time"), CoreDateUtils.DATETIME, FORMATTIME);
			jsonObject.put("matchDate", element.elementText("time"));
			jsonObject.put("matchName", element.elementText("league"));
			jsonObject.put("officialDate", element.elementText("time"));
			String issueID =  element.elementText("day");
			String gameNo = element.elementText("teamId");
			String matchNum = issueID+gameNo;
			jsonObject.put("phaseNo", issueID);
			jsonObject.put("matchNum", matchNum);
			jsonObject.put("endSaleTime",  element.elementText("endTime"));
			jsonObject.put("officialNum", gameNo.substring(gameNo.length() - 3, gameNo.length()));
			jsonObject.put("officialWeekDay", element.elementText("weekId"));
			// salesStatus返回的是赛程的销售状态,随着比赛的进行,有的比赛可能取消或者延期
			// gameStatus=未赛的时候,salesStatus="1-可销售,gameStatus=已赛,赛中,延期,取消的时候,salesStatus=0-不可销售
			String salesStatus = element.elementText("saleFlag");
			if ("0".equals(salesStatus)) {
				jsonObject.put("status", RaceStatus.OPEN.getValue());
			} else if ("1".equals(salesStatus)) {
				jsonObject.put("status", RaceStatus.UNOPEN.getValue());
			}
			String league = element.elementText("team");
			// 瑞彩暂时未提供过关的让球值，等待后续添加
			jsonObject.put("handicap", element.elementText("letBall")==null?"":element.elementText("letBall"));
			jsonObject.put("homeTeam", league.split(":")[0]);
			jsonObject.put("awayTeam", league.split(":")[1]);

			jsonObject.put("staticSaleSpfStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("dynamicSaleSpfStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("staticSaleSpfWrqStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("dynamicSaleSpfWrqStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("staticSaleBfStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("dynamicSaleBfStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("staticSaleJqsStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("dynamicSaleJqsStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("staticSaleBqcStatus", RaceSaleStatus.SALE_OPEN.getValue());
			jsonObject.put("dynamicSaleBqcStatus", RaceSaleStatus.SALE_OPEN.getValue());

            List<Element> unsupportElementList = element.elements("unsupport");
            if (unsupportElementList != null && !unsupportElementList.isEmpty()) {
                List<Element> unsupportElementItemList = unsupportElementList.iterator().next().elements("item");
                if (unsupportElementItemList != null && !unsupportElementItemList.isEmpty()) {
                    List<LotteryType> lotteryList = LotteryType.getJczq();
                    for (LotteryType lotteryType : lotteryList) {
                    	if(lotteryType==LotteryType.JCZQ_HHGG){
                    		continue;
                    	}
                        String lotteryId = fcbyConverter.convertLotteryType(lotteryType);
                        for (Element unsupportElementItem : unsupportElementItemList) {
                            if (lotteryId.equals(unsupportElementItem.elementText("lotteryId"))) {
                                if ("0".equals(unsupportElementItem.elementText("type"))) {
                                    if (lotteryType.getValue() == LotteryType.JCZQ_RQ_SPF.getValue()){
                                        jsonObject.put("dynamicSaleSpfStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_SPF_WRQ.getValue()){
                                        jsonObject.put("dynamicSaleSpfWrqStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_JQS.getValue()){
                                        jsonObject.put("dynamicSaleJqsStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_BQC.getValue()){
                                        jsonObject.put("dynamicSaleBqcStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_BF.getValue()){
                                        jsonObject.put("dynamicSaleBfStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    }
                                } else {
                                    if (lotteryType.getValue() == LotteryType.JCZQ_RQ_SPF.getValue()){
                                        jsonObject.put("staticSaleSpfStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_SPF_WRQ.getValue()){
                                        jsonObject.put("staticSaleSpfWrqStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_JQS.getValue()){
                                        jsonObject.put("staticSaleJqsStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_BQC.getValue()){
                                        jsonObject.put("staticSaleBqcStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    } else if(lotteryType.getValue() == LotteryType.JCZQ_BF.getValue()){
                                        jsonObject.put("staticSaleBfStatus", RaceSaleStatus.SALE_UNOPEN.getValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
