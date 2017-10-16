package cn.com.flaginfo.platform.export.model.excel.func;

import cn.com.flaginfo.platform.export.common.base.Constants;

/**
 * 短信模板类型
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class SmsTemplateTypeFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if(Constants.SYS_SP_ID.equals(value)){
            return "平台短信模板";
        }
        return "企业短信模板";
    }
}
