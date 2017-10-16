package cn.com.flaginfo.platform.export.model;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.flaginfo.channel.api.SmsTemplateApi;
import cn.com.flaginfo.channel.api.audit.NoAuditApi;
import cn.com.flaginfo.channel.api.audit.TaskAuditApi;
import cn.com.flaginfo.channel.api.security.BlackListApi;
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
import cn.com.flaginfo.platform.export.common.thread.FileRecordWorker;
import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.excel.ExcelDoc;
import cn.com.flaginfo.platform.export.model.excel.ExcelWriter;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;
import cn.com.flaginfo.platform.global.api.GlobalApi;
import cn.com.flaginfo.platform.global.api.GlobalApiOld;

public abstract class ExportTask extends FileRecordWorker {

    private Logger logger = Logger.getLogger(this.getClass());
    private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);
	    
    private String id;
    private String spId;
    private String operator;
    private String operatorId;
    private Date createTime;
    private String status;
    private Map<String, Object> requestParam;
    private Map<String, Object> headParam;
    private String remark;
    private String fileType;
    private int isDown;
    private String exportTitle;
    private String type;
    private String exportCurPage;
    private String exportPageLimit;
    private String source;

    public static final String STATUS_SUCC = "1";
    public static final String STATUS_WAIT = "0";
    public static final String STATUS_RUN = "9";
    public static final String STATUS_FAIL = "2";
    
    protected GlobalApi globalApi;
    protected GlobalApiOld globalApiOld;
    protected BillApi billApi;
    protected H2Api h2Api;
    protected BlackListApi blackListApi;
    protected SpDetailBillApi spDetailBillApi;
    protected SpAcceptanceApi spAcceptanceApi;
    protected RechargeApi rechargeApi;
    protected FeeRecordApi feeRecordApi;
    protected NoAuditApi noAuditApi;
    protected SmsTemplateApi smsTemplateApi;
    protected AdjustBillApi adjustBillApi;
    protected AdjustBusiBillApi adjustBusiBillApi;
    protected IncomeApi incomeApi;
    protected IndustryIncomeApi industryIncomeApi;
    protected AccountApi accountApi;
    protected IntlPriceApi intlPriceApi;
    protected TaskAuditApi taskAuditApi;
    
    /**
     * 初始化任务参数
     *
     * @param params
     */
    public void initTask(Map<String, Object> params) {
        this.spId = (String) params.get("spId");
        this.operator = (String) params.get("operator");
        this.operatorId = (String) params.get("operatorId");
        this.createTime = new Date();
        this.status = (String) params.get("status");
        this.remark = (String) params.get("remark");
        this.fileType = (String) params.get("fileType");
        if (params.get("source") instanceof Integer) {
            this.source = String.valueOf(params.get("source"));
        } else {
            this.source = (String) params.get("source");
        }
        this.isDown = 0;
        this.exportTitle = (String) params.get("fileName");
        this.type = (String) params.get("businessType");
        this.exportCurPage = (String) params.get("curPage");
        this.exportPageLimit = (String) params.get("pageLimit");
        this.requestParam = (Map<String, Object>) params.get("params");
        this.headParam = (Map<String, Object>) params.get("headParam");
    }

    /**
     * 执行导出任务
     *
     * @param writer
     * @param policy
     * @param task
     * @param result
     */
    public void excuteTask(ExcelWriter writer, Policy policy, ExportTask task, ExportResult result) {
        if (basicInfoValidate(policy, task, result)) {
            ExcelDoc excel = new ExcelDoc(policy);
            try {
                long start = System.currentTimeMillis();
                result.setStartTime(new Date());
                writer.initTotal();
                writer.writeData(excel);
                excel.wirteFile();
                long end = System.currentTimeMillis();
                result.setEndTime(new Date());
                result.setTotalTime(end - start);
                updateTaskSucc(result);
            } catch (Exception e) {
            	logger.error(e.getMessage());
                e.printStackTrace();
                updateTaskFail(result);
            } finally {
                excel.clear();
            }
        } else {
            updateTaskFail(result);
        }
    }

    /**
     * 基本信息验证
     *
     * @param policy
     * @param task
     * @param result
     * @return
     */
    public boolean basicInfoValidate(Policy policy, ExportTask task, ExportResult result) {
        if (policy == null) {
            logger.info("导出策略不能为空");
            result.setRemark("导出策略不能为空");
            return false;
        }
        if (task == null) {
            logger.info("导任务不能为空");
            result.setRemark("导任务不能为空");
            return false;
        }
       /* if (policy.getApiUrl() == null) {
            logger.info("任务调用api url不能为空");
            result.setRemark("任务调用api url不能为空");
            return false;
        }
        if (policy.getApiVersion() == null) {
            logger.info("任务调用api version不能为空");
            result.setRemark("任务调用api version不能为空");
            return false;
        }*/
        return true;
    }

    /**
     * 更新任务为成功
     *
     * @param result
     */
    public void updateTaskSucc(ExportResult result) {
        result.setExportId(this.getId());
        result.setStatus(STATUS_SUCC);
        manager.updateExportInfo(result);
    }

    /**
     * 更新任务为失败
     *
     * @param result
     */
    public void updateTaskFail(ExportResult result) {
        result.setExportId(this.getId());
        result.setStatus(STATUS_FAIL);
        manager.updateExportInfo(result);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(Map<String, Object> requestParam) {
        this.requestParam = requestParam;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getIsDown() {
        return isDown;
    }

    public void setIsDown(int isDown) {
        this.isDown = isDown;
    }

    public String getExportTitle() {
        return exportTitle;
    }

    public void setExportTitle(String exportTitle) {
        this.exportTitle = exportTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExportCurPage() {
        return exportCurPage;
    }

    public void setExportCurPage(String exportCurPage) {
        this.exportCurPage = exportCurPage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExportPageLimit() {
        return exportPageLimit;
    }

    public void setExportPageLimit(String exportPageLimit) {
        this.exportPageLimit = exportPageLimit;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

	public Map<String, Object> getHeadParam() {
		return headParam;
	}

	public void setHeadParam(Map<String, Object> headParam) {
		this.headParam = headParam;
	}

	public GlobalApi getGlobalApi() {
		return globalApi;
	}

	public void setGlobalApi(GlobalApi globalApi) {
		this.globalApi = globalApi;
	}

	public GlobalApiOld getGlobalApiOld() {
		return globalApiOld;
	}

	public void setGlobalApiOld(GlobalApiOld globalApiOld) {
		this.globalApiOld = globalApiOld;
	}

	public BillApi getBillApi() {
		return billApi;
	}

	public void setBillApi(BillApi billApi) {
		this.billApi = billApi;
	}

	public H2Api getH2Api() {
		return h2Api;
	}

	public void setH2Api(H2Api h2Api) {
		this.h2Api = h2Api;
	}

	public BlackListApi getBlackListApi() {
		return blackListApi;
	}

	public void setBlackListApi(BlackListApi blackListApi) {
		this.blackListApi = blackListApi;
	}

	public SpDetailBillApi getSpDetailBillApi() {
		return spDetailBillApi;
	}

	public void setSpDetailBillApi(SpDetailBillApi spDetailBillApi) {
		this.spDetailBillApi = spDetailBillApi;
	}

	public SpAcceptanceApi getSpAcceptanceApi() {
		return spAcceptanceApi;
	}

	public void setSpAcceptanceApi(SpAcceptanceApi spAcceptanceApi) {
		this.spAcceptanceApi = spAcceptanceApi;
	}

	public RechargeApi getRechargeApi() {
		return rechargeApi;
	}

	public void setRechargeApi(RechargeApi rechargeApi) {
		this.rechargeApi = rechargeApi;
	}

	public FeeRecordApi getFeeRecordApi() {
		return feeRecordApi;
	}

	public void setFeeRecordApi(FeeRecordApi feeRecordApi) {
		this.feeRecordApi = feeRecordApi;
	}

	public NoAuditApi getNoAuditApi() {
		return noAuditApi;
	}

	public void setNoAuditApi(NoAuditApi noAuditApi) {
		this.noAuditApi = noAuditApi;
	}

	public SmsTemplateApi getSmsTemplateApi() {
		return smsTemplateApi;
	}

	public void setSmsTemplateApi(SmsTemplateApi smsTemplateApi) {
		this.smsTemplateApi = smsTemplateApi;
	}

	public AdjustBillApi getAdjustBillApi() {
		return adjustBillApi;
	}

	public void setAdjustBillApi(AdjustBillApi adjustBillApi) {
		this.adjustBillApi = adjustBillApi;
	}

	public AdjustBusiBillApi getAdjustBusiBillApi() {
		return adjustBusiBillApi;
	}

	public void setAdjustBusiBillApi(AdjustBusiBillApi adjustBusiBillApi) {
		this.adjustBusiBillApi = adjustBusiBillApi;
	}

	public IncomeApi getIncomeApi() {
		return incomeApi;
	}

	public void setIncomeApi(IncomeApi incomeApi) {
		this.incomeApi = incomeApi;
	}

	public IndustryIncomeApi getIndustryIncomeApi() {
		return industryIncomeApi;
	}

	public void setIndustryIncomeApi(IndustryIncomeApi industryIncomeApi) {
		this.industryIncomeApi = industryIncomeApi;
	}

	public AccountApi getAccountApi() {
		return accountApi;
	}

	public void setAccountApi(AccountApi accountApi) {
		this.accountApi = accountApi;
	}

	public IntlPriceApi getIntlPriceApi() {
		return intlPriceApi;
	}

	public void setIntlPriceApi(IntlPriceApi intlPriceApi) {
		this.intlPriceApi = intlPriceApi;
	}

	public TaskAuditApi getTaskAuditApi() {
		return taskAuditApi;
	}

	public void setTaskAuditApi(TaskAuditApi taskAuditApi) {
		this.taskAuditApi = taskAuditApi;
	}
	
}
