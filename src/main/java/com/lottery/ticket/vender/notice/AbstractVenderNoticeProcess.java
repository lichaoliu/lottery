package com.lottery.ticket.vender.notice;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TerminalPropertyConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.common.util.JsonUtil;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/6/16.
 */
public abstract class AbstractVenderNoticeProcess implements IVenderNoticeProcess{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    protected final static String TICKET_NOTICE="1";//出票结果通知

    protected final static String PHASE_NOTICE="2";//新期通知
    protected final static String PHASE_RESULT_NOTICE="3";//期开奖结果通知

    protected final static String TICKET_PRIZE_NOTICE="4";//票中奖通知



    @Autowired
    protected ITerminalSelector terminalSelector;

    @Autowired
    protected IdGeneratorService idGeneratorService;
    @Autowired
    protected TicketService ticketService ;
    @Autowired
    protected VenderConfigHandler venderConfigService;

    @Autowired
    protected QueueMessageSendService queueMessageSendService;

    @Resource(name = "venderNoticeProcessMap")
    protected Map<TerminalType, IVenderNoticeProcess> venderNoticeProcessMap;

    @Resource(name = "venderConverterBinder")
    protected Map<TerminalType, IVenderConverter> venderConverterBinder;

    protected ThreadLocal<Long> terminalIdThreadLocal = new ThreadLocal<Long>();

    protected ThreadLocal<IVenderConfig> venderConfigThreadLocal = new ThreadLocal<IVenderConfig>();

    @Autowired
    private LotteryPhaseHandler phaseHandler;





    protected String getCharset() {
        return CharsetConstant.CHARSET_UTF8;
    }

    abstract protected TerminalType getTerminalType();

    protected abstract String getAgentId();
    protected String getAgentCode(Map<String,String> requestMap){
        String agentId=getAgentId();
        if (agentId!=null){
            return requestMap.get(agentId);
        }
        return null;
    }

    protected IVenderConverter getVenderConverter(){
        return this.venderConverterBinder.get(getTerminalType());
    }

    protected Map<String, String> convertRequestParameter(String requestString) {
        return CoreHttpUtils.parseQueryString(requestString);
    }


    protected  boolean isEmpty(String requestString){
        if (StringUtils.isBlank(requestString)) {
            // 空消息不做处理
            logger.error("收到空消息, 不做处理, 终端类型为: {}", this.getTerminalType());
            return true;
        }
        return false;
    }



    public String process(String requestString,String requestIp,Long terminalId)throws Exception{


        this.terminalIdThreadLocal.set(terminalId);
        if (isEmpty(requestString)) {
            return null;
        }
        requestString=getRequestString(requestString);
        Map<String,String> requestMap = this.convertRequestParameter(requestString);

        if (requestMap == null) {
            logger.error("解析请求参数出错为空,直接返回,参数为:{},ip={}",requestString,requestIp);
            return null;
        }


        IVenderConfig venderConfig= venderConfigService.getByTerminalId(terminalId);
        if (terminalId==null||venderConfig==null){
            logger.error("terminalId:{},venderconfig:{},不能同时为空",terminalId,venderConfig);
            return null;
        }

        this.venderConfigThreadLocal.set(venderConfig);

        writeNoticeLog("接收ip=["+requestIp+"]的原始字符串:"+requestString);
        String responstStr=null;
        boolean flag=this.validate(requestMap, venderConfig);
        if (!flag){
            writeNoticeLog("加密验证未通过");
            responstStr="validate error";
        }else{
            responstStr=execute(requestMap,venderConfig);
        }
        writeNoticeLog("通知返回:"+responstStr);
       return responstStr;


    }

    protected String getRequestString(String requestString) throws Exception{
        return requestString;
    }

    /**
     * 加密验证
     * */
    protected  abstract boolean validate(Map<String,String> requestMap,IVenderConfig venderConfig);



    abstract protected  String execute(Map<String,String> requestMap,IVenderConfig venderConfig);





    protected void writeNoticeLog(String requestString){
        try {
            Long terminalId = terminalIdThreadLocal.get();
            Logger notifyLogger = LoggerFactory.getLogger("notify_logger");

            if(terminalId==null)
                terminalId=0l;

            MDC.put("terminalId", terminalId+"");
            notifyLogger.error(requestString);
            logger.error("notify-{}:{}",terminalId,requestString);
        }catch (Exception e){
         logger.error("写入日志出错",e);
        }

    }

    protected TicketVender createInit(){
        TicketVender ticketVender = new TicketVender();
        ticketVender.setTicketNotify(true);
        ticketVender.setTerminalType(this.getTerminalType());
        ticketVender.setTerminalId(terminalIdThreadLocal.get());
        return ticketVender;
    }


    protected void updateLotteryPhase(int lotteryType, String phase, Date startTime, Date endTime,int number){
        phaseHandler.updateCurrentPhase(lotteryType,phase,startTime,endTime,number);
    }




    protected void ticketVenderProcess(TicketVender	ticketVender){
        try{
            QueueName queueName=QueueName.ticketNotice;
            String body= JsonUtil.toJson(ticketVender);
            if (LotteryType.getJcValue().contains(ticketVender.getLotteryType())){
                queueName=QueueName.ticketNoticeJingcai;
            }
            queueMessageSendService.sendMessage(queueName,body);
        }catch (Exception e){
            logger.error("发送通知消息体出错",e);
        }
    }


    @PostConstruct
    private void init(){
        venderNoticeProcessMap.put(getTerminalType(),this);
    }

}
