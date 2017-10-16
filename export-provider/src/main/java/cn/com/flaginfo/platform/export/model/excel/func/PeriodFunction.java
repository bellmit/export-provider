package cn.com.flaginfo.platform.export.model.excel.func;

public class PeriodFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("M".equals(value)){
			return "包月";
		}
		if("Y".equals(value)){
			return "一次性";
		}
		return "-";
	}
}
