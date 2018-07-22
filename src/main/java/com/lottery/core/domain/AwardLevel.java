package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_award_level")
public class AwardLevel implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AwardLevelPK id;

	// 奖级名称
	@Column(name = "level_name", nullable = false)
	private String levelname;

	// 奖级奖金
	@Column(name = "prize", nullable = false)
	private long prize;

	// 加奖奖金
	@Column(name = "extra_prize")
	private long extraprize;


	public AwardLevelPK getId() {
		return id;
	}

	public void setId(AwardLevelPK id) {
		this.id = id;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}

	public long getExtraPrize() {
		return extraprize;
	}

	public void setExtraPrize(long extraPrize) {
		this.extraprize = extraPrize;
	}

	public AwardLevel(){}

}
