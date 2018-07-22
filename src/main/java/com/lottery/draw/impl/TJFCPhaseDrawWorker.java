package com.lottery.draw.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.impl.SyncIssueFromTJFC;
import com.lottery.ticket.vender.lotterycenter.tianjin.impl.SyncWinDataFromTJFC;
import com.lottery.ticket.vender.lotterycenter.tianjin.impl.SyncWinInfoFromTJFC;
@Service
public class TJFCPhaseDrawWorker extends AbstractVenderPhaseDrawWorker {
    @Autowired
	protected SyncIssueFromTJFC syncissue;
    @Autowired
    protected  SyncWinInfoFromTJFC syncwinfo;
    @Autowired
    protected SyncWinDataFromTJFC syncwindata;
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_TJFC_CENTER;
	}

	
	

	@Override
	protected LotteryDraw get(IVenderConfig config, Integer lotteryType, String phase) {
		LotteryPhase lotteryPhase=syncwinfo.syncFromCenter(lotteryType, phase);
		if(lotteryPhase==null){
			return null;
		}
		LotteryDraw dra=new LotteryDraw();
		dra.setWindCode(lotteryPhase.getWincode());
		return dra;
	}


	protected boolean isAsync(int lotteryType){
		return true;
	}

}
