package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class AppRegistStatisticsPolicy extends Policy {
	
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	
	public AppRegistStatisticsPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	@Override
	public void initPolicy() {
		this.setApiUrl("statistical_by_group_id");
		this.setApiVersion("1.0");
		titles = new String[]{"部门名称", "总人数", "已开通人数", "App已安装人数"};
		keys = new String[]{"name","total","appOpened","appInstalled"};
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("AppRegistStatisticsTask"));
	}
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
