package com.lottery.ticket;

public interface IVenderConfig {
	/**
	 * 请求的地址
	 * */
	public String getRequestUrl();
	/**
	 * 请求的端口号
	 * */
	public Integer getPort();
	/**
	 * 请求的秘钥，md5,des
	 * */
	public String getKey();
	/**
	 * 公钥
	 * */
	public String getPublicKey();
	/**
	 * 私钥
	 * */
	public String getPrivateKey();
	/***
	 * 出票商编号
	 * */
	public String getAgentCode();
	
	/***
	 * 出票商密码
	 * */
	
	public String getPasswd();
	/**
	 * 每次最大送票数
	 * */
	public Integer getSendCount();
	/**
	 * 每次最大检票数
	 * */
	
	public Integer getCheckCount();
	/**
	 * 送票多久后可以查询票状态，单位:秒
	 * */
	public Integer getTimeOutSecondForCheck();
	/**
	 * 送票超时时间
	 */
	public Integer getTimeOutSecondForSend();
	/**
	 * 查票地址
	 */
	public String getCheckUrl();
	/**
	 * 该终端id
	 * */
	public Long getTerminalId();

    /**
	 * 是否进行同步检票
	 * */
	public Boolean getSyncTicketCheck();



}
