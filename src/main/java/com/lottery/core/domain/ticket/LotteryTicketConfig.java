package com.lottery.core.domain.ticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 彩种出票配置
 * @author fengqinyun 
 *
 */
@Entity
@Table(name="lottery_ticket_config")
public class LotteryTicketConfig implements Serializable{

	private static final long serialVersionUID = -1419944356828754113L;
    @Id
    @Column(name="id")
	private Long id; 		//记录编号
	@Column(name="lottery_type",unique=true)
	private Integer lotteryType;	//彩种类型
	@Column(name="batch_count")
	private Integer batchCount;		//每批票的数量
	@Column(name="batch_time")
	private Long batchTime;		//最长打包时间
	@Column(name="beginsale_forward")
	private Long beginSaleForward;			//开售提前时间，可为负，表示延后开始售票
	@Column(name="endsale_forward")
	private Long endSaleForward;			//销售提前截止时间
	@Column(name="drawbackward")
	private Long drawBackward;				// 开奖延后时间
	@Column(name="endticket_timeout")
	private Long endTicketTimeout;			//出票截止超时时间
	@Column(name="beginsaleallot_backward")
	private Long beginSaleAllotBackward;	// 开售后分票延时时间
	@Column(name="endsaleallot_forward")
	private Long endSaleAllotForward;		// 停售前分票提前时间
	@Column(name="send_count")
	private Integer sendCount;//送票数量
	@Column(name="chase_endsale_forward")
	private Long chaseEndSaleForward;//追号提前截止时间
	@Column(name="send_thread_count")
	private Integer sendThreadCount;//送票线程数
	

    @Override	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[彩种:"+lotteryType);
		sb.append(",提前销售截止是:"+endSaleForward);
		sb.append(",出票截止超时时间:"+endTicketTimeout);
		sb.append("]");
		return sb.toString();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	
	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(int lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public Long getBatchTime() {
		return batchTime;
	}

	public void setBatchTime(Long batchTime) {
		this.batchTime = batchTime;
	}

	public Long getEndSaleForward() {
		return endSaleForward;
	}

	public void setEndSaleForward(Long endSaleForward) {
		this.endSaleForward = endSaleForward;
	}

	public Long getBeginSaleForward() {
		return beginSaleForward;
	}

	public void setBeginSaleForward(Long beginSaleForward) {
		this.beginSaleForward = beginSaleForward;
	}

	public void setEndTicketTimeout(Long endTicketTimeout) {
		this.endTicketTimeout = endTicketTimeout;
	}

	public Long getEndTicketTimeout() {
		return endTicketTimeout;
	}

	public Long getDrawBackward() {
		return drawBackward;
	}

	public void setDrawBackward(Long drawBackward) {
		this.drawBackward = drawBackward;
	}

	public Long getBeginSaleAllotBackward() {
		return beginSaleAllotBackward;
	}

	public void setBeginSaleAllotBackward(Long beginSaleAllotBackward) {
		this.beginSaleAllotBackward = beginSaleAllotBackward;
	}

	public Long getEndSaleAllotForward() {
		return endSaleAllotForward;
	}

	public void setEndSaleAllotForward(Long endSaleAllotForward) {
		this.endSaleAllotForward = endSaleAllotForward;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getChaseEndSaleForward() {
		return chaseEndSaleForward;
	}

	public void setChaseEndSaleForward(Long chaseEndSaleForward) {
		this.chaseEndSaleForward = chaseEndSaleForward;
	}

	public Integer getSendThreadCount() {
		return sendThreadCount;
	}

	public void setSendThreadCount(Integer sendThreadCount) {
		this.sendThreadCount = sendThreadCount;
	}
}
