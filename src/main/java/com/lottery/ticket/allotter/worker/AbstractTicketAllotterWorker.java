package com.lottery.ticket.allotter.worker;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.PairValue;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryTicketConfigService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.TicketServiceCache;
import com.lottery.core.terminal.ITerminalSelectorFilter;
import com.lottery.core.terminal.impl.TicketFailureCacheTerminalSelectorFilter;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractTicketAllotterWorker implements ITicketAllotWorker {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
	private LotteryTicketConfigService ticketConfigService;
	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected TicketServiceCache ticketServiceCache;
	
	private int countInBatchDefault=10;

    private long countInBatchTimeoutMillis = 90000L;      // 先默认一分钟缓存

    protected Lock lock = new ReentrantLock();

    /**
     * PairValue左值为每批数量，右值为建立时的毫秒数
     */
    private Map<LotteryType, PairValue<Integer, Long>> countInBatchLotteryMap = new ConcurrentHashMap<LotteryType, PairValue<Integer, Long>>(16);
	
    @Override
    abstract public void execute(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo) throws Exception;

    protected int getCountInBatch(LotteryType lotteryType) {
        long currentTimeMillis = System.currentTimeMillis();

        // 从Map中取值
        PairValue<Integer, Long> pairValue = countInBatchLotteryMap.get(lotteryType);

        if (pairValue != null && pairValue.getRight() != null && pairValue.getRight() + countInBatchTimeoutMillis > currentTimeMillis) {
            // 如果缓存已存在且未过期，直接使用缓存
            return pairValue.getLeft();
        }

        // 否则尝试更新缓存
        
        try{
        	lock.lock();
        	  // 先重新获取一遍，确认在之前未加锁情况下是否已有其他线程更新
            pairValue = countInBatchLotteryMap.get(lotteryType);
            if (pairValue != null && pairValue.getRight() != null && pairValue.getRight() + countInBatchTimeoutMillis > currentTimeMillis) {
                // 如果缓存已存在且未过期，直接使用缓存返回并解锁
           
                return pairValue.getLeft();
            }

            int countInBatch = this.getCountInBatchDefault();
            
            LotteryTicketConfig lotteryTicketConfig = ticketConfigService.get(LotteryType.getPhaseType(lotteryType));

            if (lotteryTicketConfig != null && lotteryTicketConfig.getBatchCount() != null) {
                countInBatch = lotteryTicketConfig.getBatchCount();
            }
            pairValue = new PairValue<Integer, Long>(countInBatch, currentTimeMillis);
            countInBatchLotteryMap.put(lotteryType, pairValue);            
            return countInBatch;
        }catch(Exception e){
          return countInBatchDefault;	
        }finally{
        	// 解锁
            lock.unlock();
        }

      

        
    }

    @Override
	public List<Ticket> execute(List<Ticket> ticketList, LotteryType lotteryType,
			String phaseNo) throws Exception {

        if (ticketList == null || ticketList.isEmpty()) {
           logger.info("分票器需要打包的票数为空，直接返回");
            return null;
        }

        // 一个批次中的票数
        int countInBatch = this.getCountInBatch(lotteryType);

        this.execute(ticketList, countInBatch, lotteryType, phaseNo);
        return null;
	}

    @Override
    public Long executeWithFailureCheck(Ticket ticket, Long failedTerminalId) throws Exception {
        // 先记录当前终端号的失败缓存
        if (failedTerminalId != null) {
        	ticketServiceCache.setTicketFailureTerminalIdCache(ticket.getId(), failedTerminalId);
        }

        TicketFailureCacheTerminalSelectorFilter filter = new TicketFailureCacheTerminalSelectorFilter(ticket.getId());
        filter.setTicketServiceCache(ticketServiceCache);

        return this.executeAllot(ticket, filter);
    }
    @Override
    public Long executeWithFailureCheck(Ticket ticket) throws Exception {
        return this.executeWithFailureCheck(ticket, ticket.getTerminalId());
    }
    /**
     * 进入此方法的票直接打包成批次即可
     * @param ticket
     * @param filter
     * @return 分配到的终端号
     * @throws Exception
     */
    abstract protected Long executeAllot(Ticket ticket, ITerminalSelectorFilter filter) throws Exception;
    public List<Ticket> specifyExecute(List<Ticket> ticketList, LotteryType lotteryType, String phaseNo) throws Exception{
        return ticketList;
    }
    @Override
    public void execute(Ticket ticket) throws Exception {
        this.executeAllot(ticket, null);
    }
	public int getCountInBatchDefault() {
		return countInBatchDefault;
	}

	public void setCountInBatchDefault(int countInBatchDefault) {
		this.countInBatchDefault = countInBatchDefault;
	}


}
