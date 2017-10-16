package cn.com.flaginfo.platform.export.model.excel.func;

public class SmsAdjustRecordFeeTypeFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)){
			return "套餐费";
		}else if("1".equals(value)){
			return "超套餐费";
		}
		return "-";
	}

}
