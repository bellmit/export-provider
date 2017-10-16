package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.MessageDetailPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

public class MessageDetailTask extends ExportTask{

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		if(StringUtil.isNullOrEmptyInMap(this.getRequestParam(), "spId","startTime","endTime")){
			result.setRemark("缺少必传参数");
			updateTaskFail(result);
			return;
		}
		//设置导出策略
		Policy policy = new MessageDetailPolicy(this);
		//设置导出writer
		ExcelWriter writer = new ExcelWriter(this,policy);
		writer.setResult(result);
		//执行导出
		excuteTask(writer, policy, this, result);
	}
}
