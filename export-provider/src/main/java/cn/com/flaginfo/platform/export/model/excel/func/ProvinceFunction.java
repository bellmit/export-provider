package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class ProvinceFunction implements Function<Object, Object>{

	private Map<String,String> provinceMap;

	public ProvinceFunction(Map<String,String> provinceMap){
		this.provinceMap = provinceMap;
	}
	
	@Override
	public String apply(Object value, Object param) {
		if("-1".equals(value)){
			return "其他";
		}
		return provinceMap.get(value);
	}
}
