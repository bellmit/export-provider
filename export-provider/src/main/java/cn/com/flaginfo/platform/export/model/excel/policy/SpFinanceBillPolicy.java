package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelDoc;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class SpFinanceBillPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Integer[] types = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpFinanceBillPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_total_bill");
		this.setApiVersion("1.1");
		titles = new String[]{ "企业编号", "企业名称", "所属省份", "地市名称","账户类型","付费类型",
				"计费号码","客户经理","一类收入（元）","二类收入（元）","三类收入（元）","四类收入（元）","应收金额（元）"};
		keys = new String[]{ "spCode", "spName", "platform", "city","accountType","feeType",
				"feeMdn","customerManagerName","ssms","emms","awms","flow","shold"};
		types = new Integer[]{ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,
				ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_STRING,ExcelDoc.DATA_TYPE_NUMBER,ExcelDoc.DATA_TYPE_NUMBER,ExcelDoc.DATA_TYPE_NUMBER,ExcelDoc.DATA_TYPE_NUMBER,ExcelDoc.DATA_TYPE_NUMBER};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys,types, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spFinanceBillTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.totalBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
