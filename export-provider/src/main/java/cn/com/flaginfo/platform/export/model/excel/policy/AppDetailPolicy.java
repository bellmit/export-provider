package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.Map;

import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class AppDetailPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;
	
	public AppDetailPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_app_detail_bill");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号","企业名称","流水号", "场景应用名称", "消费金额", "开通时间"};
		keys = new String[]{ "spCode","spName","id", "packageName", "amount", "createTime"};
		initColumns(titles, keys);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("appDetailTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpDetailBillApi spDetailBillApi = task.getSpDetailBillApi();
		Map<String, Object> response = spDetailBillApi.appDetailBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
