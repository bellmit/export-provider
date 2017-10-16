package cn.com.flaginfo.platform.export.model.excel.func;

import java.util.Map;

/**
 * 行业分类
 *
 * @author guobin.liu
 * 2016年8月16日
 */
public class IndustryFunction implements Function<Object, Object>{

    private Map<String,String> industryMap;
    
    public IndustryFunction(Map<String,String> industryMap){
        this.industryMap = industryMap;
    }
    
    @Override
    public String apply(Object value, Object param) {
        if(value==null){
            return "";
        }
        String valueStr =value.toString();
        if(valueStr.indexOf(",")!=-1)  {
        	String[] valueArr = valueStr.split(",");
        	String returnValue = "";
        	for(String element:valueArr) {
        		returnValue=returnValue+industryMap.get(element)+" ";
        	}
        	return returnValue;
        }else {
        	return industryMap.get(value);
        }
    }
}
