package com.lottery.b2b.message.handler.impl;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.ticket.sender.worker.XmlParse;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 开奖号码查询
 * */
@Component("805")
public class PhaseDrawHanlder extends AbstractResponseHandler{
@Autowired
	private LotteryPhaseService lotteryPhaseService;
	@Override
	public String getResponse(ExternalResponseMessage message) {
		
		try {
			String body=message.getMessageBody();
			Element eleme=XmlParse.getElemText("content",body);
			String lottype=eleme.elementText("lotterytype");
			String phase=eleme.elementText("phase");
			if(StringUtils.isBlank(lottype)||StringUtils.isBlank(phase)){
				logger.error("参数不能为空,彩种:{},期号:{}",new Object[]{lottype,phase});
				message.setErrorCode(ErrorCode.body_error);
				return null;	
			}
			LotteryType lotteryType=LotteryType.getLotteryType(Integer.valueOf(lottype));
			if(lotteryType==null){
				message.setErrorCode(ErrorCode.lotterytype_not_exits);
				return null;	
			}
			LotteryPhase lotteryPhase=lotteryPhaseService.getByTypeAndPhase(Integer.valueOf(lottype), phase);
			if(lotteryPhase==null){
				message.setErrorCode(ErrorCode.no_phase);
				return null;
			}
			if(lotteryPhase.getPhaseStatus()==PhaseStatus.open.value||lotteryPhase.getPhaseStatus()==PhaseStatus.close.value||lotteryPhase.getPhaseStatus()==PhaseStatus.unopen.value||StringUtils.isBlank(lotteryPhase.getWincode())){
				message.setErrorCode(ErrorCode.no_windcode);
				return null;
			}
		
			 Element winelement=DocumentHelper.createElement("message");

			 Element lotteryelement=winelement.addElement("lotterytype");
			 lotteryelement.setText(lottype);
			 Element phaselement=winelement.addElement("phase");
			 phaselement.setText(phase);
			 Element wincode=winelement.addElement("wincode");
			 wincode.setText(lotteryPhase.getWincode());
			 Element windetail=winelement.addElement("windetail");
			 if(StringUtils.isNotBlank(lotteryPhase.getPrizeDetail())){
				 windetail.setText(lotteryPhase.getPrizeDetail());
			 }
			 Element poolamount=winelement.addElement("poolamount");
			 if(StringUtils.isNotBlank(lotteryPhase.getPoolAmount())){
				 poolamount.setText(lotteryPhase.getPoolAmount());
			 }

			 Element addpoolamount=winelement.addElement("addpoolamount");
			 if(StringUtils.isNotBlank(lotteryPhase.getAddPoolAmount())){
				 addpoolamount.setText(lotteryPhase.getAddPoolAmount());
			 }

			 Element saleamount=winelement.addElement("saleamount");
			 if(StringUtils.isNotBlank(lotteryPhase.getSaleAmount())){
				 saleamount.setText(lotteryPhase.getSaleAmount());
			 }


			return winelement.asXML();
		} catch (Exception e) {
			logger.error("查询开奖出错",e);
			return null;
		}
		
	}

}
