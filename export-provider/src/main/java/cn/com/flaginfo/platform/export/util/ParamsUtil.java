package cn.com.flaginfo.platform.export.util;

import java.util.Map;

import cn.com.flaginfo.platform.common.support.SessionHolder;
import cn.com.flaginfo.platform.export.model.Operator;


public class ParamsUtil {
	
	
	public static String getStringFromMap(String key,Map<String,Object> map) {
		return map.get(key)==null?"":map.get(key).toString();
	} 
	
	public static String getOperatorName() {
		Operator operator = SessionHolder.get(Operator.class);
		if(null==operator) {
			return "";
		}
		return operator.getUserName();
	}
	public static String getOperatorName(String defaultName) {
		Operator operator = SessionHolder.get(Operator.class);
		if(null==operator) {
			return defaultName;
		}
		return operator.getUserName();
	}
	
	public static String getOperatorId() {
		Operator operator = SessionHolder.get(Operator.class);
		if(null==operator) {
			return "";
		}
		return operator.getUserId();
	}
	
	public static String getOperatorSpId() {
		Operator operator = SessionHolder.get(Operator.class);
		if(null==operator) {
			return "";
		}
		return operator.getSpId();
	}
	
	public static Operator getOperator() {
		Operator operator = SessionHolder.get(Operator.class);
		return operator;
	}
}
