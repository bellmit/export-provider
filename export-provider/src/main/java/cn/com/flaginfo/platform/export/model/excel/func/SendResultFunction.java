package cn.com.flaginfo.platform.export.model.excel.func;

/**
 * 发送结果
 *
 */
public class SendResultFunction implements Function<Object, Object>{

    @Override
    public String apply(Object value, Object param) {
        if("-1".equals(value)){
            return "等待结果";
        } else if("0".equals(value)){
            return "成功";
        }else{
        	return "失败";
        }
    }
}
