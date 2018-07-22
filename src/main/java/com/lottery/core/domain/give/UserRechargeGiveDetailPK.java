package com.lottery.core.domain.give;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class UserRechargeGiveDetailPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="give_id")
	private String giveId;//赠送id
	@Column(name="userno")
	private String userno;//
	public UserRechargeGiveDetailPK(){}
	
	public UserRechargeGiveDetailPK(String giveId,String userno){
		this.giveId=giveId;
		this.userno=userno;
	}

	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((giveId == null) ? 0 : giveId.hashCode());
		result = prime * result + ((userno == null) ? 0 : userno.hashCode());
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
		UserRechargeGiveDetailPK other = (UserRechargeGiveDetailPK) obj;
		if (giveId == null) {
			if (other.giveId != null)
				return false;
		} else if (!giveId.equals(other.giveId))
			return false;
		if (userno == null) {
			if (other.userno != null)
				return false;
		} else if (!userno.equals(other.userno))
			return false;
		return true;
	}
	
	
	
	
	
}
