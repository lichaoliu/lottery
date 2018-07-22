package com.lottery.core.domain.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class UserRebateStatisticPK implements Serializable {

	@Column(name="id")
	private Long id;//月份
	@Column(name="agency_no")
	private String agenyno;//用户名或渠道号
	@Column(name="lottery_type")
	private Integer lotteryType;
	
	public UserRebateStatisticPK(Long id,String agencyno,Integer lotteryType){
		this.id=id;
		this.agenyno=agencyno;
		this.lotteryType=lotteryType;
	}
	public UserRebateStatisticPK(){
		
	}
	
	public String getAgenyno() {
		return agenyno;
	}
	public void setAgenyno(String agenyno) {
		this.agenyno = agenyno;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenyno == null) ? 0 : agenyno.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lotteryType == null) ? 0 : lotteryType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRebateStatisticPK other = (UserRebateStatisticPK) obj;
		if (agenyno == null) {
			if (other.agenyno != null)
				return false;
		} else if (!agenyno.equals(other.agenyno))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lotteryType == null) {
			if (other.lotteryType != null)
				return false;
		} else if (!lotteryType.equals(other.lotteryType))
			return false;
		return true;
	}
	
	public String toString(){
		return "id="+id+",userno="+agenyno+",lotteryType="+lotteryType;
	}
	
}
