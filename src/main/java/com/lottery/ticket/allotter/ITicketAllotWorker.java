/**
 * 
 */
package com.lottery.ticket.allotter;

import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.ticket.Ticket;

/**
 * 
 *
 */
public interface ITicketAllotWorker {

    /**
     * 将票打包成批次并分配可用终端
     * 如果已过截止期的票，将票设置失败
     * @param ticketList
     * @param lotteryType
     * @param phaseNo
     * @throws Exception
     */
	public List<Ticket> execute(List<Ticket> ticketList, LotteryType lotteryType, String phaseNo) throws Exception;


    /**
     * 将票打包成批次（指定每批票的数量）并分配可用终端
     * 如果已过截止期的票，将票设置失败
     * @param ticketList
     * @param countInBatch
     * @param lotteryType
     * @param phaseNo
     * @throws Exception
     */
    public void execute(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo) throws Exception;
    /**
     * 对单张票进行异常处理, 如已有终端号先记录该终端的失败记录, 然后排除掉所有曾经失败的终端尝试进行分票
     * @param ticket 票号
     * @param failedTerminalId 当前失败的终端号, 直接传入供送票时票信息内没有终端号的情况处理
     * @return 如果分票成功返回分配的终端号
     * @throws Exception
     */
    public Long executeWithFailureCheck(Ticket ticket, Long failedTerminalId) throws Exception;
    /**
     * 对单张票进行异常处理, 如已有终端号先记录该终端的失败记录, 然后排除掉所有曾经失败的终端尝试进行分票
     * @param ticket 票号
     * @return 如果分票成功返回分配的终端号
     * @throws Exception
     */
    public Long executeWithFailureCheck(Ticket ticket) throws Exception;

    public void execute(Ticket ticket) throws Exception;



   
}
