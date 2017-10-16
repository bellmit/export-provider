package cn.com.flaginfo.platform.export;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.taobao.pandora.boot.test.junit4.DelegateTo;
import com.taobao.pandora.boot.test.junit4.PandoraBootRunner;

import cn.com.flaginfo.WebApplication;
import cn.com.flaginfo.platform.common.model.Operator;
import cn.com.flaginfo.platform.common.support.PropertiesSourceInitializer;
import cn.com.flaginfo.platform.common.support.SessionHolder;
import cn.com.flaginfo.platform.common.util.JsonHelper;
import cn.com.flaginfo.platform.export.controller.ExportController;

@RunWith(PandoraBootRunner.class)
@DelegateTo(SpringJUnit4ClassRunner.class)
// 加载测试需要的类，一定要加入Spring Boot的启动类，其次需要加入本类
@SpringBootTest(classes = { WebApplication.class, ExportConsumerTest.class })
@ContextConfiguration(initializers = PropertiesSourceInitializer.class)
public class ExportConsumerTest {
	
	private Logger logger = LoggerFactory.getLogger(ExportConsumerTest.class);
	
	private MockMvc mockMvc;  
    @Autowired  
    private ExportController exportController;  
    @Before  
    public void setUp() throws Exception {  
         mockMvc = MockMvcBuilders.standaloneSetup(exportController).build();  
    } 
    
    @Test
    public void testList() throws Exception{
    	Map<String, Object> params = new HashMap<>();
    	params.put("id", "16070618292512064933");
    	
    	String content = JsonHelper.parseToJson(params);
    	mockMvc.perform(MockMvcRequestBuilders.post("/export/list").param("content", content)).andDo(MockMvcResultHandlers.print());
    }
    
    @Test
    public void testExcel() throws Exception{
    	Map<String, Object> params = new HashMap<>();
    	params.put("spId", "11111111");
    	params.put("businessType", "ContactsExportTask");
//    	params.put("contactsId", "222222");
    	params.put("operator", "YC001");
    	params.put("operatorId", "17050518032990050480");
    	params.put("status", "0");
    	params.put("fileType", "xlsx");
    	params.put("source", "1");
    	params.put("fileName", "登录概况_导出列表");
    	params.put("curPage", "0");
    	Map<String, Object> data = new HashMap<>();
    	data.put("startTime", "2017-05-08");
    	data.put("curPage", "1");
    	data.put("userId", "17050518032990050480");
    	data.put("spId", "17050518032990050479");
    	data.put("pageLimit", "20");
    	data.put("endTime", "2017-05-08");
    	params.put("params", data);
    	String content = JsonHelper.parseToJson(params);
    	Operator operator = new Operator();
    	operator.setIp("127.0.0.1");
    	operator.setSpId("22222222");
    	operator.setUserId("000000");
    	operator.setUserName("test");
    	SessionHolder.set(operator);
    	mockMvc.perform(MockMvcRequestBuilders.post("/export/excel")
    			.param("content", content)
    			.sessionAttr("Operator", operator))
    	.andDo(MockMvcResultHandlers.print());
    }
    
}
