package com.lottery.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user_info")
public class UserInfo implements Serializable{

	private static final long serialVersionUID = -1988154242160576198L;

	
	
	 @Id
	 @Column(name="userno")
	 private String  userno ;//用户编号
	 @Column(name="username")
	 @NotNull
	 private String  username;//登陆名
	 @Column(name="real_name")
	 private String realName;//用户真实姓名
	 @Column(name="passwd")
	 private String passwd;//登陆密码
	 @Column(name="last_login_time")
     @Temporal(TemporalType.TIMESTAMP)
	 private Date  lastLoginTime;//上次登陆时间
	 @Column(name="phoneno")
	 private String  phoneno;// 手机号码
	 @Column(name="idcard")
	 private String idcard;// 身份证
	 @Column(name="status")
	 private Integer status ;//是否可以用，YesNoStatus
	 @Column(name="email")
	 private String email;// 
	 @Column(name="agency_no")
	 private String  agencyNo;//渠道号
	 @Column(name="terminal_type")
	 private Integer  terminalType ;//出票地
	 @Column(name="photo_url")
	 private String   photoUrl;// 头像url
	 @Column(name="qq")
	 private String qq;
     @Column(name="register_time")//注册时间
	 private Date registerTime;
     @Column(name="sex")
     private Integer sex;//性别
     @Column(name="alias")
     private String alias;//别名
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getAgencyNo() {
		return agencyNo;
	}
	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	
	
}
