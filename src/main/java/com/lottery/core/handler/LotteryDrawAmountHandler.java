package com.lottery.core.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.DrawAmountStatus;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.core.service.LotteryDrawAmountService;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.drawamount.ZfbBankDrawAmount;
import com.lottery.ticket.sender.worker.XmlParse;


/**
 * Created by fengqinyun on 15/4/14.
 */
@Service
public class LotteryDrawAmountHandler {

    protected  final Logger logger= LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected LotteryDrawAmountService drawAmountService;

    public void handBatch(String batchId){
        List<LotteryDrawAmount> drawAmountList=drawAmountService.getByBatchId(batchId);
        for(LotteryDrawAmount drawAmount:drawAmountList){
            drawAmountService.handDrawAmont(drawAmount.getId());
        }
    }
    /**
     * 去支付宝接口查询并处理结果
     * @param zfbConfig
     * @param batchId
     * @return
     */

    public void zfbLotteryDraw(IPayConfig zfbConfig,String batchId){
        String str="";
        try {
            str = ZfbBankDrawAmount.queryDrawBank(batchId, zfbConfig);
        } catch (Exception e) {
            logger.error("查询支付宝提现出错",e);
            return;
        }
        try{
            if(StringUtils.isBlank(str)){
                logger.error("查询返回为空");
                return;
            }

            Element rootElt= XmlParse.getRootElement(str);
            String is_success = rootElt.elementTextTrim("is_success");
            if("T".equals(is_success)){
                List<HashMap<String,String>>mapList=XmlParse.getElementTextList("response/alipay_results/alipay_result/detailList/","detailItem",str);
                if(mapList!=null&&mapList.size()>0){
                    for(HashMap<String,String> map:mapList){
                        String bankAccountNo=map.get("bankAccountNo");
                        String status=map.get("status");
                        String amount=map.get("amount");
                        String remark=map.get("dealMemo");
                        String id=map.get("userSerialNo");

                        if("SUCCESS".equals(status)){
                            drawAmountService.doResult(id,bankAccountNo,amount,true,remark,new Date());
                        }else if("FAIL".equals(status)){
                            drawAmountService.doResult(id,bankAccountNo,amount,false,remark,new Date());
                        }else if("DISUSE".equals(status)){//余额不足
                            drawAmountService.updateStatusById(id, DrawAmountStatus.haschecked.getValue());
                        }

                    }
                }
            }else{
                logger.error("支付提现查询错误,返回:{}",str);
                String error = rootElt.elementTextTrim("error");
                if("NO_QUERY_RESULTS".equals(error)){//不存在
                    drawAmountService.updateStatusByBatchId(batchId,DrawAmountStatus.haschecked.getValue());
                }
            }
        } catch (Exception e) {
            logger.error("支付宝银行卡提现查询线程异常,strs={}",str,e);
        }
    }
}
