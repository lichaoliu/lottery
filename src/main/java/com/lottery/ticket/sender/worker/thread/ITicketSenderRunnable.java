package com.lottery.ticket.sender.worker.thread;

import com.lottery.common.thread.IThreadRunnable;

public interface ITicketSenderRunnable extends IThreadRunnable {

    /**
     * 重新加载
     * */
    public void reload()throws Exception;
}
