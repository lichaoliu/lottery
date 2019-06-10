package com.lottery.ticket.vender.process.datacenter;

import java.util.Date;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.DcRace;
import com.lottery.core.service.DcRaceService;
@Service
public class GetMatchResultFromDateCenter {
	protected final Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	protected DcRaceService dcRaceService;

	public void updateCloseAndPending(String phase) throws Exception{
//		List<DcRace> list = dcRaceService.getCloseByPhaseAndPrizeStatus(phase, PrizeStatus.pending.value, DcType.sfgg);
//		for (DcRace dcRace : list) {
//			updateDcSFGGResult(phase, dcRace.getMatchNum());
//		}
		updateDcSFGGResult(phase,null);
	}
	/*
	* 响应：
{"code":"0","message":[{"final_score":"0:1","state":1,"saiguo":"0","spStr":"5.590917","phase":"160401","match_num":"37","lottery_type":"5006"}]}
返回状态  state 0 结果没有采集完成 1 返回成功   2 改期 或 取消
	* */
	public void updateDcSFGGResult(String phase, Integer matchNum) throws Exception{
		String param = "phase="+phase;
		if(matchNum != null){
			param += "&match_num=" + matchNum;
		}
		String resultStr = HTTPUtil.sendPostMsg("http://101.201.197.13/dc/sfgg", param);
		logger.error("从数据中心获取的北单胜负过关赛果,发送的参数是:{},返回内容是:{}",param,resultStr);
		if(StringUtil.isEmpty(resultStr)){
			return;
		}
		JSONObject jo = JSONObject.fromObject(resultStr);
		String errorCode = jo.getString("code");
		if(!"0".equals(errorCode)){
			return;
		}
		JSONArray messages = jo.getJSONArray("message");
		for (int i = 0; i < messages.size(); i++){
			JSONObject message = messages.getJSONObject(i);
			Integer matchn = message.getInt("match_num");
			Integer state = message.getInt("state");
			//取消
			if(state == 2){
				canncelResult(phase, matchn, DcType.sfgg.value());
				continue;
			}else if(state == 0){

			}else if(state == 1){
				String final_score = message.getString("final_score").trim();
				String saiguo=message.getString("saiguo");
				String sp=message.getString("spStr");
				DcRace dr = dcRaceService.getByPhase(phase, matchn, DcType.sfgg.value());
				if(dr == null){
					logger.error("胜负过关,phase={},matchnum={}不存在",phase,matchn);
					continue;
				}
				if(dr.getPrizeStatus().intValue() != PrizeStatus.result_null.value && dr.getPrizeStatus().intValue() != PrizeStatus.result_set.value 
						&& dr.getPrizeStatus().intValue() != PrizeStatus.not_open.value){
					continue;
				}
				if(dr.getStatus().intValue() != RaceStatus.CLOSE.value && dr.getStatus().intValue() != RaceStatus.CANCEL.value){
					continue;
				}
				if(StringUtils.isEmpty(sp)){
					logger.error("场次:{}sp为空,phase={}",matchn,phase);
					continue;
				}
				dr.setPrizeTime(new Date());
				dr.setWholeScore(final_score);
				dr.setSfResult(saiguo);
				dr.setSpSf(sp);
				dr.setPrizeStatus(PrizeStatus.result_set.value);
				dr.setResultFrom("数据中心");
				dcRaceService.update(dr);
			}
		}
	}

//---------------------------------------------------------------------
	
	
	public void updateDcResult(String phase, Integer matchNum) throws Exception{
		String param = "phase="+phase;
		if(matchNum != null){
			param += "&match_num=" + matchNum;
		}
		String resultStr = HTTPUtil.sendPostMsg("http://101.201.197.13/dc/normal", param);
		logger.error("从数据中心获取的北单赛果,发送的参数是:{},返回内容是:{}",param,resultStr);
		if(StringUtil.isEmpty(resultStr)){
			return;
		}
		JSONObject jo = JSONObject.fromObject(resultStr);
		String errorCode = jo.getString("code");
		if(!"0".equals(errorCode)){
			return;
		}
		JSONArray messages = jo.getJSONArray("message");
		for (int i = 0; i < messages.size(); i++) {
			JSONObject message = messages.getJSONObject(i);
			Integer matchn = message.getInt("match_num");
			Integer state = message.getInt("state");
			//取消
			if(state == 2){
				canncelResult(phase, matchn,1);
				continue;
			}else if(state == 0){
				String half_score = message.getString("half_score").trim();
				String final_score = message.getString("final_score").trim();
				if(StringUtil.isNotEmpt(half_score) && StringUtil.isNotEmpt(final_score)){
					updateDcRaceScore(phase, matchn, DcType.normal.value(),half_score, final_score);
				}
			}else if(state == 1){//已开奖
				//"5001_2.426695#5002_5.095152#5003_2.878971#5004_3.187415#5005_32.373184";
				String spStr = message.getString("spStr");
				if(StringUtils.isEmpty(spStr)){
					continue;
				}
				String[] sps = spStr.split("#");
				String spSfp = "", spJqs = "", spBcsfp = "", spSxds = "", spBf = "";
				for (String sp : sps) {
					String[] spresult = sp.split("_");
					if(spresult.length != 2){
						continue;
					}
					if("5001".equals(spresult[0])){
						spSfp =  spresult[1];
					}else if("5002".equals(spresult[0])){
						spJqs = spresult[1];
					}else if("5003".equals(spresult[0])){
						spBcsfp = spresult[1];
					}else if("5004".equals(spresult[0])){
						spSxds = spresult[1];
					}else if("5005".equals(spresult[0])){
						spBf = spresult[1];
					}
				}
				//5001_1#5002_2#5003_3#5003_4#5005_3
				String saiguo = message.getString("saiguo");
				String[] sgs = saiguo.split("#");
				String sfpResult = "",jqsResult = "",bcsfpResult = "",sxdsResult = "",bfResult = "";
				int dcType=DcType.normal.intValue();
				for (String sg : sgs) {
					String[] saigu = sg.split("_");
					if("5001".equals(saigu[0])){
						sfpResult = saigu[1];
					}else if("5002".equals(saigu[0])){
						jqsResult = saigu[1];
					}else if("5003".equals(saigu[0])){
						bcsfpResult = saigu[1];
					}else if("5004".equals(saigu[0])){
						sxdsResult = saigu[1];
					}else if("5005".equals(saigu[0])){
						bfResult = saigu[1];
					}
				}
				String half_score = message.getString("half_score").trim();
				String final_score = message.getString("final_score").trim();
				if(StringUtil.isEmpty(spSfp) || StringUtil.isEmpty(sfpResult) || StringUtil.isEmpty(spSxds) || StringUtil.isEmpty(sxdsResult)
						 || StringUtil.isEmpty(spJqs) || StringUtil.isEmpty(jqsResult) || StringUtil.isEmpty(spBf)
						 	|| StringUtil.isEmpty(bfResult) || StringUtil.isEmpty(spBcsfp) || StringUtil.isEmpty(bcsfpResult)){
					continue;
				}
				
				updateDcRace(phase, matchn, spSfp, sfpResult, spSxds, sxdsResult, spJqs, jqsResult, spBf, bfResult, spBcsfp, bcsfpResult, half_score, final_score,dcType);
			}
		}
	}
	
	
	
	
	private void canncelResult(String phaseNo , Integer matchNum,int dcType){
		try {
			DcRace dr = dcRaceService.getByPhase(phaseNo, matchNum,dcType);
			if(dr == null){
				return;
			}
			if(dr.getPrizeStatus().intValue() != PrizeStatus.result_null.value && dr.getPrizeStatus().intValue() != PrizeStatus.result_set.value){
				return;
			}
			dr.setPrizeTime(new Date());
			dr.setSpSfp("1");
			dr.setSfpResult("*");
			dr.setSpSxds("1");
			dr.setSxdsResult("*");
			dr.setSpJqs("1");
			dr.setJqsResult("*");
			dr.setSpBf("1");
			dr.setBfResult("*");
			dr.setSpBcsfp("1");
			dr.setBcsfpResult("*");
			dr.setPrizeStatus(PrizeStatus.result_set.value); 
			dr.setStatus(RaceStatus.CANCEL.value);
			dr.setResultFrom("数据中心");
			dcRaceService.update(dr);
		} catch (Exception e) {
			logger.error("取消北单赛果出错", e);
		}
	}
	
