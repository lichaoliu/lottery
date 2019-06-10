package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class TJFCFtpService {


	public static FTPClient getFTPClient(String ip, int port, String username, String passwd) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ip, port);
		ftpClient.login(username, passwd);
		int reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			String errormessage=String.format("ftp登录返回失败,返回码:%s,ip:%s,port:%s,username:%s,passwd:%s", reply,ip,port,username,passwd);
		    throw new Exception(errormessage);
		}
		
		ftpClient.setDataTimeout(5000);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		return ftpClient;
	}

	/**
	 * 下载文件
	 * 
	 * @param filename
	 *            文件名字
	 * @param localdir
	 *            本地文件夹
	 * @param remotedir
	 *            远程文件夹
	 * */
	public static String downloadFtp(String filename, String localdir, String remotedir, FTPClient ftpClient) throws Exception {
		String localAddr = localdir + filename;
		try {
			String trueStr="";
			ftpClient.setControlEncoding("utf-8");
			ftpClient.changeWorkingDirectory(remotedir);	
			FTPFile[] ftpFiles = ftpClient.listFiles();
			if(ftpFiles.length==0){
				throw new Exception("ftp源未找到文件:"+filename);
			}
			 for (FTPFile ff : ftpFiles){
				 if(ff.getName().equals(filename)){
					 trueStr=ff.getName();
					 File localFile = new File(localAddr);
					 OutputStream oStream = new FileOutputStream(localFile);
						ftpClient.retrieveFile(ff.getName(), oStream);
						oStream.close();
						break;
				 }
			 }

			 if(StringUtils.isBlank(trueStr)){
				 throw new Exception("服务不存在文件:"+filename);
			 }

		} catch (Exception e) {
			throw e;
		} finally {
			closeFtp(ftpClient);
		}
		return localAddr;

	}

	/**
	 * 上传ftp文件
	 * 
	 * @param rPath
	 *            需上传文件路径
	 * @param wPath
	 *            上传文件路径目的地
	 * @param fileName
	 *            上传后文件名
	 * @return
	 */
	protected static boolean uploadFtp(String rPath, String wPath, String fileName, FTPClient ftpClient) throws Exception {

		try {
			ftpClient.setControlEncoding("utf-8");
			FileInputStream in = new FileInputStream(new File(rPath));
			boolean change = ftpClient.changeWorkingDirectory(wPath);
			if (change) {
				ftpClient.storeFile(fileName, in);
			}
			in.close();
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			closeFtp(ftpClient);
		}
	}

	/**
	 * 关闭ftp
	 * 
	 * @param ftpClient
	 */
	private static void closeFtp(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();

			} catch (IOException e) {

			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {

				}
			}
		}
	}

	/**
	 * 读取ftp文件内容
	 * 
	 * @param fileName
	 *            文件完整路径及名称
	 * @param size
	 *            长度
	 * @return
	 */
	public static String readFile(String fileName, int size, String key) throws Exception {
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

			return DESTJFC.decrypt(rByte, key);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				throw e;
			}
		}

	}

}
