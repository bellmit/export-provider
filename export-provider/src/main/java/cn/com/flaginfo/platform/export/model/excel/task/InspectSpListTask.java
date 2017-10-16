package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.InspectSpListPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/**
 * 企业管理统计列表导出
 * @author tao.he
 * @date 2017年07月24日 
 * @version v1.0
 *
 */
public class InspectSpListTask extends ExportTask{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		//设置导出策略
		Policy policy = new InspectSpListPolicy(this);
		//设置导出writer
		ExcelWriter writer = new ExcelWriter(this,policy);
		writer.setResult(result);
		//执行导出
		excuteTask(writer, policy, this, result);
	}
}
