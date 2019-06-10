package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;

public  abstract class AbstractTJFCMessageHandler extends AbstractMessageHandler {

	@Value("${local.dir}")
	protected String localDir;//本地文件
	@Value("${local.windata.dir}")
	protected String localwindatadir;
	protected final int KEY = 0x9f;
	protected TerminalType getTerminalType(){
		return TerminalType.T_TJFC_CENTER;
	}
	

	protected String downloadFtp(String localDir,String filename,TJFCConfig tjfcConfig) throws Exception{
		File dirFile = new File(localDir);
		if (!dirFile.exists()) {
			logger.debug("创建ftp目录");
			dirFile.mkdir();
		}
		String ip=tjfcConfig.getFtpIp();
		String username=tjfcConfig.getFtpUser();
		String pass=tjfcConfig.getFtpPass();
		int ipPort=tjfcConfig.getFtpPort();
		FTPClient  ftpClient=TJFCFtpService.getFTPClient(ip, ipPort, username, pass);
		String remotedir=tjfcConfig.getFtpDir();
		return TJFCFtpService.downloadFtp(filename, localDir, remotedir, ftpClient);
	}

	protected TJFCConfig getTJFCConfg(){
		return (TJFCConfig) getConfig();
	}
	
	
}
