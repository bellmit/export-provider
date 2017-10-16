package cn.com.flaginfo.platform.export.model.excel.func;

public class FlowStatusFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "成功";
		}
		if("1".equals(value)){
			return "失败";
		}
		if("2".equals(value)){
			return "超时";
		}
		return "-";
	}
}
