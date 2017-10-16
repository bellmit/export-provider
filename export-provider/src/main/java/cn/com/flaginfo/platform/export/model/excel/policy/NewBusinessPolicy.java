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
import cn.com.flaginfo.platform.export.model.excel.func.PackageProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

/**
 * 新业务导出策略
 * 
 * @author weiping.gong
 * @date 2017年7月14日 下午3:20:29
 * @version v1.0
 *
 */
public class NewBusinessPolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String, Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;

	public NewBusinessPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}

	@Override
	public void initPolicy() {
		this.setApiUrl("internet_of_things");
		this.setApiVersion("1.0");
		titles = new String[] { "企业编号", "企业名称", "省份", "地市", "业务类型", "账户类型", "付费类型", "计费号码", "客户经理", "开户时间", "套餐金额",
				"超套餐金额", "消费金额合计", "套内调账金额", "套外调账金额", "调账金额", "调账后套餐金额", "调账后超套餐金额", "应收金额", "计费数量", "赠送数量", "调整计费数量",
				"调整后计费数量" };
		keys = new String[] { "spCode", "spName", "platform", "city", "productId", "accountType", "feeType",
				"feeMdn", "customerManagerName", "createTime", "packageFee", "outPackage", "consumptionAmount",
				"packageAdjustFee", "outPackageAdjustFee", "adjustFee", "afterPackageAdjustFee",
				"outAfterPackageAdjustFee", "shouldFee", "chargeNumber", "giftsBumber", "adjustNum",
				"afterAdjustNum" };
		Map<String, String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String, String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("productId", new PackageProductIdFunction());
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("newBusinessTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.internetOfThings(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
