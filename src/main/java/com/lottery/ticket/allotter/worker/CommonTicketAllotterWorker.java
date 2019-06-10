package com.lottery.ticket.allotter.worker;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketAllotLog;
import com.lottery.core.domain.ticket.TicketBatch;

import com.lottery.core.service.TicketAllotLogService;

import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.core.terminal.ITerminalSelectorFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import java.util.*;

public class CommonTicketAllotterWorker extends AbstractTicketAllotterWorker {

	@Autowired
	private IdGeneratorService idGeneratorService;
   @Autowired
	protected ITerminalSelector terminalSelector;
   @Autowired
   @Qualifier("specicalTerminalSelector")
   protected ITerminalSelector specialTerminalSelector;

    protected List<Ticket> preAllotTicketList(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo) throws Exception {

        ticketList=this.playTypeSpecifyexecuteAllot(ticketList, lotteryType,phaseNo);
        List<Ticket> remainList=new ArrayList<Ticket>();
        for(Ticket ticket:ticketList){
            PlayType playType=PlayType.get(ticket.getPlayType());
            Long terminalId=specialTerminalSelector.getTopPriorityTerminalIdWithPlayTypeAndAmount(lotteryType,phaseNo,playType,ticket.getAmount(),ticket.getDeadline(),null,ticket.getContent());
            if(terminalId!=null){
                this.saveTicketBatch(terminalId,lotteryType.value,phaseNo,playType,ticket,ticket.getDeadline());
            }else {
                remainList.add(ticket);
            }
        }

        return remainList;
    }




    protected List<Ticket> playTypeSpecifyexecuteAllot(List<Ticket> ticketList,LotteryType lotteryType, String phaseNo) throws Exception{
        Set<PlayType> allPlayTypeSet = new HashSet<PlayType>();
        Set<PlayType> specifiedPlayTypeSet = new HashSet<PlayType>();
        for (Ticket ticket : ticketList) {
            PlayType playType = PlayType.get(ticket.getPlayType());
            if (allPlayTypeSet.contains(playType)) {
                continue;
            }
            allPlayTypeSet.add(playType);
            if (this.specialTerminalSelector.hasSpecifyTerminalConfigForPlayType(playType)) {
                specifiedPlayTypeSet.add(playType);
            }
        }

        // 如果没有需要特殊处理的玩法，直接返回原集合
        if (specifiedPlayTypeSet.isEmpty()) {
            return ticketList;
        }

        // 开始处理需要按玩法分票的内容
        // 首先分离集合，生成要返回的结果集和要处理的结果集
        Map<PlayType, List<Ticket>> preAllotTicketListMap = new HashMap<PlayType, List<Ticket>>();
        List<Ticket> returnTicketList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            PlayType playType = PlayType.get(ticket.getPlayType());
            // 分离存储
            if (specifiedPlayTypeSet.contains(playType)) {
                List<Ticket> preAllotTicketList = preAllotTicketListMap.get(playType);
                if (preAllotTicketList == null) {
                    preAllotTicketList = new ArrayList<Ticket>();
                    preAllotTicketListMap.put(playType, preAllotTicketList);
                }
                preAllotTicketList.add(ticket);
            } else {
                returnTicketList.add(ticket);
            }
        }

        // 遍历要处理的结果集，按玩法进行终端选择
        for (PlayType playType : preAllotTicketListMap.keySet()) {
            for(Ticket ticket:preAllotTicketListMap.get(playType)){
                Long terminalId=specialTerminalSelector.getTopPriorityTerminalIdSpecifyPlayTypeWithAmount(lotteryType,phaseNo,playType,ticket.getDeadline(),ticket.getAmount(),ticket.getContent());
                if(terminalId!=null){
                    this.saveTicketBatch(terminalId,lotteryType.value,phaseNo,playType,ticket,ticket.getDeadline());
                }else {
                    returnTicketList.add(ticket);
                }
            }


        }

