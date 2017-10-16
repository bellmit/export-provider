package cn.com.flaginfo.platform.export.model.excel.func;

public class GoodsTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if("0".equals(value)){
			return "流量";
		}
		return "";
	}
}
