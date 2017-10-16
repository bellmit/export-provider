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
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;

public class PlatformSmsBillByProductPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public PlatformSmsBillByProductPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_platform_sms_product");
		this.setApiVersion("1.0");
		titles = new String[]{ "省份", "业务类型","套餐费", "超套餐费","消费金额合计", "套内调账","超套调账",
				"调账金额","调后套餐费","调后超套餐费","应收金额","联通计费条数","移动计费条数","电信计费条数","合计计费数量",
				"联通赠送使用数量","移动赠送使用数量","电信赠送使用数量","赠送使用数量","联通调整计费数量","移动调整计费数量",
				"电信调整计费数量","调整计费数量","联通调整后计费数量","移动调整后计费数量","电信调整后计费数量","调整后计费数量"};
		keys = new String[]{ "platform","productId", "packageFee", "outPackageFee", "totalFee","packageFeeAddition","outPackageFeeAddtion",
				"totalFeeAddition","packageRealFee","outPackageRealFee","totalRealFee","unicomFeeNum",
				"mobileFeeNum","telcomFeeNum","totalFeeNum","unicomGiftNum","mobileGiftNum","telcomGiftNum",
				"totalGiftNum","unicomAdditionNum","mobileAdditionNum","telcomAdditionNum","totalAdditionNum",
				"unicomRealFeeNum","mobileRealFeeNum","telcomRealFeeNum","totalRealFeeNum"
				};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("productId", new ProductIdFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("PlatformSmsBillByProduct"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.platformSmsBillByProduct(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
