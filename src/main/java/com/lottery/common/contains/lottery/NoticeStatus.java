package com.lottery.common.contains.lottery;

/**
 * Created by fengqinyun on 15/6/3.
 * 出
 */
public enum NoticeStatus {
    no_notice(1,"未通知"),
    success(2,"通知成功"),
    processing(3,"通知中"),
    failur(4,"通知失败"),
    all(0,"全部");
    public int value;
    public String name;
    NoticeStatus(int value,String name){
        this.value=value;
        this.name=name;
    }
}
