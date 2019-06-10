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
import com.lottery.scheduler.fetcher.jcResult.AbstractGetJclqResultFrom;

@Component(value="jclqResultFromDc")
public class GetJclqResultFromDateCenter extends AbstractGetJclqResultFrom{
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	public JSONArray getArray(String phaseNo, String sn){
		String resultStr = null;
		try {
			String param = "style=0&phase="+phaseNo;
			if(StringUtil.isNotEmpt(sn)){
				param += "&match_num=" + phaseNo + sn;
			}
			resultStr = HTTPUtil.sendPostMsg(LotteryConstant.JCLQ_RACE_RESULT, param);
			//logger.error("从数据中心获取的获取竞彩篮球赛果,发送的参数是:{},返回内容是:{}",param,resultStr);
			if(StringUtil.isEmpty(resultStr)){
				return null;
			}  
			JSONObject jo = JSONObject.fromObject(resultStr);
			String errorCode = jo.getString("code");
			if(!"0".equals(errorCode)){
				return null;
			}
			JSONArray messages = jo.getJSONArray("message");
			JSONArray reja = new JSONArray();
			for (int i = 0; i < messages.size(); i++) {
				JSONObject message = messages.getJSONObject(i);
				String matchn = message.getString("match_num");
				Integer state = message.getInt("state");
				if(state == 0){
					
				}else if(state == 2){
					JSONObject rejo = new JSONObject();
		    		rejo.put("resultFrom", "数据中心");
		    		rejo.put("matchNum", matchn);
		    		rejo.put("state", RaceStatus.CANCEL.value);
		    		reja.add(rejo);
				}else{
					String fss = message.getString("final_score").trim();
		    		String[] fs = fss.split(":");
		    		String finalScore = fs[1] + ":" + fs[0];
		    		
		    		JSONObject rejo = new JSONObject();
		    		rejo.put("finalScore", finalScore);
		    		rejo.put("resultFrom", "数据中心");
		    		rejo.put("matchNum", matchn);
		    		rejo.put("state", RaceStatus.CLOSE.value);
		    		reja.add(rejo);
				}
			}
			return reja;
		} catch (Exception e) {
			logger.error("datacenter获取jclq赛果出错resultStr={}",  resultStr, e);
		}
		return null;
	}
	

}
