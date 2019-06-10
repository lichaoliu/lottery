package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractMessageHandler;
import com.lottery.ticket.vender.lotterycenter.tianjin.ISendMessageHandler;

/**
 * 系统时间
 * 
 * @author zhangjian
 * 
 */

public class LotteryDateTimeHandler extends AbstractMessageHandler implements ISendMessageHandler {

	@Override
	public <T> String handleMessage(T msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {
		// TODO Auto-generated method stub
		return null;
	}

	/*private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public <T> String handleMessage(T gm) {
		byte[] fcMsg = makeMessageHead(TJFcCommandEnum.TjSystemTime, gm);
		byte[] result = null;
		try {
			result = sendInfoCom(fcMsg, 60);
		} catch (Exception e) {
			logger.error("获取系统时间失败", e);

		}
		DESLotteryCenter.SetKey(ConfigEnum.tjfcdeskey.getValue());
		String rs = splitMsg(result, 1);
		modify(rs); // 修改系统时间
		logger.info("福彩返回的字符串是：" + rs + "  描述为：" + ReCode.bsCode.get(rs));
		return rs;
	}

	protected <T> byte[] makeMessagePkg(T gm) {
		byte[] b = { (byte) 209, 64, 126, 40, 54, (byte) 227, 69, 31 };
		// String mk = null;
		// try {
		// mk = new String(b, "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		return b;
	}

	private void modify(String fcTime) {
		String cmd = "/bin/date -s '" + fcTime + "'";
		String[] comands = new String[] { "/bin/sh", "-c", cmd };
		try {
			Process p = Runtime.getRuntime().exec(comands);
			InputStream in = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while (null != (line = reader.readLine())) {
				sb.append(line);
			}
			in.close();
			reader.close();
		} catch (IOException e) {
			logger.error("修改系统时间失败", e);
			
		}
	}
*/
}
