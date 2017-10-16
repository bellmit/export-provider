package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SendResultFunction;
/**
 *  国际短信发送对象导出
 * @author chengbin.luo
 *
 */
public class IntlSendObjectPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	
	public IntlSendObjectPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("cmc_sendobject_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "手机号","所属国家","费用(元)","发送时间","结果"};
		keys = new String[]{ "mdn","countryName","price","createTime","recieveResult"};
		funcMap.put("recieveResult", new SendResultFunction());
        
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("intlSendObjectList"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
