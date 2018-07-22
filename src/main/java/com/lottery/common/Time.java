/**
 * 
 */
package com.lottery.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 存储时间
 * @author fengqinyun
 *
 */
public class Time {
	
	private static final Logger logger = LoggerFactory.getLogger(Time.class.getName());

	private int hour;
	private int minute;
	private int second;
	private int millisecond;
	
	@Override
	public String toString() {
		return "Time [hour=" + hour + ", minute=" + minute + ", second=" + second + ", millisecond=" + millisecond
				+ "]";
	}

	public static Time parse(String s) {
		if (s == null) {
			return null;
		}
		Time time = new Time();

		{
			// 解析毫秒
			String[] tmp = StringUtils.split(s, ".");
			if (tmp.length > 2) {
				logger.error("不支持的毫秒格式, {}", s);
				return null;
			}
			if (tmp.length == 2) {
				try {
					time.setMillisecond(Integer.parseInt(tmp[1]));
				} catch (NumberFormatException e) {
					logger.error("不支持的毫秒数据, {}", tmp[1]);
					return null;
				}
			}
			// 没有毫秒数据则不做处理
			s = tmp[0];
		}

		{
			// 解析时分秒
			String[] tmp = StringUtils.split(s, ":");
			if (tmp.length > 3 || tmp.length < 2) {
				logger.error("不支持的时分秒格式, {}", s);
				return null;
			}
			if (tmp.length == 2 && time.getMillisecond() > 0) {
				// 如果已解析到毫秒，则必须包含时分秒
				logger.error("未解析到秒数据, {}", s);
				return null;
			}
			if (tmp.length == 3) {
				try {
					time.setSecond(Integer.parseInt(tmp[2]));
				} catch (NumberFormatException e) {
					logger.error("不支持的秒数据, {}", tmp[2]);
					return null;
				}
			}
			try {
				time.setHour(Integer.parseInt(tmp[0]));
			} catch (NumberFormatException e) {
				logger.error("不支持的小时数据, {}", tmp[0]);
				return null;
			}
			try {
				time.setMinute(Integer.parseInt(tmp[1]));
			} catch (NumberFormatException e) {
				logger.error("不支持的分数据, {}", tmp[1]);
				return null;
			}
			
		}
		return time;
	}
	
	
	
	
	
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getMillisecond() {
		return millisecond;
	}
	public void setMillisecond(int millisecond) {
		this.millisecond = millisecond;
	}
	
}
