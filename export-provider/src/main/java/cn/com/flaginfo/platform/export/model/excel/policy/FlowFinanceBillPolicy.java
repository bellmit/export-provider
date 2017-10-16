package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
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

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class FlowFinanceBillPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public FlowFinanceBillPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_flow_bill");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "所属省份", "地市名称","账户类型","付费类型",
				"计费号码","客户经理","开户时间","e信卡购买应收金额","直充金额","联通直充金额","移动直充金额",
				"电信直充金额","直充调账金额","联通直充调账金额","移动直充调账金额","电信直充调账金额","直充应收金额",
				"联通直充应收金额","移动直充应收金额","电信直充应收金额","总应收金额","e信卡充值金额","联通e信卡充值金额",
				"移动e信卡充值金额","电信e信卡充值金额"};
		keys = new String[]{ "spCode", "spName", "platform", "city","accountType","feeType",
				"feeMdn","customerManagerName","createTime","cardFee","fee","unicomFee",
				"mobileFee","telcomFee","adjustFee","unicomAdjustFee","mobileAdjustFee","telcomAdjustFee",
				"realFee","unicomRealFee","mobileRealFee","telcomRealFee","totalRealFee",
				"cardRechargeFee","unicomCardRechargeFee","mobileCardRechargeFee","telcomCardRechargeFee"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("flowFinanceBillTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.flowBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
