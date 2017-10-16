package cn.com.flaginfo.platform.export.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;

import cn.com.flaginfo.platform.common.util.JsonHelper;

/**
 * 帜讯自己的httpClient
 * @author Rain
 *
 */
public abstract class Client {
	
	protected Logger logger = Logger.getLogger(getClass());
	
	private Map<String,Object> reqMap = new HashMap<String,Object>();
	
	private Map<String,String> headerMap = new HashMap<String,String>();
	
	private String chartset = "UTF-8";
	
	private String reqUrl;
	
	public Client addReqParam(String reqKey,Object reqValue){
		this.reqMap.put(reqKey, reqValue);
		return this;
	}
	
	public Client addReqHeaderParam(String reqKey,String reqValue){
		this.headerMap.put(reqKey, reqValue);
		return this;
	}
	
	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap.putAll(headerMap);;
	}
	
	public void setReqMap(Map<String, Object> reqMap) {
		this.reqMap.putAll(reqMap);
	}
	
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
	
	public Map<String, Object> getReqMap() {
		return reqMap;
	}
	
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	
	public String getReqUrl() {
		return reqUrl;
	}
	
	public void setChartset(String chartset) {
		this.chartset = chartset;
	}
	
	public String getChartset() {
		return chartset;
	}
	
	/**
	 * HTTP POST
	 * @param <T>
	 * @return
	 */
	public abstract <T> T post(Class<T> clazz);
	
	/**
	 * HTTP GET
	 * @return
	 */
	public abstract <T> T get(Class<T> clazz);
	
	
	protected <T> T returnClass(String result,Class<T> clazz) {
		if(clazz.getName().equals(String.class.getName())){
			return (T)result;
		}
		return JsonHelper.parseToObject(result, clazz);
	}
	
	
	/**
	 * 获取输入流中的字符串
	 * @param is  输入流
	 * @return
	 */
	protected String getContent(String contentEncoding, InputStream in) throws IOException{
		String answer = null;
		if(in == null){
			return answer;
		}
		try{
			if ( contentEncoding != null && contentEncoding.equals( "gzip" ) ){
				byte[] b = null;
				GZIPInputStream gzip = new GZIPInputStream( in );
				byte[] buffer = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ( (num = gzip.read( buffer ,0 ,buffer.length )) != -1 ){
					baos.write( buffer ,0 ,num );
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
				gzip.close();
				answer = new String( b, "utf-8" ).trim();
				buffer = null;
			}else{
				InputStreamReader inr = new InputStreamReader( in, "utf-8");
				BufferedReader br = new BufferedReader( inr );
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ( (line = br.readLine()) != null ){
					sb.append( line ).append( "\n" );
				}
				answer = sb.toString();
			}
		}finally{
			in.close();
		}
		
		return answer;
	}
	

}
