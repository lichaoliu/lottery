package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.lotterycenter.IVenderMessageFromCenter;
@Service
public class SyncIssueFromTJFC extends AbstractTJFCNewIssueHandler implements IVenderMessageFromCenter {

	@Override
	public LotteryPhase syncFromCenter(int lotteryType, String phase) {
		try{
			logger.error("手动同步新期:{},{}",lotteryType,phase);
			IVenderConverter converter=getVenderConverter();
			String lotid=converter.convertLotteryType(LotteryType.get(lotteryType));
			String phaseNo=converter.convertPhase(LotteryType.get(lotteryType), phase);
			if(StringUtils.isBlank(lotid)||StringUtils.isBlank(phaseNo)){
				return null;
			}
			String ftpName="par_" + lotid + "_" + phaseNo + ".ffl";
			return newIssueProcess(ftpName, getTJFCConfg());
		}catch(Exception e){
			logger.error("同步天津福彩新期失败,彩种:{},期号:{}",lotteryType,phase,e);
			return null;
		}

	}

}
