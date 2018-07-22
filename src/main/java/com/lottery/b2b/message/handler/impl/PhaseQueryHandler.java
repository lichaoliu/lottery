package com.lottery.b2b.message.handler.impl;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.ticket.sender.worker.XmlParse;

/***
 * 新期查询
 * */
@Component("804")
public class PhaseQueryHandler extends AbstractResponseHandler{
@Autowired
	private LotteryPhaseService lotteryPhaseService;
@Autowired
    private LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	@Override
	public String getResponse(ExternalResponseMessage message) {
		XmlParse xmlParse= null;
		try {
			String body = message.getMessageBody();
			Element eleme=XmlParse.getElemText("content",body);
			String lottype=eleme.elementText("lotterytype");
			String phaseno=eleme.elementText("phaseno");
			if (StringUtils.isBlank(lottype)) {
				message.setErrorCode(ErrorCode.body_error);
				return null;
			}
			LotteryType lotteryType = LotteryType.getLotteryType(Integer.valueOf(lottype));
			if (lotteryType == null) {
				message.setErrorCode(ErrorCode.lotterytype_not_exits);
				return null;
			}
			LotteryPhase lotteryPhase = null;
			if(StringUtils.isNotBlank(phaseno)){
				lotteryPhase = lotteryPhaseService.getByTypeAndPhase(Integer.valueOf(lottype),phaseno);
				if (lotteryPhase == null) {
					message.setErrorCode(ErrorCode.no_phase);
					return null;
				}
			}else{
				lotteryPhase = lotteryPhaseService.getCurrent(Integer.valueOf(lottype));
				if (lotteryPhase == null) {
					message.setErrorCode(ErrorCode.no_current_phase);
					return null;
				}
			}
			 xmlParse=new XmlParse("content","head","message");
			 Element element=xmlParse.getBodyElement();
			 element.addElement("lotterytype").setText(lottype);
			 Element element2=element.addElement("phaselist").addElement("phase");
			 HashMap<String,String>map=new HashMap<String, String>();
			 map.put("phaseno", lotteryPhase.getPhase());
			 map.put("starttime",CoreDateUtils.formatDate(lotteryPhase.getStartSaleTime(), TIME_STAMP));
			 map.put("endtime", CoreDateUtils.formatDate(lotteryPhase.getEndSaleTime(), TIME_STAMP));
			 map.put("localendtime", CoreDateUtils.formatDate(getEndSaleForwardDate(lotteryPhase.getEndSaleTime(),lotteryType.value), TIME_STAMP));
			 map.put("istrue",lotteryPhase.getPhaseTimeStatus() + "");
			 xmlParse.addBodyElement(map,element2);
			 return xmlParse.asBodyXML();
		} catch (Exception e) {
			logger.error("新期查询出错",e);
		  return null;
		}
		
	}
	
	private Date getEndSaleForwardDate(Date date, Integer lotteryType) {
		Date returnDate = date;
		if (date == null || lotteryType == null) {
			logger.error("请求参数不能为空:时间{},彩种{}", new Object[] { date, lotteryType });
		}
		if (lotteryTicketConfigCacheModel != null && date != null) {
			try {
				LotteryTicketConfig lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
				if (lotteryTicketConfig != null) {
					if (lotteryTicketConfig.getEndSaleForward() != null) {
						long time = date.getTime() - lotteryTicketConfig.getEndSaleForward().longValue();
						returnDate = new Date(time);
					}
				}
			} catch (Exception e) {
				logger.error("获取偏移量出错", e);
			}
		}
		return returnDate;
	}
}
