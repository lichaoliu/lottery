package com.lottery.core.domain.ticket;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 票分配到批次的记录
 *
 * @author fengqinyun
 */
@Entity
@Table(name="ticket_allot_log")
public class TicketAllotLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id")
    private String id;
    @Column(name="ticket_id")
    private String ticketId;      //票号
    @Column(name="batch_id")
    private String batchId;       //批次号
    @Column(name="create_time")
    private Date createTime;    //批次创建时间
    @Column(name="terminal_id")
    private Long terminalId;    //批次所在的终端号
	@Column(name="terminal_type_id")
	private Integer terminalTypeId;//终端类型id,TeminalType 的value值
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public Integer getTerminalTypeId() {
		return terminalTypeId;
	}

	public void setTerminalTypeId(Integer terminalTypeId) {
		this.terminalTypeId = terminalTypeId;
	}

	public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

   

    public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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

    
}
