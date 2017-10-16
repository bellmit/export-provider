package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class CityFunction implements Function<Object, Object>{

	private Map<String,String> cityMap;
	
	public CityFunction(Map<String,String> cityMap){
		this.cityMap = cityMap;
	}
	
	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		return cityMap.get(value);
	}
}
