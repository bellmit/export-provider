package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.flaginfo.channel.api.SmsTemplateApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.IndustryFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SmsTemplateTypeFunction;
import cn.com.flaginfo.platform.global.api.GlobalApiOld;
/**
 * 免审白名单导出策略
 * @author chengbin.luo
 *
 */
public class TemplateListPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	
	public TemplateListPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("sms_tpl_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "模板名称","模板类型","企业编号","企业名称","递交时间","模板内容","场景用途","适用行业","申请理由"};
		keys = new String[]{ "name","spId","spCode","spName","createTime","text","categoryName","industries","applyReason"};
		funcMap.put("spId", new SmsTemplateTypeFunction());
		funcMap.put("industries", new IndustryFunction(getIndustriesName()));
        
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("giftRecordTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SmsTemplateApi smsTemplateApi = task.getSmsTemplateApi();
		Map<String, Object> response = smsTemplateApi.smsTplList(JsonHelper.parseToJson(requestParams));
		return response;
	}
	
	/**
	 * 获取行业分类名称
	 * @return
	 */
	private Map<String, String> getIndustriesName() {
	       Map<String,String> industriesNameMap = new HashMap<String,String>();
	        
	        GlobalApiOld globalApi = task.getGlobalApiOld();
	        Map<String, Object> params =new HashMap<>();
	        params.put("mainType", "");
	        Map<String,Object> responseMap = null;
	        try {
	        	responseMap = globalApi.getIndustryTypes(JsonHelper.parseToJson(params));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(responseMap == null){
	            return new HashMap<String, String>();
	        }
	        List<Map<String,Object>> industriyList = (List<Map<String,Object>>)responseMap.get("list");
	        
	        for(Map<String, Object> m:industriyList){
	            industriesNameMap.put((String) m.get("parentId"), (String) m.get("parentName"));
	            List<Map<String, String>> childType = null;
	            if(m.containsKey("childTypes")) {
	                childType = (List<Map<String,String>>) m.get("childTypes");
	                for (Map<String, String> map : childType) {
	                    industriesNameMap.put((String) map.get("id"), (String) map.get("name"));
                    }
	            }
	        }
	        
	        return industriesNameMap;
	}
}
