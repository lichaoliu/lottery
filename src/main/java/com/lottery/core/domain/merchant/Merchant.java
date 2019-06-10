package com.lottery.core.domain.merchant;

/**
 * Created by steven on 14-7-10.
 */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 出票商表
 * */
@Table(name="merchant")
@Entity
 public class Merchant implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="merchant_code")
    @Id
    private String merchantCode;//代理商编号
    @Column(name="secret_key")
    private String secretKey;//代理商秘钥
    @Column(name="ip")
    private String ip;//代理商ip
    @Column(name="credit_balance")
    private BigDecimal creditBalance;//信用额度(数据为分)
    @Column(name="merchant_name")
    private String merchantName;//代理商名字
    @Column(name="status")
    private Integer status;//状态，是否可用
    @Column(name="notice_url")
    private String noticeUrl;//通知地址
    @Column(name="real_name")
	private String realName;//用户真实姓名
    @Column(name="phoneno")
	 private String  phoneno;// 手机号码
	 @Column(name="idcard")
	 private String idcard;// 身份证
	@Column(name="is_notice")
	private Integer isNotice;//是否需要通知(YesNoStatus)
    @Column(name="is_ip")
	private Integer isIp;//是否需要IP验证
    @Column(name = "terminal_id")
	private Long terminalId=0l;//指定出票终端id

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}


	public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}

	public Integer getIsIp() {
		return isIp;
	}

	public void setIsIp(Integer isIp) {
		this.isIp = isIp;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
}
