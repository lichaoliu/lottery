package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="lottery_addprize_strategy")
public class LotteryAddPrizeStrategy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LotteryAddPrizeStrategyPK id;
	
	@Column(name="status",nullable=false)
	private Integer status;
	
	//不能为负数和0
	@Column(name="add_amt",nullable=false)
	private Integer addamt;

	public LotteryAddPrizeStrategyPK getId() {
		return id;
	}

	public void setId(LotteryAddPrizeStrategyPK id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAddamt() {
		return addamt;
	}

	public void setAddamt(Integer addamt) {
		this.addamt = addamt;
	}
	
	
	
	
}
