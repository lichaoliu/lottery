package com.lottery.pay.progress.elinkdraw.util;

public class Des implements DesCrypt
{
	  public static final boolean ENCRYPT = true;
	  public static final boolean DECRYPT = false;
	  private DesKey ks_;
	  
	  public Des(DesKey key)
	  {
	    this.ks_ = key;
	  }
	  
	  private void D_ENCRYPT(int[] ref_to_Q, int R, int S, int[] ref_to_u)
	  {
	    byte[] s = this.ks_.get_keysced();
	    S *= 4;
	    int s_at_S_offset = Int32Manipulator.bytes_to_int(s, S);
	    int s_at_S_plus_one_offset = Int32Manipulator.bytes_to_int(s, S + 4);
	    ref_to_u[0] = (R ^ s_at_S_offset);
	    int tmp = R ^ s_at_S_plus_one_offset;
	    tmp = (tmp >>> 4) + (tmp << 28);
	    ref_to_Q[0] ^= (des_SPtrans_[1][(tmp & 0x3F)] | des_SPtrans_[3][(tmp >>> 8 & 0x3F)] | des_SPtrans_[5][(tmp >>> 16 & 0x3F)] | des_SPtrans_[7][(tmp >>> 24 & 0x3F)] | des_SPtrans_[0][(ref_to_u[0] & 0x3F)] | des_SPtrans_[2][(ref_to_u[0] >>> 8 & 0x3F)] | des_SPtrans_[4][(ref_to_u[0] >>> 16 & 0x3F)] | des_SPtrans_[6][(ref_to_u[0] >>> 24 & 0x3F)]);
	  }
	  
	  public void FP(int[] ref_to_l, int[] ref_to_r)
	  {
	    int tt = 0;
	    Int32Manipulator.PERM_OP(ref_to_l, ref_to_r, tt, 1, 1431655765);
	    Int32Manipulator.PERM_OP(ref_to_r, ref_to_l, tt, 8, 16711935);
	    Int32Manipulator.PERM_OP(ref_to_l, ref_to_r, tt, 2, 858993459);
	    Int32Manipulator.PERM_OP(ref_to_r, ref_to_l, tt, 16, 65535);
	    Int32Manipulator.PERM_OP(ref_to_l, ref_to_r, tt, 4, 252645135);
	  }
	  
	  public void IP(int[] ref_to_l, int[] ref_to_r)
	  {
	    int tt = 0;
	    Int32Manipulator.PERM_OP(ref_to_r, ref_to_l, tt, 4, 252645135);
	    Int32Manipulator.PERM_OP(ref_to_l, ref_to_r, tt, 16, 65535);
	    Int32Manipulator.PERM_OP(ref_to_r, ref_to_l, tt, 2, 858993459);
	    Int32Manipulator.PERM_OP(ref_to_l, ref_to_r, tt, 8, 16711935);
	    Int32Manipulator.PERM_OP(ref_to_r, ref_to_l, tt, 1, 1431655765);
	  }
	  
	  public int cbc_cksum(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec)
	  {
	    int[] inout = new int[2];
	    int tout0 = Int32Manipulator.bytes_to_int(ivec, 0);
	    int tout1 = Int32Manipulator.bytes_to_int(ivec, 4);
	    int offset = 0;
	    for (; length > 0; length -= 8)
	    {
	      int chunksize;
	      int tin0;
	      int tin1;
	      if (length >= 8)
	      {
	         tin0 = Int32Manipulator.bytes_to_int(input, input_start);
	         tin1 = Int32Manipulator.bytes_to_int(input, input_start + 4);
	        chunksize = 8;
	      }
	      else
	      {
	        int[] ref_to_tin01 = new int[2];
	        chunksize = Int32Manipulator.c2ln(input, input_start, length, ref_to_tin01);
	        tin0 = ref_to_tin01[0];
	        tin1 = ref_to_tin01[1];
	      }
	      tin0 ^= tout0;
	      tin1 ^= tout1;
	      inout[0] = tin0;
	      inout[1] = tin1;
	      des_encrypt(inout, true);
	      tout0 = inout[0];
	      tout1 = inout[1];
	      input_start += chunksize;
	    }
	    Int32Manipulator.set_int(output, output_start, tout0);
	    Int32Manipulator.set_int(output, output_start + 4, tout1);
	    return tout1;
	  }
	  
