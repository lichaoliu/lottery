package com.lottery.core.domain.print;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 16/11/9.
 *
 * 票机配置
 */
@Entity
@Table(name="print_server_config")
public class PrintServerConfig implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="id")
    private String id;//机器编号
    @Column(name="short_id")
    private String shortId;//简称
    @Column(name = "area_name")
    private String areaName;//地区名字
    @Column(name = "area_id")
    private String areaId;//地区编号
    @Column(name="lottery_types")
    private String lotteryTypes;//彩种集合
    @Column(name="play_types")
    private String playTypes;//玩法集合
    @Column(name="weight")
    private Integer weight;//权重
    @Column(name="max_amount")
    private Integer maxAmount;//最大金额(元)
    @Column(name = "min_amount")
    private Integer minAmount;//最小金额(元)
    @Column(name="max_seconds")
    private Long maxSeconds;//最大秒
    @Column(name="min_seconds")
    private Long minSecodes;//最小秒
    @Column(name="balance")
    private Double balance;//票机余额
    @Column(name="print_amount")
    private Double printAmount;//已出票金额
    @Column(name="print_num")
    private Integer printNum;//打票张数
    @Column(name="prize_amount")
    private Double prizeAmount;//兑奖金额
    @Column(name="max_prize_amount")
    private Double maxPrizeAmount;//最大兑奖金额
    @Column(name="user_name")
    private String username;//用户名
    @Column(name="passwd")
    private String passwd;//密码
    @Column(name="is_online")
    private Integer isOnline;//是否登陆 YesNoStatus
    @Column(name="status")
    private Integer status;//票机状态 PrintServerConfigStatus

    @Column(name="machine_type")
    private String machineType;//机器类型
    @Column(name="create_time")
    private Date creatTime;//创建时间
	
    @Column(name="report_type")
	private Integer reportType;//报表类型 reportType
	
    @Column(name="begin_date")
	private String beginDate;//打印报表日期 20160923
	
    @Column(name="end_date")
	private String endDate;//打印报表日期 20160923
	@Column(name = "is_big_money")
    private Integer isBigMoney;//是否出大票 YesNoStatus
	@Column(name = "is_del")
    private Integer isDel;//是否已删除 YesNoStatus
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getLotteryTypes() {
        return lotteryTypes;
    }

    public void setLotteryTypes(String lotteryTypes) {
        this.lotteryTypes = lotteryTypes;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxSeconds() {
        return maxSeconds;
    }

    public void setMaxSeconds(Long maxSeconds) {
        this.maxSeconds = maxSeconds;
    }

    public Long getMinSecodes() {
        return minSecodes;
    }

    public void setMinSecodes(Long minSecodes) {
        this.minSecodes = minSecodes;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getPrintAmount() {
        return printAmount;
    }

    public void setPrintAmount(Double printAmount) {
        this.printAmount = printAmount;
    }

    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }

    public Double getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(Double prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public Double getMaxPrizeAmount() {
        return maxPrizeAmount;
    }

    public void setMaxPrizeAmount(Double maxPrizeAmount) {
        this.maxPrizeAmount = maxPrizeAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

    public Integer getIsBigMoney() {
        return isBigMoney;
    }

    public void setIsBigMoney(Integer isBigMoney) {
        this.isBigMoney = isBigMoney;
    }

    public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

    public String getPlayTypes() {
        return playTypes;
    }

    public void setPlayTypes(String playTypes) {
        this.playTypes = playTypes;
    }

    @Override
    public String toString() {
        return "PrintServerConfig{" +
                "id='" + id + '\'' +
                ", shortId='" + shortId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaId='" + areaId + '\'' +
                ", lotteryTypes='" + lotteryTypes + '\'' +
                ", weight=" + weight +
                ", maxAmount=" + maxAmount +
                ", minAmount=" + minAmount +
                ", maxSeconds=" + maxSeconds +
                ", minSecodes=" + minSecodes +
                ", balance=" + balance +
                ", printAmount=" + printAmount +
                ", printNum=" + printNum +
                ", prizeAmount=" + prizeAmount +
                ", maxPrizeAmount=" + maxPrizeAmount +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", isOnline=" + isOnline +
                ", status=" + status +
                ", machineType='" + machineType + '\'' +
                ", creatTime=" + creatTime +
                ", reportType=" + reportType +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrintServerConfig that = (PrintServerConfig) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (shortId != null ? !shortId.equals(that.shortId) : that.shortId != null) return false;
        if (areaName != null ? !areaName.equals(that.areaName) : that.areaName != null) return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (lotteryTypes != null ? !lotteryTypes.equals(that.lotteryTypes) : that.lotteryTypes != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (maxAmount != null ? !maxAmount.equals(that.maxAmount) : that.maxAmount != null) return false;
        if (minAmount != null ? !minAmount.equals(that.minAmount) : that.minAmount != null) return false;
        if (maxSeconds != null ? !maxSeconds.equals(that.maxSeconds) : that.maxSeconds != null) return false;
        if (minSecodes != null ? !minSecodes.equals(that.minSecodes) : that.minSecodes != null) return false;
        if (balance != null ? !balance.equals(that.balance) : that.balance != null) return false;
        if (printAmount != null ? !printAmount.equals(that.printAmount) : that.printAmount != null) return false;
        if (printNum != null ? !printNum.equals(that.printNum) : that.printNum != null) return false;
        if (prizeAmount != null ? !prizeAmount.equals(that.prizeAmount) : that.prizeAmount != null) return false;
        if (maxPrizeAmount != null ? !maxPrizeAmount.equals(that.maxPrizeAmount) : that.maxPrizeAmount != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (passwd != null ? !passwd.equals(that.passwd) : that.passwd != null) return false;
        if (isOnline != null ? !isOnline.equals(that.isOnline) : that.isOnline != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (machineType != null ? !machineType.equals(that.machineType) : that.machineType != null) return false;
        if (creatTime != null ? !creatTime.equals(that.creatTime) : that.creatTime != null) return false;
        if (reportType != null ? !reportType.equals(that.reportType) : that.reportType != null) return false;
        if (beginDate != null ? !beginDate.equals(that.beginDate) : that.beginDate != null) return false;
        return endDate != null ? endDate.equals(that.endDate) : that.endDate == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (shortId != null ? shortId.hashCode() : 0);
        result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (lotteryTypes != null ? lotteryTypes.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (maxAmount != null ? maxAmount.hashCode() : 0);
        result = 31 * result + (minAmount != null ? minAmount.hashCode() : 0);
        result = 31 * result + (maxSeconds != null ? maxSeconds.hashCode() : 0);
        result = 31 * result + (minSecodes != null ? minSecodes.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (printAmount != null ? printAmount.hashCode() : 0);
        result = 31 * result + (printNum != null ? printNum.hashCode() : 0);
        result = 31 * result + (prizeAmount != null ? prizeAmount.hashCode() : 0);
        result = 31 * result + (maxPrizeAmount != null ? maxPrizeAmount.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (isOnline != null ? isOnline.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (machineType != null ? machineType.hashCode() : 0);
        result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
        result = 31 * result + (reportType != null ? reportType.hashCode() : 0);
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }


}
