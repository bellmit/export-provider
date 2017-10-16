/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.IndustryIncomePolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/**
 * 行业收入导出策略Task
 * 
 * @author weiping.gong
 * @date 2016年11月7日 下午5:25:57
 * @version v1.0
 *
 */
public class IndustryIncomeTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		// 设置导出策略
		Policy policy = new IndustryIncomePolicy(this);
		// 设置导出writer
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		// 执行导出
		excuteTask(writer, policy, this, result);
	}

}
