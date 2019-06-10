package com.lottery.common.contains.pay;

/**
 * Created by fengqinyun on 2017/3/27.
 */
public enum RedPackageStatus {
    success(1,"成功"),
    fail(2,"失败"),
    no_receive(3,"未领取"),
    all(0,"全部");
    public int value;
    public String name;
    RedPackageStatus(int value,String name){
        this.value=value;
        this.name=name;
    }
}
