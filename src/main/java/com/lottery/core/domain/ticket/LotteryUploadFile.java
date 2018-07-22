package com.lottery.core.domain.ticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 单式上传文件表
 * */
@Entity
@Table(name="lottery_upload_file")
public class LotteryUploadFile implements Serializable{
	@Id
	@Column(name="id")
	private String id;							// 订单流水号
	@Column(name="userno")
	private String userno;
	@Column(name="file_name")
	private String fileName;//文件名
	@Column(name="content")
    private String content;//上传内容
    
    @Column(name="lottery_type")
	
	private Integer lotteryType;		// 彩种
	@Column(name="phase")
	private String phase;					// 彩期 
	
	@Column(name="multiple")
	private Integer multiple;				// 倍数
    @Column(name="amount")
	private BigDecimal amount;					// 金额
    @Column(name="convert_content")
    private String convertContent;// 转换内容
    @Column(name="create_time")
    private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getConvertContent() {
		return convertContent;
	}
	public void setConvertContent(String convertContent) {
		this.convertContent = convertContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
    
    
    
}
