package com.lottery.common.contains;

/**
 * Created by fengqinyun on 15/11/18.
 */
public enum AgencyExchanged {
    exchange_yes(1,"已兑奖"),
    exchange_no(2,"未兑奖"),
    exchange_check(3,"奖金核对中"),
    exchange_error(4,"奖金有差异"),
    all(0,"全部")
    ;
    public int value;
    public String name;
    AgencyExchanged(int value,String name){
        this.name=name;
        this.value=value;
    }
}
