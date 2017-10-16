package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.H2Api;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpStatusFunction;

public class BalanceLogPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public BalanceLogPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_h2_balance_log");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "企业状态", "计费类型","计费号码","实时话费","计费号码余额","未销话费","预存话费","账户余额","计费号码状态","记录时间"};
		keys = new String[]{"spCode","spName","spStatus","spFeeType","feeMdn","realfee","balance","owingFee","depositFee","amount","serviceStateDesc","createTime"};
		
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("spFeeType", new SpFeeTypeFunction());
		funcMap.put("spStatus", new SpStatusFunction());

		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("billingBalanceLog"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		H2Api h2Api = task.getH2Api();
		Map<String, Object> response = h2Api.listBalance(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
