package com.lottery.test.terminal;

import com.lottery.common.ResponseData;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.domain.LotteryPhase;
import org.junit.Test;

import java.util.List;

/**
 * Created by fengqinyun on 15/3/21.
 */
public class HttpTest {

    @Test
    public  void testPhase(){
        try {
            String ss= HTTPUtil.sendPostMsg("http://118.26.65.147/lottery/phase/getCurrentList", "lotterytype=4001");
            System.out.println(ss);

            ResponseData responseData=JsonUtil.fromJsonToObject(ss,ResponseData.class);
            List<LotteryPhase> phaseList=JsonUtil.objectToList(responseData.getValue(), LotteryPhase.class);
            for (int i = 0; i < phaseList.size(); i++) {
                LotteryPhase phase=phaseList.get(i);
                System.out.println(phase.getPhase());
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
