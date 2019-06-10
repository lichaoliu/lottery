package com.lottery.controller;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.service.TerminalService;
import com.lottery.ticket.vender.notice.IVenderNoticeProcess;
import com.lottery.ticket.vender.notice.impl.Gzcp.GzcpNoticeProcess;
import com.lottery.ticket.vender.notice.impl.HuAi.HuAiNoticeProcess;
import com.lottery.ticket.vender.notice.impl.Huancai.HuancaiNoticeProcess;
import com.lottery.ticket.vender.notice.impl.JYDP.JYDPNoticeProcess;
import com.lottery.ticket.vender.notice.impl.JinNuo.JinNuoNoticeProcess;
import com.lottery.ticket.vender.notice.impl.Shcp.ShcpNoticeProcess;
import com.lottery.ticket.vender.notice.impl.ZhangYi.ZYNoticeProcess;

import com.lottery.ticket.vender.notice.impl.letou.LetouVenderNoticeProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;


/**
 * Created by fengqinyun on 15/6/17.
 * 出票商通知类
 */

@Controller
@RequestMapping("/notice")
public class VenderNoticeController {
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    protected GzcpNoticeProcess gzcpNoticeProcess;
    @Autowired
    protected HuAiNoticeProcess huAiNoticeProcess;
    @Autowired
    protected ZYNoticeProcess zyNoticeProcess;
    @Autowired
    protected HuancaiNoticeProcess huancaiNoticeProcess;
    @Autowired
    protected JinNuoNoticeProcess jinnuoNoticeProcess;
    @Autowired
    protected JYDPNoticeProcess jydpNoticeProcess;
    @Autowired
    protected ShcpNoticeProcess shcpNoticeProcess;
    @Autowired
    private LetouVenderNoticeProcess letouVenderNoticeProcess;
    @Autowired
    private TerminalService terminalService;

    @Resource(name = "venderNoticeProcessMap")
    protected Map<TerminalType, IVenderNoticeProcess> venderNoticeProcessMap;



    @RequestMapping("/taobao")
    public  void taobao(HttpServletRequest request,HttpServletResponse response){

        try {
            String toke=this.readRequestString(request, CharsetConstant.CHARSET_UTF8);

            logger.error("获取的token={}",toke);
        } catch (Exception e) {
            logger.error("金诺出票通知处理出错",e);
        }
    }

    @RequestMapping("/{agentId}/ticket")
    public void ticketnotice(@PathVariable Long agentId, HttpServletRequest request, HttpServletResponse response){
        try{
            String ip=getIp(request);
            String requestString=readRequestString(request,CharsetConstant.CHARSET_UTF8);
            if (getVenderNoticeProcess(agentId)!=null){
                String responseStr=getVenderNoticeProcess(agentId).process(requestString,ip,agentId);
                this.responseStr(response,responseStr);
            }else {
                this.responseStr(response,"error");
            }
        }catch (Exception e){
            logger.error("通知处理出错",e);
        }

    }



    private IVenderNoticeProcess getVenderNoticeProcess(Long terminalId){
        Terminal terminal=terminalService.get(terminalId);
        if(terminal!=null){
            return venderNoticeProcessMap.get(TerminalType.get(terminal.getTerminalType()));
        }
        logger.error("terminalId={}的终端未找到",terminalId);
        return null;
    }

    protected String readRequestString(HttpServletRequest request,String charset) throws IOException {
        request.setCharacterEncoding(charset);
        BufferedReader reader = request.getReader();
        StringBuilder buffer = new StringBuilder();
        int n;
        while ((n = reader.read()) != -1) {
            buffer.append((char) n);
        }
        reader.close();
        return buffer.toString();
        //return URLDecoder.decode(buffer.toString(), charset);
    }

    private void responseStr(HttpServletResponse response,String responseString)throws IOException{
        response.getWriter().print(responseString);
        response.getWriter().close();
    }

    private String getIp(HttpServletRequest request){
        return CoreHttpUtils.getClientIP(request);
    }

}
