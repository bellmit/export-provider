package cn.com.flaginfo.platform.export.model.excel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;

import cn.com.flaginfo.platform.common.client.JsonApiClient;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.common.util.SystemMessage;
import cn.com.flaginfo.platform.export.model.ExportResult;
import cn.com.flaginfo.platform.export.model.ExportTask;
import cn.com.flaginfo.platform.export.model.excel.policy.Policy;

/**
 * 导出任务
 *
 * @author chengbin.luo
 */
public class ExcelWriter {

    public Logger logger = Logger.getLogger(getClass());
    private ExportTask task;
    private Policy policy;
    private int total;
    private int index;
    private ExportResult result;

    public ExcelWriter(ExportTask task, Policy policy) {
        this.task = task;
        this.policy = policy;
    }

    /**
     * 写入数据
     */
    public void writeData(ExcelDoc excel) {
        Sheet sheet = excel.createSheet(policy.getSheetName());
        logger.info("start wirte title...  id:" + task.getId());
        excel.writeTitle(sheet);
        logger.info("end wirte title...  id:" + task.getId());
        logger.info("-----------policy size :" + policy.getReadSize());
        int c = 1;
        while (total > index * policy.getReadSize()) {
            c++;
            logger.info("start wirte data... id:" + task.getId());
            excel.writeDataList(sheet, getData());
            logger.info("end wirte data... current index:" + (index * policy.getReadSize()) + " id:" + task.getId());
            if (c > 1000) {
                break;
            }
        }
    }

    /**
     * 初始化
     */
    public void initTotal() {
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("curPage", 1);
        pageInfo.put("pageLimit", 1);
        Map<String, Object> responseMap = getPageData(pageInfo);
        total = responseMap.get("dataCount") == null ? policy.getReadSize() : (Integer) responseMap.get("dataCount");
        index = 0;
        result.setExportPath(policy.getFilePath() + File.separator + policy.getFileName());
        result.setRows(total);
    }

    private List<Map<String, Object>> getData() {
        index++;
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("curPage", index);
        pageInfo.put("pageLimit", policy.getReadSize());
        Map<String, Object> responseMap = getPageData(pageInfo);
        return (List<Map<String, Object>>) responseMap.get("list");
    }

    public Map<String, Object> getPageData(Map<String, Object> pageInfo) {
//        Map<String, Object> requestParams = task.getRequestParam();
//        Map headMap = new HashMap();
//        headMap.put("operator", JsonHelper.parseToJson(task.getHeadParam()));
//        if (policy.getHearderMap() != null) {
//        	headMap.putAll(policy.getHearderMap());
//        }
//        requestParams.put("pageInfo", pageInfo);
//        JsonApiClient client = new JsonApiClient();
//        client.setApiKey(SystemMessage.getString("server_api_key"));
//        client.setReqUrl(policy.getApiUrl());
//        client.setVersion(policy.getApiVersion());
//        client.setReqMap(requestParams);
//        client.setHeaderMap(headMap);
//        Map<String, Object> responseMap = client.post(Map.class);
        Map<String, Object> responseMap = policy.getInfo(pageInfo); 
        
        return responseMap;
    }

    /**
     * @return the result
     */
    public ExportResult getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(ExportResult result) {
        this.result = result;
    }

}
