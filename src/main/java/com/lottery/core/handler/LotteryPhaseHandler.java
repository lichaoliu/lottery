package com.lottery.core.handler;


import com.lottery.common.contains.EnabledStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.LottypeConfigStatus;
import com.lottery.common.contains.lottery.PhaseTimeStatus;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.cache.model.LotteryCurrentPhaseCacheModel;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.cache.model.LottypeConfigCacheModel;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.LottypeConfigService;
import com.lottery.ticket.phase.thread.IPhaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LotteryPhaseHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
    @Autowired
    protected LotteryCurrentPhaseCacheModel currentPhaseCacheModel;
    @Autowired
    protected LottypeConfigCacheModel lottypeConfigCacheModel;
    @Autowired
    protected LottypeConfigService lottypeConfigService;

    @Autowired
    private LotteryChaseBuyService lotteryChaseBuyService;



    @Autowired
    private LotteryPhaseService lotteryPhaseService;
    @Resource(name = "allPhaseHandlerBinder")
    private Map<LotteryType, IPhaseHandler> allPhaseHandlerBinder;
    /**
     * 计算时间偏移量
     */
    public Date getEndSaleForwardDate(Date date, Integer lotteryType) {
        Date returnDate = date;
        if (date == null) {
            logger.error("请求参数不能为空:时间{},彩种{}", new Object[]{date, lotteryType});
        }
        if (lotteryType == null) {
            lotteryType = LotteryType.ALL.value;
        }
        if (lotteryTicketConfigCacheModel != null && date != null && lotteryType != null) {
            try {
                LotteryTicketConfig lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
                if (lotteryTicketConfig != null) {
                    if (lotteryTicketConfig.getEndSaleForward() != null) {
                        long time = date.getTime() - lotteryTicketConfig.getEndSaleForward().longValue();
                        returnDate = new Date(time);
                    }
                }
            } catch (CacheNotFoundException e) {

            } catch (CacheUpdateException e) {

            } catch (Exception e) {
                logger.error("彩种:{},获取偏移量出错", lotteryType, e);
            }
        }
        return returnDate;
    }

    public Long getEndTicketTimeout(Integer lotteryType) {
        Long returnDate = 0l;
        if (lotteryType == null) {
            lotteryType = LotteryType.ALL.value;
        }
        if (lotteryTicketConfigCacheModel != null && lotteryType != null) {
            try {
                LotteryTicketConfig lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
                if (lotteryTicketConfig != null) {
                    if (lotteryTicketConfig.getEndTicketTimeout() != null) {
                        returnDate = lotteryTicketConfig.getEndTicketTimeout();
                    }
                }
            } catch (CacheNotFoundException e) {

            } catch (CacheUpdateException e) {

            } catch (Exception e) {
                logger.error("彩种:{},获取出票超时截止出错", lotteryType, e);
            }
        }
        return returnDate;
    }

    public LotteryPhase getCurrent(Integer lotteryType) {
        try {
            LotteryPhase lotteryPhase = currentPhaseCacheModel.get(LotteryType.getPhaseTypeValue(lotteryType));
            return lotteryPhase;
        } catch (CacheNotFoundException e) {

        } catch (CacheUpdateException e) {

        } catch (Exception e) {
            logger.error("彩种:{},获取当前期出错", lotteryType, e);
        }
        return null;
    }

    public List<LotteryPhase> getCurrentList() {
        List<LotteryPhase> phaselist = new ArrayList<LotteryPhase>();
        for (Integer lotteryType : LotteryType.getAllLotteryType()) {
            LotteryPhase phase = this.getCurrent(lotteryType);
            if (phase != null) {
                phaselist.add(phase);
            }
        }
        return phaselist;
    }

    /**
     * 获取lottypeconfig 配置
     *
     * @param lotteryType 彩种
     */
    public LottypeConfig getValidateConfig(int lotteryType) {
        LottypeConfig config = this.filterConfig(lotteryType);
        LotteryPhase phase = this.getCurrent(lotteryType);
        if (phase != null && config != null) {
            config.setLotteryType(lotteryType);
            config.setPhase(phase.getPhase());
            config.setEndSaleTime(phase.getEndTicketTime());
        }
        return config;
    }



    public LottypeConfig filterConfig(int lotteryType) {
        LottypeConfig allConfig = null;
        try {
            allConfig = lottypeConfigCacheModel.get(LotteryType.ALL.value);
        } catch (CacheNotFoundException e) {

        } catch (CacheUpdateException e) {

        }
        LottypeConfig lottypeConfig = null;
        try {
            lottypeConfig = lottypeConfigCacheModel.get(LotteryType.getSingleValue(lotteryType));
        } catch (CacheNotFoundException e) {

        } catch (CacheUpdateException e) {

        }
        if (lottypeConfig == null || lottypeConfig.getState() == null) {
            return allConfig;
        }


        if (allConfig == null || allConfig.getState() == null) {
            return lottypeConfig;
        }


        if (lottypeConfig.getHeimaiForward() == null) {
            lottypeConfig.setHeimaiForward(allConfig.getHeimaiForward());
        }

        if (lottypeConfig.getUploadForward() == null) {
            lottypeConfig.setUploadForward(allConfig.getUploadForward());
        }

        if (lottypeConfig.getSingleHemaiForward() == null) {
            lottypeConfig.setSingleHemaiForward(allConfig.getSingleHemaiForward());
        }

        if (lottypeConfig.getB2bForward() == null) {
            lottypeConfig.setB2bForward(allConfig.getB2bForward());
        }


        if (allConfig.getState() == LottypeConfigStatus.paused.value || allConfig.getState() == LottypeConfigStatus.no_used.value) {
            lottypeConfig.setState(LottypeConfigStatus.paused.value);
        }

        if (allConfig.getSaleEnabled() != null && allConfig.getSaleEnabled() == EnabledStatus.disabled.value) {
            lottypeConfig.setSaleEnabled(EnabledStatus.disabled.value);
        }

        if (allConfig.getIosEndSale() != null && allConfig.getIosEndSale() == YesNoStatus.yes.value) {
            lottypeConfig.setIosEndSale(YesNoStatus.yes.value);
        }

        if (allConfig.getWebEndSale() != null && allConfig.getWebEndSale() == YesNoStatus.yes.value) {
            lottypeConfig.setWebEndSale(YesNoStatus.yes.value);
        }

        if (allConfig.getAndroidEendSale() != null && allConfig.getAndroidEendSale() == YesNoStatus.yes.value) {
            lottypeConfig.setAndroidEendSale(YesNoStatus.yes.value);
        }
        if (allConfig.getB2bEndSale() != null && allConfig.getB2bEndSale() == YesNoStatus.yes.value) {
            lottypeConfig.setB2bEndSale(YesNoStatus.yes.value);
        }

        if (allConfig.getHemaiEndSale() != null && allConfig.getHemaiEndSale() == YesNoStatus.yes.value) {
            lottypeConfig.setHemaiEndSale(YesNoStatus.yes.value);
        }

        if (allConfig.getChaseEndSale() != null && allConfig.getChaseEndSale() == YesNoStatus.yes.value) {
            lottypeConfig.setChaseEndSale(YesNoStatus.yes.value);
        }

        return lottypeConfig;

    }


    public LotteryTicketConfig getLotteryTicketConfig(int lotteryType) {

        LotteryTicketConfig lotteryTicketConfig = null;
        try {
            lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.get(lotteryType));
        } catch (Exception e) {

        }

        if (lotteryTicketConfig == null) {
            try {
                lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
            } catch (Exception e) {

            }

        }

        return lotteryTicketConfig;
    }

    /**
     * 修改当前新期
     * @param lotteryType 彩种
     * @param phase 新期
     * @param startTime 开始时间
     * @param endTime 官方结束时间
     * @param  number 需要修改期的数量
     * */

    public List<LotteryPhase> updateCurrentPhase(int lotteryType,String phase,Date startTime,Date endTime, int number){
        try{
            LotteryPhase lotteryPhase=lotteryPhaseService.getCurrent(lotteryType);
            if(lotteryPhase==null||!phase.equals(lotteryPhase.getPhase())){//如果当前期不是，
                lotteryPhaseService.closeCurrent(lotteryType);
            }

            //更新新期
            lotteryPhaseService.savePhaseEndSaleTime(lotteryType, phase, startTime, endTime,endTime);
            IPhaseHandler phaseHandler = allPhaseHandlerBinder.get(LotteryType.getLotteryType(lotteryType));
            if (phaseHandler != null) {
                phaseHandler.executeReload();
            }

            LotteryPhase enablePhase=lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
            if(enablePhase==null){
                logger.error("彩种:{}，彩期:{}为空",lotteryType,phase);
                return null;
            }



            List<LotteryPhase> phaseList=new ArrayList<LotteryPhase>();
            phaseList.add(enablePhase);
            if(enablePhase.getPhase().endsWith("001")){//福彩一般第一期都是001,体彩有特殊情况再说
                return phaseList;
            }
            long off=endTime.getTime()-enablePhase.getEndSaleTime().getTime();
            List<LotteryPhase> nextPhase=lotteryPhaseService.getNextPhase(lotteryType, phase, number);
            for(LotteryPhase next:nextPhase){
                if(next.getPhase().endsWith("001")){
                    break;
                }
                addOffsetTimeToPhase(next, off);
                next.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
                phaseList.add(next);
                lotteryPhaseService.update(next);
            }
            return phaseList;
        }catch(Exception e){
            logger.error("修改新期出错",e);
            return null;
        }

    }


    /**
     * 彩种往后延期
     * @param lotteryType 彩种
     * @param seconds 往后顺延时间,单位:秒
     * @param number 往后顺延多少期
     * */
    public void updatePhaseTime(int lotteryType,int seconds,int number) throws Exception {
        LotteryPhase lotteryPhase=lotteryPhaseService.getCurrent(lotteryType);
        if(lotteryPhase==null){
            throw new Exception("当前期不存在");
        }
        long off=seconds*1000;

        addOffsetTimeToPhase(lotteryPhase,off);
        lotteryPhaseService.update(lotteryPhase);

        IPhaseHandler phaseHandler = allPhaseHandlerBinder.get(LotteryType.getLotteryType(lotteryType));
        if (phaseHandler != null) {
            phaseHandler.executeReload();
        }
        List<LotteryPhase> nextPhase=lotteryPhaseService.getNextPhase(lotteryType, lotteryPhase.getPhase(), number);
        for(LotteryPhase next:nextPhase){
            if(next.getPhase().endsWith("001")){
                break;
            }
            addOffsetTimeToPhase(next, off);
            lotteryChaseBuyService.updateLotteryChaseBuyPhaseTime(next.getLotteryType(),next.getPhase(),next.getStartSaleTime(),next.getEndSaleTime());
            lotteryPhaseService.update(next);
        }

    }




    public LotteryPhase get(int lotteryType,String phase){
    	return lotteryPhaseService.getByTypeAndPhase(lotteryType, phase);
    }

    



    /**
     * 计算时间偏移量
     * */
    protected void addOffsetTimeToPhase(LotteryPhase phase, long offset){
        phase.setUpdateTime(new Date());
        Calendar startSaleCalendar = Calendar.getInstance();
        startSaleCalendar.setTime(phase.getStartSaleTime());
        startSaleCalendar.add(Calendar.MILLISECOND, (int)offset);
        phase.setStartSaleTime(startSaleCalendar.getTime());

        Calendar endSaleCalendar = Calendar.getInstance();
        endSaleCalendar.setTime(phase.getEndSaleTime());
        endSaleCalendar.add(Calendar.MILLISECOND, (int)offset);
        phase.setEndSaleTime(endSaleCalendar.getTime());
        phase.setHemaiEndTime(endSaleCalendar.getTime());

        Calendar endTicketCalendar = Calendar.getInstance();
        endTicketCalendar.setTime(phase.getEndTicketTime());
        endTicketCalendar.add(Calendar.MILLISECOND, (int)offset);
        phase.setEndTicketTime(endTicketCalendar.getTime());

        Calendar drawCalendar = Calendar.getInstance();
        drawCalendar.setTime(phase.getDrawTime());
        drawCalendar.add(Calendar.MILLISECOND, (int)offset);
        phase.setDrawTime(drawCalendar.getTime());

        phase.setPhaseTimeStatus(PhaseTimeStatus.CORRECTION.value);
    }
}
