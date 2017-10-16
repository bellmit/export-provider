package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PackageProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PeriodFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpOrderSubStatusFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SubscribStatusFunction;

public class SpOrderListPolicy extends Policy {
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpOrderListPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_package_ordering");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称","计费号码","套餐名称", "套餐类型","业务类型",
				"订购关系状态","开始时间","结束时间","当前生效状态"};
		keys = new String[]{ "spCode", "spName","feeMdn", "packageName","period","productId",
				"subscribeStatus","effectiveTime","expiredTime","status"};

		funcMap.put("period", new PeriodFunction());
		funcMap.put("productId", new PackageProductIdFunction());
		funcMap.put("status", new SubscribStatusFunction("status"));
		funcMap.put("subscribeStatus", new SpOrderSubStatusFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("SpOrderListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
