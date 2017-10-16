package cn.com.flaginfo.platform.export.model.excel.func;

public class BillSourceFunction implements Function<Object, Object> {

	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)) {
			return "5.0";
		}else if("0".equals(value)) {
			return "6.0";
		}
		return "全部";
	}

}
