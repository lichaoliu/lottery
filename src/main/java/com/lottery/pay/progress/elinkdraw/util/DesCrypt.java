package com.lottery.pay.progress.elinkdraw.util;

abstract interface DesCrypt
{
  public abstract void cbc_encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, boolean paramBoolean);
  
  public abstract void cfb64_encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, int[] paramArrayOfInt, boolean paramBoolean);
  
  public abstract void ecb_encrypt(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, boolean paramBoolean);
  
  public abstract void ofb64_encrypt(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, int[] paramArrayOfInt);
}

