package cn.com.flaginfo.platform.export.model.excel.func;

public class SmsAdjustRecordSignFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("-1".equals(value)){
			return "调减";
		}else if("1".equals(value)){
			return "调增";
		}
		return "-";
	}

}
