package com.lottery.core.domain;

import com.lottery.common.util.CoreDateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "jczq_race")
public class JczqRace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3793775322962072001L;
	public static Map<Integer, String> WEEKSTR = new HashMap<Integer, String>();

	static {
		WEEKSTR.put(1, "周日");
		WEEKSTR.put(2, "周一");
		WEEKSTR.put(3, "周二");
		WEEKSTR.put(4, "周三");
		WEEKSTR.put(5, "周四");
		WEEKSTR.put(6, "周五");
		WEEKSTR.put(7, "周六");
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}

	@Id
	@Column(name = "match_num")
	private String matchNum; // 比赛编码,年月日+官方赛事编码（01为预留位）20110314001
	@Column(name = "phase")
	private String phase; // 彩期号,比赛当天的日期
	@Column(name = "official_date")
	private Date officialDate; // 官方公布比赛场次的日期(官方截止时间)
	@Column(name = "official_num")
	private String officialNum; // 官方赛事编码,001
	@Column(name = "official_weekday")
	private String officialWeekDay; // 官方赛事星期数，for view
	@Column(name = "create_time")
	private Date createTime; // 创建时间
	@Column(name = "update_time")
	private Date updateTime; // 修改时间
	@Column(name = "end_sale_time")
	private Date endSaleTime; // 停止销售时间
	@Column(name = "match_name")
	private String matchName; // 赛事
	@Column(name = "match_short_name")
	private String matchShortName; // 赛事简称
	@Column(name = "match_date")
	private Date matchDate; // 比赛日期
	@Column(name = "home_team")
	private String homeTeam; // 主队
	@Column(name = "home_team_short")
	private String homeTeamShort; // 主队简称
	@Column(name = "away_team")
	private String awayTeam; // 客队
	@Column(name = "away_team_short")
	private String awayTeamShort; // 客队简称
	@Column(name = "handicap")
	private String handicap; // 浮动奖金投注让球
	@Column(name = "first_half")
	private String firstHalf; // 上半场比分 1:1
	@Column(name = "second_half")
	private String secondHalf; // 下半场比分 1:1
	@Column(name = "final_score")
	private String finalScore; // 最终比分 1:1
	@Column(name = "status")
	private Integer status; // 比赛状态 RaceStatus
	@Column(name = "prize_status")
	private Integer prizeStatus; // 开奖状态 获取到结果 开奖 派奖
	@Column(name = "static_draw_status")
	private Integer staticDrawStatus; // 固定奖金开奖状态
	@Column(name = "dynamic_draw_status")
	private Integer dynamicDrawStatus; // 浮动奖金开奖状态

	@Column(name = "static_sale_spf_status")
	private Integer staticSaleSpfStatus; // 固定奖金让球胜平负玩法销售状态
	@Column(name = "dg_static_sale_spf_status")
	private Integer dgStaticSaleSpfStatus; // 单关固定奖金让球胜平负玩法销售状态

	@Column(name = "static_sale_bf_status")
	private Integer staticSaleBfStatus; // 固定奖金全场比分玩法销售状态
	@Column(name = "dg_static_sale_bf_status")
	private Integer dgStaticSaleBfStatus; // 单关固定奖金全场比分玩法销售状态

	@Column(name = "static_sale_jqs_status")
	private Integer staticSaleJqsStatus; // 固定奖金进球总数玩法销售状态
	@Column(name = "dg_static_sale_jqs_status")
	private Integer dgStaticSaleJqsStatus; // 单关固定奖金进球总数玩法销售状态

	@Column(name = "static_sale_bqc_status")
	private Integer staticSaleBqcStatus; // 固定奖金半全场胜平负玩法销售状态
	@Column(name = "dg_static_sale_bqc_status")
	private Integer dgStaticSaleBqcStatus; // 单关固定奖金半全场胜平负玩法销售状态

	@Column(name = "static_sale_spf_wrq_status")
	private Integer staticSaleSpfWrqStatus; // 固定奖金胜平负玩法销售状态
	@Column(name = "dg_static_sale_spf_wrq_status")
	private Integer dgStaticSaleSpfWrqStatus; // 单关固定奖金胜平负玩法销售状态

	@Column(name = "prize_spf")
	private String prizeSpf; // 让球胜平负奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_bf")
	private String prizeBf; // 全场比分奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_jqs")
	private String prizeJqs; // 进球总数奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_bqc")
	private String prizeBqc; // 半全场胜平负奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_spf_wrq")
	private String prizeSpfWrq; // 胜平负奖金值,浮动奖金投注的开奖奖金
	@Column(name = "priority")
	private Integer priority; // 优先级
	@Column(name = "ext")
	private String ext; // 扩展信息
	@Column(name = "result_from")
	private String resultFrom;// 结果来源
	private transient String spStr;
	@Column(name="official_id")
	private String officialId;//官方id

	public JczqRace() {
	}

	public String getMatchNum() {
		return matchNum;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Date getOfficialDate() {
		return officialDate;
	}

	public void setOfficialDate(Date officialDate) {
		this.officialDate = officialDate;
	}

	public String getOfficialNum() {
		return officialNum;
	}

	public void setOfficialNum(String officialNum) {
		this.officialNum = officialNum;
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

	public Date getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
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

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getOfficialWeekDay() {
		/*
		 * officialWeekDay = ""; if (this.getOfficialDate() != null) { Calendar
		 * cd = Calendar.getInstance(); cd.setTime(this.getOfficialDate());
		 * officialWeekDay = WEEKSTR.get(cd.get(Calendar.DAY_OF_WEEK)); }
		 */
		return officialWeekDay;
	}

	public String getFirstHalf() {
		return firstHalf;
	}

	public void setFirstHalf(String firstHalf) {
		this.firstHalf = firstHalf;
	}

	public String getSecondHalf() {
		return secondHalf;
	}

	public void setSecondHalf(String secondHalf) {
		this.secondHalf = secondHalf;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStaticDrawStatus() {
		return staticDrawStatus;
	}

	public void setStaticDrawStatus(Integer staticDrawStatus) {
		this.staticDrawStatus = staticDrawStatus;
	}

	public Integer getDynamicDrawStatus() {
		return dynamicDrawStatus;
	}

	public void setDynamicDrawStatus(Integer dynamicDrawStatus) {
		this.dynamicDrawStatus = dynamicDrawStatus;
	}

	public Integer getStaticSaleSpfStatus() {
		return staticSaleSpfStatus;
	}

	public void setStaticSaleSpfStatus(Integer staticSaleSpfStatus) {
		this.staticSaleSpfStatus = staticSaleSpfStatus;
	}

	public Integer getStaticSaleBfStatus() {
		return staticSaleBfStatus;
	}

	public void setStaticSaleBfStatus(Integer staticSaleBfStatus) {
		this.staticSaleBfStatus = staticSaleBfStatus;
	}

	public Integer getStaticSaleJqsStatus() {
		return staticSaleJqsStatus;
	}

	public void setStaticSaleJqsStatus(Integer staticSaleJqsStatus) {
		this.staticSaleJqsStatus = staticSaleJqsStatus;
	}

	public Integer getStaticSaleBqcStatus() {
		return staticSaleBqcStatus;
	}

	public void setStaticSaleBqcStatus(Integer staticSaleBqcStatus) {
		this.staticSaleBqcStatus = staticSaleBqcStatus;
	}

	public Integer getStaticSaleSpfWrqStatus() {
		return staticSaleSpfWrqStatus;
	}

	public void setStaticSaleSpfWrqStatus(Integer staticSaleSpfWrqStatus) {
		this.staticSaleSpfWrqStatus = staticSaleSpfWrqStatus;
	}

	public String getPrizeSpf() {
		return prizeSpf;
	}

	public void setPrizeSpf(String prizeSpf) {
		this.prizeSpf = prizeSpf;
	}

	public String getPrizeBf() {
		return prizeBf;
	}

	public void setPrizeBf(String prizeBf) {
		this.prizeBf = prizeBf;
	}

	public String getPrizeJqs() {
		return prizeJqs;
	}

	public void setPrizeJqs(String prizeJqs) {
		this.prizeJqs = prizeJqs;
	}

	public String getPrizeBqc() {
		return prizeBqc;
	}

	public void setPrizeBqc(String prizeBqc) {
		this.prizeBqc = prizeBqc;
	}

	public String getPrizeSpfWrq() {
		return prizeSpfWrq;
	}

	public void setPrizeSpfWrq(String prizeSpfWrq) {
		this.prizeSpfWrq = prizeSpfWrq;
	}

	public void setOfficialWeekDay(String officialWeekDay) {
		this.officialWeekDay = officialWeekDay;
	}

	public Integer getPrizeStatus() {
		return prizeStatus;
	}

	public void setPrizeStatus(Integer prizeStatus) {
		this.prizeStatus = prizeStatus;
	}

	public String getSpStr() {
		return spStr;
	}

	public void setSpStr(String spStr) {
		this.spStr = spStr;
	}

	public Integer getDgStaticSaleSpfStatus() {
		return dgStaticSaleSpfStatus;
	}

	public void setDgStaticSaleSpfStatus(Integer dgStaticSaleSpfStatus) {
		this.dgStaticSaleSpfStatus = dgStaticSaleSpfStatus;
	}

	public Integer getDgStaticSaleJqsStatus() {
		return dgStaticSaleJqsStatus;
	}

	public void setDgStaticSaleJqsStatus(Integer dgStaticSaleJqsStatus) {
		this.dgStaticSaleJqsStatus = dgStaticSaleJqsStatus;
	}

	public Integer getDgStaticSaleBqcStatus() {
		return dgStaticSaleBqcStatus;
	}

	public void setDgStaticSaleBqcStatus(Integer dgStaticSaleBqcStatus) {
		this.dgStaticSaleBqcStatus = dgStaticSaleBqcStatus;
	}

	public Integer getDgStaticSaleSpfWrqStatus() {
		return dgStaticSaleSpfWrqStatus;
	}

	public void setDgStaticSaleSpfWrqStatus(Integer dgStaticSaleSpfWrqStatus) {
		this.dgStaticSaleSpfWrqStatus = dgStaticSaleSpfWrqStatus;
	}

	public Integer getDgStaticSaleBfStatus() {
		return dgStaticSaleBfStatus;
	}

	public void setDgStaticSaleBfStatus(Integer dgStaticSaleBfStatus) {
		this.dgStaticSaleBfStatus = dgStaticSaleBfStatus;
	}

	public String getResultFrom() {
		return resultFrom;
	}

	public void setResultFrom(String resultFrom) {
		this.resultFrom = resultFrom;
	}
	
	

	public String getMatchShortName() {
		return matchShortName;
	}

	public void setMatchShortName(String matchShortName) {
		this.matchShortName = matchShortName;
	}

	public String getHomeTeamShort() {
		return homeTeamShort;
	}

	public void setHomeTeamShort(String homeTeamShort) {
		this.homeTeamShort = homeTeamShort;
	}

	public String getAwayTeamShort() {
		return awayTeamShort;
	}

	public void setAwayTeamShort(String awayTeamShort) {
		this.awayTeamShort = awayTeamShort;
	}

	public String getOfficialId() {
		return officialId;
	}

	public void setOfficialId(String officialId) {
		this.officialId = officialId;
	}

	/**
	 * 获取对阵基本信息
	 * 
	 * @return
	 */
	public String getJczqRaceInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(this.getMatchName()).append("]");
		sb.append("(主队)").append(this.getHomeTeam());
		sb.append(" vs ");
		sb.append("(客队)").append(this.getAwayTeam());
		sb.append(" at ");
		sb.append(CoreDateUtils.formatDateTime(getMatchDate()));
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[编号：" + matchNum);
		sb.append("(主队)").append(this.getHomeTeam());
		sb.append(" vs ");
		sb.append("(客队)").append(this.getAwayTeam());
		sb.append(" at: ");
		sb.append(CoreDateUtils.formatDateTime(getMatchDate()));
		sb.append(",status:").append(status);
		sb.append(",结束销售时间:");
		sb.append(CoreDateUtils.formatDateTime(this.endSaleTime));
		sb.append("]");
		return sb.toString();
	}
}
