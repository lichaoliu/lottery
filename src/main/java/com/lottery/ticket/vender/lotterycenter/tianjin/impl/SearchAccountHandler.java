package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractMessageHandler;
import com.lottery.ticket.vender.lotterycenter.tianjin.ISendMessageHandler;

/**
 * 余额
 * @author zhangjian
 *
 */

public class SearchAccountHandler extends AbstractMessageHandler implements ISendMessageHandler {

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
		byte[] fcMsg = makeMessageHead(TJFcCommandEnum.balanceAccout, gm);
		byte[] result = null;
		try {
			result = sendInfoCom(fcMsg, 1024);
		} catch (Exception e) {
			logger.error("查询账户金额失败", e);
			
		}
		DESLotteryCenter.SetKey(ConfigEnum.tjfcdeskey.getValue());
		String rs = splitMsg(result, 1);
		logger.info("福彩返回的字符串是：" + rs + "  描述为：" + ReCode.bsCode.get(rs));
		return rs;

	}

	@Override
	protected <T> byte[] makeMessagePkg(T t) {
		// StringBuffer commodStr = new StringBuffer();
		// commodStr.append(paddingMessage(ConfigEnum.tjfclogicnum.getValue(),8));
		DESLotteryCenter.SetKey(ConfigEnum.tjfcdeskey.getValue());
		byte[] pkByte = DESLotteryCenter.decrypt(ConfigEnum.tjfclogicnum.getValue(), 1);
		return pkByte;
	}*/

}
