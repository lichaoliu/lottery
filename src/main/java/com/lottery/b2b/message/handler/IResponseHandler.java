package com.lottery.b2b.message.handler;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;

public interface IResponseHandler {
	
	public String getResponse(ExternalResponseMessage message);

}
