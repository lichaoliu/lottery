package com.lottery.ticket.vender.process.gaode;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.util.*;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gaode.GaoDeLotteryDef;
import com.lottery.ticket.vender.impl.gaode.GaodeConfig;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
@Service
public class GetDcPhaseFromGaoDe {	
	@Autowired
	protected LotteryPhaseService phaseService;
	private static final Logger logger=LoggerFactory.getLogger(GetDcPhaseFromGaoDe.class);


	/**
	 * 发送http请求
	 *
	 * @param url 请求地址，param请求参数
	 * @throws Exception
	 */
	public  String getResult(String url,String charset) throws Exception {
		try {
			URL reqUrl = new URL(url);
			final HttpURLConnection connection = (HttpURLConnection) reqUrl.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(30 * 1000);
			connection.setReadTimeout(30 * 1000);
			int charCount = -1;
			InputStream in = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer responseMessage = new StringBuffer();
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
			in.close();
			return responseMessage.toString();
		} catch (Exception e) {
			throw e;
		}

	}


	public JSONArray getDcPhase(Map<String, String> mapParam, IVenderConfig config){
		JSONArray ja = new JSONArray();
		try {
			String returnStr = getResult(config.getCheckUrl(),CharsetConstant.CHARSET_UTF8);
			if(StringUtil.isEmpty(returnStr)){
				logger.error("查询高德北单新期返回为空");
				return null;
			}
			logger.error("北单查询到的新期:{}",returnStr);
			String mathStr="<body>"+returnStr.split("\\?>")[1]+"</body>";
			logger.error("matchstr={}",mathStr);
			List<HashMap<String, String>> elements=null;
			try {
				elements=XmlParse.getElementAttributeList("selling", "w", mathStr);
			} catch (DocumentException e) {
				logger.error("解析高德北单新期异常",e);
			}
			if(elements==null){
				return null;
			}
			for(HashMap<String,String>map:elements){
				if(!GaoDeLotteryDef.toLotteryTypeMap.containsKey(map.get("li"))){
					continue;
				}
				int lotteryid =GaoDeLotteryDef.toLotteryTypeMap.get(map.get("li"));//玩法编号：详见附录，必返
				String issue = map.get("is");//要查询的期号，必返
				String starttime =map.get("bt")+":00";//开始销售时间2016-01-05 10:30
				String endtime = map.get("et")+":00";//停止销售时间 2016-01-05 10:30
				JSONObject object=new JSONObject();
				object.put("phaseNo", "1"+issue);
				object.put("lotteryType",lotteryid);
				object.put("startTime",starttime);
				object.put("endTime",endtime);
				object.put("drawTime", endtime);
				object.put("phaseStatus", PhaseStatus.open.value);
				ja.add(object);
			}
			return ja;
			
		} catch (Exception e) {
			logger.error("查询高德北单新期出错",e);
		}
		return null;
	}
	
