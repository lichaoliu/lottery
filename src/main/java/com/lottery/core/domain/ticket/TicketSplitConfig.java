package com.lottery.core.domain.ticket;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by fengqinyun on 2016/12/25.
 */
@Table(name="ticket_split_config")
@Entity
public class TicketSplitConfig implements Serializable{
    @EmbeddedId
    private TicketSplitConfigPK id;
    @Column(name="bet_amount")
    private Integer betmount;//投注金额(单位分)
    @Column(name="prize_amount")
    private Integer prizeAmount;//中奖金额(单位分)
    @Column(name="bet_num")
    private Integer betNum;//单式投注注数

    public TicketSplitConfig(int lotteryType,int playType,int splitType){
        TicketSplitConfigPK id=new TicketSplitConfigPK(lotteryType,playType,splitType);
        this.id=id;
    }
    public TicketSplitConfig(){}

    public TicketSplitConfigPK getId() {
        return id;
    }

    public void setId(TicketSplitConfigPK id) {
        this.id = id;
    }

    public Integer getBetmount() {
        return betmount;
    }

    public void setBetmount(Integer betmount) {
        this.betmount = betmount;
    }

    public Integer getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(Integer prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public Integer getBetNum() {
        return betNum;
    }

    public void setBetNum(Integer betNum) {
        this.betNum = betNum;
    }
}
