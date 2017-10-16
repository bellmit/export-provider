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
import cn.com.flaginfo.platform.export.model.excel.func.SmsAdjustRecordFeeTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SmsAdjustRecordSignFunction;

public class SmsAdjustRecordPolicy extends Policy{
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SmsAdjustRecordPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_adjust_bill_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "调账类型", "业务类型","调账内容","对应套餐",
				"调账金额","联通计费条数","移动计费条数","电信计费条数","账务日期","操作时间","操作人员","操作原因"};
		keys = new String[]{ "spCode", "spName", "sign", "productId","feeType","name",
				"fee","unicomNum","mobileNum","telcomNum","feeTime","createTime","operator","remark"};
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("sign", new SmsAdjustRecordSignFunction());
		funcMap.put("feeType", new SmsAdjustRecordFeeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("smsAdjustRecordTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		AdjustBillApi adjustBillApi = task.getAdjustBillApi();
		Map<String, Object> response = adjustBillApi.list(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
