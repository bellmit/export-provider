package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class CountryFunction implements Function<Object,Object> {
	
	private Map<String,String> countryMap;
	
	public CountryFunction(Map<String,String> countryMap){
		this.countryMap = countryMap;
	}
	
	@Override
	public String apply(Object value, Object param) {
		if(null==value) {
			return "";
		}
		return countryMap.get(value);
	}

}
