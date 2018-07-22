package com.lottery.common.util;
/**
 * 
 */


import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author fengqinyun
 *
 */
public abstract class CoreJSONUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(CoreJSONUtils.class.getName());
	
	public static final String NULL_DATE = "0000-00-00 00:00:00";

	private static void log(JSONObject object, String key, Exception e) {
		try {
			logger.error("从JSON对象中读取属性出错, object={}, key={}", object, key);
			logger.error(e.getMessage(), e);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), e);
		}
	}
	
	public static JSONObject getObject(JSONObject object, String key) {
		try {
			return object.getJSONObject(key);
		} catch (Exception e) {
			log(object, key, e);
			return null;
		}
	}
	
	public static JSONArray getArray(JSONObject object, String key) {
		try {
			return object.getJSONArray(key);
		} catch (Exception e) {
			log(object, key, e);
			return null;
		}
	}
	
	public static String getString(JSONObject object, String key) {
		try {
			Object valueObject = object.get(key);
			if (isNull(valueObject)) {
				return null;
			}
			return valueObject.toString();
		} catch (Exception e) {
			log(object, key, e);
			return null;
		}
	}
	
	public static int getInt(JSONObject object, String key) {
		try {
			return object.getInt(key);
		} catch (Exception e) {
			log(object, key, e);
			return -1;
		}
	}

	public static long getLong(JSONObject object, String key) {
		try {
			//从json对象里用jar包提供的getLong取得upload_id,由于精度问题,转换出现误差,获取不到正确结果,修改成获取字符串再转成long
			return Long.parseLong(object.getString(key));
		} catch (Exception e) {
			log(object, key, e);
			return 0;
		}
	}
	
	public static double getDouble(JSONObject object, String key) {
		try {
			return object.getDouble(key);
		} catch (Exception e) {
			log(object, key, e);
			return 0d;
		}
	}
	
	public static boolean getBoolean(JSONObject object, String key) {
		try {
			return object.getBoolean(key);
		} catch (Exception e) {
			log(object, key, e);
			return false;
		}
	}
	
	public static Date getDate(JSONObject object, String key, String pattern) {
		String val = getString(object, key);
		if (val == null || "".equals(val) || val.equals(NULL_DATE)) {
			return null;
		}
		try {
			Date date = CoreDateUtils.parseDate(val, pattern);
			return date;
		} catch (Exception e) {
			log(object, key, e);
			return null;
		}
	}
	
	public static boolean isEmpty(JSONObject object, String key) {
		try {
			return !object.containsKey(key);
		} catch (Exception e) {
			log(object, key, e);
			return true;
		}
	}

	private static boolean isNull(Object object) {
		return object == null || object instanceof JSONNull || JSONUtils.isNull(object);
	}
	
	public static boolean isNull(JSONObject object, String key) {
		try {
			return object == null || isNull(object.get(key));
		} catch (Exception e) {
			log(object, key, e);
			return true;
		}
	}
}
