package com.lottery.core.domain.terminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.cache.IMemcachedObject;
import com.lottery.common.contains.lottery.TerminalType;
@Entity
@Table(name="terminal")
public class Terminal implements Serializable , IMemcachedObject{


	private static final long serialVersionUID = 4942631931958711288L;
    @Id
    @Column(name="id")
	private Long id;	//终端编号
	@Column(name="name",nullable=false)
	private String name;	//终端名
	@Column(name="is_enabled",nullable=false)
	private Integer isEnabled;	//是否可用
    @Column(name="is_paused",nullable=false)
	private Integer isPaused;// 是否暂停送票
    @Column(name="terminal_type",nullable=false)
	private Integer terminalType;	//终端类型
	@Column(name="allotforbidperiod")
	private String allotForbidPeriod;	// 禁止分票时段
	@Column(name="sendforbidperiod")
	private String sendForbidPeriod;	// 禁止送票时段
	@Column(name="checkForbidPeriod")
	 private String checkForbidPeriod;	// 禁止检票时段
	 @Column(name="is_check_enabled")
	private Integer isCheckEnabled;	//是否可用异步检票
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Integer getIsPaused() {
		return isPaused;
	}
	public void setIsPaused(Integer isPaused) {
		this.isPaused = isPaused;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public String getAllotForbidPeriod() {
		return allotForbidPeriod;
	}
	public void setAllotForbidPeriod(String allotForbidPeriod) {
		this.allotForbidPeriod = allotForbidPeriod;
	}
	public String getSendForbidPeriod() {
		return sendForbidPeriod;
	}
	public void setSendForbidPeriod(String sendForbidPeriod) {
		this.sendForbidPeriod = sendForbidPeriod;
	}
	public String getCheckForbidPeriod() {
		return checkForbidPeriod;
	}
	public void setCheckForbidPeriod(String checkForbidPeriod) {
		this.checkForbidPeriod = checkForbidPeriod;
	}
	
	

	public Integer getIsCheckEnabled() {
		return isCheckEnabled;
	}
	public void setIsCheckEnabled(Integer isCheckEnabled) {
		this.isCheckEnabled = isCheckEnabled;
	}
	public String toString(){
		StringBuffer sb=new  StringBuffer();
		sb.append("[终端id:"+id);
		sb.append(",终端名字:"+name);
		sb.append(",终端类型:"+TerminalType.get(terminalType));
		sb.append(",是否可用:"+isEnabled);
		sb.append(",是否暂停送票:"+isPaused);
		return sb.toString();
	}
	
	
}
