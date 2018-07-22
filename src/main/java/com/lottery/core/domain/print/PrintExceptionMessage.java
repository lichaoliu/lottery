package com.lottery.core.domain.print;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="print_exception_message")
public class PrintExceptionMessage implements Serializable{

	@Id
	@Column(name="id")
	private String id;
	@Column(name="message")
	private String message;
	@Column(name="is_read")
	private Integer isread;
	@Column(name="create_time")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
