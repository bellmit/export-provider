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

public class PlatformIncomeDetailPolicy extends Policy {
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public PlatformIncomeDetailPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_platform_income_detail");
		this.setApiVersion("1.0");
		titles = new String[]{ "日期", "所属区域", "省份", "总收入(元)",
				"联通收入","自签收入","代理商收入","行业收入","开户总数(户)","联通开户数",
				"自签开户数","销户总数(户)","联通销户数","自签销户数"};
		
		keys = new String[]{ "statDate", "regionId", "platform", "totalAmount","unicomAmount",
				"selfSignedAmount","agentAmount","industryAmount","totalOpenNum","unicomOpenNum",
				"selfSignedOpenNum","totalCancelNum","unicomCancelNum","selfSingedCancelNum"};
		Map<String,String> platformMap = manager.getRegionPfNameMap(task.getGlobalApiOld());
		Map<String,String> regionMap = manager.getRegionMap(task.getGlobalApiOld());
		
		
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("regionId", new RegionFunction(regionMap));
		
		initColumns(titles, keys, funcMap);
		
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("platformIncomeDetail"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		IncomeApi incomeApi = task.getIncomeApi();
		Map<String, Object> response = incomeApi.platformIncomeDetail(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
