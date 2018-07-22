package com.lottery.pay.progress.elinkdraw.util;

public class DesRsa {
	public static char[] DigitalMap = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	  
	  public static String i2a(int x, int len, int base)
	  {
	    char[] s = new char[len];
	    for (int i = len - 1; i >= 0; i--)
	    {
	      s[i] = DigitalMap[(x % base)];
	      x /= base;
	    }
	    return new String(s);
	  }
	  
	  public static int c2i(char c)
	  {
	    for (int i = 0; i < 16; i++) {
	      if (c == DigitalMap[i]) {
	        return i;
	      }
	    }
	    return 0;
	  }
	  
	  public static byte[] byte2Key(byte[] key0, int len)
	  {
	    byte[] Key = new byte[len];
	    int i=0;
	    int keylen = key0.length;
	    if (keylen == len) {
	      return key0;
	    }
	    if (keylen > len)
	    {
	      for ( i = 0; i < len; i++) {
	        Key[i] = key0[i];
	      }
	    }
	    else if (keylen < len)
	    {
	      for (i = 0; i < keylen; i++) {
	        Key[i] = key0[i];
	      }
	      for (; i < len; i++) {
	        Key[i] = 32;
	      }
	    }
	    return Key;
	  }
	  
	  public static String SingleDes(String ClearStr, byte[] key, boolean flag)
	  {
	    byte[] clrb = new byte[8];
	    byte[] iv = new byte[8];
	    for (int j = 0; j < 8; j++) {
	      iv[j] = 0;
	    }
	    
	    byte[] key0 = byte2Key(key, 8);
	    
	    DesKey ks = new DesKey(key0, false);
	    Des des = new Des(ks);
	    
	    int strlen = ClearStr.length();
	    if (flag)
	    {
	      if (strlen > 8) {
	        ClearStr = ClearStr.substring(0, 8);
	      }
	      if (strlen < 8) {
	        for (int i = 1; i <= 8 - strlen; i++) {
	          ClearStr = ClearStr + " ";
	        }
	      }
	      clrb = ClearStr.getBytes();
	    }
	    else
	    {
	      if (strlen > 16) {
	        ClearStr = ClearStr.substring(0, 16);
	      }
	      if (strlen < 16) {
	        for (int i = 1; i <= 16 - strlen; i++) {
	          ClearStr = ClearStr + "0";
	        }
	      }
	      for (int i = 0; i < 8; i++) {
	        clrb[i] = ((byte)((c2i(ClearStr.charAt(i * 2)) & 0xF) << 4 | c2i(ClearStr.charAt(i * 2 + 1)) & 0xF));
	      }
	    }
	    byte[] out = new byte[8];
	    des.cbc_encrypt(clrb, 0, 8, out, 0, iv, flag);
	    String OutStr="";
	    if (flag)
	    {
	      for (int i = 0; i < 8; i++)
	      {
	        OutStr = OutStr + i2a(out[i] >> 4 & 0xF, 1, 16);
	        OutStr = OutStr + i2a(out[i] & 0xF, 1, 16);
	      }
	    }
	    else
	    {
	      OutStr = new String(out, 0, 8);
	    }
	    return OutStr;
	  }
	  
	  public static String SingleDes(byte[] ClearStr, byte[] key, boolean flag)
	  {
	    byte[] clrb = new byte[8];
	    byte[] iv = new byte[8];
	    for (int j = 0; j < 8; j++) {
	      iv[j] = 0;
	    }
	    byte[] key0 = byte2Key(key, 8);
	    
	    DesKey ks = new DesKey(key0, false);
	    Des des = new Des(ks);
	    

	    int strlen = ClearStr.length;
	    if (flag)
	    {
	      if (strlen < 8) {
	        for (int idx = strlen; idx < 8; idx++) {
	          ClearStr[idx] = 0;
	        }
	      }
	      for (int idx = 0; idx < 8; idx++) {
	        clrb[idx] = ClearStr[idx];
	      }
	    }
	    else
	    {
	      if (strlen < 16) {
	        for (int idx = strlen; idx < 8; idx++) {
	          ClearStr[idx] = 0;
	        }
	      }
	      for (int i = 0; i < 8; i++) {
	        clrb[i] = ((byte)((c2i((char)ClearStr[(i * 2)]) & 0xF) << 4 | c2i((char)ClearStr[(i * 2 + 1)]) & 0xF));
	      }
	    }
	    byte[] out = new byte[8];
	    des.cbc_encrypt(clrb, 0, 8, out, 0, iv, flag);
	    String OutStr="";
	    if (flag)
	    {
	      for (int i = 0; i < 8; i++)
	      {
	        OutStr = OutStr + i2a(out[i] >> 4 & 0xF, 1, 16);
	        OutStr = OutStr + i2a(out[i] & 0xF, 1, 16);
	      }
	    }
	    else
	    {
	      OutStr = new String(out, 0, 8);
	    }
	    return OutStr;
	  }
	  
