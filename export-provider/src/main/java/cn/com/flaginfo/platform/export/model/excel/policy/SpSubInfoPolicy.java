package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.CityFunction;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.IndustryFunction;
import cn.com.flaginfo.platform.export.model.excel.func.PeriodFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SpFeeTypeFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SubscribStatusFunction;

/**
 * 企业订购信息
 * @author admin
 *
 */
public class SpSubInfoPolicy extends Policy {
	
	private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	private String[] titles = null;
	private String[] keys = null;
	private Map<String,Function<Object, Object>> funcMap = new HashMap<>();
	private ExportTask task;
	
	public SpSubInfoPolicy (ExportTask task){
		this.task = task;
		initPolicy();
	}
	
	@Override
	public void initPolicy() {
		this.setApiUrl("billing_sp_sub_info");
		this.setApiVersion("1.0");
		titles = new String[]{"企业编号","项目名称","地市","开户日期","付费类型","信控等级","计费号码","通道类型","客户经理","集客经理",
				"集客经理电话","项目联系人","项目联系电话","联通接入号","移动接入号","电信接入号","企业签名","所属行业",
				"套餐类型-短信","套餐名称-短信","订购金额-短信  (元)","超出单价-短信(元/条)","套餐条数-短信","生效时间-短信","生效结果-短信",
				"套餐类型-E信","套餐名称-E信","订购金额-E信  (元)","超出单价-E信(元/条)","套餐条数-E信","生效时间-E信","生效结果-E信",
				"套餐类型-混合","套餐名称-混合","订购金额-混合  (元)","短信超出单价-混合(元/条)","E信超出单价-混合(元/条)","短信套餐条数-混合","E信套餐条数-混合","生效时间-混合","生效结果-混合",
				"套餐类型-彩信","套餐名称-彩信","订购金额-彩信  (元)","超出单价-彩信(元/条)","套餐条数-彩信","生效时间-彩信","生效结果-彩信",
				"套餐类型-彩E信","套餐名称-彩E信","订购金额-彩E信  (元)","超出单价-彩E信(元/条)","套餐条数-彩E信","生效时间-彩E信","生效结果-彩E信"};
		
		
		keys = new String[]{"spCode","spName","city","createTime","feeType","level","feeMdn","channelType",
				"customerManagerName","saleName","saleMdn","contactName","contactPhone","unicomAccessNo","mobileAccessNo",
				"telcomAccessNo","sign","industryType","period1","packageName1","fee1","priceRate1","insideNumber1",
				"effectiveTime1","status1","period3","packageName3","fee3","priceRate3","insideNumber3","effectiveTime3",
				"status3","period0","packageName0","fee0","priceRate0_1","priceRate0_3","insideNumber0_1","insideNumber0_3","effectiveTime0",
				"status0","period2","packageName2","fee2","priceRate2","insideNumber2","effectiveTime2",
				"status2","period5","packageName5","fee5","priceRate5","insideNumber5","effectiveTime5",
				"status5"};
		
		Map<String,String> cityMap = manager.getCityNameMap(task.getGlobalApiOld());
		Map<String,String> industryMap = manager.getIndustriesName(task.getGlobalApiOld());
		funcMap.put("period0", new PeriodFunction());
		funcMap.put("period1", new PeriodFunction());
		funcMap.put("period2", new PeriodFunction());
		funcMap.put("period3", new PeriodFunction());
		funcMap.put("period5", new PeriodFunction());
		funcMap.put("status0", new SubscribStatusFunction("status"));
		funcMap.put("status1", new SubscribStatusFunction("status"));
		funcMap.put("status2", new SubscribStatusFunction("status"));
		funcMap.put("status3", new SubscribStatusFunction("status"));
		funcMap.put("status5", new SubscribStatusFunction("status"));
		funcMap.put("city", new CityFunction(cityMap));
		funcMap.put("feeType", new SpFeeTypeFunction());
		funcMap.put("industryType", new IndustryFunction(industryMap));

		initColumns(titles, keys, funcMap);
		System.out.println("-------------------exec policy -------------------");
		this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
		this.setSheetName(task.getExportTitle());
		this.setFilePath(getUnitPath("SpSubInfoTask"));
	}
	
	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
		BillApi billApi = task.getBillApi();
		Map<String, Object> response = billApi.getSpSubInfo(JsonHelper.parseToJson(requestParams));
		return response;
	}


}
