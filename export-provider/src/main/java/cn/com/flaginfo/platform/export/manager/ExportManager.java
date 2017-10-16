package cn.com.flaginfo.platform.export.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.stereotype.Service;

import cn.com.flaginfo.platform.common.support.PartialCollection;
import cn.com.flaginfo.platform.common.support.QueryInfo;
import cn.com.flaginfo.platform.common.util.DateUtil;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.common.base.DBConstants;
import cn.com.flaginfo.platform.export.common.thread.FileThreadPool;
import cn.com.flaginfo.platform.export.common.thread.ThreadPoolFactory;
import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.task.AcceptanceOpeListTask;
import cn.com.flaginfo.platform.export.model.excel.task.AppDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.AppFinanceBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.AppLoginStatisticsTask;
import cn.com.flaginfo.platform.export.model.excel.task.AppRegistStatisticsTask;
import cn.com.flaginfo.platform.export.model.excel.task.BalanceLogTask;
import cn.com.flaginfo.platform.export.model.excel.task.BillAccountListTask;
import cn.com.flaginfo.platform.export.model.excel.task.BlackMdnTask;
import cn.com.flaginfo.platform.export.model.excel.task.BusiAdjustRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.ChannelAuditTaskResultAcqTask;
import cn.com.flaginfo.platform.export.model.excel.task.CmcSendAreaTask;
import cn.com.flaginfo.platform.export.model.excel.task.CmcSendKeywordTask;
import cn.com.flaginfo.platform.export.model.excel.task.CmcSendSurveyTask;
import cn.com.flaginfo.platform.export.model.excel.task.CmcTaskListTask;
import cn.com.flaginfo.platform.export.model.excel.task.ContactsTask;
import cn.com.flaginfo.platform.export.model.excel.task.FinanceBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.FlowAdjustRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.FlowDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.FlowFinanceBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.GiftRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.GoodsBuyRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.IndustryIncomeTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectChannelListTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectInvokeDayTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectInvokeHourTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectSpListTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectTaskDetail4EpTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectTaskDetail4PaTask;
import cn.com.flaginfo.platform.export.model.excel.task.InspectTaskDetail4SpTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSendObjectTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSmsBillByCountryTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSmsBillByTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSmsBillByTaskDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSmsBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.IntlSmsPlatformTask;
import cn.com.flaginfo.platform.export.model.excel.task.IotCardExportTask;
import cn.com.flaginfo.platform.export.model.excel.task.MessageDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.NewBusinessTask;
import cn.com.flaginfo.platform.export.model.excel.task.NoauditMdnTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformCostSyncTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformDailyIncomeTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformFinanceBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformIncomeDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformSmsBillByProductTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformSmsBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformWeeklyIncomeTask;
import cn.com.flaginfo.platform.export.model.excel.task.PlatformWeeklyOpenTask;
import cn.com.flaginfo.platform.export.model.excel.task.ProvinceTotalStatementTask;
import cn.com.flaginfo.platform.export.model.excel.task.RechargeRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.SmsAdjustRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.SmsBillByProductTask;
import cn.com.flaginfo.platform.export.model.excel.task.SmsBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpAcceptanceListTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpAdustSummaryTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpBillProductTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpCostSyncTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpCountryIntlPriceTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpFeeDetailTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpFeeRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpFinanceBillTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpListTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpOrderItemTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpOrderListTask;
import cn.com.flaginfo.platform.export.model.excel.task.SpSubInfoTask;
import cn.com.flaginfo.platform.export.model.excel.task.SubscribeEffectRecordTask;
import cn.com.flaginfo.platform.export.model.excel.task.TaskListTask;
import cn.com.flaginfo.platform.export.model.excel.task.TemplateListTask;
import cn.com.flaginfo.platform.export.model.excel.task.WechartPayListTask;


//import cn.com.flaginfo.model.export.excel.task.*;
@Service
public class ExportManager extends BaseManager {

	private ThreadPoolExecutor largeTaskPool = ThreadPoolFactory.getInstance()
			.getThreadPool("thread_pool_export_large_task",
					FileThreadPool.POOL_SMALL);
	private ThreadPoolExecutor bigTaskPool = ThreadPoolFactory.getInstance()
			.getThreadPool("thread_pool_export_big_task",
					FileThreadPool.POOL_MIDDLE);
	private ThreadPoolExecutor normalTaskPool = ThreadPoolFactory.getInstance()
			.getThreadPool("thread_pool_export_normal_task",
					FileThreadPool.POOL_LARGE);

