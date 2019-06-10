package com.lottery.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fengqinyun on 15/8/22.
 */
public class LotteryLog {

    public static Logger getLotterWarnLog(){
        return LoggerFactory.getLogger("lottery-warn");
    }
}
