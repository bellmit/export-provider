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
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

/**
 * 企业总对账单 按照业务类型
 * 
 * @author weiping.gong
 * @date 2017年7月18日 下午2:00:55
 * @version v1.0
 *
 */
public class SpBillProductPolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String, Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;

	public SpBillProductPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}

	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_bill_product");
		this.setApiVersion("1.0");
		titles = new String[] { "企业编号", "企业名称", "省份", "地市", "账户类型", "付费类型", "计费号码", "客户经理", "短信", "彩信", "长主题", "e信",
				"彩e信", "场景应用", "APP", "流量", "国际短信", "短信上行", "物联网卡月租", "三要素验真", "视频流量", "应收金额" };
		keys = new String[] { "spCode", "spName", "platform", "city", "accountType", "feeType", "feeMdn",
				"customerManagerName", "dxFee", "cxFee", "longFee", "exFee", "cexFee", "sceneFee", "appFee", "flowFee",
				"intlFee", "suFee", "ncmrFee", "tfttFee", "videoFee", "fee" };
		Map<String, String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String, String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spBillProductTask"));
		;
	}
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.spBillProduct(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
