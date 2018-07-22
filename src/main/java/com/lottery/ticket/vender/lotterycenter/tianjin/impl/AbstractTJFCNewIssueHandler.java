package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractTJFCMessageHandler;

public abstract class AbstractTJFCNewIssueHandler extends AbstractTJFCMessageHandler {

	

	protected LotteryPhase newIssueProcess(String filename,TJFCConfig config) throws Exception{
		String localFileName=downloadFtp(localDir,filename, config);
		String issuString=readFile(localFileName,config.getNoticeKey(),512);
		if(StringUtils.isBlank(issuString)){
			logger.error("下载福彩文件{}内容为空",filename);
			return null;
		}
		String[] issues = readFile(localFileName,config.getNoticeKey(),512).split("\t");
		StringBuffer sb=new StringBuffer();
		
		for(int i=0;i<issues.length;i++){
			sb.append(issues[i]).append(" ");
		}
		logger.error("获取新期所有的信息为:{}",sb.toString());
		IVenderConverter venderConverter=getVenderConverter();
		LotteryType lotteryType=venderConverter.findLotteryType(issues[0]);
		String phase = venderConverter.convertPhaseReverse(lotteryType, issues[1]);
		if(lotteryType==LotteryType.TJKL10||lotteryType==LotteryType.TJSSC){
			if(phase.endsWith("085")){
				return null;
			}
		}
		Date startTime = DateUtil.parse(issues[2]);
		Date endTime = DateUtil.parse(issues[3]);
		//venderPhaseDrawHandler.updatePhase(lotteryType.getValue(), phase, startTime, endTime,endTime);
		venderPhaseDrawHandler.magicMachine(TerminalType.T_TJFC_CENTER.value, lotteryType.value, phase);
		LotteryPhase lotteryPhase=new LotteryPhase();
		lotteryPhase.setPhase(phase);
		lotteryPhase.setLotteryType(lotteryType.value);
		lotteryPhase.setStartSaleTime(startTime);
		lotteryPhase.setEndSaleTime(endTime);
		return lotteryPhase;
	}
}
