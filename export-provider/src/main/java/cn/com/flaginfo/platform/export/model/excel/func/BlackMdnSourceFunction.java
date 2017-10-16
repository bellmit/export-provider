package cn.com.flaginfo.platform.export.model.excel.func;

public class BlackMdnSourceFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		String source = value.toString();
		if ( "0".equals(source)) {
		    return "运营平台";
		}
		else if("1".equals(source)){
		    return "企业";
		}
		else if("2".equals(source)){
		    return "用户";
		}
		return "";
	}
}
