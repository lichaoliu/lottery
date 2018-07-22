package com.lottery.ticket.vender.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fengqinyun on 15/6/12.
 * 通知类处理
 */

public interface IVenderNoticeProcess {


    /**
     * @param requestString 请求字符串
     * @param requestIp 请求ip
     * @param terminalId 终端号
     * */
    public  String process(String requestString,String requestIp,Long terminalId) throws Exception;
}
