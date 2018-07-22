package com.lottery.ticket.checker.worker;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.print.PrintServerConfig;
import com.lottery.core.domain.print.PrintTicket;
import com.lottery.core.domain.print.PrintTicketInfo;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.print.PrintServerConfigService;
import com.lottery.core.service.print.PrintTicketInfoServer;
import com.lottery.core.service.print.PrintTicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConverter;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqinyun on 16/11/10.
 */
@Component
public class LetouVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {
    @Autowired
    private PrintTicketService printTicketService;

    @Autowired
    private PrintTicketInfoServer printTicketInfoServer;
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_LETOU;
    }

    @Override
    public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
        List<TicketVender> ticketvenderList = new ArrayList<TicketVender>();
        for(Ticket ticket:ticketList){
            try {
                ticketvenderList.add(process(ticket,venderConverter));
            }catch (Exception e){
                logger.error("ticketId={}",ticket.getId());
                logger.error("检票出错",e);
            }

        }

        return ticketvenderList;
    }


    private TicketVender  process(Ticket ticket,IVenderConverter venderConverter){
        TicketVender ticketVender = createTicketVender(ticket.getTerminalId(), getTerminalType());
        ticketVender.setId(ticket.getId());

        PrintTicket printTicket=printTicketService.get(ticket.getId());//查询printTicket 里面的status,注意赔率解析
        if(printTicket==null){
            ticketVender.setStatus(TicketVenderStatus.not_found);
        }else {
            if(printTicket.getStatus()== TicketStatus.PRINT_SUCCESS.value){

                PrintTicketInfo printTicketInfo=printTicketInfoServer.get(ticket.getId());
                if(printTicketInfo==null){
                    ticketVender.setStatus(TicketVenderStatus.printing);
                   return ticketVender;
                }

                String jsonString=printTicketInfo.getPrintStub();
                JSONObject jsonObject=JSONObject.fromObject(jsonString);


                String tickeinfo=jsonObject.getString("stub");
                ticketVender.setStatus(TicketVenderStatus.success);
                ticketVender.setSerialId(printTicket.getSerialId());
                if (LotteryType.getJclqValue().contains(ticket.getLotteryType()) || LotteryType.getJczqValue().contains(ticket.getLotteryType())) {
                    //竞彩需要解析赔率
                    ticketVender.setOtherPeilv(tickeinfo);
                    String peilv = venderConverter.convertSp(ticket,tickeinfo);
                    ticketVender.setPeiLv(peilv);
                }

            }else if(printTicket.getStatus()== TicketStatus.PRINTING.value||printTicket.getStatus()== TicketStatus.UNSENT.value||printTicket.getStatus()==TicketStatus.UNINIT.value){
                ticketVender.setStatus(TicketVenderStatus.printing);
            }else if(printTicket.getStatus()== TicketStatus.CHANGING.value){//重新转换终端,当出票中处理
                ticketVender.setStatus(TicketVenderStatus.printing);
            }else{
                ticketVender.setStatus(TicketVenderStatus.failed);//暂时以失败处理

            }
        }
       return ticketVender;
    }
}
