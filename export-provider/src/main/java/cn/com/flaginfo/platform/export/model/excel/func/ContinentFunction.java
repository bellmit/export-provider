package cn.com.flaginfo.platform.export.model.excel.func;

public class ContinentFunction implements Function<Object,Object> {
	//国家/地区所在洲：1 亚洲；2 欧洲；3 北美洲；4 南美洲；5 非洲；6 大洋洲
	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)) {
			return "亚洲";
		}else if("2".equals(value)) {
			return "欧洲";
		}else if("3".equals(value)) {
			return "北美洲";
		}else if("4".equals(value)) {
			return " 南美洲";
		}else if("5".equals(value)) {
			return "非洲";
		}else if("6".equals(value)) {
			return "大洋洲";
		}
		return "";
	}

}
