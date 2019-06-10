package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lottery_caselot")
public class LotteryCaseLot implements Serializable{

	private static final long serialVersionUID = 7085435800027386185L;

	@Id
	@Column(name = "id")
	private String id;

	/** 发起人用户编号 */
	@Column(name = "starter")
	private String starter;

	/** 合买最小购买金额 */
	@Column(name = "min_amt")
	private Long minAmt;

	/** 方案总金额 */
	@Column(name = "total_amt")
	private Long totalAmt;

	/** 方案保底金额 */
	@Column(name = "safe_amt")
	private Long safeAmt;

	/** 发起人购买金额 */
	@Column(name = "buyamt_by_starter")
	private Long buyAmtByStarter;
	
	/** 参与人购买金额 */
	@Column(name = "buyamt_by_follower")
	private long buyAmtByFollower;

	/** 佣金比例 */
	@Column(name = "commision_ratio")
	private Integer commisionRatio;

	/** 方案开始时间 */
	@Column(name = "start_time")
	private Date startTime;

	/** 方案结束时间 */
	@Column(name = "end_time")
	private Date endTime;

	/** 方案内容保密状态 @see CaseLotVisibility */
	@Column(name = "visibility")
	private Integer visibility;

	/** 方案描述 */
	@Column(name = "description")
	private String description;

	/** 方案标题 */
	@Column(name = "title")
	private String title;

	/** 方案状态  CaseLotState*/
	@Column(name = "state")
	private Integer state;

	/** 用户看到的状态 CaseLotDisplayState */
	@Column(name = "display_state")
	private Integer displayState;

	/** 用户看到的状态描述 CaseLotDisplayState*/
	@Column(name = "display_state_memo")
	private String displayStateMemo;

	/** 方案内容描述 */
	@Column(name = "content")
	private String content;

	/** 订单编号 */
	@Column(name = "orderid")
	private String orderid;

	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 期号 */
	@Column(name = "phase")
	private String phase;

	/** 派奖状态  YesNoStatus */
	@Column(name = "is_exchanged")
	private Integer isExchanged;

	/** 税后奖金 */
	@Column(name = "win_big_amt")
	private Long winBigAmt;

	/** 税前奖金 */
	@Column(name = "win_pre_amt")
	private Long winPreAmt;

	/** 中奖时间 */
	@Column(name = "win_start_time")
	private Date winStartTime;

	/** 中奖信息 */
	@Column(name = "win_detail")
	private String winDetail;

	/** 方案信息 */
	@Column(name = "caselotinfo")
	private String caselotinfo;


	/** 置顶状态 CaseLotSortState*/
	@Column(name = "sort_state")
	private Integer sortState;

	/** 参与人数 */
	@Column(name = "participant_count")
	private Long participantCount;
	
	/** 方案类型 */
	@Column(name = "lots_type")
	private Integer lotsType;
	
	/** * 合买完结时间  */
	@Column(name="finish_time")
	private Date finishTime;
	
	/**  合买发起人名称 */
	@Column(name="start_name")
	private String startName;
	@Column(name="order_result_status")
	private Integer orderResultStatus;//中奖状态

    @Column(name="is_commission")
	private Integer isCommission;//是否有提成YesNoStatus



	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public Long getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(Long minAmt) {
		this.minAmt = minAmt;
	}

	public Long getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Long totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Long getSafeAmt() {
		return safeAmt;
	}

	public void setSafeAmt(Long safeAmt) {
		this.safeAmt = safeAmt;
	}

	public Long getBuyAmtByStarter() {
		return buyAmtByStarter;
	}

	public void setBuyAmtByStarter(Long buyAmtByStarter) {
		this.buyAmtByStarter = buyAmtByStarter;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplayStateMemo() {
		return displayStateMemo;
	}

	public void setDisplayStateMemo(String displayStateMemo) {
		this.displayStateMemo = displayStateMemo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public Long getWinBigAmt() {
		return winBigAmt;
	}

	public void setWinBigAmt(Long winBigAmt) {
		this.winBigAmt = winBigAmt;
	}

	public Long getWinPreAmt() {
		return winPreAmt;
	}

	public void setWinPreAmt(Long winPreAmt) {
		this.winPreAmt = winPreAmt;
	}

	public Date getWinStartTime() {
		return winStartTime;
	}

	public void setWinStartTime(Date winStartTime) {
		this.winStartTime = winStartTime;
	}

	public String getWinDetail() {
		return winDetail;
	}

	public void setWinDetail(String winDetail) {
		this.winDetail = winDetail;
	}

	public String getCaselotinfo() {
		return caselotinfo;
	}

	public void setCaselotinfo(String caselotinfo) {
		this.caselotinfo = caselotinfo;
	}

	public long getBuyAmtByFollower() {
		return buyAmtByFollower;
	}

	public void setBuyAmtByFollower(long buyAmtByFollower) {
		this.buyAmtByFollower = buyAmtByFollower;
	}

	public Integer getSortState() {
		return sortState;
	}

	public void setSortState(Integer sortState) {
		this.sortState = sortState;
	}

	public Long getParticipantCount() {
		return participantCount;
	}

	public void setParticipantCount(Long participantCount) {
		this.participantCount = participantCount;
	}
	public Integer getCommisionRatio() {
		return commisionRatio;
	}

	public void setCommisionRatio(Integer commisionRatio) {
		this.commisionRatio = commisionRatio;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDisplayState() {
		return displayState;
	}

	public void setDisplayState(Integer displayState) {
		this.displayState = displayState;
	}
	public Integer getIsExchanged() {
		return isExchanged;
	}

	public void setIsExchanged(Integer isExchanged) {
		this.isExchanged = isExchanged;
	}

	public Integer getLotsType() {
		return lotsType;
	}

	public void setLotsType(Integer lotsType) {
		this.lotsType = lotsType;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getOrderResultStatus() {
		return orderResultStatus;
	}

	public void setOrderResultStatus(Integer orderResultStatus) {
		this.orderResultStatus = orderResultStatus;
	}

	public Integer getIsCommission() {
		return isCommission;
	}

	public void setIsCommission(Integer isCommission) {
		this.isCommission = isCommission;
	}
}
