package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 支付状态类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class PaymentStatusTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.ORDER_STATUS_FAIL.equals(value)){
            return "失败";
        }
        if(Constants.ORDER_STATUS_SUCCESS.equals(value)){
            return "成功";
        }
        if(Constants.ORDER_STATUS_NO.equals(value)){
            return "未支付";
        }
        if(Constants.ORDER_STATUS_PROCESS.equals(value)){
            return "支付中";
        }
        return "-";
    }
}
