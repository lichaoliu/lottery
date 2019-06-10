package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractTJFCMessageHandler;

public abstract class AbstractTJFCWinInfoHandler extends AbstractTJFCMessageHandler {

	protected LotteryPhase winInfoProcess(String filename,TJFCConfig config) throws Exception{

		String localFileName=downloadFtp(localDir,filename, config);

		String winString=readFile(localFileName,config.getNoticeKey(),512);
		if(StringUtils.isBlank(winString)){
			logger.error("开奖信息{}，为空",filename);
			return null;
		}
		String[] winInfo = winString.split("\t");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<winInfo.length;i++){
			sb.append(winInfo[i]).append(" ");
		}
		logger.error("获取开奖所有的信息为:{}",sb.toString());
		IVenderConverter venderConverter=getVenderConverter();
		LotteryType lotteryType=venderConverter.getLotteryMap(winInfo[0]);
	
		String wincode = "";
		if(lotteryType.value==LotteryType.SSQ.value||lotteryType.value==LotteryType.QLC.value){
			wincode=wincodeSeque(winInfo[2])+"|"+wincodeNoSeque(winInfo[3]);
		}
		else if(lotteryType.value == LotteryType.F3D.value||lotteryType.value == LotteryType.TJSSC.value){
			wincode = makeD3(winInfo[2]);
		}else{
			wincode = makeWinCode(winInfo[2]);
		}

		
		String phase=venderConverter.convertPhaseReverse(lotteryType, winInfo[1]);
		prizeHandler.prizeOpen(lotteryType.value, phase, wincode.trim());
		
		
		LotteryPhase lotteryPhase=new LotteryPhase();
		lotteryPhase.setWincode(wincode.trim());
		lotteryPhase.setLotteryType(lotteryType.value);
		lotteryPhase.setPhase(phase);
		return lotteryPhase;
	}
	
	
	
	
}
