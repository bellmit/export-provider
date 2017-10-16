package cn.com.flaginfo.platform.export.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.boot.hsf.annotation.HSFConsumer;

import cn.com.flaginfo.platform.api.common.base.PagableJsonViewResponse;
import cn.com.flaginfo.platform.api.common.base.SingleValueJsonViewResponse;
import cn.com.flaginfo.platform.export.api.ExportApi;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
	
	@HSFConsumer(serviceGroup="export-service",serviceVersion="1.0.0")
	private ExportApi exportApi;
	
	/**
     * 导出任务查询
     * 
     * @param content
     * @return
     */
	@RequestMapping(value = "/list")
	@ResponseBody
	public String exportList(@RequestParam("content") String content){
		PagableJsonViewResponse result = exportApi.listPartial(content);
		return result.toString();
	}
	
	/**
     * Excel导出
     * 
     * @param content
     * @return
     */
	@RequestMapping(value = "/excel")
	@ResponseBody
	public String export(@RequestParam("content") String content){
		SingleValueJsonViewResponse result = exportApi.excel(content);
		return result.toString();
	}
	
	 /**
     * 导出任务新增
     * @param content
     * @return
     * @since 6.0
     */
	@RequestMapping(value = "/addTask")
	@ResponseBody
	public String exportTaskAdd(@RequestParam("content") String content){
		SingleValueJsonViewResponse result = exportApi.exportTaskAdd(content);
		return result.toString();
	}
	
	/**
     * 导出任务更新
     * @param content
     * @return
     * @since 6.0
     */
	@RequestMapping(value = "/updateTask")
	@ResponseBody
	public String exportTaskUpdate(@RequestParam("content") String content){
		SingleValueJsonViewResponse result = exportApi.exportTaskUpdate(content);
		return result.toString();
	}
}
