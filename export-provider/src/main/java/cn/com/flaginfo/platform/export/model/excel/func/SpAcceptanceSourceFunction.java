package cn.com.flaginfo.platform.export.model.excel.func;

public class SpAcceptanceSourceFunction implements Function<Object,Object> {
	//0运营人员 1 其他 2代理商 3 联通
	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)) {
			return "运营人员";
		}else if("1".equals(value)) {
			return "其他";
		}else if("2".equals(value)) {
			return "代理商";
		}else if("3".equals(value)) {
			return "联通";
		}
		return null;
	}

}
