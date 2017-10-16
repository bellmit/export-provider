package cn.com.flaginfo.platform.export.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import cn.com.flaginfo.platform.common.util.StringUtil;

/**
 * 表单格式的提交
 * @author Rain
 *
 */
public class FormClient extends Client {
	
	//设置连接超时时间   
	private static final int REQUEST_TIMEOUT = 30*1000; 
	private static final int SO_TIMEOUT = 30*1000;
	private static final int IDLE_TIMEOUT = 5 * 1000;
	private static final int MAX_TRY_TIMES = 3;
	private static final int MAX_PER_HOST = 10;
	private static final int MAX_CLIENT_TOTAL = 1000;
	public static HttpClient httpClient;
	
	static{
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager(); 
		HttpConnectionManagerParams params = connectionManager.getParams();
        params.setConnectionTimeout(REQUEST_TIMEOUT); 
        params.setSoTimeout(SO_TIMEOUT); 
        params.setDefaultMaxConnectionsPerHost(MAX_PER_HOST);
        params.setMaxTotalConnections(MAX_CLIENT_TOTAL);
		httpClient = new HttpClient(connectionManager);
		Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
		Protocol.registerProtocol("https", myhttps);
        System.out.println("httpclient init finished!");
	}
	
	
	/**
	 * 判断请求参数中是否含有文件
	 * @return
	 */
	private boolean hasFile(){
		for(Object o:getReqMap().values()){
			if(o instanceof File){
				return true;
			}
		}
		return false;
	}
	
	
	private Part [] fillParts(){
		
		List<Part> list = new ArrayList<Part>();
		Set<String> set = this.getReqMap().keySet();
		for(String key:set){
			Object o = this.getReqMap().get(key);
			if(o == null){
				continue;
			}
			Part part = null;
			if(o instanceof File){
				try {
					part = new FilePart(key,(File)o);
				} catch (FileNotFoundException e) {
					logger.info("file:"+((File)o).getAbsolutePath()+" don't exists ");
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}else{
				part = new StringPart(key,String.valueOf(o));
			}
			list.add(part);
		}
		
		Part[] parts = new Part[list.size()];
		list.toArray(parts);
		return parts;
	
	}
	
	/**
	 * 有文件上传的提交
	 * @return
	 */
	public String postUploadForm(){
		
		PostMethod filePost=new PostMethod(getReqUrl());//上传地址
		try {
			filePost.setRequestEntity(new MultipartRequestEntity(fillParts(), filePost.getParams()));
			int status=httpClient.executeMethod(filePost);
			StringBuffer strBuff = new StringBuffer();
			if(status!=HttpStatus.SC_OK){
				logger.info("上传文件失败:"+status);
				return strBuff.toString();
			}
			String result = getContent(getChartset(),filePost.getResponseBodyAsStream());
			logger.info("postUpload:"+getReqUrl()+";"+getReqMap()+" response:"+result);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(filePost!=null){
				filePost.releaseConnection();
			}
		}
		return null;
	}
	
	/**
	 * form方式post数据
	 * @return
	 */
	public String postForm(){
		PostMethod post=new PostMethod(getReqUrl());
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, getChartset());
		logger.info(" post url:"+getReqUrl()+" params:"+getReqMap());
		try {
			if(getHeaderMap() != null && !getHeaderMap().isEmpty()){
				Set<Entry<String, String>> entrySet = getHeaderMap().entrySet();
				for(Entry<String,String> e:entrySet){
					post.addRequestHeader(e.getKey(),e.getValue());
				}
			}
			if(getReqMap() != null){
				Set<String> keys = getReqMap().keySet();
				for(String key:keys){
					String value = (String)this.getReqMap().get(key);
					if(!StringUtil.isNullOrEmpty(value)){
						post.setParameter(key, value);
					}
				}
			}
			int status= httpClient.executeMethod(post);
			if(status!=HttpStatus.SC_OK){
				logger.info("post失败:"+status);
				return null;
			}
			return this.getContent(this.getChartset(), post.getResponseBodyAsStream());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(post!=null){
				post.releaseConnection();
				httpClient.getHttpConnectionManager().closeIdleConnections(IDLE_TIMEOUT);
			}
		}
		return null;
	}
	
	public String get() {
		GetMethod getMethod = new GetMethod(getReqUrl());
		getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, getChartset());
		
