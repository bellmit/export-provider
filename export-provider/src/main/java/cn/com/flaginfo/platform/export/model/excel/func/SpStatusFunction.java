package cn.com.flaginfo.platform.export.model.excel.func;

public class SpStatusFunction implements Function<Object, Object>{

	@Override
	public String apply(Object value, Object param) {
		if(value==null){
			return "";
		}
		if("0".equals(value)){
			return "正常";
		} else if("1".equals(value)){
			return "销户";
		}else if("2".equals(value)){
			return "冻结";
		}else if("3".equals(value)){
			return "预开户";
		}else if("4".equals(value)){
			return "企业资质审核";
		}else if("5".equals(value)){
			return "企业资质审核失败";
		}
		else {
			return "";
		}
	}
}
