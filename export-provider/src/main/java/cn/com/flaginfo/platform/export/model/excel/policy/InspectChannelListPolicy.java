package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.ChannelNetworkTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ChannelTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class InspectChannelListPolicy extends Policy {

	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public InspectChannelListPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("inspect_channel_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "通道编号", "业务类型", "通道名称", "运营商","成本单价"};
		keys = new String[]{"channelId", "channelType", "name", "network", "price"};
		funcMap.put("network", new ChannelNetworkTypeFunction());
		funcMap.put("channelType", new ChannelTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("inspectChannelListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
