package com.lottery.ticket.phase.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.util.lottery.DcUtil;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryTicketConfigService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.DcRace;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.DcRaceService;
import com.lottery.ticket.vender.IVenderInternalDcService;

/**
 * 单场新期，赛程处理
 */
public class AsyncDcPhaseRunnable extends AbstractTicketPhaseRunnable {
    protected IVenderInternalDcService internalDcService;
    @Autowired
    protected DcRaceService dcRaceService;

    @Autowired
    private LotteryTicketConfigService lotteryTicketConfigService;



    protected JSONArray getPhaseArray() {
        return internalDcService.getBjdcPhase(null);
    }

    public long getInvitl() {
        return 180000;
    }

    @Override
    protected void execute() {
        while (running) {
            try {
                JSONArray phaseArray = getPhaseArray();
                if (phaseArray != null) {
                    for (int i = 0; i < phaseArray.size(); i++) {
                        JSONObject object = phaseArray.getJSONObject(i);
                        phaseDowith(object);
                    }
                }
                recyclePhase();
            } catch (Exception e) {
                logger.error("执行单场新期出错", e);
            }


            try {
                synchronized (this) {
                    wait(getInvitl());
                }
            } catch (Exception e) {

            }
        }


    }

    /**
     * 处理获取的新期
     **/
    protected LotteryPhase phaseDowith(JSONObject object) {

        try {
            LotteryType lotteryType = LotteryType.getLotteryType(object.getInt("lotteryType"));

            String phaseNo = object.getString("phaseNo");
            Date startTime = CoreDateUtils.parseDate(object.getString("startTime"), CoreDateUtils.DATETIME);
            Date endTime = CoreDateUtils.parseDate(object.getString("endTime"), CoreDateUtils.DATETIME);
            Date drawTime = CoreDateUtils.parseDate(object.getString("drawTime"), CoreDateUtils.DATETIME);
            int phaseStatus = object.getInt("phaseStatus");
            LotteryPhase lotteryPhase = phaseService.getByTypeAndPhase(lotteryType.getValue(), phaseNo);
            if (lotteryPhase == null) {
            	lotteryPhase = new LotteryPhase();
                lotteryPhase.setCreateTime(new Date());
                lotteryPhase.setLotteryType(lotteryType.getValue());
                lotteryPhase.setPhase(phaseNo);
                phaseStatus(lotteryPhase, startTime, endTime, drawTime, phaseStatus);
                phaseService.save(lotteryPhase);
            } else {
                if ((lotteryPhase.getEndTicketTime().getTime() - endTime.getTime()) != 0) {
                    lotteryPhase.setEndSaleTime(endTime);
                    lotteryPhase.setEndTicketTime(endTime);
                    lotteryPhase.setHemaiEndTime(endTime);
                    lotteryPhase.setUpdateTime(new Date());
                    lotteryPhase.setPhaseStatus(phaseStatus);
                    phaseService.update(lotteryPhase);
                }

                if(lotteryPhase.getPhaseStatus()==PhaseStatus.close.value){
                    return lotteryPhase;
                }

                if(System.currentTimeMillis()-endTime.getTime()>=0){
                    if(lotteryPhase.getPhaseStatus()!=PhaseStatus.close.value){
                        closephase(lotteryType);
                    }
                    closeMatch(lotteryType,lotteryPhase.getPhase(),true);
                  return  lotteryPhase;
                }

            }
            match(lotteryType, phaseNo);
            closephase(lotteryType);
            return lotteryPhase;
        } catch (Exception e) {
            logger.error("操作单场新期出错", e);
        }
        return null;
    }

