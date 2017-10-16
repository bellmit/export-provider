package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.common.util.StringUtil;

public class SubscribStatusFunction implements Function<Object, Object>{

	private Map<String,String> statusMap;
	
	public SubscribStatusFunction(String type){
		statusMap = new HashMap<>();
		if("status".equals(type)){
			statusMap.put("0", "生效中");
			statusMap.put("1", "生效成功");
			statusMap.put("9", "生效失败");
		}
		if("isDrop".equals(type)){
			statusMap.put("0", "否");
			statusMap.put("1", "是");
		}
	}
	
	@Override
	public String apply(Object value, Object param) {
		String result =  statusMap.get(value);
		if(StringUtil.isNullOrEmpty(result)) {
			return "是";
		}
		return result;
		
	}
}
