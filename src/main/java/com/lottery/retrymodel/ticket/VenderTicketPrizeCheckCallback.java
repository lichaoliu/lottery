package com.lottery.retrymodel.ticket;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.retrymodel.ApiRetryCallback;
import com.lottery.ticket.IVenderConfig;

import com.lottery.ticket.vender.prizecheck.IVenderTicketPrizeCheck;

import java.util.*;

/**
 * Created by fengqinyun on 15/11/19.
 * 奖金核对
 */
public class VenderTicketPrizeCheckCallback extends ApiRetryCallback<Object> {
    private Long terminalId;
    private Integer lotteryType;
    private String phase;
    private List<String> ticketIdList;
    private Map<TerminalType,IVenderTicketPrizeCheck> venderTicketPrizeCheckMap;
    private TicketService ticketService;
    private VenderConfigHandler venderConfigHandler;
    private Integer ticketResultStatus;
    private Integer agencyExchange;
    private Date startTime;
    private Date endTime;
    @Override
    protected Object execute() throws Exception {
        if (venderTicketPrizeCheckMap==null||venderTicketPrizeCheckMap.isEmpty()||ticketService==null||venderConfigHandler==null){
            throw new RuntimeException("参数不全");
        }
        
        try {
            if (ticketIdList!=null&&ticketIdList.size()>0) {
                Map<Long, List<Ticket>> mapList = new HashMap<Long, List<Ticket>>();
                for (String id : ticketIdList) {
                    Ticket ticket = ticketService.getTicket(id);
                    if (ticket != null) {
                        List<Ticket> ticketList = mapList.get(ticket.getTerminalId());
                        if (ticketList == null) {
                            ticketList = new ArrayList<Ticket>();
                            ticketList.add(ticket);
                            mapList.put(ticket.getTerminalId(), ticketList);
                        } else {
                            ticketList.add(ticket);
                        }
                    }

                }

                for (Map.Entry<Long, List<Ticket>> entry : mapList.entrySet()) {
                    this.process(entry.getValue(),entry.getKey());
                }
                return  null;
            }
            if(terminalId == null || agencyExchange == null){
            	return null;
            }
            PageBean<Ticket> pageBean = new PageBean<Ticket>();
            int max=15;
            pageBean.setMaxResult(max);
            pageBean.setTotalFlag(false);
            int page=1;
          
            while (true){
                pageBean.setPageIndex(page);
                List<Ticket> ticketList=ticketService.getUnVenderExcechange(lotteryType,phase,ticketResultStatus,agencyExchange,terminalId,startTime,endTime,pageBean);
                if (ticketList!=null&&ticketList.size()>0){
                    this.process(ticketList, terminalId);
                	//allList.addAll(ticketList);
                    if (ticketList.size()<max){

                        break;
                    }
                }else {

                    break;
                }
                page++;
            }

           // this.process(allList, terminalId);
        }catch (Exception e){
             logger.error("奖金核对出错",e);
        }
        return null;
    }

    protected void process(List<Ticket> ticketList,Long terminalId){
        TerminalType terminalType=venderConfigHandler.getTypeByTerminalId(terminalId);
        IVenderConfig venderConfig=venderConfigHandler.getByTerminalId(terminalId);
        IVenderTicketPrizeCheck venderTicketExchange=venderTicketPrizeCheckMap.get(terminalType);
        if (venderTicketExchange==null){
            logger.error("终端:{},兑奖不存在,终端类型是:{}",terminalId,terminalType);
            return;
        }
        venderTicketExchange.check(ticketList,venderConfig);

    }

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }




    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    public List<String> getTicketIdList() {
        return ticketIdList;
    }

    public void setTicketIdList(List<String> ticketIdList) {
        this.ticketIdList = ticketIdList;
    }

    public VenderConfigHandler getVenderConfigHandler() {
        return venderConfigHandler;
    }

    public void setVenderConfigHandler(VenderConfigHandler venderConfigHandler) {
        this.venderConfigHandler = venderConfigHandler;
    }

    public Integer getTicketResultStatus() {
        return ticketResultStatus;
    }

    public void setTicketResultStatus(Integer ticketResultStatus) {
        this.ticketResultStatus = ticketResultStatus;
    }

    public Integer getAgencyExchange() {
        return agencyExchange;
    }

    public void setAgencyExchange(Integer agencyExchange) {
        this.agencyExchange = agencyExchange;
    }

    public Map<TerminalType, IVenderTicketPrizeCheck> getVenderTicketPrizeCheckMap() {
        return venderTicketPrizeCheckMap;
    }

    public void setVenderTicketPrizeCheckMap(Map<TerminalType, IVenderTicketPrizeCheck> venderTicketPrizeCheckMap) {
        this.venderTicketPrizeCheckMap = venderTicketPrizeCheckMap;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
