package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.util.CoreDateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 14-3-23.
 */
@Entity
@Table(name="dc_race")
public class DcRace implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 480602133044494761L;
	@Id
    @Column(name="id")
    private Long id;               	//流水号
    @Column(name="phase")
    private String phase;             	//彩期号
    
    @Column(name="dc_type")
    private Integer dcType=DcType.normal.value();                 //场次类型，普通北单 or 北单胜负
    @Column(name="create_time")
    private Date createTime;        		//创建时间
    @Column(name="update_time")
    private Date updateTime;        		//修改时间
    @Column(name="match_num")
    private Integer matchNum;         		//场次
    @Column(name="end_sale_time")
    private Date endSaleTime;        	//停止销售时间
    @Column(name="match_date")
    private Date matchDate;        		//比赛日期
    @Column(name="home_team")
    private String homeTeam;        	//主队
    @Column(name="away_team")
    private String awayTeam;        	//客队
    @Column(name="handicap")
    private String handicap;        	//让球
    @Column(name = "whole_score")
    private String wholeScore;        	//全场比分
    @Column(name="half_score")
    private String halfScore;           //半场比分
    @Column(name="match_name")
    private String matchName;           //赛事
    @Column(name="status")
    private Integer status;		//状态
    @Column(name="prize_status")
	private  Integer prizeStatus;  //开奖状态   获取到结果   开奖  派奖 
    @Column(name="ext")
    private String ext;               	//扩展信息
    @Column(name="sp_sfp")
    private String spSfp;				//胜负平sp值
    @Column(name="sp_sxds")
    private String spSxds;				//上下单双sp值
    @Column(name="sp_jqs")
    private String spJqs;				//进球数sp值
    @Column(name="sp_bf")
    private String spBf;				//单场比分sp值
    @Column(name="sp_bcsfp")
    private String spBcsfp;				//半场胜负平sp值
    
    @Column(name="sp_sf")
    private String spSf;                //胜负sp值
   
   	@Column(name="sfp_result")
   	private String sfpResult;          //胜负平赛果
   	
   	@Column(name="sxds_result")
   	private String sxdsResult;        //上下单双赛果
   	
   	@Column(name="jqs_result")
   	private String jqsResult;          //总进球赛果
   	
   	@Column(name="bf_result")
   	private String bfResult;         //比分赛果
   	
   	@Column(name="sf_result")
   	private String sfResult;         //胜负赛果
   	
   	@Column(name="bcsfp_result")
   	private String bcsfpResult;        //半场胜平负赛果
   	
    @Column(name="prize_time")
    private Date prizeTime;        	    //开奖时间
    @Column(name="priority")
    private Integer priority;				//优先级
    @Column(name="catch_id")
    private Integer catchId;				//数据抓取id，目前的值等同fx_id
    @Column(name="fx_id")
    private Integer fxId;					//分析id(soccer的vid)
	@Column(name="result_from")
	private String resultFrom;//结果来源
	@Column(name="game_type")
	private String gameType;                   //竞彩胜负过关里的赛事类型，如足球篮球乒乓球
    private transient String spStr;//返回sp值
    
    
    
 
    public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    

	public Integer getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(Integer matchNum) {
		this.matchNum = matchNum;
	}

	public Date getEndSaleTime() {
        return endSaleTime;
    }

    public void setEndSaleTime(Date endSaleTime) {
        this.endSaleTime = endSaleTime;
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

    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }

    public String getWholeScore() {
        return wholeScore;
    }

    public void setWholeScore(String wholeScore) {
        this.wholeScore = wholeScore;
    }

    public String getHalfScore() {
        return halfScore;
    }

    public void setHalfScore(String halfScore) {
        this.halfScore = halfScore;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSpSfp() {
        return spSfp;
    }

    public void setSpSfp(String spSfp) {
        this.spSfp = spSfp;
    }

    public String getSpSxds() {
        return spSxds;
    }

    public void setSpSxds(String spSxds) {
        this.spSxds = spSxds;
    }

    public String getSpJqs() {
        return spJqs;
    }

    public void setSpJqs(String spJqs) {
        this.spJqs = spJqs;
    }

    public String getSpBf() {
        return spBf;
    }

    public void setSpBf(String spBf) {
        this.spBf = spBf;
    }

    public String getSpBcsfp() {
        return spBcsfp;
    }

    public void setSpBcsfp(String spBcsfp) {
        this.spBcsfp = spBcsfp;
    }

    public Date getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(Date prizeTime) {
        this.prizeTime = prizeTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getCatchId() {
        return catchId;
    }

    public void setCatchId(Integer catchId) {
        this.catchId = catchId;
    }

    public Integer getFxId() {
        return fxId;
    }

    public void setFxId(Integer fxId) {
        this.fxId = fxId;
    }

	public String getSfpResult() {
		return sfpResult;
	}

	public void setSfpResult(String sfpResult) {
		this.sfpResult = sfpResult;
	}

	public String getSxdsResult() {
		return sxdsResult;
	}

	public void setSxdsResult(String sxdsResult) {
		this.sxdsResult = sxdsResult;
	}

	public String getJqsResult() {
		return jqsResult;
	}

	public void setJqsResult(String jqsResult) {
		this.jqsResult = jqsResult;
	}

	public String getBfResult() {
		return bfResult;
	}

	public void setBfResult(String bfResult) {
		this.bfResult = bfResult;
	}

	public String getBcsfpResult() {
		return bcsfpResult;
	}

	public void setBcsfpResult(String bcsfpResult) {
		this.bcsfpResult = bcsfpResult;
	}

	public Integer getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(Integer prizeStatus) {
		this.prizeStatus = prizeStatus;
	}



	public Integer getDcType() {
		return dcType;
	}

	public void setDcType(Integer dcType) {
		this.dcType = dcType;
	}

	public String getSpSf() {
		return spSf;
	}

	public void setSpSf(String spSf) {
		this.spSf = spSf;
	}

	public String getSfResult() {
		return sfResult;
	}

	public void setSfResult(String sfResult) {
		this.sfResult = sfResult;
	}

	public String getSpStr() {
		return spStr;
	}

	public void setSpStr(String spStr) {
		this.spStr = spStr;
	}
    
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[编号："+matchNum);
		sb.append("(主队)").append(this.getHomeTeam());
		sb.append(" vs ");
		sb.append("(客队)").append(this.getAwayTeam());
		sb.append(" at ");
		sb.append(CoreDateUtils.formatDateTime(getMatchDate()));
		sb.append(",status:").append(status);
		sb.append(",endTime:");
		sb.append(CoreDateUtils.formatDateTime(this.endSaleTime));
		sb.append("]");
		return sb.toString();
	}

	public String getResultFrom() {
		return resultFrom;
	}

	public void setResultFrom(String resultFrom) {
		this.resultFrom = resultFrom;
	}
	
	
    
}
