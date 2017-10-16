package cn.com.flaginfo.platform.export.model.excel.func;

public class SpAcceptanceListPeriodFunction implements Function<Object,Object> {
	
	
	//阶段，0：草稿；1：待审核；2：审核不通过；3：开户失败；4：待资质审核，5：资质审核不通过；6：等待业务配置；7：开户成功；9：删除
	@Override
	public String apply(Object value, Object param) {
		if("0".equals(value)) {
			return "草稿";
		}else if("1".equals(value)) {
			return "待审核";
		}else if("2".equals(value)) {
			return "审核不通过";
		}else if("3".equals(value)) {
			return "开户失败";
		}else if("41".equals(value)) {
			return "等待上传资质";
		}else if("42".equals(value)) {
			return "等待资质审核";
		}else if("5".equals(value)) {
			return "资质审核不通过";
		}else if("6".equals(value)) {
			return "等待业务配置";
		}else if("7".equals(value)) {
			return "开户成功";
		}else if("9".equals(value)) {
			return "删除";
		}
		return null;
	}

}
