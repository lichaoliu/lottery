package com.lottery.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="user_draw_bank")
public class UserDrawBank implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6939369852821315096L;
	@Id
	@Column(name="id")
	private String id;//id
	@Column(name="userno")
	private String userno;//用户编号
	@Column(name="real_name")
	private String realname;//用户真实姓名
	@Column(name="bank_type")
	private String bankType;//银行类型，
	@Column(name="bank_name")
	private String bankName;//银行名称
	@Column(name="bank_card")
	private String bankCard;//银行卡号
	@Column(name="province")
	private String province;//开户省
	@Column(name="city")
	private String city;//开户城市
	@Column(name="branch")
	private String branch;//网点
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="update_time")
	private Date updateTime;//修改时间
	@Column(name="draw_type")
	private Integer drawType;//提现 类型(DrawType)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
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
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDrawType() {
		return drawType;
	}
	public void setDrawType(Integer drawType) {
		this.drawType = drawType;
	}
	
	
	

}
