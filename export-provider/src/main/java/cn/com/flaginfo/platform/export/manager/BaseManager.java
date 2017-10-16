package cn.com.flaginfo.platform.export.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.boot.hsf.annotation.HSFConsumer;

import cn.com.flaginfo.platform.common.base.BaseService;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.proxy.annotation.Cache;
import cn.com.flaginfo.platform.global.api.GlobalApi;
import cn.com.flaginfo.platform.global.api.GlobalApiOld;

@Service
public class BaseManager extends BaseService{
	
//	@Autowired
//	ApplicationContext ctx;
	
//	@HSFConsumer(serviceGroup="global-service",serviceVersion="1.0.0")
//	GlobalApiOld globalApiOld;
//	
//	@HSFConsumer(serviceGroup="global-service",serviceVersion="1.0.0")
//	GlobalApi globalApi;
	
	/**
     * 查询所有的平台的名称
     * @return 返回Map<platform,name>
     */
    @Cache(cacheKey="pf_name_map",ignoreNull=true)
    public Map<String,String> getPfNameMap(GlobalApiOld globalApiOld ) {
    	Map<String,String> pfNameMap = new HashMap<String,String>();
		Map<String,Object> responseMap = globalApiOld.getPlatform(null);
		if(responseMap == null){
			return null;
		}
		List<Map> platformList = (List)responseMap.get("list");
		
		for(Map m:platformList){
			pfNameMap.put((String)m.get("id"),(String)m.get("name"));
		}
		return pfNameMap;
    }
    
    /**
     * 查询所有的平台的名称
     * @return 返回Map<platform,name>
     */
    @Cache(cacheKey="city_name_map",ignoreNull=true)
    public Map<String,String> getCityNameMap(GlobalApiOld globalApiOld) {
    	Map<String,String> cityNameMap = new HashMap<String,String>();
    	Map<String, Object> params = new HashMap<>();
    	params.put("platform", "");
		Map<String,Object> responseMap = globalApiOld.getCitys(JsonHelper.parseToJson(params));
		if(responseMap == null){
			return null;
		}
		List<Map> platformList = (List)responseMap.get("list");
		
		for(Map m:platformList){
			cityNameMap.put((String)m.get("id"),(String)m.get("name"));
		}
		return cityNameMap;
    }
    
    /**
     * 查询所有省份的名称
     * @return 返回Map<province,name>
     * @since 6.0
     */
    @Cache(cacheKey="pro_name_map",ignoreNull=true)
    public Map<String,String> getProvinceNameMap(GlobalApiOld globalApiOld) {
    	Map<String,String> pfNameMap = new HashMap<String,String>();
    	Map<String,Object> responseMap = globalApiOld.getProvince(null);
    	if(responseMap == null){
    		return null;
    	}
    	List<Map> platformList = (List)responseMap.get("list");
    	
    	for(Map m:platformList){
    		pfNameMap.put((String)m.get("id"),(String)m.get("name"));
    	}
    	return pfNameMap;
    }
    
    public Map<String,String> getRegionPfNameMap(GlobalApiOld globalApiOld) {
    	Map<String,String> pfNameMap = new HashMap<String,String>();
    	Map<String, Object> params = new HashMap<>();
    	params.put("region", "");
		Map<String,Object> responseMap = globalApiOld.getPlatformByRegion(JsonHelper.parseToJson(params));
		if(responseMap == null){
			return null;
		}
		List<Map> platformList = (List)responseMap.get("list");
		
		for(Map m:platformList){
			pfNameMap.put((String)m.get("id"),(String)m.get("name"));
		}
		return pfNameMap;
    }
    
    /***
     * 查询所有国家名称
     * @return
     */
    public Map<String,String> getCountryNameMap(GlobalApiOld globalApiOld) {
    	Map<String,String> countryNameMap = new HashMap<String,String>();
		Map<String,Object> responseMap = globalApiOld.getIntlCodeInfo(null);
		if(responseMap == null){
			return null;
		}
		List<Map> countryList = (List)responseMap.get("list");
		for(Map m:countryList){
			countryNameMap.put((String)m.get("id"),(String)m.get("cnName"));
		}
		return countryNameMap;    
	}
    
    /**
     * 查询所有的区域的名称
     * @return 返回Map<regionId,name>
     */
    @Cache(cacheKey="rg_name_map",ignoreNull=true)
    public Map<String,String> getRegionMap(GlobalApiOld globalApiOld) {
    	Map<String,String> pfNameMap = new HashMap<String,String>();
		Map<String,Object> responseMap = globalApiOld.getRegion(null);
		if(responseMap == null){
			return null;
		}
		List<Map> platformList = (List)responseMap.get("list");
		
		for(Map m:platformList){
			pfNameMap.put((String)m.get("id"),(String)m.get("name"));
		}
		return pfNameMap;
    }
    
    /**
	 * 获取行业分类名称
	 * @return
	 */
    @Cache(cacheKey="industry_name_map",ignoreNull=true)
	public Map<String, String> getIndustriesName(GlobalApiOld globalApiOld) {
	       Map<String,String> industriesNameMap = new HashMap<String,String>();
	        
	        Map<String, Object> responseMap = null;
	        Map<String, Object> params = new HashMap<>();
	        params.put("mainType", "");
			try {
				responseMap = globalApiOld.getIndustryTypes(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(responseMap == null){
	            return new HashMap<String, String>();
	        }
	        List<Map<String,Object>> industriyList = (List<Map<String,Object>>)responseMap.get("list");
	        
	        for(Map<String, Object> m:industriyList){
	            industriesNameMap.put((String) m.get("parentId"), (String) m.get("parentName"));
	            List<Map<String, String>> childType = null;
	            if(m.containsKey("childTypes")) {
	                childType = (List<Map<String,String>>) m.get("childTypes");
	                for (Map<String, String> map : childType) {
	                    industriesNameMap.put((String) map.get("id"), (String) map.get("name"));
                    }
	            }
	        }
	        
	        return industriesNameMap;
	}
}
