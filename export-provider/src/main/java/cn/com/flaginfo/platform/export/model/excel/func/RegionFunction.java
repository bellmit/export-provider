package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class RegionFunction implements Function<Object,Object> {
	
	private Map<String,String> regionMap;
	
	public RegionFunction(Map<String,String> regionMap){
		this.regionMap = regionMap;
	}
	
	@Override
	public String apply(Object value, Object param) {
		if(null==value) {
			return "";
		}
		return regionMap.get(value);
	}

}
