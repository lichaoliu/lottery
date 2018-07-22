package com.lottery.core.handler;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.core.domain.SystemExceptionMessage;
import com.lottery.core.service.SystemExcepionMessageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by fengqinyun on 16/7/9.
 */
@Service
public class SystemExceptionMessageHandler {
    @Autowired
    private SystemExcepionMessageService systemExcepionMessageService;

    private final Logger logger= LoggerFactory.getLogger(getClass());

    public   void saveMessage(final String message){

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SystemExceptionMessage systemExcepionMessage=new SystemExceptionMessage();
                    systemExcepionMessage.setMessage(message);
                    systemExcepionMessage.setCreateTime(new Date());
                    systemExcepionMessage.setIsRead(YesNoStatus.no.value);
                    systemExcepionMessageService.save(systemExcepionMessage);
                }catch (Exception e){
                    logger.error("保存出错",e);
                }
            }
        });
        thread.start();
    }



}
