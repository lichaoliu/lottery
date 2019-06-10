package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TJFcCommandEnum;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.IFtpMessageHandler;


/**
 * 中奖数据文件
 * 
 * @author Administrator
 * 
 */
@Component("10003")
public class DownloadWinDataHander extends AbstractTJFCWinDataHandler implements IFtpMessageHandler {

	@Override
	public byte[] sendMsgGS(byte[] msg) {
		try {
			TJFCConfig tjfcConfig=getTJFCConfg();
			String key=tjfcConfig.getNoticeKey();
			String ftpName = creteFcFileName(msg, key, "windata", "ffl");
			winDataProcess(ftpName, tjfcConfig);
			byte[] fcMsgRsp = makeMessageHead(TJFcCommandEnum.rsWinData, "0",tjfcConfig.getAgentCode(),tjfcConfig.getNoticeKey());
			return fcMsgRsp;

		} catch (Exception e) {
			logger.error("下载中奖数据文件失败", e);
			return null;
		}

	}


}
