/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.IndustryIncomeApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;

/**
 * 行业收入导出策略
 * 
 * @author weiping.gong
 * @date 2016年11月7日 下午5:29:01
 * @version v1.0
 *
 */
public class IndustryIncomePolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String, Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;

	public IndustryIncomePolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}

	@Override
	public void initPolicy() {
		this.setApiUrl("industry_income_list");
		this.setApiVersion("1.0");
		titles = new String[] { "流水号", "省份", "地市", "业务类型", "金额(元)", "销售人员", "OA流水号", "账务日期", "操作时间", "操作人员", "备注" };
		keys = new String[] { "id", "platform", "city", "productId", "amount", "saleName", "flowOa", "feeTime",
				"createTime", "operator", "remark" };
		Map<String, String> platformMap = manager.getRegionPfNameMap(task.getGlobalApiOld());
		Map<String, String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys,funcMap);

		this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("industryIncomeTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		IndustryIncomeApi industryIncomeApi = task.getIndustryIncomeApi();
		Map<String, Object> response = industryIncomeApi.industryIncomeList(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
