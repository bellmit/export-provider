package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.channel.api.security.BlackListApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.BlackMdnSourceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.BlackMdnTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public class BlackMdnPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	public BlackMdnPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("channel_scy_black_list");
		this.setApiVersion("1.0");
		if("1".equals(task.getSource())){
			titles = new String[]{ "手机号码", "黑名单类型","创建者","添加时间"};
			keys = new String[]{"object","spId","creator","createTime"};
		} else {
			titles = new String[]{ "手机号码", "黑名单类型", "生效企业编号  ", "不生效企业编号","创建者","添加时间","添加方式"};
			keys = new String[]{"object","spId","spCode","invlidList","creator","createTime","source"};
		}
		funcMap.put("spId", new BlackMdnTypeFunction());
		funcMap.put("source", new BlackMdnSourceFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("blackMdnTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BlackListApi blackListApi = task.getBlackListApi();
		Map<String, Object> response = blackListApi.channelScyBlackList(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
