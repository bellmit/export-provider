package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpAcceptanceApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SpAcceptanceListPeriodFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAcceptanceSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAcceptanceTypeFunction;

public class SpAcceptanceListPolicy extends Policy {

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpAcceptanceListPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("accept_acceptance_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "受理单编号", "企业编号", "企业名称", "计费号码","产品编号",
				"套餐名称","套餐类型","受理单类型","受理单状态","备注","生效时间","失效时间","请求时间","受理时间","受理人"};
		keys = new String[]{ "acceptanceNo", "spCode", "spName", "feeMdn","productId",
				"packageName","period","feeType","showStatus","remark","effectiveTime","expiredTime","createTime","processTime","operator"};
		funcMap.put("feeType", new SpAcceptanceTypeFunction());
		funcMap.put("source", new SpAcceptanceSourceFunction());
		funcMap.put("showStatus", new SpAcceptanceListPeriodFunction());

		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spAcceptanceListTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpAcceptanceApi spAcceptanceApi = task.getSpAcceptanceApi();
		Map<String, Object> response = spAcceptanceApi.listAcceptance(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
