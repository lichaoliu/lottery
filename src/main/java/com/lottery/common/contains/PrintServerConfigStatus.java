package com.lottery.common.contains;

/**
 * Created by fengqinyun on 16/11/9.
 */
public enum PrintServerConfigStatus {

    print(1,"打票"),
    prize(2,"兑奖"),
    report(3,"报表"),
    stop(4,"停止"),
    free(5,"空闲"),
    all(0,"全部");
    public int value;
    public String name;
    PrintServerConfigStatus(int value, String name){
        this.name=name;
        this.value=value;
    }
    public static PrintServerConfigStatus get(int value){
        PrintServerConfigStatus[] allType= PrintServerConfigStatus.values();
        for(PrintServerConfigStatus type:allType){
            if(type.value==value){
                return type;
            }
        }
        return null;
    }


}
