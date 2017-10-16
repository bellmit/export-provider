package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

import cn.com.flaginfo.platform.common.util.StringUtil;

/**
 * 根据验真类型获取相应的调用次数
 *
 */
public class InspectInvokeDayCountMapFunction implements Function<Object, Object>{

	private String type;
	
	public InspectInvokeDayCountMapFunction(String type){
		this.type = type;
	}
	public InspectInvokeDayCountMapFunction(){}
	
    @Override
    public String apply(Object value, Object param) {
    	Map<String,Object> map = (Map<String, Object>) param;
    	Map countMap = (Map)map.get("countMap");
    	if (countMap != null) {
			if ("three".equals(type)) {
				return StringUtil.isNullOrEmpty(countMap.get("three"))?"-":countMap.get("three").toString();
			} else if ("two".equals(type)) {
				return StringUtil.isNullOrEmpty(countMap.get("two"))?"-":countMap.get("two").toString();
			} 
		}
		return "-";
    }
}
