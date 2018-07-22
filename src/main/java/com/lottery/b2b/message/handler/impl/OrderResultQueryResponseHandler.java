package com.lottery.b2b.message.handler.impl;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.domain.ticket.Ticket;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 票投注状态查询
 * */
@Component("802")
public class OrderResultQueryResponseHandler extends AbstractResponseHandler {
	private final Logger logger= LoggerFactory.getLogger("b2b-check");
	@Override
	public String getResponse(ExternalResponseMessage message) {
		String body = message.getMessageBody();
		String userno = message.getMerchant();
		List<HashMap<String, String>> mapResultList = parseXml(body);
		if (mapResultList.isEmpty()) {
			message.setErrorCode(ErrorCode.body_error);
			return null;
		}
		Element messageElement = DocumentHelper.createElement("message");

		Element orderlistElement = messageElement.addElement("orderlist");
		for (HashMap<String, String> mapResult : mapResultList) {
			Element orderElement = orderlistElement.addElement("order");
			Element orderidElement = orderElement.addElement("orderid");
			String orderId = mapResult.get("orderid");
			orderidElement.setText(orderId);


			String value = "";
			String failreason="0";
			MerchantOrder merchantOrder = merchantOrderService.getByMerchantCodeAndMerchantNO(userno, orderId);
			if (merchantOrder == null) {
				value = ErrorCode.no_exits.value;
			} else {
				Element sysidElement=orderElement.addElement("sysid");
				sysidElement.setText(merchantOrder.getOrderid());
				if (merchantOrder.getOrderStatus().intValue() == OrderStatus.PRINTED.value) {
					value = ErrorCode.Success.value;
					Element ticketListElement = orderElement.addElement("ticketlist");
					//竞彩返回出票赔率
					if(LotteryType.getJczqValue().contains(merchantOrder.getLotteryType())
							||LotteryType.getJclqValue().contains(merchantOrder.getLotteryType())||LotteryType.getGuanyajunValue().contains(merchantOrder.getLotteryType()) ){

					}
					List<Ticket> ticketList = ticketService.getByorderId(merchantOrder.getOrderid());
					for (Ticket ticket : ticketList) {
						Element ticketElement = ticketListElement.addElement("ticket");
						ticketElement.addAttribute("sp", ticket.getPeilv());
						ticketElement.addAttribute("externalid", ticket.getExternalId());
						ticketElement.setText(ticket.getId());
					}
					Element printtimeElement = orderElement.addElement("printtime");
					printtimeElement.setText(DateUtil.format("yyyy-MM-dd HH:mm:ss", merchantOrder.getPrintTime()));
					
				} else if (merchantOrder.getOrderStatus().intValue() == OrderStatus.PRINTED_FAILED.value
						||merchantOrder.getOrderStatus().intValue() == OrderStatus.UNPAY_OBSOLETE.value
						||merchantOrder.getOrderStatus().intValue() == OrderStatus.CANCELLED.value
						||merchantOrder.getOrderStatus().intValue() == OrderStatus.REVOKED.value) {
					value = ErrorCode.Faile.value;
					List<Ticket> ticketList=ticketService.getByorderId(merchantOrder.getOrderid());
					if(ticketList!=null){
						for(Ticket ticket:ticketList){
							if(ticket.getStatus()!= TicketStatus.PRINT_SUCCESS.value){
								if(ticket.getFailureType()!=null){
									if(ticket.getFailureType()== TicketFailureType.PRINT_LIMITED.value){
										failreason=ErrorCode.bet_limit.value;
									}
								}
							}
						}
					}
				} else {
					value = ErrorCode.handling.value;
				}
			}
			Element codeElement = orderElement.addElement("errorcode");
			codeElement.setText(value);
			Element reasonElement = orderElement.addElement("failreason");
			reasonElement.setText(failreason);
		}
		return messageElement.asXML();
	}

}
