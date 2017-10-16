package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

import cn.com.flaginfo.platform.common.util.NumberUtil;

public class CalcTotalFeeFunction implements Function<Object, Object>{

	private String type;
	
	public CalcTotalFeeFunction(String type){
		this.type = type;
	}
	
	@Override
	public String apply(Object value, Object param) {
		Double val = 0d;
		if("totalFee".equals(type)){
			try{
				String amount = (String)((Map)param).get("amount");
				String feeShould = (String)((Map)param).get("feeShould");
				val = NumberUtil.add(NumberUtil.parseDouble(amount), NumberUtil.parseDouble(feeShould));
			}catch(Exception e){
				
			}
		}
		if("totalFeeShould".equals(type)){
			try{
				String amount = (String)((Map)param).get("amount");
				String feeShould = (String)((Map)param).get("feeShould");
				String feeAddition = (String)((Map)param).get("feeAddition");
				val = NumberUtil.add(NumberUtil.parseDouble(amount), NumberUtil.parseDouble(feeShould));
				val = NumberUtil.add(val, NumberUtil.parseDouble(feeAddition));
			}catch(Exception e){
				
			}
		}
		return String.valueOf(val);
	}
}
