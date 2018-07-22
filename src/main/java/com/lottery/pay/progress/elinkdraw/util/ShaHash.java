package com.lottery.pay.progress.elinkdraw.util;

public class ShaHash {
	private int hashSize;
	  private byte[] hashBytes;
	  private static final boolean USE_MODIFIED_SHA = true;
	  private static final int SHA_BLOCKSIZE = 64;
	  private static final int SHA_DIGESTSIZE = 20;
	  private int[] digest;
	  private long bitCount;
	  private byte[] dataB;
	  private int[] dataI;
	  private static final int K1 = 1518500249;
	  private static final int K2 = 1859775393;
	  private static final int K3 = -1894007588;
	  private static final int K4 = -899497514;
	  private static final int h0init = 1732584193;
	  private static final int h1init = -271733879;
	  private static final int h2init = -1732584194;
	  private static final int h3init = 271733878;
	  private static final int h4init = -1009589776;
	  private int h0;
	  private int h1;
	  private int h2;
	  private int h3;
	  private int h4;
	  private int A;
	  private int B;
	  private int C;
	  private int D;
	  private int E;
	  private int[] W;
	  
	  public ShaHash()
	  {
	    this.digest = new int[5];
	    this.dataB = new byte[64];
	    this.dataI = new int[16];
	    this.W = new int[80];
	    this.hashSize = 20;
	    this.hashBytes = new byte[20];
	  }
	  
	  public void add(byte[] data, int off, int len)
	  {
	    reset();
	    shaUpdate(data, off, len);
	  }
	  
	  public static void copyBlock(byte[] src, int srcOff, byte[] dst, int dstOff, int len)
	  {
	    for (int i = 0; i < len; i++) {
	      dst[(dstOff + i)] = src[(srcOff + i)];
	    }
	  }
	  
	  private static int f1(int x, int y, int z)
	  {
	    return x & y | (x ^ 0xFFFFFFFF) & z;
	  }
	  
	  private static int f2(int x, int y, int z)
	  {
	    return x ^ y ^ z;
	  }
	  
	  private static int f3(int x, int y, int z)
	  {
	    return x & y | x & z | y & z;
	  }
	  
