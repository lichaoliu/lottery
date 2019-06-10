package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.ruiying.RuiYingConfig;
import com.lottery.ticket.vender.impl.ruiying.RuiYingConverter;


@Component
public class RuiYingVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private static final String TRADE_CODE = "102"; // 交易代码:102-票处理结果查询



	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig, 
			IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}

		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);

		} catch (Exception e) {
			logger.error("瑞盈检票参数:{},返回:{}", messageStr, returnStr);
			logger.error(e.getMessage(),e);
			return venderList;
		}

		if (StringUtils.isEmpty(returnStr)) {
			logger.error("瑞盈查票返回空");
			return venderList;
		}

		// 查票处理结果
		try {
			dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(), venderConverter);
		} catch (Exception e) {
			logger.error("瑞盈查票异常",e);
		}

		return venderList;
	}

	/**
	 * 查询拼串
	 * 
	 * @param tickets
	 * 			票集合
	 * @param venderConfig
	 * 			出票终端配置
	 * @return
	 */
	public String getElement(List<Ticket> tickets, IVenderConfig venderConfig) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = RuiYingConfig.addHead(TRADE_CODE, venderConfig.getAgentCode(), timestamp);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		Element body = xmlParse.addBodyElement(bodyzAttr, xmlParse.getBodyElement()).addElement("ticketQuery");
		for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("tid", ticket.getId());
			xmlParse.addBodyElementAndAttribute("ticket", null, bodyAttr, body);
		}

		String wMsg = xmlParse.asXML().replace("\n", "");
		StringBuilder signSb = new StringBuilder();
		signSb.append(venderConfig.getAgentCode()).append(TRADE_CODE).append(wMsg).append(venderConfig.getKey());

		StringBuilder sb = new StringBuilder();
		try {
			sb.append("wAgent=").append(venderConfig.getAgentCode())
			.append("&wCmd=").append(TRADE_CODE)
			.append("&wMsg=").append(wMsg)
			.append("&wSign=").append(MD5Util.toMd5(signSb.toString()));
		} catch (Exception e) {
			
		}		

		return sb.toString();
	}

	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, 
			Long terminalId, IVenderConverter venderConverter) {
		try {
			if (StringUtils.isBlank(desContent)) {
				logger.warn("瑞盈查票处理结果为空");
				return;
			}

			Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
			for (Ticket ticket : ticketBatchList) {
				ticketMap.put(ticket.getId(), ticket);
			}

			desContent = desContent.substring(0, desContent.indexOf("</response>")) + "</response>";
			HashMap<String, String> mapResult = XmlParse.getElementText("/", "head", desContent);;
			String errorcode = mapResult.get("errCode");
			if ("0".equals(errorcode)) {
				List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/ticketQuery/", "ticket", desContent);
				if (mapList == null || mapList.size() == 0) {
					logger.warn("瑞盈检票xml解析内容为空");
					return;
				}

				for (HashMap<String, String> map: mapList) {
					String ticketId = map.get("tid");
					String status = map.get("status");
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_RUIYING);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);

					if ("1".equals(status)) {// 1-出票成功
						if (map.containsKey("time"))
							ticketVender.setPrintTime(DateUtil.parse(map.get("time")));
						else
							ticketVender.setPrintTime(new Date());

						ticketVender.setStatus(TicketVenderStatus.success);
						Ticket ticket = ticketMap.get(ticketId);

						String sp = map.get("sp");
						if (StringUtils.isNotBlank(sp)) {
							RuiYingConverter converter =(RuiYingConverter) venderConverter;
							String peilv = converter.convertSp(ticket, sp); 
							ticketVender.setPeiLv(peilv);
							ticketVender.setOtherPeilv(sp);
						}
					} else if ("2002".equals(status)) {//2002-投注中
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("2001".equals(status) || "2003".equals(status) ) {// 2001-代理商票流水号不存在;2003-投注失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					} else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					}

					ticketVender.setMessage(Constants.ruiyingErrorFailed.get(status));
					venderList.add(ticketVender);
				}
			}
		} catch (Exception e) {
			logger.error("瑞盈查票处理结果异常", e);
		}
	}

	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_RUIYING;
	}

}