	/**
	 * 根据任务类型确定提交的线程池
	 * @param task
	 */
	public void submit(ExportTask task) {
		//提交线程池执异步导出
		if ("ContactsExportTask".equals(task.getType())
				|| "BlackMdnExportTask".equals(task.getType())
				|| "FinanceBillExportTask".equals(task.getType())
				|| "PlatformFinanceBillTask".equals(task.getType())
				|| "SpFinanceBillTask".equals(task.getType())
				|| "FlowFinanceBillTask".equals(task.getType())
				|| "AppFinanceBillTask".equals(task.getType())
				|| "FlowDetailTask".equals(task.getType())
				|| "AppDetailTask".equals(task.getType())
				|| "SubscribeEffectRecordTask".equals(task.getType())
				|| "RechargeRecordTask".equals(task.getType())
				|| "GiftRecordTask".equals(task.getType())
				|| "SpFeeRecordTask".equals(task.getType())
				|| "SpFeeDetailTask".equals(task.getType())
				|| "SpCostSyncTask".equals(task.getType())
				|| "NoauditMdnTask".equals(task.getType())
				|| "PlatformCostSyncTask".equals(task.getType())
				|| "TemplateListTask".equals(task.getType())
				|| "IntlSmsBillTask".equals(task.getType())
				|| "IntlSmsBillByCountryTask".equals(task.getType())
				|| "IntlSmsBillByTaskDetailTask".equals(task.getType())
				|| "IntlSmsBillByTask".equals(task.getType())
				|| "IntlSmsPlatformTask".equals(task.getType())
				|| "IntlSendObjectTask".equals(task.getType())
				|| "IotCardExportTask".equals(task.getType())
				|| "BalanceLogTask".equals(task.getType())
				|| "BillAccountListTask".equals(task.getType())
				|| "FlowAdjustRecordTask".equals(task.getType())
				|| "SmsAdjustRecordTask".equals(task.getType())
				|| "BusiAdjustRecordTask".equals(task.getType())
				|| "SpAdustSummaryTask".equals(task.getType())
				|| "IndustryIncomeTask".equals(task.getType())
				|| "PlatformIncomeDetailTask".equals(task.getType())
				|| "PlatformWeeklyOpenTask".equals(task.getType())
				|| "PlatformDailyIncomeTask".equals(task.getType())
				|| "PlatformWeeklyIncomeTask".equals(task.getType())
				|| "SpAcceptanceListTask".equals(task.getType())
				|| "AcceptanceOpeListTask".equals(task.getType())
				|| "SpOrderItemTask".equals(task.getType())
				|| "SpOrderListTask".equals(task.getType())
				|| "SpSubInfoTask".equals(task.getType())
				|| "GoodsBuyRecordTask".equals(task.getType())
				|| "SpListTask".equals(task.getType())
				|| "WechartPayListTask".equals(task.getType())
				|| "SmsBillTask".equals(task.getType())
				|| "SmsBillByProductTask".equals(task.getType())
				|| "SpCountryIntlPriceTask".equals(task.getType())
				|| "CmcTaskListTask".equals(task.getType())
				|| "CmcSendSurveyTask".equals(task.getType())
				|| "CmcSendKeywordTask".equals(task.getType())
				|| "CmcSendAreaTask".equals(task.getType())
				|| "PlatformSmsBillTask".equals(task.getType())
				|| "PlatformSmsBillByProductTask".equals(task.getType())
				|| "NewBusinessTask".equals(task.getType())
				|| "ProvinceTotalStatementTask".equals(task.getType())
				|| "SpBillProductTask".equals(task.getType())
				|| "InspectTaskDetail4EpTask".equals(task.getType())
				|| "InspectTaskDetail4SpTask".equals(task.getType())
				|| "InspectTaskDetail4PaTask".equals(task.getType())
				|| "InspectInvokeDayTask".equals(task.getType())
				|| "InspectInvokeHourTask".equals(task.getType())
				|| "InspectChannelListTask".equals(task.getType())
				|| "InspectManageListTask".equals(task.getType())) {
			bigTaskPool.submit(task);
		}
		if ("MessageDetailTask".equals(task.getType())
				|| "AppLoginStatisticsTask".equals(task.getType())
				|| "AppRegistStatisticsTask".equals(task.getType())) {
			largeTaskPool.submit(task);
		}
	}

