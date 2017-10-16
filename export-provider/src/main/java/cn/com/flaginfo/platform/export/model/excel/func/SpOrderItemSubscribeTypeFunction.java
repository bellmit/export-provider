package cn.com.flaginfo.platform.export.model.excel.func;

public class SpOrderItemSubscribeTypeFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)) {
			return "订购";
		}else if("2".equals(value)) {
			return "退订";
		}else if("3".equals(value)){
			return "取消";
		}
		return null;
	}

}
