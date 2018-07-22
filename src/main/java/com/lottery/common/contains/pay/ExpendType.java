package com.lottery.common.contains.pay;

/**
 * Created by fengqinyun on 14-9-13.
 * 支出类型
 */
public enum ExpendType {
    cash_expend(1,"现金支出"),
    handsel_expend(2,"彩金支出"),
    mix_expend(3,"混合支出"),
    all(0,"全部")
    ;
    public int value;
    public String name;

    ExpendType(int value,String name){
        this.value=value;
        this.name=name;
    }
    public  static ExpendType get(int value){
        for(ExpendType expendType:ExpendType.values()){
            if(expendType.value==value){
                return  expendType;
            }
        }
        return  null;
    }
}
