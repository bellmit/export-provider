package cn.com.flaginfo.platform.export.common.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.com.flaginfo.platform.common.support.DateEditor;
import cn.com.flaginfo.platform.common.support.QueryInfo;
import cn.com.flaginfo.platform.common.support.SessionHolder;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	                              ServletRequestDataBinder binder) throws Exception {  
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, new DateEditor()); 
	}
	
	protected <T> T getSessionHolder(Class<T> clazz){
		return SessionHolder.get(clazz);
	}
	
	
	public static QueryInfo getQueryInfo(Map requestMap) {
		QueryInfo queryInfo = new QueryInfo();
		if(requestMap ==null){
			requestMap = new HashMap() ;
		}
		Map pageInfo = (Map) requestMap.get("pageInfo");
		if (pageInfo == null) {
			pageInfo = new HashMap();
			pageInfo.put("curPage", 1);
			pageInfo.put("pageLimit", 10);
		}
		int curPage = (Integer) pageInfo.get("curPage");
		if (curPage > 0) {
			queryInfo.setCurPage(curPage);
		}
		queryInfo.setPageLimit((Integer) pageInfo.get("pageLimit"));
		String startId = (String) pageInfo.get("startId");
		queryInfo.setStartId(startId);
		
		return queryInfo;
	}
	

	
	/**
	 * 判断Map中的值是否为空
	 *
	 * @param m
	 * @param args
	 * @return
	 */
	protected boolean isEmptyInMap(Map m, Object... keys) {
		if (m == null) {
			logger.info("map is null");
			return true;
		}
		if (keys == null || keys.length == 0) {
			return true;
		}
		for (Object o : keys) {
			if (isEmpty(m.get(o))) {
				logger.info("isEmptyInMap key:" + o + " is null");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为空
	 *
	 * @param args
	 * @return
	 */
	protected boolean isEmpty(Object... args) {
		if (args == null || args.length == 0) {
			return true;
		}
		for (Object o : args) {
			if (o == null || "".equals(o)) {
				return true;
			}
		}
		return false;
	}
}
