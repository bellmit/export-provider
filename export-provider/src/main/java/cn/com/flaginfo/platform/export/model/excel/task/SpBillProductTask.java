/**
 * 
 */
package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;
import cn.com.flaginfo.platform.export.model.excel.policy.SpBillProductPolicy;

/**
 * 企业总对账单 按照业务类型
 * 
 * @author weiping.gong
 * @date 2017年7月18日 下午1:45:04
 * @version v1.0
 *
 */
public class SpBillProductTask extends ExportTask {

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		Policy policy = new SpBillProductPolicy(this);
		ExcelWriter writer = new ExcelWriter(this, policy);
		writer.setResult(result);
		excuteTask(writer, policy, this, result);
	}

}
