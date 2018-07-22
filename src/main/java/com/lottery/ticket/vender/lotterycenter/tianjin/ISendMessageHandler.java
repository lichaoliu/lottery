package com.lottery.ticket.vender.lotterycenter.tianjin;

public interface ISendMessageHandler {
	
	/**
	 * 消息转化成福彩请求格式
	 * @param <T>
	 * @param msg
	 * @return
	 */
	public <T> String handleMessage(T msg);

}
