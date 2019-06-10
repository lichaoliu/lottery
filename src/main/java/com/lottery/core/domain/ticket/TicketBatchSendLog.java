package com.lottery.core.domain.ticket;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 批次送票的记录
 *
 * @author fengqinyun
 */
@Entity
@Table(name="ticket_batch_send_log")
public class TicketBatchSendLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name="id")
    @Id
    private String id;
    @Column(name="batch_id")
    private String batchId;       //批次号
    @Column(name="create_time")
    private Date createTime;    //发生时间
    @Column(name="terminal_id")
    private Long terminalId;    //送票时的终端号或变更后的终端号
   
	@Column(name="terminal_type_id")
	private Integer terminalTypeId;//终端类型id,TeminalType 的value值
	@Column(name="error_message")
    private String errorMessage;  //错误信息

  

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


    public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

  

	public Integer getTerminalTypeId() {
		return terminalTypeId;
	}

	public void setTerminalTypeId(Integer terminalTypeId) {
		this.terminalTypeId = terminalTypeId;
	}

	public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
