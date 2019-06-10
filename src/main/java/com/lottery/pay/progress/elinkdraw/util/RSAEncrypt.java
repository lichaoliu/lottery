package com.lottery.pay.progress.elinkdraw.util;

import java.math.BigInteger;

public class RSAEncrypt {
	 private BigInteger p;
	  private BigInteger q;
	  private BigInteger a;
	  private BigInteger b;
	  private BigInteger u;
	  private BigInteger dP;
	  private BigInteger dQ;
	  
	  public RSAEncrypt(PrivateKey key)
	  {
	    try
	    {
	      this.p = new BigInteger(1, key.Prime[0]);
	      this.q = new BigInteger(1, key.Prime[1]);
	      this.u = new BigInteger(1, key.Coefficient);
	      this.dP = new BigInteger(1, key.PrimeExponent[0]);
	      this.dQ = new BigInteger(1, key.PrimeExponent[1]);
	    }
	    catch (IllegalArgumentException _ex) {}
	  }
	  
	  public String EncryptMessage(byte[] input)
	  {
	    BigInteger c = new BigInteger(1, input);
	    String out =null;
	    try
	    {
	      BigInteger cp = c.mod(this.p);
	      BigInteger cq = c.mod(this.q);
	      this.a = cp.modPow(this.dP, this.p);
	      this.b = cq.modPow(this.dQ, this.q);
	      BigInteger result;
	      if (this.a.compareTo(this.b) >= 0)
	      {
	        result = this.a.subtract(this.b);
	      }
	      else
	      {
	        result = this.b.subtract(this.a);
	        result = this.p.subtract(result);
	      }
	       result = result.mod(this.p).multiply(this.u).mod(this.p).multiply(this.q).add(this.b);
	      for (out = result.toString(16); out.length() < 256; out = '0' + out) {}
	    }
	    catch (ArithmeticException _ex)
	    {
	      return null;
	    }
	    return out;
	  }
}