	  private static int f4(int x, int y, int z)
	  {
	    return x ^ y ^ z;
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
	  
	  public byte[] get()
	  {
	    prepare();
	    byte[] hb = new byte[this.hashSize + 108];
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
	    


	    System.arraycopy(HashpadString, 0, hb, 0, 108);
	    System.arraycopy(this.hashBytes, 0, hb, 108, this.hashSize);
	    return hb;
	  }
	  
	  public void prepare()
	  {
	    shaFinal();
	    spreadIntsToBytes(this.digest, 0, this.hashBytes, 0, 5);
	  }
	  
	  public void reset()
	  {
	    shaInit();
	  }
	  
	  private static int rotateL(int x, int n)
	  {
	    return x << n | x >>> 32 - n;
	  }
	  
	  public void shaFinal()
	  {
	    int count = (int)(this.bitCount >>> 3) & 0x3F;
	    this.dataB[(count++)] = -128;
	    if (count > 56)
	    {
	      fillBlock(this.dataB, count, (byte)0, 64 - count);
	      squashBytesToInts(this.dataB, 0, this.dataI, 0, 16);
	      shaTransform();
	      fillBlock(this.dataB, 0, (byte)0, 56);
	    }
	    else
	    {
	      fillBlock(this.dataB, count, (byte)0, 56 - count);
	    }
	    squashBytesToInts(this.dataB, 0, this.dataI, 0, 16);
	    this.dataI[14] = ((int)(this.bitCount >>> 32));
	    this.dataI[15] = ((int)(this.bitCount & 0xFFFFFFFF));
	    shaTransform();
	  }
	  
	  private void shaInit()
	  {
	    this.digest[0] = 1732584193;
	    this.digest[1] = -271733879;
	    this.digest[2] = -1732584194;
	    this.digest[3] = 271733878;
	    this.digest[4] = -1009589776;
	    this.bitCount = 0L;
	  }
	  
	  public void shaTransform()
	  {
		int i;
	    for (i= 0; i < 16; i++) {
	      this.W[i] = this.dataI[i];
	    }
	    for (; i < 80; i++)
	    {
	      this.W[i] = (this.W[(i - 3)] ^ this.W[(i - 8)] ^ this.W[(i - 14)] ^ this.W[(i - 16)]);
	      this.W[i] = rotateL(this.W[i], 1);
	    }
	    this.A = this.digest[0];
	    this.B = this.digest[1];
	    this.C = this.digest[2];
	    this.D = this.digest[3];
	    this.E = this.digest[4];
	    for (i = 0; i < 20; i++) {
	      subRound1(i);
	    }
	    for (; i < 40; i++) {
	      subRound2(i);
	    }
	    for (; i < 60; i++) {
	      subRound3(i);
	    }
	    for (; i < 80; i++) {
	      subRound4(i);
	    }
	    this.digest[0] += this.A;
	    this.digest[1] += this.B;
	    this.digest[2] += this.C;
	    this.digest[3] += this.D;
	    this.digest[4] += this.E;
	  }
	  
	  public void shaUpdate(byte[] buffer, int offset, int count)
	  {
	    this.bitCount += (count << 3);
	    for (; count >= 64; count -= 64)
	    {
	      copyBlock(buffer, offset, this.dataB, 0, 64);
	      squashBytesToInts(this.dataB, 0, this.dataI, 0, 16);
	      shaTransform();
	      offset += 64;
	    }
	    copyBlock(buffer, offset, this.dataB, 0, count);
	  }
	  
	  private static void spreadIntsToBytes(int[] inInts, int inOff, byte[] outBytes, int outOff, int intLen)
	  {
	    for (int i = 0; i < intLen; i++)
	    {
	      outBytes[(outOff + i * 4)] = ((byte)(inInts[(inOff + i)] >>> 24));
	      outBytes[(outOff + i * 4 + 1)] = ((byte)(inInts[(inOff + i)] >>> 16));
	      outBytes[(outOff + i * 4 + 2)] = ((byte)(inInts[(inOff + i)] >>> 8));
	      outBytes[(outOff + i * 4 + 3)] = ((byte)inInts[(inOff + i)]);
	    }
	  }
	  
	  private static void squashBytesToInts(byte[] inBytes, int inOff, int[] outInts, int outOff, int intLen)
	  {
	    for (int i = 0; i < intLen; i++) {
	      outInts[(outOff + i)] = ((inBytes[(inOff + i * 4)] & 0xFF) << 24 | (inBytes[(inOff + i * 4 + 1)] & 0xFF) << 16 | (inBytes[(inOff + i * 4 + 2)] & 0xFF) << 8 | inBytes[(inOff + i * 4 + 3)] & 0xFF);
	    }
	  }
	  
	  private void subRound1(int count)
	  {
	    int temp = rotateL(this.A, 5) + f1(this.B, this.C, this.D) + this.E + this.W[count] + 1518500249;
	    this.E = this.D;
	    this.D = this.C;
	    this.C = rotateL(this.B, 30);
	    this.B = this.A;
	    this.A = temp;
	  }
	  
	  private void subRound2(int count)
	  {
	    int temp = rotateL(this.A, 5) + f2(this.B, this.C, this.D) + this.E + this.W[count] + 1859775393;
	    this.E = this.D;
	    this.D = this.C;
	    this.C = rotateL(this.B, 30);
	    this.B = this.A;
	    this.A = temp;
	  }
	  
	  private void subRound3(int count)
	  {
	    int temp = rotateL(this.A, 5) + f3(this.B, this.C, this.D) + this.E + this.W[count] + -1894007588;
	    this.E = this.D;
	    this.D = this.C;
	    this.C = rotateL(this.B, 30);
	    this.B = this.A;
	    this.A = temp;
	  }
	  
	  private void subRound4(int count)
	  {
	    int temp = rotateL(this.A, 5) + f4(this.B, this.C, this.D) + this.E + this.W[count] + -899497514;
	    this.E = this.D;
	    this.D = this.C;
	    this.C = rotateL(this.B, 30);
	    this.B = this.A;
	    this.A = temp;
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
}
