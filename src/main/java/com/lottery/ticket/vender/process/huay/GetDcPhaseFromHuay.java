package com.lottery.ticket.vender.process.huay;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.util.*;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;
@Service
public class GetDcPhaseFromHuay {	
	private static final Logger logger=LoggerFactory.getLogger(GetDcPhaseFromHuay.class);

	public JSONArray getDcPhase(Map<String, String> mapParam, IVenderConfig config){
		JSONArray ja = new JSONArray();
		String phaseCode = "13013";
		String lotteryidp = "200";
		try {
			String returnStr = HTTPUtil.post(config.getRequestUrl(),
					getMsgParam(config, phaseCode, lotteryidp, "",null) ,CharsetConstant.CHARSET_UTF8);
			if(StringUtil.isEmpty(returnStr)){
				return null;
			} 
			Element oelement =XmlParse.getElement("body/", "oelement", returnStr);
			if(!"0".equals(oelement.elementText("errorcode"))){
				logger.warn("huay查询单场新期返回:", oelement.elementText("errormsg"));
				return null;
			}
			
			Element element=XmlParse.getElement("body/elements", "element", returnStr);
			if(element == null){
				logger.error("huay查询单场返回数据格式不对 返回是：{}",returnStr);
				return null;
			}
			String lotteryid = element.elementText("lotteryid");//玩法编号：详见附录，必返
			String issue = element.elementText("issue");//要查询的期号，必返
			String starttime = element.elementText("starttime");//开始销售时间 20140509 090000
			String endtime = element.elementText("endtime");//停止销售时间 20140511 060000
			String status = element.elementText("status");//当前状态，status具体详细值，请参看奖期信息通知
			JSONObject object=new JSONObject();
			object.put("phaseNo", "1"+issue);
			object.put("lotteryType", LotteryType.DC_SPF.value);
			Date sta = DateUtil.parse("yyyyMMdd hhmmss", starttime);
			object.put("startTime", DateUtil.format(CoreDateUtils.DATETIME,sta));
			Date endt = DateUtil.parse("yyyyMMdd hhmmss", endtime);
			object.put("endTime", DateUtil.format(CoreDateUtils.DATETIME,endt));
			object.put("drawTime", DateUtil.format(CoreDateUtils.DATETIME, endt));
			object.put("phaseStatus",  PhaseStatus.open.value);
			ja.add(object);
			return ja;
			
		} catch (Exception e) {
			logger.error("查询华阳北单新期出错",e);
		}
		return null;
	}
	
