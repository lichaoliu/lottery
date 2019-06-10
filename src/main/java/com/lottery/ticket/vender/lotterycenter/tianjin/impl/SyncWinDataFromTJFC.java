package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.lotterycenter.IVenderMessageFromCenter;
@Service
public class SyncWinDataFromTJFC extends AbstractTJFCWinDataHandler implements IVenderMessageFromCenter {

	

	@Override
	public LotteryPhase syncFromCenter(int lotteryType, String phase) {
		try{
			IVenderConverter converter=getVenderConverter();
			String lotid=converter.convertLotteryType(LotteryType.get(lotteryType));
			String phaseNo=converter.convertPhase(LotteryType.get(lotteryType), phase);
			if(StringUtils.isBlank(lotid)||StringUtils.isBlank(phaseNo)){
				return null;
			}
			String ftpName="windata_" + lotid + "_" + phaseNo + ".ffl";	
			return winDataProcess(ftpName, getTJFCConfg());
		}catch(Exception e){
			logger.error("下载彩种:{},新期:{},中奖数据文件失败",lotteryType,phase,e);
			return null;
		}
	}

}
