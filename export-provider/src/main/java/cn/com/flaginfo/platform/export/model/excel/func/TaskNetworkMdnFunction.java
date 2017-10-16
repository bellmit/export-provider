package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

public class TaskNetworkMdnFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null || !(value instanceof Map)){
			return "";
		}
		Map<String, Object> networkMdn = (Map<String, Object>)value;
		return networkMdn.get("unum") + "/" + networkMdn.get("mnum") + "/" + networkMdn.get("tnum");
	}
}
