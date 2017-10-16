package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.export.common.base.Constants;

public class PackageProductIdFunction implements Function<Object, Object> {

	@Override
	public String apply(Object value, Object param) {
		if(StringUtil.isNullOrEmpty(value)){
			return "";
		}
		if("0".equals(value)) {
			return "混合";
		}
		return (String)Constants.PRODUCT_MAP.get(value);
	}

}
