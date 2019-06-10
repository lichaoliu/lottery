package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name="lottery_draw_amount")
public class LotteryDrawAmount implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1357505952335279432L;
/**
 * 用户提现操作
 * */
	@Id
	@Column(name="id")
	private String id;
	@Column(name="userno")
	private String userno;
	@Column(name="draw_type")
	private Integer drawType;//提现类别
	@Column(name="bank_type")
	private String bankType;//银行类型 (招商、工商)
	@Column(name="bank_name")
	private String bankName;//银行名称

	@Column(name="bank_id")
	private String bankId;//银行卡号/支付宝账号
	@Column(name="bank_address")
	private String bankAddress;//银行开户行支行名称
	@Column(name="draw_amount")
	private BigDecimal drawAmount;//提现金额
	@Column(name="fee")
	private BigDecimal fee;//手续费
	@Column(name="real_amount")
	private BigDecimal realAmount;//实际金额
	@Column(name="create_time")
	private Date createTime;//申请提现时间
	@Column(name="status")
	private Integer status;//状态  
	@Column(name="finish_time")
	private Date finishTime;//完成时间
	@Column(name="description")
	private String description;//描述
	@Column(name="user_name")
	private String userName;//开户人姓名
	@Column(name="batch_id")
	private String batchId;//批次号/上传文件名字
	@Column(name="province")
	private String province;//省
	@Column(name="city")
	private String city;//市
	@Column(name="operate_type")
	private Integer operateType;//提现操作类型  (DrawOperateType)
    @Column(name="submit_time")
	private Date submitTime;//提交时间
	@Column(name="draw_bank_id")
	private String drawBankId;//绑定账号id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getDrawType() {
		return drawType;
	}
	public void setDrawType(Integer drawType) {
		this.drawType = drawType;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public BigDecimal getDrawAmount() {
		return drawAmount;
	}
	public void setDrawAmount(BigDecimal drawAmount) {
		this.drawAmount = drawAmount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getDrawBankId() {
		return drawBankId;
	}

	public void setDrawBankId(String drawBankId) {
		this.drawBankId = drawBankId;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[userno:"+userno);
		sb.append(",username:"+userName);
		sb.append(",bankid:"+bankId);
		sb.append(",bankType:"+bankType);
		sb.append(",bankAddress:"+bankAddress);
		sb.append(",drawAmount:"+drawAmount);
		sb.append(",fee:"+fee);
		sb.append(",batchId:"+batchId);
		sb.append("]");
		return  sb.toString();
	}

	
}
