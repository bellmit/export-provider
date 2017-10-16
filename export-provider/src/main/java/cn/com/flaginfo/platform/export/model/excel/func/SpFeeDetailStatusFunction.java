package cn.com.flaginfo.platform.export.model.excel.func;

public class SpFeeDetailStatusFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "扣费中";
		}
		if("-1".equals(value)){
			return "调用扣费失败";
		}
		if("1".equals(value)){
			return "成功";
		}
		return "-";
	}
}
