package com.lottery.ticket.vender.notice.impl;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;

import com.lottery.common.util.DESCoder;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.*;

/**
 * Created by fengqinyun on 2017/4/1.
 */
@Component
public class OwnCenterVenderNoticeProcess  extends AbstractVenderNoticeProcess {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_OWN_CENTER;
    }


    protected Map<String, String> convertRequestParameter(String requestString) {
        Map<String, String> map = new HashMap<String, String>();
        try{
            Document doc = DocumentHelper.parseText(requestString);
            Element rootElt = doc.getRootElement();
            Element head = rootElt.element("head");
            String merchant = head.elementText("merchant");
            String body = rootElt.elementText("body");
            map.put("merchant",merchant);
            map.put("body",body);
        }catch (Exception e){
           logger.error("xml解析出错",e);
        }

        return map;
    }

    @Override
    protected String getAgentId() {
        return "merchant";
    }

    @Override
    protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
        return true;
    }

    @Override
    protected String execute(Map<String, String> requestMap, IVenderConfig venderConfig) {
        try{
            String body = DESCoder.desDecrypt(requestMap.get("body"), venderConfig.getKey());
            logger.error("解析加密字符串:{}",body);
            Document bodydoc=DocumentHelper.parseText(body);
            List<?> projects=bodydoc.selectNodes("message/orderlist/order");
            Iterator it=projects.iterator();
            while (it.hasNext()){
                Element elm=(Element)it.next();
                String status=elm.elementText("errorcode");
                String ticketId=elm.elementText("orderid");
                TicketVender ticketVender = createInit();
                ticketVender.setId(ticketId);
                if("0".equals(status)){
                    ticketVender.setStatus(TicketVenderStatus.success);
                    Element tikcetList=elm.element("ticketlist");
                    String sp=null;
                    if (tikcetList!=null){
                        List<?> spList=tikcetList.elements();
                        for(Iterator its =  spList.iterator();its.hasNext();){
                            Element chileEle = (Element)its.next();
                            sp=chileEle.attributeValue("sp");
                        }
                    }
                    ticketVender.setPeiLv(sp);
                }else if("2".equals(status)){
                    ticketVender.setStatus(TicketVenderStatus.failed);
                }else {
                    ticketVender.setStatus(TicketVenderStatus.printing);
                }
                ticketVender.setPrintTime(new Date());
                this.ticketVenderProcess(ticketVender);
            }
        }catch (Exception e){
         logger.error("处理通知出错",e);
        }

        return "<errorcode>0</errorcode>";
    }
    @Override
    protected String getRequestString(String requestString) throws Exception{
        return URLDecoder.decode(requestString, CharsetConstant.CHARSET_UTF8);
    }

}
