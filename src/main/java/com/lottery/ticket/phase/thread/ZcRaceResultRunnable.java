package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ZcRace;
import com.lottery.core.service.LotteryPhaseService;

import com.lottery.core.service.ZcRaceService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqinyun on 16/3/7.
 */

public class ZcRaceResultRunnable extends AbstractThreadRunnable {
    protected long interval=180000;
    @Autowired
    private LotteryPhaseService lotteryPhaseService;
    @Autowired
    private ZcRaceService zcRaceService;


    @Override
    protected void executeRun() {
        while(running){

            try{
              process();
            }catch(Exception e){
                logger.error(e.getMessage(),e);
            }
            try {
                synchronized(this){
                    this.wait(this.interval);
                }
            } catch (InterruptedException e) {

            }
        }

    }

    protected  void process()throws Exception{
        List<Integer> statusList=new ArrayList<Integer>();
        statusList.add(PhaseStatus.close.value);
        statusList.add(PhaseStatus.prize_ing.value);
        for(int lotteryType: LotteryType.getZcValue()){
            List<LotteryPhase> lotteryPhaseList=lotteryPhaseService.getByLotteryAndStatuses(lotteryType,statusList);
            if (lotteryPhaseList!=null){
                for (LotteryPhase lotteryPhase:lotteryPhaseList){
                    String param="lottery_type="+lotteryType+"&phase="+lotteryPhase.getPhase();
                    String responseStr= HTTPUtil.sendPostMsg(LotteryConstant.ZC_RESULT_URL,param);
                    try{
                        if(StringUtils.isBlank(responseStr)){
                            logger.error("请求参数:{},返回空",param);
                            return;
                        }
                        JSONObject object=JSONObject.fromObject(responseStr);
                        String errorcode=object.getString("code");
                        if(errorcode.equals("0")){
                            JSONArray jsArray=JSONArray.fromObject(object.get("message"));
                            if(jsArray!=null){
                                for(int i=0;i<jsArray.size();i++){
                                    JSONObject jsObject=jsArray.getJSONObject(i);
                                    int isoutmoney=jsObject.getInt("isoutmoney");// 2开奖中 1已开奖 0未开奖
                                    if (isoutmoney==0){
                                        continue;
                                    }
                                    String result=jsObject.getString("result");
                                    String final_score=jsObject.getString("final_score");
                                    String match_num=jsObject.getString("match_num");


                                    String num1=jsObject.getString("num1");
                                    String money1=jsObject.getString("money1");
                                    String ccmoney=jsObject.getString("ccmoney");
                                    String totalmoney=jsObject.getString("totalmoney");
                                    String l1="1_"+num1+"_"+money1;
                                    if(lotteryType==LotteryType.ZC_SFC.value){
                                        String num2=jsObject.getString("num2");
                                        String money2=jsObject.getString("money2");
                                        l1+=",2_"+num2+"_"+money2;
                                    }
                                    if(lotteryType==LotteryType.ZC_RJC.value){
                                        totalmoney=jsObject.getString("ninetotalmoney");
                                        l1="1_"+jsObject.getString("ninenum")+"_"+jsObject.getString("ninemoney");
                                    }
                                    lotteryPhase.setPrizeDetail(l1);
                                    lotteryPhase.setSaleAmount(totalmoney);
                                    lotteryPhase.setPoolAmount(ccmoney);
                                    lotteryPhase.setWincode(result);
                                    if(lotteryPhase.getPhaseStatus()==PhaseStatus.close.value){
                                        lotteryPhase.setPhaseStatus(PhaseStatus.prize_ing.value);
                                    }
                                    lotteryPhaseService.update(lotteryPhase);
                                    ZcRace zcRace=zcRaceService.getByLotteryPhaseAndNum(lotteryType,lotteryPhase.getPhase(),Integer.valueOf(match_num));
                                    if (zcRace!=null&& StringUtils.isNotBlank(final_score)){
										if(lotteryType==LotteryType.ZC_BQC.value){
                                            String half_score=jsObject.getString("half_score");
                                            if(StringUtils.isNotBlank(half_score)){
                                                zcRace.setHalfScore(half_score);
                                            }
                                        }
                                        zcRace.setFinalScore(final_score);
                                        zcRaceService.update(zcRace);
                                    }


                                }
                            }
                        }

                    }catch (Exception e){
                       logger.error("获取足彩开奖结果出错",e);
                        logger.error("开奖结果原始信息是:{}",responseStr);
                    }
                }
            }

        }

    }

    private String getWincode(String code){
        StringBuffer querystr = new StringBuffer();
        for(int i=0;i<code.length();i++){
            querystr.append(code.charAt(i));
            querystr.append(",");

        }

        if (querystr.charAt(querystr.length() - 1) == ',') {
            querystr.deleteCharAt(querystr.length() - 1);
        }
        return querystr.toString();
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
