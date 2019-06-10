package com.lottery.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.util.CoreDateUtils;

/**
 * Created by fengqinyun on 14-3-23.
 */
@Entity
@Table(name = "jclq_race")
public class JclqRace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "match_num")
	private String matchNum; // 比赛编码,年月日+官方赛事编码 20110314301
	@Column(name = "phase")
	private String phase; // 彩期号
	@Column(name = "official_date")
	private Date officialDate; // 官方公布比赛场次的日期
	@Column(name = "official_num")
	private String officialNum; // 官方赛事编码,301
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
	@Column(name = "dynamic_handicap")
	private String dynamicHandicap; // 浮动奖金投注让分
	@Column(name = "static_handicap")
	private String staticHandicap; // 固定奖金投注当前让分
	@Column(name = "dynamic_preset_score")
	private String dynamicPresetScore; // 浮动奖金投注预设总分
	@Column(name = "static_preset_score")
	private String staticPresetScore; // 固定奖金投注当前预设总分
	@Column(name = "first_quarter")
	private String firstQuarter; // 第一节比分
	@Column(name = "second_quarter")
	private String secondQuarter; // 第二节比分
	@Column(name = "third_quarter")
	private String thirdQuarter; // 第三节比分
	@Column(name = "fourth_quarter")
	private String fourthQuarter; // 第四节比分
	@Column(name = "final_score")
	private String finalScore; // 最终比分
	@Column(name = "status")
	private Integer status; // 比赛状态 未开启 开启 关闭 取消
	@Column(name = "prize_status")
	private Integer prizeStatus; // 开奖状态 获取到结果 开奖 派奖
	@Column(name = "static_draw_status")
	private Integer staticDrawStatus; // 固定奖金开奖状态
	@Column(name = "dynamic_draw_status")
	private Integer dynamicDrawStatus; // 浮动奖金开奖状态

	@Column(name = "static_sale_sf_status")
	private Integer staticSaleSfStatus; // 固定奖金胜负玩法销售状态
	@Column(name = "dg_static_sale_sf_status")
	private Integer dgStaticSaleSfStatus; // 单关固定奖金胜负玩法销售状态

	@Column(name = "static_sale_rfsf_status")
	private Integer staticSaleRfsfStatus; // 固定奖金让分胜负玩法销售状态
	@Column(name = "dg_static_sale_rfsf_status")
	private Integer dgStaticSaleRfsfStatus; // 单关固定奖金让分胜负玩法销售状态

	@Column(name = "static_sale_sfc_status")
	private Integer staticSaleSfcStatus; // 固定奖金胜分差玩法销售状态
	@Column(name = "dg_static_sale_sfc_status")
	private Integer dgStaticSaleSfcStatus; // 浮动奖金胜分差玩法销售状态

	@Column(name = "static_sale_dxf_status")
	private Integer staticSaleDxfStatus; // 固定奖金大小分玩法销售状态
	@Column(name = "dg_static_sale_dxf_status")
	private Integer dgStaticSaleDxfStatus; // 单关固定固定奖金大小分玩法销售状态

	@Column(name = "prize_sf")
	private String prizeSf; // 胜负奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_rfsf")
	private String prizeRfsf; // 让分胜负奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_sfc")
	private String prizeSfc; // 胜分差奖金值,浮动奖金投注的开奖奖金
	@Column(name = "prize_dxf")
	private String prizeDxf; // 大小分奖金值,浮动奖金投注的开奖奖金
	@Column(name = "priority")
	private Integer priority; // 优先级
	@Column(name = "ext")
	private String ext; // 扩展信息
	@Column(name = "fx_id")
	private Integer fxId;
	@Column(name = "result_from")
	private String resultFrom;// 结果来源
	private transient String spStr;// sp值
	@Column(name="official_id")
	private String officialId;//官方id
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

	public String getOfficialWeekDay() {
		/*
		 * officialWeekDay = ""; if (this.getOfficialDate() != null) { Calendar
		 * cd = Calendar.getInstance(); cd.setTime(this.getOfficialDate());
		 * officialWeekDay = WEEKSTR.get(cd.get(Calendar.DAY_OF_WEEK)); }
		 */
		return officialWeekDay;
	}

	/**
	 * 获取对阵基本信息
	 * 
	 * @return
	 */
	public String getJclqRaceInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(this.getMatchName()).append("]");
		sb.append("(客队)").append(this.getAwayTeam());
		sb.append(" vs ");
		sb.append("(主队)").append(this.getHomeTeam());
		sb.append(" at ");
		sb.append(CoreDateUtils.formatDateTime(getMatchDate()));
		return sb.toString();
	}

	public JclqRace() {
	}

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
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

	public void setOfficialWeekDay(String officialWeekDay) {
		this.officialWeekDay = officialWeekDay;
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

	public String getDynamicHandicap() {
		return dynamicHandicap;
	}

	public void setDynamicHandicap(String dynamicHandicap) {
		this.dynamicHandicap = dynamicHandicap;
	}

	public String getStaticHandicap() {
		return staticHandicap;
	}

	public void setStaticHandicap(String staticHandicap) {
		this.staticHandicap = staticHandicap;
	}

	public String getDynamicPresetScore() {
		return dynamicPresetScore;
	}

	public void setDynamicPresetScore(String dynamicPresetScore) {
		this.dynamicPresetScore = dynamicPresetScore;
	}

	public String getStaticPresetScore() {
		return staticPresetScore;
	}

	public void setStaticPresetScore(String staticPresetScore) {
		this.staticPresetScore = staticPresetScore;
	}

	public String getFirstQuarter() {
		return firstQuarter;
	}

	public void setFirstQuarter(String firstQuarter) {
		this.firstQuarter = firstQuarter;
	}

	public String getSecondQuarter() {
		return secondQuarter;
	}

	public void setSecondQuarter(String secondQuarter) {
		this.secondQuarter = secondQuarter;
	}

	public String getThirdQuarter() {
		return thirdQuarter;
	}

	public void setThirdQuarter(String thirdQuarter) {
		this.thirdQuarter = thirdQuarter;
	}

	public String getFourthQuarter() {
		return fourthQuarter;
	}

	public void setFourthQuarter(String fourthQuarter) {
		this.fourthQuarter = fourthQuarter;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
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

	public Integer getStaticSaleSfStatus() {
		return staticSaleSfStatus;
	}

	public void setStaticSaleSfStatus(Integer staticSaleSfStatus) {
		this.staticSaleSfStatus = staticSaleSfStatus;
	}

	public Integer getStaticSaleRfsfStatus() {
		return staticSaleRfsfStatus;
	}

	public void setStaticSaleRfsfStatus(Integer staticSaleRfsfStatus) {
		this.staticSaleRfsfStatus = staticSaleRfsfStatus;
	}

	public Integer getStaticSaleSfcStatus() {
		return staticSaleSfcStatus;
	}

	public void setStaticSaleSfcStatus(Integer staticSaleSfcStatus) {
		this.staticSaleSfcStatus = staticSaleSfcStatus;
	}

	public Integer getStaticSaleDxfStatus() {
		return staticSaleDxfStatus;
	}

	public void setStaticSaleDxfStatus(Integer staticSaleDxfStatus) {
		this.staticSaleDxfStatus = staticSaleDxfStatus;
	}

	public Integer getDgStaticSaleSfcStatus() {
		return dgStaticSaleSfcStatus;
	}

	public void setDgStaticSaleSfcStatus(Integer dgStaticSaleSfcStatus) {
		this.dgStaticSaleSfcStatus = dgStaticSaleSfcStatus;
	}

	public String getPrizeSf() {
		return prizeSf;
	}

	public void setPrizeSf(String prizeSf) {
		this.prizeSf = prizeSf;
	}

	public String getPrizeRfsf() {
		return prizeRfsf;
	}

	public void setPrizeRfsf(String prizeRfsf) {
		this.prizeRfsf = prizeRfsf;
	}

	public String getPrizeSfc() {
		return prizeSfc;
	}

	public void setPrizeSfc(String prizeSfc) {
		this.prizeSfc = prizeSfc;
	}

	public String getPrizeDxf() {
		return prizeDxf;
	}

	public void setPrizeDxf(String prizeDxf) {
		this.prizeDxf = prizeDxf;
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

	public Integer getFxId() {
		return fxId;
	}

	public void setFxId(Integer fxId) {
		this.fxId = fxId;
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

	public String getOfficialId() {
		return officialId;
	}

	public void setOfficialId(String officialId) {
		this.officialId = officialId;
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
		sb.append(",status=").append(status);
		sb.append(",结束销售时间:");
		sb.append(CoreDateUtils.formatDateTime(this.endSaleTime));
		sb.append("]");
		return sb.toString();
	}

	public Integer getDgStaticSaleSfStatus() {
		return dgStaticSaleSfStatus;
	}

	public void setDgStaticSaleSfStatus(Integer dgStaticSaleSfStatus) {
		this.dgStaticSaleSfStatus = dgStaticSaleSfStatus;
	}

	public Integer getDgStaticSaleRfsfStatus() {
		return dgStaticSaleRfsfStatus;
	}

	public void setDgStaticSaleRfsfStatus(Integer dgStaticSaleRfsfStatus) {
		this.dgStaticSaleRfsfStatus = dgStaticSaleRfsfStatus;
	}

	public Integer getDgStaticSaleDxfStatus() {
		return dgStaticSaleDxfStatus;
	}

	public void setDgStaticSaleDxfStatus(Integer dgStaticSaleDxfStatus) {
		this.dgStaticSaleDxfStatus = dgStaticSaleDxfStatus;
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



}
