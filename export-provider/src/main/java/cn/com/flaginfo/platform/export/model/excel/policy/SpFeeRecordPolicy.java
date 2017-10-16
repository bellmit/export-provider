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
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;


public class SpFeeRecordPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpFeeRecordPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_fee_deduct_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "所属省份", "企业名称", "企业编号", "套餐费", "发送费用 ","套餐补足","系统补足",
				"手工补扣","公免金额","发起扣费合计","待扣金额","成功金额","失败金额","无结果金额"};
		keys = new String[]{ "platform", "spName", "spCode", "deductPackage","deductSend","deductPackageAdd","deductSystemAdd",
				"deductHandAdd","publicSum","deductTotal","waitDeduct","deductSucc","deductFail","noResult"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spFeeRecordTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		FeeRecordApi feeRecordApi = task.getFeeRecordApi();
		Map<String, Object> response = feeRecordApi.listSpFeeRecord(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
