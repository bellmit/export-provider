package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.SpAcceptanceApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.AcceptanceProcessTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SpAcceptanceSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAcceptanceTypeFunction;

public class AcceptanceOpeListPolicy extends Policy {

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public AcceptanceOpeListPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("accept_acceptance_op_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "受理单编号", "企业编号", "企业名称", "计费号码","申请方式","代理商名称",
				"申请人","受理类型","受理阶段","请求时间","备注"};
		keys = new String[]{ "acceptanceNo", "spCode", "spName", "feeMdn","source","ownedAgentId",
				"creator","type","processType","createTime","remark"};
		funcMap.put("processType", new AcceptanceProcessTypeFunction());
		funcMap.put("type", new SpAcceptanceTypeFunction());
		funcMap.put("source", new SpAcceptanceSourceFunction());

		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("acceptanceOpeListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpAcceptanceApi spAcceptanceApi = task.getSpAcceptanceApi();
		Map<String, Object> response = spAcceptanceApi.listAcceptanceOperationHis(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
