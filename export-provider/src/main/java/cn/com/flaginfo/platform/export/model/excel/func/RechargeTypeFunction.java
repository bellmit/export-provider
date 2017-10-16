package cn.com.flaginfo.platform.export.model.excel.func;

public class RechargeTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "充值";
		}
		if("1".equals(value)){
			return "扣减";
		}
		return "-";
	}
}
