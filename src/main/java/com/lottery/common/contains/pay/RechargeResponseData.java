package com.lottery.common.contains.pay;

/**
 * Created by fengqinyun on 14-3-19.
 * 充值组装返回数据
 */
public class RechargeResponseData {
	private String userno;//用户编号
    private String requestUrl;//充值请求地址
    private String result;//请求参数
	private String returnUrl;
	private String key;//
	private String sign;
	private String seller;
	private String partner;
	private String noticeUrl;//通知地址
	private String orderNo;//充值订单号 
	private String cardType;//卡类型
	private String isPaused;//是否暂停(0否,1是)
    public String getRequestUrl() {
        return requestUrl;
    }
    
    public String getReturnUrl() {
		return returnUrl;
	}
    
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
   

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;

}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(String isPaused) {
		this.isPaused = isPaused;
	}
}
