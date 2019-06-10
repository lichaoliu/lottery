package com.lottery.ticket.phase.execute;

import com.lottery.common.contains.EnabledStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.core.cache.model.LottypeConfigCacheModel;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.ticket.phase.worker.IPhaseCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15-7-24.
 * 彩期不足创建
 */
@Service
public class PhaseNotEnoughCreateWorker {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private LotteryPhaseService lotteryPhaseService;
    @Autowired
    private LottypeConfigCacheModel lottypeConfigCacheModel;
    @Resource(name="lotteryPhaseCreateMap")
    protected Map<LotteryType,IPhaseCreate> phaseCreateMap;

    public  void create(LotteryType lotteryType){
        LottypeConfig lottypeConfig = null;
        try {
            lottypeConfig = lottypeConfigCacheModel.get(lotteryType.value);
        }catch (Exception e) {

        }

        try {
            if (lottypeConfig == null||lottypeConfig.getSaleEnabled()==null) {
                logger.info("彩种:{}彩种配置为空", lotteryType);
                return;
            }

            if(lottypeConfig.getSaleEnabled()== EnabledStatus.disabled.value){
                return;
            }
            Integer phaseAmt = lottypeConfig.getPrePhaseNum();
            if (phaseAmt != null) {
                Long phaseCount = lotteryPhaseService.getCountByLotteryTypeAndStatus(lotteryType.value, PhaseStatus.unopen.getValue());
                if (phaseCount!=null&&phaseCount.intValue() < phaseAmt) {
                    Integer creatAmt = phaseAmt - phaseCount.intValue();
                    if (creatAmt > 0) {
                        createPhase(lotteryType, creatAmt);
                    }
                }
                if (phaseCount==null||phaseCount.intValue() == 0) {
                    createPhase(lotteryType, phaseAmt);
                }
            }
        } catch (Exception e) {
            logger.error("创建彩种:{}新期出错", lotteryType, e);
        }

    }

    protected void createPhase(LotteryType lotteryType,int max){
        IPhaseCreate phaseCreate=phaseCreateMap.get(lotteryType);
        if(phaseCreate==null){
            logger.error("彩种{}创建新期方法不存在",lotteryType);
            return;
        }
        List<LotteryPhase> list=lotteryPhaseService.getByLotteryType(lotteryType.getValue(), 1);
        if(list.size() == 0){
            logger.error("彩种:{}一期都没有请录入基础数据",lotteryType);
            return;
        }
        LotteryPhase lastLotteryPhase = list.get(0);
        for(int i = 0; i < max; i++) {
            LotteryPhase newLotteryPhase =phaseCreate.creatNextPhase(lastLotteryPhase);
            if (newLotteryPhase.getPhase().equals(lastLotteryPhase.getPhase())){
                logger.error("彩种:{},新建期号和最后一期相同,略过,phaseNo={}",lastLotteryPhase.getLotteryType(),lastLotteryPhase.getPhase());
                continue;
            }
            lotteryPhaseService.insert(newLotteryPhase);
            lastLotteryPhase=newLotteryPhase;
        }
    }
}
