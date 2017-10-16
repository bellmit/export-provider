package cn.com.flaginfo.platform.export.model.excel.func;

/**
 * 三要素鉴真业务类型
 * @author tao.he
 *
 */
public class InspectBizTypeFunction implements Function<Object,Object> {

	@Override
	public String apply(Object value, Object param) {
		if("1".equals(value)){
			return "三要素验真";
		}
		return "-";
	}


}
