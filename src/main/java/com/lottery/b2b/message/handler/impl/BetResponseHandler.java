package com.lottery.b2b.message.handler.impl;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.LottypeConfigStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.handler.OrderProcessHandler;
import com.lottery.core.handler.SystemExceptionMessageHandler;
import com.lottery.core.service.SystemExcepionMessageService;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.bet.BetService;
import com.lottery.core.service.merchant.MerchantOrderService;
import com.lottery.ticket.sender.worker.XmlParse;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("801")
public class BetResponseHandler extends AbstractResponseHandler {

	private final Logger logger= LoggerFactory.getLogger("b2b-bet");
	@Autowired
	protected UserAccountService userAccountService;
	@Autowired
	private MerchantOrderService betService;
	@Autowired
	private IdGeneratorService idGeneratorService;
    @Autowired
	private BetService bservice;

	@Autowired
	private OrderProcessHandler orderProcessHandler;
	@Autowired
	private SystemExceptionMessageHandler systemExceptionMessageHandler;
	@Override
	public String getResponse(ExternalResponseMessage message) {

		String messageid = message.getMessageId();
		String body = message.getMessageBody();
		List<HashMap<String, String>> mapResultList = parseXml(body);
		if (mapResultList.isEmpty()) {
			message.setErrorCode(ErrorCode.body_error);
			return null;
		}
		XmlParse xmlParse = new XmlParse("content", "head", "message");
		Element bodyeElement = xmlParse.getBodyElement();
		Element bElement = bodyeElement.addElement("orderlist");
		Element bEleme = null;


		Long orderIds=idGeneratorService.getLotteryOrderCount(mapResultList.size());
		for (HashMap<String, String> mapResult : mapResultList) {
			bEleme = bElement.addElement("order");
			HashMap<String, String> bodyAttr = new HashMap<String, String>();

			String orderId = mapResult.get("orderid");
			String lotterytype = mapResult.get("lotterytype");
			String phase = mapResult.get("phase");
			String playtype = mapResult.get("playtype");
			String betcode = mapResult.get("betcode");
			String multiple = mapResult.get("multiple");
			String amount = mapResult.get("amount");
			String add = mapResult.get("add");
			String endtime=mapResult.get("endtime");
			String terminalId=message.getTerminalId()+"";
			boolean sellflag=true;
            int b2bForward=0;
			LottypeConfig lottypeConfig=phaseHandler.filterConfig(Integer.valueOf(lotterytype));
			if (lottypeConfig!=null&&lottypeConfig.getB2bEndSale()!=null){
				if (lottypeConfig.getB2bEndSale()== YesNoStatus.yes.value||lottypeConfig.getState()== LottypeConfigStatus.no_used.value||lottypeConfig.getState()==LottypeConfigStatus.paused.value){
					sellflag=false;
				}
				if (lottypeConfig.getB2bForward()!=null){
					b2bForward=lottypeConfig.getB2bForward();
				}

			}
			String code = "";
			String msg = "";
			String sysid = "";

			if (sellflag){
				try {
					String userno = message.getMerchant();
					int lotteryType = Integer.valueOf(lotterytype);
					int playType = Integer.valueOf(playtype);
					int multipl = Integer.valueOf(multiple);
					int addition = Integer.valueOf(add);
					int amountBlance = Integer.valueOf(amount) * 100;
					UserAccount userAccount = userAccountService.get(userno);
					BigDecimal total = userAccount.getBalance().add(message.getCreditBalance());
					if (total.compareTo(new BigDecimal(amountBlance)) < 0) {
						code = ErrorCode.account_no_enough.value;
						msg = ErrorCode.account_no_enough.memo;
					} else {
						MerchantOrder merchantOrder = merchantOrderService.getByMerchantCodeAndMerchantNO(userno, orderId);
						if (merchantOrder != null) {
							code = ErrorCode.exits.value;
							msg = ErrorCode.exits.memo;
						} else {
                            bservice.lotteryTypeValidate(lotteryType,BetType.bet_merchant.value);
							int oneAmout = 200;
							if (addition == YesNoStatus.yes.value) {
								oneAmout = 300;
							}
							bservice.codeValidate(lotteryType,phase,playType + "-" +betcode,multipl,oneAmout,new BigDecimal(amountBlance),YesNoStatus.no.value);
							String betOrder = idGeneratorService.getLotteryOrderId(orderIds);
									//idGeneratorService.getLotteryOrderId();
							sysid = betOrder;
							betService.bet(userno, orderId, betcode, betOrder, lotteryType, phase, playType, multipl, amountBlance, addition,b2bForward,endtime,terminalId);
							sendJMS(betOrder,lotteryType);
							code = ErrorCode.Success.value;
							msg = ErrorCode.Success.memo;
							orderIds++;

						}
					}

				} catch (LotteryException e) {
					logger.error("messageid={},orderid={}送票出错", messageid, orderId, e);
					logger.error("messageid="+messageid,e);
					code = e.getErrorCode().value;
					msg = e.getErrorCode().memo;
				} catch (Exception e) {

					String errormessage=String.format("messageid=%s,orderId=%s,sysid=%s,送票出错",messageid,orderId,sysid);
					logger.error(errormessage,e);
					code = ErrorCode.system_error.value;
					msg = "系统异常";
				}
			}else{
				 code =ErrorCode.paused_sale.value;
				 msg = ErrorCode.paused_sale.memo;
				 sysid = "";
			}


			bodyAttr.put("sysid", sysid);
			bodyAttr.put("errorcode", code);
			bodyAttr.put("msg", msg);
			bodyAttr.put("orderid", orderId);
			xmlParse.addBodyElement(bodyAttr, bEleme);
		}

		return xmlParse.asBodyXML();
	}

	protected void sendJMS(String orderid,int lotteryType) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderid);
			if (HighFrequencyLottery.contains(LotteryType.get(lotteryType))){
				queueMessageSendService.sendMessage(QueueName.betgaopinFreeze, map);
			}else {
				queueMessageSendService.sendMessage(QueueName.betFreeze, map);
			}

			
		} catch (Exception e) {
			logger.error("大客户发送拆票冻结jms失败", e);

			orderProcessHandler.freezeAndSplit(orderid);
            String errormessage=String.format("发送大客户jms消息失败,errormessage=%s",e.getMessage());
			systemExceptionMessageHandler.saveMessage(errormessage);
		}
	}
}
