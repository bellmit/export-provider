package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 支付状态类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class InspectTaskBizTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.INSPECT_TASK_TYPE_THREE.equals(value)){
            return "三要素验真";
        }else if (Constants.INSPECT_TASK_TYPE_TWO.equals(value)) {
        	return "二要素验真";
		}else {
			return "其他";
		}
    }
}
