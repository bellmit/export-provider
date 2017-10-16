package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.export.common.base.Constants;

public class SpAccountTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(StringUtil.isNullOrEmpty(value)){
			return "全部账户类型";
		}
		return Constants.SP_TYPE_MAP.get(value);
	}
}
