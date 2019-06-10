package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huay.HuayLotteryDef;
import com.lottery.ticket.vender.impl.huay.HuayService;
@Component
public class HYVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {


	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		return dealResult(ticketList, venderConfig);
	}



	/**
	 * 查票结果处理
	 *
	 * @param desContent
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private List<TicketVender> dealResult(List<Ticket> ticketBatchList, IVenderConfig venderConfig) {
		List<TicketVender> ticketvenderList = new ArrayList<TicketVender>();
		String messageStr="";
		String returnStr="";
		try {

			Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
			for (Ticket ticket : ticketBatchList) {
				ticketMap.put(ticket.getId(), ticket);
			}
			messageStr = getElement(ticketBatchList, venderConfig);
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
            
			logger.error("华阳请求内容为{}，返回为{}",messageStr,returnStr);
			HashMap<String, String> mapCode = XmlParse.getElementText("body/", "oelement", returnStr);
			String code = mapCode.get("errorcode");
			if (code.equals("0")) {
				List<HashMap<String, String>> mapLists = XmlParse.getElementTextList("body/elements", "element", returnStr);
				if(mapLists==null||mapLists.isEmpty()){
					for (Ticket ticket : ticketBatchList) {
						TicketVender ticketVender = createTicketVender(venderConfig.getTerminalId(), TerminalType.T_HY);
						ticketVender.setStatus(TicketVenderStatus.not_found);
						ticketVender.setMessage("不存在此票");
						ticketVender.setId(ticket.getId());
						ticketvenderList.add(ticketVender);
						ticketVender.setResponseMessage(returnStr);
					}
					return ticketvenderList;
				}
				for (HashMap<String, String> map : mapLists) {
					String ticketId = map.get("id");
					String externalId = map.get("ticketid");
					String status = map.get("status");
					String printStr = map.get("tickettime");
					String odds = map.get("spvalue");

					TicketVender ticketVender = createTicketVender(venderConfig.getTerminalId(), TerminalType.T_HY);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setExternalId(externalId);
					ticketVender.setResponseMessage(returnStr);
					ticketvenderList.add(ticketVender);
					if ("0".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.unkown);
						ticketVender.setMessage("不可出票");
					} else if ("1".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.printing);
						ticketVender.setMessage("可出票");
					} else if ("2".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.success);
						ticketVender.setMessage("出票成功");
						ticketVender.setOtherPeilv(odds);
						if (StringUtils.isNotBlank(odds)) {
							Ticket ticket = ticketMap.get(ticketId);
							String peilv = dealOdds(odds, ticket.getPlayType().toString().substring(0, 4));
							ticketVender.setPeiLv(peilv);
						}
						if (printStr == null) {
							ticketVender.setPrintTime(new Date());
						} else {
							ticketVender.setPrintTime(CoreDateUtils.parseDateTime(printStr));
						}
					} else if ("3".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.printing);
						ticketVender.setMessage("允许再出票");
					} else if ("4".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.printing);
						ticketVender.setMessage("出票中");
					} else if ("5".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.printing);
						ticketVender.setMessage("出票中");
					} else if ("6".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.failed);
						ticketVender.setMessage("出票失败");
					} else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
						ticketVender.setMessage("未知异常");
					}

				}
			} else {
				logger.error("华阳查票返回结果异常,发送:{},返回:{}", messageStr, returnStr);
			}

		} catch (Exception e) {
			logger.error("华阳查票异常,发送:{},返回:{},异常为", messageStr, returnStr,e);
		}
		return ticketvenderList;
	}

	/**
	 * 赔率格式转换
	 *
	 * @param odds
	 * @return
	 */
	public static String dealOdds(String odds, String playType) {
		if (StringUtils.isNotBlank(odds)) {
			StringBuffer buffer = new StringBuffer();
			if(Integer.parseInt(playType) != LotteryType.JCLQ_RFSF.getValue()&&Integer.parseInt(playType) != LotteryType.JCLQ_DXF.getValue()&&Integer.parseInt(playType) != LotteryType.JCLQ_HHGG.getValue()){
				odds=odds.replace("-", "");
			}
			String[] numbers = StringUtils.split(odds, ";");
			int i = 0;
			if (odds.contains("^")) {
				int playTypeStr=Integer.parseInt(odds.split("\\^")[0]);
				if(playTypeStr>=214){
					for (String num : numbers) {
						playTypeStr=Integer.parseInt(StringUtils.split(num, "^")[0]);
						buffer.append(StringUtils.split(num, "^")[1].split("\\(")[0].replace("-","")).append("*");
						buffer.append(HuayLotteryDef.toplayTypeMapJcMix.get(StringUtils.split(num, "^")[0])).append("(");
						if(playTypeStr==214||playTypeStr==215){
							String []nums=num.split("\\^")[1].split("\\(")[1].replace(")", "").split("\\,");

							int f=0;
							buffer.append(nums[0].split("\\_")[1]).append(":");
							for(String tt:nums){
								String []tts=tt.split("\\_");
								buffer.append(tts[0]).append("_").append(tts[2]);
								if(f<nums.length-1){
									buffer.append(",");
								}else{
									buffer.append(")");
								}
								f++;
							}

						}else if(playTypeStr==217){
							String []strs=StringUtils.split(num, "^")[1].split("\\(")[1].split("\\,");
							int f=0;
							for(String str:strs){
								String con=str.split("\\_")[0].replace("11", "15").replace("12", "16").replace("7", "11").replace("8", "12").replace("9", "13").replace("10", "14");
								if(Integer.parseInt(con)<=9){
									buffer.append("0").append(con).append("_").append(str.split("\\_")[1]);
								}else{
									buffer.append(con).append("_").append(str.split("\\_")[1]);
								}
								if(f<strs.length-1){
									buffer.append(",");
								}
								f++;
							}
						}else{
							buffer.append(StringUtils.split(num, "^")[1].split("\\(")[1]);
						}

						if (i != numbers.length - 1) {
							buffer.append("|");
						}
						i++;
					}
				}else{
					for (String num : numbers) {
						buffer.append("20").append(StringUtils.split(num, "^")[1].split("\\(")[0]).append("*");
						buffer.append(HuayLotteryDef.toplayTypeMapJcMix.get(StringUtils.split(num, "^")[0])).append("(");
						buffer.append(StringUtils.split(num, "^")[1].split("\\(")[1]);
						if (i != numbers.length - 1) {
							buffer.append("|");
						}
						i++;
					}
				}

			} else {
				for (String num : numbers) {
					if (StringUtils.split(odds, "-")[0].length() == 6) {
						buffer.append("20").append(num);
					}else if(LotteryType.getJczq().contains(LotteryType.get(Integer.parseInt(playType)))&&Integer.parseInt(playType)!=LotteryType.JCZQ_HHGG.getValue()) {
						buffer.append("20").append(num);
					}else {
						if (Integer.parseInt(playType) == LotteryType.JCLQ_SFC.getValue()) {
							String[] nums = num.split("\\(")[1].split("\\)")[0].split("\\,");
							buffer.append(StringUtils.split(num, "(")[0]).append("(");
							int j = 0;
							for (String number : nums) {
								buffer.append(HuayLotteryDef.tonumberMap.get(number.split("\\_")[0])).append("_");
								buffer.append(number.split("\\_")[1]);
								if (j != nums.length - 1) {
									buffer.append(",");
								}
								j++;
							}
							buffer.append(")");
						} else if (Integer.parseInt(playType) == LotteryType.JCLQ_RFSF.getValue()||Integer.parseInt(playType) == LotteryType.JCLQ_DXF.getValue()){
							String []nums=num.split("\\(")[1].replace(")", "").split("\\,");
							buffer.append(num.split("\\(")[0].replace("-", "")).append("(");
							int f=0;
							buffer.append(nums[0].split("\\_")[1]).append(":");
							for(String tt:nums){
								String []tts=tt.split("\\_");
								buffer.append(tts[0]).append("_").append(tts[2]);
								if(f<nums.length-1){
									buffer.append(",");
								}else{
									buffer.append(")");
								}
								f++;
							}

						}else{
							buffer.append(num);
						}

					}
					if (i != numbers.length - 1) {
						buffer.append("|");
					}
					i++;
				}
			}
			odds = buffer.toString();
		}
		return odds;
	}

	/**
	 * 查票前拼串
	 *
	 * @param ticketBatchList
	 *            票集合
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */

	private String getElement(List<Ticket> ticketBatchList, IVenderConfig huayConfig) throws Exception {
		String queryCode = "13004";
		if(huayConfig.getPort()!=null&&huayConfig.getPort()!=0){
			queryCode="21004";
		}
		// 头部
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		String messageid = idGeneratorService.getMessageId();
		XmlParse xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), queryCode, messageid, timestamp);
		Element bodyeElement = xmlParse.getBodyElement();
		Element elements = bodyeElement.addElement("elements");
		HashMap<String, String> bodyAttr = null;
		for (Ticket ticket : ticketBatchList) {
			bodyAttr = new HashMap<String, String>();
			bodyAttr.put("id", ticket.getId());
			Element element2 = elements.addElement("element");
			xmlParse.addBodyElement(bodyAttr, element2);
		}
		String md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
		xmlParse.addHeaderElement("digest", md);
		return xmlParse.asXML();

	}

	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_HY;
	}


}
