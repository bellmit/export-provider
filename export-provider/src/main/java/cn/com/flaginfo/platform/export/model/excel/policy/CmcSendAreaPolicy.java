package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.HashMap;
import java.util.Map;

import cn.com.flaginfo.platform.export.consumer.ManagerProxy;
import cn.com.flaginfo.platform.export.manager.ExportManager;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.func.Function;
import cn.com.flaginfo.platform.export.model.excel.func.ProvinceFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SendFailPercentFunction;
import cn.com.flaginfo.platform.export.model.excel.func.SendSuccPercentFunction;

/**
 * ClassName: CmcSendAreaPolicy
 * Created by Christian-Lau on 2017/5/4.
 * Function:
 *
 * @version v1.0
 */
public class CmcSendAreaPolicy extends Policy {
    private ExportManager manager = ManagerProxy.getInstance(ExportManager.class);

    private String[] titles = null;
    private String[] keys = null;
    private ExportTask task;
    private Map<String,Function<Object, Object>> funcMap = new HashMap<>();

    public CmcSendAreaPolicy(ExportTask task){
        this.task = task;
        initPolicy();
    }

    @Override
    public void initPolicy() {
        this.setApiUrl("/send/area");
        this.setApiVersion("1.0");
        titles = new String[]{"省份","发送总数量","移动","联通","电信","成功","成功率","失败","失败率"};
        keys = new String[]{"platform","allNum","mobileNum","unicomNum","telcomNum","successNum","sccp","failNum","failPercent"};

        Map<String,String> provinceMap = manager.getProvinceNameMap(task.getGlobalApiOld());
        funcMap.put("platform", new ProvinceFunction(provinceMap));
        funcMap.put("sccp",new SendSuccPercentFunction());
        funcMap.put("failPercent",new SendFailPercentFunction());
        initColumns(titles, keys, funcMap);
        this.setFileName(task.getExportTitle()+task.getId()+"."+task.getFileType());
        this.setSheetName(task.getExportTitle());
        this.setFilePath(getUnitPath("cmcSendAreaTask"));
    }

	@Override
	public Map<String, Object> getInfo(Map<String, Object> pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
