package com.lottery.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 16/7/9.
 *
 */
@Entity
@Table(name="system_exception_message")
public class SystemExceptionMessage implements Serializable{
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="message")
    private String message;
    @Column(name="is_read")
    private Integer isRead;
    @Column(name="create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