    /**
     * 期状态修改
     */
    protected boolean phaseStatus(LotteryPhase lotteryPhase, Date startTime, Date endTime, Date drawTime, int phaseStatus) {
        boolean flag = false;
        if (phaseStatus == PhaseStatus.open.getValue()) {
            lotteryPhase.setPhaseStatus(PhaseStatus.open.value);
            lotteryPhase.setForSale(YesNoStatus.yes.value);
            lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
            lotteryPhase.setTerminalStatus(TerminalStatus.enable.value);
            lotteryPhase.setSwitchTime(new Date());
            lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
            lotteryPhase.setForCurrent(YesNoStatus.yes.getValue());
            lotteryPhase.setStartSaleTime(startTime);
            lotteryPhase.setEndSaleTime(endTime);
            lotteryPhase.setEndTicketTime(endTime);
            lotteryPhase.setHemaiEndTime(endTime);
            lotteryPhase.setDrawTime(drawTime);
            flag = true;
        } else {
            lotteryPhase.setPhaseStatus(PhaseStatus.unopen.value);
            lotteryPhase.setForSale(YesNoStatus.no.value);
            lotteryPhase.setPhaseTimeStatus(PhaseTimeStatus.NO_CORRECTION.value);
            lotteryPhase.setTerminalStatus(TerminalStatus.close.value);
            lotteryPhase.setSwitchTime(new Date());
            lotteryPhase.setPhaseEncaseStatus(PhaseEncaseStatus.draw_not.getValue());
            lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
            lotteryPhase.setStartSaleTime(startTime);
            lotteryPhase.setDrawTime(drawTime);
            lotteryPhase.setEndSaleTime(endTime);
            lotteryPhase.setEndTicketTime(endTime);
            lotteryPhase.setHemaiEndTime(endTime);
            lotteryPhase.setDrawTime(drawTime);
            flag = true;
        }

        if (lotteryPhase.getEndSaleTime() != null) {
            if ((endTime.getTime() - lotteryPhase.getEndSaleTime().getTime()) != 0) {
                flag = true;
                lotteryPhase.setEndSaleTime(endTime);
                lotteryPhase.setEndTicketTime(endTime);
                lotteryPhase.setHemaiEndTime(endTime);
            }
        } else {
            lotteryPhase.setEndSaleTime(endTime);
            lotteryPhase.setEndTicketTime(endTime);
            lotteryPhase.setHemaiEndTime(endTime);
            flag = true;
        }

        return flag;
    }


