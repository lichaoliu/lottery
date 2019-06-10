package com.lottery.pay.progress.elinkpc.util;

import java.io.IOException;
import java.net.Socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
public class BaseHttpSSLSocketFactory extends SSLSocketFactory
{
	  private SSLContext getSSLContext()
	  {
	    return createEasySSLContext();
	  }

	  public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3)
	    throws IOException
	  {
	    return getSSLContext().getSocketFactory().createSocket(arg0, arg1, 
	      arg2, arg3);
	  }

	  public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3)
	    throws IOException, UnknownHostException
	  {
	    return getSSLContext().getSocketFactory().createSocket(arg0, arg1, 
	      arg2, arg3);
	  }

	  public Socket createSocket(InetAddress arg0, int arg1) throws IOException
	  {
	    return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	  }

	  public Socket createSocket(String arg0, int arg1)
	    throws IOException, UnknownHostException
	  {
	    return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	  }

	  public String[] getSupportedCipherSuites()
	  {
	    return null;
	  }

	  public String[] getDefaultCipherSuites()
	  {
	    return null;
	  }

	  public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3)
	    throws IOException
	  {
	    return getSSLContext().getSocketFactory().createSocket(arg0, arg1, 
	      arg2, arg3);
	  }

	  private SSLContext createEasySSLContext() {
	    try {
	      SSLContext context = SSLContext.getInstance("SSL");
	      context.init(null, 
	        new TrustManager[] { MyX509TrustManager.manger }, null);
	      return context;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }return null;
	  }

	  public static class MyX509TrustManager
	    implements X509TrustManager
	  {
	    static MyX509TrustManager manger = new MyX509TrustManager();

	    public X509Certificate[] getAcceptedIssuers()
	    {
	      return null;
	    }

	    public void checkClientTrusted(X509Certificate[] chain, String authType)
	    {
	    }

	    public void checkServerTrusted(X509Certificate[] chain, String authType)
	    {
	    }
	  }


	  public static class TrustAnyHostnameVerifier implements HostnameVerifier
	  {
	    public boolean verify(String hostname, SSLSession session)
	    {
	      return true;
	    }
	  }
}