	  public static String SingleDesEnc(byte[] ClearStr, byte[] key)
	  {
	    return SingleDes(ClearStr, key, true);
	  }
	  
	  public static String SingleDesEnc(String ClearStr, byte[] key)
	  {
	    return SingleDes(ClearStr, key, true);
	  }
	  
	  public static String SingleDesDec(String ClearStr, byte[] key)
	  {
	    return SingleDes(ClearStr, key, false);
	  }
	  
	  public static String TripleDes(String ClearStr, byte[] Key, boolean flag)
	  {
		int i=0;
	    byte[] clrb = new byte[8];
	    byte[] iv = new byte[8];
	    for (int j = 0; j < 8; j++) {
	      iv[j] = 0;
	    }
	    byte[] key1 = byte2Key(Key, 16);
	    byte[] key2 = new byte[8];
	    for (i = 0; i < 8; i++) {
	      key2[i] = key1[(8 + i)];
	    }
	    DesKey ks1 = new DesKey(key1, false);
	    DesKey ks2 = new DesKey(key2, false);
	    
	    TripleDes tdes = new TripleDes(ks1, ks2);
	    
	    int strlen = ClearStr.length();
	    if (flag)
	    {
	      if (strlen > 8) {
	        ClearStr = ClearStr.substring(0, 8);
	      }
	      if (strlen < 8) {
	        for (i = 1; i <= 8 - strlen; i++) {
	          ClearStr = ClearStr + " ";
	        }
	      }
	      clrb = ClearStr.getBytes();
	    }
	    else
	    {
	      if (strlen > 16) {
	        ClearStr = ClearStr.substring(0, 16);
	      }
	      if (strlen < 16) {
	        for (i = 1; i <= 16 - strlen; i++) {
	          ClearStr = ClearStr + "0";
	        }
	      }
	      for (i = 0; i < 8; i++) {
	        clrb[i] = ((byte)((c2i(ClearStr.charAt(i * 2)) & 0xF) << 4 | c2i(ClearStr.charAt(i * 2 + 1)) & 0xF));
	      }
	    }
	    byte[] out = new byte[8];
	    tdes.cbc_encrypt(clrb, 0, 8, out, 0, iv, flag);
	    String OutStr="";
	    if (flag)
	    {
	      for (i = 0; i < 8; i++)
	      {
	        OutStr = OutStr + i2a(out[i] >> 4 & 0xF, 1, 16);
	        OutStr = OutStr + i2a(out[i] & 0xF, 1, 16);
	      }
	    }
	    else
	    {
	      OutStr = new String(out, 0, 8);
	    }
	    return OutStr;
	  }
	  
	  public static String TripleDesEnc(String ClearStr, byte[] Key)
	  {
	    return TripleDes(ClearStr, Key, true);
	  }
	  
	  public static String TripleDesDec(String ClearStr, byte[] Key)
	  {
	    return TripleDes(ClearStr, Key, false);
	  }
	  
	  String DES0(String Key, String s_text)
	  {
	    String d_text = "";
	    
	    int len = s_text.length();
	    int k;
	    if (len % 8 == 0) {
	      k = len / 8;
	    } else {
	      k = len / 8 + 1;
	    }
	    s_text = s_text + "         ";
	    for (int i = 0; i < k; i++)
	    {
	      String buf1 = s_text.substring(i * 8, (i + 1) * 8);
	      
	      String buf2 = SingleDesEnc(buf1, Key.getBytes());
	      

	      d_text = d_text + buf2;
	    }
	    return d_text;
	  }
	  
	  String _DES0(String Key, String s_text)
	  {
	    String d_text = "";
	    
	    int len = s_text.length();
	    int k;
	    if (len % 16 == 0) {
	      k = len / 16;
	    } else {
	      k = len / 16 + 1;
	    }
	    for (int i = 0; i < k; i++)
	    {
	      String buf1 = s_text.substring(i * 16, (i + 1) * 16);
	      


	      String buf2 = SingleDesDec(buf1, Key.getBytes());
	      


	      d_text = d_text + buf2;
	    }
	    return d_text;
	  }
	  
