/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.NewBusinessPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/**
 * 新业务对账单
 * 
 * @author weiping.gong
 * @date 2017年7月14日 下午3:18:43
 * @version v1.0
 *
 */
public class NewBusinessTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		// 设置导出策略
		Policy policy = new NewBusinessPolicy(this);
		// 设置导出writer
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		// 执行导出
		excuteTask(writer, policy, this, result);
	}

}
