package com.lottery.pay.progress.elinkdraw.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecoderStream  extends FilterInputStream
{
	  public BASE64DecoderStream(InputStream paramInputStream)
	  {
	    super(paramInputStream);
	  }
	  
	  private byte[] buffer = new byte[3];
	  private int bufsize;
	  private int index;
	  
	  public int read()
	    throws IOException
	  {
	    if (this.index >= this.bufsize)
	    {
	      decode();
	      if (this.bufsize == 0) {
	        return -1;
	      }
	      this.index = 0;
	    }
	    return this.buffer[(this.index++)] & 0xFF;
	  }
	  
	  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	    throws IOException
	  {
		int i=0;
	    for (i = 0; i < paramInt2; i++)
	    {
	      int j;
	      if ((j = read()) == -1)
	      {
	        if (i != 0) {
	          break;
	        }
	        i = -1;
	        break;
	      }
	      paramArrayOfByte[(paramInt1 + i)] = ((byte)j);
	    }
	    return i;
	  }
	  
	  public boolean markSupported()
	  {
	    return false;
	  }
	  
	  public int available()
	    throws IOException
	  {
	    return this.in.available() * 3 / 4 + (this.bufsize - this.index);
	  }
	  
	  private static final char[] pem_array = {
	    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
	    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
	    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
	    'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
	    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
	    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
	    'w', 'x', 'y', 'z', '0', '1', '2', '3', 
	    '4', '5', '6', '7', '8', '9', '+', '/' };
	  private static final byte[] pem_convert_array = new byte[256];
	  
	  static
	  {
	    for (int i = 0; i < 255; i++) {
	      pem_convert_array[i] = -1;
	    }
	    for (int j = 0; j < pem_array.length; j++) {
	      pem_convert_array[pem_array[j]] = ((byte)j);
	    }
	  }
	  
	  private byte[] decode_buffer = new byte[4];
	  
	  private void decode()
	    throws IOException
	  {
	    this.bufsize = 0;
	    




	    int i = 0;
	    int j=0;
	    while (i < 4)
	    {
	      j = this.in.read();
	      if (j == -1)
	      {
	        if (i == 0) {
	          return;
	        }
	        throw new IOException("Error in encoded stream, got " + i);
	      }
	      if (((j >= 0) && (j < 256) && (j == 61)) || (pem_convert_array[j] != -1)) {
	        this.decode_buffer[(i++)] = ((byte)j);
	      }
	    }
	    j = pem_convert_array[(this.decode_buffer[0] & 0xFF)];
	    int k = pem_convert_array[(this.decode_buffer[1] & 0xFF)];
	    
	    this.buffer[(this.bufsize++)] = ((byte)(j << 2 & 0xFC | k >>> 4 & 0x3));
	    if (this.decode_buffer[2] == 61) {
	      return;
	    }
	    j = k;
	    k = pem_convert_array[(this.decode_buffer[2] & 0xFF)];
	    
	    this.buffer[(this.bufsize++)] = ((byte)(j << 4 & 0xF0 | k >>> 2 & 0xF));
	    if (this.decode_buffer[3] == 61) {
	      return;
	    }
	    j = k;
	    k = pem_convert_array[(this.decode_buffer[3] & 0xFF)];
	    
	    this.buffer[(this.bufsize++)] = ((byte)(j << 6 & 0xC0 | k & 0x3F));
	  }
	  
	  public static byte[] decode(byte[] paramArrayOfByte)
	  {
	    int i = paramArrayOfByte.length / 4 * 3;
	    if (i == 0) {
	      return paramArrayOfByte;
	    }
	    if (paramArrayOfByte[(paramArrayOfByte.length - 1)] == 61)
	    {
	      i--;
	      if (paramArrayOfByte[(paramArrayOfByte.length - 2)] == 61) {
	        i--;
	      }
	    }
	    byte[] arrayOfByte = new byte[i];
	    
	    int j = 0;int k = 0;
	    i = paramArrayOfByte.length;
	    while (i > 0)
	    {
	      int m = pem_convert_array[(paramArrayOfByte[(j++)] & 0xFF)];
	      int n = pem_convert_array[(paramArrayOfByte[(j++)] & 0xFF)];
	      
	      arrayOfByte[(k++)] = ((byte)(m << 2 & 0xFC | n >>> 4 & 0x3));
	      if (paramArrayOfByte[j] == 61) {
	        return arrayOfByte;
	      }
	      m = n;
	      n = pem_convert_array[(paramArrayOfByte[(j++)] & 0xFF)];
	      
	      arrayOfByte[(k++)] = ((byte)(m << 4 & 0xF0 | n >>> 2 & 0xF));
	      if (paramArrayOfByte[j] == 61) {
	        return arrayOfByte;
	      }
	      m = n;
	      n = pem_convert_array[(paramArrayOfByte[(j++)] & 0xFF)];
	      
	      arrayOfByte[(k++)] = ((byte)(m << 6 & 0xC0 | n & 0x3F));
	      i -= 4;
	    }
	    return arrayOfByte;
	  }
}
