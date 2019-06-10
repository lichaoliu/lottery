package com.lottery.ticket.vender;

import java.util.Map;

import net.sf.json.JSONArray;

import com.lottery.ticket.IVenderConfig;



public interface IVenderInternalJclqService {

	/**
	 * 获取竞彩篮球赛程
	 */
	public JSONArray getJclqSchedule(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩篮球开奖结果
	 */
	public JSONArray getJclqDrawResult(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩篮球单关浮动奖金实时sp值
	 */
	public JSONArray getJclqDynamicSp(IVenderConfig config, Map<String, String> requestMap);

	/**
	 * 获取竞彩篮球过关固定奖金实时sp值和让分和预设总分
	 */
	public JSONArray getJclqStaticSp(IVenderConfig config, Map<String, String> requestMap);

}
