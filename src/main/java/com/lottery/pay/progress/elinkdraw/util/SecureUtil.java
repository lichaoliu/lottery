package com.lottery.pay.progress.elinkdraw.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class SecureUtil {
	public static final int SIGN_LENGTH = 256;
	  
	  public static String digitalSign(String merID, String msg, String priKeyPath)
	  {
	    if ((merID == null) || (msg == null) || (priKeyPath == null))
	    {
	      return null;
	    }
	    String digestMsg = getMsgDigestStr(msg);
	    String ChkValue = "";
	    if (!"".equals(priKeyPath))
	    {
	      PrivateKey key = new PrivateKey();	
	      if (!key.buildKey(merID, 0, priKeyPath))
	      {
	        return "";
	      }
	      SecureLink t = new SecureLink(key);
	      ChkValue = t.Sign(digestMsg, digestMsg.length());
	    }
	    return ChkValue;
	  }
	  
	  public static boolean validateSign(String merId, String plainData, String chkValue, String keyPath)
	  {
	    if ((merId == null) || (plainData == null) || (chkValue == null) || (keyPath == null))
	    {
	      return false;
	    }
	    if (chkValue.length() < 256)
	    {
	      return false;
	    }
	    String md = getMsgDigestStr(plainData);
	    PrivateKey key = new PrivateKey();
	    if (!key.buildKey(merId, 0, keyPath))
	    {
	      System.out.println("build key 出错");
	      return false;
	    }
	    SecureLink sl = new SecureLink(key);
	    if (!sl.verifyAuthToken(md, chkValue))
	    {
	      return false;
	    }
	    return true;
	  }
	  
	  public static String getMsgDigestStr(String fileMsg)
	  {
	    String base64Str = null;
	    if ((fileMsg != null) && (!"".equals(fileMsg)))
	    {
	      byte[] messDigest = (byte[])null;
	      try
	      {
	        MessageDigest md = MessageDigest.getInstance("SHA");
	        md.update(fileMsg.getBytes("GBK"));
	        messDigest = md.digest();
	      }
	      catch (Exception e)
	      {
	        e.printStackTrace();
	      }
	      base64Str = new String(ElinkBase64.encode(messDigest));
	    }
	    return base64Str;
	  }
	  
	  /**
	   * 压缩
	   * @param f
	   * @return
	   * @throws Exception
	   */
	  public static byte[] getBytes(File f) throws Exception {
			FileInputStream in = new FileInputStream(f);
			byte[] b = new byte[4 * 1024];
			int n;
			byte[] tmpResult = null;
			byte[] base64Result = null;
			while ((n = in.read(b)) != -1) {
				if (tmpResult == null) {
					tmpResult = getSumByte(null, 0, b, n);
				} else {
					tmpResult = getSumByte(tmpResult, tmpResult.length, b, n);
				}
			}
			base64Result = deflateEncode(tmpResult);
			in.close();
			return base64Result;
		}
		
		public static byte[] getSumByte(byte[] baseValue, int orLength, byte[] streamByte,
				int length) {
			byte[] result = new byte[orLength + length];
			for (int i = 0; i < orLength; i++) {
				result[i] = baseValue[i];
			}
			for (int i = 0; i < length; i++) {
				result[orLength + i] = streamByte[i];
			}
			return result;
		}
		
		
		/**
		 * 压缩编码
		 * 
		 * @param inputByte
		 * @return
		 * @throws IOException
		 */
		public static byte[] deflateEncode(byte[] inputByte) throws IOException {
			try {
				if (inputByte == null || inputByte.length == 0) {
					throw new IOException("压缩编码异常:输入不能为空指针!");
				}
				byte[] tmpByte = deflater(inputByte);
				return encode(tmpByte);
			} catch (IOException ioex) {
				throw ioex;
			}

		}

		/**
		 * 解码解压缩
		 * 
		 * @param inputByte
		 * @return
		 * @throws IOException
		 */
		public byte[] decodeInflate(byte[] inputByte) throws IOException {
			try {
				if (inputByte == null || inputByte.length == 0) {
					throw new IOException("解码解压缩异常:输入不能为空!");
				}
				byte[] tmpByte = decode(inputByte);
				return inflater(tmpByte);
			} catch (IOException ioex) {
				throw ioex;
			}
		}

		/**
		 * 压缩方法
		 */
		public static byte[] deflater(byte[] inputByte) throws IOException {
			int compressedDataLength = 0;
			Deflater compresser = new Deflater();
			compresser.setInput(inputByte);
			compresser.finish();
			ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
			byte[] result = new byte[1024];
			try {
				while (!compresser.finished()) {
					compressedDataLength = compresser.deflate(result);
					o.write(result, 0, compressedDataLength);
				}
			} finally {
				o.close();
			}
			compresser.end();

			return o.toByteArray();

		}

		static final int BUFFER = 128;

		/**
		 * 解压缩方法
		 * 
		 * @param inputByte
		 * @return
		 * @throws IOException
		 */
		public byte[] inflater(byte[] inputByte) throws IOException {
			int compressedDataLength = 0;
			Inflater compresser = new Inflater(false);
			compresser.setInput(inputByte, 0, inputByte.length);
			ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
			byte[] result = new byte[1024];
			try {
				while (!compresser.finished()) {
					compressedDataLength = compresser.inflate(result);
					if (compressedDataLength == 0) {
						break;
					}
					o.write(result, 0, compressedDataLength);
				}
			} catch (Exception ex) {
				System.err.println("Data format error!\n");
				ex.printStackTrace();
			} finally {
				o.close();
			}
			compresser.end();
			// System.out.println("decompressed data length:"+compressedDataLength);
			return o.toByteArray();
		}

		/**
		 * BASE64解码
		 */
		public static byte[] decode(byte[] inputByte) throws IOException {
			return BASE64DecoderStream.decode(inputByte);
		}

		/**
		 * BASE64编码
		 */
		public static byte[] encode(byte[] inputByte) throws IOException {
			return BASE64EncoderStream.encode(inputByte);
		}
}
