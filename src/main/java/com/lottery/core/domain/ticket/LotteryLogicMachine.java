package com.lottery.core.domain.ticket;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 14-8-20.
 *
 * 逻辑机管理
 */
@Entity
@Table(name = "lottery_logic_machine")
public class LotteryLogicMachine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4210890611447510886L;
	@EmbeddedId
	private LogicMachinePK pk;
	@Column(name="city_code")
	private String cityCode;//城市编号
	@Column(name = "terminal_id")
	private Long terminalId;// 终端id
	@Column(name = "start_id")
	private Long startId;// 开始流水号
	@Column(name = "end_id")
	private Long endId;// 结束流水号
	@Column(name = "current_id")
	private Long currentId;// 当前流水号
	@Column(name = "status")
	private Integer status;// 状态(0用完1正使用2未使用) LogicMachineStatus
	@Column(name = "currentdate")
	private String currentDate;// 当天时间
	@Column(name = "switch_time")
	private Date switchTime;// 切换时间
	@Column(name = "update_time")
	private Date updateTime;// 修改时间
	@Column(name = "phase")
	private String phase;// 期号
    @Column(name="weight")
	private Integer weight;//权重
    @Column(name="ip")
    private String ip;
    @Column(name="port")
    private Integer port;
    @Column(name="describe_str")
    private String describeStr;//描述
    
    private transient BigDecimal balance;
	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public Long getStartId() {
		return startId;
	}

	public void setStartId(Long startId) {
		this.startId = startId;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public Long getCurrentId() {
		return currentId;
	}

	public void setCurrentId(Long currentId) {
		this.currentId = currentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSwitchTime() {
		return switchTime;
	}

	public void setSwitchTime(Date switchTime) {
		this.switchTime = switchTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public LogicMachinePK getPk() {
		return pk;
	}

	public void setPk(LogicMachinePK pk) {
		this.pk = pk;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDescribeStr() {
		return describeStr;
	}

	public void setDescribeStr(String describeStr) {
		this.describeStr = describeStr;
	}


	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
