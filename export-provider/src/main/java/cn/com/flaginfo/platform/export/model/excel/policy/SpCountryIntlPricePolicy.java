package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.IntlPriceApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.ContinentFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SpIntlStatusFunction;

public class SpCountryIntlPricePolicy extends Policy {

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public  SpCountryIntlPricePolicy(ExportTask task) {
		this.task = task;
		initPolicy();
	}
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_price_list");
		this.setApiVersion("1.0");
		titles = new String[]{"国家代码","中文名称","英文名称","所属洲际","开通状态","默认单价","企业单价"};
		
		keys = new String[]{"code","cnName","enName","continent","status",
				"defaultPrice","price"};
		funcMap.put("status", new SpIntlStatusFunction());
		funcMap.put("continent", new ContinentFunction());
		
		initColumns(titles, keys,funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("SpCountryIntlPrice"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		IntlPriceApi intlPriceApi = task.getIntlPriceApi();
		Map<String, Object> response = intlPriceApi.getSpCountryPriceList(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
