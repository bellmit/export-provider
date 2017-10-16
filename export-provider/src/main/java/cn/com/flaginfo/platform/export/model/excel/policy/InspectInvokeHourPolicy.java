package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class InspectInvokeHourPolicy extends Policy {

	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public InspectInvokeHourPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("inspect_report_hour_count");
		this.setApiVersion("1.0");
		titles = new String[]{ "时间", "调用次数"};
		keys = new String[]{"completeTime", "count"};
		initColumns(titles, keys);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("inspectInvokeHourTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