	public JSONArray getDcSchedule(Map<String, String> requestMap, GaodeConfig config) {
		JSONArray retja = new JSONArray();
		try {
			String phaseNo=requestMap.get("phaseNo");
			String[] lotteryids = {"w","d"};
			for(String lotteryType:lotteryids){
				String returnStr = getResult(config.getDcUrl()+lotteryType+"_"+phaseNo.substring(1)+".xml",CharsetConstant.CHARSET_GB2312);
				if(StringUtils.isBlank(returnStr)){
					logger.error("北单:{}期对阵查询返回为空",phaseNo);
					return null;
				}
				logger.error("北单赛程为:{}",returnStr);

				Document doc  = DocumentHelper.parseText(returnStr);
				Element rootElt = doc.getRootElement(); 
				Iterator<Object> iterator=rootElt.elementIterator();

				while(iterator.hasNext()){
					Element element=(Element)iterator.next();
					String lotteryissue = rootElt.attributeValue("is");//彩票期号 40507
					String gamename = element.attributeValue("gn");//赛事名称 14J2联赛
					String gameendtime = element.attributeValue("et");//每场比赛投注截止时间  2014-05-09 23:55
					String gamestarttime = element.attributeValue("gt");//每场比赛开始时间2014-05-09 23:55
					String hteam = element.attributeValue("h"); //主队名称 山形山神
					String isconcede = element.attributeValue("r");//是否让球或让分  0：表示不让	-n：表示主让客  n：表示客让主	篮彩大小分时，该参数为预设总分
					String vteam = element.attributeValue("a");//客队名称  千叶市原
					String status = element.attributeValue("st");//售票状态 	2：未开售 1销售中 -1  已停售-2  已开奖3  已计算4  已返奖
					String score = element.attributeValue("sr");//比分
					
					JSONObject jo = new JSONObject();
					jo.put("phase", "1"+lotteryissue);//彩期号
					if("d".equals(lotteryType)){
						jo.put("dcType", DcType.sfgg.value());  //场次类型，普通北单 or 北单胜负
					}else{
						jo.put("dcType", DcType.normal.value());  //场次类型，普通北单 or 北单胜负
					}

                    String matchNum=element.getName().replace("w","");

					jo.put("matchNum", Integer.valueOf(matchNum));  //场次
					jo.put("endSaleTime", gameendtime+":00"); //停止销售时间
					jo.put("matchDate", gamestarttime+":00"); //比赛日期
					jo.put("homeTeam", hteam); //主队
					jo.put("awayTeam", vteam); //客队
					jo.put("handicap", isconcede); //让球
					jo.put("matchName", gamename); //赛事
					
					jo.put("sr", score);  //全场比分
					
					
					Integer joStatus = RaceStatus.CLOSE.value;
					jo.put("prizeStatus", PrizeStatus.not_open.value);
					//2：未开售 1销售中 -1  已停售-2  已开奖3  已计算4  已返奖
					if("2".equals(status)){
						joStatus = RaceStatus.UNOPEN.value;
						jo.put("prizeStatus", PrizeStatus.not_open.value);
					}else if("1".equals(status)){
						joStatus = RaceStatus.OPEN.value;
						jo.put("prizeStatus", PrizeStatus.not_open.value);
					}else if("-2".equals(status)){
						joStatus = RaceStatus.CLOSE.value;
						jo.put("prizeStatus", PrizeStatus.draw.value);
					}else if("3".equals(status)){
						joStatus = RaceStatus.CLOSE.value;
						jo.put("prizeStatus", PrizeStatus.draw.value);
					}else if("4".equals(status)){
						joStatus = RaceStatus.CLOSE.value;
						if(StringUtils.isNotEmpty(score)&&score.contains("$")){
							if(StringUtils.isNotEmpty(score.split("\\$")[0])&&score.split("\\$")[0].contains("延")||
							 (StringUtils.isNotEmpty(score.split("\\$")[1])&&score.split("\\$")[1].contains("延"))){
								jo.put("prizeStatus", PrizeStatus.not_open.value); //开奖状态
							}else{
								jo.put("prizeStatus", PrizeStatus.result_set.value); //开奖状态
							}
						}
					}else if("-1".equals(status)){
						joStatus = RaceStatus.CLOSE.value;
						jo.put("prizeStatus", PrizeStatus.not_open.value); //开奖状态
					}
					jo.put("status", joStatus); //状态
					retja.add(jo);
				}
			}
			
		} catch (Exception e) {
			logger.error("高德查询北单对阵出错",e);
		}
		return retja;
	}
	
