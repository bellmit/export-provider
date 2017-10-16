package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 支付状态类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class InspectTaskStatusTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.INSPECT_TASK_STATUS_FAIL.equals(value)){
            return "失败";
        }
        if(Constants.INSPECT_TASK_STATUS_UNDO.equals(value)){
            return "待验真";
        }
        if(Constants.INSPECT_TASK_STATUS_SUCCESS.equals(value)){
        	return "成功";
        }
        return "-";
    }
}
