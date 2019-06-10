package com.lottery.core.domain.ticket;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 彩票批次表.
 * 
 */
@Entity
@Table(name="ticket_batch")
public class TicketBatch implements Serializable{

	private static final long serialVersionUID = -2552552743754980391L;
	@Id
	@Column(name="id")
	private String id;			//批次号
	@Column(name="terminal_id",nullable=false)
	private Long terminalId;	//终端号，本次送票采用的终端号
	@Column(name="ticket_batch_status")
	 
	private int status;	//批次状态 TicketBatchStatus
	@Column(name="create_time")

	private Date createTime;	//批次创建时间
	@Column(name="send_time")
	private Date sendTime;		//送票成功时间
	@Column(name="terminal_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date terminateTime;	//截止送票时间，冗余字段，实际上就是当前彩期出票截止时间

    @Column(name="phase")
	private String phase;		//期数
	@Column(name="lottery_type",nullable=false)
	private Integer lotteryType;
	@Column(name="play_type")
	private Integer playType; //PlayType
	@Column(name="terminal_type_id")
	private Integer terminalTypeId;//终端类型id,TeminalType 的value值
	@Column(name="update_time")
	private Date updateTime;//修改时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getTerminateTime() {
		return terminateTime;
	}
	public void setTerminateTime(Date terminateTime) {
		this.terminateTime = terminateTime;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Integer getPlayType() {
		return playType;
	}
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

    public Integer getTerminalTypeId() {
        return terminalTypeId;
    }

    public void setTerminalTypeId(Integer terminalTypeId) {
        this.terminalTypeId = terminalTypeId;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TicketBatch that = (TicketBatch) o;

		if (status != that.status) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) return false;
		if (terminateTime != null ? !terminateTime.equals(that.terminateTime) : that.terminateTime != null)
			return false;
		if (phase != null ? !phase.equals(that.phase) : that.phase != null) return false;
		if (lotteryType != null ? !lotteryType.equals(that.lotteryType) : that.lotteryType != null) return false;
		if (playType != null ? !playType.equals(that.playType) : that.playType != null) return false;
		if (terminalTypeId != null ? !terminalTypeId.equals(that.terminalTypeId) : that.terminalTypeId != null)
			return false;
		return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
		result = 31 * result + status;
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
		result = 31 * result + (terminateTime != null ? terminateTime.hashCode() : 0);
		result = 31 * result + (phase != null ? phase.hashCode() : 0);
		result = 31 * result + (lotteryType != null ? lotteryType.hashCode() : 0);
		result = 31 * result + (playType != null ? playType.hashCode() : 0);
		result = 31 * result + (terminalTypeId != null ? terminalTypeId.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		return result;
	}


	@Override
	public String toString() {
		return "TicketBatch{" +
				"id='" + id + '\'' +
				", terminalId=" + terminalId +
				", status=" + status +
				", createTime=" + createTime +
				", sendTime=" + sendTime +
				", terminateTime=" + terminateTime +
				", phase='" + phase + '\'' +
				", lotteryType=" + lotteryType +
				", playType=" + playType +
				", terminalTypeId=" + terminalTypeId +
				", updateTime=" + updateTime +
				'}';
	}
}
