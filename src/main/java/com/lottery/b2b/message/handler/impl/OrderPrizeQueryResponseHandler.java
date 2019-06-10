package com.lottery.b2b.message.handler.impl;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.ticket.sender.worker.XmlParse;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 订单中奖查询
 * */
@Component("803")
public class OrderPrizeQueryResponseHandler extends AbstractResponseHandler{

	@Override
	public String getResponse(ExternalResponseMessage message) {
		String body=message.getMessageBody();
		String userno=message.getMerchant();
		List<HashMap<String,String>> mapResultList=parseXml(body);
		if(mapResultList.isEmpty()){
			message.setErrorCode(ErrorCode.body_error);
			return null;
		}

		XmlParse xmlParse=new XmlParse("content","head","message");
		Element element=xmlParse.getBodyElement();
		Element element2=element.addElement("orderlist");
		for(HashMap<String,String> mapResult:mapResultList){
			Element	elementBody=element2.addElement("order");
			String orderId=mapResult.get("orderid");
			HashMap<String, String> bodyAttr = new HashMap<String, String>();
			bodyAttr.put("orderid",orderId);

			MerchantOrder merchantOrder=merchantOrderService.getByMerchantCodeAndMerchantNO(userno, orderId);
		    if(merchantOrder==null){
		    	bodyAttr.put("errorcode",ErrorCode.no_exits.value);
		    }else{
		    	bodyAttr.put("errorcode",merchantOrder.getOrderResultStatus()+"");
		    	BigDecimal amount=merchantOrder.getTotalPrize();
		    	if(amount!=null){
		    		amount=amount.divide(new BigDecimal(100));
		    	}else{
		    		amount=BigDecimal.ZERO;
		    	}
		    	bodyAttr.put("amount",amount.doubleValue()+"");
		    }
		    xmlParse.addBodyElement(bodyAttr,elementBody);
		}
	    return	xmlParse.asBodyXML();
	}

}
