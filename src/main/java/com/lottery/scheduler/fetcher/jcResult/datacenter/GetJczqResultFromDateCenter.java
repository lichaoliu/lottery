package com.lottery.scheduler.fetcher.jcResult.datacenter;

import com.lottery.common.contains.LotteryConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.scheduler.fetcher.jcResult.AbstractGetJczqResultFrom;

@Component(value="jczqResultFromDc")
public class GetJczqResultFromDateCenter extends AbstractGetJczqResultFrom{
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public JSONArray getArray(String phaseNo, String sn){
		String resultStr = null; 
		try {
			String param = "style=0&phase="+phaseNo;
			if(StringUtil.isNotEmpt(sn)){
				param += "&match_num=" + phaseNo + sn;
			}
			resultStr = HTTPUtil.sendPostMsg(LotteryConstant.JCZQ_RACE_RESULT, param);
			//logger.error("从数据中心获取的竞彩足球赛果,发送的参数是:{},返回内容是:{}",param,resultStr);
			JSONObject jo = JSONObject.fromObject(resultStr);
			if(StringUtil.isEmpty(resultStr)){
				return null;
			}
			String errorCode = jo.getString("code");
			if(!"0".equals(errorCode)){
				return null;
			}
			JSONArray messages = jo.getJSONArray("message");
			JSONArray reja = new JSONArray();
			for (int i = 0; i < messages.size(); i++) {
				JSONObject message = messages.getJSONObject(i);
				Integer state = message.getInt("state");
				String matchn = message.getString("match_num");
				if(state == 0){
					
				}else if(state == 1){
					String firstHalf  = message.getString("half").trim();
					String finalScore = message.getString("final").trim();
					String[] half = firstHalf.split(":");
				    String[] finals = finalScore.split(":");
				    String secondHalf = (Integer.parseInt(finals[0])-Integer.parseInt(half[0])) +  ":" + (Integer.parseInt(finals[1])-Integer.parseInt(half[1]));
				    
				    JSONObject rejo = new JSONObject();
				    rejo.put("matchNum", matchn);
		    		rejo.put("firstHalf", firstHalf);
		    		rejo.put("secondHalf", secondHalf);
		    		rejo.put("finalScore", finalScore);
		    		rejo.put("resultFrom", "数据中心");
		    		rejo.put("state", RaceStatus.CLOSE.value);
		    		reja.add(rejo);
				}else if(state == 2){
					JSONObject rejo = new JSONObject();
				    rejo.put("matchNum", matchn);
		    		rejo.put("resultFrom", "数据中心");
		    		rejo.put("state", RaceStatus.CANCEL.value);
		    		reja.add(rejo);
				}
			}
			return reja;
		} catch (Exception e) {
			logger.error("datacenter获取jczq赛果出错,resultStr={}", resultStr, e);
		}
		return null;
	}
	

}
