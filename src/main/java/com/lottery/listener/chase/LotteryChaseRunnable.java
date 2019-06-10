package com.lottery.listener.chase;

import com.lottery.common.PageBean;
import com.lottery.common.contains.ChaseStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.LotteryChase;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.LotteryChaseHandler;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryChaseService;
import com.lottery.core.service.LotteryPhaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by fengqinyun on 16/10/2.
 */
public class LotteryChaseRunnable extends AbstractThreadRunnable {
    private long interval = 90000l; // 每次处理间隔(毫秒)
    @Autowired
    private LotteryPhaseService lotteryPhaseService;
    @Autowired
    private LotteryChaseHandler chaseHandler;
    @Autowired
    private LotteryChaseService lotteryChaseService;
    @Autowired
    private LotteryChaseBuyService lotteryChaseBuyService;


    private int max=10;
    @Override
    protected void executeRun() {
        while (running){
            try{

                for(LotteryType lotteryType:LotteryType.get()){

                    PageBean<LotteryChase> pageBean = new PageBean<LotteryChase>();
                    pageBean.setMaxResult(max);
                    pageBean.setTotalFlag(false);
                    int page=1;
                    while (true){
                        pageBean.setPageIndex(page);
                        List<LotteryChase> chaseList=lotteryChaseService.get(lotteryType.value, ChaseStatus.NORMAL.value,pageBean);
                        if(chaseList!=null||chaseList.size()>0){
                             this.process(chaseList);
                            if(chaseList.size()<max){
                                break;
                            }
                        }else {
                            break;
                        }
                        page ++;

                    }

                }

            }catch (Exception e){
             logger.error(e.getMessage(),e);
            }
            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error("等待出错", e);
                }
            }

        }

    }



    private void process(List<LotteryChase> lotteryChaseList){
        for (LotteryChase lotteryChase:lotteryChaseList){
            String chaseId=lotteryChase.getId();
             try {
                 List<LotteryChaseBuy> lotteryChaseBuyList=lotteryChaseBuyService.getByChaseIdAndStatus(chaseId, ChaseBuyStatus.chasebuy_no.value);
                 if(lotteryChaseBuyList!=null){
                     for (LotteryChaseBuy lotteryChaseBuy:lotteryChaseBuyList){
                         try{
                             if (lotteryChaseBuy.getPhaseStartTime().before(new Date())&&lotteryChaseBuy.getPhaseEndTime().after(new Date())){
                                 if (lotteryChase.getChaseType()==ChaseType.normal_end.value){
                                     chaseHandler.process(lotteryChaseBuy.getLotteryType(),lotteryChaseBuy.getPhase(),ChaseType.normal_end.value);
                                 }else {
                                     String lastPhase=lotteryChase.getLastPhase();
                                     LotteryPhase lotteryPhase=lotteryPhaseService.getPhase(lotteryChase.getLotteryType(),lastPhase);
                                     if(lotteryPhase!=null){
                                         if(lotteryPhase.getPhaseStatus()==PhaseStatus.prize_end.value&&lotteryPhase.getEndTicketTime().after(new Date())){
                                             chaseHandler.process(lotteryChaseBuy.getLotteryType(),lotteryChaseBuy.getPhase(),ChaseType.prize_end.value);
                                             chaseHandler.process(lotteryChaseBuy.getLotteryType(),lotteryChaseBuy.getPhase(),ChaseType.total_prize_end.value);
                                         }
                                     }else{
                                         chaseHandler.canelChaseBuy(lotteryChaseBuy.getId(),"无此新期");
                                     }
                                 }
                             }else if(lotteryChaseBuy.getPhaseEndTime().before(new Date()))  {
                                 chaseHandler.canelChaseBuy(lotteryChaseBuy.getId(),"已过期");
                             }
                         }catch (Exception e){
                             logger.error("chaseBuy追号出错",e);
                             chaseHandler.canelChaseBuy(lotteryChaseBuy.getId(),"追号出错");
                         }

                     }
                 }
             }catch (Exception e){
               logger.error("处理追号失败",e);
               logger.error("chaseId={}",chaseId);
             }


        }
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