	  public void cbc_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, boolean encrypt)
	  {
	    int[] inout = new int[2];
	    if (encrypt)
	    {
	      int tout0 = Int32Manipulator.bytes_to_int(ivec, 0);
	      int tout1 = Int32Manipulator.bytes_to_int(ivec, 4);
	      for (; length > 0; length -= 8)
	      {
	        int chunksize;
	        int tin0;
	        int tin1;
	        if (length >= 8)
	        {
	           tin0 = Int32Manipulator.bytes_to_int(input, input_start);
	           tin1 = Int32Manipulator.bytes_to_int(input, input_start + 4);
	          chunksize = 8;
	        }
	        else
	        {
	          int[] ref_to_tin01 = new int[2];
	          chunksize = Int32Manipulator.c2ln(input, input_start, length, ref_to_tin01);
	          tin0 = ref_to_tin01[0];
	          tin1 = ref_to_tin01[1];
	        }
	        tin0 ^= tout0;
	        tin1 ^= tout1;
	        inout[0] = tin0;
	        inout[1] = tin1;
	        des_encrypt(inout, encrypt);
	        tout0 = inout[0];
	        tout1 = inout[1];
	        Int32Manipulator.set_int(output, output_start, tout0);
	        Int32Manipulator.set_int(output, output_start + 4, tout1);
	        input_start += chunksize;
	        output_start += chunksize;
	      }
	      Int32Manipulator.set_int(ivec, 0, tout0);
	      Int32Manipulator.set_int(ivec, 4, tout1);
	    }
	    else
	    {
	      int xor0 = Int32Manipulator.bytes_to_int(ivec, 0);
	      int xor1 = Int32Manipulator.bytes_to_int(ivec, 4);
	      for (; length > 0; length -= 8)
	      {
	        int tin0 = Int32Manipulator.bytes_to_int(input, input_start);
	        int tin1 = Int32Manipulator.bytes_to_int(input, input_start + 4);
	        inout[0] = tin0;
	        inout[1] = tin1;
	        des_encrypt(inout, encrypt);
	        int tout0 = inout[0] ^ xor0;
	        int tout1 = inout[1] ^ xor1;
	        int chunksize;
	        if (length >= 8)
	        {
	          Int32Manipulator.set_int(output, output_start, tout0);
	          Int32Manipulator.set_int(output, output_start + 4, tout1);
	          chunksize = 8;
	        }
	        else
	        {
	          chunksize = Int32Manipulator.l2cn(output, output_start, length, tout0, tout1);
	        }
	        xor0 = tin0;
	        xor1 = tin1;
	        input_start += chunksize;
	        output_start += chunksize;
	      }
	      Int32Manipulator.set_int(ivec, 0, xor0);
	      Int32Manipulator.set_int(ivec, 4, xor1);
	    }
	  }
	  
	  public void cfb64_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, int[] num, boolean encrypt)
	  {
	    int n = num[0];
	    int[] ti = new int[2];
	    if (encrypt) {
	      while (length-- != 0)
	      {
	        if (n == 0)
	        {
	          ti[0] = Int32Manipulator.bytes_to_int(ivec, 0);
	          ti[1] = Int32Manipulator.bytes_to_int(ivec, 4);
	          des_encrypt(ti, encrypt);
	          Int32Manipulator.set_int(ivec, 0, ti[0]);
	          Int32Manipulator.set_int(ivec, 4, ti[1]);
	        }
	        byte c = (byte)(input[input_start] ^ ivec[n]);
	        output[output_start] = c;
	        input_start++;
	        output_start++;
	        ivec[n] = c;
	        n = n + 1 & 0x7;
	      }
	    } else {
	      while (length-- != 0)
	      {
	        if (n == 0)
	        {
	          ti[0] = Int32Manipulator.bytes_to_int(ivec, 0);
	          ti[1] = Int32Manipulator.bytes_to_int(ivec, 4);
	          des_encrypt(ti, true);
	          Int32Manipulator.set_int(ivec, 0, ti[0]);
	          Int32Manipulator.set_int(ivec, 4, ti[1]);
	        }
	        byte cc = input[input_start];
	        byte c = ivec[n];
	        ivec[n] = cc;
	        output[output_start] = ((byte)(c ^ cc));
	        input_start++;
	        output_start++;
	        n = n + 1 & 0x7;
	      }
	    }
	    num[0] = n;
	  }
	  
	  public void cfb_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, int numbits, byte[] ivec, boolean encrypt)
	  {
	    if (numbits > 64) {
	      numbits = 64;
	    }
	    int n = (numbits + 7) / 8;
	    int mask1;
	    int mask0 = -1;
	    if (numbits > 32)
	    {
	      if (numbits == 64) {
	        mask1 = mask0;
	      } else {
	        mask1 = (1 << numbits - 32) - 1;
	      }
	    }
	    else
	    {
	      if (numbits == 32) {
	        mask0 = -1;
	      } else {
	        mask0 = (1 << numbits) - 1;
	      }
	      mask1 = 0;
	    }
	    int v0 = Int32Manipulator.bytes_to_int(ivec, 0);
	    int v1 = Int32Manipulator.bytes_to_int(ivec, 4);
	    int[] ref_to_d01 = new int[2];
	    int[] ti = new int[2];
	    if (encrypt) {
	      while (length >= n)
	      {
	        length -= n;
	        ti[0] = v0;
	        ti[1] = v1;
	        des_encrypt(ti, encrypt);
	        Int32Manipulator.c2ln(input, input_start, n, ref_to_d01);
	        input_start += n;
	        ref_to_d01[0] = ((ref_to_d01[0] ^ ti[0]) & mask0);
	        ref_to_d01[1] = ((ref_to_d01[1] ^ ti[1]) & mask1);
	        Int32Manipulator.l2cn(output, output_start, n, ref_to_d01[0], ref_to_d01[1]);
	        output_start += n;
	        if (numbits == 32)
	        {
	          v0 = v1;
	          v1 = ref_to_d01[0];
	        }
	        else if (numbits == 64)
	        {
	          v0 = ref_to_d01[0];
	          v1 = ref_to_d01[1];
	        }
	        else if (numbits > 32)
	        {
	          v0 = v1 >>> numbits - 32 | ref_to_d01[0] << 64 - numbits;
	          v1 = ref_to_d01[0] >>> numbits - 32 | ref_to_d01[1] << 64 - numbits;
	        }
	        else
	        {
	          v0 = v0 >>> numbits | v1 << 32 - numbits;
	          v1 = v1 >>> numbits | ref_to_d01[0] << 32 - numbits;
	        }
	      }
	    } else {
	      while (length >= n)
	      {
	        length -= n;
	        ti[0] = v0;
	        ti[1] = v1;
	        des_encrypt(ti, true);
	        Int32Manipulator.c2ln(input, input_start, n, ref_to_d01);
	        if (numbits == 32)
	        {
	          v0 = v1;
	          v1 = ref_to_d01[0];
	        }
	        else if (numbits == 64)
	        {
	          v0 = ref_to_d01[0];
	          v1 = ref_to_d01[1];
	        }
	        else if (numbits > 32)
	        {
	          v0 = v1 >>> numbits - 32 | ref_to_d01[0] << 64 - numbits;
	          v1 = ref_to_d01[0] >>> numbits - 32 | ref_to_d01[1] << 64 - numbits;
	        }
	        else
	        {
	          v0 = v0 >>> numbits | v1 << 32 - numbits;
	          v1 = v1 >>> numbits | ref_to_d01[0] << 32 - numbits;
	        }
	        ref_to_d01[0] = ((ref_to_d01[0] ^ ti[0]) & mask0);
	        ref_to_d01[1] = ((ref_to_d01[1] ^ ti[1]) & mask1);
	        Int32Manipulator.l2cn(output, output_start, n, ref_to_d01[0], ref_to_d01[1]);
	        input_start += n;
	        output_start += n;
	      }
	    }
	    Int32Manipulator.set_int(ivec, 0, v0);
	    Int32Manipulator.set_int(ivec, 4, v1);
	  }
	  
	  private void des_encrypt(int[] data, boolean encrypt)
	  {
	    int tmp = 0;
	    int[] ref_to_u = new int[1];
	    int[] ref_to_r = new int[1];
	    int[] ref_to_l = new int[1];
	    ref_to_u[0] = data[0];
	    ref_to_r[0] = data[1];
	    IP(ref_to_u, ref_to_r);
	    ref_to_l[0] = (ref_to_r[0] << 1 | ref_to_r[0] >>> 31);
	    ref_to_r[0] = (ref_to_u[0] << 1 | ref_to_u[0] >>> 31);
	    if (encrypt) {
	      for (int i = 0; i < 32; i += 4)
	      {
	        D_ENCRYPT(ref_to_l, ref_to_r[0], i, ref_to_u);
	        D_ENCRYPT(ref_to_r, ref_to_l[0], i + 2, ref_to_u);
	      }
	    } else {
	      for (int i = 30; i > 0; i -= 4)
	      {
	        D_ENCRYPT(ref_to_l, ref_to_r[0], i, ref_to_u);
	        D_ENCRYPT(ref_to_r, ref_to_l[0], i - 2, ref_to_u);
	      }
	    }
	    ref_to_l[0] = (ref_to_l[0] >>> 1 | ref_to_l[0] << 31);
	    ref_to_r[0] = (ref_to_r[0] >>> 1 | ref_to_r[0] << 31);
	    FP(ref_to_r, ref_to_l);
	    data[0] = ref_to_l[0];
	    data[1] = ref_to_r[0];
	  }
	  
	  public void des_encrypt2(int[] data, boolean encrypt)
	  {
	    int tmp = 0;
	    int[] ref_to_u = new int[1];
	    int[] ref_to_r = new int[1];
	    int[] ref_to_l = new int[1];
	    ref_to_u[0] = data[0];
	    ref_to_r[0] = data[1];
	    ref_to_l[0] = (ref_to_r[0] << 1 | ref_to_r[0] >>> 31);
	    ref_to_r[0] = (ref_to_u[0] << 1 | ref_to_u[0] >>> 31);
	    if (encrypt) {
	      for (int i = 0; i < 32; i += 4)
	      {
	        D_ENCRYPT(ref_to_l, ref_to_r[0], i, ref_to_u);
	        D_ENCRYPT(ref_to_r, ref_to_l[0], i + 2, ref_to_u);
	      }
	    } else {
	      for (int i = 30; i > 0; i -= 4)
	      {
	        D_ENCRYPT(ref_to_l, ref_to_r[0], i, ref_to_u);
	        D_ENCRYPT(ref_to_r, ref_to_l[0], i - 2, ref_to_u);
	      }
	    }
	    ref_to_l[0] = (ref_to_l[0] >>> 1 | ref_to_l[0] << 31);
	    ref_to_r[0] = (ref_to_r[0] >>> 1 | ref_to_r[0] << 31);
	    data[0] = ref_to_l[0];
	    data[1] = ref_to_r[0];
	  }
	  
	  public void ecb_encrypt(byte[] input, int input_start, byte[] output, int output_start, boolean encrypt)
	  {
	    int[] ll = new int[2];
	    ll[0] = Int32Manipulator.bytes_to_int(input, input_start);
	    ll[1] = Int32Manipulator.bytes_to_int(input, input_start + 4);
	    des_encrypt(ll, encrypt);
	    Int32Manipulator.set_int(output, output_start, ll[0]);
	    Int32Manipulator.set_int(output, output_start + 4, ll[1]);
	  }
	  
	  public void ofb64_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, int[] num)
	  {
	    byte[] dp = new byte[8];
	    int[] ti = new int[2];
	    int n = num[0];
	    boolean save = false;
	    ti[0] = Int32Manipulator.bytes_to_int(ivec, 0);
	    ti[1] = Int32Manipulator.bytes_to_int(ivec, 4);
	    Int32Manipulator.set_int(dp, 0, ti[0]);
	    Int32Manipulator.set_int(dp, 4, ti[1]);
	    while (length-- != 0)
	    {
	      if (n == 0)
	      {
	        des_encrypt(ti, true);
	        Int32Manipulator.set_int(dp, 0, ti[0]);
	        Int32Manipulator.set_int(dp, 4, ti[1]);
	        save = true;
	      }
	      output[output_start] = ((byte)(input[input_start] ^ dp[n]));
	      input_start++;
	      output_start++;
	      n = n + 1 & 0x7;
	    }
	    if (save)
	    {
	      Int32Manipulator.set_int(ivec, 0, ti[0]);
	      Int32Manipulator.set_int(ivec, 4, ti[1]);
	    }
	    num[0] = n;
	  }
	  
	  public void ofb_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, int numbits, byte[] ivec)
	  {
	    if (numbits > 64) {
	      numbits = 64;
	    }
	    int n = (numbits + 7) / 8;
	    int mask1;
	    int mask0 = -1;
	    if (numbits > 32)
	    {
	      if (numbits == 64) {
	        mask1 = mask0;
	      } else {
	        mask1 = (1 << numbits - 32) - 1;
	      }
	    }
	    else
	    {
	      if (numbits == 32) {
	        mask0 = -1;
	      } else {
	        mask0 = (1 << numbits) - 1;
	      }
	      mask1 = 0;
	    }
	    int v0 = Int32Manipulator.bytes_to_int(ivec, 0);
	    int v1 = Int32Manipulator.bytes_to_int(ivec, 4);
	    int[] ti = new int[2];
	    ti[0] = v0;
	    ti[1] = v1;
	    int[] ref_to_d01 = new int[2];
	    while (length-- > 0)
	    {
	      des_encrypt(ti, true);
	      Int32Manipulator.c2ln(input, input_start, n, ref_to_d01);
	      ref_to_d01[0] = ((ref_to_d01[0] ^ ti[0]) & mask0);
	      ref_to_d01[1] = ((ref_to_d01[1] ^ ti[1]) & mask1);
	      Int32Manipulator.l2cn(output, output_start, n, ref_to_d01[0], ref_to_d01[1]);
	      input_start += n;
	      output_start += n;
	    }
	    v0 = ti[0];
	    v1 = ti[1];
	    Int32Manipulator.set_int(ivec, 0, v0);
	    Int32Manipulator.set_int(ivec, 4, v1);
	  }
	  
	  public void pcbc_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, boolean encrypt)
	  {
	    int[] inout = new int[2];
	    int xor0 = Int32Manipulator.bytes_to_int(ivec, 0);
	    int xor1 = Int32Manipulator.bytes_to_int(ivec, 4);
	    if (encrypt) {
	      for (; length > 0; length -= 8)
	      {
	        int chunksize;
	        int sin0;
	        int sin1;
	        if (length >= 8)
	        {
	           sin0 = Int32Manipulator.bytes_to_int(input, input_start);
	           sin1 = Int32Manipulator.bytes_to_int(input, input_start + 4);
	          chunksize = 8;
	        }
	        else
	        {
	          int[] ref_to_sin01 = new int[2];
	          chunksize = Int32Manipulator.c2ln(input, input_start, length, ref_to_sin01);
	          sin0 = ref_to_sin01[0];
	          sin1 = ref_to_sin01[1];
	        }
	        inout[0] = (sin0 ^ xor0);
	        inout[1] = (sin1 ^ xor1);
	        des_encrypt(inout, encrypt);
	        int tout0 = inout[0];
	        int tout1 = inout[1];
	        xor0 = sin0 ^ tout0;
	        xor1 = sin1 ^ tout1;
	        Int32Manipulator.set_int(output, output_start, tout0);
	        Int32Manipulator.set_int(output, output_start + 4, tout1);
	        input_start += chunksize;
	        output_start += chunksize;
	      }
	    } else {
	      for (; length > 0; length -= 8)
	      {
	        int sin0 = Int32Manipulator.bytes_to_int(input, input_start);
	        int sin1 = Int32Manipulator.bytes_to_int(input, input_start + 4);
	        inout[0] = sin0;
	        inout[1] = sin1;
	        des_encrypt(inout, encrypt);
	        int tout0 = inout[0] ^ xor0;
	        int tout1 = inout[1] ^ xor1;
	        int chunksize;
	        if (length >= 8)
	        {
	          Int32Manipulator.set_int(output, output_start, tout0);
	          Int32Manipulator.set_int(output, output_start + 4, tout1);
	          chunksize = 8;
	        }
	        else
	        {
	          chunksize = Int32Manipulator.l2cn(output, output_start, length, tout0, tout1);
	        }
	        xor0 = tout0 ^ sin0;
	        xor1 = tout1 ^ sin1;
	        input_start += chunksize;
	        output_start += chunksize;
	      }
	    }
	  }
	  
	  private static final int[][] des_SPtrans_ = {
	    {
	    8520192, 131072, -2139095040, -2138963456, 8388608, -2147352064, -2147352576, -2139095040, -2147352064, 8520192, 
	    8519680, -2147483136, -2139094528, 8388608, 0, -2147352576, 131072, -2147483648, 8389120, 131584, 
	    -2138963456, 8519680, -2147483136, 8389120, -2147483648, 512, 131584, -2138963968, 512, -2139094528, 
	    -2138963968, 0, 0, -2138963456, 8389120, -2147352576, 8520192, 131072, -2147483136, 8389120, 
	    -2138963968, 512, 131584, -2139095040, -2147352064, -2147483648, -2139095040, 8519680, -2138963456, 131584, 
	    8519680, -2139094528, 8388608, -2147483136, -2147352576, 0, 131072, 8388608, -2139094528, 8520192, 
	    -2147483648, -2138963968, 512, -2147352064 }, 
	    {
	    268705796, 0, 270336, 268697600, 268435460, 8196, 268443648, 270336, 8192, 268697604, 
	    4, 268443648, 262148, 268705792, 268697600, 4, 262144, 268443652, 268697604, 8192, 
	    270340, 268435456, 0, 262148, 268443652, 270340, 268705792, 268435460, 268435456, 262144, 
	    8196, 268705796, 262148, 268705792, 268443648, 270340, 268705796, 262148, 268435460, 
	    0, 268435456, 8196, 262144, 268697604, 8192, 268435456, 270340, 268443652, 268705792, 8192, 
	    0, 268435460, 4, 268705796, 270336, 268697600, 268697604, 262144, 8196, 268443648, 
	    268443652, 4, 268697600, 270336 }, 
	    {
	    1090519040, 16842816, 64, 1090519104, 1073807360, 16777216, 1090519104, 65600, 16777280, 65536, 
	    16842752, 1073741824, 1090584640, 1073741888, 1073741824, 1090584576, 0, 1073807360, 16842816, 64, 
	    1073741888, 1090584640, 65536, 1090519040, 1090584576, 16777280, 1073807424, 16842752, 65600, 
	    0, 16777216, 1073807424, 16842816, 64, 1073741824, 65536, 1073741888, 1073807360, 16842752, 1090519104, 
	    0, 16842816, 65600, 1090584576, 1073807360, 16777216, 1090584640, 1073741824, 1073807424, 1090519040, 
	    16777216, 1090584640, 65536, 16777280, 1090519104, 65600, 16777280, 0, 1090584576, 1073741888, 
	    1090519040, 1073807424, 64, 16842752 }, 
	    {
	    1049602, 67109888, 2, 68158466, 0, 68157440, 67109890, 1048578, 68158464, 67108866, 
	    67108864, 1026, 67108866, 1049602, 1048576, 67108864, 68157442, 1049600, 1024, 2, 
	    1049600, 67109890, 68157440, 1024, 1026, 0, 1048578, 68158464, 67109888, 68157442, 
	    68158466, 1048576, 68157442, 1026, 1048576, 67108866, 1049600, 67109888, 2, 68157440, 
	    67109890, 0, 1024, 1048578, 0, 68157442, 68158464, 1024, 67108864, 68158466, 
	    1049602, 1048576, 68158466, 2, 67109888, 1049602, 1048578, 1049600, 68157440, 67109890, 
	    1026, 67108864, 67108866, 68158464 }, 
	    {
	    33554432, 16384, 256, 33571080, 33570824, 33554688, 16648, 33570816, 16384, 8, 
	    33554440, 16640, 33554696, 33570824, 33571072, 0, 16640, 33554432, 16392, 264, 
	    33554688, 16648, 0, 33554440, 8, 33554696, 33571080, 16392, 33570816, 256, 
	    264, 33571072, 33571072, 33554696, 16392, 33570816, 16384, 8, 33554440, 33554688, 
	    33554432, 16640, 33571080, 0, 16648, 33554432, 256, 16392, 33554696, 256, 
	    0, 33571080, 33570824, 33571072, 264, 16384, 16640, 33570824, 33554688, 264, 
	    8, 16648, 33570816, 33554440 }, 
	    {
	    536870928, 524304, 0, 537397248, 524304, 2048, 536872976, 524288, 2064, 537397264, 
	    526336, 536870912, 536872960, 536870928, 537395200, 526352, 524288, 536872976, 537395216, 
	    0, 2048, 16, 537397248, 537395216, 537397264, 537395200, 536870912, 2064, 16, 526336, 
	    526352, 536872960, 2064, 536870912, 536872960, 526352, 537397248, 524304, 0, 536872960, 
	    536870912, 2048, 537395216, 524288, 524304, 537397264, 526336, 16, 537397264, 526336, 
	    524288, 536872976, 536870928, 537395200, 526352, 0, 2048, 536870928, 536872976, 537397248, 
	    537395200, 2064, 16, 537395216 }, 
	    {
	    4096, 128, 4194432, 4194305, 4198529, 4097, 4224, 0, 4194304, 4194433, 
	    129, 4198400, 1, 4198528, 4198400, 129, 4194433, 4096, 4097, 4198529, 
	    0, 4194432, 4194305, 4224, 4198401, 4225, 4198528, 1, 4225, 4198401, 
	    128, 4194304, 4225, 4198400, 4198401, 129, 4096, 128, 4194304, 4198401, 
	    4194433, 4225, 4224, 0, 128, 4194305, 1, 4194432, 0, 4194433, 
	    4194432, 4224, 129, 4096, 4198529, 4194304, 4198528, 1, 4097, 4198529, 
	    4194305, 4198528, 4198400, 4097 }, 
	    {
	    136314912, 136347648, 32800, 0, 134250496, 2097184, 136314880, 136347680, 32, 134217728, 
	    2129920, 32800, 2129952, 134250528, 134217760, 136314880, 32768, 2129952, 2097184, 134250496, 
	    136347680, 134217760, 0, 2129920, 134217728, 2097152, 134250528, 136314912, 2097152, 32768, 
	    136347648, 32, 2097152, 32768, 134217760, 136347680, 32800, 134217728, 0, 2129920, 
	    136314912, 134250528, 134250496, 2097184, 136347648, 32, 2097184, 134250496, 136347680, 2097152, 
	    136314880, 134217760, 2129920, 32800, 134250528, 136314880, 32, 136347648, 2129952, 
	    0, 134217728, 136314912, 32768, 2129952 } };
}
