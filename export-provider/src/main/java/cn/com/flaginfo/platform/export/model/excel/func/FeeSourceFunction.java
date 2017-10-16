package cn.com.flaginfo.platform.export.model.excel.func;

/**
 * 扣费方式
 * @author chengbin.luo
 */
public class FeeSourceFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "发送扣费";
		}
		if("1".equals(value)){
			return "系统补扣";
		}
		if("2".equals(value)){
			return "人工补扣";
		}
		if("3".equals(value)){
			return "订购";
		}
		if("4".equals(value)){
			return "服务费";
		}
		return "-";
	}
}
