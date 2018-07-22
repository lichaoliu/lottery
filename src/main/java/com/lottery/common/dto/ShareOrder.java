package com.lottery.common.dto;

import java.util.List;

/**
 * 晒单
 * */
public class ShareOrder {
	
	private String orderId;//跟单id
	private Double commission;//提成多少(不能大于1,0.35表示35%)
	private List<DocumentaryOrder> documentaryList;//所有跟单
    private String userno;
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public List<DocumentaryOrder> getDocumentaryList() {
		return documentaryList;
	}

	public void setDocumentaryList(List<DocumentaryOrder> documentaryList) {
		this.documentaryList = documentaryList;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	
	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	

}