        // 将未处理的结果集返回
        return returnTicketList;
    }

    /**
     * 执行带玩法的分票，必须保证传入此方法的票列表玩法相同
     * @param ticketList
     * @param countInBatch
     * @param lotteryType
     * @param phaseNo
     * @param playType
     * @throws Exception
     */
    protected void executeWithPlayType(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo, PlayType playType) throws Exception {

        // 首先将所有票列表按照截止时间升序排列
        Collections.sort(ticketList, new Comparator<Ticket>() {

            @Override
            public int compare(Ticket o1, Ticket o2) {
                // 如果截止时间不一致,按照截止时间升序排列
                if (o1.getTerminateTime() != null && o2.getTerminateTime() != null
                        && o1.getTerminateTime().getTime() != o2.getTerminateTime().getTime()) {
                    return o1.getTerminateTime().compareTo(o2.getTerminateTime());
                }
                // 否则按照ID升序排列
                return o1.getId().compareTo(o2.getId());
            }

        });

        // 开始执行分批打包
        // 保证每个批次的所有票的截止时间相同
        // 保证传入打包操作的所有票为截止时间一致，且票数量不超过数量限制
        int count = 0;
        int sub_start = 0;
        Date deadline = null;

        for (Ticket ticket : ticketList) {
            // 如果批次数量达到限制进行打包
            if (count == countInBatch) {
                this.executeAllot(ticketList.subList(sub_start, sub_start + count), lotteryType, phaseNo, playType);
                // 打包完成后维护计数器
                sub_start += count;
                count = 0;
            }

            if (deadline == null) {
                // 初始化截止时间
                deadline = ticket.getTerminateTime();
            }

            // 如果截止时间发生变化，进行打包
            if (deadline.compareTo(ticket.getTerminateTime()) != 0) {
                if (count > 0) {
                    // 可能存在刚刚满批次打了一包，而当前票刚好发生截止时间变化，所以必须判断count > 0
                    this.executeAllot(ticketList.subList(sub_start, sub_start + count), lotteryType, phaseNo, playType);
                    // 打包完成后维护计数器
                    sub_start += count;
                    count = 0;
                }

                // 标记最新的截止时间
                deadline = ticket.getTerminateTime();
            }

            count ++;
        }

        if (sub_start < ticketList.size()) {
            this.executeAllot(ticketList.subList(sub_start, ticketList.size()), lotteryType, phaseNo, playType);
        }
    }

    @Override
    public void execute(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo) throws Exception {
        if (countInBatch <= 0) {
            // 保证有一个默认值
            countInBatch = 1;
        }

        ticketList = this.preAllotTicketList(ticketList, countInBatch, lotteryType, phaseNo);

        this.executeWithPlayType(ticketList, countInBatch, lotteryType, phaseNo, PlayType.mix);
	}

    /**
     * 进入此方法的票直接打包成批次即可
     * @param ticketList
     * @param lotteryType
     * @param phaseNo
     * @throws Exception
     */
    protected void executeAllot(List<Ticket> ticketList, LotteryType lotteryType,
                                String phaseNo) throws Exception {
        this.executeAllot(ticketList, lotteryType, phaseNo, PlayType.mix);
    }
    protected void clearTicketInfo(Ticket ticket) {
        ticket.setTerminalId(null);
        ticket.setSendTime(null);
        ticket.setBatchId(null);
        ticket.setBatchIndex(null);
        ticket.setExternalId(null);
    }

    @Override
    protected Long executeAllot(Ticket ticket, ITerminalSelectorFilter filter) throws Exception {
        this.clearTicketInfo(ticket);

        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(ticket);

        return this.executeAllot(ticketList, LotteryType.get(ticket.getLotteryType()), ticket.getPhase(), null, filter);
    }
    /**
     * 进入此方法的票直接打包成批次即可，并指定玩法
     * @param ticketList
     * @param lotteryType
     * @param phaseNo
     * @param playType
     * @param filter
     * @return 分配到的终端号
     * @throws Exception
     */
    protected Long executeAllot(List<Ticket> ticketList, LotteryType lotteryType,
                                String phaseNo, PlayType playType, ITerminalSelectorFilter filter) throws Exception {


        // 已经排完序,直接取第一张票的截止时间作为批次截止时间
        Date deadline = ticketList.get(0).getTerminateTime();
        Long terminalId = terminalSelector.getTopPriorityTerminalId(lotteryType, phaseNo, playType, deadline,filter);
        if (terminalId == null) {
            logger.error("({})截止期({})的票未找到可用终端", lotteryType.getName(), CoreDateUtils.formatDateTime(deadline));

            boolean isExpired = deadline.before(new Date());	// 是否已过期

            if (isExpired) {
                // 如果已过期，直接将票置为已撤单状态
                for (Ticket ticket : ticketList) {
                    logger.error("截止期前无可用终端,直接将票置为已撤单,ticketId={},dealline={}", ticket.getId(),CoreDateUtils.DateToStr(deadline));
                    ticket.setStatus(TicketStatus.CANCELLED.value);
                  
                    ticketService.updateTicketStatus(ticket);
                    String error=String.format("未找到彩种(%s)(%s)期的可用终端, 票(%s,%s)分票失败，请检查",
                            ticket.getLotteryType(), ticket.getPhase(),
                            ticket.getId(), ticket.getPlayType());
                    logger.error(error);
                }

            }

            throw new LotteryException(ErrorCode.no_used_terminal,"无可用终端");
        }


       this.saveTicketBatch(terminalId,lotteryType.value,phaseNo,playType,ticketList,deadline);
        return terminalId;
    }

    protected  void saveTicketBatch(Long terminalId,int lotteryType,String phase,PlayType playType,Ticket ticket,Date deadline){
        List<Ticket> ticketList=new ArrayList<Ticket>();
        ticketList.add(ticket);
        this.saveTicketBatch(terminalId,lotteryType,phase,playType,ticketList,deadline);

    }

    protected  void saveTicketBatch(Long terminalId,int lotteryType,String phase,PlayType playType,List<Ticket> ticketList,Date deadline){
        // 找到可用终端，进行分票
        TicketBatch ticketBatch = new TicketBatch();
        ticketBatch.setCreateTime(new Date());
        ticketBatch.setPhase(phase);
        ticketBatch.setTerminateTime(deadline);
        ticketBatch.setLotteryType(lotteryType);
        ticketBatch.setTerminalId(terminalId);
        ticketBatch.setId(idGeneratorService.getBatchTicketId());
        ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);
        if (playType==null){
            playType=PlayType.mix;
        }
        ticketBatch.setPlayType(playType.getValue());
        Terminal terminal=terminalSelector.getTerminal(terminalId);
        if(terminal!=null)
            ticketBatch.setTerminalTypeId(terminal.getTerminalType());
        ticketService.saveAllottedTicketsAndBatch(ticketList, ticketBatch);
        saveTicketAllotLog(ticketList, ticketBatch);

    }
    /**
     * 进入此方法的票直接打包成批次即可，并指定玩法
     * @param ticketList
     * @param lotteryType
     * @param phaseNo
     * @param playType
     * @throws Exception
     */
    protected void executeAllot(List<Ticket> ticketList, LotteryType lotteryType,
                                String phaseNo, PlayType playType) throws Exception {

       this.executeAllot(ticketList, lotteryType, phaseNo, playType,null);
    }
    @Autowired
    private TicketAllotLogService ticketAllotLogService;
    private void saveTicketAllotLog(List<Ticket> ticketList,TicketBatch ticketBatch){
    	try{
    		for(Ticket ticket:ticketList){
    			TicketAllotLog allotLog=new TicketAllotLog();
        		allotLog.setBatchId(ticketBatch.getId());
        		allotLog.setCreateTime(new Date());
        		allotLog.setTerminalId(ticketBatch.getTerminalId());
        		allotLog.setTicketId(ticket.getId());
        		ticketAllotLogService.save(allotLog);
    		}
    	}catch(Exception e){
    		logger.error("票分配到批次id={},日志出错",ticketBatch.getId(),e);
    	}
    }
    



}
