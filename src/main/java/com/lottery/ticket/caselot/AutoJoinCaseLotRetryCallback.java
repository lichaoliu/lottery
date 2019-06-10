package com.lottery.ticket.caselot;

import com.lottery.common.util.StringUtil;
import com.lottery.core.handler.CaseLotHandler;
import com.lottery.retrymodel.ApiRetryCallback;

/**
 * Created by fengqinyun on 15/4/20.
 */
public class AutoJoinCaseLotRetryCallback extends ApiRetryCallback<Object> {


    protected  String caseLotId;
    protected CaseLotHandler caseLotHandler;
    @Override
    protected Boolean execute() throws Exception{

        if(StringUtil.isEmpty(caseLotId)||caseLotHandler==null){
            throw  new Exception("参数不全");
        }
        caseLotHandler.autoJoinCaselot(caseLotId);
        return  true;
    }

    public String getCaseLotId() {
        return caseLotId;
    }

    public void setCaseLotId(String caseLotId) {
        this.caseLotId = caseLotId;
    }

    public CaseLotHandler getCaseLotHandler() {
        return caseLotHandler;
    }

    public void setCaseLotHandler(CaseLotHandler caseLotHandler) {
        this.caseLotHandler = caseLotHandler;
    }
}
