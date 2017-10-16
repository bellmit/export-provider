package cn.com.flaginfo.platform.export.model.excel.func;

public class SpIntlStatusFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)) {
			return "开通";
		}else if("0".equals(value)) {
			return "未开通";
		}
		return "";
	}

}
