package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskNetworkMdnFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskSendSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskSendTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskStatusFunction;

public class CmcTaskListPolicy extends Policy {

	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public CmcTaskListPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("cmc_task_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "任务编号","企业编号","企业名称","发送内容","业务类型","任务状态","任务类型","总人数","总条数","成功条数","失败条数","联通/移动/电信条数","优先级","提交时间","完成时间"};
		keys = new String[]{"taskId","spCode","spName","text","productId","status","sendType","allMdn","allNum","succNum","failNum","networkNum","sendSource","createTime","endTime"};
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("status", new TaskStatusFunction());
		funcMap.put("sendType", new TaskSendTypeFunction());
		funcMap.put("sendSource", new TaskSendSourceFunction());
		funcMap.put("networkNum", new TaskNetworkMdnFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("cmcTaskListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
