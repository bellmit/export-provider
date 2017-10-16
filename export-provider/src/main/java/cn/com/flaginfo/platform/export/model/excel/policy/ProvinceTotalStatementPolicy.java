/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PackageProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

/**
 * 省份总对账单 按照业务类型导出策略
 * 
 * @author weiping.gong
 * @date 2017年7月14日 下午5:25:08
 * @version v1.0
 *
 */
public class ProvinceTotalStatementPolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String, Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;

	public ProvinceTotalStatementPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}

	@Override
	public void initPolicy() {
		this.setApiUrl("province_total_statement");
		this.setApiVersion("1.0");
		titles = new String[] { "省份", "业务类型", "账户类型", "付费类型", "短信", "彩信", "长主题", "e信", "彩e信", "场景应用", "APP", "流量",
				"国际短信", "短信上行", "物联网卡月租", "三要素验真", "视频流量", "合计应收金额" };
		keys = new String[] { "platform", "productId", "accountType", "feeType", "message", "mmessage", "longsubject",
				"emessage", "memessage", "functionfee", "app", "flow", "imessage", "su", "ncmr", "tftt", "video",
				"provincesummary" };
		Map<String, String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("productId", new PackageProductIdFunction());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("provinceTotalStatementTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.provinceTotalStatement(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