	/**
	 * 根据任务类型获取导出任务
	 * @param task
	 * @return
	 */
	public ExportTask getTaskByType(String type) {
		if ("ContactsExportTask".equals(type)) {
			return new ContactsTask();
		}
		if ("BlackMdnExportTask".equals(type)) {
			return new BlackMdnTask();
		}
		if ("FinanceBillExportTask".equals(type)) {
			return new FinanceBillTask();
		}
		if ("PlatformFinanceBillTask".equals(type)) {
			return new PlatformFinanceBillTask();
		}
		if ("SpFinanceBillTask".equals(type)) {
			return new SpFinanceBillTask();
		}
		if ("FlowFinanceBillTask".equals(type)) {
			return new FlowFinanceBillTask();
		}
		if ("AppFinanceBillTask".equals(type)) {
			return new AppFinanceBillTask();
		}
		if ("MessageDetailTask".equals(type)) {
			return new MessageDetailTask();
		}
		if ("FlowDetailTask".equals(type)) {
			return new FlowDetailTask();
		}
		if ("AppDetailTask".equals(type)) {
			return new AppDetailTask();
		}
		if ("SubscribeEffectRecordTask".equals(type)) {
			return new SubscribeEffectRecordTask();
		}
		if ("RechargeRecordTask".equals(type)) {
			return new RechargeRecordTask();
		}
		if ("GiftRecordTask".equals(type)) {
			return new GiftRecordTask();
		}
		if ("SpFeeRecordTask".equals(type)) {
			return new SpFeeRecordTask();
		}
		if ("SpFeeDetailTask".equals(type)) {
			return new SpFeeDetailTask();
		}
		if ("SpCostSyncTask".equals(type)) {
			return new SpCostSyncTask();
		}
		if ("PlatformCostSyncTask".equals(type)) {
			return new PlatformCostSyncTask();
		}
		if ("NoauditMdnTask".equals(type)) {
			return new NoauditMdnTask();
		}
		if ("TemplateListTask".equals(type)) {
			return new TemplateListTask();
		}
		if ("IntlSmsBillTask".equals(type)) {
			return new IntlSmsBillTask();
		}
		if ("IntlSmsBillByCountryTask".equals(type)) {
			return new IntlSmsBillByCountryTask();
		}
		if ("IntlSmsBillByTaskDetailTask".equals(type)) {
			return new IntlSmsBillByTaskDetailTask();
		}
		if ("IntlSmsBillByTask".equals(type)) {
			return new IntlSmsBillByTask();
		}
		if ("IntlSmsPlatformTask".equals(type)) {
			return new IntlSmsPlatformTask();
		}
		if ("IntlSendObjectTask".equals(type)) {
			return new IntlSendObjectTask();
		}
		if ("IotCardExportTask".equals(type)) {
			return new IotCardExportTask();
		}
		if ("SmsAdjustRecordTask".equals(type)) {
			return new SmsAdjustRecordTask();
		}
		if ("BusiAdjustRecordTask".equals(type)) {
			return new BusiAdjustRecordTask();
		}
		if ("FlowAdjustRecordTask".equals(type)) {
			return new FlowAdjustRecordTask();
		}
		if ("SpAdustSummaryTask".equals(type)) {
			return new SpAdustSummaryTask();
		}
		if ("PlatformIncomeDetailTask".equals(type)) {
			return new PlatformIncomeDetailTask();
		}
		if ("PlatformWeeklyOpenTask".equals(type)) {
			return new PlatformWeeklyOpenTask();
		}
		if ("PlatformDailyIncomeTask".equals(type)) {
			return new PlatformDailyIncomeTask();
		}
		if ("PlatformWeeklyIncomeTask".equals(type)) {
			return new PlatformWeeklyIncomeTask();
		}
		if ("IndustryIncomeTask".equals(type)) {
			return new IndustryIncomeTask();
		}
		if ("BalanceLogTask".equals(type)) {
			return new BalanceLogTask();
		}
		if ("BillAccountListTask".equals(type)) {
			return new BillAccountListTask();
		}
		if ("SpAcceptanceListTask".equals(type)) {
			return new SpAcceptanceListTask();
		}
		if ("AcceptanceOpeListTask".equals(type)) {
			return new AcceptanceOpeListTask();
		}
		if ("SpOrderItemTask".equals(type)) {
			return new SpOrderItemTask();
		}
		if ("SpOrderListTask".equals(type)) {
			return new SpOrderListTask();
		}
		if ("SpSubInfoTask".equals(type)) {
			return new SpSubInfoTask();
		}
		if ("GoodsBuyRecordTask".equals(type)) {
			return new GoodsBuyRecordTask();
		}
		if ("WechartPayListTask".equals(type)) {
			return new WechartPayListTask();
		}
		if ("SmsBillTask".equals(type)) {
			return new SmsBillTask();
		}
		if ("SmsBillByProductTask".equals(type)) {
			return new SmsBillByProductTask();
		}
		if ("SpCountryIntlPriceTask".equals(type)) {
			return new SpCountryIntlPriceTask();
		}
		if ("SpListTask".equals(type)) {
			return new SpListTask();
		}
		if ("CmcTaskListTask".equals(type)) {
			return new CmcTaskListTask();
		}
		if ("AppLoginStatisticsTask".equals(type)) {
			return new AppLoginStatisticsTask();
		}
		if ("AppRegistStatisticsTask".equals(type)) {
			return new AppRegistStatisticsTask();
		}
		if ("ChannelAuditTaskResultAcq".equals(type)) {
			return new ChannelAuditTaskResultAcqTask();
		}
		if ("TaskList".equals(type)) {
			return new TaskListTask();
		}
		if ("CmcSendSurveyTask".equals(type)) {
			return new CmcSendSurveyTask();
		}
		if ("CmcSendKeywordTask".equals(type)) {
			return new CmcSendKeywordTask();
		}
		if ("CmcSendAreaTask".equals(type)) {
			return new CmcSendAreaTask();
		}
		if ("PlatformSmsBillTask".equals(type)) {
			return new PlatformSmsBillTask();
		}
		if ("PlatformSmsBillByProductTask".equals(type)) {
			return new PlatformSmsBillByProductTask();
		}
		if ("NewBusinessTask".equals(type)) {
			return new NewBusinessTask();
		}
		if ("ProvinceTotalStatementTask".equals(type)) {
			return new ProvinceTotalStatementTask();
		}
		if ("SpBillProductTask".equals(type)) {
			return new SpBillProductTask();
		}
		if ("InspectTaskDetail4EpTask".equals(type)) {
			return new InspectTaskDetail4EpTask();
		}
		if ("InspectTaskDetail4SpTask".equals(type)) {
			return new InspectTaskDetail4SpTask();
		}
		if ("InspectTaskDetail4PaTask".equals(type)) {
			return new InspectTaskDetail4PaTask();
		}
		if ("InspectInvokeDayTask".equals(type)) {
			return new InspectInvokeDayTask();
		}
		if ("InspectInvokeHourTask".equals(type)) {
			return new InspectInvokeHourTask();
		}
		if ("InspectChannelListTask".equals(type)) {
			return new InspectChannelListTask();
		}
		if ("InspectManageListTask".equals(type)) {
			return new InspectSpListTask();
		}
		return null;
	}

