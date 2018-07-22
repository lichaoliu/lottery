package com.lottery.common.contains.lottery;

/**
 * Created by fengqinyun on 14-3-22.
 *
 * 追号类型
 */
public enum ChaseType {
  
    prize_end(1,"中奖截止"),
    total_prize_end(2,"达到中奖金额截止"),
    normal_end(3,"正常截止"),
    all(0,"全部");
    public  int value;
    public  String name;
     ChaseType(int value,String name){
        this.value=value;
        this.name=name;
    }
    @Override
    public String toString() {
      return "[value="+value+",name="+name+"]";
    }
}
