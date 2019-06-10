package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TJFcCommandEnum;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractTJFCMessageHandler;
@Service
public class SyncAccountHandler extends AbstractTJFCMessageHandler{

	public List<LotteryLogicMachine> getAccount(){
		TJFCConfig config=getTJFCConfg();
		List<LotteryLogicMachine> enableList=new ArrayList<LotteryLogicMachine>();
		Map<Long,LotteryLogicMachine> map=filter();
		for (Map.Entry<Long, LotteryLogicMachine> entry : map.entrySet()) {
			  Long key=entry.getKey();
			  LotteryLogicMachine logic=entry.getValue();
			  String balance=getBalance(key+"", config);
			  if(StringUtils.isNotBlank(balance)){
				  logic.setBalance(new BigDecimal(balance));
				  enableList.add(logic);
			  }
			  }
		
		return enableList;
	}
	
	protected String getBalance(String matchineCode,TJFCConfig venderConfig){
		try {
			byte[] send=makeMessageHead(TJFcCommandEnum.balanceAccout.getValue(), "", paddingMessage(matchineCode), venderConfig.getAgentCode(), venderConfig.getNoticeKey());
		
			byte[] resquest=sendMs(send, venderConfig.getRequestUrl(), venderConfig.getPort(), 1024);
			 String response= splitMsg(resquest, venderConfig.getKey());
			 logger.error("天津福彩逻辑机:{},返回的查询余额返回:{}",matchineCode,response);
			 String[] res=response.split("\t");
			 String code=res[0];
			 if(code.equals("0")){
				 return res[1];
			 }
			 return null;
		} catch (Exception e) {
			logger.error("天津福彩查询余额出错",e);
			return null;
		}
	}
	
	protected Map<Long,LotteryLogicMachine> filter(){
		Map<Long,LotteryLogicMachine> map=new HashMap<Long,LotteryLogicMachine>();
		List<LotteryLogicMachine> list=lotteryLogicMachineService.getByTerminalType(TerminalType.T_TJFC_CENTER.value);
		for(LotteryLogicMachine logic:list){
			map.put(logic.getPk().getId(), logic);
		}
		return map;
	}
}
