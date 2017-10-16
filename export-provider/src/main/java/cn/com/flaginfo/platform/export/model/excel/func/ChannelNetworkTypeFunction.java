package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 支付状态类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class ChannelNetworkTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.CHANNEL_NETWORK_CU.equals(value)){
            return "中国联通";
        }
        if(Constants.CHANNEL_NETWORK_CMCC.equals(value)){
            return "中国移动";
        }
        if(Constants.CHANNEL_NETWORK_CT.equals(value)){
            return "中国电信";
        }
        if(Constants.CHANNEL_NETWORK_ALL.equals(value)){
            return "全网通";
        }
        return "-";
    }
}
