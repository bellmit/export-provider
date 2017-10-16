package cn.com.flaginfo.platform.export.client;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.flaginfo.ApiClient;
import cn.com.flaginfo.platform.common.client.JsonApiClient;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.common.util.SystemMessage;
import cn.com.flaginfo.platform.export.model.Operator;
import cn.com.flaginfo.platform.export.model.OperatorHolder;

/**
 * @see JsonApiClient
 * @author Rain
 *
 */
@Deprecated
public class JsonClient extends WebClient {
	
	private Logger logger = Logger.getLogger(JsonClient.class);
	
	/**
	 * post请求后台接口
	 * 
	 * @param urlkey
	 *            请求url的唯一标识key
	 * @param apikey
	 *            客户端请求的apikey
	 * @param version
	 *            请求接口的版本
	 * @param requestMap
	 *            请求的数据
	 * @return 返回值
	 */
	public Map postForMap(String urlKey, String apiKey, String version, Map<String, Object> requestMap) {
		String response = postForString(urlKey, apiKey, version, requestMap);
		return JsonHelper.parseToObject(response, Map.class);
	}
	
	public String postForString(String urlKey,String apiKey,String version,Map<String,Object> requestMap) {
		long startTime = System.currentTimeMillis();
		String reqParam = JsonHelper.parseToJson(requestMap);
		Operator operator = OperatorHolder.get();
		logger.info("request urlkey=" + urlKey + ";request params:" + reqParam);
		ApiClient client = new ApiClient(urlKey, apiKey, version);
		if (operator != null) {
			client.addHeadPara("operator", JsonHelper.parseToJson(operator));
		}
		String res = client.setJsonBody(reqParam).postAsJson();
		long endTime = System.currentTimeMillis();
		logger.info("response urlkey=" + urlKey + ";response data:" + res + "; cost time:(" + (endTime - startTime) + ") ms");
		return res;
	}
	
	
	public Map getForMap(String urlkey,String apikey,String version,Map<String,String> requestMap) {
		long startTime = System.currentTimeMillis();
		String reqParam = JsonHelper.parseToJson(requestMap);
		Operator operator = OperatorHolder.get();
		logger.info("request urlkey=" + urlkey + ";request params:" + reqParam);
		ApiClient client = new ApiClient(urlkey, apikey, version);
		if (operator != null) {
			client.addHeadPara("operator", JsonHelper.parseToJson(operator));
		}
		Set<String> keys = requestMap.keySet();
		for(String key :keys) {
			client.addUrlPara(key, requestMap.get(key));
		}
		String res = client.get();
		long endTime = System.currentTimeMillis();
		logger.info("response urlkey=" + urlkey + ";response data:" + res + "; cost time:(" + (endTime - startTime) + ") ms");
		return JsonHelper.parseToObject(res, Map.class);
	}
	
	/**
	 * post
	 * @param urlKey		api url key
	 * @param requestMap	请求参数
	 * @return
	 */
	public Map postServerForMap(String urlKey,Map<String,Object> requestMap){
		String apiKey = SystemMessage.getString("server_api_key");
		String version = "1.0";
		return postForMap(urlKey, apiKey, version, requestMap);
	}
}
