package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

public class NetTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if(Constants.NETWORK_TYPE_UNICOM.equals(value)){
			return "联通";
		}
		if(Constants.NETWORK_TYPE_MOBILE.equals(value)){
			return "移动";
		}
		if(Constants.NETWORK_TYPE_TELECOM.equals(value)){
			return "电信";
		}
		if(Constants.NETWORK_TYPE_INTL.equals(value)){
			return "国际";
		}
		return "";
	}
}
