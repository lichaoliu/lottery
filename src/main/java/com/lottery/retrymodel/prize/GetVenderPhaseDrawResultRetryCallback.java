package com.lottery.retrymodel.prize;

import com.lottery.draw.IVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.retrymodel.ApiRetryCallback;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fengqinyun on 2017/3/5.
 * 抓取开奖号码
 */
public class GetVenderPhaseDrawResultRetryCallback extends ApiRetryCallback<Object> {
     private Integer lotteryType;
     private String phase;
     private IVenderPhaseDrawWorker venderPhaseDrawWorker;
    private AtomicBoolean hasFetched;

    private AtomicInteger fetchCounter;
    @Override
    protected LotteryDraw execute() throws Exception{
        if (lotteryType==null||phase==null||venderPhaseDrawWorker==null){
            throw new Exception("缺少必要的参数");
        }
        if(hasFetched!=null){
            if(hasFetched.get()){
                logger.error("彩种:{},期号:{}已经从别处抓取开奖号码",lotteryType,phase);
                return null;
            }
        }


        LotteryDraw lotteryDraw=venderPhaseDrawWorker.getPhaseDraw(lotteryType,phase);
        if (lotteryDraw!=null&&hasFetched != null) {
            hasFetched.compareAndSet(false, true);
        }
        return lotteryDraw;
    }


    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public IVenderPhaseDrawWorker getVenderPhaseDrawWorker() {
        return venderPhaseDrawWorker;
    }

    public void setVenderPhaseDrawWorker(IVenderPhaseDrawWorker venderPhaseDrawWorker) {
        this.venderPhaseDrawWorker = venderPhaseDrawWorker;
    }

    public AtomicBoolean getHasFetched() {
        return hasFetched;
    }

    public void setHasFetched(AtomicBoolean hasFetched) {
        this.hasFetched = hasFetched;
    }

    public AtomicInteger getFetchCounter() {
        return fetchCounter;
    }

    public void setFetchCounter(AtomicInteger fetchCounter) {
        this.fetchCounter = fetchCounter;
    }
}
