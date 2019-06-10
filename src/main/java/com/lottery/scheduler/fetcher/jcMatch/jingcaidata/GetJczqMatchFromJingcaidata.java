package com.lottery.scheduler.fetcher.jcMatch.jingcaidata;

import java.util.Date;
import java.util.Map;

import com.lottery.common.contains.LotteryConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.lottery.RaceSaleStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.scheduler.fetcher.IGetDataFrom;
import com.lottery.ticket.IVenderConfig;


public class GetJczqMatchFromJingcaidata implements IGetDataFrom {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	static String[] week = new String[]{"周0", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
	static int[] salestate = new int[]{RaceSaleStatus.SALE_UNOPEN.value, RaceSaleStatus.SALE_OPEN.value};
	
	public JSONArray perfrom(Map<String, String> reqMap, IVenderConfig config) {
		try {
			String resultStr = HTTPUtil.sendPostMsg(LotteryConstant.CURRENT_RACE_URL, "type=1");
			JSONObject resultjo = JSONObject.fromObject(resultStr);
			if("0".equals(resultjo.getString("errorCode"))){
				JSONArray retja = new JSONArray();
				JSONArray jar = resultjo.getJSONArray("value");
				for (int i = 0; i < jar.size(); i++) {
					JSONObject match = jar.getJSONObject(i);
					Long matchDate = match.getLong("matchDate");//1427040000000
					Long endSaleTime = match.getLong("endSaleTime");//1427039940000
					String matchNum = match.getString("matchNum");//20150322_7_032
					String matchName = match.getString("matchName");//英格兰超级联赛
					String handicap = match.getString("handicap");//+1
					String homeTeam = match.getString("homeTeam");//赫尔城
					String awayTeam = match.getString("awayTeam");//切尔西
					String officialId= match.getString("officialId");//官方id
				    String homeTeamForShort = match.getString("homeTeamForShort");
				    String awayTeamForShort = match.getString("awayTeamForShort");
					String matchNameForShort = match.getString("matchNameForShort");
				      
					Integer spfDgStatus = match.getInt("spfDgStatus");//0
					Integer spfCgStatus = match.getInt("spfCgStatus");//1
					Integer spfwrqDgStatus = match.getInt("spfwrqDgStatus");//1
					Integer spfwrqCgStatus = match.getInt("spfwrqCgStatus");//1
					Integer bfDgStatus = match.getInt("bfDgStatus");//1
					Integer bfCgStatus = match.getInt("bfCgStatus");//1
					Integer jqsDgStatus = match.getInt("jqsDgStatus");//1
					Integer jqsCgStatus = match.getInt("jqsCgStatus");//1
					Integer bqcDgStatus = match.getInt("bqcDgStatus");//1
					Integer bqcCgStatus = match.getInt("bqcCgStatus");//1
					/*
					Long createTime = match.getLong("createTime");//1426778281000
					Long updateTime = match.getLong("updateTime");//1426778281000
					String matchNameForShort = match.getString("matchNameForShort");//英超
					String homeTeamForShort = match.getString("homeTeamForShort");//赫尔城
					String awayTeamForShort = match.getString("awayTeamForShort");//切尔西
					String matchColor = match.getString("matchColor");//FF3333
					String prizeStatus = match.getString("prizeStatus");//null
					String finalScore = match.getString("finalScore");//null
					String firstHalf = match.getString("firstHalf");//null
					String secondHalf = match.getString("secondHalf");//null
					Integer status = match.getInt("status");//1
					*/
					String matchDateStr = CoreDateUtils.formatDateTime(new Date(matchDate));
					String endSaleTimeStr = CoreDateUtils.formatDateTime(new Date(endSaleTime));
					String[] matchs = matchNum.split("_");
					String phaseNo = matchs[0];
					int weekDay = Integer.parseInt(matchs[1]);
					String num = matchs[2];
					JSONObject jo = new JSONObject();
					jo.put("matchNum", phaseNo + num);//20150320001
					jo.put("matchDate", matchDateStr);// 比赛日期  yyyy-MM-dd HH:mm:ss
					jo.put("endSaleTime", endSaleTimeStr);//2015-03-20 16:39:00  停止销售时间 yyyy-MM-dd HH:mm:ss
					jo.put("matchName", matchName);//澳大利亚超级联赛
					jo.put("phaseNo", phaseNo);//20150320  彩期号,比赛当天的日期
					jo.put("officialNum", num); //001 官方赛事编码,001
					jo.put("officialWeekDay", week[weekDay]);//周五  官方赛事星期数，for view
					jo.put("homeTeam", homeTeam);// 悉尼FC  
					jo.put("awayTeam", awayTeam);//墨尔本城 
					jo.put("handicap", handicap.trim());// -1
					jo.put("operation", RaceStatus.OPEN.value);//2
					jo.put("officialId",officialId);
					jo.put("homeTeamForShort", homeTeamForShort);
					jo.put("awayTeamForShort", awayTeamForShort);
					jo.put("matchNameForShort", matchNameForShort);
					
					jo.put("staticSaleSpfStatus", salestate[spfCgStatus]);//2
					jo.put("staticSaleSpfWrqStatus", salestate[spfwrqCgStatus]);//2
					jo.put("staticSaleBfStatus", salestate[bfCgStatus]);//2
					jo.put("staticSaleJqsStatus", salestate[jqsCgStatus]);//2
					jo.put("staticSaleBqcStatus", salestate[bqcCgStatus]);//2
					
					jo.put("dgStaticSaleSpfStatus", salestate[spfDgStatus]);//1
					jo.put("dgStaticSaleBfStatus", salestate[bfDgStatus]); //2
					jo.put("dgStaticSaleJqsStatus", salestate[jqsDgStatus]);//2
					jo.put("dgStaticSaleBqcStatus", salestate[bqcDgStatus]);//2
					jo.put("dgStaticSaleSpfWrqStatus", salestate[spfwrqDgStatus]);//2
					retja.add(jo);
				}
				return retja;
			}
		} catch (Exception e) {
			logger.error("获取 jingcaidata的竞彩足球 数据出错", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		JSONArray jar = new GetJczqMatchFromJingcaidata().perfrom(null, null);
		for (int i = 0; i < jar.size(); i++) {
			System.out.println(jar.getJSONObject(i));
		}
		System.out.println(jar.size());
	}
}
