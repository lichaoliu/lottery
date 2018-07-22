package com.lottery.listener.check;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class OrderDeductRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.betSuccessDeduct.value+"?concurrentConsumers=5&asyncConsumer=true").to("bean:orderDeductListener?method=deduct").setId("订单成功扣款");
        from(QueueName.betFailuerUnfreeze.value+"?concurrentConsumers=1").to("bean:orderDeductListener?method=unfreeze").setId("订单失败解冻");
        from(QueueName.betRefund.value+"?concurrentConsumers=1").to("bean:orderDeductListener?method=refund").setId("退款队列");

    }
}
