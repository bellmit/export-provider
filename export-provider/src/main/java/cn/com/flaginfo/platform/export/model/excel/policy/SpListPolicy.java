package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpStatusFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpTypeFunction;
/**
 * 企业列表导出策略
 * @author chengbin.luo
 *
 */
public class SpListPolicy extends Policy{

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpListPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("get_sp_list");
		this.setApiVersion("1");
		titles = new String[]{ "企业编号", "企业名称", "省份","地市","开户日期","企业类型",
				"计费号码","销售","联系电话","企业状态"};
		keys = new String[]{ "spCode", "spName", "platform", "city","createTime",
				"spType","feeMdn","customerManagerName","contactPhone","status"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("spType", new SpTypeFunction());
		funcMap.put("status", new SpStatusFunction());
		initColumns(titles, keys,funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("appDetailTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
