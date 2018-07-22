package com.lottery.ticket.vender.impl.letou;

import java.util.HashMap;
import java.util.Map;

import com.lottery.ticket.vender.impl.BaseConfig;

public class LeTouConfig extends BaseConfig {
	
	public static String addHead(String command,String agentCode,String messageId,String key,String bodyStr,String timeStamp) {

		
		Map<String,Object> mapStr=new HashMap<String, Object>();
		mapStr.put("cmd", command);
//		mapStr.put("digest",MD5Util.toMd5(key+bodyStr+timeStamp));
		mapStr.put("digestType", "md5");
		mapStr.put("timeStamp", timeStamp);
		mapStr.put("userId", "md5");
		mapStr.put("userType", "terminal");
		return null;
	}
	
	

}
