package com.lottery.controller.pay;

import com.lottery.common.Constants;
import com.lottery.common.PageBean;
import com.lottery.common.ResponseData;
import com.lottery.common.contains.DrawAmountStatus;
import com.lottery.common.contains.DrawOperateType;
import com.lottery.common.contains.DrawType;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.RechargeResponseData;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.core.domain.UserDrawBank;
import com.lottery.core.handler.LotteryDrawAmountHandler;
import com.lottery.core.service.LotteryDrawAmountService;
import com.lottery.core.service.UserDrawBankService;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.drawamount.ZfbBankDrawAmount;
import com.lottery.pay.drawamount.ZfbDrawAmount;
import com.lottery.pay.impl.PayConfigService;
import com.lottery.pay.impl.elinkdraw.ElinkDrawRechargePrcodess;
import com.lottery.pay.progress.zfb.util.ZfbUtil;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现
 *
 * @author lxy
 */

@Controller
@RequestMapping("/lotteryDrawAmount")
public class LotteryDrawAmountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LotteryDrawAmountService lotteryDrawAmountService;
    @Autowired
    private UserDrawBankService userBankService;
    @Autowired
    protected PayConfigService payConfigService;
    @Autowired
    protected LotteryDrawAmountHandler drawAmountHandler;
    @Autowired
    protected ElinkDrawRechargePrcodess elinkDrawRechargePrcodess;
    
    /**
     * 提现请求
     * @param userno         用户编号编号
     * @param cardid//绑定账号id
     * @param drawAmount;    //提现金额(单位:元)
     * @return
     */
    @RequestMapping(value = "/cardDraw", method = RequestMethod.POST)
    public @ResponseBody ResponseData cardDraw(@RequestParam("userno") String userno, 
    		@RequestParam(value = "cardid") String cardid, @RequestParam(value = "drawAmount") String drawAmount){
        ResponseData rd = new ResponseData();
        try {
            logger.error("用户{}发起提现,银行卡号id是：{}", new Object[]{userno, cardid});
            LotteryDrawAmount lotteryDrawAmount = lotteryDrawAmountService.drawAmount(userno, cardid, drawAmount);
            logger.error("用户详细提现信息:{}", lotteryDrawAmount.toString());
            rd.setErrorCode(ErrorCode.Success.getValue());
            rd.setValue(lotteryDrawAmount);
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.getValue());
            logger.error("用户提现出错", e);
        }
        return rd;
    }

    /**
     * 查看银行信息
     *
     * @param userno
     * @return
     */
    @RequestMapping(value = "/getBankInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData getBankinfo(@RequestParam("userno") String userno) {
        ResponseData rd = new ResponseData();
        try {

            List<UserDrawBank> userDrawBanks = userBankService.getByUserno(userno);
            rd.setErrorCode(ErrorCode.Success.getValue());
            rd.setValue(userDrawBanks);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.getValue());
        }
        return rd;
    }

    /**
     * 后台支付宝提现请求
     *
     * @param strs
     * @return
     */
    @RequestMapping(value = "/drawAmount", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData drawAmount(@RequestParam("strs") String strs) {
        ResponseData rd = new ResponseData();
        try {
            //得到配置
            IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbdraw);
            if (zfbConfig == null) {
                rd.setErrorCode(ErrorCode.system_error.getValue());
                rd.setValue("支付宝提现配置为空");
                return rd;
            }
            String formStr = "";
            //查询审核完成的提现记录
            List<LotteryDrawAmount> lotteryDrawList = lotteryDrawAmountService.queryDrawAmountList(DrawType.zfb_draw.value, strs);
           

            if (lotteryDrawList != null && lotteryDrawList.size() > 0) {
                String batchNo = lotteryDrawAmountService.updateZfbAutoDrawStatus(lotteryDrawList,DrawOperateType.zfb_draw.value);
                //封装
                formStr = ZfbDrawAmount.getDrawStr(zfbConfig, lotteryDrawList, batchNo);
                logger.error("提现确定提交数据为{}",formStr);
                rd.setValue(formStr);
                rd.setErrorCode(ErrorCode.Success.getValue());
            }else{
            	rd.setValue("没有提现记录");
                rd.setErrorCode(ErrorCode.no_exits.getValue());
            }

            
        } catch (Exception e) {
            logger.error("提现自动提交到支付宝出错", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }

    /**
     * 支付宝提现通知
     *
     * @param strs
     * @return
     */
    @RequestMapping(value = "/toDrawNotify", method = RequestMethod.POST)
    public
    @ResponseBody
    String toDrawNotify(@RequestParam("strs") String strs) {
        try {
            String[] array = strs.split(",");
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < array.length; i++) {
                String[] data = array[i].split("=");
                map.put(data[0], data[1].toString());
            }
            //得到配置
            IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbdraw);
            if (zfbConfig == null) {
                logger.error("支付宝提现配置为空");
                return null;
            }
            if (ZfbUtil.VerfySign(map, zfbConfig)) {
                String success_details = map.get("success_details") + "";//成功的批次信息
                String fail_details = map.get("fail_details") + "";//失败的批次信息
                if (StringUtils.isNotBlank(success_details)) {
                    String[] success = success_details.split("\\|");
                    for (int i = 0; i < success.length; i++) {
                        String[] contents = success[i].split("\\^");
                        Date finishTime = DateUtil.parse("yyyyMMddHHmmss", contents[7]);
                        lotteryDrawAmountService.doResult(contents[0], contents[1], contents[3], true, DrawAmountStatus.success.getName(), finishTime);
                    }
                }
                if (StringUtils.isNotBlank(fail_details)) {
                    String[] fails = fail_details.split("\\|");
                    for (int i = 0; i < fails.length; i++) {
                        String[] contents = fails[i].split("\\^");
                        Date finishTime = DateUtil.parse("yyyyMMddHHmmss", contents[7]);
                        lotteryDrawAmountService.doResult(contents[0], contents[1], contents[2], false, contents[5], finishTime);
                    }
                }
            }
            return "success";
        } catch (Exception e) {
            logger.error("支付宝提现异常", e);
        }
        return null;
    }

    /**
     * 提交支付宝银行接口
     *
     * @param digest   摘要
     * @param filename 文件名
     * @return
     */
    @RequestMapping(value = "/bankDrawAmount", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData toBankDraw(@RequestParam("digest") String digest, @RequestParam("filename") String filename) {
        logger.error("digest=" + digest + ",filename=" + filename);
        ResponseData rd = new ResponseData();
        //得到配置
        IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbbankdraw);
        if (zfbConfig == null) {
            return null;
        }
        try {
            //查询审核完成的提现记录
            Map<String, String> result = ZfbBankDrawAmount.getResult(zfbConfig, digest, "bptb_pay_file", filename);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("result", result);
            maps.put("requestUrl", zfbConfig.getRequestUrl());
            maps.put("bptb_pay_file", "bptb_pay_file");
            //待请求参数数组
            rd.setValue(maps);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("lotteryDrawAmount/bankDrawAmount", e);
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("lotteryDrawAmount/bankDrawAmount", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }


    /**
     * 提交支付宝银行接口
     *
     * @param filename 文件名
     * @return
     */
    @RequestMapping(value = "/confirmbankDraw", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData confirmbankDraw(@RequestParam("filename") String filename) {

        ResponseData rd = new ResponseData();
        //得到配置
        IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbbankdraw);
        if (zfbConfig == null) {
            return null;
        }
        String returnResult = "";
        try {
            filename = filename.split("\\.")[0];
            //查询审核完成的提现记录
            logger.error("根据文件名{}确定提前的数据", filename);
            List<LotteryDrawAmount> lotteryDrawList = lotteryDrawAmountService.queryDrawAmountList(DrawType.bank_draw.value, DrawAmountStatus.handing.value, filename);
            if (lotteryDrawList != null && lotteryDrawList.size() > 0) {
                logger.error("根据文件名{}确定提前的数据为{}", filename, lotteryDrawList.size());

            }

            //查询审核完成的提现记录
            returnResult = ZfbBankDrawAmount.confirm(zfbConfig, filename);
            rd.setValue(returnResult);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("lotteryDrawAmount/confirmbankDraw", e);
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("lotteryDrawAmount/confirmbankDraw", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }

    /**
     * 同步返回验证
     *
     * @param strs
     * @return
     */
    @RequestMapping(value = "/toDrawBankReturn", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData toDrawBankReturn(@RequestParam("strs") String strs) {
        ResponseData rd = new ResponseData();
        try {
            //得到配置
            logger.error("接收到支付宝同步验证返回:{}", strs);
            IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbbankdraw);
            if (zfbConfig == null) {
                return null;
            }
            Map<String, String> params = getMap(strs);
            //验证签名
            if (ZfbUtil.VerfySign(params, zfbConfig)) {
                String result_code = params.get("result_code");
                rd.setValue(Constants.zfbDrawError.get(result_code) != null ? Constants.zfbDrawError.get(result_code) : "验证成功");
                rd.setErrorCode(ErrorCode.Success.getValue());
            }
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }


    /**
     * 支付宝银行提现通知
     *
     * @param notifyStr
     * @return
     */
    @RequestMapping(value = "/toDrawBankNotify", method = RequestMethod.POST)
    public
    @ResponseBody
    String toDrawBankNotify(@RequestParam("notify_data") String notifyStr, @RequestParam("payChannel") String payChannel) {
        try {
            logger.error("接收到支付宝银行通知:{}", notifyStr);
            JSONObject jsonObject = JSONObject.fromObject(notifyStr);
            Map p = (Map) jsonObject;
            String[] array = p.toString().split("\\{")[1].split("\\}")[0].split("\\,");
            Map<String, String> params = new HashMap<String, String>();
            for (int i = 0; i < array.length; i++) {
                String[] data = array[i].split("\\:");
                if (data.length > 1) {
                    params.put(data[0].substring(1, data[0].length() - 1).trim(), data[1].toString().substring(2, data[1].toString().length() - 2).trim());
                } else {
                    params.put(data[0], "");
                }
            }
            //得到配置
            IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.get(payChannel));
            if (zfbConfig == null) {
                return "1";
            }
            //验证sign
            if (ZfbUtil.VerfySign(params, zfbConfig)) {
                String fileName = params.get("file_name") + "";
                drawAmountHandler.zfbLotteryDraw(zfbConfig, fileName);
            } else {
                logger.error("支付宝银行验签异常:{}", notifyStr);
            }
            return "0";
        } catch (Exception e) {
            logger.error("支付宝提现异常" + e.getMessage());
        }
        return "1";
    }


    /**
     * 同步返回验证
     *
     * @param strs
     * @return
     */
    @RequestMapping(value = "/toZfbWebPayReturn", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData toZfbWebPayReturn(@RequestParam("strs") String strs) {
        ResponseData rd = new ResponseData();
        logger.error("toZfbWebPayReturn接收到支付宝同步验证返回:{}", strs);
        try {
            //得到配置
            IPayConfig zfbConfig = payConfigService.getByPayChannel(PayChannel.zfbdraw);
            if (zfbConfig == null) {
                return null;
            }
            Map<String, String> params = new HashMap<String, String>();
            String str[] = strs.split("\\,");
            for (int i = 0; i < str.length; i++) {
                String mapstr[] = str[i].split("=");
                params.put(mapstr[0], mapstr[1]);
            }

            //验证签名
            if (ZfbUtil.VerfySign(params, zfbConfig)) {
                String result_code = params.get("result_code");
                rd.setValue(Constants.zfbDrawError.get(result_code) != null ? Constants.zfbDrawError.get(result_code) : "验证成功");
                rd.setErrorCode(ErrorCode.Success.getValue());
            }
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }

    /**
     * 查询提现记录
     *
     * @param userno 用户编号
     */
    @RequestMapping(value = "/getDrawAmountList", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData getDrawAmountList(@RequestParam("userno") String userno,
                                   @RequestParam(value = "startLine", required = false, defaultValue = "1") int startLine,
                                   @RequestParam(value = "maxLine", required = false, defaultValue = "15") int maxResult,
                                   @RequestParam(value = "startTime", required = false, defaultValue = "") String startTime,
                                   @RequestParam(value = "endTime", required = false, defaultValue = "") String endTime) {
        ResponseData rd = new ResponseData();
        try {
            BetweenDate bd = SpecialDateUtils.getBetweenDate(3, startTime, endTime);
            Date startDate = bd.getStartDate();
            Date endDate = bd.getEndDate();
            PageBean<LotteryDrawAmount> page = new PageBean<LotteryDrawAmount>();
            page.setPageIndex(startLine);
            page.setMaxResult(maxResult);
            lotteryDrawAmountService.getDrawAmountList(userno, startDate, endDate, page);
            rd.setValue(page);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (Exception e) {
            logger.error("查询提现记录出错", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }

    /**
     * 提现失败手动撤单
     * @param id   提现id
     * @param memo 失败原因
     */
    @RequestMapping(value = "/failHand", method = RequestMethod.POST)
    public @ResponseBody ResponseData drawFaileHand(@RequestParam String id, @RequestParam String memo) {
        ResponseData rd = new ResponseData();
        try {
            lotteryDrawAmountService.handFaile(id, memo);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (Exception e) {
            logger.error("银行卡记录驳回失败", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
            rd.setValue(e.getMessage());
        }
        return rd;
    }



    /**
     * 提现驳回
     *
     * @param id   提现id
     * @param memo 驳回原因
     */
    @RequestMapping(value = "/handReject", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData drawReject(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "memo", required = true) String memo) {
        ResponseData rd = new ResponseData();
        try {

            lotteryDrawAmountService.handReject(id, memo);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (Exception e) {
            logger.error("手动驳回失败", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 手动提现成功修改，提现成功手工减款
     *
     * @param batchId 提现批次id
     */
    @RequestMapping(value = "/handBatch", method = RequestMethod.POST)
    public @ResponseBody  ResponseData handBatch(@RequestParam String batchId) {
        ResponseData rd = new ResponseData();
        try {
            drawAmountHandler.handBatch(batchId);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (Exception e) {
            logger.error("手工批次处理失败", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    /**
     * 单个修改提现成功
     *
     * @param drawamountid 提现记录id
     */
    @RequestMapping(value = "/drawamountsuccess", method = RequestMethod.POST)
    public @ResponseBody  ResponseData drawamountsuccess(@RequestParam String drawamountid) {
        ResponseData rd = new ResponseData();
        try {
            lotteryDrawAmountService.handDrawAmonthaschecked(drawamountid);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (Exception e) {
            logger.error("手工处理提现失败", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    protected Map<String, String> getMap(String strs) {
        JSONObject jsonObject = JSONObject.fromObject(strs);
        Map p = (Map) jsonObject;
        String[] array = p.toString().split("\\{")[1].split("\\}")[0].split("\\,");
        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < array.length; i++) {
            String[] data = array[i].split("\\:\\[");
            if (data.length > 1) {
                params.put(data[0].substring(1, data[0].length() - 1), data[1].toString().substring(1, data[1].toString().length() - 2));
            } else {
                params.put(data[0], "");
            }
        }
        return params;
    }
    
    
    
    
    /**
     * 后台银联提现请求
     *
     * @param strs
     * @return
     */
    @RequestMapping(value = "/elinkDrawAmount", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData elinkDrawAmount(@RequestParam("strs") String strs) {
        ResponseData rd = new ResponseData();
        try {
            //得到配置
            IPayConfig elinkConfig = payConfigService.getByPayChannel(PayChannel.elinkdraw);
            if (elinkConfig == null) {
                rd.setErrorCode(ErrorCode.system_error.getValue());
                rd.setValue("银联提现配置为空");
                return rd;
            }
            //查询审核完成的提现记录
            List<LotteryDrawAmount> lotteryDrawList = lotteryDrawAmountService.queryDrawAmountList(DrawType.bank_draw.value, strs);
            if (lotteryDrawList== null ||lotteryDrawList.size() ==0) {
            	rd.setValue("没有提现记录");
                rd.setErrorCode(ErrorCode.no_exits.getValue());
                return rd;
            }
            logger.error("查询出审核成功的数据为:{}",lotteryDrawList.size());
            String batchNo = lotteryDrawAmountService.updateZfbAutoDrawStatus(lotteryDrawList, DrawOperateType.elink_draw.value);
            RechargeResponseData rechargeResponseData= null;
            try {  
	        	rechargeResponseData=  elinkDrawRechargePrcodess.getDataReturn(batchNo,lotteryDrawList);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }     
           
            rd.setValue(rechargeResponseData);
            rd.setErrorCode(ErrorCode.Success.getValue());	 
            
            	
            
        } catch (Exception e) {
            logger.error("银联提现出错", e);
            rd.setErrorCode(ErrorCode.Faile.getValue());
        }
        return rd;
    }
    
    /**
     * 金额相加
     * @param lotteryDrawAmounts
     * @return
     */
    private BigDecimal getTotalMoney(List<LotteryDrawAmount> lotteryDrawAmounts){
    	BigDecimal totalLotteryDrawAmount=BigDecimal.ZERO;
    	for(LotteryDrawAmount lotteryDrawAmount:lotteryDrawAmounts){
    		totalLotteryDrawAmount=totalLotteryDrawAmount.add(lotteryDrawAmount.getDrawAmount());
    	}
    	return totalLotteryDrawAmount;
    }

    /**
     * 出票商提现请求
     *
     * @param userno         用户编号编号
     * @param drawAmount;    //提现金额(单位:元)
     * @return
     */
    @RequestMapping(value = "/merchantHandDraw", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData merchantHandDraw(@RequestParam("userno") String userno, @RequestParam(value = "drawAmount") String drawAmount
    ) {
        ResponseData rd = new ResponseData();
        try {

            LotteryDrawAmount lotteryDrawAmount = lotteryDrawAmountService.merchantDrawAmount(userno, drawAmount);
            rd.setErrorCode(ErrorCode.Success.getValue());
            rd.setValue(lotteryDrawAmount);
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.getValue());
            logger.error("接入商提现出错", e);
        }
        return rd;
    }





}
