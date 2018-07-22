package com.lottery.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.CharsetConstant;

public class FileUtil {
	
	private static final  Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 字符串写入文本
	 * @param path
	 * @param content
	 * @return
	 */
	public static void strToFile(String path,String content){
		FileWriter fw = null;
		try {
			fw = new FileWriter(path);
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			logger.error(path+"写入失败",e);
			
		} finally{
			if(fw!=null)
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(path+"关闭失败",e);
					e.printStackTrace();
				}
		}
	}
	
public static final int DEFAULT_BUFFER_SIZE = 1 * 1024 * 1024;	// 1M
	
	/**
	 * 把源文件压缩到目标文件
	 * 
	 * @param sourceFileName
	 *            源文件路径
	 * @param targetFileName
	 *            目标文件路径名称
	 * @param deleteSource
	 *            压缩完成后是否删除源文件
	 * @throws IOException
	 */
	public static void zip(String sourceFileName, String destFileName,
			boolean deleteSource, int buffer_size) throws IOException {
        ZipArchiveOutputStream out = null;
        InputStream is = null;  
        try {
        	File srcFile = new File(sourceFileName);
        	
        	if (!srcFile.exists() || !srcFile.canRead()) {
    			throw new IOException("Source file is not readable or not existed");
    		}
        	
        	File destFile = new File(destFileName);
    		File destFilePath = destFile.getParentFile();
    		if (!destFilePath.exists()) {
    			destFilePath.mkdirs();
    		}
        	
            is = new BufferedInputStream(new FileInputStream(srcFile), buffer_size);  
            out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), buffer_size));  
            ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());  
            entry.setSize(srcFile.length());
            out.putArchiveEntry(entry);
            IOUtils.copy(is, out);
            out.closeArchiveEntry();
            IOUtils.closeQuietly(is);
            
            if (deleteSource) {
            	srcFile.delete();
    		}
        } catch (Exception e) {
        	throw new IOException(e.getMessage());
        } finally {  
            IOUtils.closeQuietly(is);  
            IOUtils.closeQuietly(out);  
        }
	}
	
	public static void zip(String sourceFileName, String destFileName,
			boolean deleteSource) throws IOException {
		zip(sourceFileName, destFileName, deleteSource, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * 把源文件压缩到目标文件
	 * 
	 * @param sourceFileName
	 *            源文件路径
	 * @param targetFileName
	 *            目标文件路径名称
	 * @param deleteSource
	 *            压缩完成后是否删除源文件
	 * @throws IOException
	 */
	public static void gzip(String sourceFileName, String destFileName,
			boolean deleteSource, int buffer_size) throws IOException {
		File sourceFile = new File(sourceFileName);
		if (!sourceFile.exists() || !sourceFile.canRead()) {
			throw new IOException("Source file is not readable or not existed");
		}

		File destFile = new File(destFileName);
		File destFilePath = destFile.getParentFile();
		if (!destFilePath.exists()) {
			destFilePath.mkdirs();
		}

		FileInputStream sourceStream = new FileInputStream(sourceFile);
		GZIPOutputStream destStream = new GZIPOutputStream(new FileOutputStream(destFileName));

		byte data[] = new byte[buffer_size];
		while ((sourceStream.read(data, 0, buffer_size)) != -1) {
			destStream.write(data);
		}

		sourceStream.close();
		destStream.close();

		if (deleteSource) {
			sourceFile.delete();
		}
	}

	public static void gzip(String sourceFileName, String destFileName,
			boolean deleteSource) throws IOException {
		gzip(sourceFileName, destFileName, deleteSource, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * 读取文件的首行，如果是空文件返回null
	 * @param fileName
	 * @param newheader
	 * @throws IOException
	 */
	public static String readHeadLine(String fileName){
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader((new FileInputStream(fileName)), CharsetConstant.CHARSET_UTF8));
			line = reader.readLine();
			while (line != null&&line.equals("")) {
				line = reader.readLine();
			}
			reader.close();
			return line;
		} catch (IOException e) {
			return null;
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * 读取文件的最后一行非空行
	 * @param fileName 目标文件路径
	 * @return
	 * @throws IOException
	 */
	public static String readTailLine(String fileName) throws IOException {
		RandomAccessFile fileHandler = new RandomAccessFile(fileName, "r");
		long fileLength = fileHandler.length() - 1;
		long filePointer = fileLength;

		// 从末尾开始找到第一个非换行符之后的换行符作为开始位置
		boolean contentFound = false;
		for (; filePointer != -1; filePointer--) {
			fileHandler.seek(filePointer);
			byte readByte = fileHandler.readByte();

			if (readByte == 0xA || readByte == 0xD) {
				if (!contentFound) {
					continue;
				} else {
					break;
				}
			}else if(!contentFound){
				contentFound = true;
				fileLength = filePointer;//从末尾开始第一个非换行符，作为结束位置
			}	
		}
		
		if (filePointer == -1) {
			filePointer = 0;
		} else {
			filePointer = filePointer + 1;
		}

		fileHandler.seek(filePointer);
		byte[] buffer = new byte[(int) (fileLength - filePointer + 1)];
		fileHandler.read(buffer);
		fileHandler.close();
		return new String(buffer, CharsetConstant.CHARSET_UTF8);
	}
	
	/**
	 * 删除文件的最后一行非空行和之后的空行
	 * @param fileName
	 * @throws IOException
	 */
	public static void deleteLastLine(String fileName) throws IOException {
		RandomAccessFile fileHandler = new RandomAccessFile(fileName, "rw");
		long fileLength = fileHandler.length() - 1;
		long filePointer = fileLength;
		
		boolean contentFound = false;
		for (; filePointer != -1; filePointer--) {
			fileHandler.seek(filePointer);
			byte byteRead = fileHandler.readByte();
			if (byteRead == 0XA || byteRead ==0XD) {
				if (!contentFound) {
					continue;
				} else {
					break;
				}
			} else {
				contentFound = true;
			}
		}
		
		fileHandler.setLength(filePointer+1);
		fileHandler.close();
	}
	
	/**
	 * 把源文件拷贝的到目标文件
	 * @param sourceFileName 源文件路径
	 * @param destFileName 目标文件路径
	 * @throws IOException
	 */
	public static void move(String sourceFileName, String destFileName) throws IOException{
		File srcFile = new File(sourceFileName);
    	
    	if (!srcFile.exists() || !srcFile.canRead()) {
			throw new IOException("Source file is not readable or not existed");
		}
    	
    	File destFile = new File(destFileName);
		File destFilePath = destFile.getParentFile();
		if (!destFilePath.exists()) {
			destFilePath.mkdirs();
		}
		
		boolean success = srcFile.renameTo(destFile); 
		if (!success) {
			throw new IOException("failed to move file: " + srcFile.getAbsolutePath());
		}
	}

}
