package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.List;
import java.util.Map;

public class ContactsAttrFunction implements Function<Object, Object>{

	private Map<String,String> optionMap;
	
	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if(value instanceof String){
			return optionMap.get(value);
		}
		List<String> values = (List<String>)value;
		String val = "";
		if(values==null || values.size()==0){
			return "";
		}
		for (String str : values) {
			if("".equals(val)){
				val = optionMap.get(str);
			} else {
				val += ","+optionMap.get(str);
			}
		}
		return val;
	}

	/**
	 * @return the optionMap
	 */
	public Map<String, String> getOptionMap() {
		return optionMap;
	}

	/**
	 * @param optionMap the optionMap to set
	 */
	public void setOptionMap(Map<String, String> optionMap) {
		this.optionMap = optionMap;
	}

}
