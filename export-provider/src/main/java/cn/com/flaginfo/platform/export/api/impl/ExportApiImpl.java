package cn.com.flaginfo.platform.export.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.boot.hsf.annotation.HSFConsumer;
import com.alibaba.boot.hsf.annotation.HSFProvider;

import cn.com.flaginfo.channel.api.SmsTemplateApi;
import cn.com.flaginfo.channel.api.audit.NoAuditApi;
import cn.com.flaginfo.channel.api.audit.TaskAuditApi;
import cn.com.flaginfo.channel.api.security.BlackListApi;
import cn.com.flaginfo.platform.api.common.base.PagableJsonViewResponse;
import cn.com.flaginfo.platform.api.common.base.SingleValueJsonViewResponse;
import cn.com.flaginfo.platform.billing.api.AccountApi;
import cn.com.flaginfo.platform.billing.api.AdjustBillApi;
import cn.com.flaginfo.platform.billing.api.AdjustBusiBillApi;
import cn.com.flaginfo.platform.billing.api.BillApi;
import cn.com.flaginfo.platform.billing.api.FeeRecordApi;
import cn.com.flaginfo.platform.billing.api.H2Api;
import cn.com.flaginfo.platform.billing.api.IncomeApi;
import cn.com.flaginfo.platform.billing.api.IndustryIncomeApi;
import cn.com.flaginfo.platform.billing.api.IntlPriceApi;
import cn.com.flaginfo.platform.billing.api.RechargeApi;
import cn.com.flaginfo.platform.billing.api.SpAcceptanceApi;
import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.support.PartialCollection;
import cn.com.flaginfo.platform.common.support.QueryInfo;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.api.ExportApi;
import cn.com.flaginfo.platform.export.common.base.BaseController;
import cn.com.flaginfo.platform.export.common.base.Constants;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.util.ParamsUtil;
import cn.com.flaginfo.platform.global.api.GlobalApiOld;

@HSFProvider(serviceInterface=ExportApi.class)
public class ExportApiImpl extends BaseController implements ExportApi {
	
	@Autowired
	public ExportManager exportManager;
	
	@HSFConsumer(serviceGroup="global-service",serviceVersion="1.0.0")
	GlobalApiOld globalApiOld;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	BillApi billApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	H2Api h2Api;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	SpDetailBillApi spDetailBillApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	SpAcceptanceApi spAcceptanceApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	RechargeApi rechargeApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	FeeRecordApi feeRecordApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	AdjustBillApi adjustBillApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	AdjustBusiBillApi adjustBusiBillApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	IncomeApi incomeApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	IndustryIncomeApi industryIncomeApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	AccountApi accountApi;
	
	@HSFConsumer(serviceGroup="billing-service",serviceVersion="1.0.0")
	IntlPriceApi intlPriceApi;
	
	@HSFConsumer(serviceGroup="channel-service",serviceVersion="1.0.0")
	BlackListApi blackListApi;
	
	@HSFConsumer(serviceGroup="channel-service",serviceVersion="1.0.0")
	NoAuditApi noAuditApi;
	
	@HSFConsumer(serviceGroup="channel-service",serviceVersion="1.0.0")
	SmsTemplateApi smsTemplateApi;
	
	@HSFConsumer(serviceGroup="channel-service",serviceVersion="1.0.0")
	TaskAuditApi taskAuditApi;
	
	@Override
	public SingleValueJsonViewResponse excel(String content) {
		logger.info("导出excel文件参数: {}",content);
		SingleValueJsonViewResponse response = new SingleValueJsonViewResponse();
		Map<String,Object> params = JsonHelper.parseToMap(content);
		params.put("operator", ParamsUtil.getOperatorName());
		params.put("operatorId", ParamsUtil.getOperatorId());
		if(!isEmptyInMap(params, "spId")){
			params.put("spId",params.get("spId"));
		}else{
			params.put("spId",ParamsUtil.getOperatorSpId());
		}
		params.put("headParam",JsonHelper.parseToMap(ParamsUtil.getOperator()));
		ExportTask task = exportManager.getTaskByType((String)params.get("businessType"));
		if(task==null){
			response.setFail(Constants.RETURN_NULL_ERROR);
			response.setReturnMsg("不支持的导出类型");
			return response;
		}
		task.setGlobalApiOld(globalApiOld);
		task.setBillApi(billApi);
		task.setH2Api(h2Api);
		task.setSpDetailBillApi(spDetailBillApi);
		task.setSpAcceptanceApi(spAcceptanceApi);
		task.setRechargeApi(rechargeApi);
		task.setFeeRecordApi(feeRecordApi);
		task.setAdjustBillApi(adjustBillApi);
		task.setAdjustBusiBillApi(adjustBusiBillApi);
		task.setIndustryIncomeApi(industryIncomeApi);
		task.setAccountApi(accountApi);
		task.setIntlPriceApi(intlPriceApi);
		task.setBlackListApi(blackListApi);
		task.setNoAuditApi(noAuditApi);
		task.setSmsTemplateApi(smsTemplateApi);
		task.setIncomeApi(incomeApi);
		task.setTaskAuditApi(taskAuditApi);
		task.initTask(params);
		//添加导出任务
		String id = exportManager.addExportInfo(task);
		task.setId(id);
		exportManager.submit(task);
		response.setSuccess();
		return response;
	}
	
	@Override
	public PagableJsonViewResponse listPartial(String content) {
		logger.info("导出任务查询参数: {}",content);
        Map<String, Object> params = JsonHelper.parseToMap(content);
        String id = (String) params.get("id");
        String spId = (String) params.get("spId");
        String status = (String) params.get("status");
        String operatorId = (String) params.get("operatorId");
        String source = (String) params.get("source");
        String type = (String) params.get("type");
        String exportTitle = (String) params.get("exportTitle");
        String fileType = (String) params.get("fileType");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        QueryInfo queryInfo = getQueryInfo(params);
        PartialCollection partial = exportManager.listExportPartial(id, spId, status, type, fileType,
                startTime, endTime, operatorId, source, exportTitle,queryInfo);
        PagableJsonViewResponse response = new PagableJsonViewResponse();
        response.setList(partial.getRealDataSet());
		response.setDataCount(partial.getTotal());
		response.setSuccess();
		return response;
	}

	@Override
	public SingleValueJsonViewResponse<Map<String, String>> exportTaskAdd(String content) {
		logger.info("新增导出任务: {}", content);
        Map<String, Object> params = JsonHelper.parseToMap(content);
        String exportTaskId = exportManager.addExportInfo(params);
        SingleValueJsonViewResponse<Map<String, String>> response = new SingleValueJsonViewResponse<Map<String, String>>();
        Map<String, String> result = new HashMap<>();
        result.put("exportTaskId", exportTaskId);
        response.putAll(result);
        return response;
	}

	@Override
	public SingleValueJsonViewResponse exportTaskUpdate(String content) {
		logger.info("更新导出任务: {}", content);
		SingleValueJsonViewResponse response = new SingleValueJsonViewResponse();
        ExportResult params = JsonHelper.parseToObject(content, ExportResult.class);
        exportManager.updateExportInfo(params);
        response.setSuccess();
        return response;
	}

	
	
}
