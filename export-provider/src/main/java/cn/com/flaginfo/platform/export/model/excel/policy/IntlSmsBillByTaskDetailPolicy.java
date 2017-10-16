package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CountryFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class IntlSmsBillByTaskDetailPolicy extends Policy {
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public  IntlSmsBillByTaskDetailPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_intl_task_detail");
		this.setApiVersion("1.0");
		titles = new String[]{"企业编号","企业名称","任务编号","国家","单价","发送人数","发送条数","计费条数","消费金额","应收金额"};
		
		keys = new String[]{"spCode","spName","taskId","countryId","price","mdnNum","num",
				"feeNum","amount","should"};
		
		
		Map<String,String> countryMap = manager.getCountryNameMap(task.getGlobalApiOld());
		
		funcMap.put("countryId", new CountryFunction(countryMap));
		initColumns(titles, keys,funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("IntlSmsBillTaskDetail"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpDetailBillApi spDetailBillApi = task.getSpDetailBillApi();
		Map<String, Object> response = spDetailBillApi.intlSmsDetailBillByTaskDetail(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
