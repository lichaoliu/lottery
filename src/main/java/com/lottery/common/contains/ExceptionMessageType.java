package com.lottery.common.contains;

/**
 * Created by fengqinyun on 16/9/8.
 */
public enum ExceptionMessageType {
    bet_faile(1,"投注失败"),
    bet_limit(2,"投注限号"),
    http_error(3,"http错误"),
    all(0,"全部");
    public int value;
    public String name;
    ExceptionMessageType(int value,String name){
        this.value=value;
        this.name=name;
    }

}