	  public static byte[] bTripleDes(byte[] clrb, byte[] key, boolean flag)
	  {
	    byte[] iv = new byte[8];
	    for (int j = 0; j < 8; j++) {
	      iv[j] = 0;
	    }
	    byte[] key1 = new byte[8];
	    byte[] key2 = new byte[8];
	    for (int i = 0; i < 8; i++)
	    {
	      key1[i] = key[i];
	      key2[i] = key[(i + 8)];
	    }
	    DesKey ks1 = new DesKey(key1, false);
	    DesKey ks2 = new DesKey(key2, false);
	    
	    TripleDes tdes = new TripleDes(ks1, ks2);
	    
	    byte[] out = new byte[8];
	    tdes.cbc_encrypt(clrb, 0, 8, out, 0, iv, flag);
	    
	    return out;
	  }
	  
	  public static byte[] bTripleDesEnc(byte[] ClearStr, byte[] Key)
	  {
	    return bTripleDes(ClearStr, Key, true);
	  }
	  
	  public static byte[] bTripleDesDec(byte[] ClearStr, byte[] Key)
	  {
	    return bTripleDes(ClearStr, Key, false);
	  }
	  
	  public byte[] DES3b(byte[] Key, byte[] s_text)
	  {
	    byte[] buf1 = new byte[8];
	    byte[] buf2 = new byte[8];
	    byte[] IV_temp = "ChinaPay".getBytes();
	    byte[] tail = "         ".getBytes();
	    
	    int len = s_text.length;
	    
	    byte[] bText = new byte[len + tail.length];
	    int k;
	    if (len % 8 == 0) {
	      k = len / 8;
	    } else {
	      k = len / 8 + 1;
	    }
	    System.arraycopy(s_text, 0, bText, 0, len);
	    System.arraycopy(tail, 0, bText, len, tail.length);
	    
	    byte[] d_text = new byte[k * 8];
	    for (int i = 0; i < k; i++)
	    {
	      System.arraycopy(bText, i * 8, buf1, 0, 8);
	      for (int j = 0; j < 8; j++) {
	        buf1[j] = ((byte)(IV_temp[j] ^ buf1[j]));
	      }
	      buf2 = bTripleDesEnc(buf1, Key);
	      for (int j = 0; j < 8; j++) {
	        IV_temp[j] = buf2[j];
	      }
	      System.arraycopy(d_text, 0, d_text, 0, i * 8);
	      System.arraycopy(buf2, 0, d_text, i * 8, 8);
	    }
	    return d_text;
	  }
	  
	  byte[] _DES3bs(byte[] Key, byte[] stringgetbyte)
	  {
	    String sorg = new String(stringgetbyte);
	    int slen = sorg.length();
	    if (slen % 8 != 0)
	    {
	      byte[] err = "InStream bitNum wrong!".getBytes();
	      return err;
	    }
	    byte[] bOrg = Asc2Hex(slen, sorg);
	    return _DES3b(Key, bOrg);
	  }
	  
	  public byte[] _DES3b(byte[] Key, byte[] s_text)
	  {
	    byte[] buf1 = new byte[8];
	    byte[] buf2 = new byte[8];
	    byte[] IV_temp = "ChinaPay".getBytes();
	    int len = s_text.length;
	    int k;
	    if (len % 8 == 0) {
	      k = len / 8;
	    } else {
	      k = len / 8 + 1;
	    }
	    byte[] d_text = new byte[k * 8];
	    for (int i = 0; i < k; i++)
	    {
	      System.arraycopy(s_text, i * 8, buf1, 0, 8);
	      buf2 = bTripleDesDec(buf1, Key);
	      for (int j = 0; j < 8; j++)
	      {
	        buf2[j] = ((byte)(IV_temp[j] ^ buf2[j]));
	        IV_temp[j] = buf1[j];
	      }
	      System.arraycopy(buf2, 0, d_text, i * 8, 8);
	    }
	    return d_text;
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
	  
	  public static byte[] Asc2Hex(int len, String in)
	  {
	    in = in.toUpperCase();
	    if (len % 2 != 0) {
	      return null;
	    }
	    for (int i = 0; i < len; i++)
	    {
	      char ch = in.charAt(i);
	      if ((ch < '0') || ((ch > '9') && (ch < 'A')) || (ch > 'Z')) {
	        return null;
	      }
	    }
	    byte[] out = new byte[len / 2];
	    for (int i = 0; i < len / 2; i++)
	    {
	      char ch = in.charAt(i * 2);
	      byte h = (byte)((ch >= '0') && (ch <= '9') ? ch - '0' : ch - 'A' + 10);
	      
	      ch = in.charAt(i * 2 + 1);
	      byte l = (byte)((ch >= '0') && (ch <= '9') ? ch - '0' : ch - 'A' + 10);
	      
	      out[i] = ((byte)(h * 16 + l));
	    }
	    return out;
	  }
}
