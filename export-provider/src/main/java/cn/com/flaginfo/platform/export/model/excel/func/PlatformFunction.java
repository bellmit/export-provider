package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class PlatformFunction implements Function<Object, Object>{

	private Map<String,String> platformMap;
	
	public PlatformFunction(Map<String,String> platformMap){
		this.platformMap = platformMap;
	}
	
	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		return platformMap.get(value);
	}
}
