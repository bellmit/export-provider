package cn.com.flaginfo.platform.export.model.excel.func;

public class BlackMdnTypeFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		String spId = value.toString();
		if ( "0".equals(spId)) {
		    return "系统黑名单";
		}
		else {
		    return "企业黑名单";
		}
	}
}
