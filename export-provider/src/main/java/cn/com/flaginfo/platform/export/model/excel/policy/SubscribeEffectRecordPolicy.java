package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PackageProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PeriodFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SubscribStatusFunction;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class SubscribeEffectRecordPolicy extends Policy{
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SubscribeEffectRecordPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_effect_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称","省份","地市","计费号码","套餐名称", "套餐类型","业务类型",
				"生效时间","失效时间","生效状态","备注","是否失效","套餐金额","短信套内条数","短信剩余套内条数","e信套内条数","e信剩余套内条数",
				"彩信套内条数","彩信剩余套内条数","彩e信套内条数","彩e信剩余套内条数",
				"物联网卡月租套内张数","物联网卡月租剩余套内张数","短信上行套内条数","短信上行剩余套内条数",
				"三要素验真套内次数","三要素验真剩余套内次数","视频流量套内流量","视频流量剩余套内流量"};
		keys = new String[]{ "spCode", "spName","platform","city","feeMdn", "packageName","period","productId",
				"effectiveTime","dropTime","status","remark","isDrop","fee","dxInsideNumber","dxInsideLeft",
				"exInsideNumber","exInsideLeft","cxInsideNumber","cxInsideLeft","cexInsideNumber","cexInsideLeft",
				"wlwInsideNumber","wlwInsideLeft","dxsxInsideNumber","dxsxInsideLeft",
				"sysInsideNumber","sysInsideLeft","spllInsideNumber","spllInsideLeft",};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));

		funcMap.put("period", new PeriodFunction());
		funcMap.put("productId", new PackageProductIdFunction());
		funcMap.put("status", new SubscribStatusFunction("status"));
		funcMap.put("isDrop", new SubscribStatusFunction("isDrop"));
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("subscribeEffectRecordTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