    public void match(LotteryType lotteryType, String phase) {

        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("lotteryType", lotteryType.getValue() + "");
        mapParam.put("phaseNo", phase);
        JSONArray array = internalDcService.getBjdcSchedule(mapParam);
        if (array == null) {
            logger.error("lotteryType={},phase={}的返回是空",lotteryType,phase);
            return;
        }

        try {
            for (int i = 0; i < array.size(); i++) {
                JSONObject jo = array.getJSONObject(i);
              try{
                  Integer matchNum = jo.getInt("matchNum");
                  long endSaleForward = 0;
                  LotteryTicketConfig config = lotteryTicketConfigService.get(LotteryType.getPhaseType(lotteryType));
                  if (config != null && config.getEndSaleForward() != null) {
                      endSaleForward = config.getEndSaleForward();
                  }
                  Date endSaleTime = DcUtil.getEndSaleTimeByMatchDate(CoreDateUtils.parseDate(jo.getString("endSaleTime"), CoreDateUtils.DATETIME),endSaleForward);

                  Date matchDate = CoreDateUtils.parseDate(jo.getString("matchDate"), CoreDateUtils.DATETIME);
                  String score = jo.get("sr") == null ? "" : jo.getString("sr");
                  Integer status = jo.getInt("status");
                  String phaseNo = jo.getString("phase");
                  Integer dcType = jo.getInt("dcType");
                  String homeTeam = jo.getString("homeTeam");
                  String awayTeam = jo.getString("awayTeam");
                  String handicap = jo.getString("handicap");

                  String matchName = jo.getString("matchName");

                  Integer prizeStatus = jo.getInt("prizeStatus");
                  Long id=new Long(phaseNo + matchNum);
                  DcRace dcRace = dcRaceService.getByPhase(phase, matchNum, jo.getInt("dcType"));
                  if (dcRace == null) {

                      dcRace = new DcRace();
                      dcRace.setId(id);
                      dcRace.setCreateTime(new Date());
                      dcRace.setUpdateTime(new Date());
                      dcRace.setPhase(phaseNo);
                      dcRace.setDcType(dcType);
                      dcRace.setMatchNum(matchNum);
                      dcRace.setMatchDate(matchDate);
                      dcRace.setEndSaleTime(endSaleTime);
                      dcRace.setHomeTeam(homeTeam);
                      dcRace.setAwayTeam(awayTeam);
                      dcRace.setHandicap(handicap);
                      if(StringUtils.isNotEmpty(score)){
                          if(score.contains("$")){
                              dcRace.setHalfScore(score.split("\\$")[0]);
                              dcRace.setWholeScore( score.split("\\$")[1]);
                          }else {
                              dcRace.setWholeScore( score);
                          }
                      }else {
                          dcRace.setHalfScore("");
                          dcRace.setWholeScore("");
                      }
                      dcRace.setMatchName(matchName);
                      dcRace.setStatus(status);
                      dcRace.setPrizeStatus(prizeStatus);
                      dcRaceService.save(dcRace);
                  } else {
                      //2：未开售 1销售中 -1  已停售-2  已开奖3  已计算4  已返奖

                      if(dcRace.getStatus()==RaceStatus.CLOSE.value){
                          logger.error("比赛已经结束:{}",dcRace.toString());
                          continue;
                      }


                      if (status == RaceStatus.OPEN.value ||
                              status == RaceStatus.UNOPEN.value ||
                              status == RaceStatus.PAUSE.value) {
                          dcRace.setMatchName(matchName);
                          dcRace.setMatchNum(matchNum);
                          dcRace.setMatchDate(matchDate);
                          dcRace.setHomeTeam(homeTeam);
                          dcRace.setAwayTeam(awayTeam);
                          dcRace.setHandicap(handicap);
                          dcRace.setStatus(status);
                          dcRace.setEndSaleTime(endSaleTime);
                          dcRace.setUpdateTime(new Date());
                          dcRaceService.update(dcRace);
                          //关闭的 设置比分
                      } else if (status == RaceStatus.CLOSE.value) {
                          if (!StringUtil.isEmpty(score)) {
                              dcRace.setStatus(status);
//							dcRace.setPrizeStatus(jo.getInt("prizeStatus"));
                              if(dcType==DcType.normal.value()){
                                  String[] scores=score.split("\\$");
                                  if (scores.length==2){
                                      dcRace.setHalfScore(score.split("\\$")[0]);
                                      dcRace.setWholeScore(score.split("\\$")[1]);
                                      dcRaceService.merge(dcRace);
                                  }else{
                                      logger.error("比分有问题,matchNum={},原始比分是:{}",id,score);
                                  }
                              }
                              if (dcType==DcType.sfgg.value()){
                                  dcRace.setWholeScore(score);
                                  dcRaceService.merge(dcRace);
                              }

                          }
                      }

                  }
              }catch (Exception e){
                  logger.error("单场单个赛程处理出错", e);
                  logger.error("jo={}",jo);
              }
            }
        } catch (Exception e) {
            logger.error("单场赛程处理出错", e);
            logger.error("lotterytype={},phase={},array={}",lotteryType,phase, array);
        }

        closeMatch(lotteryType,phase,false);
    }
    /**
     * 将已过期的彩期关闭
     * */

    protected void closephase(LotteryType lotteryType){
        try{
            List<LotteryPhase> phaseList=phaseService.getByTypeAndForSale(lotteryType.value, YesNoStatus.yes.getValue());
            if(phaseList!=null){
                for (LotteryPhase lotteryPhase:phaseList){
                    this.closeMatch(lotteryType,lotteryPhase.getPhase(),false);
                    Date endTicketTime=lotteryPhase.getEndTicketTime();
                    if((new Date().getTime()-endTicketTime.getTime())>=0){
                        logger.error("北单:{},{}期已过期,进行关闭",new Object[]{lotteryType,lotteryPhase.getPhase()});
                        lotteryPhase.setForSale(YesNoStatus.no.getValue());
                        lotteryPhase.setTerminalStatus(TerminalStatus.close.value);
                        lotteryPhase.setPhaseStatus(PhaseStatus.close.getValue());
                        lotteryPhase.setForCurrent(YesNoStatus.no.getValue());
                        lotteryPhase.setUpdateTime(new Date());
                        phaseService.update(lotteryPhase);
                        this.closeMatch(lotteryType,lotteryPhase.getPhase(),true);
                    }
                }
            }
        }catch (Exception e){
            logger.error("关闭彩期出错",e);
        }
    }
    /**
     * @param lotteryType  彩种
     * @param phase  期号
     * @param closeAll boolean 是否全部关闭
     *
     *
     * */

