package com.lottery.draw.impl;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DESCoder;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fengqinyun on 15/9/6.
 */
@Component("owncenter_account")
public class OwnCenterAccountDrawWork extends AbstractVenderBalanceWork {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_OWN_CENTER;
    }

    @Override
    protected MemberAccount getAccount(IVenderConfig config) {
        try{
            String command="806";
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
            messageElement.addElement("merchant").setText(config.getAgentCode());
            String body = DESCoder.desEncrypt(bodyDocument.asXML(), config.getKey());
            String md5Str = command + dateStr + config.getAgentCode() + config.getKey();
            String md5 = CoreStringUtils.md5(md5Str, CharsetConstant.CHARSET_UTF8);
            rootElement.addElement("body").setText(body);
            rootElement.addElement("signature").setText(md5);
            String resposeStr=HTTPUtil.post(config.getRequestUrl(), document.asXML() ,CharsetConstant.CHARSET_UTF8);

            Document doc  = DocumentHelper.parseText(resposeStr);
            Element rootElt = doc.getRootElement();
            Element head=rootElt.element("head");
            String  errorcode=head.element("errorcode").getText();
            if("0".equals(errorcode)){
                String bodym = DESCoder.desDecrypt(rootElt.element("body").getText(), config.getKey());
                Document document1=DocumentHelper.parseText(bodym);
                Element balanceE=document1.getRootElement().element("balance");
                String balance=balanceE.getText();
                MemberAccount memberAccount=new MemberAccount();
                memberAccount.setBalance(new BigDecimal(new Double(balance)/100));
                memberAccount.setTerminalName(getTerminalType().getName());
                memberAccount.setId(config.getTerminalId());
                memberAccount.setSmsFlag(YesNoStatus.no.value);
                return memberAccount;
            }else {
                logger.error("request={},response={}",document.asXML(),resposeStr);
            }

        }catch (Exception e){

        }


        return null;
    }


}
