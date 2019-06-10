package com.lottery.pay;

import java.math.BigDecimal;

/**
 * 充值配置相关
 * */
public interface IPayConfig {

	/**
	 * 请求的地址
	 * */
	public String getRequestUrl();
	/***
	 * 通知地址
	 * */
	public String getNoticeUrl();
	/***
	 * 查询地址
	 * */
	public String getSearchUrl();
	/**
	 * 公钥
	 * */
	public String getPublicKey();
	/**
	 * 私钥
	 * */
	public String getPrivateKey();
	/**
	 * 签约账号
	 */
	public String getSeller();
	/**
	 * 合作商户ID
	 */
	public String getPartner();

	/**
	 * 密码
	 * */
	public String getPasswd();
	/**
	 * 充值费率
	 * */
	public BigDecimal getFee();
	/**
	 * 交易安全检验码
	 * @return
	 */
	public String getKey();
	/**
	 * 返回地址
	 * @return
	 */


	public String getReturnUrl();
	/**
	 * account_name参数
	 * 账户名
	 * */
	public String getAccountName();
	/**subject
	 * 商品名称
	 * */
	public String getSubject();
	/**
	 * description
	 * 商品描述
	 * */
	public String getDescription();
    /**
	 * 加密证书位置
	 * **/
	public String getEncryptCerPath();
	/**
	 *公钥证书位置
	 * */
	public String getPublicCerPath();
	/**
	 * 私钥证书位置
	 * */
	public String getPrivateCerPath();
	/**
	 * 商品链接地址
	 * 网站网址之类的
	 * */
	public String getShowUrl();

    /**
     * 服务IP
     */
    public String getIp();
	/**
	 * 是否暂停
	 * 0否1是
	 * */
	public String getIsPaused();



}