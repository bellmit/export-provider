package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.PlatformWeeklyOpenPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

public class PlatformWeeklyOpenTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		Policy policy = new PlatformWeeklyOpenPolicy(this);
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		excuteTask(writer, policy, this, result);
	}

}
