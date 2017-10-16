package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CountryFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpAccountTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;

public class IntlSmsPlatformPolicy extends Policy {
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public  IntlSmsPlatformPolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_intl_sms_bill");
		this.setApiVersion("1.0");
		titles = new String[]{"省份","业务类型","账户类型","付费类型","计费条数","消费金额",
				"合计应收金额（元）"};
		
		keys = new String[]{"platform","productId","accountType","feeType","feeNum",
				"amount","should"};
		
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		
		Map<String,String> countryMap = manager.getCountryNameMap(task.getGlobalApiOld());
		
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("countryId", new CountryFunction(countryMap));
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("accountType", new SpAccountTypeFunction());
		funcMap.put("feeType", new SpFeeTypeFunction());
		initColumns(titles, keys,funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("IntlSmsPlatformBill"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.intlSmsBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
