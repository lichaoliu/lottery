package com.lottery.core.domain.terminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.cache.IMemcachedObject;
@Entity
@Table(name="terminal_property")
public class TerminalProperty implements Serializable , IMemcachedObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3481991940609418773L;
    @Id
    @Column(name="id")
	private Long id;	//终端编号
	@Column(name="terminalid",nullable=false)
    private Long terminalId;	//终端编号
	@Column(name="terminale_key",nullable=false)
	private String terminalKey;
	@Column(name="terminal_value",nullable=false)
	private String terminalValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public String getTerminalKey() {
		return terminalKey;
	}
	public void setTerminalKey(String terminalKey) {
		this.terminalKey = terminalKey;
	}
	public String getTerminalValue() {
		return terminalValue;
	}
	public void setTerminalValue(String terminalValue) {
		this.terminalValue = terminalValue;
	}
}
