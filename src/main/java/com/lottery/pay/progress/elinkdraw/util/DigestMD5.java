package com.lottery.pay.progress.elinkdraw.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestMD5 {
	private static MessageDigest alg;
	  private static int length;
	  
	  public static String MD5Sign(String merId, String fileName, byte[] fileContent, String MerKeyPath)
	    throws Exception
	  {
	    String MD5 = getMD5Content(fileContent);
	    String plainData = merId + fileName + MD5;
	    int KeyUsage = 0;
	    PrivateKey key = new PrivateKey();
	    key.buildKey(merId, KeyUsage, MerKeyPath);
	    SecureLink sl = new SecureLink(key);
	    String chkValue = sl.Sign(plainData);
	    return chkValue;
	  }
	  
	  public static String MD5Sign(String merId, String fileName, String fileContent, String MerKeyPath)
	    throws Exception
	  {
	    String MD5 = getMD5Content(fileContent);
	    String plainData = merId + fileName + MD5;
	    int KeyUsage = 0;
	    PrivateKey key = new PrivateKey();
	    key.buildKey(merId, KeyUsage, MerKeyPath);
	    SecureLink sl = new SecureLink(key);
	    String chkValue = sl.Sign(plainData);
	    return chkValue;
	  }
	  
	  public static boolean MD5Verify(String Plain, String CheckValue, String PubKeyPath)
	    throws Exception
	  {
	    String plainData = getMD5Content(Plain);
	    boolean res = false;
	    PrivateKey key = new PrivateKey();
	    key.buildKey("999999999999999", 0, PubKeyPath);
	    SecureLink s2 = new SecureLink(key);
	    res = s2.verifyAuthToken(plainData, CheckValue);
	    return res;
	  }
	  
	  public static String getMD5Content(String fileContent)
	    throws Exception
	  {
	    byte[] tmpByte = getMD5(fileContent.getBytes(), 
	      fileContent.getBytes().length);
	    String tmpRetMd5 = Hex2Asc(tmpByte.length, tmpByte);
	    return tmpRetMd5;
	  }
	  
	  public static String getMD5Content(byte[] fileContent)
	    throws Exception
	  {
	    byte[] tmpByte = getMD5(fileContent, 
	      fileContent.length);
	    String tmpRetMd5 = Hex2Asc(tmpByte.length, tmpByte);
	    return tmpRetMd5;
	  }
	  
	  public static String Hex2Asc(int len, byte[] in)
	  {
	    byte[] out = new byte[2 * len];
	    for (int i = 0; i < len; i++)
	    {
	      byte h = (byte)(in[i] >> 4);
	      h = (byte)(h & 0xF);
	      byte l = (byte)(in[i] & 0xF);
	      out[(i * 2)] = ((byte)(h > 9 ? 65 + h - 10 : 48 + h));
	      out[(i * 2 + 1)] = ((byte)(l > 9 ? 65 + l - 10 : 48 + l));
	    }
	    return new String(out);
	  }
	  
	  private static int maxBytesReadPerTime = 8192;
	  
	  static
	  {
	    try
	    {
	      alg = MessageDigest.getInstance("MD5");
	      length = alg.getDigestLength();
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public static byte[] getMD5(InputStream is, long fileLength)
	    throws Exception
	  {
	    byte[] bytes = new byte[maxBytesReadPerTime];
	    int bytesRead = 0;
	    int maxReadTimesBeforeRedigest = 100;
	    byte[] digestOfPart = new byte[length];
	    byte[] digest = new byte[length * maxReadTimesBeforeRedigest];
	    int timesRead = 0;
	    byte[] digestToReturn = new byte[length];
	    try
	    {
	      while (fileLength > 0L)
	      {
	        bytesRead = is.read(bytes);
	        digestOfPart = getMD5(bytes, 0, bytesRead);
	        System.arraycopy(digestOfPart, 0, digest, timesRead * length, 
	          length);
	        timesRead++;
	        if (timesRead == maxReadTimesBeforeRedigest)
	        {
	          digestOfPart = getMD5(digest, 0, digest.length);
	          System.arraycopy(digestOfPart, 0, digest, 0, length);
	          timesRead = 1;
	        }
	        fileLength -= bytesRead;
	      }
	      if (timesRead > 1)
	      {
	        digestOfPart = getMD5(digest, 0, timesRead * length);
	        System.arraycopy(digestOfPart, 0, digestToReturn, 0, length);
	      }
	      else
	      {
	        System.arraycopy(digest, 0, digestToReturn, 0, length);
	      }
	      if (is != null) {
	        is.close();
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return digestToReturn;
	  }
	  
	  public static byte[] getMD5(byte[] in)
	  {
	    alg.update(in);
	    return alg.digest();
	  }
	  
	  public static byte[] getMD5(byte[] in, int from, int len)
	  {
	    alg.update(in, from, len);
	    return alg.digest();
	  }
	  
	  public static int getDigestLength()
	  {
	    return length;
	  }
	  
	  public static byte[] getMd5(byte[] tmpValue)
	  {
	    alg.update(tmpValue);
	    byte[] digest = alg.digest();
	    return digest;
	  }
	  
	  public static byte[] getMD5(byte[] is, long fileLength)
	  {
	    byte[] bytes = new byte[maxBytesReadPerTime];
	    int bytesRead = 0;
	    int maxReadTimesBeforeRedigest = 100;
	    byte[] digestOfPart = new byte[length];
	    byte[] digest = new byte[length * maxReadTimesBeforeRedigest];
	    int timesRead = 0;
	    int readTimes = 0;
	    byte[] digestToReturn = new byte[length];
	    while (fileLength > 0L)
	    {
	      if (fileLength >= maxBytesReadPerTime)
	      {
	        System.arraycopy(is, readTimes * maxBytesReadPerTime, bytes, 0, 
	          maxBytesReadPerTime);
	        bytesRead = maxBytesReadPerTime;
	      }
	      else
	      {
	        System.arraycopy(is, readTimes * maxBytesReadPerTime, bytes, 0, 
	          (int)fileLength);
	        bytesRead = (int)fileLength;
	      }
	      digestOfPart = getMD5(bytes, 0, bytesRead);
	      System.arraycopy(digestOfPart, 0, digest, timesRead * length, 
	        length);
	      timesRead++;
	      readTimes++;
	      if (timesRead == maxReadTimesBeforeRedigest)
	      {
	        digestOfPart = getMD5(digest, 0, digest.length);
	        System.arraycopy(digestOfPart, 0, digest, 0, length);
	        timesRead = 1;
	      }
	      fileLength -= bytesRead;
	    }
	    if (timesRead > 1)
	    {
	      digestOfPart = getMD5(digest, 0, timesRead * length);
	      System.arraycopy(digestOfPart, 0, digestToReturn, 0, length);
	    }
	    else
	    {
	      System.arraycopy(digest, 0, digestToReturn, 0, length);
	    }
	    return digestToReturn;
	  }
}
