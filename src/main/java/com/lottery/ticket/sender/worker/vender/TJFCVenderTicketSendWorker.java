package com.lottery.ticket.sender.worker.vender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.common.util.TransformType;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.LotteryLogicMachine;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.LotteryLogicMachineService;
import com.lottery.core.service.UserInfoService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.lotterycenter.tianjin.DESTJFC;

@Component
public class TJFCVenderTicketSendWorker extends AbstractVenderTicketSendWorker {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LotteryLogicMachineService loMachineService;

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_TJFC_CENTER;
	}

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();

		for (Ticket ticket : ticketList) {
			TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
			ticketSendResultList.add(ticketSendResult);
			try {
				IVenderConverter tjConverter = getVenderConverter();
				String lotteryId = tjConverter.convertLotteryType(lotteryType);
				String phase = tjConverter.convertPhase(lotteryType, ticketBatch.getPhase());

				LotteryLogicMachine logicMachine = loMachineService.getUseingMachine(this.getTerminalType().value, ticket.getLotteryType(), ticketBatch.getTerminalId());

				if (logicMachine != null) {

					try {
						Long machineId=logicMachine.getCurrentId();
						// 格式封装
						Date sellDate=new Date();
						String requestStr=getFcRequestStr(ticket, ticket.getUserno(), phase, lotteryId, logicMachine.getPk().getId(), machineId.intValue(), tjConverter, sellDate,logicMachine.getCityCode());
						ticketSendResult.setSendMessage(requestStr);
						byte[] messStr = this.makeMessageHead(venderConfig.getAgentCode(),machineId.intValue(),venderConfig.getKey(),requestStr);
						
						ticketSendResult.setSendTime(sellDate);
						byte[] responseStr = this.sendMsgCommon(messStr, venderConfig.getRequestUrl(), venderConfig.getPort(), 1024);
						String rs = splitMsg(responseStr, venderConfig.getKey());
						ticketSendResult.setResponseMessage(rs);
					
						ticketSendResult.setStatusCode(rs);
						if ("0".equals(rs.split("\\\t")[0])) {
							String ticketCode = rs.split("\\\t")[1];
							ticketSendResult.setSellRunCode(machineId+"");
							logicMachine.setCurrentId(machineId + 1);
							logicMachine.setUpdateTime(new Date());
							loMachineService.update(logicMachine);
							ticketSendResult.setStatus(TicketSendResultStatus.printed);
							ticketSendResult.setExternalId(ticketCode);
							ticketSendResult.setMachineCode(logicMachine.getPk().getId()+"");
							ticketSendResult.setPasswd(ticketCode);
							ticketSendResult.setTerminalType(getTerminalType());
							ticketSendResult.setPrintTime(new Date());
						} else if ("1010".equals(rs)) {
							logger.error("天津福彩中心订单{}送票异常,异常原因：订单已存在", ticket.getId());
							ticketSendResult.setStatus(TicketSendResultStatus.printed);
						} else if ("1030".equals(rs)) {
							ticketSendResult.setStatus(TicketSendResultStatus.unkown);
							ticketSendResult.setStatusCode(rs);
							ticketSendResult.setMessage("逻辑机正在使用");
							logicMachine.setCurrentId(logicMachine.getCurrentId() + 1);
							logicMachine.setUpdateTime(new Date());
							loMachineService.update(logicMachine);
						} else if (Constants.tianjinCenterSendError.containsKey(rs)) {
							logger.error("天津福彩中心订单{}送票异常,异常原因：{}", ticket.getId(), Constants.tianjinCenterSendError.get(rs));
							ticketSendResult.setMessage(Constants.tianjinCenterSendError.get(rs));
							ticketSendResult.setStatus(TicketSendResultStatus.failed);
						} else if (Constants.tianjinCenterFailedError.containsKey(rs)) {
							logger.error("天津福彩中心订单{}送票失败,异常原因：{}", ticket.getId(), Constants.tianjinCenterFailedError.get(rs));
							ticketSendResult.setStatus(TicketSendResultStatus.failed);
						} else {
							logger.error("天津福彩中心订单{}送票失败,异常原因：{}", ticket.getId(), rs);
							ticketSendResult.setStatus(TicketSendResultStatus.unkown);
						}
					} catch (Exception e) {
						ticketSendResult.setStatus(convertResultStatusFromException(e));
						ticketSendResult.setMessage(e.getMessage());
						logger.error("天津送票处理错误", e);
					}

				} else {
					ticketSendResult.setStatus(TicketSendResultStatus.unkown);
					ticketSendResult.setMessage("逻辑机为空");
				}
			} catch (Exception e) {
				ticketSendResult.setStatus(convertResultStatusFromException(e));
				ticketSendResult.setMessage(e.getMessage());
				logger.error("天津福彩中心送票处理错误", e);
			}
		}
		return ticketSendResultList;
	}

	/**
	 * 拆报体
	 * 
	 * @param rs
	 *            返回报文
	 * @return
	 */
	protected String splitMsg(byte[] rs, String key) throws Exception {
		if (rs == null)
			return null;
		int size = rs[3];
		int tempSize = size - 36;
		if (tempSize <= 0)
			return null;
		byte[] temp = new byte[tempSize];
		System.arraycopy(rs, 36, temp, 0, tempSize);
		return DESTJFC.decrypt(temp, key);
	}

	/**
	 * 数据封装
	 * 
	 * @param resquestStr 请求字符串
	 * @param key 秘钥
	 * @return
	 * @throws Exception
	 */
	private byte[] getMessage(String resquestStr,String key) throws Exception {

		String rs = hanlerMessage(resquestStr);

		byte[] pkByte = DESTJFC.encrypt(rs, key);
	
		return pkByte;
	}

	
	private String getFcRequestStr(Ticket ticket, String userId, String phase, String lotteryId, Long logicNum, int currentId, IVenderConverter tjConverter,Date sellDate,String citycode) throws Exception {

		Double amount = ticket.getAmount().doubleValue() / 100;
		UserInfo userInfo = userInfoService.get(userId);
		String zhushu = (amount.intValue() / 2) + "";
		String contentStr = tjConverter.convertContent(ticket);
		StringBuilder commodStr = new StringBuilder();
		commodStr.append(paddingMessage(lotteryId));
		commodStr.append(paddingMessage(logicNum + ""));
		commodStr.append(paddingMessage(citycode));
		commodStr.append(paddingMessage(phase));
		commodStr.append(paddingMessage(phase));
		commodStr.append(paddingMessage(currentId + ""));// 流水号
		commodStr.append(paddingMessage(DateUtil.format("yyyy-MM-dd HH:mm:ss", sellDate)));// 销售时间
		commodStr.append(paddingMessage(zhushu));// 注数
		commodStr.append(paddingMessage("0"));// 投注方式
		commodStr.append(paddingMessage("0"));// 选号方式
		commodStr.append(paddingMessage(contentStr));
		commodStr.append(paddingMessage("0"));// 注销标志
		commodStr.append(paddingMessage(userInfo.getIdcard() != null ? userInfo.getIdcard() : "0"));// 身份证号
		commodStr.append(paddingMessage(userInfo.getPhoneno() != null ? userInfo.getPhoneno() : "0"));// 手机号
		commodStr.append(paddingMessage("0"));// 投注账号
		commodStr.append(paddingMessage("0"));
		commodStr.append(paddingMessage("0"));
		String md5 = MD5Util.toMd5(commodStr.toString());
		commodStr.append(paddingMessage(md5));
		return commodStr.toString();
		
	}

	/**
	 * 组报文
	 * 
	 * @param agentid  代理商编号
	 * @param  sendIndex 出票逻辑机号
	 * @param  key 秘钥 
	 * @param  requestStr 出票字符串
	 * @return
	 * @throws Exception
	 */
	protected byte[] makeMessageHead(String agentid,int sendIndex,String key,String requestStr) throws Exception {
		byte[] makePkg = getMessage(requestStr,key);
		int pkgSize = 36 + makePkg.length;
		byte[] resultByte = new byte[pkgSize];
		byte[] size = TransformType.intToByteArray(pkgSize);
		byte[] command = TransformType.intToByteArray(4);
		String sellerAccount = createMessage(agentid, 20);
		byte[] sellerByte = sellerAccount.getBytes();
		int datePart = Integer.parseInt(DateUtil.format("MMddHHmmss", new Date()));
		byte[] datePartByte = TransformType.intToByteArray(datePart);
		byte[] sendIndexByte = TransformType.intToByteArray(sendIndex);
		System.arraycopy(size, 0, resultByte, 0, 4);
		System.arraycopy(sellerByte, 0, resultByte, 4, 20);
		System.arraycopy(command, 0, resultByte, 24, 4);
		System.arraycopy(datePartByte, 0, resultByte, 28, 4);
		System.arraycopy(sendIndexByte, 0, resultByte, 32, 4);
		System.arraycopy(makePkg, 0, resultByte, 36, makePkg.length);
		return resultByte;
	}

	/**
	 * 补全字符串
	 * 
	 * @param str
	 *            待补全字符串
	 * @param size
	 *            补全最终长度
	 * @return
	 */
	protected String createMessage(String str, int size) {
		String returnStr = str;
		int temp = size - str.length();
		if (temp <= 0)
			return str;
		for (int loop = 0; loop < temp; loop++) {
			returnStr += "\0";
		}
		return returnStr;
	}

	/**
	 * 16进制字符串转byte
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2bin(String hex) {
		String digital = "0123456789ABCDEF";
		char[] hex2char = hex.toCharArray();
		byte[] bytes = new byte[hex.length() / 2];
		int temp;
		for (int i = 0; i < bytes.length; i++) {
			temp = digital.indexOf(hex2char[2 * i]) * 16;
			temp += digital.indexOf(hex2char[2 * i + 1]);
			bytes[i] = (byte) (temp & 0xff);
		}
		return bytes;
	}

	/**
	 * 补位
	 * 
	 * @param str
	 * @return
	 */
	protected String hanlerMessage(String str) {
		int len = str.length();
		int remainder = 8 - len % 8;
		for (int loop = 0; loop < remainder; loop++) {
			str += "\0";
		}
		return str;
	}

	/*
	 * 给福彩发消息
	 * 
	 * @param command
	 * 
	 * @param ip
	 * 
	 * @param port
	 * 
	 * @return
	 */
	public byte[] sendMsgCommon(byte[] command, String ip, int port, int length) throws Exception {
		Socket socket = null;
		OutputStream o = null;
		InputStream in = null;
		try {
			byte[] temp = new byte[length];
			socket = new Socket(ip, port);
			socket.setSoTimeout(7000);
			o = socket.getOutputStream();
			in = socket.getInputStream();
			o.write(command);
			o.flush();
			in.read(temp);
			socket.close();
			return temp;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (o != null)
					o.close();
				if (in != null)
					in.close();
				if (socket != null&&!socket.isClosed())
					socket.close();
			} catch (IOException e) {
				throw e;
			}
		}

	}

	/**
	 * 填充字符串
	 * 
	 * @param str
	 *            待补全字符串
	 * @return
	 */
	protected String paddingMessage(String str) {
		return str + "\t";
	}

}
