package com.lottery.common.mail;

public interface MailTool {
	/**
	 * 发送邮件
	 * */
	void sendEmail(String msg);
	void sendPhoneMsg(String msg);
	String sendPhoneMsg(String msg,String phone);

}
