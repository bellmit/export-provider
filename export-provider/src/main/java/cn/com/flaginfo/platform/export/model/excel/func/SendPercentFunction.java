package cn.com.flaginfo.platform.export.model.excel.func;

import java.text.NumberFormat;

import cn.com.flaginfo.platform.common.util.StringUtil;

/**
 * ClassName: SendPercentFunction
 * Created by Christian-Lau on 2017/5/4.
 * Function:
 *
 * @version v1.0
 */
public class SendPercentFunction implements Function<Object,Object> {
    @Override
    public String apply(Object value, Object param) {
        if(StringUtil.isNullOrEmpty(value)){
            return "";
        }
        
        if("--".equals(value)){
            return "--";
        }
        double sccp = Double.parseDouble((String) value);
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(0);
        return nt.format(sccp);
    }
}
