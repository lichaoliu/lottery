package com.lottery.ticket.caselot.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.caselot.CaseLotState;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.service.LotteryCaseLotService;
import com.lottery.core.service.LottypeConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqinyun on 15/4/18.
 * 用户合买结束操作
 */
public class LotteryCaseLotEndCheckerRunnable extends AbstractThreadRunnable {
    @Autowired
    protected LotteryCaseLotService caseLotService;
    private long interval = 30000;
    protected  int max=20;
    @Autowired
    protected LottypeConfigService lottypeConfigService;

    protected  static List<Integer> statesList=new ArrayList<Integer>();
    static {
        statesList.add(CaseLotState.alreadyBet.value);
        statesList.add(CaseLotState.processing.value);
        statesList.add(CaseLotState.finished.value);
    }
    @Override
    protected void executeRun() {
        while (running){
            try{
                for (int state:statesList){
                   process(max,state);
                }

            }catch (Exception e){
               logger.error("合买回收出错",e);
            }
            synchronized (this){
                try {
                    this.wait(interval);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(),e);
                }
            }

        }



    }

    protected  void process(int max,int status){
        while (true){

            List<LotteryCaseLot> caseLotList=caseLotService.getByStatus(max, status);
            if(caseLotList!=null&&caseLotList.size()>0){
                try{
                    for (LotteryCaseLot caseLot:caseLotList){
                        endCaseLot(caseLot);
                    }
                }catch (Exception e){
                    logger.error("执行合买回收出错",e);
                    break;
                }

                if(caseLotList.size()<max){
                    break;
                }
            }else{
                break;
            }
        }


    }

    protected  void endCaseLot(LotteryCaseLot caseLot){
        if (caseLot.getState()== CaseLotState.finished.value){
            caseLotService.endCaseLot(caseLot.getId());
        }
        if(caseLot.getState()==CaseLotState.alreadyBet.value){
            if(caseLot.getEndTime().getTime()-System.currentTimeMillis()<0){
                caseLotService.endCaseLot(caseLot.getId());
            }
        }
        if(caseLot.getState()==CaseLotState.processing.value){
            long timeout=caseLot.getEndTime().getTime()-getForWard(caseLot.getLotteryType());
            if(timeout-System.currentTimeMillis()<0){
                caseLotService.syscancelCaslot(caseLot.getId());
            }
        }
    }

    protected  long getForWard(int lotteryType){
        LottypeConfig lottypeConfig=lottypeConfigService.get(LotteryType.getSingleValue(lotteryType));
        if(lottypeConfig!=null&&lottypeConfig.getHeimaiForward()!=null){
            return  lottypeConfig.getHeimaiForward()*60*1000;
        }
        lottypeConfig=lottypeConfigService.get(LotteryType.ALL.value);
        if(lottypeConfig!=null&&lottypeConfig.getHeimaiForward()!=null){
            return  lottypeConfig.getHeimaiForward()*60*1000;
        }
        return 0;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
