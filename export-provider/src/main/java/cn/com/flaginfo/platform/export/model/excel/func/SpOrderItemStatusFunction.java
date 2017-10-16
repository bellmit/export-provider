package cn.com.flaginfo.platform.export.model.excel.func;

public class SpOrderItemStatusFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)) {
			return "未处理";
		}else if("1".equals(value)) {
			return "处理成功";
		}else if("2".equals(value)) {
			return "处理失败";
		}else if("3".equals(value)) {
			return "忽略";
		}
		return null;
	}

}
