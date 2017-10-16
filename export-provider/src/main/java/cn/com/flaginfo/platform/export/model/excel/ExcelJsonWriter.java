package cn.com.flaginfo.platform.export.model.excel;

import java.util.Map;

import cn.com.flaginfo.platform.common.util.SystemMessage;
import cn.com.flaginfo.platform.export.client.JsonClient;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/**
 * 导出任务
 * @author chengbin.luo
 *
 */
public class ExcelJsonWriter extends ExcelWriter{
	private ExportTask task;
	private Policy policy;

	public ExcelJsonWriter(ExportTask task, Policy policy) {
		super(task, policy);
		this.task = task;
		this.policy = policy;
	}

	@Override
	public Map<String,Object> getPageData(Map<String,Object> pageInfo){
		Map<String,Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		JsonClient client = new JsonClient();
		String url = SystemMessage.getString("cmc_report_service_url") + policy.getApiUrl();
		Map<String,Object> responseMap = client.postJsonReturnMap(url,requestParams);
		return responseMap;
	}
}
