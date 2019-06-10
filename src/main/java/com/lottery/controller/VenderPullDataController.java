package com.lottery.controller;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.vender.impl.xcs.XCSConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by fengqinyun on 15/7/1. //提供接口给出票商
 */
@RestController
@RequestMapping("/venderpull")
public class VenderPullDataController {
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	protected XCSConverter xcsConverter;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected QueueMessageSendService queueMessageSendService;
    @Autowired
	private IdGeneratorService idGeneratorService;
	@RequestMapping(value = "/xcs", method = RequestMethod.POST)
	public String getXcs(@RequestParam(value = "terminalId", required = true) Long terminaId, @RequestParam(value = "lotteryType", required = true) int lotteryType) {
		try {
			List<Ticket> ticketList = ticketService.getByTerminalIdLotteryTypeAndStatus(terminaId, lotteryType, TicketStatus.UNSENT.value, 1);
			if (ticketList != null && ticketList.size() > 0) {
				Ticket ticket = ticketList.get(0);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("caizhong=").append(xcsConverter.convertLotteryType(ticket.getLotteryType()));
				stringBuffer.append("&ticketid=").append(ticket.getId());
				stringBuffer.append("&beishu=").append(ticket.getMultiple());
				stringBuffer.append("&jine=").append(ticket.getAmount().divide(new BigDecimal(100)).intValue());
				String[] zhushu = StringUtils.split(ticket.getContent(), "^");
				stringBuffer.append("&zhushu=").append(zhushu.length);
				stringBuffer.append("&wanfa=").append(xcsConverter.convertPlayType(ticket.getPlayType()));
				stringBuffer.append("&neirong=").append(xcsConverter.convertContent(ticket));
				return stringBuffer.toString();
			}
		} catch (Exception e) {
			logger.error("获取数据失败", e);
		}

		return null;

	}

	@RequestMapping(value = "/xcsreturn", method = RequestMethod.POST)
	public String xcsreturn(@RequestParam(value = "ticketid", required = true) String ticketid) {
		try {
			Ticket ticket = ticketService.getTicket(ticketid);
			if (ticket != null) {
				ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
				ticket.setPrintTime(new Date());
				ticketService.update(ticket);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("", ticket.getOrderId());
				queueMessageSendService.sendMessage(QueueName.betChercher, map);
			}

			return "success";
		} catch (Exception e) {
			logger.error("获取数据失败", e);
		}

		return "failure";
	}

	@RequestMapping(value = "/xcstc", method = RequestMethod.POST)
	public ResponseData getXcstc(@RequestParam(value = "terminalId", required = true) Long terminalId, @RequestParam(value = "max", required = false, defaultValue = "100") int max) {
		ResponseData rd=new ResponseData();
		try {
			List<Ticket> ticketList = ticketService.getByTerminalIdAndStatus(terminalId, TicketStatus.UNSENT.value, max);
			List<String> allList = new ArrayList<String>();
			if (ticketList != null && ticketList.size() > 0) {
				String  batchId=idGeneratorService.getBatchTicketId();
				rd.setErrorCode(batchId);
				
				for (Ticket ticket : ticketList) {
					int playType = ticket.getPlayType();
					if (playType == PlayType.jczq_bf_4_1.value || playType == PlayType.jczq_bf_4_4.value || playType == PlayType.jczq_bf_4_5.value || playType == PlayType.jczq_bf_4_6.value || playType == PlayType.jczq_bf_4_11.value || playType == PlayType.jczq_bqc_4_1.value || playType == PlayType.jczq_bqc_4_4.value || playType == PlayType.jczq_bqc_4_5.value || playType == PlayType.jczq_bqc_4_6.value || playType == PlayType.jczq_bqc_4_11.value || playType == PlayType.jczq_bqc_1_1.value
							|| playType == PlayType.jczq_bf_1_1.value || playType == PlayType.jczq_jqs_1_1.value || playType == PlayType.jczq_spf_1_1.value || playType == PlayType.jczq_spfwrq_1_1.value) {
						logger.error("票:{},的玩法是:{}自动过滤", ticket.getId(), playType);
						ticket.setStatus(TicketStatus.UNALLOTTED.value);
						ticketService.update(ticket);
						continue;
					} else {

						if (playType == PlayType.jczq_mix_4_4.value || playType == PlayType.jczq_mix_4_5.value || playType == PlayType.jczq_mix_4_6.value || playType == PlayType.jczq_mix_4_11.value ||

						playType == PlayType.jczq_mix_4_1.value) {
							String content = ticket.getContent();
							if (content.contains("*3007") || content.contains("*3008") || content.contains("*3009")) {
								ticket.setStatus(TicketStatus.UNALLOTTED.value);
								ticketService.update(ticket);
                                continue;
							}

						}
					}
					Long i=1l;
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append(xcsConverter.convertLotteryType(ticket.getLotteryType()));// 彩种
					stringBuffer.append("#").append(ticket.getMultiple());// 倍数
					String[] changshu = ticket.getContent().split("\\-")[1].split("\\|");

					stringBuffer.append("#").append(changshu.length);// 场次

					String play = playType + "";
					play = play.substring(play.length() - 4);
					String g = play.substring(0, 1);
					String gu = play.substring(1);
					if (gu.substring(0,1).equals("0")&&gu.substring(1,2).equals("0")){
						gu=gu.substring(2);
					}
					if (gu.substring(0,1).equals("0")&&!gu.substring(1,2).equals("0")){
						gu=gu.substring(1);
					}
					String guoguan = g + "x" + gu;

					if (guoguan.equals("1x1")) {
						guoguan = "01";
					}
					stringBuffer.append("#").append(guoguan);// 过关方式
					stringBuffer.append("#").append(xcsConverter.convertContent(ticket));// 内容
					stringBuffer.append("#").append(ticket.getAmount().divide(new BigDecimal(100)).intValue());
					allList.add(stringBuffer.toString());
                    ticket.setStatus(TicketStatus.PRINTING.value);
                    ticket.setBatchId(batchId);
                    ticket.setBatchIndex(i);
                    ticketService.update(ticket);
                    i++;
				}
                
			}
			rd.setValue(allList);
			
		} catch (Exception e) {
			logger.error("获取数据失败", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}

		return rd;

	}


	@RequestMapping(value = "/yc", method = RequestMethod.POST)
	public ResponseData getYc(@RequestParam(value = "terminalId", required = true) Long terminalId, @RequestParam(value = "max", required = false, defaultValue = "100") int max) {
		ResponseData rd=new ResponseData();
		try {
			List<Ticket> ticketList = ticketService.getByTerminalIdAndStatus(terminalId, TicketStatus.UNSENT.value, max);

			if (ticketList != null && ticketList.size() > 0) {
				String  batchId=idGeneratorService.getBatchTicketId();
				rd.setErrorCode(batchId);
				for (Ticket ticket : ticketList) {
					Long i=1l;
					ticket.setStatus(TicketStatus.PRINTING.value);
					ticket.setBatchId(batchId);
					ticket.setBatchIndex(i);
					ticketService.update(ticket);
					i++;
				}
			}
			rd.setValue(ticketList);

		} catch (Exception e) {
			logger.error("获取数据失败", e);
			rd.setErrorCode(ErrorCode.system_error.value);
		}

		return rd;

	}



}
