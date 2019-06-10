package com.lottery.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 14-3-23.
 */
@Entity
@Table(name="zc_race")
public class ZcRace implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7084026195637075134L;
	@Id
    @Column(name="id")
    private Long id;               	    //流水号
    @Column(name="lottery_type")
    private Integer lotteryType;
    @Column(name="phase")
    private String phase;             //彩期号
    @Column(name="create_time")
    private Date createTime;        	//创建时间
    @Column(name="match_num")
    private Integer matchNum;         		//场次
    @Column(name="match_id")
    private Integer matchId;     		//对阵id(与联赛库对应,暂不使用)
   @Column(name="match_date")
    private Date matchDate;        		//比赛日期
  @Column(name = "home_team")
    private String homeTeam;        	//主队
   @Column(name="away_team")
    private String awayTeam;        	//客队
    @Column(name="match_name")
    private String matchName;           //赛事
    @Column(name="final_score")
    private String finalScore;        	//全场比分
    @Column(name="half_score")
    private String halfScore;  			//半场比分
   @Column(name="average_index")
    private String averageIndex;     	//平均指数，格式:胜_平_负
   @Column(name="ext")
    private String ext;               	//扩展信息
   @Column(name="is_possibledelay")
    private String isPossibledelay;     //是否可能延期

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(Integer matchNum) {
        this.matchNum = matchNum;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    public String getAverageIndex() {
        return averageIndex;
    }

    public void setAverageIndex(String averageIndex) {
        this.averageIndex = averageIndex;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getIsPossibledelay() {
        return isPossibledelay;
    }

    public void setIsPossibledelay(String isPossibledelay) {
        this.isPossibledelay = isPossibledelay;
    }
}
