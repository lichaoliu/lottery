package com.lottery.pay.progress.elinkdraw.util;

public class TripleDes implements DesCrypt
{
	  public static final boolean ENCRYPT = true;
	  public static final boolean DECRYPT = false;
	  private DesKey ks1_;
	  private DesKey ks2_;
	  private DesKey ks3_;
	  
	  public TripleDes(DesKey key1, DesKey key2)
	  {
	    this.ks1_ = key1;
	    this.ks2_ = key2;
	    this.ks3_ = this.ks1_;
	  }
	  
	  public TripleDes(DesKey key1, DesKey key2, DesKey key3)
	  {
	    this.ks1_ = key1;
	    this.ks2_ = key2;
	    this.ks3_ = key3;
	  }
	  
	  public void cbc_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, boolean encrypt)
	  {
	    int[] ref_to_tin0 = new int[1];
	    int[] ref_to_tin1 = new int[1];
	    int[] inout = new int[2];
	    int[] ref_to_tout0 = new int[1];
	    int[] ref_to_tout1 = new int[1];
	    Des des1 = new Des(this.ks1_);
	    Des des2 = new Des(this.ks2_);
	    Des des3 = new Des(this.ks3_);
	    if (encrypt)
	    {
	      ref_to_tout0[0] = Int32Manipulator.bytes_to_int(ivec, 0);
	      ref_to_tout1[0] = Int32Manipulator.bytes_to_int(ivec, 4);
	      for (; length > 0; length -= 8)
	      {
	        int chunksize;
	        if (length >= 8)
	        {
	          ref_to_tin0[0] = Int32Manipulator.bytes_to_int(input, input_start);
	          ref_to_tin1[0] = Int32Manipulator.bytes_to_int(input, input_start + 4);
	          chunksize = 8;
	        }
	        else
	        {
	          int[] ref_to_tin01 = new int[2];
	          chunksize = Int32Manipulator.c2ln(input, input_start, length, ref_to_tin01);
	          ref_to_tin0[0] = ref_to_tin01[0];
	          ref_to_tin1[0] = ref_to_tin01[1];
	        }
	        ref_to_tin0[0] ^= ref_to_tout0[0];
	        ref_to_tin1[0] ^= ref_to_tout1[0];
	        des1.IP(ref_to_tin0, ref_to_tin1);
	        inout[0] = ref_to_tin0[0];
	        inout[1] = ref_to_tin1[0];
	        des1.des_encrypt2(inout, true);
	        des2.des_encrypt2(inout, false);
	        des3.des_encrypt2(inout, true);
	        ref_to_tout0[0] = inout[0];
	        ref_to_tout1[0] = inout[1];
	        des1.FP(ref_to_tout1, ref_to_tout0);
	        Int32Manipulator.set_int(output, output_start, ref_to_tout0[0]);
	        Int32Manipulator.set_int(output, output_start + 4, ref_to_tout1[0]);
	        input_start += chunksize;
	        output_start += chunksize;
	      }
	      Int32Manipulator.set_int(ivec, 0, ref_to_tout0[0]);
	      Int32Manipulator.set_int(ivec, 4, ref_to_tout1[0]);
	    }
	    else
	    {
	      int xor0 = Int32Manipulator.bytes_to_int(ivec, 0);
	      int xor1 = Int32Manipulator.bytes_to_int(ivec, 4);
	      for (; length > 0; length -= 8)
	      {
	        ref_to_tin0[0] = Int32Manipulator.bytes_to_int(input, input_start);
	        ref_to_tin1[0] = Int32Manipulator.bytes_to_int(input, input_start + 4);
	        int t0 = ref_to_tin0[0];
	        int t1 = ref_to_tin1[0];
	        des1.IP(ref_to_tin0, ref_to_tin1);
	        inout[0] = ref_to_tin0[0];
	        inout[1] = ref_to_tin1[0];
	        des3.des_encrypt2(inout, false);
	        des2.des_encrypt2(inout, true);
	        des1.des_encrypt2(inout, false);
	        ref_to_tout0[0] = inout[0];
	        ref_to_tout1[0] = inout[1];
	        des1.FP(ref_to_tout1, ref_to_tout0);
	        ref_to_tout0[0] ^= xor0;
	        ref_to_tout1[0] ^= xor1;
	        int chunksize;
	        if (length >= 8)
	        {
	          Int32Manipulator.set_int(output, output_start, ref_to_tout0[0]);
	          Int32Manipulator.set_int(output, output_start + 4, ref_to_tout1[0]);
	          chunksize = 8;
	        }
	        else
	        {
	          chunksize = Int32Manipulator.l2cn(output, output_start, length, ref_to_tout0[0], ref_to_tout1[0]);
	        }
	        xor0 = t0;
	        xor1 = t1;
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
	    Des des1 = new Des(this.ks1_);
	    Des des2 = new Des(this.ks2_);
	    Des des3 = new Des(this.ks3_);
	    int[] ref_to_v0 = new int[1];
	    int[] ref_to_v1 = new int[1];
	    if (encrypt) {
	      while (length-- != 0)
	      {
	        if (n == 0)
	        {
	          int v0 = Int32Manipulator.bytes_to_int(ivec, 0);
	          int v1 = Int32Manipulator.bytes_to_int(ivec, 4);
	          ref_to_v0[0] = v0;
	          ref_to_v1[0] = v1;
	          des1.IP(ref_to_v0, ref_to_v1);
	          v0 = ref_to_v0[0];
	          v1 = ref_to_v1[0];
	          ti[0] = v0;
	          ti[1] = v1;
	          des1.des_encrypt2(ti, true);
	          des2.des_encrypt2(ti, false);
	          des3.des_encrypt2(ti, true);
	          v0 = ti[0];
	          v1 = ti[1];
	          ref_to_v0[0] = v0;
	          ref_to_v1[0] = v1;
	          des1.FP(ref_to_v1, ref_to_v0);
	          v0 = ref_to_v0[0];
	          v1 = ref_to_v1[0];
	          Int32Manipulator.set_int(ivec, 0, v0);
	          Int32Manipulator.set_int(ivec, 4, v1);
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
	          int v0 = Int32Manipulator.bytes_to_int(ivec, 0);
	          int v1 = Int32Manipulator.bytes_to_int(ivec, 4);
	          ref_to_v0[0] = v0;
	          ref_to_v1[0] = v1;
	          des1.IP(ref_to_v0, ref_to_v1);
	          v0 = ref_to_v0[0];
	          v1 = ref_to_v1[0];
	          ti[0] = v0;
	          ti[1] = v1;
	          des1.des_encrypt2(ti, true);
	          des2.des_encrypt2(ti, false);
	          des3.des_encrypt2(ti, true);
	          v0 = ti[0];
	          v1 = ti[1];
	          ref_to_v0[0] = v0;
	          ref_to_v1[0] = v1;
	          des1.FP(ref_to_v1, ref_to_v0);
	          v0 = ref_to_v0[0];
	          v1 = ref_to_v1[0];
	          Int32Manipulator.set_int(ivec, 0, v0);
	          Int32Manipulator.set_int(ivec, 4, v1);
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
	  
	  public void ecb_encrypt(byte[] input, int input_start, byte[] output, int output_start, boolean encrypt)
	  {
	    int[] ref_to_ll = new int[2];
	    int[] ref_to_l0 = new int[1];
	    int[] ref_to_l1 = new int[1];
	    ref_to_l0[0] = Int32Manipulator.bytes_to_int(input, input_start);
	    ref_to_l1[0] = Int32Manipulator.bytes_to_int(input, input_start + 4);
	    Des des1 = new Des(this.ks1_);
	    Des des2 = new Des(this.ks2_);
	    Des des3 = new Des(this.ks3_);
	    des1.IP(ref_to_l0, ref_to_l1);
	    ref_to_ll[0] = ref_to_l0[0];
	    ref_to_ll[1] = ref_to_l1[0];
	    des1.des_encrypt2(ref_to_ll, encrypt);
	    des2.des_encrypt2(ref_to_ll, encrypt ^ true);
	    des3.des_encrypt2(ref_to_ll, encrypt);
	    ref_to_l0[0] = ref_to_ll[0];
	    ref_to_l1[0] = ref_to_ll[1];
	    des1.FP(ref_to_l1, ref_to_l0);
	    Int32Manipulator.set_int(output, output_start, ref_to_l0[0]);
	    Int32Manipulator.set_int(output, output_start + 4, ref_to_l1[0]);
	  }
	  
	  public void ofb64_encrypt(byte[] input, int input_start, int length, byte[] output, int output_start, byte[] ivec, int[] num)
	  {
	    int[] ref_to_v0 = new int[1];
	    int[] ref_to_v1 = new int[1];
	    int n = num[0];
	    int[] ti = new int[2];
	    boolean save = false;
	    byte[] dp = new byte[8];
	    Des des1 = new Des(this.ks1_);
	    Des des2 = new Des(this.ks2_);
	    Des des3 = new Des(this.ks3_);
	    int v0 = Int32Manipulator.bytes_to_int(ivec, 0);
	    int v1 = Int32Manipulator.bytes_to_int(ivec, 4);
	    ti[0] = v0;
	    ti[1] = v1;
	    Int32Manipulator.set_int(dp, 0, ti[0]);
	    Int32Manipulator.set_int(dp, 4, ti[1]);
	    while (length-- != 0)
	    {
	      if (n == 0)
	      {
	        ref_to_v0[0] = v0;
	        ref_to_v1[0] = v1;
	        des1.IP(ref_to_v0, ref_to_v1);
	        v0 = ref_to_v0[0];
	        v1 = ref_to_v1[0];
	        ti[0] = v0;
	        ti[1] = v1;
	        des1.des_encrypt2(ti, true);
	        des2.des_encrypt2(ti, false);
	        des3.des_encrypt2(ti, true);
	        v0 = ti[0];
	        v1 = ti[1];
	        ref_to_v0[0] = v0;
	        ref_to_v1[0] = v1;
	        des1.FP(ref_to_v1, ref_to_v0);
	        v0 = ref_to_v0[0];
	        v1 = ref_to_v1[0];
	        Int32Manipulator.set_int(dp, 0, v0);
	        Int32Manipulator.set_int(dp, 4, v1);
	        save = true;
	      }
	      output[output_start] = ((byte)(input[input_start] ^ dp[n]));
	      input_start++;
	      output_start++;
	      n = n + 1 & 0x7;
	    }
	    if (save)
	    {
	      Int32Manipulator.set_int(ivec, 0, v0);
	      Int32Manipulator.set_int(ivec, 4, v1);
	    }
	    num[0] = n;
	  }
}
