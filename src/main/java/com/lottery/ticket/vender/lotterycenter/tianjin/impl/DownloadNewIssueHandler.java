package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TJFcCommandEnum;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.IFtpMessageHandler;

/**
 * 新期文件处理
 * 
 * @author Administrator
 * 
 */
@Component("10001")
public class DownloadNewIssueHandler extends AbstractTJFCNewIssueHandler implements IFtpMessageHandler {

	@Override
	public byte[] sendMsgGS(byte[] msg) {
		try {
			TJFCConfig tjfcConfig=getTJFCConfg();
			String key=tjfcConfig.getNoticeKey();
			String ftpName = creteFcFileName(msg, key, "par", "ffl");
			newIssueProcess(ftpName, tjfcConfig);
			byte[] fcMsgRsp = makeMessageHead(TJFcCommandEnum.rsNewIssue, "0",tjfcConfig.getAgentCode(),tjfcConfig.getNoticeKey());
			return fcMsgRsp;
		} catch (Exception e) {
			logger.error("获取新期失败", e);
		}
		return null;
		

	}

	

}
