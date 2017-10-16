package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.FeeRecordApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;

public class SpCostSyncPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpCostSyncPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_cost_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "所属省份",
				"本网应同步","本网公免同步","本网发起合计","本网同步成功","本网同步失败","本网同步无结果",
				"异网应同步","异网公免同步","异网发起合计","异网同步成功","异网同步失败","异网同步无结果"};
		keys = new String[]{ "spCode", "spName", "platform",
				"factUnicomTotal","unicomPublicSum","unicomTotal","unicomSucc","unicomFail","unicomNoResult",
				"factOtherTotal","otherPublicSum","otherTotal","otherSucc","otherFail","otherNoResult"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spCostSyncTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		FeeRecordApi feeRecordApi = task.getFeeRecordApi();
		Map<String, Object> response = feeRecordApi.listSpCostRecord(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
