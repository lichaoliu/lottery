package com.lottery.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 16/10/1.
 */
@Service
public class OrderProcessHandler {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    protected UserAccountHandler accountHandler;
    @Autowired
    protected OrderSplitHandler orderSplitHandler;

    @Autowired
    protected OrderCheckerHandler orderCheckerHandler;
    public void freezeAndSplit(String orderId){
        try {
            accountHandler.freeze(orderId);
            orderSplitHandler.handler(orderId);
        }catch (Exception e){
            logger.error("冻结拆票失败orderid={}",orderId);
            logger.error(e.getMessage(),e);
        }
    }

    public void check(String orderId){
        try {
             orderCheckerHandler.check(orderId);
        }catch (Exception e){
            logger.error("检票失败orderid={}",orderId);
            logger.error(e.getMessage(),e);
        }
    }


    public void split(String orderId){
        try {
            orderSplitHandler.handler(orderId);
        }catch (Exception e){
            logger.error("拆票失败orderid={}",orderId);
            logger.error(e.getMessage(),e);
        }
    }


}