	private void updateDcRaceScore(String phaseNo, Integer matchNum,int dcType,  String halfScore, String wholeScore){
		try {
			DcRace dr = dcRaceService.getByPhase(phaseNo, matchNum,dcType);
			if(dr == null){
				return;
			}
			dr.setHalfScore(halfScore);
			dr.setWholeScore(wholeScore);
			dcRaceService.update(dr);
			logger.error("更新单场比分phaseNo：{}，matchNum：{}，halfScore：{}，wholeScore：{}", 
					new Object[]{ phaseNo, matchNum, halfScore, wholeScore});
		} catch (Exception e) {
			logger.error("更新单场比分出错", e);
		}
	}
	
	private void updateDcRace(String phaseNo, Integer matchNum, String spSfp, String sfpResult, String spSxds,
			String sxdsResult, String spJqs, String jqsResult, String spBf, String bfResult, String spBcsfp, String bcsfpResult, String halfScore,String wholeScore,int dcType){
		try {
			DcRace dr = dcRaceService.getByPhase(phaseNo, matchNum,dcType);
			if(dr == null){
				return;
			}
			if(dr.getPrizeStatus().intValue() != PrizeStatus.result_null.value && dr.getPrizeStatus().intValue() != PrizeStatus.result_set.value 
					&& dr.getPrizeStatus().intValue() != PrizeStatus.not_open.value){
				return;
			}
			if(dr.getStatus().intValue() != RaceStatus.CLOSE.value && dr.getStatus().intValue() != RaceStatus.CANCEL.value){
				return;
			}
			logger.error("更新前单场赛果phaseNo：{}，matchNum：{}，spSfp：{}，sfpResult：{}，spSxds：{}，sxdsResult：{}，spJqs：{}，jqsResult：{}，spBf：{}，bfResult：{}，spBcsfp：{}，bcsfpResult：{}，halfScore：{}，wholeScore：{}", 
					new Object[]{ dr.getPhase(),  dr.getMatchNum(),  dr.getSpSfp(),  dr.getSfpResult(),  dr.getSpSxds(), dr.getSxdsResult(),  dr.getSpJqs(),
					dr.getJqsResult(),  dr.getSpBf(),  dr.getBfResult(),  dr.getSpBcsfp(),  dr.getBcsfpResult(),  dr.getHalfScore(), dr.getWholeScore()});
			dr.setPrizeTime(new Date());
			dr.setHalfScore(halfScore);
			dr.setWholeScore(wholeScore);
			dr.setSpSfp(spSfp);
			dr.setSfpResult(sfpResult);
			dr.setSpSxds(spSxds);
			dr.setSxdsResult(sxdsResult);
			dr.setSpJqs(spJqs);
			dr.setJqsResult(jqsResult);
			dr.setSpBf(spBf);
			dr.setBfResult(bfResult);
			dr.setSpBcsfp(spBcsfp);
			dr.setBcsfpResult(bcsfpResult);
			dr.setPrizeStatus(PrizeStatus.result_set.value);
			dr.setResultFrom("数据中心");
			dcRaceService.update(dr);
			logger.error("保存单场赛果phaseNo：{}，matchNum：{}，spSfp：{}，sfpResult：{}，spSxds：{}，sxdsResult：{}，spJqs：{}，jqsResult：{}，spBf：{}，bfResult：{}，spBcsfp：{}，bcsfpResult：{}，halfScore：{}，wholeScore：{}", 
					new Object[]{ phaseNo, matchNum, spSfp, sfpResult,  spSxds, sxdsResult, spJqs, jqsResult, spBf, bfResult, spBcsfp, bcsfpResult, halfScore, wholeScore});
		} catch (Exception e) {
			logger.error("保存北单赛果出错", e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String result = HTTPUtil.sendPostMsg("http://101.201.197.13/dc/sfgg", "phase="+160403+"&match_num="+5);
		System.out.println(result);
		//String param = "style=0&phase=141202";
		//String resultStr = HTTPUtil.sendPostMsg("http://filter.dajiang365.com/interphp/bjdc/", param);
		//System.out.println(resultStr.getBytes().length);
		//new GetMatchResultFromDateCenter().updateDcResult("141013", 143);
		//new GetMatchResultFromDateCenter().updateJczqResult("20141027", null);
		//new GetMatchResultFromDateCenter().updateJclqResult("20141029", null);
	}
}
