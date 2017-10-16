package cn.com.flaginfo.platform.export.model.excel.func;

public class AccountStatusFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		//状态 0正常 2停机 3冻结 4关闭
		if("0".equals(value)) {
			return "正常";
		}else if("2".equals(value)) {
			return "停机";
		}else if("3".equals(value)) {
			return "冻结";
		}else if("4".equals(value)) {
			return "关闭";
		}else {
			return "";
		}
	}

}
