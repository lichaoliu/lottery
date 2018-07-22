package com.lottery.pay.progress.elinkpc.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class CertUtil {
	 protected static Logger logger = LoggerFactory.getLogger(CertUtil.class);
	 private static KeyStore keyStore = null;

	  private static X509Certificate validateCert = null;

	  private static Map<String, X509Certificate> certMap = new HashMap<String, X509Certificate>();

	  private static KeyStore certKeyStore = null;
	 
	  private  static String signCertType="PKCS12";
	  private  static String validateCertPath;

	  public static void initSignCert(String certFilePath, String certPwd)
	  {
	    File files = new File(certFilePath);
	    if (!files.exists()) {
	    	logger.error("证书文件不存在,初始化签名证书失败.");
	      return;
	    }
	    if (certKeyStore != null) {
	      certKeyStore = null;
	    }
	    certKeyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
	  }

	  public static PrivateKey getSignCertPrivateKey(String signCertPwd)
	  {
	    try
	    {
	      Enumeration<?> aliasenum = keyStore.aliases();
	      String keyAlias = null;
	      if (aliasenum.hasMoreElements()) {
	        keyAlias = (String)aliasenum.nextElement();
	      }
	      PrivateKey privateKey = (PrivateKey)keyStore.getKey(keyAlias, signCertPwd.toCharArray());
	      return privateKey;
	    } catch (Exception e) {
	     logger.error("获取签名证书的私钥失败", e);
	    }
	    return null;
	  }

	  /** @deprecated */
	  public  void initValidateCert()
	  {
	    if ((validateCertPath == null) || ("".equals(validateCertPath))) {
	    	logger.error("验证签名证书路径为空");
	      return;
	    }
	    CertificateFactory cf = null;
	    FileInputStream in = null;
	    try {
	      cf = CertificateFactory.getInstance("X.509");
	      in = new FileInputStream(validateCertPath);
	      validateCert = (X509Certificate)cf.generateCertificate(in);
	    } catch (CertificateException e) {
	    	logger.error("验证签名证书加载失败", e);

	      if (in != null)
	        try {
	          in.close();
	        } catch (IOException e1) {
	          logger.error(e1.toString());
	        }
	    }
	    catch (FileNotFoundException e)
	    {
	    	logger.error("验证签名证书加载失败,证书文件不存在", e);
	    }
	    finally
	    {
	      if (in != null) {
	        try {
	          in.close();
	        } catch (IOException e) {
	        	logger.error(e.toString());
	        }
	      }
	    }
	    logger.error("加载验证签名证书结束 ");
	  }

	  public static void initValidateCertFromDir(String validateCertDir)
	  {
	   
	    if ((validateCertDir == null) || ("".equals(validateCertDir))) {
	    	logger.error("验证签名证书路径配置为空.");
	        return;
	    }

	    CertificateFactory cf = null;
	    FileInputStream in = null;
	    try
	    {
	      cf = CertificateFactory.getInstance("X.509");
	      File fileDir = new File(validateCertDir);
	      File[] files = fileDir.listFiles(new CerFilter());
	      for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	        in = new FileInputStream(file.getAbsolutePath());
	        validateCert = (X509Certificate)cf.generateCertificate(in);
	        certMap.put(validateCert.getSerialNumber().toString(),validateCert);
           //logger.error(file.getAbsolutePath() ,"[serialNumber=" + validateCert.getSerialNumber().toString() + "]");
			 // logger.error("a={},b={},c={}",file.getAbsoluteFile(),validateCert.getSerialNumber().toString(),validateCert);
	      }
	    } catch (CertificateException e) {
	    	logger.error("验证签名证书加载失败", e);

	      if (in != null)
	        try {
	          in.close();
	        } catch (IOException e1) {
	        	logger.error(e1.toString());
	        }
	    }
	    catch (FileNotFoundException e)
	    {
	     logger.error("验证签名证书加载失败,证书文件不存在", e);
	      if (in != null)
	        try {
	          in.close();
	        } catch (IOException e1) {
	        	logger.error(e1.toString());
	        }
	    }
	    finally
	    {
	      if (in != null) {
	        try {
	          in.close();
	        } catch (IOException e) {
	        	logger.error(e.toString());
	        }
	      }
	    }
	  }
	  public static PublicKey getValidateKey(String certId,String validateCertDir)
	  {
		//logger.error("certId={}验签证书文件路径{}",certId,validateCertDir);
	    X509Certificate cf = null;
	    if (certMap.containsKey(certId))
	    {
	      cf = (X509Certificate)certMap.get(certId);
	      return cf.getPublicKey();
	    }

	    initValidateCertFromDir(validateCertDir);
	    if (certMap.containsKey(certId))
	    {
	      cf = (X509Certificate)certMap.get(certId);
	      return cf.getPublicKey();
	    }
	    logger.error("没有certId={},validateCertDir={},对应的验签证书文件,返回NULL",certId,validateCertDir);
	    return null;
	  }
	 

	  public static PublicKey getValidateKey()
	  {
	    try
	    {
	      if (validateCert == null) {
	        return null;
	      }
	      return validateCert.getPublicKey();
	    } catch (Exception e) {
	    	logger.error("获取验证签名证书失败", e);
	    }return null;
	  }

	 
	  public  static String getSignCertId(String signCertPath,String signCertPwd)
	  {
	   X509Certificate cert =null;
	    try
	    {
	      keyStore = getKeyInfo(signCertPath, signCertPwd, signCertType);
	      Enumeration<?> aliasenum = keyStore.aliases();
	      String keyAlias = null;
	      if (aliasenum.hasMoreElements()) {
	        keyAlias = (String)aliasenum.nextElement();
	      }
	      cert = (X509Certificate)keyStore.getCertificate(keyAlias);
	    } catch (Exception e) {
	    	logger.error("获取签名证书的序列号失败", e);
	      if (keyStore == null)
	    	logger.error("keyStore实例化失败,当前为NULL,signCertPath={},signCertPwd={}",signCertPath,signCertPwd);
	    }
	    if(cert!=null)
	     return cert.getSerialNumber().toString();
	    
	    return null;
	  }

	
	  public static PublicKey getSignPublicKey()
	  {
	    try
	    {
	      Enumeration<?> aliasenum = keyStore.aliases();
	      String keyAlias = null;
	      if (aliasenum.hasMoreElements())
	      {
	        keyAlias = (String)aliasenum.nextElement();
	      }

	      Certificate cert = keyStore.getCertificate(keyAlias);
	      PublicKey pubkey = cert.getPublicKey();
	      return pubkey;
	    } catch (Exception e) {
	    	logger.error(e.toString());
	    }return null;
	  }

	  public static KeyStore getKeyInfo(String pfxkeyfile, String keypwd, String type)
	  {
	    try
	    {
	      KeyStore ks = null;
	      if ("JKS".equals(type)) {
	        ks = KeyStore.getInstance(type);
	      } else if ("PKCS12".equals(type))
	      {
	        Security.addProvider(new BouncyCastleProvider());
	        ks = KeyStore.getInstance(type);
	      }
	      FileInputStream fis = new FileInputStream(pfxkeyfile);
	      char[] nPassword = (char[])null;
	      nPassword = (keypwd == null) || ("".equals(keypwd.trim())) ? null : 
	        keypwd.toCharArray();
	      if (ks != null) {
	        ks.load(fis, nPassword);
	      }
	      fis.close();

	      return ks;
	    } catch (Exception e) {
	      if (Security.getProvider("BC") == null) {
	    	  logger.error("BC Provider not installed.");
	      }
	      logger.error("读取私钥证书失败", e);
	      if (((e instanceof KeyStoreException)) && ("PKCS12".equals(type)))
	        Security.removeProvider("BC");
	    }
	    return null;
	  }

	  public static String getCertIdByCertPath(String path, String pwd, String certTp)
	  {
	    KeyStore ks = getKeyInfo(path, pwd, certTp);
	    if (ks == null)
	      return "";
	    try
	    {
	      Enumeration<?> aliasenum = ks.aliases();
	      String keyAlias = null;
	      if (aliasenum.hasMoreElements()) {
	        keyAlias = (String)aliasenum.nextElement();
	      }
	      X509Certificate cert = (X509Certificate)ks
	        .getCertificate(keyAlias);
	      return cert.getSerialNumber().toString();
	    } catch (Exception e) {
	    	logger.error("获取签名证书的序列号失败", e);
	    }return "";
	  }

	  public static Map<String, X509Certificate> getCertMap()
	  {
	    return certMap;
	  }

	  public static void setCertMap(Map<String, X509Certificate> certMap)
	  {
	    CertUtil.certMap = certMap;
	  }

	  static class CerFilter
	    implements FilenameFilter
	  {
	    public boolean isCer(String name)
	    {
	      return name.toLowerCase().endsWith(".cer");
	    }

	    public boolean accept(File dir, String name)
	    {
	      return isCer(name);
	    }
	  }
}
