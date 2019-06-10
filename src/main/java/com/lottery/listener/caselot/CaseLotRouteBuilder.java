package com.lottery.listener.caselot;

import com.lottery.common.contains.QueueName;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/3/17.
 */
public class CaseLotRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.hemaiFailuerUnfreeze.value).to("bean:caseLotHandler?method=onhemaiFailuerUnfreeze").routeId("合买订单出票失败");
        from("jms:queue:VirtualTopicConsumers.caselotprize.prizeopen-queue").to("bean:caseLotHandler?method=ineffectiveAchievement").routeId("开奖计算未成功合买战绩");

    }
}
