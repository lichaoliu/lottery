package com.lottery.pay.progress.elinkdraw.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class SecureLink {


	  private String timeStamp;
	  private PrivateKey privateKey;
	  private boolean containTimestamp = false;
	  private String EncMsg;
	  private String DecTxt;
	  private byte[] DataBuf;
	  private String CheckValue;
	  private String EncPin;
	  
	  public SecureLink(PrivateKey key)
	  {
	    this.privateKey = key;
	    this.timeStamp = "";
	  }
	  
	  public boolean getContainsTimestamp()
	  {
	    return this.containTimestamp;
	  }
	  
	  public void setContainTimestamp(boolean value)
	  {
	    this.containTimestamp = value;
	  }
	  
	  private String currentTimeStamp()
	  {
	    Calendar now = Calendar.getInstance();
	    int hour = now.get(11);
	    int minute = now.get(12);
	    int second = now.get(13);
	    int month = now.get(2) + 1;
	    int day = now.get(5);
	    int year = now.get(1);
	    
	    StringBuffer buffer = new StringBuffer();
	    
	    buffer.append(year).append(leftFillZero(month, 2)).append(
	      leftFillZero(day, 2)).append(leftFillZero(hour, 2)).append(
	      leftFillZero(minute, 2)).append(leftFillZero(second, 2));
	    return buffer.toString();
	  }
	  
	  private String DecryptMessage(String input)
	  {
		BigInteger result;
	    try
	    {
	      BigInteger In = new BigInteger(input, 16);
	      BigInteger Modulus = new BigInteger(1, this.privateKey.Modulus);
	      BigInteger Exponent = new BigInteger("010001", 16);
	      result = In.modPow(Exponent, Modulus);
	    }
	    catch (NumberFormatException _ex)
	    {
	      return "-1";
	    }
	    String rb="";
	    for (rb = result.toString(16); rb.length() < 256; rb = "0" + rb) {}
	    return rb;
	  }
	  
	  private static void fillBlock(byte[] block, byte b)
	  {
	    fillBlock(block, 0, b, block.length);
	  }
	  
	  private static void fillBlock(byte[] block, int blockOff, byte b, int len)
	  {
	    for (int i = blockOff; i < blockOff + len; i++) {
	      block[i] = b;
	    }
	  }
	  
	  private String leftPad(String str, int length, char c)
	  {
	    StringBuffer buffer = new StringBuffer("");
	    if (str == null) {
	      str = "";
	    }
	    if (length <= str.length()) {
	      return str;
	    }
	    int strLen = length - str.length();
	    for (int i = 0; i < strLen; i++) {
	      buffer.append(c);
	    }
	    buffer.append(str);
	    return buffer.toString();
	  }
	  
	  private String leftFillZero(int number, int length)
	  {
	    StringBuffer buffer = new StringBuffer("");
	    String str;
	    if (number >= 0)
	    {
	      str = String.valueOf(number);
	    }
	    else
	    {
	      buffer.append("-");
	      str = String.valueOf(Math.abs(number));
	      length--;
	    }
	    buffer.append(leftPad(str, length, '0'));
	    return buffer.toString();
	  }
	  
	  public String rightPad(String str, int len, char ch)
	  {
	    int p = len - str.length();
	    String nstr = "";
	    for (int i = 0; i < p; i++) {
	      nstr = nstr + ch;
	    }
	    nstr = str + nstr;
	    
	    return nstr;
	  }
	  
	  public String genRandom(int len)
	  {
	    String result = "";
	    Random ran = new Random();
	    
	    String sValue = String.valueOf(Math.abs(ran.nextLong()));
	    if (sValue.length() >= len) {
	      result = sValue.substring(0, len);
	    } else {
	      result = rightPad(sValue, len, '0');
	    }
	    return result;
	  }
	  
	  public String getTimeStamp()
	  {
	    return this.timeStamp;
	  }
	  
	  public String Sign(String MsgBody)
	  {
	    String plainData;
	    if (this.containTimestamp)
	    {
	      this.timeStamp = currentTimeStamp();
	      plainData = this.timeStamp + MsgBody;
	    }
	    else
	    {
	      this.timeStamp = "";
	      plainData = MsgBody;
	    }
	    return this.timeStamp + Sign(plainData, plainData.length());
	  }
	  
	  public String Sign(String MsgBody, int MsgLen)
	  {
	    byte[] data = new byte[7000];
	    ShaHash hash = new ShaHash();
	    RSAEncrypt S = new RSAEncrypt(this.privateKey);
	    
	    fillBlock(data, (byte)0);
	    byte[] tmp = MsgBody.getBytes();
	    System.arraycopy(tmp, 0, data, 0, MsgLen);
	    
	    hash.add(data, 0, MsgLen);
	    byte[] hb = hash.get();
	    String rb = S.EncryptMessage(hb);
	    return rb.toUpperCase();
	  }
	  
	  public String genSign(String MerID, String OrderNO, String Amount, String CurrencyCode, String TransDate, String TransType)
	  {
	  
	    
	    int len = MerID.length();
	    if (len > 15) {
	      MerID = MerID.substring(0, 15);
	    }
	    len = OrderNO.length();
	    if (len > 16) {
	      OrderNO = OrderNO.substring(0, 16);
	    }
	    len = Amount.length();
	    if (len > 12) {
	      Amount = Amount.substring(0, 12);
	    }
	    len = CurrencyCode.length();
	    if (len > 3) {
	      CurrencyCode = CurrencyCode.substring(0, 3);
	    }
	    len = TransDate.length();
	    if (len > 8) {
	      TransDate = TransDate.substring(0, 8);
	    }
	    len = TransType.length();
	    if (len > 4) {
	      TransType = TransType.substring(0, 4);
	    }
	    String MsgBody = "";
	    MsgBody = MsgBody + MerID + OrderNO + Amount + CurrencyCode + TransDate + 
	      TransType;
	    


	    int MsgLen = MsgBody.length();
	    
	    return Sign(MsgBody, MsgLen);
	  }
	  
	  public String signOrder(String MerID, String OrderNO, String Amount, String CurrencyCode, String TransDate, String TransType)
	  {
	    return genSign(MerID, OrderNO, Amount, CurrencyCode, TransDate, 
	      TransType);
	  }
	  

	  
	  public static boolean memcmp(byte[] out, byte[] in, int len)
	  {
	    for (int i = 0; i < len; i++) {
	      if (out[i] != in[i]) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	 
	  
	  private static String toStringBlock(byte[] block)
	  {
	    return toStringBlock(block, 0, block.length);
	  }
	  
	  private static String toStringBlock(byte[] block, int off, int len)
	  {
	    String hexits = "0123456789abcdef";
	    StringBuffer buf = new StringBuffer();
	    for (int i = off; i < off + len; i++)
	    {
	      buf.append(hexits.charAt(block[i] >>> 4 & 0xF));
	      buf.append(hexits.charAt(block[i] & 0xF));
	    }
	    return String.valueOf(String.valueOf(buf));
	  }
	  
	  public boolean verifyAuthToken(String MerID, String OrderNO, String Amount, String CurrencyCode, String TransDate, String TransType, String TransCode, String CheckValue)
	  {
	    byte[] data = new byte[100];
	    ShaHash hash = new ShaHash();
	    int len = 0;
	    
	    fillBlock(data, (byte)0);
	    byte[] tmp = MerID.getBytes();
	    

	    len = MerID.length();
	    if (len > 15) {
	      len = 15;
	    }
	    System.arraycopy(tmp, 0, data, 0, len);
	    tmp = OrderNO.getBytes();
	    len = OrderNO.length();
	    if (len > 16) {
	      len = 16;
	    }
	    System.arraycopy(tmp, 0, data, 15, len);
	    tmp = Amount.getBytes();
	    len = Amount.length();
	    if (len > 12) {
	      len = 12;
	    }
	    System.arraycopy(tmp, 0, data, 31, len);
	    tmp = CurrencyCode.getBytes();
	    len = CurrencyCode.length();
	    if (len > 3) {
	      len = 3;
	    }
	    System.arraycopy(tmp, 0, data, 43, len);
	    tmp = TransDate.getBytes();
	    len = TransDate.length();
	    if (len > 8) {
	      len = 8;
	    }
	    System.arraycopy(tmp, 0, data, 46, len);
	    tmp = TransType.getBytes();
	    len = TransType.length();
	    if (len > 4) {
	      len = 4;
	    }
	    System.arraycopy(tmp, 0, data, 54, len);
	    if (TransCode != null)
	    {
	      tmp = TransCode.getBytes();
	      len = TransCode.length();
	      if (len > 4) {
	        len = 4;
	      }
	      System.arraycopy(tmp, 0, data, 58, len);
	    }
	    hash.add(data, 0, 62);
	    byte[] hb = hash.get();
	    String rb = DecryptMessage(CheckValue);
	    String sb = toStringBlock(hb);
	    return sb.equalsIgnoreCase(rb);
	  }
	  
	  public boolean verifyAuthToken(String PlainData, String CheckValue)
	  {
	    byte[] data = PlainData.getBytes();
	    ShaHash hash = new ShaHash();
	    hash.add(data, 0, data.length);
	    byte[] hb = hash.get();
	    String rb = DecryptMessage(CheckValue);
	    String sb = toStringBlock(hb);
	    return sb.equalsIgnoreCase(rb);
	  }
	  
	  public boolean verifyTransResponse(String MerID, String OrderNO, String Amount, String CurrencyCode, String TransDate, String TransType, String TransCode, String CheckValue)
	  {
	    return verifyAuthToken(MerID, OrderNO, Amount, CurrencyCode, 
	      TransDate, TransType, TransCode, CheckValue);
	  }
	  
	
	  public static byte[] md5Digest(byte[] input)
	  {
	    try
	    {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(input);
	      return md.digest();
	    }
	    catch (Exception e) {}
	    return "error".getBytes();
	  }
	  
	  public static byte[] md5digestAdd128(byte[] input)
	  {
	    byte[] hb = new byte[''];
	    byte[] HashpadString = {
	      0, 1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
	      -1, -1, 0, 48, 33, 48, 9, 6, 5, 43, 
	      14, 3, 2, 26, 5, 0, 4, 20 };
	    System.arraycopy(HashpadString, 0, hb, 0, 112);
	    System.arraycopy(input, 0, hb, 112, 16);
	    return hb;
	  }
	  
	  byte[] OAEP_Two(byte[] DES_Key1, byte[] DES_Key2, byte[] IV)
	  {
	    byte[] tmpStr = "ffFIEFlw81f03frL8f2lfsg".getBytes();
	    
	    Random rd = new Random();
	    byte[] R = new byte[''];
	    rd.nextBytes(R);
	    R[0] = 1;
	    R[1] = 0;
	    System.arraycopy(tmpStr, 0, R, 10, tmpStr.length);
	    

	    R[103] = 0;
	    System.arraycopy(DES_Key1, 0, R, 104, 8);
	    System.arraycopy(DES_Key2, 0, R, 112, 8);
	    System.arraycopy(IV, 0, R, 120, 8);
	    
	    return R;
	  }
	  
	  String addLenString(int len)
	  {
	    if (len > 9999) {
	      return "-1";
	    }
	    String sOut = Integer.toString(len);
	    
	    int b = 4 - sOut.length();
	    if (b != 0) {
	      for (int i = 0; i < b; i++) {
	        sOut = "0" + sOut;
	      }
	    }
	    return sOut;
	  }
	  
	  int getLenString(String sIn)
	  {
	    if (sIn.length() != 4) {
	      return -1;
	    }
	    int iOut = 0;
	    try
	    {
	      Integer I = new Integer(sIn);
	      iOut = I.intValue();
	    }
	    catch (Exception e)
	    {
	      return -2;
	    }
	    return iOut;
	  }
	  
	  public int newEncData_J_Client(byte[] DataBuf)
	  {
	    byte[] deskey1 = new byte[8];
	    byte[] deskey2 = new byte[8];
	    byte[] IV = "FIEf124H".getBytes();
	    
	    Random rd = new Random();
	    rd.nextBytes(deskey1);
	    rd.nextBytes(deskey2);
	    
	    byte[] msgKey = OAEP_Two(deskey1, deskey2, IV);
	    
	    int DataBufLen = DataBuf.length;
	    if (DataBufLen > 9999) {
	      return -9999;
	    }
	    String sLen = addLenString(DataBufLen);
	    if (sLen.compareTo("-1") == 0) {
	      return -9998;
	    }
	    if (sLen.length() != 4) {
	      return -9997;
	    }
	    DesRsa dr = new DesRsa();
	    String tmp = DesRsa.Hex2Asc(msgKey.length, msgKey);
	    byte[] trkey = new byte[16];
	    System.arraycopy(deskey1, 0, trkey, 0, 8);
	    System.arraycopy(deskey2, 0, trkey, 8, 8);
	    
	    tmp = new String(DataBuf);
	    byte[] btrOut = dr.DES3b(trkey, DataBuf);
	    String strOut = DesRsa.Hex2Asc(btrOut.length, btrOut);
	    String shMsgKey = DesRsa.Hex2Asc(msgKey.length, msgKey);
	    String sMsgKey = DecryptMessage(shMsgKey);
	    sMsgKey = sMsgKey.toUpperCase();
	    this.EncMsg = (sMsgKey + sLen + strOut);
	    return 0;
	  }
	  
	  public int newEncData_J_Client(byte[] DataBuf, int encoding)
	  {
	    String ecd = null;
	    switch (encoding)
	    {
	    case 0: 
	      ecd = "US-ASCII";
	      break;
	    case 1: 
	      ecd = "UTF-16LE";
	      break;
	    case 2: 
	      ecd = "UTF-16BE";
	      break;
	    case 3: 
	      ecd = "UTF-8";
	      break;
	    default: 
	      return -1102;
	    }
	    String content;
	    try
	    {
	      content = new String(DataBuf, ecd);
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      return -1101;
	    }
	  
	    DataBuf = content.getBytes();
	    
	    byte[] deskey1 = new byte[8];
	    byte[] deskey2 = new byte[8];
	    byte[] IV = "FIEf124H".getBytes();
	    
	    Random rd = new Random();
	    rd.nextBytes(deskey1);
	    rd.nextBytes(deskey2);
	    
	    byte[] msgKey = OAEP_Two(deskey1, deskey2, IV);
	    
	    int DataBufLen = DataBuf.length;
	    if (DataBufLen > 9999) {
	      return -9999;
	    }
	    String sLen = addLenString(DataBufLen);
	    if (sLen.compareTo("-1") == 0) {
	      return -9998;
	    }
	    if (sLen.length() != 4) {
	      return -9997;
	    }
	    DesRsa dr = new DesRsa();
	    String tmp = DesRsa.Hex2Asc(msgKey.length, msgKey);
	    byte[] trkey = new byte[16];
	    System.arraycopy(deskey1, 0, trkey, 0, 8);
	    System.arraycopy(deskey2, 0, trkey, 8, 8);
	    
	    tmp = new String(DataBuf);
	    byte[] btrOut = dr.DES3b(trkey, DataBuf);
	    String strOut = DesRsa.Hex2Asc(btrOut.length, btrOut);
	    String shMsgKey = DesRsa.Hex2Asc(msgKey.length, msgKey);
	    String sMsgKey = DecryptMessage(shMsgKey);
	    sMsgKey = sMsgKey.toUpperCase();
	    this.EncMsg = (sMsgKey + sLen + strOut);
	    return 0;
	  }
	  
	  public String getEncMsg()
	  {
	    return this.EncMsg;
	  }
	  
	  int checkOAEP(byte[] R, byte[] trKey)
	  {
	    if (R[0] != 1) {
	      return -2000;
	    }
	    if (R[1] != 0) {
	      return 2001;
	    }
	    byte[] tmpStr = "ffFIEFlw81f03frL8f2lfsg".getBytes();
	    int tlen = tmpStr.length;
	    byte[] gettmpStr = new byte[tlen];
	    System.arraycopy(R, 10, gettmpStr, 0, tlen);
	    if (!Arrays.equals(tmpStr, gettmpStr)) {
	      return -1009;
	    }
	    byte[] IV = "FIEf124H".getBytes();
	    tlen = IV.length;
	    byte[] getIV = new byte[tlen];
	    System.arraycopy(R, 120, getIV, 0, tlen);
	    if (!Arrays.equals(IV, getIV)) {
	      return -1008;
	    }
	    if (trKey.length != 16) {
	      return -2;
	    }
	    System.arraycopy(R, 104, trKey, 0, 16);
	    
	    return 0;
	  }
	  
	  int getMsgKey(String CipherTxt, byte[] ClearTxt)
	  {
	    if (CipherTxt.length() != 256) {
	      return -2;
	    }
	    if (ClearTxt.length != 128) {
	      return -3;
	    }
	    RSAEncrypt S = new RSAEncrypt(this.privateKey);
	    byte[] cpt = DesRsa.Asc2Hex(CipherTxt.length(), CipherTxt);
	    String rb = S.EncryptMessage(cpt);
	    byte[] br = DesRsa.Asc2Hex(rb.length(), rb);
	    
	    String s = DesRsa.Hex2Asc(br.length, br);
	    if (rb.compareTo("-1") == 0) {
	      return -1;
	    }
	    System.arraycopy(br, 0, ClearTxt, 0, ClearTxt.length);
	    return 0;
	  }
	  
	  public int newDecData_J_Client(String EncMsg)
	  {
	    String msgKeyCipher = EncMsg.substring(0, 256);
	    String sLen = EncMsg.substring(256, 260);
	    String desResult = EncMsg.substring(260);
	    
	    int DataLen = getLenString(sLen);
	    if (DataLen <= 0) {
	      return -8999;
	    }
	    byte[] deskey = new byte[''];
	    int rs = getMsgKey(msgKeyCipher, deskey);
	    if (rs != 0) {
	      return rs;
	    }
	    String s = DesRsa.Hex2Asc(deskey.length, deskey);
	    byte[] trkey = new byte[16];
	    rs = checkOAEP(deskey, trkey);
	    if (rs != 0) {
	      return rs;
	    }
	    DesRsa dr = new DesRsa();
	    byte[] bDec = dr._DES3bs(trkey, desResult.getBytes());
	    int blen = bDec.length;
	    this.DataBuf = new byte[DataLen];
	    System.arraycopy(bDec, 0, this.DataBuf, 0, DataLen);
	    
	    return 0;
	  }
	  
	  public byte[] getDecMsg()
	  {
	    return this.DataBuf;
	  }
	  
	  public String getDataBuf()
	  {
	    try
	    {
	      this.DecTxt = new String(this.DataBuf);
	    }
	    catch (Exception e)
	    {
	      return "-1";
	    }
	    return this.DecTxt;
	  }
	  
	  public int newSignData_J_Client(int CompressType, byte[] DataBuf)
	  {
	    int MsgLen = DataBuf.length;
	    byte[] data = new byte[MsgLen];
	    byte[] hb = new byte[''];
	    byte[] md5out = new byte[16];
	    if (MsgLen > 10240) {
	      return -2;
	    }
	    RSAEncrypt S = new RSAEncrypt(this.privateKey);
	    fillBlock(data, (byte)0);
	    
	    System.arraycopy(DataBuf, 0, data, 0, MsgLen);
	    if (CompressType == 0)
	    {
	      ShaHash hash = new ShaHash();
	      hash.add(data, 0, MsgLen);
	      hb = hash.get();
	    }
	    else if (CompressType == 1)
	    {
	      md5out = md5Digest(DataBuf);
	      hb = md5digestAdd128(md5out);
	    }
	    else
	    {
	      return -3;
	    }
	    String rb = S.EncryptMessage(hb);
	    this.CheckValue = rb.toUpperCase();
	    return 0;
	  }
	  
	  public int newSignData_J_Client(int CompressType, byte[] DataBuf, int encoding)
	  {
	    String ecd = null;
	    switch (encoding)
	    {
	    case 0: 
	      ecd = "US-ASCII";
	      break;
	    case 1: 
	      ecd = "UTF-16LE";
	      break;
	    case 2: 
	      ecd = "UTF-16BE";
	      break;
	    case 3: 
	      ecd = "UTF-8";
	      break;
	    default: 
	      return -1102;
	    }
	    String content;
	    try
	    {
	      content = new String(DataBuf, ecd);
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      return -1101;
	    }
	    DataBuf = content.getBytes();
	    int MsgLen = DataBuf.length;
	    byte[] data = new byte[MsgLen];
	    byte[] hb = new byte[''];
	    byte[] md5out = new byte[16];
	    if (MsgLen > 10240) {
	      return -2;
	    }
	    RSAEncrypt S = new RSAEncrypt(this.privateKey);
	    fillBlock(data, (byte)0);
	    
	    System.arraycopy(DataBuf, 0, data, 0, MsgLen);
	    if (CompressType == 0)
	    {
	      ShaHash hash = new ShaHash();
	      hash.add(data, 0, MsgLen);
	      hb = hash.get();
	    }
	    else if (CompressType == 1)
	    {
	      md5out = md5Digest(DataBuf);
	      hb = md5digestAdd128(md5out);
	    }
	    else
	    {
	      return -3;
	    }
	    String rb = S.EncryptMessage(hb);
	    this.CheckValue = rb.toUpperCase();
	    
	    return 0;
	  }
	  
	  public String getCheckValue()
	  {
	    return this.CheckValue;
	  }
	  
	  public int newVeriSignData_J_Client(int CompressType, byte[] DataBuf, String ChkValue)
	  {
	    byte[] data = new byte[10240];
	    int MsgLen = DataBuf.length;
	    if (MsgLen > 10240) {
	      return -2;
	    }
	    fillBlock(data, (byte)0);
	    byte[] hb;
	    System.arraycopy(DataBuf, 0, data, 0, MsgLen);
	    if (CompressType == 0)
	    {
	      ShaHash hash = new ShaHash();
	      hash.add(data, 0, MsgLen);
	      hb = hash.get();
	    }
	    else if (CompressType == 1)
	    {
	      hb = md5Digest(data);
	      hb = md5digestAdd128(hb);
	    }
	    else
	    {
	      return -3;
	    }
	  
	    String rb = DecryptMessage(ChkValue);
	    String sb = toStringBlock(hb);
	    if (sb.equalsIgnoreCase(rb)) {
	      return 0;
	    }
	    return 1;
	  }
	  
	  public int newEncPin_J_Client(String Pin, String MerId, String TransDate, String SeqId, String CardId)
	  {
	    if (CardId.length() < 7) {
	      return -1;
	    }
	    DesRsa drPin = new DesRsa();
	    this.EncPin = DesRsa.SingleDesEnc(Pin, CardId.substring(CardId.length() - 8).getBytes());
	    
	    return 0;
	  }
	  
	  public String getEncPin()
	  {
	    return this.EncPin;
	  }
}
