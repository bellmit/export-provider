package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.BillSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

public class SmsBillPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SmsBillPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sms_info");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "所属省份", "地市名称","业务类型 ","来源平台","账户类型","付费类型",
				"计费号码","帜讯销售姓名","开户时间","套餐金额","超套餐金额","消费金额合计","套内调账","套外调账","调账金额","调后套餐费","调后超套餐费",
				"应收金额","联通计费数量","移动计费数量","电信计费数量","合计计费数量","联通赠送条数","移动赠送条数","电信赠送条数",
				"赠送数量","调整联通计费数量 ","调整移动计费数量","调整电信计费数量","调整计费数量","调整后联通计费数量","调整后移动计费数量",
				"调整后电信计费数量","调整后计费数量"};
		
		keys = new String[]{ "spCode", "spName", "platform", "city","productId","source","accountType","feeType",
				"feeMdn","customerManagerName","openTime","packageFee","outPackageFee","totalFee","packageFeeAddition",
				"outPackageFeeAddition","totalFeeAddition","packageRealFee","outPackageRealFee","totalRealFee",
				"unicomFeeNum","mobileFeeNum","telcomFeeNum","totalFeeNum","unicomGiftNum","mobileGiftNum",
				"telcomGiftNum","totalGiftNum","unicomAdditionNum","mobileAdditionNum","telcomAdditionNum",
				"totalAdditionNum","unicomRealFeeNum","mobileRealFeeNum","telcomRealFeeNum","totalRealFeeNum"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		funcMap.put("source", new BillSourceFunction());

		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("SmsBillTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.smsBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
