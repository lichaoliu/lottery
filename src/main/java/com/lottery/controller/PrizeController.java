package com.lottery.controller;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.core.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseData;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.draw.LotteryDraw;
import com.lottery.draw.prize.CommonLotteryDrawTask;

@Controller
@RequestMapping("/prize")
public class PrizeController {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private PrizeHandler handler;

    @Autowired
    private JingcaiService jingcaiService;


    @Autowired
    private LotteryPhaseService phaseService;
    @Autowired
    private PrizeErrorHandler prizeErrorHandler;

    @Autowired
    protected SystemService systemService;
    @Autowired
    private LotteryOrderService lotteryOrderService;


    /**
     * 彩种开奖，会进行算奖派奖
     *
     * @param lotterytype
     * @param phase
     * @param wincode
     * @return
     */
    @RequestMapping(value = "/openPrize", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData openPrize(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
        ResponseData rd = new ResponseData();

        try {

            handler.prizeOpen(lotterytype, phase, wincode);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("通知开奖lotterytype={},phase={},wincode={};错误,原因是:{}", lotterytype, phase, wincode, e.getMessage());
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("通知开奖开始出错lotterytype={} phase={} wincode={}", lotterytype, phase, wincode, e.getMessage());
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 保存开奖号码，不进行开奖算奖
     *
     * @param lotterytype
     * @param phase
     * @param wincode
     * @return
     */
    @RequestMapping(value = "/savePrize", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData savePrize(@RequestParam(value = "lotterytype", required = true) String lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
        ResponseData rd = new ResponseData();
        // logger.error("保存开奖开始lotterytype={} phase={} wincode={}",new
        // Object[]{lotterytype,phase,wincode});
        try {
            prizeService.savePrize(lotterytype, phase, wincode);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("保存开奖出错lotterytype={} phase={} wincode={}", new Object[]{lotterytype, phase, wincode}, e);
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("保存开奖出错lotterytype={} phase={} wincode={}", new Object[]{lotterytype, phase, wincode}, e);
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        // logger.error("保存开奖结束lotterytype={} phase={} wincode={}",new
        // Object[]{lotterytype,phase,wincode});
        return rd;
    }

    /**
     * 保存开奖号码，不进行开奖算奖
     *
     * @param lotterytype
     * @param phase

     * @return
     */
    @RequestMapping(value = "/savePrizeInfo", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData savePrizeInfo(@RequestParam(value = "lotterytype", required = true) String lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "info", required = true) String info, @RequestParam(value = "poolAmount", required = true) String poolAmount, @RequestParam(value = "addPoolAmount", required = true) String addPoolAmount, @RequestParam(value = "saleAmount", required = true) String saleAmount) {
        ResponseData rd = new ResponseData();
        // logger.error("保存开奖详情开始lotterytype={} phase={} info={} poolAmount={} addPoolAmount={} saleAmount={}",lotterytype,phase,info,poolAmount,addPoolAmount,saleAmount);
        try {
            prizeService.savePrizeInfo(lotterytype, phase, info, poolAmount, addPoolAmount, saleAmount);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("保存开奖详情出错lotterytype={} phase={} info={} poolAmount={} addPoolAmount={} saleAmount={}", lotterytype, phase, info, poolAmount, addPoolAmount, saleAmount, e);
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("保存开奖详情出错lotterytype={} phase={} info={} poolAmount={} addPoolAmount={} saleAmount={}", lotterytype, phase, info, poolAmount, addPoolAmount, saleAmount, e);
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        // logger.error("保存开奖详情结束lotterytype={} phase={} info={} poolAmount={} addPoolAmount={} saleAmount={}",new
        // Object[]{lotterytype,phase,info,poolAmount,addPoolAmount,saleAmount});
        return rd;
    }

    /**
     * 足彩开奖 如果比赛没有比完，则用*表示 开奖状态为开开奖中
     * <p>
     * 返回phase来判断状态是否为已经开奖，已经开奖后，不在进行通知开奖
     *
     * @param lotterytype
     * @param phase
     * @param wincode
     * @return
     */
    @RequestMapping(value = "/zcSavePrize", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData zcSavePrize(@RequestParam(value = "lotterytype", required = true) String lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
        ResponseData rd = new ResponseData();
        try {
            if (LotteryType.getZc().contains(LotteryType.getLotteryType(Integer.parseInt(lotterytype)))) {
                prizeService.zcSavePrize(lotterytype, phase, wincode);

                LotteryPhase phase2 = phaseService.getPhase(Integer.parseInt(lotterytype), phase);
                rd.setValue(phase2);
                rd.setErrorCode(ErrorCode.Success.getValue());
            }
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 订单派奖，可以派发大奖
     *
     * @param orderid
     * @return
     */
    @RequestMapping(value = "/encaseOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData encashOrder(@RequestParam(value = "orderid", required = true) String orderid) {
        ResponseData rd = new ResponseData();
        logger.error("订单手工派奖orderid={}开始", orderid);
        try {
            handler.encashProcess(orderid, true);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            logger.error("订单手工派奖orderid={}出错", orderid, e);
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("订单手工派奖orderid={}出错", orderid, e);
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        logger.error("订单手工派奖orderid={}结束", orderid);
        return rd;
    }

    /**
     * 检查算奖结束
     *
     * @param lotterytype
     * @param phase
     * @return
     */
    @RequestMapping(value = "/checkPrizeEnd", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData checkPrizeEnd(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();
        rd.setErrorCode(ErrorCode.Success.value);
        logger.error("手工检查算奖结束lotterytype={} phase={}开始", new Object[]{lotterytype, phase});
        try {
            rd.setValue("成功");
            handler.prizeEndCheckNotReturn(lotterytype, phase);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        logger.error("手工检查算奖结束lotterytype={} phase={}结束", new Object[]{lotterytype, phase});
        return rd;
    }

    /**
     * 检查派奖结束
     *
     * @param lotterytype
     * @param phase
     * @return
     */
    @RequestMapping(value = "/checkEncashEnd", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData checkEncashEnd(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();

        try {
            handler.prizeEncashEndCheck(lotterytype, phase);
            rd.setValue("成功");
            rd.setErrorCode(ErrorCode.Success.value);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    /**
     * 检查订单是否算奖
     *
     * @param lotterytype
     * @param phase
     * @return
     */
    @RequestMapping(value = "/orderDrawCheck", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData orderDrawCheck(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();

        logger.error("检查算奖开始,lotterytype={},phase={}", lotterytype, phase);

        try {

            if (LotteryType.getDcValue().contains(lotterytype)) {
                if (lotterytype == LotteryType.DC_SFGG.value) {
                    CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                    lotterydrawTask.setLotteryType(lotterytype);
                    lotterydrawTask.setPhase(phase);
                    handler.orderDrawCheck(lotterydrawTask);
                } else {
                    for (Integer lotteryType : LotteryType.getDcValue()) {
                        if (lotteryType != LotteryType.DC_SFGG.value) {
                            CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                            lotterydrawTask.setLotteryType(lotteryType);
                            lotterydrawTask.setPhase(phase);
                            handler.orderDrawCheck(lotterydrawTask);
                        }
                    }
                }

            } else if (LotteryType.getJclqValue().contains(lotterytype)) {
                for (Integer lotteryType : LotteryType.getJclqValue()) {
                    CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                    lotterydrawTask.setLotteryType(lotteryType);
                    lotterydrawTask.setPhase(phase);
                    handler.orderDrawCheck(lotterydrawTask);
                }
            } else if (LotteryType.getJczqValue().contains(lotterytype)) {
                for (Integer lotteryType : LotteryType.getJczqValue()) {
                    CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                    lotterydrawTask.setLotteryType(lotteryType);
                    lotterydrawTask.setPhase(phase);
                    handler.orderDrawCheck(lotterydrawTask);
                }
            } else if (LotteryType.getGuanyajunValue().contains(lotterytype)) {
                for(Integer lottype:LotteryType.getGuanyajunValue()){
                    CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                    lotterydrawTask.setLotteryType(lottype);
                    lotterydrawTask.setPhase(phase);
                    handler.orderDrawCheck(lotterydrawTask);
                }

            } else {
                LotteryPhase lotteryPhase = phaseService.getByTypeAndPhase(lotterytype, phase);
                if (lotteryPhase != null) {
                    if (StringUtils.isNotBlank(lotteryPhase.getWincode())) {
                        CommonLotteryDrawTask lotterydrawTask = new CommonLotteryDrawTask();
                        lotterydrawTask.setLotteryType(lotterytype);
                        lotterydrawTask.setPhase(phase);
                        lotterydrawTask.setWinCode(lotteryPhase.getWincode());
                        handler.orderDrawCheck(lotterydrawTask);
                    } else {
                        logger.error("彩种:{},第:{}期没有开奖号码", lotterytype, phase);
                    }
                }else {
                    logger.error("彩种:{},新期:{}不存在",lotterytype,phase);
                }
            }
            final int lot=lotterytype;
            final String ph=phase;
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler.prizeCallbak(lot, ph);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


            rd.setValue("成功");
            rd.setErrorCode(ErrorCode.Success.value);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    /**
     * 计算单个订单中奖情况
     *
     * @param lotterytype
     * @param orderid
     * @param wincode
     * @return
     */
    @RequestMapping(value = "/caculateOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData caculateOrder(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "orderid", required = true) String orderid, @RequestParam(value = "wincode", required = true) String wincode) {
        ResponseData rd = new ResponseData();
        try {
            lotteryOrderService.updateOrderResultStatus(orderid, OrderResultStatus.prizing.value,null);
            handler.drawWork(orderid, wincode);
            rd.setErrorCode(ErrorCode.Success.value);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 根据彩种，期号从各个出票商处抓取
     */
    @RequestMapping(value = "/getLotteryDraw", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData getLotteryDraw(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();
        try {
            LotteryDraw draw = prizeService.getLotteryDraw(lotterytype, phase);
            if (draw != null) {
                rd.setErrorCode(ErrorCode.Success.getValue());
                rd.setValue(draw);
            } else {
                rd.setErrorCode(ErrorCode.no_windcode.getValue());
            }
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            logger.error("请求单个开奖错误",e);
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 根据彩种，期号从各个出票商处抓取
     */
    @RequestMapping(value = "/getLotteryDrawList", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData getLotteryDrawList(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();
        try {
            List<LotteryDraw> drawList = prizeService.getLotteryDrawList(lotterytype, phase);
            if (drawList.isEmpty()) {
                rd.setErrorCode(ErrorCode.no_windcode.getValue());
            } else {
                rd.setErrorCode(ErrorCode.Success.getValue());
                rd.setValue(drawList);
            }
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            logger.error("获取开奖错误",e);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 普通彩种按期算奖
     *
     * @param lotterytype 彩种编号
     * @param phase       期号
     * @param wincode     开奖号码
     * @return
     */
    @RequestMapping(value = "/caculatePhase", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData caculatePhase(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = true) String wincode) {
        ResponseData rd = new ResponseData();
        try {

            handler.drawExcetor(lotterytype, phase, wincode, null);
            rd.setErrorCode(ErrorCode.Success.getValue());
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 根据订单号计算竞彩订单奖金
     *
     * @param orderid 订单编号
     * @return
     */
    @RequestMapping(value = "/calJingcaiOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData calJingcaiOrder(@RequestParam(value = "orderid", required = true) String orderid) {
        ResponseData rd = new ResponseData();
        rd.setErrorCode(ErrorCode.Success.value);
        try {
            logger.error("计算奖金:{}",orderid);
            lotteryOrderService.updateOrderResultStatus(orderid, OrderResultStatus.prizing.value,null);
            handler.drawWork(orderid, null);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 根据订单编号计算北单订单奖金
     *
     * @param orderid 订单编号
     * @return
     */
    @RequestMapping(value = "/calBeidanOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData calBeidanOrder(@RequestParam(value = "orderid", required = true) String orderid) {
        ResponseData rd = new ResponseData();
        rd.setErrorCode(ErrorCode.Success.value);
        try {
            lotteryOrderService.updateOrderResultStatus(orderid, OrderResultStatus.prizing.value,null);
            handler.drawWork(orderid, null);
        } catch (LotteryException e) {
            rd.setErrorCode(e.getErrorCode().getValue());
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    /**
     * 新的算奖
     *
     * @param lotterytype  彩种
     * @param phase        期号
     * @param wincode      开奖号码
     * @param lastMatchNum 最后场次号
     * @return
     */
    @RequestMapping(value = "/drawExcetor", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData draw(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase, @RequestParam(value = "wincode", required = false) String wincode, @RequestParam(value = "lastmatchnum", required = false) Long lastMatchNum) {
        ResponseData rd = new ResponseData();
        try {
            rd.setErrorCode(ErrorCode.Success.value);
            if ((!LotteryType.getDcValue().contains(lotterytype)) && (!LotteryType.getJczqValue().contains(lotterytype))
                    && (!LotteryType.getJclqValue().contains(lotterytype)) && (!LotteryType.getGuanyajunValue().contains(lotterytype))) {
                if (StringUtil.isEmpty(wincode)) {
                    rd.setErrorCode(ErrorCode.paramter_error.value);
                } else {
                    handler.drawExcetor(lotterytype, phase, wincode, lastMatchNum);
                }
            } else {
                handler.drawExcetor(lotterytype, phase, wincode, lastMatchNum);
            }
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    /**
     * 定时算奖开关
     *
     * @param lotterytype 彩种
     * @param type        类型，内容如下： check 检查 start 开启 stop 停止
     */
    @RequestMapping(value = "/drawTimer", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData drawTimer(@RequestParam(value = "lotterytype", required = true) int lotterytype, @RequestParam(value = "type", required = true) String type) {
        ResponseData rd = new ResponseData();
        try {
            logger.error("算奖开关{},{}", lotterytype, type);
            LotteryType lotType = LotteryType.get(lotterytype);
            ThreadContainer threadContainer = null;
            if (LotteryType.getJczq().contains(lotType)) {
                threadContainer = systemService.getCtx().getBean("jczqPhaseDrawTimerContainer", ThreadContainer.class);
            }
            if (LotteryType.getJclq().contains(lotType)) {
                threadContainer = systemService.getCtx().getBean("jclqPhaseDrawTimerContainer", ThreadContainer.class);
            }
            if (LotteryType.getDc().contains(lotType)) {
                threadContainer = systemService.getCtx().getBean("dcPhaseDrawTimerContainer", ThreadContainer.class);
            }
            if (threadContainer == null) {
                rd.setErrorCode(ErrorCode.no_exits.value);
                rd.setValue("该彩种的定时开奖任务不存在");
                return rd;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("彩种:").append(lotType.getName());
            sb.append("<br/>");
            sb.append("运行状态:");
            if (type.equals("check")) {
                sb.append(threadContainer.isRunning());
            }
            if (type.equals("start")) {
                if (!threadContainer.isRunning()) {
                    logger.error("开始彩种{}的定时开奖任务", lotType.getName());
                    threadContainer.setBeforeRunWaitTime(0);
                    threadContainer.start();
                }
                sb.append(threadContainer.isRunning());
            }
            if (type.equals("stop")) {
                if (threadContainer.isRunning()) {
                    logger.error("停止彩种{}的定时开奖任务", lotType.getName());
                    threadContainer.stop();
                }
                sb.append(threadContainer.isRunning());
            }
            sb.append("<br/>");
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue(sb.toString());
            return rd;
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    @RequestMapping(value = "/asyncPhase", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData asyncPhase(@RequestParam(value = "lotteryType", required = true) int lotteryType, @RequestParam(value = "terminalType", required = true) int terminalType) {
        ResponseData rd = new ResponseData();
        try {
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue(prizeService.asyncPhase(lotteryType, terminalType));
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData refund(@RequestParam(value = "lotteryType", required = true) int lotterytype, @RequestParam(value = "phase", required = true) String phase) {
        ResponseData rd = new ResponseData();
        try {
            prizeErrorHandler.refundOrder(lotterytype, phase);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("开始退款");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/simulateOdd", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData simulateOdd(@RequestParam(value = "ticketid", required = true) String ticketid) {
        ResponseData rd = new ResponseData();
        try {
            prizeService.simulateTicketOdd(ticketid);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("模拟赔率");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/simulateGuanjunOdd", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData simulateGuanjunOdd(@RequestParam(value = "ticketid", required = true) String ticketid) {
        ResponseData rd = new ResponseData();
        try {
            prizeService.simulateGuanjunTicketOdd(ticketid);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("模拟赔率");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/calculateWinBigOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData calculateWinBigOrder(@RequestParam(value = "orderid", required = true) String orderid) {
        ResponseData rd = new ResponseData();
        try {
            prizeService.prizeWinBigLotteryOrder(orderid);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("");
        } catch (Exception e) {
            logger.error("大奖算奖出错",e);
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/sendOrderPrize", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData sendOrderPrize(@RequestParam(value = "orderid", required = true) String orderid,
                                @RequestParam(value = "prizeAmount", required = true) int prizeAmount,
                                @RequestParam(value = "lotterytype", required = true) int lotterytype,
                                @RequestParam(value = "phase", required = true) String phase,
                                @RequestParam(value = "userno", required = true) String userno) {
        ResponseData rd = new ResponseData();
        try {
            prizeService.sendOrderPrize(orderid, 1, new BigDecimal(prizeAmount), false, lotterytype, phase, userno);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/jingcaiFailTicketPrize", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData jingcaiFailTicketPrize(@RequestParam(value = "ticketid", required = true) String[] ticketid) {
        ResponseData rd = new ResponseData();
        try {
            for (String id : ticketid) {
                jingcaiService.calcuteFailJingcaiTicket(id);
            }
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }


    @RequestMapping(value = "/betCompensate", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseData betCompensate(@RequestParam(value = "lotteryType", required = false) int lotterytype, @RequestParam(value = "phase", required = false) String phase,@RequestParam(value = "ticketid", required = false) String[] orders) {
        ResponseData rd = new ResponseData();
        try {
            prizeErrorHandler.betCompensate(lotterytype, phase,orders);
            rd.setErrorCode(ErrorCode.Success.value);
            rd.setValue("开始退款");
        } catch (Exception e) {
            rd.setErrorCode(ErrorCode.system_error.value);
            rd.setValue(e.getMessage());
        }
        return rd;
    }




}
