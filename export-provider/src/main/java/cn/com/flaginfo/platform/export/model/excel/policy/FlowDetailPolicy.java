package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.FlowStatusFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.GoodsTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.NetTypeFunction;

/**
 * 总对账单导出策略
 * @author chengbin.luo
 *
 */
public class FlowDetailPolicy extends Policy{

	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public FlowDetailPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_flow_detail_bill");
		this.setApiVersion("1.0");
		titles = new String[]{"企业编号","企业名称","流水号", "商品类型", "商品名称", "商品资费","优惠后费用","最终实收资费","折扣率",
				"渠道","流量充值手机号码","购买时间","结果返回时间","计费时间","流量充值状态"};
		keys = new String[]{ "spCode","spName","taskId", "goodsType", "goodsName", "fee","realFee","deductFee","discount",
				"netType","mdn","buyTime","reachTime","submitTime","status"};
		funcMap.put("goodsType", new GoodsTypeFunction());
		funcMap.put("netType", new NetTypeFunction());
		funcMap.put("status", new FlowStatusFunction());
		initColumns(titles, keys, funcMap);
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("flowDetailTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		SpDetailBillApi spDetailBillApi = task.getSpDetailBillApi();
		Map<String, Object> response = spDetailBillApi.flowDetailBill(JsonHelper.parseToJson(requestParams));
		return response;
	}
}
