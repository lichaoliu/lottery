package com.lottery.ticket.vender.lotterycenter.tianjin;



public abstract class AbstractFtpHandler  {

	/*protected  final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	protected VenderPhaseDrawHandler venderPhaseDrawHandler;

	@PostConstruct
	public void makeFtpDir() {
		File dirFile = new File(ConfigEnum.localdir.getValue());
		if (!dirFile.exists()) {
			logger.debug("创建ftp目录");
			dirFile.mkdir();
		}
	}

	protected String getPhase(int lotteryType,String phase){
		if(lotteryType==LotteryType.TJKL10.value){
			return CoreDateUtils.formatDate(new Date(), CoreDateUtils.DATE_YEAR)+phase;
		}
		return phase;
	}


	*//**
	 * 初始化ftp客户端
	 * 
	 * @return
	 *//*
	private FTPClient getFTPClient() {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ConfigEnum.tjfcftpip.getValue(), Integer
					.valueOf(ConfigEnum.tjfcftpport.getValue()));

			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.info("登录ftp失败");
				return null;
			}
			ftpClient.login(ConfigEnum.tjfcftpname.getValue(),
					ConfigEnum.tjfcftppw.getValue());
			ftpClient.setDataTimeout(5000);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (NumberFormatException e) {
			logger.error("初始ftp客户端化失败",e);
		} catch (SocketException e) {
			logger.error("初始ftp客户端化失败",e);
		} catch (IOException e) {
			logger.error("初始ftp客户端化失败",e);
		}
		return ftpClient;
	}

	*//**
	 * 下载ftp文件
	 * 
	 * @param name
	 *            ftp文件名
	 * @return
	 *//*
	protected String downloadFtp(String name) {
		String localAddr = ConfigEnum.localdir.getValue() + name;
		FTPClient ftpClient = getFTPClient();
		try {	
			ftpClient.setControlEncoding("utf-8");
			File localFile = new File(localAddr);
//			File ftpFile = new File(ConfigEnum.tjfcftpdir.getValue() + name);
			FTPFile[] ftpFiles = ftpClient.listFiles(ConfigEnum.tjfcftpdir.getValue() + name);
			
			if(ftpFiles.length == 0){
				localAddr = "0";
			}else{
				OutputStream oStream = new FileOutputStream(localFile);
				ftpClient.retrieveFile(ConfigEnum.tjfcftpdir.getValue() + name,
						oStream);
				oStream.close();
			}
			
		} catch (FileNotFoundException e) {
			logger.error("读取本地文件出错",e);
		} catch (IOException e) {
			logger.error("ftp写入出错",e);
		} finally {
			closeFtp(ftpClient);
		}
		return localAddr;

	}

	*//**
	 * 上传ftp文件
	 * 
	 * @param rPath
	 *            需上传文件路径
	 * @param wPath
	 *            上传文件路径目的地
	 * @param fileName
	 *            上传后文件名
	 * @return
	 *//*
	protected boolean uploadFtp(String rPath, String wPath, String fileName) {
		FTPClient ftpClient = getFTPClient();
		ftpClient.setControlEncoding("utf-8");
		boolean result = false;
		try {
			FileInputStream in = new FileInputStream(new File(rPath));
			boolean change = ftpClient.changeWorkingDirectory(wPath);
			if (change) {
				result = ftpClient.storeFile(fileName, in);
			}
			in.close();
		} catch (FileNotFoundException e) {
			logger.error("读取本地文件出错",e);
		} catch (IOException e) {
			logger.error("ftp写入出错",e);
		} finally {
			closeFtp(ftpClient);
		}
		return result;
	}

	*//**
	 * 关闭ftp
	 * 
	 * @param ftpClient
	 *//*
	private static void closeFtp(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				boolean isLogOut = ftpClient.logout();
				if (isLogOut) {
//					logger.info("成功关闭ftp连接");
				}
			} catch (IOException e) {
				 
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					 
				}
			}
		}
	}

	*//**
	 * 读取ftp文件内容
	 * 
	 * @param fileName
	 *            文件完整路径及名称
	 * @param size
	 *            长度
	 * @return
	 *//*
	public String readFile(String fileName, int size) throws Exception {
		String dRs = "";
		InputStream fs = null;
		byte[] tempb = new byte[size];
		byte[] rByte = null;
		try {
			int byteread = 0;
			fs = new FileInputStream(fileName);
			byteread = fs.read(tempb);
			if (byteread <= 0)
				return null;
			rByte = new byte[byteread];
			System.arraycopy(tempb, 0, rByte, 0, byteread);
			DESLotteryCenter.SetNoticeKey(ConfigEnum.tjfcNotice.getValue());
			dRs = DESLotteryCenter.encrypt(rByte, 2);
		} catch (FileNotFoundException e) {
			logger.error(fileName + "未找到文件",e);
			throw new LotteryException("未找到文件");
		} catch (IOException e) {
			logger.error(fileName + "读取新期文件错误",e);
		} finally {
			try {
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				logger.error(fileName + "流关闭失败",e);
			}
		}
		// return result;
		return dRs;
	}

	@Override
	protected <T> byte[] makeMessagePkg(T t) {
		StringBuffer str = new StringBuffer();
		str.append(paddingMessage((String) t, 4));
		String md5 = MD5Util.toMd5(str.toString());
		str.append(md5 + "/t");
		String strRes = hanlerMessage(str.toString());
		DESLotteryCenter.SetNoticeKey(ConfigEnum.tjfcNotice.getValue());
		byte[] pkByte = DESLotteryCenter.decrypt(strRes, 2);
		return pkByte;
	}

	*//**
	 * 拼接文件路径
	 * 
	 * @param msg
	 * @param pri
	 * @param suffix
	 * @return
	 *//*
	protected String creteFcFileName(byte[] msg, String pri, String suffix) {
		DESLotteryCenter.SetNoticeKey(ConfigEnum.tjfcNotice.getValue());
		String fcMsgReq = splitMsg(msg, 2);
		logger.error("福彩文件原始内容:{}",fcMsgReq);
		String[] msgArr = fcMsgReq.split("\t");
		String gameName = msgArr[0];
		String issue = msgArr[1];
//		logger.info("拼接" + pri + "下载:" + pri + "_" + gameName + "_" + issue + "."
//				+ suffix);
		return pri + "_" + gameName + "_" + issue + "." + suffix;
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
		String resultStr = "";
		int strLength = str.length();
		for(int loop = 0; loop < strLength; loop ++){
			if(loop > 0) resultStr += ",";
			resultStr += str.substring(loop, loop + 1);
		}
		return resultStr;
	}
	
	protected String makeGlobal(String str){
		logger.info("开奖号码为：" + str);
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
*/
}
