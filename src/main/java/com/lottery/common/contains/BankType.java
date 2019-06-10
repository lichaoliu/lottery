package com.lottery.common.contains;

/**
 * Created by fengqinyun on 15/5/11.
 * 银行类型
 */
public enum BankType {
    all("0","全部")
    ;
    public String value;
    public String name;
    BankType(String value,String name){
        this.name=name;
        this.value=value;
    }
}
