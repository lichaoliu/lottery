/**
 * 
 */
package com.lottery.ticket.sender.worker.thread;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.core.domain.ticket.TicketBatch;

/**
 * 每个终端一个送票线程的多线程送票实现
 * 和MultiThreadTerminalTicketSenderRunnable区别是这个实现本身不自己取票
 * @author fengqinyun
 *
 */
@Component("singletonTicketSendDispatcher")
public class MultiThreadTerminalSingletonTicketSendDispatcher extends MultiThreadTerminalTicketSenderRunnable implements ITicketSendDispatcher {

    @Override
    protected void executeRun() {
        if (this.ticketSendWorker == null) {
            logger.error("未绑定送票器");
            return;
        }


        logger.info("准备开始执行送票线程");
        this.execute();
    }

    @Override
    public void execute() {

    }

    @Override
    public void dispatch(TicketBatch ticketBatch) throws Exception {

    	  Long terminalId = ticketBatch.getTerminalId();
          if (terminalId == null || terminalId <= 0) {
              throw new RuntimeException("进入送票任务分发的批次终端号不正确, terminalId=" + terminalId);
          }

         
          // 处理成中间态
          ticketBatch.setStatus(TicketBatchStatus.SEND_QUEUED.value);
          ticketBatchService.updateTicketBatchStatus(ticketBatch);
          // 分派到子线程
          logger.info("获取送票子线程， terminalId = {}", terminalId);
          MultiThreadInstanceTicketSenderRunnable runnable = this.initInstance(terminalId);
          runnable.addQueue(ticketBatch);
          runnable.executeNotify();
    }
    @PreDestroy
    protected void desroySingleton(){
    	try {
            destroyInstance();
        } catch (Exception e) {
            logger.error("送票子线程销毁失败", e);
        }
    }
}
