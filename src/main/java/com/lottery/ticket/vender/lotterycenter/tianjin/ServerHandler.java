package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class ServerHandler extends IoHandlerAdapter {

	protected static final Logger logger = LoggerFactory
			.getLogger(ServerHandler.class);
	
	
	@Autowired
	public Map<String, IFtpMessageHandler> gameServerMap;


	public void messageReceived(IoSession session, Object message)
			throws Exception {
		//logger.info("收到新期消息");
		IoBuffer ioBuffer = (IoBuffer) message;
		byte[] b = new byte[ioBuffer.limit()];
		ioBuffer.get(b);
		byte command = b[27];
		//logger.info("当前取ftp命令为:" + command);
		IFtpMessageHandler fh = gameServerMap.get("1000" + command);
		byte[] result = fh.sendMsgGS(b);
		if (null != result) {
			session.write(IoBuffer.wrap(result));
			//logger.info(command + "命令处理成功");
			return;
		}
		logger.info(command + "命令处理失败");
	}
	

	public void sessionClosed(IoSession session) throws Exception {
		//logger.info("关闭会话");
	}

	public void sessionIdle(IoSession session, IdleStatus status) {
		//logger.warn("Unexpected exception.", status);
		session.close(true);
		
	}

	public void exceptionCaught(IoSession session, Throwable cause) {
		//logger.info("进入异常");
		session.close(true);
	}
}
