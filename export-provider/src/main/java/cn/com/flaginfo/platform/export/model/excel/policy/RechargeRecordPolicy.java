package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.RechargeApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.RechargeStatusFunction;
import cn.com.flaginfo.platform.export.model.excel.func.RechargeTypeFunction;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class RechargeRecordPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public RechargeRecordPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("bill_recharge_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "所属省份", "金额","状态","计费号码",
				"操作类型","操作员","操作时间","操作原因"};
		keys = new String[]{ "spCode", "spName", "platform", "amount","status","feeMdn",
				"type","operator","time","remark"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("type", new RechargeTypeFunction());
		funcMap.put("status", new RechargeStatusFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("rechargeRecordTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		RechargeApi rechargeApi = task.getRechargeApi();
		Map<String, Object> response = rechargeApi.rechargeRcord(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
