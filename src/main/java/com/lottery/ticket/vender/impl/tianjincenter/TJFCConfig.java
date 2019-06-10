package com.lottery.ticket.vender.impl.tianjincenter;

import com.lottery.ticket.vender.AbstractVenderConfig;

public class TJFCConfig extends AbstractVenderConfig{

	private int servicePort;//综合信息服务端口
	private String ftpIp;//ftp ip
	private int ftpPort;//ftp端口
	private String ftpUser;//ftp用户名
	private String ftpPass;//ftp密码
	
	private String ftpDir;//ftp目录
	private String noticeKey;//通知key
	
	public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPass() {
		return ftpPass;
	}
	public void setFtpPass(String ftpPass) {
		this.ftpPass = ftpPass;
	}
	public String getNoticeKey() {
		return noticeKey;
	}
	public void setNoticeKey(String noticeKey) {
		this.noticeKey = noticeKey;
	}
	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public String getFtpDir() {
		return ftpDir;
	}
	public void setFtpDir(String ftpDir) {
		this.ftpDir = ftpDir;
	}
	public int getServicePort() {
		return servicePort;
	}
	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}
	
	
	
}