	public JSONArray getDcSchedule(Map<String, String> requestMap, IVenderConfig config) {
		JSONArray retja = new JSONArray();
		String duizhenCode = "13012";
		String lotteryId = "200";
		try {
			String phaseNo=requestMap.get("phaseNo");
			logger.info("查询北单,phaseNo:{}对阵", phaseNo);
			String returnStr = HTTPUtil.post(config.getRequestUrl(),
						getMsgParam(config, duizhenCode, lotteryId, phaseNo.substring(1), null) ,CharsetConstant.CHARSET_UTF8);
			if(StringUtils.isBlank(returnStr)){
				return null;
			}
			logger.error("北单:{}期对阵查询返回:{}",phaseNo,returnStr);
			Element oelement =XmlParse.getElement("body/", "oelement", returnStr);
			if(!"0".equals(oelement.elementText("errorcode"))){
				logger.warn("huay查询单场对阵返回:", oelement.elementText("errormsg"));
				return null;
			}
			Element elements=XmlParse.getElement("body/", "elements", returnStr);
			Iterator<Element> matchs = elements.elementIterator("element");
			while (matchs.hasNext()) {
				Element element = matchs.next();
				String lotteryid = element.elementText("lotteryid");//玩法编号  200
				String lotteryissue = element.elementText("lotteryissue");//彩票期号 40507
				String lotttime = element.elementText("lotttime");//批次时间 40507
				String lottweek = element.elementText("lottweek");//批次时间的星期值 0 
				String starttime = element.elementText("starttime");//每批次投注开始时间  2014-05-11 09:00
				String endtime = element.elementText("endtime");//每批次投注截止时间 2014-05-13 02:40
				String ballid = element.elementText("ballid"); //显示编号 1
				
				String gamename = element.elementText("gamename");//赛事名称 14J2联赛
				String gamestarttime = element.elementText("gamestarttime");//每场比赛开始时间  2014-05-09 23:55
				String gameendtime = element.elementText("gameendtime");//每场比赛投注截止时间  2014-05-09 23:55
				String hteam = element.elementText("hteam"); //主队名称 山形山神
				String isconcede = element.elementText("isconcede");//是否让球或让分  0：表示不让	-n：表示主让客  n：表示客让主	篮彩大小分时，该参数为预设总分
				String vteam = element.elementText("vteam");//客队名称  千叶市原
				
				//-1:审核未通过 0：销售中 1：停止销售 2：已开奖，奖金处理中 3：奖金处理完毕 4：取消
				String status = element.elementText("status");//售票状态 	0：销售中	1：停止销售   3
				
				String score = element.elementText("score");//比分
				String hteam_half_score = element.elementText("hteam_half_score");//单场主队半场进球数 0
				String vteam_half_score = element.elementText("vteam_half_score");//单场客队半场进球数 0
				String hteam_full_score = element.elementText("hteam_full_score");//单场主队全场进球数 1
				String vteam_full_score = element.elementText("vteam_full_score");//单场客队全场进球数 1
				
				//String gameresult = element.elementText("gameresult");//赛果 1
				//String bonus = element.elementText("bonus");//奖金
				//String spvalue = element.elementText("spvalue");//本场比赛SP值  4.031237
				
				JSONObject jo = new JSONObject();
				jo.put("phase", "1"+lotteryissue);//彩期号
				jo.put("dcType", DcType.normal.value());  //场次类型，普通北单 or 北单胜负
				//jo.put("createTime", ""); 
				//jo.put("updateTime", ""); //修改时间
				jo.put("matchNum", ballid);  //场次
				jo.put("endSaleTime", gameendtime+":00"); //停止销售时间
				jo.put("matchDate", gamestarttime+":00"); //比赛日期
				jo.put("homeTeam", hteam); //主队
				jo.put("awayTeam", vteam); //客队
				jo.put("handicap", isconcede); //让球
				
				String wholeScore = "";
				if(!StringUtil.isEmpty(hteam_full_score) && !StringUtil.isEmpty(vteam_full_score)){
					wholeScore = hteam_full_score + ":" + vteam_full_score;
				}
				jo.put("wholeScore", wholeScore);  //全场比分
				
				String halfScore = "";
				if(!StringUtil.isEmpty(hteam_half_score) && !StringUtil.isEmpty(vteam_half_score)){
					halfScore = hteam_half_score + ":" + vteam_half_score;
				}
				jo.put("halfScore", halfScore);  //半场比分
				jo.put("matchName", gamename); //赛事
				Integer joStatus = RaceStatus.CLOSE.value;
				//-1:审核未通过 0：销售中 1：停止销售 2：已开奖，奖金处理中 3：奖金处理完毕 4：取消
				if("-1".equals(status)){
					joStatus = RaceStatus.UNOPEN.value;
				}else if("0".equals(status)){
					joStatus = RaceStatus.OPEN.value;
				}else if("1".equals(status)){
					joStatus = RaceStatus.PAUSE.value;
				}else if("4".equals(status)){
					joStatus = RaceStatus.CANCEL.value;
				}
				jo.put("status", joStatus); //状态
				jo.put("prizeStatus", PrizeStatus.result_null.value); //开奖状态   获取到结果   开奖  派奖 
				retja.add(jo);
			}
		} catch (Exception e) {
			logger.error("查询北单对阵出错",e);
		}
		return retja;
	}
	
	
	public JSONArray getDcDrawResult(Map<String, String> requestMap, IVenderConfig config) {
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		String resultCode = "13014";
		String[] lotteryids = {"200","201","202" ,"203","204"};//"胜平负" "上下单双" "总进球" "比分" "半全场"
		String phase = requestMap.get("phaseNo");
		String sportballid1 = requestMap.get("sportballid");
		try {
			for(String lotteryid : lotteryids) {
				String returnStr = HTTPUtil.post(config.getRequestUrl(),
						getMsgParam(config, resultCode ,lotteryid, phase.substring(1), sportballid1) ,CharsetConstant.CHARSET_UTF8);
				Element oelement =XmlParse.getElement("body/", "oelement", returnStr);
				if(!"0".equals(oelement.elementText("errorcode"))){
					logger.warn("huay查询单场赛果返回:", oelement.elementText("errormsg"));
					return null;
				}
				logger.error("从华阳获取[{}]的赛果是:{}",lotteryid,returnStr);
				Element elements=XmlParse.getElement("body/", "elements", returnStr);
				@SuppressWarnings("unchecked")
				Iterator<Element> matchs = elements.elementIterator("element");
				while (matchs.hasNext()) {
					Element element = matchs.next();
					String sportballid = element.elementText("sportballid");
					JSONObject jo = map.get(sportballid);
					if(jo == null){
						jo = new JSONObject();
						jo.put("matchNum", sportballid);
						map.put(sportballid, jo);
						jo.put("spSfp", ""); //胜负平sp值
						jo.put("sfpResult", ""); //胜负平赛果
						jo.put("spSxds", ""); //上下单双sp值
						jo.put("sxdsResult", ""); //上下单双赛果
						jo.put("spJqs", ""); //进球数sp值
						jo.put("jqsResult", "");//总进球赛果
						jo.put("spBf", ""); //单场比分sp值
						jo.put("bfResult", "");//比分赛果
						jo.put("spBcsfp", ""); //半场胜负平sp值
						jo.put("bcsfpResult", "");//半场胜平负赛果
					}
					String spvalue = element.elementText("spvalue");
					String gameresult = element.elementText("gameresult");
					if("200".equals(lotteryid)){
						jo.put("spSfp", spvalue); //胜负平sp值
						jo.put("sfpResult", gameresult); //胜负平赛果
					}else if("201".equals(lotteryid)){
						jo.put("spSxds", spvalue); //上下单双sp值
						jo.put("sxdsResult", gameresult); //上下单双赛果
					}else if("202".equals(lotteryid)){
						jo.put("spJqs", spvalue); //进球数sp值
						jo.put("jqsResult", gameresult);//总进球赛果
					}else if("203".equals(lotteryid)){
						jo.put("spBf", spvalue); //单场比分sp值
						jo.put("bfResult", gameresult);//比分赛果
					}else if("204".equals(lotteryid)){
						jo.put("spBcsfp", spvalue); //半场胜负平sp值
						jo.put("bcsfpResult", gameresult);//半场胜平负赛果
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询北单赛果出错",e);
			
		}
		return JSONArray.fromObject(map.values());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MatchSpDomain> getDcSp(Map<String, String> requestMap,
			IVenderConfig config) {
		Map<String, MatchSpDomain> map = new HashMap<String, MatchSpDomain>();
		// "胜平负"  "上下单双"  "总进球"  "比分"  "半全场"
		String[] lotteryids = {"200","201","202" ,"203","204"};
		String spCode = "13999";
		try{
			JSONArray ja = getDcPhase(requestMap, config);
			String phaseNo = "";
			if(ja!=null){
				for(int i=0;i<ja.size();i++){
					JSONObject object=ja.getJSONObject(i);
					phaseNo=object.getString("phaseNo");
				}
			}
			
			for(String lotteryid : lotteryids) {
				String param = getMsgParam(config, spCode ,lotteryid, phaseNo, null);
				String returnStr = HTTPUtil.post(config.getRequestUrl(),param ,CharsetConstant.CHARSET_UTF8 );
				if(StringUtils.isNotBlank(returnStr)){
					Element elements=XmlParse.getElement("body/", "elements", returnStr);
					if(elements == null){
						logger.error("华阳查询北单sp返回的格式不正确 param:{}, returnStr:{}", new Object[]{param, returnStr});
						continue;
					}
					Iterator<Element> matchs = elements.elementIterator("element");
					
					while (matchs.hasNext()) {
						Element element = matchs.next();
						String played = element.elementText("played");
						MatchSpDomain bsp = map.get(played);
						if(bsp == null){
							bsp = new MatchSpDomain();
							bsp.setMatchNum(phaseNo+played);
							Map<String, Map<String, Object>> lottery_type = new HashMap<String, Map<String, Object>>();
							bsp.setLotteryType(lottery_type);
							map.put(played, bsp);
						}
						if("200".equals(lotteryid)){
							String sp_s = element.elementText("sp_s");
							String sp_p = element.elementText("sp_p");
							String sp_f = element.elementText("sp_f");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_SFP_S_VALUE,sp_s);
							cons.put(LotteryConstant.DC_SFP_P_VALUE,sp_p);
							cons.put(LotteryConstant.DC_SFP_F_VALUE,sp_f);
							bsp.getLotteryType().put(LotteryType.DC_SPF.value+"", cons);
						}else if("201".equals(lotteryid)){
							String sp_s = element.elementText("sp_s");
							String sp_x = element.elementText("sp_x");
							String sp_d = element.elementText("sp_d");
							String sp_sh = element.elementText("sp_sh");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_SXDS_SD_VALUE,sp_s);
							cons.put(LotteryConstant.DC_SXDS_SS_VALUE,sp_x);
							cons.put(LotteryConstant.DC_SXDS_XD_VALUE,sp_d);
							cons.put(LotteryConstant.DC_SXDS_XS_VALUE,sp_sh);
							bsp.getLotteryType().put(LotteryType.DC_SXDS.value+"", cons);
						}else if("202".equals(lotteryid)){
							String sp_0 = element.elementText("sp_0");
							String sp_1 = element.elementText("sp_1");
							String sp_2 = element.elementText("sp_2");
							String sp_3 = element.elementText("sp_3");
							String sp_4 = element.elementText("sp_4");
							String sp_5 = element.elementText("sp_5");
							String sp_6 = element.elementText("sp_6");
							String sp_7 = element.elementText("sp_7");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_JQX_0_VALUE,sp_0);
							cons.put(LotteryConstant.DC_JQX_1_VALUE,sp_1);
							cons.put(LotteryConstant.DC_JQX_2_VALUE,sp_2);
							cons.put(LotteryConstant.DC_JQX_3_VALUE,sp_3);
							cons.put(LotteryConstant.DC_JQX_4_VALUE,sp_4);
							cons.put(LotteryConstant.DC_JQX_5_VALUE,sp_5);
							cons.put(LotteryConstant.DC_JQX_6_VALUE,sp_6);
							cons.put(LotteryConstant.DC_JQX_7_VALUE,sp_7);
							bsp.getLotteryType().put(LotteryType.DC_ZJQ.value+"", cons);
						}else if("203".equals(lotteryid)){
							String b0 =  element.elementText("b0");
							String b1 =  element.elementText("b1");
							String b2 =  element.elementText("b2");
							String b3 =  element.elementText("b3");
							String b4 =  element.elementText("b4");
							String b5 =  element.elementText("b5");
							String b6 =  element.elementText("b6");
							String b7 =  element.elementText("b7");
							String b8 =  element.elementText("b8");
							String b9 =  element.elementText("b9");//胜其他 (53.87)
							String b10 =  element.elementText("b10");
							String b11 =  element.elementText("b11");
							String b12 =  element.elementText("b12");
							String b13 =  element.elementText("b13");
							String b14 =  element.elementText("b14");//平其他	(221.45)
							String b15 =  element.elementText("b15");
							String b16 =  element.elementText("b16");
							String b17 =  element.elementText("b17");
							String b18 =  element.elementText("b18");
							String b19 =  element.elementText("b19");
							String b20 =  element.elementText("b20");
							String b21 =  element.elementText("b21");
							String b22 =  element.elementText("b22");
							String b23 =  element.elementText("b23");
							String b24 =  element.elementText("b24");//负其他 (130.58) 
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_BF_S_10_VALUE,b0);
							cons.put(LotteryConstant.DC_BF_S_20_VALUE, b1);
							cons.put(LotteryConstant.DC_BF_S_21_VALUE, b2);
							cons.put(LotteryConstant.DC_BF_S_30_VALUE, b3);
							cons.put(LotteryConstant.DC_BF_S_31_VALUE, b4);
							cons.put(LotteryConstant.DC_BF_S_32_VALUE, b5);
							cons.put(LotteryConstant.DC_BF_S_40_VALUE, b6);
							cons.put(LotteryConstant.DC_BF_S_41_VALUE, b7);
							cons.put(LotteryConstant.DC_BF_S_42_VALUE, b8);
							cons.put(LotteryConstant.DC_BF_S_Ohter_VALUE, b9);
							cons.put(LotteryConstant.DC_BF_P_0_VALUE, b10);
							cons.put(LotteryConstant.DC_BF_P_1_VALUE, b11);
							cons.put(LotteryConstant.DC_BF_P_2_VALUE, b12);
							cons.put(LotteryConstant.DC_BF_P_3_VALUE, b13);
							cons.put(LotteryConstant.DC_BF_P_Other_VALUE, b14);
							cons.put(LotteryConstant.DC_BF_F_01_VALUE, b15);
							cons.put(LotteryConstant.DC_BF_F_02_VALUE, b16);
							cons.put(LotteryConstant.DC_BF_F_12_VALUE, b17);
							cons.put(LotteryConstant.DC_BF_F_03_VALUE, b18);
							cons.put(LotteryConstant.DC_BF_F_13_VALUE, b19);
							cons.put(LotteryConstant.DC_BF_F_23_VALUE, b20);
							cons.put(LotteryConstant.DC_BF_F_04_VALUE, b21);
							cons.put(LotteryConstant.DC_BF_F_14_VALUE, b22);
							cons.put(LotteryConstant.DC_BF_F_24_VALUE, b23);
							cons.put(LotteryConstant.DC_BF_F_Other_VALUE,b24);
							bsp.getLotteryType().put(LotteryType.DC_BF.value+"", cons);
						}else if("204".equals(lotteryid)){
							String b0 =  element.elementText("b0");
							String b1 =  element.elementText("b1");
							String b2 =  element.elementText("b2");
							String b3 =  element.elementText("b3");
							String b4 =  element.elementText("b4");
							String b5 =  element.elementText("b5");
							String b6 =  element.elementText("b6");
							String b7 =  element.elementText("b7");
							String b8 =  element.elementText("b8");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_BCSFP_SS_VALUE, b0);
							cons.put(LotteryConstant.DC_BCSFP_SP_VALUE, b1);
							cons.put(LotteryConstant.DC_BCSFP_SF_VALUE, b2);
							cons.put(LotteryConstant.DC_BCSFP_PS_VALUE, b3);
							cons.put(LotteryConstant.DC_BCSFP_PP_VALUE, b4);
							cons.put(LotteryConstant.DC_BCSFP_PF_VALUE, b5);
							cons.put(LotteryConstant.DC_BCSFP_FS_VALUE, b6);
							cons.put(LotteryConstant.DC_BCSFP_FP_VALUE, b7);
							cons.put(LotteryConstant.DC_BCSFP_FF_VALUE, b8);
							bsp.getLotteryType().put(LotteryType.DC_BQC.value+"", cons);
						}
				     }
				}
			}
		} catch (Exception e) {
			logger.error("查询北单sp出错",e);
		}
		return map.values();
	}
	
	/**
	 * 
	 * @param config
	 * @param code
	 * @param lotteryId
	 * @param issue
	 * @return
	 */
	private String getMsgParam(IVenderConfig config, String code, String lotteryId, String issue, String sportballid){
		XmlParse xmlParse = new XmlParse("message", "header", "body");
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		//此处先不写 有问题再改
		String messageid = "1";//igenGeneratorService.getMessageId();
		xmlParse.addHeaderElement("messengerid", messageid);
		xmlParse.addHeaderElement("agenterid",config.getAgentCode());
		xmlParse.addHeaderElement("transactiontype", code); //北单赛程 13012   查询当前期信息（13013）     北单各彩种SP值查询(13999)
		xmlParse.addHeaderElement("timestamp", timestamp);
		xmlParse.addHeaderElement("username",config.getAgentCode());
		
		xmlParse.getDocument().getRootElement().addAttribute("version", "1.0");
		
		Element bodyeElement=xmlParse.getBodyElement();
		Element elements= bodyeElement.addElement("elements");
		Element element2=elements.addElement("element");
		HashMap<String, String> bodyAttr = new HashMap<String, String>();
		bodyAttr.put("lotteryid", lotteryId);
		bodyAttr.put("lotteryissue", issue);
		if(StringUtil.isNotEmpt(sportballid)){
			bodyAttr.put("sportballid", sportballid);
		}
		
		xmlParse.addBodyElement(bodyAttr, element2);
		try {
			String md = MD5Util.MD5(timestamp + config.getKey() + xmlParse.getBodyElement().asXML());
			xmlParse.addHeaderElement("digest", md);
		} catch (UnsupportedEncodingException e) {
			
		}
		return xmlParse.asXML();
	}

	
	public static void main(String[] args) {
		//String url1 = "http://interlib.198tc.com/b2blib/lotteryxml.php";// 124.205.138.74
		//String HUAYANG_KEY = "859fe415351be71a0d9d2ab8f0de2996";
		//String AGENT_ID = "10000542";
		String url1 = "http://124.202.244.142:81/b2blib/lotteryxml.php";// 124.205.138.74
		String HUAYANG_KEY = "56de81b61bdcafe2a8ca12d7e61b8532";
		String AGENT_ID = "10000602";
		
		IVenderConfig config = new IVenderConfig() {
			@Override
			public Integer getTimeOutSecondForSend() {
				return 0;
			}
			@Override
			public Integer getTimeOutSecondForCheck() {
				return 0;
			}
			@Override
			public Integer getSendCount() {
				return 0;
			}
			@Override
			public String getRequestUrl() {
				return "http://interlib.198tc.com/b2blib/lotteryxml.php";
			}
			@Override
			public String getPublicKey() {
				return null;
			}
			@Override
			public String getPrivateKey() {
				return null;
			}
			@Override
			public Integer getPort() {
				return 0;
			}
			@Override
			public String getPasswd() {
				return null;
			}
			@Override
			public String getKey() {
				return "859fe415351be71a0d9d2ab8f0de2996";
			}
			@Override
			public String getCheckUrl() {
				return null;
			}
			@Override
			public Integer getCheckCount() {
				return 0;
			}
			@Override
			public String getAgentCode() {
				return "10000542";
			}
			@Override
			public Long getTerminalId() {
			
				return null;
			}

			@Override
			public Boolean getSyncTicketCheck() {
				return Boolean.TRUE;
			}
		};
		Map<String, String> requestMap = new HashMap<String, String>();
		//requestMap.put("lotteryType", "200");
		requestMap.put("phaseNo", "141007");
		//requestMap.put("sportballid", "133");
		
		//String phaseNo = "140508";
		//System.out.println(phaseNo.substring(1));
		//System.out.println(new GetDcPhaseFromHuay().getDcPhase(null, config));
		//System.out.println(new GetDcPhaseFromHuay().getDcSchedule(requestMap, config));
		//System.out.println(new GetDcPhaseFromHuay().getDcDrawResult(requestMap, config));;
		/*Collection<MatchSpDomain> cols = new GetDcPhaseFromHuay().getDcSp(requestMap,config);
		for (MatchSpDomain bjdcSp : cols) {
			System.out.println(bjdcSp.getMatchNum());
			System.out.println(bjdcSp.getLotteryType());
		}*/
	}
}
