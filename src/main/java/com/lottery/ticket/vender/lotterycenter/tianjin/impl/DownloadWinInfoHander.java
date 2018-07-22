package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TJFcCommandEnum;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.IFtpMessageHandler;


/**
 * 中奖号码文件
 * 
 * @author Administrator
 * 
 */
@Component("10002")
public class DownloadWinInfoHander extends AbstractTJFCWinInfoHandler implements IFtpMessageHandler {

	

	@Override
	public byte[] sendMsgGS(byte[] msg) {
		try {
			TJFCConfig tjfcConfig=getTJFCConfg();
			String key=tjfcConfig.getNoticeKey();
			String ftpName = creteFcFileName(msg, key, "wininfo", "ffl");
		    winInfoProcess(ftpName, tjfcConfig);
			byte[] fcMsgRsp = makeMessageHead(TJFcCommandEnum.rsWinInfo, "0",tjfcConfig.getAgentCode(),tjfcConfig.getNoticeKey());
			return fcMsgRsp;
		} catch (Exception e) {
			logger.error("获取中奖号码文件失败", e);
		}
		return  null;
	}

	
	
}
