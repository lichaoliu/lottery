package com.lottery.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="lottype_config")
public class LottypeConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6057468692241830359L;

	@Id
	@Column(name = "lottery_type")
	private Integer lotteryType;//彩种编号

	@Column(name = "state")
	private Integer state;//  LottypeConfigStatus 
	@Column(name="sale_enabled")
	private Integer saleEnabled; //EnabledStatus ,是否允许销售

	
	
	@Column(name = "onprize")
	private Integer onprize; //0关闭  1打开   自动算奖

	@Column(name = "autoencash")
	private Integer autoencash;//0关闭  1打开  自动兑奖
	
	@Column(name = "lotcenterisvalid")
	private Integer lotcenterisvalid;//0关闭  1打开  彩票中心校验

	@Column(name = "pre_phase_num")
	private Integer prePhaseNum;//预生成期的数量
	
    @Column(name="heimai_Forward")
	private Integer heimaiForward; //合买提前(单位:分)
    
    @Column(name="upload_Forward")
 	private Integer uploadForward; //单式上传提前时间

    @Column(name="single_hemai_forward")
    private Integer singleHemaiForward; //单式合买提前
    @Column(name="b2b_forward")
	private Integer b2bForward;//b2b销售提前
    @Column(name="end_forward")
	private Integer endForward;//销售提前(单位分)
    
    @Column(name="web_end_sale")
    private Integer webEndSale; //网站是否停售 YesNoStatus
    @Column(name="ios_end_sale")
    private Integer iosEndSale;//ios是否停售
    @Column(name="android_end_sale")
    private Integer androidEendSale;//android
	@Column(name="b2b_end_sale")
	private Integer b2bEndSale;//b2b是否停售
    @Column(name="is_add_prize")
    private Integer isAddPrize;//是否加奖
	@Column(name = "hemai_end_sale")
	private Integer hemaiEndSale;//合买是否停止
	@Column(name="chase_end_sale")
	private Integer chaseEndSale;//追号是否停止
	
