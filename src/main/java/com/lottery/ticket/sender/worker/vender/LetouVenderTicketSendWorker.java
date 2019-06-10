package com.lottery.ticket.sender.worker.vender;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.print.PrintServerConfig;
import com.lottery.core.domain.print.PrintTicket;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.print.PrintServerConfigService;
import com.lottery.core.service.print.PrintTicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.letou.LeTouLotteryDef;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fengqinyun on 16/11/10.
 */
@Component
public class LetouVenderTicketSendWorker extends AbstractVenderTicketSendWorker{
    @Autowired
    private PrintServerConfigService printServerConfigService;
    @Autowired
    private PrintTicketService printTicketService;
    @Override
    protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType, IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
        try {
            return process(ticketList,venderConverter);
        }catch (Exception e){
            List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
            processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, "", "", e.getMessage());
            logger.error("送票出错",e);
            return ticketSendResultList;
        }

    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_LETOU;
    }

    private   List<TicketSendResult>  process(List<Ticket> ticketList,IVenderConverter venderConverter){
        List<TicketSendResult> ticketSendResultList=new ArrayList<TicketSendResult>();
        List<PrintServerConfig> printServerConfigList=printServerConfigService.getEnablePrintServer();
//        if(printServerConfigList==null||printServerConfigList.size()==0){
//            for (Ticket ticket:ticketList){//按送票失败处理
//                TicketSendResult ticketSendResult=createInitializedTicketSendResult(ticket);
//                ticketSendResult.setStatus(TicketSendResultStatus.failed);
//                ticketSendResultList.add(ticketSendResult);
//            }
//
//        }else {
//
//        }

        for (Ticket ticket:ticketList){
            ticketSendResultList.add(savePrintTicket(ticket,venderConverter));
        }
        return ticketSendResultList;
    }





    private  TicketSendResult savePrintTicket(Ticket ticket,IVenderConverter venderConverter){
        TicketSendResult ticketSendResult=createInitializedTicketSendResult(ticket);

        PrintTicket printTicket=printTicketService.get(ticket.getId());
        if(printTicket!=null){
            ticketSendResult.setStatus(TicketSendResultStatus.duplicate);//按重复出票处理
            return ticketSendResult;
        }
        printTicket=new PrintTicket();
        printTicket.setId(ticket.getId());
        printTicket.setContent(ticket.getContent());
        printTicket.setLotteryType(ticket.getLotteryType());
        printTicket.setPhase(ticket.getPhase());
        printTicket.setAmount(ticket.getAmount());
        printTicket.setPlayType(ticket.getPlayType());
        printTicket.setCreateTime(new Date());
        printTicket.setEndTime(ticket.getDeadline());
        printTicket.setStatus(TicketStatus.UNINIT.value);
        printTicket.setTicketResultStatus(TicketResultStatus.not_draw.value);
        printTicket.setAddition(ticket.getAddition());
        printTicket.setAgencyPrize(BigDecimal.ZERO);
        printTicket.setExchangeCount(0);
        printTicket.setPrintCount(0);
        printTicket.setMultiple(ticket.getMultiple());
        printTicket.setIsExchanged(YesNoStatus.no.value);
        printTicket.setIsPrint(YesNoStatus.no.value);
        printTicket.setPosttaxPrize(BigDecimal.ZERO);
        printTicket.setPretaxPrize(BigDecimal.ZERO);
        printTicket.setIsPriority(YesNoStatus.no.value);
        //存入对方的票信息
        if (LotteryType.getJczqValue().contains(ticket.getLotteryType())&&String.valueOf(ticket.getPlayType()).endsWith("1001")) {
        	printTicket.setOtherLotteryType("202");
        }else{
        	 printTicket.setOtherLotteryType(venderConverter.convertLotteryType(ticket.getLotteryType()));
        }
       
        printTicket.setOtherPhase(venderConverter.convertPhase(ticket.getLotteryType(),ticket.getPhase()));
        String content=venderConverter.convertContent(ticket);
        if(StringUtils.isBlank(content)){
            logger.error("票:{},转换内容为空",ticket.getId());
            ticketSendResult.setStatus(TicketSendResultStatus.failed);
            return ticketSendResult;
        }
        printTicket.setOtherContent(content);//对方出票内容需要做

        if (LotteryType.getJczqValue().contains(ticket.getLotteryType()) || LotteryType.getJclqValue().contains(ticket.getLotteryType())) {
            printTicket.setOtherPlayType(LeTouLotteryDef.toLotteryTypeMap.get(LotteryType.get(ticket.getLotteryType())));
        }else {
            //toplayTypeMap 对应 OtherPlayType
            printTicket.setOtherPlayType(LeTouLotteryDef.toplayTypeMap.get(ticket.getPlayType()));
        }

        if(ticket.getLotteryType()==LotteryType.CJDLT.value){
        	if(ticket.getAddition()== YesNoStatus.yes.value){
                printTicket.setOtherPlayType("60");//追加
            }else{
            	printTicket.setOtherPlayType("10");//标准
            }
        }
        printTicket.setOtherBetType(LeTouLotteryDef.playTypeMap.get(ticket.getPlayType()));

        int zhushu=(ticket.getAmount().divide(new BigDecimal(100)).intValue())/ticket.getMultiple();
        zhushu=zhushu/2;
        if(ticket.getAddition()==YesNoStatus.yes.value){
            zhushu=zhushu/3;
        }
        printTicket.setIcount(zhushu);

        printTicketService.save(printTicket);

        ticketSendResult.setStatus(TicketSendResultStatus.success);
        return ticketSendResult;
    }
    
   

}
