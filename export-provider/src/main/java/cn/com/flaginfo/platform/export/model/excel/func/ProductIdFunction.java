package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.export.common.base.Constants;

public class ProductIdFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(StringUtil.isNullOrEmpty(value) || "-2".equals(value)){
			return "全部";
		}
		return (String)Constants.PRODUCT_MAP.get(value);
	}
}
