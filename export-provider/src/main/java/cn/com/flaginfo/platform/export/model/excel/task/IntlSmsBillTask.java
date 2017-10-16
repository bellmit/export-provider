package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.IntlSmsBillPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

public class IntlSmsBillTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		Policy policy = new IntlSmsBillPolicy(this);
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		excuteTask(writer, policy, this, result);
	}

}
