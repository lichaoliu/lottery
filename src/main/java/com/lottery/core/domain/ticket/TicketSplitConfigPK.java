package com.lottery.core.domain.ticket;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by fengqinyun on 2016/12/25.
 */
public class TicketSplitConfigPK implements Serializable{

    @Column(name="lottery_type")
    private Integer lotteryType;//彩种
    @Column(name="play_type")
    private Integer playType;//玩法
    @Column(name="split_type")
    private Integer splitType;//拆票方式

    public TicketSplitConfigPK(int lotteryType,int playType,int splitType){
        this.lotteryType=lotteryType;
        this.playType=playType;
        this.splitType=splitType;
    }
    public TicketSplitConfigPK(){

    }
    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Integer getSplitType() {
        return splitType;
    }

    public void setSplitType(Integer splitType) {
        this.splitType = splitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketSplitConfigPK that = (TicketSplitConfigPK) o;

        if (lotteryType != null ? !lotteryType.equals(that.lotteryType) : that.lotteryType != null) return false;
        if (playType != null ? !playType.equals(that.playType) : that.playType != null) return false;
        return splitType != null ? splitType.equals(that.splitType) : that.splitType == null;

    }

    @Override
    public int hashCode() {
        int result = lotteryType != null ? lotteryType.hashCode() : 0;
        result = 31 * result + (playType != null ? playType.hashCode() : 0);
        result = 31 * result + (splitType != null ? splitType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketSplitConfigPK{" +
                "lotteryType=" + lotteryType +
                ", playType=" + playType +
                ", splitType=" + splitType +
                '}';
    }
}
