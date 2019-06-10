package com.lottery.common.contains.ticket;

/**
 * Created by fengqinyun on 2016/12/25.
 */
public enum SplitType {
    bet_num_n(1,"单式N注"),//N<=5
    bet_num_n_1(2,"N串M拆成N串1"),
    amount(3,"按票金额拆"),
    prize(4,"按中奖金额拆"),

    all(0,"全部");
    public int value;
    public String name;
    SplitType(int value,String name){
        this.name=name;
        this.value=value;
    }
}
