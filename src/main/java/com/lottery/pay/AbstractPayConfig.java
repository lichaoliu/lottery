package com.lottery.pay;

import java.math.BigDecimal;

public abstract class AbstractPayConfig implements IPayConfig {

	private String requestUrl;
	private String returnUrl;
	private String noticeUrl;
	private String publicKey;
	private String privateKey;
	private String searchUrl;
	private String seller;
	private String partner;
	private String passwd;
	private String key;
	private String description;
	private String subject;
	private String accountName;
	private String encryptCerPath;//证书位置
	private String privateCerPath;
	private String publicCerPath;
	private String showUrl;
    private String ip;

	private String isPaused="0";//是否暂停 YesNoStatus

	private BigDecimal fee=BigDecimal.ZERO;
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getNoticeUrl() {
		return noticeUrl;
	}
	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEncryptCerPath() {
		return encryptCerPath;
	}

	public void setEncryptCerPath(String encryptCerPath) {
		this.encryptCerPath = encryptCerPath;
	}

	@Override
	public String getPrivateCerPath() {
		return privateCerPath;
	}

	public void setPrivateCerPath(String privateCerPath) {
		this.privateCerPath = privateCerPath;
	}

	@Override
	public String getPublicCerPath() {
		return publicCerPath;
	}

	public void setPublicCerPath(String publicCerPath) {
		this.publicCerPath = publicCerPath;
	}

	@Override
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
	public String getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(String isPaused) {
		this.isPaused = isPaused;
	}
}
