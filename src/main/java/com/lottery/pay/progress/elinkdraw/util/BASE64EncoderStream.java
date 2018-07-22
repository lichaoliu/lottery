package com.lottery.pay.progress.elinkdraw.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BASE64EncoderStream extends FilterOutputStream
{
	  private byte[] buffer;
	  private int bufsize;
	  private int count;
	  private int bytesPerLine;
	  
	  public BASE64EncoderStream(OutputStream paramOutputStream, int paramInt)
	  {
	    super(paramOutputStream);
	    this.buffer = new byte[3];
	    this.bytesPerLine = paramInt;
	  }
	  
	  public BASE64EncoderStream(OutputStream paramOutputStream)
	  {
	    this(paramOutputStream, 76);
	  }
	  
	  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	    throws IOException
	  {
	    for (int i = 0; i < paramInt2; i++) {
	      write(paramArrayOfByte[(paramInt1 + i)]);
	    }
	  }
	  
	  public void write(byte[] paramArrayOfByte)
	    throws IOException
	  {
	    write(paramArrayOfByte, 0, paramArrayOfByte.length);
	  }
	  
	  public void write(int paramInt)
	    throws IOException
	  {
	    this.buffer[(this.bufsize++)] = ((byte)paramInt);
	    if (this.bufsize == 3)
	    {
	      encode();
	      this.bufsize = 0;
	    }
	  }
	  
	  public void flush()
	    throws IOException
	  {
	    if (this.bufsize > 0)
	    {
	      encode();
	      this.bufsize = 0;
	    }
	    this.out.flush();
	  }
	  
	  public void close()
	    throws IOException
	  {
	    flush();
	    this.out.close();
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
	  
	  private void encode()
	    throws IOException
	  {
	    if (this.count + 4 > this.bytesPerLine)
	    {
	      this.out.write(13);
	      this.out.write(10);
	      this.count = 0;
	    }
	    int i;
	    int j;
	    int k;
	    if (this.bufsize == 1)
	    {
	      i = this.buffer[0];
	      j = 0;
	      k = 0;
	      this.out.write(pem_array[(i >>> 2 & 0x3F)]);
	      this.out.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
	      this.out.write(61);
	      this.out.write(61);
	    }
	    else if (this.bufsize == 2)
	    {
	      i = this.buffer[0];
	      j = this.buffer[1];
	      k = 0;
	      this.out.write(pem_array[(i >>> 2 & 0x3F)]);
	      this.out.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
	      this.out.write(pem_array[((j << 2 & 0x3C) + (k >>> 6 & 0x3))]);
	      this.out.write(61);
	    }
	    else
	    {
	      i = this.buffer[0];
	      j = this.buffer[1];
	      k = this.buffer[2];
	      this.out.write(pem_array[(i >>> 2 & 0x3F)]);
	      this.out.write(pem_array[((i << 4 & 0x30) + (j >>> 4 & 0xF))]);
	      this.out.write(pem_array[((j << 2 & 0x3C) + (k >>> 6 & 0x3))]);
	      this.out.write(pem_array[(k & 0x3F)]);
	    }
	    this.count += 4;
	  }
	  
	  public static byte[] encode(byte[] paramArrayOfByte)
	  {
	    if (paramArrayOfByte.length == 0) {
	      return paramArrayOfByte;
	    }
	    byte[] arrayOfByte = new byte[(paramArrayOfByte.length + 2) / 3 * 4];
	    int i = 0;int j = 0;
	    int k = paramArrayOfByte.length;
	    while (k > 0)
	    {
	      int m;
	      int n;
	      int i1;
	      if (k == 1)
	      {
	        m = paramArrayOfByte[(i++)];
	        n = 0;
	        i1 = 0;
	        arrayOfByte[(j++)] = ((byte)pem_array[(m >>> 2 & 0x3F)]);
	        arrayOfByte[(j++)] = 
	          ((byte)pem_array[((m << 4 & 0x30) + (n >>> 4 & 0xF))]);
	        arrayOfByte[(j++)] = 61;
	        arrayOfByte[(j++)] = 61;
	      }
	      else if (k == 2)
	      {
	        m = paramArrayOfByte[(i++)];
	        n = paramArrayOfByte[(i++)];
	        i1 = 0;
	        arrayOfByte[(j++)] = ((byte)pem_array[(m >>> 2 & 0x3F)]);
	        arrayOfByte[(j++)] = 
	          ((byte)pem_array[((m << 4 & 0x30) + (n >>> 4 & 0xF))]);
	        arrayOfByte[(j++)] = 
	          ((byte)pem_array[((n << 2 & 0x3C) + (i1 >>> 6 & 0x3))]);
	        arrayOfByte[(j++)] = 61;
	      }
	      else
	      {
	        m = paramArrayOfByte[(i++)];
	        n = paramArrayOfByte[(i++)];
	        i1 = paramArrayOfByte[(i++)];
	        arrayOfByte[(j++)] = ((byte)pem_array[(m >>> 2 & 0x3F)]);
	        arrayOfByte[(j++)] = 
	          ((byte)pem_array[((m << 4 & 0x30) + (n >>> 4 & 0xF))]);
	        arrayOfByte[(j++)] = 
	          ((byte)pem_array[((n << 2 & 0x3C) + (i1 >>> 6 & 0x3))]);
	        arrayOfByte[(j++)] = ((byte)pem_array[(i1 & 0x3F)]);
	      }
	      k -= 3;
	    }
	    return arrayOfByte;
	  }
}
