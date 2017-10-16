package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.channel.api.audit.TaskAuditApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.AuditResultFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class ChannelAuditTaskResultAcqPolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public ChannelAuditTaskResultAcqPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("channel_audit_task_result_acq");
		this.setApiVersion("1.0");
		titles = new String[]{"企业编号","企业名称",  "任务编号","发送人数","发送条数","提交时间","审核时间","审核结果"};
		keys = new String[]{"spCode","spName","taskId","mdn","num","taskTime","auditTime","result"};
		funcMap.put("result", new AuditResultFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("auditTaskResult"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		TaskAuditApi taskAuditApi = task.getTaskAuditApi();
		Map<String, Object> response = taskAuditApi.channelAuditTaskResultAcq(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
