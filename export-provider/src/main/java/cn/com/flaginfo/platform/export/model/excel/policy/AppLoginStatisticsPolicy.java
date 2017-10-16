package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.billing.api.SpDetailBillApi;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.model.ExportTask;

public class AppLoginStatisticsPolicy extends Policy {

    private String[] titles = null;
    private String[] keys = null;
    private ExportTask task;

    public AppLoginStatisticsPolicy(ExportTask task) {
        this.task = task;
        initPolicy();
    }

    @Override
    public void initPolicy() {
    	this.setApiUrl("group_login_statistical");
  //  	this.setApiUrl("last_login_time");
        this.setApiVersion("1.0");
        titles = new String[]{"人员名称", "部门", "上级部门", "开通时间", "上次登陆时间", "设备名称"};
        keys = new String[]{"name", "groupName", "parentGroupName", "openTime", "lastLoginTime", "deviceName"};
//        keys = new String[]{"name", "deptName", "parentName", "OpenDate", "loginTime", "deviceName"};
        initColumns(titles, keys);
        this.setFileName(task.getExportTitle() + task.getId() + "." + task.getFileType());
        this.setSheetName(task.getExportTitle());
        this.setFilePath(getUnitPath("AppLoginStatisticsTask"));
        Map headermap = new HashMap<>();
        headermap.put("content-type", "application/json");
        this.setHearderMap(headermap);
    }
    
    @Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		Map<String, Object> requestParams = task.getRequestParam();
		requestParams.put("pageInfo", pageInfo);
//		SpDetailBillApi spDetailBillApi = task.getSpDetailBillApi();
//		Map<String, Object> response = spDetailBillApi.appDetailBill(JsonHelper.parseToJson(requestParams));
//		return response;
		return null;
	}
}
