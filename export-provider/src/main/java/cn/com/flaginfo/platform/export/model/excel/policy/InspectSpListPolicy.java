package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.InspectBizTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.ProvinceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpStatusFunction;

/**
 * 企业管理统计列表导出策略
 * @author tao.he
 *
 */
public class InspectSpListPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private String[] titles = null;
	private String[] keys = null;
	private ExportTask task;

	public InspectSpListPolicy(ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("report_sp_list");
		this.setApiVersion("1.0");
		titles = new String[]{ "企业编号", "企业名称", "省份","地市", "账户类型","开户时间","销售","订购业务","调用次数","消费金额","成本","收入"};
		keys = new String[]{"spCode", "spName", "platform","city", "accType", "accOpenTime","salesman","bizType","invokeCount","expense","cost","income"};
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		Map<String,String> platformMap =  manager.getPfNameMap(task.getGlobalApiOld());
		funcMap.put("platform", new ProvinceFunction(platformMap));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("accType", new SpStatusFunction());
		funcMap.put("bizType", new InspectBizTypeFunction());
		
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("InspectSpListTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
