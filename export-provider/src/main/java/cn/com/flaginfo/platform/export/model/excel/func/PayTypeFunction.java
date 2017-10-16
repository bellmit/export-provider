package cn.com.flaginfo.platform.export.model.excel.func;

public class PayTypeFunction implements Function<Object, Object> {

	@Override
	public String apply(Object value,Object param) {
		if("3".equals(value)) {
			return "e信卡";
		}
		return "账户余额";
	}

}
