package com.lottery.draw.impl;

import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IVenderConfig;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/9/6.
 */
@Component("all_phase")
public class AllPhaseDrawWorker extends AbstractVenderPhaseDrawWorker {

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.all;
    }

    public LotteryDraw getPhaseDraw(Integer lotteryType, String phase){//重新该方法
        if(LotteryType.getDaPan().contains(LotteryType.get(lotteryType))){
            LotteryDraw lotteryDraw=get(null,lotteryType,phase);
            if (lotteryDraw!=null){
                lotteryPhaseService.saveWininfo(lotteryType, phase,lotteryDraw.getWindCode(),lotteryDraw.getResultDetail(), lotteryDraw.getJackpot(), lotteryDraw.getAddJackpot(), lotteryDraw.getVolumeOfSales(), lotteryDraw.getDrawFrom());
                this.sendJMS(lotteryType,phase,lotteryDraw.getWindCode());
                return lotteryDraw;
            }

        }
        return null;

    }



    @Override
    protected LotteryDraw get(IVenderConfig config, Integer lotteryType, String phase) {

        try {
           String responseStr=HTTPUtil.post(LotteryConstant.WINCODE_URL,"lottery_type="+lotteryType+"&phase="+phase);
            JSONObject object=JSONObject.fromObject(responseStr);
            String errorcode=object.getString("code");
            if(errorcode.equals("0")){
                JSONObject jsonObject=JSONObject.fromObject(object.get("message"));
                int isoutmoney=jsonObject.getInt("isoutmoney");// 2开奖中 1已开奖 0未开奖
                if(isoutmoney==1){
                    logger.error("从抓取中心抓取的开奖内容是:{}",responseStr);
                    LotteryDraw lotteryDraw = new LotteryDraw();
                    lotteryDraw.setLotteryType(lotteryType);
                    lotteryDraw.setPhase(phase);
                    lotteryDraw.setWindCode(jsonObject.getString("wincode"));
                    lotteryDraw.setResultDetail(jsonObject.getString("prize_detail"));
                    lotteryDraw.setResponsMessage(responseStr);
                    lotteryDraw.setVolumeOfSales(jsonObject.getString("sale_money"));
                    lotteryDraw.setJackpot(jsonObject.getString("pool_money"));
                    lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
                    lotteryDraw.setIsDraw(YesNoStatus.yes.value);
                    lotteryDraw.setDrawFrom("抓取中心");
                    return lotteryDraw;

                }

            }
        }catch (Exception e){
            logger.error("错误信息",e);

        }
        return null;

    }
}
