package com.lottery.common.mail;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lottery.common.contains.MailType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.JsonUtil;

public class MailToolImpl extends AbstractMailTool {

	@Override
	public void sendEmail(String msg) {
		send(MailType.email, msg);

	}

	@Override
	public void sendPhoneMsg(String msg) {
		send(MailType.phone, msg);

	}

    @Override
    public String sendPhoneMsg(String msg, String phone) {

        if(forSend){
            return "";
        }

        ObjectNode node = JsonUtil.createObjectNode();
        node.put("type", 2);
        node.put("msg", msg);
        node.put("clientType", "android");

        ArrayNode array = JsonUtil.createArrayNode();
        array.add(phone);

        node.put("numList", array);
        String response = "";
        try {
            response = HTTPUtil.post(this.getUrl(),node.toString());
        } catch (Exception e) {
            logger.info("请求smscenter出错 ",e);
        }
        return response;
    }
}
