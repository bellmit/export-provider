package cn.com.flaginfo.platform.export.model.excel.func;

public class AuditResultFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		//审核结果：0 不通过，1通过
		if("0".equals(value)) {
			return "不通过";
		}else if("1".equals(value)) {
			return "通过";
		}else {
			return "";
		}
	}

}
