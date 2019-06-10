package com.lottery.draw.impl;



import com.lottery.common.contains.YesNoStatus;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;


import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.PhaseStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DESCoder;
import com.lottery.common.util.HTTPUtil;
import com.lottery.draw.AbstractVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.ticket.IVenderConfig;

import java.util.Date;

/**
 * Created by fengqinyun on 15/9/6.
 */
@Component("owncenter_draw")
public class OwnCenterPhaseDrawWorker extends AbstractVenderPhaseDrawWorker {

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_OWN_CENTER;
    }




    @Override
    protected LotteryDraw get(IVenderConfig config, Integer lotteryType, String phase) {
        String resposeStr="";
        String deciphStr="";
        try {
            String command="805";
            Document document= DocumentHelper.createDocument();
            Element rootElement=document.addElement("content");
            Element headerElement=rootElement.addElement("head");
            headerElement.addElement("version").setText("1.0");
            headerElement.addElement("merchant").setText(config.getAgentCode());
            headerElement.addElement("command").setText(command);
            String dateStr = CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            String messageId= config.getAgentCode() + command + dateStr;
            headerElement.addElement("messageid").setText(messageId);
            headerElement.addElement("timestamp").setText(dateStr);

            Document bodyDocument= DocumentHelper.createDocument();
            Element bodyRootElement=bodyDocument.addElement("message");
            Element messageElement=bodyRootElement.addElement("content");
            messageElement.addElement("lotterytype").setText(lotteryType+"");
            messageElement.addElement("phase").setText(phase);

            String body = DESCoder.desEncrypt(bodyDocument.asXML(), config.getKey());
            String md5Str = command + dateStr + config.getAgentCode() + config.getKey();
            String md5 = CoreStringUtils.md5(md5Str, CharsetConstant.CHARSET_UTF8);
            rootElement.addElement("body").setText(body);
            rootElement.addElement("signature").setText(md5);
            resposeStr=HTTPUtil.post(config.getRequestUrl(), document.asXML() ,CharsetConstant.CHARSET_UTF8);

            Document doc  = DocumentHelper.parseText(resposeStr);
            Element rootElt = doc.getRootElement();
            Element head=rootElt.element("head");
            String  errorcode=head.element("errorcode").getText();

            if("0".equals(errorcode)){
                String bodym = DESCoder.desDecrypt(rootElt.element("body").getText(), config.getKey());
                deciphStr=bodym;
                Document document1=DocumentHelper.parseText(bodym);
                LotteryDraw lotteryDraw = new LotteryDraw();
                lotteryDraw.setLotteryType(lotteryType);
                lotteryDraw.setPhase(phase);
                lotteryDraw.setResponsMessage(bodym);
                lotteryDraw.setWindCode(document1.getRootElement().element("wincode").getText());
                String windetail=document1.getRootElement().element("windetail").getText();
                if(StringUtils.isNotBlank(windetail)&&!"null".equals(windetail)){
                    lotteryDraw.setResultDetail(windetail);
                }
                String sall=document1.getRootElement().element("saleamount").getText();
                if(StringUtils.isNotBlank(sall)&&!"null".equals(sall)){
                    lotteryDraw.setVolumeOfSales(sall);
                }
                String pool=document1.getRootElement().element("poolamount").getText();
                if(StringUtils.isNotBlank(pool)&&!"null".equals(pool)){
                    lotteryDraw.setJackpot(pool);
                }

                lotteryDraw.setStatus(PhaseStatus.prize_open.getValue());
                lotteryDraw.setIsDraw(YesNoStatus.yes.value);
                lotteryDraw.setDrawFrom("出票中心"+config.getAgentCode());
                return lotteryDraw;
            }else {
                logger.error("request={},response={}",document.asXML(),resposeStr);
            }

        }catch (Exception e){
            logger.error("错误信息",e);
            logger.error("response={},deciph={}",resposeStr,deciphStr);

        }
        return null;

    }
}
