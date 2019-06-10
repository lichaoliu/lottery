package com.lottery.core.domain.agency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by fengqinyun on 15/4/2.
 * 渠道点位表
 */
@Entity
@Table(name="lottery_agency_point_location")
public class LotteryAgencyPointLocation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9199553879900440955L;
	@Id
    @Column(name="id")
    private Long id;
    @Column(name="agency_no")
    private String agencyNo;//渠道号
    @Column(name="parent_agency")
    private String parentAgency;//父渠道号
    @Column(name="lottery_type")
    private Integer lotteryType;//彩种
    @Column(name="point_location")
    private BigDecimal pointLocation;//点位
    @Column(name="agency_point_location")
    private BigDecimal agencyPointLocation;//所属渠道点位
    @Column(name="parent_point_location")
    private BigDecimal parentPointLocation;//父渠道点位
    @Column(name="status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(String agencyNo) {
        this.agencyNo = agencyNo;
    }

    public String getParentAgency() {
        return parentAgency;
    }

    public void setParentAgency(String parentAgency) {
        this.parentAgency = parentAgency;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public BigDecimal getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(BigDecimal pointLocation) {
        this.pointLocation = pointLocation;
    }

    public BigDecimal getAgencyPointLocation() {
        return agencyPointLocation;
    }

    public void setAgencyPointLocation(BigDecimal agencyPointLocation) {
        this.agencyPointLocation = agencyPointLocation;
    }

    public BigDecimal getParentPointLocation() {
        return parentPointLocation;
    }

    public void setParentPointLocation(BigDecimal parentPointLocation) {
        this.parentPointLocation = parentPointLocation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