		//设置请求参数
		if(getReqMap() != null){
			int size = getReqMap().size();
			NameValuePair[] nameValuePairs = new NameValuePair[size];
			int count = 0;
			Set<String> keys = getReqMap().keySet();
			for(String key : keys){
				String value = this.getReqMap().get(key).toString();
				if(!StringUtil.isNullOrEmpty(value)){
					nameValuePairs[count] = new NameValuePair(key, value);
					count ++;
				}
			}
			getMethod.setQueryString(nameValuePairs);
		}
		
		// 设置header
		if(getHeaderMap() != null && !getHeaderMap().isEmpty()){
			Set<Entry<String, String>> entrySet = getHeaderMap().entrySet();
			for(Entry<String,String> e:entrySet){
				getMethod.addRequestHeader(e.getKey(),e.getValue());
			}
		}
		try {
			httpClient.executeMethod(getMethod);
			return this.getContent(this.getChartset(), getMethod.getResponseBodyAsStream());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(getMethod != null){
				getMethod.releaseConnection();
			}
		}
		return null;
	}
	
	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 *
	 * @param paramsMap
	 * 参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	private static NameValuePair[] getNameValuePair(Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		NameValuePair[] params = new NameValuePair[paramsMap.size()];
		int count = 0;
		for (Entry<String, String> map : paramsMap.entrySet()) {
			String key = (String) map.getKey();
			String value = (String) map.getValue();
			params[count] = new NameValuePair(key, value);
			count ++;
		}
		return params;
	}
	
	/**
	 * post
	 */
	@Override
	public <T> T post(Class<T> clazz) {
		String result = null;
		if(!hasFile()){
			result = postForm();
		}else{
			result = postUploadForm();
		}
		return returnClass(result, clazz);
		
	}


	@Override
	public <T> T get(Class<T> clazz) {
		String result = get();
		return returnClass(result, clazz);
	}

}

class MySSLProtocolSocketFactory implements ProtocolSocketFactory {  
	  
	  private SSLContext sslcontext = null;   
	   
	  private SSLContext createSSLContext() {   
	      SSLContext sslcontext=null;   
	      try {   
	          sslcontext = SSLContext.getInstance("SSL");   
	          sslcontext.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());   
	      } catch (NoSuchAlgorithmException e) {   
	          e.printStackTrace();   
	      } catch (KeyManagementException e) {   
	          e.printStackTrace();   
	      }   
	      return sslcontext;   
	  }   
	   
	  private SSLContext getSSLContext() {   
	      if (this.sslcontext == null) {   
	          this.sslcontext = createSSLContext();   
	      }   
	      return this.sslcontext;   
	  }   
	   
	  public Socket createSocket(Socket socket, String host, int port, boolean autoClose)   
	          throws IOException, UnknownHostException {   
	      return getSSLContext().getSocketFactory().createSocket(   
	              socket,   
	              host,   
	              port,   
	              autoClose   
	          );   
	  }   
	  
	  public Socket createSocket(String host, int port) throws IOException,   
	          UnknownHostException {   
	      return getSSLContext().getSocketFactory().createSocket(   
	              host,   
	              port   
	          );   
	  }   
	   
	   
	  public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)   
	          throws IOException, UnknownHostException {   
	      return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);   
	  }   
	  
	  public Socket createSocket(String host, int port, InetAddress localAddress,   
	          int localPort, HttpConnectionParams params) throws IOException,   
	          UnknownHostException, ConnectTimeoutException {   
	      if (params == null) {   
	          throw new IllegalArgumentException("Parameters may not be null");   
	      }   
	      int timeout = params.getConnectionTimeout();   
	      SocketFactory socketfactory = getSSLContext().getSocketFactory();   
	      if (timeout == 0) {   
	          return socketfactory.createSocket(host, port, localAddress, localPort);   
	      } else {   
	          Socket socket = socketfactory.createSocket();   
	          SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);   
	          SocketAddress remoteaddr = new InetSocketAddress(host, port);   
	          socket.bind(localaddr);   
	          socket.connect(remoteaddr, timeout);   
	          return socket;   
	      }   
	  }   
	   
	  //自定义私有类   
	  private static class TrustAnyTrustManager implements X509TrustManager {   
	      
	      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {   
	      }   
	  
	      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {   
	      }   
	  
	      public X509Certificate[] getAcceptedIssuers() {   
	          return new X509Certificate[]{};   
	      }   
	  }     
	  
	  
	} 
