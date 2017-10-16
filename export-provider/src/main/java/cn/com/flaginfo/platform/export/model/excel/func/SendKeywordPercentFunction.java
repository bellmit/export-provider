package cn.com.flaginfo.platform.export.model.excel.func;

import java.text.NumberFormat;
import java.util.Map;

import cn.com.flaginfo.platform.common.util.StringUtil;

/**
 * ClassName: SendKeywordPercentFunction
 * Created by Christian-Lau on 2017/5/4.
 * Function:
 *
 * @version v1.0
 */
public class SendKeywordPercentFunction implements Function<Object,Object> {
    @Override
    public String apply(Object value, Object param) {
        Map<String,Object> map = (Map<String, Object>) param;
        double total = Double.parseDouble(StringUtil.isNullOrEmpty(map.get("total"))==true?"0": (String) map.get("total"));
        double allNum = Double.parseDouble(StringUtil.isNullOrEmpty(map.get("allNum"))==true?"0": (String) map.get("allNum"));
        if (allNum==0){
            return "--";
        }
        double percent = total/allNum;
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        return nt.format(percent);
    }
}
