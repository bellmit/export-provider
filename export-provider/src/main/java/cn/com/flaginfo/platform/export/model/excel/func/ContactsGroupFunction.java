package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.List;
import java.util.Map;

public class ContactsGroupFunction implements Function<Object, Object>{

	private Map<String,String> groupMap;
	
	@Override
	public String apply(Object value, Object param) {
		List<Map<String,String>> values = (List<Map<String,String>>)value;
		String val = "";
		if(values==null || values.size()==0){
			return "";
		}
		for (Map<String,String> m : values) {
			if("".equals(val)){
				val = groupMap.get(m.get("id"));
			} else {
				val += ","+groupMap.get(m.get("id"));
			}
		}
		return val;
	}

	/**
	 * @return the groupMap
	 */
	public Map<String, String> getGroupMap() {
		return groupMap;
	}

	/**
	 * @param groupMap the groupMap to set
	 */
	public void setGroupMap(Map<String, String> groupMap) {
		this.groupMap = groupMap;
	}

}
