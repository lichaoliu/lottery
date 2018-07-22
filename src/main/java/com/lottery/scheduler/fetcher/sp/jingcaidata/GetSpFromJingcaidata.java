package com.lottery.scheduler.fetcher.sp.jingcaidata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.lottery.common.contains.LotteryConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.util.HTTPUtil;
import com.lottery.scheduler.fetcher.sp.IGetSpData;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.ticket.IVenderConfig;

public class GetSpFromJingcaidata implements IGetSpData {
	private static Logger logger = LoggerFactory.getLogger(GetSpFromJingcaidata.class);
	
	@Override
	public List<MatchSpDomain> getJczqStatic(IVenderConfig config) {
		try {
			String retStr = HTTPUtil.sendPostMsg(LotteryConstant.JC_SP_URL, "type=1");
			JSONObject rejo = JSONObject.fromObject(retStr);
			if(!"0".equals(rejo.getString("errorCode"))){
				return null;
			}
			JSONArray jar = rejo.getJSONArray("value");
			List<MatchSpDomain> retList = new ArrayList<MatchSpDomain>();
			for (int i = 0; i < jar.size(); i++) {
				JSONObject jo = jar.getJSONObject(i);
				String matchNum = jo.getString("matchNum");
				JSONObject matchSps = jo.getJSONObject("matchSps");
				MatchSpDomain jczqStaticSp = new MatchSpDomain();
				String[] matchNums = matchNum.split("_");
				jczqStaticSp.setMatchNum(matchNums[0] + matchNums[2]);
				Map<String, Map<String, Object>> map = json2map(matchSps);
				jczqStaticSp.setLotteryType(map);
				retList.add(jczqStaticSp);
			}
			return retList;
		} catch (Exception e) {
			logger.error("jingcaidata获取竞彩足球 sp出错", e);
		}
		return null;
	}
	
	
	@Override
	public List<MatchSpDomain> getJclqStatic(IVenderConfig config) {
		try {
			String retStr = HTTPUtil.sendPostMsg(LotteryConstant.JC_SP_URL, "type=0");
			JSONObject rejo = JSONObject.fromObject(retStr);
			if(!"0".equals(rejo.getString("errorCode"))){
				return null;
			}
			JSONArray jar = rejo.getJSONArray("value");
			List<MatchSpDomain> retList = new ArrayList<MatchSpDomain>();
			for (int i = 0; i < jar.size(); i++) {
				JSONObject jo = jar.getJSONObject(i);
				String matchNum = jo.getString("matchNum");
				JSONObject matchSps = jo.getJSONObject("matchSps");
				MatchSpDomain jczqStaticSp = new MatchSpDomain();
				String[] matchNums = matchNum.split("_");
				jczqStaticSp.setMatchNum(matchNums[0] + matchNums[2]);
				Map<String, Map<String, Object>> map = json2map(matchSps);
				jczqStaticSp.setLotteryType(map);
				retList.add(jczqStaticSp);
			}
			return retList;
		} catch (Exception e) {
			logger.error("jingcaidata获取竞彩篮球 sp出错", e);
		}
		return null;
	}

	private Map<String, Map<String, Object>> json2map(JSONObject jo){
		Set<Entry<String, Object>> entrys = jo.entrySet();
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for (Entry<String, Object> entry : entrys) {
			JSONObject playtype = JSONObject.fromObject(entry.getValue());
			Set<Entry<String, Object>> playentrys = playtype.entrySet();
			Map<String, Object> playmap = new HashMap<String, Object>();
			for (Entry<String, Object> playentry : playentrys) {
				playmap.put(playentry.getKey(), playentry.getValue());
			}
			map.put(entry.getKey(), playmap);
		}
		return map;
	}
}
