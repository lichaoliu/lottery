package com.lottery.ticket.sender;

import java.io.Serializable;

/**
 * Created by fengqinyun on 16/7/19.
 * 送票基本类
 */
public class TicketSendUser implements Serializable{

    private String realName;//真实姓名
    private String phone;//手机号码
    private String idCard;//身份证

    private String email;//电子邮箱

    private String userName;//用户名
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
