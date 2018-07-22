package com.lottery.ticket.vender.exchange.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.exchange.AbstractVenderTicketExchange;
import com.lottery.ticket.vender.impl.zhangyi.ZYConverter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author liuxiaoyan
 *
 */
@Service
public class ZyVenderTicketExchange extends AbstractVenderTicketExchange {

	@Override
	protected void execute(List<Ticket> ticketList, IVenderConfig venderConfig) {
		ZYConverter zyConverter = (ZYConverter) getVenderConverter();
		Ticket ticket = ticketList.get(0);
		String lotno = zyConverter.convertLotteryType(ticket.getLotteryType());
		String phase = zyConverter.convertPhase(ticket.getLotteryType(), ticket.getPhase());
		String getElement = getElement(venderConfig, lotno, phase);
		String returnStr = null;
		try {
			returnStr = HTTPUtil.postJson(venderConfig.getRequestUrl(), getElement);
		} catch (Exception e) {
			logger.error("掌奕兑奖异常", e);
			return;
		}

		try {
			JSONObject returnMsg = JSONObject.fromObject(returnStr);
			String returnCode = String.valueOf(returnMsg.getJSONObject("msg").get("errorcode"));
			if ("0".equals(returnCode)) {
				if (!returnMsg.getJSONObject("msg").containsKey("ticketlist")) {
					return;
				}

				JSONArray jsArray = returnMsg.getJSONObject("msg").getJSONArray("ticketlist");
				for (int i = 0; i < jsArray.size(); i++) {
					JSONObject jsObject = jsArray.getJSONObject(i);
					int status = jsObject.getInt("status");
					String ticketId = jsObject.getString("id");
					Ticket ticket1 = ticketService.getTicket(ticketId);
					if (ticket1 != null && (status == 2 || status == 3)) {
						String taxawardbets = jsObject.getString("bonusvalue");

						this.ticketCreate(ticket1, new BigDecimal(taxawardbets));
					}
				}

			} else {
				logger.error("彩岛兑奖返回return={}", returnStr);
			}

		} catch (Exception e) {
			logger.error("兑奖出错", e);
		}

	}

	public String getElement(IVenderConfig zyConfig, String lotteryType, String phase) {
		try {
			JSONObject message = new JSONObject();
			JSONObject header = new JSONObject();
			header.put("messengerid",
					idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid, CoreDateUtils.DATE_YYYYMMDDHHMMSS));
			header.put("timestamp", DateUtil.format("yyyyMMddHHmmss", new Date()));
			header.put("transactiontype", "13003");
			header.put("des", "0");
			header.put("agenterid", zyConfig.getAgentCode());
			message.put("header", header);
			JSONObject map = new JSONObject();
			map.put("lotteryid", lotteryType);
			map.put("issue", phase);
			map.put("isbonus", 1);
			message.put("msg", map);
			return message.toString();
		} catch (Exception e) {
			logger.error("兑奖拼串异常", e);
		}
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_ZY;
	}

	protected IVenderConverter getVenderConverter() {
		return venderConverterBinder.get(getTerminalType());
	}

}
