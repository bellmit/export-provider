package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Arrays;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 支付状态类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class InspectTaskCodeTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.INSPECT_TASK_CODE_CONSISTENT.equals(value)){
            return "一致";
        }else if (Arrays.asList(Constants.INSPECT_TASK_CODE_INCONSISTENT).contains(value)) {
        	return "不一致";
		}else {
			return "其他";
		}
    }
}
