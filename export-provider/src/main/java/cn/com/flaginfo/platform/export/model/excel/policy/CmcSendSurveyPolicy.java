package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SendFailPercentFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SendPercentFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SendSuccPercentFunction;

/**
 * ClassName: CmcSendSurveyPolicy
 * Created by Christian-Lau on 2017/5/4.
 * Function:
 *
 * @version v1.0
 */
public class CmcSendSurveyPolicy extends Policy {
    private String[] titles = null;
    private String[] keys = null;
    private ExportTask task;
    private Map<String,Function<Object, Object>> funcMap = new HashMap<>();

    public CmcSendSurveyPolicy(ExportTask task){
        this.task = task;
        initPolicy();
    }

    @Override
    public void initPolicy() {
        this.setApiUrl("/send/survey");
        this.setApiVersion("1.0");
        titles = new String[]{"时间","发送总数量","移动","联通","电信","成功","成功率","失败","失败率","同比","环比"};
        keys = new String[]{"time","allNum","mobileNum","unicomNum","telcomNum","successNum","sccp","failNum","failPercent","tbPercent","hbPercent"};
        funcMap.put("sccp",new SendSuccPercentFunction());
        funcMap.put("failPercent",new SendFailPercentFunction());
        funcMap.put("tbPercent",new SendPercentFunction());
        funcMap.put("hbPercent",new SendPercentFunction());
        initColumns(titles, keys,funcMap);
        this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
        this.setSheetName(task.getExportTitle());
        this.setFilePath(getUnitPath("cmcSendSurveyTask"));
    }

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
