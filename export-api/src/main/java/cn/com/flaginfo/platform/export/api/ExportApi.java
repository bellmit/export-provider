package cn.com.flaginfo.platform.export.api;

import java.util.Map;

import cn.com.flaginfo.platform.api.common.base.PagableJsonViewResponse;
import cn.com.flaginfo.platform.api.common.base.SingleValueJsonViewResponse;

public interface ExportApi {
	
	 /**
     * Excel导出
     * 
     * @param content
     * @return
     */
    public SingleValueJsonViewResponse excel(String content);
	
	/**
     * 导出任务查询
     * 
     * @param content
     * @return
     */
	public PagableJsonViewResponse listPartial(String content);
	
	 /**
     * 导出任务新增
     * @param content
     * @return
     * @since 6.0
     */
    public SingleValueJsonViewResponse<Map<String, String>> exportTaskAdd(String content);
    
    /**
     * 导出任务更新
     * @param content
     * @return
     * @since 6.0
     */
    public SingleValueJsonViewResponse exportTaskUpdate(String content);

}
