package com.lottery.ticket.vender;

import java.util.Map;

import net.sf.json.JSONArray;

import com.lottery.ticket.IVenderConfig;



public interface IVenderInternalJczqService {

	/**
	 * 获取竞彩足球赛程
	 */
	public JSONArray getJczqSchedule(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩足球开奖结果
	 */
	public JSONArray getJczqDrawResult(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩足球单关浮动奖金实时sp值
	 */
	public JSONArray getJczqDynamicSp(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩足球过关固定奖金实时sp值和让分和预设总分
	 */
	public JSONArray getJczqStaticSp(IVenderConfig config, Map<String, String> requestMap);

}
