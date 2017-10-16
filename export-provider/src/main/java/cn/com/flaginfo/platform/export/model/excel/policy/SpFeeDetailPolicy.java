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
import cn.com.flaginfo.platform.export.model.excel.func.FeeSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.NetTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeDetailStatusFunction;


public class SpFeeDetailPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpFeeDetailPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_fee_detail");
		this.setApiVersion("1.0");
		titles = new String[]{ "扣费流水号", "扣费方式", "发起扣费", "扣费结果", "业务类型","扣费产品编号",
				"本网/异网","条数","单价","扣费时间","扣费结果返回时间"};
		keys = new String[]{ "flowNo", "source", "fee", "status","productId","productCode",
				"netType","quantity","price","createTime","updateTime"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("source", new FeeSourceFunction());
		funcMap.put("netType", new NetTypeFunction());
		funcMap.put("status", new SpFeeDetailStatusFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spFeeDetailTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		FeeRecordApi feeRecordApi = task.getFeeRecordApi();
		Map<String, Object> response = feeRecordApi.listSpFeeDetail(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
