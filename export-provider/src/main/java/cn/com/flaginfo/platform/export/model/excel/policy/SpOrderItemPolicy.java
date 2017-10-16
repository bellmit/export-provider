package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.PeriodFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpOrderItemStatusFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpOrderItemSubscribeTypeFunction;

/**
 * 订购受理单
 * @author admin
 *
 */
public class SpOrderItemPolicy extends Policy {

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpOrderItemPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("package_handling_list");
		this.setApiVersion("1.0");
		
		
		//0：未处理；1：处理成功；2：处理失败；3：忽略
		//1：订购；2：退订
		titles = new String[]{"受理单编号","企业编号","企业名称","计费号码","产品编号","套餐名称","套餐类型","受理单类型","受理单状态",
				"备注","生效时间","失效时间","请求时间","受理时间","受理人"};
		keys = new String[]{"id","spCode","spName","feeMdn","productCode","packageName","period","subscribeType","status",
				"remark","effectiveTime","expiredTime","createTime","processTime","operator"};
		
		funcMap.put("subscribeType", new SpOrderItemSubscribeTypeFunction());
		funcMap.put("status", new SpOrderItemStatusFunction());
		funcMap.put("period", new PeriodFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("SpOrderItemTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
