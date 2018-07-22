package com.lottery.ticket.vender.process.zch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.ZcRaceService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.zch.ZchConverter;
@Service
public class GetZcPhaseFromZch {	
private  final Logger logger=LoggerFactory.getLogger(getClass());
@Autowired
private ZchConverter zhcConverter;
@Autowired
private LotteryPhaseService lotteryPhaseService;
@Autowired
private ZcRaceService zcRaceService;
	public JSONArray getZcPhase(Map<String,String> mapParam,IVenderConfig config){
		List<String> lotteryidList=new ArrayList<String>();
		lotteryidList.add("300");
		lotteryidList.add("301");
		lotteryidList.add("302");
		lotteryidList.add("303");
		JSONArray jsonArray=new JSONArray();
		for(String lotteryId:lotteryidList){
		String returnStr="";
		String msg=getMsgParam(config,"018" ,lotteryId,"");
		    LotteryType ltype=zhcConverter.getLotteryMap(lotteryId);
			try {
				returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(),msg);
			} catch (Exception e) {
				logger.error("获取足彩新期错误",e);
			}
			if(StringUtils.isNotBlank(returnStr)){
				try {
					Map<String,String> map=XmlParse.getElementAttribute("body/", "response", returnStr);
					String code=map.get("code");
					
					if(code.equals("0000")){
						
				/*		List<HashMap<String,String>> mapResultList=XmlParse.getElementAttributeList("body/response", "issue", returnStr);
						for(HashMap<String,String> mapResult:mapResultList){
							String phaseNo=mapResult.get("name");
							String  lottery=mapResult.get("lotteryId");
							LotteryType lotteryType=zhcConverter.getLotteryMap(lottery);
							Date startTime= CoreDateUtils.parseDate(mapResult.get("startTime"),CoreDateUtils.DATETIME);
							Date endTime=CoreDateUtils.parseDate(mapResult.get("endTime"),CoreDateUtils.DATETIME);
							Date stopTime=CoreDateUtils.parseDate(mapResult.get("stopTime"),CoreDateUtils.DATETIME);
							Date drawTime=CoreDateUtils.parseDate(mapResult.get("bonusTime"),CoreDateUtils.DATETIME);
						    String status=mapResult.get("status");
						    if(status.equals("1")){
						        LotteryPhase lotteryPhase=lotteryPhaseService.getByTypeAndPhase(lotteryType.getValue(), phaseNo);
			                    if(lotteryPhase==null){
			                    	lotteryPhase=new LotteryPhase();
			                    	lotteryPhase.setLotteryType(lotteryType.getValue());
			                    	lotteryPhase.setPhase(phaseNo);
			                    	lotteryPhase.setStartSaleTime(startTime);
			                    	lotteryPhase.setEndSaleTime(endTime);
			                    	lotteryPhase.setEndTicketTime(stopTime);
			                    	lotteryPhase.setHemaiEndTime(stopTime);
			                    	lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
			                    	lotteryPhase.setForSale(YesNoStatus.yes.value);
			                    	lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
			                    	lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
			                    	lotteryPhase.setDrawTime(drawTime);
			                    	lotteryPhase.setSwitchTime(new Date());
			    					lotteryPhase.setCreateTime(new Date());
			    					lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
			    					lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
			    					lotteryPhaseService.save(lotteryPhase);
			                    }else{
			                      
			                      if((stopTime.getTime()-lotteryPhase.getEndTicketTime().getTime())!=0){
			                    		logger.error("彩种:{}期号:{},数据库期结时间:{},出票商给于时间:{}不同，进行修改，请注意",new Object[]{lotteryType,phaseNo,CoreDateUtils.formatDateTime(stopTime),CoreDateUtils.formatDateTime(lotteryPhase.getEndTicketTime())});
			                    		lotteryPhase.setEndSaleTime(endTime);
				                    	lotteryPhase.setEndTicketTime(stopTime);
				                    	lotteryPhase.setHemaiEndTime(stopTime);
				                    	lotteryPhase.setSwitchTime(new Date());
				                    	lotteryPhaseService.update(lotteryPhase);
			                    	}
			                    	if(lotteryPhase.getPhaseStatus()!=PhaseStatus.open.getValue()){
			                    		logger.error("彩种:{}期号:{},平台已关闭，出票商仍可出票，请检查",new Object[]{lotteryType,phaseNo});
			                    		
			                    	 	lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
				                    	lotteryPhase.setForSale(YesNoStatus.yes.value);
				                    	lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
				                    	lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
				                    	lotteryPhase.setDrawTime(drawTime);
				                    	lotteryPhase.setSwitchTime(new Date());
				    					lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
				    					lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
				    					lotteryPhaseService.update(lotteryPhase); 
			                    	}
			                    }
						    }else if(status.equals("0")){
						    	 LotteryPhase lotteryPhase=lotteryPhaseService.getByTypeAndPhase(lotteryType.getValue(), phaseNo);
						    	 logger.error("彩种:{}得到未开售的期号:{}",new Object[]{lotteryType,phaseNo});
						         if(lotteryPhase==null){
				                    	lotteryPhase=new LotteryPhase();
				                    	lotteryPhase.setLotteryType(lotteryType.getValue());
				                    	lotteryPhase.setPhase(phaseNo);
				                    	lotteryPhase.setStartSaleTime(startTime);
				                    	lotteryPhase.setEndSaleTime(endTime);
				                    	lotteryPhase.setEndTicketTime(stopTime);
				                    	lotteryPhase.setHemaiEndTime(stopTime);
				                    	lotteryPhase.setPhaseStatus(PhaseStatus.unopen.value);
				                    	lotteryPhase.setForSale(YesNoStatus.no.value);
				                    	lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
				                    	lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
				                    	lotteryPhase.setDrawTime(drawTime);
				                    	lotteryPhase.setSwitchTime(new Date());
				    					lotteryPhase.setCreateTime(new Date());
				    					lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
				    					lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
				    					lotteryPhaseService.save(lotteryPhase);
				                    }
						    }else{
						    	LotteryPhase lotteryPhase=lotteryPhaseService.getByTypeAndPhase(lotteryType.getValue(), phaseNo);
						    	 logger.error("彩种:{},期号:{}的销售状态是:{},不是开售或停止",new Object[]{lotteryType,phaseNo,status});
						    	if(lotteryPhase!=null){
						    		lotteryPhase.setPhaseStatus(PhaseStatus.close.value);
			                    	lotteryPhase.setForSale(YesNoStatus.no.value);
			                    	lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
			                    	lotteryPhase.setTerminalStatus(TerminalStatus.disenable.value);
			                    	lotteryPhase.setDrawTime(drawTime);
			                    	lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
			    					lotteryPhaseService.update(lotteryPhase);
						    	}
						    }
						}*/
					}else{
						logger.error("抓取中彩汇新期返回错误，错误码是：{},发送的内容是:{}",new Object[]{code,msg});
					}
			     
				} catch (Exception e) {
					logger.error("中彩汇抓取新期错误",e);
					
				}
			}
			logger.error("足彩{}新期获取的字符串是:{}",new Object[]{ltype,returnStr});
		}
		
