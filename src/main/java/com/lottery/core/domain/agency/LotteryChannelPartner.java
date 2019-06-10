package com.lottery.core.domain.agency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by fengqinyun on 16/5/20.
 * 合作渠道表
 */
@Table(name="lottery_channel_partner")
@Entity
public class LotteryChannelPartner implements Serializable{

    @Id
    private Long id;
    @Column(name="agencyno")
    private String agencyno;//渠道编号
    @Column(name="agencyuser")
    private String agencyuser;//渠道用户(用户名或手机号)
    @Column(name="userno")
    private String userno;//平台用户userno
    @Column(name = "agencytoken")
    private String agencytoken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencyno() {
        return agencyno;
    }

    public void setAgencyno(String agencyno) {
        this.agencyno = agencyno;
    }

    public String getAgencyuser() {
        return agencyuser;
    }

    public void setAgencyuser(String agencyuser) {
        this.agencyuser = agencyuser;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getAgencytoken() {
        return agencytoken;
    }

    public void setAgencytoken(String agencytoken) {
        this.agencytoken = agencytoken;
    }
}
