/**
 * 
 */
package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;



/**
 * 彩期
 * 
 */
@Entity
@Table(name = "lottery_phase")
public class LotteryPhase implements Serializable {
	private static final long serialVersionUID = -637992010545764091L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "lottery_type")
	private Integer lotteryType; // 彩种类型
	@Column(name = "phase")
	private String phase; // 彩期号
	@Column(name = "create_time")

	private Date createTime; // 创建彩期时间
	@Column(name = "start_sale_time", nullable = false)

	private Date startSaleTime; // 开始销售时间
	@Column(name = "end_sale_time", nullable = false)
	private Date endSaleTime; // 结束销售时间

	@Column(name = "end_ticket_time", nullable = false)
	private Date endTicketTime; // 停止出票时间
	@Column(name = "draw_time", nullable = false)
	private Date drawTime; // 开奖时间
	@Column(name = "phase_status")
	private Integer phaseStatus; // 彩期状态
	@Column(name = "phase_encash_status")
	private Integer phaseEncaseStatus; // 派奖状态
	@Column(name = "phase_time_status", nullable = false)
	private Integer phaseTimeStatus; // 彩期状态
	@Column(name = "hemai_end_time")
	
	private Date hemaiEndTime; // 合买截止时间
	@Column(name = "terminal_status", nullable = false)
	private Integer terminalStatus; // 所有终端是否出票 TermianlStatus
	@Column(name = "wincode")
	private String wincode; // 开奖号码
	@Column(name = "prize_detail")
	private String prizeDetail; // 开奖详情，具体为：奖级_注数_奖金,奖级_注数_奖金,奖级_注数_奖金
	@Column(name = "pool_amount")
	private String poolAmount; // 奖池金额
	@Column(name = "add_pool_amount")
	private String addPoolAmount; // 加奖奖池金额，针对大乐透
	@Column(name = "sale_amount")
	private String saleAmount; // 销售总金额
	@Column(name = "switch_time")
	private Date switchTime; // 新期切换时间
	@Column(name = "for_sale")
	private Integer forSale;// 是否可销售 YesNoStatus
	@Column(name = "for_current")
	private Integer forCurrent;// 是否是当前期
	@Column(name = "draw_data_from")
	private String drawDataFrom; // 数据来源
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="real_draw_time")
	private Date realDrawTime;//实际开奖时间



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

	public Date getStartSaleTime() {
		return startSaleTime;
	}

	public void setStartSaleTime(Date startSaleTime) {
		this.startSaleTime = startSaleTime;
	}

	public Date getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

	public Date getEndTicketTime() {
		return endTicketTime;
	}

	public void setEndTicketTime(Date endTicketTime) {
		this.endTicketTime = endTicketTime;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Integer getPhaseStatus() {
		return phaseStatus;
	}

	public void setPhaseStatus(Integer phaseStatus) {
		this.phaseStatus = phaseStatus;
	}

	public Integer getPhaseEncaseStatus() {
		return phaseEncaseStatus;
	}

	public void setPhaseEncaseStatus(Integer phaseEncaseStatus) {
		this.phaseEncaseStatus = phaseEncaseStatus;
	}

	public Integer getPhaseTimeStatus() {
		return phaseTimeStatus;
	}

	public void setPhaseTimeStatus(Integer phaseTimeStatus) {
		this.phaseTimeStatus = phaseTimeStatus;
	}

	public Date getHemaiEndTime() {
		return hemaiEndTime;
	}

	public void setHemaiEndTime(Date hemaiEndTime) {
		this.hemaiEndTime = hemaiEndTime;
	}

	public Integer getTerminalStatus() {
		return terminalStatus;
	}

	public void setTerminalStatus(Integer terminalStatus) {
		this.terminalStatus = terminalStatus;
	}

	public String getWincode() {
		return wincode;
	}

	public void setWincode(String wincode) {
		this.wincode = wincode;
	}

	public Date getSwitchTime() {
		return switchTime;
	}

	public void setSwitchTime(Date switchTime) {
		this.switchTime = switchTime;
	}

	public String getPrizeDetail() {
		return prizeDetail;
	}

	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}

	public Integer getForSale() {
		return forSale;
	}

	public void setForSale(Integer forSale) {
		this.forSale = forSale;
	}

	public Integer getForCurrent() {
		return forCurrent;
	}

	public void setForCurrent(Integer forCurrent) {
		this.forCurrent = forCurrent;
	}

	public String getPoolAmount() {
		return poolAmount;
	}

	public void setPoolAmount(String poolAmount) {
		this.poolAmount = poolAmount;
	}

	public String getAddPoolAmount() {
		return addPoolAmount;
	}

	public void setAddPoolAmount(String addPoolAmount) {
		this.addPoolAmount = addPoolAmount;
	}

	public String getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}



	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[彩种:" + lotteryType);
		sb.append(",期号：" + phase);
		sb.append(",出票截止时间:" + endTicketTime);
		sb.append(",销售截止时间:" + endSaleTime);
		sb.append(",销售标示:" + forSale);
		sb.append(",是否当前期:" + forCurrent);
		sb.append(",数据来源：" + drawDataFrom + "]");

		return sb.toString();
	}


	public Date getRealDrawTime() {
		return realDrawTime;
	}

	public void setRealDrawTime(Date realDrawTime) {
		this.realDrawTime = realDrawTime;
	}

	public String getDrawDataFrom() {
		return drawDataFrom;
	}

	public void setDrawDataFrom(String drawDataFrom) {
		this.drawDataFrom = drawDataFrom;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

}
