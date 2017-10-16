package cn.com.flaginfo.platform.export.model.excel.func;

public class SpAcceptanceTypeFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)) {
			return "开户";
		}else if("1".equals(value)) {
			return "销户";
		}
		return null;
	}

}
