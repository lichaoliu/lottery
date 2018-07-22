package com.lottery.retrymodel.ticket;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.retrymodel.ApiRetryCallback;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.exchange.IVenderTicketExchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/11/19.
 * 兑奖操作
 */
public class VenderTicketExchangeCallback extends ApiRetryCallback<Object> {
    private Long terminalId;
    private Integer lotteryType;
    private String phase;
    private List<String> ticketIdList;
    private Map<TerminalType,IVenderTicketExchange> venderTicketExchangeMap;
    private TicketService ticketService;
    private VenderConfigHandler venderConfigHandler;
    private Integer ticketResultStatus;
    private Integer agencyExchange;
    private Date startTime;
    private Date endTime;
    @Override
    protected Object execute() throws Exception {
        if (venderTicketExchangeMap==null||venderTicketExchangeMap.isEmpty()||ticketService==null||venderConfigHandler==null){
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
            if(terminalId==null || agencyExchange==null){
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

          //  this.process(allList,terminalId);
        }catch (Exception e){
             logger.error("兑奖出错",e);
        }
        return null;
    }

    protected void process(List<Ticket> ticketList,Long terminalId){
        TerminalType terminalType=venderConfigHandler.getTypeByTerminalId(terminalId);
        IVenderConfig venderConfig=venderConfigHandler.getByTerminalId(terminalId);
        IVenderTicketExchange venderTicketExchange=venderTicketExchangeMap.get(terminalType);
        if (venderTicketExchange==null){
            logger.error("终端:{},兑奖不存在,终端类型是:{}",terminalId,terminalType);
            return;
        }
        venderTicketExchange.exchange(ticketList,venderConfig);

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



    public Map<TerminalType, IVenderTicketExchange> getVenderTicketExchangeMap() {
        return venderTicketExchangeMap;
    }

    public void setVenderTicketExchangeMap(Map<TerminalType, IVenderTicketExchange> venderTicketExchangeMap) {
        this.venderTicketExchangeMap = venderTicketExchangeMap;
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
