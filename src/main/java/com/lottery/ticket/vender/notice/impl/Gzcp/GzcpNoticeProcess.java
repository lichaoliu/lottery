package com.lottery.ticket.vender.notice.impl.Gzcp;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gzcp.GzcpConfig;
import com.lottery.ticket.vender.impl.gzcp.GzcpDes;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/6/17.
 */
@Component
public class GzcpNoticeProcess extends AbstractVenderNoticeProcess {


    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_GZCP;
    }


    @Override
    protected String getAgentId() {
        return "venderId";
    }

    protected Map<String, String> convertRequestParameter(String requestString) {
        Map<String, String> map = new HashMap<String, String>();

        try {
            Document doc = DocumentHelper.parseText(requestString);
            Element rootElt = doc.getRootElement();
            Element el = rootElt.element("head");
            String venderId = el.elementText("venderId");
            String bodyencode = rootElt.elementText("body");
            map.put("body", bodyencode);
            map.put("venderId", venderId);
            return map;
        } catch (DocumentException e) {
            logger.error("解析xml异常", e);
        }

        return null;
    }

    @Override
    protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
        return true;
    }


    @Override
    protected String execute(Map<String, String> requestMap, IVenderConfig venderConfig) {

        //将具体信息解析

        String bodyStr = null;
        try {
            String body = requestMap.get("body");
            bodyStr = GzcpDes.des3DecodeCBC(venderConfig.getKey(), body);
        } catch (Exception e) {
            logger.error("body解密异常", e);
        }
        if (StringUtils.isEmpty(bodyStr)) {
            logger.error("解析完的body为空");
            return null;
        }
        writeNoticeLog("加密串解密:" + bodyStr);
        String lotteryType = null;

        try {

            Document document = DocumentHelper.parseText(bodyStr);
            Element root = document.getRootElement();
            Element records = root.element("records");
            @SuppressWarnings("unchecked")
            List<Element> list = records.elements("record");
            List<HashMap<String, String>> mapList = XmlParse.getElementTextList("records/", "record", bodyStr);
            String messageId = idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
            String noticeCode = "1100";
            if (GzcpUtil.spLotteryType.containsKey(lotteryType)) {
                noticeCode = "1101";
            }

            XmlParse xmlParse = GzcpConfig.addGzcpHead(noticeCode, venderConfig.getAgentCode(), messageId);
            XmlParse xml = new XmlParse("body");
            Element returnBody = xml.getBodyElement().addElement("records");

            if (mapList != null && mapList.size() > 0) {
                int i = 0;
                for (Map<String, String> map : mapList) {
                    TicketVender ticketVender = createInit();//参照检票操作
                    ticketVender.setTerminalIdJudge(false);
                    String status = map.get("result");
                    String externalId = map.get("ticketId");
                    String ticketId = map.get("id");
                    ticketVender.setId(ticketId);
                    ticketVender.setExternalId(externalId);
                    if ("200021".equals(status)) {//出票中
                        ticketVender.setStatus(TicketVenderStatus.printing);
                    } else if ("0".equals(status)) {// 成功
                        ticketVender.setStatus(TicketVenderStatus.success);
                        Ticket ticket = ticketService.getTicket(ticketId);
                        if (ticket == null) {
                            continue;
                        }
                        ticketVender.setLotteryType(ticket.getLotteryType());
                        String info = null;
                        if (list.get(i).element("info") != null) {
                            info = list.get(i).element("info").asXML();
                            GzcpUtil gzcpUtil = new GzcpUtil();
                            String peilv = null;
                            try {
                                ticketVender.setOtherPeilv(info);
                                peilv = gzcpUtil.convertPeilu(info, LotteryType.get(ticket.getLotteryType()), ticket.getContent(), getVenderConverter());
                            } catch (Exception e) {
                                logger.error("解析赔率异常");
                            }

                            ticketVender.setPeiLv(peilv);
                        }
                    } else if ("200020".equals(status)) {// 失败
                        ticketVender.setStatus(TicketVenderStatus.failed);
                    } else if ("200022".equals(status)) {// 票不存在
                        ticketVender.setStatus(TicketVenderStatus.not_found);
                    } else {
                        ticketVender.setStatus(TicketVenderStatus.unkown);
                    }
                    ticketVender.setPrintTime(new Date());
                    this.ticketVenderProcess(ticketVender);
                    HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
                    bodyAttr.put("id", ticketId);
                    bodyAttr.put("result", "0");
                    xml.addBodyElement("record", bodyAttr, returnBody);
                    i++;
                }
                String des = GzcpDes.des3EncodeCBC(venderConfig.getKey(), "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml.getBodyElement().asXML());
                String md = MD5Util.toMd5(des);
                xmlParse.addHeaderElement("md", md);
                xmlParse.getBodyElement().setText(des);
            }
            return xmlParse.asXML();
        } catch (Exception e) {
            logger.error("处理通知异常", e);
            logger.error("bodyStr={}", bodyStr);
            return null;
        }


    }

    protected IVenderConverter getVenderConverter() {
        return venderConverterBinder.get(getTerminalType());
    }

}
