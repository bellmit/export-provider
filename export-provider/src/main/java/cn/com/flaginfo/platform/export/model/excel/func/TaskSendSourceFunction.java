package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

public class TaskSendSourceFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if(Constants.CMC_MESSAGE_SOURCE_PF.equals(value)){
			return "平台发送";
		} else if(Constants.CMC_MESSAGE_SOURCE_API.equals(value)){
			return "接口发送";
		} else {
			return "";
		}
	}
}
