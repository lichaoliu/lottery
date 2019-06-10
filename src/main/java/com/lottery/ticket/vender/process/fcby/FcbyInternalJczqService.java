package com.lottery.ticket.vender.process.fcby;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;

import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.IVenderInternalJczqService;
@Service
public class FcbyInternalJczqService implements IVenderInternalJczqService {
    @Autowired
	private GetJczqScheduleFromFcby getJczqScheduleFromFcby;
    @Autowired
	private GetJczqDrawResultFromFcby getJczqDrawResultFromFcby;
    @Autowired
	private GetJczqDynamicSpFromFcby getJczqDynamicSpFromFcby;
    @Autowired
	private GetJczqStaticSpFromFcby getJczqStaticSpFromFcby;

	@Override
	public JSONArray getJczqDynamicSp(IVenderConfig config, Map<String, String> requestMap) {
		return getJczqDynamicSpFromFcby.perform(requestMap, config);
	}

	@Override
	public JSONArray getJczqSchedule(IVenderConfig config, Map<String, String> requestMap) {
		return getJczqScheduleFromFcby.perform(requestMap, config);
	}

	@Override
	public JSONArray getJczqStaticSp(IVenderConfig config, Map<String, String> requestMap) {
		return getJczqStaticSpFromFcby.perform(requestMap, config);
	}

	@Override
	public JSONArray getJczqDrawResult(IVenderConfig config, Map<String, String> requestMap) {
		return getJczqDrawResultFromFcby.perform(requestMap, config);
	}

	
}
