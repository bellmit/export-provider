package cn.com.flaginfo.platform.export.model.excel.func;

public class NoauditMdnStatusFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "-";
		}
		if("0".equals(value)){
			return "待审核";
		}
		if("1".equals(value)){
			return "审核通过";
		}
		if("2".equals(value)){
			return "审核不通过";
		}
		return "";
	}

}
