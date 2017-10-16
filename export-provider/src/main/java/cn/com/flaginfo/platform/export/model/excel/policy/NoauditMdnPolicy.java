package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.channel.api.audit.NoAuditApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.NoauditMdnStatusFunction;
/**
 * 免审白名单导出策略
 * @author chengbin.luo
 *
 */
public class NoauditMdnPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public NoauditMdnPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("noaudit_mdn_list_detail");
		this.setApiVersion("1.0");
		titles = new String[]{ "手机号码","姓名","添加时间","操作员","状态","备注"};
		keys = new String[]{ "mdn","name","createTime","operator","status","remark"};
		funcMap.put("status", new NoauditMdnStatusFunction());
		initColumns(titles, keys,funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("NoauditMdnTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		NoAuditApi noAuditApi = task.getNoAuditApi();
		Map<String, Object> response = noAuditApi.noauditMdnListDetail(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
