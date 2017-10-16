/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;
import cn.com.flaginfo.platform.export.model.excel.policy.ProvinceTotalStatementPolicy;

/**
 * 省份总对账单 按照业务类型
 * 
 * @author weiping.gong
 * @date 2017年7月14日 下午5:21:11
 * @version v1.0
 *
 */
public class ProvinceTotalStatementTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		// 设置导出策略
		Policy policy = new ProvinceTotalStatementPolicy(this);
		// 设置导出Writer
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		// 执行导出
		excuteTask(writer, policy, this, result);
	}

}
