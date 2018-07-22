package com.lottery.core.domain.agency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 15/4/1.
 * 系统渠道表
 */
@Table(name="lottery_agency")
@Entity
public class LotteryAgency implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5026780515856194825L;
	@Id
    @Column(name="agency_no")
    private String agencyNo;//渠道编号
    @Column(name="parent_agency")
    private String parentAgency;//上级渠道
    @Column(name="agency_type")
    private Integer agencyType;//渠道类型 AgencyType 类
    @Column(name="agency_name")
    private String agencyName;//等级名称
    @Column(name="status")
    private Integer status;//状态

    @Column(name="level")
    private Integer level;//代理等级
    
    @Column(name="leaf")
    private Integer leaf;//是否叶子节点
    
    @Column(name="create_time")
    private Date createTime;//创建时间
    @Column(name="agency_key")
    private String key;//加密密钥
    
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

    public Integer getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(Integer agencyType) {
        this.agencyType = agencyType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
