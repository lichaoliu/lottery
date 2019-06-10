package com.lottery.common.mail;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.MailType;
import com.lottery.common.util.HTTPUtil;

public abstract class AbstractMailTool implements MailTool {
    
	protected final Logger logger=LoggerFactory.getLogger(getClass());

	private String url;
	public boolean forSend=false;//是否发送邮件
	protected void send(MailType type,String msg){
		try{
			if(forSend){
				return;
			}
			String str=HTTPUtil.post(url, "type="+type.getValue()+"&message="+msg ,CharsetConstant.CHARSET_UTF8);
			if(StringUtils.isBlank(str)){
				logger.error("发送msg类型{}失败",type.getName());
			}
		}catch(Exception e){
			logger.error("发送类型为{}的信息出错",type.getName(),e);
		}
	}

	public boolean isForSend() {
		return forSend;
	}

	public void setForSend(boolean forSend) {
		this.forSend = forSend;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
