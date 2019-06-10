package com.lottery.core.domain.give;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 2017/2/15.
 * 用户红包
 */
@Entity
@Table(name="user_red_package")
public class UserRedPackage implements Serializable{
    @Id
    @Column(name="id")
    private String id;//主键
    @Column(name="give_userno")
    private String giveUserno;//赠送人userno
    @Column(name="give_phone")
    private String givePhone;//赠送人手机号
    @Column(name="give_realname")
    private String giveRealName;//赠送人真实姓名
    @Column(name="amount")
    private BigDecimal amount;//赠送金额(单位分)
    @Column(name="receive_userno")
    private String receiveUserno;//接受人userno
    @Column(name="receive_phone")
    private String receivePhone;//接受人手机号
    @Column(name="give_time")
    private Date giveTime;//赠送时间
    @Column(name="receive_time")
    private Date receiveTime;//接受时间
    @Column(name="expiry_time")
    private Date expiryTime;//失效时间
    @Column(name = "status")
    private Integer status;//状态  RedPackageStatus

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGiveUserno() {
        return giveUserno;
    }

    public void setGiveUserno(String giveUserno) {
        this.giveUserno = giveUserno;
    }

    public String getGivePhone() {
        return givePhone;
    }

    public void setGivePhone(String givePhone) {
        this.givePhone = givePhone;
    }

    public String getGiveRealName() {
        return giveRealName;
    }

    public void setGiveRealName(String giveRealName) {
        this.giveRealName = giveRealName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiveUserno() {
        return receiveUserno;
    }

    public void setReceiveUserno(String receiveUserno) {
        this.receiveUserno = receiveUserno;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public Date getGiveTime() {
        return giveTime;
    }

    public void setGiveTime(Date giveTime) {
        this.giveTime = giveTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
