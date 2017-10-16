package cn.com.flaginfo.platform.export.model.excel.task;

import cn.com.flaginfo.platform.common.util.StringUtil;
import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.ContactsPolicy;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

public class ContactsTask extends ExportTask{

	@Override
	public void execute() {
		ExportResult result = new ExportResult();
		if(StringUtil.isNullOrEmptyInMap(this.getRequestParam(), "spId","contactsId")){
			result.setRemark("缺少必传参数");
			updateTaskFail(result);
			return;
		}
		//设置导出策略
		Policy policy = new ContactsPolicy(this);
		//设置导出writer
		ExcelWriter writer = new ExcelWriter(this,policy);
		writer.setResult(result);
		//执行导出
		excuteTask(writer, policy, this, result);
	}
}
