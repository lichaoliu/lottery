package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.zch.ZchConfig;
import com.lottery.ticket.vender.impl.zch.ZchLotteryDef;


public class ZCHVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "011";

	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
		} catch (Exception e) {
			logger.error("中彩汇发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId());
			} catch (Exception e) {
				logger.error("中彩汇查票异常" + e);
			}
		} else {
			logger.error("中彩汇查票返回空");
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @param count
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId) {
		try {
			Map<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
			String code = mapResult.get("code");
			String msg = mapResult.get("msg");
			if ("0000".equals(code)) {// 成功
				List<HashMap<String, String>> mapLists = XmlParse.getElementAttributeList("body/response/", "ticket", desContent);
				List<HashMap<String, String>> mLists = XmlParse.getElementTextList("body/response/", "ticket", desContent);
				for (int i = 0, len = mapLists.size(); i < len; i++) {
					HashMap<String, String> map = mapLists.get(i);
					HashMap<String, String> mapValue = mLists.get(i);
					String status = map.get("status");
					String extendId = map.get("sysId");
					String msgStatus = map.get("msg");
					String ticketId = map.get("ticketId");
					String odds = mapValue.get("odds");
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_ZCH);
					if (StringUtils.isNotBlank(odds)) {
						odds = dealOdds(odds);
						ticketVender.setPeiLv(odds);
					}
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setMessage(msgStatus);
					ticketVender.setExternalId(extendId);
					if ("0".equals(status)) {//
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("1".equals(status)) {//
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("2".equals(status)) {// 成功
						ticketVender.setStatus(TicketVenderStatus.success);
						ticketVender.setPrintTime(new Date());
					} else if ("3".equals(status)) {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					} else if ("999".equals(status)) {// 不存在
						ticketVender.setStatus(TicketVenderStatus.not_found);
					} else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					}
					venderList.add(ticketVender);
				}

			} else if (Constants.zchSendErrorFailed.containsKey(code)) {// 失败
				for (Ticket ticket : ticketBatchList) {
					logger.error("中彩汇订单{}失败,失败原因{}", ticket.getId(), Constants.zchSendErrorFailed.get(code));
				}
			} else {
				logger.error("中彩汇查票返回异常,code={},msg={}", code, msg);
			}
		} catch (Exception e) {
			logger.error("中彩汇查票处理结果异常{}", e);
		}
	}

	/**
	 * 赔率格式转换
	 * 
	 * @param odds
	 * @return
	 */
	public String dealOdds(String odds) {
		String strs[] = StringUtils.split(odds, "|");
		String dd = strs[1].replace("(", "_").replace(",", "|").replace(")", "").replace("/", ",");
		String[] dds = StringUtils.split(dd, "|");
		String result = "";
		int i = 0;
		for (String ds : dds) {
			result += "20" + ds;
			if (i != dds.length - 1) {
				result += ")|";
			}
			i++;
		}
		odds = result.replace("=", "(") + ")";
		StringBuilder stringBuilder = new StringBuilder();

		String numbers[] = StringUtils.split(odds, "|");
		if ("ZHT".equals(strs[0])) {
			int f = 0;
			for (String ss : numbers) {
				String[] s = StringUtils.split(ss.replace("-", ""), "(");
				String num = StringUtils.split(s[0], ":")[1].split("\\_")[0];
				if ("01".equals(num) || "02".equals(num) || "05".equals(num) || "03".equals(num) || "04".equals(num)) {
					stringBuilder.append(StringUtils.split(ss, ":")[0]).append("*").append(ZchLotteryDef.playTypeMapJc.get("jczq" + num)).append("(").append(s[1].replace(":", ""));
				}
				if (f != numbers.length - 1) {
					stringBuilder.append("|");
				}
				f++;
			}
		} else if ("LHT".equals(strs[0])) {
			int f = 0;
			for (String ss : numbers) {
				String[] s = StringUtils.split(ss, "(");
				String num = StringUtils.split(s[0], ":")[1].split("\\_")[0];
				stringBuilder.append(StringUtils.split(ss, ":")[0]).append("*").append(ZchLotteryDef.playTypeMapJc.get("jclq" + num)).append("(");
				if ("05".equals(num)) {
					stringBuilder.append(s[1]);
				} else if ("01".equals(num)) {
					stringBuilder.append(s[1].replace("2", "0").replace("1", "3"));
				} else if ("04".equals(num)) {
					stringBuilder.append(StringUtils.split(s[0], ":")[1].split("\\_")[1]).append(":").append(s[1]);
				} else if ("02".equals(num)) {
					stringBuilder.append(StringUtils.split(s[0], ":")[1].split("\\_")[1]).append(":").append(s[1].replace("2", "0").replace("1", "3"));
				}
				if (f != numbers.length - 1) {
					stringBuilder.append("|");
				}
				f++;
			}
		} else {
			int j = 0;
			for (String ss : numbers) {
				if ("RQS".equals(strs[0]) || "SFD".equals(strs[0]) || "SPF".equals(strs[0])) {
					String[] s = StringUtils.split(ss, "(")[0].split("\\_");
					stringBuilder.append(s[0]).append("(").append(ss.split("\\(")[1]);
				} else if ("SFC".equals(strs[0])) {
					String[] s = StringUtils.split(ss, "(")[0].split("\\_");
					stringBuilder.append(s[0]).append("(").append(ss.split("\\(")[1].split("\\_")[0].replace("2", "0").replace("1", "3")).append("_").append(ss.split("\\(")[1].split("\\_")[1]);
				} else if ("JQS".equals(strs[0]) || "CBF".equals(strs[0])) {
					stringBuilder.append(ss);
				} else if ("BQC".equals(strs[0])) {
					stringBuilder.append(ss.replace("-", ""));
				} else if ("RSF".equals(strs[0])) {
					String[] s = StringUtils.split(ss, "(")[0].split("\\_");
					stringBuilder.append(s[0]).append("(").append(s[1]).append(":").append(ss.split("\\(")[1].split("\\_")[0].replace("2", "0").replace("1", "3")).append("_").append(ss.split("\\(")[1].split("\\_")[1]);

				} else if ("DXF".equals(strs[0])) {
					String[] s = StringUtils.split(ss, "(")[0].split("\\_");
					stringBuilder.append(s[0]).append("(").append(s[1]).append(":").append(ss.split("\\(")[1]);
				}
				if (j != numbers.length - 1) {
					stringBuilder.append("|");
				}
				j++;

			}
		}

		odds = stringBuilder.toString();
		if ("CBF".equals(strs[0])) {
			odds = odds.replace(":", "");
		}
		return odds;
	}

	/**
	 * 查票前拼串
	 * 
	 * @param ticketBatchList
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	private String getElement(List<Ticket> ticketBatchList, IVenderConfig zchConfig) {
		// 头部
		XmlParse xmlParse = ZchConfig.addZchHead(zchConfig.getAgentCode(), queryCode);
		Element element = xmlParse.addBodyElementAndAttribute("tickets", "", new HashMap<String, Object>());

		HashMap<String, Object> bodyAttr = null;
		for (Ticket ticket : ticketBatchList) {
			bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("ticketId", ticket.getId());
			xmlParse.addBodyElementAndAttribute("ticket", "", bodyAttr, element);
		}
		try{
			String md = MD5Util.toMd5(zchConfig.getAgentCode() + zchConfig.getKey() + xmlParse.getBodyElement().asXML());
			xmlParse.addHeaderElement("digest", md);
			return "msg=" + xmlParse.asXML();
		}catch(Exception e){
			logger.error("加密出错",e);
			return null;
		}
	

	}

	@Override
	protected void init() {
		venderCheckerWorkerMap.put(TerminalType.T_ZCH, this);

	}

	@Override
	protected TerminalType getTerminalType() {
		// TODO Auto-generated method stub
		return null;
	}

}
