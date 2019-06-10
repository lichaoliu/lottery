package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DESCoder;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.owncenter.OwnCenterConfig;

/**
 * Created by fengqinyun on 15/5/24.
 */
@Component
public class OwnCenterVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
    private String betCode = "801";

    @Override
    protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType, IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
        List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();

        String messageStr = "";
        String returnStr = "";
        try {

            String lotno = lotteryType.getValue() + "";
            String phase = ticketBatch.getPhase();
            messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, venderConverter);
            returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
            dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
            return ticketSendResultList;
        } catch (Exception e) {
            processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
            logger.error("出票中心送票处理错误", e);
            return ticketSendResultList;
        }
    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_OWN_CENTER;
    }

    /**
     * 送票结果处理
     *
     * @param desContent
     * @return
     * @throws Exception
     */
    protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> ticket, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType, IVenderConfig ownerConfig) throws Exception {

        Map<String, String> mapResult = XmlParse.getElementText("/", "head", desContent);
        if (mapResult != null) {
            if ("0".equals(mapResult.get("errorcode"))) {
                Document doc = DocumentHelper.parseText(desContent);
                Element rootElt = doc.getRootElement();
                String body = DESCoder.desDecrypt(rootElt.element("body").getText(), ownerConfig.getKey());
                List<HashMap<String, String>> mapList = XmlParse.getElementTextList("orderlist/", "order", body);
                if (mapList != null && mapList.size() > 0) {
                    for (Map<String, String> map : mapList) {
                        TicketSendResult ticketSendResult = new TicketSendResult();
                        ticketSendResultsList.add(ticketSendResult);
                        ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
                        String returnCode = map.get("errorcode");
                        String ticketId = map.get("orderid");
                        String extendId = map.get("sysid");
                        ticketSendResult.setId(ticketId);
                        ticketSendResult.setLotteryType(lotteryType);
                        ticketSendResult.setStatusCode(returnCode);
                        ticketSendResult.setExternalId(extendId);
                        ticketSendResult.setResponseMessage(desContent);
                        ticketSendResult.setSendMessage(requestStr);
                        ticketSendResult.setSendTime(new Date());
                        ticketSendResult.setTerminalType(getTerminalType());
                        if (ErrorCode.Success.getValue().equals(returnCode)) {
                            ticketSendResult.setStatus(TicketSendResultStatus.success);
                        } else if (Constants.ownerSendErrorFailed.containsKey(returnCode)) {
                            ticketSendResult.setStatus(TicketSendResultStatus.failed);
                            ticketSendResult.setMessage(Constants.ownerSendErrorFailed.get(returnCode));
                        } else if (Constants.ownerSendError.containsKey(returnCode)) {
                            ticketSendResult.setStatus(TicketSendResultStatus.unkown);
                            ticketSendResult.setMessage(Constants.ownerSendError.get(returnCode));
                        } else if ("6".equals(returnCode)) {
                            ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
                            ticketSendResult.setMessage("票已存在");
                        } else {
                            ticketSendResult.setStatus(TicketSendResultStatus.unkown);
                            ticketSendResult.setMessage(returnCode);
                        }
                    }
                }
            } else {
                processError(ticketSendResultsList, ticketBatch, ticket, TicketSendResultStatus.unkown, null, requestStr, desContent, "返回结果不为0");
            }
        }
    }

    /**
     * 送票前拼串
     *
     * @param tickets   票集合
     * @param lotteryNo 彩种
     * @return
     * @throws Exception
     * @throws Exception
     */

    public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig ownerConfig, LotteryType lotteryType, IVenderConverter ownerConverter) throws Exception {
        String messageId = getMessageId(ownerConfig.getAgentCode(), betCode);
        XmlParse xmlParse = OwnCenterConfig.addOwnCenterHead(betCode, ownerConfig.getAgentCode(), messageId);
        XmlParse xml = new XmlParse("message");
        Element bodyElement = xml.getBodyElement().addElement("orderlist");
        String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
        for (Ticket ticket : tickets) {
            HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
            bodyAttr.put("lotterytype", lotteryNo);
            bodyAttr.put("phase", phase);
            bodyAttr.put("orderid", ticket.getId() + "");
            bodyAttr.put("playtype", ticket.getPlayType() + "");
            bodyAttr.put("betcode", ownerConverter.convertContent(ticket));
            Double amount = ticket.getAmount().doubleValue() / 100;
            bodyAttr.put("amount", amount.intValue() + "");
            bodyAttr.put("multiple", ticket.getMultiple() + "");
            bodyAttr.put("add", ticket.getAddition() + "");
            bodyAttr.put("endtime", CoreDateUtils.formatDateTime(ticket.getDeadline()));
            xml.addBodyElement("order", bodyAttr, bodyElement);
        }
        String md5Str = betCode + timestamp + ownerConfig.getAgentCode() + ownerConfig.getKey();
        String md5 = CoreStringUtils.md5(md5Str, CharsetConstant.CHARSET_UTF8);
        String body = DESCoder.desEncrypt(xml.asXML(), ownerConfig.getKey());
        xmlParse.getBodyElement().setText(body);
        xmlParse.addRootElement("signature", md5);
        return xmlParse.asXML();
    }


    public String getMessageId(String agentNo, String command) {

        String dateStr = CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        return agentNo + command + dateStr;
    }
}
