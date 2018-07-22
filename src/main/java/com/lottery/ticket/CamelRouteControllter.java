package com.lottery.ticket;

import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.service.SystemService;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 16/3/16.
 */
public class CamelRouteControllter {
    @Autowired
    SystemService systemService;
    public void start(){
        Map<String, CamelContext> map = systemService.getMap(CamelContext.class);
        for (Map.Entry<String, CamelContext> entry : map.entrySet()){
            String key=entry.getKey();
            CamelContext value=entry.getValue();
             if(!value.isStartingRoutes()){
                 try {
                     value.start();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
        }
    }
    public void stop(){
        Map<String, CamelContext> map = systemService.getMap(CamelContext.class);
        for (Map.Entry<String, CamelContext> entry : map.entrySet()){
            String key=entry.getKey();
            CamelContext value=entry.getValue();
            if(value.isStartingRoutes()){
                try {
                    value.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
