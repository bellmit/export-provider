package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.GoodsTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PayTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PlatformFunction;

public class GoodsBuyRecordPolicy extends Policy {

	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public GoodsBuyRecordPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_goods_buy_record");
		this.setApiVersion("1.0");
		titles = new String[]{ "购买流水号","企业编号", "企业名称","省份","地市","商品类型","支付方式","购买费用(元)","优惠后费用(元)","数量","实际计费金额","折扣率","购买时间"};
		keys = new String[]{ "id","spCode", "spName","platform","city","goodsType","accountType", "fee","realFee","quantity","deductFee","discount","createTime"};
		Map<String,String> platformMap = manager.getPfNameMap(task.getGlobalApiOld());
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("platform", new PlatformFunction(platformMap));
		funcMap.put("goodsType", new GoodsTypeFunction());
		funcMap.put("accountType", new PayTypeFunction());

		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("GoodsBuyRecordTask"));
	}

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
