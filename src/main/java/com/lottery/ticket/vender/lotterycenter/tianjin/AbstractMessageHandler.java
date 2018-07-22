package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lottery.core.handler.PrizeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.TJFcCommandEnum;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.common.util.TransformType;
import com.lottery.core.service.LotteryLogicMachineService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.TicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.ticket.vender.impl.VenderPhaseDrawHandler;

public abstract class AbstractMessageHandler {

	protected static int sendIndex = 1;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
	protected VenderConfigHandler venderConfigService;
    @Resource(name="venderConverterBinder")
    protected Map<TerminalType,IVenderConverter> venderConverterBinder;
    @Autowired
	protected VenderPhaseDrawHandler venderPhaseDrawHandler;
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected LotteryPhaseService lotteryPhaseService;
    @Autowired
    protected LotteryLogicMachineService lotteryLogicMachineService;

	@Autowired
	protected PrizeHandler prizeHandler;
	/**
	 * 组报文
	 * 
	 * @param fcc
	 * @param gm
	 * @return
	 */
	protected byte[] makeMessageHead(TJFcCommandEnum fcc, String content,String agentid,String key) throws Exception{
		if (sendIndex >= 2147483647)
			sendIndex = 1;
		byte[] makePkg = makeMessagePkg(content,key);
		int pkgSize = 36 + makePkg.length;
		byte[] resultByte = new byte[pkgSize];
		byte[] size = TransformType.intToByteArray(pkgSize);
		byte[] command = null;
		if (fcc.getValue() == 0) {
			command = hex2bin(fcc.getHex());
		} else {
			command = TransformType.intToByteArray(fcc.getValue());
		}
		String sellerAccount = createMessage(agentid,20);
		byte[] sellerByte = sellerAccount.getBytes();
		int datePart = 1221110316;
		byte[] datePartByte = TransformType.intToByteArray(datePart);
		byte[] sendIndexByte = TransformType.intToByteArray(sendIndex);
		System.arraycopy(size, 0, resultByte, 0, 4);
		System.arraycopy(sellerByte, 0, resultByte, 4, 20);
		System.arraycopy(command, 0, resultByte, 24, 4);
		System.arraycopy(datePartByte, 0, resultByte, 28, 4);
		System.arraycopy(sendIndexByte, 0, resultByte, 32, 4);
		System.arraycopy(makePkg, 0, resultByte, 36, makePkg.length);
		sendIndex++;
		return resultByte;

	}

	
	protected void asyncAccount(){
		
	}
	
	/**
	 * 数据封装
	 * 
	 * @param resquestStr 请求字符串
	 * @param key 秘钥
	 * @return
	 * @throws Exception
	 */
	protected byte[] getMessage(String resquestStr,String key) throws Exception {

		String rs = hanlerMessage(resquestStr);

		logger.error("最终发送福彩字符串:{}",rs);
		byte[] pkByte = DESTJFC.encrypt(rs, key);
	
		return pkByte;
	}
	
	/**
	 * 组报文
	 * 
	 * @param commod 命令吗
	 * @param hex 
	 * @param content 内容
	 * @param agentid 用户编号
	 * @param key 秘钥
	 * @return
	 */
	protected byte[] makeMessageHead(int commod, String hex,  String content,String agentid,String key) throws Exception{
		if (sendIndex >= 2147483647)
			sendIndex = 1;
				
		byte[] makePkg = getMessage(content,key);
		int pkgSize = 36 + makePkg.length;
		byte[] resultByte = new byte[pkgSize];
		byte[] size = TransformType.intToByteArray(pkgSize);
		byte[] command = null;
		if (commod == 0) {
			command = hex2bin(hex);
		} else {
			command = TransformType.intToByteArray(commod);
		}
		String sellerAccount = createMessage(agentid,20);
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
		sendIndex++;
		return resultByte;

	}

	