	/**
	 * 获取赛果
	 * @param requestMap
	 * @param config
	 * @return
	 */
	public JSONArray getDcDrawResult(Map<String, String> requestMap, GaodeConfig config) {
		Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		String[] lotteryids = {"w","o","g","s","b","d"};
		String phase = requestMap.get("phaseNo");
		try {
			for(String lotteryid:lotteryids) {
				String returnStr = getResult(config.getDcUrl()+lotteryid+"_"+phase.substring(1)+".xml",CharsetConstant.CHARSET_GB2312);
				if(returnStr.isEmpty()){
					logger.warn("高德查询单场赛果返回为null");
					return null;
				}
				
				Document doc  = DocumentHelper.parseText(returnStr);
				Element rootElt = doc.getRootElement(); 
				Iterator<Object> iterator=rootElt.elementIterator();
				int i=1;
				while(iterator.hasNext()){
				    Element element=(Element)iterator.next();
					String sportballid = element.elementText("li");//玩法
					JSONObject jo = map.get(phase+i);
					if(jo == null){
						jo = new JSONObject();
						jo.put("matchNum", i);
						map.put(phase+i, jo);
						jo.put("spSfp", ""); //胜平负sp值
						jo.put("sfpResult",""); //胜平负赛果
						jo.put("spBcsfp", ""); //胜平负sp值
						jo.put("bcsfpResult","");//半场胜平负赛果
						jo.put("spJqs",""); //进球数sp值
						jo.put("jqsResult","");//总进球赛果
						jo.put("spSxds",""); //上下单双sp值
						jo.put("sxdsResult",""); //上下单双赛果
						jo.put("spBf",""); //单场比分sp值
						jo.put("bfResult", "");//比分赛果
						jo.put("spsf", ""); //单场胜负sp值
						jo.put("sfResult", "");//胜负赛果
					}
					if("d".equals(lotteryid)){
						jo.put("dcType", DcType.sfgg.value());  //场次类型，普通北单 or 北单胜负
					}else{
						jo.put("dcType", DcType.normal.value());  //场次类型，普通北单 or 北单胜负
					}
					if("w".equals(lotteryid)){//让分胜平负
						jo.put("spSfp", element.attributeValue("sp")); //胜平负sp值
						if(dcsfpMap.containsKey(element.attributeValue("rt"))){
							jo.put("sfpResult", dcsfpMap.get(element.attributeValue("rt"))); //胜平负赛果
						}else{
							jo.put("sfpResult", element.attributeValue("rt")); //胜平负赛果
						}
					}else if("b".equals(lotteryid)){//半全场
						jo.put("spBcsfp", element.attributeValue("sp")); //胜平负sp值
						if(bcspfMap.containsKey(element.attributeValue("rt"))){
							jo.put("bcsfpResult",bcspfMap.get(element.attributeValue("rt")));//半场胜平负赛果
						}else{
							jo.put("bcsfpResult",element.attributeValue("rt"));//半场胜平负赛果
						}
					}else if("g".equals(lotteryid)){//总进球数
						jo.put("spJqs", element.attributeValue("sp")); //进球数sp值
						if(zjqMap.containsKey(element.attributeValue("rt"))){
							jo.put("jqsResult", zjqMap.get(element.attributeValue("rt")));//总进球赛果
						}else{
							jo.put("jqsResult", element.attributeValue("rt"));//总进球赛果
						}
						
					}else if("o".equals(lotteryid)){//上下单双
						jo.put("spSxds", element.attributeValue("sp")); //上下单双sp值
						if(sxdsMap.containsKey(element.attributeValue("rt"))){
							jo.put("sxdsResult", sxdsMap.get(element.attributeValue("rt"))); //上下单双赛果
						}else{
							jo.put("sxdsResult", element.attributeValue("rt")); //上下单双赛果
						}
					}else if("s".equals(lotteryid)){//比分
						jo.put("spBf", element.attributeValue("sp")); //单场比分sp值
						if(bfMap.containsKey(element.attributeValue("rt"))){
							jo.put("bfResult", bfMap.get(element.attributeValue("rt")));//比分赛果
						}else{
							jo.put("bfResult", element.attributeValue("rt"));//比分赛果
						}
					}else if("d".equals(lotteryid)){//胜负过关
						jo.put("spsf", element.attributeValue("sp")); //单场胜负sp值
						if(sfMap.containsKey(element.attributeValue("rt"))){
							jo.put("sfResult", sfMap.get(element.attributeValue("rt")));//胜负赛果
						}else{
							jo.put("sfResult", element.attributeValue("rt"));//胜负赛果
						}
					}
					i++;
				}
			}
		} catch (Exception e) {
			logger.error("查询北单赛果出错",e);
			
		}
		return JSONArray.fromObject(map.values());
	}
	
	
	
	
	//单场胜负平
	private static Map<String,String>dcsfpMap=new HashMap<String, String>();
	private static Map<String,String>sxdsMap=new HashMap<String, String>();
	private static Map<String,String>bfMap=new HashMap<String, String>();
	private static Map<String,String>bcspfMap=new HashMap<String, String>();
	private static Map<String,String>zjqMap=new HashMap<String, String>();
	private static Map<String,String>sfMap=new HashMap<String, String>();
	static{
		dcsfpMap.put("胜", "3");
		dcsfpMap.put("平", "1");
		dcsfpMap.put("负", "0");
		
		sxdsMap.put("下+单", "3");
		sxdsMap.put("上+单", "1");
		sxdsMap.put("下+双", "4");
		sxdsMap.put("上+双", "2");
		
		bfMap.put("胜其他", "1");
		bfMap.put("1:0", "2");
		bfMap.put("2:0", "3");
		bfMap.put("2:1", "4");
		bfMap.put("3:0", "5");
		bfMap.put("3:1", "6");
		bfMap.put("3:2", "7");
		bfMap.put("4:0", "8");
		bfMap.put("4:1", "9");
		bfMap.put("4:2", "10");
		bfMap.put("平其他", "11");
		bfMap.put("0:0", "12");
		bfMap.put("1:1", "13");
		bfMap.put("2:2", "14");
		bfMap.put("3:3", "15");
		bfMap.put("负其他", "16");
		bfMap.put("0:1", "17");
		bfMap.put("0:2", "18");
		bfMap.put("1:2", "19");
		bfMap.put("0:3", "20");
		bfMap.put("1:3", "21");
		bfMap.put("2:3", "22");
		bfMap.put("0:4", "23");
		bfMap.put("1:4", "24");
		bfMap.put("2:4", "25");
		
		bcspfMap.put("胜-胜","1");
		bcspfMap.put("胜-平","2");
		bcspfMap.put("胜-负","3");
		bcspfMap.put("平-胜","4");
		bcspfMap.put("平-平","5");
		bcspfMap.put("平-负","6");
		bcspfMap.put("负-胜","7");
		bcspfMap.put("负-平","8");
		bcspfMap.put("负-负","9");
		
		zjqMap.put("7+","8");
		zjqMap.put("0","1");
		zjqMap.put("1","2");
		zjqMap.put("2","3");
		zjqMap.put("3","4");
		zjqMap.put("4","5");
		zjqMap.put("5","6");
		zjqMap.put("6","7");
		
		sfMap.put("胜","3");
		sfMap.put("负","0");
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Collection<MatchSpDomain> getDcSp(Map<String, String> requestMap,
			GaodeConfig config) {
		List<MatchSpDomain> retList = new ArrayList<MatchSpDomain>();
		// "胜平负"  "上下单双"  "总进球"  "比分"  "半全场" "胜负"
		String[] lotteryids = {"w","o","g","s","b","d"};
		try{
			JSONArray ja = getDcPhase(requestMap, config);
			String phaseNo = "";
			if(ja==null||ja.isEmpty()){
				return null;
			}
			JSONObject object=ja.getJSONObject(0);
			phaseNo=object.getString("phaseNo");	
			
			for(String lotteryid : lotteryids) {
				String returnStr = getResult(config.getDcUrl()+lotteryid+"_"+phaseNo.substring(1)+".xml",CharsetConstant.CHARSET_GB2312);
				if(returnStr.isEmpty()){
					logger.warn("高德查询单场赛果返回为null");
					return null;
				}
				Document doc  = DocumentHelper.parseText(returnStr);
				Element rootElt = doc.getRootElement(); 
				Iterator<Object> iterator=rootElt.elementIterator();
				int i=1;
				Map<String, Map<String, Object>> lottery_type =null;
				while(iterator.hasNext()){
					    Element element=(Element)iterator.next();
						String played = rootElt.attributeValue("li");
						String status = element.attributeValue("st");//售票状态 
						//2：未开售 1销售中 -1  已停售-2  已开奖3  已计算4  已返奖
//						if("4".equals(status)||"-2".equals(status)||"-1".equals(status)){
//						         continue;
//						}
						MatchSpDomain sp = new MatchSpDomain();
						sp.setMatchNum(phaseNo+toLotteryMap.get(lotteryid)+i);
						lottery_type = new HashMap<String, Map<String, Object>>();
						if("w".equals(lotteryid)){//胜平负
							String sp_s = element.attributeValue("c1");
							String sp_p = element.attributeValue("c3");
							String sp_f = element.attributeValue("c5");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_SFP_S_VALUE,sp_s);
							cons.put(LotteryConstant.DC_SFP_P_VALUE,sp_p);
							cons.put(LotteryConstant.DC_SFP_F_VALUE,sp_f);
							lottery_type.put(LotteryType.DC_SPF.value+"", cons);
						}else if("o".equals(lotteryid)){//上下单双
							String sp_s = element.attributeValue("c1");
							String sp_x = element.attributeValue("c3");
							String sp_d = element.attributeValue("c5");
							String sp_sh = element.attributeValue("c7");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_SXDS_SD_VALUE,sp_s);
							cons.put(LotteryConstant.DC_SXDS_SS_VALUE,sp_x);
							cons.put(LotteryConstant.DC_SXDS_XD_VALUE,sp_d);
							cons.put(LotteryConstant.DC_SXDS_XS_VALUE,sp_sh);
							lottery_type.put(LotteryType.DC_SXDS.value+"", cons);
						}else if("g".equals(lotteryid)){//进球彩
							String sp_0 = element.attributeValue("c1");
							String sp_1 = element.attributeValue("c3");
							String sp_2 = element.attributeValue("c5");
							String sp_3 = element.attributeValue("c7");
							String sp_4 = element.attributeValue("c9");
							String sp_5 = element.attributeValue("c11");
							String sp_6 = element.attributeValue("c13");
							String sp_7 = element.attributeValue("c15");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_JQX_0_VALUE,sp_0);
							cons.put(LotteryConstant.DC_JQX_1_VALUE,sp_1);
							cons.put(LotteryConstant.DC_JQX_2_VALUE,sp_2);
							cons.put(LotteryConstant.DC_JQX_3_VALUE,sp_3);
							cons.put(LotteryConstant.DC_JQX_4_VALUE,sp_4);
							cons.put(LotteryConstant.DC_JQX_5_VALUE,sp_5);
							cons.put(LotteryConstant.DC_JQX_6_VALUE,sp_6);
							cons.put(LotteryConstant.DC_JQX_7_VALUE,sp_7);
							lottery_type.put(LotteryType.DC_ZJQ.value+"", cons);
						}else if("s".equals(lotteryid)){//比分
							String b1 =  element.attributeValue("c1");//胜其他 (53.87)
							String b2 =  element.attributeValue("c2");
							String b3 =  element.attributeValue("c3");
							String b4 =  element.attributeValue("c4");
							String b5 =  element.attributeValue("c5");
							String b6 =  element.attributeValue("c6");
							String b7 =  element.attributeValue("c7");
							String b8 =  element.attributeValue("c8");
							String b9 =  element.attributeValue("c9");
							String b10 =  element.attributeValue("c10");
							String b11 =  element.attributeValue("c11");//平其他	(221.45)
							String b12 =  element.attributeValue("c12");
							String b13 =  element.attributeValue("c13");
							String b14 =  element.attributeValue("c14");
							String b15 =  element.attributeValue("c15");
							String b16 =  element.attributeValue("c16");//负其他 (130.58) 
							String b17 =  element.attributeValue("c17");
							String b18 =  element.attributeValue("c18");
							String b19 =  element.attributeValue("c19");
							String b20 =  element.attributeValue("c20");
							String b21 =  element.attributeValue("c21");
							String b22 =  element.attributeValue("c22");
							String b23 =  element.attributeValue("c23");
							String b24 =  element.attributeValue("c24");
							String b25 =  element.attributeValue("c25");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_BF_S_Ohter_VALUE, b1);
							cons.put(LotteryConstant.DC_BF_S_10_VALUE,b2);
							cons.put(LotteryConstant.DC_BF_S_20_VALUE, b3);
							cons.put(LotteryConstant.DC_BF_S_21_VALUE, b4);
							cons.put(LotteryConstant.DC_BF_S_30_VALUE, b5);
							cons.put(LotteryConstant.DC_BF_S_31_VALUE, b6);
							cons.put(LotteryConstant.DC_BF_S_32_VALUE, b7);
							cons.put(LotteryConstant.DC_BF_S_40_VALUE, b8);
							cons.put(LotteryConstant.DC_BF_S_41_VALUE, b9);
							cons.put(LotteryConstant.DC_BF_S_42_VALUE, b10);
							
							cons.put(LotteryConstant.DC_BF_P_Other_VALUE, b11);
							cons.put(LotteryConstant.DC_BF_P_0_VALUE, b12);
							cons.put(LotteryConstant.DC_BF_P_1_VALUE, b13);
							cons.put(LotteryConstant.DC_BF_P_2_VALUE, b14);
							cons.put(LotteryConstant.DC_BF_P_3_VALUE, b15);
							
							cons.put(LotteryConstant.DC_BF_F_Other_VALUE,b16);
							cons.put(LotteryConstant.DC_BF_F_01_VALUE, b17);
							cons.put(LotteryConstant.DC_BF_F_02_VALUE, b18);
							cons.put(LotteryConstant.DC_BF_F_12_VALUE, b19);
							cons.put(LotteryConstant.DC_BF_F_03_VALUE, b20);
							cons.put(LotteryConstant.DC_BF_F_13_VALUE, b21);
							cons.put(LotteryConstant.DC_BF_F_23_VALUE, b22);
							cons.put(LotteryConstant.DC_BF_F_04_VALUE, b23);
							cons.put(LotteryConstant.DC_BF_F_14_VALUE, b24);
							cons.put(LotteryConstant.DC_BF_F_24_VALUE, b25);
							lottery_type.put(LotteryType.DC_BF.value+"", cons);
						}else if("b".equals(lotteryid)){//半全场
							String b1 =  element.attributeValue("c1");
							String b2 =  element.attributeValue("c3");
							String b3 =  element.attributeValue("c5");
							String b4 =  element.attributeValue("c7");
							String b5 =  element.attributeValue("c9");
							String b6 =  element.attributeValue("c11");
							String b7 =  element.attributeValue("c13");
							String b8 =  element.attributeValue("c15");
							String b9 =  element.attributeValue("c17");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_BCSFP_SS_VALUE, b1);
							cons.put(LotteryConstant.DC_BCSFP_SP_VALUE, b2);
							cons.put(LotteryConstant.DC_BCSFP_SF_VALUE, b3);
							cons.put(LotteryConstant.DC_BCSFP_PS_VALUE, b4);
							cons.put(LotteryConstant.DC_BCSFP_PP_VALUE, b5);
							cons.put(LotteryConstant.DC_BCSFP_PF_VALUE, b6);
							cons.put(LotteryConstant.DC_BCSFP_FS_VALUE, b7);
							cons.put(LotteryConstant.DC_BCSFP_FP_VALUE, b8);
							cons.put(LotteryConstant.DC_BCSFP_FF_VALUE, b9);
							lottery_type.put(LotteryType.DC_BQC.value+"", cons);
						}else if("d".equals(lotteryid)){//胜负
							String b1 =  element.attributeValue("c1");
							String b2 =  element.attributeValue("c2");
							Map<String, Object> cons= new HashMap<String, Object>();
							cons.put(LotteryConstant.DC_SFGG_S_VALUE, b1);
							cons.put(LotteryConstant.DC_SFGG_F_VALUE, b2);
							lottery_type.put(LotteryType.DC_SFGG.value+"", cons);
						}
						sp.setLotteryType(lottery_type);
						retList.add(sp);
						i++;
				     }
				}
		} catch (Exception e) {
			logger.error("高德查询北单sp出错"+e);
		}
		return retList;
	}
	
	

	/**
	 * 玩法转换
	 */
	public static Map<Integer,String> lotteryMap=new HashMap<Integer, String>();
	public static Map<String,Integer> toLotteryMap=new HashMap<String,Integer>();
	static{
		lotteryMap.put(LotteryType.DC_SFGG.getValue(),"d");
		lotteryMap.put(LotteryType.DC_BF.getValue(),"s");
		lotteryMap.put(LotteryType.DC_SXDS.getValue(),"o");
		lotteryMap.put(LotteryType.DC_BQC.getValue(),"b"); 
		lotteryMap.put(LotteryType.DC_ZJQ.getValue(),"g"); 
		lotteryMap.put(LotteryType.DC_SPF.getValue(),"w");
		
		toLotteryMap.put("d",LotteryType.DC_SFGG.getValue());
		toLotteryMap.put("s",LotteryType.DC_BF.getValue());
		toLotteryMap.put("o",LotteryType.DC_SXDS.getValue());
		toLotteryMap.put("b",LotteryType.DC_BQC.getValue()); 
		toLotteryMap.put("g",LotteryType.DC_ZJQ.getValue()); 
		toLotteryMap.put("w",LotteryType.DC_SPF.getValue());
	}
	
	

}
