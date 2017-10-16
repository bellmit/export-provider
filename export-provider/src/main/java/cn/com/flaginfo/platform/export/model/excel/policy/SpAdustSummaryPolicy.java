package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.AdjustBillApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;

public class SpAdustSummaryPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpAdustSummaryPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_adjust_summary");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "所属省份","地市名称","总调账金额","短信调账总金额","短信调账套餐费","短信调账超套餐费",
				"短信调账计费条数","联通短信调账计费条数","移动短信调账计费条数","电信短信调账计费条数","e信调账总金额","e信调账套餐费","e信调账超套餐费",
				"e信调账计费条数","联通e信调账计费条数","移动e信调账计费条数","电信e信调账计费条数","彩信调账总金额","彩信调账套餐费","彩信调账超套餐费",
				"彩信调账计费条数","联通彩信调账计费条数","移动彩信调账计费条数","电信彩信调账计费条数","彩e信调账总金额","彩e信调账套餐费","彩e信调账超套餐费",
				"彩e信调账计费条数","联通彩e信调账计费条数","移动彩e信调账计费条数","电信彩e信调账计费条数","流量调账总金额","联通流量调账金额","移动流量调账金额",
				"电信流量调账金额","短信上行调帐总金额","短信上行调帐套餐费","短信上行调帐超套餐费","短信上行调帐计费数量","物联网卡月租调帐总金额","物联网卡月租调帐套餐费",
				"物联网卡月租调帐超套餐费","物联网卡月租调帐计费数量","三要素验真调帐总金额","三要素验真调帐套餐费","三要素验真调帐超套餐费","三要素验真调帐计费数量",
				"视频流量调帐总金额","视频流量调帐套餐费","视频流量调帐超套餐费","视频流量调帐计费数量"};
		keys = new String[]{ "spCode", "spName", "platform","city", "adjustFee","dxAdjustFee","dxAdjustPackageFee",
				"dxAdjustOutFee","dxAdjustNum","dxUnicomAdjustNum","dxMobileAdjustNum","dxTelecomAdjustNum",
				"exAdjustFee","exAdjustPackageFee","exAdjustOutFee","exAdjustNum","exUnicomAdjustNum","exMobileAdjustNum","exTelecomAdjustNum",
				"cxAdjustFee","cxAdjustPackageFee",
				"cxAdjustOutFee","cxAdjustNum","dcUnicomAdjustNum","cxMobileAdjustNum","cxTelecomAdjustNum",
				"cexAdjustFee","cexAdjustPackageFee",
				"cexAdjustOutFee","cexAdjustNum","cexUnicomAdjustNum","cexMobileAdjustNum","cexTelecomAdjustNum",
				"llAdjustFee","llUnicomAdjustFee","llMobileAdjustFee","llTelecomAdjustFee","dxsxAdjustFee","dxsxAdjustPackageFee",
				"dxsxAdjustOutFee","dxsxAdjustNum","wlwAdjustFee","wlwAdjustPackageFee","wlwAdjustOutFee","wlwAdjustNum",
				"sysAdjustFee","sysAdjustPackageFee","sysAdjustOutFee","sysAdjustNum",
				"spllAdjustFee","spllAdjustPackageFee","spllAdjustOutFee","spllAdjustNum"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("spAdustSummaryTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		AdjustBillApi adjustBillApi = task.getAdjustBillApi();
		Map<String, Object> response = adjustBillApi.spAdjustList(JsonHelper.parseToJson(requestParams));
		return response;
	}

}
