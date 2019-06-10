package com.lottery.b2b.message.handler;

import com.lottery.b2b.message.IExternalMessage;
import com.lottery.b2b.message.impl.response.ExternalResponseMessage;

public interface IExternalMessageHandler {

	public IExternalMessage getMessage(String requestString)throws Exception;
	public String getResult(ExternalResponseMessage message);
}
