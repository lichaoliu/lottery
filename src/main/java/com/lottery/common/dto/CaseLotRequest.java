package com.lottery.common.dto;

import com.lottery.common.contains.YesNoStatus;



public class CaseLotRequest {
	private String userno;//用户名
	private String betcode;//投注内容
	private int lotteryTypeNo;//彩种
	private String phaseNo;//期号
	private long totalAmount;//总金额
	private int oneAmount;//单注金额
	private int lotmulti;//倍数

	private long buyAmt;
	/** 方案保底金额 */
	private long safeAmt;
	/** 佣金比例 */
	private int commisionRatio;
	/** 方案标题 */
	private String title;
	/** 方案描述 */
	private String desc;
	/** 方案内容保密状态 @see CaseLotState */
	private int visibility;
	/** 合买最小购买金额。也代表每注多少钱oneamount */
	private long minAmt; //也就是 1份多少钱
	/** 发起人用户编号 */
	private String starter;
	/** 方案信息 */
	private String caselotinfo;
	/** 订单类型 0-单式上传，1-复式，2-胆拖，3-多方案 */
	private int lotsType;
	
	/** 是否为奖金优化订单*/
	private int prizeOptimize = YesNoStatus.no.value;
	
	private int codeFilter = YesNoStatus.no.value;


	private long totalAmt;
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getBetcode() {
		return betcode;
	}
	public void setBetcode(String betcode) {
		this.betcode = betcode;
	}
	public int getLotteryTypeNo() {
		return lotteryTypeNo;
	}
	public void setLotteryTypeNo(int lotteryTypeNo) {
		this.lotteryTypeNo = lotteryTypeNo;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getOneAmount() {
		return oneAmount;
	}
	public void setOneAmount(int oneAmount) {
		this.oneAmount = oneAmount;
	}
	public int getLotmulti() {
		return lotmulti;
	}
	public void setLotmulti(int lotmulti) {
		this.lotmulti = lotmulti;
	}
	public long getBuyAmt() {
		return buyAmt;
	}
	public void setBuyAmt(long buyAmt) {
		this.buyAmt = buyAmt;
	}
	public long getSafeAmt() {
		return safeAmt;
	}
	public void setSafeAmt(long safeAmt) {
		this.safeAmt = safeAmt;
	}
	public int getCommisionRatio() {
		return commisionRatio;
	}
	public void setCommisionRatio(int commisionRatio) {
		this.commisionRatio = commisionRatio;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public long getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(long minAmt) {
		this.minAmt = minAmt;
	}
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	public String getCaselotinfo() {
		return caselotinfo;
	}
	public void setCaselotinfo(String caselotinfo) {
		this.caselotinfo = caselotinfo;
	}
	public int getLotsType() {
		return lotsType;
	}
	public void setLotsType(int lotsType) {
		this.lotsType = lotsType;
	}
	public int getPrizeOptimize() {
		return prizeOptimize;
	}
	public void setPrizeOptimize(int prizeOptimize) {
		this.prizeOptimize = prizeOptimize;
	}
	public int getCodeFilter() {
		return codeFilter;
	}
	public void setCodeFilter(int codeFilter) {
		this.codeFilter = codeFilter;
	}

	public long getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(long totalAmt) {
		this.totalAmt = totalAmt;
	}
}
