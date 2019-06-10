package com.lottery.ticket.vender.exchange.impl;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.exchange.AbstractVenderTicketExchange;
import com.lottery.ticket.vender.impl.huancai.HuancaiConfig;
import com.lottery.ticket.vender.impl.huancai.HuancaiConverter;

import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author liuxiaoyan
 *
 */
@Service
public class HuanCaiTicketExchange extends AbstractVenderTicketExchange {

	@Override
	protected void execute(List<Ticket> ticketList, IVenderConfig venderConfig) {
		HuancaiConverter huancaiConverter = (HuancaiConverter) getVenderConverter();
		Ticket ticket = ticketList.get(0);
		String lotno = huancaiConverter.convertLotteryType(ticket.getLotteryType());
		String phase = huancaiConverter.convertPhase(ticket.getLotteryType(), ticket.getPhase());
		String getElement = getElement(venderConfig, lotno, phase);
		String returnStr = null;
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), getElement);
		} catch (Exception e) {
			logger.error("环彩兑奖返回", e);
			return;
		}

		try {
			Map<String, String> map = XmlParse.getElementAttribute("/", "response", returnStr);
			String code = map.get("code");
			if ("0000".equals(code)) {
				List<HashMap<String, String>> mapParams = XmlParse.getElementAttributeList("response/bonus/", "ticket",
						returnStr);
				for (HashMap<String, String> mapStr : mapParams) {
					String ticketid = mapStr.get("ticketid");
					Ticket ticket1 = ticketService.getTicket(ticketid);
					if (ticket1 != null) {
						String taxawardbets = mapStr.get("fixamount");
						this.ticketCreate(ticket1, new BigDecimal(taxawardbets));
					}
				}
			} else {
				logger.error("环彩return={}", returnStr);
			}

		} catch (Exception e) {
			logger.error("兑奖出错", e);
			logger.error("return={}", returnStr);
		}

	}

	public String getElement(IVenderConfig huancaiConfig, String lotteryType, String phase) {
		XmlParse xmlParse = null;
		try {
			String messageId = DateUtil.format("yyyyMMddHHmmss", new Date());
			;
			xmlParse = HuancaiConfig.addGxHead("1004", huancaiConfig.getAgentCode(), messageId);
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			HashMap<String, Object> bodyAttr2 = new HashMap<String, Object>();
			bodyAttr2.put("name", phase);
			bodyAttr2.put("lottid", lotteryType);
			Element attributeMap2 = xmlParse.addBodyElementAndAttribute("bonusQuery", "", bodyAttr);
			xmlParse.addBodyElementAndAttribute("issue", "", bodyAttr2, attributeMap2);
			String md = "";
			try {
				md = MD5Util.toMd5(huancaiConfig.getAgentCode() + messageId + huancaiConfig.getKey()
						+ xmlParse.getBodyElement().asXML().replace("&lt;", "<").replace("&gt;", ">"));
			} catch (Exception e) {
				logger.error("加密异常" + e.getMessage());
			}
			xmlParse.addHeaderElement("digest", md);
			return "cmd=1004&msg=" + xmlParse.asXML();
		} catch (Exception e) {
			logger.error("兑奖封装数据异常", e);
		}
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_HUANCAI;
	}

	protected IVenderConverter getVenderConverter() {
		return venderConverterBinder.get(getTerminalType());
	}

}
