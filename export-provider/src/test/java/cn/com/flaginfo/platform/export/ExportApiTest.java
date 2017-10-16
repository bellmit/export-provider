package cn.com.flaginfo.platform.export;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.boot.hsf.annotation.HSFConsumer;

import cn.com.flaginfo.platform.common.support.QueryInfo;
import cn.com.flaginfo.platform.export.api.ExportApi;

//@RunWith(PandoraBootRunner.class)
//@DelegateTo(SpringJUnit4ClassRunner.class)
//// 加载测试需要的类，一定要加入Spring Boot的启动类，其次需要加入本类
//@SpringBootTest(classes = { WebApplication.class, ExportApiTest.class })
//@ContextConfiguration(initializers = PropertiesSourceInitializer.class)
public class ExportApiTest {
	
	private Logger logger = LoggerFactory.getLogger(ExportApiTest.class);
	
	@Autowired
	private ApplicationContext ctx;
	
	@HSFConsumer(serviceGroup="export-service",serviceVersion="1.0.0")
	ExportApi exportApi;
	
	@Test
	public void testList(){
		Map<String, Object> params = new HashMap<>();
		params.put("id", "16050810301890063938");
		params.put("spId", "17050518032990050479");
		params.put("status", "1");
		params.put("type", "CmcSendKeywordTask");
		params.put("fileType", "xls");
		params.put("startTime", "2016-05-08 09:30:18");
		params.put("endTime", "2016-05-08 11:30:18");
		params.put("operatorId", "17050518032990050480");
		params.put("source", "1");
		params.put("exportTitle", "关键字统计_导出列表");
		QueryInfo queryInfo = new QueryInfo();
		queryInfo.setTotal(99999);
		params.put("queryInfo", queryInfo);
//		PagableJsonViewResponse response = exportApi.listPartial(JsonHelper.parseToJson(params));
		
//		logger.info(JsonHelper.parseToJson(response.getList()));
	}
	
	/*@Test
	public void testAdd(){
		Map<String, Object> params = new HashMap<>();
		params.put("spId", "17050518032990050479");
		params.put("operator", "YC001");
		params.put("operatorId", "17050518032990050480");
		params.put("createTime", "2016-05-08 10:30:18");
		params.put("requestParma", "{\"startTime\":\"2017-05-08\",\"curPage\":\"1\",\"userId\":\"17050518032990050480\",\"spId\":\"17050518032990050479\",\"pageLimit\":\"20\",\"endTime\":\"2017-05-08\"}");
		params.put("remark", null);
		params.put("fileType", "xls");
		params.put("exportTitle", "test123");
		params.put("type", "CmcSendKeywordTask");
		params.put("exportCurPage", null);
		params.put("source", "1");
		params.put("headParam", null);
		SingleValueJsonViewResponse<Map<String, String>> response = exportApi.exportTaskAdd(JsonHelper.parseToJson(params));
		logger.info(JsonHelper.parseToJson(response.getData()));
	}*/
	
	/*@SuppressWarnings("unchecked")
	@Test
	public void testUpdate(){
		Map<String, Object> params = new HashMap<>();
		params.put("id", "17052018184790114016");
//		params.put("filePath", "/ext/export/201705/AppLoginStatisticsTask/登录概况_导出列表17052018184790114016.xlsx9");
//		params.put("exportNum", "18");
//		params.put("exportCost", "645");
//		params.put("startTime", "2017-05-20 18:18:48");
//		params.put("status", "1");
//		params.put("endTime", "2017-05-20 18:18:49");
		params.put("remark", "test001");
		SingleValueJsonViewResponse<Map<String, String>> response = exportApi.exportTaskUpdate(JsonHelper.parseToJson(params));
		logger.info(JsonHelper.parseToJson(response.getData()));
	}*/
	
	/*@Test
	public void testExcel(){
		Map<String, Object> params = new HashMap<>();
		params.put("type", "ContactsExportTask");
		params.put("businessType", "ContactsExportTask");
		SingleValueJsonViewResponse response = exportApi.excel(JsonHelper.parseToJson(params));
		logger.info(JsonHelper.parseToJson(response.getData()));
	}*/
	
}
