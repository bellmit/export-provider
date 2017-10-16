package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;

public class IotCardExportPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public IotCardExportPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("get_iot_card_export_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "物联网卡号（必填）", "卡号说明","上行关键字","分组","手机号（必填）","姓名（必填）"};
		keys = new String[]{"iccId","remark","replyCode","groupName","mdn","name"};
		initColumns(titles, keys);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("iotCardTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
