package com.lottery.common.contains.lottery;

/**
 * Created by fengqinyun on 16/8/25.
 * 限号类型
 */
public enum LimitNumberType {

    paytype_limit(1,"玩法限号"),
    group_limit(2,"组合限号"),
    all(0,"全部");
    public int value;
    public String name;
    LimitNumberType(int value,String name){
        this.name=name;
        this.value=value;
    }

}
