package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

public class AcceptanceProcessTypeFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		return Constants.ACCEPTANCE_PROCESS_STATUS_MAP.get(value);
	}

}
