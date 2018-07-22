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


public class GetJclqMatchFromJingcaidata implements IGetDataFrom {
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	static String[] week = new String[]{"周0", "周一", "周二", "周三", "周四", "周五", "周六", "周日"};
	static int[] salestate = new int[]{RaceSaleStatus.SALE_UNOPEN.value, RaceSaleStatus.SALE_OPEN.value};
	
	public JSONArray perfrom(Map<String, String> reqMap, IVenderConfig config) {
		try {
			String resultStr = HTTPUtil.sendPostMsg(LotteryConstant.CURRENT_RACE_URL, "type=0");
			JSONObject resultjo = JSONObject.fromObject(resultStr);
			if("0".equals(resultjo.getString("errorCode"))){
				JSONArray retja = new JSONArray();
				JSONArray jar = resultjo.getJSONArray("value");
				for (int i = 0; i < jar.size(); i++) {
					JSONObject match = jar.getJSONObject(i);
					
					Long endSaleTime = match.getLong("endSaleTime");//1426867140000
					Long matchDate = match.getLong("matchDate");//1426868100000
					String matchNum = match.getString("matchNum");//20150320_5_301
					String handicap = match.getString("handicap");//-10.5
					String presetScore = match.getString("presetScore");//131.5
					String matchName = match.getString("matchName");//美国大学篮球联盟
					String homeTeam = match.getString("homeTeam");//新墨西哥州立大学
					String awayTeam = match.getString("awayTeam");//堪萨斯大学
					String officialId= match.getString("officialId");//官方id
					String matchNameForShort = match.getString("matchNameForShort");
					String homeTeamForShort = match.getString("homeTeamForShort");
					String awayTeamForShort = match.getString("awayTeamForShort");
				    	  
					Integer sfDgStatus = match.getInt("sfDgStatus");//0
					Integer sfCgStatus = match.getInt("sfCgStatus");//1
					Integer rfsfDgStatus = match.getInt("rfsfDgStatus");//1
					Integer rfsfCgStatus = match.getInt("rfsfCgStatus");//1
					Integer sfcDgStatus = match.getInt("sfcDgStatus");//1
					Integer sfcCgStatus = match.getInt("sfcCgStatus");//1
					Integer dxfDgStatus = match.getInt("dxfDgStatus");//0
					Integer dxfCgStatus = match.getInt("dxfCgStatus");//1
					/*
					Long createTime = match.getLong("createTime");//1426812300000
					Long updateTime = match.getLong("updateTime");//1426812300000
					String matchNameForShort = match.getString("matchNameForShort");//NCAA
					String homeTeamForShort = match.getString("homeTeamForShort");//新墨州大
					String awayTeamForShort = match.getString("awayTeamForShort");//堪萨斯大
					String matchColor = match.getString("matchColor");//#2E74CC
					String prizeStatus = match.getString("prizeStatus");//null
					String firstQuarter = match.getString("firstQuarter");//null
					String secondQuarter = match.getString("secondQuarter");//null
					String thirdQuarter = match.getString("thirdQuarter");//null
					String fourthQuarter = match.getString("fourthQuarter");//null
					String finalScore = match.getString("finalScore");//null
					String status = match.getString("status");//1
					*/
					String[] matchs = matchNum.split("_");
					String phaseNo = matchs[0];
					Integer weekDay = Integer.parseInt(matchs[1]);
					String num = matchs[2];
					String matchDateStr = CoreDateUtils.formatDateTime(new Date(matchDate));
					String endSaleTimeStr = CoreDateUtils.formatDateTime(new Date(endSaleTime));
					
					JSONObject jo = new JSONObject();
					jo.put("matchNum", phaseNo + num);//20150320310  比赛编码,年月日+官方赛事编码 20110314301
					jo.put("phaseNo", phaseNo);//20150320 彩期号
					jo.put("officialNum", num); //310 官方赛事编码,301
					jo.put("officialWeekDay", week[weekDay]);//周五 官方赛事星期数，for view
					jo.put("endSaleTime", endSaleTimeStr);//2015-03-21 04:10:00 停止销售时间
					jo.put("matchDate", matchDateStr);// 比赛日期    
					jo.put("matchName", matchName);//美国大学篮球联盟  赛事
					jo.put("homeTeam", homeTeam);//路易维尔大学 主队
					jo.put("awayTeam", awayTeam);//加大欧文分校  客队
					jo.put("officialId",officialId);//官方id
					jo.put("matchNameForShort", matchNameForShort);
					jo.put("homeTeamForShort", homeTeamForShort);
					jo.put("awayTeamForShort", awayTeamForShort);
					
					jo.put("operation", RaceStatus.OPEN.value);//2
					jo.put("staticHandicap", handicap.trim()); //-8.50  固定奖金投注当前让分
					jo.put("staticPresetScore", presetScore.trim());//+125.50  固定奖金投注当前预设总分
					jo.put("staticSaleSfStatus", salestate[sfCgStatus]);//2 固定奖金胜负玩法销售状态
					jo.put("staticSaleRfsfStatus", salestate[rfsfCgStatus]);//2 固定奖金让分胜负玩法销售状态
					jo.put("staticSaleSfcStatus", salestate[sfcCgStatus]); //2固定奖金胜分差玩法销售状态
					jo.put("staticSaleDxfStatus", salestate[dxfCgStatus]);//2固定奖金大小分玩法销售状态
					
					jo.put("dgStaticSaleSfStatus", salestate[sfDgStatus]);//1单关固定奖金胜负玩法销售状态
					jo.put("dgStaticSaleRfsfStatus", salestate[rfsfDgStatus]);//1单关固定奖金让分胜负玩法销售状态
					jo.put("dgStaticSaleSfcStatus", salestate[sfcDgStatus]);//2浮动奖金胜分差玩法销售状态
					jo.put("dgStaticSaleDxfStatus", salestate[dxfDgStatus]);//1单关固定固定奖金大小分玩法销售状态
				
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
		JSONArray jar = new GetJclqMatchFromJingcaidata().perfrom(null, null);
		for (int i = 0; i < jar.size(); i++) {
			System.out.println(jar.getJSONObject(i));
		}
		System.out.println(jar.size());
	}
}