		if(jsonArray.size()==0){
			return null;
		}
		return jsonArray;
		
	}
	
	private String getMsgParam(IVenderConfig config,String code,String lotteryId,String issue){
		Document document=DocumentHelper.createDocument();
		Element rootElement=document.addElement("message");
		Element headerElement=rootElement.addElement("header");
		headerElement.addElement("sid").setText(config.getAgentCode());
		headerElement.addElement("cmd").setText(code);
		headerElement.addElement("digestType").setText("md5");
		Element bodyElement=rootElement.addElement("body");
		Element query=bodyElement.addElement("query");
		query.addAttribute("lotteryId", lotteryId);
		query.addAttribute("issue", issue);
		//DigestUtils
		String md5Str=config.getAgentCode()+config.getKey()+bodyElement.asXML();
		String md=DigestUtils.md5Hex(md5Str);
		headerElement.addElement("digest").setText(md);
		return "msg="+document.asXML();
	}
	
	public JSONArray getAgainst(LotteryType lotteryType,String phaseNo,IVenderConfig config){
		JSONArray jsonArray=new JSONArray();
		String phase="";
		String lotteryId=zhcConverter.convertLotteryType(lotteryType);
		String returnStr="";
		if(StringUtils.isNotBlank(phaseNo)){
			phase=zhcConverter.convertPhaseReverse(lotteryType, phaseNo);
		}
		try {
			returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(),getMsgParam(config,"018" ,lotteryId,phase));
		} catch (Exception e) {
			logger.error("获取足彩对阵错误",e);
		}
		if(StringUtils.isBlank(returnStr)){
			return null;
		}
		try{
			Map<String,String> map=XmlParse.getElementAttribute("body/", "response", returnStr);
			String code=map.get("code");
			if(code.equals("0000")){
				/*List<HashMap<String,String>> attributeList=XmlParse.getElementAttributeList("body/response/issue/gameList/","game",returnStr);
				for(HashMap<String,String> mapList:attributeList){
					String homeTeam=mapList.get("masterName");
					String awayTeam=mapList.get("guestName");
					String matchName=mapList.get("leageName");
					Date matchDate=CoreDateUtils.parseDate(mapList.get("startTime"), CoreDateUtils.DATETIME);
					int matchNum=Integer.valueOf(mapList.get("index"));
					ZcRace zcRace=zcRaceService.getByLotteryPhaseAndNum(lotteryType.getValue(), phase, matchNum);
					if(zcRace==null){
						zcRace=new ZcRace();
						zcRace.setCreateTime(new Date());
						zcRace.setLotteryType(lotteryType.getValue());
						zcRace.setPhase(phase);
						zcRace.setMatchNum(matchNum);
						zcRace.setMatchDate(matchDate);
						zcRace.setMatchName(matchName);
						zcRace.setHomeTeam(homeTeam);
						zcRace.setAwayTeam(awayTeam);
						zcRaceService.save(zcRace);
					}else{
						if(!homeTeam.equals(zcRace.getHomeTeam())||!awayTeam.equals(zcRace.getAwayTeam())||(matchDate.getTime()-zcRace.getMatchDate().getTime()!=0)){
							zcRace.setHomeTeam(homeTeam);
							zcRace.setAwayTeam(awayTeam);
							zcRace.setMatchDate(matchDate);
							zcRaceService.update(zcRace);
						}
					}
				}*/
			}else{
				logger.error("获取彩种为{},彩期为{}的对阵出错,返回码是{}",new Object[]{lotteryType,phaseNo,code});
			}
			logger.error("彩种{},获取的对阵信息是：{}",new Object[]{lotteryType,returnStr});
		}catch(Exception e){
			logger.error("中彩汇获取对阵出错",e);
		}
		if(jsonArray.size()==0){
			return null;
		}
		return jsonArray;
	
	}
}
