package com.lottery.common.contains.pay;

import java.math.BigDecimal;

/**
 * Created by fengqinyun on 14-3-19.
 *充值请求数据
 */
public class RechargeRequestData {
	private String userno;
    private BigDecimal amount;//充值金额
    private Integer forGive;//是否赠送(yesnostatus)
    private String cardType;//卡类型
    private String batchNo;//批次号
    private String totalCount;//总笔数
    private String fileContent;//文件内容
	private String frontUrl;//前端f返回地址

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Integer getForGive() {
		return forGive;
	}
    
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTotalCount() {
		return totalCount;
	}
    
	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public void setForGive(Integer forGive) {
		this.forGive = forGive;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
}
