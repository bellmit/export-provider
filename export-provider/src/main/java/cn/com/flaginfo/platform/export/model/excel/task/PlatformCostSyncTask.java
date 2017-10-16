package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.PlatformCostSyncPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

public class PlatformCostSyncTask extends ExportTask{

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		//设置导出策略
		Policy policy = new PlatformCostSyncPolicy(this);
		//设置导出writer
		ExcelWriter writer = new ExcelWriter(this,policy);
		writer.setResult(result);
		//执行导出
		excuteTask(writer, policy, this, result);
	}
}
