package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.InspectTaskCodeTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.InspectTaskStatusTypeFunction;

public class InspectTaskDetail4EpPolicy extends Policy {

	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public InspectTaskDetail4EpPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("inspect_task_detail_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "任务编号", "手机号", "姓名", "身份证号","查询状态","查询结果","说明","查询时间"};
		keys = new String[]{"taskId", "mdn", "name", "idNo", "status","code","desc","createTime"};
		funcMap.put("status", new InspectTaskStatusTypeFunction());
		funcMap.put("code", new InspectTaskCodeTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("inspectTaskDetailTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
