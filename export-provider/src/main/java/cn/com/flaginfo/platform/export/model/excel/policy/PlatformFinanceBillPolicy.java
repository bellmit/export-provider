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
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;


/**
 * 总对账单导出策略
 * 
 * @author chengbin.luo
 *
 */
public class PlatformFinanceBillPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Integer[] types = null;
	private Map<String, Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;

	public PlatformFinanceBillPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}

	@Override
	public void initPolicy() {
		this.setApiUrl("billing_nation_bill_platform");
		this.setApiVersion("1.0");
		String productId = (String) task.getRequestParam().get("productId");
		if (!"".equals(productId)) {
			titles = new String[] { "省份", "业务类型", "账户类型", "付费类型", "合计应收金额（元）" };
			keys = new String[] { "platform", "productId", "accountType", "feeType", "shold" };
			types = new Integer[] { ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_STRING,
					ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_NUMBER };
		} else {
			titles = new String[] { "省份", "业务类型", "账户类型", "付费类型", "一类收入（元）", "二类收入（元）", "三类收入（元）", "四类收入（元）",
					"应收金额（元）" };
			// keys = new String[]{ "platform", "productId", "accountType",
			// "feeType","ssms","emms","awms","flow","shold"};
			keys = new String[] { "platform", "productId", "accountType", "feeType", "ssms", "emms", "awms", "flow",
					"amount" };
			types = new Integer[] { ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_STRING,
					ExcelDoc.DATA_TYPE_STRING, ExcelDoc.DATA_TYPE_NUMBER, ExcelDoc.DATA_TYPE_NUMBER,
					ExcelDoc.DATA_TYPE_NUMBER, ExcelDoc.DATA_TYPE_NUMBER, ExcelDoc.DATA_TYPE_NUMBER };
		}
		Map<String, String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys, types, funcMap);
		this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("platformFinanceBillTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.financeNationBillPlatform(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
