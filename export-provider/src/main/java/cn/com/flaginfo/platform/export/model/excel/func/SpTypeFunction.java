package cn.com.flaginfo.platform.export.model.excel.func;

public class SpTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if("0".equals(value)){
			return "个人";
		} else if("1".equals(value)){
			return "企业";
		} else {
			return "";
		}
	}
}
