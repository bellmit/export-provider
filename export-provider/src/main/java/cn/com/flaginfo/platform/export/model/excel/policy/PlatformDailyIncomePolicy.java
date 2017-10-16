package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.IncomeApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.RegionFunction;

public class PlatformDailyIncomePolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	
	public PlatformDailyIncomePolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_platform_daily_income");
		this.setApiVersion("1.0");
		titles = new String[]{ "区域",  "省份", "目标收入(万元)",
				"当月总收入(万元)","当月本省收入(万元)","当月自签收入(万元)","当月新开户数(户)","当日总收入(元)","当日本省收入(元)","当日自签收入",
				"当日新开户数(户)","平台收入完成率"};
		
		keys = new String[]{ "regionId", "platform", "targetAmount","monthTotalAmount",
				"monthPlatformAmount","monthSelfSignedAmount","monthOpenNum","dayTotalAmount","dayPlatformAmount",
				"daySelfSignedAmount","dayOpenNum","complateRate"};
		Map<String,String> platformMap = manager.getRegionPfNameMap(task.getGlobalApiOld());
		Map<String,String> regionMap = manager.getRegionMap(task.getGlobalApiOld());
		
		
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("regionId", new RegionFunction(regionMap));
		
		initColumns(titles, keys, funcMap);
		
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("platformDailyIncome"));

	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		IncomeApi incomeApi = task.getIncomeApi();
		Map<String, Object> response = incomeApi.platformDailyIncome(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
