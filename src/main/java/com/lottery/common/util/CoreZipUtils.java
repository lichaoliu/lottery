package com.lottery.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreZipUtils {
	private static final Logger logger = LoggerFactory.getLogger(CoreZipUtils.class.getName());
	private static final int BUFFER = 2048;
	
	public static void zip(File file, String pathAndName) {
		logger.info("zip pathAndName to file");
		try {  
			BufferedInputStream origin = null;  
			FileOutputStream dest = new FileOutputStream(file);  
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));  
			byte data[] = new byte[BUFFER];  
			File f = new File(pathAndName);  
			File files[] = f.listFiles();  
			for (int i = 0; i < files.length; i++) {  
			    FileInputStream fi = new FileInputStream(files[i]);  
			    origin = new BufferedInputStream(fi, BUFFER);  
			    ZipEntry entry = new ZipEntry(files[i].getName());  
			    out.putNextEntry(entry);  
			    int count;  
			    while ((count = origin.read(data, 0, BUFFER)) != -1) {  
			      out.write(data, 0, count);  
			    }  
			    origin.close();  
			}  
			out.close();  
		} catch (Exception e) {  
			logger.error(e.getMessage(), e);
		}  
	}
	
	
	public static void unzip(File file, String path) {
		logger.info("unzip file to pathAndName");
		try {  
			ZipFile zipFile = new ZipFile(file);  
			Enumeration<? extends ZipEntry> emu = zipFile.entries();  
			while (emu.hasMoreElements()) {  
				ZipEntry entry = (ZipEntry) emu.nextElement();  
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。  
				if (entry.isDirectory()) {  
					new File(path + entry.getName()).mkdirs();  
					continue;  
				}  
				BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));  
				File f = new File(path + entry.getName());  
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件  
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。  
			    File parent = f.getParentFile();  
			    if (parent != null && (!parent.exists())) {  
			    	parent.mkdirs();  
			    }  
			    FileOutputStream fos = new FileOutputStream(f);  
			    BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);  
			    int count;  
			    byte data[] = new byte[BUFFER];  
			    while ((count = bis.read(data, 0, BUFFER)) != -1) {  
			      bos.write(data, 0, count);  
			    }  
			    bos.flush();  
			    bos.close();  
			    bis.close();  
			 }  
			zipFile.close();  
		} catch (Exception e) {  
			logger.error(e.getMessage(), e);
		}  
	}
}
