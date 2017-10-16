package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PaymentStatusTypeFunction;

public class WechartPayListPolicy extends Policy {

	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public WechartPayListPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("life_pay_order_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "水表号", "地址", "支付日期", "支付金额","支付状态","微信昵称"};
		keys = new String[]{"bizCode", "bizAddress", "transTime", "transAmt", "status","payUserName"};
		funcMap.put("status", new PaymentStatusTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("wechartPayListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
