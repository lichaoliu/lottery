package com.lottery.scheduler.fetcher;

import java.util.Map;

import net.sf.json.JSONArray;

import com.lottery.ticket.IVenderConfig;

public interface IGetDataFrom {
	
	public JSONArray perfrom(Map<String,String> reqMap,IVenderConfig config);
}
