package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.AdjustBillApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SmsAdjustRecordSignFunction;

public class FlowAdjustReocrdPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public FlowAdjustReocrdPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_flow_adjust_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "调账类型", "业务类型",
				"调账金额","联通调账金额","移动调账金额","电信调账金额","联通全国漫游折扣","联通本地漫游折扣",
				"联通本地非漫游折扣","移动全国漫游折扣","移动本地漫游折扣","移动本地非漫游折扣",
				"电信全国漫游折扣","电信本地漫游折扣","电信本地非漫游折扣","账务日期","操作时间","操作人员","操作原因"};
		keys = new String[]{ "spCode", "spName", "sign", "productId",
				"fee","unicomFee","mobileFee","telcomFee","unicomNation",
				"unicomLocalRoam","unicomLocalNotRoam","mobileNation","mobileLocalRoam",
				"telecomNation","telecomLocalRoam","telecomLocalNotRoam","mobileLocalNotRoam","feeTime","createTime","operator","remark"};
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("sign", new SmsAdjustRecordSignFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("flowAdjustRecordTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		AdjustBillApi adjustBillApi = task.getAdjustBillApi();
		Map<String, Object> response = adjustBillApi.flowList(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
