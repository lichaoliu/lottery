package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.io.Serializable;

public class FcServerMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507179636424346311L;

	/**
	 * 随机码
	 */
	private String randCode;
    /**
     *彩票票据信息长度
     */
	private String ticketLen;
    /**
     * 玩法英文名称,游戏编号
     */
	private String playName;
    /**
     * 逻辑机号
     */
	private String logicCode;
    /**
     * 城市代码
     */
	private String cityCode;
    /**
     * 销售期号
     */
	private String selltermCode;
    /**
     * 有效期号
     */
	private String validtermCode;
    /**
     *流水号 
     */
	private String runCode;
    /**
     *开始 销售日期时间 
     */
	private String selldateTime;
    /**
     *投注注数
     */
	private String betNum;
    /**
     *投注方式 
     */
	private String drawWay;
    /**
     *销售方式 
     */
	private String sellWay;
    /**
     *彩票注码 
     */
	private String investCode;
    /**
     *注销标志(0有效，1注销)
     */
	private String withDraw;
    /**
     *身份证号
     */
	private String idCard;
	/**
	 * 号码
	 */
	private String mobileCode;
    /**
     *彩民记名销售卡号 
     */
	private String sellpersonId;
	
    
	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	public String getTicketLen() {
		return ticketLen;
	}

	public void setTicketLen(String ticketLen) {
		this.ticketLen = ticketLen;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public String getLogicCode() {
		return logicCode;
	}

	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getSelltermCode() {
		return selltermCode;
	}

	public void setSelltermCode(String selltermCode) {
		this.selltermCode = selltermCode;
	}

	public String getValidtermCode() {
		return validtermCode;
	}

	public void setValidtermCode(String validtermCode) {
		this.validtermCode = validtermCode;
	}

	public String getRunCode() {
		return runCode;
	}

	public void setRunCode(String runCode) {
		this.runCode = runCode;
	}

	public String getSelldateTime() {
		return selldateTime;
	}

	public void setSelldateTime(String selldateTime) {
		this.selldateTime = selldateTime;
	}

	public String getBetNum() {
		return betNum;
	}

	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}

	public String getDrawWay() {
		return drawWay;
	}

	public void setDrawWay(String drawWay) {
		this.drawWay = drawWay;
	}

	public String getSellWay() {
		return sellWay;
	}

	public void setSellWay(String sellWay) {
		this.sellWay = sellWay;
	}

	public String getInvestCode() {
		return investCode;
	}

	public void setInvestCode(String investCode) {
		this.investCode = investCode;
	}

	public String getWithDraw() {
		return withDraw;
	}

	public void setWithDraw(String withDraw) {
		this.withDraw = withDraw;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getSellpersonId() {
		return sellpersonId;
	}

	public void setSellpersonId(String sellpersonId) {
		this.sellpersonId = sellpersonId;
	}
	
	

}
