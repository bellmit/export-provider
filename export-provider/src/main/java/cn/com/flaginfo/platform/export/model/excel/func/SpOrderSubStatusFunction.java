package cn.com.flaginfo.platform.export.model.excel.func;

public class SpOrderSubStatusFunction implements Function<Object,Object> {
	
	
	//0预约订购 1 正式订购 2订购结束 9订购失败
	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)) {
			return "预约订购";
		}else if("1".equals(value)){
			return "正式订购";
		}else if("2".equals(value)) {
			return "订购结束";
		}else if("3".equals(value)) {
			return "订购失败";
		}
		return null;
	}

}
