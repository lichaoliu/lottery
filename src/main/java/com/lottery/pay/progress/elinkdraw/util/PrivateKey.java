package com.lottery.pay.progress.elinkdraw.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class PrivateKey {
	  protected byte[] Modulus;
	  protected byte[][] Prime;
	  protected byte[][] PrimeExponent;
	  protected byte[] Coefficient;
	  
	  public PrivateKey()
	  {
	    this.Modulus = new byte[''];
	    this.Prime = new byte[2][64];
	    this.PrimeExponent = new byte[2][64];
	    this.Coefficient = new byte[64];
	  }
	  
	  public boolean buildKey(String MerID, int KeyUsage, String KeyFile)
	  {
	    byte[] iv = new byte[8];
	    DesKey ks = new DesKey("SCUBEPGW".getBytes(), false);
	    Des des = new Des(ks);
	    byte[] KeyBuf = new byte[712];
	    BufferedReader br=null;
	    try
	    {
	      FileInputStream fis = new FileInputStream(KeyFile);
	      br = new BufferedReader(new InputStreamReader(fis));
	    }
	    catch (FileNotFoundException _ex)
	    {
	    
	      return false;
	    }
	    String MerPG_flag;
	    String tmpString;
	    BigInteger Convert=null;
	    try
	    {
	      tmpString = br.readLine();
	      if ((tmpString.compareTo("[SecureLink]") != 0) && (tmpString.compareTo("[NetPayClient]") != 0))
	      {
	        boolean flag = false;
	        boolean flag3 = flag;
	        return flag3;
	      }
	      tmpString = br.readLine();
	      int m_Pos = tmpString.indexOf("=");
	      MerPG_flag = tmpString.substring(0, m_Pos);
	      tmpString = tmpString.substring(m_Pos + 1, tmpString.length());
	      if (tmpString.compareTo(MerID) != 0)
	      {
	        boolean flag1 = false;
	        boolean flag4 = flag1;
	        return flag4;
	      }
	      if (KeyUsage == 0)
	      {
	        tmpString = br.readLine();
	        if (MerPG_flag.compareTo("PGID") != 0) {
	          tmpString = tmpString.substring(88, tmpString.length());
	        } else {
	          tmpString = tmpString.substring(56, tmpString.length());
	        }
	      }
	      else
	      {
	        tmpString = br.readLine();
	        tmpString = br.readLine();
	        if (MerPG_flag.compareTo("PGID") != 0) {
	          tmpString = tmpString.substring(88, tmpString.length());
	        } else {
	          tmpString = tmpString.substring(56, tmpString.length());
	        }
	      }
	    }
	    catch (Exception IE)
	    {
	      boolean flag2 = false;
	      boolean flag5 = flag2;
	      return flag5;
	    }
	    finally
	    {
	      try
	      {
	        if (br != null) {
	          br.close();
	        }
	      }
	      catch (IOException _ex)
	      {
	        return false;
	      }
	    }
	    KeyBuf = Convert.toByteArray();
	    if (KeyBuf[0] == 0) {
	      if (MerPG_flag.compareTo("PGID") != 0) {
	        for (int i = 0; i < 712; i++) {
	          KeyBuf[i] = KeyBuf[(i + 1)];
	        }
	      } else {
	        for (int i = 0; i < 264; i++) {
	          KeyBuf[i] = KeyBuf[(i + 1)];
	        }
	      }
	    }
	    System.arraycopy(KeyBuf, 0, this.Modulus, 0, 128);
	    if (MerPG_flag.compareTo("MERID") == 0)
	    {
	      memset(iv, (byte)0, iv.length);
	      des.cbc_encrypt(KeyBuf, 384, 64, this.Prime[0], 0, iv, false);
	      memset(iv, (byte)0, iv.length);
	      des.cbc_encrypt(KeyBuf, 448, 64, this.Prime[1], 0, iv, false);
	      memset(iv, (byte)0, iv.length);
	      des.cbc_encrypt(KeyBuf, 512, 64, this.PrimeExponent[0], 0, iv, false);
	      memset(iv, (byte)0, iv.length);
	      des.cbc_encrypt(KeyBuf, 576, 64, this.PrimeExponent[1], 0, iv, false);
	      memset(iv, (byte)0, iv.length);
	      des.cbc_encrypt(KeyBuf, 640, 64, this.Coefficient, 0, iv, false);
	    }
	    else if (MerPG_flag.compareTo("PGID") != 0)
	    {
	      return false;
	    }
	    return true;
	  }
	  
	  private void memset(byte[] out, byte c, int len)
	  {
	    for (int i = 0; i < len; i++) {
	      out[i] = c;
	    }
	  }
	  
	  private final String DESKEY = "SCUBEPGW";
}
