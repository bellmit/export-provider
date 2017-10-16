package cn.com.flaginfo.platform.export.model.excel.policy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;

import cn.com.flaginfo.platform.export.model.excel.ExcelColumn;
import cn.com.flaginfo.platform.export.model.excel.ExcelDoc;
import cn.com.flaginfo.platform.export.model.excel.func.Function;

public abstract class Policy {

    private List<ExcelColumn> columns = new ArrayList<>();
    private String fileName;
    private String filePath;
    private String sheetName;
    private String apiUrl;
    private String apiVersion;
    private Map hearderMap;
    private int readSize = 1000;
    
    public abstract void initPolicy();

    
    public abstract Map<String, Object> getInfo(Map<String, Object> pageInfo);
    
    public String getUnitPath(String taskName) {
        String path = "/ext/export/" + DateUtil.formatDate(new Date(), "yyyyMM") + "/" + taskName;
        return path;
    }

    /**
     * 初始化列信息
     *
     * @param titles 表格标题（有序）
     * @param keys   表格key（有序）
     */
    public void initColumns(String[] titles, String[] keys) {
        initColumns(titles, keys, null);
    }

    /**
     * 初始化列信息
     *
     * @param titles  表格标题（有序）
     * @param keys    表格key（有序）
     * @param funcMap 表格key对应的处理函数
     */
    public void initColumns(String[] titles, String[] keys, Map<String, Function<Object, Object>> funcMap) {
        Integer[] types = new Integer[titles.length];
        for (int i = 0; i < titles.length; i++) {
            types[i] = ExcelDoc.DATA_TYPE_STRING;
        }
        initColumns(titles, keys, types, funcMap);
    }

    /**
     * 初始化列信息
     *
     * @param titles  表格标题（有序）
     * @param keys    表格key（有序）
     * @param types   表格列类型
     * @param funcMap 表格列处理函数
     */
    public void initColumns(String[] titles, String[] keys, Integer[] types, Map<String, Function<Object, Object>> funcMap) {
        Function<Object, Object>[] funcs = new Function[titles.length];
        for (int i = 0; i < titles.length; i++) {
            if (funcMap != null && funcMap.get(keys[i]) != null) {
                funcs[i] = funcMap.get(keys[i]);
            } else {
                funcs[i] = null;
            }
        }
        initColumns(titles, keys, types, funcs);
    }

    public void initColumns(String[] titles, String[] keys, Integer[] types, Function<Object, Object>[] funcs) {
        List<ExcelColumn> list = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            ExcelColumn column = new ExcelColumn();
            column.setTitle(titles[i]);
            column.setKey(keys[i]);
            column.setType(types[i]);
            column.setFunction(funcs[i]);
            list.add(column);
        }
        this.columns = list;
    }


    /**
     * @return the columns
     */
    public List<ExcelColumn> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<ExcelColumn> columns) {
        this.columns = columns;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return the apiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * @param apiUrl the apiUrl to set
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * @return the apiVersion
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * @param apiVersion the apiVersion to set
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * @return the readSize
     */
    public int getReadSize() {
        return readSize;
    }

    /**
     * @param readSize the readSize to set
     */
    public void setReadSize(int readSize) {
        this.readSize = readSize;
    }

    public Map getHearderMap() {
        return hearderMap;
    }

    public void setHearderMap(Map hearderMap) {
        this.hearderMap = hearderMap;
    }

}
