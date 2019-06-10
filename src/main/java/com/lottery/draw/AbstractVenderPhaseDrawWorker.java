package com.lottery.draw;

import com.lottery.common.contains.EnabledStatus;
import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LotteryPhaseDrawConfig;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.LotteryPhaseDrawConfigService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.VenderPhaseDrawHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractVenderPhaseDrawWorker implements IVenderPhaseDrawWorker {
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private VenderConfigHandler venderConfigService;
	@Autowired
	protected LotteryPhaseService lotteryPhaseService;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected QueueMessageSendService queueMessageSendService;
	@Resource(name = "venderPhaseDrawWorkerMap")
	protected Map<TerminalType, IVenderPhaseDrawWorker> venderPhaseDrawWorkerMap;
	@Autowired
	protected VenderPhaseDrawHandler venderPhaseDrawHandler;

	@Resource(name = "venderConverterBinder")
	protected Map<TerminalType, IVenderConverter> venderConverterBinder;
	@Autowired
	protected LotteryPhaseDrawConfigService lotteryPhaseDrawConfigService;

	protected abstract TerminalType getTerminalType();
	@Autowired
	protected PrizeHandler prizeHandler;

	/**
	 * 抓取开奖号码
	 * */
	public LotteryDraw getPhaseDraw(Integer lotteryType, String phase) {
		List<IVenderConfig> configList = venderConfigService.getAllByTerminalType(getTerminalType());
		for (IVenderConfig config : configList) {
			LotteryPhaseDrawConfig lotteryPhaseDrawConfig=getLotteryPhaseDrawConfig(lotteryType, config.getTerminalId());
			if (lotteryPhaseDrawConfig==null||lotteryPhaseDrawConfig.getIsEnabled()== EnabledStatus.disabled.value){
				continue;
			}

			LotteryDraw draw = get(config, lotteryType, phase);
			if (draw != null) {
				draw.setDrawFrom(getTerminalType().toString());
                this.process(lotteryType,null,config);
				printLog(config, lotteryType, phase, draw);
				if(isDraw(lotteryPhaseDrawConfig,lotteryType,phase,draw.getWindCode())){
					draw.setIsDraw(YesNoStatus.yes.getValue());
				}
				return draw;
			}
		}
		return null;
	}


	protected  boolean isDraw(LotteryPhaseDrawConfig lotteryPhaseDrawConfig,Integer lotteryType, String phase,String wincode){
		if(lotteryPhaseDrawConfig.getIsDraw()==YesNoStatus.yes.value&& StringUtils.isNotBlank(wincode)){
           return this.sendJMS(lotteryType,phase,wincode);
		}
		return false;
	}


	protected boolean sendJMS(int lotteryType,String phase,String wincode){
		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("lotteryType", lotteryType);
			headers.put("phase", phase);
			headers.put("wincode", wincode);
			queueMessageSendService.sendMessage(TopicName.phaseOpenPrize.value, headers);
			return true;
		} catch (Exception e) {
			logger.error("发送开奖号码出错", e);
			return false;
		}
	}


	protected  List<LotteryPhase> process(int lotteryType,String phase,IVenderConfig venderConfig){

       return null;

	}



	public List<LotteryPhase> getPhaseList(int lotteryType,String phase) {
		List<IVenderConfig> configList = venderConfigService.getAllByTerminalType(getTerminalType());
		List<LotteryPhase> phaseList = null;
		for (IVenderConfig config : configList) {
			LotteryPhaseDrawConfig lotteryPhaseDrawConfig=getLotteryPhaseDrawConfig(lotteryType,config.getTerminalId());
			if (lotteryPhaseDrawConfig==null||lotteryPhaseDrawConfig.getSyncTime()==YesNoStatus.no.value){
				continue;
			}
			phaseList = this.process(lotteryType,phase,config);
			if (phaseList != null)
				break;
		}
		return phaseList;
	}



	protected abstract LotteryDraw get(IVenderConfig config, Integer lotteryType, String phase);

	private boolean printLog(IVenderConfig config, Integer lotteryType, String phase, LotteryDraw draw) {
		logger.error("从出票商:{},地址:{},抓到的彩种:{},期号:{},开奖号码是:({})详细内容是:{},lotterydraw={}", new Object[] { config.getAgentCode(), config.getRequestUrl(), lotteryType, phase, draw.getWindCode(), draw.getResponsMessage(),draw.toString() });

		try {
			lotteryPhaseService.saveWininfo(lotteryType, phase,draw.getWindCode(),draw.getResultDetail(), draw.getJackpot(), draw.getAddJackpot(), draw.getVolumeOfSales(), draw.getDrawFrom());
			return true;
		} catch (Exception e) {
			logger.error("存储开奖信息出错", e);
			return false;
		}

	}

	protected String ConvertNum(String totalSaleNum) {
		int len = totalSaleNum.split("\\.")[0].length();
		int leftLen = 0;
		int num = 0;
		boolean innum = false;
		if (len % 3 > 0) {
			leftLen = len % 3;
			innum = true;
			num = (len / 3) + 1;
		} else {
			num = len / 3;
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0, j = 3; i < num; i++) {
			String ss = "";
			if (innum && i == 0) {
				ss = totalSaleNum.substring(j - 3, leftLen);
				j = leftLen + 3;
			} else {
				ss = totalSaleNum.substring(j - 3, j);
				j = j + 3;
				;
			}
			stringBuilder.append(ss);
			if (i != num - 1) {
				stringBuilder.append(",");
			}
		}
		return stringBuilder.toString() + ".00";
	}

	@PostConstruct
	protected void init() {
		venderPhaseDrawWorkerMap.put(getTerminalType(), this);
	}

	protected IVenderConverter getVenderConverter() {
		return venderConverterBinder.get(getTerminalType());
	}

	protected LotteryPhaseDrawConfig getLotteryPhaseDrawConfig(int lotteryType,Long terminalId){
		List<LotteryPhaseDrawConfig> configList=lotteryPhaseDrawConfigService.get(lotteryType,terminalId);
		if(configList!=null&&!configList.isEmpty()){
			return configList.get(0);
		}
		return  null;
	}

}