	/**
	 * 拆报体
	 * 
	 * @param rs
	 *            返回报文
	 * @return
	 * @throws Exception
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

	
	
	public String readFile(String fileName, String key,int size) throws Exception {
		InputStream fs = null;
		try {
			byte[] tempb = new byte[size];
			int byteread = 0;
			fs = new FileInputStream(fileName);
			byteread = fs.read(tempb);
			if (byteread <= 0)
				return null;
			byte[] rByte = new byte[byteread];
			System.arraycopy(tempb, 0, rByte, 0, byteread);
			return DESTJFC.decrypt(rByte, key);
		}catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				throw  e;
			}
		}
		
	
	}

	
	

	/**
	 * 向福彩发送请求
	 * 
	 * @param contant
	 *            内容
	 * @param ip
	 *            地址
	 * @param port
	 *            端口号
	 * @param length
	 *            报文长度
	 * */
	protected byte[] sendMs(byte[] contant, String ip, int port, int length)
			throws Exception {
		Socket socket = null;
	
		OutputStream o = null;
		InputStream in = null;
		try {
			byte[] temp = new byte[length];
			socket = new Socket(ip, port);
			socket.setSoTimeout(7000);
			o = socket.getOutputStream();
			in = socket.getInputStream();
			o.write(contant);
			o.flush();
			in.read(temp);

			socket.close();
			return temp;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (o != null)
					o.close();
				if (in != null)
					in.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				throw e;
			}
		}
		
	}
	
	protected IVenderConfig getConfig(){
		List<IVenderConfig> list=venderConfigService.getAllByTerminalType(getTerminalType());
		if(list!=null&&!list.isEmpty())
			return list.get(0);
		return null;
	}
   abstract protected TerminalType getTerminalType();
   
   protected IVenderConverter getVenderConverter(){
	   return venderConverterBinder.get(getTerminalType());
   }
	
	
	
	/**
	 * 填充字符串
	 * 
	 * @param str
	 *            待补全字符串
	 * @param size
	 *            补全最终长度
	 * @return
	 */
	protected String paddingMessage(String str, int size) {
		// return createMessage(str,size)+"\t";
		return str + "\t";
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
	
	protected  byte[] makeMessagePkg(String content,String key) throws Exception{
		StringBuffer str = new StringBuffer();
		str.append(paddingMessage(content, 4));
		String md5 = MD5Util.toMd5(str.toString());
		str.append(md5 + "/t");
		String strRes = hanlerMessage(str.toString());
		return DESTJFC.encrypt(strRes, key);
	}

	
	/**
	 * 拼接文件路径
	 * 
	 * @param msg
	 * @param pri
	 * @param suffix
	 * @return
	 */
	protected String creteFcFileName(byte[] msg, String key,String pri, String suffix) throws  Exception{
		
		String fcMsgReq =splitMsg(msg, key);
		//logger.error("福彩文件原始内容:{}",fcMsgReq);
		String[] msgArr = fcMsgReq.split("\t");
		String gameName = msgArr[0];
		String issue = msgArr[1];
		return pri + "_" + gameName + "_" + issue + "." + suffix;
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

protected String makeWinCode(String str){
		
		String resultStr = "";
		int strLength = str.length();
		for(int loop = 0; loop < strLength; loop += 2){
			if(loop > 0) resultStr += ",";
			resultStr += str.substring(loop, loop + 2);
		}
		return resultStr;
	}
	
	protected String makeD3(String str){
		int strLength = str.length();
		String [] arr = new String[strLength/2];
		int index = 0;
		for(int loop = 0; loop < strLength; loop += 2){
			String sbr=str.substring(loop,loop + 2);
			if(sbr.equals("00")){
				sbr=sbr.replace("00", "0");
			}else{
				sbr=sbr.replace("0", "");
			}
			arr[index] = sbr;
			index ++;
		}
		String ar = Arrays.toString(arr);
		return ar.replace("[", "").replace("]", "").replaceAll(" ", "");
	}
	
	protected String makeGlobal(String str){
		int strLength = str.length();
		String [] arr = new String[strLength/2];
		int index = 0;
		for(int loop = 0; loop < strLength; loop += 2){
			arr[index] = str.substring(loop,loop + 2);
			index ++;
		}
		Arrays.sort(arr);
		String ar = Arrays.toString(arr);
		return ar.replace("[", "").replace("]", "").replaceAll(" ", "");
	}
	protected String wincodeSeque(String str){
		int strLength = str.length();
		String [] arr = new String[strLength/2];
		int index = 0;
		for(int loop = 0; loop < strLength; loop += 2){
			arr[index] = str.substring(loop,loop + 2);
			index ++;
		}
		Arrays.sort(arr);
		String ar = Arrays.toString(arr);
		return ar.replace("[", "").replace("]", "").replaceAll(" ", "");
	}
	
	protected String wincodeNoSeque(String str){
		int strLength = str.length();
		String [] arr = new String[strLength/2];
		int index = 0;
		for(int loop = 0; loop < strLength; loop += 2){
			arr[index] = str.substring(loop,loop + 2);
			index ++;
		}
		String ar = Arrays.toString(arr);
		return ar.replace("[", "").replace("]", "").replaceAll(" ", "");
	}
	/**
	 * 修改系统时间
	 * 
	 * @param fctime
	 *            return
	 */
	public static void updateServerTime(String fctime) {
		// String cmd = "/bin/date -s '2012-08-23 10:17:12'"

		// try {
		// String cmd = "/bin/date -s '" + fctime + "'";
		// String[] comands = new String[] { "/bin/sh", "-c", cmd };
		// Process p = Runtime.getRuntime().exec(comands);
		// System.out.println("修改完成-当前系统时间变为:" + new Date());
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

}
