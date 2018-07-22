package com.lottery.draw.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
/**
 * 
 * @author wyliuxiaoyan
 *
 */
@Service
public class XmacPhaseDrawWork extends AbstractVenderPhaseDrawWorker {
	protected final Logger logger = LoggerFactory.getLogger(XmacPhaseDrawWork.class);
	protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String openPrizeNum="110";

	@Override
	protected LotteryDraw get(IVenderConfig config,
			Integer lotteryType, String phase) {
		if(config==null){
			return null;
		}
		String lotno = getVenderConverter().convertLotteryType(LotteryType.getLotteryType(lotteryType));
		String message=getElement(lotno,phase,config);
		String returnStr="";
		try {
			 returnStr= HTTPUtil.post(config.getRequestUrl(),message,CharsetConstant.CHARSET_GBK);
		} catch (Exception e) {
			logger.error("开奖结果接口返回异常" + e);
		}
		try {
         	   //处理结果
         	   LotteryDraw lotteryDraw= dealResult(returnStr,lotteryType);
         	   return lotteryDraw;
		} catch (Exception e) {
			logger.error("处理开奖结果异常"+e);
		}
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_XMAC;
	}

	/**
	 * 查奖结果处理
	 * @param desContent
	 * @param count
	 * @return
	 */
	private LotteryDraw dealResult(String desContent,int lotteryType){
		LotteryDraw lotteryDraw=new LotteryDraw();
		lotteryDraw.setResponsMessage(desContent);
		try {
			if(StringUtils.isNotBlank(desContent)){
				 desContent="<param>"+desContent.split("\\?>")[1]+"</param>";
	         }
			 HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", desContent);
				String xCode=mapResult.get("xCode");	
				if("0".equals(xCode)){
					String value=mapResult.get("xValue");
				    String issue=value.split("\\_")[0];
				    String prizecode=value.split("\\_")[1];;
				    if(StringUtils.isNotBlank(prizecode)){
				    	 IPrizeNumConverter prConverter=prizeNumConverterMap.get(lotteryType);
				    	 lotteryDraw.setLotteryType(lotteryType);
				    	 lotteryDraw.setPhase(issue);
						lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
						 lotteryDraw.setWindCode(prConverter.convert(prizecode));
						 return lotteryDraw;
				    }
				}
				    return null;
		} catch (Exception e) {
			logger.error("厦门爱彩处理开奖结果异常");
		}
		return null;
	}
	private String getElement(String lotteryNo,String phase, IVenderConfig xmacConfig) {
		
			try {
				 String wAgent=xmacConfig.getAgentCode();
			     String wMsgID=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS);
			     StringBuilder wParam=new StringBuilder();
			     wParam.append("LotID=").append(lotteryNo)
                 .append("_").append("LotIssue=").append(phase);
	             String key=wAgent+openPrizeNum+wMsgID+wParam.toString()+xmacConfig.getKey();
	             String wSign= MD5Util.getMD5((key).getBytes("GBK"));
	             String param="wAgent="+wAgent+"&wAction="+openPrizeNum+"&wMsgID="+wMsgID+"&wSign="+wSign+"&wParam="+URLEncoder.encode(wParam.toString(),CharsetConstant.CHARSET_GBK);;
				 return param;
			} catch (Exception e) {
				logger.error("开奖号码查询异常" + e.getMessage());
			}
		return null;
	}

	static{
		//开奖号码转换
		//jxssc
		IPrizeNumConverter jxsscConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content;
			}
		}; 
		
		prizeNumConverterMap.put(LotteryType.JXSSC.getValue(),jxsscConverter);
	}


}
