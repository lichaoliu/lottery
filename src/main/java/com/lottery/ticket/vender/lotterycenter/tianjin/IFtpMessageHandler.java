package com.lottery.ticket.vender.lotterycenter.tianjin;
/**
 * 处理ftp接口
 * @author Administrator
 *
 */
public interface IFtpMessageHandler {
	/**
	 * 通知ftp存放地址
	 * @param msg
	 * @return
	 */
	public byte[] sendMsgGS(byte[] msg);
}