	/**
	 * 新增导出任务记录
	 * @param params
	 */
	public String addExportInfo(ExportTask task) {
		Map<String, Object> condsMap = new HashMap<String, Object>();
		condsMap.put("spId", task.getSpId());
		condsMap.put("operator", task.getOperator());
		condsMap.put("operatorId", task.getOperatorId());
		condsMap.put("createTime", task.getCreateTime());
		condsMap.put("status", "0");
		condsMap.put("requestParam",
				JsonHelper.parseToJson(task.getRequestParam()));
		condsMap.put("remark", task.getRemark());
		condsMap.put("fileType", task.getFileType());
		condsMap.put("isDown", 0);
		condsMap.put("exportTitle", task.getExportTitle());
		condsMap.put("type", task.getType());
		condsMap.put("exportCurPage", task.getExportCurPage());
		condsMap.put("source", task.getSource());
		condsMap.put("headParam", JsonHelper.parseToJson(task.getHeadParam()));
		String exportId = this.save(condsMap, "dc_task");
		return exportId;
	}
	

	/**
	 * 新增导出任务记录
	 * @param task
	 * @return
	 * @since 6.0
	 */
	public String addExportInfo(Map<String, Object> task) {
		Map<String, Object> condsMap = new HashMap<String, Object>();
		condsMap.put("spId", task.get("spId"));
		condsMap.put("operator", task.get("operator"));
		condsMap.put("operatorId", task.get("operatorId"));
		condsMap.put("createTime", DateUtil.getDate(String.valueOf(task.get("createTime"))));
		condsMap.put("status", "0");
		condsMap.put("requestParam",
				JsonHelper.parseToJson(task.get("requestParma")));
		condsMap.put("remark", task.get("remark"));
		condsMap.put("fileType", task.get("fileType"));
		condsMap.put("isDown", 0);
		condsMap.put("exportTitle", task.get("exportTitle"));
		condsMap.put("type", task.get("type"));
		condsMap.put("exportCurPage", task.get("exportCurPage"));
		condsMap.put("source", task.get("source"));
		condsMap.put("headParam", JsonHelper.parseToJson(task.get("headParam")));

		String exportId = this.save(condsMap, "dc_task");
		return exportId;
	}

