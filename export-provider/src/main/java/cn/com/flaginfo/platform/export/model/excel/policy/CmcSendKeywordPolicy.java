package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.SendKeywordPercentFunction;

/**
 * ClassName: CmcSendKeywordPolicy
 * Created by Christian-Lau on 2017/5/4.
 * Function:
 *
 * @version v1.0
 */
public class CmcSendKeywordPolicy extends Policy {
    private String[] titles = null;
    private String[] keys = null;
    private ExportTask task;
    private Map<String,Function<Object, Object>> funcMap = new HashMap<>();

    public CmcSendKeywordPolicy(ExportTask task){
        this.task = task;
        initPolicy();
    }

    @Override
    public void initPolicy() {
        this.setApiUrl("/send/keyword");
        this.setApiVersion("1.0");
        titles = new String[]{"关键字","人数","移动","联通","电信","占比"};
        keys = new String[]{"keyword","total","mobileNum","unicomNum","telcomNum","percent"};
        funcMap.put("percent",new SendKeywordPercentFunction());
        initColumns(titles, keys,funcMap);
        this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
        this.setSheetName(task.getExportTitle());
        this.setFilePath(getUnitPath("cmcSendKeywordTask"));
    }

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