    protected void closeMatch(LotteryType lotteryType,String phase,boolean closeAll) {
        try {
            int dctType=DcType.normal.value();

            if (lotteryType==LotteryType.DC_SFGG){
                dctType=DcType.sfgg.value();
            }
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(RaceStatus.UNOPEN.getValue());
            statusList.add(RaceStatus.OPEN.getValue());
            List<DcRace> list = dcRaceService.getByPhaseAndStatus(phase,dctType , statusList);
            if (list != null && list.size() > 0) {
                for (DcRace dcRace : list) {
                    if(closeAll){
                        logger.error("彩期:{}关闭,比赛全部关闭:{}关闭", phase,dcRace.toString());
                        dcRace.setStatus(RaceStatus.CLOSE.getValue());
                        dcRaceService.update(dcRace);
                    }else {
                        if ((new Date().getTime()) - (dcRace.getEndSaleTime().getTime()) >= 0) {
                            logger.error("单场:{}关闭", dcRace.toString());
                            dcRace.setStatus(RaceStatus.CLOSE.getValue());
                            dcRaceService.update(dcRace);
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("关闭单场过期赛程出错", e);
        }
    }

    public IVenderInternalDcService getInternalDcService() {
        return internalDcService;
    }

    public void setInternalDcService(IVenderInternalDcService internalDcService) {
        this.internalDcService = internalDcService;
    }


    public void proccessResult(String phaseNo, String sportballid) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("phaseNo", phaseNo);
        if (!StringUtil.isEmpty(sportballid)) {
            paramMap.put("sportballid", sportballid);
        }
        JSONArray ja = internalDcService.getBjdcDrawResult(paramMap);
        for (int i = 0; i < ja.size(); i++) {
            this.updateDcRace(phaseNo, ja.getJSONObject(i));
        }
    }

    private void updateDcRace(String phaseNo, JSONObject jo) {
        Integer matchNum = jo.getInt("matchNum");
        DcRace dr = dcRaceService.getByPhase(phaseNo, matchNum, jo.getInt("dcType"));
        if (dr == null) {
            return;
        }
        //结束或取消
        if (dr.getStatus().intValue() != RaceStatus.CLOSE.value) {
            return;
        }
        if (dr.getPrizeStatus().intValue() == PrizeStatus.not_open.value || dr.getPrizeStatus().intValue() == PrizeStatus.draw.value
                || dr.getPrizeStatus().intValue() == PrizeStatus.rewarded.value) {
            return;
        }
        String spSfp = jo.getString("spSfp");
        String sfpResult = jo.getString("sfpResult");
        String spSxds = jo.getString("spSxds");
        String sxdsResult = jo.getString("sxdsResult");
        String spJqs = jo.getString("spJqs");
        String jqsResult = jo.getString("jqsResult");
        String spBf = jo.getString("spBf");
        String sfsp = jo.getString("spsf");
        String sfResult = jo.getString("sfResult");
        String bfResult = jo.getString("bfResult");
        String spBcsfp = jo.getString("spBcsfp");
        String bcsfpResult = jo.getString("bcsfpResult");

        if (StringUtil.isEmpty(spSfp) || StringUtil.isEmpty(sfpResult) || StringUtil.isEmpty(spSxds) || StringUtil.isEmpty(sxdsResult)
                || StringUtil.isEmpty(spJqs) || StringUtil.isEmpty(jqsResult) || StringUtil.isEmpty(spBf)
                || StringUtil.isEmpty(bfResult) || bfResult.contains("延") || sfpResult.contains("延") || StringUtil.isEmpty(spBcsfp) || StringUtil.isEmpty(bcsfpResult)) {
            return;
        }
        dr.setPrizeTime(new Date());
//		dr.setHalfScore(halfScore);
//		dr.setWholeScore(wholeScore);
        if (spSfp != null && StringUtils.isNotEmpty(spSfp)) {
            dr.setSpSfp(spSfp);
        }
        if (sfpResult != null && StringUtils.isNotEmpty(sfpResult)) {
            dr.setSfpResult(sfpResult);
        }
        if (sfsp != null && StringUtils.isNotEmpty(sfsp)) {
            dr.setSpSf(sfsp);
        }
        if (sfResult != null && StringUtils.isNotEmpty(sfResult)) {
            dr.setSfResult(sfResult);
        }
        if (spSxds != null && StringUtils.isNotEmpty(spSxds)) {
            dr.setSpSxds(spSxds);
        }
        if (sxdsResult != null && StringUtils.isNotEmpty(sxdsResult)) {
            dr.setSxdsResult(sxdsResult);
        }
        if (spJqs != null && StringUtils.isNotEmpty(spJqs)) {
            dr.setSpJqs(spJqs);
        }
        if (jqsResult != null && StringUtils.isNotEmpty(jqsResult)) {
            dr.setJqsResult(jqsResult);
        }
        if (spBf != null && StringUtils.isNotEmpty(spBf)) {
            dr.setSpBf(spBf);
        }
        if (bfResult != null && StringUtils.isNotEmpty(bfResult)) {
            dr.setBfResult(bfResult);
        }
        if (spBcsfp != null && StringUtils.isNotEmpty(spBcsfp)) {
            dr.setSpBcsfp(spBcsfp);
        }
        if (bcsfpResult != null && StringUtils.isNotEmpty(bcsfpResult)) {
            dr.setBcsfpResult(bcsfpResult);
        }

        dr.setPrizeStatus(PrizeStatus.result_set.value);  //开奖状态   获取到结果   开奖  派奖
        dr.setResultFrom("高德");
        dcRaceService.update(dr);
    }

    @Override
    protected List<LotteryType> getLotteryTypeList() {

        return LotteryType.getDc();
    }

    @Override
    protected boolean isNotPrize(String phase) {
        List<DcRace> list = dcRaceService.getCloseByPhaseAndPrizeStatus(phase, PrizeStatus.not_open.value, DcType.normal);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }



    /**
     * 回收期状态将   已关闭改为 已开奖
     * */

    protected void recyclePhase(){//回收竞彩彩期

        super.recyclePhase();
        List<LotteryPhase> phaseList=phaseService.getByLotteryTypeAndStatus(LotteryType.DC_SFGG.value, PhaseStatus.close.value);
        if(phaseList!=null){
            List<Integer> orderStatusList=new ArrayList<Integer>();
            orderStatusList.add(OrderStatus.PRINTED.value);
            orderStatusList.add(OrderStatus.HALF_PRINTED.value);
            List<Integer> orderResultStatusList=new ArrayList<Integer>();
            orderResultStatusList.add(OrderResultStatus.not_open.value);
            orderResultStatusList.add(OrderResultStatus.prizing.value);
            for(LotteryPhase lotteryPhase:phaseList){
                try{
                    boolean planFlag=false;
                    String phase=lotteryPhase.getPhase();
                    PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
                    page.setPageIndex(1);
                    page.setMaxResult(1);
                    page.setPageFlag(false);
                    List<LotteryOrder> orders=null;
                    try{
                        orders=lotteryOrderService.getByLotteryPhaseMatchNumAndStatus(LotteryType.DC_SFGG.value, phase, null, orderStatusList, orderResultStatusList, page);
                    }catch(Exception e){
                        logger.error("查询订单状态出错",e);
                        planFlag=true;
                        break;
                    }
                    if (orders != null && !orders.isEmpty()) {
                        logger.error("竞彩[{}],[{}]期存在未开奖订单[{}],内容是:{}，不能修改状态为已开奖", new Object[]{LotteryType.DC_SFGG.name,phase, orders.get(0).getId(),orders.get(0).getContent()});
                        planFlag = true;
                        break;
                    }
                    if(!planFlag){
                        if(isNotPrize(phase)){
                            logger.error("彩种[{}],期[{}]场次有未开奖，请检查",LotteryType.DC_SFGG.name,phase);
                            continue;
                        }
                        logger.error("彩种[{}],期[{}]修改为已开奖",LotteryType.DC_SFGG.name,phase);
                        lotteryPhase.setPhaseStatus(PhaseStatus.prize_open.value);
                        phaseService.update(lotteryPhase);
                    }
                }catch(Exception e){
                    logger.error("彩期状态回收出错",e);
                }

            }

        }
    }
}