	/**
	 * 更新任务导出结果
	 * @param exportId
	 * @param result
	 */
	public void updateExportInfo(ExportResult result) {
		if (result == null)
			return;
		Map<String, Object> setMap = new HashMap<String, Object>();
		setMap.put("filePath", result.getExportPath());
		setMap.put("exportNum", result.getRows());
		setMap.put("exportCost", result.getTotalTime());
		setMap.put("status", result.getStatus());
		setMap.put("startTime", result.getStartTime());
		setMap.put("endTime", result.getEndTime());
		setMap.put("remark", result.getRemark());
		Map<String, Object> condMap = new HashMap<>();
		condMap.put("id", result.getExportId());
		this.update(setMap,condMap, "dc_task");
	}

	/**
	 * 查询导出任务结果
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @param taskId
	 * @return
	 */
	public List listExportTasks(Date startTime, Date endTime, String status,
			String taskId) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
				.append(" select id, sp_id, operator, operator ")
				.append(" ,create_time,status,start_time,end_time,remark ")
				.append(" ,export_num,export_cost,export_title, source ")
				.append(" ,file_type,file_path,is_down,type, request_param ")
				.append(" from dc_task ")
				.append(" where create_time >=:startTime and create_time <=:endTime ")
				.append(" and status=:status and id =:id and sp_id =:spId and type =:type ")
				.append(" and file_type =:fileType and operator_id =:operatorId and source=:source ")
				.append(" order by create_time desc ");
		return createDBOperation(sqlBuilder.toString(), DBConstants.DB_PORTAL)
				.setParameter("taskId", taskId).setDate("startTime", startTime)
				.setDate("endTime", endTime).setParameter("status", status)
				.list();
	}

	/**
	 * 分页查询导出任务列表
	 * @param id
	 * @param spId
	 * @param status
	 * @param type
	 * @param fileType
	 * @param startTime
	 * @param endTime
	 * @param queryInfo
	 * @return
	 */
	public PartialCollection listExportPartial(String id, String spId,
			String status, String type, String fileType, String startTime,
			String endTime, String operatorId, String source,
			String exportTitle, QueryInfo queryInfo) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder
				.append(" select id, sp_id, operator, operator ")
				.append(" ,create_time,status,start_time,end_time,remark ")
				.append(" ,export_num,export_cost,export_title, source ")
				.append(" ,file_type,file_path,is_down,type ")
				.append(" from dc_task ")
				.append(" where create_time >=:startTime and create_time <=:endTime and export_title like :exportTitle ")
				.append(" and status=:status and id =:id and sp_id =:spId and type =:type ")
				.append(" and file_type =:fileType and operator_id =:operatorId and source=:source ")
				.append(" order by create_time desc ");
		return createDBOperation(sqlBuilder.toString(), DBConstants.DB_PORTAL)
				.setParameter("id", id).setDateString("startTime", startTime)
				.setDateString("endTime", endTime)
				.setParameter("status", status).setParameter("spId", spId)
				.setParameter("type", type).setParameter("source", source)
				.setParameter("operatorId", operatorId)
				.setParameter("fileType", fileType)
				.setLike("exportTitle", exportTitle, "%", "%")
				.setQueryInfo(queryInfo).listPartial();
	}

}
