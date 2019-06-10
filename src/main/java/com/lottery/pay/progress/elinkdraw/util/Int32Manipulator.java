package com.lottery.pay.progress.elinkdraw.util;

public class Int32Manipulator {
	public static void HPERM_OP(int[] ref_to_a, int tmp, int n, int m)
	  {
	    int a = ref_to_a[0];
	    tmp = (a << 16 - n ^ a) & m;
	    a = a ^ tmp ^ tmp >>> 16 - n;
	    ref_to_a[0] = a;
	  }
	  
	  public static void PERM_OP(int[] ref_to_a, int[] ref_to_b, int tmp, int n, int m)
	  {
	    int a = ref_to_a[0];
	    int b = ref_to_b[0];
	    tmp = (a >>> n ^ b) & m;
	    b ^= tmp;
	    a ^= tmp << n;
	    ref_to_a[0] = a;
	    ref_to_b[0] = b;
	  }
	  
	  public static int bytes_to_int(byte[] b, int start)
	  {
	    return b[start] & 0xFF | (b[(start + 1)] & 0xFF) << 8 | (b[(start + 2)] & 0xFF) << 16 | (b[(start + 3)] & 0xFF) << 24;
	  }
	  
	  public static int c2ln(byte[] input, int offset, int length, int[] ref_to_in01)
	  {
	    int orig_length = length;
	    ref_to_in01[0] = 0;
	    ref_to_in01[1] = 0;
	    switch (length)
	    {
	    case 8: 
	      ref_to_in01[1] = ((input[(offset + --length)] & 0xFF) << 24);
	    case 7: 
	      ref_to_in01[1] |= (input[(offset + --length)] & 0xFF) << 16;
	    case 6: 
	      ref_to_in01[1] |= (input[(offset + --length)] & 0xFF) << 8;
	    case 5: 
	      ref_to_in01[1] |= input[(offset + --length)] & 0xFF;
	    case 4: 
	      ref_to_in01[0] = ((input[(offset + --length)] & 0xFF) << 24);
	    case 3: 
	      ref_to_in01[0] |= (input[(offset + --length)] & 0xFF) << 16;
	    case 2: 
	      ref_to_in01[0] |= (input[(offset + --length)] & 0xFF) << 8;
	    case 1: 
	      ref_to_in01[0] |= input[(offset + --length)] & 0xFF;
	    }
	    return orig_length;
	  }
	  
	  public static int l2cn(byte[] output, int offset, int length, int out0, int out1)
	  {
	    int orig_length = length;
	    switch (length)
	    {
	    case 8: 
	      output[(offset + --length)] = ((byte)(out1 >>> 24 & 0xFF));
	    case 7: 
	      output[(offset + --length)] = ((byte)(out1 >>> 16 & 0xFF));
	    case 6: 
	      output[(offset + --length)] = ((byte)(out1 >>> 8 & 0xFF));
	    case 5: 
	      output[(offset + --length)] = ((byte)(out1 & 0xFF));
	    case 4: 
	      output[(offset + --length)] = ((byte)(out0 >>> 24 & 0xFF));
	    case 3: 
	      output[(offset + --length)] = ((byte)(out0 >>> 16 & 0xFF));
	    case 2: 
	      output[(offset + --length)] = ((byte)(out0 >>> 8 & 0xFF));
	    case 1: 
	      output[(offset + --length)] = ((byte)(out0 & 0xFF));
	    }
	    return orig_length;
	  }
	  
	  public static void set_int(byte[] bytes, int offset, int val)
	  {
	    bytes[(offset++)] = ((byte)(val & 0xFF));
	    bytes[(offset++)] = ((byte)(val >>> 8 & 0xFF));
	    bytes[(offset++)] = ((byte)(val >>> 16 & 0xFF));
	    bytes[(offset++)] = ((byte)(val >>> 24 & 0xFF));
	  }
}
