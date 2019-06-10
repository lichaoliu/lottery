package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="jc_guanyajun_race")
public class JcGuanYaJunRace implements Serializable{

	private static final long serialVersionUID = 9028527269226966110L;
	
	@EmbeddedId
	private GuanyajunRacePK id;
	
	@Column(name="content",length=20)
	private String content;//投注内容  冠军为某个球队 冠亚军为两个球队
	
	@Column(name="status",nullable=false)
	private Integer status;//比赛状态 RaceStatus
	
	@Column(name="prize_status",nullable=false)
	private Integer prizeStatus;//开奖状态PrizeStatus
	
	@Column(name="odd",length=10)
	private String odd;//当前赔率
	
	@Column(name="probability",length=10)
	private String probability;
	
	@Column(name="supportrate",length=10)
	private String supportRate;
	
	@Column(name="iswin",nullable=false)
	private Integer isWin;//场次是否中奖


	
	public GuanyajunRacePK getId() {
		return id;
	}

	public void setId(GuanyajunRacePK id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(Integer prizeStatus) {
		this.prizeStatus = prizeStatus;
	}

	public String getOdd() {
		return odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public Integer getIsWin() {
		return isWin;
	}

	public void setIsWin(Integer isWin) {
		this.isWin = isWin;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getSupportRate() {
		return supportRate;
	}

	public void setSupportRate(String supportRate) {
		this.supportRate = supportRate;
	}
	
	
	
	

}
