package com.lottery.draw.impl;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.PhaseStatus;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huai.HuAiConverter;
@Service
public class HuAiPhaseDrawWork extends AbstractVenderPhaseDrawWorker {
	protected final static Logger logger = LoggerFactory.getLogger(HuAiPhaseDrawWork.class);
	protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();

	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String openPrizeNum="103";
	@Autowired
	private HuAiConverter huAiConverter;	
	@Override
	protected LotteryDraw get(IVenderConfig config,
			Integer lotteryType, String phase) {
		if(config==null){
			return null;
		}
		IPhaseConverter converter=phaseConverterMap.get(LotteryType.get(lotteryType));
		phase=converter.convert(phase);
		String lotno = huAiConverter.convertLotteryType(LotteryType.getLotteryType(lotteryType));
		String message=getElement(lotno,phase,config);
		String returnStr=null;
		try {
			
			returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
         	 //处理结果
         	 LotteryDraw lotteryDraw= dealResult(returnStr,lotteryType);
         	 return lotteryDraw;
		} catch (Exception e) {
			logger.error("发送:{},返回:{}",message,returnStr);
			logger.error("处理开奖结果异常",e);
		}
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_HUAI;
	}

	/**
	 * 查奖结果处理
	 * @param desContent
	 * @param
	 * @return
	 */
	private LotteryDraw dealResult(String desContent,int lotteryType){
		LotteryDraw lotteryDraw=new LotteryDraw();
		lotteryDraw.setResponsMessage(desContent);
		try {
			desContent="<response>"+desContent+"</response>";
			HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", desContent);
			String code = mapResult.get("reCode");
			String msg = mapResult.get("reMessage");
			if ("0".equals(code)) {
				HashMap<String, String> reResult = XmlParse.getElementAttribute("/ActionResult/reValue/", "Issue", desContent);
				    String issue=reResult.get("LotIssue");
				    String prizecode=reResult.get("BonusCode");
				    if(StringUtils.isNotBlank(prizecode)){
				    	 IPrizeNumConverter prConverter=prizeNumConverterMap.get(lotteryType);
				    	 lotteryDraw.setLotteryType(lotteryType);
				    	 lotteryDraw.setPhase(issue);
						 lotteryDraw.setStatus(PhaseStatus.prize_open.value);
						 lotteryDraw.setWindCode(prConverter.convert(prizecode));
						 return lotteryDraw;
				    }
				    return null;
			 }else{
				 logger.error("code:{},msg:{}",code,msg);
			 }
		} catch (DocumentException e) {
			logger.error("处理开奖结果异常",e);
		}
		return null;
	}
	private String getElement(String lotteryNo,String phase, IVenderConfig huaiConfig) {
		StringBuffer stringBuffer=new StringBuffer();
		StringBuilder  exParam=new StringBuilder();
		exParam.append("LotID=").append(lotteryNo).append("_LotIssue=").append(phase);
		String messageId=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		stringBuffer.append("exAgent=").append(huaiConfig.getAgentCode())
		.append("&exAction=").append(openPrizeNum).append("&exMsgID="+messageId);
		try{
			String signMsg=MD5Util.toMd5(huaiConfig.getAgentCode()+openPrizeNum+messageId+exParam.toString()+huaiConfig.getKey());
			stringBuffer.append("&exParam=").append(exParam.toString()).append("&exSign=").append(signMsg);
			return stringBuffer.toString();
		}catch(Exception e){
			logger.error("加密出错",e);
			return null;
		}
		
	}

	static{
		//开奖号码转换
		//11x5
		IPrizeNumConverter syx5Converter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content;
			}
		}; 
		IPrizeNumConverter ssqConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content.replace("+", "|");
			}
		}; 
		
		prizeNumConverterMap.put(LotteryType.SD_11X5.getValue(),syx5Converter);
		prizeNumConverterMap.put(LotteryType.GD_11X5.getValue(),syx5Converter);
		prizeNumConverterMap.put(LotteryType.SSQ.getValue(),ssqConverter);
		prizeNumConverterMap.put(LotteryType.F3D.getValue(),syx5Converter);
		prizeNumConverterMap.put(LotteryType.QLC.getValue(),ssqConverter);
		prizeNumConverterMap.put(LotteryType.CJDLT.getValue(),ssqConverter);
		prizeNumConverterMap.put(LotteryType.PL3.getValue(),syx5Converter);
		prizeNumConverterMap.put(LotteryType.PL5.getValue(),syx5Converter);
		prizeNumConverterMap.put(LotteryType.QXC.getValue(),syx5Converter);
	}

	

static{
		
		/**
		 * 彩期转换
		 * */
		//默认的期号转换
		IPhaseConverter defaultPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20"+phase;
			}
		};
		IPhaseConverter ygPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		phaseConverterMap.put(LotteryType.SSQ, ygPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, ygPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, ygPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GD_11X5, defaultPhaseConverter);
       }

	
}
