package cn.com.flaginfo.platform.export.model.excel;

import cn.com.flaginfo.platform.export.model.excel.func.Function;

/**
 * 列信息
 * @author chengbin.luo
 */
public class ExcelColumn{

	public String title;
	public String key;
	public Integer type;
	public Function function;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
}
