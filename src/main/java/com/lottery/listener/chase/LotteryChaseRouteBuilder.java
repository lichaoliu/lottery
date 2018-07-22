package com.lottery.listener.chase;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class LotteryChaseRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:VirtualTopicConsumers.chase.open-phase?concurrentConsumers=1").to("bean:lotteryChaseListener?method=processChase").routeId("新期订单追号");
        //from(QueueName.continueChase.value+"?concurrentConsumers=1").to("bean:lotteryChaseListener?method=continueChase").routeId("继续订单追号");
        from("jms:queue:VirtualTopicConsumers.chase.encash-end?concurrentConsumers=2").to("bean:lotteryChaseListener?method=drawPrizeCustomer").routeId("中奖停止追号");
       // from(QueueName.chasebet.value+"?concurrentConsumers=1").to("bean:lotteryChaseListener?method=chaseBet").routeId("具体彩种期追号");
    }
}
