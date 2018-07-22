package com.lottery.ticket.vender.notice.impl.JYDP;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.jydp.JydpConverter;
import com.lottery.ticket.vender.impl.jydp.ToolsAesCrypt;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JYDPNoticeProcess extends AbstractVenderNoticeProcess {

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_JYDP;
	}

	

	protected Map<String, String> convertRequestParameter(String requestString) {
		Map<String, String> map = new HashMap<String, String>();

		try {

			map.put("msg", requestString.split("&")[0].split("=")[1]);
			map.put("agentid",requestString.split("&")[1].split("=")[1]);
			return map;
		} catch (Exception e) {
			logger.error("解析xml异常", e);
		}

		return null;
	}
	
	@Override
	protected String getAgentId() {
		return "agentid";
	}

	@Override
	protected boolean validate(Map<String, String> requestMap,
			IVenderConfig venderConfig) {
		return true;
	}

	@Override
	protected String execute(Map<String, String> requestMap,
			IVenderConfig venderConfig) {
		// 将具体信息解析
		String exValue = requestMap.get("msg");
		try {
			exValue=ToolsAesCrypt.Decrypt(exValue,venderConfig.getKey());
			writeNoticeLog("解析后的字符串:"+exValue);
			JSONArray jsArray = JSONArray.fromObject(exValue);
			if(jsArray!=null){
				for(int i=0;i<jsArray.size();i++){
					JSONObject jsObject=jsArray.getJSONObject(i);
					String status=jsObject.getString("status");
					String externalId=jsObject.getString("tempid");
					String ticketId=jsObject.getString("fromserialno");
					TicketVender ticketVender = createInit();// 参照检票操作
					ticketVender.setTerminalIdJudge(false);
					ticketVender.setId(ticketId);
					ticketVender.setExternalId(externalId);
					if ("2".equals(status)) {// 成功
						String sp = jsObject.getString("spvalue");
						if (StringUtils.isNotBlank(sp)) {
							JydpConverter jydpConverter = (JydpConverter) venderConverterBinder.get(getTerminalType());
							Ticket ticket = ticketService.getTicket(ticketId);
							String peilv = jydpConverter.venderSpConvert(ticket, sp);
							ticketVender.setPeiLv(peilv);
							ticketVender.setOtherPeilv(sp);
						}

						String print=jsObject.getString("printtime");
						if(StringUtils.isNotBlank(print)&&!print.equals("0")){
							Date printtime= CoreDateUtils.parseDate(print,CoreDateUtils.DATE_YYYYMMDDHHMMSS);
							ticketVender.setPrintTime(printtime);
						}
						ticketVender.setStatus(TicketVenderStatus.success);
					} else if ("-2".equals(status)) {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					} else if ("404".equals(status)) {// 不存在
						ticketVender.setStatus(TicketVenderStatus.not_found);
					}
					this.ticketVenderProcess(ticketVender);
				}
			}

		} catch (Exception e) {
			logger.error("嘉优处理通知异常", e);
		}
		return "ok";
	}

}