//	@Column(name="split_limit_enable")
//	private Integer splitLimitEnable;//是否开启按金额拆票
//	
//	@Column(name="split_limit_config")
//	private String splitLimitConfig;//按金额拆票配置playtype:amt;playtype:amt
	
	
	
	
    
	

	private transient Date endSaleTime;
	
	private transient String phase;
	
	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getOnprize() {
		return onprize;
	}
	public void setOnprize(Integer onprize) {
		this.onprize = onprize;
	}
	public Integer getAutoencash() {
		return autoencash;
	}
	public void setAutoencash(Integer autoencash) {
		this.autoencash = autoencash;
	}
	public Integer getLotcenterisvalid() {
		return lotcenterisvalid;
	}
	public void setLotcenterisvalid(Integer lotcenterisvalid) {
		this.lotcenterisvalid = lotcenterisvalid;
	}

	public Integer getPrePhaseNum() {
		return prePhaseNum;
	}

	public void setPrePhaseNum(Integer prePhaseNum) {
		this.prePhaseNum = prePhaseNum;
	}

	public Integer getHeimaiForward() {
		return heimaiForward;
	}

	public void setHeimaiForward(Integer heimaiForward) {
		this.heimaiForward = heimaiForward;
	}

	public Integer getUploadForward() {
		return uploadForward;
	}

	public void setUploadForward(Integer uploadForward) {
		this.uploadForward = uploadForward;
	}

	public Integer getWebEndSale() {
		return webEndSale;
	}

	public void setWebEndSale(Integer webEndSale) {
		this.webEndSale = webEndSale;
	}

	public Integer getIosEndSale() {
		return iosEndSale;
	}

	public void setIosEndSale(Integer iosEndSale) {
		this.iosEndSale = iosEndSale;
	}

	public Integer getAndroidEendSale() {
		return androidEendSale;
	}

	public void setAndroidEendSale(Integer androidEendSale) {
		this.androidEendSale = androidEendSale;
	}

	public Integer getIsAddPrize() {
		return isAddPrize;
	}

	public void setIsAddPrize(Integer isAddPrize) {
		this.isAddPrize = isAddPrize;
	}

	public Date getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

    public Integer getSingleHemaiForward() {
        return singleHemaiForward;
    }

    public void setSingleHemaiForward(Integer singleHemaiForward) {
        this.singleHemaiForward = singleHemaiForward;
    }

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Integer getSaleEnabled() {
		return saleEnabled;
	}

	public void setSaleEnabled(Integer saleEnabled) {
		this.saleEnabled = saleEnabled;
	}

	public Integer getB2bEndSale() {
		return b2bEndSale;
	}

	public void setB2bEndSale(Integer b2bEndSale) {
		this.b2bEndSale = b2bEndSale;
	}

	public Integer getB2bForward() {
		return b2bForward;
	}

	public void setB2bForward(Integer b2bForward) {
		this.b2bForward = b2bForward;
	}

	public Integer getHemaiEndSale() {
		return hemaiEndSale;
	}

	public void setHemaiEndSale(Integer hemaiEndSale) {
		this.hemaiEndSale = hemaiEndSale;
	}

	public Integer getChaseEndSale() {
		return chaseEndSale;
	}

	public void setChaseEndSale(Integer chaseEndSale) {
		this.chaseEndSale = chaseEndSale;
	}


	public Integer getEndForward() {
		return endForward;
	}

	public void setEndForward(Integer endForward) {
		this.endForward = endForward;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LottypeConfig that = (LottypeConfig) o;

		if (lotteryType != null ? !lotteryType.equals(that.lotteryType) : that.lotteryType != null) return false;
		if (state != null ? !state.equals(that.state) : that.state != null) return false;
		if (saleEnabled != null ? !saleEnabled.equals(that.saleEnabled) : that.saleEnabled != null) return false;
		if (onprize != null ? !onprize.equals(that.onprize) : that.onprize != null) return false;
		if (autoencash != null ? !autoencash.equals(that.autoencash) : that.autoencash != null) return false;
		if (lotcenterisvalid != null ? !lotcenterisvalid.equals(that.lotcenterisvalid) : that.lotcenterisvalid != null)
			return false;
		if (prePhaseNum != null ? !prePhaseNum.equals(that.prePhaseNum) : that.prePhaseNum != null) return false;
		if (heimaiForward != null ? !heimaiForward.equals(that.heimaiForward) : that.heimaiForward != null)
			return false;
		if (uploadForward != null ? !uploadForward.equals(that.uploadForward) : that.uploadForward != null)
			return false;
		if (singleHemaiForward != null ? !singleHemaiForward.equals(that.singleHemaiForward) : that.singleHemaiForward != null)
			return false;
		if (b2bForward != null ? !b2bForward.equals(that.b2bForward) : that.b2bForward != null) return false;
		if (webEndSale != null ? !webEndSale.equals(that.webEndSale) : that.webEndSale != null) return false;
		if (iosEndSale != null ? !iosEndSale.equals(that.iosEndSale) : that.iosEndSale != null) return false;
		if (androidEendSale != null ? !androidEendSale.equals(that.androidEendSale) : that.androidEendSale != null)
			return false;
		if (b2bEndSale != null ? !b2bEndSale.equals(that.b2bEndSale) : that.b2bEndSale != null) return false;
		if (isAddPrize != null ? !isAddPrize.equals(that.isAddPrize) : that.isAddPrize != null) return false;
		if (hemaiEndSale != null ? !hemaiEndSale.equals(that.hemaiEndSale) : that.hemaiEndSale != null) return false;
		if (chaseEndSale != null ? !chaseEndSale.equals(that.chaseEndSale) : that.chaseEndSale != null) return false;
		if (endSaleTime != null ? !endSaleTime.equals(that.endSaleTime) : that.endSaleTime != null) return false;
		return !(phase != null ? !phase.equals(that.phase) : that.phase != null);

	}

	@Override
	public int hashCode() {
		int result = lotteryType != null ? lotteryType.hashCode() : 0;
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (saleEnabled != null ? saleEnabled.hashCode() : 0);
		result = 31 * result + (onprize != null ? onprize.hashCode() : 0);
		result = 31 * result + (autoencash != null ? autoencash.hashCode() : 0);
		result = 31 * result + (lotcenterisvalid != null ? lotcenterisvalid.hashCode() : 0);
		result = 31 * result + (prePhaseNum != null ? prePhaseNum.hashCode() : 0);
		result = 31 * result + (heimaiForward != null ? heimaiForward.hashCode() : 0);
		result = 31 * result + (uploadForward != null ? uploadForward.hashCode() : 0);
		result = 31 * result + (singleHemaiForward != null ? singleHemaiForward.hashCode() : 0);
		result = 31 * result + (b2bForward != null ? b2bForward.hashCode() : 0);
		result = 31 * result + (webEndSale != null ? webEndSale.hashCode() : 0);
		result = 31 * result + (iosEndSale != null ? iosEndSale.hashCode() : 0);
		result = 31 * result + (androidEendSale != null ? androidEendSale.hashCode() : 0);
		result = 31 * result + (b2bEndSale != null ? b2bEndSale.hashCode() : 0);
		result = 31 * result + (isAddPrize != null ? isAddPrize.hashCode() : 0);
		result = 31 * result + (hemaiEndSale != null ? hemaiEndSale.hashCode() : 0);
		result = 31 * result + (chaseEndSale != null ? chaseEndSale.hashCode() : 0);
		result = 31 * result + (endSaleTime != null ? endSaleTime.hashCode() : 0);
		result = 31 * result + (phase != null ? phase.hashCode() : 0);
		return result;
	}
}
