package com.lottery.listener.check;

import com.lottery.core.handler.OrderCheckerHandler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 16/6/15.
 */
@Service
public class OrderCheckListener {
    @Autowired
    private OrderCheckerHandler checkerHandler;
    public void process(@Header("orderId")String orderId){

        checkerHandler.check(orderId);
    }
}
