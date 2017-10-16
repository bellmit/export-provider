package cn.com.flaginfo.platform.export.model.excel.func;

public class GiftTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)){
			return "赠送";
		}
		if("2".equals(value)){
			return "扣减";
		}
		return "-";
	}
}
