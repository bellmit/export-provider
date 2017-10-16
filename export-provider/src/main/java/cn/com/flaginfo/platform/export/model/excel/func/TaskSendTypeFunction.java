package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

public class TaskSendTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if(Constants.CMC_MESSAGE_SEND_TYPE_IMMEDIATE.equals(value)){
			return "立即发送";
		} else if(Constants.CMC_MESSAGE_SEND_TYPE_BIRTHDAY_BOOK.equals(value)){
			return "生日预约";
		} else if(Constants.CMC_MESSAGE_SEND_TYPE_BOOK.equals(value)){
			return "预约发送";
		} else {
			return "";
		}
	}
}
