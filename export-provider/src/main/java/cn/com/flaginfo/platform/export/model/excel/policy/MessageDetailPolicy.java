package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.ProductIdFunction;
import cn.com.flaginfo.platform.export.model.excel.func.TaskSendTypeFunction;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class MessageDetailPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public MessageDetailPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sms_detail_bill");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号","企业名称","任务编号", "发送账号", "信息主题", "计费日期","发送时间","发送人数",
				"发送类型","业务类型","发送条数","计费条数","联通条数","移动条数","电信条数",
				"国际条数","联通计费数","移动计费数","电信计费数","国际计费数"};
		keys = new String[]{ "spCode","spName","taskId", "userName", "text", "feeTime","createTime","allMdn",
				"sendType","productId","allNum","feeNum","unicomNum","mobileNum","telNum",
				"iNum","unicomFeeNum","mobileFeeNum","telFeeNum","iFeeNum"};
		funcMap.put("productId", new ProductIdFunction());
		funcMap.put("sendType", new TaskSendTypeFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("messageDetailTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpDetailBillApi spDetailBillApi = task.getSpDetailBillApi();
		Map<String, Object> response = spDetailBillApi.smsDetailBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
