package com.lottery.common.contains;

/**
 * Created by fengqinyun on 15/8/30.
 * 算奖方式
 */
public enum OrderPrizeType {
    orderPrize(1,"订单算奖"),
    tikcetPrize(2,"按票算奖"),
    unused(3,"不可用"),
    all(0,"全部");
    public int value;
    public String name;
    OrderPrizeType(int value,String name){
        this.value=value;
        this.name=name;
    }
}
