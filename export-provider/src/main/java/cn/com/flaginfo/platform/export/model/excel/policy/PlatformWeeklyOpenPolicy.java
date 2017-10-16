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

public class PlatformWeeklyOpenPolicy extends Policy {
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	
	public PlatformWeeklyOpenPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_platform_open_weekly");
		this.setApiVersion("1.0");
		titles = new String[]{ "区域",  "省份", "总开户数(户)",
				"本省开户数(户)","自签开户数(户)","W0(1)","W1(2-8)","W2(9-15)","W3(16-22)",
				"W4(23-29)","W5(30-31)"};
		
		keys = new String[]{ "regionId", "platform", "totalOpenNum","unicomOpenNum",
				"selfSignedOpenNum","w0OpenNum","w1OpenNum","w2OpenNum","w3OpenNum",
				"w4OpenNum","w5OpenNum"};
		Map<String,String> platformMap = manager.getRegionPfNameMap(task.getGlobalApiOld());
		Map<String,String> regionMap = manager.getRegionMap(task.getGlobalApiOld());
		
		
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("regionId", new RegionFunction(regionMap));
		
		initColumns(titles, keys, funcMap);
		
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("platformWeeklyOpen"));

	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		IncomeApi incomeApi = task.getIncomeApi();
		Map<String, Object> response = incomeApi.platformWeeklyOpen(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
