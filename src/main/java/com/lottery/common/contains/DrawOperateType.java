package com.lottery.common.contains;

/**
 * Created by fengqinyun on 15/5/11.
 * 提现操作类型
 */
public enum  DrawOperateType {
    zfb_bankdraw_hand(1,"支付宝银行卡提现手动"),
    zfb_bankdraw_auto(2,"支付宝银行卡提现自动"),
    zfb_draw(3,"支付宝账号提现"),
    elink_draw(4,"银联提现"),
    all(0,"全部");
    public  int value;
    public  String name;
    DrawOperateType(int value,String name){
        this.name=name;
        this.value=value;
    }
}
