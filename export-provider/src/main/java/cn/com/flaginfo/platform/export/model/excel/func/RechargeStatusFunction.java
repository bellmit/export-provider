package cn.com.flaginfo.platform.export.model.excel.func;

public class RechargeStatusFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "充值成功";
		}
		if("1".equals(value)){
			return "充值失败";
		}
		return "-";
	}
}
