package com.lottery.draw.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.PhaseStatus;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IPrizeNumConverter;
import com.lottery.ticket.IVenderConfig;
@Service
public class ZYPhaseDrawWork  extends AbstractVenderPhaseDrawWorker {
	protected final static Logger logger = LoggerFactory.getLogger(ZYPhaseDrawWork.class);
	protected static Map<Integer, IPrizeNumConverter> prizeNumConverterMap = new HashMap<Integer, IPrizeNumConverter>();

	public static Map<String,String> sdklpk3WincodeMap=new HashMap<String, String>();
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String openPrizeNum="13007";

	
	protected LotteryDraw get(IVenderConfig config,
			Integer lotteryType, String phase) {
		if(config==null){
			return null;
		}
		String lotno = getVenderConverter().convertLotteryType(LotteryType.getLotteryType(lotteryType));
		String message=getElement(lotno,phase,config);
		String returnStr="";

		try {
			   returnStr = HTTPUtil.postJson(config.getRequestUrl(),message);
         	   //处理结果
         	   LotteryDraw lotteryDraw= dealResult(returnStr,lotteryType);
         	   return lotteryDraw;
		} catch (Exception e) {
			logger.error("发送:{},返回:{}",message,returnStr);
			logger.error("处理开奖结果异常",e);
		}
		return null;
	}

	
	protected TerminalType getTerminalType() {
		return TerminalType.T_ZY;
	}

	/**
	 * 查奖结果处理
	 * @param desContent
	 * @param lotteryType
	 * @return
	 */
	private LotteryDraw dealResult(String desContent,int lotteryType){
		LotteryDraw lotteryDraw=new LotteryDraw();
		lotteryDraw.setResponsMessage(desContent);
		try {
			 JSONObject map =JSONObject.fromObject(desContent);
		     JSONObject returnMsg=JSONObject.fromObject(map.get("msg"));

		        String returnCode=String.valueOf(returnMsg.get("errorcode"));
				if("0".equals(returnCode)){
					JSONObject mapInfo =(JSONObject)returnMsg.get("issueinfo");
				    String issue=mapInfo.getString("issue");
				    String prizecode=mapInfo.getString("bonuscode");
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
			logger.error("掌奕处理开奖结果异常");
		}
		return null;
	}



	private String getElement(String lotteryNo,String phase, IVenderConfig zyConfig) {
		try {
			JSONObject message = new JSONObject();
			JSONObject header = new JSONObject();
			header.put("messengerid",igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS));
			header.put("timestamp",DateUtil.format("yyyyMMddHHmmss", new Date()));
			header.put("transactiontype",openPrizeNum);
			header.put("des","0");
			header.put("agenterid",zyConfig.getAgentCode());
			message.put("header", header);		
			JSONObject map = new JSONObject();
			map.put("lotteryid",lotteryNo);
			map.put("issue",phase);
			message.put("msg",map);
			return message.toString();
		} catch (Exception e) {
			logger.error("开奖号码查询异常",e);
		}
		return null;
	}

	static{

		sdklpk3WincodeMap.put("01","1");
		sdklpk3WincodeMap.put("02","2");
		sdklpk3WincodeMap.put("03","3");
		sdklpk3WincodeMap.put("04","4");



		//开奖号码转换
		//山东11x5
		IPrizeNumConverter qlcConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content;
			}
		}; 
		IPrizeNumConverter ssqConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content.substring(0,17)+"|"+content.substring(18,20);
			}
		}; 
		
		IPrizeNumConverter dltConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content.substring(0,14)+"|"+content.substring(15,20);
			}
		};
		IPrizeNumConverter qlConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				return content.substring(0,20)+"|"+content.substring(21,23);
			}
		};
		IPrizeNumConverter sdklpkConverter=new IPrizeNumConverter() {
			@Override
			public String convert(String content) {
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(sdklpk3WincodeMap.get(con.split("\\-")[0]));
					stringBuffer.append(con.split("\\-")[1]);
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		}; 
		
		prizeNumConverterMap.put(LotteryType.SSQ.getValue(),ssqConverter);
		prizeNumConverterMap.put(LotteryType.F3D.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.CJDLT.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.QLC.getValue(),qlConverter);
		prizeNumConverterMap.put(LotteryType.PL3.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.PL5.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.QXC.getValue(),dltConverter);
		
		prizeNumConverterMap.put(LotteryType.SD_11X5.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.GD_11X5.getValue(),qlcConverter);
		prizeNumConverterMap.put(LotteryType.SD_KLPK3.getValue(),sdklpkConverter);
	}

	
	protected List<LotteryPhase> getOnSalePhaseList(int lotteryType, IVenderConfig venderConfig) {
	
		return null;
	}
	

}
